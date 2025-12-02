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
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

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
	protected TreeDecoratorType<?> type() {
		return WWTreeDecorators.ABOVE_LOGS_TREE_DECORATOR;
	}

	@Override
	public void place(Context context) {
		final RandomSource random = context.random();
		if (random.nextFloat() > this.probability) return;

		final ObjectArrayList<BlockPos> poses = new ObjectArrayList<>(context.logs());
		poses.addAll(context.leaves());
		Util.shuffle(poses, random);

		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (BlockPos pos : poses) {
			if (random.nextFloat() > this.placementChance || !context.isAir(mutable.setWithOffset(pos, Direction.UP))) continue;
			context.setBlock(mutable, this.blockStateProvider.getState(random, mutable));
		}
	}
}
