/*
 * Copyright 2023-2025 FrozenBlock
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
import net.frozenblock.lib.block.sound.api.BlockSoundTypeOverwrites;
import net.frozenblock.lib.tag.api.FrozenBlockTags;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import static net.frozenblock.wilderwild.registry.WWSoundTypes.FLOWER;

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
		return TagKey.create(this.registryKey, ResourceLocation.parse(id));
	}

	private void generateSounds() {
		this.getOrCreateTagBuilder(WWBlockTags.SOUND_FLOWER)
			.add(Blocks.DANDELION)
			.add(Blocks.POPPY)
			.add(Blocks.BLUE_ORCHID)
			.add(Blocks.ALLIUM)
			.add(Blocks.AZURE_BLUET)
			.add(Blocks.ORANGE_TULIP)
			.add(Blocks.PINK_TULIP)
			.add(Blocks.RED_TULIP)
			.add(Blocks.WHITE_TULIP)
			.add(Blocks.OXEYE_DAISY)
			.add(Blocks.CORNFLOWER)
			.add(Blocks.LILY_OF_THE_VALLEY)
			.add(WWBlocks.SEEDING_DANDELION)
			.add(WWBlocks.CARNATION)
			.add(WWBlocks.MARIGOLD)
			.add(WWBlocks.PASQUEFLOWER)
			.add(WWBlocks.RED_HIBISCUS)
			.add(WWBlocks.PURPLE_HIBISCUS)
			.add(WWBlocks.PINK_HIBISCUS)
			.add(WWBlocks.YELLOW_HIBISCUS)
			.add(WWBlocks.WHITE_HIBISCUS)
			.add(WWBlocks.DATURA)
			.add(WWBlocks.MILKWEED)
			.add(Blocks.SUNFLOWER)
			.add(Blocks.ROSE_BUSH)
			.add(Blocks.PEONY)
			.add(Blocks.LILAC)
			.add(Blocks.TORCHFLOWER)
			.add(Blocks.PINK_PETALS)
			.addOptional(ResourceLocation.fromNamespaceAndPath("betterend", "hydralux_petal_block"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("betterend", "end_lotus_flower"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("betterend", "tenanea_flowers"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("betternether", "soul_lily"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("betternether", "soul_lily_sapling"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "white_petals"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "wildflower"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "rose"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "violet"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "lavendar"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "white_lavendar"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "orange_cosmos"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "pink_daffodil"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "pink_hibiscus"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "waterlily"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "glowflower"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "wilted_lily"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "burning_blossom"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "endbloom"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "tall_lavendar"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "tall_white_lavendar"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "blue_hydrangea"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "goldenrod"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("biomesoplenty", "icy_iris"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","anemone"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","begonia"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","black_iris"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","bleeding_heart"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","blue_bulbs"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","blue_iris"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","bluebell"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","carnation"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","dwarf_blossoms"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","foxglove"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","gardenia"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","hibiscus"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","iris"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","lavender"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","lotus_flower"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","marigold"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","protea"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","purple_heather"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","red_bearberries"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","red_heather"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","ruby_blossoms"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","snapdragon"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","tiger_lily"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","white_heather"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","yellow_wildflower"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","purple_wildflower"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("natures_spirit","black_iris"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","cactus_flower"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","tassel"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","day_lily"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","aster"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","bleeding_heart"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","blue_lupine"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","daisy"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","dorcel"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","felicia_daisy"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","fireweed"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","hibiscus"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","mallow"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","hyssop"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","pink_lupine"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","poppy_bush"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","salmon_poppy_bush"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","purple_lupine"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","red_lupine"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","waratah"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","tsubaki"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","white_trillium"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","wilting_trillium"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","yellow_lupine"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","red_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","orange_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","yellow_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","lime_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","green_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","cyan_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","light_blue_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","blue_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","purple_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","magenta_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","pink_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","brown_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","white_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","light_gray_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","gray_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","black_snowbelle"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","hyacinth_flowers"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","orange_coneflower"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","purple_coneflower"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","blue_magnolia_flowers"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","pink_magnolia_flowers"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("regions_unexplored","white_magnolia_flowers"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("terrestria","indian_paintbrush"))
			.addOptional(ResourceLocation.fromNamespaceAndPath("terrestria","monsteras"));
	}

	private void generateCompat() {
		this.getOrCreateTagBuilder(ConventionalBlockTags.CHESTS)
			.add(WWBlocks.STONE_CHEST);

		this.getOrCreateTagBuilder(getTag("c:stripped_logs"))
			.add(WWBlocks.STRIPPED_BAOBAB_LOG)
			.add(WWBlocks.STRIPPED_WILLOW_LOG)
			.add(WWBlocks.STRIPPED_CYPRESS_LOG)
			.add(WWBlocks.STRIPPED_PALM_LOG)
			.add(WWBlocks.STRIPPED_MAPLE_LOG);

		this.getOrCreateTagBuilder(getTag("sereneseasons:summer_crops"))
			.add(WWBlocks.RED_HIBISCUS)
			.add(WWBlocks.YELLOW_HIBISCUS)
			.add(WWBlocks.WHITE_HIBISCUS)
			.add(WWBlocks.PINK_HIBISCUS)
			.add(WWBlocks.PURPLE_HIBISCUS)
			.add(WWBlocks.BUSH)
			.add(WWBlocks.MILKWEED)
			.add(WWBlocks.DATURA)
			.add(WWBlocks.SEEDING_DANDELION)
			.add(WWBlocks.WILLOW_SAPLING)
			.add(WWBlocks.CYPRESS_SAPLING)
			.add(WWBlocks.BAOBAB_NUT)
			.add(WWBlocks.COCONUT)
			.add(WWBlocks.BROWN_SHELF_FUNGI)
			.add(WWBlocks.RED_SHELF_FUNGI)
			.add(WWBlocks.TUMBLEWEED_PLANT);

		this.getOrCreateTagBuilder(getTag("sereneseasons:spring_crops"))
			.add(WWBlocks.RED_HIBISCUS)
			.add(WWBlocks.YELLOW_HIBISCUS)
			.add(WWBlocks.WHITE_HIBISCUS)
			.add(WWBlocks.PINK_HIBISCUS)
			.add(WWBlocks.PURPLE_HIBISCUS)
			.add(WWBlocks.BUSH)
			.add(WWBlocks.MILKWEED)
			.add(WWBlocks.DATURA)
			.add(WWBlocks.SEEDING_DANDELION)
			.add(WWBlocks.WILLOW_SAPLING)
			.add(WWBlocks.CYPRESS_SAPLING)
			.add(WWBlocks.BAOBAB_NUT)
			.add(WWBlocks.COCONUT)
			.add(WWBlocks.BROWN_SHELF_FUNGI)
			.add(WWBlocks.RED_SHELF_FUNGI)
			.add(WWBlocks.TUMBLEWEED_PLANT);

		this.getOrCreateTagBuilder(getTag("sereneseasons:autumn_crops"))
			.add(WWBlocks.MILKWEED)
			.add(WWBlocks.SEEDING_DANDELION)
			.add(WWBlocks.MAPLE_SAPLING)
			.add(WWBlocks.BROWN_SHELF_FUNGI)
			.add(WWBlocks.RED_SHELF_FUNGI)
			.add(WWBlocks.MARIGOLD)
			.add(WWBlocks.PASQUEFLOWER);

		this.getOrCreateTagBuilder(getTag("sereneseasons:winter_crops"))
			.add(WWBlocks.SEEDING_DANDELION)
			.add(WWBlocks.BROWN_SHELF_FUNGI)
			.add(WWBlocks.RED_SHELF_FUNGI);

		this.getOrCreateTagBuilder(getTag("sereneseasons:unbreakable_infertile_crops"))
			.add(WWBlocks.BUSH)
			.add(WWBlocks.MILKWEED)
			.add(WWBlocks.DATURA)
			.add(WWBlocks.SEEDING_DANDELION)
			.add(WWBlocks.WILLOW_SAPLING)
			.add(WWBlocks.CYPRESS_SAPLING)
			.add(WWBlocks.BAOBAB_NUT)
			.add(WWBlocks.COCONUT)
			.add(WWBlocks.MAPLE_SAPLING)
			.add(WWBlocks.BROWN_SHELF_FUNGI)
			.add(WWBlocks.RED_SHELF_FUNGI)
			.add(WWBlocks.TUMBLEWEED)
			.add(WWBlocks.TUMBLEWEED_PLANT);

		this.getOrCreateTagBuilder(getTag("sereneseasons:greenhouse_glass"))
			.add(WWBlocks.ECHO_GLASS);
	}

	private void generateLib() {
		this.getOrCreateTagBuilder(FrozenBlockTags.DRIPSTONE_CAN_DRIP_ON)
			.add(Blocks.DIRT)
			.add(Blocks.SAND)
			.add(Blocks.RED_SAND)
			.add(WWBlocks.SCORCHED_SAND)
			.add(WWBlocks.SCORCHED_RED_SAND);
	}

	private void generateFeatures() {
		this.getOrCreateTagBuilder(WWBlockTags.STONE_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.DIRT)
			.add(Blocks.MUD)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WWBlockTags.STONE_TRANSITION_PLACEABLE)
			.add(Blocks.STONE);

		this.getOrCreateTagBuilder(WWBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.DIRT)
			.add(Blocks.MUD);

		this.getOrCreateTagBuilder(WWBlockTags.GRAVEL_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.DIRT)
			.add(Blocks.MUD)
			.add(Blocks.SAND)
			.add(Blocks.STONE);

		this.getOrCreateTagBuilder(WWBlockTags.GRAVEL_TRANSITION_PLACEABLE)
			.add(Blocks.GRAVEL);

		this.getOrCreateTagBuilder(WWBlockTags.SAND_TRANSITION_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.STONE)
			.add(Blocks.DIRT)
			.add(Blocks.MUD);

		this.getOrCreateTagBuilder(WWBlockTags.SAND_TRANSITION_PLACEABLE)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WWBlockTags.RED_SAND_TRANSITION_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.GRAVEL)
			.add(Blocks.MUD)
			.add(Blocks.SAND)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WWBlockTags.RED_SAND_TRANSITION_PLACEABLE)
			.add(Blocks.RED_SAND)
			.addOptionalTag(BlockTags.TERRACOTTA);

		this.getOrCreateTagBuilder(WWBlockTags.MUD_TRANSITION_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.CLAY)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WWBlockTags.MUD_TRANSITION_PLACEABLE)
			.add(Blocks.MUD)
			.add(Blocks.MUDDY_MANGROVE_ROOTS);

		this.getOrCreateTagBuilder(WWBlockTags.MUD_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.CLAY)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WWBlockTags.COARSE_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.PODZOL);

		this.getOrCreateTagBuilder(WWBlockTags.COARSE_CLEARING_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.PODZOL)
			.add(Blocks.GRAVEL);

		this.getOrCreateTagBuilder(WWBlockTags.ROOTED_DIRT_PATH_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.add(Blocks.COARSE_DIRT);

		this.getOrCreateTagBuilder(WWBlockTags.UNDER_WATER_SAND_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.GRAVEL);

		this.getOrCreateTagBuilder(WWBlockTags.UNDER_WATER_GRAVEL_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.STONE);

		this.getOrCreateTagBuilder(WWBlockTags.UNDER_WATER_CLAY_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.GRAVEL)
			.add(Blocks.STONE);

		this.getOrCreateTagBuilder(WWBlockTags.BEACH_CLAY_PATH_REPLACEABLE)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WWBlockTags.RIVER_GRAVEL_PATH_REPLACEABLE)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WWBlockTags.SAND_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WWBlockTags.GRAVEL_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.COARSE_DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WWBlockTags.GRAVEL_CLEARING_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.COARSE_DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WWBlockTags.STONE_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.addOptionalTag(BlockTags.SAND);

		this.getOrCreateTagBuilder(WWBlockTags.PACKED_MUD_PATH_REPLACEABLE)
			.add(Blocks.DIRT)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.COARSE_DIRT);

		this.getOrCreateTagBuilder(WWBlockTags.MOSS_PATH_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.PODZOL);

		this.getOrCreateTagBuilder(WWBlockTags.SANDSTONE_PATH_REPLACEABLE)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WWBlockTags.SMALL_COARSE_DIRT_PATH_REPLACEABLE)
			.add(Blocks.RED_SAND);

		this.getOrCreateTagBuilder(WWBlockTags.PACKED_MUD_PATH_BADLANDS_REPLACEABLE)
			.add(Blocks.RED_SAND)
			.add(Blocks.RED_SANDSTONE)
			.addOptionalTag(BlockTags.TERRACOTTA);

		this.getOrCreateTagBuilder(WWBlockTags.POLLEN_FEATURE_PLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.addOptionalTag(BlockTags.LEAVES)
			.addOptionalTag(BlockTags.OVERWORLD_NATURAL_LOGS);

		this.getOrCreateTagBuilder(WWBlockTags.TERMITE_DISK_REPLACEABLE)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WWBlockTags.TERMITE_DISK_BLOCKS)
			.add(Blocks.COARSE_DIRT)
			.add(Blocks.PACKED_MUD);

		this.getOrCreateTagBuilder(WWBlockTags.BLUE_NEMATOCYST_FEATURE_PLACEABLE)
			.add(Blocks.CLAY)
			.add(Blocks.DRIPSTONE_BLOCK)
			.add(Blocks.CALCITE)
			.add(WWBlocks.BLUE_PEARLESCENT_MESOGLEA)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WWBlockTags.PURPLE_NEMATOCYST_FEATURE_PLACEABLE)
			.add(Blocks.CLAY)
			.add(Blocks.DRIPSTONE_BLOCK)
			.add(Blocks.CALCITE)
			.add(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WWBlockTags.SHELF_FUNGI_FEATURE_PLACEABLE)
			.add(Blocks.MUSHROOM_STEM)
			.addOptionalTag(BlockTags.OVERWORLD_NATURAL_LOGS);

		this.getOrCreateTagBuilder(WWBlockTags.SHELF_FUNGI_FEATURE_PLACEABLE_NETHER)
			.addOptionalTag(BlockTags.CRIMSON_STEMS)
			.addOptionalTag(BlockTags.WARPED_STEMS);

		this.getOrCreateTagBuilder(WWBlockTags.SCORCHED_SAND_FEATURE_INNER_REPLACEABLE)
			.add(Blocks.SAND)
			.add(WWBlocks.SCORCHED_SAND);

		this.getOrCreateTagBuilder(WWBlockTags.SCORCHED_SAND_FEATURE_REPLACEABLE)
			.add(Blocks.SAND);

		this.getOrCreateTagBuilder(WWBlockTags.RED_SCORCHED_SAND_FEATURE_INNER_REPLACEABLE)
			.add(Blocks.RED_SAND)
			.add(WWBlocks.SCORCHED_RED_SAND);

		this.getOrCreateTagBuilder(WWBlockTags.RED_SCORCHED_SAND_FEATURE_REPLACEABLE)
			.add(Blocks.RED_SAND);

		this.getOrCreateTagBuilder(WWBlockTags.ICE_FEATURE_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.add(Blocks.SNOW_BLOCK)
			.add(Blocks.SNOW);

		this.getOrCreateTagBuilder(WWBlockTags.DIORITE_ICE_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.add(Blocks.SNOW_BLOCK)
			.add(Blocks.SNOW)
			.add(Blocks.ICE)
			.add(Blocks.BLUE_ICE)
			.add(Blocks.PACKED_ICE);

		this.getOrCreateTagBuilder(WWBlockTags.MESOGLEA_PATH_REPLACEABLE)
			.add(Blocks.CLAY)
			.add(Blocks.DRIPSTONE_BLOCK)
			.add(Blocks.CALCITE)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WWBlockTags.MAGMA_REPLACEABLE)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD);

		this.getOrCreateTagBuilder(WWBlockTags.NETHER_GEYSER_REPLACEABLE)
			.addOptionalTag(BlockTags.BASE_STONE_NETHER);

		this.getOrCreateTagBuilder(WWBlockTags.OASIS_PATH_REPLACEABLE)
			.add(Blocks.SAND)
			.add(Blocks.SANDSTONE);

		this.getOrCreateTagBuilder(WWBlockTags.COARSE_DIRT_DISK_REPLACEABLE)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.DIRT)
			.add(Blocks.CLAY)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.PODZOL);

		this.getOrCreateTagBuilder(WWBlockTags.RIVER_POOL_REPLACEABLE)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.DIRT)
			.add(Blocks.GRAVEL)
			.add(Blocks.CLAY);

		this.getOrCreateTagBuilder(WWBlockTags.FALLEN_TREE_PLACEABLE)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.SAND)
			.addOptionalTag(BlockTags.DIRT)
			.add(Blocks.GRAVEL)
			.add(Blocks.CLAY)
			.add(Blocks.MOSS_BLOCK)
			.add(Blocks.PACKED_MUD)
			.add(Blocks.SNOW);

		this.getOrCreateTagBuilder(WWBlockTags.PACKED_MUD_REPLACEABLE)
			.add(Blocks.STONE)
			.add(Blocks.DIRT)
			.add(Blocks.SANDSTONE);

		this.getOrCreateTagBuilder(WWBlockTags.SMALL_SPONGE_GROWS_ON)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.GRAVEL)
			.add(Blocks.SPONGE)
			.add(Blocks.CLAY)
			.add(Blocks.MOSS_BLOCK)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.getOrCreateTagBuilder(WWBlockTags.BASIN_REPLACEABLE)
			.add(Blocks.GRASS_BLOCK)
			.add(Blocks.COARSE_DIRT)
			.add(Blocks.PODZOL)
			.add(Blocks.MOSS_BLOCK);

		this.getOrCreateTagBuilder(WWBlockTags.CATTAIL_FEATURE_PLACEABLE)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.CLAY);

		this.getOrCreateTagBuilder(WWBlockTags.CATTAIL_FEATURE_MUD_PLACEABLE)
			.add(Blocks.MUD);

		this.getOrCreateTagBuilder(WWBlockTags.BUSH_MAY_PLACE_ON_FEATURE_NO_SAND)
			.addOptionalTag(BlockTags.TERRACOTTA)
			.addOptionalTag(BlockTags.DIRT);

		this.getOrCreateTagBuilder(WWBlockTags.SAND_POOL_REPLACEABLE)
			.add(Blocks.SAND);
	}

	private void generateTags() {
		this.getOrCreateTagBuilder(WWBlockTags.MESOGLEA)
			.add(WWBlocks.BLUE_MESOGLEA)
			.add(WWBlocks.BLUE_PEARLESCENT_MESOGLEA)
			.add(WWBlocks.LIME_MESOGLEA)
			.add(WWBlocks.PINK_MESOGLEA)
			.add(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA)
			.add(WWBlocks.RED_MESOGLEA)
			.add(WWBlocks.YELLOW_MESOGLEA);

		this.getOrCreateTagBuilder(WWBlockTags.NEMATOCYSTS)
			.add(WWBlocks.BLUE_NEMATOCYST)
			.add(WWBlocks.BLUE_PEARLESCENT_NEMATOCYST)
			.add(WWBlocks.LIME_NEMATOCYST)
			.add(WWBlocks.PINK_NEMATOCYST)
			.add(WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST)
			.add(WWBlocks.RED_NEMATOCYST)
			.add(WWBlocks.YELLOW_NEMATOCYST);

		this.getOrCreateTagBuilder(WWBlockTags.STOPS_TUMBLEWEED)
			.add(Blocks.MUD)
			.add(Blocks.MUDDY_MANGROVE_ROOTS)
			.add(Blocks.SLIME_BLOCK)
			.add(Blocks.IRON_BARS)
			.add(Blocks.HONEY_BLOCK);

		this.getOrCreateTagBuilder(WWBlockTags.SPLITS_COCONUT)
			.addOptionalTag(BlockTags.MINEABLE_WITH_PICKAXE)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.BASE_STONE_NETHER)
			.addOptionalTag(BlockTags.DRAGON_IMMUNE)
			.addOptionalTag(BlockTags.WITHER_IMMUNE)
			.addOptionalTag(BlockTags.LOGS);

		this.getOrCreateTagBuilder(WWBlockTags.FIREFLY_HIDEABLE_BLOCKS)
			.add(Blocks.SHORT_GRASS)
			.add(Blocks.TALL_GRASS)
			.add(Blocks.FERN)
			.add(Blocks.LARGE_FERN)
			.add(Blocks.PEONY)
			.add(Blocks.ROSE_BUSH)
			.add(Blocks.DEAD_BUSH)
			.add(WWBlocks.CATTAIL)
			.add(WWBlocks.MILKWEED)
			.add(WWBlocks.BUSH)
			.addOptionalTag(BlockTags.SMALL_FLOWERS);

		this.getOrCreateTagBuilder(WWBlockTags.CRAB_HIDEABLE)
			.addOptionalTag(BlockTags.DIRT)
			.addOptionalTag(BlockTags.SAND)
			.add(Blocks.CLAY)
			.add(Blocks.GRAVEL);

		this.getOrCreateTagBuilder(WWBlockTags.OSTRICH_BEAK_BURYABLE)
			.addOptionalTag(BlockTags.MINEABLE_WITH_SHOVEL)
			.addOptionalTag(BlockTags.MINEABLE_WITH_HOE)
			.addOptionalTag(BlockTags.WOOL);

		this.getOrCreateTagBuilder(WWBlockTags.PENGUIN_IGNORE_FRICTION)
			.addOptionalTag(BlockTags.ICE);

		this.getOrCreateTagBuilder(WWBlockTags.PENGUINS_SPAWNABLE_ON)
			.add(Blocks.SNOW_BLOCK)
			.addOptionalTag(BlockTags.ICE)
			.add(Blocks.SAND)
			.add(Blocks.GRAVEL)
			.addOptionalTag(BlockTags.BASE_STONE_OVERWORLD)
			.addOptionalTag(BlockTags.ANIMALS_SPAWNABLE_ON);

		this.getOrCreateTagBuilder(WWBlockTags.GEYSER_CAN_PASS_THROUGH)
			.addOptionalTag(BlockTags.TRAPDOORS)
			.add(Blocks.COPPER_GRATE)
			.add(Blocks.EXPOSED_COPPER_GRATE)
			.add(Blocks.WEATHERED_COPPER_GRATE)
			.add(Blocks.OXIDIZED_COPPER_GRATE)
			.add(Blocks.WAXED_COPPER_GRATE)
			.add(Blocks.WAXED_EXPOSED_COPPER_GRATE)
			.add(Blocks.WAXED_WEATHERED_COPPER_GRATE)
			.add(Blocks.WAXED_OXIDIZED_COPPER_GRATE);

		this.getOrCreateTagBuilder(WWBlockTags.GEYSER_CANNOT_PASS_THROUGH)
			.addOptionalTag(ConventionalBlockTags.GLASS_BLOCKS);

		this.getOrCreateTagBuilder(WWBlockTags.NO_LIGHTNING_BLOCK_PARTICLES)
			.add(Blocks.LIGHTNING_ROD);

		this.getOrCreateTagBuilder(WWBlockTags.NO_LIGHTNING_SMOKE_PARTICLES)
			.add(Blocks.LIGHTNING_ROD);

		this.getOrCreateTagBuilder(ConventionalBlockTags.GLASS_BLOCKS)
			.add(WWBlocks.ECHO_GLASS);

		this.getOrCreateTagBuilder(WWBlockTags.BUSH_MAY_PLACE_ON)
			.addOptionalTag(BlockTags.DEAD_BUSH_MAY_PLACE_ON);

		this.getOrCreateTagBuilder(WWBlockTags.MYCELIUM_GROWTH_REPLACEABLE)
			.add(Blocks.SHORT_GRASS)
			.add(Blocks.FERN);
	}

	private void generateDeepDark() {
		this.getOrCreateTagBuilder(WWBlockTags.ANCIENT_CITY_BLOCKS)
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

		this.getOrCreateTagBuilder(WWBlockTags.SCULK_SLAB_REPLACEABLE)
			.add(Blocks.STONE_SLAB)
			.add(Blocks.GRANITE_SLAB)
			.add(Blocks.DIORITE_SLAB)
			.add(Blocks.ANDESITE_SLAB)
			.add(Blocks.BLACKSTONE_SLAB);

		this.getOrCreateTagBuilder(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)
			.add(Blocks.COBBLED_DEEPSLATE_SLAB)
			.add(Blocks.POLISHED_DEEPSLATE_SLAB)
			.add(Blocks.DEEPSLATE_BRICK_SLAB)
			.add(Blocks.DEEPSLATE_TILE_SLAB)
			.addOptionalTag(WWBlockTags.SCULK_SLAB_REPLACEABLE);

		this.getOrCreateTagBuilder(WWBlockTags.SCULK_STAIR_REPLACEABLE)
			.add(Blocks.STONE_STAIRS)
			.add(Blocks.GRANITE_STAIRS)
			.add(Blocks.DIORITE_STAIRS)
			.add(Blocks.ANDESITE_STAIRS)
			.add(Blocks.BLACKSTONE_STAIRS);

		this.getOrCreateTagBuilder(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)
			.add(Blocks.COBBLED_DEEPSLATE_STAIRS)
			.add(Blocks.POLISHED_DEEPSLATE_STAIRS)
			.add(Blocks.DEEPSLATE_BRICK_STAIRS)
			.add(Blocks.DEEPSLATE_TILE_STAIRS)
			.addOptionalTag(WWBlockTags.SCULK_STAIR_REPLACEABLE);

		this.getOrCreateTagBuilder(WWBlockTags.SCULK_WALL_REPLACEABLE)
			.add(Blocks.COBBLESTONE_WALL)
			.add(Blocks.GRANITE_WALL)
			.add(Blocks.DIORITE_WALL)
			.add(Blocks.ANDESITE_WALL)
			.add(Blocks.BLACKSTONE_WALL);

		this.getOrCreateTagBuilder(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)
			.add(Blocks.COBBLED_DEEPSLATE_WALL)
			.add(Blocks.POLISHED_DEEPSLATE_WALL)
			.add(Blocks.DEEPSLATE_BRICK_WALL)
			.add(Blocks.DEEPSLATE_TILE_WALL)
			.addOptionalTag(WWBlockTags.SCULK_WALL_REPLACEABLE);

		this.getOrCreateTagBuilder(BlockTags.OCCLUDES_VIBRATION_SIGNALS)
			.add(WWBlocks.ECHO_GLASS);
	}

	private void generateHollowedAndTermites() {
		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_ACACIA_LOGS)
			.add(WWBlocks.HOLLOWED_ACACIA_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_BIRCH_LOGS)
			.add(WWBlocks.HOLLOWED_BIRCH_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_CHERRY_LOGS)
			.add(WWBlocks.HOLLOWED_CHERRY_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_CRIMSON_STEMS)
			.add(WWBlocks.HOLLOWED_CRIMSON_STEM)
			.add(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_DARK_OAK_LOGS)
			.add(WWBlocks.HOLLOWED_DARK_OAK_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_JUNGLE_LOGS)
			.add(WWBlocks.HOLLOWED_JUNGLE_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_MANGROVE_LOGS)
			.add(WWBlocks.HOLLOWED_MANGROVE_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_OAK_LOGS)
			.add(WWBlocks.HOLLOWED_OAK_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_SPRUCE_LOGS)
			.add(WWBlocks.HOLLOWED_SPRUCE_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_WARPED_STEMS)
			.add(WWBlocks.HOLLOWED_WARPED_STEM)
			.add(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_BAOBAB_LOGS)
			.add(WWBlocks.HOLLOWED_BAOBAB_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_WILLOW_LOGS)
			.add(WWBlocks.HOLLOWED_WILLOW_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_CYPRESS_LOGS)
			.add(WWBlocks.HOLLOWED_CYPRESS_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_PALM_LOGS)
			.add(WWBlocks.HOLLOWED_PALM_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_PALM_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_MAPLE_LOGS)
			.add(WWBlocks.HOLLOWED_MAPLE_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_LOGS_THAT_BURN)
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
			.addOptionalTag(WWBlockTags.HOLLOWED_MAPLE_LOGS);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_LOGS_DONT_BURN)
			.addOptionalTag(WWBlockTags.HOLLOWED_CRIMSON_STEMS)
			.addOptionalTag(WWBlockTags.HOLLOWED_WARPED_STEMS);

		this.getOrCreateTagBuilder(WWBlockTags.HOLLOWED_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS_DONT_BURN);

		this.getOrCreateTagBuilder(WWBlockTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.add(WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_PALM_LOG)
			.add(WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG);

		this.getOrCreateTagBuilder(WWBlockTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN)
			.add(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM)
			.add(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

		this.getOrCreateTagBuilder(WWBlockTags.STRIPPED_HOLLOWED_LOGS)
			.addOptionalTag(WWBlockTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWBlockTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN);

		this.getOrCreateTagBuilder(WWBlockTags.BLOCKS_TERMITE)
			.addOptionalTag(ConventionalBlockTags.GLASS_BLOCKS)
			.addOptionalTag(ConventionalBlockTags.GLASS_PANES);

		this.getOrCreateTagBuilder(WWBlockTags.KILLS_TERMITE)
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
			.add(WWBlocks.HOLLOWED_CRIMSON_STEM)
			.add(WWBlocks.HOLLOWED_WARPED_STEM)
			.add(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM)
			.add(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM)
			.addOptionalTag(BlockTags.WARPED_STEMS)
			.addOptionalTag(BlockTags.CRIMSON_STEMS);
	}

	private void generateMinecraft() {
		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
			.add(Blocks.CACTUS)
			.add(Blocks.SWEET_BERRY_BUSH)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS)
			.add(WWBlocks.TUMBLEWEED_PLANT)
			.add(WWBlocks.TUMBLEWEED)
			.add(WWBlocks.PRICKLY_PEAR_CACTUS)
			.add(WWBlocks.MYCELIUM_GROWTH)
			.add(WWBlocks.BROWN_SHELF_FUNGI)
			.add(WWBlocks.RED_SHELF_FUNGI)
			.add(WWBlocks.CRIMSON_SHELF_FUNGI)
			.add(WWBlocks.WARPED_SHELF_FUNGI);

		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_HOE)
			.add(Blocks.SWEET_BERRY_BUSH)
			.add(WWBlocks.OSSEOUS_SCULK)
			.add(WWBlocks.SCULK_SLAB)
			.add(WWBlocks.SCULK_STAIRS)
			.add(WWBlocks.SCULK_WALL)
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
			.add(WWBlocks.SPONGE_BUD);

		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(WWBlocks.STONE_CHEST)
			.add(WWBlocks.NULL_BLOCK)
			.add(WWBlocks.DISPLAY_LANTERN)
			.add(WWBlocks.SCORCHED_SAND)
			.add(WWBlocks.SCORCHED_RED_SAND)
			.add(WWBlocks.CHISELED_MUD_BRICKS)
			.add(WWBlocks.CRACKED_MUD_BRICKS)
			.add(WWBlocks.MOSSY_MUD_BRICKS)
			.add(WWBlocks.MOSSY_MUD_BRICK_STAIRS)
			.add(WWBlocks.MOSSY_MUD_BRICK_SLAB)
			.add(WWBlocks.MOSSY_MUD_BRICK_WALL)

			.add(WWBlocks.GABBRO)
			.add(WWBlocks.GEYSER)
			.add(WWBlocks.GABBRO_STAIRS)
			.add(WWBlocks.GABBRO_SLAB)
			.add(WWBlocks.GABBRO_WALL)
			.add(WWBlocks.POLISHED_GABBRO)
			.add(WWBlocks.POLISHED_GABBRO_STAIRS)
			.add(WWBlocks.POLISHED_GABBRO_SLAB)
			.add(WWBlocks.POLISHED_GABBRO_WALL)
			.add(WWBlocks.CHISELED_GABBRO_BRICKS)
			.add(WWBlocks.GABBRO_BRICKS)
			.add(WWBlocks.CRACKED_GABBRO_BRICKS)
			.add(WWBlocks.GABBRO_BRICK_STAIRS)
			.add(WWBlocks.GABBRO_BRICK_SLAB)
			.add(WWBlocks.GABBRO_BRICK_WALL)
			.add(WWBlocks.MOSSY_GABBRO_BRICKS)
			.add(WWBlocks.MOSSY_GABBRO_BRICK_STAIRS)
			.add(WWBlocks.MOSSY_GABBRO_BRICK_SLAB)
			.add(WWBlocks.MOSSY_GABBRO_BRICK_WALL);

		this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL)
			.addOptionalTag(WWBlockTags.MESOGLEA)
			.add(WWBlocks.SCORCHED_SAND)
			.add(WWBlocks.SCORCHED_RED_SAND)
			.add(WWBlocks.TERMITE_MOUND);

		this.getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT)
			.add(WWBlocks.BUSH)
			.add(WWBlocks.TUMBLEWEED)
			.add(WWBlocks.TUMBLEWEED_PLANT)
			.add(WWBlocks.MILKWEED)
			.add(WWBlocks.DATURA)
			.add(WWBlocks.CATTAIL)
			.add(WWBlocks.FLOWERING_LILY_PAD)
			.add(WWBlocks.ALGAE)
			.add(WWBlocks.BROWN_SHELF_FUNGI)
			.add(WWBlocks.RED_SHELF_FUNGI)
			.add(WWBlocks.CRIMSON_SHELF_FUNGI)
			.add(WWBlocks.WARPED_SHELF_FUNGI)
			.add(WWBlocks.SPONGE_BUD)
			.add(WWBlocks.PRICKLY_PEAR_CACTUS)
			.add(WWBlocks.MYCELIUM_GROWTH)
			.addOptionalTag(WWBlockTags.NEMATOCYSTS);

		this.getOrCreateTagBuilder(BlockTags.SAND)
			.add(WWBlocks.SCORCHED_SAND)
			.add(WWBlocks.SCORCHED_RED_SAND);

		this.getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
			.add(WWBlocks.GABBRO)
			.add(WWBlocks.GEYSER)
			.add(WWBlocks.GABBRO_STAIRS)
			.add(WWBlocks.GABBRO_SLAB)
			.add(WWBlocks.GABBRO_WALL)
			.add(WWBlocks.POLISHED_GABBRO)
			.add(WWBlocks.POLISHED_GABBRO_STAIRS)
			.add(WWBlocks.POLISHED_GABBRO_SLAB)
			.add(WWBlocks.POLISHED_GABBRO_WALL)
			.add(WWBlocks.CHISELED_GABBRO_BRICKS)
			.add(WWBlocks.GABBRO_BRICKS)
			.add(WWBlocks.CRACKED_GABBRO_BRICKS)
			.add(WWBlocks.GABBRO_BRICK_STAIRS)
			.add(WWBlocks.GABBRO_BRICK_SLAB)
			.add(WWBlocks.GABBRO_BRICK_WALL)
			.add(WWBlocks.MOSSY_GABBRO_BRICKS)
			.add(WWBlocks.MOSSY_GABBRO_BRICK_STAIRS)
			.add(WWBlocks.MOSSY_GABBRO_BRICK_SLAB)
			.add(WWBlocks.MOSSY_GABBRO_BRICK_WALL);

		this.getOrCreateTagBuilder(BlockTags.BASE_STONE_OVERWORLD)
			.add(WWBlocks.GABBRO);

		this.getOrCreateTagBuilder(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
			.add(WWBlocks.GABBRO);

		this.getOrCreateTagBuilder(BlockTags.STAIRS)
			.add(WWBlocks.SCULK_STAIRS)
			.add(WWBlocks.MOSSY_MUD_BRICK_STAIRS)
			.add(WWBlocks.GABBRO_STAIRS)
			.add(WWBlocks.POLISHED_GABBRO_STAIRS)
			.add(WWBlocks.GABBRO_BRICK_STAIRS)
			.add(WWBlocks.MOSSY_GABBRO_BRICK_STAIRS);

		this.getOrCreateTagBuilder(BlockTags.SLABS)
			.add(WWBlocks.SCULK_SLAB)
			.add(WWBlocks.MOSSY_MUD_BRICK_SLAB)
			.add(WWBlocks.GABBRO_SLAB)
			.add(WWBlocks.POLISHED_GABBRO_SLAB)
			.add(WWBlocks.GABBRO_BRICK_SLAB)
			.add(WWBlocks.MOSSY_GABBRO_BRICK_SLAB);

		this.getOrCreateTagBuilder(BlockTags.WALLS)
			.add(WWBlocks.SCULK_WALL)
			.add(WWBlocks.MOSSY_MUD_BRICK_WALL)
			.add(WWBlocks.GABBRO_WALL)
			.add(WWBlocks.POLISHED_GABBRO_WALL)
			.add(WWBlocks.GABBRO_BRICK_WALL)
			.add(WWBlocks.MOSSY_GABBRO_BRICK_WALL);

		this.getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS)
			.add(Blocks.COBWEB)
			.add(Blocks.LILY_PAD)
			.add(WWBlocks.FLOWERING_LILY_PAD)
			.add(WWBlocks.ALGAE)
			.add(WWBlocks.TUMBLEWEED_PLANT)
			.add(WWBlocks.SPONGE_BUD)
			.add(WWBlocks.PRICKLY_PEAR_CACTUS)
			.add(WWBlocks.POLLEN);

		this.getOrCreateTagBuilder(BlockTags.REPLACEABLE)
			.add(WWBlocks.MYCELIUM_GROWTH)
			.addOptionalTag(WWBlockTags.LEAF_LITTERS);

		this.getOrCreateTagBuilder(BlockTags.REPLACEABLE_BY_TREES)
			.add(WWBlocks.MYCELIUM_GROWTH)
			.add(WWBlocks.DATURA)
			.add(WWBlocks.CATTAIL)
			.add(WWBlocks.MILKWEED)
			.add(WWBlocks.BUSH)
			.add(WWBlocks.POLLEN)
			.addOptionalTag(WWBlockTags.LEAF_LITTERS);

		this.getOrCreateTagBuilder(BlockTags.COMBINATION_STEP_SOUND_BLOCKS)
			.addOptionalTag(WWBlockTags.LEAF_LITTERS)
			.add(WWBlocks.POLLEN);

		this.getOrCreateTagBuilder(BlockTags.FLOWER_POTS)
			.add(WWBlocks.POTTED_BAOBAB_NUT)
			.add(WWBlocks.POTTED_WILLOW_SAPLING)
			.add(WWBlocks.POTTED_CYPRESS_SAPLING)
			.add(WWBlocks.POTTED_COCONUT)
			.add(WWBlocks.POTTED_MAPLE_SAPLING)
			.add(WWBlocks.POTTED_BUSH)
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
			.add(WWBlocks.POTTED_YELLOW_HIBISCUS)
			.add(WWBlocks.POTTED_WHITE_HIBISCUS)
			.add(WWBlocks.POTTED_PINK_HIBISCUS)
			.add(WWBlocks.POTTED_PURPLE_HIBISCUS);

		this.getOrCreateTagBuilder(BlockTags.FLOWERS)
			.add(WWBlocks.POLLEN);

		this.getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS)
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

		this.getOrCreateTagBuilder(BlockTags.TALL_FLOWERS)
			.add(WWBlocks.DATURA)
			.add(WWBlocks.CATTAIL)
			.add(WWBlocks.MILKWEED);

		this.getOrCreateTagBuilder(BlockTags.FROG_PREFER_JUMP_TO)
			.add(WWBlocks.FLOWERING_LILY_PAD);

		this.getOrCreateTagBuilder(BlockTags.BIG_DRIPLEAF_PLACEABLE)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.getOrCreateTagBuilder(BlockTags.SMALL_DRIPLEAF_PLACEABLE)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.getOrCreateTagBuilder(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH)
			.add(WWBlocks.ALGAE);

		this.getOrCreateTagBuilder(BlockTags.GUARDED_BY_PIGLINS)
			.add(WWBlocks.STONE_CHEST);

		this.getOrCreateTagBuilder(BlockTags.IMPERMEABLE)
			.add(WWBlocks.ECHO_GLASS)
			.addOptionalTag(WWBlockTags.MESOGLEA);

		this.getOrCreateTagBuilder(BlockTags.FEATURES_CANNOT_REPLACE)
			.add(WWBlocks.STONE_CHEST);

		this.getOrCreateTagBuilder(BlockTags.SCULK_REPLACEABLE_WORLD_GEN)
			.addOptionalTag(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)
			.addOptionalTag(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)
			.addOptionalTag(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN);
	}

	private void generateWoods() {
		this.getOrCreateTagBuilder(BlockTags.OVERWORLD_NATURAL_LOGS)
			.add(WWBlocks.BAOBAB_LOG)
			.add(WWBlocks.WILLOW_LOG)
			.add(WWBlocks.CYPRESS_LOG)
			.add(WWBlocks.PALM_LOG)
			.add(WWBlocks.MAPLE_LOG);

		this.getOrCreateTagBuilder(BlockTags.ACACIA_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_ACACIA_LOGS);

		this.getOrCreateTagBuilder(BlockTags.BIRCH_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_BIRCH_LOGS);

		this.getOrCreateTagBuilder(BlockTags.CHERRY_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_CHERRY_LOGS);

		this.getOrCreateTagBuilder(BlockTags.CRIMSON_STEMS)
			.addOptionalTag(WWBlockTags.HOLLOWED_CRIMSON_STEMS);

		this.getOrCreateTagBuilder(BlockTags.DARK_OAK_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_DARK_OAK_LOGS);

		this.getOrCreateTagBuilder(BlockTags.JUNGLE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_JUNGLE_LOGS);

		this.getOrCreateTagBuilder(BlockTags.ACACIA_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_ACACIA_LOGS);

		this.getOrCreateTagBuilder(BlockTags.MANGROVE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_MANGROVE_LOGS);

		this.getOrCreateTagBuilder(BlockTags.OAK_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_OAK_LOGS);

		this.getOrCreateTagBuilder(BlockTags.ACACIA_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_ACACIA_LOGS);

		this.getOrCreateTagBuilder(BlockTags.SPRUCE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_SPRUCE_LOGS);

		this.getOrCreateTagBuilder(BlockTags.WARPED_STEMS)
			.addOptionalTag(WWBlockTags.HOLLOWED_WARPED_STEMS);

		this.getOrCreateTagBuilder(WWBlockTags.LEAF_LITTER_CANNOT_SURVIVE_ON)
			.add(Blocks.BARRIER)
			.addOptionalTag(BlockTags.LEAVES);

		this.getOrCreateTagBuilder(WWBlockTags.LEAF_LITTER_CAN_SURVIVE_ON)
			.add(Blocks.HONEY_BLOCK)
			.add(Blocks.SOUL_SAND)
			.add(Blocks.MUD);

		this.getOrCreateTagBuilder(WWBlockTags.BAOBAB_LOGS)
			.add(WWBlocks.BAOBAB_LOG)
			.add(WWBlocks.STRIPPED_BAOBAB_LOG)
			.add(WWBlocks.BAOBAB_WOOD)
			.add(WWBlocks.STRIPPED_BAOBAB_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_BAOBAB_LOGS);

		this.getOrCreateTagBuilder(WWBlockTags.WILLOW_LOGS)
			.add(WWBlocks.WILLOW_LOG)
			.add(WWBlocks.STRIPPED_WILLOW_LOG)
			.add(WWBlocks.WILLOW_WOOD)
			.add(WWBlocks.STRIPPED_WILLOW_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_WILLOW_LOGS);

		this.getOrCreateTagBuilder(WWBlockTags.CYPRESS_LOGS)
			.add(WWBlocks.CYPRESS_LOG)
			.add(WWBlocks.STRIPPED_CYPRESS_LOG)
			.add(WWBlocks.CYPRESS_WOOD)
			.add(WWBlocks.STRIPPED_CYPRESS_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_CYPRESS_LOGS);

		this.getOrCreateTagBuilder(WWBlockTags.PALM_LOGS)
			.add(WWBlocks.PALM_LOG)
			.add(WWBlocks.STRIPPED_PALM_LOG)
			.add(WWBlocks.PALM_WOOD)
			.add(WWBlocks.STRIPPED_PALM_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_PALM_LOGS);

		this.getOrCreateTagBuilder(WWBlockTags.MAPLE_LOGS)
			.add(WWBlocks.MAPLE_LOG)
			.add(WWBlocks.STRIPPED_MAPLE_LOG)
			.add(WWBlocks.MAPLE_WOOD)
			.add(WWBlocks.STRIPPED_MAPLE_WOOD)
			.addOptionalTag(WWBlockTags.HOLLOWED_MAPLE_LOGS);

		this.getOrCreateTagBuilder(BlockTags.LOGS)
			.addOptionalTag(WWBlockTags.BAOBAB_LOGS)
			.addOptionalTag(WWBlockTags.WILLOW_LOGS)
			.addOptionalTag(WWBlockTags.CYPRESS_LOGS)
			.addOptionalTag(WWBlockTags.PALM_LOGS)
			.addOptionalTag(WWBlockTags.MAPLE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS);

		this.getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
			.addOptionalTag(WWBlockTags.BAOBAB_LOGS)
			.addOptionalTag(WWBlockTags.WILLOW_LOGS)
			.addOptionalTag(WWBlockTags.CYPRESS_LOGS)
			.addOptionalTag(WWBlockTags.PALM_LOGS)
			.addOptionalTag(WWBlockTags.MAPLE_LOGS)
			.addOptionalTag(WWBlockTags.HOLLOWED_LOGS_THAT_BURN);

		this.getOrCreateTagBuilder(WWBlockTags.WILLOW_ROOTS_CAN_GROW_THROUGH)
			.add(Blocks.MUDDY_MANGROVE_ROOTS)
			.add(Blocks.MANGROVE_ROOTS)
			.add(Blocks.MOSS_CARPET)
			.add(Blocks.VINE)
			.add(Blocks.SNOW)
			.add(Blocks.RED_MUSHROOM)
			.add(Blocks.BROWN_MUSHROOM)
			.addOptionalTag(BlockTags.SMALL_FLOWERS);

		this.getOrCreateTagBuilder(BlockTags.LEAVES)
			.add(WWBlocks.BAOBAB_LEAVES)
			.add(WWBlocks.WILLOW_LEAVES)
			.add(WWBlocks.CYPRESS_LEAVES)
			.add(WWBlocks.PALM_FRONDS)
			.add(WWBlocks.YELLOW_MAPLE_LEAVES)
			.add(WWBlocks.ORANGE_MAPLE_LEAVES)
			.add(WWBlocks.RED_MAPLE_LEAVES);

		this.getOrCreateTagBuilder(WWBlockTags.LEAF_LITTERS)
			.add(WWBlocks.YELLOW_MAPLE_LEAF_LITTER)
			.add(WWBlocks.ORANGE_MAPLE_LEAF_LITTER)
			.add(WWBlocks.RED_MAPLE_LEAF_LITTER);

		this.getOrCreateTagBuilder(BlockTags.PLANKS)
			.add(WWBlocks.BAOBAB_PLANKS)
			.add(WWBlocks.WILLOW_PLANKS)
			.add(WWBlocks.CYPRESS_PLANKS)
			.add(WWBlocks.PALM_PLANKS)
			.add(WWBlocks.MAPLE_PLANKS);

		this.getOrCreateTagBuilder(BlockTags.SAPLINGS)
			.add(WWBlocks.BAOBAB_NUT)
			.add(WWBlocks.WILLOW_SAPLING)
			.add(WWBlocks.CYPRESS_SAPLING)
			.add(WWBlocks.COCONUT)
			.add(WWBlocks.MAPLE_SAPLING);

		this.getOrCreateTagBuilder(BlockTags.STANDING_SIGNS)
			.add(WWBlocks.BAOBAB_SIGN)
			.add(WWBlocks.WILLOW_SIGN)
			.add(WWBlocks.CYPRESS_SIGN)
			.add(WWBlocks.PALM_SIGN)
			.add(WWBlocks.MAPLE_SIGN);

		this.getOrCreateTagBuilder(BlockTags.WALL_SIGNS)
			.add(WWBlocks.BAOBAB_WALL_SIGN)
			.add(WWBlocks.WILLOW_WALL_SIGN)
			.add(WWBlocks.CYPRESS_WALL_SIGN)
			.add(WWBlocks.PALM_WALL_SIGN)
			.add(WWBlocks.MAPLE_WALL_SIGN);

		this.getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS)
			.add(WWBlocks.BAOBAB_HANGING_SIGN)
			.add(WWBlocks.WILLOW_HANGING_SIGN)
			.add(WWBlocks.CYPRESS_HANGING_SIGN)
			.add(WWBlocks.PALM_HANGING_SIGN)
			.add(WWBlocks.MAPLE_HANGING_SIGN);

		this.getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS)
			.add(WWBlocks.BAOBAB_WALL_HANGING_SIGN)
			.add(WWBlocks.WILLOW_WALL_HANGING_SIGN)
			.add(WWBlocks.CYPRESS_WALL_HANGING_SIGN)
			.add(WWBlocks.PALM_WALL_HANGING_SIGN)
			.add(WWBlocks.MAPLE_WALL_HANGING_SIGN);

		this.getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS)
			.add(WWBlocks.BAOBAB_BUTTON)
			.add(WWBlocks.WILLOW_BUTTON)
			.add(WWBlocks.CYPRESS_BUTTON)
			.add(WWBlocks.PALM_BUTTON)
			.add(WWBlocks.MAPLE_BUTTON);

		this.getOrCreateTagBuilder(BlockTags.WOODEN_DOORS)
			.add(WWBlocks.BAOBAB_DOOR)
			.add(WWBlocks.WILLOW_DOOR)
			.add(WWBlocks.CYPRESS_DOOR)
			.add(WWBlocks.PALM_DOOR)
			.add(WWBlocks.MAPLE_DOOR);

		this.getOrCreateTagBuilder(BlockTags.WOODEN_FENCES)
			.add(WWBlocks.BAOBAB_FENCE)
			.add(WWBlocks.WILLOW_FENCE)
			.add(WWBlocks.CYPRESS_FENCE)
			.add(WWBlocks.PALM_FENCE)
			.add(WWBlocks.MAPLE_FENCE);

		this.getOrCreateTagBuilder(BlockTags.FENCE_GATES)
			.add(WWBlocks.BAOBAB_FENCE_GATE)
			.add(WWBlocks.WILLOW_FENCE_GATE)
			.add(WWBlocks.CYPRESS_FENCE_GATE)
			.add(WWBlocks.PALM_FENCE_GATE)
			.add(WWBlocks.MAPLE_FENCE_GATE);

		this.getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES)
			.add(WWBlocks.BAOBAB_PRESSURE_PLATE)
			.add(WWBlocks.WILLOW_PRESSURE_PLATE)
			.add(WWBlocks.CYPRESS_PRESSURE_PLATE)
			.add(WWBlocks.PALM_PRESSURE_PLATE)
			.add(WWBlocks.MAPLE_PRESSURE_PLATE);

		this.getOrCreateTagBuilder(BlockTags.WOODEN_SLABS)
			.add(WWBlocks.BAOBAB_SLAB)
			.add(WWBlocks.WILLOW_SLAB)
			.add(WWBlocks.CYPRESS_SLAB)
			.add(WWBlocks.PALM_SLAB)
			.add(WWBlocks.MAPLE_SLAB);

		this.getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS)
			.add(WWBlocks.BAOBAB_STAIRS)
			.add(WWBlocks.WILLOW_STAIRS)
			.add(WWBlocks.CYPRESS_STAIRS)
			.add(WWBlocks.PALM_STAIRS)
			.add(WWBlocks.MAPLE_STAIRS);

		this.getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS)
			.add(WWBlocks.BAOBAB_TRAPDOOR)
			.add(WWBlocks.WILLOW_TRAPDOOR)
			.add(WWBlocks.CYPRESS_TRAPDOOR)
			.add(WWBlocks.PALM_TRAPDOOR)
			.add(WWBlocks.MAPLE_TRAPDOOR);
	}
}
