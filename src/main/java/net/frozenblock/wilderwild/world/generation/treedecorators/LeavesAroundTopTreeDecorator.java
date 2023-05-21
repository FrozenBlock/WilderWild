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
			Codec.intRange(0, 16).fieldOf("exclusionRadiusXZ").forGetter((treeDecorator) -> treeDecorator.exclusionRadiusXZ),
			Codec.intRange(0, 16).fieldOf("exclusionRadiusY").forGetter((treeDecorator) -> treeDecorator.exclusionRadiusY),
			BlockStateProvider.CODEC.fieldOf("blockProvider").forGetter((treeDecorator) -> treeDecorator.blockProvider),
			Codec.INT.fieldOf("requiredEmptyBlocks").forGetter((treeDecorator) -> treeDecorator.requiredEmptyBlocks),
			ExtraCodecs.nonEmptyList(Direction.CODEC.listOf()).fieldOf("directions").forGetter((treeDecorator) -> treeDecorator.directions)
	).apply(instance, LeavesAroundTopTreeDecorator::new));

	private final float probability;
	private final int exclusionRadiusXZ;
	private final int exclusionRadiusY;
	private final BlockStateProvider blockProvider;
	private final int requiredEmptyBlocks;
	private final List<Direction> directions;

	public LeavesAroundTopTreeDecorator(float f, int i, int j, BlockStateProvider blockStateProvider, int k, List<Direction> list) {
		this.probability = f;
		this.exclusionRadiusXZ = i;
		this.exclusionRadiusY = j;
		this.blockProvider = blockStateProvider;
		this.requiredEmptyBlocks = k;
		this.directions = list;
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
		for (BlockPos blockPos : Util.shuffledCopy(context.leaves(), randomSource)) {
			if (((int)(Math.sqrt(blockPos.distToCenterSqr(highPos)))) <= 2) {
				Direction direction;
				BlockPos blockPos2 = blockPos.relative(direction = Util.getRandom(this.directions, randomSource));
				if (set.contains(blockPos2) || !(randomSource.nextFloat() < this.probability) || !this.hasRequiredEmptyBlocks(context, blockPos, direction))
					continue;
				BlockPos blockPos3 = blockPos2.offset(-this.exclusionRadiusXZ, -this.exclusionRadiusY, -this.exclusionRadiusXZ);
				BlockPos blockPos4 = blockPos2.offset(this.exclusionRadiusXZ, this.exclusionRadiusY, this.exclusionRadiusXZ);
				for (BlockPos blockPos5 : BlockPos.betweenClosed(blockPos3, blockPos4)) {
					set.add(blockPos5.immutable());
				}
				context.setBlock(blockPos2, this.blockProvider.getState(randomSource, blockPos2));
			}
		}
	}

	private boolean hasRequiredEmptyBlocks(TreeDecorator.Context context, BlockPos pos, Direction direction) {
		for (int i = 1; i <= this.requiredEmptyBlocks; ++i) {
			BlockPos blockPos = pos.relative(direction, i);
			if (context.isAir(blockPos)) continue;
			return false;
		}
		return true;
	}
}

