package com.provismet.CombatPlusCore.enchantment.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContextParameters;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface DoubleEntityCondition extends LootItemCondition {
    @Override
    @NotNull
    default Set<ContextKey<?>> getReferencedContextParams () {
        return ImmutableSet.of(
            LootContextParams.ORIGIN,
            LootContextParams.ENCHANTMENT_LEVEL,
            LootContextParams.THIS_ENTITY,
            CPCLootContextParameters.TARGET_ENTITY,
            LootContextParams.TOOL
        );
    }

    @FunctionalInterface
    interface Builder extends LootItemCondition.Builder {
        @NotNull
        DoubleEntityCondition build ();
    }
}
