package com.provismet.CombatPlusCore.api.enchantment.effect.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.api.enchantment.effect.CombatPlusDataComponentEntityEffect;
import com.provismet.CombatPlusCore.registries.CombatPlusDataComponentTypes;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public record MaxUseTimeComponentEntityEffect (LevelBasedValue ticks) implements CombatPlusDataComponentEntityEffect<Integer> {
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
        return CombatPlusDataComponentTypes.MAX_USE_TIME;
    }

    @Override
    public MapCodec<MaxUseTimeComponentEntityEffect> getCodec () {
        return CODEC;
    }
}
