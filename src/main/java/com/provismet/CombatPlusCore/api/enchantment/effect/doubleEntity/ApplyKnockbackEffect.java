package com.provismet.CombatPlusCore.api.enchantment.effect.doubleEntity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.api.enchantment.effect.CombatPlusEnchantmentEntityEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.phys.Vec3;

/**
 * Applies velocity, launching the target away from the attacker.
 * @param strength The strength of the knockback.
 * @param respectAttribute Whether or not this should respect knockback resistance.
 */
public record ApplyKnockbackEffect (LevelBasedValue strength, boolean respectAttribute) implements CombatPlusEnchantmentEntityEffect {
    public static final MapCodec<ApplyKnockbackEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(LevelBasedValue.CODEC.fieldOf("strength").forGetter(ApplyKnockbackEffect::strength),
            Codec.BOOL.fieldOf("respect_knockback_resistance").forGetter(ApplyKnockbackEffect::respectAttribute)
        ).apply(instance, ApplyKnockbackEffect::new)
    );

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity attacker, Entity target) {
        double strengthValue = this.strength.calculate(level);

        if (this.respectAttribute && target instanceof LivingEntity living) {
            strengthValue *= 1 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            if (strengthValue <= 0) return;
        }
        Vec3 velocity = new Vec3(target.getX() - attacker.getX(), 0.0, target.getZ() - attacker.getZ()).normalize().scale(strengthValue).add(0.0, 0.1, 0.0);
        target.push(velocity);
        target.needsSync = true;
    }

    @Override
    public MapCodec<? extends CombatPlusEnchantmentEntityEffect> getCodec () {
        return CODEC;
    }
}
