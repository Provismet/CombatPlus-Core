package com.provismet.combatplus.api.enchantment.effect.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.combatplus.api.enchantment.effect.CombatPlusDataComponentEntityEffect;
import com.provismet.combatplus.registries.CombatPlusDataComponentTypes;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

/**
 * Enchantment data component that modifies the maximum use time of an item by granting the {@link CombatPlusDataComponentTypes#MAX_USE_TIME} item component.
 * @param ticks The maximum use time of the item in ticks.
 */
public record MaxUseTimeComponentEntityEffect (LevelBasedValue ticks) implements CombatPlusDataComponentEntityEffect<Integer> {
    public static final MapCodec<MaxUseTimeComponentEntityEffect> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            LevelBasedValue.CODEC.fieldOf("ticks").forGetter(MaxUseTimeComponentEntityEffect::ticks)
        ).apply(instance, MaxUseTimeComponentEntityEffect::new)
    );

    public MaxUseTimeComponentEntityEffect (int ticks) {
        this(new LevelBasedValue.Constant(ticks));
    }

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
