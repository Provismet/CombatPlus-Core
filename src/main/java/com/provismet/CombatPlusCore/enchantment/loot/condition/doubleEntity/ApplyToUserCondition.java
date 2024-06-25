package com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.DoubleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContext;
import com.provismet.CombatPlusCore.registries.DoubleEntityLootConditionTypes;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

/**
 * Applies a single condition to the attacker.
 */
public class ApplyToUserCondition extends AbstractSingleWrapperCondition {
    public static final MapCodec<ApplyToUserCondition> CODEC = ApplyToUserCondition.createCodec(ApplyToUserCondition::new);

    protected ApplyToUserCondition (LootCondition condition) {
        super(condition);
    }

    @Override
    public LootConditionType getType () {
        return DoubleEntityLootConditionTypes.APPLY_TO_USER;
    }

    @Override
    public boolean test (LootContext lootContext) {
        LootContext userContext = CPCLootContext.createSingleEntity(
            lootContext.getWorld(),
            lootContext.get(LootContextParameters.ENCHANTMENT_LEVEL),
            lootContext.get(LootContextParameters.THIS_ENTITY),
            lootContext.get(LootContextParameters.TOOL)
        );
        return this.condition.test(userContext);
    }

    public static DoubleEntityCondition.Builder builder (EntityPropertiesLootCondition propertiesCondition) {
        return () -> new ApplyToUserCondition(propertiesCondition);
    }

    public static DoubleEntityCondition.Builder builder (SingleEntityCondition condition) {
        return () -> new ApplyToUserCondition(condition);
    }

    public static DoubleEntityCondition.Builder builder (SingleEntityCondition.Builder condition) {
        return () -> new ApplyToUserCondition(condition.build());
    }

    public static DoubleEntityCondition.Builder builder (MatchToolLootCondition toolCondition) {
        return () -> new ApplyToUserCondition(toolCondition);
    }
}
