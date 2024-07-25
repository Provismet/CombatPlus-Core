package com.provismet.CombatPlusCore.mixin;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import com.provismet.CombatPlusCore.interfaces.mixin.IMixinItemStack;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import com.provismet.CombatPlusCore.utility.CPCGameRules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin (EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method="attack", at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", shift=At.Shift.AFTER))
    private void postApplyHitEffects (Entity entity, CallbackInfo info, @Local(ordinal=0) boolean charged, @Local(ordinal=2) boolean critical) {
        if (entity instanceof LivingEntity target && this.getWorld() instanceof ServerWorld world) {
            if (charged) {
                ((IMixinItemStack)(Object)this.getMainHandStack()).CPC_postChargedHit(this, target);
                CPCEnchantmentHelper.postChargedHit(world, this, target, EquipmentSlot.MAINHAND);
            }
            if (critical) {
                ((IMixinItemStack)(Object)this.getMainHandStack()).CPC_postCriticalHit(this, target);
                CPCEnchantmentHelper.postCriticalHit(world, this, target, EquipmentSlot.MAINHAND);
            }
        }
    }

    @ModifyVariable(method="attack", at=@At(value="STORE"), ordinal=3)
    private boolean stopSweeping (boolean original) {
        if (this.getWorld().getGameRules().getBoolean(CPCGameRules.SWEEPING_REQUIRES_ENCHANTMENT) && this.getAttributeValue(EntityAttributes.PLAYER_SWEEPING_DAMAGE_RATIO) <= 0) return false;
        return original;
    }

    @Inject(
        method = "attack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/EnchantmentHelper;onTargetDamaged(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;)V",
            shift = At.Shift.AFTER
        ),
        allow = 1,
        slice = @Slice(
            from = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/world/World;getNonSpectatingEntities(Ljava/lang/Class;Lnet/minecraft/util/math/Box;)Ljava/util/List;"
            ),
            to = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/entity/player/PlayerEntity;spawnSweepAttackParticles()V"
            )
        )
    )
    private void sweepingAppliesChargedHit (Entity primaryTarget, CallbackInfo ci, @Local LivingEntity sweepTarget, @Local ServerWorld world) {
        CPCEnchantmentHelper.postChargedHit(world, this, sweepTarget, EquipmentSlot.MAINHAND);
    }
}
