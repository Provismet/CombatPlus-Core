package com.provismet.datagen.CombatPlusCore;

import com.provismet.CombatPlusCore.debug.registries.CPCDebugItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.Models;

public class ModelGenerator extends FabricModelProvider {
    public ModelGenerator (FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels (BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels (ItemModelGenerator itemModelGenerator) {
        CPCDebugItems.getOptionalDebugItem().ifPresent(item -> itemModelGenerator.register(item, Models.HANDHELD));

        CPCDebugItems.getOptionalDebugShield().ifPresent(shield -> itemModelGenerator.registerCondition(
            shield,
            ItemModels.usingItemProperty(),
            ItemModels.basic(ModelIds.getItemSubModelId(shield, "_blocking")),
            ItemModels.basic(ModelIds.getItemModelId(shield))
        ));
    }
}
