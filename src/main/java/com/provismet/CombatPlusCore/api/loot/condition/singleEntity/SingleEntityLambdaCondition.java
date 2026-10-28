package com.provismet.CombatPlusCore.api.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.api.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.utility.CombatPlusRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Executes a registered predicate against a single entity.
 *
 * @param function The identifier of the predicate.
 */
public record SingleEntityLambdaCondition (Identifier function) implements SingleEntityCondition {
    public static final MapCodec<SingleEntityLambdaCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Identifier.CODEC.fieldOf("function").forGetter(SingleEntityLambdaCondition::function)
    ).apply(instance, SingleEntityLambdaCondition::new));

    @Override
    public boolean test (LootContext lootContext) {
        Predicate<Entity> predicate = CombatPlusRegistries.SINGLE_ENTITY_LAMBDA_CONDITION.getValue((this.function));
        if (predicate == null) {
            CombatPlusCoreMain.LOGGER.warn("Enchantment attempted to execute unregistered lambda function: {}", this.function.toString());
            return false;
        }

        Entity entity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);
        return predicate.test(entity);
    }

    public static SingleEntityCondition.Builder builder (Identifier function) {
        return () -> new SingleEntityLambdaCondition(function);
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
