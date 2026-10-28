package com.provismet.CombatPlusCore.api.tag;

import com.provismet.CombatPlusCore.CombatPlusCoreMain;

import com.provismet.CombatPlusCore.utility.CombatPlusEnchantmentHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * <p> Using these tags directly is optional, but enchantments should be registered to them regardless for better inter-mod compatibility outside
 * of the Combat+ ecosystem. </p>
 * 
 * <p> See {@link CombatPlusEnchantmentHelper} for how these are used in practice. </p>
 */
public interface CombatPlusEnchantmentTags {
    // Enchantment Types
    /**
     * Combat+ damage enchantments, made separate from vanilla's damage enchantments.
     */
    TagKey<Enchantment> ADDITIONAL_DAMAGE = createTag("additional_damage");
    /**
     * Enchantments that apply a state to the user or the target.
     */
    TagKey<Enchantment> ASPECT = createTag("aspect");
    /**
     * Enchantments for offhand weapons.
     */
    TagKey<Enchantment> OFFHAND = createTag("offhand");
    /**
     * Enchantments that enhance or modify the utility of a weapon. (Such as Sweeping Edge.)
     */
    TagKey<Enchantment> WEAPON_UTILITY = createTag("weapon_utility");

    // Exclusive Sets
    TagKey<Enchantment> ADDITION_DAMAGE_EXCLUSIVE = createTag("exclusive_set/additional_damage");
    TagKey<Enchantment> ASPECT_EXCLUSIVE = createTag("exclusive_set/aspect");
    TagKey<Enchantment> OFFHAND_EXCLUSIVE = createTag("exclusive_set/offhand");
    TagKey<Enchantment> WEAPON_UTILITY_EXCLUSIVE = createTag("exclusive_set/weapon_utility");

    TagKey<Enchantment> ASPECT_OFFHAND_EXCLUSIVE = createTag("exclusive_set/aspect_offhand");
    TagKey<Enchantment> WEAPON_UTILITY_OFFHAND_EXCLUSIVE = createTag("exclusive_set/weapon_utility_offhand");

    /**
     * Every enchantment. This contains {@code minecraft:treasure} and {@code minecraft:non_treasure}.
     */
    TagKey<Enchantment> ALL = createTag("all_enchantments");

    private static TagKey<Enchantment> createTag(String path) {
        return TagKey.create(Registries.ENCHANTMENT, CombatPlusCoreMain.identifier(path));
    }
}
