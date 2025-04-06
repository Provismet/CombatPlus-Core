package com.provismet.CombatPlusCore.enchantment.effect.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.effect.CPCDataComponentEntityEffect;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.UseCooldownComponent;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.util.Identifier;

import java.util.Optional;

public record UseCooldownComponentEntityEffect (EnchantmentLevelBasedValue seconds, Optional<Identifier> cooldownGroup) implements CPCDataComponentEntityEffect<UseCooldownComponent> {
    public static final MapCodec<UseCooldownComponentEntityEffect> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            EnchantmentLevelBasedValue.CODEC.fieldOf("seconds").forGetter(UseCooldownComponentEntityEffect::seconds),
            Identifier.CODEC.optionalFieldOf("cooldown_group").forGetter(UseCooldownComponentEntityEffect::cooldownGroup)
        ).apply(instance, UseCooldownComponentEntityEffect::new)
    );

    @Override
    public UseCooldownComponent getComponent (int level) {
        return new UseCooldownComponent(Math.max(this.seconds.getValue(level), 0.0001f), this.cooldownGroup);
    }

    @Override
    public ComponentType<UseCooldownComponent> getComponentType () {
        return DataComponentTypes.USE_COOLDOWN;
    }

    @Override
    public MapCodec<UseCooldownComponentEntityEffect> getCodec () {
        return CODEC;
    }
}
