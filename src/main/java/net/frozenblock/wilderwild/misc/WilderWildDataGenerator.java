package net.frozenblock.wilderwild.misc;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.frozenblock.lib.datagen.api.FrozenBiomeTagProvider;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterStructures;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.feature.WilderFeatureBootstrap;
import net.frozenblock.wilderwild.world.gen.noise.WilderNoise;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.NotNull;

public class WilderWildDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();
		final CompletableFuture<HolderLookup.Provider> completableFuture = CompletableFuture.supplyAsync(VanillaRegistries::createLookup, Util.backgroundExecutor());
		pack.addProvider(bindRegistries(WilderTagsProvider::new, completableFuture));
		pack.addProvider(WilderWorldgenProvider::new);
		final FabricDataGenerator.Pack experimentalPack = dataGenerator.createSubPack("experimental");
		experimentalPack.addProvider((FabricDataGenerator.Pack.Factory<ExperimentRecipeProvider>) ExperimentRecipeProvider::new);
		experimentalPack.addProvider(
				(FabricDataGenerator.Pack.Factory<PackMetadataGenerator>) packOutput -> PackMetadataGenerator.forFeaturePack(
						packOutput, Component.translatable("dataPack.wilderwild.experiment.description"), FeatureFlagSet.of(WilderFeatureFlags.EXPERIMENTAL)
				)
		);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		WilderWild.logWild("Registering Biomes for", WilderSharedConstants.UNSTABLE_LOGGING);

		registryBuilder.add(Registries.CONFIGURED_FEATURE, WilderFeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, WilderFeatureBootstrap::bootstrapPlaced);
		registryBuilder.add(Registries.BIOME, RegisterWorldgen::bootstrap);
		registryBuilder.add(Registries.NOISE, WilderNoise::bootstrap);
		registryBuilder.add(Registries.PROCESSOR_LIST, RegisterStructures::bootstrapProcessor);
		registryBuilder.add(Registries.TEMPLATE_POOL, RegisterStructures::bootstrapTemplatePool);
		registryBuilder.add(Registries.STRUCTURE, RegisterStructures::bootstrap);
		registryBuilder.add(Registries.STRUCTURE_SET, RegisterStructures::bootstrapStructureSet);
	}

	private static class WilderWorldgenProvider extends FabricDynamicRegistryProvider {

		public WilderWorldgenProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void configure(HolderLookup.Provider registries, Entries entries) {
			WilderFeatureBootstrap.bootstrap(entries);
		}

		@Override
		public String getName() {
			return "Wilder Wild Dynamic Registries";
		}
	}

	private static class WilderTagsProvider extends FrozenBiomeTagProvider {

		public WilderTagsProvider(FabricDataOutput output, CompletableFuture registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.generateBiomeTags();
			this.generateClimateAndVegetationTags();
			this.generateUtilityTags();
			this.generateFeatureTags();
			this.generateStructureTags();
		}

		private void generateBiomeTags() {
			this.getOrCreateTagBuilder(WilderBiomeTags.BIRCH_FOREST)
					.add(Biomes.BIRCH_FOREST)
					.add(Biomes.OLD_GROWTH_BIRCH_FOREST);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.CAVES)
					.addOptional(RegisterWorldgen.JELLYFISH_CAVES);

			this.getOrCreateTagBuilder(WilderBiomeTags.DARK_FOREST)
					.add(Biomes.DARK_FOREST);

			this.getOrCreateTagBuilder(WilderBiomeTags.GROVE)
					.add(Biomes.GROVE);

			this.getOrCreateTagBuilder(WilderBiomeTags.MEADOW)
					.add(Biomes.MEADOW);

			this.getOrCreateTagBuilder(WilderBiomeTags.NON_FROZEN_PLAINS)
					.add(Biomes.PLAINS)
					.add(Biomes.SUNFLOWER_PLAINS);

			this.getOrCreateTagBuilder(WilderBiomeTags.NORMAL_SAVANNA)
					.add(Biomes.SAVANNA)
					.add(Biomes.SAVANNA_PLATEAU);

			this.getOrCreateTagBuilder(WilderBiomeTags.SHORT_TAIGA)
					.add(Biomes.TAIGA)
					.add(Biomes.SNOWY_TAIGA);

			this.getOrCreateTagBuilder(WilderBiomeTags.SNOWY_PLAINS)
					.add(Biomes.SNOWY_PLAINS);
		}

		private void generateClimateAndVegetationTags() {
			this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_HOT)
					.addOptional(RegisterWorldgen.WARM_RIVER);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_TEMPERATE)
					.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
					.addOptional(RegisterWorldgen.MIXED_FOREST);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_WET)
					.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.RIVER)
					.addOptional(RegisterWorldgen.WARM_RIVER);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.TREE_CONIFEROUS)
					.addOptional(RegisterWorldgen.MIXED_FOREST);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.TREE_DECIDUOUS)
					.addOptional(RegisterWorldgen.MIXED_FOREST);
		}

		private void generateUtilityTags() {
			this.getOrCreateTagBuilder(WilderBiomeTags.FIREFLY_SPAWNABLE)
					.add(Biomes.JUNGLE)
					.add(Biomes.SPARSE_JUNGLE)
					.add(Biomes.BAMBOO_JUNGLE);

			this.getOrCreateTagBuilder(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE);

			this.getOrCreateTagBuilder(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY)
					.add(Biomes.SWAMP)
					.add(Biomes.MANGROVE_SWAMP);

			this.getOrCreateTagBuilder(WilderBiomeTags.HAS_JELLYFISH)
					.add(Biomes.WARM_OCEAN)
					.addOptional(RegisterWorldgen.JELLYFISH_CAVES);

			this.getOrCreateTagBuilder(WilderBiomeTags.NO_POOLS)
					.addOptional(Biomes.DEEP_DARK);

			this.getOrCreateTagBuilder(WilderBiomeTags.PEARLESCENT_JELLYFISH)
					.addOptional(RegisterWorldgen.JELLYFISH_CAVES);

			this.getOrCreateTagBuilder(WilderBiomeTags.SWAMP_TREES)
					.add(Biomes.SWAMP);
		}

		private void generateFeatureTags() {
			this.getOrCreateTagBuilder(WilderBiomeTags.FOREST_GRASS)
					.add(Biomes.FOREST)
					.add(Biomes.FLOWER_FOREST)
					.add(Biomes.BIRCH_FOREST)
					.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
					.add(Biomes.DARK_FOREST)
					.add(Biomes.TAIGA);
		}

		private void generateStructureTags() {
			this.getOrCreateTagBuilder(WilderBiomeTags.ABANDONED_CABIN_HAS_STRUCTURE)
					.addOptionalTag(BiomeTags.IS_OCEAN)
					.addOptionalTag(BiomeTags.IS_RIVER)
					.addOptionalTag(BiomeTags.IS_MOUNTAIN)
					.addOptionalTag(BiomeTags.IS_HILL)
					.addOptionalTag(BiomeTags.IS_TAIGA)
					.addOptionalTag(BiomeTags.IS_JUNGLE)
					.addOptionalTag(BiomeTags.IS_FOREST)
					.add(Biomes.STONY_SHORE)
					.add(Biomes.MUSHROOM_FIELDS)
					.add(Biomes.ICE_SPIKES)
					.add(Biomes.WINDSWEPT_SAVANNA)
					.add(Biomes.DESERT)
					.add(Biomes.SAVANNA)
					.add(Biomes.SNOWY_PLAINS)
					.add(Biomes.PLAINS)
					.add(Biomes.SUNFLOWER_PLAINS)
					.add(Biomes.SWAMP)
					.add(Biomes.MANGROVE_SWAMP)
					.add(Biomes.SAVANNA_PLATEAU)
					.add(Biomes.DRIPSTONE_CAVES)
					.add(Biomes.DEEP_DARK)
					.addOptional(RegisterWorldgen.JELLYFISH_CAVES);
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

	public static <T extends DataProvider> FabricDataGenerator.Pack.Factory<T> bindRegistries(
			BiFunction<FabricDataOutput, CompletableFuture<HolderLookup.Provider>, T> biFunction, CompletableFuture<HolderLookup.Provider> completableFuture
	) {
		return packOutput -> (T)biFunction.apply(packOutput, completableFuture);
	}
}
