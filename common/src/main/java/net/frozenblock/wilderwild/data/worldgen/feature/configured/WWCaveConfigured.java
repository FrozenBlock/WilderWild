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

package net.frozenblock.wilderwild.data.worldgen.feature.configured;

import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.ColumnFeature;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils.register;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWFeatureBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.NoOpFeature;
import net.minecraft.world.level.levelgen.feature.SpeleothemClusterFeature;
import net.minecraft.world.level.levelgen.feature.SpeleothemUtils;
import net.minecraft.world.level.levelgen.feature.VegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public final class WWCaveConfigured {
	// ICE CAVES
	public static final FrozenLibFeature DECORATIVE_ICICLE_CLUSTER = register("decorative_icicle_cluster");
	public static final FrozenLibFeature FRAGILE_ICE_PATCH_CEILING = WWFeatureUtils.register("fragile_ice_patch_ceiling");
	public static final FrozenLibFeature FRAGILE_ICE_PATCH = WWFeatureUtils.register("fragile_ice_patch");

	public static void registerCaveConfigured(BootstrapContext<Feature> entries) {
		WWConstants.logWithModId("Registering WWCaveConfigured for", true);
		final HolderGetter<Block> blocks = entries.lookup(Registries.BLOCK);

		// ICE CAVES
		DECORATIVE_ICICLE_CLUSTER.makeAndSetHolder(
			new SpeleothemClusterFeature(
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.ICICLE.defaultBlockState(),
				HolderSet.empty(),
				12,
				BiasedToBottomInt.of(1, 2),
				ConstantInt.of(3),
				1,
				2,
				BiasedToBottomInt.of(1, 2),
				UniformFloat.of(0.1F, 0.25F),
				ConstantFloat.ZERO,
				0.1F,
				2,
				5,
				new SpeleothemClusterFeature.PlacementOptions(
					SpeleothemClusterFeature.PlacementMode.FLOOR_AND_CEILING, SpeleothemUtils.BaseBlockTransformer.SET_ATTACHED, false
				)
			)
		);

		FRAGILE_ICE_PATCH_CEILING.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(WWFeatureBlockTags.CAVE_FRAGILE_ICE_REPLACEABLE),
				BlockStateProvider.holderOf(WWBlocks.FRAGILE_ICE.get()),
				PlacementUtils.inlinePlaced(
					new ColumnFeature(
						BlockStateProvider.holderOf(WWBlocks.FRAGILE_ICE.get()),
						BlockPredicate.replaceable(),
						UniformInt.of(0, 4),
						Direction.DOWN,
						true
					)
				),
				CaveSurface.CEILING,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.035F,
				UniformInt.of(4, 10),
				0.6F
			)
		);

		FRAGILE_ICE_PATCH.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(WWFeatureBlockTags.CAVE_FRAGILE_ICE_REPLACEABLE),
				BlockStateProvider.holderOf(WWBlocks.FRAGILE_ICE.get()),
				PlacementUtils.inlinePlaced(new NoOpFeature()),
				CaveSurface.FLOOR,
				UniformInt.of(2, 5),
				0.25F,
				4,
				0.035F,
				UniformInt.of(4, 10),
				0.4F
			)
		);
	}
}
