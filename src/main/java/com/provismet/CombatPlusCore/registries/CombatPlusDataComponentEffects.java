package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.api.enchantment.effect.CombatPlusDataComponentEntityEffect;
import com.provismet.CombatPlusCore.api.enchantment.effect.component.BlocksAttacksComponentEntityEffect;
import com.provismet.CombatPlusCore.api.enchantment.effect.component.CooldownGroupComponentEntityEffect;
import com.provismet.CombatPlusCore.api.enchantment.effect.component.MaxUseTimeComponentEntityEffect;
import com.provismet.CombatPlusCore.api.enchantment.effect.component.UseCooldownComponentEntityEffect;
import com.provismet.CombatPlusCore.utility.CombatPlusRegistries;
import net.minecraft.core.Registry;

public abstract class CombatPlusDataComponentEffects {
    public static void register () {
        register("blocks_attacks_component", BlocksAttacksComponentEntityEffect.CODEC);
        register("use_cooldown_component", UseCooldownComponentEntityEffect.CODEC);
        register("use_group_component", CooldownGroupComponentEntityEffect.CODEC);
        register("max_use_time_component", MaxUseTimeComponentEntityEffect.CODEC);
    }

    private static void register (String name, MapCodec<? extends CombatPlusDataComponentEntityEffect<?>> codec) {
        Registry.register(CombatPlusRegistries.ENCHANTMENT_DATA_COMPONENT_EFFECT_TYPE, CombatPlusCoreMain.identifier(name), codec);
    }
}
