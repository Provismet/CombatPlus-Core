package com.provismet.CombatPlusCore.utility;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.doubleEntity.CodeExecutionDoubleEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.singleEntity.CodeExecutionSingleEntityEffect;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Pair;

import java.util.function.Predicate;

public class CPCRegistries {
    public static final Registry<CodeExecutionSingleEntityEffect.Lambda> SINGLE_ENTITY_LAMBDA = create(CPCRegistryKeys.SINGLE_ENTITY_LAMBDA);
    public static final Registry<CodeExecutionDoubleEntityEffect.Lambda> DOUBLE_ENTITY_LAMBDA = create(CPCRegistryKeys.DOUBLE_ENTITY_LAMBDA);
    public static final Registry<Predicate<Entity>> SINGLE_ENTITY_LAMBDA_CONDITION = create(CPCRegistryKeys.SINGLE_ENTITY_CONDITION);
    public static final Registry<Predicate<Pair<Entity,Entity>>> DOUBLE_ENTITY_LAMBDA_CONDITION = create(CPCRegistryKeys.DOUBLE_ENTITY_CONDITION);
    public static final Registry<Predicate<ItemStack>> ITEM_LAMBDA_CONDITION = create(CPCRegistryKeys.ITEM_CONDITION);

    public static final Registry<MapCodec<? extends CPCEnchantmentEntityEffect>> ENCHANTMENT_DUAL_ENTITY_EFFECT_TYPE = create(CPCRegistryKeys.ENCHANTMENT_DUAL_ENTITY_EFFECT_TYPE);

    private static <T> Registry<T> create (RegistryKey<Registry<T>> key) {
        return FabricRegistryBuilder.createSimple(key).buildAndRegister();
    }

    public static void init () {}
}
