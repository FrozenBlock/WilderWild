package net.frozenblock.wilderwild.misc;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.flag.FeatureFlagSet;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class WilderWildDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		final FabricDataGenerator.Pack menuPack = dataGenerator.createPack();
		menuPack.addProvider(WilderWorldgenProvider::new);
		CompletableFuture<HolderLookup.Provider> completableFuture = CompletableFuture.supplyAsync(WilderRegistry::createLookup, Util.backgroundExecutor());
		final FabricDataGenerator.Pack experimentalPack = dataGenerator.createSubPack("experimental");
		experimentalPack.addProvider((FabricDataGenerator.Pack.Factory<ExperimentRecipeProvider>) ExperimentRecipeProvider::new);
		experimentalPack.addProvider(
				(FabricDataGenerator.Pack.Factory<PackMetadataGenerator>) packOutput -> PackMetadataGenerator.forFeaturePack(
						packOutput, Component.translatable("dataPack.wilderwild.experiment.description"), FeatureFlagSet.of(WilderFeatureFlags.EXPERIMENTAL)
				)
		);
	}

	private static class WilderWorldgenProvider extends FabricWorldgenProvider {

		public WilderWorldgenProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void configure(HolderLookup.Provider registries, Entries entries) {
			//entries.add(RegisterWorldgen.CYPRESS_WETLANDS, RegisterWorldgen.cypressWetlands());
			//entries.add(RegisterWorldgen.JELLYFISH_CAVES, RegisterWorldgen.jellyfishCaves());
			//entries.add(RegisterWorldgen.MIXED_FOREST, RegisterWorldgen.mixedForest());
		}

		@Override
		public String getName() {
			return "Wilder Wild";
		}
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
