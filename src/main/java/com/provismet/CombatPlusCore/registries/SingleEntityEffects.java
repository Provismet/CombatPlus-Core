package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.ApplyVelocityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.CodeExecutionSingleEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.DamageEquipmentEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.FreezeEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.HealEffect;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class SingleEntityEffects {
    public static void register () {
        registerEntityEffect("code_execution_single_entity", CodeExecutionSingleEntityEffect.CODEC);
        registerEntityEffect("freeze", FreezeEffect.CODEC);
        registerEntityEffect("apply_velocity", ApplyVelocityEffect.CODEC);
        registerEntityEffect("heal", HealEffect.CODEC);
        registerEntityEffect("damage_equipment", DamageEquipmentEffect.CODEC);
    }

    private static void registerEntityEffect (String name, MapCodec<? extends EnchantmentEntityEffect> codec) {
        Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, CPCMain.identifier(name), codec);
    }
}
