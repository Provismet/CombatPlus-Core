package com.provismet.CombatPlusCore.enchantment.loot.condition;

import com.google.common.collect.ImmutableSet;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.context.ContextParameter;

import java.util.Set;

public interface ItemCondition extends LootCondition {
    @Override
    default Set<ContextParameter<?>> getAllowedParameters () {
        return ImmutableSet.of(LootContextParameters.TOOL);
    }

    @FunctionalInterface
    interface Builder extends LootCondition.Builder {
        ItemCondition build ();
    }
}
