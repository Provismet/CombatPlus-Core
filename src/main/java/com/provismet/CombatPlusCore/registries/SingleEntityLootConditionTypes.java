package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.ApplyToAttacker;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.ApplyToItem;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.DimensionCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.ExposedToMoonCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.ExposedToSkyCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.ExposedToSunCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.HealthCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.RelativeHealthCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.SingleEntityLambdaCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity.TickModuloCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class SingleEntityLootConditionTypes {
    public static final LootConditionType LAMBDA = register("code_execution_single_entity_condition", SingleEntityLambdaCondition.CODEC);
    public static final LootConditionType HEALTH = register("health", HealthCondition.CODEC);
    public static final LootConditionType RELATIVE_HEALTH = register("relative_health", RelativeHealthCondition.CODEC);
    public static final LootConditionType DIMENSION = register("dimension", DimensionCondition.CODEC);
    public static final LootConditionType TICK_MODULO = register("every_x_ticks", TickModuloCondition.CODEC);
    public static final LootConditionType EXPOSED_TO_SKY = register("exposed_to_sky", ExposedToSkyCondition.CODEC);
    public static final LootConditionType EXPOSED_TO_SUN = register("exposed_to_sun", ExposedToSunCondition.CODEC);
    public static final LootConditionType EXPOSED_TO_MOON = register("exposed_to_moon", ExposedToMoonCondition.CODEC);
    public static final LootConditionType APPLY_TO_ITEM = register("apply_to_item", ApplyToItem.CODEC);
    public static final LootConditionType APPLY_TO_ATTACKER = register("apply_to_attacker", ApplyToAttacker.CODEC);

    private static LootConditionType register (String name, MapCodec<? extends LootCondition> codec) {
        return Registry.register(Registries.LOOT_CONDITION_TYPE, CPCMain.identifier(name), new LootConditionType(codec));
    }

    public static void init () {}
}
