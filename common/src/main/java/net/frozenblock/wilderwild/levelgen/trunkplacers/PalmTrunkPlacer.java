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

package net.frozenblock.wilderwild.levelgen.trunkplacers;

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
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.joml.Vector3f;

public class PalmTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<PalmTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
		instance -> trunkPlacerParts(instance).apply(instance, PalmTrunkPlacer::new)
	);

	public PalmTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
		super(baseHeight, heightRandA, heightRandB);
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return WWFeatures.PALM_TRUNK_PLACER.get();
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(
		WorldGenLevel level,
		BiConsumer<BlockPos, BlockState> trunkSetter,
		RandomSource random,
		int treeHeight,
		BlockPos origin,
		TreeFeature tree
	) {
		placeBelowTrunkBlock(level, trunkSetter, random, origin.below(), tree);

		final ArrayList<FoliagePlacer.FoliageAttachment> attachments = Lists.newArrayList();
		final Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		final Vector3f offset = direction.step();
		final int leanHeight = treeHeight - random.nextInt(4) - 1;
		int leanSteps = 4 - random.nextInt(3);
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		double x = origin.getX();
		double z = origin.getZ();
		OptionalInt foliageHeight = OptionalInt.empty();
		for (int y = 0; y < treeHeight; ++y) {
			int dy = origin.getY() + y;
			if (y >= leanHeight && leanSteps > 0) {
				x += offset.x();
				z += offset.z();
				--leanSteps;
			}
			if (!this.placeLog(level, trunkSetter, random, mutable.set(x, dy, z), tree)) continue;
			foliageHeight = OptionalInt.of(dy + 1);
		}
		if (foliageHeight.isPresent()) {
			attachments.add(new FoliagePlacer.FoliageAttachment(BlockPos.containing(x, foliageHeight.getAsInt(), z), 1, false));
		}
		return attachments;
	}
}

