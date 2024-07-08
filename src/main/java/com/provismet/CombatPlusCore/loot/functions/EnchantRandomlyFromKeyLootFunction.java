package com.provismet.CombatPlusCore.loot.functions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.registries.CPCLootFunctionTypes;
import com.provismet.lilylib.container.EnchantmentContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class EnchantRandomlyFromKeyLootFunction extends AbstractEnchantmentLootFunction {
    public static final MapCodec<EnchantRandomlyFromKeyLootFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> EnchantRandomlyFromKeyLootFunction.createCodec(instance).and(RegistryKey.createCodec(RegistryKeys.ENCHANTMENT).listOf().fieldOf("enchantments").forGetter(function -> function.keys)).apply(instance, EnchantRandomlyFromKeyLootFunction::new));

    private final List<RegistryKey<Enchantment>> keys;

    protected EnchantRandomlyFromKeyLootFunction (List<LootCondition> conditions, boolean onlyCompatibleItems, List<RegistryKey<Enchantment>> keys) {
        super(conditions, onlyCompatibleItems);
        this.keys = keys;
    }

    @Override
    public LootFunctionType<? extends ConditionalLootFunction> getType () {
        return CPCLootFunctionTypes.ENCHANT_FROM_KEY;
    }

    @Override
    protected ItemStack process (ItemStack itemStack, LootContext context) {
        if (this.keys.isEmpty()) return itemStack;
        Stream<RegistryEntry.Reference<Enchantment>> valid = this.keys
            .stream()
            .map(key -> context.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(key))
            .filter(entry -> this.isAcceptable(itemStack, entry));

        Optional<RegistryEntry.Reference<Enchantment>> enchantment = Util.getRandomOrEmpty(valid.toList(), context.getRandom());
        if (enchantment.isEmpty()) return itemStack;
        return this.enchantWithRandomLevel(itemStack, enchantment.get(), context.getRandom());
    }

    public static Builder create () {
        return new Builder();
    }

    public static class Builder extends AbstractEnchantmentLootFunction.Builder<Builder> {
        private final List<RegistryKey<Enchantment>> keys = new ArrayList<>();

        @Override
        protected Builder getThisBuilder () {
            return this;
        }

        public Builder option (RegistryKey<Enchantment> key) {
            this.keys.add(key);
            return this;
        }

        public Builder option (EnchantmentContainer container) {
            this.keys.add(container.getKey());
            return this;
        }

        @Override
        public LootFunction build () {
            return new EnchantRandomlyFromKeyLootFunction(this.getConditions(), this.isOnlyForCompatibleItems(), this.keys);
        }
    }
}
