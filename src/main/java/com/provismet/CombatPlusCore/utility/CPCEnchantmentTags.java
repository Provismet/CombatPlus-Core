package com.provismet.CombatPlusCore.utility;

import com.provismet.CombatPlusCore.CPCMain;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

/**
 * Using these tags directly is optional, but enchantments should be registered to them regardless for better inter-mod compatibility outside
 * of the Combat+ ecosystem.
 * 
 * <p> See {@link CPCEnchantmentHelper} for how these are used in practice.
 */
public class CPCEnchantmentTags {
    public static final TagKey<Enchantment> ADDITIONAL_DAMAGE = createItemTag("additional_damage");
    public static final TagKey<Enchantment> ASPECT = createItemTag("aspect");
    public static final TagKey<Enchantment> OFFHAND = createItemTag("offhand");
    public static final TagKey<Enchantment> WEAPON_UTILITY = createItemTag("weapon_utility");

    private static TagKey<Enchantment> createItemTag (String path) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, CPCMain.identifier(path));
    }
}
