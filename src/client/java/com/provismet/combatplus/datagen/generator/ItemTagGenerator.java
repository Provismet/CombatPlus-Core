package com.provismet.combatplus.datagen.generator;

import com.provismet.combatplus.api.tag.CombatPlusItemTags;
import com.provismet.combatplus.debug.references.CombatPlusDebugItemIds;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagsProvider.ItemTagsProvider {
    public ItemTagGenerator (FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags (HolderLookup.Provider wrapperLookup) {
        this.builder(CombatPlusItemTags.DUAL_WEAPON)
            .addOptionalTag(ItemTags.SWORDS)
            .addOptional(CombatPlusDebugItemIds.DEBUG_SWORD);

        this.builder(CombatPlusItemTags.MELEE_WEAPON)
            .addOptionalTag(CombatPlusItemTags.DUAL_WEAPON)
            .addOptionalTag(ItemTags.AXES)
            .addOptionalTag(ItemTags.SPEARS)
            .add(ItemIds.MACE);

        this.builder(CombatPlusItemTags.ASPECT_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.ASPECT_PRIMARY_ENCHANTABLE);

        this.builder(CombatPlusItemTags.ASPECT_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.FIRE_ASPECT_ENCHANTABLE);

        this.builder(CombatPlusItemTags.DAMAGE_ENCHANTABLE)
            .addOptionalTag(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.DAMAGE_PRIMARY_ENCHANTABLE)
            .addOptional(CombatPlusDebugItemIds.DEBUG_SWORD);

        this.builder(CombatPlusItemTags.DAMAGE_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.MELEE_WEAPON_ENCHANTABLE)
            .addOptional(CombatPlusDebugItemIds.DEBUG_SWORD);

        this.builder(CombatPlusItemTags.WEAPON_UTILITY_ENCHANTABLE)
            .addOptionalTag(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.WEAPON_UTILITY_PRIMARY_ENCHANTABLE);

        this.builder(CombatPlusItemTags.WEAPON_UTILITY_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.SHARP_WEAPON_ENCHANTABLE);

        this.builder(CombatPlusItemTags.OFFHAND_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.OFFHAND_PRIMARY_ENCHANTABLE);

        this.builder(CombatPlusItemTags.OFFHAND_PRIMARY_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.DUAL_WEAPON);

        this.builder(CombatPlusItemTags.SHIELD_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.SHIELD_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ConventionalItemTags.SHIELD_TOOLS);

        this.builder(CombatPlusItemTags.SHIELD_PRIMARY_ENCHANTABLE)
            .add(ItemIds.SHIELD);

        this.builder(ConventionalItemTags.MELEE_WEAPON_TOOLS)
            .addOptionalTag(CombatPlusItemTags.MELEE_WEAPON);

        this.builder(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.MELEE_WEAPON);

        this.builder(ItemTags.SHARP_WEAPON_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.DAMAGE_ENCHANTABLE);

        this.builder(ConventionalItemTags.ENCHANTABLES)
            .addOptionalTag(CombatPlusItemTags.DAMAGE_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.WEAPON_UTILITY_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.ASPECT_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.OFFHAND_ENCHANTABLE)
            .addOptionalTag(CombatPlusItemTags.SHIELD_ENCHANTABLE);

        this.builder(ConventionalItemTags.SHIELD_TOOLS)
            .addOptional(CombatPlusDebugItemIds.DEBUG_SHIELD);
    }


}
