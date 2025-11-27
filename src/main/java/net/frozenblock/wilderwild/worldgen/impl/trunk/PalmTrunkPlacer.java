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
import org.joml.Vector3f;

public class PalmTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<PalmTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
		instance -> trunkPlacerParts(instance).apply(instance, PalmTrunkPlacer::new)
	);

	public PalmTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return WWFeatures.PALM_TRUNK_PLACER;
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> blockSetter,
		RandomSource random,
		int freeTreeHeight,
		BlockPos pos,
		TreeConfiguration config
	) {
		PalmTrunkPlacer.setDirtAt(level, blockSetter, random, pos.below(), config);

		final ArrayList<FoliagePlacer.FoliageAttachment> foliageAttachments = Lists.newArrayList();
		final Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		final Vector3f offset = direction.step();
		int i = freeTreeHeight - random.nextInt(4) - 1;
		int j = 4 - random.nextInt(3);
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
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
			if (!this.placeLog(level, blockSetter, random, mutable.set(x, n, z), config)) continue;
			optionalInt = OptionalInt.of(n + 1);
		}
		if (optionalInt.isPresent()) {
			foliageAttachments.add(new FoliagePlacer.FoliageAttachment(BlockPos.containing(x, optionalInt.getAsInt(), z), 1, false));
		}
		return foliageAttachments;
	}
}

