package com.provismet.datagen.CombatPlusCore;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.Tuple;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NumeralGenerator extends FabricLanguageProvider {
    protected NumeralGenerator (FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations (HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        for (int i = 0; i < 256; ++i) {
            translationBuilder.add("enchantment.level." + i, NumeralGenerator.getRomanNumeral(i));
        }
    }

    private static String getRomanNumeral (int value) {
        if (value < 1) return "NULL";

        List<Tuple<Integer, String>> numerals = List.of(
            new Tuple<>(100, "C"), // Enchantments only go up to 255 anyway, so we can stop at C.
            new Tuple<>(90, "XC"),
            new Tuple<>(50, "L"),
            new Tuple<>(40, "XL"),
            new Tuple<>(10, "X"),
            new Tuple<>(9, "IX"),
            new Tuple<>(5, "V"),
            new Tuple<>(4, "IV"),
            new Tuple<>(1, "I")
        );
        StringBuilder numeral = new StringBuilder();

        while (value > 0) {
            for (Tuple<Integer, String> key : numerals) {
                if (value >= key.getA()) {
                    value -= key.getA();
                    numeral.append(key.getB());
                    break;
                }
            }
        }
        return numeral.toString();
    }
}
