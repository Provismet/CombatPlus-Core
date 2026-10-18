package com.provismet.CombatPlusCore.enchantment.loot.condition;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public interface SingleEntityCondition extends LootItemCondition {
    @Override
    @NotNull
    default Set<ContextKey<?>> getReferencedContextParams () {
        return ImmutableSet.of(
            LootContextParams.ORIGIN,
            LootContextParams.THIS_ENTITY,
            LootContextParams.ENCHANTMENT_LEVEL
        );
    }

    static Set<ContextKey<?>> getExtendedRequiredParameters () {
        return ImmutableSet.of(
            LootContextParams.ORIGIN,
            LootContextParams.THIS_ENTITY,
            LootContextParams.ENCHANTMENT_LEVEL,
            LootContextParams.TOOL
        );
    }

    @FunctionalInterface
    interface Builder extends LootItemCondition.Builder {
        @NotNull
        SingleEntityCondition build ();
    }
}
