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

import java.util.function.Function;
import net.frozenblock.wilderwild.references.WWBlockItemIds;
import net.frozenblock.wilderwild.tag.WWBlockItemTags;
import net.minecraft.data.tags.BlockItemTagsProvider;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.BlockItemTagId;
import net.minecraft.tags.BlockItemTags;

public final class WWBlockItemTagsProvider extends BlockItemTagsProvider {

	WWBlockItemTagsProvider(Function<BlockItemTagId, CombinedAppender> tagSupplier) {
		super(tagSupplier);
	}

	@Override
	protected void run() {
		this.tag(BlockItemTags.STAIRS)
			.add(WWBlockItemIds.SCULK_STAIRS)
			.add(WWBlockItemIds.MOSSY_MUD_BRICK_STAIRS)
			.add(WWBlockItemIds.GABBRO_STAIRS)
			.add(WWBlockItemIds.POLISHED_GABBRO_STAIRS)
			.add(WWBlockItemIds.GABBRO_BRICK_STAIRS)
			.add(WWBlockItemIds.MOSSY_GABBRO_BRICK_STAIRS);

		this.tag(BlockItemTags.SLABS)
			.add(WWBlockItemIds.SCULK_SLAB)
			.add(WWBlockItemIds.MOSSY_MUD_BRICK_SLAB)
			.add(WWBlockItemIds.GABBRO_SLAB)
			.add(WWBlockItemIds.POLISHED_GABBRO_SLAB)
			.add(WWBlockItemIds.GABBRO_BRICK_SLAB)
			.add(WWBlockItemIds.MOSSY_GABBRO_BRICK_SLAB);

		this.tag(BlockItemTags.WALLS)
			.add(WWBlockItemIds.SCULK_WALL)
			.add(WWBlockItemIds.MOSSY_MUD_BRICK_WALL)
			.add(WWBlockItemIds.GABBRO_WALL)
			.add(WWBlockItemIds.POLISHED_GABBRO_WALL)
			.add(WWBlockItemIds.GABBRO_BRICK_WALL)
			.add(WWBlockItemIds.MOSSY_GABBRO_BRICK_WALL);

		this.tag(BlockItemTags.WOODEN_SHELVES)
			.add(WWBlockItemIds.BAOBAB_SHELF)
			.add(WWBlockItemIds.WILLOW_SHELF)
			.add(WWBlockItemIds.CYPRESS_SHELF)
			.add(WWBlockItemIds.PALM_SHELF)
			.add(WWBlockItemIds.MAPLE_SHELF);

		this.tag(BlockItemTags.SIGNS)
			.add(WWBlockItemIds.BAOBAB_SIGN)
			.add(WWBlockItemIds.WILLOW_SIGN)
			.add(WWBlockItemIds.CYPRESS_SIGN)
			.add(WWBlockItemIds.PALM_SIGN)
			.add(WWBlockItemIds.MAPLE_SIGN);

		this.tag(BlockItemTags.HANGING_SIGNS)
			.add(WWBlockItemIds.BAOBAB_HANGING_SIGN)
			.add(WWBlockItemIds.WILLOW_HANGING_SIGN)
			.add(WWBlockItemIds.CYPRESS_HANGING_SIGN)
			.add(WWBlockItemIds.PALM_HANGING_SIGN)
			.add(WWBlockItemIds.MAPLE_HANGING_SIGN);

		this.tag(BlockItemTags.WOODEN_BUTTONS)
			.add(WWBlockItemIds.BAOBAB_BUTTON)
			.add(WWBlockItemIds.WILLOW_BUTTON)
			.add(WWBlockItemIds.CYPRESS_BUTTON)
			.add(WWBlockItemIds.PALM_BUTTON)
			.add(WWBlockItemIds.MAPLE_BUTTON);

		this.tag(BlockItemTags.WOODEN_DOORS)
			.add(WWBlockItemIds.BAOBAB_DOOR)
			.add(WWBlockItemIds.WILLOW_DOOR)
			.add(WWBlockItemIds.CYPRESS_DOOR)
			.add(WWBlockItemIds.PALM_DOOR)
			.add(WWBlockItemIds.MAPLE_DOOR);

		this.tag(BlockItemTags.WOODEN_FENCES)
			.add(WWBlockItemIds.BAOBAB_FENCE)
			.add(WWBlockItemIds.WILLOW_FENCE)
			.add(WWBlockItemIds.CYPRESS_FENCE)
			.add(WWBlockItemIds.PALM_FENCE)
			.add(WWBlockItemIds.MAPLE_FENCE);

		this.tag(BlockItemTags.FENCE_GATES)
			.add(WWBlockItemIds.BAOBAB_FENCE_GATE)
			.add(WWBlockItemIds.WILLOW_FENCE_GATE)
			.add(WWBlockItemIds.CYPRESS_FENCE_GATE)
			.add(WWBlockItemIds.PALM_FENCE_GATE)
			.add(WWBlockItemIds.MAPLE_FENCE_GATE);

		this.tag(BlockItemTags.WOODEN_PRESSURE_PLATES)
			.add(WWBlockItemIds.BAOBAB_PRESSURE_PLATE)
			.add(WWBlockItemIds.WILLOW_PRESSURE_PLATE)
			.add(WWBlockItemIds.CYPRESS_PRESSURE_PLATE)
			.add(WWBlockItemIds.PALM_PRESSURE_PLATE)
			.add(WWBlockItemIds.MAPLE_PRESSURE_PLATE);

		this.tag(BlockItemTags.WOODEN_SLABS)
			.add(WWBlockItemIds.BAOBAB_SLAB)
			.add(WWBlockItemIds.WILLOW_SLAB)
			.add(WWBlockItemIds.CYPRESS_SLAB)
			.add(WWBlockItemIds.PALM_SLAB)
			.add(WWBlockItemIds.MAPLE_SLAB);

		this.tag(BlockItemTags.WOODEN_STAIRS)
			.add(WWBlockItemIds.BAOBAB_STAIRS)
			.add(WWBlockItemIds.WILLOW_STAIRS)
			.add(WWBlockItemIds.CYPRESS_STAIRS)
			.add(WWBlockItemIds.PALM_STAIRS)
			.add(WWBlockItemIds.MAPLE_STAIRS);

		this.tag(BlockItemTags.WOODEN_TRAPDOORS)
			.add(WWBlockItemIds.BAOBAB_TRAPDOOR)
			.add(WWBlockItemIds.WILLOW_TRAPDOOR)
			.add(WWBlockItemIds.CYPRESS_TRAPDOOR)
			.add(WWBlockItemIds.PALM_TRAPDOOR)
			.add(WWBlockItemIds.MAPLE_TRAPDOOR);

		this.tag(BlockItemTags.LOGS)
			.addTag(WWBlockItemTags.BAOBAB_LOGS)
			.addTag(WWBlockItemTags.WILLOW_LOGS)
			.addTag(WWBlockItemTags.CYPRESS_LOGS)
			.addTag(WWBlockItemTags.PALM_LOGS)
			.addTag(WWBlockItemTags.MAPLE_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_LOGS);

		this.tag(BlockItemTags.LOGS_THAT_BURN)
			.addTag(WWBlockItemTags.BAOBAB_LOGS)
			.addTag(WWBlockItemTags.WILLOW_LOGS)
			.addTag(WWBlockItemTags.CYPRESS_LOGS)
			.addTag(WWBlockItemTags.PALM_LOGS)
			.addTag(WWBlockItemTags.MAPLE_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_LOGS_THAT_BURN);

		this.tag(BlockItemTags.PLANKS)
			.add(WWBlockItemIds.BAOBAB_PLANKS)
			.add(WWBlockItemIds.WILLOW_PLANKS)
			.add(WWBlockItemIds.CYPRESS_PLANKS)
			.add(WWBlockItemIds.PALM_PLANKS)
			.add(WWBlockItemIds.MAPLE_PLANKS);

		this.tag(BlockItemTags.LEAVES)
			.add(WWBlockItemIds.BAOBAB_LEAVES)
			.add(WWBlockItemIds.WILLOW_LEAVES)
			.add(WWBlockItemIds.CYPRESS_LEAVES)
			.add(WWBlockItemIds.PALM_FRONDS)
			.add(WWBlockItemIds.YELLOW_MAPLE_LEAVES)
			.add(WWBlockItemIds.ORANGE_MAPLE_LEAVES)
			.add(WWBlockItemIds.RED_MAPLE_LEAVES);

		this.tag(BlockItemTags.SAPLINGS)
			.add(WWBlockItemIds.BAOBAB_NUT)
			.add(WWBlockItemIds.WILLOW_SAPLING)
			.add(WWBlockItemIds.CYPRESS_SAPLING)
			.add(WWBlockItemIds.COCONUT)
			.add(WWBlockItemIds.YELLOW_MAPLE_SAPLING, WWBlockItemIds.ORANGE_MAPLE_SAPLING, WWBlockItemIds.RED_MAPLE_SAPLING);

		this.tag(BlockItemTags.FLOWERS)
			.add(WWBlockItemIds.DATURA)
			.add(WWBlockItemIds.CATTAIL)
			.add(WWBlockItemIds.MILKWEED)
			.add(WWBlockItemIds.POLLEN)
			.add(WWBlockItemIds.PHLOX)
			.add(WWBlockItemIds.LANTANAS);

		this.tag(BlockItemTags.SMALL_FLOWERS)
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

		this.tag(BlockItemTags.BEE_FOOD)
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
			.add(WWBlockItemIds.POLLEN)
			.add(WWBlockItemIds.PHLOX)
			.add(WWBlockItemIds.LANTANAS);

		this.tag(BlockItemTags.MOSS_BLOCKS)
			.add(WWBlockItemIds.AUBURN_MOSS_BLOCK);

		this.tag(BlockItemTags.SAND)
			.add(WWBlockItemIds.SCORCHED_SAND, WWBlockItemIds.SCORCHED_RED_SAND);

		// WILDER WILD TAGS
		this.tag(WWBlockItemTags.BAOBAB_LOGS)
			.add(WWBlockItemIds.BAOBAB_LOG, WWBlockItemIds.STRIPPED_BAOBAB_LOG)
			.add(WWBlockItemIds.BAOBAB_WOOD, WWBlockItemIds.STRIPPED_BAOBAB_WOOD);

		this.tag(WWBlockItemTags.WILLOW_LOGS)
			.add(WWBlockItemIds.WILLOW_LOG, WWBlockItemIds.STRIPPED_WILLOW_LOG)
			.add(WWBlockItemIds.WILLOW_WOOD, WWBlockItemIds.STRIPPED_WILLOW_WOOD);

		this.tag(WWBlockItemTags.CYPRESS_LOGS)
			.add(WWBlockItemIds.CYPRESS_LOG, WWBlockItemIds.STRIPPED_CYPRESS_LOG)
			.add(WWBlockItemIds.CYPRESS_WOOD, WWBlockItemIds.STRIPPED_CYPRESS_WOOD);

		this.tag(WWBlockItemTags.PALM_LOGS)
			.add(WWBlockItemIds.PALM_LOG, WWBlockItemIds.STRIPPED_PALM_LOG)
			.add(WWBlockItemIds.PALM_WOOD, WWBlockItemIds.STRIPPED_PALM_WOOD);

		this.tag(WWBlockItemTags.MAPLE_LOGS)
			.add(WWBlockItemIds.MAPLE_LOG, WWBlockItemIds.STRIPPED_MAPLE_LOG)
			.add(WWBlockItemIds.MAPLE_WOOD, WWBlockItemIds.STRIPPED_MAPLE_WOOD);

		this.tag(WWBlockItemTags.HOLLOWED_ACACIA_LOGS)
			.add(WWBlockItemIds.HOLLOWED_ACACIA_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_ACACIA_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_BIRCH_LOGS)
			.add(WWBlockItemIds.HOLLOWED_BIRCH_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_BIRCH_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_CHERRY_LOGS)
			.add(WWBlockItemIds.HOLLOWED_CHERRY_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_CHERRY_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_CRIMSON_STEMS)
			.add(WWBlockItemIds.HOLLOWED_CRIMSON_STEM, WWBlockItemIds.STRIPPED_HOLLOWED_CRIMSON_STEM);

		this.tag(WWBlockItemTags.HOLLOWED_DARK_OAK_LOGS)
			.add(WWBlockItemIds.HOLLOWED_DARK_OAK_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_DARK_OAK_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_JUNGLE_LOGS)
			.add(WWBlockItemIds.HOLLOWED_JUNGLE_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_JUNGLE_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_MANGROVE_LOGS)
			.add(WWBlockItemIds.HOLLOWED_MANGROVE_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_MANGROVE_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_OAK_LOGS)
			.add(WWBlockItemIds.HOLLOWED_OAK_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_OAK_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_SPRUCE_LOGS)
			.add(WWBlockItemIds.HOLLOWED_SPRUCE_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_SPRUCE_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_WARPED_STEMS)
			.add(WWBlockItemIds.HOLLOWED_WARPED_STEM, WWBlockItemIds.STRIPPED_HOLLOWED_WARPED_STEM);

		this.tag(WWBlockItemTags.HOLLOWED_BAOBAB_LOGS)
			.add(WWBlockItemIds.HOLLOWED_BAOBAB_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_BAOBAB_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_WILLOW_LOGS)
			.add(WWBlockItemIds.HOLLOWED_WILLOW_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_WILLOW_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_CYPRESS_LOGS)
			.add(WWBlockItemIds.HOLLOWED_CYPRESS_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_CYPRESS_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_PALM_LOGS)
			.add(WWBlockItemIds.HOLLOWED_PALM_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_PALM_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_PALE_OAK_LOGS)
			.add(WWBlockItemIds.HOLLOWED_PALE_OAK_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_PALE_OAK_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_MAPLE_LOGS)
			.add(WWBlockItemIds.HOLLOWED_MAPLE_LOG, WWBlockItemIds.STRIPPED_HOLLOWED_MAPLE_LOG);

		this.tag(WWBlockItemTags.HOLLOWED_LOGS_THAT_BURN)
			.addTag(WWBlockItemTags.HOLLOWED_ACACIA_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_BIRCH_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_CHERRY_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_DARK_OAK_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_JUNGLE_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_MANGROVE_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_OAK_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_SPRUCE_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_BAOBAB_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_WILLOW_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_CYPRESS_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_PALM_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_PALE_OAK_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_MAPLE_LOGS);

		this.tag(WWBlockItemTags.HOLLOWED_LOGS_DONT_BURN)
			.addTag(WWBlockItemTags.HOLLOWED_CRIMSON_STEMS)
			.addTag(WWBlockItemTags.HOLLOWED_WARPED_STEMS);

		this.tag(WWBlockItemTags.HOLLOWED_LOGS)
			.addTag(WWBlockItemTags.HOLLOWED_LOGS_THAT_BURN)
			.addTag(WWBlockItemTags.HOLLOWED_LOGS_DONT_BURN);

		this.tag(WWBlockItemTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_ACACIA_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_BIRCH_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_CHERRY_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_DARK_OAK_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_JUNGLE_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_MANGROVE_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_OAK_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_SPRUCE_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_BAOBAB_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_WILLOW_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_CYPRESS_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_PALM_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_PALE_OAK_LOG)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_MAPLE_LOG);

		this.tag(WWBlockItemTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_CRIMSON_STEM)
			.add(WWBlockItemIds.STRIPPED_HOLLOWED_WARPED_STEM);

		this.tag(WWBlockItemTags.STRIPPED_HOLLOWED_LOGS)
			.addTag(WWBlockItemTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.addTag(WWBlockItemTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN);

		this.tag(WWBlockItemTags.LEAF_LITTERS)
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

		this.tag(WWBlockItemTags.MESOGLEA)
			.add(WWBlockItemIds.BLUE_MESOGLEA)
			.add(WWBlockItemIds.PEARLESCENT_BLUE_MESOGLEA)
			.add(WWBlockItemIds.LIME_MESOGLEA)
			.add(WWBlockItemIds.PINK_MESOGLEA)
			.add(WWBlockItemIds.PEARLESCENT_PURPLE_MESOGLEA)
			.add(WWBlockItemIds.RED_MESOGLEA)
			.add(WWBlockItemIds.YELLOW_MESOGLEA);

		this.tag(WWBlockItemTags.NEMATOCYSTS)
			.add(WWBlockItemIds.BLUE_NEMATOCYST)
			.add(WWBlockItemIds.PEARLESCENT_BLUE_NEMATOCYST)
			.add(WWBlockItemIds.LIME_NEMATOCYST)
			.add(WWBlockItemIds.PINK_NEMATOCYST)
			.add(WWBlockItemIds.PEARLESCENT_PURPLE_NEMATOCYST)
			.add(WWBlockItemIds.RED_NEMATOCYST)
			.add(WWBlockItemIds.YELLOW_NEMATOCYST);

		this.tag(WWBlockItemTags.PEARLESCENT_NEMATOCYSTS)
			.add(WWBlockItemIds.PEARLESCENT_BLUE_NEMATOCYST)
			.add(WWBlockItemIds.PEARLESCENT_PURPLE_NEMATOCYST);

		this.tag(WWBlockItemTags.FROGLIGHT_GOOP)
			.add(WWBlockItemIds.PEARLESCENT_FROGLIGHT_GOOP)
			.add(WWBlockItemIds.VERDANT_FROGLIGHT_GOOP)
			.add(WWBlockItemIds.OCHRE_FROGLIGHT_GOOP);
	}
}
