package com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.DoubleEntityCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.function.Function;

public abstract class AbstractSingleWrapperCondition implements DoubleEntityCondition {
    protected final LootItemCondition condition;

    protected AbstractSingleWrapperCondition (LootItemCondition condition) {
        this.condition = condition;
    }

    public LootItemCondition condition () {
        return this.condition;
    }

    protected static <T extends  AbstractSingleWrapperCondition> MapCodec<T> createCodec (Function<LootItemCondition, T> termToCondition) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(LootItemCondition.DIRECT_CODEC.fieldOf("applied_condition").forGetter(AbstractSingleWrapperCondition::condition)).apply(instance, termToCondition));
    }
}
