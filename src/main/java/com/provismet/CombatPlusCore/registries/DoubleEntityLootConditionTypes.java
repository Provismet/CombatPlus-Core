package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity.ApplyToBothCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity.ApplyToTargetCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity.ApplyToUserCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity.DoubleEntityLambdaCondition;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public abstract class DoubleEntityLootConditionTypes {
    public static final LootItemConditionType LAMBDA = register("code_execution_double_entity_condition", DoubleEntityLambdaCondition.CODEC);
    public static final LootItemConditionType APPLY_TO_BOTH = register("apply_to_both", ApplyToBothCondition.CODEC);
    public static final LootItemConditionType APPLY_TO_USER = register("apply_to_user", ApplyToUserCondition.CODEC);
    public static final LootItemConditionType APPLY_TO_TARGET = register("apply_to_target", ApplyToTargetCondition.CODEC);

    private static LootItemConditionType register (String name, MapCodec<? extends LootItemCondition> codec) {
        return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, CPCMain.identifier(name), new LootItemConditionType(codec));
    }

    public static void init () {}
}
