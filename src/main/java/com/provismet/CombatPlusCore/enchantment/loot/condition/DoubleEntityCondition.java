package com.provismet.CombatPlusCore.enchantment.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContextParameters;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.context.ContextParameter;

import java.util.Set;

public interface DoubleEntityCondition extends LootCondition {
    @Override
    default Set<ContextParameter<?>> getAllowedParameters () {
        return ImmutableSet.of(
            LootContextParameters.ORIGIN,
            LootContextParameters.ENCHANTMENT_LEVEL,
            LootContextParameters.THIS_ENTITY,
            CPCLootContextParameters.TARGET_ENTITY,
            LootContextParameters.TOOL
        );
    }

    @FunctionalInterface
    interface Builder extends LootCondition.Builder {
        DoubleEntityCondition build ();
    }
}
