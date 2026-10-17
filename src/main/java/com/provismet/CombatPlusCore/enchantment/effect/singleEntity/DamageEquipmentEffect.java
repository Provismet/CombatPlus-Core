package com.provismet.CombatPlusCore.enchantment.effect.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record DamageEquipmentEffect (List<EquipmentSlot> slots, LevelBasedValue value) implements EnchantmentEntityEffect {
    public static final MapCodec<DamageEquipmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            EquipmentSlot.CODEC.listOf().fieldOf("equipment_slots").forGetter(DamageEquipmentEffect::slots),
            LevelBasedValue.CODEC.fieldOf("value").forGetter(DamageEquipmentEffect::value)
        ).apply(instance, DamageEquipmentEffect::new)
    );

    @Override
    public void apply (ServerLevel world, int level, EnchantedItemInUse context, Entity target, Vec3 pos) {
        if (!(target instanceof LivingEntity livingTarget)) return;
        int amount = (int)this.value.calculate(level);
        for (EquipmentSlot slot : this.slots) {
            livingTarget.getItemBySlot(slot).hurtAndBreak(amount, livingTarget, slot);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec () {
        return CODEC;
    }
}
