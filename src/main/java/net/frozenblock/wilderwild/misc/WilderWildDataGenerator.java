package net.frozenblock.wilderwild.misc;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.frozenblock.lib.datagen.api.FrozenBiomeTagProvider;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;

public class WilderWildDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		dataGenerator.addProvider(WilderBiomeTagProvider::new);
		dataGenerator.addProvider(WilderBlockTagProvider::new);
	}

	private static final class WilderBiomeTagProvider extends FrozenBiomeTagProvider {

		public WilderBiomeTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator);
		}

		@Override
		protected void generateTags() {
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

			this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_TEMPERATE)
					.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
					.addOptional(RegisterWorldgen.MIXED_FOREST);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_WET)
					.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

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

	private static final class WilderBlockTagProvider extends FabricTagProvider.BlockTagProvider {
		public WilderBlockTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator);
		}

		@Override
		protected void generateTags() {
			this.generateCompatTags();
		}

		private void generateCompatTags() {
			this.getOrCreateTagBuilder(WilderBlockTags.TERMITE_BREAKABLE)
					.addOptionalTag(BlockTags.LEAVES)
					.add(RegisterBlocks.HOLLOWED_ACACIA_LOG)
					.add(RegisterBlocks.HOLLOWED_BIRCH_LOG)
					.add(RegisterBlocks.HOLLOWED_DARK_OAK_LOG)
					.add(RegisterBlocks.HOLLOWED_OAK_LOG)
					.add(RegisterBlocks.HOLLOWED_JUNGLE_LOG)
					.add(RegisterBlocks.HOLLOWED_MANGROVE_LOG)
					.add(RegisterBlocks.HOLLOWED_SPRUCE_LOG)
					.add(RegisterBlocks.HOLLOWED_CYPRESS_LOG)
					.add(RegisterBlocks.HOLLOWED_BAOBAB_LOG)
					.add(Blocks.BAMBOO)
					.addOptional(
							ResourceKey.create(
									Registry.BLOCK_REGISTRY,
									new ResourceLocation("immersive_weathering", "leaf_piles")
							)
					);

		}
	}
}
