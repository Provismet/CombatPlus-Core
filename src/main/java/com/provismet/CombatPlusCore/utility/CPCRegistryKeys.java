package com.provismet.CombatPlusCore.utility;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.effect.CPCDataComponentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.CodeExecutionSingleEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Pair;

import java.util.function.Predicate;

public class CPCRegistryKeys {
    public static final RegistryKey<Registry<CodeExecutionSingleEntityEffect.Lambda>> SINGLE_ENTITY_LAMBDA = CPCRegistryKeys.of("single_entity_lambda");
    public static final RegistryKey<Registry<CodeExecutionDoubleEntityEffect.Lambda>> DOUBLE_ENTITY_LAMBDA = CPCRegistryKeys.of("double_entity_lambda");
    public static final RegistryKey<Registry<MapCodec<? extends CPCEnchantmentEntityEffect>>> ENCHANTMENT_DUAL_ENTITY_EFFECT_TYPE = CPCRegistryKeys.of("enchantment_dual_entity_effect_type");
    public static final RegistryKey<Registry<MapCodec<? extends CPCDataComponentEntityEffect<?>>>> ENCHANTMENT_DATA_COMPONENT_EFFECT_TYPE = CPCRegistryKeys.of("enchantment_data_component_effect_type");
    public static final RegistryKey<Registry<Predicate<Entity>>> SINGLE_ENTITY_CONDITION = CPCRegistryKeys.of("single_entity_condition");
    public static final RegistryKey<Registry<Predicate<Pair<Entity,Entity>>>> DOUBLE_ENTITY_CONDITION = CPCRegistryKeys.of("double_entity_condition");
    public static final RegistryKey<Registry<Predicate<ItemStack>>> ITEM_CONDITION = CPCRegistryKeys.of("item_condition");

    private static <T> RegistryKey<Registry<T>> of (String id) {
        return RegistryKey.ofRegistry(CPCMain.identifier(id));
    }
}
