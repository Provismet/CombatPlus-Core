package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.utility.CPCCallbackUtil;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
    protected MobEntityMixin (EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    
    @Inject(method="tryAttack", at=@At(value="INVOKE", target="Lnet/minecraft/entity/mob/MobEntity;getKnockbackAgainst(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;)F", shift=At.Shift.AFTER))
    public void onHit (Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof LivingEntity living && this.getWorld() instanceof ServerWorld world) {
            CPCCallbackUtil.postChargedHit(world, this.getMainHandStack(), EquipmentSlot.MAINHAND, this, living);
        }
    }
}
