package com.provismet.CombatPlusCore.enchantment.loot.context;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Utility class for Combat+ loot contexts.
 *
 * @see com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition
 */
public final class CPCLootContext {
    public static LootContext createDoubleEntity (ServerWorld world, int level, Entity attacker, Entity target, ItemStack heldItem) {
        LootContextParameterSet paramSet = new LootContextParameterSet.Builder(world)
            .add(LootContextParameters.THIS_ENTITY, attacker)
            .add(LootContextParameters.ATTACKING_ENTITY, attacker)
            .add(CPCLootContextParameters.TARGET_ENTITY, target)
            .add(LootContextParameters.ENCHANTMENT_LEVEL, level)
            .add(LootContextParameters.TOOL, heldItem)
            .add(LootContextParameters.ORIGIN, attacker.getPos())
            .build(CPCLootContextTypes.DOUBLE_ENTITY);

        return new LootContext.Builder(paramSet).build(Optional.empty());
    }

    public static LootContext createReversedDoubleEntity (ServerWorld world, int level, Entity target, Entity attacker, ItemStack heldItem) {
        LootContextParameterSet paramSet = new LootContextParameterSet.Builder(world)
            .add(LootContextParameters.THIS_ENTITY, target)
            .add(LootContextParameters.ATTACKING_ENTITY, attacker)
            .add(CPCLootContextParameters.TARGET_ENTITY, target)
            .add(LootContextParameters.ENCHANTMENT_LEVEL, level)
            .add(LootContextParameters.TOOL, heldItem)
            .add(LootContextParameters.ORIGIN, target.getPos())
            .build(CPCLootContextTypes.DOUBLE_ENTITY);

        return new LootContext.Builder(paramSet).build(Optional.empty());
    }

    public static LootContext createSingleEntity (ServerWorld world, int level, Entity entity, @Nullable ItemStack heldItem) {
        ItemStack weaponStack = heldItem == null ? entity.getWeaponStack() : heldItem;
        LootContextParameterSet paramSet = new LootContextParameterSet.Builder(world)
            .add(LootContextParameters.THIS_ENTITY, entity)
            .add(LootContextParameters.ENCHANTMENT_LEVEL, level)
            .add(LootContextParameters.ORIGIN, entity.getPos())
            .add(LootContextParameters.TOOL, weaponStack)
            .build(CPCLootContextTypes.SINGLE_ENTITY);

        return new LootContext.Builder(paramSet).build(Optional.empty());
    }

    public static enum Comparison implements StringIdentifiable {
        LESS_THAN("<") {
            @Override
            public boolean compare (float left, float right) {
                return left < right;
            }
        },
        LESS_THAN_OR_EQUAL_TO("<=") {
            @Override
            public boolean compare (float left, float right) {
                return left <= right;
            }
        },
        EQUAL_TO("==") {
            @Override
            public boolean compare (float left, float right) {
                return left == right;
            }
        },
        GREATER_THAN(">") {
            @Override
            public boolean compare (float left, float right) {
                return left > right;
            }
        },
        GREATER_THAN_OR_EQUAL_TO(">=") {
            @Override
            public boolean compare (float left, float right) {
                return left >= right;
            }
        };

        public static final StringIdentifiable.EnumCodec<CPCLootContext.Comparison> CODEC;
        private final String type;

        private Comparison (String type) {
            this.type = type;
        }

        public abstract boolean compare (float left, float right);

        @Override
        public String asString() {
            return this.type;
        }

        static {
            CODEC = StringIdentifiable.createCodec(CPCLootContext.Comparison::values);
        }
    }

    public static enum EntityTarget implements StringIdentifiable {
        THIS("this", LootContextParameters.THIS_ENTITY),
        ATTACKER("attacker", LootContextParameters.ATTACKING_ENTITY),
        TARGET("target_entity", CPCLootContextParameters.TARGET_ENTITY);

        public static final StringIdentifiable.EnumCodec<CPCLootContext.EntityTarget> CODEC;
        private final String type;
        private final LootContextParameter<? extends Entity> parameter;

        private EntityTarget (String type, LootContextParameter<? extends Entity> parameter) {
            this.type = type;
            this.parameter = parameter;
        }

        public LootContextParameter<? extends Entity> getParameter() {
            return this.parameter;
        }

        public static CPCLootContext.EntityTarget fromString (String type) {
            CPCLootContext.EntityTarget entityTarget = CODEC.byId(type);
            if (entityTarget != null) {
                return entityTarget;
            }
            throw new IllegalArgumentException("Invalid entity target " + type);
        }

        @Override
        public String asString() {
            return this.type;
        }

        static {
            CODEC = StringIdentifiable.createCodec(CPCLootContext.EntityTarget::values);
        }
    }
}
