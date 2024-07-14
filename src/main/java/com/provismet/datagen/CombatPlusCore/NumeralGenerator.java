package com.provismet.datagen.CombatPlusCore;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Pair;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NumeralGenerator extends FabricLanguageProvider {
    protected NumeralGenerator (FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations (RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        for (int i = 11; i < 256; ++i) {
            translationBuilder.add("enchantment.level." + i, NumeralGenerator.getRomanNumeral(i));
        }
    }

    private static String getRomanNumeral (int value) {
        if (value < 1) return "NULL";

        List<Pair<Integer, String>> numerals = List.of(
            new Pair<>(100, "C"), // Enchantments only go up to 255 anyway, so we can stop at C.
            new Pair<>(90, "XC"),
            new Pair<>(50, "L"),
            new Pair<>(40, "XL"),
            new Pair<>(10, "X"),
            new Pair<>(9, "IX"),
            new Pair<>(5, "V"),
            new Pair<>(4, "IV"),
            new Pair<>(1, "I")
        );
        StringBuilder numeral = new StringBuilder();

        while (value > 0) {
            for (Pair<Integer, String> key : numerals) {
                if (value >= key.getLeft()) {
                    value -= key.getLeft();
                    numeral.append(key.getRight());
                    break;
                }
            }
        }
        return numeral.toString();
    }
}
