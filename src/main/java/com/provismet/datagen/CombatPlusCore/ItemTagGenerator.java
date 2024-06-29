package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Items;
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
            .addOptionalTag(ItemTags.SWORDS)
            .addOptional(CPCMain.identifier("debugger"));

        getOrCreateTagBuilder(CPCItemTags.MELEE_WEAPON)
            .addOptionalTag(CPCItemTags.DUAL_WEAPON)
            .addOptionalTag(ItemTags.AXES)
            .add(Items.MACE);

        getOrCreateTagBuilder(CPCItemTags.SHIELD_BREAKER)
            .addOptionalTag(ItemTags.AXES)
            .addOptional(CPCMain.identifier("debugger"));

        getOrCreateTagBuilder(CPCItemTags.ASPECT_ENCHANTABLE)
            .addOptionalTag(ItemTags.FIRE_ASPECT_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.ASPECT_PRIMARY_ENCHANTABLE);

        getOrCreateTagBuilder(CPCItemTags.ASPECT_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.FIRE_ASPECT_ENCHANTABLE);

        getOrCreateTagBuilder(CPCItemTags.DAMAGE_ENCHANTABLE)
            .addOptionalTag(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE)
            .addOptional(CPCMain.identifier("debugger"));

        getOrCreateTagBuilder(CPCItemTags.DAMAGE_PRIMARY_ENCHANTABLE)
            .addOptionalTag(ItemTags.SWORD_ENCHANTABLE)
            .addOptional(CPCMain.identifier("debugger"));

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

        getOrCreateTagBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS)
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
