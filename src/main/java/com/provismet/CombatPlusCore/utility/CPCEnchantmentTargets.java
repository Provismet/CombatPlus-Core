package com.provismet.CombatPlusCore.utility;

import com.chocohead.mm.api.ClassTinkerers;

import net.minecraft.enchantment.EnchantmentTarget;

/**
 * <p> Due to weirdness involving FabricASM. These targets will crash in a dev environment.
 * This issue is also encountered by FabricShieldLib and no fix has currently been found.
 *
 * <p> If you plan to use these anywhere if your mod, I recommend swapping them out for
 * EnchantmentTarget.WEAPON when in a dev environment. It should be safe to build against
 * these and use them in a live environment.
 */
public class CPCEnchantmentTargets {
    public static final EnchantmentTarget MELEE_WEAPON = ClassTinkerers.getEnum(EnchantmentTarget.class, "COMBATPLUS$MELEE");
    public static final EnchantmentTarget DUAL_WEAPON = ClassTinkerers.getEnum(EnchantmentTarget.class, "COMBATPLUS$DUAL");

    // Does nothing, just forces this class into memory so errors can be caught immediately.
    public static void init () {

    }
}
