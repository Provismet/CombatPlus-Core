package com.provismet.CombatPlusCore.api.loot.functions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Combat+ loot function for enchanting from an enchantment tag.
 * <p>
 * A single, compatible, enchantment will be selected from the tag.
 */
public class EnchantRandomlyFromTagLootFunction extends AbstractEnchantmentLootFunction {
    public static final MapCodec<EnchantRandomlyFromTagLootFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> EnchantRandomlyFromTagLootFunction.createCodec(instance).and(TagKey.hashedCodec(Registries.ENCHANTMENT).fieldOf("tag").forGetter(function -> function.tagKey)).apply(instance, EnchantRandomlyFromTagLootFunction::new));
    private final TagKey<Enchantment> tagKey;

    protected EnchantRandomlyFromTagLootFunction (List<LootItemCondition> conditions, boolean onlyCompatibleItems, TagKey<Enchantment> tag) {
        super(conditions, onlyCompatibleItems);
        this.tagKey = tag;
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemConditionalFunction> codec () {
        return CODEC;
    }

    @Override
    protected ItemStack run (ItemStack itemStack, LootContext context) {
        if (this.tagKey == null) return itemStack;

        Stream<Holder<Enchantment>> enchantments = context.getLevel()
            .registryAccess()
            .lookupOrThrow(Registries.ENCHANTMENT)
            .getOrThrow(this.tagKey)
            .stream()
            .filter(enchant -> this.isAcceptable(itemStack, enchant));
        Optional<Holder<Enchantment>> optionalEnchant = Util.getRandomSafe(enchantments.toList(), context.getRandom());

        if (optionalEnchant.isEmpty()) return itemStack;
        return this.enchantWithRandomLevel(itemStack, optionalEnchant.get(), context.getRandom());
    }

    public static EnchantRandomlyFromTagLootFunction.Builder create () {
        return new EnchantRandomlyFromTagLootFunction.Builder();
    }

    public static EnchantRandomlyFromTagLootFunction.Builder create (TagKey<Enchantment> tag) {
        return new EnchantRandomlyFromTagLootFunction.Builder().tag(tag);
    }

    public static class Builder extends AbstractEnchantmentLootFunction.Builder<EnchantRandomlyFromTagLootFunction.Builder> {
        private TagKey<Enchantment> tagKey;

        @Override
        protected EnchantRandomlyFromTagLootFunction.Builder getThis () {
            return this;
        }

        public EnchantRandomlyFromTagLootFunction.Builder tag (TagKey<Enchantment> tagKey) {
            this.tagKey = tagKey;
            return this;
        }

        @Override
        public LootItemFunction build () {
            return new EnchantRandomlyFromTagLootFunction(this.getConditions(), this.isOnlyForCompatibleItems(), this.tagKey);
        }
    }
}
