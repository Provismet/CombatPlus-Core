package com.provismet.CombatPlusCore.registries;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.CodeExecutionSingleEntityEffect;
import com.provismet.CombatPlusCore.utility.CPCRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.util.Pair;

import java.util.function.Predicate;

public class LambdaRegistry {
    public static void register () {
        registerSingles();
        registerDoubles();
        registerSingleConditions();
        registerDoubleConditions();
        registerItemConditions();
    }

    private static void registerSingles () {
        register("log", (world, level, context, user, pos) -> CPCMain.LOGGER.info("Single Entity Component: {} triggered this log", user.getName().getString()));
    }

    private static void registerDoubles () {
        register("log-charged", (world, level, context, user, target, pos) -> CPCMain.LOGGER.info("Double Entity Component: {} performed a charged attack on {}", user.getName().getString(), target.getName().getString()));
        register("log-critical", (world, level, context, user, target, pos) -> CPCMain.LOGGER.info("Double Entity Component: {} performed a critical attack on {}", user.getName().getString(), target.getName().getString()));
        register("log-kill", (world, level, context, user, target, pos) -> CPCMain.LOGGER.info("Double Entity Component: {} killed {}", user.getName().getString(), target.getName().getString()));
    }

    private static void registerSingleConditions () {
        registerSingleCondition("true", entity -> true);
        registerSingleCondition("false", entity -> false);
    }

    private static void registerDoubleConditions () {
        registerDoubleCondition("true", pair -> true);
        registerDoubleCondition("false", pair -> false);
    }

    private static void registerItemConditions () {
        registerItemCondition("true", item -> true);
        registerItemCondition("false", item -> false);
    }

    private static void register (String name, CodeExecutionSingleEntityEffect.Lambda lambda) {
        Registry.register(CPCRegistries.SINGLE_ENTITY_LAMBDA, CPCMain.identifier(name), lambda);
    }

    private static void register (String name, CodeExecutionDoubleEntityEffect.Lambda lambda) {
        Registry.register(CPCRegistries.DOUBLE_ENTITY_LAMBDA, CPCMain.identifier(name), lambda);
    }

    private static void registerSingleCondition (String name, Predicate<Entity> predicate) {
        Registry.register(CPCRegistries.SINGLE_ENTITY_LAMBDA_CONDITION, CPCMain.identifier(name), predicate);
    }

    private static void registerDoubleCondition (String name, Predicate<Pair<Entity,Entity>> predicate) {
        Registry.register(CPCRegistries.DOUBLE_ENTITY_LAMBDA_CONDITION, CPCMain.identifier(name), predicate);
    }

    private static void registerItemCondition (String name, Predicate<ItemStack> predicate) {
        Registry.register(CPCRegistries.ITEM_LAMBDA_CONDITION, CPCMain.identifier(name), predicate);
    }
}
