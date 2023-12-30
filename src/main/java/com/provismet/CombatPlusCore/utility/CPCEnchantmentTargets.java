package com.provismet.CombatPlusCore.utility;

import com.chocohead.mm.api.ClassTinkerers;

import net.minecraft.enchantment.EnchantmentTarget;

public class CPCEnchantmentTargets {
    public static final EnchantmentTarget MELEE_WEAPON = ClassTinkerers.getEnum(EnchantmentTarget.class, "COMBATPLUS$MELEE");
    public static final EnchantmentTarget DUAL_WEAPON = ClassTinkerers.getEnum(EnchantmentTarget.class, "COMBATPLUS$DUAL");

    // Does nothing, just forces this class into memory so errors can be caught immediately.
    public static void init () {

    }
}
