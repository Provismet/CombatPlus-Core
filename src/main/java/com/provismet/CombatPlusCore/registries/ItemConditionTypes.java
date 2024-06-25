package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.loot.condition.ItemCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.item.IsDualWeaponCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.item.IsMeleeWeaponCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.item.ItemLambdaCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemConditionTypes {
    public static final LootConditionType LAMBDA = register("code_execution_item_condition", ItemLambdaCondition.CODEC);
    public static final LootConditionType IS_MELEE_WEAPON = register("is_melee_weapon", IsMeleeWeaponCondition.CODEC);
    public static final LootConditionType IS_DUAL_WEAPON = register("is_dual_weapon", IsDualWeaponCondition.CODEC);

    private static LootConditionType register (String name, MapCodec<? extends ItemCondition> codec) {
        return Registry.register(Registries.LOOT_CONDITION_TYPE, CPCMain.identifier(name), new LootConditionType(codec));
    }

    public static void init () {};
}
