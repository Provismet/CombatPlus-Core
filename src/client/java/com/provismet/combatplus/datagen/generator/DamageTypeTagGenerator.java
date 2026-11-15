package com.provismet.combatplus.datagen.generator;

import com.provismet.combatplus.utility.CombatPlusDamageTypes;
import com.provismet.combatplus.api.tag.CombatPlusDamageTypeTags;
import com.provismet.lilylib.datagen.tag.LilyTagProviders;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;

import java.util.concurrent.CompletableFuture;

public class DamageTypeTagGenerator extends LilyTagProviders.LilyDamageTypeTagProvider {
    public DamageTypeTagGenerator (FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags (HolderLookup.Provider wrapperLookup) {
        this.builder(CombatPlusDamageTypeTags.IS_STANDARD)
            .addOptionalTag(DamageTypeTags.IS_PLAYER_ATTACK)
            .add(DamageTypes.MOB_ATTACK)
            .add(DamageTypes.MOB_ATTACK_NO_AGGRO);

        this.builder(CombatPlusDamageTypeTags.IS_MELEE)
            .addOptionalTag(CombatPlusDamageTypeTags.IS_STANDARD)
            .addOptionalTag(DamageTypeTags.IS_MACE_SMASH)
            .add(DamageTypes.MACE_SMASH)
            .add(DamageTypes.SPEAR);

        this.builder(CombatPlusDamageTypeTags.IS_MAGIC)
            .add(DamageTypes.MAGIC)
            .add(DamageTypes.INDIRECT_MAGIC)
            .add(DamageTypes.WIND_CHARGE);

        this.builder(CombatPlusDamageTypeTags.IS_WATER);
        this.builder(CombatPlusDamageTypeTags.IS_EARTH);
        this.builder(CombatPlusDamageTypeTags.IS_WIND)
            .add(DamageTypes.WIND_CHARGE)
            .add(DamageTypes.SONIC_BOOM);
        this.builder(CombatPlusDamageTypeTags.IS_LIGHT);
        this.builder(CombatPlusDamageTypeTags.IS_DARK);
        this.builder(CombatPlusDamageTypeTags.IS_LIFE);
        this.builder(CombatPlusDamageTypeTags.IS_DEATH)
            .add(DamageTypes.WITHER)
            .add(DamageTypes.WITHER_SKULL);
        this.builder(CombatPlusDamageTypeTags.IS_SPATIAL);
        this.builder(CombatPlusDamageTypeTags.IS_TEMPORAL);
        this.builder(CombatPlusDamageTypeTags.IS_VOID);

        this.builder(CombatPlusDamageTypeTags.IS_ELEMENTAL)
            .addOptionalTag(DamageTypeTags.IS_FIRE)
            .addOptionalTag(DamageTypeTags.IS_FREEZING)
            .addOptionalTag(DamageTypeTags.IS_LIGHTNING)
            .addOptionalTag(CombatPlusDamageTypeTags.IS_WATER)
            .addOptionalTag(CombatPlusDamageTypeTags.IS_EARTH)
            .addOptionalTag(CombatPlusDamageTypeTags.IS_WIND)
            .addOptionalTag(CombatPlusDamageTypeTags.IS_LIGHT)
            .addOptionalTag(CombatPlusDamageTypeTags.IS_DARK)
            .addOptionalTag(CombatPlusDamageTypeTags.IS_LIFE)
            .addOptionalTag(CombatPlusDamageTypeTags.IS_DEATH)
            .addOptionalTag(CombatPlusDamageTypeTags.IS_SPATIAL)
            .addOptionalTag(CombatPlusDamageTypeTags.IS_TEMPORAL)
            .addOptionalTag(CombatPlusDamageTypeTags.IS_VOID);

        this.builder(DamageTypeTags.BYPASSES_ARMOR)
            .add(CombatPlusDamageTypes.POISON.getKey());
    }
}
