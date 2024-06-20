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
 * Compares the entity's health percentage (health / maxHealth) against a value.
 *
 * @param comparison The comparison to evaluate.
 * @param value The value to compare against.
 */
public record RelativeHealthCondition (CPCLootContext.Comparison comparison, EnchantmentLevelBasedValue value) implements SingleEntityCondition {
    public static final MapCodec<RelativeHealthCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(CPCLootContext.Comparison.CODEC.fieldOf("comparison").forGetter(RelativeHealthCondition::comparison), EnchantmentLevelBasedValue.CODEC.fieldOf("value").forGetter(RelativeHealthCondition::value)).apply(instance, RelativeHealthCondition::new));

    @Override
    public LootConditionType getType () {
        return CPCSingleEntityLootConditionTypes.RELATIVE_HEALTH;
    }

    @Override
    public boolean test (LootContext lootContext) {
        if (lootContext.get(LootContextParameters.THIS_ENTITY) instanceof LivingEntity livingEntity) {
            float percentage = livingEntity.getHealth() / livingEntity.getMaxHealth();
            float compareTo = this.value.getValue(lootContext.get(LootContextParameters.ENCHANTMENT_LEVEL));
            return this.comparison.compare(percentage, compareTo);
        }
        return false;
    }

    public static SingleEntityCondition.Builder builder (CPCLootContext.Comparison comparison, EnchantmentLevelBasedValue value) {
        return () -> new RelativeHealthCondition(comparison, value);
    }
}
