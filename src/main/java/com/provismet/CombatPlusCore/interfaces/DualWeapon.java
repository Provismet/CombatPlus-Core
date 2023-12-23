package com.provismet.CombatPlusCore.interfaces;

import com.provismet.lilylib.util.MoreMath;

import net.minecraft.util.math.MathHelper;

public interface DualWeapon extends MeleeWeapon {
    public default double getOffhandDamage () {
        return MathHelper.absMax(0.5, MoreMath.roundDownToMultiple(this.getWeaponDamage() / 3.5, 0.5));
    }
}
