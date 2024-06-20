package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.registries.CPCSingleEntityLootConditionTypes;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;

public record ExposedToMoon () implements SingleEntityCondition {
    public static final MapCodec<ExposedToMoon> CODEC = MapCodec.unit(ExposedToMoon::new);

    @Override
    public LootConditionType getType () {
        return CPCSingleEntityLootConditionTypes.EXPOSED_TO_MOON;
    }

    @Override
    public boolean test (LootContext lootContext) {
        return lootContext.getWorld().isNight() && ExposedToSky.isSkyVisible(lootContext);
    }

    public static SingleEntityCondition.Builder builder () {
        return ExposedToMoon::new;
    }
}
