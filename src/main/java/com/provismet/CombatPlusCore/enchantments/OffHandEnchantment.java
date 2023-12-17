package com.provismet.CombatPlusCore.enchantments;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.enchantment.SweepingEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public abstract class OffHandEnchantment extends Enchantment implements CPCEnchantment {

    protected OffHandEnchantment (Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, new EquipmentSlot[] {EquipmentSlot.OFFHAND});
    }
    
    @Override
    public boolean canAccept (Enchantment other) {
        return super.canAccept(other) &&
            !(other instanceof DamageEnchantment) &&
            !(other instanceof FireAspectEnchantment) &&
            !(other instanceof SweepingEnchantment) &&
            !(other instanceof LuckEnchantment);
    }

    @Override
    public boolean isAcceptableItem (ItemStack itemStack) {
        return ExtraEnchantmentTarget.DualWeapons.accepts(itemStack.getItem());
    }
}
