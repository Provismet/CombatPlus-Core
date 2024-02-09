package com.provismet.CombatPlusCore.enchantments;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.entity.EquipmentSlot;

/**
 * Weapon enchantments that apply a status change.
 * 
 * <p> Such enchantments should be considered equivalent to {@link FireAspectEnchantment}.
 */
public abstract class AspectEnchantment extends Enchantment implements CPCEnchantment {
    protected AspectEnchantment (Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }
    
    /**
     * Offhand Enchantments do not block Aspect Enchantments. This is one-sided intentionally.
     * <p> To make an Aspect Enchantment that works with Offhand Enchantments, simply override
     * this method to return true for Offhand Enchantments.
     */
    @Override
    public boolean canAccept (Enchantment other) {
        return super.canAccept(other) &&
            !CPCEnchantmentHelper.isAspect(other) &&
            !CPCEnchantmentHelper.isOffhand(other);
    }
}
