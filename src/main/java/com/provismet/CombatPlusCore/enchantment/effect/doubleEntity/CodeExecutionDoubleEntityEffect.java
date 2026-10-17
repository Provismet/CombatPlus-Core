package com.provismet.CombatPlusCore.enchantment.effect.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.CodeExecutionSingleEntityEffect;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.phys.Vec3;

/**
 * Executes a registered function against the pair of entities.
 * @see CodeExecutionSingleEntityEffect
 * @param function The identifier of the function.
 */
public record CodeExecutionDoubleEntityEffect (Identifier function) implements CPCEnchantmentEntityEffect {
    public static final MapCodec<CodeExecutionDoubleEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Identifier.CODEC.fieldOf("function").forGetter(CodeExecutionDoubleEntityEffect::function)
        ).apply(instance, CodeExecutionDoubleEntityEffect::new)
    );

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity attacker, Entity target) {
        Lambda lambda = CPCRegistries.DOUBLE_ENTITY_LAMBDA.getValue(this.function);
        if (lambda == null) {
            CPCMain.LOGGER.warn("Enchantment attempted to execute unregistered lambda function: {}", this.function.toString());
            return;
        }
        lambda.execute(world, level, context, attacker, target, attacker.position());
    }

    @Override
    public MapCodec<? extends CPCEnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

    @FunctionalInterface
    public interface Lambda {
        void execute (ServerLevel world, int level, EnchantedItemInUse context, Entity user, Entity target, Vec3 pos);
    }
}
