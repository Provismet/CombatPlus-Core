package com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.DoubleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContext;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContextParameters;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Applies a single condition to the target.
 */
public class ApplyToTargetCondition extends AbstractSingleWrapperCondition {
    public static final MapCodec<ApplyToTargetCondition> CODEC = ApplyToTargetCondition.createCodec(ApplyToTargetCondition::new);

    protected ApplyToTargetCondition (LootItemCondition condition) {
        super(condition);
    }

    @Override
    public boolean test (LootContext lootContext) {
        LootContext targetContext = CPCLootContext.createSingleEntity(
            lootContext.getLevel(),
            Objects.requireNonNullElse(lootContext.getOptionalParameter(LootContextParams.ENCHANTMENT_LEVEL), 0),
            lootContext.getOptionalParameter(CPCLootContextParameters.TARGET_ENTITY),
            null
        );
        return this.condition.test(targetContext);
    }

    public static DoubleEntityCondition.Builder builder (SingleEntityCondition.Builder combatCondition) {
        return () -> new ApplyToTargetCondition(combatCondition.build());
    }

    public static DoubleEntityCondition.Builder builder (SingleEntityCondition combatCondition) {
        return () -> new ApplyToTargetCondition(combatCondition);
    }

    public static DoubleEntityCondition.Builder builder (LootItemCondition.Builder condition) {
        return () -> new ApplyToTargetCondition(condition.build());
    }

    public static DoubleEntityCondition.Builder builder (LootItemCondition condition) {
        return () -> new ApplyToTargetCondition(condition);
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
