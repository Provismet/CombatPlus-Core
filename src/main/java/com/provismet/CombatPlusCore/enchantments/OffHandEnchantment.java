package com.provismet.CombatPlusCore.enchantments;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;
import com.provismet.CombatPlusCore.interfaces.DualWeapon;
import com.provismet.CombatPlusCore.utility.CombatTags;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.enchantment.SweepingEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Enchantments that are applied to offhanded dual weapons.
 */
public abstract class OffHandEnchantment extends Enchantment implements CPCEnchantment {

    protected OffHandEnchantment (Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.OFFHAND});
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

    @Override
    public boolean isAcceptableItem (ItemStack itemStack) {
        return itemStack.getItem() instanceof DualWeapon || itemStack.isIn(CombatTags.DUAL_WEAPON);
    }
}
