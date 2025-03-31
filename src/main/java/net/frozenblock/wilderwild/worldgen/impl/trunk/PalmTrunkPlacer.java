/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.worldgen.impl.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class PalmTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<PalmTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
		instance -> trunkPlacerParts(instance).apply(instance, PalmTrunkPlacer::new)
	);

	public PalmTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return WWFeatures.PALM_TRUNK_PLACER;
	}

	@Override
	@NotNull
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> blockSetter,
		@NotNull RandomSource random,
		int freeTreeHeight,
		@NotNull BlockPos pos,
		@NotNull TreeConfiguration config
	) {
		PalmTrunkPlacer.setDirtAt(level, blockSetter, random, pos.below(), config);
		ArrayList<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
		Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		Vector3f offset = direction.step();
		int i = freeTreeHeight - random.nextInt(4) - 1;
		int j = 4 - random.nextInt(3);
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		double x = pos.getX();
		double z = pos.getZ();
		OptionalInt optionalInt = OptionalInt.empty();
		for (int m = 0; m < freeTreeHeight; ++m) {
			int n = pos.getY() + m;
			if (m >= i && j > 0) {
				x += offset.x();
				z += offset.z();
				--j;
			}
			if (!this.placeLog(level, blockSetter, random, mutableBlockPos.set(x, n, z), config)) continue;
			optionalInt = OptionalInt.of(n + 1);
		}
		if (optionalInt.isPresent()) {
			list.add(new FoliagePlacer.FoliageAttachment(BlockPos.containing(x, optionalInt.getAsInt(), z), 1, false));
		}
		return list;
	}
}

