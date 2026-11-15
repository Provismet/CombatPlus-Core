package com.provismet.combatplus.registries;

import com.provismet.combatplus.CombatPlusCoreMain;
import com.provismet.combatplus.api.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.combatplus.api.enchantment.effect.singleEntity.CodeExecutionSingleEntityEffect;
import com.provismet.combatplus.utility.CombatPlusRegistries;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface LambdaRegistry {
    static void register () {
        registerSingles();
        registerDoubles();
        registerSingleConditions();
        registerDoubleConditions();
        registerItemConditions();
    }

    private static void registerSingles () {
        register("log", (_, _, _, user, _) -> CombatPlusCoreMain.LOGGER.info("Single Entity Component: {} triggered this log", user.getName().getString()));
    }

    private static void registerDoubles () {
        register("log-charged", (_, _, _, user, target, _) -> CombatPlusCoreMain.LOGGER.info("Double Entity Component: {} performed a charged attack on {}", user.getName().getString(), target.getName().getString()));
        register("log-critical", (_, _, _, user, target, _) -> CombatPlusCoreMain.LOGGER.info("Double Entity Component: {} performed a critical attack on {}", user.getName().getString(), target.getName().getString()));
        register("log-kill", (_, _, _, user, target, _) -> CombatPlusCoreMain.LOGGER.info("Double Entity Component: {} killed {}", user.getName().getString(), target.getName().getString()));
        register("log-block", ((_, _, _, user, target, _) -> CombatPlusCoreMain.LOGGER.info("Double Entity Component: {} blocked an attack from {}", user.getName().getString(), target.getName().getString())));
    }

    private static void registerSingleConditions () {
        registerSingleCondition("true", _ -> true);
        registerSingleCondition("false", _ -> false);
    }

    private static void registerDoubleConditions () {
        registerDoubleCondition("true", (_, _) -> true);
        registerDoubleCondition("false", (_, _) -> false);
    }

    private static void registerItemConditions () {
        registerItemCondition("true", _ -> true);
        registerItemCondition("false", _ -> false);
    }

    private static void register (String name, CodeExecutionSingleEntityEffect.Lambda lambda) {
        Registry.register(CombatPlusRegistries.SINGLE_ENTITY_LAMBDA, CombatPlusCoreMain.identifier(name), lambda);
    }

    private static void register (String name, CodeExecutionDoubleEntityEffect.Lambda lambda) {
        Registry.register(CombatPlusRegistries.DOUBLE_ENTITY_LAMBDA, CombatPlusCoreMain.identifier(name), lambda);
    }

    private static void registerSingleCondition (String name, Predicate<Entity> predicate) {
        Registry.register(CombatPlusRegistries.SINGLE_ENTITY_LAMBDA_CONDITION, CombatPlusCoreMain.identifier(name), predicate);
    }

    private static void registerDoubleCondition (String name, BiPredicate<Entity,Entity> predicate) {
        Registry.register(CombatPlusRegistries.DOUBLE_ENTITY_LAMBDA_CONDITION, CombatPlusCoreMain.identifier(name), predicate);
    }

    private static void registerItemCondition (String name, Predicate<ItemStack> predicate) {
        Registry.register(CombatPlusRegistries.ITEM_LAMBDA_CONDITION, CombatPlusCoreMain.identifier(name), predicate);
    }
}
