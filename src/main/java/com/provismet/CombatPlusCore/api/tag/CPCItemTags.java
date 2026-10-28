package com.provismet.CombatPlusCore.api.tag;

import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

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

    /**
     * Can be enchanted with aspect enchantments in an anvil.
     */
    public static final TagKey<Item> ASPECT_ENCHANTABLE = createItemTag("enchantable/aspect");

    /**
     * Can be enchanted with aspect enchantments from random sources.
     */
    public static final TagKey<Item> ASPECT_PRIMARY_ENCHANTABLE = createItemTag("enchantable/aspect_primary");

    /**
     * Can be enchanted with damage boosting enchantments (such as sharpness/smite) in an anvil.
     */
    public static final TagKey<Item> DAMAGE_ENCHANTABLE = createItemTag("enchantable/damage");

    /**
     * Can be enchanted with damage boosting enchantments (such as sharpness/smite) from random sources.
     */
    public static final TagKey<Item> DAMAGE_PRIMARY_ENCHANTABLE = createItemTag("enchantable/damage_primary");

    /**
     * Can be enchanted with utility/functional enchantments in an anvil.
     */
    public static final TagKey<Item> WEAPON_UTILITY_ENCHANTABLE = createItemTag("enchantable/weapon_utility");

    /**
     * Can be enchanted with utility/functional enchantments from random sources.
     */
    public static final TagKey<Item> WEAPON_UTILITY_PRIMARY_ENCHANTABLE = createItemTag("enchantable/weapon_utility_primary");

    /**
     * Can be enchanted with offhand enchantments in an anvil.
     */
    public static final TagKey<Item> OFFHAND_ENCHANTABLE = createItemTag("enchantable/offhand");

    /**
     * Can be enchanted with offhand enchantments from random sources.
     */
    public static final TagKey<Item> OFFHAND_PRIMARY_ENCHANTABLE = createItemTag("enchantable/offhand_primary");

    /**
     * Can be enchanted with shield enchantments in an anvil.
     */
    public static final TagKey<Item> SHIELD_ENCHANTABLE = createItemTag("enchantable/shield");

    /**
     * Can be enchanted with shield enchantments from random sources.
     */
    public static final TagKey<Item> SHIELD_PRIMARY_ENCHANTABLE = createItemTag("enchantable/shield_primary");

    private static TagKey<Item> createItemTag (String path) {
        return TagKey.create(Registries.ITEM, CombatPlusCoreMain.identifier(path));
    }
}
