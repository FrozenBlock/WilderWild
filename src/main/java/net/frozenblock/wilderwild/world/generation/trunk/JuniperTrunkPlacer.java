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
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class JuniperTrunkPlacer extends CherryTrunkPlacer {
	/**
	 * The juniper tree just extends a CherryTrunkPlacer and modify the method placeTrunk. Keep that in mind when updating to 1.20*/
	public static final Codec<JuniperTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
			juniperCodec(instance).apply(instance, JuniperTrunkPlacer::new));

	protected static <P extends JuniperTrunkPlacer> Products.P7<RecordCodecBuilder.Mu<P>, Integer, Integer, Integer, IntProvider, IntProvider, UniformInt, IntProvider> juniperCodec(RecordCodecBuilder.Instance<P> builder) {
		return trunkPlacerParts(builder)
				.and((IntProvider.codec(1, 3).fieldOf("branch_count")).forGetter(placer -> placer.branchCount))
				.and((IntProvider.codec(2, 16).fieldOf("branch_horizontal_length")).forGetter(placer -> placer.branchHorizontalLength))
				.and((UniformInt.CODEC.fieldOf("branch_start_offset_from_top")).forGetter(placer -> placer.branchStartOffsetFromTop))
				.and((IntProvider.codec(-16, 16).fieldOf("branch_end_offset_from_top")).forGetter(placer -> placer.branchEndOffsetFromTop));
	}

	public JuniperTrunkPlacer(int i, int j, int k, IntProvider intProvider, IntProvider intProvider2, UniformInt uniformInt, IntProvider intProvider3) {
		super(i, j, k, intProvider, intProvider2, uniformInt, intProvider3);
	}


	@Override
	protected TrunkPlacerType<?> type() {
		return WilderWild.JUNIPER_TRUNK_PLACER;
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> blockSetter, @NotNull RandomSource random, int freeTreeHeight, BlockPos pos, @NotNull TreeConfiguration config) {
		CherryTrunkPlacer.setDirtAt(level, blockSetter, random, pos.below(), config);
		int i = Math.max(0, freeTreeHeight - 1 + this.branchStartOffsetFromTop.sample(random));
		int j = Math.max(0, freeTreeHeight - 1 + this.secondBranchStartOffsetFromTop.sample(random));
		if (j >= i) {
			++j;
		}
		int branchCount = this.branchCount.sample(random);
		boolean isThreeBranches = branchCount == 3;
		boolean moreThanOneBranch = branchCount >= 2;
		int l = isThreeBranches ? freeTreeHeight : (moreThanOneBranch ? Math.max(i, j) + 1 : i + 1);
		for (int m = 0; m < l; ++m) {
			this.placeLog(level, blockSetter, random, pos.above(m), config);
		}
		ArrayList<FoliagePlacer.FoliageAttachment> list = new ArrayList<>();
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		Function<BlockState, BlockState> function = state -> (BlockState)state.setValue(RotatedPillarBlock.AXIS, direction.getAxis());
		list.add(this.generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, function, direction, i, i < l - 1, mutableBlockPos));
		for(Direction dir : Direction.values()) {
			//Math.random() > 0.3f
			if(dir != direction && Math.random() > 0.1f) {
				function = state -> (BlockState)state.setValue(RotatedPillarBlock.AXIS, dir.getAxis());
				list.add(this.generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, function, dir, i, i < l - 1, mutableBlockPos));
			}
		}
        /*if (moreThanOneBranch) {
            list.add(this.generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, function, direction.getOpposite(), j, j < l - 1, mutableBlockPos));
        }*/
		return list;
	}

}
