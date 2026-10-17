package com.provismet.CombatPlusCore.enchantment.effect.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.effect.CPCDataComponentEntityEffect;
import com.provismet.CombatPlusCore.registries.CPCDataComponentTypes;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public record MaxUseTimeComponentEntityEffect (LevelBasedValue ticks) implements CPCDataComponentEntityEffect<Integer> {
    public static final MapCodec<MaxUseTimeComponentEntityEffect> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            LevelBasedValue.CODEC.fieldOf("ticks").forGetter(MaxUseTimeComponentEntityEffect::ticks)
        ).apply(instance, MaxUseTimeComponentEntityEffect::new)
    );

    @Override
    public Integer getComponent (int level) {
        return Math.max((int)ticks.calculate(level), 1);
    }

    @Override
    public DataComponentType<Integer> getComponentType () {
        return CPCDataComponentTypes.MAX_USE_TIME;
    }

    @Override
    public MapCodec<MaxUseTimeComponentEntityEffect> getCodec () {
        return CODEC;
    }
}
