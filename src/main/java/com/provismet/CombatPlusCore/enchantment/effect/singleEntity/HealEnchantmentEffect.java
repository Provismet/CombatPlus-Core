package com.provismet.CombatPlusCore.enchantment.effect.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

/**
 * Heals the entity. This cannot take the entity over its maximum health.
 * @param value The amount of health to heal.
 */
public record HealEnchantmentEffect (EnchantmentLevelBasedValue value) implements EnchantmentEntityEffect {
    public static final MapCodec<HealEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(EnchantmentLevelBasedValue.CODEC.fieldOf("value").forGetter(HealEnchantmentEffect::value)).apply(instance, HealEnchantmentEffect::new));

    @Override
    public void apply (ServerWorld world, int level, EnchantmentEffectContext context, Entity entity, Vec3d pos) {
        if (entity instanceof LivingEntity living) {
            living.heal(this.value.getValue(level));
            if (living.getHealth() > living.getMaxHealth()) living.setHealth(living.getMaxHealth());
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec () {
        return CODEC;
    }
}
