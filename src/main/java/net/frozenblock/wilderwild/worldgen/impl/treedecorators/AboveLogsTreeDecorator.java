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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;
import net.minecraft.Util;

public class AboveLogsTreeDecorator extends TreeDecorator {
	public static final MapCodec<AboveLogsTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.floatRange(0F, 1F).fieldOf("probability").forGetter(treeDecorator -> treeDecorator.probability),
		Codec.floatRange(0F, 1F).fieldOf("placement_chance").forGetter(treeDecorator -> treeDecorator.placementChance),
		BlockStateProvider.CODEC.fieldOf("state").forGetter(treeDecorator -> treeDecorator.blockStateProvider)
	).apply(instance, AboveLogsTreeDecorator::new));

	private final float probability;
	private final float placementChance;
	private final BlockStateProvider blockStateProvider;

	public AboveLogsTreeDecorator(float probability, float placementChance, BlockStateProvider blockStateProvider) {
		this.probability = probability;
		this.placementChance = placementChance;
		this.blockStateProvider = blockStateProvider;
	}

	@Override
	@NotNull
	protected TreeDecoratorType<?> type() {
		return WWTreeDecorators.ABOVE_LOGS_TREE_DECORATOR;
	}

	@Override
	public void place(@NotNull Context context) {
		RandomSource random = context.random();
		if (random.nextFloat() <= this.probability) {
			ObjectArrayList<BlockPos> poses = new ObjectArrayList<>(context.logs());
			poses.addAll(context.leaves());
			Util.shuffle(poses, random);
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
			for (BlockPos pos : poses) {
				mutableBlockPos.set(pos).move(Direction.UP);
				if (context.isAir(mutableBlockPos)) {
					if (random.nextFloat() <= this.placementChance) {
						context.setBlock(mutableBlockPos, this.blockStateProvider.getState(random, mutableBlockPos));
					}
				}
			}
		}
	}
}
