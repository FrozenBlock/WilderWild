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

package net.frozenblock.wilderwild.levelgen.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class MapleFoliagePlacer extends BlobFoliagePlacer {
	public static final MapCodec<MapleFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		blobParts(instance)
			.and(Codec.floatRange(0F, 1F).fieldOf("hanging_leaves_chance").forGetter(foliagePlacer -> foliagePlacer.hangingLeavesChance))
			.and(Codec.floatRange(0F, 1F).fieldOf("hanging_leaves_extension_chance").forGetter(foliagePlacer -> foliagePlacer.hangingLeavesExtensionChance))
			.apply(instance, MapleFoliagePlacer::new)
	);
	private final float hangingLeavesChance;
	private final float hangingLeavesExtensionChance;

	public MapleFoliagePlacer(
		IntProvider radius,
		IntProvider offset,
		int height,
		float hangingLeavesChance,
		float hangingLeavesExtensionChance
	) {
		super(radius, offset, height);
		this.hangingLeavesChance = hangingLeavesChance;
		this.hangingLeavesExtensionChance = hangingLeavesExtensionChance;
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return WWFeatures.MAPLE_FOLIAGE_PLACER.get();
	}

	@Override
	public void createFoliage(
		WorldGenLevel level,
		FoliageSetter foliageSetter,
		RandomSource random,
		TreeFeature tree,
		int treeHeight,
		FoliageAttachment foliageAttachment,
		int foliageHeight,
		int leafRadius,
		int offset
	) {
		for (int relativeY = offset; relativeY >= offset - foliageHeight; relativeY--) {
			final int newRadius = Math.max(leafRadius + foliageAttachment.radiusOffsetXZ() - 1 - relativeY / 2, 0);
			if (relativeY <= offset - foliageHeight) {
				this.placeLeavesRowWithHangingLeavesBelow(
					level,
					foliageSetter,
					random,
					tree,
					foliageAttachment.pos().above(2),
					newRadius,
					relativeY,
					foliageAttachment.doubleTrunk(),
					this.hangingLeavesChance,
					this.hangingLeavesExtensionChance
				);
			} else {
				this.placeLeavesRow(
					level,
					foliageSetter,
					random,
					tree,
					foliageAttachment.pos().above(2),
					newRadius,
					relativeY,
					foliageAttachment.doubleTrunk()
				);
			}
		}
	}
}

