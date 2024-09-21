/*
 * Copyright 2023-2024 FrozenBlock
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class ShelfFungiTreeDecorator extends TreeDecorator {
	public static final Codec<ShelfFungiTreeDecorator> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(
			Codec.floatRange(0F, 1F).fieldOf("probability").forGetter((treeDecorator) -> treeDecorator.probability),
			Codec.floatRange(0F, 1F).fieldOf("placement_chance").forGetter((treeDecorator) -> treeDecorator.placementChance),
			Codec.floatRange(0F, 1F).fieldOf("red_shelf_fungi_chance").forGetter((treeDecorator) -> treeDecorator.redShelfFungiChance)
		).apply(instance, ShelfFungiTreeDecorator::new));
	private final float probability;
	private final float placementChance;
	private final float redShelfFungiChance;

	public ShelfFungiTreeDecorator(float probability, float placementChance, float redShelfFungusChance) {
		this.probability = probability;
		this.placementChance = placementChance;
		this.redShelfFungiChance = redShelfFungusChance;
	}

	@Override
	@NotNull
	protected TreeDecoratorType<?> type() {
		return WWTreeDecorators.SHELF_FUNGI_TREE_DECORATOR;
	}

	@Override
	public void place(@NotNull Context generator) {
		RandomSource abstractRandom = generator.random();
		if (abstractRandom.nextFloat() <= this.probability) {
			List<BlockPos> poses = generator.logs();
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
			BlockState redState = WWBlocks.RED_SHELF_FUNGI.defaultBlockState();
			BlockState brownState = WWBlocks.BROWN_SHELF_FUNGI.defaultBlockState();
			for (BlockPos pos : poses) {
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					if (abstractRandom.nextFloat() <= this.placementChance) {
						mutableBlockPos.setWithOffset(pos, direction);
						if (generator.isAir(mutableBlockPos)) {
							if (generator.random().nextFloat() < redShelfFungiChance) {
								generator.setBlock(mutableBlockPos, redState.setValue(ShelfFungiBlock.STAGE, abstractRandom.nextInt(3) + 1).setValue(ShelfFungiBlock.FACE, AttachFace.WALL).setValue(ShelfFungiBlock.FACING, direction));
							} else {
								generator.setBlock(mutableBlockPos, brownState.setValue(ShelfFungiBlock.STAGE, abstractRandom.nextInt(3) + 1).setValue(ShelfFungiBlock.FACE, AttachFace.WALL).setValue(ShelfFungiBlock.FACING, direction));
							}
						}
					}
				}
			}
		}
	}
}
