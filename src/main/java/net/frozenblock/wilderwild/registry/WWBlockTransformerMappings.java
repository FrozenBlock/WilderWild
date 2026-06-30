/*
 * Copyright 2026 FrozenBlock
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
 * along with this program; if not, WWBlocks.see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.item.api.component.BlockTransformerMappingsApi;
import net.frozenblock.lib.levelgen.blockpredicates.HasMatchingAxisPredicate;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.world.item.component.BlockTransformerMappings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.CopyPropertiesProvider;
import java.util.Arrays;

public final class WWBlockTransformerMappings implements BlockTransformerMappingsApi.ModifyAxeBlockTransformer {
	private static final BlockPredicate LOG_HOLLOWING_ENABLED = ConfigPredicate.equalTo(WWBlockConfig.LOG_HOLLOWING, true).asBlockPredicate();

	@Override
	public void modifyAxeBlockTransformer(BlockTransformerMappingsApi.Context context) {
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.BAOBAB_WOOD, WWBlocks.STRIPPED_BAOBAB_WOOD));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.WILLOW_WOOD, WWBlocks.STRIPPED_WILLOW_WOOD));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.CYPRESS_WOOD, WWBlocks.STRIPPED_CYPRESS_WOOD));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.PALM_LOG, WWBlocks.STRIPPED_PALM_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.PALM_WOOD, WWBlocks.STRIPPED_PALM_WOOD));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.MAPLE_WOOD, WWBlocks.STRIPPED_MAPLE_WOOD));

		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_POPLAR_LOG, WWBlocks.STRIPPED_HOLLOWED_POPLAR_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_CRIMSON_STEM, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_WARPED_STEM, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_WILLOW_LOG, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG));

		// HOLLOWED
		addHollowableBlockData(context, Blocks.OAK_LOG, WWBlocks.HOLLOWED_OAK_LOG, false);
		addHollowableBlockData(context, Blocks.BIRCH_LOG, WWBlocks.HOLLOWED_BIRCH_LOG, false);
		addHollowableBlockData(context, Blocks.CHERRY_LOG, WWBlocks.HOLLOWED_CHERRY_LOG, false);
		addHollowableBlockData(context, Blocks.SPRUCE_LOG, WWBlocks.HOLLOWED_SPRUCE_LOG, false);
		addHollowableBlockData(context, Blocks.DARK_OAK_LOG, WWBlocks.HOLLOWED_DARK_OAK_LOG, false);
		addHollowableBlockData(context, Blocks.JUNGLE_LOG, WWBlocks.HOLLOWED_JUNGLE_LOG, false);
		addHollowableBlockData(context, Blocks.ACACIA_LOG, WWBlocks.HOLLOWED_ACACIA_LOG, false);
		addHollowableBlockData(context, Blocks.MANGROVE_LOG, WWBlocks.HOLLOWED_MANGROVE_LOG, false);
		addHollowableBlockData(context, Blocks.PALE_OAK_LOG, WWBlocks.HOLLOWED_PALE_OAK_LOG, false);
		addHollowableBlockData(context, Blocks.POPLAR_LOG, WWBlocks.HOLLOWED_POPLAR_LOG, false);
		addHollowableBlockData(context, Blocks.CRIMSON_STEM, WWBlocks.HOLLOWED_CRIMSON_STEM, true);
		addHollowableBlockData(context, Blocks.WARPED_STEM, WWBlocks.HOLLOWED_WARPED_STEM, true);
		addHollowableBlockData(context, WWBlocks.BAOBAB_LOG, WWBlocks.HOLLOWED_BAOBAB_LOG, false);
		addHollowableBlockData(context, WWBlocks.WILLOW_LOG, WWBlocks.HOLLOWED_WILLOW_LOG, false);
		addHollowableBlockData(context, WWBlocks.CYPRESS_LOG, WWBlocks.HOLLOWED_CYPRESS_LOG, false);
		addHollowableBlockData(context, WWBlocks.PALM_LOG, WWBlocks.HOLLOWED_PALM_LOG, false);
		addHollowableBlockData(context, WWBlocks.MAPLE_LOG, WWBlocks.HOLLOWED_MAPLE_LOG, false);
		// STRIPPED HOLLOWED
		addHollowableBlockData(context, Blocks.STRIPPED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG, false);
		addHollowableBlockData(context, Blocks.STRIPPED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, false);
		addHollowableBlockData(context, Blocks.STRIPPED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, false);
		addHollowableBlockData(context, Blocks.STRIPPED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, false);
		addHollowableBlockData(context, Blocks.STRIPPED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, false);
		addHollowableBlockData(context, Blocks.STRIPPED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, false);
		addHollowableBlockData(context, Blocks.STRIPPED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, false);
		addHollowableBlockData(context, Blocks.STRIPPED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, false);
		addHollowableBlockData(context, Blocks.STRIPPED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG, false);
		addHollowableBlockData(context, Blocks.STRIPPED_POPLAR_LOG, WWBlocks.STRIPPED_HOLLOWED_POPLAR_LOG, false);
		addHollowableBlockData(context, Blocks.STRIPPED_CRIMSON_STEM, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, true);
		addHollowableBlockData(context, Blocks.STRIPPED_WARPED_STEM, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM, true);
		addHollowableBlockData(context, WWBlocks.STRIPPED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, false);
		addHollowableBlockData(context, WWBlocks.STRIPPED_WILLOW_LOG, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG, false);
		addHollowableBlockData(context, WWBlocks.STRIPPED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, false);
		addHollowableBlockData(context, WWBlocks.STRIPPED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG, false);
		addHollowableBlockData(context, WWBlocks.STRIPPED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG, false);
	}

	public static void addHollowableBlockData(BlockTransformerMappingsApi.Context context, Block fromBlock, Block toBlock, boolean isStem) {
		Arrays.stream(Direction.Axis.values())
			.map(axis -> getHollowableBlockDataForAxis(fromBlock, toBlock, isStem, axis))
			.forEach(context::addFirst);;
	}

	private static BlockTransformer.BlockTransformData getHollowableBlockDataForAxis(Block fromBlock, Block toBlock, boolean isStem, Direction.Axis axis) {
		return BlockTransformer.BlockTransformData.builder(
				BlockPredicate.allOf(LOG_HOLLOWING_ENABLED, BlockPredicate.matchesBlocks(fromBlock), HasMatchingAxisPredicate.of(axis)),
				new CopyPropertiesProvider(toBlock)
			)
			.disallowedFaces(Arrays.stream(Direction.values()).filter(direction -> direction.getAxis() != axis).toList())
			// TODO: figure out a way to get hollowing particles in here
			.particle(BlockTransformer.TransformParticle.NONE)
			.sound(isStem ? WWSounds.STEM_HOLLOWED_AXE : WWSounds.LOG_HOLLOWED_AXE)
			.build();
	}
}
