package com.provismet.CombatPlusCore.enchantments;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

/**
 * Damaging enchantments intended as alternatives to the typical {@link DamageEnchantment} type.
 */
public abstract class AdditionalDamageEnchantment extends Enchantment implements CPCEnchantment {
    protected AdditionalDamageEnchantment (Rarity weight, EnchantmentTarget target) {
        super(weight, target, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    protected boolean canAccept (Enchantment other) {
        return super.canAccept(other) &&
            !(other instanceof AdditionalDamageEnchantment) &&
            !(other instanceof DamageEnchantment) &&
            !(other instanceof OffHandEnchantment);
    }
}
