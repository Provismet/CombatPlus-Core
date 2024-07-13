package com.provismet.CombatPlusCore.loot.functions;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

import java.util.List;

public abstract class AbstractEnchantmentLootFunction extends ConditionalLootFunction {
    protected final boolean onlyCompatibleItems;

    protected AbstractEnchantmentLootFunction (List<LootCondition> conditions, boolean onlyCompatibleItems) {
        super(conditions);
        this.onlyCompatibleItems = onlyCompatibleItems;
    }

    protected static <T extends AbstractEnchantmentLootFunction> Products.P2<RecordCodecBuilder.Mu<T>, List<LootCondition>, Boolean> createCodec (RecordCodecBuilder.Instance<T> instance) {
        return AbstractEnchantmentLootFunction.addConditionsField(instance)
            .and(Codec.BOOL
                .optionalFieldOf("only_compatible", true)
                .forGetter(foo -> foo.onlyCompatibleItems)
            );
    }

    protected boolean isAcceptable (ItemStack itemStack, RegistryEntry<Enchantment> enchantment) {
        if (!this.onlyCompatibleItems || itemStack.isOf(Items.BOOK)) return true;
        return enchantment.value().isAcceptableItem(itemStack);
    }

    protected ItemStack enchantWithRandomLevel (ItemStack itemStack, RegistryEntry<Enchantment> enchantment, Random random) {
        int level = MathHelper.nextInt(random, enchantment.value().getMinLevel(), enchantment.value().getMaxLevel());
        if (itemStack.isOf(Items.BOOK)) {
            itemStack = new ItemStack(Items.ENCHANTED_BOOK);
        }

        itemStack.addEnchantment(enchantment, level);
        return itemStack;
    }

    public static abstract class Builder<T extends Builder<T>> extends ConditionalLootFunction.Builder<T> {
        private boolean onlyCompatible = true;

        protected boolean isOnlyForCompatibleItems () {
            return this.onlyCompatible;
        }

        public T allowIncompatible () {
            this.onlyCompatible = false;
            return this.getThisBuilder();
        }

        public T disallowIncompatible () {
            this.onlyCompatible = true;
            return this.getThisBuilder();
        }
    }
}
