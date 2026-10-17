package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.registries.SingleEntityLootConditionTypes;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record ExposedToSunCondition () implements SingleEntityCondition {
    public static final MapCodec<ExposedToSunCondition> CODEC = MapCodec.unit(ExposedToSunCondition::new);

    @Override
    public LootItemConditionType getType () {
        return SingleEntityLootConditionTypes.EXPOSED_TO_SUN;
    }

    @Override
    public boolean test (LootContext lootContext) {
        return lootContext.getLevel().isBrightOutside() && ExposedToSkyCondition.isSkyVisible(lootContext);
    }

    public static SingleEntityCondition.Builder builder () {
        return ExposedToSunCondition::new;
    }
}
