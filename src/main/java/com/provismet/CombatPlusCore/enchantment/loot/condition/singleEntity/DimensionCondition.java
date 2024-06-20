package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.registries.CPCSingleEntityLootConditionTypes;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

/**
 * Checks what dimension the entity is in.
 *
 * @param dimension The identifier of the dimension.
 */
public record DimensionCondition (Identifier dimension) implements SingleEntityCondition {
    public static final MapCodec<DimensionCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Identifier.CODEC.fieldOf("dimension_key").forGetter(DimensionCondition::dimension)).apply(instance, DimensionCondition::new));

    @Override
    public LootConditionType getType () {
        return CPCSingleEntityLootConditionTypes.DIMENSION;
    }

    @Override
    public boolean test (LootContext lootContext) {
        ServerWorld world = lootContext.getWorld();
        return world.getDimensionEntry().matchesId(this.dimension);
    }

    public static SingleEntityCondition.Builder builder (Identifier dimension) {
        return () -> new DimensionCondition(dimension);
    }
}
