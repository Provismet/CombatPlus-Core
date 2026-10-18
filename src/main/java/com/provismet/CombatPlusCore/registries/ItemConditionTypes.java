package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.loot.condition.ItemCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.item.IsDualWeaponCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.item.IsMeleeWeaponCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.item.ItemLambdaCondition;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public abstract class ItemConditionTypes {
    public static void register () {
        register("code_execution_item_condition", ItemLambdaCondition.CODEC);
        register("is_melee_weapon", IsMeleeWeaponCondition.CODEC);
        register("is_dual_weapon", IsDualWeaponCondition.CODEC);
    }

    private static void register (String name, MapCodec<? extends ItemCondition> codec) {
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, CPCMain.identifier(name), codec);
    }
}
