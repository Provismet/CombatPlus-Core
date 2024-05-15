package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.utility.CPCEnchantmentTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEnchantmentTags;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class EnchantmentTagGenerator extends FabricTagProvider.EnchantmentTagProvider {
    public EnchantmentTagGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure (RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(CPCEnchantmentTags.ADDITIONAL_DAMAGE);
        getOrCreateTagBuilder(CPCEnchantmentTags.ASPECT).add(Enchantments.FIRE_ASPECT);
        getOrCreateTagBuilder(CPCEnchantmentTags.WEAPON_UTILITY).add(Enchantments.SWEEPING_EDGE);
        getOrCreateTagBuilder(CPCEnchantmentTags.OFFHAND);

        getOrCreateTagBuilder(ConventionalEnchantmentTags.WEAPON_DAMAGE_ENHANCEMENTS)
                .addOptionalTag(CPCEnchantmentTags.ADDITIONAL_DAMAGE);
    }
}
