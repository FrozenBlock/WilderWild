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

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.frozenblock.wilderwild.registry.WWFeatures;
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

public class JuniperTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<JuniperTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		trunkPlacerParts(instance)
			.and((IntProvider.codec(1, 3).fieldOf("branch_count")).forGetter(trunkPlacer -> trunkPlacer.branchCount))
			.and((IntProvider.codec(2, 16).fieldOf("branch_horizontal_length")).forGetter(trunkPlacer -> trunkPlacer.branchHorizontalLength))
			.and((UniformInt.CODEC.fieldOf("branch_start_offset_from_top")).forGetter(trunkPlacer -> trunkPlacer.branchStartOffsetFromTop))
			.and((IntProvider.codec(-16, 16).fieldOf("branch_end_offset_from_top")).forGetter(trunkPlacer -> trunkPlacer.branchEndOffsetFromTop))
			.apply(instance, JuniperTrunkPlacer::new)
	);
	public final IntProvider branchCount;
	public final IntProvider branchHorizontalLength;
	public final UniformInt branchStartOffsetFromTop;
	public final UniformInt secondBranchStartOffsetFromTop;
	public final IntProvider branchEndOffsetFromTop;

	public JuniperTrunkPlacer(
		int baseHeight,
		int firstRandomHeight,
		int secondRandomHeight,
		@NotNull IntProvider branchCount,
		@NotNull IntProvider branchHorizontalLength,
		@NotNull UniformInt branchStartOffsetFromTop,
		@NotNull IntProvider branchEndOffsetFromTop
	) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
		this.branchCount = branchCount;
		this.branchHorizontalLength = branchHorizontalLength;
		this.branchStartOffsetFromTop = branchStartOffsetFromTop;
		this.secondBranchStartOffsetFromTop = UniformInt.of(branchStartOffsetFromTop.getMinValue(), branchStartOffsetFromTop.getMaxValue() - 1);
		this.branchEndOffsetFromTop = branchEndOffsetFromTop;
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return WWFeatures.JUNIPER_TRUNK_PLACER;
	}

	@Override
	@NotNull
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> blockSetter, @NotNull RandomSource random, int freeTreeHeight, @NotNull BlockPos pos, @NotNull TreeConfiguration config) {
		JuniperTrunkPlacer.setDirtAt(level, blockSetter, random, pos.below(), config);
		int i = Math.max(0, freeTreeHeight - 1 + this.branchStartOffsetFromTop.sample(random));
		int j = Math.max(0, freeTreeHeight - 1 + this.secondBranchStartOffsetFromTop.sample(random));
		if (j >= i) {
			++j;
		}
		int branchCount = this.branchCount.sample(random);
		boolean isThreeBranches = branchCount == 3;
		boolean moreThanOneBranch = branchCount >= 2;
		int l = isThreeBranches ? freeTreeHeight : (moreThanOneBranch ? Math.max(i, j) + 1 : i + 1);
		for (int m = 0; m < l; ++m) this.placeLog(level, blockSetter, random, pos.above(m), config);

		ArrayList<FoliagePlacer.FoliageAttachment> list = new ArrayList<>();
		if (isThreeBranches) list.add(new FoliagePlacer.FoliageAttachment(pos.above(l), 0, false));

		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		Function<BlockState, BlockState> function = state -> (BlockState) state.setValue(RotatedPillarBlock.AXIS, direction.getAxis());
		list.add(this.generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, function, direction, i, i < l - 1, mutableBlockPos));
		ArrayList<Direction> allDirsMF = new ArrayList<>();

		for (Direction d : Direction.Plane.HORIZONTAL) if (d != direction) allDirsMF.add(d);

		Direction secondDir = allDirsMF.get((int) (Math.random() * 2));
		if (moreThanOneBranch) {
			function = state -> (BlockState) state.setValue(RotatedPillarBlock.AXIS, secondDir.getAxis());
			list.add(this.generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, function, secondDir, j, j < l - 1, mutableBlockPos));
		}
		return list;
	}

	@NotNull
	private FoliagePlacer.FoliageAttachment generateBranch(@NotNull LevelSimulatedReader world, @NotNull BiConsumer<BlockPos, BlockState> biConsumer, @NotNull RandomSource random, int i, @NotNull BlockPos pos, @NotNull TreeConfiguration treeConfiguration, @NotNull Function<BlockState, BlockState> function, @NotNull Direction direction, int j, boolean bl, @NotNull BlockPos.MutableBlockPos mutablePos) {
		int o;
		mutablePos.set(pos).move(Direction.UP, j);
		int k = i - 1 + this.branchEndOffsetFromTop.sample(random);
		boolean bl2 = bl || k < j;
		int l = this.branchHorizontalLength.sample(random) + (bl2 ? 1 : 0);
		BlockPos blockPos = pos.relative(direction, l).above(k);
		int m = bl2 ? 2 : 1;
		for (int n = 0; n < m; ++n) {
			this.placeLog(world, biConsumer, random, mutablePos.move(direction), treeConfiguration, function);
		}
		Direction direction2 = blockPos.getY() > mutablePos.getY() ? Direction.UP : Direction.DOWN;
		while ((o = mutablePos.distManhattan(blockPos)) != 0) {
			float f = (float) Math.abs(blockPos.getY() - mutablePos.getY()) / (float) o;
			boolean bl3 = random.nextFloat() < f;
			mutablePos.move(bl3 ? direction2 : direction);
			this.placeLog(world, biConsumer, random, mutablePos, treeConfiguration, bl3 ? Function.identity() : function);
		}
		return new FoliagePlacer.FoliageAttachment(blockPos.above(), 0, false);
	}
}

