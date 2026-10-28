package com.provismet.CombatPlusCore.utility;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.api.enchantment.effect.CombatPlusDataComponentEntityEffect;
import com.provismet.CombatPlusCore.api.enchantment.effect.CombatPlusEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.api.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.CombatPlusCore.api.enchantment.effect.singleEntity.CodeExecutionSingleEntityEffect;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class CombatPlusRegistries {
    public static final Registry<CodeExecutionSingleEntityEffect.Lambda> SINGLE_ENTITY_LAMBDA = create(CombatPlusRegistryKeys.SINGLE_ENTITY_LAMBDA);
    public static final Registry<CodeExecutionDoubleEntityEffect.Lambda> DOUBLE_ENTITY_LAMBDA = create(CombatPlusRegistryKeys.DOUBLE_ENTITY_LAMBDA);
    public static final Registry<Predicate<Entity>> SINGLE_ENTITY_LAMBDA_CONDITION = create(CombatPlusRegistryKeys.SINGLE_ENTITY_CONDITION);
    public static final Registry<Predicate<Tuple<Entity,Entity>>> DOUBLE_ENTITY_LAMBDA_CONDITION = create(CombatPlusRegistryKeys.DOUBLE_ENTITY_CONDITION);
    public static final Registry<Predicate<ItemStack>> ITEM_LAMBDA_CONDITION = create(CombatPlusRegistryKeys.ITEM_CONDITION);

    public static final Registry<MapCodec<? extends CombatPlusEnchantmentEntityEffect>> ENCHANTMENT_DUAL_ENTITY_EFFECT_TYPE = create(CombatPlusRegistryKeys.ENCHANTMENT_DUAL_ENTITY_EFFECT_TYPE);
    public static final Registry<MapCodec<? extends CombatPlusDataComponentEntityEffect<?>>> ENCHANTMENT_DATA_COMPONENT_EFFECT_TYPE = create(CombatPlusRegistryKeys.ENCHANTMENT_DATA_COMPONENT_EFFECT_TYPE);

    private static <T> Registry<T> create (ResourceKey<Registry<T>> key) {
        return FabricRegistryBuilder.create(key).buildAndRegister();
    }

    public static void init () {}
}
