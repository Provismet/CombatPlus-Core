package com.provismet.CombatPlusCore.enchantment.effect.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

/**
 * Applies an {@link EnchantmentEntityEffect} to the target.
 * @see ApplyToUserEntityEffect
 * @see ApplyToBothEntityEffect
 * @param effect The effect to apply.
 */
public record ApplyToTargetEntityEffect (EnchantmentEntityEffect effect) implements CPCEnchantmentEntityEffect {
    public static final MapCodec<ApplyToTargetEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            EnchantmentEntityEffect.CODEC.fieldOf("effect").forGetter(ApplyToTargetEntityEffect::effect)
        ).apply(instance, ApplyToTargetEntityEffect::new)
    );

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity attacker, Entity target) {
        this.effect.apply(world, level, context, target, target.position());
    }

    @Override
    public MapCodec<? extends CPCEnchantmentEntityEffect> getCodec () {
        return CODEC;
    }
}
