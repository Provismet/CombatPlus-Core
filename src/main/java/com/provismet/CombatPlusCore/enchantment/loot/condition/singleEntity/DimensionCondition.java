package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.registries.SingleEntityLootConditionTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

/**
 * Checks what dimension the entity is in.
 *
 * @param dimension The identifier of the dimension.
 */
public record DimensionCondition (Identifier dimension) implements SingleEntityCondition {
    public static final MapCodec<DimensionCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Identifier.CODEC.fieldOf("dimension_key").forGetter(DimensionCondition::dimension)).apply(instance, DimensionCondition::new));

    @Override
    public LootItemConditionType getType () {
        return SingleEntityLootConditionTypes.DIMENSION;
    }

    @Override
    public boolean test (LootContext lootContext) {
        ServerLevel world = lootContext.getLevel();
        return world.dimensionTypeRegistration().is(this.dimension);
    }

    public static SingleEntityCondition.Builder builder (Identifier dimension) {
        return () -> new DimensionCondition(dimension);
    }
}
