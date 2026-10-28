package com.provismet.CombatPlusCore.event.handler;

import com.provismet.CombatPlusCore.utility.CPCCallbackUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public interface CombatEvents {
    static void register () {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(CombatEvents::applyPostKill);
    }

    private static void applyPostKill (ServerLevel level, Entity entity, LivingEntity killedEntity, DamageSource damageSource) {
        if (entity instanceof LivingEntity user) {
            CPCCallbackUtil.postKill(level, user.getMainHandItem(), EquipmentSlot.MAINHAND, user, killedEntity);
        }
    }
}
