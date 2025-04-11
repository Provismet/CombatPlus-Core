package com.provismet.CombatPlusCore.registries;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.CombatPlusCore.items.component.MeleeWeaponComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public abstract class CPCDataComponentTypes {
    public static final ComponentType<Identifier> COOLDOWN_GROUP = register("cooldown_group", builder -> builder.codec(Identifier.CODEC).packetCodec(Identifier.PACKET_CODEC));
    public static final ComponentType<Integer> MAX_USE_TIME = register("max_use_time", builder -> builder.codec(Codecs.POSITIVE_INT).packetCodec(PacketCodecs.INTEGER));
    public static final ComponentType<MeleeWeaponComponent> MELEE_WEAPON = register("melee_weapon", builder -> builder.codec(MeleeWeaponComponent.CODEC).packetCodec(MeleeWeaponComponent.PACKET_CODEC));

    private static <T> ComponentType<T> register (String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, CPCMain.identifier(name), builderOperator.apply(builderOperator.apply(ComponentType.builder())).build());
    }

    public static void init () {}
}
