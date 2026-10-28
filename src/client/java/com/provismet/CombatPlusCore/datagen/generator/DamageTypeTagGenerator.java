package com.provismet.CombatPlusCore.datagen.generator;

import com.provismet.CombatPlusCore.utility.CPCDamageTypes;
import com.provismet.CombatPlusCore.api.tag.CPCDamageTypeTags;
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
        this.builder(CPCDamageTypeTags.STANDARD_ATTACK)
            .addOptionalTag(DamageTypeTags.IS_PLAYER_ATTACK)
            .add(DamageTypes.MOB_ATTACK)
            .add(DamageTypes.MOB_ATTACK_NO_AGGRO);

        this.builder(DamageTypeTags.BYPASSES_ARMOR)
            .add(CPCDamageTypes.POISON.getKey());
    }
}
