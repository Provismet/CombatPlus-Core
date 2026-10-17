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
 * Compares the entity's health percentage (health / maxHealth) against a value.
 *
 * @param comparison The comparison to evaluate.
 * @param value The value to compare against.
 */
public record RelativeHealthCondition (CPCLootContext.Comparison comparison, LevelBasedValue value) implements SingleEntityCondition {
    public static final MapCodec<RelativeHealthCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(CPCLootContext.Comparison.CODEC.fieldOf("comparison").forGetter(RelativeHealthCondition::comparison), LevelBasedValue.CODEC.fieldOf("value").forGetter(RelativeHealthCondition::value)).apply(instance, RelativeHealthCondition::new));

    @Override
    public LootItemConditionType getType () {
        return SingleEntityLootConditionTypes.RELATIVE_HEALTH;
    }

    @Override
    public boolean test (LootContext lootContext) {
        if (lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof LivingEntity livingEntity) {
            float percentage = livingEntity.getHealth() / livingEntity.getMaxHealth();
            float compareTo = this.value.calculate(lootContext.getOptionalParameter(LootContextParams.ENCHANTMENT_LEVEL));
            return this.comparison.compare(percentage, compareTo);
        }
        return false;
    }

    public static SingleEntityCondition.Builder builder (CPCLootContext.Comparison comparison, LevelBasedValue value) {
        return () -> new RelativeHealthCondition(comparison, value);
    }
}
