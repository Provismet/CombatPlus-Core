package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.loot.condition.ItemCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.item.IsDualWeaponCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.item.IsMeleeWeaponCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.item.ItemLambdaCondition;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public abstract class ItemConditionTypes {
    public static final LootItemConditionType LAMBDA = register("code_execution_item_condition", ItemLambdaCondition.CODEC);
    public static final LootItemConditionType IS_MELEE_WEAPON = register("is_melee_weapon", IsMeleeWeaponCondition.CODEC);
    public static final LootItemConditionType IS_DUAL_WEAPON = register("is_dual_weapon", IsDualWeaponCondition.CODEC);

    private static LootItemConditionType register (String name, MapCodec<? extends ItemCondition> codec) {
        return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, CPCMain.identifier(name), new LootItemConditionType(codec));
    }

    public static void init () {}
}
