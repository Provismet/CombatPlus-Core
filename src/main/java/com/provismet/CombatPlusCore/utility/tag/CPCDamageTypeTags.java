package com.provismet.CombatPlusCore.utility.tag;

import com.provismet.CombatPlusCore.CPCMain;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class CPCDamageTypeTags {
    public static final TagKey<DamageType> STANDARD_ATTACK = CPCDamageTypeTags.of("standard_attack");

    private static TagKey<DamageType> of (String path) {
        return TagKey.of(RegistryKeys.DAMAGE_TYPE, CPCMain.identifier(path));
    }
}
