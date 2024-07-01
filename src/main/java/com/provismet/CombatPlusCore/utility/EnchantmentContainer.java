package com.provismet.CombatPlusCore.utility;

import com.provismet.datagen.CombatPlusCore.provider.CPCEnchantmentProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Optional;

/**
 * Utility class for linking a registry key to an enchantment builder.
 * <p>
 * The EnchantmentContainer allows the enchantment builder to be reused between data-gen and in bootstrapping.
 * <p>
 * The EnchantmentContainer can be fed directly to the {@link CPCEnchantmentProvider.EnchantmentBuilder} during
 * data generation.
 *
 * @see RegistryKey
 * @see Enchantment.Builder
 * @see CPCEnchantmentProvider
 */
public class EnchantmentContainer {
    private final RegistryKey<Enchantment> key;
    private final BuilderBuilder internalBuilder;

    public EnchantmentContainer (Identifier id, BuilderBuilder builder) {
        this.key = RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
        this.internalBuilder = builder;
    }

    public RegistryKey<Enchantment> getKey () {
        return this.key;
    }

    public Optional<? extends RegistryEntry<Enchantment>> getEntry () {
        return BuiltinRegistries.createWrapperLookup().createRegistryLookup().getOptionalEntry(RegistryKeys.ENCHANTMENT, this.getKey());
    }

    public Optional<? extends RegistryEntry<Enchantment>> getEntry (DynamicRegistryManager manager) {
        return manager.get(RegistryKeys.ENCHANTMENT).getEntry(this.key);
    }

    public Optional<? extends RegistryEntry<Enchantment>> getEntry (RegistryWrapper.WrapperLookup registryLookup) {
        return registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOptional(this.key);
    }

    public RegistryEntry<Enchantment> getEntryOrThrow () {
        Optional<RegistryEntry.Reference<Enchantment>> reference = BuiltinRegistries.createWrapperLookup().createRegistryLookup().getOptionalEntry(
            RegistryKeys.ENCHANTMENT, this.getKey()
        );
        return reference.orElseThrow();
    }

    public RegistryEntry<Enchantment> getEntryOrThrow (DynamicRegistryManager manager) {
        Optional<RegistryEntry.Reference<Enchantment>> reference = manager.get(RegistryKeys.ENCHANTMENT).getEntry(this.key);
        return reference.orElseThrow();
    }

    public RegistryEntry<Enchantment> getEntryOrThrow (RegistryWrapper.WrapperLookup registryLookup) {
        Optional<RegistryEntry.Reference<Enchantment>> reference = registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOptional(this.key);
        return reference.orElseThrow();
    }

    public Enchantment.Builder getBuilder (Registerable<Enchantment> registerable) {
        RegistryEntryLookup<Item> itemLookup = registerable.getRegistryLookup(RegistryKeys.ITEM);
        RegistryEntryLookup<Enchantment> enchantmentLookup = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        RegistryEntryLookup<DamageType> damageLookup = registerable.getRegistryLookup(RegistryKeys.DAMAGE_TYPE);
        RegistryEntryLookup<Block> blockLookup = registerable.getRegistryLookup(RegistryKeys.BLOCK);
        return this.getBuilder(itemLookup, enchantmentLookup, damageLookup, blockLookup);
    }

    public Enchantment.Builder getBuilder (CPCEnchantmentProvider.EnchantmentBuilder enchantmentBuilder) {
        return this.getBuilder(enchantmentBuilder.itemLookup, enchantmentBuilder.enchantmentLookup, enchantmentBuilder.damageTypeLookup, enchantmentBuilder.blockLookup);
    }

    public Enchantment.Builder getBuilder (RegistryEntryLookup<Item> itemLookup, RegistryEntryLookup<Enchantment> enchantmentLookup, RegistryEntryLookup<DamageType> damageLookup, RegistryEntryLookup<Block> blockLookup) {
        return this.internalBuilder.create(itemLookup, enchantmentLookup, damageLookup, blockLookup);
    }

    @FunctionalInterface
    public interface BuilderBuilder {
        Enchantment.Builder create (RegistryEntryLookup<Item> itemLookup, RegistryEntryLookup<Enchantment> enchantmentLookup, RegistryEntryLookup<DamageType> damageLookup, RegistryEntryLookup<Block> blockLookup);
    }
}
