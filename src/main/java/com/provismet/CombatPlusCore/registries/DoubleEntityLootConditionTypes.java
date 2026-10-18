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

public abstract class DoubleEntityLootConditionTypes {
    public static void register () {
        register("code_execution_double_entity_condition", DoubleEntityLambdaCondition.CODEC);
        register("apply_to_both", ApplyToBothCondition.CODEC);
        register("apply_to_user", ApplyToUserCondition.CODEC);
        register("apply_to_target", ApplyToTargetCondition.CODEC);
    }

    private static void register (String name, MapCodec<? extends LootItemCondition> codec) {
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, CPCMain.identifier(name), codec);
    }
}
