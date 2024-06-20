package com.provismet.CombatPlusCore.enchantment.effect.doubleEntity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

/**
 * Applies velocity, launching the target away from the attacker.
 * @param strength The strength of the knockback.
 * @param respectAttribute Whether or not this should respect knockback resistance.
 */
public record ApplyKnockbackEnchantmentEffect (EnchantmentLevelBasedValue strength, boolean respectAttribute) implements CPCEnchantmentEntityEffect {
    public static final MapCodec<ApplyKnockbackEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(EnchantmentLevelBasedValue.CODEC.fieldOf("strength").forGetter(ApplyKnockbackEnchantmentEffect::strength), Codec.BOOL.fieldOf("respect_knockback_resistance").forGetter(ApplyKnockbackEnchantmentEffect::respectAttribute)).apply(instance, ApplyKnockbackEnchantmentEffect::new));

    @Override
    public void apply (ServerWorld world, int level, EnchantmentEffectContext context, Entity attacker, Entity target) {
        double strengthValue = this.strength.getValue(level);

        if (this.respectAttribute && target instanceof LivingEntity living) {
            strengthValue *= 1 - living.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            if (strengthValue <= 0) return;
        }
        Vec3d velocity = new Vec3d(target.getX() - attacker.getX(), 0.0, target.getZ() - attacker.getZ()).normalize().multiply(strengthValue).add(0.0, 0.1, 0.0);
        target.addVelocity(velocity);
        target.velocityModified = true;
    }

    @Override
    public MapCodec<? extends CPCEnchantmentEntityEffect> getCodec () {
        return CODEC;
    }
}
