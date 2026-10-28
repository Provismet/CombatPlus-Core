package com.provismet.CombatPlusCore.api.loot.condition.singleEntity;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.api.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.api.loot.context.CombatPlusLootContext;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Modifies a loot context to change the ATTACKING_ENTITY to THIS_ENTITY.
 *
 * <p> This should be used to apply a {@link SingleEntityCondition} to the attacking entity inside a vanilla component. </p>
 *
 * @param condition The {@link SingleEntityCondition} to apply.
 */
public record ApplyToAttackerCondition(LootItemCondition condition) implements SingleEntityCondition {
    public static final MapCodec<ApplyToAttackerCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(LootItemCondition.DIRECT_CODEC.fieldOf("applied_condition").forGetter(ApplyToAttackerCondition::condition)).apply(instance, ApplyToAttackerCondition::new));

    @Override
    @NotNull
    public Set<ContextKey<?>> getReferencedContextParams () {
        return ImmutableSet.of(
            LootContextParams.ENCHANTMENT_LEVEL,
            LootContextParams.ATTACKING_ENTITY
        );
    }

    @Override
    public boolean test (LootContext lootContext) {
        LootContext flippedContext = CombatPlusLootContext.createSingleEntity(
            lootContext.getLevel(),
            lootContext.getOptionalParameter(LootContextParams.ENCHANTMENT_LEVEL),
            lootContext.getOptionalParameter(LootContextParams.ATTACKING_ENTITY),
            null
        );
        return this.condition.test(flippedContext);
    }

    public static SingleEntityCondition.Builder builder (SingleEntityCondition condition) {
        return () -> new ApplyToAttackerCondition(condition);
    }

    public static SingleEntityCondition.Builder builder (SingleEntityCondition.Builder condition) {
        return () -> new ApplyToAttackerCondition(condition.build());
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
