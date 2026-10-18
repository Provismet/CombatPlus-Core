package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public record ExposedToSunCondition () implements SingleEntityCondition {
    public static final MapCodec<ExposedToSunCondition> CODEC = MapCodec.unit(ExposedToSunCondition::new);

    @Override
    public boolean test (LootContext lootContext) {
        return lootContext.getLevel().isBrightOutside() && ExposedToSkyCondition.isSkyVisible(lootContext);
    }

    public static SingleEntityCondition.Builder builder () {
        return ExposedToSunCondition::new;
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
