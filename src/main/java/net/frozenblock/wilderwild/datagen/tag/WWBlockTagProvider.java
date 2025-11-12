/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.frozenblock.lib.tag.api.FrozenBlockTags;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public final class WWBlockTagProvider extends FabricTagProvider.BlockTagProvider {

	public WWBlockTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.generateSounds();
		this.generateCompat();
		this.generateLib();
		this.generateFeatures();
		this.generateDeepDark();
		this.generateHollowedAndTermites();
		this.generateTags();
		this.generateMinecraft();
		this.generateWoods();
	}

	@NotNull
	private TagKey<Block> getTag(String id) {
		return TagKey.create(this.registryKey, Identifier.parse(id));
	}

	@NotNull private ResourceKey<Block> getKey(String namespace, String path) {
		return ResourceKey.create(this.registryKey, Identifier.fromNamespaceAndPath(namespace, path));
	}

	private void generateCompat() {
		this.valueLookupBuilder(ConventionalBlockTags.CHESTS)
			.add(WWBlocks.STONE_CHEST);

		this.valueLookupBuilder(ConventionalBlockTags.STRIPPED_LOGS)
			.add(WWBlocks.STRIPPED_BAOBAB_LOG)
			.add(WWBlocks.STRIPPED_WILLOW_LOG)
			.add(WWBlocks.STRIPPED_CYPRESS_LOG)
			.add(WWBlocks.STRIPPED_PALM_LOG)
			.add(WWBlocks.STRIPPED_MAPLE_LOG)
			.addOptionalTag(WWBlockTags.STRIPPED_HOLLOWED_LOGS);
	}

	private void generateLib() {
		this.valueLookupBuilder(FrozenBlockTags.DRIPSTONE_CAN_DRIP_ON)
			.add(Blocks.DIRT)
			.add(WWBlocks.SCORCHED_SAND, WWBlocks.SCORCHED_RED_SAND);
	}

	private void generateFeatures() {
		this.valueLookupBuilder(WWBlockTags.STONE_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.MUD)
			.add(Blocks.SAND);

		this.valueLookupBuilder(WWBlockTags.STONE_TRANSITION_PLACEABLE)
			.add(Blocks.STONE);

		this.valueLookupBuilder(WWBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.MUD);

		this.valueLookupBuilder(WWBlockTags.GRAVEL_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.MUD)
			.add(Blocks.SAND)
			.add(Blocks.STONE);

		this.valueLookupBuilder(WWBlockTags.GRAVEL_TRANSITION_PLACEABLE)
			.add(Blocks.GRAVEL);

		this.valueLookupBuilder(WWBlockTags.SAND_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.MUD)
			.add(Blocks.GRAVEL)
			.add(Blocks.STONE);

		this.valueLookupBuilder(WWBlockTags.SAND_TRANSITION_PLACEABLE)
			.add(Blocks.SAND);

		this.valueLookupBuilder(WWBlockTags.RED_SAND_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.MUD)
			.add(Blocks.GRAVEL)
			.add(Blocks.SAND)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.valueLookupBuilder(WWBlockTags.RED_SAND_TRANSITION_PLACEABLE)
			.add(Blocks.RED_SAND)
			.addOptionalTag(BlockTags.TERRACOTTA);

		this.valueLookupBuilder(WWBlockTags.MUD_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT)
			.add(Blocks.CLAY)
			.add(Blocks.SAND);

		this.valueLookupBuilder(WWBlockTags.MUD_TRANSITION_PLACEABLE)
			.add(Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS);

		this.valueLookupBuilder(WWBlockTags.MUD_PATH_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT)
			.add(Blocks.CLAY)
			.add(Blocks.SAND);

		this.valueLookupBuilder(WWBlockTags.COARSE_PATH_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT)
			.add(Blocks.PODZOL);

		this.valueLookupBuilder(WWBlockTags.COARSE_CLEARING_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT)
			.add(Blocks.PODZOL)
			.add(Blocks.GRAVEL);

		this.valueLookupBuilder(WWBlockTags.ROOTED_DIRT_PATH_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.add(Blocks.COARSE_DIRT);

		this.valueLookupBuilder(WWBlockTags.UNDER_WATER_SAND_PATH_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT)
			.add(Blocks.GRAVEL);

		this.valueLookupBuilder(WWBlockTags.UNDER_WATER_GRAVEL_PATH_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT)
			.add(Blocks.STONE);

		this.valueLookupBuilder(WWBlockTags.UNDER_WATER_CLAY_PATH_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT)
			.add(Blocks.GRAVEL)
			.add(Blocks.STONE);

		this.valueLookupBuilder(WWBlockTags.BEACH_CLAY_PATH_REPLACEABLE)
			.add(Blocks.SAND);

		this.valueLookupBuilder(WWBlockTags.RIVER_GRAVEL_PATH_REPLACEABLE)
			.add(Blocks.SAND);

		this.valueLookupBuilder(WWBlockTags.SAND_PATH_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.valueLookupBuilder(WWBlockTags.GRAVEL_PATH_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.valueLookupBuilder(WWBlockTags.GRAVEL_CLEARING_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.valueLookupBuilder(WWBlockTags.GRAVEL_AND_PALE_MOSS_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.COARSE_DIRT)
			.add(Blocks.PALE_MOSS_BLOCK)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.valueLookupBuilder(WWBlockTags.STONE_PATH_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT)
			.addOptionalTag(BlockTags.SAND);

		this.valueLookupBuilder(WWBlockTags.PACKED_MUD_PATH_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT);

		this.valueLookupBuilder(WWBlockTags.MOSS_PATH_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.PODZOL);

		this.valueLookupBuilder(WWBlockTags.OCEAN_MOSS_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.SAND);

		this.valueLookupBuilder(WWBlockTags.SANDSTONE_PATH_REPLACEABLE)
			.add(Blocks.SAND);

		this.valueLookupBuilder(WWBlockTags.SMALL_COARSE_DIRT_PATH_REPLACEABLE)
			.add(Blocks.RED_SAND);

		this.valueLookupBuilder(WWBlockTags.PACKED_MUD_PATH_BADLANDS_REPLACEABLE)
			.add(Blocks.RED_SAND, Blocks.RED_SANDSTONE)
			.addOptionalTag(BlockTags.TERRACOTTA);

		this.valueLookupBuilder(WWBlockTags.POLLEN_FEATURE_PLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.addOptionalTag(BlockTags.LEAVES)
			.addOptionalTag(BlockTags.OVERWORLD_NATURAL_LOGS);

		this.valueLookupBuilder(WWBlockTags.AUBURN_CREEPING_MOSS_FEATURE_PLACEABLE)
			.add(Blocks.PRISMARINE)
			.add(Blocks.PRISMARINE_BRICKS)
			.add(Blocks.DARK_PRISMARINE)
			.add(Blocks.SEA_LANTERN)
			.add(Blocks.CLAY)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.STONE_BRICKS)
			.addOptionalTag(BlockTags.LOGS)
			.addOptionalTag(BlockTags.PLANKS)
			.addOptionalTag(BlockTags.SLABS)
			.addOptionalTag(BlockTags.STAIRS)
			.addOptionalTag(BlockTags.DOORS)
			.addOptionalTag(BlockTags.TRAPDOORS)
			.addOptionalTag(BlockTags.LEAVES)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.BASE_STONE_NETHER);

		this.valueLookupBuilder(WWBlockTags.BARNACLES_FEATURE_PLACEABLE)
			.add(Blocks.PRISMARINE)
			.add(Blocks.PRISMARINE_BRICKS)
			.add(Blocks.DARK_PRISMARINE)
			.add(Blocks.SEA_LANTERN)
			.add(Blocks.CLAY)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.STONE_BRICKS)
			.addOptionalTag(BlockTags.LOGS)
			.addOptionalTag(BlockTags.PLANKS)
			.addOptionalTag(BlockTags.SLABS)
			.addOptionalTag(BlockTags.STAIRS)
			.addOptionalTag(BlockTags.DOORS)
			.addOptionalTag(BlockTags.TRAPDOORS)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.valueLookupBuilder(WWBlockTags.BARNACLES_FEATURE_PLACEABLE_STRUCTURE)
			.add(Blocks.PRISMARINE)
			.add(Blocks.PRISMARINE_BRICKS)
			.add(Blocks.DARK_PRISMARINE)
			.add(Blocks.SEA_LANTERN)
			.addOptionalTag(BlockTags.STONE_BRICKS)
			.addOptionalTag(BlockTags.LOGS)
			.addOptionalTag(BlockTags.PLANKS)
			.addOptionalTag(BlockTags.SLABS)
			.addOptionalTag(BlockTags.STAIRS)
			.addOptionalTag(BlockTags.DOORS)
			.addOptionalTag(BlockTags.TRAPDOORS);

		this.valueLookupBuilder(WWBlockTags.SEA_ANEMONE_FEATURE_CANNOT_PLACE)
			.add(Blocks.MOSS_BLOCK);

		this.valueLookupBuilder(WWBlockTags.TERMITE_DISK_REPLACEABLE)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.valueLookupBuilder(WWBlockTags.TERMITE_DISK_BLOCKS)
			.add(Blocks.COARSE_DIRT)
			.add(Blocks.PACKED_MUD);

		this.valueLookupBuilder(WWBlockTags.BLUE_NEMATOCYST_FEATURE_PLACEABLE)
			.add(Blocks.CLAY)
			.add(Blocks.DRIPSTONE_BLOCK)
			.add(Blocks.CALCITE)
			.add(WWBlocks.PEARLESCENT_BLUE_MESOGLEA)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.valueLookupBuilder(WWBlockTags.PURPLE_NEMATOCYST_FEATURE_PLACEABLE)
			.add(Blocks.CLAY)
			.add(Blocks.DRIPSTONE_BLOCK)
			.add(Blocks.CALCITE)
			.add(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.valueLookupBuilder(WWBlockTags.SHELF_FUNGI_FEATURE_PLACEABLE)
			.add(Blocks.MUSHROOM_STEM)
			.addOptionalTag(BlockTags.OVERWORLD_NATURAL_LOGS);

		this.valueLookupBuilder(WWBlockTags.SHELF_FUNGI_FEATURE_PLACEABLE_NETHER)
			.addOptionalTag(BlockTags.CRIMSON_STEMS)
			.addOptionalTag(BlockTags.WARPED_STEMS);

		this.valueLookupBuilder(WWBlockTags.SCORCHED_SAND_FEATURE_INNER_REPLACEABLE)
			.add(Blocks.SAND, WWBlocks.SCORCHED_SAND);

		this.valueLookupBuilder(WWBlockTags.SCORCHED_SAND_FEATURE_REPLACEABLE)
			.add(Blocks.SAND);

		this.valueLookupBuilder(WWBlockTags.RED_SCORCHED_SAND_FEATURE_INNER_REPLACEABLE)
			.add(Blocks.RED_SAND, WWBlocks.SCORCHED_RED_SAND);

		this.valueLookupBuilder(WWBlockTags.RED_SCORCHED_SAND_FEATURE_REPLACEABLE)
			.add(Blocks.RED_SAND);

		this.valueLookupBuilder(WWBlockTags.CAVE_ICE_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.add(Blocks.SNOW_BLOCK)
			.add(Blocks.SNOW);

		this.valueLookupBuilder(WWBlockTags.CAVE_FRAGILE_ICE_REPLACEABLE)
			.addOptionalTag(BlockTags.ICE)
			.addOptionalTag(WWBlockTags.CAVE_ICE_REPLACEABLE);

		this.valueLookupBuilder(WWBlockTags.DIORITE_ICE_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.add(Blocks.SNOW_BLOCK)
			.add(Blocks.SNOW)
			.add(Blocks.ICE)
			.add(Blocks.BLUE_ICE)
			.add(Blocks.PACKED_ICE);

		this.valueLookupBuilder(WWBlockTags.MESOGLEA_PATH_REPLACEABLE)
			.add(Blocks.CLAY)
			.add(Blocks.DRIPSTONE_BLOCK)
			.add(Blocks.CALCITE)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.valueLookupBuilder(WWBlockTags.MAGMA_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.valueLookupBuilder(WWBlockTags.NETHER_GEYSER_REPLACEABLE)
			.addOptionalTag(BlockTags.BASE_STONE_NETHER);

		this.valueLookupBuilder(WWBlockTags.OASIS_PATH_REPLACEABLE)
			.add(Blocks.SAND)
			.add(Blocks.SANDSTONE);

		this.valueLookupBuilder(WWBlockTags.COARSE_DIRT_DISK_REPLACEABLE)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.GRASS_BLOCK, Blocks.DIRT)
			.add(Blocks.CLAY)
			.add(Blocks.PODZOL);

		this.valueLookupBuilder(WWBlockTags.RIVER_POOL_REPLACEABLE)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.DIRT)
			.add(Blocks.GRAVEL)
			.add(Blocks.CLAY);

		this.valueLookupBuilder(WWBlockTags.FALLEN_TREE_PLACEABLE)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.DIRT)
			.add(Blocks.GRAVEL)
			.add(Blocks.CLAY)
			.add(Blocks.MOSS_BLOCK)
			.add(Blocks.PACKED_MUD)
			.add(Blocks.SNOW)
			.add(WWBlocks.AUBURN_MOSS_BLOCK);

		this.valueLookupBuilder(WWBlockTags.PACKED_MUD_REPLACEABLE)
			.add(Blocks.STONE)
			.add(Blocks.DIRT)
			.add(Blocks.SANDSTONE);

		this.valueLookupBuilder(WWBlockTags.SMALL_SPONGE_GROWS_ON)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.GRAVEL)
			.add(Blocks.SPONGE)
			.add(Blocks.CLAY)
			.add(Blocks.MOSS_BLOCK)
			.add(WWBlocks.AUBURN_MOSS_BLOCK)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.valueLookupBuilder(WWBlockTags.BASIN_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT)
			.add(Blocks.PODZOL)
			.add(Blocks.MOSS_BLOCK)
			.add(WWBlocks.AUBURN_MOSS_BLOCK);

		this.valueLookupBuilder(WWBlockTags.HYDROTHERMAL_VENT_REPLACEABLE)
			.add(Blocks.CLAY)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.valueLookupBuilder(WWBlockTags.CATTAIL_FEATURE_PLACEABLE)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.CLAY);

		this.valueLookupBuilder(WWBlockTags.CATTAIL_FEATURE_MUD_PLACEABLE)
			.add(Blocks.MUD);

		this.valueLookupBuilder(WWBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
			.addOptionalTag(BlockTags.TERRACOTTA)
			.addOptionalTag(BlockTags.DIRT);

		this.valueLookupBuilder(WWBlockTags.SAND_POOL_REPLACEABLE)
			.add(Blocks.SAND);
	}

	private void generateTags() {
		this.valueLookupBuilder(WWBlockTags.MESOGLEA)
			.add(WWBlocks.BLUE_MESOGLEA)
			.add(WWBlocks.PEARLESCENT_BLUE_MESOGLEA)
			.add(WWBlocks.LIME_MESOGLEA)
			.add(WWBlocks.PINK_MESOGLEA)
			.add(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA)
			.add(WWBlocks.RED_MESOGLEA)
			.add(WWBlocks.YELLOW_MESOGLEA);

		this.valueLookupBuilder(WWBlockTags.NEMATOCYSTS)
			.add(WWBlocks.BLUE_NEMATOCYST)
			.add(WWBlocks.PEARLESCENT_BLUE_NEMATOCYST)
			.add(WWBlocks.LIME_NEMATOCYST)
			.add(WWBlocks.PINK_NEMATOCYST)
			.add(WWBlocks.PEARLESCENT_PURPLE_NEMATOCYST)
			.add(WWBlocks.RED_NEMATOCYST)
			.add(WWBlocks.YELLOW_NEMATOCYST);

		this.valueLookupBuilder(WWBlockTags.FROGLIGHT_GOOP)
			.add(WWBlocks.PEARLESCENT_FROGLIGHT_GOOP)
			.add(WWBlocks.PEARLESCENT_FROGLIGHT_GOOP_BODY)
			.add(WWBlocks.VERDANT_FROGLIGHT_GOOP)
			.add(WWBlocks.VERDANT_FROGLIGHT_GOOP_BODY)
			.add(WWBlocks.OCHRE_FROGLIGHT_GOOP)
			.add(WWBlocks.OCHRE_FROGLIGHT_GOOP_BODY);

		this.valueLookupBuilder(WWBlockTags.ICICLE_FALLS_FROM)
			.add(Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE, WWBlocks.FRAGILE_ICE);

		this.valueLookupBuilder(WWBlockTags.ICICLE_GROWS_WHEN_UNDER)
			.add(Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE, WWBlocks.FRAGILE_ICE);

		this.valueLookupBuilder(WWBlockTags.STOPS_TUMBLEWEED)
			.add(Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS)
			.add(Blocks.SLIME_BLOCK)
			.add(Blocks.IRON_BARS)
			.add(Blocks.HONEY_BLOCK);

		this.valueLookupBuilder(WWBlockTags.SPLITS_COCONUT)
			.addOptionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.BASE_STONE_NETHER)
			.addOptionalTag(BlockTags.DRAGON_IMMUNE)
			.addOptionalTag(BlockTags.WITHER_IMMUNE)
			.addOptionalTag(BlockTags.LOGS);

		this.valueLookupBuilder(WWBlockTags.FIREFLY_HIDEABLE_BLOCKS)
			.add(Blocks.SHORT_GRASS, Blocks.TALL_GRASS)
			.add(Blocks.FERN, Blocks.LARGE_FERN)
			.add(Blocks.BUSH)
			.add(WWBlocks.FROZEN_SHORT_GRASS, WWBlocks.FROZEN_TALL_GRASS)
			.add(WWBlocks.FROZEN_FERN, WWBlocks.FROZEN_LARGE_FERN)
			.add(WWBlocks.FROZEN_BUSH)
			.add(Blocks.PEONY)
			.add(Blocks.ROSE_BUSH)
			.add(Blocks.DEAD_BUSH)
			.add(WWBlocks.CATTAIL)
			.add(WWBlocks.MILKWEED)
			.add(WWBlocks.SHRUB)
			.add(Blocks.FIREFLY_BUSH)
			.addOptionalTag(BlockTags.SMALL_FLOWERS);

		this.valueLookupBuilder(WWBlockTags.CRAB_HIDEABLE)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.CLAY)
			.add(Blocks.GRAVEL);

		this.valueLookupBuilder(WWBlockTags.OSTRICH_BEAK_BURYABLE)
			.addOptionalTag(BlockTags.MINEABLE_WITH_SHOVEL)
			.addOptionalTag(BlockTags.MINEABLE_WITH_HOE)
			.addOptionalTag(BlockTags.WOOL);

		this.valueLookupBuilder(WWBlockTags.PENGUIN_IGNORE_FRICTION)
			.addOptionalTag(BlockTags.ICE);

		this.valueLookupBuilder(WWBlockTags.PENGUINS_SPAWNABLE_ON)
			.add(Blocks.ICE)
			.add(Blocks.SNOW_BLOCK)
			.add(Blocks.SAND)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.ANIMALS_SPAWNABLE_ON);

		this.valueLookupBuilder(FrozenBlockTags.BLOWING_CAN_PASS_THROUGH)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.valueLookupBuilder(WWBlockTags.NO_LIGHTNING_BLOCK_PARTICLES)
			.addOptionalTag(BlockTags.LIGHTNING_RODS);

		this.valueLookupBuilder(WWBlockTags.NO_LIGHTNING_SMOKE_PARTICLES)
			.addOptionalTag(BlockTags.LIGHTNING_RODS);

		this.valueLookupBuilder(ConventionalBlockTags.GLASS_BLOCKS)
			.add(WWBlocks.ECHO_GLASS);

		this.valueLookupBuilder(WWBlockTags.SHRUB_MAY_PLACE_ON)
			.addOptionalTag(BlockTags.DRY_VEGETATION_MAY_PLACE_ON);

		this.valueLookupBuilder(WWBlockTags.MYCELIUM_GROWTH_REPLACEABLE)
			.add(Blocks.SHORT_GRASS)
			.add(Blocks.FERN);

		this.valueLookupBuilder(WWBlockTags.RED_MOSS_REPLACEABLE)
			.addOptionalTag(BlockTags.MOSS_REPLACEABLE)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.CLAY)
			.add(Blocks.GRAVEL);

		this.valueLookupBuilder(WWBlockTags.SNOW_GENERATION_CAN_SEARCH_THROUGH)
			.add(Blocks.LADDER)
			.add(WWBlocks.ICICLE)
			.addOptionalTag(BlockTags.LEAVES)
			.addOptionalTag(BlockTags.WALLS)
			.addOptionalTag(BlockTags.FENCE_GATES)
			.addOptionalTag(BlockTags.FENCES);
	}

	private void generateDeepDark() {
		this.valueLookupBuilder(WWBlockTags.ANCIENT_CITY_BLOCKS)
			.add(Blocks.COBBLED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE_STAIRS, Blocks.COBBLED_DEEPSLATE_SLAB, Blocks.COBBLED_DEEPSLATE_WALL)
			.add(Blocks.POLISHED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE_STAIRS, Blocks.POLISHED_DEEPSLATE_SLAB, Blocks.POLISHED_DEEPSLATE_WALL)
			.add(Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_BRICK_STAIRS, Blocks.DEEPSLATE_BRICK_SLAB, Blocks.DEEPSLATE_BRICK_WALL)
			.add(Blocks.CRACKED_DEEPSLATE_BRICKS)
			.add(Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_TILE_STAIRS)
			.add(Blocks.CHISELED_DEEPSLATE)
			.add(Blocks.REINFORCED_DEEPSLATE)
			.add(Blocks.POLISHED_BASALT, Blocks.SMOOTH_BASALT)
			.add(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_FENCE)
			.add(Blocks.LIGHT_BLUE_CARPET, Blocks.BLUE_CARPET)
			.add(Blocks.LIGHT_BLUE_WOOL, Blocks.GRAY_WOOL)
			.add(Blocks.CHEST, WWBlocks.STONE_CHEST)
			.add(Blocks.LADDER)
			.add(Blocks.CANDLE, Blocks.WHITE_CANDLE)
			.add(Blocks.SOUL_LANTERN, Blocks.SOUL_FIRE)
			.add(Blocks.SOUL_SAND);

		this.valueLookupBuilder(WWBlockTags.SCULK_SLAB_REPLACEABLE)
			.add(Blocks.STONE_SLAB, Blocks.GRANITE_SLAB, Blocks.DIORITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.BLACKSTONE_SLAB, WWBlocks.GABBRO_SLAB);

		this.valueLookupBuilder(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)
			.add(Blocks.COBBLED_DEEPSLATE_SLAB, Blocks.POLISHED_DEEPSLATE_SLAB, Blocks.DEEPSLATE_BRICK_SLAB, Blocks.DEEPSLATE_TILE_SLAB)
			.addOptionalTag(WWBlockTags.SCULK_SLAB_REPLACEABLE);

		this.valueLookupBuilder(WWBlockTags.SCULK_STAIR_REPLACEABLE)
			.add(Blocks.STONE_STAIRS, Blocks.GRANITE_STAIRS, Blocks.DIORITE_STAIRS, Blocks.ANDESITE_STAIRS, Blocks.BLACKSTONE_STAIRS, WWBlocks.GABBRO_STAIRS);

		this.valueLookupBuilder(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)
			.add(Blocks.COBBLED_DEEPSLATE_STAIRS, Blocks.POLISHED_DEEPSLATE_STAIRS, Blocks.DEEPSLATE_BRICK_STAIRS, Blocks.DEEPSLATE_TILE_STAIRS)
			.addOptionalTag(WWBlockTags.SCULK_STAIR_REPLACEABLE);

		this.valueLookupBuilder(WWBlockTags.SCULK_WALL_REPLACEABLE)
			.add(Blocks.COBBLESTONE_WALL, Blocks.GRANITE_WALL, Blocks.DIORITE_WALL, Blocks.ANDESITE_WALL, Blocks.BLACKSTONE_WALL, WWBlocks.GABBRO_WALL);

		this.valueLookupBuilder(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)
			.add(Blocks.COBBLED_DEEPSLATE_WALL, Blocks.POLISHED_DEEPSLATE_WALL, Blocks.DEEPSLATE_BRICK_WALL, Blocks.DEEPSLATE_TILE_WALL)
			.addOptionalTag(WWBlockTags.SCULK_WALL_REPLACEABLE);

		this.valueLookupBuilder(BlockTags.OCCLUDES_VIBRATION_SIGNALS)
			.add(WWBlocks.ECHO_GLASS);
	}

	private void generateHollowedAndTermites() {
		this.valueLookupBuilder(WWBlockTags.HOLLOWED_ACACIA_LOGS)
			.add(WWBlocks.HOLLOWED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_BIRCH_LOGS)
			.add(WWBlocks.HOLLOWED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_CHERRY_LOGS)
			.add(WWBlocks.HOLLOWED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_CRIMSON_STEMS)
			.add(WWBlocks.HOLLOWED_CRIMSON_STEM, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_DARK_OAK_LOGS)
			.add(WWBlocks.HOLLOWED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_JUNGLE_LOGS)
			.add(WWBlocks.HOLLOWED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_MANGROVE_LOGS)
			.add(WWBlocks.HOLLOWED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_OAK_LOGS)
			.add(WWBlocks.HOLLOWED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_SPRUCE_LOGS)
			.add(WWBlocks.HOLLOWED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_WARPED_STEMS)
			.add(WWBlocks.HOLLOWED_WARPED_STEM, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_BAOBAB_LOGS)
			.add(WWBlocks.HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_WILLOW_LOGS)
			.add(WWBlocks.HOLLOWED_WILLOW_LOG, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_CYPRESS_LOGS)
			.add(WWBlocks.HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_PALM_LOGS)
			.add(WWBlocks.HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_MAPLE_LOGS)
			.add(WWBlocks.HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_PALE_OAK_LOGS)
			.add(WWBlocks.HOLLOWED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWBlockTags.HOLLOWED_ACACIA_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_BIRCH_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_CHERRY_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_CRIMSON_STEMS)
			.addOptionalTag(WWBlockTags.HOLLOWED_DARK_OAK_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_JUNGLE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_MANGROVE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_OAK_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_SPRUCE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_WARPED_STEMS)
			.addOptionalTag(WWBlockTags.HOLLOWED_BAOBAB_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_WILLOW_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_CYPRESS_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_PALM_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_PALE_OAK_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_MAPLE_LOGS);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_LOGS_DONT_BURN)
			.addOptionalTag(WWBlockTags.HOLLOWED_CRIMSON_STEMS)
			.addOptionalTag(WWBlockTags.HOLLOWED_WARPED_STEMS);

		this.valueLookupBuilder(WWBlockTags.HOLLOWED_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS_DONT_BURN);

		this.valueLookupBuilder(WWBlockTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.add(WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_PALM_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG);

		this.valueLookupBuilder(WWBlockTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN)
			.add(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM)
			.add(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

		this.valueLookupBuilder(WWBlockTags.STRIPPED_HOLLOWED_LOGS)
			.addOptionalTag(WWBlockTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWBlockTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN);

		this.valueLookupBuilder(WWBlockTags.NON_OVERRIDEN_FALLING_LEAVES)
			.add(Blocks.CHERRY_LEAVES)
			.add(Blocks.PALE_OAK_LEAVES);

		this.valueLookupBuilder(WWBlockTags.BLOCKS_TERMITE)
			.addOptionalTag(ConventionalBlockTags.GLASS_BLOCKS)
			.addOptionalTag(ConventionalBlockTags.GLASS_PANES);

		this.valueLookupBuilder(WWBlockTags.KILLS_TERMITE)
			.add(Blocks.WATER, Blocks.WATER_CAULDRON)
			.add(Blocks.LAVA, Blocks.LAVA_CAULDRON)
			.add(Blocks.POWDER_SNOW, Blocks.POWDER_SNOW_CAULDRON)
			.add(Blocks.CRIMSON_ROOTS, Blocks.NETHER_SPROUTS)
			.add(Blocks.CRIMSON_PLANKS, Blocks.WARPED_PLANKS)
			.add(Blocks.WEEPING_VINES, Blocks.WEEPING_VINES_PLANT)
			.add(Blocks.TWISTING_VINES, Blocks.TWISTING_VINES_PLANT)
			.add(Blocks.CRIMSON_SLAB, Blocks.WARPED_SLAB)
			.add(Blocks.CRIMSON_PRESSURE_PLATE, Blocks.WARPED_PRESSURE_PLATE)
			.add(Blocks.CRIMSON_FENCE, Blocks.WARPED_FENCE)
			.add(Blocks.CRIMSON_TRAPDOOR, Blocks.WARPED_TRAPDOOR)
			.add(Blocks.CRIMSON_FENCE_GATE, Blocks.WARPED_FENCE_GATE)
			.add(Blocks.CRIMSON_STAIRS, Blocks.WARPED_STAIRS)
			.add(Blocks.CRIMSON_BUTTON, Blocks.WARPED_BUTTON)
			.add(Blocks.CRIMSON_DOOR, Blocks.WARPED_DOOR)
			.add(Blocks.CRIMSON_SIGN, Blocks.WARPED_SIGN)
			.add(Blocks.CRIMSON_WALL_SIGN, Blocks.WARPED_WALL_SIGN)
			.add(Blocks.CRIMSON_HANGING_SIGN, Blocks.WARPED_HANGING_SIGN)
			.add(Blocks.CRIMSON_WALL_HANGING_SIGN, Blocks.WARPED_WALL_HANGING_SIGN)
			.add(Blocks.CRIMSON_STEM, Blocks.WARPED_STEM)
			.add(Blocks.STRIPPED_CRIMSON_STEM, Blocks.STRIPPED_WARPED_STEM)
			.add(Blocks.STRIPPED_CRIMSON_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE)
			.add(Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM)
			.add(Blocks.CRIMSON_FUNGUS, Blocks.WARPED_FUNGUS)
			.add(Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK)
			.add(Blocks.NETHER_WART)
			.add(Blocks.REDSTONE_WIRE, Blocks.REDSTONE_BLOCK, Blocks.REDSTONE_TORCH, Blocks.REDSTONE_WALL_TORCH)
			.add(WWBlocks.HOLLOWED_CRIMSON_STEM, WWBlocks.HOLLOWED_WARPED_STEM)
			.add(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM)
			.addOptionalTag(BlockTags.WARPED_STEMS)
			.addOptionalTag(BlockTags.CRIMSON_STEMS)
			.add(Blocks.RESIN_CLUMP)
			.add(Blocks.CREAKING_HEART)
			.add(Blocks.CLOSED_EYEBLOSSOM, Blocks.OPEN_EYEBLOSSOM)
			.add(WWBlocks.PALE_MUSHROOM, WWBlocks.PALE_SHELF_FUNGI, WWBlocks.PALE_MUSHROOM_BLOCK);
	}

	private void generateMinecraft() {
		this.valueLookupBuilder(BlockTags.MINEABLE_WITH_AXE)
			.add(Blocks.CACTUS)
			.add(Blocks.SWEET_BERRY_BUSH)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS)
			.add(WWBlocks.TUMBLEWEED, WWBlocks.TUMBLEWEED_PLANT)
			.add(WWBlocks.PRICKLY_PEAR_CACTUS)
			.add(WWBlocks.MYCELIUM_GROWTH)
			.add(WWBlocks.BROWN_SHELF_FUNGI, WWBlocks.RED_SHELF_FUNGI, WWBlocks.PALE_SHELF_FUNGI)
			.add(WWBlocks.CRIMSON_SHELF_FUNGI, WWBlocks.WARPED_SHELF_FUNGI)
			.add(WWBlocks.PALE_MUSHROOM_BLOCK, WWBlocks.PALE_MUSHROOM)
			.add(WWBlocks.CLOVERS)
			.add(WWBlocks.FROZEN_SHORT_GRASS, WWBlocks.FROZEN_TALL_GRASS)
			.add(WWBlocks.FROZEN_FERN, WWBlocks.FROZEN_LARGE_FERN)
			.add(WWBlocks.FROZEN_BUSH)
			.add(WWBlocks.BARNACLES);

		this.valueLookupBuilder(BlockTags.MINEABLE_WITH_HOE)
			.add(Blocks.SWEET_BERRY_BUSH)
			.add(WWBlocks.OSSEOUS_SCULK)
			.add(WWBlocks.SCULK_SLAB, WWBlocks.SCULK_STAIRS, WWBlocks.SCULK_WALL)
			.add(WWBlocks.HANGING_TENDRIL)
			.add(WWBlocks.BAOBAB_LEAVES)
			.add(WWBlocks.WILLOW_LEAVES)
			.add(WWBlocks.CYPRESS_LEAVES)
			.add(WWBlocks.PALM_FRONDS)
			.add(WWBlocks.YELLOW_MAPLE_LEAVES)
			.add(WWBlocks.ORANGE_MAPLE_LEAVES)
			.add(WWBlocks.RED_MAPLE_LEAVES)
			.add(WWBlocks.TUMBLEWEED_PLANT)
			.add(WWBlocks.TUMBLEWEED)
			.add(WWBlocks.PRICKLY_PEAR_CACTUS)
			.add(WWBlocks.SPONGE_BUD)
			.add(WWBlocks.BARNACLES)
			.add(WWBlocks.PHLOX)
			.add(WWBlocks.LANTANAS)
			.add(WWBlocks.AUBURN_MOSS_BLOCK)
			.add(WWBlocks.AUBURN_MOSS_CARPET)
			.add(WWBlocks.AUBURN_CREEPING_MOSS)
			.addOptionalTag(WWBlockTags.FROGLIGHT_GOOP);

		this.valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(WWBlocks.STONE_CHEST)
			.add(WWBlocks.NULL_BLOCK)
			.add(WWBlocks.DISPLAY_LANTERN)
			.add(WWBlocks.SCORCHED_SAND, WWBlocks.SCORCHED_RED_SAND)
			.add(WWBlocks.CHISELED_MUD_BRICKS)
			.add(WWBlocks.CRACKED_MUD_BRICKS)
			.add(WWBlocks.MOSSY_MUD_BRICKS, WWBlocks.MOSSY_MUD_BRICK_STAIRS, WWBlocks.MOSSY_MUD_BRICK_SLAB, WWBlocks.MOSSY_MUD_BRICK_WALL)

			.add(WWBlocks.GABBRO)
			.add(WWBlocks.GEYSER)
			.add(WWBlocks.GABBRO_STAIRS, WWBlocks.GABBRO_SLAB, WWBlocks.GABBRO_WALL)
			.add(WWBlocks.POLISHED_GABBRO, WWBlocks.POLISHED_GABBRO_STAIRS, WWBlocks.POLISHED_GABBRO_SLAB, WWBlocks.POLISHED_GABBRO_WALL)
			.add(WWBlocks.CHISELED_GABBRO_BRICKS)
			.add(WWBlocks.GABBRO_BRICKS)
			.add(WWBlocks.CRACKED_GABBRO_BRICKS)
			.add(WWBlocks.GABBRO_BRICK_STAIRS, WWBlocks.GABBRO_BRICK_SLAB, WWBlocks.GABBRO_BRICK_WALL)
			.add(WWBlocks.MOSSY_GABBRO_BRICKS, WWBlocks.MOSSY_GABBRO_BRICK_STAIRS, WWBlocks.MOSSY_GABBRO_BRICK_SLAB, WWBlocks.MOSSY_GABBRO_BRICK_WALL)

			.add(WWBlocks.FRAGILE_ICE)
			.add(WWBlocks.ICICLE)

			.add(WWBlocks.BARNACLES);

		this.valueLookupBuilder(BlockTags.MINEABLE_WITH_SHOVEL)
			.add(WWBlocks.SCORCHED_SAND)
			.add(WWBlocks.SCORCHED_RED_SAND)
			.add(WWBlocks.TERMITE_MOUND)
			.add(WWBlocks.PLANKTON)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.valueLookupBuilder(BlockTags.SWORD_EFFICIENT)
			.add(WWBlocks.SHRUB)
			.add(WWBlocks.TUMBLEWEED, WWBlocks.TUMBLEWEED_PLANT)
			.add(WWBlocks.MILKWEED)
			.add(WWBlocks.DATURA)
			.add(WWBlocks.CATTAIL)
			.add(WWBlocks.FLOWERING_LILY_PAD)
			.add(WWBlocks.ALGAE)
			.add(WWBlocks.PLANKTON)
			.add(WWBlocks.BROWN_SHELF_FUNGI)
			.add(WWBlocks.RED_SHELF_FUNGI)
			.add(WWBlocks.PALE_SHELF_FUNGI)
			.add(WWBlocks.CRIMSON_SHELF_FUNGI)
			.add(WWBlocks.WARPED_SHELF_FUNGI)
			.add(WWBlocks.SPONGE_BUD)
			.add(WWBlocks.BARNACLES)
			.add(WWBlocks.PRICKLY_PEAR_CACTUS)
			.add(WWBlocks.MYCELIUM_GROWTH)
			.add(WWBlocks.PALE_MUSHROOM)
			.add(WWBlocks.PHLOX)
			.add(WWBlocks.LANTANAS)
			.add(WWBlocks.CLOVERS)
			.add(WWBlocks.FROZEN_SHORT_GRASS, WWBlocks.FROZEN_TALL_GRASS)
			.add(WWBlocks.FROZEN_FERN, WWBlocks.FROZEN_LARGE_FERN)
			.add(WWBlocks.FROZEN_BUSH)
			.add(WWBlocks.AUBURN_MOSS_CARPET)
			.add(WWBlocks.AUBURN_CREEPING_MOSS)
			.addOptionalTag(WWBlockTags.NEMATOCYSTS)
			.addOptionalTag(WWBlockTags.FROGLIGHT_GOOP);

		this.valueLookupBuilder(BlockTags.ENDERMAN_HOLDABLE)
			.add(WWBlocks.PALE_MUSHROOM);

		this.valueLookupBuilder(BlockTags.SAND)
			.add(WWBlocks.SCORCHED_SAND, WWBlocks.SCORCHED_RED_SAND);

		this.valueLookupBuilder(BlockTags.DIRT)
			.add(WWBlocks.AUBURN_MOSS_BLOCK);

		this.valueLookupBuilder(BlockTags.SNIFFER_DIGGABLE_BLOCK)
			.add(WWBlocks.AUBURN_MOSS_BLOCK);

		this.valueLookupBuilder(BlockTags.NEEDS_STONE_TOOL)
			.add(WWBlocks.GABBRO)
			.add(WWBlocks.GEYSER)
			.add(WWBlocks.GABBRO_STAIRS, WWBlocks.GABBRO_SLAB, WWBlocks.GABBRO_WALL)
			.add(WWBlocks.POLISHED_GABBRO, WWBlocks.POLISHED_GABBRO_STAIRS, WWBlocks.POLISHED_GABBRO_SLAB, WWBlocks.POLISHED_GABBRO_WALL)
			.add(WWBlocks.CHISELED_GABBRO_BRICKS)
			.add(WWBlocks.GABBRO_BRICKS)
			.add(WWBlocks.CRACKED_GABBRO_BRICKS)
			.add(WWBlocks.GABBRO_BRICK_STAIRS, WWBlocks.GABBRO_BRICK_SLAB, WWBlocks.GABBRO_BRICK_WALL)
			.add(WWBlocks.MOSSY_GABBRO_BRICKS, WWBlocks.MOSSY_GABBRO_BRICK_STAIRS, WWBlocks.MOSSY_GABBRO_BRICK_SLAB, WWBlocks.MOSSY_GABBRO_BRICK_WALL);

		this.valueLookupBuilder(BlockTags.BASE_STONE_OVERWORLD)
			.add(WWBlocks.GABBRO);

		this.valueLookupBuilder(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
			.add(WWBlocks.GABBRO);

		this.valueLookupBuilder(BlockTags.STAIRS)
			.add(WWBlocks.SCULK_STAIRS)
			.add(WWBlocks.MOSSY_MUD_BRICK_STAIRS)
			.add(WWBlocks.GABBRO_STAIRS)
			.add(WWBlocks.POLISHED_GABBRO_STAIRS)
			.add(WWBlocks.GABBRO_BRICK_STAIRS)
			.add(WWBlocks.MOSSY_GABBRO_BRICK_STAIRS);

		this.valueLookupBuilder(BlockTags.SLABS)
			.add(WWBlocks.SCULK_SLAB)
			.add(WWBlocks.MOSSY_MUD_BRICK_SLAB)
			.add(WWBlocks.GABBRO_SLAB)
			.add(WWBlocks.POLISHED_GABBRO_SLAB)
			.add(WWBlocks.GABBRO_BRICK_SLAB)
			.add(WWBlocks.MOSSY_GABBRO_BRICK_SLAB);

		this.valueLookupBuilder(BlockTags.WALLS)
			.add(WWBlocks.SCULK_WALL)
			.add(WWBlocks.MOSSY_MUD_BRICK_WALL)
			.add(WWBlocks.GABBRO_WALL)
			.add(WWBlocks.POLISHED_GABBRO_WALL)
			.add(WWBlocks.GABBRO_BRICK_WALL)
			.add(WWBlocks.MOSSY_GABBRO_BRICK_WALL);

		this.valueLookupBuilder(BlockTags.WOODEN_SHELVES)
			.add(WWBlocks.BAOBAB_SHELF)
			.add(WWBlocks.WILLOW_SHELF)
			.add(WWBlocks.CYPRESS_SHELF)
			.add(WWBlocks.PALM_SHELF)
			.add(WWBlocks.MAPLE_SHELF);

		this.valueLookupBuilder(BlockTags.CLIMBABLE)
			.addOptionalTag(WWBlockTags.FROGLIGHT_GOOP);

		this.valueLookupBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS)
			.add(Blocks.COBWEB)
			.add(Blocks.LILY_PAD)
			.add(WWBlocks.FLOWERING_LILY_PAD)
			.add(WWBlocks.ALGAE)
			.add(WWBlocks.PLANKTON)
			.add(WWBlocks.TUMBLEWEED_PLANT)
			.add(WWBlocks.SPONGE_BUD)
			.add(WWBlocks.BARNACLES)
			.add(WWBlocks.PRICKLY_PEAR_CACTUS)
			.add(WWBlocks.POLLEN)
			.add(WWBlocks.PHLOX)
			.add(WWBlocks.LANTANAS);

		this.valueLookupBuilder(BlockTags.REPLACEABLE)
			.add(WWBlocks.MYCELIUM_GROWTH);

		this.valueLookupBuilder(BlockTags.GEODE_INVALID_BLOCKS)
			.add(WWBlocks.FRAGILE_ICE);

		this.valueLookupBuilder(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)
			.add(WWBlocks.FRAGILE_ICE);

		this.valueLookupBuilder(BlockTags.ICE)
			.add(WWBlocks.FRAGILE_ICE);

		this.valueLookupBuilder(BlockTags.REPLACEABLE_BY_TREES)
			.add(WWBlocks.MYCELIUM_GROWTH)
			.add(WWBlocks.DATURA)
			.add(WWBlocks.CATTAIL)
			.add(WWBlocks.MILKWEED)
			.add(WWBlocks.SHRUB)
			.add(WWBlocks.POLLEN)
			.add(WWBlocks.BARNACLES)
			.add(WWBlocks.PHLOX)
			.add(WWBlocks.LANTANAS)
			.add(WWBlocks.CLOVERS)
			.add(WWBlocks.FROZEN_SHORT_GRASS, WWBlocks.FROZEN_TALL_GRASS)
			.add(WWBlocks.FROZEN_FERN, WWBlocks.FROZEN_LARGE_FERN)
			.add(WWBlocks.FROZEN_BUSH)
			.add(WWBlocks.SEA_ANEMONE)
			.add(WWBlocks.SEA_WHIP)
			.add(WWBlocks.TUBE_WORMS)
			.addOptionalTag(WWBlockTags.LEAF_LITTERS);

		this.valueLookupBuilder(BlockTags.REPLACEABLE_BY_MUSHROOMS)
			.add(WWBlocks.MYCELIUM_GROWTH)
			.add(WWBlocks.DATURA)
			.add(WWBlocks.CATTAIL)
			.add(WWBlocks.MILKWEED)
			.add(WWBlocks.SHRUB)
			.add(WWBlocks.POLLEN)
			.add(WWBlocks.PHLOX)
			.add(WWBlocks.LANTANAS)
			.add(WWBlocks.CLOVERS)
			.add(WWBlocks.FROZEN_SHORT_GRASS)
			.add(WWBlocks.FROZEN_TALL_GRASS)
			.add(WWBlocks.FROZEN_FERN)
			.add(WWBlocks.FROZEN_LARGE_FERN)
			.add(WWBlocks.FROZEN_BUSH)
			.addOptionalTag(WWBlockTags.LEAF_LITTERS);

		this.valueLookupBuilder(BlockTags.COMBINATION_STEP_SOUND_BLOCKS)
			.add(WWBlocks.POLLEN)
			.add(WWBlocks.BARNACLES)
			.add(WWBlocks.AUBURN_MOSS_CARPET)
			.add(WWBlocks.AUBURN_CREEPING_MOSS)
			.addOptionalTag(WWBlockTags.LEAF_LITTERS);

		this.valueLookupBuilder(BlockTags.FLOWER_POTS)
			.add(WWBlocks.POTTED_BAOBAB_NUT)
			.add(WWBlocks.POTTED_WILLOW_SAPLING)
			.add(WWBlocks.POTTED_CYPRESS_SAPLING)
			.add(WWBlocks.POTTED_COCONUT)
			.add(WWBlocks.POTTED_MAPLE_SAPLING)
			.add(WWBlocks.POTTED_SHRUB)
			.add(WWBlocks.POTTED_BIG_DRIPLEAF)
			.add(WWBlocks.POTTED_SMALL_DRIPLEAF)
			.add(WWBlocks.POTTED_SHORT_GRASS)
			.add(WWBlocks.POTTED_MYCELIUM_GROWTH)
			.add(WWBlocks.POTTED_TUMBLEWEED)
			.add(WWBlocks.POTTED_TUMBLEWEED_PLANT)
			.add(WWBlocks.POTTED_PRICKLY_PEAR)
			.add(WWBlocks.POTTED_CARNATION)
			.add(WWBlocks.POTTED_MARIGOLD)
			.add(WWBlocks.POTTED_SEEDING_DANDELION)
			.add(WWBlocks.POTTED_PASQUEFLOWER)
			.add(WWBlocks.POTTED_RED_HIBISCUS)
			.add(WWBlocks.POTTED_YELLOW_HIBISCUS)
			.add(WWBlocks.POTTED_WHITE_HIBISCUS)
			.add(WWBlocks.POTTED_PINK_HIBISCUS)
			.add(WWBlocks.POTTED_PURPLE_HIBISCUS)
			.add(WWBlocks.POTTED_PINK_PETALS)
			.add(WWBlocks.POTTED_BUSH)
			.add(WWBlocks.POTTED_FIREFLY_BUSH)
			.add(WWBlocks.POTTED_SHORT_DRY_GRASS)
			.add(WWBlocks.POTTED_TALL_DRY_GRASS)
			.add(WWBlocks.POTTED_CACTUS_FLOWER)
			.add(WWBlocks.POTTED_WILDFLOWERS)
			.add(WWBlocks.POTTED_PHLOX)
			.add(WWBlocks.POTTED_LANTANAS)
			.add(WWBlocks.POTTED_FROZEN_SHORT_GRASS)
			.add(WWBlocks.POTTED_FROZEN_FERN)
			.add(WWBlocks.POTTED_FROZEN_BUSH)
			.add(WWBlocks.POTTED_PALE_MUSHROOM);

		this.valueLookupBuilder(BlockTags.FLOWERS)
			.add(WWBlocks.DATURA)
			.add(WWBlocks.CATTAIL)
			.add(WWBlocks.MILKWEED)
			.add(WWBlocks.POLLEN)
			.add(WWBlocks.PHLOX)
			.add(WWBlocks.LANTANAS);

		this.valueLookupBuilder(BlockTags.SMALL_FLOWERS)
			.add(WWBlocks.CARNATION)
			.add(WWBlocks.MARIGOLD)
			.add(WWBlocks.SEEDING_DANDELION)
			.add(WWBlocks.PASQUEFLOWER)
			.add(WWBlocks.RED_HIBISCUS)
			.add(WWBlocks.YELLOW_HIBISCUS)
			.add(WWBlocks.WHITE_HIBISCUS)
			.add(WWBlocks.PINK_HIBISCUS)
			.add(WWBlocks.PURPLE_HIBISCUS)
			.add(WWBlocks.FLOWERING_LILY_PAD);

		this.valueLookupBuilder(BlockTags.BEE_ATTRACTIVE)
			.add(WWBlocks.CARNATION)
			.add(WWBlocks.MARIGOLD)
			.add(WWBlocks.SEEDING_DANDELION)
			.add(WWBlocks.RED_HIBISCUS)
			.add(WWBlocks.YELLOW_HIBISCUS)
			.add(WWBlocks.WHITE_HIBISCUS)
			.add(WWBlocks.PINK_HIBISCUS)
			.add(WWBlocks.PURPLE_HIBISCUS)
			.add(WWBlocks.FLOWERING_LILY_PAD)
			.add(WWBlocks.DATURA)
			.add(WWBlocks.CATTAIL)
			.add(WWBlocks.MILKWEED)
			.add(WWBlocks.POLLEN);

		this.valueLookupBuilder(BlockTags.FROG_PREFER_JUMP_TO)
			.add(WWBlocks.FLOWERING_LILY_PAD);

		this.valueLookupBuilder(BlockTags.BIG_DRIPLEAF_PLACEABLE)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.valueLookupBuilder(BlockTags.SMALL_DRIPLEAF_PLACEABLE)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.valueLookupBuilder(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH)
			.add(WWBlocks.ALGAE)
			.add(WWBlocks.PLANKTON)
			.add(WWBlocks.AUBURN_MOSS_CARPET)
			.add(WWBlocks.AUBURN_CREEPING_MOSS);

		this.valueLookupBuilder(BlockTags.GUARDED_BY_PIGLINS)
			.add(WWBlocks.STONE_CHEST);

		this.valueLookupBuilder(BlockTags.IMPERMEABLE)
			.add(WWBlocks.ECHO_GLASS)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.valueLookupBuilder(BlockTags.FEATURES_CANNOT_REPLACE)
			.add(WWBlocks.STONE_CHEST);

		this.valueLookupBuilder(BlockTags.SCULK_REPLACEABLE_WORLD_GEN)
			.addOptionalTag(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)
			.addOptionalTag(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)
			.addOptionalTag(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN);

		this.valueLookupBuilder(BlockTags.EDIBLE_FOR_SHEEP)
			.add(WWBlocks.FROZEN_SHORT_GRASS)
			.add(WWBlocks.FROZEN_FERN);

		this.valueLookupBuilder(BlockTags.TRIGGERS_AMBIENT_DESERT_SAND_BLOCK_SOUNDS)
			.add(WWBlocks.SCORCHED_SAND, WWBlocks.SCORCHED_RED_SAND);

		this.valueLookupBuilder(BlockTags.TRIGGERS_AMBIENT_DESERT_DRY_VEGETATION_BLOCK_SOUNDS)
			.add(WWBlocks.SCORCHED_SAND, WWBlocks.SCORCHED_RED_SAND);
	}

	private void generateWoods() {
		this.valueLookupBuilder(BlockTags.OVERWORLD_NATURAL_LOGS)
			.add(WWBlocks.BAOBAB_LOG)
			.add(WWBlocks.WILLOW_LOG)
			.add(WWBlocks.CYPRESS_LOG)
			.add(WWBlocks.PALM_LOG)
			.add(WWBlocks.MAPLE_LOG);

		this.valueLookupBuilder(BlockTags.ACACIA_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_ACACIA_LOGS);

		this.valueLookupBuilder(BlockTags.BIRCH_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_BIRCH_LOGS);

		this.valueLookupBuilder(BlockTags.CHERRY_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_CHERRY_LOGS);

		this.valueLookupBuilder(BlockTags.CRIMSON_STEMS)
			.addOptionalTag(WWBlockTags.HOLLOWED_CRIMSON_STEMS);

		this.valueLookupBuilder(BlockTags.DARK_OAK_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_DARK_OAK_LOGS);

		this.valueLookupBuilder(BlockTags.JUNGLE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_JUNGLE_LOGS);

		this.valueLookupBuilder(BlockTags.ACACIA_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_ACACIA_LOGS);

		this.valueLookupBuilder(BlockTags.MANGROVE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_MANGROVE_LOGS);

		this.valueLookupBuilder(BlockTags.OAK_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_OAK_LOGS);

		this.valueLookupBuilder(BlockTags.ACACIA_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_ACACIA_LOGS);

		this.valueLookupBuilder(BlockTags.SPRUCE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_SPRUCE_LOGS);

		this.valueLookupBuilder(BlockTags.PALE_OAK_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_PALE_OAK_LOGS);

		this.valueLookupBuilder(BlockTags.WARPED_STEMS)
			.addOptionalTag(WWBlockTags.HOLLOWED_WARPED_STEMS);

		this.valueLookupBuilder(WWBlockTags.BAOBAB_LOGS)
			.add(WWBlocks.BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_LOG)
			.add(WWBlocks.BAOBAB_WOOD, WWBlocks.STRIPPED_BAOBAB_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_BAOBAB_LOGS);

		this.valueLookupBuilder(WWBlockTags.WILLOW_LOGS)
			.add(WWBlocks.WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_LOG)
			.add(WWBlocks.WILLOW_WOOD, WWBlocks.STRIPPED_WILLOW_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_WILLOW_LOGS);

		this.valueLookupBuilder(WWBlockTags.CYPRESS_LOGS)
			.add(WWBlocks.CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_LOG)
			.add(WWBlocks.CYPRESS_WOOD, WWBlocks.STRIPPED_CYPRESS_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_CYPRESS_LOGS);

		this.valueLookupBuilder(WWBlockTags.PALM_LOGS)
			.add(WWBlocks.PALM_LOG, WWBlocks.STRIPPED_PALM_LOG)
			.add(WWBlocks.PALM_WOOD, WWBlocks.STRIPPED_PALM_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_PALM_LOGS);

		this.valueLookupBuilder(WWBlockTags.MAPLE_LOGS)
			.add(WWBlocks.MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_LOG)
			.add(WWBlocks.MAPLE_WOOD, WWBlocks.STRIPPED_MAPLE_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_MAPLE_LOGS);

		this.valueLookupBuilder(BlockTags.LOGS)
			.addOptionalTag(WWBlockTags.BAOBAB_LOGS)
			.addOptionalTag(WWBlockTags.WILLOW_LOGS)
			.addOptionalTag(WWBlockTags.CYPRESS_LOGS)
			.addOptionalTag(WWBlockTags.PALM_LOGS)
			.addOptionalTag(WWBlockTags.MAPLE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS);

		this.valueLookupBuilder(BlockTags.LOGS_THAT_BURN)
			.addOptionalTag(WWBlockTags.BAOBAB_LOGS)
			.addOptionalTag(WWBlockTags.WILLOW_LOGS)
			.addOptionalTag(WWBlockTags.CYPRESS_LOGS)
			.addOptionalTag(WWBlockTags.PALM_LOGS)
			.addOptionalTag(WWBlockTags.MAPLE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS_THAT_BURN);

		this.valueLookupBuilder(WWBlockTags.WILLOW_ROOTS_CAN_GROW_THROUGH)
			.add(Blocks.MUDDY_MANGROVE_ROOTS)
			.add(Blocks.MANGROVE_ROOTS)
			.add(Blocks.MOSS_CARPET)
			.add(Blocks.VINE)
			.add(Blocks.SNOW)
			.add(Blocks.RED_MUSHROOM)
			.add(Blocks.BROWN_MUSHROOM)
			.add(WWBlocks.ALGAE)
			.add(WWBlocks.PLANKTON)
			.add(WWBlocks.AUBURN_MOSS_CARPET)
			.add(WWBlocks.AUBURN_CREEPING_MOSS)
			.addOptionalTag(BlockTags.SMALL_FLOWERS);

		this.valueLookupBuilder(WWBlockTags.FALLEN_TREE_STUMP_PLACEABLE_ON)
			.add(Blocks.MOSS_BLOCK)
			.add(Blocks.PALE_MOSS_BLOCK)
			.add(Blocks.SANDSTONE)
			.add(Blocks.RED_SANDSTONE)
			.add(Blocks.CLAY)
			.add(Blocks.GRAVEL)
			.add(Blocks.SNOW_BLOCK)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.DIRT);

		this.valueLookupBuilder(BlockTags.LEAVES)
			.add(WWBlocks.BAOBAB_LEAVES)
			.add(WWBlocks.WILLOW_LEAVES)
			.add(WWBlocks.CYPRESS_LEAVES)
			.add(WWBlocks.PALM_FRONDS)
			.add(WWBlocks.YELLOW_MAPLE_LEAVES)
			.add(WWBlocks.ORANGE_MAPLE_LEAVES)
			.add(WWBlocks.RED_MAPLE_LEAVES);

		this.valueLookupBuilder(WWBlockTags.LEAF_LITTERS)
			.add(Blocks.LEAF_LITTER)
			.add(WWBlocks.ACACIA_LEAF_LITTER)
			.add(WWBlocks.AZALEA_LEAF_LITTER)
			.add(WWBlocks.BAOBAB_LEAF_LITTER)
			.add(WWBlocks.BIRCH_LEAF_LITTER)
			.add(WWBlocks.CHERRY_LEAF_LITTER)
			.add(WWBlocks.CYPRESS_LEAF_LITTER)
			.add(WWBlocks.DARK_OAK_LEAF_LITTER)
			.add(WWBlocks.JUNGLE_LEAF_LITTER)
			.add(WWBlocks.MANGROVE_LEAF_LITTER)
			.add(WWBlocks.PALE_OAK_LEAF_LITTER)
			.add(WWBlocks.PALM_FROND_LITTER)
			.add(WWBlocks.SPRUCE_LEAF_LITTER)
			.add(WWBlocks.WILLOW_LEAF_LITTER)
			.add(WWBlocks.YELLOW_MAPLE_LEAF_LITTER)
			.add(WWBlocks.ORANGE_MAPLE_LEAF_LITTER)
			.add(WWBlocks.RED_MAPLE_LEAF_LITTER);

		this.valueLookupBuilder(BlockTags.PLANKS)
			.add(WWBlocks.BAOBAB_PLANKS)
			.add(WWBlocks.WILLOW_PLANKS)
			.add(WWBlocks.CYPRESS_PLANKS)
			.add(WWBlocks.PALM_PLANKS)
			.add(WWBlocks.MAPLE_PLANKS);

		this.valueLookupBuilder(BlockTags.SAPLINGS)
			.add(WWBlocks.BAOBAB_NUT)
			.add(WWBlocks.WILLOW_SAPLING)
			.add(WWBlocks.CYPRESS_SAPLING)
			.add(WWBlocks.COCONUT)
			.add(WWBlocks.MAPLE_SAPLING);

		this.valueLookupBuilder(BlockTags.STANDING_SIGNS)
			.add(WWBlocks.BAOBAB_SIGN)
			.add(WWBlocks.WILLOW_SIGN)
			.add(WWBlocks.CYPRESS_SIGN)
			.add(WWBlocks.PALM_SIGN)
			.add(WWBlocks.MAPLE_SIGN);

		this.valueLookupBuilder(BlockTags.WALL_SIGNS)
			.add(WWBlocks.BAOBAB_WALL_SIGN)
			.add(WWBlocks.WILLOW_WALL_SIGN)
			.add(WWBlocks.CYPRESS_WALL_SIGN)
			.add(WWBlocks.PALM_WALL_SIGN)
			.add(WWBlocks.MAPLE_WALL_SIGN);

		this.valueLookupBuilder(BlockTags.CEILING_HANGING_SIGNS)
			.add(WWBlocks.BAOBAB_HANGING_SIGN)
			.add(WWBlocks.WILLOW_HANGING_SIGN)
			.add(WWBlocks.CYPRESS_HANGING_SIGN)
			.add(WWBlocks.PALM_HANGING_SIGN)
			.add(WWBlocks.MAPLE_HANGING_SIGN);

		this.valueLookupBuilder(BlockTags.WALL_HANGING_SIGNS)
			.add(WWBlocks.BAOBAB_WALL_HANGING_SIGN)
			.add(WWBlocks.WILLOW_WALL_HANGING_SIGN)
			.add(WWBlocks.CYPRESS_WALL_HANGING_SIGN)
			.add(WWBlocks.PALM_WALL_HANGING_SIGN)
			.add(WWBlocks.MAPLE_WALL_HANGING_SIGN);

		this.valueLookupBuilder(BlockTags.WOODEN_BUTTONS)
			.add(WWBlocks.BAOBAB_BUTTON)
			.add(WWBlocks.WILLOW_BUTTON)
			.add(WWBlocks.CYPRESS_BUTTON)
			.add(WWBlocks.PALM_BUTTON)
			.add(WWBlocks.MAPLE_BUTTON);

		this.valueLookupBuilder(BlockTags.WOODEN_DOORS)
			.add(WWBlocks.BAOBAB_DOOR)
			.add(WWBlocks.WILLOW_DOOR)
			.add(WWBlocks.CYPRESS_DOOR)
			.add(WWBlocks.PALM_DOOR)
			.add(WWBlocks.MAPLE_DOOR);

		this.valueLookupBuilder(BlockTags.WOODEN_FENCES)
			.add(WWBlocks.BAOBAB_FENCE)
			.add(WWBlocks.WILLOW_FENCE)
			.add(WWBlocks.CYPRESS_FENCE)
			.add(WWBlocks.PALM_FENCE)
			.add(WWBlocks.MAPLE_FENCE);

		this.valueLookupBuilder(BlockTags.FENCE_GATES)
			.add(WWBlocks.BAOBAB_FENCE_GATE)
			.add(WWBlocks.WILLOW_FENCE_GATE)
			.add(WWBlocks.CYPRESS_FENCE_GATE)
			.add(WWBlocks.PALM_FENCE_GATE)
			.add(WWBlocks.MAPLE_FENCE_GATE);

		this.valueLookupBuilder(BlockTags.WOODEN_PRESSURE_PLATES)
			.add(WWBlocks.BAOBAB_PRESSURE_PLATE)
			.add(WWBlocks.WILLOW_PRESSURE_PLATE)
			.add(WWBlocks.CYPRESS_PRESSURE_PLATE)
			.add(WWBlocks.PALM_PRESSURE_PLATE)
			.add(WWBlocks.MAPLE_PRESSURE_PLATE);

		this.valueLookupBuilder(BlockTags.WOODEN_SLABS)
			.add(WWBlocks.BAOBAB_SLAB)
			.add(WWBlocks.WILLOW_SLAB)
			.add(WWBlocks.CYPRESS_SLAB)
			.add(WWBlocks.PALM_SLAB)
			.add(WWBlocks.MAPLE_SLAB);

		this.valueLookupBuilder(BlockTags.WOODEN_STAIRS)
			.add(WWBlocks.BAOBAB_STAIRS)
			.add(WWBlocks.WILLOW_STAIRS)
			.add(WWBlocks.CYPRESS_STAIRS)
			.add(WWBlocks.PALM_STAIRS)
			.add(WWBlocks.MAPLE_STAIRS);

		this.valueLookupBuilder(BlockTags.WOODEN_TRAPDOORS)
			.add(WWBlocks.BAOBAB_TRAPDOOR)
			.add(WWBlocks.WILLOW_TRAPDOOR)
			.add(WWBlocks.CYPRESS_TRAPDOOR)
			.add(WWBlocks.PALM_TRAPDOOR)
			.add(WWBlocks.MAPLE_TRAPDOOR);
	}

	private void generateSounds() {
		this.valueLookupBuilder(WWBlockTags.SOUND_PALE_OAK_LEAF_LITTER)
			.add(WWBlocks.PALE_OAK_LEAF_LITTER);

		this.valueLookupBuilder(WWBlockTags.SOUND_PALE_OAK_LEAVES)
			.add(Blocks.PALE_OAK_LEAVES);

		this.valueLookupBuilder(WWBlockTags.SOUND_HOLLOWED_PALE_OAK_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_PALE_OAK_LOGS);

		this.valueLookupBuilder(WWBlockTags.SOUND_PALE_OAK_WOOD_HANGING_SIGN)
			.add(Blocks.PALE_OAK_HANGING_SIGN, Blocks.PALE_OAK_WALL_HANGING_SIGN);

		this.valueLookupBuilder(WWBlockTags.SOUND_PALE_OAK_WOOD)
			.add(Blocks.PALE_OAK_LOG, Blocks.STRIPPED_PALE_OAK_LOG)
			.add(Blocks.PALE_OAK_WOOD, Blocks.STRIPPED_PALE_OAK_WOOD)
			.add(Blocks.PALE_OAK_PLANKS, Blocks.PALE_OAK_STAIRS, Blocks.PALE_OAK_SLAB)
			.add(Blocks.PALE_OAK_FENCE, Blocks.PALE_OAK_FENCE_GATE)
			.add(Blocks.PALE_OAK_BUTTON, Blocks.PALE_OAK_PRESSURE_PLATE)
			.add(Blocks.PALE_OAK_DOOR, Blocks.PALE_OAK_TRAPDOOR)
			.add(Blocks.PALE_OAK_SIGN, Blocks.PALE_OAK_WALL_SIGN);

		this.valueLookupBuilder(WWBlockTags.SOUND_PALE_MOSS_CARPET)
			.add(Blocks.PALE_MOSS_CARPET, Blocks.PALE_HANGING_MOSS);

		this.valueLookupBuilder(WWBlockTags.SOUND_PALE_MOSS)
			.add(Blocks.PALE_MOSS_BLOCK);

		this.valueLookupBuilder(WWBlockTags.SOUND_AUBURN_MOSS_CARPET)
			.add(WWBlocks.AUBURN_MOSS_CARPET, WWBlocks.AUBURN_CREEPING_MOSS);

		this.valueLookupBuilder(WWBlockTags.SOUND_AUBURN_MOSS)
			.add(WWBlocks.AUBURN_MOSS_BLOCK);

		this.builder(WWBlockTags.SOUND_COCONUT)
			.add(WWBlocks.COCONUT.builtInRegistryHolder().key())

			.addOptional(this.getKey("natures_spirit", "coconut"))
			.addOptional(this.getKey("natures_spirit", "young_coconut"));

		this.valueLookupBuilder(WWBlockTags.SOUND_MAGMA_BLOCK)
			.add(Blocks.MAGMA_BLOCK);

		this.valueLookupBuilder(WWBlockTags.SOUND_WITHER_ROSE)
			.add(Blocks.WITHER_ROSE);

		this.valueLookupBuilder(WWBlockTags.SOUND_SUGAR_CANE)
			.add(Blocks.SUGAR_CANE);

		this.valueLookupBuilder(WWBlockTags.SOUND_REINFORCED_DEEPSLATE)
			.add(Blocks.REINFORCED_DEEPSLATE);

		this.valueLookupBuilder(WWBlockTags.SOUND_PODZOL)
			.add(Blocks.PODZOL);

		this.valueLookupBuilder(WWBlockTags.SOUND_DEAD_BUSH)
			.add(Blocks.DEAD_BUSH);

		this.valueLookupBuilder(WWBlockTags.SOUND_CLAY)
			.add(Blocks.CLAY);

		this.valueLookupBuilder(WWBlockTags.SOUND_GRAVEL)
			.add(Blocks.GRAVEL);

		this.valueLookupBuilder(WWBlockTags.SOUND_MELON)
			.add(Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN)
			.add(Blocks.MELON);

		this.valueLookupBuilder(WWBlockTags.SOUND_MELON_STEM)
			.add(Blocks.PUMPKIN_STEM, Blocks.ATTACHED_PUMPKIN_STEM)
			.add(Blocks.MELON_STEM, Blocks.ATTACHED_MELON_STEM);

		this.valueLookupBuilder(WWBlockTags.SOUND_LILY_PAD)
			.add(Blocks.LILY_PAD)
			.add(WWBlocks.FLOWERING_LILY_PAD);

		this.builder(WWBlockTags.SOUND_SANDSTONE)
			.addOptionalTag(ConventionalBlockTags.SANDSTONE_BLOCKS);

		this.builder(WWBlockTags.SOUND_MUSHROOM_BLOCK)
			.add(Blocks.RED_MUSHROOM_BLOCK.builtInRegistryHolder().key())
			.add(Blocks.BROWN_MUSHROOM_BLOCK.builtInRegistryHolder().key())
			.add(WWBlocks.PALE_MUSHROOM_BLOCK.builtInRegistryHolder().key())
			.add(Blocks.MUSHROOM_STEM.builtInRegistryHolder().key())

			.addOptional(this.getKey("betterend", "mossy_glowshroom_cap"))
			.addOptional(this.getKey("betterend", "mossy_glowshroom_fur"))
			.addOptional(this.getKey("betterend", "jellyshroom_cap_purple"))

			.addOptional(this.getKey("betternether", "red_large_mushroom"))
			.addOptional(this.getKey("betternether", "brown_large_mushroom"))

			.addOptional(this.getKey("biomesoplenty", "glowshroom_block"))
			.addOptional(this.getKey("natures_spirit","shitake_mushroom_block"))
			.addOptional(this.getKey("regions_unexplored","blue_bioshroom"));

		this.builder(WWBlockTags.SOUND_MUSHROOM)
			.add(Blocks.RED_MUSHROOM.builtInRegistryHolder().key())
			.add(Blocks.BROWN_MUSHROOM.builtInRegistryHolder().key())
			.add(WWBlocks.PALE_MUSHROOM.builtInRegistryHolder().key())

			.addOptional(this.getKey("betterend", "mossy_glowshroom_sapling"))
			.addOptional(this.getKey("betterend", "small_jellyshroom"))
			.addOptional(this.getKey("betterend", "bolux_mushroom"))

			.addOptional(this.getKey("betternether", "lucis_mushroom"))
			.addOptional(this.getKey("betternether", "wall_mushroom_red"))
			.addOptional(this.getKey("betternether", "wall_mushroom_brown"))

			.addOptional(this.getKey("biomesoplenty", "glowshroom"))
			.addOptional(this.getKey("natures_spirit","shitake_mushroom"))
			.addOptional(this.getKey("regions_unexplored","brown_wall_mushroom"))
			.addOptional(this.getKey("regions_unexplored","mycotoxic_mushrooms"));

		this.valueLookupBuilder(WWBlockTags.SOUND_FROSTED_ICE)
			.add(Blocks.FROSTED_ICE, WWBlocks.FRAGILE_ICE);

		this.valueLookupBuilder(WWBlockTags.SOUND_ICE)
			.add(Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE)
			.add(WWBlocks.ICICLE);

		this.builder(WWBlockTags.SOUND_COARSE_DIRT)
			.add(Blocks.COARSE_DIRT.builtInRegistryHolder().key())
			.addOptional(this.getKey("natures_spirit","sandy_soil"))
			.addOptional(this.getKey("regions_unexplored","peat_coarse_dirt"))
			.addOptional(this.getKey("regions_unexplored","silt_coarse_dirt"));

		this.builder(WWBlockTags.SOUND_CACTUS)
			.add(Blocks.CACTUS.builtInRegistryHolder().key())
			.add(WWBlocks.PRICKLY_PEAR_CACTUS.builtInRegistryHolder().key())

			.addOptional(this.getKey("betternether", "barrel_cactus"))
			.addOptional(this.getKey("betternether", "nether_cactus"))

			.addOptional(this.getKey("natures_spirit","aureate_succulent"))
			.addOptional(this.getKey("natures_spirit","drowsy_succulent"))
			.addOptional(this.getKey("natures_spirit","foamy_succulent"))
			.addOptional(this.getKey("natures_spirit","imperial_succulent"))
			.addOptional(this.getKey("natures_spirit","ornate_succulent"))
			.addOptional(this.getKey("natures_spirit","regal_succulent"))
			.addOptional(this.getKey("natures_spirit","sage_succulent"))
			.addOptional(this.getKey("natures_spirit","orange_maple_sapling"))
			.addOptional(this.getKey("natures_spirit","white_wisteria_sapling"))
			.addOptional(this.getKey("natures_spirit","larch_sapling"))

			.addOptional(this.getKey("regions_unexplored","barrel_cactus"))
			.addOptional(this.getKey("regions_unexplored","saguaro_cactus"))
			.addOptional(this.getKey("regions_unexplored","saguaro_cactus_corner"))

			.addOptional(this.getKey("terrestria","tiny_cactus"))
			.addOptional(this.getKey("terrestria","saguaro_cactus_sapling"))
			.addOptional(this.getKey("terrestria","saguaro_cactus"));

		this.builder(WWBlockTags.SOUND_SAPLING)
			.add(Blocks.ACACIA_SAPLING.builtInRegistryHolder().key())
			.add(Blocks.BIRCH_SAPLING.builtInRegistryHolder().key())
			.add(Blocks.DARK_OAK_SAPLING.builtInRegistryHolder().key())
			.add(Blocks.JUNGLE_SAPLING.builtInRegistryHolder().key())
			.add(Blocks.MANGROVE_PROPAGULE.builtInRegistryHolder().key())
			.add(Blocks.OAK_SAPLING.builtInRegistryHolder().key())
			.add(Blocks.SPRUCE_SAPLING.builtInRegistryHolder().key())
			.add(WWBlocks.WILLOW_SAPLING.builtInRegistryHolder().key())
			.add(WWBlocks.CYPRESS_SAPLING.builtInRegistryHolder().key())
			.add(WWBlocks.PALM_FRONDS.builtInRegistryHolder().key())
			.add(WWBlocks.MAPLE_SAPLING.builtInRegistryHolder().key())
			.add(WWBlocks.SHRUB.builtInRegistryHolder().key())
			.add(Blocks.PALE_OAK_SAPLING.builtInRegistryHolder().key())

			.addOptional(this.getKey("betterend", "pythadendron_sapling"))
			.addOptional(this.getKey("betterend", "lacugrove_sapling"))
			.addOptional(this.getKey("betterend", "dragon_tree_sapling"))
			.addOptional(this.getKey("betterend", "tenanea_sapling"))
			.addOptional(this.getKey("betterend", "helix_tree_sapling"))
			.addOptional(this.getKey("betterend", "umbrella_tree_sapling"))
			.addOptional(this.getKey("betterend", "lucernia_sapling"))

			.addOptional(this.getKey("biomesoplenty", "cypress_sapling"))
			.addOptional(this.getKey("biomesoplenty", "dead_sapling"))
			.addOptional(this.getKey("biomesoplenty", "empyreal_sapling"))
			.addOptional(this.getKey("biomesoplenty", "fir_sapling"))
			.addOptional(this.getKey("biomesoplenty", "flowering_oak_sapling"))
			.addOptional(this.getKey("biomesoplenty", "hellbark_sapling"))
			.addOptional(this.getKey("biomesoplenty", "jacaranda_sapling"))
			.addOptional(this.getKey("biomesoplenty", "magic_sapling"))
			.addOptional(this.getKey("biomesoplenty", "mahogany_sapling"))
			.addOptional(this.getKey("biomesoplenty", "orange_maple_sapling"))
			.addOptional(this.getKey("biomesoplenty", "palm_sapling"))
			.addOptional(this.getKey("biomesoplenty", "pine_sapling"))
			.addOptional(this.getKey("biomesoplenty", "rainbow_birch_sapling"))
			.addOptional(this.getKey("biomesoplenty", "red_maple_sapling"))
			.addOptional(this.getKey("biomesoplenty", "redwood_sapling"))
			.addOptional(this.getKey("biomesoplenty", "snowblossom_sapling"))
			.addOptional(this.getKey("biomesoplenty", "umbran_sapling"))
			.addOptional(this.getKey("biomesoplenty", "willow_sapling"))
			.addOptional(this.getKey("biomesoplenty", "yellow_maple_sapling"))

			.addOptional(this.getKey("blockus","white_oak_sapling"))

			.addOptional(this.getKey("excessive_building","ancient_sapling"))
			.addOptional(this.getKey("excessive_building","gloom_sapling"))

			.addOptional(this.getKey("natures_spirit","saxaul_sapling"))
			.addOptional(this.getKey("natures_spirit","fir_sapling"))
			.addOptional(this.getKey("natures_spirit","cedar_sapling"))
			.addOptional(this.getKey("natures_spirit","pink_wisteria_sapling"))
			.addOptional(this.getKey("natures_spirit","blue_wisteria_sapling"))
			.addOptional(this.getKey("natures_spirit","olive_sapling"))
			.addOptional(this.getKey("natures_spirit","joshua_sapling"))
			.addOptional(this.getKey("natures_spirit","orange_maple_sapling"))
			.addOptional(this.getKey("natures_spirit","white_wisteria_sapling"))
			.addOptional(this.getKey("natures_spirit","larch_sapling"))
			.addOptional(this.getKey("natures_spirit","mahogany_sapling"))
			.addOptional(this.getKey("natures_spirit","redwood_sapling"))
			.addOptional(this.getKey("natures_spirit","purple_wisteria_sapling"))
			.addOptional(this.getKey("natures_spirit","aspen_sapling"))
			.addOptional(this.getKey("natures_spirit","red_maple_sapling"))
			.addOptional(this.getKey("natures_spirit","cypress_sapling"))
			.addOptional(this.getKey("natures_spirit","willow_sapling"))
			.addOptional(this.getKey("natures_spirit","sugi_sapling"))
			.addOptional(this.getKey("natures_spirit","yellow_maple_sapling"))
			.addOptional(this.getKey("natures_spirit","palo_verde_sapling"))
			.addOptional(this.getKey("natures_spirit","ghaf_sapling"))
			.addOptional(this.getKey("natures_spirit","silverbush"))

			.addOptional(this.getKey("regions_unexplored","apple_oak_sapling"))
			.addOptional(this.getKey("regions_unexplored","ashen_sapling"))
			.addOptional(this.getKey("regions_unexplored","bamboo_sapling"))
			.addOptional(this.getKey("regions_unexplored","blackwood_sapling"))
			.addOptional(this.getKey("regions_unexplored","blue_magnolia_sapling"))
			.addOptional(this.getKey("regions_unexplored","brimwood_sapling"))
			.addOptional(this.getKey("regions_unexplored","cobalt_sapling"))
			.addOptional(this.getKey("regions_unexplored","cypress_sapling"))
			.addOptional(this.getKey("regions_unexplored","dead_pine_sapling"))
			.addOptional(this.getKey("regions_unexplored","dead_sapling"))
			.addOptional(this.getKey("regions_unexplored","enchanted_birch_sapling"))
			.addOptional(this.getKey("regions_unexplored","eucalyptus_sapling"))
			.addOptional(this.getKey("regions_unexplored","flowering_sapling"))
			.addOptional(this.getKey("regions_unexplored","golden_larch_sapling"))
			.addOptional(this.getKey("regions_unexplored","joshua_sapling"))
			.addOptional(this.getKey("regions_unexplored","kapok_sapling"))
			.addOptional(this.getKey("regions_unexplored","larch_sapling"))
			.addOptional(this.getKey("regions_unexplored","magnolia_sapling"))
			.addOptional(this.getKey("regions_unexplored","mauve_leaves"))
			.addOptional(this.getKey("regions_unexplored","maple_sapling"))
			.addOptional(this.getKey("regions_unexplored","mauve_sapling"))
			.addOptional(this.getKey("regions_unexplored","orange_maple_sapling"))
			.addOptional(this.getKey("regions_unexplored","palm_sapling"))
			.addOptional(this.getKey("regions_unexplored","pine_sapling"))
			.addOptional(this.getKey("regions_unexplored","pink_magnolia_sapling"))
			.addOptional(this.getKey("regions_unexplored","red_maple_sapling"))
			.addOptional(this.getKey("regions_unexplored","redwood_sapling"))
			.addOptional(this.getKey("regions_unexplored","silver_birch_sapling"))
			.addOptional(this.getKey("regions_unexplored","small_oak_sapling"))
			.addOptional(this.getKey("regions_unexplored","socotra_sapling"))
			.addOptional(this.getKey("regions_unexplored","white_magnolia_sapling"))
			.addOptional(this.getKey("regions_unexplored","willow_sapling"))

			.addOptional(this.getKey("terrestria","bryce_sapling"))
			.addOptional(this.getKey("terrestria","cypress_sapling"))
			.addOptional(this.getKey("terrestria","dark_japanese_maple_sapling"))
			.addOptional(this.getKey("terrestria","hemlock_sapling"))
			.addOptional(this.getKey("terrestria","japanese_maple_sapling"))
			.addOptional(this.getKey("terrestria","japanese_maple_shrub_sapling"))
			.addOptional(this.getKey("terrestria","jungle_palm_sapling"))
			.addOptional(this.getKey("terrestria","rainbow_eucalyptus_sapling"))
			.addOptional(this.getKey("terrestria","redwood_sapling"))
			.addOptional(this.getKey("terrestria","rubber_sapling"))
			.addOptional(this.getKey("terrestria","sakura_sapling"))
			.addOptional(this.getKey("terrestria","willow_sapling"))
			.addOptional(this.getKey("terrestria","yucca_palm_sapling"))

			.addOptional(this.getKey("traverse","brown_autumnal_sapling"))
			.addOptional(this.getKey("traverse","fir_sapling"))
			.addOptional(this.getKey("traverse","orange_autumnal_sapling"))
			.addOptional(this.getKey("traverse","red_autumnal_sapling"))
			.addOptional(this.getKey("traverse","yellow_autumnal_sapling"));

		this.valueLookupBuilder(WWBlockTags.SOUND_CONIFER_LEAF_LITTER)
			.add(WWBlocks.SPRUCE_LEAF_LITTER)
			.add(WWBlocks.CYPRESS_LEAF_LITTER);

		this.builder(WWBlockTags.SOUND_CONIFER_LEAVES)
			.add(Blocks.SPRUCE_LEAVES.builtInRegistryHolder().key())
			.add(WWBlocks.CYPRESS_LEAVES.builtInRegistryHolder().key())

			.addOptional(this.getKey("biomesoplenty", "cypress_leaves"))
			.addOptional(this.getKey("biomesoplenty", "fir_leaves"))
			.addOptional(this.getKey("biomesoplenty", "pine_leaves"))
			.addOptional(this.getKey("biomesoplenty", "redwood_leaves"))
			.addOptional(this.getKey("biomesoplenty", "umbran_leaves"))

			.addOptional(this.getKey("natures_spirit","fir_leaves"))
			.addOptional(this.getKey("natures_spirit","joshua_leaves"))
			.addOptional(this.getKey("natures_spirit","frosty_fir_leaves"))
			.addOptional(this.getKey("natures_spirit","saxaul_leaves"))
			.addOptional(this.getKey("natures_spirit","cedar_leaves"))
			.addOptional(this.getKey("natures_spirit","larch_leaves"))
			.addOptional(this.getKey("natures_spirit","redwood_leaves"))
			.addOptional(this.getKey("natures_spirit","frosty_redwood_leaves"))
			.addOptional(this.getKey("natures_spirit","cypress_leaves"))
			.addOptional(this.getKey("natures_spirit","sugi_leaves"))

			.addOptional(this.getKey("regions_unexplored","pine_leaves"))
			.addOptional(this.getKey("regions_unexplored","dead_pine_leaves"))
			.addOptional(this.getKey("regions_unexplored","cypress_leaves"))
			.addOptional(this.getKey("regions_unexplored","redwood_leaves"))
			.addOptional(this.getKey("regions_unexplored","eucalyptus_leaves"))
			.addOptional(this.getKey("regions_unexplored","joshua_leaves"))

			.addOptional(this.getKey("terrestria","cypress_leaves"))
			.addOptional(this.getKey("terrestria","rainbow_eucalyptus_leaves"))
			.addOptional(this.getKey("terrestria","redwood_leaves"))
			.addOptional(this.getKey("terrestria","hemlock_leaves"))

			.addOptional(this.getKey("traverse","fir_leaves"));

		this.builder(WWBlockTags.SOUND_LEAVES)
			.add(Blocks.ACACIA_LEAVES.builtInRegistryHolder().key())
			.add(Blocks.BIRCH_LEAVES.builtInRegistryHolder().key())
			.add(Blocks.DARK_OAK_LEAVES.builtInRegistryHolder().key())
			.add(Blocks.JUNGLE_LEAVES.builtInRegistryHolder().key())
			.add(Blocks.MANGROVE_LEAVES.builtInRegistryHolder().key())
			.add(Blocks.OAK_LEAVES.builtInRegistryHolder().key())
			.add(WWBlocks.BAOBAB_LEAVES.builtInRegistryHolder().key())
			.add(WWBlocks.WILLOW_LEAVES.builtInRegistryHolder().key())
			.add(WWBlocks.PALM_FRONDS.builtInRegistryHolder().key())

			.addOptional(this.getKey("betterend", "pythadendron_leaves"))
			.addOptional(this.getKey("betterend", "lacugrove_leaves"))
			.addOptional(this.getKey("betterend", "dragon_tree_leaves"))
			.addOptional(this.getKey("betterend", "tenanea_leaves"))
			.addOptional(this.getKey("betterend", "helix_tree_leaves"))
			.addOptional(this.getKey("betterend", "lucernia_leaves"))
			.addOptional(this.getKey("betterend", "lucernia_outer_leaves"))
			.addOptional(this.getKey("betterend", "glowing_pillar_leaves"))
			.addOptional(this.getKey("betterend", "cave_bush"))
			.addOptional(this.getKey("betterend", "end_lotus_leaf"))

			.addOptional(this.getKey("betternether", "willow_leaves"))
			.addOptional(this.getKey("betternether", "rubeus_leaves"))
			.addOptional(this.getKey("betternether", "anchor_tree_leaves"))
			.addOptional(this.getKey("betternether", "nether_sakura_leaves"))

			.addOptional(this.getKey("biomesoplenty", "bramble_leaves"))
			.addOptional(this.getKey("biomesoplenty", "dead_leaves"))
			.addOptional(this.getKey("biomesoplenty", "empyreal_leaves"))
			.addOptional(this.getKey("biomesoplenty", "flowering_oak_leaves"))
			.addOptional(this.getKey("biomesoplenty", "hellbark_leaves"))
			.addOptional(this.getKey("biomesoplenty", "jacaranda_leaves"))
			.addOptional(this.getKey("biomesoplenty", "magic_leaves"))
			.addOptional(this.getKey("biomesoplenty", "mahogany_leaves"))
			.addOptional(this.getKey("biomesoplenty", "null_leaves"))
			.addOptional(this.getKey("biomesoplenty", "orange_maple_leaves"))
			.addOptional(this.getKey("biomesoplenty", "palm_leaves"))
			.addOptional(this.getKey("biomesoplenty", "rainbow_birch_leaves"))
			.addOptional(this.getKey("biomesoplenty", "red_maple_leaves"))
			.addOptional(this.getKey("biomesoplenty", "snowblossom_leaves"))
			.addOptional(this.getKey("biomesoplenty", "willow_leaves"))
			.addOptional(this.getKey("biomesoplenty", "yellow_maple_leaves"))

			.addOptional(this.getKey("blockus","white_oak_leaves"))

			.addOptional(this.getKey("excessive_building","ancient_leaves"))
			.addOptional(this.getKey("excessive_building","gloom_leaves"))

			.addOptional(this.getKey("natures_spirit","part_pink_wisteria_leaves"))
			.addOptional(this.getKey("natures_spirit","yellow_aspen_leaves"))
			.addOptional(this.getKey("natures_spirit","part_white_wisteria_leaves"))
			.addOptional(this.getKey("natures_spirit","wisteria_leaves"))
			.addOptional(this.getKey("natures_spirit","part_purple_wisteria_leaves"))
			.addOptional(this.getKey("natures_spirit","pink_wisteria_leaves"))
			.addOptional(this.getKey("natures_spirit","blue_wisteria_leaves"))
			.addOptional(this.getKey("natures_spirit","olive_leaves"))
			.addOptional(this.getKey("natures_spirit","orange_maple_leaves"))
			.addOptional(this.getKey("natures_spirit","white_wisteria_leaves"))
			.addOptional(this.getKey("natures_spirit","mahogany_leaves"))
			.addOptional(this.getKey("natures_spirit","purple_wisteria_leaves"))
			.addOptional(this.getKey("natures_spirit","aspen_leaves"))
			.addOptional(this.getKey("natures_spirit","red_maple_leaves"))
			.addOptional(this.getKey("natures_spirit","coconut_leaves"))
			.addOptional(this.getKey("natures_spirit","willow_leaves"))
			.addOptional(this.getKey("natures_spirit","yellow_maple_leaves"))
			.addOptional(this.getKey("natures_spirit","palo_verde_leaves"))
			.addOptional(this.getKey("natures_spirit","part_blue_wisteria_leaves"))
			.addOptional(this.getKey("natures_spirit","ghaf_leaves"))

			.addOptional(this.getKey("regions_unexplored","apple_oak_leaves"))
			.addOptional(this.getKey("regions_unexplored","ashen_leaves"))
			.addOptional(this.getKey("regions_unexplored","bamboo_leaves"))
			.addOptional(this.getKey("regions_unexplored","baobab_leaves"))
			.addOptional(this.getKey("regions_unexplored","blackwood_leaves"))
			.addOptional(this.getKey("regions_unexplored","blue_magnolia_leaves"))
			.addOptional(this.getKey("regions_unexplored","brimwood_leaves"))
			.addOptional(this.getKey("regions_unexplored","dead_leaves"))
			.addOptional(this.getKey("regions_unexplored","enchanted_birch_leaves"))
			.addOptional(this.getKey("regions_unexplored","flowering_leaves"))
			.addOptional(this.getKey("regions_unexplored","golden_larch_leaves"))
			.addOptional(this.getKey("regions_unexplored","kapok_leaves"))
			.addOptional(this.getKey("regions_unexplored","larch_leaves"))
			.addOptional(this.getKey("regions_unexplored","magnolia_leaves"))
			.addOptional(this.getKey("regions_unexplored","maple_leaves"))
			.addOptional(this.getKey("regions_unexplored","mauve_leaves"))
			.addOptional(this.getKey("regions_unexplored","orange_maple_leaves"))
			.addOptional(this.getKey("regions_unexplored","palm_leaves"))
			.addOptional(this.getKey("regions_unexplored","pink_magnolia_leaves"))
			.addOptional(this.getKey("regions_unexplored","red_maple_leaves"))
			.addOptional(this.getKey("regions_unexplored","silver_birch_leaves"))
			.addOptional(this.getKey("regions_unexplored","small_oak_leaves"))
			.addOptional(this.getKey("regions_unexplored","socotra_leaves"))
			.addOptional(this.getKey("regions_unexplored","white_magnolia_leaves"))
			.addOptional(this.getKey("regions_unexplored","willow_leaves"))

			.addOptional(this.getKey("techreborn","rubber_leaves"))

			.addOptional(this.getKey("terrestria","dark_japanese_maple_leaves"))
			.addOptional(this.getKey("terrestria","japanese_maple_leaves"))
			.addOptional(this.getKey("terrestria","japanese_maple_shrub_leaves"))
			.addOptional(this.getKey("terrestria","jungle_palm_leaves"))
			.addOptional(this.getKey("terrestria","rubber_leaves"))
			.addOptional(this.getKey("terrestria","sakura_leaves"))
			.addOptional(this.getKey("terrestria","willow_leaves"))
			.addOptional(this.getKey("terrestria","yucca_palm_leaves"))

			.addOptional(this.getKey("traverse","brown_autumnal_leaves"))
			.addOptional(this.getKey("traverse","orange_autumnal_leaves"))
			.addOptional(this.getKey("traverse","red_autumnal_leaves"))
			.addOptional(this.getKey("traverse","yellow_autumnal_leaves"));

		this.builder(WWBlockTags.SOUND_FLOWER)
			.add(Blocks.DANDELION.builtInRegistryHolder().key())
			.add(Blocks.POPPY.builtInRegistryHolder().key())
			.add(Blocks.BLUE_ORCHID.builtInRegistryHolder().key())
			.add(Blocks.ALLIUM.builtInRegistryHolder().key())
			.add(Blocks.AZURE_BLUET.builtInRegistryHolder().key())
			.add(Blocks.ORANGE_TULIP.builtInRegistryHolder().key())
			.add(Blocks.PINK_TULIP.builtInRegistryHolder().key())
			.add(Blocks.RED_TULIP.builtInRegistryHolder().key())
			.add(Blocks.WHITE_TULIP.builtInRegistryHolder().key())
			.add(Blocks.OXEYE_DAISY.builtInRegistryHolder().key())
			.add(Blocks.CORNFLOWER.builtInRegistryHolder().key())
			.add(Blocks.LILY_OF_THE_VALLEY.builtInRegistryHolder().key())
			.add(WWBlocks.SEEDING_DANDELION.builtInRegistryHolder().key())
			.add(WWBlocks.CARNATION.builtInRegistryHolder().key())
			.add(WWBlocks.MARIGOLD.builtInRegistryHolder().key())
			.add(WWBlocks.PASQUEFLOWER.builtInRegistryHolder().key())
			.add(WWBlocks.RED_HIBISCUS.builtInRegistryHolder().key())
			.add(WWBlocks.PINK_HIBISCUS.builtInRegistryHolder().key())
			.add(WWBlocks.YELLOW_HIBISCUS.builtInRegistryHolder().key())
			.add(WWBlocks.WHITE_HIBISCUS.builtInRegistryHolder().key())
			.add(WWBlocks.PURPLE_HIBISCUS.builtInRegistryHolder().key())
			.add(WWBlocks.DATURA.builtInRegistryHolder().key())
			.add(WWBlocks.MILKWEED.builtInRegistryHolder().key())
			.add(Blocks.SUNFLOWER.builtInRegistryHolder().key())
			.add(Blocks.ROSE_BUSH.builtInRegistryHolder().key())
			.add(Blocks.PEONY.builtInRegistryHolder().key())
			.add(Blocks.LILAC.builtInRegistryHolder().key())
			.add(Blocks.TORCHFLOWER.builtInRegistryHolder().key())
			.add(Blocks.WILDFLOWERS.builtInRegistryHolder().key())
			.add(WWBlocks.PHLOX.builtInRegistryHolder().key())
			.add(WWBlocks.LANTANAS.builtInRegistryHolder().key())
			.add(Blocks.CLOSED_EYEBLOSSOM.builtInRegistryHolder().key())
			.add(Blocks.OPEN_EYEBLOSSOM.builtInRegistryHolder().key())

			.addOptional(this.getKey("trailiertales", "cyan_rose"))
			.addOptional(this.getKey("trailiertales", "manedrop"))

			.addOptional(this.getKey("betterend", "hydralux_petal_block"))
			.addOptional(this.getKey("betterend", "end_lotus_flower"))
			.addOptional(this.getKey("betterend", "tenanea_flowers"))

			.addOptional(this.getKey("betternether", "soul_lily"))
			.addOptional(this.getKey("betternether", "soul_lily_sapling"))

			.addOptional(this.getKey("biomesoplenty", "white_petals"))
			.addOptional(this.getKey("biomesoplenty", "wildflower"))
			.addOptional(this.getKey("biomesoplenty", "rose"))
			.addOptional(this.getKey("biomesoplenty", "violet"))
			.addOptional(this.getKey("biomesoplenty", "lavendar"))
			.addOptional(this.getKey("biomesoplenty", "white_lavendar"))
			.addOptional(this.getKey("biomesoplenty", "orange_cosmos"))
			.addOptional(this.getKey("biomesoplenty", "pink_daffodil"))
			.addOptional(this.getKey("biomesoplenty", "pink_hibiscus"))
			.addOptional(this.getKey("biomesoplenty", "waterlily"))
			.addOptional(this.getKey("biomesoplenty", "glowflower"))
			.addOptional(this.getKey("biomesoplenty", "wilted_lily"))
			.addOptional(this.getKey("biomesoplenty", "burning_blossom"))
			.addOptional(this.getKey("biomesoplenty", "endbloom"))
			.addOptional(this.getKey("biomesoplenty", "tall_lavendar"))
			.addOptional(this.getKey("biomesoplenty", "tall_white_lavendar"))
			.addOptional(this.getKey("biomesoplenty", "blue_hydrangea"))
			.addOptional(this.getKey("biomesoplenty", "goldenrod"))
			.addOptional(this.getKey("biomesoplenty", "icy_iris"))

			.addOptional(this.getKey("natures_spirit","anemone"))
			.addOptional(this.getKey("natures_spirit","begonia"))
			.addOptional(this.getKey("natures_spirit","black_iris"))
			.addOptional(this.getKey("natures_spirit","bleeding_heart"))
			.addOptional(this.getKey("natures_spirit","blue_bulbs"))
			.addOptional(this.getKey("natures_spirit","blue_iris"))
			.addOptional(this.getKey("natures_spirit","bluebell"))
			.addOptional(this.getKey("natures_spirit","carnation"))
			.addOptional(this.getKey("natures_spirit","dwarf_blossoms"))
			.addOptional(this.getKey("natures_spirit","foxglove"))
			.addOptional(this.getKey("natures_spirit","gardenia"))
			.addOptional(this.getKey("natures_spirit","hibiscus"))
			.addOptional(this.getKey("natures_spirit","iris"))
			.addOptional(this.getKey("natures_spirit","lavender"))
			.addOptional(this.getKey("natures_spirit","lotus_flower"))
			.addOptional(this.getKey("natures_spirit","marigold"))
			.addOptional(this.getKey("natures_spirit","protea"))
			.addOptional(this.getKey("natures_spirit","purple_heather"))
			.addOptional(this.getKey("natures_spirit","red_bearberries"))
			.addOptional(this.getKey("natures_spirit","red_heather"))
			.addOptional(this.getKey("natures_spirit","ruby_blossoms"))
			.addOptional(this.getKey("natures_spirit","snapdragon"))
			.addOptional(this.getKey("natures_spirit","tiger_lily"))
			.addOptional(this.getKey("natures_spirit","white_heather"))
			.addOptional(this.getKey("natures_spirit","yellow_wildflower"))
			.addOptional(this.getKey("natures_spirit","purple_wildflower"))
			.addOptional(this.getKey("natures_spirit","black_iris"))

			.addOptional(this.getKey("regions_unexplored","cactus_flower"))
			.addOptional(this.getKey("regions_unexplored","tassel"))
			.addOptional(this.getKey("regions_unexplored","day_lily"))
			.addOptional(this.getKey("regions_unexplored","aster"))
			.addOptional(this.getKey("regions_unexplored","bleeding_heart"))
			.addOptional(this.getKey("regions_unexplored","blue_lupine"))
			.addOptional(this.getKey("regions_unexplored","daisy"))
			.addOptional(this.getKey("regions_unexplored","dorcel"))
			.addOptional(this.getKey("regions_unexplored","felicia_daisy"))
			.addOptional(this.getKey("regions_unexplored","fireweed"))
			.addOptional(this.getKey("regions_unexplored","hibiscus"))
			.addOptional(this.getKey("regions_unexplored","mallow"))
			.addOptional(this.getKey("regions_unexplored","hyssop"))
			.addOptional(this.getKey("regions_unexplored","pink_lupine"))
			.addOptional(this.getKey("regions_unexplored","poppy_bush"))
			.addOptional(this.getKey("regions_unexplored","salmon_poppy_bush"))
			.addOptional(this.getKey("regions_unexplored","purple_lupine"))
			.addOptional(this.getKey("regions_unexplored","red_lupine"))
			.addOptional(this.getKey("regions_unexplored","waratah"))
			.addOptional(this.getKey("regions_unexplored","tsubaki"))
			.addOptional(this.getKey("regions_unexplored","white_trillium"))
			.addOptional(this.getKey("regions_unexplored","wilting_trillium"))
			.addOptional(this.getKey("regions_unexplored","yellow_lupine"))
			.addOptional(this.getKey("regions_unexplored","red_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","orange_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","yellow_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","lime_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","green_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","cyan_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","light_blue_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","blue_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","purple_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","magenta_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","pink_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","brown_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","white_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","light_gray_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","gray_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","black_snowbelle"))
			.addOptional(this.getKey("regions_unexplored","hyacinth_flowers"))
			.addOptional(this.getKey("regions_unexplored","orange_coneflower"))
			.addOptional(this.getKey("regions_unexplored","purple_coneflower"))
			.addOptional(this.getKey("regions_unexplored","blue_magnolia_flowers"))
			.addOptional(this.getKey("regions_unexplored","pink_magnolia_flowers"))
			.addOptional(this.getKey("regions_unexplored","white_magnolia_flowers"))

			.addOptional(this.getKey("terrestria","indian_paintbrush"))
			.addOptional(this.getKey("terrestria","monsteras"));

		this.valueLookupBuilder(WWBlockTags.SOUND_GRASS)
			.add(Blocks.SHORT_GRASS)
			.add(Blocks.TALL_GRASS)
			.add(Blocks.FERN)
			.add(Blocks.LARGE_FERN)
			.add(Blocks.BUSH)
			.add(WWBlocks.CLOVERS);

		this.valueLookupBuilder(WWBlockTags.SOUND_FROZEN_GRASS)
			.add(WWBlocks.FROZEN_SHORT_GRASS)
			.add(WWBlocks.FROZEN_TALL_GRASS)
			.add(WWBlocks.FROZEN_FERN)
			.add(WWBlocks.FROZEN_LARGE_FERN)
			.add(WWBlocks.FROZEN_BUSH);

		this.valueLookupBuilder(WWBlockTags.SOUND_DRY_GRASS)
			.add(Blocks.SHORT_DRY_GRASS)
			.add(Blocks.TALL_DRY_GRASS);
	}
}
