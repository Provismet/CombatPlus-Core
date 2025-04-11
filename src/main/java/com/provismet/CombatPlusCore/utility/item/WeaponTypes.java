package com.provismet.CombatPlusCore.utility.item;

import com.provismet.CombatPlusCore.interfaces.DualWeapon;
import com.provismet.CombatPlusCore.interfaces.MeleeWeapon;

import com.provismet.CombatPlusCore.items.component.MeleeWeaponComponent;
import com.provismet.CombatPlusCore.registries.CPCDataComponentTypes;
import com.provismet.CombatPlusCore.utility.tag.CPCItemTags;
import net.minecraft.item.ItemStack;

public class WeaponTypes {
    public static boolean isMeleeWeapon (ItemStack itemStack) {
        return itemStack.getItem() instanceof MeleeWeapon || itemStack.isIn(CPCItemTags.MELEE_WEAPON) || itemStack.get(CPCDataComponentTypes.MELEE_WEAPON) != null;
    }

    public static boolean isDualWeapon (ItemStack itemStack) {
        return itemStack.getItem() instanceof DualWeapon || itemStack.isIn(CPCItemTags.DUAL_WEAPON) || itemStack.getOrDefault(CPCDataComponentTypes.MELEE_WEAPON, MeleeWeaponComponent.DEFAULT).isDual();
    }
}
