package com.provismet.CombatPlusCore.utility.resource;

import com.mojang.serialization.MapCodec;
import com.provismet.CombatPlusCore.CPCMain;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

public class CPCResourceConditions {
    public static final ResourceConditionType<DevModeResourceCondition> DEVELOPMENT_MODE_ENABLED = createResourceConditionType("developer_mode", DevModeResourceCondition.CODEC);

    public static void register () {
        ResourceConditions.register(DEVELOPMENT_MODE_ENABLED);
    }

    private static <T extends ResourceCondition> ResourceConditionType<T> createResourceConditionType (String name, MapCodec<T> codec) {
        return ResourceConditionType.create(CPCMain.identifier(name), codec);
    }
}
