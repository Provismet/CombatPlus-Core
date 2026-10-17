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
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public abstract class SingleEntityLootConditionTypes {
    public static final LootItemConditionType LAMBDA = register("code_execution_single_entity_condition", SingleEntityLambdaCondition.CODEC);
    public static final LootItemConditionType HEALTH = register("health", HealthCondition.CODEC);
    public static final LootItemConditionType RELATIVE_HEALTH = register("relative_health", RelativeHealthCondition.CODEC);
    public static final LootItemConditionType DIMENSION = register("dimension", DimensionCondition.CODEC);
    public static final LootItemConditionType TICK_MODULO = register("every_x_ticks", TickModuloCondition.CODEC);
    public static final LootItemConditionType EXPOSED_TO_SKY = register("exposed_to_sky", ExposedToSkyCondition.CODEC);
    public static final LootItemConditionType EXPOSED_TO_SUN = register("exposed_to_sun", ExposedToSunCondition.CODEC);
    public static final LootItemConditionType EXPOSED_TO_MOON = register("exposed_to_moon", ExposedToMoonCondition.CODEC);
    public static final LootItemConditionType APPLY_TO_ITEM = register("apply_to_item", ApplyToItemCondition.CODEC);
    public static final LootItemConditionType APPLY_TO_ATTACKER = register("apply_to_attacker", ApplyToAttackerCondition.CODEC);

    private static LootItemConditionType register (String name, MapCodec<? extends LootItemCondition> codec) {
        return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, CPCMain.identifier(name), new LootItemConditionType(codec));
    }

    public static void init () {}
}
