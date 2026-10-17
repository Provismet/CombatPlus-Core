package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.registries.SingleEntityLootConditionTypes;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

/**
 * Executes a registered predicate against a single entity.
 *
 * @param function The identifier of the predicate.
 */
public record SingleEntityLambdaCondition (Identifier function) implements SingleEntityCondition {
    public static final MapCodec<SingleEntityLambdaCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Identifier.CODEC.fieldOf("function").forGetter(SingleEntityLambdaCondition::function)).apply(instance, SingleEntityLambdaCondition::new));

    @Override
    public LootItemConditionType getType () {
        return SingleEntityLootConditionTypes.LAMBDA;
    }

    @Override
    public boolean test (LootContext lootContext) {
        Predicate<Entity> predicate = CPCRegistries.SINGLE_ENTITY_LAMBDA_CONDITION.getValue((this.function));
        if (predicate == null) {
            CPCMain.LOGGER.warn("Enchantment attempted to execute unregistered lambda function: {}", this.function.toString());
            return false;
        }

        Entity entity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);
        return predicate.test(entity);
    }

    public static SingleEntityCondition.Builder builder (Identifier function) {
        return () -> new SingleEntityLambdaCondition(function);
    }
}
