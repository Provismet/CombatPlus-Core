package com.provismet.CombatPlusCore.api.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.api.loot.condition.SingleEntityCondition;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public record ExposedToMoonCondition () implements SingleEntityCondition {
    public static final MapCodec<ExposedToMoonCondition> CODEC = MapCodec.unit(ExposedToMoonCondition::new);

    @Override
    public boolean test (LootContext lootContext) {
        return lootContext.getLevel().isDarkOutside() && ExposedToSkyCondition.isSkyVisible(lootContext);
    }

    public static SingleEntityCondition.Builder builder () {
        return ExposedToMoonCondition::new;
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
