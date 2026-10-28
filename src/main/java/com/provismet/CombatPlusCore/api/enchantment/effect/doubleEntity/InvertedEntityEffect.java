package com.provismet.CombatPlusCore.api.enchantment.effect.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.api.enchantment.effect.CombatPlusEnchantmentEntityEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;

/**
 * Flips the attacker and target parameters, and executes a {@link CombatPlusEnchantmentEntityEffect} with the new parameters.
 * @param effect The effect to apply.
 */
public record InvertedEntityEffect (
    CombatPlusEnchantmentEntityEffect effect) implements CombatPlusEnchantmentEntityEffect {
    public static final MapCodec<InvertedEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            CombatPlusEnchantmentEntityEffect.CODEC.fieldOf("effect").forGetter(InvertedEntityEffect::effect)
        ).apply(instance, InvertedEntityEffect::new)
    );

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity attacker, Entity target) {
        this.effect.apply(world, level, context, target, attacker);
    }

    @Override
    public MapCodec<? extends CombatPlusEnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
