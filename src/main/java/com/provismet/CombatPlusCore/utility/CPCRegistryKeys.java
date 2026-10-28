package com.provismet.CombatPlusCore.utility;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.api.enchantment.effect.CPCDataComponentEntityEffect;
import com.provismet.CombatPlusCore.api.enchantment.effect.CPCEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.api.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.CombatPlusCore.api.enchantment.effect.singleEntity.CodeExecutionSingleEntityEffect;
import java.util.function.Predicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class CPCRegistryKeys {
    public static final ResourceKey<Registry<CodeExecutionSingleEntityEffect.Lambda>> SINGLE_ENTITY_LAMBDA = CPCRegistryKeys.of("single_entity_lambda");
    public static final ResourceKey<Registry<CodeExecutionDoubleEntityEffect.Lambda>> DOUBLE_ENTITY_LAMBDA = CPCRegistryKeys.of("double_entity_lambda");
    public static final ResourceKey<Registry<MapCodec<? extends CPCEnchantmentEntityEffect>>> ENCHANTMENT_DUAL_ENTITY_EFFECT_TYPE = CPCRegistryKeys.of("enchantment_dual_entity_effect_type");
    public static final ResourceKey<Registry<MapCodec<? extends CPCDataComponentEntityEffect<?>>>> ENCHANTMENT_DATA_COMPONENT_EFFECT_TYPE = CPCRegistryKeys.of("enchantment_data_component_effect_type");
    public static final ResourceKey<Registry<Predicate<Entity>>> SINGLE_ENTITY_CONDITION = CPCRegistryKeys.of("single_entity_condition");
    public static final ResourceKey<Registry<Predicate<Tuple<Entity,Entity>>>> DOUBLE_ENTITY_CONDITION = CPCRegistryKeys.of("double_entity_condition");
    public static final ResourceKey<Registry<Predicate<ItemStack>>> ITEM_CONDITION = CPCRegistryKeys.of("item_condition");

    private static <T> ResourceKey<Registry<T>> of (String id) {
        return ResourceKey.createRegistryKey(CombatPlusCoreMain.identifier(id));
    }
}
