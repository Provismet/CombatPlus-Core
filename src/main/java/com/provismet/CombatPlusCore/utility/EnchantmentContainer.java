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

    /**
     * @return The RegistryKey associated with this enchantment.
     */
    public RegistryKey<Enchantment> getKey () {
        return this.key;
    }

    /**
     * Creates a generic registry entry for this enchantment.
     * <p>
     * NOTE: This method should only be used if the alternatives are unavailable.
     *
     * @return An optional RegistryEntry of this enchantment.
     */
    public Optional<? extends RegistryEntry<Enchantment>> getEntry () {
        return BuiltinRegistries.createWrapperLookup().createRegistryLookup().getOptionalEntry(RegistryKeys.ENCHANTMENT, this.getKey());
    }

    /**
     * Uses a registry manager to obtain an entry for this enchantment.
     *
     * @see net.minecraft.world.WorldView
     *
     * @param manager A registry manager, typically obtained from a World.
     * @return An optional RegistryEntry of this enchantment.
     */
    public Optional<? extends RegistryEntry<Enchantment>> getEntry (DynamicRegistryManager manager) {
        return manager.get(RegistryKeys.ENCHANTMENT).getEntry(this.key);
    }

    /**
     * Uses a registry lookup to obtain an entry for this enchantment.
     *
     * @param registryLookup A lookup, typically obtained from the data generator.
     * @return An optional RegistryEntry of this enchantment.
     */
    public Optional<? extends RegistryEntry<Enchantment>> getEntry (RegistryWrapper.WrapperLookup registryLookup) {
        return registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOptional(this.key);
    }

    /**
     * Creates a generic registry entry for this enchantment.
     * <p>
     * NOTE: This method should only be used if the alternatives are unavailable.
     *
     * @return An RegistryEntry of this enchantment.
     */
    public RegistryEntry<Enchantment> getEntryOrThrow () {
        return this.getEntry().orElseThrow();
    }

    /**
     * Uses a registry manager to obtain an entry for this enchantment.
     *
     * @see net.minecraft.world.WorldView
     *
     * @param manager A registry manager, typically obtained from a World.
     * @return An RegistryEntry of this enchantment.
     */
    public RegistryEntry<Enchantment> getEntryOrThrow (DynamicRegistryManager manager) {
        return this.getEntry(manager).orElseThrow();
    }

    /**
     * Uses a registry lookup to obtain an entry for this enchantment.
     *
     * @param registryLookup A lookup, typically obtained from the data generator.
     * @return An RegistryEntry of this enchantment.
     */
    public RegistryEntry<Enchantment> getEntryOrThrow (RegistryWrapper.WrapperLookup registryLookup) {
        return this.getEntry(registryLookup).orElseThrow();
    }

    /**
     * Unwraps a registerable provided by the vanilla bootstrapper to produce the enchantment builder associated with this container.
     *
     * @param registerable The registry object provided by the vanilla bootstrapper.
     * @return The enchantment builder.
     */
    public Enchantment.Builder getBuilder (Registerable<Enchantment> registerable) {
        RegistryEntryLookup<Item> itemLookup = registerable.getRegistryLookup(RegistryKeys.ITEM);
        RegistryEntryLookup<Enchantment> enchantmentLookup = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        RegistryEntryLookup<DamageType> damageLookup = registerable.getRegistryLookup(RegistryKeys.DAMAGE_TYPE);
        RegistryEntryLookup<Block> blockLookup = registerable.getRegistryLookup(RegistryKeys.BLOCK);
        return this.getBuilder(itemLookup, enchantmentLookup, damageLookup, blockLookup);
    }

    /**
     * Unwraps the Combat+ data-gen EnchantmentBuilder to produce the enchantment builder associated with this container.
     *
     * @see CPCEnchantmentProvider
     *
     * @param enchantmentBuilder The builder provided by the Combat+ provider.
     * @return The enchantment builder.
     */
    public Enchantment.Builder getBuilder (CPCEnchantmentProvider.EnchantmentBuilder enchantmentBuilder) {
        return this.getBuilder(enchantmentBuilder.itemLookup, enchantmentBuilder.enchantmentLookup, enchantmentBuilder.damageTypeLookup, enchantmentBuilder.blockLookup);
    }

    /**
     * Creates the enchantment builder associated with this container.
     *
     * @param itemLookup Registry lookup for item tags.
     * @param enchantmentLookup Registry lookup for enchantment tags.
     * @param damageLookup Registry lookup for damage type tags.
     * @param blockLookup  Registry lookup for block tags.
     * @return The enchantment builder.
     */
    public Enchantment.Builder getBuilder (RegistryEntryLookup<Item> itemLookup, RegistryEntryLookup<Enchantment> enchantmentLookup, RegistryEntryLookup<DamageType> damageLookup, RegistryEntryLookup<Block> blockLookup) {
        return this.internalBuilder.create(itemLookup, enchantmentLookup, damageLookup, blockLookup);
    }

    @FunctionalInterface
    public interface BuilderBuilder {
        Enchantment.Builder create (RegistryEntryLookup<Item> itemLookup, RegistryEntryLookup<Enchantment> enchantmentLookup, RegistryEntryLookup<DamageType> damageLookup, RegistryEntryLookup<Block> blockLookup);
    }
}
