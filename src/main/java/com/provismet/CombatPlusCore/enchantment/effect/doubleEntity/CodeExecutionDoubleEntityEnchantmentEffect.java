package com.provismet.CombatPlusCore.enchantment.effect.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

/**
 * Executes a registered function against the pair of entities.
 * @see com.provismet.CombatPlusCore.enchantment.effect.singleEntity.CodeExecutionSingleEntityEnchantmentEffect
 * @param function The identifier of the function.
 */
public record CodeExecutionDoubleEntityEnchantmentEffect (Identifier function) implements CPCEnchantmentEntityEffect {
    public static final MapCodec<CodeExecutionDoubleEntityEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Identifier.CODEC.fieldOf("function").forGetter(CodeExecutionDoubleEntityEnchantmentEffect::function)).apply(instance, CodeExecutionDoubleEntityEnchantmentEffect::new));

    @Override
    public void apply (ServerWorld world, int level, EnchantmentEffectContext context, Entity attacker, Entity target) {
        Optional<Lambda> lambda = CPCRegistries.DOUBLE_ENTITY_LAMBDA.getOrEmpty(this.function);
        lambda.ifPresent(value -> value.execute(world, level, context, attacker, target, attacker.getPos()));
    }

    @Override
    public MapCodec<? extends CPCEnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

    @FunctionalInterface
    public interface Lambda {
        void execute (ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Entity target, Vec3d pos);
    }
}
