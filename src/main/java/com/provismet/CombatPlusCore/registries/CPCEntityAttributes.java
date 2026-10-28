package com.provismet.CombatPlusCore.registries;

import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public abstract class CPCEntityAttributes {
    public static final Holder<Attribute> PROTECTION_EFFECTIVENESS = register(
        "protection_effectiveness", new RangedAttribute("cpc.attribute.name.protection_effectiveness", 1, 0, 1)
    );

    public static void init () {}

    private static Holder<Attribute> register (String name, Attribute attribute) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, CombatPlusCoreMain.identifier(name), attribute);
    }
}
