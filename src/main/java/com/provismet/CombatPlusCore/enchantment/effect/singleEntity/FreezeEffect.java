package com.provismet.CombatPlusCore.enchantment.effect.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

/**
 * Applies frozen ticks to the target.
 * @param duration The number of seconds to apply frozen ticks for. The entity gains {@code duration * 20} ticks.
 */
public record FreezeEffect (LevelBasedValue duration) implements EnchantmentEntityEffect {
    public static final MapCodec<FreezeEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            LevelBasedValue.CODEC.fieldOf("duration").forGetter(effect -> effect.duration)
        ).apply(instance, FreezeEffect::new)
    );

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity entity, Vec3 pos) {
        int ticks = Math.min(entity.getTicksFrozen() + (int)(this.duration.calculate(level) * 20), entity.getTicksRequiredToFreeze() + 100);
        entity.setTicksFrozen(ticks);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
