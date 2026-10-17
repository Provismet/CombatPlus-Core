package com.provismet.CombatPlusCore.enchantment.loot.context;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

/**
 * Utility class for Combat+ loot contexts.
 *
 * @see com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition
 */
public final class CPCLootContext {
    public static LootContext createDoubleEntity (ServerLevel world, int level, Entity attacker, Entity target, ItemStack heldItem) {
        LootParams paramSet = new LootParams.Builder(world)
            .withParameter(LootContextParams.THIS_ENTITY, attacker)
            .withParameter(LootContextParams.ATTACKING_ENTITY, attacker)
            .withParameter(CPCLootContextParameters.TARGET_ENTITY, target)
            .withParameter(LootContextParams.ENCHANTMENT_LEVEL, level)
            .withParameter(LootContextParams.TOOL, heldItem)
            .withParameter(LootContextParams.ORIGIN, attacker.position())
            .create(CPCLootContextTypes.DOUBLE_ENTITY);

        return new LootContext.Builder(paramSet).create(Optional.empty());
    }

    public static LootContext createReversedDoubleEntity (ServerLevel world, int level, Entity target, Entity attacker, ItemStack heldItem) {
        LootParams paramSet = new LootParams.Builder(world)
            .withParameter(LootContextParams.THIS_ENTITY, target)
            .withParameter(LootContextParams.ATTACKING_ENTITY, attacker)
            .withParameter(CPCLootContextParameters.TARGET_ENTITY, target)
            .withParameter(LootContextParams.ENCHANTMENT_LEVEL, level)
            .withParameter(LootContextParams.TOOL, heldItem)
            .withParameter(LootContextParams.ORIGIN, target.position())
            .create(CPCLootContextTypes.DOUBLE_ENTITY);

        return new LootContext.Builder(paramSet).create(Optional.empty());
    }

    public static LootContext createSingleEntity (ServerLevel world, int level, Entity entity, @Nullable ItemStack heldItem) {
        ItemStack weaponStack = heldItem == null ? entity.getWeaponItem() : heldItem;
        LootParams paramSet = new LootParams.Builder(world)
            .withParameter(LootContextParams.THIS_ENTITY, entity)
            .withParameter(LootContextParams.ENCHANTMENT_LEVEL, level)
            .withParameter(LootContextParams.ORIGIN, entity.position())
            .withParameter(LootContextParams.TOOL, weaponStack)
            .create(CPCLootContextTypes.SINGLE_ENTITY);

        return new LootContext.Builder(paramSet).create(Optional.empty());
    }

    public enum Comparison implements StringRepresentable {
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

        public static final StringRepresentable.EnumCodec<CPCLootContext.Comparison> CODEC;
        private final String type;

        Comparison (String type) {
            this.type = type;
        }

        public abstract boolean compare (float left, float right);

        @Override
        public String getSerializedName() {
            return this.type;
        }

        static {
            CODEC = StringRepresentable.fromEnum(CPCLootContext.Comparison::values);
        }
    }

    public enum EntityTarget implements StringRepresentable {
        THIS("this", LootContextParams.THIS_ENTITY),
        ATTACKER("attacker", LootContextParams.ATTACKING_ENTITY),
        TARGET("target_entity", CPCLootContextParameters.TARGET_ENTITY);

        public static final StringRepresentable.EnumCodec<CPCLootContext.EntityTarget> CODEC;
        private final String type;
        private final ContextKey<? extends Entity> parameter;

        private EntityTarget (String type, ContextKey<? extends Entity> parameter) {
            this.type = type;
            this.parameter = parameter;
        }

        public ContextKey<? extends Entity> getParameter() {
            return this.parameter;
        }

        public static CPCLootContext.EntityTarget fromString (String type) {
            CPCLootContext.EntityTarget entityTarget = CODEC.byName(type);
            if (entityTarget != null) {
                return entityTarget;
            }
            throw new IllegalArgumentException("Invalid entity target " + type);
        }

        @Override
        public String getSerializedName() {
            return this.type;
        }

        static {
            CODEC = StringRepresentable.fromEnum(CPCLootContext.EntityTarget::values);
        }
    }
}
