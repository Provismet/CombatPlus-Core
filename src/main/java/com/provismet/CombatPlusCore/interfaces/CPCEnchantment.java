package com.provismet.CombatPlusCore.interfaces;

import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

/**
 * General interface for more enchantments that use the hooks provided by Combat+ Core.
 * 
 * <p> See {@link CPCEnchantmentHelper} for how these are used.
 * 
 * <p> Order of callbacks: kill -> charged hit -> critical hit
 * <p> All callbacks occur after damage is applied and directly after the weapon callback of the same type.
 */
public interface CPCEnchantment {
    /**
     * Should be used instead of the vanilla {@link Enchantment#getAttackDamage(int, net.minecraft.entity.EntityGroup)}.
     * 
     * <p> See {@link CPCEnchantmentHelper#getAttackDamage(EquipmentSlot, LivingEntity, LivingEntity)} for how this is applied.
     * 
     * @param level The level of the enchantment.
     * @param slot The equipment slot that the item stack occupies. All equipped items are checked.
     * @param user The wielder of the enchanted weapon.
     * @param target The entity that was struck.
     * @return The increased damage.
     */
    public default float getAttackDamage (int level, EquipmentSlot slot, LivingEntity user, LivingEntity target) {
        return 0f;
    }

    /**
     * Enchantment callback for when the user performs a charged attack on an entity with this weapon.
     * 
     * @param level The level of the enchantment.
     * @param user The wielder of the enchanted weapon.
     * @param target The entity that was struck.
     */
    public default void postChargedHit (int level, LivingEntity user, LivingEntity target) {

    }

    /**
     * Enchantment callback for when the user performs a critical attack on an entity with this weapon.
     * 
     * @param level The level of the enchantment.
     * @param user The wielder of the enchanted weapon.
     * @param target The entity that was struck.
     */
    public default void postCriticalHit (int level, LivingEntity user, LivingEntity target) {
        
    }

    /**
     * Enchantment callback for when the user kills another entity with this item in their mainhand.
     * 
     * @param level The level of the enchantment.
     * @param user The wielder of the enchanted weapon.
     * @param target The entity that was killed.
     */
    public default void postKill (int level, LivingEntity user, LivingEntity target) {
        
    }
}
