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
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class CoconutTreeDecorator extends TreeDecorator {
	public static final Codec<CoconutTreeDecorator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		Codec.FLOAT.fieldOf("placement_chance").forGetter((treeDecorator) -> treeDecorator.placementChance)
	).apply(instance, CoconutTreeDecorator::new));

	private final float placementChance;

	public CoconutTreeDecorator(float placementChance) {
		this.placementChance = placementChance;
	}

	@Override
	@NotNull
	protected TreeDecoratorType<?> type() {
		return WilderTreeDecorators.COCONUT_TREE_DECORATOR;
	}

	@Override
	public void place(@NotNull Context context) {
		RandomSource random = context.random();
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos mutableCheckBelowPos = new BlockPos.MutableBlockPos();

		for (BlockPos blockPos : Util.shuffledCopy(context.leaves(), random)) {
			mutableBlockPos.set(blockPos);
			if (this.isEmptyBelow(context, mutableCheckBelowPos.set(mutableBlockPos)) && random.nextFloat() <= this.placementChance) {
				context.setBlock(mutableBlockPos.move(Direction.DOWN, 1), RegisterBlocks.COCONUT.defaultBlockState().setValue(BlockStateProperties.HANGING, true));
			}
		}
	}

	private boolean isEmptyBelow(@NotNull TreeDecorator.Context context, @NotNull BlockPos.MutableBlockPos mutableBlockPos) {
		return context.isAir(mutableBlockPos.set(mutableBlockPos).move(Direction.DOWN, 1));
	}
}

