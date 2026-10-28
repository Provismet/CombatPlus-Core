package com.provismet.CombatPlusCore.api.enchantment.effect.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.api.enchantment.effect.CombatPlusDataComponentEntityEffect;
import java.util.Optional;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.component.UseCooldown;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public record UseCooldownComponentEntityEffect (LevelBasedValue seconds, Optional<Identifier> cooldownGroup) implements CombatPlusDataComponentEntityEffect<UseCooldown> {
    public static final MapCodec<UseCooldownComponentEntityEffect> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            LevelBasedValue.CODEC.fieldOf("seconds").forGetter(UseCooldownComponentEntityEffect::seconds),
            Identifier.CODEC.optionalFieldOf("cooldown_group").forGetter(UseCooldownComponentEntityEffect::cooldownGroup)
        ).apply(instance, UseCooldownComponentEntityEffect::new)
    );

    @Override
    public UseCooldown getComponent (int level) {
        return new UseCooldown(Math.max(this.seconds.calculate(level), 0.0001f), this.cooldownGroup);
    }

    @Override
    public DataComponentType<UseCooldown> getComponentType () {
        return DataComponents.USE_COOLDOWN;
    }

    @Override
    public MapCodec<UseCooldownComponentEntityEffect> getCodec () {
        return CODEC;
    }
}
