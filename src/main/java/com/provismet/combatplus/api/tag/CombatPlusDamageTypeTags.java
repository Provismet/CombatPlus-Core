package com.provismet.combatplus.api.tag;

import com.provismet.combatplus.CombatPlusCoreMain;
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

    /**
     * Attacks that would fit into a common elemental structure.
     */
    TagKey<DamageType> IS_ELEMENTAL = CombatPlusDamageTypeTags.of("is_elemental");

    /**
     * Elemental attacks that use water.
     */
    TagKey<DamageType> IS_WATER = CombatPlusDamageTypeTags.of("is_water");

    /**
     * Elemental attacks that use earth,
     */
    TagKey<DamageType> IS_EARTH = CombatPlusDamageTypeTags.of("is_earth");

    /**
     * Elemental attacks that use wind.
     */
    TagKey<DamageType> IS_WIND = CombatPlusDamageTypeTags.of("is_wind");

    /**
     * Elemental attacks that use light, radiation, holiness, or divinity.
     */
    TagKey<DamageType> IS_LIGHT = CombatPlusDamageTypeTags.of("is_light");

    /**
     * Elemental attacks that use darkness or are demonic.
     */
    TagKey<DamageType> IS_DARK = CombatPlusDamageTypeTags.of("is_dark");

    /**
     * Elemental attacks associated with life energy.
     */
    TagKey<DamageType> IS_LIFE = CombatPlusDamageTypeTags.of("is_life");

    /**
     * Elemental attacks associated with death energy or necromancy.
     */
    TagKey<DamageType> IS_DEATH = CombatPlusDamageTypeTags.of("is_death");

    /**
     * Elemental attacks that use space manipulation.
     */
    TagKey<DamageType> IS_SPATIAL = CombatPlusDamageTypeTags.of("is_spatial");

    /**
     * Elemental attacks that use time manipulation.
     */
    TagKey<DamageType> IS_TEMPORAL = CombatPlusDamageTypeTags.of("is_temporal");

    /**
     * Elemental attacks that use void manipulation.
     */
    TagKey<DamageType> IS_VOID = CombatPlusDamageTypeTags.of("is_void");

    private static TagKey<DamageType> of (String path) {
        return TagKey.create(Registries.DAMAGE_TYPE, CombatPlusCoreMain.identifier(path));
    }
}
