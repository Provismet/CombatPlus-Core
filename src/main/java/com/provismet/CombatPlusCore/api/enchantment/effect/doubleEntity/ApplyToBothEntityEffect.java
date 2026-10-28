package com.provismet.CombatPlusCore.api.enchantment.effect.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.api.enchantment.effect.CombatPlusEnchantmentEntityEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

/**
 * Applies an {@link EnchantmentEntityEffect} to both the attacker and the target.
 * @see ApplyToUserEntityEffect
 * @see ApplyToTargetEntityEffect
 * @param effect The effect to apply.
 */
public record ApplyToBothEntityEffect (EnchantmentEntityEffect effect) implements CombatPlusEnchantmentEntityEffect {
    public static final MapCodec<ApplyToBothEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            EnchantmentEntityEffect.CODEC.fieldOf("effect").forGetter(ApplyToBothEntityEffect::effect)
        ).apply(instance, ApplyToBothEntityEffect::new)
    );

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity attacker, Entity target) {
        this.effect.apply(world, level, context, attacker, attacker.position());
        this.effect.apply(world, level, context, target, attacker.position());
    }

    @Override
    public MapCodec<? extends CombatPlusEnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
