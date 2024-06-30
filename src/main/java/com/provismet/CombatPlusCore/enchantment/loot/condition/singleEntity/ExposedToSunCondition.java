package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.registries.SingleEntityLootConditionTypes;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;

public record ExposedToSunCondition () implements SingleEntityCondition {
    public static final MapCodec<ExposedToSunCondition> CODEC = MapCodec.unit(ExposedToSunCondition::new);

    @Override
    public LootConditionType getType () {
        return SingleEntityLootConditionTypes.EXPOSED_TO_SUN;
    }

    @Override
    public boolean test (LootContext lootContext) {
        return lootContext.getWorld().isDay() && ExposedToSkyCondition.isSkyVisible(lootContext);
    }

    public static SingleEntityCondition.Builder builder () {
        return ExposedToSunCondition::new;
    }
}
