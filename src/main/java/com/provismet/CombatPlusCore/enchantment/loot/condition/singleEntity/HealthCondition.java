package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContext;
import com.provismet.CombatPlusCore.registries.SingleEntityLootConditionTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

/**
 * Compares the entity's health against a provided value.
 *
 * @param comparison The type of comparison to evaluate.
 * @param value The value to compare against.
 */
public record HealthCondition (CPCLootContext.Comparison comparison, LevelBasedValue value) implements SingleEntityCondition {
    public static final MapCodec<HealthCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(CPCLootContext.Comparison.CODEC.fieldOf("comparison").forGetter(HealthCondition::comparison), LevelBasedValue.CODEC.fieldOf("value").forGetter(HealthCondition::value)).apply(instance, HealthCondition::new));

    @Override
    public LootItemConditionType getType () {
        return SingleEntityLootConditionTypes.HEALTH;
    }

    @Override
    public boolean test (LootContext lootContext) {
        if (lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof LivingEntity living) {
            return this.comparison.compare(living.getHealth(), value.calculate(lootContext.getOptionalParameter(LootContextParams.ENCHANTMENT_LEVEL)));
        }
        return false;
    }

    public static SingleEntityCondition.Builder builder (CPCLootContext.Comparison comparison, LevelBasedValue value) {
        return () -> new HealthCondition(comparison, value);
    }
}
