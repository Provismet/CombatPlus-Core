package com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.DoubleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContext;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContextParameters;
import com.provismet.CombatPlusCore.registries.DoubleEntityLootConditionTypes;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

/**
 * Applies a single condition to the target.
 */
public class ApplyToTargetCondition extends AbstractSingleWrapperCondition {
    public static final MapCodec<ApplyToTargetCondition> CODEC = ApplyToTargetCondition.createCodec(ApplyToTargetCondition::new);

    protected ApplyToTargetCondition (LootCondition condition) {
        super(condition);
    }

    @Override
    public LootConditionType getType () {
        return DoubleEntityLootConditionTypes.APPLY_TO_TARGET;
    }

    @Override
    public boolean test (LootContext lootContext) {
        LootContext targetContext = CPCLootContext.createSingleEntity(
            lootContext.getWorld(),
            lootContext.get(LootContextParameters.ENCHANTMENT_LEVEL),
            lootContext.get(CPCLootContextParameters.TARGET_ENTITY),
            null
        );
        return this.condition.test(targetContext);
    }

    public static DoubleEntityCondition.Builder builder (EntityPropertiesLootCondition propertiesCondition) {
        return () -> new ApplyToTargetCondition(propertiesCondition);
    }

    public static DoubleEntityCondition.Builder builder (SingleEntityCondition combatCondition) {
        return () -> new ApplyToTargetCondition(combatCondition);
    }

    public static DoubleEntityCondition.Builder builder (SingleEntityCondition.Builder combatCondition) {
        return () -> new ApplyToTargetCondition(combatCondition.build());
    }

    public static DoubleEntityCondition.Builder builder (MatchToolLootCondition toolCondition) {
        return () -> new ApplyToTargetCondition(toolCondition);
    }
}
