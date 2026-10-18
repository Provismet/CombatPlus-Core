package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.ApplyToAttackerCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.ApplyToItemCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.DimensionCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.ExposedToMoonCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.ExposedToSkyCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.ExposedToSunCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.HealthCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.RelativeHealthCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.SingleEntityLambdaCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.TickModuloCondition;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public abstract class SingleEntityLootConditionTypes {
    public static void register () {
        register("code_execution_single_entity_condition", SingleEntityLambdaCondition.CODEC);
        register("health", HealthCondition.CODEC);
        register("relative_health", RelativeHealthCondition.CODEC);
        register("dimension", DimensionCondition.CODEC);
        register("every_x_ticks", TickModuloCondition.CODEC);
        register("exposed_to_sky", ExposedToSkyCondition.CODEC);
        register("exposed_to_sun", ExposedToSunCondition.CODEC);
        register("exposed_to_moon", ExposedToMoonCondition.CODEC);
        register("apply_to_item", ApplyToItemCondition.CODEC);
        register("apply_to_attacker", ApplyToAttackerCondition.CODEC);
    }

    private static void register (String name, MapCodec<? extends LootItemCondition> codec) {
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, CPCMain.identifier(name), codec);
    }
}
