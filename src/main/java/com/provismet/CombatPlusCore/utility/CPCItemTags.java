package com.provismet.CombatPlusCore.utility;

import com.provismet.CombatPlusCore.CPCMain;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class CPCItemTags {
    // Weapon Types
    public static final TagKey<Item> DUAL_WEAPON = createItemTag("dual_weapon");
    public static final TagKey<Item> MELEE_WEAPON = createItemTag("melee_weapon");

    // Weapon Effects
    public static final TagKey<Item> SHIELD_BREAKER = createItemTag("breaks_shields");

    // Enchantment Compatibility
    public static final TagKey<Item> ASPECT_ENCHANTABLE = createItemTag("enchantable/aspect");

    private static TagKey<Item> createItemTag (String path) {
        return TagKey.of(RegistryKeys.ITEM, CPCMain.identifier(path));
    }
}
