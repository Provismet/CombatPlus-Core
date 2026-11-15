package com.provismet.combatplus.debug.references;

import com.provismet.combatplus.CombatPlusCoreMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public interface CombatPlusDebugItemIds {
    ResourceKey<Item> DEBUG_SWORD = create("debug_sword");
    ResourceKey<Item> DEBUG_SHIELD = create("debug_shield");

    private static ResourceKey<Item> create (String path) {
        return ResourceKey.create(Registries.ITEM, CombatPlusCoreMain.identifier(path));
    }
}
