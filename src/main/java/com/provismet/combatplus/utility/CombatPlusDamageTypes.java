package com.provismet.combatplus.utility;

import com.provismet.combatplus.CombatPlusCoreMain;
import com.provismet.lilylib.container.DamageTypeContainer;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageType;

public abstract class CombatPlusDamageTypes {
    public static final DamageTypeContainer POISON = new DamageTypeContainer(
        CombatPlusCoreMain.identifier("lethal_poison"),
        new DamageType("lethal_poison", 0.1f)
    );

    public static void bootstrap (BootstrapContext<DamageType> registerable) {
        registerable.register(POISON.getKey(), POISON.getDamageType());
    }
}
