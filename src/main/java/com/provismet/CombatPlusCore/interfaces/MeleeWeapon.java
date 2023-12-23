package com.provismet.CombatPlusCore.interfaces;

import net.minecraft.entity.LivingEntity;

public interface MeleeWeapon {
    public float getWeaponDamage ();

    public default void postChargedHit (LivingEntity user, LivingEntity target) {

    }

    public default void postCriticalHit (LivingEntity user, LivingEntity target) {

    }

    public default void postKill (LivingEntity user) {
        
    }
}
