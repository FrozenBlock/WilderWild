/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.world.generation.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashSet;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LeavesAroundTopTreeDecorator extends TreeDecorator {
	public static final Codec<LeavesAroundTopTreeDecorator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		Codec.FLOAT.fieldOf("probability").forGetter((treeDecorator) -> treeDecorator.probability),
		Codec.intRange(0, 16).fieldOf("exclusion_radius_xz").forGetter((treeDecorator) -> treeDecorator.exclusionRadiusXZ),
		Codec.intRange(0, 16).fieldOf("exclusion_radius_y").forGetter((treeDecorator) -> treeDecorator.exclusionRadiusY),
		BlockStateProvider.CODEC.fieldOf("state").forGetter((treeDecorator) -> treeDecorator.state),
		Codec.INT.fieldOf("empty_block_count_for_placement").forGetter((treeDecorator) -> treeDecorator.emptyBlockCountForPlacement),
		ExtraCodecs.nonEmptyList(Direction.CODEC.listOf()).fieldOf("directions").forGetter((treeDecorator) -> treeDecorator.directions)
	).apply(instance, LeavesAroundTopTreeDecorator::new));

	private final float probability;
	private final int exclusionRadiusXZ;
	private final int exclusionRadiusY;
	private final BlockStateProvider state;
	private final int emptyBlockCountForPlacement;
	private final List<Direction> directions;

	public LeavesAroundTopTreeDecorator(float probability, int exclusionRadiusXZ, int exclusionRadiusY, @NotNull BlockStateProvider state, int emptyBlockCountForPlacement, @NotNull List<Direction> directions) {
		this.probability = probability;
		this.exclusionRadiusXZ = exclusionRadiusXZ;
		this.exclusionRadiusY = exclusionRadiusY;
		this.state = state;
		this.emptyBlockCountForPlacement = emptyBlockCountForPlacement;
		this.directions = directions;
	}

	@Override
	@NotNull
	protected TreeDecoratorType<?> type() {
		return WilderTreeDecorators.LEAVES_AROUND_TOP_TREE_DECORATOR;
	}

	@Override
	public void place(@NotNull Context context) {
		BlockPos highestPos = context.logs().get(1);
		for (BlockPos logPos : context.logs()) {
			if (logPos.getY() > highestPos.getY()) {
				highestPos = logPos;
			}
		}
		HashSet<BlockPos> set = new HashSet<>();
		RandomSource randomSource = context.random();
		Vec3 highPos = new Vec3(highestPos.getX() + 0.5, highestPos.getY() + 0.5, highestPos.getZ() + 0.5);
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos mutableBlockPos2 = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos mutableBlockPos3 = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos mutableCheckPos = new BlockPos.MutableBlockPos();

		for (BlockPos blockPos : Util.shuffledCopy(context.leaves(), randomSource)) {
			if (((int) (Math.sqrt(blockPos.distToCenterSqr(highPos)))) <= 2) {
				Direction direction;
				mutableBlockPos.setWithOffset(blockPos, direction = Util.getRandom(this.directions, randomSource));
				if (set.contains(mutableBlockPos) || !(randomSource.nextFloat() < this.probability) || !this.hasRequiredEmptyBlocks(context, blockPos, mutableCheckPos, direction))
					continue;

				for (BlockPos blockPos5 : BlockPos.betweenClosed(
					mutableBlockPos2.setWithOffset(mutableCheckPos, -this.exclusionRadiusXZ, -this.exclusionRadiusY, -this.exclusionRadiusXZ),
					mutableBlockPos3.setWithOffset(mutableCheckPos, this.exclusionRadiusXZ, this.exclusionRadiusY, this.exclusionRadiusXZ)
				)
				) {
					set.add(blockPos5);
				}
				context.setBlock(mutableBlockPos, this.state.getState(randomSource, mutableBlockPos));
			}
		}
	}

	private boolean hasRequiredEmptyBlocks(@NotNull TreeDecorator.Context context, @NotNull BlockPos pos, @NotNull BlockPos.MutableBlockPos mutableBlockPos, @NotNull Direction direction) {
		for (int i = 1; i <= this.emptyBlockCountForPlacement; ++i) {
			mutableBlockPos.set(pos).move(direction, i);
			if (context.isAir(mutableBlockPos)) continue;
			return false;
		}
		return true;
	}
}

