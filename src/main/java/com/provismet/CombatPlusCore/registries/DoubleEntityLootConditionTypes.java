package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity.ApplyToBothCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity.ApplyToTargetCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity.ApplyToUserCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity.DoubleEntityLambdaCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public abstract class DoubleEntityLootConditionTypes {
    public static final LootConditionType LAMBDA = register("code_execution_double_entity_condition", DoubleEntityLambdaCondition.CODEC);
    public static final LootConditionType APPLY_TO_BOTH = register("apply_to_both", ApplyToBothCondition.CODEC);
    public static final LootConditionType APPLY_TO_USER = register("apply_to_user", ApplyToUserCondition.CODEC);
    public static final LootConditionType APPLY_TO_TARGET = register("apply_to_target", ApplyToTargetCondition.CODEC);

    private static LootConditionType register (String name, MapCodec<? extends LootCondition> codec) {
        return Registry.register(Registries.LOOT_CONDITION_TYPE, CPCMain.identifier(name), new LootConditionType(codec));
    }

    public static void init () {}
}
