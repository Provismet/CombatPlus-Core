package com.provismet.CombatPlusCore.api.enchantment.effect.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.registries.CombatPlusEnchantmentComponentTypes;
import com.provismet.CombatPlusCore.utility.CombatPlusEnchantmentHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record SetCooldownEnchantmentEffect (LevelBasedValue ticks) implements EnchantmentEntityEffect {
    public static final MapCodec<SetCooldownEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            LevelBasedValue.CODEC.fieldOf("ticks").forGetter(SetCooldownEnchantmentEffect::ticks)
        ).apply(instance, SetCooldownEnchantmentEffect::new)
    );

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity user, Vec3 pos) {
        if (!(user instanceof Player player)) return;

        int cooldown = (int) CombatPlusEnchantmentHelper.modifyValue(CombatPlusEnchantmentComponentTypes.MODIFY_COOLDOWN, world, context.itemStack(), user, this.ticks.calculate(level));
        player.getCooldowns().addCooldown(context.itemStack(), cooldown);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec () {
        return CODEC;
    }
}
