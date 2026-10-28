package com.provismet.CombatPlusCore.api.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.provismet.CombatPlusCore.api.loot.condition.SingleEntityCondition;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

/**
 * Checks what dimension the entity is in.
 *
 * @param dimension The identifier of the dimension.
 */
public record DimensionCondition (Identifier dimension) implements SingleEntityCondition {
    public static final MapCodec<DimensionCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Identifier.CODEC.fieldOf("dimension_key").forGetter(DimensionCondition::dimension)).apply(instance, DimensionCondition::new));

    @Override
    public boolean test (LootContext lootContext) {
        ServerLevel world = lootContext.getLevel();
        return world.dimensionTypeRegistration().is(this.dimension);
    }

    public static SingleEntityCondition.Builder builder (Identifier dimension) {
        return () -> new DimensionCondition(dimension);
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
