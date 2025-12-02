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

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class SmallBushFoliagePlacer extends BushFoliagePlacer {
	public static final MapCodec<SmallBushFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		instance -> blobParts(instance).apply(instance, SmallBushFoliagePlacer::new)
	);

	public SmallBushFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
		super(radius, offset, height);
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return WWFeatures.SMALL_BUSH_FOLIAGE_PLACER;
	}

	@Override
	protected void createFoliage(
		LevelSimulatedReader level,
		FoliagePlacer.FoliageSetter placer,
		RandomSource random,
		TreeConfiguration config,
		int i,
		FoliagePlacer.FoliageAttachment foliageAttachment,
		int j,
		int k,
		int l
	) {
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final BlockPos logPos = foliageAttachment.pos().below();
		for (Direction direction : Direction.values()) {
			if (direction == Direction.DOWN) continue;
			mutable.setWithOffset(logPos, direction);
			tryPlaceLeaf(level, placer, random, config, mutable);
		}

		super.createFoliage(level, placer, random, config, i, foliageAttachment, j, k, l);
	}
}

