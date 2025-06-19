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
import org.jetbrains.annotations.NotNull;

public class SmallBushFoliagePlacer extends BushFoliagePlacer {
	public static final MapCodec<SmallBushFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		instance -> blobParts(instance).apply(instance, SmallBushFoliagePlacer::new)
	);

	public SmallBushFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
		super(radius, offset, height);
	}

	@Override
	protected @NotNull FoliagePlacerType<?> type() {
		return WWFeatures.SMALL_BUSH_FOLIAGE_PLACER;
	}

	@Override
	protected void createFoliage(
		LevelSimulatedReader levelSimulatedReader,
		FoliagePlacer.FoliageSetter foliageSetter,
		RandomSource randomSource,
		TreeConfiguration treeConfiguration,
		int i,
		FoliagePlacer.@NotNull FoliageAttachment foliageAttachment,
		int j,
		int k,
		int l
	) {
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		BlockPos logPos = foliageAttachment.pos().below();
		for (Direction direction : Direction.values()) {
			if (direction == Direction.DOWN) continue;
			mutableBlockPos.setWithOffset(logPos, direction);
			tryPlaceLeaf(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, mutableBlockPos);
		}

		super.createFoliage(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, i, foliageAttachment, j, k, l);
	}
}

