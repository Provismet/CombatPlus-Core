package com.provismet.CombatPlusCore.enchantment.effect.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.effect.CPCDataComponentEntityEffect;
import com.provismet.CombatPlusCore.registries.CPCDataComponentTypes;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;

public record MaxUseTimeComponentEntityEffect (EnchantmentLevelBasedValue ticks) implements CPCDataComponentEntityEffect<Integer> {
    public static final MapCodec<MaxUseTimeComponentEntityEffect> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            EnchantmentLevelBasedValue.CODEC.fieldOf("ticks").forGetter(MaxUseTimeComponentEntityEffect::ticks)
        ).apply(instance, MaxUseTimeComponentEntityEffect::new)
    );

    @Override
    public Integer getComponent (int level) {
        return Math.max((int)ticks.getValue(level), 1);
    }

    @Override
    public ComponentType<Integer> getComponentType () {
        return CPCDataComponentTypes.MAX_USE_TIME;
    }

    @Override
    public MapCodec<MaxUseTimeComponentEntityEffect> getCodec () {
        return CODEC;
    }
}
