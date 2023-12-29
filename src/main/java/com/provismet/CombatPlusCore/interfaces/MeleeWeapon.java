package com.provismet.CombatPlusCore.interfaces;

import com.provismet.CombatPlusCore.enchantments.WeaponUtilityEnchantment;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

/**
 * Interface for items intended as melee weapons. All weapons must implement a damage value.
 * Callbacks are optional, but follow a specific order of execution.
 * <p> Item callbacks of a certain type also occur directly before enchantment callbacks of the same type.
 * 
 * <p> Order of callbacks: charged hit -> kill -> critical hit
 * 
 * <p> Items of this type are applicable for {@link WeaponUtilityEnchantment} enchantments.
 * 
 */
public interface MeleeWeapon {
    public float getWeaponDamage ();

    /**
     * A callback for when an entity performs a fully charged attack with this weapon.
     * 
     * <p> All attacks from a mob are considered charged attacks.
     * 
     * @param user The wielder of the weapon.
     * @param target The entity that was struck.
     */
    public default void postChargedHit (ItemStack itemStack, LivingEntity user, LivingEntity target) {

    }

    /**
     * A callback for when an entity performs a critical hit with this weapon. This occurs only if critical particles would have spawned.
     * 
     * <p> This callback only applies to players. Mobs cannot critical hit.
     * 
     * @param user The wielder of the weapon.
     * @param target The entity that was struck.
     */
    public default void postCriticalHit (ItemStack itemStack, LivingEntity user, LivingEntity target) {

    }

    /**
     * A callback for when an entity uses this item to kill another.
     * 
     * @param user The wielder of the weapon.
     * @param target The entity that was struck.
     */
    public default void postKill (ItemStack itemStack, LivingEntity user, LivingEntity target) {
        
    }
}
