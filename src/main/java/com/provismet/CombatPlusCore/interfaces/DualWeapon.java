package com.provismet.CombatPlusCore.interfaces;

import com.provismet.CombatPlusCore.items.component.MeleeWeaponComponent;
import com.provismet.CombatPlusCore.registries.CPCDataComponentTypes;
import net.minecraft.item.ItemStack;

/**
 * Interface for melee weapons that can be held in either hand.
 * 
 * <p> Dual weapons are applicable for Offhand enchantments.
 */
public interface DualWeapon extends MeleeWeapon {
    default float getOffhandDamage (ItemStack itemStack) {
        return itemStack.getOrDefault(CPCDataComponentTypes.MELEE_WEAPON, MeleeWeaponComponent.DEFAULT).dualDamage();
    }
}
