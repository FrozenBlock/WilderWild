/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.worldgen.impl.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.util.Util;

public class HeightBasedCobwebTreeDecorator extends TreeDecorator {
	public static final MapCodec<HeightBasedCobwebTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.floatRange(0F, 1F).fieldOf("probability").forGetter(treeDecorator -> treeDecorator.probability),
		Codec.intRange(-63, 319).fieldOf("max_height").forGetter(treeDecorator -> treeDecorator.maxHeight),
		Codec.floatRange(0F, 1F).fieldOf("placement_chance").forGetter(treeDecorator -> treeDecorator.placementChance)
	).apply(instance, HeightBasedCobwebTreeDecorator::new));

	private final float probability;
	private final int maxHeight;
	private final float placementChance;

	public HeightBasedCobwebTreeDecorator(float probability, int maxHeight, float placementChance) {
		this.probability = probability;
		this.maxHeight = maxHeight;
		this.placementChance = placementChance;
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return WWTreeDecorators.HEIGHT_BASED_COBWEB_TREE_DECORATOR;
	}

	@Override
	public void place(Context context) {
		final RandomSource random = context.random();
		if (random.nextFloat() > this.probability) return;

		final BlockState cobwebState = Blocks.COBWEB.defaultBlockState();
		final List<BlockPos> logs = Util.shuffledCopy(context.logs(), random);

		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (BlockPos pos : logs) {
			if (pos.getY() > this.maxHeight) continue;
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				if (random.nextFloat() > this.placementChance || !context.isAir(mutable.setWithOffset(pos, direction))) continue;
				context.setBlock(mutable, cobwebState);
			}
		}
	}
}
