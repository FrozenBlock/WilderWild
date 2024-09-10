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
import java.util.List;

public class NetherShelfFungiTreeDecorator extends TreeDecorator {
	public static final MapCodec<NetherShelfFungiTreeDecorator> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		instance.group(
			Codec.floatRange(0F, 1F).fieldOf("probability").forGetter((treeDecorator) -> treeDecorator.probability),
			Codec.floatRange(0F, 1F).fieldOf("placement_chance").forGetter((treeDecorator) -> treeDecorator.placementChance),
			Codec.floatRange(0F, 1F).fieldOf("warped_shelf_fungi_change").forGetter((treeDecorator) -> treeDecorator.warpedShelfFungiChance)
		).apply(instance, NetherShelfFungiTreeDecorator::new));
	private final float probability;
	private final float placementChance;
	private final float warpedShelfFungiChance;

	public NetherShelfFungiTreeDecorator(float probability, float placementChance, float warpedShelfFungiChance) {
		this.probability = probability;
		this.placementChance = placementChance;
		this.warpedShelfFungiChance = warpedShelfFungiChance;
	}

	@Override
	@NotNull
	protected TreeDecoratorType<?> type() {
		return WWTreeDecorators.NETHER_SHELF_FUNGI_TREE_DECORATOR;
	}

	@Override
	public void place(@NotNull Context generator) {
		RandomSource abstractRandom = generator.random();
		if (abstractRandom.nextFloat() <= this.probability) {
			List<BlockPos> poses = generator.logs();
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
			BlockState warpedState = WWBlocks.WARPED_SHELF_FUNGI.defaultBlockState();
			BlockState crimsonState = WWBlocks.CRIMSON_SHELF_FUNGI.defaultBlockState();
			for (BlockPos pos : poses) {
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					if (abstractRandom.nextFloat() <= this.placementChance) {
						mutableBlockPos.setWithOffset(pos, direction);
						if (generator.isAir(mutableBlockPos)) {
							if (generator.random().nextFloat() < warpedShelfFungiChance) {
								generator.setBlock(mutableBlockPos, warpedState.setValue(ShelfFungiBlock.STAGE, abstractRandom.nextInt(3) + 1).setValue(ShelfFungiBlock.FACE, AttachFace.WALL).setValue(ShelfFungiBlock.FACING, direction));
							} else {
								generator.setBlock(mutableBlockPos, crimsonState.setValue(ShelfFungiBlock.STAGE, abstractRandom.nextInt(3) + 1).setValue(ShelfFungiBlock.FACE, AttachFace.WALL).setValue(ShelfFungiBlock.FACING, direction));
							}
						}
					}
				}
			}
		}
	}
}
