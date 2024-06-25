package com.provismet.CombatPlusCore.enchantment.loot.condition;

import com.google.common.collect.ImmutableSet;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;

import java.util.Set;

public interface ItemCondition extends LootCondition {
    @Override
    default Set<LootContextParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootContextParameters.TOOL);
    }

    @FunctionalInterface
    public static interface Builder extends LootCondition.Builder {
        ItemCondition build ();
    }
}
