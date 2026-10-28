package com.provismet.CombatPlusCore.api.enchantment.effect.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.api.enchantment.effect.CPCDataComponentEntityEffect;
import com.provismet.CombatPlusCore.registries.CPCDataComponentTypes;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;

public record CooldownGroupComponentEntityEffect (Identifier group) implements CPCDataComponentEntityEffect<Identifier> {
    public static final MapCodec<CooldownGroupComponentEntityEffect> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            Identifier.CODEC.fieldOf("group").forGetter(CooldownGroupComponentEntityEffect::group)
        ).apply(instance, CooldownGroupComponentEntityEffect::new)
    );

    @Override
    public Identifier getComponent (int level) {
        return this.group;
    }

    @Override
    public DataComponentType<Identifier> getComponentType () {
        return CPCDataComponentTypes.COOLDOWN_GROUP;
    }

    @Override
    public MapCodec<CooldownGroupComponentEntityEffect> getCodec () {
        return CODEC;
    }
}
