package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.registries.SingleEntityLootConditionTypes;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Executes a registered predicate against a single entity.
 *
 * @param function The identifier of the predicate.
 */
public record SingleEntityLambdaCondition (Identifier function) implements SingleEntityCondition {
    public static final MapCodec<SingleEntityLambdaCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Identifier.CODEC.fieldOf("function").forGetter(SingleEntityLambdaCondition::function)).apply(instance, SingleEntityLambdaCondition::new));

    @Override
    public LootConditionType getType () {
        return SingleEntityLootConditionTypes.LAMBDA;
    }

    @Override
    public boolean test (LootContext lootContext) {
        Optional<Predicate<Entity>> predicate = CPCRegistries.SINGLE_ENTITY_CONDITION.getOrEmpty(this.function);
        Entity entity = lootContext.get(LootContextParameters.THIS_ENTITY);
        if (predicate.isEmpty()) return false;
        else return predicate.get().test(entity);
    }

    SingleEntityCondition.Builder builder (Identifier function) {
        return () -> new SingleEntityLambdaCondition(function);
    }
}
