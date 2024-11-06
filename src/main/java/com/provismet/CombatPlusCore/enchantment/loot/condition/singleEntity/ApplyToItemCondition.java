package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.ItemCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.registries.SingleEntityLootConditionTypes;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.context.ContextParameter;

import java.util.Set;

/**
 * Applies a condition to the item associated with this single entity condition (typically a held item).
 *
 * @param condition The condition to apply.
 */
public record ApplyToItemCondition(LootCondition condition) implements SingleEntityCondition {
    public static final MapCodec<ApplyToItemCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(LootCondition.CODEC.fieldOf("applied_condition").forGetter(ApplyToItemCondition::condition)).apply(instance, ApplyToItemCondition::new));

    @Override
    public LootConditionType getType () {
        return SingleEntityLootConditionTypes.APPLY_TO_ITEM;
    }

    @Override
    public boolean test (LootContext lootContext) {
        if (lootContext.get(LootContextParameters.TOOL) == null) return false;
        return this.condition.test(lootContext);
    }

    @Override
    public Set<ContextParameter<?>> getAllowedParameters() {
        return SingleEntityCondition.getExtendedRequiredParameters();
    }

    public static SingleEntityCondition.Builder builder (ItemCondition condition) {
        return () -> new ApplyToItemCondition(condition);
    }

    public static SingleEntityCondition.Builder builder (ItemCondition.Builder condition) {
        return () -> new ApplyToItemCondition(condition.build());
    }

    public static SingleEntityCondition.Builder builder (LootCondition condition) {
        return () -> new ApplyToItemCondition((condition));
    }

    public static SingleEntityCondition.Builder builder (LootCondition.Builder condition) {
        return () -> new ApplyToItemCondition((condition.build()));
    }
}
