package com.provismet.CombatPlusCore.registries;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.enchantment.effect.CPCDataComponentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContextTypes;
import java.util.List;
import java.util.function.UnaryOperator;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public abstract class CPCEnchantmentComponentTypes {
    public static final DataComponentType<List<ConditionalEffect<CPCEnchantmentEntityEffect>>> POST_CHARGED_ATTACK = register("post_charged_attack", builder -> builder.persistent(ConditionalEffect.codec(CPCEnchantmentEntityEffect.CODEC, CPCLootContextTypes.DOUBLE_ENTITY).listOf()));
    public static final DataComponentType<List<ConditionalEffect<CPCEnchantmentEntityEffect>>> POST_CRITICAL_ATTACK = register("post_critical_attack", builder -> builder.persistent(ConditionalEffect.codec(CPCEnchantmentEntityEffect.CODEC, CPCLootContextTypes.DOUBLE_ENTITY).listOf()));
    public static final DataComponentType<List<ConditionalEffect<CPCEnchantmentEntityEffect>>> POST_KILL = register("post_kill", builder -> builder.persistent(ConditionalEffect.codec(CPCEnchantmentEntityEffect.CODEC, CPCLootContextTypes.DOUBLE_ENTITY).listOf()));
    public static final DataComponentType<List<ConditionalEffect<CPCEnchantmentEntityEffect>>> POST_BLOCK = register("post_block", builder -> builder.persistent(ConditionalEffect.codec(CPCEnchantmentEntityEffect.CODEC, CPCLootContextTypes.DOUBLE_ENTITY).listOf()));
    public static final DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>> BONUS_DAMAGE = register("bonus_damage", builder -> builder.persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE).listOf()));
    public static final DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>> GAMERULE_DAMAGE = register("gamerule_damage", builder -> builder.persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE).listOf()));
    public static final DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>> MODIFY_COOLDOWN = register("modify_cooldown", builder -> builder.persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ENTITY).listOf()));
    public static final DataComponentType<List<ConditionalEffect<CPCDataComponentEntityEffect<?>>>> DATA_COMPONENT = register("data_component", builder -> builder.persistent(ConditionalEffect.codec(CPCDataComponentEntityEffect.CODEC, LootContextParamSets.ENCHANTED_ITEM).listOf()));

    public static void init () {}

    private static <T> DataComponentType<T> register (String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, CPCMain.identifier(name), (builderOperator.apply(DataComponentType.builder())).build());
    }
}
