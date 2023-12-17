package com.provismet.CombatPlusCore.interfaces;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public interface CPCEnchantment {
    public default float getAttackDamage (int level, LivingEntity user, LivingEntity target) {
        return 0f;
    }

    public default boolean shouldApplyDamage (int level, EquipmentSlot slot, LivingEntity user, @Nullable LivingEntity target) {
        return false;
    }

    public default boolean shouldApplyEffects (int level, EquipmentSlot slot, LivingEntity user, @Nullable LivingEntity target) {
        return false;
    }

    public default void postChargeHit (int level, LivingEntity user, LivingEntity target) {

    }

    public default void postCriticalHit (int level, LivingEntity user, LivingEntity target) {
        
    }
}
