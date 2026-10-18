package com.provismet.CombatPlusCore.enchantment.loot.condition.singleEntity;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.enchantment.loot.condition.SingleEntityCondition;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record ExposedToSkyCondition () implements SingleEntityCondition {
    public static final MapCodec<ExposedToSkyCondition> CODEC = MapCodec.unit(ExposedToSkyCondition::new);

    @Override
    public boolean test (LootContext lootContext) {
        return ExposedToSkyCondition.isSkyVisible(lootContext);
    }

    public static boolean isSkyVisible (LootContext lootContext) {
        ServerLevel world = lootContext.getLevel();
        Vec3 position = lootContext.getOptionalParameter(LootContextParams.ORIGIN);
        if (position == null) return false;
        return world.canSeeSky(new BlockPos((int)position.x, (int)position.y, (int)position.z));
    }

    public static SingleEntityCondition.Builder builder () {
        return ExposedToSkyCondition::new;
    }

    @Override
    @NotNull
    public MapCodec<? extends LootItemCondition> codec () {
        return CODEC;
    }
}
