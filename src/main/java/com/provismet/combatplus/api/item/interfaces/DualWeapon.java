package com.provismet.combatplus.api.item.interfaces;

import com.provismet.combatplus.api.item.component.MeleeWeaponComponent;
import com.provismet.combatplus.registries.CombatPlusDataComponentTypes;
import net.minecraft.world.item.ItemStack;

/**
 * Interface for melee weapons that can be held in either hand.
 * 
 * <p> Dual weapons are applicable for Offhand enchantments.
 */
public interface DualWeapon extends MeleeWeapon {
    default float getOffhandDamage (ItemStack itemStack) {
        return itemStack.getOrDefault(CombatPlusDataComponentTypes.MELEE_WEAPON, MeleeWeaponComponent.DEFAULT).dualDamage();
    }
}
