package com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.DoubleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContextParameters;
import com.provismet.CombatPlusCore.registries.CPCDoubleEntityLootConditionTypes;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Executes a registered predicate against the pair of entities.
 *
 * @param function The identifier of the predicate.
 */
public record DoubleEntityLambdaCondition (Identifier function) implements DoubleEntityCondition {
    public static final MapCodec<DoubleEntityLambdaCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Identifier.CODEC.fieldOf("function").forGetter(DoubleEntityLambdaCondition::function)).apply(instance, DoubleEntityLambdaCondition::new));

    @Override
    public LootConditionType getType () {
        return CPCDoubleEntityLootConditionTypes.LAMBDA;
    }

    @Override
    public boolean test (LootContext lootContext) {
        Optional<Predicate<Pair<Entity,Entity>>> predicate = CPCRegistries.DOUBLE_ENTITY_CONDITION.getOrEmpty(function);
        Pair<Entity,Entity> pair = new Pair<>(lootContext.get(LootContextParameters.THIS_ENTITY), lootContext.get(CPCLootContextParameters.TARGET_ENTITY));
        if (predicate.isEmpty()) return false;
        else return predicate.get().test(pair);
    }

    public static DoubleEntityCondition.Builder builder (Identifier function) {
        return () -> new DoubleEntityLambdaCondition(function);
    }
}
