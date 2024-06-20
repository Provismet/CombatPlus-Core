package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.registries.CPCSingleEntityLootConditionTypes;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;

public record ExposedToSun () implements SingleEntityCondition {
    public static final MapCodec<ExposedToSun> CODEC = MapCodec.unit(ExposedToSun::new);

    @Override
    public LootConditionType getType () {
        return CPCSingleEntityLootConditionTypes.EXPOSED_TO_SUN;
    }

    @Override
    public boolean test (LootContext lootContext) {
        return lootContext.getWorld().isDay() && ExposedToSky.isSkyVisible(lootContext);
    }

    public static SingleEntityCondition.Builder builder () {
        return ExposedToSun::new;
    }
}
