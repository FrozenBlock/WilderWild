/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.worldgen.impl.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class PaleShelfFungiTreeDecorator extends TreeDecorator {
	public static final MapCodec<PaleShelfFungiTreeDecorator> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		instance.group(
			Codec.floatRange(0F, 1F).fieldOf("probability").forGetter((treeDecorator) -> treeDecorator.probability),
			Codec.floatRange(0F, 1F).fieldOf("placement_chance").forGetter((treeDecorator) -> treeDecorator.placementChance)
		).apply(instance, PaleShelfFungiTreeDecorator::new));
	private final float probability;
	private final float placementChance;

	public PaleShelfFungiTreeDecorator(float probability, float placementChance) {
		this.probability = probability;
		this.placementChance = placementChance;
	}

	@Override
	@NotNull
	protected TreeDecoratorType<?> type() {
		return WWTreeDecorators.PALE_SHELF_FUNGI_TREE_DECORATOR;
	}

	@Override
	public void place(@NotNull Context generator) {
		RandomSource abstractRandom = generator.random();
		if (abstractRandom.nextFloat() <= this.probability) {
			List<BlockPos> poses = generator.logs();
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
			for (BlockPos pos : poses) {
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					if (abstractRandom.nextFloat() <= this.placementChance) {
						mutableBlockPos.setWithOffset(pos, direction);
						if (generator.isAir(mutableBlockPos)) {
							generator.setBlock(
								mutableBlockPos,
								WWBlocks.PALE_SHELF_FUNGI.defaultBlockState()
									.setValue(ShelfFungiBlock.STAGE, abstractRandom.nextInt(3) + 1)
									.setValue(ShelfFungiBlock.FACE, AttachFace.WALL)
									.setValue(ShelfFungiBlock.FACING, direction)
							);
						}
					}
				}
			}
		}
	}
}
