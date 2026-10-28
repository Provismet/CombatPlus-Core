package com.provismet.CombatPlusCore.api.enchantment.effect.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.api.enchantment.effect.CombatPlusDataComponentEntityEffect;
import com.provismet.CombatPlusCore.registries.CombatPlusDataComponentTypes;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;

public record CooldownGroupComponentEntityEffect (Identifier group) implements CombatPlusDataComponentEntityEffect<Identifier> {
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
        return CombatPlusDataComponentTypes.COOLDOWN_GROUP;
    }

    @Override
    public MapCodec<CooldownGroupComponentEntityEffect> getCodec () {
        return CODEC;
    }
}
