package com.provismet.CombatPlusCore.enchantment.effect.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.registries.CPCEnchantmentComponentTypes;
import com.provismet.CombatPlusCore.utility.CPCEnchantmentHelper;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record SetCooldownEnchantmentEffect (EnchantmentLevelBasedValue ticks) implements EnchantmentEntityEffect {
    public static final MapCodec<SetCooldownEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            EnchantmentLevelBasedValue.CODEC.fieldOf("ticks").forGetter(SetCooldownEnchantmentEffect::ticks)
        ).apply(instance, SetCooldownEnchantmentEffect::new)
    );

    @Override
    public void apply (ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (!(user instanceof PlayerEntity player)) return;

        int cooldown = (int)CPCEnchantmentHelper.modifyValue(CPCEnchantmentComponentTypes.MODIFY_COOLDOWN, world, context.stack(), user, this.ticks.getValue(level));
        player.getItemCooldownManager().set(context.stack(), cooldown);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec () {
        return CODEC;
    }
}
