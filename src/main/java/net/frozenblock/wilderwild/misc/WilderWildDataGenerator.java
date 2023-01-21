package net.frozenblock.wilderwild.misc;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
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
					.add(Biomes.SUNFLOWER_PLAINS)
					.addOptional(RegisterWorldgen.FLOWER_FIELD);

			this.getOrCreateTagBuilder(WilderBiomeTags.NORMAL_SAVANNA)
					.add(Biomes.SAVANNA)
					.add(Biomes.SAVANNA_PLATEAU)
					.addOptional(RegisterWorldgen.ARID_SAVANNA);

			this.getOrCreateTagBuilder(WilderBiomeTags.SHORT_TAIGA)
					.add(Biomes.TAIGA)
					.add(Biomes.SNOWY_TAIGA);

			this.getOrCreateTagBuilder(WilderBiomeTags.SNOWY_PLAINS)
					.add(Biomes.SNOWY_PLAINS);

			this.getOrCreateTagBuilder(WilderBiomeTags.TALL_PINE_TAIGA)
					.add(Biomes.OLD_GROWTH_PINE_TAIGA);

			this.getOrCreateTagBuilder(WilderBiomeTags.TALL_SPRUCE_TAIGA)
					.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA);

			this.getOrCreateTagBuilder(WilderBiomeTags.WINDSWEPT_FOREST)
					.add(Biomes.WINDSWEPT_FOREST);

			this.getOrCreateTagBuilder(WilderBiomeTags.WINDSWEPT_HILLS)
					.add(Biomes.WINDSWEPT_HILLS)
					.add(Biomes.WINDSWEPT_GRAVELLY_HILLS);

			this.getOrCreateTagBuilder(WilderBiomeTags.WINDSWEPT_SAVANNA)
					.add(Biomes.WINDSWEPT_SAVANNA);

			this.getOrCreateTagBuilder(BiomeTags.IS_TAIGA)
					.addOptional(RegisterWorldgen.BIRCH_TAIGA);

			this.getOrCreateTagBuilder(BiomeTags.IS_SAVANNA)
					.addOptional(RegisterWorldgen.ARID_SAVANNA);

			this.getOrCreateTagBuilder(BiomeTags.IS_RIVER)
					.addOptional(RegisterWorldgen.OASIS);

			this.getOrCreateTagBuilder(BiomeTags.IS_OVERWORLD)
					.addOptional(RegisterWorldgen.MIXED_FOREST)
					.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
					.addOptional(RegisterWorldgen.JELLYFISH_CAVES)
					.addOptional(RegisterWorldgen.OASIS)
					.addOptional(RegisterWorldgen.WARM_RIVER)
					.addOptional(RegisterWorldgen.FLOWER_FIELD)
					.addOptional(RegisterWorldgen.ARID_SAVANNA);
		}

		private void generateClimateAndVegetationTags() {

			this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_TEMPERATE)
					.addOptional(RegisterWorldgen.CYPRESS_WETLANDS)
					.addOptional(RegisterWorldgen.MIXED_FOREST);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_WET)
					.addOptional(RegisterWorldgen.CYPRESS_WETLANDS);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.TREE_CONIFEROUS)
					.addOptional(RegisterWorldgen.MIXED_FOREST)
					.addOptional(RegisterWorldgen.BIRCH_TAIGA);

			this.getOrCreateTagBuilder(ConventionalBiomeTags.TREE_DECIDUOUS)
					.addOptional(RegisterWorldgen.MIXED_FOREST)
					.addOptional(RegisterWorldgen.BIRCH_TAIGA);
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

			this.getOrCreateTagBuilder(WilderBiomeTags.HAS_TUMBLEWEED_ENTITY)
					.add(Biomes.DESERT)
					.add(Biomes.BADLANDS)
					.add(Biomes.ERODED_BADLANDS)
					.add(Biomes.WOODED_BADLANDS)
					.add(Biomes.WINDSWEPT_SAVANNA);

			this.getOrCreateTagBuilder(WilderBiomeTags.HAS_TUMBLEWEED_PLANT)
					.add(Biomes.DESERT)
					.add(Biomes.BADLANDS)
					.add(Biomes.ERODED_BADLANDS)
					.add(Biomes.WOODED_BADLANDS)
					.add(Biomes.WINDSWEPT_SAVANNA)
					.add(Biomes.SAVANNA_PLATEAU)
					.addOptional(RegisterWorldgen.ARID_SAVANNA);

			this.getOrCreateTagBuilder(WilderBiomeTags.NO_POOLS)
					.addOptional(Biomes.DEEP_DARK);

			this.getOrCreateTagBuilder(WilderBiomeTags.PEARLESCENT_JELLYFISH)
					.addOptional(RegisterWorldgen.JELLYFISH_CAVES);

			this.getOrCreateTagBuilder(WilderBiomeTags.SWAMP_TREES)
					.add(Biomes.SWAMP);

			this.getOrCreateTagBuilder(WilderBiomeTags.GRAVEL_BEACH)
					.add(Biomes.BIRCH_FOREST)
					.add(Biomes.FROZEN_RIVER)
					.add(RegisterWorldgen.MIXED_FOREST)
					.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
					.add(Biomes.OLD_GROWTH_PINE_TAIGA)
					.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
					.add(Biomes.TAIGA)
					.add(Biomes.SNOWY_TAIGA)
					.addOptional(RegisterWorldgen.BIRCH_TAIGA);

			this.getOrCreateTagBuilder(WilderBiomeTags.SAND_BEACHES)
					.add(Biomes.DARK_FOREST)
					.add(Biomes.FLOWER_FOREST)
					.add(Biomes.FOREST)
					.addOptional(RegisterWorldgen.ARID_SAVANNA);

			this.getOrCreateTagBuilder(WilderBiomeTags.MULTI_LAYER_SAND_BEACHES)
					.add(Biomes.BAMBOO_JUNGLE)
					.add(Biomes.JUNGLE)
					.add(Biomes.SAVANNA)
					.add(Biomes.SPARSE_JUNGLE)
					.addOptional(new ResourceLocation("terralith", "arid_highlands"));
		}

		private void generateFeatureTags() {
			this.getOrCreateTagBuilder(WilderBiomeTags.FOREST_GRASS)
					.add(Biomes.FOREST)
					.add(Biomes.FLOWER_FOREST)
					.add(Biomes.BIRCH_FOREST)
					.add(Biomes.OLD_GROWTH_BIRCH_FOREST)
					.add(Biomes.DARK_FOREST)
					.add(Biomes.TAIGA)
					.addOptional(RegisterWorldgen.BIRCH_TAIGA)
					.addOptional(RegisterWorldgen.FLOWER_FIELD);

			this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SMALL_SPONGE)
					.add(Biomes.WARM_OCEAN);

			this.getOrCreateTagBuilder(WilderBiomeTags.HAS_SMALL_SPONGE_RARE)
					.add(Biomes.LUKEWARM_OCEAN)
					.add(Biomes.DEEP_LUKEWARM_OCEAN)
					.add(Biomes.LUSH_CAVES);
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
					.addOptional(RegisterWorldgen.JELLYFISH_CAVES)
					.addOptional(RegisterWorldgen.ARID_SAVANNA)
					.addOptional(RegisterWorldgen.FLOWER_FIELD)
					.addOptional(RegisterWorldgen.BIRCH_TAIGA);
		}
	}

	private static final class WilderBlockTagProvider extends FabricTagProvider.BlockTagProvider {
		public WilderBlockTagProvider(FabricDataGenerator dataGenerator) {
			super(dataGenerator);
		}

		@Override
		protected void generateTags() {
			this.generateDeepDark();
			this.generateHollowedAndTermites();
		}

		private void generateDeepDark() {
			this.getOrCreateTagBuilder(WilderBlockTags.ANCIENT_CITY_BLOCKS)
					.add(Blocks.COBBLED_DEEPSLATE)
					.add(Blocks.COBBLED_DEEPSLATE_STAIRS)
					.add(Blocks.COBBLED_DEEPSLATE_SLAB)
					.add(Blocks.COBBLED_DEEPSLATE_WALL)
					.add(Blocks.POLISHED_DEEPSLATE)
					.add(Blocks.POLISHED_DEEPSLATE_STAIRS)
					.add(Blocks.POLISHED_DEEPSLATE_SLAB)
					.add(Blocks.POLISHED_DEEPSLATE_WALL)
					.add(Blocks.DEEPSLATE_BRICKS)
					.add(Blocks.DEEPSLATE_BRICK_STAIRS)
					.add(Blocks.DEEPSLATE_BRICK_SLAB)
					.add(Blocks.DEEPSLATE_BRICK_WALL)
					.add(Blocks.CRACKED_DEEPSLATE_BRICKS)
					.add(Blocks.DEEPSLATE_TILES)
					.add(Blocks.DEEPSLATE_TILE_STAIRS)
					.add(Blocks.CHISELED_DEEPSLATE)
					.add(Blocks.REINFORCED_DEEPSLATE)
					.add(Blocks.POLISHED_BASALT)
					.add(Blocks.SMOOTH_BASALT)
					.add(Blocks.DARK_OAK_LOG)
					.add(Blocks.DARK_OAK_PLANKS)
					.add(Blocks.DARK_OAK_FENCE)
					.add(Blocks.LIGHT_BLUE_CARPET)
					.add(Blocks.BLUE_CARPET)
					.add(Blocks.LIGHT_BLUE_WOOL)
					.add(Blocks.GRAY_WOOL)
					.add(Blocks.CHEST)
					.add(Blocks.LADDER)
					.add(Blocks.CANDLE)
					.add(Blocks.WHITE_CANDLE)
					.add(Blocks.SOUL_LANTERN)
					.add(Blocks.SOUL_FIRE)
					.add(Blocks.SOUL_SAND);

			this.getOrCreateTagBuilder(WilderBlockTags.ANCIENT_HORN_NON_COLLIDE)
					.add(Blocks.SCULK)
					.add(RegisterBlocks.OSSEOUS_SCULK)
					.addOptionalTag(ConventionalBlockTags.GLASS_BLOCKS)
					.addOptionalTag(ConventionalBlockTags.GLASS_PANES)
					.addOptionalTag(BlockTags.LEAVES)
					.add(Blocks.BELL)
					.add(Blocks.POINTED_DRIPSTONE)
					.add(Blocks.BAMBOO)
					.add(Blocks.ICE)
					.add(RegisterBlocks.SCULK_STAIRS)
					.add(RegisterBlocks.SCULK_SLAB)
					.add(RegisterBlocks.SCULK_WALL);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_SLAB_REPLACEABLE)
					.add(Blocks.STONE_SLAB)
					.add(Blocks.GRANITE_SLAB)
					.add(Blocks.DIORITE_SLAB)
					.add(Blocks.ANDESITE_SLAB)
					.add(Blocks.BLACKSTONE_SLAB);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)
					.add(Blocks.COBBLED_DEEPSLATE_SLAB)
					.add(Blocks.POLISHED_DEEPSLATE_SLAB)
					.add(Blocks.DEEPSLATE_BRICK_SLAB)
					.add(Blocks.DEEPSLATE_TILE_SLAB)
					.addOptionalTag(WilderBlockTags.SCULK_SLAB_REPLACEABLE);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_STAIR_REPLACEABLE)
					.add(Blocks.STONE_STAIRS)
					.add(Blocks.GRANITE_STAIRS)
					.add(Blocks.DIORITE_STAIRS)
					.add(Blocks.ANDESITE_STAIRS)
					.add(Blocks.BLACKSTONE_STAIRS);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)
					.add(Blocks.COBBLED_DEEPSLATE_STAIRS)
					.add(Blocks.POLISHED_DEEPSLATE_STAIRS)
					.add(Blocks.DEEPSLATE_BRICK_STAIRS)
					.add(Blocks.DEEPSLATE_TILE_STAIRS)
					.addOptionalTag(WilderBlockTags.SCULK_STAIR_REPLACEABLE);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_VEIN_REMOVE)
					.add(Blocks.SCULK)
					.add(RegisterBlocks.SCULK_WALL)
					.add(RegisterBlocks.SCULK_SLAB)
					.add(RegisterBlocks.SCULK_STAIRS);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_WALL_REPLACEABLE)
					.add(Blocks.COBBLESTONE_WALL)
					.add(Blocks.GRANITE_WALL)
					.add(Blocks.DIORITE_WALL)
					.add(Blocks.ANDESITE_WALL)
					.add(Blocks.BLACKSTONE_WALL);

			this.getOrCreateTagBuilder(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)
					.add(Blocks.COBBLED_DEEPSLATE_WALL)
					.add(Blocks.POLISHED_DEEPSLATE_WALL)
					.add(Blocks.DEEPSLATE_BRICK_WALL)
					.add(Blocks.DEEPSLATE_TILE_WALL)
					.addOptionalTag(WilderBlockTags.SCULK_WALL_REPLACEABLE);
		}

		private void generateHollowedAndTermites() {
			this.getOrCreateTagBuilder(WilderBlockTags.BLOCKS_TERMITE)
					.addOptionalTag(ConventionalBlockTags.GLASS_BLOCKS)
					.addOptionalTag(ConventionalBlockTags.GLASS_PANES)
					.add(RegisterBlocks.ECHO_GLASS);

			this.getOrCreateTagBuilder(ConventionalBlockTags.GLASS_BLOCKS)
					.add(RegisterBlocks.ECHO_GLASS);

			this.getOrCreateTagBuilder(WilderBlockTags.BUSH_MAY_PLACE_ON)
					.addOptionalTag(BlockTags.SAND)
					.addOptionalTag(BlockTags.DIRT);

			this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_LOGS)
					.add(RegisterBlocks.HOLLOWED_ACACIA_LOG)
					.add(RegisterBlocks.HOLLOWED_BIRCH_LOG)
					.add(RegisterBlocks.HOLLOWED_DARK_OAK_LOG)
					.add(RegisterBlocks.HOLLOWED_OAK_LOG)
					.add(RegisterBlocks.HOLLOWED_JUNGLE_LOG)
					.add(RegisterBlocks.HOLLOWED_MANGROVE_LOG)
					.add(RegisterBlocks.HOLLOWED_SPRUCE_LOG)
					.add(RegisterBlocks.HOLLOWED_WARPED_STEM)
					.add(RegisterBlocks.HOLLOWED_CRIMSON_STEM)
					.add(RegisterBlocks.HOLLOWED_BAOBAB_LOG)
					.add(RegisterBlocks.HOLLOWED_CYPRESS_LOG)
					.add(RegisterBlocks.HOLLOWED_PALM_LOG);

			this.getOrCreateTagBuilder(WilderBlockTags.KILLS_TERMITE)
					.add(Blocks.POWDER_SNOW)
					.add(Blocks.WATER_CAULDRON)
					.add(Blocks.LAVA_CAULDRON)
					.add(Blocks.POWDER_SNOW_CAULDRON)
					.add(Blocks.CRIMSON_ROOTS)
					.add(Blocks.CRIMSON_PLANKS)
					.add(Blocks.WARPED_PLANKS)
					.add(Blocks.WEEPING_VINES)
					.add(Blocks.WEEPING_VINES_PLANT)
					.add(Blocks.TWISTING_VINES)
					.add(Blocks.TWISTING_VINES_PLANT)
					.add(Blocks.CRIMSON_SLAB)
					.add(Blocks.WARPED_SLAB)
					.add(Blocks.CRIMSON_PRESSURE_PLATE)
					.add(Blocks.WARPED_PRESSURE_PLATE)
					.add(Blocks.CRIMSON_FENCE)
					.add(Blocks.WARPED_FENCE)
					.add(Blocks.CRIMSON_TRAPDOOR)
					.add(Blocks.WARPED_TRAPDOOR)
					.add(Blocks.CRIMSON_FENCE_GATE)
					.add(Blocks.WARPED_FENCE_GATE)
					.add(Blocks.CRIMSON_STAIRS)
					.add(Blocks.WARPED_STAIRS)
					.add(Blocks.CRIMSON_BUTTON)
					.add(Blocks.WARPED_BUTTON)
					.add(Blocks.CRIMSON_DOOR)
					.add(Blocks.WARPED_DOOR)
					.add(Blocks.CRIMSON_SIGN)
					.add(Blocks.WARPED_SIGN)
					.add(Blocks.CRIMSON_WALL_SIGN)
					.add(Blocks.WARPED_WALL_SIGN)
					.add(Blocks.CRIMSON_STEM)
					.add(Blocks.WARPED_STEM)
					.add(Blocks.STRIPPED_WARPED_STEM)
					.add(Blocks.STRIPPED_WARPED_HYPHAE)
					.add(Blocks.WARPED_NYLIUM)
					.add(Blocks.WARPED_FUNGUS)
					.add(Blocks.STRIPPED_CRIMSON_STEM)
					.add(Blocks.STRIPPED_CRIMSON_HYPHAE)
					.add(Blocks.CRIMSON_NYLIUM)
					.add(Blocks.CRIMSON_FUNGUS)
					.add(Blocks.REDSTONE_WIRE)
					.add(Blocks.REDSTONE_BLOCK)
					.add(Blocks.REDSTONE_TORCH)
					.add(Blocks.REDSTONE_WALL_TORCH)
					.addOptional(ResourceKey.create(
							Registry.BLOCK_REGISTRY,
							WilderSharedConstants.id("hollowed_crimson_stem")
					))
					.addOptional(ResourceKey.create(
							Registry.BLOCK_REGISTRY,
							WilderSharedConstants.id("hollowed_warped_stem")
					));

			this.getOrCreateTagBuilder(WilderBlockTags.TERMITE_BREAKABLE)
					.addOptionalTag(BlockTags.LEAVES)
					.addOptionalTag(WilderBlockTags.HOLLOWED_LOGS)
					.add(Blocks.BAMBOO)
					.addOptional(
							ResourceKey.create(
									Registry.BLOCK_REGISTRY,
									new ResourceLocation("immersive_weathering", "leaf_piles")
							)
					);

			this.getOrCreateTagBuilder(WilderBlockTags.SAND_POOL_REPLACEABLE)
					.add(Blocks.SAND);
		}
	}
}
