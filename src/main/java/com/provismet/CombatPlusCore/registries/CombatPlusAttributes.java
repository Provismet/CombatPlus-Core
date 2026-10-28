package com.provismet.CombatPlusCore.registries;

import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public interface CombatPlusAttributes {
    Holder<Attribute> PROTECTION_EFFECTIVENESS = register("protection_effectiveness", 1, 0, 1);
    Holder<Attribute> MAGIC_DAMAGE = register("magic_damage", 0, 0, 2048);
    Holder<Attribute> PROJECTILE_DAMAGE = register("projectile_damage", 0, 0, 2048);

    static void init () {}

    private static Holder<Attribute> register (String name, Attribute attribute) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, CombatPlusCoreMain.identifier(name), attribute);
    }

    private static Holder<Attribute> register (String name, double defaultValue, double minValue, double maxValue) {
        return register(name, new RangedAttribute("combat-plus.attribute.name." + name, defaultValue, minValue, maxValue));
    }
}
