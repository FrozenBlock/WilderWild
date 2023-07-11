/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.world.generation.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.RegisterFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class LargeSnappedTrunkPlacer extends TrunkPlacer {
	public static final Codec<LargeSnappedTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> trunkPlacerParts(instance).apply(instance, LargeSnappedTrunkPlacer::new));

	public LargeSnappedTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return RegisterFeatures.LARGE_SNAPPED_TRUNK_PLACER;
	}

	@Override
	@NotNull
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> replacer, @NotNull RandomSource random, int height, @NotNull BlockPos startPos, @NotNull TreeConfiguration config) {
		setDirtAt(level, replacer, random, startPos.below(), config);
		setDirtAt(level, replacer, random, startPos.below().east(), config);
		setDirtAt(level, replacer, random, startPos.below().south(), config);
		setDirtAt(level, replacer, random, startPos.below().south().east(), config);
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (int i = 0; i < height; ++i) {
			this.placeLog(level, replacer, random, mutable, config, startPos, 0, i, 0);
			if (i < height - 1) {
				this.placeLog(level, replacer, random, mutable, config, startPos, 1, i, 0);
				this.placeLog(level, replacer, random, mutable, config, startPos, 1, i, 1);
				this.placeLog(level, replacer, random, mutable, config, startPos, 0, i, 1);
			}
		}
		return Lists.newArrayList();
	}
	private void placeLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config, BlockPos offsetPos, int offsetX, int offsetY, int offsetZ) {
		pos.setWithOffset(offsetPos, offsetX, offsetY, offsetZ);
		this.placeLogIfFree(level, blockSetter, random, pos, config);
	}

}
