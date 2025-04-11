package com.provismet.CombatPlusCore.interfaces;

import com.provismet.CombatPlusCore.items.component.MeleeWeaponComponent;
import com.provismet.CombatPlusCore.registries.CPCDataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

/**
 * Interface for items intended as melee weapons. All weapons must implement a damage value.
 * Callbacks are optional, but follow a specific order of execution.
 * <p> Item callbacks of a certain type also occur directly before enchantment callbacks of the same type. </p>
 * 
 * <p> Order of callbacks: kill -> charged hit -> critical hit </p>
 * <p> All callbacks occur after damage is applied. </p>
 */
public interface MeleeWeapon {
    /**
     * Gets the bonus damage granted by a weapon. This is only used within Combat+ and
     * does not affect any vanilla gameplay mechanics.
     *
     * <p> It should be assumed that this method returns the value the weapon adds
     * to the generic attack attribute. </p>
     *
     * @param itemStack The itemstack containing this weapon.
     * @return The damage bonus from this weapon.
     */
    default float getWeaponDamage (ItemStack itemStack) {
        return itemStack.getOrDefault(CPCDataComponentTypes.MELEE_WEAPON, MeleeWeaponComponent.DEFAULT).weaponDamage();
    }

    /**
     * A callback for when an entity performs a fully charged attack with this weapon.
     * 
     * <p> All attacks from a mob are considered charged attacks.
     * 
     * @param user The wielder of the weapon.
     * @param target The entity that was struck.
     */
    default void postChargedHit (ItemStack itemStack, LivingEntity user, LivingEntity target) {

    }

    /**
     * A callback for when an entity performs a critical hit with this weapon. This occurs only if critical particles would have spawned.
     * 
     * <p> This callback only applies to players. Mobs cannot critical hit.
     * 
     * @param user The wielder of the weapon.
     * @param target The entity that was struck.
     */
    default void postCriticalHit (ItemStack itemStack, LivingEntity user, LivingEntity target) {

    }

    /**
     * A callback for when an entity uses this item to kill another.
     * 
     * @param user The wielder of the weapon.
     * @param target The entity that was struck.
     */
    default void postKill (ItemStack itemStack, LivingEntity user, LivingEntity target) {
        
    }
}
