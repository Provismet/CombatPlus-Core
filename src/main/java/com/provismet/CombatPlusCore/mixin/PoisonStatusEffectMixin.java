package com.provismet.CombatPlusCore.mixin;

import com.provismet.CombatPlusCore.utility.CPCDamageTypes;
import com.provismet.CombatPlusCore.utility.CPCGameRules;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.PoisonStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PoisonStatusEffect.class)
public abstract class PoisonStatusEffectMixin extends StatusEffect {
    protected PoisonStatusEffectMixin (StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Inject(method = "applyUpdateEffect", at=@At("HEAD"))
    private void applyDamage (LivingEntity entity, int amplifier, CallbackInfoReturnable<Boolean> cir) {
        if (entity.getHealth() <= 1 && entity.getWorld().getGameRules().getBoolean(CPCGameRules.LETHAL_POISON)) {
            entity.damage(CPCDamageTypes.POISON.createDamageSource(entity.getDamageSources()), 1);
        }
    }
}
