package com.provismet.CombatPlusCore.utility;

import com.provismet.CombatPlusCore.CPCMain;
import com.provismet.lilylib.container.DamageTypeContainer;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;

public abstract class CPCDamageTypes {
    public static final DamageTypeContainer POISON = new DamageTypeContainer(
        CPCMain.identifier("lethal_poison"),
        new DamageType("lethal_poison", 0.1f)
    );

    public static void bootstrap (Registerable<DamageType> registerable) {
        registerable.register(POISON.getKey(), POISON.getDamageType());
    }
}
