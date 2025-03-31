package com.provismet.CombatPlusCore.utility.tag;

import com.provismet.CombatPlusCore.CPCMain;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

/**
 * Item tags used within Combat+
 *
 * @see com.provismet.datagen.CombatPlusCore.ItemTagGenerator
 */
public class CPCItemTags {
    // Weapon Types
    public static final TagKey<Item> DUAL_WEAPON = createItemTag("dual_weapon");
    public static final TagKey<Item> MELEE_WEAPON = createItemTag("melee_weapon");

    // Enchantment Compatibility
    public static final TagKey<Item> ASPECT_ENCHANTABLE = createItemTag("enchantable/aspect");
    public static final TagKey<Item> ASPECT_PRIMARY_ENCHANTABLE = createItemTag("enchantable/aspect_primary");
    public static final TagKey<Item> DAMAGE_ENCHANTABLE = createItemTag("enchantable/damage");
    public static final TagKey<Item> DAMAGE_PRIMARY_ENCHANTABLE = createItemTag("enchantable/damage_primary");
    public static final TagKey<Item> WEAPON_UTILITY_ENCHANTABLE = createItemTag("enchantable/weapon_utility");
    public static final TagKey<Item> WEAPON_UTILITY_PRIMARY_ENCHANTABLE = createItemTag("enchantable/weapon_utility_primary");
    public static final TagKey<Item> OFFHAND_ENCHANTABLE = createItemTag("enchantable/offhand");
    public static final TagKey<Item> OFFHAND_PRIMARY_ENCHANTABLE = createItemTag("enchantable/offhand_primary");
    public static final TagKey<Item> SHIELD_ENCHANTABLE = createItemTag("enchantable/shield");
    public static final TagKey<Item> SHIELD_PRIMARY_ENCHANTABLE = createItemTag("enchantable/shield_primary");

    private static TagKey<Item> createItemTag (String path) {
        return TagKey.of(RegistryKeys.ITEM, CPCMain.identifier(path));
    }
}
