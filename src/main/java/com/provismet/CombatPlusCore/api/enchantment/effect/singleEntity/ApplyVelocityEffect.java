package com.provismet.CombatPlusCore.api.enchantment.effect.singleEntity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

/**
 * Applies velocity to the entity. The velocity is NOT normalised.
 * @param x The x component.
 * @param y The y component.
 * @param z The z component.
 * @param strength The strength of the velocity vector. All values are multiplied by strength.
 */
public record ApplyVelocityEffect (double x, double y, double z, LevelBasedValue strength, boolean respectAttribute) implements EnchantmentEntityEffect {
    public static final MapCodec<ApplyVelocityEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codec.DOUBLE.fieldOf("x").forGetter(ApplyVelocityEffect::x),
            Codec.DOUBLE.fieldOf("y").forGetter(ApplyVelocityEffect::y),
            Codec.DOUBLE.fieldOf("z").forGetter(ApplyVelocityEffect::z),
            LevelBasedValue.CODEC.fieldOf("strength").forGetter(ApplyVelocityEffect::strength),
            Codec.BOOL.fieldOf("respect_knockback_resistance").forGetter(ApplyVelocityEffect::respectAttribute)
        ).apply(instance, ApplyVelocityEffect::new)
    );

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity entity, Vec3 pos) {
        Vec3 velocity = new Vec3(this.x, this.y, this.z);
        if (entity instanceof LivingEntity living) {
            double strengthValue = this.strength.calculate(level);
            if (this.respectAttribute) strengthValue *= 1 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            if (strengthValue <= 0) return;

            living.push(velocity.scale(strengthValue));
            living.needsSync = true;
        }
        else {
            entity.push(velocity.scale(this.strength.calculate(level)));
            entity.needsSync = true;
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec () {
        return CODEC;
    }
}
