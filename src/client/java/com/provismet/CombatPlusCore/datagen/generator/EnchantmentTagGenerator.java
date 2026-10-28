package com.provismet.CombatPlusCore.datagen.generator;

import com.provismet.CombatPlusCore.api.tag.CombatPlusEnchantmentTags;
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
        this.builder(CombatPlusEnchantmentTags.ADDITIONAL_DAMAGE);
        this.builder(CombatPlusEnchantmentTags.ASPECT).add(Enchantments.FIRE_ASPECT);
        this.builder(CombatPlusEnchantmentTags.WEAPON_UTILITY).add(Enchantments.SWEEPING_EDGE).add(Enchantments.WIND_BURST);
        this.builder(CombatPlusEnchantmentTags.OFFHAND);

        this.builder(ConventionalEnchantmentTags.WEAPON_DAMAGE_ENHANCEMENTS)
            .addOptionalTag(CombatPlusEnchantmentTags.ADDITIONAL_DAMAGE);

        // Exclusive Sets
        this.builder(EnchantmentTags.DAMAGE_EXCLUSIVE)
            .addOptionalTag(CombatPlusEnchantmentTags.ADDITIONAL_DAMAGE)
            .addOptionalTag(CombatPlusEnchantmentTags.OFFHAND);

        this.builder(CombatPlusEnchantmentTags.ADDITION_DAMAGE_EXCLUSIVE)
            .addOptionalTag(CombatPlusEnchantmentTags.ADDITIONAL_DAMAGE)
            .addOptionalTag(CombatPlusEnchantmentTags.OFFHAND);

        this.builder(CombatPlusEnchantmentTags.ASPECT_EXCLUSIVE)
            .addOptionalTag(CombatPlusEnchantmentTags.ASPECT_OFFHAND_EXCLUSIVE)
            .addOptionalTag(CombatPlusEnchantmentTags.OFFHAND);

        this.builder(CombatPlusEnchantmentTags.ASPECT_OFFHAND_EXCLUSIVE)
            .addOptionalTag(CombatPlusEnchantmentTags.ASPECT);

        this.builder(CombatPlusEnchantmentTags.OFFHAND_EXCLUSIVE)
            .addOptionalTag(CombatPlusEnchantmentTags.ADDITIONAL_DAMAGE)
            .addOptionalTag(ConventionalEnchantmentTags.WEAPON_DAMAGE_ENHANCEMENTS);

        this.builder(CombatPlusEnchantmentTags.WEAPON_UTILITY_EXCLUSIVE)
            .addOptionalTag(CombatPlusEnchantmentTags.OFFHAND)
            .addOptionalTag(CombatPlusEnchantmentTags.WEAPON_UTILITY_OFFHAND_EXCLUSIVE);

        this.builder(CombatPlusEnchantmentTags.WEAPON_UTILITY_OFFHAND_EXCLUSIVE)
            .addOptionalTag(CombatPlusEnchantmentTags.WEAPON_UTILITY);

        this.builder(CombatPlusEnchantmentTags.ALL)
            .addOptionalTag(EnchantmentTags.TREASURE)
            .addOptionalTag(EnchantmentTags.NON_TREASURE);
    }
}
