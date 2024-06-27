package com.provismet.CombatPlusCore.enchantment.effect.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public record DamageEquipmentEffect (List<EquipmentSlot> slots, EnchantmentLevelBasedValue value) implements EnchantmentEntityEffect {
    public static final MapCodec<DamageEquipmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(EquipmentSlot.CODEC.listOf().fieldOf("equipment_slots").forGetter(DamageEquipmentEffect::slots), EnchantmentLevelBasedValue.CODEC.fieldOf("value").forGetter(DamageEquipmentEffect::value)).apply(instance, DamageEquipmentEffect::new));

    @Override
    public void apply (ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if (!(target instanceof LivingEntity livingTarget)) return;
        int amount = (int)this.value.getValue(level);
        for (EquipmentSlot slot : this.slots) {
            livingTarget.getEquippedStack(slot).damage(amount, livingTarget, slot);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec () {
        return CODEC;
    }
}
