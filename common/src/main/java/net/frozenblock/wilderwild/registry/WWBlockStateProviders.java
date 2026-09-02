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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;

public final class WWBlockStateProviders {
	public static final ResourceKey<BlockStateProvider> BAOBAB_NUT = bind("baobab_nut");
	public static final ResourceKey<BlockStateProvider> COCONUT = bind("coconut");
	public static final ResourceKey<BlockStateProvider> SOIL_BENEATH_WATERLOGGABLE_TREE = bind("soil_beneath_waterloggable_tree");
	public static final ResourceKey<BlockStateProvider> SOIL_BENEATH_DESERT_TREE = bind("soil_beneath_desert_tree");

	public static void bootstrap(BootstrapContext<BlockStateProvider> context) {
		context.register(
			BAOBAB_NUT,
			new RandomizedIntStateProvider(
				BlockStateProvider.of(WWBlocks.BAOBAB_NUT.get().defaultBlockState().setValue(BlockStateProperties.HANGING, true)),
				BaobabNutBlock.AGE,
				UniformInt.of(0, 2)
			)
		);

		context.register(
			COCONUT,
			BlockStateProvider.of(WWBlocks.COCONUT.get().defaultBlockState().setValue(BlockStateProperties.HANGING, true))
		);

		context.register(
			SOIL_BENEATH_WATERLOGGABLE_TREE,
			RuleBasedStateProvider.ifTrueThenProvide(
				BlockPredicate.not(BlockPredicate.matchesTag(WWBlockTags.CANNOT_REPLACE_BELOW_TREE_TRUNK_WATERLOGGABLE)),
				Blocks.DIRT
			)
		);
		context.register(
			SOIL_BENEATH_DESERT_TREE,
			RuleBasedStateProvider.ifTrueThenProvide(
				BlockPredicate.not(BlockPredicate.matchesTag(WWBlockTags.CANNOT_REPLACE_BELOW_TREE_TRUNK_DESERT)),
				Blocks.DIRT
			)
		);
	}

	private static ResourceKey<BlockStateProvider> bind(String name) {
		return ResourceKey.create(Registries.BLOCK_STATE_PROVIDER, WWConstants.id(name));
	}

	private WWBlockStateProviders() {}
}
