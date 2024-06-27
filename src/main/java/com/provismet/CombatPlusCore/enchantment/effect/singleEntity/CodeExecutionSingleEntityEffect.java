package com.provismet.CombatPlusCore.enchantment.effect.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

/**
 * Executes a registered lambda function against an entity.
 * @see com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEnchantmentEffect
 * @param function The id of the function.
 */
public record CodeExecutionSingleEntityEffect(Identifier function) implements EnchantmentEntityEffect {
    public static final MapCodec<CodeExecutionSingleEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Identifier.CODEC.fieldOf("function").forGetter(CodeExecutionSingleEntityEffect::function)).apply(instance, CodeExecutionSingleEntityEffect::new));

    @Override
    public void apply (ServerWorld world, int level, EnchantmentEffectContext context, Entity entity, Vec3d pos) {
        Optional<Lambda> lambda = CPCRegistries.SINGLE_ENTITY_LAMBDA.getOrEmpty(this.function);
        lambda.ifPresent(value -> value.execute(world, level, context, entity, pos));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec () {
        return CODEC;
    }

    @FunctionalInterface
    public interface Lambda {
        void execute (ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos);
    }
}
