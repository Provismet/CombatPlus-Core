package com.provismet.CombatPlusCore.interfaces;

import com.provismet.lilylib.util.MoreMath;
import net.minecraft.item.ItemStack;

/**
 * Interface for melee weapons that can be held in either hand.
 * 
 * <p> Dual weapons are applicable for Offhand enchantments.
 */
public interface DualWeapon extends MeleeWeapon {
    public default float getOffhandDamage (ItemStack itemStack) {
        float defaultValue = MoreMath.roundDownToMultipleFloat(this.getWeaponDamage(itemStack) / 3.5f, 0.5f);
        if (defaultValue > 0.5f) return defaultValue;
        return 0.5f;
    }
}
