package com.provismet.CombatPlusCore.utility.tag;

import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class CPCDamageTypeTags {
    public static final TagKey<DamageType> STANDARD_ATTACK = CPCDamageTypeTags.of("standard_attack");

    private static TagKey<DamageType> of (String path) {
        return TagKey.create(Registries.DAMAGE_TYPE, CombatPlusCoreMain.identifier(path));
    }
}
