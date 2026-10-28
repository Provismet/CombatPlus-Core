package com.provismet.CombatPlusCore.api.tag;

import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

/**
 * Contains tags for damage types. Combat+ Core does not necessarily use these tags itself, these are provided for other mods to use.
 */
public interface CombatPlusDamageTypeTags {
    /**
     * Is a regular attack from an entity or player. (The left-click attack.)
     */
    TagKey<DamageType> IS_STANDARD = CombatPlusDamageTypeTags.of("is_standard");

    /**
     * Attacks performed unarmed or by melee weapons.
     */
    TagKey<DamageType> IS_MELEE = CombatPlusDamageTypeTags.of("is_melee");

    /**
     * Attacks of a magical nature.
     */
    TagKey<DamageType> IS_MAGIC = CombatPlusDamageTypeTags.of("is_magic");

    private static TagKey<DamageType> of (String path) {
        return TagKey.create(Registries.DAMAGE_TYPE, CombatPlusCoreMain.identifier(path));
    }
}
