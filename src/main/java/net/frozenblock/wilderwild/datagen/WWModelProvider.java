package net.frozenblock.wilderwild.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

final class WWModelProvider extends FabricModelProvider {
	public WWModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generator) {

	}

	@Override
	public void generateItemModels(ItemModelGenerators generator) {
		generator.generateFlatItem(RegisterItems.CRAB_CLAW, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(RegisterItems.COOKED_CRAB_CLAW, ModelTemplates.FLAT_ITEM);
	}
}
