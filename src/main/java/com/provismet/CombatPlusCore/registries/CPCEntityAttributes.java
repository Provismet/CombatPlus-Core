package com.provismet.CombatPlusCore.registries;

import com.provismet.CombatPlusCore.CPCMain;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public abstract class CPCEntityAttributes {
    public static final RegistryEntry<EntityAttribute> PROTECTION_EFFECTIVENESS = register(
        "protection_effectiveness", new ClampedEntityAttribute("cpc.attribute.name.protection_effectiveness", 1, 0, 1)
    );

    public static void init () {}

    private static RegistryEntry<EntityAttribute> register (String name, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, CPCMain.identifier(name), attribute);
    }
}
