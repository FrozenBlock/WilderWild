/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.worldgen.impl.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

public class MapleFoliagePlacer extends BlobFoliagePlacer {
	public static final MapCodec<MapleFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		blobParts(instance).and(
			instance.group(
				Codec.floatRange(0F, 1F).fieldOf("hanging_leaves_chance").forGetter(foliagePlacer -> foliagePlacer.hangingLeavesChance),
				Codec.floatRange(0F, 1F).fieldOf("hanging_leaves_extension_chance").forGetter(foliagePlacer -> foliagePlacer.hangingLeavesExtensionChance),
				FoliagePlacer.CODEC.fieldOf("alt_foliage_placer").forGetter(placer -> placer.altFoliagePlacer)
			)
		).apply(instance, MapleFoliagePlacer::new)
	);
	private final float hangingLeavesChance;
	private final float hangingLeavesExtensionChance;
	private final FoliagePlacer altFoliagePlacer;

	public MapleFoliagePlacer(
		IntProvider radius,
		IntProvider offset,
		int height,
		float hangingLeavesChance,
		float hangingLeavesExtensionChance,
		FoliagePlacer altFoliagePlacer
	) {
		super(radius, offset, height);
		this.hangingLeavesChance = hangingLeavesChance;
		this.hangingLeavesExtensionChance = hangingLeavesExtensionChance;
		this.altFoliagePlacer = altFoliagePlacer;
	}

	@Override
	protected @NotNull FoliagePlacerType<?> type() {
		return WWFeatures.MAPLE_FOLIAGE_PLACER;
	}

	@Override
	public void createFoliage(
		LevelSimulatedReader level,
		FoliageSetter setter,
		RandomSource random,
		TreeConfiguration config,
		int trunkHeight,
		FoliageAttachment foliageAttachment,
		int foliageHeight,
		int radius,
		int offset
	) {
		if (!WWWorldgenConfig.NEW_MAPLES) {
			this.altFoliagePlacer.createFoliage(
				level,
				setter,
				random,
				config,
				trunkHeight,
				foliageAttachment,
				this.altFoliagePlacer.foliageHeight(random, trunkHeight, config),
				this.altFoliagePlacer.foliageRadius(random, trunkHeight)
			);
			return;
		}

		for (int relativeY = offset; relativeY >= offset - foliageHeight; relativeY--) {
			final int newRadius = Math.max(radius + foliageAttachment.radiusOffset() - 1 - relativeY / 2, 0);
			if (relativeY <= offset - foliageHeight) {
				this.placeLeavesRowWithHangingLeavesBelow(
					level,
					setter,
					random,
					config,
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
					setter,
					random,
					config,
					foliageAttachment.pos().above(2),
					newRadius,
					relativeY,
					foliageAttachment.doubleTrunk()
				);
			}
		}
	}
}

