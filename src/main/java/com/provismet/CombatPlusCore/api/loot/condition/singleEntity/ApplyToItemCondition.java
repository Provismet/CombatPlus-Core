package com.provismet.CombatPlusCore.api.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.api.loot.condition.ItemCondition;
import com.provismet.CombatPlusCore.api.loot.condition.SingleEntityCondition;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Applies a condition to the item associated with this single entity condition (typically a held item).
 *
 * @param condition The condition to apply.
 */
public record ApplyToItemCondition(LootItemCondition condition) implements SingleEntityCondition {
    public static final MapCodec<ApplyToItemCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(LootItemCondition.DIRECT_CODEC.fieldOf("applied_condition").forGetter(ApplyToItemCondition::condition)).apply(instance, ApplyToItemCondition::new));

    @Override
    public boolean test (LootContext lootContext) {
        if (lootContext.getOptionalParameter(LootContextParams.TOOL) == null) return false;
        return this.condition.test(lootContext);
    }

    @Override
    @NotNull
    public Set<ContextKey<?>> getReferencedContextParams() {
        return SingleEntityCondition.getExtendedRequiredParameters();
    }

    public static SingleEntityCondition.Builder builder (ItemCondition condition) {
        return () -> new ApplyToItemCondition(condition);
    }

    public static SingleEntityCondition.Builder builder (ItemCondition.Builder condition) {
        return () -> new ApplyToItemCondition(condition.build());
    }

    public static SingleEntityCondition.Builder builder (LootItemCondition condition) {
        return () -> new ApplyToItemCondition((condition));
    }

    public static SingleEntityCondition.Builder builder (LootItemCondition.Builder condition) {
        return () -> new ApplyToItemCondition((condition.build()));
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
