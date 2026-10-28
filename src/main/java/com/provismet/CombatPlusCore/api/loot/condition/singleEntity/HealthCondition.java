package com.provismet.CombatPlusCore.api.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.api.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.api.loot.context.CombatPlusLootContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Compares the entity's health against a provided value.
 *
 * @param comparison The type of comparison to evaluate.
 * @param value The value to compare against.
 */
public record HealthCondition (CombatPlusLootContext.Comparison comparison, LevelBasedValue value) implements SingleEntityCondition {
    public static final MapCodec<HealthCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(CombatPlusLootContext.Comparison.CODEC.fieldOf("comparison").forGetter(HealthCondition::comparison), LevelBasedValue.CODEC.fieldOf("value").forGetter(HealthCondition::value)).apply(instance, HealthCondition::new));

    @Override
    public boolean test (LootContext lootContext) {
        if (lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof LivingEntity living) {
            int enchantmentLevel = Objects.requireNonNullElse(lootContext.getOptionalParameter(LootContextParams.ENCHANTMENT_LEVEL), 0);
            return this.comparison.compare(living.getHealth(), value.calculate(enchantmentLevel));
        }
        return false;
    }

    public static SingleEntityCondition.Builder builder (CombatPlusLootContext.Comparison comparison, LevelBasedValue value) {
        return () -> new HealthCondition(comparison, value);
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
