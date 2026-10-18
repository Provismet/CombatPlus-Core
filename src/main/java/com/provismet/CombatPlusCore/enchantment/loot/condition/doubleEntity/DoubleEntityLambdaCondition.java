package com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.DoubleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContextParameters;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.function.Predicate;

/**
 * Executes a registered predicate against the pair of entities.
 *
 * @param function The identifier of the predicate.
 */
public record DoubleEntityLambdaCondition (Identifier function) implements DoubleEntityCondition {
    public static final MapCodec<DoubleEntityLambdaCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Identifier.CODEC.fieldOf("function").forGetter(DoubleEntityLambdaCondition::function)).apply(instance, DoubleEntityLambdaCondition::new));

    @Override
    public boolean test (LootContext lootContext) {
        Predicate<Tuple<Entity,Entity>> predicate = CPCRegistries.DOUBLE_ENTITY_LAMBDA_CONDITION.getValue(function);
        if (predicate == null) return false;

        Tuple<Entity,Entity> pair = new Tuple<>(lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY), lootContext.getOptionalParameter(CPCLootContextParameters.TARGET_ENTITY));
        return predicate.test(pair);
    }

    public static DoubleEntityCondition.Builder builder (Identifier function) {
        return () -> new DoubleEntityLambdaCondition(function);
    }

    @Override
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
