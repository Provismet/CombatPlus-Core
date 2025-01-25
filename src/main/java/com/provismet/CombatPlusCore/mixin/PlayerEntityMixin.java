package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.interfaces.BlockingItem;
import com.provismet.CombatPlusCore.utility.CPCCallbackUtil;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
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
    @Shadow public abstract ItemCooldownManager getItemCooldownManager ();

    @Shadow public abstract void incrementStat (Stat<?> stat);

    protected PlayerEntityMixin (EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method="attack", at=@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerEntity;getKnockbackAgainst(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;)F", shift=At.Shift.AFTER))
    private void postApplyHitEffects (Entity entity, CallbackInfo info, @Local(ordinal=0) boolean charged, @Local(ordinal=2) boolean critical) {
        if (entity instanceof LivingEntity target && this.getWorld() instanceof ServerWorld world) {
            if (charged) CPCCallbackUtil.postChargedHit(world, this.getMainHandStack(), EquipmentSlot.MAINHAND, this, target);
            if (critical) CPCCallbackUtil.postCriticalHit(world, this.getMainHandStack(), EquipmentSlot.MAINHAND, this, target);
        }
    }

    @ModifyVariable(method="attack", at=@At(value="STORE"), ordinal=3)
    private boolean stopSweeping (boolean original) {
        if (this.getWorld() instanceof ServerWorld world) {
            if (world.getGameRules().getBoolean(CPCGameRules.SWEEPING_REQUIRES_ENCHANTMENT) && this.getAttributeValue(EntityAttributes.SWEEPING_DAMAGE_RATIO) <= 0) return false;
        }
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

    @Inject(method="disableShield", at=@At("HEAD"), cancellable=true)
    private void applyShieldCooldown (CallbackInfo info) {
        if (this.getActiveItem().getItem() instanceof BlockingItem blockingItem) {
            this.getItemCooldownManager().set(this.getActiveItem(), blockingItem.getMaxCooldown(this.getActiveItem()));
            this.clearActiveItem();
            this.getWorld().sendEntityStatus(this, EntityStatuses.BREAK_SHIELD);
            info.cancel();
        }
    }

    @Inject(method="damageShield", at=@At("HEAD"), cancellable=true)
    private void damageBlockingItem (float amount, CallbackInfo info) {
        if (this.activeItemStack.getItem() instanceof BlockingItem) {
            if (!this.getWorld().isClient) {
                this.incrementStat(Stats.USED.getOrCreateStat(this.activeItemStack.getItem()));
                super.damageShield(amount);
                info.cancel();
            }
        }
    }
}
