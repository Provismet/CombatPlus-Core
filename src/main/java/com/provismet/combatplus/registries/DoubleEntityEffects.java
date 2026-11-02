package com.provismet.combatplus.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.combatplus.CombatPlusCoreMain;
import com.provismet.combatplus.api.enchantment.effect.CombatPlusEnchantmentEntityEffect;
import com.provismet.combatplus.api.enchantment.effect.doubleEntity.ApplyKnockbackEffect;
import com.provismet.combatplus.api.enchantment.effect.doubleEntity.ApplyToBothEntityEffect;
import com.provismet.combatplus.api.enchantment.effect.doubleEntity.ApplyToTargetEntityEffect;
import com.provismet.combatplus.api.enchantment.effect.doubleEntity.ApplyToUserEntityEffect;
import com.provismet.combatplus.api.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.combatplus.api.enchantment.effect.doubleEntity.InvertedEntityEffect;
import com.provismet.combatplus.api.enchantment.effect.doubleEntity.WeaponPostChargedEffect;
import com.provismet.combatplus.api.enchantment.effect.doubleEntity.WeaponPostCriticalEffect;
import com.provismet.combatplus.api.enchantment.effect.doubleEntity.WeaponPostKillEffect;
import com.provismet.combatplus.utility.CombatPlusRegistries;
import net.minecraft.core.Registry;

public interface DoubleEntityEffects {
    static void register () {
        register("code_execution_double_entity", CodeExecutionDoubleEntityEffect.CODEC);
        register("apply_to_owner", ApplyToUserEntityEffect.CODEC);
        register("apply_to_target", ApplyToTargetEntityEffect.CODEC);
        register("apply_to_both", ApplyToBothEntityEffect.CODEC);
        register("invert", InvertedEntityEffect.CODEC);
        register("apply_knockback", ApplyKnockbackEffect.CODEC);
        register("weapon_post_charged_attack", WeaponPostChargedEffect.CODEC);
        register("weapon_post_critical_attack", WeaponPostCriticalEffect.CODEC);
        register("weapon_post_kill", WeaponPostKillEffect.CODEC);
    }

    private static void register (String name, MapCodec<? extends CombatPlusEnchantmentEntityEffect> codec) {
        Registry.register(CombatPlusRegistries.ENCHANTMENT_DUAL_ENTITY_EFFECT_TYPE, CombatPlusCoreMain.identifier(name), codec);
    }
}
