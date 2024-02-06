package com.provismet.CombatPlusCore.interfaces;

import com.provismet.CombatPlusCore.enchantments.OffHandEnchantment;
import com.provismet.lilylib.util.MoreMath;

/**
 * Interface for melee weapons that can be held in either hand.
 * 
 * <p> Dual weapons are applicable for {@link OffHandEnchantment} by default.
 */
public interface DualWeapon extends MeleeWeapon {
    public default float getOffhandDamage () {
        float defaultValue = MoreMath.roundDownToMultipleFloat(this.getWeaponDamage() / 3.5f, 0.5f);
        if (defaultValue > 0.5f) return defaultValue;
        return 0.5f;
    }
}
