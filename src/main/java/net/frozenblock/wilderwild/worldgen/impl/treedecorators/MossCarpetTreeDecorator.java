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
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class MossCarpetTreeDecorator extends TreeDecorator {
	public static final MapCodec<MossCarpetTreeDecorator> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((treeDecorator) -> treeDecorator.probability),
			Codec.floatRange(0.0F, 1.0F).fieldOf("placement_chance").forGetter((treeDecorator) -> treeDecorator.placementChance)
		).apply(instance, MossCarpetTreeDecorator::new));

	private final float probability;
	private final float placementChance;

	public MossCarpetTreeDecorator(float probability, float placementChance) {
		this.probability = probability;
		this.placementChance = placementChance;
	}

	@Override
	@NotNull
	protected TreeDecoratorType<?> type() {
		return WWTreeDecorators.MOSS_CARPET_TREE_DECORATOR;
	}

	@Override
	public void place(@NotNull Context generator) {
		RandomSource random = generator.random();
		if (random.nextFloat() <= this.probability) {
			ObjectArrayList<BlockPos> poses = new ObjectArrayList<>(generator.logs());
			poses.addAll(generator.leaves());
			Util.shuffle(poses, random);
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
			BlockState mossState = Blocks.MOSS_CARPET.defaultBlockState();
			for (BlockPos pos : poses) {
				mutableBlockPos.set(pos).move(Direction.UP);
				if (generator.isAir(mutableBlockPos)) {
					if (random.nextFloat() <= this.placementChance) {
						generator.setBlock(mutableBlockPos, mossState);
					}
				}
			}
		}
	}
}
