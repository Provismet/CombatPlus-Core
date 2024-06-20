package com.provismet.CombatPlusCore.enchantment.loot.condition;

import com.google.common.collect.ImmutableSet;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;

import java.util.Set;

public interface SingleEntityCondition extends LootCondition {
    @Override
    default Set<LootContextParameter<?>> getRequiredParameters () {
        return ImmutableSet.of(
            LootContextParameters.ORIGIN,
            LootContextParameters.THIS_ENTITY,
            LootContextParameters.ENCHANTMENT_LEVEL
        );
    }

    public static Set<LootContextParameter<?>> getExtendedRequiredParameters () {
        return ImmutableSet.of(
            LootContextParameters.ORIGIN,
            LootContextParameters.THIS_ENTITY,
            LootContextParameters.ENCHANTMENT_LEVEL,
            LootContextParameters.TOOL
        );
    }

    @FunctionalInterface
    public static interface Builder extends LootCondition.Builder {
        SingleEntityCondition build ();
    }
}
