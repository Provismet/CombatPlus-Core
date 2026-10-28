package com.provismet.CombatPlusCore.datagen;

import com.provismet.CombatPlusCore.CombatPlusCoreMain;
import com.provismet.CombatPlusCore.datagen.generator.DamageTypeGenerator;
import com.provismet.CombatPlusCore.datagen.generator.DamageTypeTagGenerator;
import com.provismet.CombatPlusCore.datagen.generator.EnchantmentGenerator;
import com.provismet.CombatPlusCore.datagen.generator.EnchantmentTagGenerator;
import com.provismet.CombatPlusCore.datagen.generator.ItemTagGenerator;
import com.provismet.CombatPlusCore.datagen.generator.LanguageGenerator;
import com.provismet.CombatPlusCore.datagen.generator.ModelGenerator;
import com.provismet.CombatPlusCore.datagen.generator.NumberGenerator;
import com.provismet.CombatPlusCore.datagen.generator.NumeralGenerator;
import com.provismet.CombatPlusCore.utility.CombatPlusDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class CombatPlusCoreDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator (FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(EnchantmentGenerator::new);
        pack.addProvider(DamageTypeGenerator::new);
        pack.addProvider(DamageTypeTagGenerator::new);
        pack.addProvider(ItemTagGenerator::new);
        pack.addProvider(EnchantmentTagGenerator::new);
        pack.addProvider(LanguageGenerator::new);
        pack.addProvider(ModelGenerator::new);

        FabricDataGenerator.Pack numerals = fabricDataGenerator.createBuiltinResourcePack(CombatPlusCoreMain.identifier("enchanted_numerals"));
        numerals.addProvider(NumeralGenerator::new);

        FabricDataGenerator.Pack numbers = fabricDataGenerator.createBuiltinResourcePack(CombatPlusCoreMain.identifier("enchanted_numbers"));
        numbers.addProvider(NumberGenerator::new);
    }

    @Override
    public void buildRegistry (RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.DAMAGE_TYPE, CombatPlusDamageTypes::bootstrap);
    }
}
