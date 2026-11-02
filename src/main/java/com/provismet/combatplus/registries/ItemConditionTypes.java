package com.provismet.combatplus.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.combatplus.CombatPlusCoreMain;
import com.provismet.combatplus.api.loot.condition.ItemCondition;
import com.provismet.combatplus.api.loot.condition.item.IsDualWeaponCondition;
import com.provismet.combatplus.api.loot.condition.item.IsMeleeWeaponCondition;
import com.provismet.combatplus.api.loot.condition.item.ItemLambdaCondition;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public interface ItemConditionTypes {
    static void register () {
        register("code_execution_item_condition", ItemLambdaCondition.CODEC);
        register("is_melee_weapon", IsMeleeWeaponCondition.CODEC);
        register("is_dual_weapon", IsDualWeaponCondition.CODEC);
    }

    private static void register (String name, MapCodec<? extends ItemCondition> codec) {
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, CombatPlusCoreMain.identifier(name), codec);
    }
}
