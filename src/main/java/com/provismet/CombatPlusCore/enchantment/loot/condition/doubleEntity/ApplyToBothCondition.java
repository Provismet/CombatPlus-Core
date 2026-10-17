package com.provismet.CombatPlusCore.enchantment.loot.condition.doubleEntity;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.DoubleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContext;
import com.provismet.CombatPlusCore.enchantment.loot.context.CPCLootContextParameters;
import com.provismet.CombatPlusCore.registries.DoubleEntityLootConditionTypes;
import java.util.Set;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

/**
 * Applies a single condition to both the attacker and the target.
 */
public class ApplyToBothCondition extends AbstractSingleWrapperCondition {
    public static final MapCodec<ApplyToBothCondition> CODEC = ApplyToBothCondition.createCodec(ApplyToBothCondition::new);

    protected ApplyToBothCondition (LootItemCondition condition) {
        super(condition);
    }

    @Override
    public Set<ContextKey<?>> getReferencedContextParams() {
        return ImmutableSet.of(
            LootContextParams.THIS_ENTITY,
            CPCLootContextParameters.TARGET_ENTITY,
            LootContextParams.ATTACKING_ENTITY,
            LootContextParams.ORIGIN,
            LootContextParams.ENCHANTMENT_LEVEL
        );
    }

    @Override
    public LootItemConditionType getType () {
        return DoubleEntityLootConditionTypes.APPLY_TO_BOTH;
    }

    @Override
    public boolean test (LootContext lootContext) {
        Entity attacker = lootContext.getOptionalParameter(LootContextParams.ATTACKING_ENTITY);
        Entity target = lootContext.getOptionalParameter(CPCLootContextParameters.TARGET_ENTITY);
        Integer level = lootContext.getOptionalParameter(LootContextParams.ENCHANTMENT_LEVEL);
        ItemStack item = lootContext.getOptionalParameter(LootContextParams.TOOL);

        LootContext attackerContext = CPCLootContext.createSingleEntity(lootContext.getLevel(), level, attacker, item);
        LootContext targetContext = CPCLootContext.createSingleEntity(lootContext.getLevel(), level, target, null);

        return this.condition.test(attackerContext) && this.condition.test(targetContext);
    }

    public static DoubleEntityCondition.Builder builder (SingleEntityCondition.Builder combatCondition) {
        return () -> new ApplyToBothCondition(combatCondition.build());
    }

    public static DoubleEntityCondition.Builder builder (SingleEntityCondition combatCondition) {
        return () -> new ApplyToBothCondition(combatCondition);
    }

    public static DoubleEntityCondition.Builder builder (LootItemCondition.Builder condition) {
        return () -> new ApplyToBothCondition(condition.build());
    }

    public static DoubleEntityCondition.Builder builder (LootItemCondition condition) {
        return () -> new ApplyToBothCondition(condition);
    }
}
