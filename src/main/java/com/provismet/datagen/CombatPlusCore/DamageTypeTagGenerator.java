package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.utility.CPCDamageTypes;
import com.provismet.CombatPlusCore.utility.tag.CPCDamageTypeTags;
import com.provismet.lilylib.datagen.tag.LilyTagProviders;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class DamageTypeTagGenerator extends LilyTagProviders.LilyDamageTypeTagProvider {
    public DamageTypeTagGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(CPCDamageTypeTags.STANDARD_ATTACK)
            .addOptionalTag(DamageTypeTags.IS_PLAYER_ATTACK)
            .add(DamageTypes.MOB_ATTACK)
            .add(DamageTypes.MOB_ATTACK_NO_AGGRO);


        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
            .add(CPCDamageTypes.POISON.getKey());
    }
}
