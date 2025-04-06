package com.provismet.CombatPlusCore.enchantment.effect.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.effect.CPCDataComponentEntityEffect;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;

import java.util.List;
import java.util.Optional;

public record BlocksAttacksComponentEntityEffect (
    EnchantmentLevelBasedValue blockDelaySeconds,
    EnchantmentLevelBasedValue disableCooldownScale,
    List<BlocksAttacksComponent.DamageReduction> damageReductions,
    BlocksAttacksComponent.ItemDamage itemDamage,
    Optional<TagKey<DamageType>> bypassedBy,
    Optional<RegistryEntry<SoundEvent>> blockSound,
    Optional<RegistryEntry<SoundEvent>> disableSound)
    implements CPCDataComponentEntityEffect<BlocksAttacksComponent>
{
    public static final MapCodec<BlocksAttacksComponentEntityEffect> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            EnchantmentLevelBasedValue.CODEC.optionalFieldOf("block_delay_seconds", EnchantmentLevelBasedValue.constant(0)).forGetter(BlocksAttacksComponentEntityEffect::blockDelaySeconds),
            EnchantmentLevelBasedValue.CODEC.optionalFieldOf("disable_cooldown_scale", EnchantmentLevelBasedValue.constant(1)).forGetter(BlocksAttacksComponentEntityEffect::disableCooldownScale),
            BlocksAttacksComponent.DamageReduction.CODEC.listOf().optionalFieldOf("damage_reductions", List.of(new BlocksAttacksComponent.DamageReduction(90f, Optional.empty(), 0f, 1f))).forGetter(BlocksAttacksComponentEntityEffect::damageReductions),
            BlocksAttacksComponent.ItemDamage.CODEC.optionalFieldOf("item_damage", BlocksAttacksComponent.ItemDamage.DEFAULT).forGetter(BlocksAttacksComponentEntityEffect::itemDamage),
            TagKey.codec(RegistryKeys.DAMAGE_TYPE).optionalFieldOf("bypassed_by").forGetter(BlocksAttacksComponentEntityEffect::bypassedBy),
            SoundEvent.ENTRY_CODEC.optionalFieldOf("block_sound").forGetter(BlocksAttacksComponentEntityEffect::blockSound),
            SoundEvent.ENTRY_CODEC.optionalFieldOf("disabled_sound").forGetter(BlocksAttacksComponentEntityEffect::disableSound)
        ).apply(instance, BlocksAttacksComponentEntityEffect::new)
    );

    @Override
    public MapCodec<BlocksAttacksComponentEntityEffect> getCodec () {
        return CODEC;
    }

    @Override
    public BlocksAttacksComponent getComponent (int level) {
        return new BlocksAttacksComponent(
            this.blockDelaySeconds.getValue(level),
            this.disableCooldownScale.getValue(level),
            this.damageReductions,
            this.itemDamage,
            this.bypassedBy,
            this.blockSound,
            this.disableSound
        );
    }

    @Override
    public ComponentType<BlocksAttacksComponent> getComponentType () {
        return DataComponentTypes.BLOCKS_ATTACKS;
    }
}
