package com.provismet.combatplus.api.enchantment.effect.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.combatplus.api.enchantment.effect.CombatPlusDataComponentEntityEffect;
import com.provismet.combatplus.registries.CombatPlusDataComponentTypes;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;

/**
 * Enchantment component that grants the {@link CombatPlusDataComponentTypes#COOLDOWN_GROUP} data component to an item.
 * <p>
 * This component alters the cooldown group used by the item. Items within the same cooldown group will share cooldowns with each other.
 * An item's default group is its own item id.
 *
 * @param group The identifier of the cooldown group.
 */
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
