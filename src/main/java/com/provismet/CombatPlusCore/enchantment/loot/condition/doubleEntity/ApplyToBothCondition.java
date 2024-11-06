package com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.DoubleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContext;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContextParameters;
import com.provismet.CombatPlusCore.registries.DoubleEntityLootConditionTypes;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.context.ContextParameter;

import java.util.Set;

/**
 * Applies a single condition to both the attacker and the target.
 */
public class ApplyToBothCondition extends AbstractSingleWrapperCondition {
    public static final MapCodec<ApplyToBothCondition> CODEC = ApplyToBothCondition.createCodec(ApplyToBothCondition::new);

    protected ApplyToBothCondition (LootCondition condition) {
        super(condition);
    }

    @Override
    public Set<ContextParameter<?>> getAllowedParameters() {
        return ImmutableSet.of(
            LootContextParameters.THIS_ENTITY,
            CPCLootContextParameters.TARGET_ENTITY,
            LootContextParameters.ATTACKING_ENTITY,
            LootContextParameters.ORIGIN,
            LootContextParameters.ENCHANTMENT_LEVEL
        );
    }

    @Override
    public LootConditionType getType () {
        return DoubleEntityLootConditionTypes.APPLY_TO_BOTH;
    }

    @Override
    public boolean test (LootContext lootContext) {
        Entity attacker = lootContext.get(LootContextParameters.ATTACKING_ENTITY);
        Entity target = lootContext.get(CPCLootContextParameters.TARGET_ENTITY);
        Integer level = lootContext.get(LootContextParameters.ENCHANTMENT_LEVEL);
        ItemStack item = lootContext.get(LootContextParameters.TOOL);

        LootContext attackerContext = CPCLootContext.createSingleEntity(lootContext.getWorld(), level, attacker, item);
        LootContext targetContext = CPCLootContext.createSingleEntity(lootContext.getWorld(), level, target, null);

        return this.condition.test(attackerContext) && this.condition.test(targetContext);
    }

    public static DoubleEntityCondition.Builder builder (SingleEntityCondition.Builder combatCondition) {
        return () -> new ApplyToBothCondition(combatCondition.build());
    }

    public static DoubleEntityCondition.Builder builder (SingleEntityCondition combatCondition) {
        return () -> new ApplyToBothCondition(combatCondition);
    }

    public static DoubleEntityCondition.Builder builder (LootCondition.Builder condition) {
        return () -> new ApplyToBothCondition(condition.build());
    }

    public static DoubleEntityCondition.Builder builder (LootCondition condition) {
        return () -> new ApplyToBothCondition(condition);
    }
}
