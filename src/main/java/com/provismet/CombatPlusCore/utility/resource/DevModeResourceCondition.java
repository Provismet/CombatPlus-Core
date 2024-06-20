package com.provismet.CombatPlusCore.utility.resource;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.Nullable;

public record DevModeResourceCondition () implements ResourceCondition {
    public static final MapCodec<DevModeResourceCondition> CODEC = MapCodec.unit(DevModeResourceCondition::new);

    @Override
    public ResourceConditionType<?> getType () {
        return CPCResourceConditions.DEVELOPMENT_MODE_ENABLED;
    }

    @Override
    public boolean test (@Nullable RegistryWrapper.WrapperLookup registryLookup) {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
