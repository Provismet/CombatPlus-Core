package com.provismet.CombatPlusCore.enchantment.effect.singleEntity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

/**
 * Applies velocity to the entity. The velocity is NOT normalised.
 * @param x The x component.
 * @param y The y component.
 * @param z The z component.
 * @param strength The strength of the velocity vector. All values are multiplied by strength.
 */
public record ApplyVelocityEffect (double x, double y, double z, EnchantmentLevelBasedValue strength, boolean respectAttribute) implements EnchantmentEntityEffect {
    public static final MapCodec<ApplyVelocityEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codec.DOUBLE.fieldOf("x").forGetter(ApplyVelocityEffect::x),
            Codec.DOUBLE.fieldOf("y").forGetter(ApplyVelocityEffect::y),
            Codec.DOUBLE.fieldOf("z").forGetter(ApplyVelocityEffect::z),
            EnchantmentLevelBasedValue.CODEC.fieldOf("strength").forGetter(ApplyVelocityEffect::strength),
            Codec.BOOL.fieldOf("respect_knockback_resistance").forGetter(ApplyVelocityEffect::respectAttribute)
        ).apply(instance, ApplyVelocityEffect::new)
    );

    @Override
    public void apply (ServerWorld world, int level, EnchantmentEffectContext context, Entity entity, Vec3d pos) {
        Vec3d velocity = new Vec3d(this.x, this.y, this.z);
        if (entity instanceof LivingEntity living) {
            double strengthValue = this.strength.getValue(level);
            if (this.respectAttribute) strengthValue *= 1 - living.getAttributeValue(EntityAttributes.KNOCKBACK_RESISTANCE);
            if (strengthValue <= 0) return;

            living.addVelocity(velocity.multiply(strengthValue));
            living.velocityModified = true;
        }
        else {
            entity.addVelocity(velocity.multiply(this.strength.getValue(level)));
            entity.velocityModified = true;
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec () {
        return CODEC;
    }
}
