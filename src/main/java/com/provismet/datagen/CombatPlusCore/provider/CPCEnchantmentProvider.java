package com.provismet.datagen.CombatPlusCore.provider;

import com.provismet.CombatPlusCore.utility.EnchantmentContainer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public abstract class CPCEnchantmentProvider extends FabricDynamicRegistryProvider {
    public CPCEnchantmentProvider (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public final String getName () {
        return "enchantment";
    }

    protected RegistryKey<Enchantment> createKey (Identifier id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    @Override
    protected final void configure (RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        EnchantmentBuilder builder = new EnchantmentBuilder(entries);
        configure(wrapperLookup, entries, builder);
    }

    protected abstract void configure (RegistryWrapper.WrapperLookup wrapperLookup, Entries entries, EnchantmentBuilder builder);

    public static class EnchantmentBuilder {
        public final RegistryEntryLookup<Item> itemLookup;
        public final RegistryEntryLookup<DamageType> damageTypeLookup;
        public final RegistryEntryLookup<Block> blockLookup;
        public final RegistryEntryLookup<Enchantment> enchantmentLookup;

        private final Entries entries;

        protected EnchantmentBuilder (Entries entries) {
            this.entries = entries;
            this.itemLookup = entries.getLookup(RegistryKeys.ITEM);
            this.damageTypeLookup = entries.getLookup(RegistryKeys.DAMAGE_TYPE);
            this.blockLookup = entries.getLookup(RegistryKeys.BLOCK);
            this.enchantmentLookup = entries.getLookup(RegistryKeys.ENCHANTMENT);
        }

        public void add (Identifier id, Enchantment.Builder enchantmentBuilder) {
            this.entries.add(RegistryKey.of(RegistryKeys.ENCHANTMENT, id), enchantmentBuilder.build(id));
        }

        public void add (Identifier id, Enchantment.Builder enchantmentBuilder, ResourceCondition... conditions) {
            this.entries.add(RegistryKey.of(RegistryKeys.ENCHANTMENT, id), enchantmentBuilder.build(id), conditions);
        }

        public void add (RegistryKey<Enchantment> enchantmentKey, Enchantment.Builder enchantmentBuilder) {
            this.add(enchantmentKey.getValue(), enchantmentBuilder);
        }

        public void add (RegistryKey<Enchantment> enchantmentKey, Enchantment.Builder enchantmentBuilder, ResourceCondition... conditions) {
            this.add(enchantmentKey.getValue(), enchantmentBuilder, conditions);
        }

        public void add (EnchantmentContainer container) {
            this.add(container.getKey(), container.getBuilder(this));
        }

        public void add (EnchantmentContainer container, ResourceCondition... conditions) {
            this.add(container.getKey(), container.getBuilder(this), conditions);
        }

        public RegistryEntryList<Item> getItemEntryList (TagKey<Item> tag) {
            return this.itemLookup.getOrThrow(tag);
        }

        public RegistryEntryList<DamageType> getDamageTypeEntryList (TagKey<DamageType> tag) {
            return this.damageTypeLookup.getOrThrow(tag);
        }

        public RegistryEntryList<Block> getBlockEntryList (TagKey<Block> tag) {
            return this.blockLookup.getOrThrow(tag);
        }

        public RegistryEntryList<Enchantment> getEnchantmentEntryList (TagKey<Enchantment> tag) {
            return this.enchantmentLookup.getOrThrow(tag);
        }
    }
}
