package com.provismet.CombatPlusCore.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.provismet.CombatPlusCore.utility.CPCCallbackUtil;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import com.provismet.CombatPlusCore.utility.CPCGameRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin (EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method="attack", at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/player/Player;getKnockback(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;)F", shift=At.Shift.AFTER))
    private void postApplyHitEffects (Entity entity, CallbackInfo info, @Local(ordinal=0) boolean charged, @Local(ordinal=2) boolean critical) {
        if (entity instanceof LivingEntity target && this.level() instanceof ServerLevel world) {
            if (charged) CPCCallbackUtil.postChargedHit(world, this.getMainHandItem(), EquipmentSlot.MAINHAND, this, target);
            if (critical) CPCCallbackUtil.postCriticalHit(world, this.getMainHandItem(), EquipmentSlot.MAINHAND, this, target);
        }
    }

    @ModifyVariable(method="attack", at=@At(value="STORE"), ordinal=3)
    private boolean stopSweeping (boolean original) {
        if (this.level() instanceof ServerLevel world) {
            if (world.getGameRules().get(CPCGameRules.SWEEPING_REQUIRES_ENCHANTMENT) && this.getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO) <= 0) return false;
        }
        return original;
    }

    @Inject(
        method = "doSweepAttack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;doPostAttackEffects(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;)V",
            shift = At.Shift.AFTER
        )
    )
    private void sweepingAppliesChargedHit (Entity target, float damage, DamageSource damageSource, float cooldownProgress, CallbackInfo ci, @Local LivingEntity sweepTarget, @Local ServerLevel world) {
        CPCEnchantmentHelper.postChargedHit(world, this, sweepTarget, EquipmentSlot.MAINHAND);
    }
}
