package com.provismet.CombatPlusCore.enchantment.effect.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;

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
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity attacker, Entity target) {
        this.effect.apply(world, level, context, target, attacker);
    }

    @Override
    public MapCodec<? extends CPCEnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
