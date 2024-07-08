package com.provismet.CombatPlusCore.loot.functions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.registries.CPCLootFunctionTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Combat+ loot function for enchanting from an enchantment tag.
 * <p>
 * A single, compatible, enchantment will be selected from the tag.
 */
public class EnchantRandomlyFromTagLootFunction extends AbstractEnchantmentLootFunction {
    public static final MapCodec<EnchantRandomlyFromTagLootFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> EnchantRandomlyFromTagLootFunction.createCodec(instance).and(TagKey.codec(RegistryKeys.ENCHANTMENT).fieldOf("tag").forGetter(function -> function.tagKey)).apply(instance, EnchantRandomlyFromTagLootFunction::new));
    private final TagKey<Enchantment> tagKey;

    protected EnchantRandomlyFromTagLootFunction (List<LootCondition> conditions, boolean onlyCompatibleItems, TagKey<Enchantment> tag) {
        super(conditions, onlyCompatibleItems);
        this.tagKey = tag;
    }

    @Override
    public LootFunctionType<? extends ConditionalLootFunction> getType () {
        return CPCLootFunctionTypes.ENCHANT_FROM_TAG;
    }

    @Override
    protected ItemStack process (ItemStack itemStack, LootContext context) {
        if (this.tagKey == null) return itemStack;

        Stream<RegistryEntry<Enchantment>> enchantments = context.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(this.tagKey).stream().filter(enchant -> this.isAcceptable(itemStack, enchant));
        Optional<RegistryEntry<Enchantment>> optionalEnchant = Util.getRandomOrEmpty(enchantments.toList(), context.getRandom());

        if (optionalEnchant.isEmpty()) return itemStack;
        return this.enchantWithRandomLevel(itemStack, optionalEnchant.get(), context.getRandom());
    }

    public static Builder create () {
        return new Builder();
    }

    public static Builder create (TagKey<Enchantment> tag) {
        return new Builder().tag(tag);
    }

    public static class Builder extends AbstractEnchantmentLootFunction.Builder<Builder> {
        private TagKey<Enchantment> tagKey;

        @Override
        protected Builder getThisBuilder () {
            return this;
        }

        public Builder tag (TagKey<Enchantment> tagKey) {
            this.tagKey = tagKey;
            return this;
        }

        @Override
        public LootFunction build () {
            return new EnchantRandomlyFromTagLootFunction(this.getConditions(), this.isOnlyForCompatibleItems(), this.tagKey);
        }
    }
}
