package com.provismet.CombatPlusCore.utility;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.api.enchantment.effect.CombatPlusDataComponentEntityEffect;
import com.provismet.CombatPlusCore.api.enchantment.effect.CombatPlusEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.api.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.CombatPlusCore.api.enchantment.effect.singleEntity.CodeExecutionSingleEntityEffect;
import java.util.function.Predicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class CombatPlusRegistryKeys {
    public static final ResourceKey<Registry<CodeExecutionSingleEntityEffect.Lambda>> SINGLE_ENTITY_LAMBDA = CombatPlusRegistryKeys.of("single_entity_lambda");
    public static final ResourceKey<Registry<CodeExecutionDoubleEntityEffect.Lambda>> DOUBLE_ENTITY_LAMBDA = CombatPlusRegistryKeys.of("double_entity_lambda");
    public static final ResourceKey<Registry<MapCodec<? extends CombatPlusEnchantmentEntityEffect>>> ENCHANTMENT_DUAL_ENTITY_EFFECT_TYPE = CombatPlusRegistryKeys.of("enchantment_dual_entity_effect_type");
    public static final ResourceKey<Registry<MapCodec<? extends CombatPlusDataComponentEntityEffect<?>>>> ENCHANTMENT_DATA_COMPONENT_EFFECT_TYPE = CombatPlusRegistryKeys.of("enchantment_data_component_effect_type");
    public static final ResourceKey<Registry<Predicate<Entity>>> SINGLE_ENTITY_CONDITION = CombatPlusRegistryKeys.of("single_entity_condition");
    public static final ResourceKey<Registry<Predicate<Tuple<Entity,Entity>>>> DOUBLE_ENTITY_CONDITION = CombatPlusRegistryKeys.of("double_entity_condition");
    public static final ResourceKey<Registry<Predicate<ItemStack>>> ITEM_CONDITION = CombatPlusRegistryKeys.of("item_condition");

    private static <T> ResourceKey<Registry<T>> of (String id) {
        return ResourceKey.createRegistryKey(CombatPlusCoreMain.identifier(id));
    }
}
