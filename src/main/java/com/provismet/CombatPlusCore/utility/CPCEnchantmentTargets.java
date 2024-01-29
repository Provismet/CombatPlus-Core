package com.provismet.CombatPlusCore.utility;

import com.chocohead.mm.api.ClassTinkerers;

import net.minecraft.enchantment.EnchantmentTarget;

/**
* <p>Due to weirdness involving FabricASM. These targets will crash in a dev environment.
* This is an issue also encountered by FabricShieldLib, no fix has currently been found.
*
* <p>I recommend swapping these out for EnchantmentTarget.WEAPON when in a dev environment.
* It should be safe to build against these and use them in a live environment.
*/
public class CPCEnchantmentTargets {
    public static final EnchantmentTarget MELEE_WEAPON = ClassTinkerers.getEnum(EnchantmentTarget.class, "COMBATPLUS$MELEE");
    public static final EnchantmentTarget DUAL_WEAPON = ClassTinkerers.getEnum(EnchantmentTarget.class, "COMBATPLUS$DUAL");

    // Does nothing, just forces this class into memory so errors can be caught immediately.
    public static void init () {

    }
}
