package com.provismet.CombatPlusCore.utility.item;

import com.provismet.CombatPlusCore.api.item.interfaces.DualWeapon;
import com.provismet.CombatPlusCore.api.item.interfaces.MeleeWeapon;

import com.provismet.CombatPlusCore.api.item.component.MeleeWeaponComponent;
import com.provismet.CombatPlusCore.registries.CombatPlusDataComponentTypes;
import com.provismet.CombatPlusCore.api.tag.CombatPlusItemTags;
import net.minecraft.world.item.ItemStack;

public class WeaponTypes {
    public static boolean isMeleeWeapon (ItemStack itemStack) {
        return itemStack.getItem() instanceof MeleeWeapon || itemStack.is(CombatPlusItemTags.MELEE_WEAPON) || itemStack.get(CombatPlusDataComponentTypes.MELEE_WEAPON) != null;
    }

    public static boolean isDualWeapon (ItemStack itemStack) {
        return itemStack.getItem() instanceof DualWeapon || itemStack.is(CombatPlusItemTags.DUAL_WEAPON) || itemStack.getOrDefault(CombatPlusDataComponentTypes.MELEE_WEAPON, MeleeWeaponComponent.DEFAULT).isDual();
    }
}
