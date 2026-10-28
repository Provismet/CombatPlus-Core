package com.provismet.CombatPlusCore.api.enchantment.effect.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.api.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.CombatPlusCore.utility.CombatPlusRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

/**
 * Executes a registered lambda function against an entity.
 * @see CodeExecutionDoubleEntityEffect
 * @param function The id of the function.
 */
public record CodeExecutionSingleEntityEffect (Identifier function) implements EnchantmentEntityEffect {
    public static final MapCodec<CodeExecutionSingleEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Identifier.CODEC.fieldOf("function").forGetter(CodeExecutionSingleEntityEffect::function)
        ).apply(instance, CodeExecutionSingleEntityEffect::new)
    );

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity entity, Vec3 pos) {
        Lambda lambda = CombatPlusRegistries.SINGLE_ENTITY_LAMBDA.getValue(this.function);
        if (lambda == null) {
            CombatPlusCoreMain.LOGGER.warn("Enchantment attempted to execute unregistered lambda function: {}", this.function.toString());
            return;
        }
        lambda.execute(world, level, context, entity, pos);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec () {
        return CODEC;
    }

    @FunctionalInterface
    public interface Lambda {
        void execute (ServerLevel world, int level, EnchantedItemInUse context, Entity user, Vec3 pos);
    }
}
