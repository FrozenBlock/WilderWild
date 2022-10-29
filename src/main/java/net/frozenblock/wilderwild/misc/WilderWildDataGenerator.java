package net.frozenblock.wilderwild.misc;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBuiltinRegistriesProvider;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterResources;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.flag.FeatureFlagSet;
import org.jetbrains.annotations.NotNull;
import java.util.function.Consumer;

public class WilderWildDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		final FabricDataGenerator.Pack menuPack = dataGenerator.createPack();
		menuPack.addProvider(FabricBuiltinRegistriesProvider.forCurrentMod());
		final FabricDataGenerator.Pack experimentalPack = dataGenerator.createSubPack("experimental");
		experimentalPack.addProvider((FabricDataGenerator.Pack.Factory<ExperimentRecipeProvider>) ExperimentRecipeProvider::new);
		experimentalPack.addProvider(
				(FabricDataGenerator.Pack.Factory<PackMetadataGenerator>) packOutput -> PackMetadataGenerator.forFeaturePack(
						packOutput, Component.translatable("dataPack.wilderwild.experiment.description"), FeatureFlagSet.of(RegisterResources.EXPERIMENTAL)
				)
		);
	}

	private static class ExperimentRecipeProvider extends RecipeProvider {

		public ExperimentRecipeProvider(PackOutput packOutput) {
			super(packOutput);
		}

		@Override
		public void buildRecipes(final @NotNull Consumer<FinishedRecipe> consumer) {
			chestBoat(consumer, RegisterItems.ANCIENT_HORN, RegisterItems.ANCIENT_HORN_FRAGMENT);
		}
	}
}
