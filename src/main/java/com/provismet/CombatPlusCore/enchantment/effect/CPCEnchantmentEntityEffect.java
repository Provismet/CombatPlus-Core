package com.provismet.CombatPlusCore.enchantment.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.registries.CPCEnchantmentDoubleEntityEffects;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;

import java.util.function.Function;

/**
 * Combat+ Enchantment Effect for effects that can look at both the attacker and the target simultaneously.
 *
 * @see CPCEnchantmentDoubleEntityEffects
 */
public interface CPCEnchantmentEntityEffect {
    public static final Codec<CPCEnchantmentEntityEffect> CODEC = CPCRegistries.ENCHANTMENT_DUAL_ENTITY_EFFECT_TYPE.getCodec().dispatch(CPCEnchantmentEntityEffect::getCodec, Function.identity());

    public void apply (ServerWorld world, int level, EnchantmentEffectContext context, Entity attacker, Entity target);

    public MapCodec<? extends CPCEnchantmentEntityEffect> getCodec();
}
