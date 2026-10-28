package com.provismet.CombatPlusCore.api.enchantment.effect.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.api.enchantment.effect.CPCEnchantmentEntityEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

/**
 * Applies an {@link EnchantmentEntityEffect} to the attacker.
 * @see ApplyToBothEntityEffect
 * @see ApplyToTargetEntityEffect
 * @param effect The effect to apply.
 */
public record ApplyToUserEntityEffect (EnchantmentEntityEffect effect) implements CPCEnchantmentEntityEffect {
    public static final MapCodec<ApplyToUserEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            EnchantmentEntityEffect.CODEC.fieldOf("effect").forGetter(ApplyToUserEntityEffect::effect)
        ).apply(instance, ApplyToUserEntityEffect::new)
    );

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity attacker, Entity target) {
        this.effect.apply(world, level, context, attacker, attacker.position());
    }

    @Override
    public MapCodec<? extends CPCEnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
