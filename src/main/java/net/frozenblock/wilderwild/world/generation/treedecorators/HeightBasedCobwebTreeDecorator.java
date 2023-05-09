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
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;


public class HeightBasedCobwebTreeDecorator extends TreeDecorator {
	public static final Codec<HeightBasedCobwebTreeDecorator> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(
			Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((treeDecorator) -> treeDecorator.probability),
			Codec.intRange(-63, 319).fieldOf("maxHeight").forGetter((treeDecorator) -> treeDecorator.maxHeight),
			Codec.floatRange(0.0F, 1.0F).fieldOf("cobweb_count").forGetter((treeDecorator) -> treeDecorator.cobweb_count)
		).apply(instance, HeightBasedCobwebTreeDecorator::new));
	private final float probability;
	private final int maxHeight;
	private final float cobweb_count;

	public HeightBasedCobwebTreeDecorator(float probability, int maxHeight, float cobweb_count) {
		this.probability = probability;
		this.maxHeight = maxHeight;
		this.cobweb_count = cobweb_count;
	}

	@Override
	@NotNull
	protected TreeDecoratorType<?> type() {
		return WilderTreeDecorators.HEIGHT_BASED_COBWEB_TREE_DECORATOR;
	}

	@Override
	public void place(Context generator) {
		RandomSource abstractRandom = generator.random();
		if (abstractRandom.nextFloat() <= this.probability) {
			List<BlockPos> list = generator.logs();
			list.forEach((pos) -> {
				if (pos.getY() <= this.maxHeight) {
					for (Direction direction : Direction.Plane.HORIZONTAL) {
						if (abstractRandom.nextFloat() <= this.cobweb_count) {
							BlockPos blockPos = pos.offset(direction.getStepX(), 0, direction.getStepZ());
							if (generator.isAir(blockPos)) {
								generator.setBlock(blockPos, Blocks.COBWEB.defaultBlockState());
							}
						}
					}
				}
			});
		}
	}

}
