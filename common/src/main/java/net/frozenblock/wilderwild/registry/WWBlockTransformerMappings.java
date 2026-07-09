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
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
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

// TODO: NeoForge
public final class WWBlockTransformerMappings implements BlockTransformerMappingsApi.ModifyAxeBlockTransformer {
	private static final BlockPredicate LOG_HOLLOWING_ENABLED = ConfigPredicate.equalTo(WWBlockConfig.LOG_HOLLOWING, true).asBlockPredicate();

	@Override
	public void modifyAxeBlockTransformer(BlockTransformerMappingsApi.Context context) {
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.BAOBAB_LOG.get(), WWBlocks.STRIPPED_BAOBAB_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.BAOBAB_WOOD.get(), WWBlocks.STRIPPED_BAOBAB_WOOD.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.WILLOW_LOG.get(), WWBlocks.STRIPPED_WILLOW_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.WILLOW_WOOD.get(), WWBlocks.STRIPPED_WILLOW_WOOD.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.CYPRESS_LOG.get(), WWBlocks.STRIPPED_CYPRESS_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.CYPRESS_WOOD.get(), WWBlocks.STRIPPED_CYPRESS_WOOD.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.PALM_LOG.get(), WWBlocks.STRIPPED_PALM_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.PALM_WOOD.get(), WWBlocks.STRIPPED_PALM_WOOD.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.MAPLE_LOG.get(), WWBlocks.STRIPPED_MAPLE_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.MAPLE_WOOD.get(), WWBlocks.STRIPPED_MAPLE_WOOD.get()));

		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_ACACIA_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_BIRCH_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_CHERRY_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_DARK_OAK_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_OAK_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_OAK_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_SPRUCE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_JUNGLE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_MANGROVE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_PALE_OAK_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_POPLAR_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_POPLAR_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_CRIMSON_STEM.get(), WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_WARPED_STEM.get(), WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_WILLOW_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_CYPRESS_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_BAOBAB_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_PALM_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_PALM_LOG.get()));
		context.addFirst(BlockTransformerMappings.getStrippableBlockData(WWBlocks.HOLLOWED_MAPLE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG.get()));

		// HOLLOWED
		addHollowableBlockData(context, Blocks.OAK_LOG, WWBlocks.HOLLOWED_OAK_LOG.get(), false);
		addHollowableBlockData(context, Blocks.BIRCH_LOG, WWBlocks.HOLLOWED_BIRCH_LOG.get(), false);
		addHollowableBlockData(context, Blocks.CHERRY_LOG, WWBlocks.HOLLOWED_CHERRY_LOG.get(), false);
		addHollowableBlockData(context, Blocks.SPRUCE_LOG, WWBlocks.HOLLOWED_SPRUCE_LOG.get(), false);
		addHollowableBlockData(context, Blocks.DARK_OAK_LOG, WWBlocks.HOLLOWED_DARK_OAK_LOG.get(), false);
		addHollowableBlockData(context, Blocks.JUNGLE_LOG, WWBlocks.HOLLOWED_JUNGLE_LOG.get(), false);
		addHollowableBlockData(context, Blocks.ACACIA_LOG, WWBlocks.HOLLOWED_ACACIA_LOG.get(), false);
		addHollowableBlockData(context, Blocks.MANGROVE_LOG, WWBlocks.HOLLOWED_MANGROVE_LOG.get(), false);
		addHollowableBlockData(context, Blocks.PALE_OAK_LOG, WWBlocks.HOLLOWED_PALE_OAK_LOG.get(), false);
		addHollowableBlockData(context, Blocks.POPLAR_LOG, WWBlocks.HOLLOWED_POPLAR_LOG.get(), false);
		addHollowableBlockData(context, Blocks.CRIMSON_STEM, WWBlocks.HOLLOWED_CRIMSON_STEM.get(), true);
		addHollowableBlockData(context, Blocks.WARPED_STEM, WWBlocks.HOLLOWED_WARPED_STEM.get(), true);
		addHollowableBlockData(context, WWBlocks.BAOBAB_LOG.get(), WWBlocks.HOLLOWED_BAOBAB_LOG.get(), false);
		addHollowableBlockData(context, WWBlocks.WILLOW_LOG.get(), WWBlocks.HOLLOWED_WILLOW_LOG.get(), false);
		addHollowableBlockData(context, WWBlocks.CYPRESS_LOG.get(), WWBlocks.HOLLOWED_CYPRESS_LOG.get(), false);
		addHollowableBlockData(context, WWBlocks.PALM_LOG.get(), WWBlocks.HOLLOWED_PALM_LOG.get(), false);
		addHollowableBlockData(context, WWBlocks.MAPLE_LOG.get(), WWBlocks.HOLLOWED_MAPLE_LOG.get(), false);
		// STRIPPED HOLLOWED
		addHollowableBlockData(context, Blocks.STRIPPED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG.get(), false);
		addHollowableBlockData(context, Blocks.STRIPPED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.get(), false);
		addHollowableBlockData(context, Blocks.STRIPPED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.get(), false);
		addHollowableBlockData(context, Blocks.STRIPPED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.get(), false);
		addHollowableBlockData(context, Blocks.STRIPPED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.get(), false);
		addHollowableBlockData(context, Blocks.STRIPPED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.get(), false);
		addHollowableBlockData(context, Blocks.STRIPPED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.get(), false);
		addHollowableBlockData(context, Blocks.STRIPPED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.get(), false);
		addHollowableBlockData(context, Blocks.STRIPPED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG.get(), false);
		addHollowableBlockData(context, Blocks.STRIPPED_POPLAR_LOG, WWBlocks.STRIPPED_HOLLOWED_POPLAR_LOG.get(), false);
		addHollowableBlockData(context, Blocks.STRIPPED_CRIMSON_STEM, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM.get(), true);
		addHollowableBlockData(context, Blocks.STRIPPED_WARPED_STEM, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM.get(), true);
		addHollowableBlockData(context, WWBlocks.STRIPPED_BAOBAB_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG.get(), false);
		addHollowableBlockData(context, WWBlocks.STRIPPED_WILLOW_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG.get(), false);
		addHollowableBlockData(context, WWBlocks.STRIPPED_CYPRESS_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG.get(), false);
		addHollowableBlockData(context, WWBlocks.STRIPPED_PALM_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_PALM_LOG.get(), false);
		addHollowableBlockData(context, WWBlocks.STRIPPED_MAPLE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG.get(), false);
	}

	public static void addHollowableBlockData(BlockTransformerMappingsApi.Context context, Block fromBlock, Block toBlock, boolean isStem) {
		Arrays.stream(Direction.Axis.values())
			.map(axis -> getHollowableBlockDataForAxis(fromBlock, toBlock, isStem, axis))
			.forEach(context::addFirst);
	}

	private static BlockTransformer.BlockTransformData getHollowableBlockDataForAxis(Block fromBlock, Block toBlock, boolean isStem, Direction.Axis axis) {
		return BlockTransformer.BlockTransformData.builder(
				BlockPredicate.allOf(LOG_HOLLOWING_ENABLED, BlockPredicate.matchesBlocks(fromBlock), HasMatchingAxisPredicate.of(axis)),
				new CopyPropertiesProvider(toBlock)
			)
			.disallowedFaces(Arrays.stream(Direction.values()).filter(direction -> direction.getAxis() != axis).toList())
			// TODO: figure out a way to get hollowing particles in here
			.particle(BlockTransformer.TransformParticle.NONE)
			.sound(isStem ? WWSounds.STEM_HOLLOWED_AXE.asHolder() : WWSounds.LOG_HOLLOWED_AXE.asHolder())
			.build();
	}
}
