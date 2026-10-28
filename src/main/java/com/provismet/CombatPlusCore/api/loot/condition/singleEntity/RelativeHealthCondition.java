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
 * Compares the entity's health percentage (health / maxHealth) against a value.
 *
 * @param comparison The comparison to evaluate.
 * @param value The value to compare against.
 */
public record RelativeHealthCondition (CombatPlusLootContext.Comparison comparison, LevelBasedValue value) implements SingleEntityCondition {
    public static final MapCodec<RelativeHealthCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        CombatPlusLootContext.Comparison.CODEC.fieldOf("comparison").forGetter(RelativeHealthCondition::comparison),
        LevelBasedValue.CODEC.fieldOf("value").forGetter(RelativeHealthCondition::value)
    ).apply(instance, RelativeHealthCondition::new));

    @Override
    public boolean test (LootContext lootContext) {
        if (lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof LivingEntity livingEntity) {
            float percentage = livingEntity.getHealth() / livingEntity.getMaxHealth();
            float compareTo = this.value.calculate(Objects.requireNonNullElse(lootContext.getParameter(LootContextParams.ENCHANTMENT_LEVEL), 0));
            return this.comparison.compare(percentage, compareTo);
        }
        return false;
    }

    public static SingleEntityCondition.Builder builder (CombatPlusLootContext.Comparison comparison, LevelBasedValue value) {
        return () -> new RelativeHealthCondition(comparison, value);
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
