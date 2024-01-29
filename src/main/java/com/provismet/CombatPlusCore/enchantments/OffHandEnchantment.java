package com.provismet.CombatPlusCore.enchantments;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentTargets;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.enchantment.SweepingEnchantment;
import net.minecraft.entity.EquipmentSlot;

/**
 * Enchantments that are applied to offhanded dual weapons.
 */
public abstract class OffHandEnchantment extends Enchantment implements CPCEnchantment {
    protected OffHandEnchantment (Rarity weight, EnchantmentTarget target) {
        super(weight, target, new EquipmentSlot[] {EquipmentSlot.OFFHAND});
    }

    protected OffHandEnchantment (Rarity weight) {
        this(weight, CPCEnchantmentTargets.DUAL_WEAPON);
    }
    
    @Override
    public boolean canAccept (Enchantment other) {
        return super.canAccept(other) &&
            !(other instanceof DamageEnchantment) &&
            !(other instanceof FireAspectEnchantment) &&
            !(other instanceof SweepingEnchantment) &&
            !(other instanceof LuckEnchantment) &&
            !(other instanceof AdditionalDamageEnchantment) &&
            !(other instanceof AspectEnchantment) &&
            !(other instanceof OffHandEnchantment);
    }
}
