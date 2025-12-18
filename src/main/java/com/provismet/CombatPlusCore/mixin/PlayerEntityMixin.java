package com.provismet.CombatPlusCore.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.provismet.CombatPlusCore.utility.CPCCallbackUtil;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import com.provismet.CombatPlusCore.utility.CPCGameRules;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin (EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method="attack", at=@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerEntity;getAttackKnockbackAgainst(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;)F", shift=At.Shift.AFTER))
    private void postApplyHitEffects (Entity entity, CallbackInfo info, @Local(ordinal=0) boolean charged, @Local(ordinal=2) boolean critical) {
        if (entity instanceof LivingEntity target && this.getEntityWorld() instanceof ServerWorld world) {
            if (charged) CPCCallbackUtil.postChargedHit(world, this.getMainHandStack(), EquipmentSlot.MAINHAND, this, target);
            if (critical) CPCCallbackUtil.postCriticalHit(world, this.getMainHandStack(), EquipmentSlot.MAINHAND, this, target);
        }
    }

    @ModifyVariable(method="attack", at=@At(value="STORE"), ordinal=3)
    private boolean stopSweeping (boolean original) {
        if (this.getEntityWorld() instanceof ServerWorld world) {
            if (world.getGameRules().getValue(CPCGameRules.SWEEPING_REQUIRES_ENCHANTMENT) && this.getAttributeValue(EntityAttributes.SWEEPING_DAMAGE_RATIO) <= 0) return false;
        }
        return original;
    }

    @Inject(
        method = "doSweepingAttack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/EnchantmentHelper;onTargetDamaged(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;)V",
            shift = At.Shift.AFTER
        )
    )
    private void sweepingAppliesChargedHit (Entity target, float damage, DamageSource damageSource, float cooldownProgress, CallbackInfo ci, @Local LivingEntity sweepTarget, @Local ServerWorld world) {
        CPCEnchantmentHelper.postChargedHit(world, this, sweepTarget, EquipmentSlot.MAINHAND);
    }
}
