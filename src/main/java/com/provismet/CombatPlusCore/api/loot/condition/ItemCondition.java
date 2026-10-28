package com.provismet.CombatPlusCore.api.loot.condition;

import com.google.common.collect.ImmutableSet;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface ItemCondition extends LootItemCondition {
    @Override
    @NotNull
    default Set<ContextKey<?>> getReferencedContextParams () {
        return ImmutableSet.of(LootContextParams.TOOL);
    }

    @FunctionalInterface
    interface Builder extends LootItemCondition.Builder {
        @NotNull
        ItemCondition build ();
    }
}
