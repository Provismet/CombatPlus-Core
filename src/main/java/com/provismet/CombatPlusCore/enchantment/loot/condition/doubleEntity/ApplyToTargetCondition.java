package com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.DoubleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContext;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContextParameters;
import com.provismet.CombatPlusCore.registries.DoubleEntityLootConditionTypes;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

/**
 * Applies a single condition to the target.
 */
public class ApplyToTargetCondition extends AbstractSingleWrapperCondition {
    public static final MapCodec<ApplyToTargetCondition> CODEC = ApplyToTargetCondition.createCodec(ApplyToTargetCondition::new);

    protected ApplyToTargetCondition (LootItemCondition condition) {
        super(condition);
    }

    @Override
    public LootItemConditionType getType () {
        return DoubleEntityLootConditionTypes.APPLY_TO_TARGET;
    }

    @Override
    public boolean test (LootContext lootContext) {
        LootContext targetContext = CPCLootContext.createSingleEntity(
            lootContext.getLevel(),
            lootContext.getOptionalParameter(LootContextParams.ENCHANTMENT_LEVEL),
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
}
