package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.CPCMain;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CPCDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator (FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(EnchantmentGenerator::new);
        pack.addProvider(DamageTypeTagGenerator::new);
        pack.addProvider(ItemTagGenerator::new);
        pack.addProvider(EnchantmentTagGenerator::new);
        pack.addProvider(LanguageGenerator::new);

        FabricDataGenerator.Pack numerals = fabricDataGenerator.createBuiltinResourcePack(CPCMain.identifier("enchanted_numerals"));
        numerals.addProvider(NumeralGenerator::new);

        FabricDataGenerator.Pack numbers = fabricDataGenerator.createBuiltinResourcePack(CPCMain.identifier("enchanted_numbers"));
        numbers.addProvider(NumberGenerator::new);
    }
}
