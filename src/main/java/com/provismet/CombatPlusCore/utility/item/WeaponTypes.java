package com.provismet.CombatPlusCore.utility.item;

import com.provismet.CombatPlusCore.api.item.interfaces.DualWeapon;
import com.provismet.CombatPlusCore.api.item.interfaces.MeleeWeapon;

import com.provismet.CombatPlusCore.api.item.component.MeleeWeaponComponent;
import com.provismet.CombatPlusCore.registries.CPCDataComponentTypes;
import com.provismet.CombatPlusCore.api.tag.CPCItemTags;
import net.minecraft.world.item.ItemStack;

public class WeaponTypes {
    public static boolean isMeleeWeapon (ItemStack itemStack) {
        return itemStack.getItem() instanceof MeleeWeapon || itemStack.is(CPCItemTags.MELEE_WEAPON) || itemStack.get(CPCDataComponentTypes.MELEE_WEAPON) != null;
    }

    public static boolean isDualWeapon (ItemStack itemStack) {
        return itemStack.getItem() instanceof DualWeapon || itemStack.is(CPCItemTags.DUAL_WEAPON) || itemStack.getOrDefault(CPCDataComponentTypes.MELEE_WEAPON, MeleeWeaponComponent.DEFAULT).isDual();
    }
}
