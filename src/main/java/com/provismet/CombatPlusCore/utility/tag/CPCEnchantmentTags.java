package com.provismet.CombatPlusCore.utility.tag;

import com.provismet.CombatPlusCore.CPCMain;

import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

/**
 * <p> Using these tags directly is optional, but enchantments should be registered to them regardless for better inter-mod compatibility outside
 * of the Combat+ ecosystem. </p>
 * 
 * <p> See {@link CPCEnchantmentHelper} for how these are used in practice. </p>
 */
public class CPCEnchantmentTags {
    // Enchantment Types
    public static final TagKey<Enchantment> ADDITIONAL_DAMAGE = createTag("additional_damage");
    public static final TagKey<Enchantment> ASPECT = createTag("aspect");
    public static final TagKey<Enchantment> OFFHAND = createTag("offhand");
    public static final TagKey<Enchantment> WEAPON_UTILITY = createTag("weapon_utility");

    // Exclusive Sets
    public static final TagKey<Enchantment> ADDITION_DAMAGE_EXCLUSIVE = createTag("exclusive_set/additional_damage");
    public static final TagKey<Enchantment> ASPECT_EXCLUSIVE = createTag("exclusive_set/aspect");
    public static final TagKey<Enchantment> OFFHAND_EXCLUSIVE = createTag("exclusive_set/offhand");
    public static final TagKey<Enchantment> WEAPON_UTILITY_EXCLUSIVE = createTag("exclusive_set/weapon_utility");

    public static final TagKey<Enchantment> ASPECT_OFFHAND_EXCLUSIVE = createTag("exclusive_set/aspect_offhand");
    public static final TagKey<Enchantment> WEAPON_UTILITY_OFFHAND_EXCLUSIVE = createTag("exclusive_set/weapon_utility_offhand");

    private static TagKey<Enchantment> createTag(String path) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, CPCMain.identifier(path));
    }
}
