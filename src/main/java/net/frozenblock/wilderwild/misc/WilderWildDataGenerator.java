package net.frozenblock.wilderwild.misc;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBuiltinRegistriesProvider;

public class WilderWildDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		final FabricDataGenerator.Pack menuPack = dataGenerator.create();
		menuPack.addProvider(FabricBuiltinRegistriesProvider.forCurrentMod());
	}
}
