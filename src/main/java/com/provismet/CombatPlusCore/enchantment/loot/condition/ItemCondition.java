package com.provismet.CombatPlusCore.enchantment.loot.condition;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public interface ItemCondition extends LootItemCondition {
    @Override
    default Set<ContextKey<?>> getReferencedContextParams () {
        return ImmutableSet.of(LootContextParams.TOOL);
    }

    @FunctionalInterface
    interface Builder extends LootItemCondition.Builder {
        ItemCondition build ();
    }
}
