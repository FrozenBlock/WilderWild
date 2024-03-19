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

package net.frozenblock.wilderwild.world.impl.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class PollenTreeDecorator extends TreeDecorator {
	public static final Codec<PollenTreeDecorator> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((treeDecorator) -> treeDecorator.probability),
			Codec.floatRange(0.0F, 1.0F).fieldOf("placement_chance").forGetter((treeDecorator) -> treeDecorator.placementChance),
			Codec.INT.fieldOf("max_count").forGetter((treeDecorator) -> treeDecorator.maxCount)
		).apply(instance, PollenTreeDecorator::new));

	private final float probability;
	private final float placementChance;
	private final int maxCount;

	public PollenTreeDecorator(float probability, float placementChance, int maxCount) {
		this.probability = probability;
		this.placementChance = placementChance;
		this.maxCount = maxCount;
	}

	@Override
	@NotNull
	protected TreeDecoratorType<?> type() {
		return WilderTreeDecorators.POLLEN_TREE_DECORATOR;
	}

	@Override
	public void place(@NotNull Context generator) {
		RandomSource random = generator.random();
		if (random.nextFloat() <= this.probability) {
			ObjectArrayList<BlockPos> poses = new ObjectArrayList<>(generator.logs());
			poses.addAll(generator.leaves());
			Util.shuffle(poses, random);
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
			int placedPollen = 0;
			BlockState pollenState = RegisterBlocks.POLLEN.defaultBlockState();
			for (BlockPos pos : poses) {
				if (placedPollen >= this.maxCount) {
					return;
				}
				for (Direction direction : Direction.values()) {
					mutableBlockPos.setWithOffset(pos, direction);
					if (generator.isAir(mutableBlockPos)) {
						if (random.nextFloat() <= this.placementChance) {
							generator.setBlock(mutableBlockPos, pollenState.setValue(MultifaceBlock.getFaceProperty(direction.getOpposite()), true));
							placedPollen += 1;
						}
					}
				}
			}
		}
	}
}
