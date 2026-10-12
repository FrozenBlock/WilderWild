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
import net.frozenblock.wilderwild.WWConstants;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils.register;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SpeleothemClusterFeature;
import net.minecraft.world.level.levelgen.feature.SpeleothemUtils;

public final class WWCaveConfigured {
	// ICE CAVES
	public static final FrozenLibFeature DECORATIVE_ICICLE_CLUSTER = register("decorative_icicle_cluster");

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
	}
}
