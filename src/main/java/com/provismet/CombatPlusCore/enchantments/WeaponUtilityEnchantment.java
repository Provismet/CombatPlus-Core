package com.provismet.CombatPlusCore.enchantments;

import com.provismet.CombatPlusCore.interfaces.CPCEnchantment;
import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;
import com.provismet.CombatPlusCore.utility.CombatTags;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.SweepingEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

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
    protected WeaponUtilityEnchantment (Rarity weight) {
        super(weight, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    protected boolean canAccept (Enchantment other) {
        return super.canAccept(other) &&
            !(other instanceof WeaponUtilityEnchantment) &&
            !(other instanceof SweepingEnchantment) &&
            !(other instanceof OffHandEnchantment);
    }

    @Override
    public boolean isAcceptableItem (ItemStack stack) {
        return super.isAcceptableItem(stack) || stack.getItem() instanceof MeleeWeapon || stack.isIn(CombatTags.MELEE_WEAPON);
    }
}
