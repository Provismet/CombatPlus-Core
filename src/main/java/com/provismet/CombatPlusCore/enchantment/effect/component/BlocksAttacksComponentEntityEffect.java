package com.provismet.CombatPlusCore.enchantment.effect.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.effect.CPCDataComponentEntityEffect;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public record BlocksAttacksComponentEntityEffect (
    LevelBasedValue blockDelaySeconds,
    LevelBasedValue disableCooldownScale,
    List<BlocksAttacks.DamageReduction> damageReductions,
    BlocksAttacks.ItemDamageFunction itemDamage,
    Optional<TagKey<DamageType>> bypassedBy,
    Optional<Holder<SoundEvent>> blockSound,
    Optional<Holder<SoundEvent>> disableSound)
    implements CPCDataComponentEntityEffect<BlocksAttacks>
{
    public static final MapCodec<BlocksAttacksComponentEntityEffect> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            LevelBasedValue.CODEC.optionalFieldOf("block_delay_seconds", LevelBasedValue.constant(0)).forGetter(BlocksAttacksComponentEntityEffect::blockDelaySeconds),
            LevelBasedValue.CODEC.optionalFieldOf("disable_cooldown_scale", LevelBasedValue.constant(1)).forGetter(BlocksAttacksComponentEntityEffect::disableCooldownScale),
            BlocksAttacks.DamageReduction.CODEC.listOf().optionalFieldOf("damage_reductions", List.of(new BlocksAttacks.DamageReduction(90f, Optional.empty(), 0f, 1f))).forGetter(BlocksAttacksComponentEntityEffect::damageReductions),
            BlocksAttacks.ItemDamageFunction.CODEC.optionalFieldOf("item_damage", BlocksAttacks.ItemDamageFunction.DEFAULT).forGetter(BlocksAttacksComponentEntityEffect::itemDamage),
            TagKey.hashedCodec(Registries.DAMAGE_TYPE).optionalFieldOf("bypassed_by").forGetter(BlocksAttacksComponentEntityEffect::bypassedBy),
            SoundEvent.CODEC.optionalFieldOf("block_sound").forGetter(BlocksAttacksComponentEntityEffect::blockSound),
            SoundEvent.CODEC.optionalFieldOf("disabled_sound").forGetter(BlocksAttacksComponentEntityEffect::disableSound)
        ).apply(instance, BlocksAttacksComponentEntityEffect::new)
    );

    @Override
    public MapCodec<BlocksAttacksComponentEntityEffect> getCodec () {
        return CODEC;
    }

    @Override
    public BlocksAttacks getComponent (int level) {
        return new BlocksAttacks(
            this.blockDelaySeconds.calculate(level),
            this.disableCooldownScale.calculate(level),
            this.damageReductions,
            this.itemDamage,
            this.bypassedBy,
            this.blockSound,
            this.disableSound
        );
    }

    @Override
    public DataComponentType<BlocksAttacks> getComponentType () {
        return DataComponents.BLOCKS_ATTACKS;
    }
}
