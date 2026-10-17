package com.provismet.CombatPlusCore.loot.functions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.registries.CPCLootFunctionTypes;
import com.provismet.lilylib.container.EnchantmentContainer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class EnchantRandomlyFromKeyLootFunction extends AbstractEnchantmentLootFunction {
    public static final MapCodec<EnchantRandomlyFromKeyLootFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> EnchantRandomlyFromKeyLootFunction.createCodec(instance).and(ResourceKey.codec(Registries.ENCHANTMENT).listOf().fieldOf("enchantments").forGetter(function -> function.keys)).apply(instance, EnchantRandomlyFromKeyLootFunction::new));

    private final List<ResourceKey<Enchantment>> keys;

    protected EnchantRandomlyFromKeyLootFunction (List<LootItemCondition> conditions, boolean onlyCompatibleItems, List<ResourceKey<Enchantment>> keys) {
        super(conditions, onlyCompatibleItems);
        this.keys = keys;
    }

    @Override
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType () {
        return CPCLootFunctionTypes.ENCHANT_FROM_KEY;
    }

    @Override
    protected ItemStack run (ItemStack itemStack, LootContext context) {
        if (this.keys.isEmpty()) return itemStack;
        Stream<Holder.Reference<Enchantment>> valid = this.keys
            .stream()
            .map(key -> context.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key))
            .filter(entry -> this.isAcceptable(itemStack, entry));

        Optional<Holder.Reference<Enchantment>> enchantment = Util.getRandomSafe(valid.toList(), context.getRandom());
        if (enchantment.isEmpty()) return itemStack;
        return this.enchantWithRandomLevel(itemStack, enchantment.get(), context.getRandom());
    }

    public static com.provismet.CombatPlusCore.loot.functions.EnchantRandomlyFromKeyLootFunction.Builder create () {
        return new com.provismet.CombatPlusCore.loot.functions.EnchantRandomlyFromKeyLootFunction.Builder();
    }

    public static class Builder extends AbstractEnchantmentLootFunction.Builder<com.provismet.CombatPlusCore.loot.functions.EnchantRandomlyFromKeyLootFunction.Builder> {
        private final List<ResourceKey<Enchantment>> keys = new ArrayList<>();

        @Override
        protected com.provismet.CombatPlusCore.loot.functions.EnchantRandomlyFromKeyLootFunction.Builder getThis () {
            return this;
        }

        public com.provismet.CombatPlusCore.loot.functions.EnchantRandomlyFromKeyLootFunction.Builder option (ResourceKey<Enchantment> key) {
            this.keys.add(key);
            return this;
        }

        public com.provismet.CombatPlusCore.loot.functions.EnchantRandomlyFromKeyLootFunction.Builder option (EnchantmentContainer container) {
            this.keys.add(container.getKey());
            return this;
        }

        @Override
        public LootItemFunction build () {
            return new EnchantRandomlyFromKeyLootFunction(this.getConditions(), this.isOnlyForCompatibleItems(), this.keys);
        }
    }
}
