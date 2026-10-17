package com.provismet.CombatPlusCore.loot.functions;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public abstract class AbstractEnchantmentLootFunction extends LootItemConditionalFunction {
    protected final boolean onlyCompatibleItems;

    protected AbstractEnchantmentLootFunction (List<LootItemCondition> conditions, boolean onlyCompatibleItems) {
        super(conditions);
        this.onlyCompatibleItems = onlyCompatibleItems;
    }

    protected static <T extends AbstractEnchantmentLootFunction> Products.P2<RecordCodecBuilder.Mu<T>, List<LootItemCondition>, Boolean> createCodec (RecordCodecBuilder.Instance<T> instance) {
        return AbstractEnchantmentLootFunction.commonFields(instance)
            .and(Codec.BOOL
                .optionalFieldOf("only_compatible", true)
                .forGetter(foo -> foo.onlyCompatibleItems)
            );
    }

    protected boolean isAcceptable (ItemStack itemStack, Holder<Enchantment> enchantment) {
        if (!this.onlyCompatibleItems || itemStack.is(Items.BOOK)) return true;
        return enchantment.value().canEnchant(itemStack);
    }

    protected ItemStack enchantWithRandomLevel (ItemStack itemStack, Holder<Enchantment> enchantment, RandomSource random) {
        int level = Mth.nextInt(random, enchantment.value().getMinLevel(), enchantment.value().getMaxLevel());
        if (itemStack.is(Items.BOOK)) {
            itemStack = new ItemStack(Items.ENCHANTED_BOOK);
        }

        itemStack.enchant(enchantment, level);
        return itemStack;
    }

    public static abstract class Builder<T extends com.provismet.CombatPlusCore.loot.functions.AbstractEnchantmentLootFunction.Builder<T>> extends LootItemConditionalFunction.Builder<T> {
        private boolean onlyCompatible = true;

        protected boolean isOnlyForCompatibleItems () {
            return this.onlyCompatible;
        }

        public T allowIncompatible () {
            this.onlyCompatible = false;
            return this.getThis();
        }

        public T disallowIncompatible () {
            this.onlyCompatible = true;
            return this.getThis();
        }
    }
}
