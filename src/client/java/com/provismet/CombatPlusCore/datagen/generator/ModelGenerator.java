package com.provismet.CombatPlusCore.datagen.generator;

import com.provismet.CombatPlusCore.debug.registries.CPCDebugItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModelGenerator extends FabricModelProvider {
    public ModelGenerator (FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels (BlockModelGenerators blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels (ItemModelGenerators itemModelGenerator) {
        CPCDebugItems.getOptionalDebugItem().ifPresent(item -> itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM));

        CPCDebugItems.getOptionalDebugShield().ifPresent(shield -> itemModelGenerator.generateBooleanDispatch(
            shield,
            ItemModelUtils.isUsingItem(),
            ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(shield, "_blocking")),
            ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(shield))
        ));
    }
}
