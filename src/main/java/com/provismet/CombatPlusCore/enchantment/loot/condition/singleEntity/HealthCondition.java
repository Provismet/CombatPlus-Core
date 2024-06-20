package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContext;
import com.provismet.CombatPlusCore.registries.CPCSingleEntityLootConditionTypes;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

/**
 * Compares the entity's health against a provided value.
 *
 * @param comparison The type of comparison to evaluate.
 * @param value The value to compare against.
 */
public record HealthCondition (CPCLootContext.Comparison comparison, EnchantmentLevelBasedValue value) implements SingleEntityCondition {
    public static final MapCodec<HealthCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(CPCLootContext.Comparison.CODEC.fieldOf("comparison").forGetter(HealthCondition::comparison), EnchantmentLevelBasedValue.CODEC.fieldOf("value").forGetter(HealthCondition::value)).apply(instance, HealthCondition::new));

    @Override
    public LootConditionType getType () {
        return CPCSingleEntityLootConditionTypes.HEALTH;
    }

    @Override
    public boolean test (LootContext lootContext) {
        if (lootContext.get(LootContextParameters.THIS_ENTITY) instanceof LivingEntity living) {
            return this.comparison.compare(living.getHealth(), value.getValue(lootContext.get(LootContextParameters.ENCHANTMENT_LEVEL)));
        }
        return false;
    }

    public static SingleEntityCondition.Builder builder (CPCLootContext.Comparison comparison, EnchantmentLevelBasedValue value) {
        return () -> new HealthCondition(comparison, value);
    }
}
