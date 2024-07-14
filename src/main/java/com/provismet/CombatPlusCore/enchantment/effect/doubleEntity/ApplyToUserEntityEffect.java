package com.provismet.CombatPlusCore.enchantment.effect.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;

/**
 * Applies an {@link EnchantmentEntityEffect} to the attacker.
 * @see ApplyToBothEntityEffect
 * @see ApplyToTargetEntityEffect
 * @param effect The effect to apply.
 */
public record ApplyToUserEntityEffect(EnchantmentEntityEffect effect) implements CPCEnchantmentEntityEffect {
    public static final MapCodec<ApplyToUserEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(EnchantmentEntityEffect.CODEC.fieldOf("effect").forGetter(ApplyToUserEntityEffect::effect)).apply(instance, ApplyToUserEntityEffect::new));

    @Override
    public void apply (ServerWorld world, int level, EnchantmentEffectContext context, Entity attacker, Entity target) {
        this.effect.apply(world, level, context, attacker, attacker.getPos());
    }

    @Override
    public MapCodec<? extends CPCEnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
