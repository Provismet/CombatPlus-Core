package com.provismet.CombatPlusCore.enchantments;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentTargets;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

/**
 * Enchantments that add utility effects to a weapon. It is intended, but not required, that these
 * enchantments do not damage the opponent directly.
 * 
 * <p> For example: if Sweeping Edge were not in vanilla, then it would be considered one of these.
 * 
 * <p> See {@link AdditionalDamageEnchantment} for enchantments that directly increase damage dealt
 * to an opponent.
 */
public abstract class WeaponUtilityEnchantment extends Enchantment implements CPCEnchantment {
    protected WeaponUtilityEnchantment (Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }

    protected WeaponUtilityEnchantment (Rarity weight, EquipmentSlot... slotTypes) {
        this(weight, CPCEnchantmentTargets.MELEE_WEAPON, slotTypes);
    }

    /**
     * Offhand Enchantments do not block Weapon Utility Enchantments. This is one-sided intentionally.
     * <p> To make a Weapon Utility Enchantment that works with Offhand Enchantments, simply override
     * this method to return true for Offhand Enchantments.
     */
    @Override
    protected boolean canAccept (Enchantment other) {
        return super.canAccept(other) &&
            !CPCEnchantmentHelper.isWeaponUtility(other) &&
            !CPCEnchantmentHelper.isOffhand(other);
    }
}
