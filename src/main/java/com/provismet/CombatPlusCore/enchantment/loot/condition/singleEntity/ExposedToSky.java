package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import com.provismet.CombatPlusCore.registries.CPCSingleEntityLootConditionTypes;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public record ExposedToSky () implements SingleEntityCondition {
    public static final MapCodec<ExposedToSky> CODEC = MapCodec.unit(ExposedToSky::new);

    @Override
    public LootConditionType getType () {
        return CPCSingleEntityLootConditionTypes.EXPOSED_TO_SKY;
    }

    @Override
    public boolean test (LootContext lootContext) {
        return ExposedToSky.isSkyVisible(lootContext);
    }

    public static boolean isSkyVisible (LootContext lootContext) {
        ServerWorld world = lootContext.getWorld();
        Vec3d position = lootContext.get(LootContextParameters.ORIGIN);
        return world.isSkyVisible(new BlockPos((int)position.x, (int)position.y, (int)position.z));
    }

    public static SingleEntityCondition.Builder builder () {
        return ExposedToSky::new;
    }
}
