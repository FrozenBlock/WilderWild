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

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.item.api.component.BlockTransformerMappingsApi;
import net.frozenblock.lib.levelgen.blockpredicates.HasMatchingAxisPredicate;
import net.frozenblock.lib.levelgen.feature.api.stateproviders.StrictRuleBasedStateProvider;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.CopyPropertiesProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;

// TODO: NeoForge
public final class WWBlockTransformerMappings implements BlockTransformerMappingsApi.ModifyAxeBlockTransformer, BlockTransformerMappingsApi.ModifyAxeStrippablesBuilder {
	private static final BlockPredicate LOG_HOLLOWING_ENABLED = ConfigPredicate.equalTo(WWBlockConfig.LOG_HOLLOWING, true).asBlockPredicate();
	private static final Function<Direction.Axis, BlockTransformer.BlockTransformData> AXE_HOLLOWABLES = axis -> BlockTransformer.BlockTransformData
		.builder(fillWithHollowables(axis).build())
		.disallowedFaces(Arrays.stream(Direction.values()).filter(direction -> direction.getAxis() != axis).toList())
		.sound(WWSounds.AXE_HOLLOW.asHolder())
		.build();

	@Override
	public void modifyAxeStrippablesBuilder(RuleBasedStateProvider.Builder builder, BiFunction<Block, Block, RuleBasedStateProvider.Builder> addStrippable) {
		addStrippable.apply(WWBlocks.BAOBAB_LOG.get(), WWBlocks.STRIPPED_BAOBAB_LOG.get());
		addStrippable.apply(WWBlocks.BAOBAB_WOOD.get(), WWBlocks.STRIPPED_BAOBAB_WOOD.get());
		addStrippable.apply(WWBlocks.WILLOW_LOG.get(), WWBlocks.STRIPPED_WILLOW_LOG.get());
		addStrippable.apply(WWBlocks.WILLOW_WOOD.get(), WWBlocks.STRIPPED_WILLOW_WOOD.get());
		addStrippable.apply(WWBlocks.CYPRESS_LOG.get(), WWBlocks.STRIPPED_CYPRESS_LOG.get());
		addStrippable.apply(WWBlocks.CYPRESS_WOOD.get(), WWBlocks.STRIPPED_CYPRESS_WOOD.get());
		addStrippable.apply(WWBlocks.PALM_LOG.get(), WWBlocks.STRIPPED_PALM_LOG.get());
		addStrippable.apply(WWBlocks.PALM_WOOD.get(), WWBlocks.STRIPPED_PALM_WOOD.get());
		addStrippable.apply(WWBlocks.MAPLE_LOG.get(), WWBlocks.STRIPPED_MAPLE_LOG.get());
		addStrippable.apply(WWBlocks.MAPLE_WOOD.get(), WWBlocks.STRIPPED_MAPLE_WOOD.get());

		addStrippable.apply(WWBlocks.HOLLOWED_ACACIA_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_BIRCH_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_CHERRY_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_DARK_OAK_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_OAK_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_OAK_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_SPRUCE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_JUNGLE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_MANGROVE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_PALE_OAK_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_POPLAR_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_POPLAR_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_CRIMSON_STEM.get(), WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM.get());
		addStrippable.apply(WWBlocks.HOLLOWED_WARPED_STEM.get(), WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM.get());
		addStrippable.apply(WWBlocks.HOLLOWED_WILLOW_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_CYPRESS_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_BAOBAB_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_PALM_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_PALM_LOG.get());
		addStrippable.apply(WWBlocks.HOLLOWED_MAPLE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG.get());
	}

	@Override
	public void modifyAxeBlockTransformer(BlockTransformerMappingsApi.Context context) {
		context.addFirst(AXE_HOLLOWABLES.apply(Direction.Axis.X));
		context.addFirst(AXE_HOLLOWABLES.apply(Direction.Axis.Y));
		context.addFirst(AXE_HOLLOWABLES.apply(Direction.Axis.Z));
	}

	public static StrictRuleBasedStateProvider.Builder fillWithHollowables(Direction.Axis axis) {
		final RuleBasedStateProvider.Builder nestedBuilder = RuleBasedStateProvider.builder();
		final BiConsumer<Block, Block> addHollowable = (fromBlock, toBlock) -> nestedBuilder.ifTrueThenProvide(
			BlockPredicate.matchesBlocks(fromBlock),
			new CopyPropertiesProvider(toBlock)
		);

		// HOLLOWED
		addHollowable.accept(Blocks.OAK_LOG, WWBlocks.HOLLOWED_OAK_LOG.get());
		addHollowable.accept(Blocks.BIRCH_LOG, WWBlocks.HOLLOWED_BIRCH_LOG.get());
		addHollowable.accept(Blocks.CHERRY_LOG, WWBlocks.HOLLOWED_CHERRY_LOG.get());
		addHollowable.accept(Blocks.SPRUCE_LOG, WWBlocks.HOLLOWED_SPRUCE_LOG.get());
		addHollowable.accept(Blocks.DARK_OAK_LOG, WWBlocks.HOLLOWED_DARK_OAK_LOG.get());
		addHollowable.accept(Blocks.JUNGLE_LOG, WWBlocks.HOLLOWED_JUNGLE_LOG.get());
		addHollowable.accept(Blocks.ACACIA_LOG, WWBlocks.HOLLOWED_ACACIA_LOG.get());
		addHollowable.accept(Blocks.MANGROVE_LOG, WWBlocks.HOLLOWED_MANGROVE_LOG.get());
		addHollowable.accept(Blocks.PALE_OAK_LOG, WWBlocks.HOLLOWED_PALE_OAK_LOG.get());
		addHollowable.accept(Blocks.POPLAR_LOG, WWBlocks.HOLLOWED_POPLAR_LOG.get());
		addHollowable.accept(Blocks.CRIMSON_STEM, WWBlocks.HOLLOWED_CRIMSON_STEM.get());
		addHollowable.accept(Blocks.WARPED_STEM, WWBlocks.HOLLOWED_WARPED_STEM.get());
		addHollowable.accept(WWBlocks.BAOBAB_LOG.get(), WWBlocks.HOLLOWED_BAOBAB_LOG.get());
		addHollowable.accept(WWBlocks.WILLOW_LOG.get(), WWBlocks.HOLLOWED_WILLOW_LOG.get());
		addHollowable.accept(WWBlocks.CYPRESS_LOG.get(), WWBlocks.HOLLOWED_CYPRESS_LOG.get());
		addHollowable.accept(WWBlocks.PALM_LOG.get(), WWBlocks.HOLLOWED_PALM_LOG.get());
		addHollowable.accept(WWBlocks.MAPLE_LOG.get(), WWBlocks.HOLLOWED_MAPLE_LOG.get());
		// STRIPPED HOLLOWED
		addHollowable.accept(Blocks.STRIPPED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG.get());
		addHollowable.accept(Blocks.STRIPPED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.get());
		addHollowable.accept(Blocks.STRIPPED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.get());
		addHollowable.accept(Blocks.STRIPPED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.get());
		addHollowable.accept(Blocks.STRIPPED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.get());
		addHollowable.accept(Blocks.STRIPPED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.get());
		addHollowable.accept(Blocks.STRIPPED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.get());
		addHollowable.accept(Blocks.STRIPPED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.get());
		addHollowable.accept(Blocks.STRIPPED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG.get());
		addHollowable.accept(Blocks.STRIPPED_POPLAR_LOG, WWBlocks.STRIPPED_HOLLOWED_POPLAR_LOG.get());
		addHollowable.accept(Blocks.STRIPPED_CRIMSON_STEM, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM.get());
		addHollowable.accept(Blocks.STRIPPED_WARPED_STEM, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM.get());
		addHollowable.accept(WWBlocks.STRIPPED_BAOBAB_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG.get());
		addHollowable.accept(WWBlocks.STRIPPED_WILLOW_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG.get());
		addHollowable.accept(WWBlocks.STRIPPED_CYPRESS_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG.get());
		addHollowable.accept(WWBlocks.STRIPPED_PALM_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_PALM_LOG.get());
		addHollowable.accept(WWBlocks.STRIPPED_MAPLE_LOG.get(), WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG.get());

		return StrictRuleBasedStateProvider.builder().ifTrueThenProvide(
			BlockPredicate.allOf(LOG_HOLLOWING_ENABLED, HasMatchingAxisPredicate.of(axis)),
			nestedBuilder.build()
		);
	}
}
