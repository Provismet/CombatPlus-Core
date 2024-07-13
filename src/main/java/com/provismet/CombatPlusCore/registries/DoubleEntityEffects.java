package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.ApplyKnockbackEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.ApplyToBothEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.ApplyToTargetEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.ApplyToUserEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.InvertedEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.WeaponPostChargedEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.WeaponPostCriticalEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.WeaponPostKillEffect;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.registry.Registry;

public class DoubleEntityEffects {
    public static void register () {
        register("code_execution_double_entity", CodeExecutionDoubleEntityEffect.CODEC);
        register("apply_to_owner", ApplyToUserEntityEffect.CODEC);
        register("apply_to_target", ApplyToTargetEntityEffect.CODEC);
        register("apply_to_both", ApplyToBothEffect.CODEC);
        register("invert", InvertedEntityEffect.CODEC);
        register("apply_knockback", ApplyKnockbackEffect.CODEC);
        register("weapon_post_charged_attack", WeaponPostChargedEffect.CODEC);
        register("weapon_post_critical_attack", WeaponPostCriticalEffect.CODEC);
        register("weapon_post_kill", WeaponPostKillEffect.CODEC);
    }

    private static void register (String name, MapCodec<? extends CPCEnchantmentEntityEffect> codec) {
        Registry.register(CPCRegistries.ENCHANTMENT_DUAL_ENTITY_EFFECT_TYPE, CPCMain.identifier(name), codec);
    }
}
