package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.utility.CPCItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public ItemTagGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(CPCItemTags.DUAL_WEAPON)
            .addOptionalTag(ItemTags.SWORDS);

        getOrCreateTagBuilder(CPCItemTags.MELEE_WEAPON)
            .addOptionalTag(CPCItemTags.DUAL_WEAPON)
            .addOptionalTag(ItemTags.AXES);

        getOrCreateTagBuilder(CPCItemTags.SHIELD_BREAKER)
            .addOptionalTag(ItemTags.AXES);

        getOrCreateTagBuilder(CPCItemTags.ASPECT_ENCHANTABLE)
            .addOptionalTag(ItemTags.FIRE_ASPECT_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.ASPECT_PRIMARY_ENCHANTABLE);

        getOrCreateTagBuilder(CPCItemTags.ASPECT_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.FIRE_ASPECT_ENCHANTABLE);

        getOrCreateTagBuilder(CPCItemTags.DAMAGE_ENCHANTABLE)
            .addOptionalTag(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE);

        getOrCreateTagBuilder(CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.SWORD_ENCHANTABLE);

        getOrCreateTagBuilder(CPCItemTags.WEAPON_UTILITY_ENCHANTABLE)
            .addOptionalTag(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.WEAPON_UTILITY_PRIMARY_ENCHANTABLE);

        getOrCreateTagBuilder(CPCItemTags.WEAPON_UTILITY_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.SWORD_ENCHANTABLE)
            .addOptionalTag(ItemTags.AXES);

        getOrCreateTagBuilder(CPCItemTags.OFFHAND_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.OFFHAND_PRIMARY_ENCHANTABLE);

        getOrCreateTagBuilder(CPCItemTags.OFFHAND_PRIMARY_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.DUAL_WEAPON);

        getOrCreateTagBuilder(ConventionalItemTags.MELEE_WEAPONS_TOOLS)
            .addOptionalTag(CPCItemTags.MELEE_WEAPON);

        getOrCreateTagBuilder(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.MELEE_WEAPON);

        getOrCreateTagBuilder(ConventionalItemTags.ENCHANTABLES)
            .addOptionalTag(CPCItemTags.DAMAGE_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.WEAPON_UTILITY_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.ASPECT_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.OFFHAND_ENCHANTABLE);
    }
}
