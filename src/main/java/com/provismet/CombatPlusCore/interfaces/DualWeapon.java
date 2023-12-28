package com.provismet.CombatPlusCore.interfaces;

import com.provismet.CombatPlusCore.enchantments.OffHandEnchantment;
import com.provismet.lilylib.util.MoreMath;

import net.minecraft.util.math.MathHelper;

/**
 * Interface for melee weapons that can be held in either hand.
 * 
 * <p> Dual weapons are applicable for {@link OffHandEnchantment} by default.
 */
public interface DualWeapon extends MeleeWeapon {
    public default double getOffhandDamage () {
        return MathHelper.absMax(0.5, MoreMath.roundDownToMultiple(this.getWeaponDamage() / 3.5, 0.5));
    }
}
