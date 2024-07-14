package com.provismet.datagen.CombatPlusCore;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class NumberGenerator extends FabricLanguageProvider {
    protected NumberGenerator (FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations (RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        for (int i = 0; i < 256; ++i) {
            translationBuilder.add("enchantment.level." + i, String.valueOf(i));
        }
    }
}
