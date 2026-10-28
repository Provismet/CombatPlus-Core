package com.provismet.CombatPlusCore.registries;

import com.mojang.serialization.Codec;
import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.enchantment.effect.CPCDataComponentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.effect.CPCEnchantmentEntityEffect;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContextTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.Validatable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.function.UnaryOperator;

public abstract class CPCEnchantmentComponentTypes {
    public static final DataComponentType<List<ConditionalEffect<CPCEnchantmentEntityEffect>>> POST_CHARGED_ATTACK = register("post_charged_attack", builder -> builder.persistent(conditionalCodec(CPCEnchantmentEntityEffect.CODEC, CPCLootContextTypes.DOUBLE_ENTITY)));
    public static final DataComponentType<List<ConditionalEffect<CPCEnchantmentEntityEffect>>> POST_CRITICAL_ATTACK = register("post_critical_attack", builder -> builder.persistent(conditionalCodec(CPCEnchantmentEntityEffect.CODEC, CPCLootContextTypes.DOUBLE_ENTITY)));
    public static final DataComponentType<List<ConditionalEffect<CPCEnchantmentEntityEffect>>> POST_KILL = register("post_kill", builder -> builder.persistent(conditionalCodec(CPCEnchantmentEntityEffect.CODEC, CPCLootContextTypes.DOUBLE_ENTITY)));
    public static final DataComponentType<List<ConditionalEffect<CPCEnchantmentEntityEffect>>> POST_BLOCK = register("post_block", builder -> builder.persistent(conditionalCodec(CPCEnchantmentEntityEffect.CODEC, CPCLootContextTypes.DOUBLE_ENTITY)));
    public static final DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>> BONUS_DAMAGE = register("bonus_damage", builder -> builder.persistent(conditionalCodec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE)));
    public static final DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>> GAMERULE_DAMAGE = register("gamerule_damage", builder -> builder.persistent(conditionalCodec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE)));
    public static final DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>> MODIFY_COOLDOWN = register("modify_cooldown", builder -> builder.persistent(conditionalCodec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ENTITY)));
    public static final DataComponentType<List<ConditionalEffect<CPCDataComponentEntityEffect<?>>>> DATA_COMPONENT = register("data_component", builder -> builder.persistent(conditionalCodec(CPCDataComponentEntityEffect.CODEC, LootContextParamSets.ENCHANTED_ITEM)));

    public static void init () {}

    private static <T> DataComponentType<T> register (String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, CombatPlusCoreMain.identifier(name), (builderOperator.apply(DataComponentType.builder())).build());
    }

    private static <T extends Validatable> Codec<List<T>> validatedListCodec (Codec<T> elementCodec, ContextKeySet paramSet) {
        return elementCodec.listOf().validate(Validatable.listValidatorForContext(paramSet));
    }

    private static <T> Codec<List<ConditionalEffect<T>>> conditionalCodec (Codec<T> codec, ContextKeySet params) {
        return validatedListCodec(ConditionalEffect.codec(codec), params);
    }
}
