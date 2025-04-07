package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.effect.CPCDataComponentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.component.BlocksAttacksComponentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.component.CooldownGroupComponentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.component.MaxUseTimeComponentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.component.UseCooldownComponentEntityEffect;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.registry.Registry;

public abstract class CPCDataComponentEffects {
    public static void register () {
        register("blocks_attacks_component", BlocksAttacksComponentEntityEffect.CODEC);
        register("use_cooldown_component", UseCooldownComponentEntityEffect.CODEC);
        register("use_group_component", CooldownGroupComponentEntityEffect.CODEC);
        register("max_use_time_component", MaxUseTimeComponentEntityEffect.CODEC);
    }

    private static void register (String name, MapCodec<? extends CPCDataComponentEntityEffect<?>> codec) {
        Registry.register(CPCRegistries.ENCHANTMENT_DATA_COMPONENT_EFFECT_TYPE, CPCMain.identifier(name), codec);
    }
}
