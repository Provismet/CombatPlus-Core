package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.utility.tag.CPCEnchantmentTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEnchantmentTags;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class EnchantmentTagGenerator extends FabricTagProvider.EnchantmentTagProvider {
    public EnchantmentTagGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(CPCEnchantmentTags.ADDITIONAL_DAMAGE);
        getOrCreateTagBuilder(CPCEnchantmentTags.ASPECT).add(Enchantments.FIRE_ASPECT);
        getOrCreateTagBuilder(CPCEnchantmentTags.WEAPON_UTILITY).add(Enchantments.SWEEPING_EDGE).add(Enchantments.WIND_BURST);
        getOrCreateTagBuilder(CPCEnchantmentTags.OFFHAND);

        getOrCreateTagBuilder(ConventionalEnchantmentTags.WEAPON_DAMAGE_ENHANCEMENTS)
            .addOptionalTag(CPCEnchantmentTags.ADDITIONAL_DAMAGE);

        // Exclusive Sets
        getOrCreateTagBuilder(EnchantmentTags.DAMAGE_EXCLUSIVE_SET)
            .addOptionalTag(CPCEnchantmentTags.ADDITIONAL_DAMAGE)
            .addOptionalTag(CPCEnchantmentTags.OFFHAND);

        getOrCreateTagBuilder(CPCEnchantmentTags.ADDITION_DAMAGE_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.ADDITIONAL_DAMAGE)
            .addOptionalTag(CPCEnchantmentTags.OFFHAND);

        getOrCreateTagBuilder(CPCEnchantmentTags.ASPECT_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.ASPECT_OFFHAND_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.OFFHAND);

        getOrCreateTagBuilder(CPCEnchantmentTags.ASPECT_OFFHAND_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.ASPECT);

        getOrCreateTagBuilder(CPCEnchantmentTags.OFFHAND_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.ADDITIONAL_DAMAGE)
            .addOptionalTag(ConventionalEnchantmentTags.WEAPON_DAMAGE_ENHANCEMENTS);

        getOrCreateTagBuilder(CPCEnchantmentTags.WEAPON_UTILITY_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.OFFHAND)
            .addOptionalTag(CPCEnchantmentTags.WEAPON_UTILITY_OFFHAND_EXCLUSIVE);

        getOrCreateTagBuilder(CPCEnchantmentTags.WEAPON_UTILITY_OFFHAND_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.WEAPON_UTILITY);
    }
}
