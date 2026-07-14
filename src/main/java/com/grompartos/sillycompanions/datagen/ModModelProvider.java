package com.grompartos.sillycompanions.datagen;

import com.grompartos.sillycompanions.SillyCompanions;
import com.grompartos.sillycompanions.item.MyModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, SillyCompanions.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        itemModels.generateFlatItem(MyModItems.SUMMONING_BOOK.get(), ModelTemplates.FLAT_ITEM);
    }
}
