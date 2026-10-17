package com.provismet.datagen.CombatPlusCore;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import java.util.concurrent.CompletableFuture;

public class NumberGenerator extends FabricLanguageProvider {
    protected NumberGenerator (FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations (HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        for (int i = 0; i < 256; ++i) {
            translationBuilder.add("enchantment.level." + i, String.valueOf(i));
        }
    }
}
