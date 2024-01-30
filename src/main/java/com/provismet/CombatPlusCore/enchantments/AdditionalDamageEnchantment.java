package com.provismet.CombatPlusCore.enchantments;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;
import com.provismet.CombatPlusCore.utility.WeaponTypes;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Damaging enchantments intended as alternatives to the typical {@link DamageEnchantment} type.
 */
public abstract class AdditionalDamageEnchantment extends Enchantment implements CPCEnchantment {
    protected AdditionalDamageEnchantment (Rarity weight, EnchantmentTarget target) {
        super(weight, target, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    protected AdditionalDamageEnchantment (Rarity weight) {
        this(weight, EnchantmentTarget.WEAPON);
    }

    @Override
    public boolean isAcceptableItem (ItemStack stack) {
        if (this.target == EnchantmentTarget.WEAPON && WeaponTypes.isMeleeWeapon(stack)) return true;
        return super.isAcceptableItem(stack);
    }

    @Override
    protected boolean canAccept (Enchantment other) {
        return super.canAccept(other) &&
            !(other instanceof AdditionalDamageEnchantment) &&
            !(other instanceof DamageEnchantment) &&
            !(other instanceof OffHandEnchantment);
    }
}
