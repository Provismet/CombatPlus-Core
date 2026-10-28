package com.provismet.CombatPlusCore.api.loot.condition.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.api.loot.condition.DoubleEntityCondition;
import com.provismet.CombatPlusCore.api.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.api.loot.context.CombatPlusLootContext;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Applies a single condition to the attacker.
 */
public class ApplyToUserCondition extends AbstractSingleWrapperCondition {
    public static final MapCodec<ApplyToUserCondition> CODEC = ApplyToUserCondition.createCodec(ApplyToUserCondition::new);

    protected ApplyToUserCondition (LootItemCondition condition) {
        super(condition);
    }

    @Override
    public boolean test (LootContext lootContext) {
        LootContext userContext = CombatPlusLootContext.createSingleEntity(
            lootContext.getLevel(),
            Objects.requireNonNullElse(lootContext.getOptionalParameter(LootContextParams.ENCHANTMENT_LEVEL), 0),
            lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY),
            lootContext.getOptionalParameter(LootContextParams.TOOL)
        );
        return this.condition.test(userContext);
    }

    public static DoubleEntityCondition.Builder builder (SingleEntityCondition.Builder combatCondition) {
        return () -> new ApplyToUserCondition(combatCondition.build());
    }

    public static DoubleEntityCondition.Builder builder (SingleEntityCondition combatCondition) {
        return () -> new ApplyToUserCondition(combatCondition);
    }

    public static DoubleEntityCondition.Builder builder (LootItemCondition.Builder condition) {
        return () -> new ApplyToUserCondition(condition.build());
    }

    public static DoubleEntityCondition.Builder builder (LootItemCondition condition) {
        return () -> new ApplyToUserCondition(condition);
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
