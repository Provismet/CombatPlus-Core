package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContext;
import com.provismet.CombatPlusCore.registries.SingleEntityLootConditionTypes;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.context.ContextParameter;

import java.util.Set;

/**
 * Modifies a loot context to change the ATTACKING_ENTITY to THIS_ENTITY.
 *
 * <p> This should be used to apply a {@link SingleEntityCondition} to the attacking entity inside a vanilla component. </p>
 *
 * @param condition The {@link SingleEntityCondition} to apply.
 */
public record ApplyToAttackerCondition(LootCondition condition) implements SingleEntityCondition {
    public static final MapCodec<ApplyToAttackerCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(LootCondition.CODEC.fieldOf("applied_condition").forGetter(ApplyToAttackerCondition::condition)).apply(instance, ApplyToAttackerCondition::new));

    @Override
    public Set<ContextParameter<?>> getAllowedParameters () {
        return ImmutableSet.of(
            LootContextParameters.ENCHANTMENT_LEVEL,
            LootContextParameters.ATTACKING_ENTITY
        );
    }

    @Override
    public LootConditionType getType () {
        return SingleEntityLootConditionTypes.APPLY_TO_ATTACKER;
    }

    @Override
    public boolean test (LootContext lootContext) {
        LootContext flippedContext = CPCLootContext.createSingleEntity(
            lootContext.getWorld(),
            lootContext.get(LootContextParameters.ENCHANTMENT_LEVEL),
            lootContext.get(LootContextParameters.ATTACKING_ENTITY),
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
}
