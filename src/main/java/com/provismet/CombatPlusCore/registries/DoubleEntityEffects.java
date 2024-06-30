package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.ApplyKnockbackEnchantmentEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.ApplyToBothEntityEnchantmentEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.ApplyToTargetEntityEnchantmentEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.ApplyToUserEntityEnchantmentEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.InvertedEntityEnchantmentEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.WeaponPostCharged;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.WeaponPostCritical;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.WeaponPostKill;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.registry.Registry;

public class DoubleEntityEffects {
    public static void register () {
        register("code_execution_double_entity", CodeExecutionDoubleEntityEffect.CODEC);
        register("apply_to_owner", ApplyToUserEntityEnchantmentEffect.CODEC);
        register("apply_to_target", ApplyToTargetEntityEnchantmentEffect.CODEC);
        register("apply_to_both", ApplyToBothEntityEnchantmentEffect.CODEC);
        register("invert", InvertedEntityEnchantmentEffect.CODEC);
        register("apply_knockback", ApplyKnockbackEnchantmentEffect.CODEC);
        register("weapon_post_charged_attack", WeaponPostCharged.CODEC);
        register("weapon_post_critical_attack", WeaponPostCritical.CODEC);
        register("weapon_post_kill", WeaponPostKill.CODEC);
    }

    private static void register (String name, MapCodec<? extends CPCEnchantmentEntityEffect> codec) {
        Registry.register(CPCRegistries.ENCHANTMENT_DUAL_ENTITY_EFFECT_TYPE, CPCMain.identifier(name), codec);
    }
}
