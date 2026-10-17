package com.provismet.CombatPlusCore.enchantment.effect.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

/**
 * Heals the entity. This cannot take the entity over its maximum health.
 * @param value The amount of health to heal.
 */
public record HealEffect (LevelBasedValue value) implements EnchantmentEntityEffect {
    public static final MapCodec<HealEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            LevelBasedValue.CODEC.fieldOf("value").forGetter(HealEffect::value)
        ).apply(instance, HealEffect::new)
    );

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity entity, Vec3 pos) {
        if (entity instanceof LivingEntity living) {
            living.heal(this.value.calculate(level));
            if (living.getHealth() > living.getMaxHealth()) living.setHealth(living.getMaxHealth());
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec () {
        return CODEC;
    }
}
