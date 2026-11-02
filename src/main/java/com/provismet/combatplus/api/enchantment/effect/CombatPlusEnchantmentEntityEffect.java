package com.provismet.combatplus.api.enchantment.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.provismet.combatplus.utility.CombatPlusRegistries;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;

/**
 * Combat+ Enchantment Effect for effects that can look at both the attacker and the target simultaneously.
 *
 * @see com.provismet.combatplus.registries.DoubleEntityEffects
 */
public interface CombatPlusEnchantmentEntityEffect {
    Codec<CombatPlusEnchantmentEntityEffect> CODEC = CombatPlusRegistries.ENCHANTMENT_DUAL_ENTITY_EFFECT_TYPE.byNameCodec().dispatch(CombatPlusEnchantmentEntityEffect::getCodec, Function.identity());

    void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity attacker, Entity target);
    MapCodec<? extends CombatPlusEnchantmentEntityEffect> getCodec ();
}
