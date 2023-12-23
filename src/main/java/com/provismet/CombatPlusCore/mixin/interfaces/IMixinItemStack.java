package com.provismet.CombatPlusCore.mixin.interfaces;

import net.minecraft.entity.LivingEntity;

public interface IMixinItemStack {
    public void CPC_postChargedHit (LivingEntity user, LivingEntity target);

    public void CPC_postCriticalHit (LivingEntity user, LivingEntity target);

    public void CPC_postKill (LivingEntity user);
}
