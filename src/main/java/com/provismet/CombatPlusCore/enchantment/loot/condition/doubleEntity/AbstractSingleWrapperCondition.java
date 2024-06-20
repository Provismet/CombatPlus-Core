package com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.DoubleEntityCondition;
import net.minecraft.loot.condition.LootCondition;

import java.util.function.Function;

public abstract class AbstractSingleWrapperCondition implements DoubleEntityCondition {
    protected final LootCondition condition;

    protected AbstractSingleWrapperCondition (LootCondition condition) {
        this.condition = condition;
    }

    public LootCondition condition () {
        return this.condition;
    }

    protected static <T extends  AbstractSingleWrapperCondition> MapCodec<T> createCodec (Function<LootCondition, T> termToCondition) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(LootCondition.CODEC.fieldOf("applied_condition").forGetter(AbstractSingleWrapperCondition::condition)).apply(instance, termToCondition));
    }
}
