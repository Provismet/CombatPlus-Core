package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.ApplyVelocityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.CodeExecutionSingleEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.DamageEquipmentEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.FreezeEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.HealEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.SetCooldownEnchantmentEffect;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public class SingleEntityEffects {
    public static void register () {
        registerEntityEffect("code_execution_single_entity", CodeExecutionSingleEntityEffect.CODEC);
        registerEntityEffect("freeze", FreezeEffect.CODEC);
        registerEntityEffect("apply_velocity", ApplyVelocityEffect.CODEC);
        registerEntityEffect("heal", HealEffect.CODEC);
        registerEntityEffect("damage_equipment", DamageEquipmentEffect.CODEC);
        registerEntityEffect("set_cooldown", SetCooldownEnchantmentEffect.CODEC);
    }

    private static void registerEntityEffect (String name, MapCodec<? extends EnchantmentEntityEffect> codec) {
        Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, CombatPlusCoreMain.identifier(name), codec);
    }
}
