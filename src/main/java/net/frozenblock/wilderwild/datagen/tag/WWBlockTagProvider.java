/*
 * Copyright 2025-2026 FrozenBlock
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
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.frozenblock.lib.tag.api.FrozenBlockTags;
import net.frozenblock.wilderwild.references.WWBlockIds;
import net.frozenblock.wilderwild.references.WWBlockItemIds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockIds;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class WWBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

	public WWBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
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

	private TagKey<Block> getTag(String id) {
		return TagKey.create(this.registryKey, Identifier.parse(id));
	}

	private ResourceKey<Block> getKey(String namespace, String path) {
		return ResourceKey.create(this.registryKey, Identifier.fromNamespaceAndPath(namespace, path));
	}

	private void generateCompat() {
		this.builder(ConventionalBlockTags.CHESTS)
			.add(WWBlockItemIds.STONE_CHEST);

		this.builder(ConventionalBlockTags.STRIPPED_LOGS)
			.add(WWBlockItemIds.STRIPPED_BAOBAB_LOG)
			.add(WWBlockItemIds.STRIPPED_WILLOW_LOG)
			.add(WWBlockItemIds.STRIPPED_CYPRESS_LOG)
			.add(WWBlockItemIds.STRIPPED_PALM_LOG)
			.add(WWBlockItemIds.STRIPPED_MAPLE_LOG)
			.addOptionalTag(WWBlockTags.STRIPPED_HOLLOWED_LOGS);
	}

	private void generateLib() {
		this.builder(FrozenBlockTags.DRIPSTONE_CAN_DRIP_ON)
			.add(BlockItemIds.DIRT)
			.add(WWBlockItemIds.SCORCHED_SAND, WWBlockItemIds.SCORCHED_RED_SAND);
	}

	private void generateFeatures() {
		this.builder(WWBlockTags.STONE_TRANSITION_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT, BlockItemIds.MUD)
			.add(BlockItemIds.SAND);

		this.builder(WWBlockTags.STONE_TRANSITION_PLACEABLE)
			.add(BlockItemIds.STONE);

		this.builder(WWBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT, BlockItemIds.MUD);

		this.builder(WWBlockTags.GRAVEL_TRANSITION_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT, BlockItemIds.MUD)
			.add(BlockItemIds.SAND)
			.add(BlockItemIds.STONE);

		this.builder(WWBlockTags.GRAVEL_TRANSITION_PLACEABLE)
			.add(BlockItemIds.GRAVEL);

		this.builder(WWBlockTags.SAND_TRANSITION_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT, BlockItemIds.MUD)
			.add(BlockItemIds.GRAVEL)
			.add(BlockItemIds.STONE);

		this.builder(WWBlockTags.SAND_TRANSITION_PLACEABLE)
			.add(BlockItemIds.SAND);

		this.builder(WWBlockTags.RED_SAND_TRANSITION_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.MUD)
			.add(BlockItemIds.GRAVEL)
			.add(BlockItemIds.SAND)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.builder(WWBlockTags.RED_SAND_TRANSITION_PLACEABLE)
			.add(BlockItemIds.RED_SAND)
			.addOptionalTag(BlockTags.TERRACOTTA);

		this.builder(WWBlockTags.MUD_TRANSITION_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.SAND);

		this.builder(WWBlockTags.MUD_TRANSITION_PLACEABLE)
			.add(BlockItemIds.MUD, BlockItemIds.MUDDY_MANGROVE_ROOTS);

		this.builder(WWBlockTags.MUD_PATH_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.SAND);

		this.builder(WWBlockTags.COARSE_PATH_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT)
			.add(BlockItemIds.PODZOL);

		this.builder(WWBlockTags.COARSE_CLEARING_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT)
			.add(BlockItemIds.PODZOL)
			.add(BlockItemIds.GRAVEL);

		this.builder(WWBlockTags.ROOTED_DIRT_PATH_REPLACEABLE)
			.add(BlockItemIds.GRAVEL)
			.add(BlockItemIds.COARSE_DIRT);

		this.builder(WWBlockTags.UNDER_WATER_SAND_PATH_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT)
			.add(BlockItemIds.GRAVEL);

		this.builder(WWBlockTags.UNDER_WATER_GRAVEL_PATH_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT)
			.add(BlockItemIds.STONE);

		this.builder(WWBlockTags.UNDER_WATER_CLAY_PATH_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT)
			.add(BlockItemIds.GRAVEL)
			.add(BlockItemIds.STONE);

		this.builder(WWBlockTags.BEACH_CLAY_PATH_REPLACEABLE)
			.add(BlockItemIds.SAND);

		this.builder(WWBlockTags.RIVER_GRAVEL_PATH_REPLACEABLE)
			.add(BlockItemIds.SAND);

		this.builder(WWBlockTags.SAND_PATH_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT)
			.add(BlockItemIds.GRAVEL)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.builder(WWBlockTags.GRAVEL_PATH_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT, BlockItemIds.COARSE_DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.builder(WWBlockTags.GRAVEL_CLEARING_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT, BlockItemIds.COARSE_DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.builder(WWBlockTags.GRAVEL_AND_PALE_MOSS_PATH_REPLACEABLE)
			.add(BlockItemIds.DIRT)
			.add(BlockItemIds.GRASS_BLOCK)
			.add(BlockItemIds.COARSE_DIRT)
			.add(BlockItemIds.PALE_MOSS_BLOCK)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.builder(WWBlockTags.STONE_PATH_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT)
			.addOptionalTag(BlockTags.SAND);

		this.builder(WWBlockTags.PACKED_MUD_PATH_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT, BlockItemIds.COARSE_DIRT);

		this.builder(WWBlockTags.MOSS_PATH_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK)
			.add(BlockItemIds.PODZOL);

		this.builder(WWBlockTags.OCEAN_MOSS_REPLACEABLE)
			.add(BlockItemIds.GRAVEL)
			.addOptionalTag(BlockTags.SAND);

		this.builder(WWBlockTags.SANDSTONE_PATH_REPLACEABLE)
			.add(BlockItemIds.SAND);

		this.builder(WWBlockTags.SMALL_COARSE_DIRT_PATH_REPLACEABLE)
			.add(BlockItemIds.RED_SAND);

		this.builder(WWBlockTags.PACKED_MUD_PATH_BADLANDS_REPLACEABLE)
			.add(BlockItemIds.RED_SAND, BlockItemIds.RED_SANDSTONE)
			.addOptionalTag(BlockTags.TERRACOTTA);

		this.builder(WWBlockTags.POLLEN_FEATURE_PLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK)
			.addOptionalTag(BlockTags.LEAVES)
			.addOptionalTag(BlockTags.OVERWORLD_NATURAL_LOGS);

		this.builder(WWBlockTags.AUBURN_CREEPING_MOSS_FEATURE_PLACEABLE)
			.add(BlockItemIds.PRISMARINE)
			.add(BlockItemIds.PRISMARINE_BRICKS)
			.add(BlockItemIds.DARK_PRISMARINE)
			.add(BlockItemIds.SEA_LANTERN)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.GRAVEL)
			.addOptionalTag(BlockTags.SUBSTRATE_OVERWORLD)
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

		this.builder(WWBlockTags.BARNACLES_FEATURE_PLACEABLE)
			.add(BlockItemIds.PRISMARINE)
			.add(BlockItemIds.PRISMARINE_BRICKS)
			.add(BlockItemIds.DARK_PRISMARINE)
			.add(BlockItemIds.SEA_LANTERN)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.GRAVEL)
			.addOptionalTag(BlockTags.SUBSTRATE_OVERWORLD)
			.addOptionalTag(BlockTags.STONE_BRICKS)
			.addOptionalTag(BlockTags.LOGS)
			.addOptionalTag(BlockTags.PLANKS)
			.addOptionalTag(BlockTags.SLABS)
			.addOptionalTag(BlockTags.STAIRS)
			.addOptionalTag(BlockTags.DOORS)
			.addOptionalTag(BlockTags.TRAPDOORS)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.builder(WWBlockTags.BARNACLES_FEATURE_PLACEABLE_STRUCTURE)
			.add(BlockItemIds.PRISMARINE)
			.add(BlockItemIds.PRISMARINE_BRICKS)
			.add(BlockItemIds.DARK_PRISMARINE)
			.add(BlockItemIds.SEA_LANTERN)
			.addOptionalTag(BlockTags.STONE_BRICKS)
			.addOptionalTag(BlockTags.LOGS)
			.addOptionalTag(BlockTags.PLANKS)
			.addOptionalTag(BlockTags.SLABS)
			.addOptionalTag(BlockTags.STAIRS)
			.addOptionalTag(BlockTags.DOORS)
			.addOptionalTag(BlockTags.TRAPDOORS);

		this.builder(WWBlockTags.SEA_ANEMONE_FEATURE_CANNOT_PLACE)
			.add(BlockItemIds.MOSS_BLOCK);

		this.builder(WWBlockTags.TERMITE_DISK_REPLACEABLE)
			.addOptionalTag(BlockTags.SUBSTRATE_OVERWORLD)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.builder(WWBlockTags.TERMITE_DISK_BLOCKS)
			.add(BlockItemIds.COARSE_DIRT)
			.add(BlockItemIds.PACKED_MUD);

		this.builder(WWBlockTags.BLUE_NEMATOCYST_FEATURE_PLACEABLE)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.DRIPSTONE_BLOCK)
			.add(BlockItemIds.CALCITE)
			.add(WWBlockItemIds.PEARLESCENT_BLUE_MESOGLEA)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.builder(WWBlockTags.PURPLE_NEMATOCYST_FEATURE_PLACEABLE)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.DRIPSTONE_BLOCK)
			.add(BlockItemIds.CALCITE)
			.add(WWBlockItemIds.PEARLESCENT_PURPLE_MESOGLEA)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.builder(WWBlockTags.SHELF_FUNGI_FEATURE_PLACEABLE)
			.add(BlockItemIds.MUSHROOM_STEM)
			.addOptionalTag(BlockTags.OVERWORLD_NATURAL_LOGS);

		this.builder(WWBlockTags.SHELF_FUNGI_FEATURE_PLACEABLE_NETHER)
			.addOptionalTag(BlockItemTags.CRIMSON_STEMS.block())
			.addOptionalTag(BlockItemTags.WARPED_STEMS.block());

		this.builder(WWBlockTags.SCORCHED_SAND_FEATURE_INNER_REPLACEABLE)
			.add(BlockItemIds.SAND, WWBlockItemIds.SCORCHED_SAND);

		this.builder(WWBlockTags.SCORCHED_SAND_FEATURE_REPLACEABLE)
			.add(BlockItemIds.SAND);

		this.builder(WWBlockTags.RED_SCORCHED_SAND_FEATURE_INNER_REPLACEABLE)
			.add(BlockItemIds.RED_SAND, WWBlockItemIds.SCORCHED_RED_SAND);

		this.builder(WWBlockTags.RED_SCORCHED_SAND_FEATURE_REPLACEABLE)
			.add(BlockItemIds.RED_SAND);

		this.builder(WWBlockTags.CAVE_ICE_REPLACEABLE)
			.add(BlockItemIds.GRAVEL)
			.addOptionalTag(BlockTags.SUBSTRATE_OVERWORLD)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.add(BlockItemIds.SNOW_BLOCK)
			.add(BlockItemIds.SNOW);

		this.builder(WWBlockTags.CAVE_FRAGILE_ICE_REPLACEABLE)
			.addOptionalTag(BlockTags.ICE)
			.addOptionalTag(WWBlockTags.CAVE_ICE_REPLACEABLE);

		this.builder(WWBlockTags.DIORITE_ICE_REPLACEABLE)
			.add(BlockItemIds.GRAVEL)
			.addOptionalTag(BlockTags.SUBSTRATE_OVERWORLD)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.add(BlockItemIds.SNOW_BLOCK)
			.add(BlockItemIds.SNOW)
			.add(BlockItemIds.ICE)
			.add(BlockItemIds.BLUE_ICE)
			.add(BlockItemIds.PACKED_ICE);

		this.builder(WWBlockTags.ICICLE_REPLACEABLE)
			.addOptionalTag(WWBlockTags.ICICLE_GROWS_WHEN_UNDER)
			.addOptionalTag(WWBlockTags.CAVE_FRAGILE_ICE_REPLACEABLE);

		this.builder(WWBlockTags.MESOGLEA_REPLACEABLE)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.DRIPSTONE_BLOCK)
			.add(BlockItemIds.CALCITE)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.builder(WWBlockTags.MAGMA_REPLACEABLE)
			.add(BlockItemIds.GRAVEL)
			.addOptionalTag(BlockTags.SUBSTRATE_OVERWORLD)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.builder(WWBlockTags.NETHER_GEYSER_REPLACEABLE)
			.addOptionalTag(BlockTags.BASE_STONE_NETHER);

		this.builder(WWBlockTags.OASIS_PATH_REPLACEABLE)
			.add(BlockItemIds.SAND)
			.add(BlockItemIds.SANDSTONE);

		this.builder(WWBlockTags.COARSE_DIRT_DISK_REPLACEABLE)
			.addOptionalTag(BlockTags.SAND)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.DIRT)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.PODZOL);

		this.builder(WWBlockTags.RIVER_POOL_REPLACEABLE)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.SUBSTRATE_OVERWORLD)
			.add(BlockItemIds.GRAVEL)
			.add(BlockItemIds.CLAY);

		this.builder(WWBlockTags.FALLEN_TREE_PLACEABLE)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.SUBSTRATE_OVERWORLD)
			.add(BlockItemIds.GRAVEL)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.MOSS_BLOCK)
			.add(BlockItemIds.PACKED_MUD)
			.add(BlockItemIds.SNOW)
			.add(WWBlockItemIds.AUBURN_MOSS_BLOCK);

		this.builder(WWBlockTags.PACKED_MUD_REPLACEABLE)
			.add(BlockItemIds.STONE)
			.add(BlockItemIds.DIRT)
			.add(BlockItemIds.SANDSTONE);

		this.builder(WWBlockTags.SMALL_SPONGE_GROWS_ON)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.SAND)
			.add(BlockItemIds.GRAVEL)
			.add(BlockItemIds.SPONGE)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.MOSS_BLOCK)
			.add(WWBlockItemIds.AUBURN_MOSS_BLOCK)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.builder(WWBlockTags.BASIN_REPLACEABLE)
			.add(BlockItemIds.GRASS_BLOCK, BlockItemIds.COARSE_DIRT)
			.add(BlockItemIds.PODZOL)
			.add(BlockItemIds.MOSS_BLOCK)
			.add(WWBlockItemIds.AUBURN_MOSS_BLOCK);

		this.builder(WWBlockTags.HYDROTHERMAL_VENT_REPLACEABLE)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.GRAVEL)
			.addOptionalTag(BlockTags.SUBSTRATE_OVERWORLD)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.builder(WWBlockTags.SULFUR_SPRING_REPLACEABLE)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.GRAVEL)
			.add(BlockItemIds.SULFUR, BlockItemIds.CINNABAR)
			.addOptionalTag(BlockTags.SUBSTRATE_OVERWORLD)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.LEAVES)
			.addOptionalTag(BlockTags.REPLACEABLE)
			.addOptionalTag(BlockTags.TERRACOTTA);

		this.builder(WWBlockTags.SULFUR_SPRING_PLACEABLE)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.GRAVEL)
			.add(BlockItemIds.SULFUR, BlockItemIds.CINNABAR)
			.addOptionalTag(BlockTags.SUBSTRATE_OVERWORLD)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.TERRACOTTA);

		this.builder(WWBlockTags.CATTAIL_FEATURE_PLACEABLE)
			.addOptionalTag(BlockTags.SUBSTRATE_OVERWORLD)
			.addOptionalTag(BlockTags.SAND)
			.add(BlockItemIds.CLAY);

		this.builder(WWBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
			.addOptionalTag(BlockTags.TERRACOTTA)
			.addOptionalTag(BlockTags.SUPPORTS_VEGETATION);

		this.builder(WWBlockTags.SAND_POOL_REPLACEABLE)
			.add(BlockItemIds.SAND);
	}

	private void generateTags() {
		this.builder(WWBlockTags.MESOGLEA)
			.add(WWBlockItemIds.BLUE_MESOGLEA)
			.add(WWBlockItemIds.PEARLESCENT_BLUE_MESOGLEA)
			.add(WWBlockItemIds.LIME_MESOGLEA)
			.add(WWBlockItemIds.PINK_MESOGLEA)
			.add(WWBlockItemIds.PEARLESCENT_PURPLE_MESOGLEA)
			.add(WWBlockItemIds.RED_MESOGLEA)
			.add(WWBlockItemIds.YELLOW_MESOGLEA);

		this.builder(WWBlockTags.NEMATOCYSTS)
			.add(WWBlockItemIds.BLUE_NEMATOCYST)
			.add(WWBlockItemIds.PEARLESCENT_BLUE_NEMATOCYST)
			.add(WWBlockItemIds.LIME_NEMATOCYST)
			.add(WWBlockItemIds.PINK_NEMATOCYST)
			.add(WWBlockItemIds.PEARLESCENT_PURPLE_NEMATOCYST)
			.add(WWBlockItemIds.RED_NEMATOCYST)
			.add(WWBlockItemIds.YELLOW_NEMATOCYST);

		this.builder(WWBlockTags.FROGLIGHT_GOOP)
			.add(WWBlockItemIds.PEARLESCENT_FROGLIGHT_GOOP)
			.add(WWBlockIds.PEARLESCENT_FROGLIGHT_GOOP_BODY)
			.add(WWBlockItemIds.VERDANT_FROGLIGHT_GOOP)
			.add(WWBlockIds.VERDANT_FROGLIGHT_GOOP_BODY)
			.add(WWBlockItemIds.OCHRE_FROGLIGHT_GOOP)
			.add(WWBlockIds.OCHRE_FROGLIGHT_GOOP_BODY);

		this.builder(WWBlockTags.ICICLE_FALLS_FROM)
			.add(BlockItemIds.ICE, BlockItemIds.PACKED_ICE, BlockItemIds.BLUE_ICE, WWBlockItemIds.FRAGILE_ICE);

		this.builder(WWBlockTags.ICICLE_GROWS_WHEN_UNDER)
			.add(BlockItemIds.ICE, BlockItemIds.PACKED_ICE, BlockItemIds.BLUE_ICE, WWBlockItemIds.FRAGILE_ICE);

		this.builder(WWBlockTags.STOPS_TUMBLEWEED)
			.add(BlockItemIds.MUD, BlockItemIds.MUDDY_MANGROVE_ROOTS)
			.add(BlockItemIds.SLIME_BLOCK)
			.add(BlockItemIds.IRON_BARS)
			.add(BlockItemIds.HONEY_BLOCK);

		this.builder(WWBlockTags.SPLITS_COCONUT)
			.addOptionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.BASE_STONE_NETHER)
			.addOptionalTag(BlockTags.DRAGON_IMMUNE)
			.addOptionalTag(BlockTags.WITHER_IMMUNE)
			.addOptionalTag(BlockTags.LOGS);

		this.builder(WWBlockTags.FIREFLY_HIDEABLE_BLOCKS)
			.add(BlockItemIds.SHORT_GRASS, BlockItemIds.TALL_GRASS)
			.add(BlockItemIds.FERN, BlockItemIds.LARGE_FERN)
			.add(BlockItemIds.BUSH)
			.add(WWBlockItemIds.FROZEN_SHORT_GRASS, WWBlockItemIds.FROZEN_TALL_GRASS)
			.add(WWBlockItemIds.FROZEN_FERN, WWBlockItemIds.FROZEN_LARGE_FERN)
			.add(WWBlockItemIds.FROZEN_BUSH)
			.add(BlockItemIds.PEONY)
			.add(BlockItemIds.ROSE_BUSH)
			.add(BlockItemIds.DEAD_BUSH)
			.add(WWBlockItemIds.CATTAIL)
			.add(WWBlockItemIds.MILKWEED)
			.add(WWBlockItemIds.SHRUB)
			.add(BlockItemIds.FIREFLY_BUSH)
			.addOptionalTag(BlockTags.SMALL_FLOWERS);

		this.builder(WWBlockTags.CRAB_HIDEABLE)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.MUD)
			.addOptionalTag(BlockTags.SAND)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.GRAVEL);

		this.builder(WWBlockTags.OSTRICH_BEAK_BURYABLE)
			.addOptionalTag(BlockTags.MINEABLE_WITH_SHOVEL)
			.addOptionalTag(BlockTags.MINEABLE_WITH_HOE)
			.addOptionalTag(BlockTags.WOOL);

		this.builder(WWBlockTags.PENGUIN_IGNORE_FRICTION)
			.addOptionalTag(BlockTags.ICE);

		this.builder(WWBlockTags.PENGUINS_SPAWNABLE_ON)
			.add(BlockItemIds.ICE)
			.add(BlockItemIds.SNOW_BLOCK)
			.add(BlockItemIds.SAND)
			.add(BlockItemIds.GRAVEL)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.ANIMALS_SPAWNABLE_ON);

		this.builder(FrozenBlockTags.BLOWING_CAN_PASS_THROUGH)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.builder(WWBlockTags.NO_LIGHTNING_BLOCK_PARTICLES)
			.addOptionalTag(BlockTags.LIGHTNING_RODS);

		this.builder(WWBlockTags.NO_LIGHTNING_SMOKE_PARTICLES)
			.addOptionalTag(BlockTags.LIGHTNING_RODS);

		this.builder(ConventionalBlockTags.GLASS_BLOCKS)
			.add(WWBlockItemIds.ECHO_GLASS);

		this.builder(WWBlockTags.SUPPORTS_SHRUB)
			.addOptionalTag(BlockTags.SUPPORTS_DRY_VEGETATION);

		this.builder(WWBlockTags.CYPRESS_GROWS_AS_JUNIPER_ON)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.TERRACOTTA);

		this.builder(WWBlockTags.MYCELIUM_GROWTH_REPLACEABLE)
			.add(BlockItemIds.SHORT_GRASS)
			.add(BlockItemIds.FERN);

		this.builder(WWBlockTags.AUBURN_MOSS_REPLACEABLE)
			.addOptionalTag(BlockTags.MOSS_REPLACEABLE)
			.addOptionalTag(BlockTags.SAND)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.GRAVEL);

		this.builder(WWBlockTags.SUPPORTS_ALGAE);
		this.builder(WWBlockTags.SUPPORTS_PLANKTON);

		this.builder(WWBlockTags.SUPPORTS_HANGING_BAOBAB_NUT)
			.add(WWBlockItemIds.BAOBAB_LEAVES);

		this.builder(WWBlockTags.SUPPORTS_CATTAIL)
			.addOptionalTag(BlockTags.SUPPORTS_VEGETATION)
			.addOptionalTag(WWBlockTags.CATTAIL_FEATURE_PLACEABLE);

		this.builder(WWBlockTags.SUPPORTS_COCONUT)
			.addOptionalTag(BlockTags.SUPPORTS_VEGETATION)
			.addOptionalTag(BlockTags.SAND);

		this.builder(WWBlockTags.SUPPORTS_HANGING_COCONUT)
			.add(WWBlockItemIds.PALM_FRONDS);

		this.builder(BlockTags.CANNOT_SUPPORT_SEAGRASS)
			.add(WWBlockItemIds.GEYSER);

		this.builder(BlockTags.CANNOT_SUPPORT_KELP)
			.add(WWBlockItemIds.GEYSER);

		this.builder(WWBlockTags.CANNOT_SUPPORT_SEA_ANEMONE)
			.add(BlockItemIds.MAGMA_BLOCK)
			.add(WWBlockItemIds.GEYSER);

		this.builder(WWBlockTags.CANNOT_SUPPORT_SEA_WHIP)
			.add(BlockItemIds.MAGMA_BLOCK)
			.add(WWBlockItemIds.GEYSER);

		this.builder(WWBlockTags.CANNOT_SUPPORT_TUBE_WORMS)
			.add(BlockItemIds.MAGMA_BLOCK)
			.add(WWBlockItemIds.GEYSER);

		this.builder(WWBlockTags.SUPPORTS_FROZEN_VEGETATION)
			.addOptionalTag(BlockTags.SUPPORTS_VEGETATION)
			.add(BlockItemIds.SNOW_BLOCK);

		this.builder(WWBlockTags.SUPPORTS_WATERLOGGABLE_SAPLING)
			.addOptionalTag(BlockTags.SUPPORTS_VEGETATION)
			.add(BlockItemIds.CLAY)
			.addOptionalTag(BlockTags.SAND);

		this.builder(WWBlockTags.CANNOT_REPLACE_BELOW_TREE_TRUNK_WATERLOGGABLE)
			.addOptionalTag(BlockTags.CANNOT_REPLACE_BELOW_TREE_TRUNK)
			.add(BlockItemIds.CLAY)
			.addOptionalTag(BlockTags.SAND);

		this.builder(WWBlockTags.CANNOT_REPLACE_BELOW_TREE_TRUNK_DESERT)
			.addOptionalTag(BlockTags.CANNOT_REPLACE_BELOW_TREE_TRUNK)
			.addOptionalTag(BlockTags.SAND);

		this.builder(WWBlockTags.SNOW_GENERATION_CAN_SEARCH_THROUGH)
			.add(BlockItemIds.LADDER)
			.add(WWBlockItemIds.ICICLE)
			.addOptionalTag(BlockTags.LEAVES)
			.addOptionalTag(BlockTags.WALLS)
			.addOptionalTag(BlockTags.FENCE_GATES)
			.addOptionalTag(BlockTags.FENCES);
	}

	private void generateDeepDark() {
		this.builder(WWBlockTags.ANCIENT_CITY_BLOCKS)
			.add(BlockItemIds.COBBLED_DEEPSLATE, BlockItemIds.COBBLED_DEEPSLATE_STAIRS, BlockItemIds.COBBLED_DEEPSLATE_SLAB, BlockItemIds.COBBLED_DEEPSLATE_WALL)
			.add(BlockItemIds.POLISHED_DEEPSLATE, BlockItemIds.POLISHED_DEEPSLATE_STAIRS, BlockItemIds.POLISHED_DEEPSLATE_SLAB, BlockItemIds.POLISHED_DEEPSLATE_WALL)
			.add(BlockItemIds.DEEPSLATE_BRICKS, BlockItemIds.DEEPSLATE_BRICK_STAIRS, BlockItemIds.DEEPSLATE_BRICK_SLAB, BlockItemIds.DEEPSLATE_BRICK_WALL)
			.add(BlockItemIds.CRACKED_DEEPSLATE_BRICKS)
			.add(BlockItemIds.DEEPSLATE_TILES, BlockItemIds.DEEPSLATE_TILE_STAIRS)
			.add(BlockItemIds.CHISELED_DEEPSLATE)
			.add(BlockItemIds.REINFORCED_DEEPSLATE)
			.add(BlockItemIds.POLISHED_BASALT, BlockItemIds.SMOOTH_BASALT)
			.add(BlockItemIds.DARK_OAK_LOG, BlockItemIds.DARK_OAK_PLANKS, BlockItemIds.DARK_OAK_FENCE)
			.add(BlockItemIds.CARPET.lightBlue(), BlockItemIds.CARPET.blue())
			.add(BlockItemIds.WOOL.lightBlue(), BlockItemIds.WOOL.gray())
			.add(BlockItemIds.CHEST, WWBlockItemIds.STONE_CHEST)
			.add(BlockItemIds.LADDER)
			.add(BlockItemIds.CANDLE, BlockItemIds.DYED_CANDLE.white())
			.add(BlockItemIds.SOUL_LANTERN.block(), BlockIds.SOUL_FIRE)
			.add(BlockItemIds.SOUL_SAND);

		this.builder(WWBlockTags.SCULK_SLAB_REPLACEABLE)
			.add(BlockItemIds.STONE_SLAB, BlockItemIds.GRANITE_SLAB, BlockItemIds.DIORITE_SLAB, BlockItemIds.ANDESITE_SLAB, BlockItemIds.BLACKSTONE_SLAB, WWBlockItemIds.GABBRO_SLAB);

		this.builder(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)
			.add(BlockItemIds.COBBLED_DEEPSLATE_SLAB, BlockItemIds.POLISHED_DEEPSLATE_SLAB, BlockItemIds.DEEPSLATE_BRICK_SLAB, BlockItemIds.DEEPSLATE_TILE_SLAB)
			.addOptionalTag(WWBlockTags.SCULK_SLAB_REPLACEABLE);

		this.builder(WWBlockTags.SCULK_STAIR_REPLACEABLE)
			.add(BlockItemIds.STONE_STAIRS, BlockItemIds.GRANITE_STAIRS, BlockItemIds.DIORITE_STAIRS, BlockItemIds.ANDESITE_STAIRS, BlockItemIds.BLACKSTONE_STAIRS, WWBlockItemIds.GABBRO_STAIRS);

		this.builder(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)
			.add(BlockItemIds.COBBLED_DEEPSLATE_STAIRS, BlockItemIds.POLISHED_DEEPSLATE_STAIRS, BlockItemIds.DEEPSLATE_BRICK_STAIRS, BlockItemIds.DEEPSLATE_TILE_STAIRS)
			.addOptionalTag(WWBlockTags.SCULK_STAIR_REPLACEABLE);

		this.builder(WWBlockTags.SCULK_WALL_REPLACEABLE)
			.add(BlockItemIds.COBBLESTONE_WALL, BlockItemIds.GRANITE_WALL, BlockItemIds.DIORITE_WALL, BlockItemIds.ANDESITE_WALL, BlockItemIds.BLACKSTONE_WALL, WWBlockItemIds.GABBRO_WALL);

		this.builder(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)
			.add(BlockItemIds.COBBLED_DEEPSLATE_WALL, BlockItemIds.POLISHED_DEEPSLATE_WALL, BlockItemIds.DEEPSLATE_BRICK_WALL, BlockItemIds.DEEPSLATE_TILE_WALL)
			.addOptionalTag(WWBlockTags.SCULK_WALL_REPLACEABLE);

		this.builder(BlockTags.OCCLUDES_VIBRATION_SIGNALS)
			.add(WWBlockItemIds.ECHO_GLASS);
	}

	private void generateHollowedAndTermites() {
		this.builder(WWBlockTags.HOLLOWED_ACACIA_LOGS)
			.add(WWBlockItemIds.HOLLOWED_ACACIA_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_ACACIA_LOG);

		this.builder(WWBlockTags.HOLLOWED_BIRCH_LOGS)
			.add(WWBlockItemIds.HOLLOWED_BIRCH_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_BIRCH_LOG);

		this.builder(WWBlockTags.HOLLOWED_CHERRY_LOGS)
			.add(WWBlockItemIds.HOLLOWED_CHERRY_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_CHERRY_LOG);

		this.builder(WWBlockTags.HOLLOWED_CRIMSON_STEMS)
			.add(WWBlockItemIds.HOLLOWED_CRIMSON_STEM, WWBlockItemIds.STRIPPED_HOLLOWED_CRIMSON_STEM);

		this.builder(WWBlockTags.HOLLOWED_DARK_OAK_LOGS)
			.add(WWBlockItemIds.HOLLOWED_DARK_OAK_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_DARK_OAK_LOG);

		this.builder(WWBlockTags.HOLLOWED_JUNGLE_LOGS)
			.add(WWBlockItemIds.HOLLOWED_JUNGLE_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_JUNGLE_LOG);

		this.builder(WWBlockTags.HOLLOWED_MANGROVE_LOGS)
			.add(WWBlockItemIds.HOLLOWED_MANGROVE_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_MANGROVE_LOG);

		this.builder(WWBlockTags.HOLLOWED_OAK_LOGS)
			.add(WWBlockItemIds.HOLLOWED_OAK_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_OAK_LOG);

		this.builder(WWBlockTags.HOLLOWED_SPRUCE_LOGS)
			.add(WWBlockItemIds.HOLLOWED_SPRUCE_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_SPRUCE_LOG);

		this.builder(WWBlockTags.HOLLOWED_WARPED_STEMS)
			.add(WWBlockItemIds.HOLLOWED_WARPED_STEM, WWBlockItemIds.STRIPPED_HOLLOWED_WARPED_STEM);

		this.builder(WWBlockTags.HOLLOWED_BAOBAB_LOGS)
			.add(WWBlockItemIds.HOLLOWED_BAOBAB_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_BAOBAB_LOG);

		this.builder(WWBlockTags.HOLLOWED_WILLOW_LOGS)
			.add(WWBlockItemIds.HOLLOWED_WILLOW_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_WILLOW_LOG);

		this.builder(WWBlockTags.HOLLOWED_CYPRESS_LOGS)
			.add(WWBlockItemIds.HOLLOWED_CYPRESS_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_CYPRESS_LOG);

		this.builder(WWBlockTags.HOLLOWED_PALM_LOGS)
			.add(WWBlockItemIds.HOLLOWED_PALM_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_PALM_LOG);

		this.builder(WWBlockTags.HOLLOWED_MAPLE_LOGS)
			.add(WWBlockItemIds.HOLLOWED_MAPLE_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_MAPLE_LOG);

		this.builder(WWBlockTags.HOLLOWED_PALE_OAK_LOGS)
			.add(WWBlockItemIds.HOLLOWED_PALE_OAK_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_PALE_OAK_LOG);

		this.builder(WWBlockTags.HOLLOWED_LOGS_THAT_BURN)
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

		this.builder(WWBlockTags.HOLLOWED_LOGS_DONT_BURN)
			.addOptionalTag(WWBlockTags.HOLLOWED_CRIMSON_STEMS)
			.addOptionalTag(WWBlockTags.HOLLOWED_WARPED_STEMS);

		this.builder(WWBlockTags.HOLLOWED_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS_DONT_BURN);

		this.builder(WWBlockTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_ACACIA_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_BIRCH_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_CHERRY_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_DARK_OAK_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_JUNGLE_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_MANGROVE_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_OAK_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_SPRUCE_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_PALE_OAK_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_BAOBAB_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_WILLOW_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_CYPRESS_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_PALM_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_MAPLE_LOG);

		this.builder(WWBlockTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_CRIMSON_STEM)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_WARPED_STEM);

		this.builder(WWBlockTags.STRIPPED_HOLLOWED_LOGS)
			.addOptionalTag(WWBlockTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWBlockTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN);

		this.builder(WWBlockTags.NON_OVERRIDEN_FALLING_LEAVES)
			.add(BlockItemIds.CHERRY_LEAVES)
			.add(BlockItemIds.PALE_OAK_LEAVES);

		this.builder(WWBlockTags.KILLS_TERMITE)
			.add(BlockIds.WATER, BlockIds.WATER_CAULDRON)
			.add(BlockIds.LAVA, BlockIds.LAVA_CAULDRON)
			.add(BlockItemIds.POWDER_SNOW.block(), BlockIds.POWDER_SNOW_CAULDRON)
			.add(BlockItemIds.CRIMSON_ROOTS, BlockItemIds.NETHER_SPROUTS)
			.add(BlockItemIds.CRIMSON_PLANKS, BlockItemIds.WARPED_PLANKS)
			.add(BlockItemIds.WEEPING_VINES.block(), BlockIds.WEEPING_VINES_PLANT)
			.add(BlockItemIds.TWISTING_VINES.block(), BlockIds.TWISTING_VINES_PLANT)
			.add(BlockItemIds.CRIMSON_SLAB, BlockItemIds.WARPED_SLAB)
			.add(BlockItemIds.CRIMSON_PRESSURE_PLATE, BlockItemIds.WARPED_PRESSURE_PLATE)
			.add(BlockItemIds.CRIMSON_FENCE, BlockItemIds.WARPED_FENCE)
			.add(BlockItemIds.CRIMSON_TRAPDOOR, BlockItemIds.WARPED_TRAPDOOR)
			.add(BlockItemIds.CRIMSON_FENCE_GATE, BlockItemIds.WARPED_FENCE_GATE)
			.add(BlockItemIds.CRIMSON_STAIRS, BlockItemIds.WARPED_STAIRS)
			.add(BlockItemIds.CRIMSON_BUTTON, BlockItemIds.WARPED_BUTTON)
			.add(BlockItemIds.CRIMSON_DOOR, BlockItemIds.WARPED_DOOR)
			.add(BlockItemIds.CRIMSON_SIGN, BlockItemIds.WARPED_SIGN)
			.add(BlockIds.CRIMSON_WALL_SIGN, BlockIds.WARPED_WALL_SIGN)
			.add(BlockItemIds.CRIMSON_HANGING_SIGN, BlockItemIds.WARPED_HANGING_SIGN)
			.add(BlockIds.CRIMSON_WALL_HANGING_SIGN, BlockIds.WARPED_WALL_HANGING_SIGN)
			.add(BlockItemIds.CRIMSON_STEM, BlockItemIds.WARPED_STEM)
			.add(BlockItemIds.STRIPPED_CRIMSON_STEM, BlockItemIds.STRIPPED_WARPED_STEM)
			.add(BlockItemIds.STRIPPED_CRIMSON_HYPHAE, BlockItemIds.STRIPPED_WARPED_HYPHAE)
			.add(BlockItemIds.CRIMSON_NYLIUM, BlockItemIds.WARPED_NYLIUM)
			.add(BlockItemIds.CRIMSON_FUNGUS, BlockItemIds.WARPED_FUNGUS)
			.add(BlockItemIds.NETHER_WART_BLOCK, BlockItemIds.WARPED_WART_BLOCK)
			.add(BlockItemIds.NETHER_WART)
			.add(BlockItemIds.REDSTONE_DUST.block(), BlockItemIds.REDSTONE_BLOCK.block(), BlockItemIds.REDSTONE_TORCH.block(), BlockIds.REDSTONE_WALL_TORCH)
			.add(WWBlockItemIds.HOLLOWED_CRIMSON_STEM, WWBlockItemIds.HOLLOWED_WARPED_STEM)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_CRIMSON_STEM, WWBlockItemIds.STRIPPED_HOLLOWED_WARPED_STEM)
			.addOptionalTag(BlockItemTags.WARPED_STEMS.block())
			.addOptionalTag(BlockItemTags.CRIMSON_STEMS.block())
			.add(BlockItemIds.RESIN_CLUMP)
			.add(BlockItemIds.CREAKING_HEART)
			.add(BlockItemIds.CLOSED_EYEBLOSSOM, BlockItemIds.OPEN_EYEBLOSSOM)
			.add(WWBlockItemIds.PALE_MUSHROOM, WWBlockItemIds.PALE_SHELF_FUNGI, WWBlockItemIds.PALE_MUSHROOM_BLOCK);

		this.builder(WWBlockTags.BLOCKS_TERMITE)
			.addOptionalTag(ConventionalBlockTags.GLASS_BLOCKS)
			.addOptionalTag(ConventionalBlockTags.GLASS_PANES);

		this.builder(WWBlockTags.CANNOT_SUPPORT_UPWARDS_TERMITE_MOVEMENT)
			.addOptionalTag(BlockTags.INSIDE_STEP_SOUND_BLOCKS)
			.addOptionalTag(BlockTags.REPLACEABLE_BY_TREES)
			.addOptionalTag(BlockTags.FLOWERS);
	}

	private void generateMinecraft() {
		this.builder(BlockTags.MINEABLE_WITH_AXE)
			.add(BlockItemIds.CACTUS)
			.add(BlockItemIds.SWEET_BERRY_CROP)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS)
			.add(WWBlockItemIds.TUMBLEWEED, WWBlockItemIds.TUMBLEWEED_PLANT)
			.add(WWBlockItemIds.PRICKLY_PEAR)
			.add(WWBlockItemIds.MYCELIUM_GROWTH)
			.add(WWBlockItemIds.BROWN_SHELF_FUNGI, WWBlockItemIds.RED_SHELF_FUNGI, WWBlockItemIds.PALE_SHELF_FUNGI)
			.add(WWBlockItemIds.CRIMSON_SHELF_FUNGI, WWBlockItemIds.WARPED_SHELF_FUNGI)
			.add(WWBlockItemIds.PALE_MUSHROOM_BLOCK, WWBlockItemIds.PALE_MUSHROOM)
			.add(WWBlockItemIds.CLOVERS)
			.add(WWBlockItemIds.FROZEN_SHORT_GRASS, WWBlockItemIds.FROZEN_TALL_GRASS)
			.add(WWBlockItemIds.FROZEN_FERN, WWBlockItemIds.FROZEN_LARGE_FERN)
			.add(WWBlockItemIds.FROZEN_BUSH)
			.add(WWBlockItemIds.BARNACLES);

		this.builder(BlockTags.MINEABLE_WITH_HOE)
			.add(BlockItemIds.SWEET_BERRY_CROP)
			.add(WWBlockItemIds.OSSEOUS_SCULK)
			.add(WWBlockItemIds.SCULK_SLAB, WWBlockItemIds.SCULK_STAIRS, WWBlockItemIds.SCULK_WALL)
			.add(WWBlockItemIds.HANGING_TENDRIL)
			.add(WWBlockItemIds.BAOBAB_LEAVES)
			.add(WWBlockItemIds.WILLOW_LEAVES)
			.add(WWBlockItemIds.CYPRESS_LEAVES)
			.add(WWBlockItemIds.PALM_FRONDS)
			.add(WWBlockItemIds.YELLOW_MAPLE_LEAVES)
			.add(WWBlockItemIds.ORANGE_MAPLE_LEAVES)
			.add(WWBlockItemIds.RED_MAPLE_LEAVES)
			.add(WWBlockItemIds.TUMBLEWEED_PLANT)
			.add(WWBlockItemIds.TUMBLEWEED)
			.add(WWBlockItemIds.PRICKLY_PEAR)
			.add(WWBlockItemIds.SPONGE_BUD)
			.add(WWBlockItemIds.BARNACLES)
			.add(WWBlockItemIds.PHLOX)
			.add(WWBlockItemIds.LANTANAS)
			.add(WWBlockItemIds.AUBURN_MOSS_BLOCK)
			.add(WWBlockItemIds.AUBURN_MOSS_CARPET)
			.add(WWBlockItemIds.AUBURN_CREEPING_MOSS)
			.addOptionalTag(WWBlockTags.FROGLIGHT_GOOP);

		this.builder(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(WWBlockItemIds.STONE_CHEST)
			.add(WWBlockItemIds.NULL_BLOCK)
			.add(WWBlockItemIds.DISPLAY_LANTERN)
			.add(WWBlockItemIds.SCORCHED_SAND, WWBlockItemIds.SCORCHED_RED_SAND)
			.add(WWBlockItemIds.CHISELED_MUD_BRICKS)
			.add(WWBlockItemIds.CRACKED_MUD_BRICKS)
			.add(WWBlockItemIds.MOSSY_MUD_BRICKS, WWBlockItemIds.MOSSY_MUD_BRICK_STAIRS, WWBlockItemIds.MOSSY_MUD_BRICK_SLAB, WWBlockItemIds.MOSSY_MUD_BRICK_WALL)

			.add(WWBlockItemIds.GABBRO)
			.add(WWBlockItemIds.GEYSER)
			.add(WWBlockItemIds.GABBRO_STAIRS, WWBlockItemIds.GABBRO_SLAB, WWBlockItemIds.GABBRO_WALL)
			.add(WWBlockItemIds.POLISHED_GABBRO, WWBlockItemIds.POLISHED_GABBRO_STAIRS, WWBlockItemIds.POLISHED_GABBRO_SLAB, WWBlockItemIds.POLISHED_GABBRO_WALL)
			.add(WWBlockItemIds.CHISELED_GABBRO_BRICKS)
			.add(WWBlockItemIds.GABBRO_BRICKS)
			.add(WWBlockItemIds.CRACKED_GABBRO_BRICKS)
			.add(WWBlockItemIds.GABBRO_BRICK_STAIRS, WWBlockItemIds.GABBRO_BRICK_SLAB, WWBlockItemIds.GABBRO_BRICK_WALL)
			.add(WWBlockItemIds.MOSSY_GABBRO_BRICKS, WWBlockItemIds.MOSSY_GABBRO_BRICK_STAIRS, WWBlockItemIds.MOSSY_GABBRO_BRICK_SLAB, WWBlockItemIds.MOSSY_GABBRO_BRICK_WALL)

			.add(WWBlockItemIds.FRAGILE_ICE)
			.add(WWBlockItemIds.ICICLE)

			.add(WWBlockItemIds.BARNACLES);

		this.builder(BlockTags.MINEABLE_WITH_SHOVEL)
			.add(WWBlockItemIds.SCORCHED_SAND)
			.add(WWBlockItemIds.SCORCHED_RED_SAND)
			.add(WWBlockItemIds.TERMITE_MOUND)
			.add(WWBlockItemIds.PLANKTON)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.builder(BlockTags.SWORD_EFFICIENT)
			.add(WWBlockItemIds.SHRUB)
			.add(WWBlockItemIds.TUMBLEWEED, WWBlockItemIds.TUMBLEWEED_PLANT)
			.add(WWBlockItemIds.MILKWEED)
			.add(WWBlockItemIds.DATURA)
			.add(WWBlockItemIds.CATTAIL)
			.add(WWBlockItemIds.FLOWERING_LILY_PAD)
			.add(WWBlockItemIds.ALGAE)
			.add(WWBlockItemIds.PLANKTON)
			.add(WWBlockItemIds.BROWN_SHELF_FUNGI)
			.add(WWBlockItemIds.RED_SHELF_FUNGI)
			.add(WWBlockItemIds.PALE_SHELF_FUNGI)
			.add(WWBlockItemIds.CRIMSON_SHELF_FUNGI)
			.add(WWBlockItemIds.WARPED_SHELF_FUNGI)
			.add(WWBlockItemIds.SPONGE_BUD)
			.add(WWBlockItemIds.BARNACLES)
			.add(WWBlockItemIds.PRICKLY_PEAR)
			.add(WWBlockItemIds.MYCELIUM_GROWTH)
			.add(WWBlockItemIds.PALE_MUSHROOM)
			.add(WWBlockItemIds.PHLOX)
			.add(WWBlockItemIds.LANTANAS)
			.add(WWBlockItemIds.CLOVERS)
			.add(WWBlockItemIds.FROZEN_SHORT_GRASS, WWBlockItemIds.FROZEN_TALL_GRASS)
			.add(WWBlockItemIds.FROZEN_FERN, WWBlockItemIds.FROZEN_LARGE_FERN)
			.add(WWBlockItemIds.FROZEN_BUSH)
			.add(WWBlockItemIds.AUBURN_MOSS_CARPET)
			.add(WWBlockItemIds.AUBURN_CREEPING_MOSS)
			.addOptionalTag(WWBlockTags.NEMATOCYSTS)
			.addOptionalTag(WWBlockTags.FROGLIGHT_GOOP);

		this.builder(BlockTags.ENDERMAN_HOLDABLE)
			.add(WWBlockItemIds.PALE_MUSHROOM);

		this.builder(BlockTags.SAND)
			.add(WWBlockItemIds.SCORCHED_SAND, WWBlockItemIds.SCORCHED_RED_SAND);

		this.builder(BlockTags.MOSS_BLOCKS)
			.add(WWBlockItemIds.AUBURN_MOSS_BLOCK);

		this.builder(BlockTags.SNIFFER_DIGGABLE_BLOCK)
			.add(WWBlockItemIds.AUBURN_MOSS_BLOCK);

		this.builder(BlockTags.NEEDS_STONE_TOOL)
			.add(WWBlockItemIds.GABBRO)
			.add(WWBlockItemIds.GEYSER)
			.add(WWBlockItemIds.GABBRO_STAIRS, WWBlockItemIds.GABBRO_SLAB, WWBlockItemIds.GABBRO_WALL)
			.add(WWBlockItemIds.POLISHED_GABBRO, WWBlockItemIds.POLISHED_GABBRO_STAIRS, WWBlockItemIds.POLISHED_GABBRO_SLAB, WWBlockItemIds.POLISHED_GABBRO_WALL)
			.add(WWBlockItemIds.CHISELED_GABBRO_BRICKS)
			.add(WWBlockItemIds.GABBRO_BRICKS)
			.add(WWBlockItemIds.CRACKED_GABBRO_BRICKS)
			.add(WWBlockItemIds.GABBRO_BRICK_STAIRS, WWBlockItemIds.GABBRO_BRICK_SLAB, WWBlockItemIds.GABBRO_BRICK_WALL)
			.add(WWBlockItemIds.MOSSY_GABBRO_BRICKS, WWBlockItemIds.MOSSY_GABBRO_BRICK_STAIRS, WWBlockItemIds.MOSSY_GABBRO_BRICK_SLAB, WWBlockItemIds.MOSSY_GABBRO_BRICK_WALL);

		this.builder(BlockTags.BASE_STONE_OVERWORLD)
			.add(WWBlockItemIds.GABBRO);

		this.builder(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
			.add(WWBlockItemIds.GABBRO);

		this.builder(BlockTags.STAIRS)
			.add(WWBlockItemIds.SCULK_STAIRS)
			.add(WWBlockItemIds.MOSSY_MUD_BRICK_STAIRS)
			.add(WWBlockItemIds.GABBRO_STAIRS)
			.add(WWBlockItemIds.POLISHED_GABBRO_STAIRS)
			.add(WWBlockItemIds.GABBRO_BRICK_STAIRS)
			.add(WWBlockItemIds.MOSSY_GABBRO_BRICK_STAIRS);

		this.builder(BlockTags.SLABS)
			.add(WWBlockItemIds.SCULK_SLAB)
			.add(WWBlockItemIds.MOSSY_MUD_BRICK_SLAB)
			.add(WWBlockItemIds.GABBRO_SLAB)
			.add(WWBlockItemIds.POLISHED_GABBRO_SLAB)
			.add(WWBlockItemIds.GABBRO_BRICK_SLAB)
			.add(WWBlockItemIds.MOSSY_GABBRO_BRICK_SLAB);

		this.builder(BlockTags.WALLS)
			.add(WWBlockItemIds.SCULK_WALL)
			.add(WWBlockItemIds.MOSSY_MUD_BRICK_WALL)
			.add(WWBlockItemIds.GABBRO_WALL)
			.add(WWBlockItemIds.POLISHED_GABBRO_WALL)
			.add(WWBlockItemIds.GABBRO_BRICK_WALL)
			.add(WWBlockItemIds.MOSSY_GABBRO_BRICK_WALL);

		this.builder(BlockTags.WOODEN_SHELVES)
			.add(WWBlockItemIds.BAOBAB_SHELF)
			.add(WWBlockItemIds.WILLOW_SHELF)
			.add(WWBlockItemIds.CYPRESS_SHELF)
			.add(WWBlockItemIds.PALM_SHELF)
			.add(WWBlockItemIds.MAPLE_SHELF);

		this.builder(BlockTags.CLIMBABLE)
			.addOptionalTag(WWBlockTags.FROGLIGHT_GOOP);

		this.builder(BlockTags.INSIDE_STEP_SOUND_BLOCKS)
			.add(BlockItemIds.COBWEB)
			.add(BlockItemIds.LILY_PAD)
			.add(WWBlockItemIds.FLOWERING_LILY_PAD)
			.add(WWBlockItemIds.ALGAE)
			.add(WWBlockItemIds.PLANKTON)
			.add(WWBlockItemIds.TUMBLEWEED_PLANT)
			.add(WWBlockItemIds.SPONGE_BUD)
			.add(WWBlockItemIds.BARNACLES)
			.add(WWBlockItemIds.PRICKLY_PEAR)
			.add(WWBlockItemIds.POLLEN)
			.add(WWBlockItemIds.PHLOX)
			.add(WWBlockItemIds.LANTANAS);

		this.builder(BlockTags.REPLACEABLE)
			.add(WWBlockItemIds.MYCELIUM_GROWTH);

		this.builder(BlockTags.GEODE_INVALID_BLOCKS)
			.add(WWBlockItemIds.FRAGILE_ICE);

		this.builder(BlockTags.CANNOT_SUPPORT_SNOW_LAYER)
			.add(WWBlockItemIds.FRAGILE_ICE);

		this.builder(BlockTags.ICE)
			.add(WWBlockItemIds.FRAGILE_ICE);

		this.builder(BlockTags.SPELEOTHEMS)
			.add(WWBlockItemIds.ICICLE);

		this.builder(BlockTags.REPLACEABLE_BY_TREES)
			.add(WWBlockItemIds.MYCELIUM_GROWTH)
			.add(WWBlockItemIds.DATURA)
			.add(WWBlockItemIds.CATTAIL)
			.add(WWBlockItemIds.MILKWEED)
			.add(WWBlockItemIds.SHRUB)
			.add(WWBlockItemIds.POLLEN)
			.add(WWBlockItemIds.BARNACLES)
			.add(WWBlockItemIds.PHLOX)
			.add(WWBlockItemIds.LANTANAS)
			.add(WWBlockItemIds.CLOVERS)
			.add(WWBlockItemIds.FROZEN_SHORT_GRASS, WWBlockItemIds.FROZEN_TALL_GRASS)
			.add(WWBlockItemIds.FROZEN_FERN, WWBlockItemIds.FROZEN_LARGE_FERN)
			.add(WWBlockItemIds.FROZEN_BUSH)
			.add(WWBlockItemIds.SEA_ANEMONE)
			.add(WWBlockItemIds.SEA_WHIP)
			.add(WWBlockItemIds.TUBE_WORMS)
			.addOptionalTag(WWBlockTags.LEAF_LITTERS);

		this.builder(BlockTags.REPLACEABLE_BY_MUSHROOMS)
			.add(WWBlockItemIds.MYCELIUM_GROWTH)
			.add(WWBlockItemIds.DATURA)
			.add(WWBlockItemIds.CATTAIL)
			.add(WWBlockItemIds.MILKWEED)
			.add(WWBlockItemIds.SHRUB)
			.add(WWBlockItemIds.POLLEN)
			.add(WWBlockItemIds.PHLOX)
			.add(WWBlockItemIds.LANTANAS)
			.add(WWBlockItemIds.CLOVERS)
			.add(WWBlockItemIds.FROZEN_SHORT_GRASS)
			.add(WWBlockItemIds.FROZEN_TALL_GRASS)
			.add(WWBlockItemIds.FROZEN_FERN)
			.add(WWBlockItemIds.FROZEN_LARGE_FERN)
			.add(WWBlockItemIds.FROZEN_BUSH)
			.addOptionalTag(WWBlockTags.LEAF_LITTERS);

		this.builder(BlockTags.COMBINATION_STEP_SOUND_BLOCKS)
			.add(WWBlockItemIds.POLLEN)
			.add(WWBlockItemIds.BARNACLES)
			.add(WWBlockItemIds.AUBURN_MOSS_CARPET)
			.add(WWBlockItemIds.AUBURN_CREEPING_MOSS)
			.addOptionalTag(WWBlockTags.LEAF_LITTERS);

		this.builder(BlockTags.FLOWER_POTS)
			.add(WWBlockIds.POTTED_BAOBAB_NUT)
			.add(WWBlockIds.POTTED_WILLOW_SAPLING)
			.add(WWBlockIds.POTTED_CYPRESS_SAPLING)
			.add(WWBlockIds.POTTED_COCONUT)
			.add(WWBlockIds.POTTED_YELLOW_MAPLE_SAPLING, WWBlockIds.POTTED_ORANGE_MAPLE_SAPLING, WWBlockIds.POTTED_RED_MAPLE_SAPLING)
			.add(WWBlockIds.POTTED_SHRUB)
			.add(WWBlockIds.POTTED_BUSH)
			.add(WWBlockIds.POTTED_BIG_DRIPLEAF)
			.add(WWBlockIds.POTTED_SMALL_DRIPLEAF)
			.add(WWBlockIds.POTTED_SHORT_GRASS)
			.add(WWBlockIds.POTTED_MYCELIUM_GROWTH)
			.add(WWBlockIds.POTTED_TUMBLEWEED)
			.add(WWBlockIds.POTTED_TUMBLEWEED_PLANT)
			.add(WWBlockIds.POTTED_PRICKLY_PEAR)
			.add(WWBlockIds.POTTED_CARNATION)
			.add(WWBlockIds.POTTED_MARIGOLD)
			.add(WWBlockIds.POTTED_SEEDING_DANDELION)
			.add(WWBlockIds.POTTED_PASQUEFLOWER)
			.add(WWBlockIds.POTTED_RED_HIBISCUS)
			.add(WWBlockIds.POTTED_YELLOW_HIBISCUS)
			.add(WWBlockIds.POTTED_WHITE_HIBISCUS)
			.add(WWBlockIds.POTTED_PINK_HIBISCUS)
			.add(WWBlockIds.POTTED_PURPLE_HIBISCUS)
			.add(WWBlockIds.POTTED_PINK_PETALS)
			.add(WWBlockIds.POTTED_BUSH)
			.add(WWBlockIds.POTTED_FIREFLY_BUSH)
			.add(WWBlockIds.POTTED_SHORT_DRY_GRASS)
			.add(WWBlockIds.POTTED_TALL_DRY_GRASS)
			.add(WWBlockIds.POTTED_CACTUS_FLOWER)
			.add(WWBlockIds.POTTED_WILDFLOWERS)
			.add(WWBlockIds.POTTED_PHLOX)
			.add(WWBlockIds.POTTED_LANTANAS)
			.add(WWBlockIds.POTTED_FROZEN_SHORT_GRASS)
			.add(WWBlockIds.POTTED_FROZEN_FERN)
			.add(WWBlockIds.POTTED_FROZEN_BUSH)
			.add(WWBlockIds.POTTED_PALE_MUSHROOM);

		this.builder(BlockTags.FLOWERS)
			.add(WWBlockItemIds.DATURA)
			.add(WWBlockItemIds.CATTAIL)
			.add(WWBlockItemIds.MILKWEED)
			.add(WWBlockItemIds.POLLEN)
			.add(WWBlockItemIds.PHLOX)
			.add(WWBlockItemIds.LANTANAS);

		this.builder(BlockTags.SMALL_FLOWERS)
			.add(WWBlockItemIds.CARNATION)
			.add(WWBlockItemIds.MARIGOLD)
			.add(WWBlockItemIds.SEEDING_DANDELION)
			.add(WWBlockItemIds.PASQUEFLOWER)
			.add(WWBlockItemIds.RED_HIBISCUS)
			.add(WWBlockItemIds.YELLOW_HIBISCUS)
			.add(WWBlockItemIds.WHITE_HIBISCUS)
			.add(WWBlockItemIds.PINK_HIBISCUS)
			.add(WWBlockItemIds.PURPLE_HIBISCUS)
			.add(WWBlockItemIds.FLOWERING_LILY_PAD);

		this.builder(BlockTags.BEE_ATTRACTIVE)
			.add(WWBlockItemIds.CARNATION)
			.add(WWBlockItemIds.MARIGOLD)
			.add(WWBlockItemIds.SEEDING_DANDELION)
			.add(WWBlockItemIds.RED_HIBISCUS)
			.add(WWBlockItemIds.YELLOW_HIBISCUS)
			.add(WWBlockItemIds.WHITE_HIBISCUS)
			.add(WWBlockItemIds.PINK_HIBISCUS)
			.add(WWBlockItemIds.PURPLE_HIBISCUS)
			.add(WWBlockItemIds.FLOWERING_LILY_PAD)
			.add(WWBlockItemIds.DATURA)
			.add(WWBlockItemIds.CATTAIL)
			.add(WWBlockItemIds.MILKWEED)
			.add(WWBlockItemIds.POLLEN);

		this.builder(BlockTags.FROG_PREFER_JUMP_TO)
			.add(WWBlockItemIds.FLOWERING_LILY_PAD);

		this.builder(BlockTags.SUPPORTS_BIG_DRIPLEAF)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.builder(BlockTags.SUPPORTS_SMALL_DRIPLEAF)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.builder(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH)
			.add(WWBlockItemIds.ALGAE)
			.add(WWBlockItemIds.PLANKTON)
			.add(WWBlockItemIds.AUBURN_MOSS_CARPET)
			.add(WWBlockItemIds.AUBURN_CREEPING_MOSS);

		this.builder(BlockTags.GUARDED_BY_PIGLINS)
			.add(WWBlockItemIds.STONE_CHEST);

		this.builder(BlockTags.IMPERMEABLE)
			.add(WWBlockItemIds.ECHO_GLASS)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.builder(BlockTags.FEATURES_CANNOT_REPLACE)
			.add(WWBlockItemIds.STONE_CHEST);

		this.builder(BlockTags.SCULK_REPLACEABLE_WORLD_GEN)
			.addOptionalTag(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)
			.addOptionalTag(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)
			.addOptionalTag(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN);

		this.builder(BlockTags.EDIBLE_FOR_SHEEP)
			.add(WWBlockItemIds.FROZEN_SHORT_GRASS)
			.add(WWBlockItemIds.FROZEN_FERN);

		this.builder(BlockTags.TRIGGERS_AMBIENT_DESERT_SAND_BLOCK_SOUNDS)
			.add(WWBlockItemIds.SCORCHED_SAND, WWBlockItemIds.SCORCHED_RED_SAND);

		this.builder(BlockTags.TRIGGERS_AMBIENT_DESERT_DRY_VEGETATION_BLOCK_SOUNDS)
			.add(WWBlockItemIds.SCORCHED_SAND, WWBlockItemIds.SCORCHED_RED_SAND);
	}

	private void generateWoods() {
		this.builder(BlockTags.OVERWORLD_NATURAL_LOGS)
			.add(WWBlockItemIds.BAOBAB_LOG)
			.add(WWBlockItemIds.WILLOW_LOG)
			.add(WWBlockItemIds.CYPRESS_LOG)
			.add(WWBlockItemIds.PALM_LOG)
			.add(WWBlockItemIds.MAPLE_LOG);

		this.builder(BlockItemTags.ACACIA_LOGS.block())
			.addOptionalTag(WWBlockTags.HOLLOWED_ACACIA_LOGS);

		this.builder(BlockItemTags.BIRCH_LOGS.block())
			.addOptionalTag(WWBlockTags.HOLLOWED_BIRCH_LOGS);

		this.builder(BlockItemTags.CHERRY_LOGS.block())
			.addOptionalTag(WWBlockTags.HOLLOWED_CHERRY_LOGS);

		this.builder(BlockItemTags.CRIMSON_STEMS.block())
			.addOptionalTag(WWBlockTags.HOLLOWED_CRIMSON_STEMS);

		this.builder(BlockItemTags.DARK_OAK_LOGS.block())
			.addOptionalTag(WWBlockTags.HOLLOWED_DARK_OAK_LOGS);

		this.builder(BlockTags.JUNGLE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_JUNGLE_LOGS);

		this.builder(BlockItemTags.ACACIA_LOGS.block())
			.addOptionalTag(WWBlockTags.HOLLOWED_ACACIA_LOGS);

		this.builder(BlockItemTags.MANGROVE_LOGS.block())
			.addOptionalTag(WWBlockTags.HOLLOWED_MANGROVE_LOGS);

		this.builder(BlockItemTags.OAK_LOGS.block())
			.addOptionalTag(WWBlockTags.HOLLOWED_OAK_LOGS);

		this.builder(BlockItemTags.ACACIA_LOGS.block())
			.addOptionalTag(WWBlockTags.HOLLOWED_ACACIA_LOGS);

		this.builder(BlockItemTags.SPRUCE_LOGS.block())
			.addOptionalTag(WWBlockTags.HOLLOWED_SPRUCE_LOGS);

		this.builder(BlockTags.PALE_OAK_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_PALE_OAK_LOGS);

		this.builder(BlockItemTags.WARPED_STEMS.block())
			.addOptionalTag(WWBlockTags.HOLLOWED_WARPED_STEMS);

		this.builder(WWBlockTags.BAOBAB_LOGS)
			.add(WWBlockItemIds.BAOBAB_LOG, WWBlockItemIds.STRIPPED_BAOBAB_LOG)
			.add(WWBlockItemIds.BAOBAB_WOOD, WWBlockItemIds.STRIPPED_BAOBAB_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_BAOBAB_LOGS);

		this.builder(WWBlockTags.WILLOW_LOGS)
			.add(WWBlockItemIds.WILLOW_LOG, WWBlockItemIds.STRIPPED_WILLOW_LOG)
			.add(WWBlockItemIds.WILLOW_WOOD, WWBlockItemIds.STRIPPED_WILLOW_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_WILLOW_LOGS);

		this.builder(WWBlockTags.CYPRESS_LOGS)
			.add(WWBlockItemIds.CYPRESS_LOG, WWBlockItemIds.STRIPPED_CYPRESS_LOG)
			.add(WWBlockItemIds.CYPRESS_WOOD, WWBlockItemIds.STRIPPED_CYPRESS_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_CYPRESS_LOGS);

		this.builder(WWBlockTags.PALM_LOGS)
			.add(WWBlockItemIds.PALM_LOG, WWBlockItemIds.STRIPPED_PALM_LOG)
			.add(WWBlockItemIds.PALM_WOOD, WWBlockItemIds.STRIPPED_PALM_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_PALM_LOGS);

		this.builder(WWBlockTags.MAPLE_LOGS)
			.add(WWBlockItemIds.MAPLE_LOG, WWBlockItemIds.STRIPPED_MAPLE_LOG)
			.add(WWBlockItemIds.MAPLE_WOOD, WWBlockItemIds.STRIPPED_MAPLE_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_MAPLE_LOGS);

		this.builder(BlockTags.LOGS)
			.addOptionalTag(WWBlockTags.BAOBAB_LOGS)
			.addOptionalTag(WWBlockTags.WILLOW_LOGS)
			.addOptionalTag(WWBlockTags.CYPRESS_LOGS)
			.addOptionalTag(WWBlockTags.PALM_LOGS)
			.addOptionalTag(WWBlockTags.MAPLE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS);

		this.builder(BlockItemTags.LOGS_THAT_BURN.block())
			.addOptionalTag(WWBlockTags.BAOBAB_LOGS)
			.addOptionalTag(WWBlockTags.WILLOW_LOGS)
			.addOptionalTag(WWBlockTags.CYPRESS_LOGS)
			.addOptionalTag(WWBlockTags.PALM_LOGS)
			.addOptionalTag(WWBlockTags.MAPLE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS_THAT_BURN);

		this.builder(WWBlockTags.WILLOW_ROOTS_CAN_GROW_THROUGH)
			.add(BlockItemIds.MUDDY_MANGROVE_ROOTS)
			.add(BlockItemIds.MANGROVE_ROOTS)
			.add(BlockItemIds.MOSS_CARPET)
			.add(BlockItemIds.VINE)
			.add(BlockItemIds.SNOW)
			.add(BlockItemIds.RED_MUSHROOM)
			.add(BlockItemIds.BROWN_MUSHROOM)
			.add(WWBlockItemIds.ALGAE)
			.add(WWBlockItemIds.PLANKTON)
			.add(WWBlockItemIds.AUBURN_MOSS_CARPET)
			.add(WWBlockItemIds.AUBURN_CREEPING_MOSS)
			.addOptionalTag(BlockTags.SMALL_FLOWERS);

		this.builder(WWBlockTags.FALLEN_TREE_STUMP_PLACEABLE_ON)
			.add(BlockItemIds.MOSS_BLOCK)
			.add(BlockItemIds.PALE_MOSS_BLOCK)
			.add(BlockItemIds.SANDSTONE)
			.add(BlockItemIds.RED_SANDSTONE)
			.add(BlockItemIds.CLAY)
			.add(BlockItemIds.GRAVEL)
			.add(BlockItemIds.SNOW_BLOCK)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.SUBSTRATE_OVERWORLD);

		this.builder(BlockTags.LEAVES)
			.add(WWBlockItemIds.BAOBAB_LEAVES)
			.add(WWBlockItemIds.WILLOW_LEAVES)
			.add(WWBlockItemIds.CYPRESS_LEAVES)
			.add(WWBlockItemIds.PALM_FRONDS)
			.add(WWBlockItemIds.YELLOW_MAPLE_LEAVES)
			.add(WWBlockItemIds.ORANGE_MAPLE_LEAVES)
			.add(WWBlockItemIds.RED_MAPLE_LEAVES);

		this.builder(WWBlockTags.LEAF_LITTERS)
			.add(BlockItemIds.LEAF_LITTER)
			.add(WWBlockItemIds.ACACIA_LEAF_LITTER)
			.add(WWBlockItemIds.AZALEA_LEAF_LITTER)
			.add(WWBlockItemIds.BAOBAB_LEAF_LITTER)
			.add(WWBlockItemIds.BIRCH_LEAF_LITTER)
			.add(WWBlockItemIds.CHERRY_LEAF_LITTER)
			.add(WWBlockItemIds.CYPRESS_LEAF_LITTER)
			.add(WWBlockItemIds.DARK_OAK_LEAF_LITTER)
			.add(WWBlockItemIds.JUNGLE_LEAF_LITTER)
			.add(WWBlockItemIds.MANGROVE_LEAF_LITTER)
			.add(WWBlockItemIds.PALE_OAK_LEAF_LITTER)
			.add(WWBlockItemIds.PALM_FROND_LITTER)
			.add(WWBlockItemIds.SPRUCE_LEAF_LITTER)
			.add(WWBlockItemIds.WILLOW_LEAF_LITTER)
			.add(WWBlockItemIds.YELLOW_MAPLE_LEAF_LITTER)
			.add(WWBlockItemIds.ORANGE_MAPLE_LEAF_LITTER)
			.add(WWBlockItemIds.RED_MAPLE_LEAF_LITTER);

		this.builder(BlockTags.PLANKS)
			.add(WWBlockItemIds.BAOBAB_PLANKS)
			.add(WWBlockItemIds.WILLOW_PLANKS)
			.add(WWBlockItemIds.CYPRESS_PLANKS)
			.add(WWBlockItemIds.PALM_PLANKS)
			.add(WWBlockItemIds.MAPLE_PLANKS);

		this.builder(BlockItemTags.SAPLINGS.block())
			.add(WWBlockItemIds.BAOBAB_NUT)
			.add(WWBlockItemIds.WILLOW_SAPLING)
			.add(WWBlockItemIds.CYPRESS_SAPLING)
			.add(WWBlockItemIds.COCONUT)
			.add(WWBlockItemIds.YELLOW_MAPLE_SAPLING, WWBlockItemIds.ORANGE_MAPLE_SAPLING, WWBlockItemIds.RED_MAPLE_SAPLING);

		this.builder(BlockTags.STANDING_SIGNS)
			.add(WWBlockItemIds.BAOBAB_SIGN)
			.add(WWBlockItemIds.WILLOW_SIGN)
			.add(WWBlockItemIds.CYPRESS_SIGN)
			.add(WWBlockItemIds.PALM_SIGN)
			.add(WWBlockItemIds.MAPLE_SIGN);

		this.builder(BlockTags.WALL_SIGNS)
			.add(WWBlockIds.BAOBAB_WALL_SIGN)
			.add(WWBlockIds.WILLOW_WALL_SIGN)
			.add(WWBlockIds.CYPRESS_WALL_SIGN)
			.add(WWBlockIds.PALM_WALL_SIGN)
			.add(WWBlockIds.MAPLE_WALL_SIGN);

		this.builder(BlockTags.CEILING_HANGING_SIGNS)
			.add(WWBlockItemIds.BAOBAB_HANGING_SIGN)
			.add(WWBlockItemIds.WILLOW_HANGING_SIGN)
			.add(WWBlockItemIds.CYPRESS_HANGING_SIGN)
			.add(WWBlockItemIds.PALM_HANGING_SIGN)
			.add(WWBlockItemIds.MAPLE_HANGING_SIGN);

		this.builder(BlockTags.WALL_HANGING_SIGNS)
			.add(WWBlockIds.BAOBAB_WALL_HANGING_SIGN)
			.add(WWBlockIds.WILLOW_WALL_HANGING_SIGN)
			.add(WWBlockIds.CYPRESS_WALL_HANGING_SIGN)
			.add(WWBlockIds.PALM_WALL_HANGING_SIGN)
			.add(WWBlockIds.MAPLE_WALL_HANGING_SIGN);

		this.builder(BlockTags.WOODEN_BUTTONS)
			.add(WWBlockItemIds.BAOBAB_BUTTON)
			.add(WWBlockItemIds.WILLOW_BUTTON)
			.add(WWBlockItemIds.CYPRESS_BUTTON)
			.add(WWBlockItemIds.PALM_BUTTON)
			.add(WWBlockItemIds.MAPLE_BUTTON);

		this.builder(BlockTags.WOODEN_DOORS)
			.add(WWBlockItemIds.BAOBAB_DOOR)
			.add(WWBlockItemIds.WILLOW_DOOR)
			.add(WWBlockItemIds.CYPRESS_DOOR)
			.add(WWBlockItemIds.PALM_DOOR)
			.add(WWBlockItemIds.MAPLE_DOOR);

		this.builder(BlockTags.WOODEN_FENCES)
			.add(WWBlockItemIds.BAOBAB_FENCE)
			.add(WWBlockItemIds.WILLOW_FENCE)
			.add(WWBlockItemIds.CYPRESS_FENCE)
			.add(WWBlockItemIds.PALM_FENCE)
			.add(WWBlockItemIds.MAPLE_FENCE);

		this.builder(BlockTags.FENCE_GATES)
			.add(WWBlockItemIds.BAOBAB_FENCE_GATE)
			.add(WWBlockItemIds.WILLOW_FENCE_GATE)
			.add(WWBlockItemIds.CYPRESS_FENCE_GATE)
			.add(WWBlockItemIds.PALM_FENCE_GATE)
			.add(WWBlockItemIds.MAPLE_FENCE_GATE);

		this.builder(BlockTags.WOODEN_PRESSURE_PLATES)
			.add(WWBlockItemIds.BAOBAB_PRESSURE_PLATE)
			.add(WWBlockItemIds.WILLOW_PRESSURE_PLATE)
			.add(WWBlockItemIds.CYPRESS_PRESSURE_PLATE)
			.add(WWBlockItemIds.PALM_PRESSURE_PLATE)
			.add(WWBlockItemIds.MAPLE_PRESSURE_PLATE);

		this.builder(BlockTags.WOODEN_SLABS)
			.add(WWBlockItemIds.BAOBAB_SLAB)
			.add(WWBlockItemIds.WILLOW_SLAB)
			.add(WWBlockItemIds.CYPRESS_SLAB)
			.add(WWBlockItemIds.PALM_SLAB)
			.add(WWBlockItemIds.MAPLE_SLAB);

		this.builder(BlockTags.WOODEN_STAIRS)
			.add(WWBlockItemIds.BAOBAB_STAIRS)
			.add(WWBlockItemIds.WILLOW_STAIRS)
			.add(WWBlockItemIds.CYPRESS_STAIRS)
			.add(WWBlockItemIds.PALM_STAIRS)
			.add(WWBlockItemIds.MAPLE_STAIRS);

		this.builder(BlockTags.WOODEN_TRAPDOORS)
			.add(WWBlockItemIds.BAOBAB_TRAPDOOR)
			.add(WWBlockItemIds.WILLOW_TRAPDOOR)
			.add(WWBlockItemIds.CYPRESS_TRAPDOOR)
			.add(WWBlockItemIds.PALM_TRAPDOOR)
			.add(WWBlockItemIds.MAPLE_TRAPDOOR);
	}

	private void generateSounds() {
		this.builder(WWBlockTags.SOUND_PALE_OAK_LEAF_LITTER)
			.add(WWBlockItemIds.PALE_OAK_LEAF_LITTER);

		this.builder(WWBlockTags.SOUND_PALE_OAK_LEAVES)
			.add(BlockItemIds.PALE_OAK_LEAVES);

		this.builder(WWBlockTags.SOUND_HOLLOWED_PALE_OAK_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_PALE_OAK_LOGS);

		this.builder(WWBlockTags.SOUND_PALE_OAK_WOOD_HANGING_SIGN)
			.add(BlockItemIds.PALE_OAK_HANGING_SIGN.block(), BlockIds.PALE_OAK_WALL_HANGING_SIGN);

		this.builder(WWBlockTags.SOUND_PALE_OAK_WOOD)
			.add(BlockItemIds.PALE_OAK_LOG, BlockItemIds.STRIPPED_PALE_OAK_LOG)
			.add(BlockItemIds.PALE_OAK_WOOD, BlockItemIds.STRIPPED_PALE_OAK_WOOD)
			.add(BlockItemIds.PALE_OAK_PLANKS, BlockItemIds.PALE_OAK_STAIRS, BlockItemIds.PALE_OAK_SLAB)
			.add(BlockItemIds.PALE_OAK_FENCE, BlockItemIds.PALE_OAK_FENCE_GATE)
			.add(BlockItemIds.PALE_OAK_BUTTON, BlockItemIds.PALE_OAK_PRESSURE_PLATE)
			.add(BlockItemIds.PALE_OAK_DOOR, BlockItemIds.PALE_OAK_TRAPDOOR)
			.add(BlockItemIds.PALE_OAK_SIGN.block(), BlockIds.PALE_OAK_WALL_SIGN);

		this.builder(WWBlockTags.SOUND_PALE_MOSS_CARPET)
			.add(BlockItemIds.PALE_MOSS_CARPET, BlockItemIds.PALE_HANGING_MOSS);

		this.builder(WWBlockTags.SOUND_PALE_MOSS)
			.add(BlockItemIds.PALE_MOSS_BLOCK);

		this.builder(WWBlockTags.SOUND_AUBURN_MOSS_CARPET)
			.add(WWBlockItemIds.AUBURN_MOSS_CARPET, WWBlockItemIds.AUBURN_CREEPING_MOSS);

		this.builder(WWBlockTags.SOUND_AUBURN_MOSS)
			.add(WWBlockItemIds.AUBURN_MOSS_BLOCK);

		this.builder(WWBlockTags.SOUND_COCONUT)
			.add(WWBlockItemIds.COCONUT)

			.addOptional(this.getKey("natures_spirit", "coconut"))
			.addOptional(this.getKey("natures_spirit", "young_coconut"));

		this.builder(WWBlockTags.SOUND_MAGMA_BLOCK)
			.add(BlockItemIds.MAGMA_BLOCK);

		this.builder(WWBlockTags.SOUND_WITHER_ROSE)
			.add(BlockItemIds.WITHER_ROSE);

		this.builder(WWBlockTags.SOUND_SUGAR_CANE)
			.add(BlockItemIds.SUGAR_CANE);

		this.builder(WWBlockTags.SOUND_REINFORCED_DEEPSLATE)
			.add(BlockItemIds.REINFORCED_DEEPSLATE);

		this.builder(WWBlockTags.SOUND_PODZOL)
			.add(BlockItemIds.PODZOL);

		this.builder(WWBlockTags.SOUND_DEAD_BUSH)
			.add(BlockItemIds.DEAD_BUSH);

		this.builder(WWBlockTags.SOUND_CLAY)
			.add(BlockItemIds.CLAY);

		this.builder(WWBlockTags.SOUND_GRAVEL)
			.add(BlockItemIds.GRAVEL);

		this.builder(WWBlockTags.SOUND_MELON)
			.add(BlockItemIds.PUMPKIN, BlockItemIds.CARVED_PUMPKIN, BlockItemIds.JACK_O_LANTERN)
			.add(BlockItemIds.MELON);

		this.builder(WWBlockTags.SOUND_MELON_STEM)
			.add(BlockItemIds.PUMPKIN_CROP.block(), BlockIds.ATTACHED_PUMPKIN_STEM)
			.add(BlockItemIds.MELON_CROP.block(), BlockIds.ATTACHED_MELON_STEM);

		this.builder(WWBlockTags.SOUND_LILY_PAD)
			.add(BlockItemIds.LILY_PAD)
			.add(WWBlockItemIds.FLOWERING_LILY_PAD);

		this.builder(WWBlockTags.SOUND_SANDSTONE)
			.addOptionalTag(ConventionalBlockTags.SANDSTONE_BLOCKS);

		this.builder(WWBlockTags.SOUND_MUSHROOM_BLOCK)
			.add(BlockItemIds.RED_MUSHROOM_BLOCK)
			.add(BlockItemIds.BROWN_MUSHROOM_BLOCK)
			.add(WWBlockItemIds.PALE_MUSHROOM_BLOCK)
			.add(BlockItemIds.MUSHROOM_STEM)

			.addOptional(this.getKey("betterend", "mossy_glowshroom_cap"))
			.addOptional(this.getKey("betterend", "mossy_glowshroom_fur"))
			.addOptional(this.getKey("betterend", "jellyshroom_cap_purple"))

			.addOptional(this.getKey("betternether", "red_large_mushroom"))
			.addOptional(this.getKey("betternether", "brown_large_mushroom"))

			.addOptional(this.getKey("biomesoplenty", "glowshroom_block"))
			.addOptional(this.getKey("natures_spirit","shitake_mushroom_block"))
			.addOptional(this.getKey("regions_unexplored","blue_bioshroom"));

		this.builder(WWBlockTags.SOUND_MUSHROOM)
			.add(BlockItemIds.RED_MUSHROOM)
			.add(BlockItemIds.BROWN_MUSHROOM)
			.add(WWBlockItemIds.PALE_MUSHROOM)

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

		this.builder(WWBlockTags.SOUND_FROSTED_ICE)
			.add(BlockIds.FROSTED_ICE, WWBlockItemIds.FRAGILE_ICE.block());

		this.builder(WWBlockTags.SOUND_ICE)
			.add(BlockItemIds.ICE, BlockItemIds.PACKED_ICE, BlockItemIds.BLUE_ICE)
			.add(WWBlockItemIds.ICICLE);

		this.builder(WWBlockTags.SOUND_COARSE_DIRT)
			.add(BlockItemIds.COARSE_DIRT)
			.addOptional(this.getKey("natures_spirit","sandy_soil"))
			.addOptional(this.getKey("regions_unexplored","peat_coarse_dirt"))
			.addOptional(this.getKey("regions_unexplored","silt_coarse_dirt"));

		this.builder(WWBlockTags.SOUND_CACTUS)
			.add(BlockItemIds.CACTUS)
			.add(WWBlockItemIds.PRICKLY_PEAR)

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
			.add(BlockItemIds.ACACIA_SAPLING)
			.add(BlockItemIds.BIRCH_SAPLING)
			.add(BlockItemIds.DARK_OAK_SAPLING)
			.add(BlockItemIds.JUNGLE_SAPLING)
			.add(BlockItemIds.MANGROVE_PROPAGULE)
			.add(BlockItemIds.OAK_SAPLING)
			.add(BlockItemIds.SPRUCE_SAPLING)
			.add(WWBlockItemIds.WILLOW_SAPLING)
			.add(WWBlockItemIds.CYPRESS_SAPLING)
			.add(WWBlockItemIds.PALM_FRONDS)
			.add(WWBlockItemIds.YELLOW_MAPLE_SAPLING)
			.add(WWBlockItemIds.ORANGE_MAPLE_SAPLING)
			.add(WWBlockItemIds.RED_MAPLE_SAPLING)
			.add(WWBlockItemIds.SHRUB)
			.add(BlockItemIds.PALE_OAK_SAPLING)

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

		this.builder(WWBlockTags.SOUND_CONIFER_LEAF_LITTER)
			.add(WWBlockItemIds.SPRUCE_LEAF_LITTER)
			.add(WWBlockItemIds.CYPRESS_LEAF_LITTER);

		this.builder(WWBlockTags.SOUND_CONIFER_LEAVES)
			.add(BlockItemIds.SPRUCE_LEAVES)
			.add(WWBlockItemIds.CYPRESS_LEAVES)

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
			.add(BlockItemIds.ACACIA_LEAVES)
			.add(BlockItemIds.BIRCH_LEAVES)
			.add(BlockItemIds.DARK_OAK_LEAVES)
			.add(BlockItemIds.JUNGLE_LEAVES)
			.add(BlockItemIds.MANGROVE_LEAVES)
			.add(BlockItemIds.OAK_LEAVES)
			.add(WWBlockItemIds.BAOBAB_LEAVES)
			.add(WWBlockItemIds.WILLOW_LEAVES)
			.add(WWBlockItemIds.PALM_FRONDS)

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
			.add(BlockItemIds.DANDELION)
			.add(BlockItemIds.POPPY)
			.add(BlockItemIds.BLUE_ORCHID)
			.add(BlockItemIds.ALLIUM)
			.add(BlockItemIds.AZURE_BLUET)
			.add(BlockItemIds.ORANGE_TULIP)
			.add(BlockItemIds.PINK_TULIP)
			.add(BlockItemIds.RED_TULIP)
			.add(BlockItemIds.WHITE_TULIP)
			.add(BlockItemIds.OXEYE_DAISY)
			.add(BlockItemIds.CORNFLOWER)
			.add(BlockItemIds.LILY_OF_THE_VALLEY)
			.add(WWBlockItemIds.SEEDING_DANDELION)
			.add(WWBlockItemIds.CARNATION)
			.add(WWBlockItemIds.MARIGOLD)
			.add(WWBlockItemIds.PASQUEFLOWER)
			.add(WWBlockItemIds.RED_HIBISCUS)
			.add(WWBlockItemIds.PINK_HIBISCUS)
			.add(WWBlockItemIds.YELLOW_HIBISCUS)
			.add(WWBlockItemIds.WHITE_HIBISCUS)
			.add(WWBlockItemIds.PURPLE_HIBISCUS)
			.add(WWBlockItemIds.DATURA)
			.add(WWBlockItemIds.MILKWEED)
			.add(BlockItemIds.SUNFLOWER)
			.add(BlockItemIds.ROSE_BUSH)
			.add(BlockItemIds.PEONY)
			.add(BlockItemIds.LILAC)
			.add(BlockItemIds.TORCHFLOWER)
			.add(BlockItemIds.WILDFLOWERS)
			.add(WWBlockItemIds.PHLOX)
			.add(WWBlockItemIds.LANTANAS)
			.add(BlockItemIds.CLOSED_EYEBLOSSOM)
			.add(BlockItemIds.OPEN_EYEBLOSSOM)

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

		this.builder(WWBlockTags.SOUND_GRASS)
			.add(BlockItemIds.SHORT_GRASS)
			.add(BlockItemIds.TALL_GRASS)
			.add(BlockItemIds.FERN)
			.add(BlockItemIds.LARGE_FERN)
			.add(BlockItemIds.BUSH)
			.add(WWBlockItemIds.CLOVERS);

		this.builder(WWBlockTags.SOUND_FROZEN_GRASS)
			.add(WWBlockItemIds.FROZEN_SHORT_GRASS)
			.add(WWBlockItemIds.FROZEN_TALL_GRASS)
			.add(WWBlockItemIds.FROZEN_FERN)
			.add(WWBlockItemIds.FROZEN_LARGE_FERN)
			.add(WWBlockItemIds.FROZEN_BUSH);

		this.builder(WWBlockTags.SOUND_DRY_GRASS)
			.add(BlockItemIds.SHORT_DRY_GRASS)
			.add(BlockItemIds.TALL_DRY_GRASS);
	}
}
