package com.provismet.CombatPlusCore.enchantment.effect.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;

/**
 * Flips the attacker and target parameters, and executes a {@link CPCEnchantmentEntityEffect} with the new parameters.
 * @param effect The effect to apply.
 */
public record InvertedEntityEffect (CPCEnchantmentEntityEffect effect) implements CPCEnchantmentEntityEffect {
    public static final MapCodec<InvertedEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            CPCEnchantmentEntityEffect.CODEC.fieldOf("effect").forGetter(InvertedEntityEffect::effect)
        ).apply(instance, InvertedEntityEffect::new)
    );

    @Override
    public void apply (ServerWorld world, int level, EnchantmentEffectContext context, Entity attacker, Entity target) {
        this.effect.apply(world, level, context, target, attacker);
    }

    @Override
    public MapCodec<? extends CPCEnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
