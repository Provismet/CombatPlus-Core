package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.utility.CombatPlusCallbackUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobEntityMixin extends LivingEntity {
    protected MobEntityMixin (EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }
    
    @Inject(method="doHurtTarget", at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/Mob;getKnockback(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;)F"))
    public void onHit (ServerLevel world, Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof LivingEntity living) {
            CombatPlusCallbackUtil.postChargedHit(world, this.getMainHandItem(), EquipmentSlot.MAINHAND, this, living);
        }
    }
}
