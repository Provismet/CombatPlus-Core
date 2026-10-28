package com.provismet.CombatPlusCore.registries;

import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.api.item.component.MeleeWeaponComponent;
import java.util.function.UnaryOperator;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

public abstract class CPCDataComponentTypes {
    public static final DataComponentType<Identifier> COOLDOWN_GROUP = register("cooldown_group", builder -> builder.persistent(Identifier.CODEC).networkSynchronized(Identifier.STREAM_CODEC));
    public static final DataComponentType<Integer> MAX_USE_TIME = register("max_use_time", builder -> builder.persistent(ExtraCodecs.POSITIVE_INT).networkSynchronized(ByteBufCodecs.INT));
    public static final DataComponentType<MeleeWeaponComponent> MELEE_WEAPON = register("melee_weapon", builder -> builder.persistent(MeleeWeaponComponent.CODEC).networkSynchronized(MeleeWeaponComponent.PACKET_CODEC));

    private static <T> DataComponentType<T> register (String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, CombatPlusCoreMain.identifier(name), builderOperator.apply(builderOperator.apply(DataComponentType.builder())).build());
    }

    public static void init () {}
}
