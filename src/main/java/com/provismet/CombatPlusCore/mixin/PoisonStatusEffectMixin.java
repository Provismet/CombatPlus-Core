package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.utility.CPCDamageTypes;
import com.provismet.CombatPlusCore.utility.CPCGameRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.PoisonMobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PoisonMobEffect.class)
public abstract class PoisonStatusEffectMixin extends MobEffect {
    protected PoisonStatusEffectMixin (MobEffectCategory category, int color) {
        super(category, color);
    }

    @Inject(method = "applyEffectTick", at=@At("HEAD"))
    private void applyDamage (ServerLevel world, LivingEntity entity, int amplifier, CallbackInfoReturnable<Boolean> cir) {
        if (entity.getHealth() <= 1 && world.getGameRules().get(CPCGameRules.LETHAL_POISON)) {
            entity.hurtServer(world, CPCDamageTypes.POISON.createDamageSource(entity.damageSources()), 1);
        }
    }
}
