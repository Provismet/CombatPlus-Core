package com.provismet.CombatPlusCore.interfaces;

import com.provismet.CombatPlusCore.enchantments.OffHandEnchantment;
import com.provismet.lilylib.util.MoreMath;
import net.minecraft.item.ItemStack;

/**
 * Interface for melee weapons that can be held in either hand.
 * 
 * <p> Dual weapons are applicable for {@link OffHandEnchantment} by default.
 */
public interface DualWeapon extends MeleeWeapon {
    public default float getOffhandDamage () {
        float defaultValue = MoreMath.roundDownToMultipleFloat(this.getWeaponDamage() / 3.5f, 0.5f);
        return Math.max(defaultValue, 0.5f);
    }

    public default float getOffhandDamage (ItemStack itemStack) {
        float defaultValue = MoreMath.roundDownToMultipleFloat(this.getWeaponDamage(itemStack) / 3.5f, 0.5f);
        return Math.max(defaultValue, 0.5f);
    }
}
