package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.CPCMain;
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
        Predicate<Entity> predicate = CPCRegistries.SINGLE_ENTITY_LAMBDA_CONDITION.get((this.function));
        if (predicate == null) {
            CPCMain.LOGGER.warn("Enchantment attempted to execute unregistered lambda function: {}", this.function.toString());
            return false;
        }

        Entity entity = lootContext.get(LootContextParameters.THIS_ENTITY);
        return predicate.test(entity);
    }

    public static SingleEntityCondition.Builder builder (Identifier function) {
        return () -> new SingleEntityLambdaCondition(function);
    }
}
