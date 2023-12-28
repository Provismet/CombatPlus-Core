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
 * <p> All callbacks occurs directly after the item callback of the same type.
 * <p> Order of callbacks: charged hit -> kill -> critical hit
 */
public interface CPCEnchantment {
    /**
     * Should be used instead of the vanilla {@link Enchantment#getAttackDamage(int, net.minecraft.entity.EntityGroup)}.
     * 
     * <p> See {@link CPCEnchantmentHelper#getAttackDamage(EquipmentSlot, LivingEntity, LivingEntity)} for how this is applied.
     * 
     * @param level The level of the enchantment.
     * @param slot The equipment slot that the item stack occupies.
     * @param user The wielder of the enchanted weapon.
     * @param target The entity that was struck.
     * @return The increased damage.
     */
    public default float getAttackDamage (int level, EquipmentSlot slot, LivingEntity user, LivingEntity target) {
        return 0f;
    }

    /**
     * Enchantment callback for when the user performs a charged attack on an entity.
     * 
     * @param level The level of the enchantment.
     * @param slot The equipment slot that the item stack occupies.
     * @param user The wielder of the enchanted weapon.
     * @param target The entity that was struck.
     */
    public default void postChargedHit (int level, EquipmentSlot slot, LivingEntity user, LivingEntity target) {

    }

    /**
     * Enchantment callback for when the user performs a critical attack on an entity.
     * 
     * @param level The level of the enchantment.
     * @param slot The equipment slot that the item stack occupies.
     * @param user The wielder of the enchanted weapon.
     * @param target The entity that was struck.
     */
    public default void postCriticalHit (int level, EquipmentSlot slot, LivingEntity user, LivingEntity target) {
        
    }

    /**
     * Enchantment callback for when the user kills another entity.
     * 
     * @param level The level of the enchantment.
     * @param slot The equipment slot that the item stack occupies.
     * @param user The wielder of the enchanted weapon.
     * @param target The entity that was killed.
     */
    public default void postKill (int level, EquipmentSlot slot, LivingEntity user, LivingEntity target) {
        
    }
}
