package com.provismet.CombatPlusCore.api.loot.condition.doubleEntity;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.api.loot.condition.DoubleEntityCondition;
import com.provismet.CombatPlusCore.api.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.api.loot.context.CPCLootContext;
import com.provismet.CombatPlusCore.api.loot.context.CPCLootContextParameters;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

/**
 * Applies a single condition to both the attacker and the target.
 */
public class ApplyToBothCondition extends AbstractSingleWrapperCondition {
    public static final MapCodec<ApplyToBothCondition> CODEC = ApplyToBothCondition.createCodec(ApplyToBothCondition::new);

    protected ApplyToBothCondition (LootItemCondition condition) {
        super(condition);
    }

    @Override
    @NotNull
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
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }

    @Override
    public boolean test (LootContext lootContext) {
        Entity attacker = lootContext.getOptionalParameter(LootContextParams.ATTACKING_ENTITY);
        Entity target = lootContext.getOptionalParameter(CPCLootContextParameters.TARGET_ENTITY);
        int level = Objects.requireNonNullElse(lootContext.getOptionalParameter(LootContextParams.ENCHANTMENT_LEVEL), 0);
        ItemInstance item = lootContext.getOptionalParameter(LootContextParams.TOOL);

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
