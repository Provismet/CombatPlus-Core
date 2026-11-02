package com.provismet.combatplus.utility.item;

import com.provismet.combatplus.api.item.interfaces.DualWeapon;
import com.provismet.combatplus.api.item.interfaces.MeleeWeapon;

import com.provismet.combatplus.api.item.component.MeleeWeaponComponent;
import com.provismet.combatplus.registries.CombatPlusDataComponentTypes;
import com.provismet.combatplus.api.tag.CombatPlusItemTags;
import net.minecraft.world.item.ItemStack;

public interface WeaponTypes {
    static boolean isMeleeWeapon (ItemStack itemStack) {
        return itemStack.getItem() instanceof MeleeWeapon || itemStack.is(CombatPlusItemTags.MELEE_WEAPON) || itemStack.get(CombatPlusDataComponentTypes.MELEE_WEAPON) != null;
    }

    static boolean isDualWeapon (ItemStack itemStack) {
        return itemStack.getItem() instanceof DualWeapon || itemStack.is(CombatPlusItemTags.DUAL_WEAPON) || itemStack.getOrDefault(CombatPlusDataComponentTypes.MELEE_WEAPON, MeleeWeaponComponent.DEFAULT).isDual();
    }
}
