package com.provismet.CombatPlusCore.interfaces;

import net.minecraft.entity.LivingEntity;

public interface CPCEnchantment {
    public default float getAttackDamage (int level, LivingEntity user, LivingEntity target) {
        return 0f;
    }

    public default void postChargedHit (int level, LivingEntity user, LivingEntity target) {

    }

    public default void postCriticalHit (int level, LivingEntity user, LivingEntity target) {
        
    }

    public default void postKill (int level, LivingEntity user) {
        
    }
}
