/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.frozenblock.lib.tag.api.FrozenBlockTags;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
		this.generateCompat();
		this.generateLib();
		this.generateFeatures();
		this.generateDeepDark();
		this.generateHollowedAndTermites();
		this.generateTags();
		this.generateMinecraft();
	}

	@NotNull
	private TagKey<Block> getTag(String id) {
		return TagKey.create(this.registryKey, ResourceLocation.parse(id));
	}

	private void generateCompat() {
		this.getOrCreateTagBuilder(ConventionalBlockTags.CHESTS)
			.add(RegisterBlocks.STONE_CHEST);

		this.getOrCreateTagBuilder(getTag("c:stripped_logs"))
			.add(RegisterBlocks.STRIPPED_BAOBAB_LOG)
			.add(RegisterBlocks.STRIPPED_CYPRESS_LOG)
			.add(RegisterBlocks.STRIPPED_PALM_LOG);

		this.getOrCreateTagBuilder(getTag("sereneseasons:summer_crops"))
			.add(RegisterBlocks.BUSH)
			.add(RegisterBlocks.MILKWEED)
			.add(RegisterBlocks.DATURA)
			.add(RegisterBlocks.SEEDING_DANDELION)
			.add(RegisterBlocks.CYPRESS_SAPLING)
			.add(RegisterBlocks.BAOBAB_NUT)
			.add(RegisterBlocks.COCONUT)
			.add(RegisterBlocks.BROWN_SHELF_FUNGUS)
			.add(RegisterBlocks.RED_SHELF_FUNGUS)
			.add(RegisterBlocks.TUMBLEWEED_PLANT);

		this.getOrCreateTagBuilder(getTag("sereneseasons:spring_crops"))
			.add(RegisterBlocks.BUSH)
			.add(RegisterBlocks.MILKWEED)
			.add(RegisterBlocks.DATURA)
			.add(RegisterBlocks.SEEDING_DANDELION)
			.add(RegisterBlocks.CYPRESS_SAPLING)
			.add(RegisterBlocks.BAOBAB_NUT)
			.add(RegisterBlocks.COCONUT)
			.add(RegisterBlocks.BROWN_SHELF_FUNGUS)
			.add(RegisterBlocks.RED_SHELF_FUNGUS)
			.add(RegisterBlocks.TUMBLEWEED_PLANT);

		this.getOrCreateTagBuilder(getTag("sereneseasons:autumn_crops"))
			.add(RegisterBlocks.MILKWEED)
			.add(RegisterBlocks.GLORY_OF_THE_SNOW)
			.add(RegisterBlocks.SEEDING_DANDELION)
			.add(RegisterBlocks.CYPRESS_SAPLING)
			.add(RegisterBlocks.BROWN_SHELF_FUNGUS)
			.add(RegisterBlocks.RED_SHELF_FUNGUS);

		this.getOrCreateTagBuilder(getTag("sereneseasons:winter_crops"))
			.add(RegisterBlocks.GLORY_OF_THE_SNOW)
			.add(RegisterBlocks.SEEDING_DANDELION)
			.add(RegisterBlocks.BROWN_SHELF_FUNGUS)
			.add(RegisterBlocks.RED_SHELF_FUNGUS);

		this.getOrCreateTagBuilder(getTag("sereneseasons:unbreakable_infertile_crops"))
			.add(RegisterBlocks.BUSH)
			.add(RegisterBlocks.MILKWEED)
			.add(RegisterBlocks.DATURA)
			.add(RegisterBlocks.GLORY_OF_THE_SNOW)
			.add(RegisterBlocks.SEEDING_DANDELION)
			.add(RegisterBlocks.CYPRESS_SAPLING)
			.add(RegisterBlocks.BAOBAB_NUT)
			.add(RegisterBlocks.COCONUT)
			.add(RegisterBlocks.BROWN_SHELF_FUNGUS)
			.add(RegisterBlocks.RED_SHELF_FUNGUS)
			.add(RegisterBlocks.TUMBLEWEED)
			.add(RegisterBlocks.TUMBLEWEED_PLANT);

		this.getOrCreateTagBuilder(getTag("sereneseasons:greenhouse_glass"))
			.add(RegisterBlocks.ECHO_GLASS);
	}

	private void generateLib() {
		this.getOrCreateTagBuilder(FrozenBlockTags.DRIPSTONE_CAN_DRIP_ON)
			.add(Blocks.DIRT)
			.add(Blocks.SAND)
			.add(Blocks.RED_SAND)
			.add(RegisterBlocks.SCORCHED_SAND)
			.add(RegisterBlocks.SCORCHED_RED_SAND);
	}

	private void generateFeatures() {
		this.getOrCreateTagBuilder(WilderBlockTags.STONE_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.DIRT)
			.add(Blocks.MUD)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WilderBlockTags.STONE_TRANSITION_PLACEABLE)
			.add(Blocks.STONE);

		this.getOrCreateTagBuilder(WilderBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.DIRT)
			.add(Blocks.MUD);

		this.getOrCreateTagBuilder(WilderBlockTags.GRAVEL_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.DIRT)
			.add(Blocks.MUD)
			.add(Blocks.SAND)
			.add(Blocks.STONE);

		this.getOrCreateTagBuilder(WilderBlockTags.GRAVEL_TRANSITION_PLACEABLE)
			.add(Blocks.GRAVEL);

		this.getOrCreateTagBuilder(WilderBlockTags.SAND_TRANSITION_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.STONE)
			.add(Blocks.DIRT)
			.add(Blocks.MUD);

		this.getOrCreateTagBuilder(WilderBlockTags.SAND_TRANSITION_PLACEABLE)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WilderBlockTags.RED_SAND_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.GRAVEL)
			.add(Blocks.MUD)
			.add(Blocks.SAND)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WilderBlockTags.RED_SAND_TRANSITION_PLACEABLE)
			.add(Blocks.RED_SAND)
			.addOptionalTag(BlockTags.TERRACOTTA);

		this.getOrCreateTagBuilder(WilderBlockTags.MUD_TRANSITION_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.CLAY)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WilderBlockTags.MUD_TRANSITION_PLACEABLE)
			.add(Blocks.MUD)
			.add(Blocks.MUDDY_MANGROVE_ROOTS);

		this.getOrCreateTagBuilder(WilderBlockTags.MUD_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.CLAY)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WilderBlockTags.COARSE_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.PODZOL);

		this.getOrCreateTagBuilder(WilderBlockTags.COARSE_CLEARING_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.PODZOL)
			.add(Blocks.GRAVEL);

		this.getOrCreateTagBuilder(WilderBlockTags.ROOTED_DIRT_PATH_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.add(Blocks.COARSE_DIRT);

		this.getOrCreateTagBuilder(WilderBlockTags.UNDER_WATER_SAND_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.GRAVEL);

		this.getOrCreateTagBuilder(WilderBlockTags.UNDER_WATER_GRAVEL_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.STONE);

		this.getOrCreateTagBuilder(WilderBlockTags.UNDER_WATER_CLAY_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.GRAVEL)
			.add(Blocks.STONE);

		this.getOrCreateTagBuilder(WilderBlockTags.BEACH_CLAY_PATH_REPLACEABLE)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WilderBlockTags.RIVER_GRAVEL_PATH_REPLACEABLE)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WilderBlockTags.SAND_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WilderBlockTags.GRAVEL_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.COARSE_DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WilderBlockTags.GRAVEL_CLEARING_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.COARSE_DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WilderBlockTags.STONE_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.addOptionalTag(BlockTags.SAND);

		this.getOrCreateTagBuilder(WilderBlockTags.PACKED_MUD_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.COARSE_DIRT);

		this.getOrCreateTagBuilder(WilderBlockTags.MOSS_PATH_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.PODZOL);

		this.getOrCreateTagBuilder(WilderBlockTags.SANDSTONE_PATH_REPLACEABLE)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WilderBlockTags.SMALL_COARSE_DIRT_PATH_REPLACEABLE)
			.add(Blocks.RED_SAND);

		this.getOrCreateTagBuilder(WilderBlockTags.PACKED_MUD_PATH_BADLANDS_REPLACEABLE)
			.add(Blocks.RED_SAND)
			.add(Blocks.RED_SANDSTONE)
			.addOptionalTag(BlockTags.TERRACOTTA);

		this.getOrCreateTagBuilder(WilderBlockTags.POLLEN_FEATURE_PLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.addOptionalTag(BlockTags.LEAVES)
			.addOptionalTag(BlockTags.OVERWORLD_NATURAL_LOGS);

		this.getOrCreateTagBuilder(WilderBlockTags.TERMITE_DISC_REPLACEABLE)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WilderBlockTags.TERMITE_DISC_BLOCKS)
			.add(Blocks.COARSE_DIRT)
			.add(Blocks.SAND)
			.add(Blocks.PACKED_MUD);

		this.getOrCreateTagBuilder(WilderBlockTags.BLUE_NEMATOCYST_FEATURE_PLACEABLE)
			.add(Blocks.CLAY)
			.add(Blocks.DRIPSTONE_BLOCK)
			.add(Blocks.CALCITE)
			.add(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WilderBlockTags.PURPLE_NEMATOCYST_FEATURE_PLACEABLE)
			.add(Blocks.CLAY)
			.add(Blocks.DRIPSTONE_BLOCK)
			.add(Blocks.CALCITE)
			.add(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WilderBlockTags.SHELF_FUNGUS_FEATURE_PLACEABLE)
			.add(Blocks.MUSHROOM_STEM)
			.addOptionalTag(BlockTags.OVERWORLD_NATURAL_LOGS);

		this.getOrCreateTagBuilder(WilderBlockTags.SCORCHED_SAND_FEATURE_INNER_REPLACEABLE)
			.add(Blocks.SAND)
			.add(RegisterBlocks.SCORCHED_SAND);

		this.getOrCreateTagBuilder(WilderBlockTags.SCORCHED_SAND_FEATURE_REPLACEABLE)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WilderBlockTags.RED_SCORCHED_SAND_FEATURE_INNER_REPLACEABLE)
			.add(Blocks.RED_SAND)
			.add(RegisterBlocks.SCORCHED_RED_SAND);

		this.getOrCreateTagBuilder(WilderBlockTags.RED_SCORCHED_SAND_FEATURE_REPLACEABLE)
			.add(Blocks.RED_SAND);

		this.getOrCreateTagBuilder(WilderBlockTags.PACKED_ICE_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WilderBlockTags.MESOGLEA_PATH_REPLACEABLE)
			.add(Blocks.CLAY)
			.add(Blocks.DRIPSTONE_BLOCK)
			.add(Blocks.CALCITE)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WilderBlockTags.MAGMA_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WilderBlockTags.NETHER_GEYSER_REPLACEABLE)
			.addOptionalTag(BlockTags.BASE_STONE_NETHER);

		this.getOrCreateTagBuilder(WilderBlockTags.OASIS_PATH_REPLACEABLE)
			.add(Blocks.SAND)
			.add(Blocks.SANDSTONE);

		this.getOrCreateTagBuilder(WilderBlockTags.COARSE_DIRT_DISK_REPLACEABLE)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.DIRT)
			.add(Blocks.GRAVEL)
			.add(Blocks.CLAY)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.PODZOL);

		this.getOrCreateTagBuilder(WilderBlockTags.RIVER_POOL_REPLACEABLE)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.DIRT)
			.add(Blocks.GRAVEL)
			.add(Blocks.CLAY);

		this.getOrCreateTagBuilder(WilderBlockTags.FALLEN_TREE_PLACEABLE)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.DIRT)
			.add(Blocks.GRAVEL)
			.add(Blocks.CLAY)
			.add(Blocks.MOSS_BLOCK)
			.add(Blocks.PACKED_MUD)
			.add(Blocks.SNOW);

		this.getOrCreateTagBuilder(WilderBlockTags.PACKED_MUD_REPLACEABLE)
			.add(Blocks.STONE)
			.add(Blocks.DIRT)
			.add(Blocks.SANDSTONE);
	}

	private void generateTags() {
		this.getOrCreateTagBuilder(WilderBlockTags.BAOBAB_LOGS)
			.add(RegisterBlocks.BAOBAB_LOG)
			.add(RegisterBlocks.STRIPPED_BAOBAB_LOG)
			.add(RegisterBlocks.BAOBAB_WOOD)
			.add(RegisterBlocks.STRIPPED_BAOBAB_WOOD)
			.addOptionalTag(WilderBlockTags.HOLLOWED_BAOBAB_LOGS);

		this.getOrCreateTagBuilder(WilderBlockTags.CYPRESS_LOGS)
			.add(RegisterBlocks.CYPRESS_LOG)
			.add(RegisterBlocks.STRIPPED_CYPRESS_LOG)
			.add(RegisterBlocks.CYPRESS_WOOD)
			.add(RegisterBlocks.STRIPPED_CYPRESS_WOOD)
			.addOptionalTag(WilderBlockTags.HOLLOWED_CYPRESS_LOGS);

		this.getOrCreateTagBuilder(WilderBlockTags.PALM_LOGS)
			.add(RegisterBlocks.PALM_LOG)
			.add(RegisterBlocks.STRIPPED_PALM_LOG)
			.add(RegisterBlocks.PALM_WOOD)
			.add(RegisterBlocks.STRIPPED_PALM_WOOD)
			.addOptionalTag(WilderBlockTags.HOLLOWED_PALM_LOGS);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_ACACIA_LOGS)
			.add(RegisterBlocks.HOLLOWED_ACACIA_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_BIRCH_LOGS)
			.add(RegisterBlocks.HOLLOWED_BIRCH_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_CHERRY_LOGS)
			.add(RegisterBlocks.HOLLOWED_CHERRY_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_CRIMSON_STEMS)
			.add(RegisterBlocks.HOLLOWED_CRIMSON_STEM)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_DARK_OAK_LOGS)
			.add(RegisterBlocks.HOLLOWED_DARK_OAK_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_JUNGLE_LOGS)
			.add(RegisterBlocks.HOLLOWED_JUNGLE_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_MANGROVE_LOGS)
			.add(RegisterBlocks.HOLLOWED_MANGROVE_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_OAK_LOGS)
			.add(RegisterBlocks.HOLLOWED_OAK_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_SPRUCE_LOGS)
			.add(RegisterBlocks.HOLLOWED_SPRUCE_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_WARPED_STEMS)
			.add(RegisterBlocks.HOLLOWED_WARPED_STEM)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_BAOBAB_LOGS)
			.add(RegisterBlocks.HOLLOWED_BAOBAB_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_CYPRESS_LOGS)
			.add(RegisterBlocks.HOLLOWED_CYPRESS_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_PALM_LOGS)
			.add(RegisterBlocks.HOLLOWED_PALM_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_PALM_LOG);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WilderBlockTags.HOLLOWED_ACACIA_LOGS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_BIRCH_LOGS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_CHERRY_LOGS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_CRIMSON_STEMS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_DARK_OAK_LOGS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_JUNGLE_LOGS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_MANGROVE_LOGS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_OAK_LOGS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_SPRUCE_LOGS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_WARPED_STEMS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_BAOBAB_LOGS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_CYPRESS_LOGS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_PALM_LOGS);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_LOGS_DONT_BURN)
			.addOptionalTag(WilderBlockTags.HOLLOWED_CRIMSON_STEMS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_WARPED_STEMS);

		this.getOrCreateTagBuilder(WilderBlockTags.HOLLOWED_LOGS)
			.addOptionalTag(WilderBlockTags.HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WilderBlockTags.HOLLOWED_LOGS_DONT_BURN);

		this.getOrCreateTagBuilder(WilderBlockTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_ACACIA_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_BIRCH_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_CHERRY_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_PALM_LOG);

		this.getOrCreateTagBuilder(WilderBlockTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

		this.getOrCreateTagBuilder(WilderBlockTags.STRIPPED_HOLLOWED_LOGS)
			.addOptionalTag(WilderBlockTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WilderBlockTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN);

		this.getOrCreateTagBuilder(WilderBlockTags.MESOGLEA)
			.add(RegisterBlocks.BLUE_MESOGLEA)
			.add(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA)
			.add(RegisterBlocks.LIME_MESOGLEA)
			.add(RegisterBlocks.PINK_MESOGLEA)
			.add(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA)
			.add(RegisterBlocks.RED_MESOGLEA)
			.add(RegisterBlocks.YELLOW_MESOGLEA);

		this.getOrCreateTagBuilder(WilderBlockTags.NEMATOCYSTS)
			.add(RegisterBlocks.BLUE_NEMATOCYST)
			.add(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST)
			.add(RegisterBlocks.LIME_NEMATOCYST)
			.add(RegisterBlocks.PINK_NEMATOCYST)
			.add(RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST)
			.add(RegisterBlocks.RED_NEMATOCYST)
			.add(RegisterBlocks.YELLOW_NEMATOCYST);

		this.getOrCreateTagBuilder(WilderBlockTags.SMALL_SPONGE_GROWS_ON)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.GRAVEL)
			.add(Blocks.SPONGE)
			.add(Blocks.CLAY)
			.add(Blocks.MOSS_BLOCK)
			.addOptionalTag(WilderBlockTags.MESOGLEA);

		this.getOrCreateTagBuilder(WilderBlockTags.BASIN_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.COARSE_DIRT)
			.add(Blocks.PODZOL)
			.add(Blocks.MOSS_BLOCK);

		this.getOrCreateTagBuilder(WilderBlockTags.STOPS_TUMBLEWEED)
			.add(Blocks.MUD)
			.add(Blocks.MUDDY_MANGROVE_ROOTS)
			.add(Blocks.SLIME_BLOCK)
			.add(Blocks.IRON_BARS)
			.add(Blocks.HONEY_BLOCK);

		this.getOrCreateTagBuilder(WilderBlockTags.SPLITS_COCONUT)
			.addOptionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.BASE_STONE_NETHER)
			.addOptionalTag(BlockTags.DRAGON_IMMUNE)
			.addOptionalTag(BlockTags.WITHER_IMMUNE)
			.addOptionalTag(BlockTags.LOGS);

		this.getOrCreateTagBuilder(WilderBlockTags.FIREFLY_HIDEABLE_BLOCKS)
			.add(Blocks.SHORT_GRASS)
			.add(Blocks.TALL_GRASS)
			.add(Blocks.FERN)
			.add(Blocks.LARGE_FERN)
			.add(Blocks.PEONY)
			.add(Blocks.ROSE_BUSH)
			.add(RegisterBlocks.CATTAIL);

		this.getOrCreateTagBuilder(WilderBlockTags.CRAB_CAN_HIDE)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.CLAY)
			.add(Blocks.GRAVEL);

		this.getOrCreateTagBuilder(WilderBlockTags.OSTRICH_BEAK_BURYABLE)
			.addOptionalTag(BlockTags.MINEABLE_WITH_SHOVEL)
			.addOptionalTag(BlockTags.MINEABLE_WITH_HOE)
			.addOptionalTag(BlockTags.WOOL);

		this.getOrCreateTagBuilder(WilderBlockTags.GEYSER_CAN_PASS_THROUGH)
			.addOptionalTag(BlockTags.TRAPDOORS)
			.add(Blocks.COPPER_GRATE)
			.add(Blocks.EXPOSED_COPPER_GRATE)
			.add(Blocks.WEATHERED_COPPER_GRATE)
			.add(Blocks.OXIDIZED_COPPER_GRATE)
			.add(Blocks.WAXED_COPPER_GRATE)
			.add(Blocks.WAXED_EXPOSED_COPPER_GRATE)
			.add(Blocks.WAXED_WEATHERED_COPPER_GRATE)
			.add(Blocks.WAXED_OXIDIZED_COPPER_GRATE);

		this.getOrCreateTagBuilder(WilderBlockTags.GEYSER_CANNOT_PASS_THROUGH)
			.addOptionalTag(net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags.GLASS_BLOCKS);

		this.getOrCreateTagBuilder(WilderBlockTags.NO_LIGHTNING_BLOCK_PARTICLES)
			.add(Blocks.LIGHTNING_ROD);

		this.getOrCreateTagBuilder(WilderBlockTags.NO_LIGHTNING_SMOKE_PARTICLES)
			.add(Blocks.LIGHTNING_ROD);

		this.getOrCreateTagBuilder(WilderBlockTags.CATTAIL_PLACEABLE)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.CLAY);

		this.getOrCreateTagBuilder(WilderBlockTags.CATTAIL_MUD_PLACEABLE)
			.add(Blocks.MUD);

		this.getOrCreateTagBuilder(BlockTags.SAND)
			.add(RegisterBlocks.SCORCHED_SAND)
			.add(RegisterBlocks.SCORCHED_RED_SAND);
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
			.addOptionalTag(net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags.GLASS_BLOCKS)
			.addOptionalTag(net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags.GLASS_PANES)
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

		this.getOrCreateTagBuilder(BlockTags.OCCLUDES_VIBRATION_SIGNALS)
			.add(RegisterBlocks.ECHO_GLASS);
	}

	private void generateHollowedAndTermites() {
		this.getOrCreateTagBuilder(WilderBlockTags.BLOCKS_TERMITE)
			.addOptionalTag(net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags.GLASS_BLOCKS)
			.addOptionalTag(net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags.GLASS_PANES)
			.add(RegisterBlocks.ECHO_GLASS);

		this.getOrCreateTagBuilder(net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags.GLASS_BLOCKS)
			.add(RegisterBlocks.ECHO_GLASS);

		this.getOrCreateTagBuilder(WilderBlockTags.BUSH_MAY_PLACE_ON)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.DEAD_BUSH_MAY_PLACE_ON);

		this.getOrCreateTagBuilder(WilderBlockTags.KILLS_TERMITE)
			.add(Blocks.WATER)
			.add(Blocks.LAVA)
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
			.add(RegisterBlocks.HOLLOWED_CRIMSON_STEM)
			.add(RegisterBlocks.HOLLOWED_WARPED_STEM)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM)
			.add(RegisterBlocks.STRIPPED_HOLLOWED_WARPED_STEM)
			.addOptionalTag(BlockTags.WARPED_STEMS)
			.addOptionalTag(BlockTags.CRIMSON_STEMS);

		this.getOrCreateTagBuilder(WilderBlockTags.TERMITE_BREAKABLE)
			.addOptionalTag(BlockTags.LEAVES)
			.addOptionalTag(WilderBlockTags.STRIPPED_HOLLOWED_LOGS)
			.add(Blocks.BAMBOO)
			.add(Blocks.DEAD_BUSH)
			.add(Blocks.STRIPPED_ACACIA_WOOD)
			.add(Blocks.STRIPPED_BIRCH_WOOD)
			.add(Blocks.STRIPPED_DARK_OAK_WOOD)
			.add(Blocks.STRIPPED_JUNGLE_WOOD)
			.add(Blocks.STRIPPED_MANGROVE_WOOD)
			.add(Blocks.STRIPPED_OAK_WOOD)
			.add(Blocks.STRIPPED_SPRUCE_WOOD)
			.add(Blocks.STRIPPED_ACACIA_WOOD)
			.add(RegisterBlocks.STRIPPED_BAOBAB_WOOD)
			.add(RegisterBlocks.STRIPPED_CYPRESS_WOOD)
			.add(RegisterBlocks.STRIPPED_PALM_WOOD)
			.addOptional(
				ResourceKey.create(
					Registries.BLOCK,
					ResourceLocation.parse("immersive_weathering:leaf_piles")
				)
			);

		this.getOrCreateTagBuilder(WilderBlockTags.SAND_POOL_REPLACEABLE)
			.add(Blocks.SAND);
	}

	private void generateMinecraft() {
		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
			.add(Blocks.CACTUS)
			.add(Blocks.SWEET_BERRY_BUSH)
			.addOptionalTag(WilderBlockTags.HOLLOWED_LOGS)
			.add(RegisterBlocks.TUMBLEWEED_PLANT)
			.add(RegisterBlocks.TUMBLEWEED)
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS);

		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_HOE)
			.add(Blocks.SWEET_BERRY_BUSH)
			.add(RegisterBlocks.OSSEOUS_SCULK)
			.add(RegisterBlocks.SCULK_SLAB)
			.add(RegisterBlocks.SCULK_STAIRS)
			.add(RegisterBlocks.SCULK_WALL)
			.add(RegisterBlocks.HANGING_TENDRIL)
			.add(RegisterBlocks.BAOBAB_LEAVES)
			.add(RegisterBlocks.CYPRESS_LEAVES)
			.add(RegisterBlocks.PALM_FRONDS)
			.add(RegisterBlocks.TUMBLEWEED_PLANT)
			.add(RegisterBlocks.TUMBLEWEED)
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS)
			.add(RegisterBlocks.SMALL_SPONGE);

		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(RegisterBlocks.STONE_CHEST)
			.add(RegisterBlocks.NULL_BLOCK)
			.add(RegisterBlocks.DISPLAY_LANTERN)
			.add(RegisterBlocks.SCORCHED_SAND)
			.add(RegisterBlocks.SCORCHED_RED_SAND)
			.add(RegisterBlocks.GEYSER)

			.add(RegisterBlocks.CHISELED_MUD_BRICKS)
			.add(RegisterBlocks.CRACKED_MUD_BRICKS)
			.add(RegisterBlocks.MOSSY_MUD_BRICKS)
			.add(RegisterBlocks.MOSSY_MUD_BRICK_STAIRS)
			.add(RegisterBlocks.MOSSY_MUD_BRICK_SLAB)
			.add(RegisterBlocks.MOSSY_MUD_BRICK_WALL);

		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL)
			.addOptionalTag(WilderBlockTags.MESOGLEA)
			.add(RegisterBlocks.SCORCHED_SAND)
			.add(RegisterBlocks.SCORCHED_RED_SAND)
			.add(RegisterBlocks.TERMITE_MOUND);

		this.getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT)
			.add(RegisterBlocks.BUSH)
			.add(RegisterBlocks.TUMBLEWEED)
			.add(RegisterBlocks.TUMBLEWEED_PLANT)
			.add(RegisterBlocks.MILKWEED)
			.add(RegisterBlocks.DATURA)
			.add(RegisterBlocks.CATTAIL)
			.add(RegisterBlocks.FLOWERING_LILY_PAD)
			.add(RegisterBlocks.ALGAE)
			.add(RegisterBlocks.BROWN_SHELF_FUNGUS)
			.add(RegisterBlocks.RED_SHELF_FUNGUS)
			.add(RegisterBlocks.SMALL_SPONGE)
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS)
			.addOptionalTag(WilderBlockTags.NEMATOCYSTS);

		this.getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
			.add(RegisterBlocks.GEYSER);

		this.getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS)
			.add(RegisterBlocks.BAOBAB_HANGING_SIGN)
			.add(RegisterBlocks.CYPRESS_HANGING_SIGN)
			.add(RegisterBlocks.PALM_HANGING_SIGN);

		this.getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS)
			.add(RegisterBlocks.BAOBAB_WALL_HANGING_SIGN)
			.add(RegisterBlocks.CYPRESS_WALL_HANGING_SIGN)
			.add(RegisterBlocks.PALM_WALL_HANGING_SIGN);

		this.getOrCreateTagBuilder(BlockTags.STAIRS)
			.add(RegisterBlocks.SCULK_STAIRS)
			.add(RegisterBlocks.MOSSY_MUD_BRICK_STAIRS);

		this.getOrCreateTagBuilder(BlockTags.SLABS)
			.add(RegisterBlocks.SCULK_SLAB)
			.add(RegisterBlocks.MOSSY_MUD_BRICK_SLAB);

		this.getOrCreateTagBuilder(BlockTags.WALLS)
			.add(RegisterBlocks.SCULK_WALL)
			.add(RegisterBlocks.MOSSY_MUD_BRICK_WALL);
	}
}
