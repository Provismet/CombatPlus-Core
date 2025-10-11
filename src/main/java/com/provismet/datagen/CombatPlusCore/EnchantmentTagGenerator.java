package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.utility.tag.CPCEnchantmentTags;
import com.provismet.lilylib.datagen.tag.LilyTagProviders;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEnchantmentTags;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class EnchantmentTagGenerator extends LilyTagProviders.LilyEnchantmentTagProvider {
    public EnchantmentTagGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup wrapperLookup) {
        this.builder(CPCEnchantmentTags.ADDITIONAL_DAMAGE);
        this.builder(CPCEnchantmentTags.ASPECT).add(Enchantments.FIRE_ASPECT);
        this.builder(CPCEnchantmentTags.WEAPON_UTILITY).add(Enchantments.SWEEPING_EDGE).add(Enchantments.WIND_BURST);
        this.builder(CPCEnchantmentTags.OFFHAND);

        this.builder(ConventionalEnchantmentTags.WEAPON_DAMAGE_ENHANCEMENTS)
            .addOptionalTag(CPCEnchantmentTags.ADDITIONAL_DAMAGE);

        // Exclusive Sets
        this.builder(EnchantmentTags.DAMAGE_EXCLUSIVE_SET)
            .addOptionalTag(CPCEnchantmentTags.ADDITIONAL_DAMAGE)
            .addOptionalTag(CPCEnchantmentTags.OFFHAND);

        this.builder(CPCEnchantmentTags.ADDITION_DAMAGE_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.ADDITIONAL_DAMAGE)
            .addOptionalTag(CPCEnchantmentTags.OFFHAND);

        this.builder(CPCEnchantmentTags.ASPECT_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.ASPECT_OFFHAND_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.OFFHAND);

        this.builder(CPCEnchantmentTags.ASPECT_OFFHAND_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.ASPECT);

        this.builder(CPCEnchantmentTags.OFFHAND_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.ADDITIONAL_DAMAGE)
            .addOptionalTag(ConventionalEnchantmentTags.WEAPON_DAMAGE_ENHANCEMENTS);

        this.builder(CPCEnchantmentTags.WEAPON_UTILITY_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.OFFHAND)
            .addOptionalTag(CPCEnchantmentTags.WEAPON_UTILITY_OFFHAND_EXCLUSIVE);

        this.builder(CPCEnchantmentTags.WEAPON_UTILITY_OFFHAND_EXCLUSIVE)
            .addOptionalTag(CPCEnchantmentTags.WEAPON_UTILITY);

        this.builder(CPCEnchantmentTags.ALL)
            .addOptionalTag(EnchantmentTags.TREASURE)
            .addOptionalTag(EnchantmentTags.NON_TREASURE);
    }
}
