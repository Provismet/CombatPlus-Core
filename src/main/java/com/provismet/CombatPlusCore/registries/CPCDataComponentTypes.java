package com.provismet.CombatPlusCore.registries;

import com.provismet.CombatPlusCore.CPCMain;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public abstract class CPCDataComponentTypes {
    public static final ComponentType<Identifier> COOLDOWN_GROUP = register("cooldown_group", builder -> builder.codec(Identifier.CODEC).packetCodec(Identifier.PACKET_CODEC));

    private static <T> ComponentType<T> register (String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, CPCMain.identifier(name), builderOperator.apply(builderOperator.apply(ComponentType.builder())).build());
    }

    public static void init () {}
}
