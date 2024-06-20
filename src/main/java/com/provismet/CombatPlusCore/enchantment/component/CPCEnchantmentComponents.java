package com.provismet.CombatPlusCore.enchantment.component;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContextTypes;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.List;
import java.util.function.UnaryOperator;

public class CPCEnchantmentComponents {
    public static final ComponentType<List<EnchantmentEffectEntry<CPCEnchantmentEntityEffect>>> POST_CHARGED_ATTACK = register("post_charged_attack", builder -> builder.codec(EnchantmentEffectEntry.createCodec(CPCEnchantmentEntityEffect.CODEC, CPCLootContextTypes.DOUBLE_ENTITY).listOf()));
    public static final ComponentType<List<EnchantmentEffectEntry<CPCEnchantmentEntityEffect>>> POST_CRITICAL_ATTACK = register("post_critical_attack", builder -> builder.codec(EnchantmentEffectEntry.createCodec(CPCEnchantmentEntityEffect.CODEC, CPCLootContextTypes.DOUBLE_ENTITY).listOf()));
    public static final ComponentType<List<EnchantmentEffectEntry<CPCEnchantmentEntityEffect>>> POST_KILL = register("post_kill", builder -> builder.codec(EnchantmentEffectEntry.createCodec(CPCEnchantmentEntityEffect.CODEC, CPCLootContextTypes.DOUBLE_ENTITY).listOf()));

    public static void init () {}

    private static <T> ComponentType<T> register (String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, CPCMain.identifier(name), (builderOperator.apply(ComponentType.builder())).build());
    }
}
