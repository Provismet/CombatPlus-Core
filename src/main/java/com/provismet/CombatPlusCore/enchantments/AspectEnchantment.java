package com.provismet.CombatPlusCore.enchantments;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;

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
    
    @Override
    public boolean canAccept (Enchantment other) {
        return super.canAccept(other) &&
            !(other instanceof FireAspectEnchantment) &&
            !(other instanceof OffHandEnchantment);
    }
}
