package com.provismet.CombatPlusCore.datagen.generator;

import com.provismet.CombatPlusCore.api.tag.CPCEnchantmentTags;
import com.provismet.lilylib.datagen.tag.LilyTagProviders;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEnchantmentTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.concurrent.CompletableFuture;

public class EnchantmentTagGenerator extends LilyTagProviders.LilyEnchantmentTagProvider {
    public EnchantmentTagGenerator (FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags (HolderLookup.Provider wrapperLookup) {
        this.builder(CPCEnchantmentTags.ADDITIONAL_DAMAGE);
        this.builder(CPCEnchantmentTags.ASPECT).add(Enchantments.FIRE_ASPECT);
        this.builder(CPCEnchantmentTags.WEAPON_UTILITY).add(Enchantments.SWEEPING_EDGE).add(Enchantments.WIND_BURST);
        this.builder(CPCEnchantmentTags.OFFHAND);

        this.builder(ConventionalEnchantmentTags.WEAPON_DAMAGE_ENHANCEMENTS)
            .addOptionalTag(CPCEnchantmentTags.ADDITIONAL_DAMAGE);

        // Exclusive Sets
        this.builder(EnchantmentTags.DAMAGE_EXCLUSIVE)
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
