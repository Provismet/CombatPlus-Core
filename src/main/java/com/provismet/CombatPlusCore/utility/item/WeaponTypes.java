package com.provismet.CombatPlusCore.utility.item;

import com.provismet.CombatPlusCore.interfaces.DualWeapon;
import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;

import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import net.minecraft.item.ItemStack;

public class WeaponTypes {
    public static boolean isMeleeWeapon (ItemStack itemStack) {
        return itemStack.getItem() instanceof MeleeWeapon || itemStack.isIn(CPCItemTags.MELEE_WEAPON);
    }

    public static boolean isDualWeapon (ItemStack itemStack) {
        return itemStack.getItem() instanceof DualWeapon || itemStack.isIn(CPCItemTags.DUAL_WEAPON);
    }
}
