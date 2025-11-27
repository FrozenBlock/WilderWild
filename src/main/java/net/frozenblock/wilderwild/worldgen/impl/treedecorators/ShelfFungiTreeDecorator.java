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
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class ShelfFungiTreeDecorator extends TreeDecorator {
	public static final MapCodec<ShelfFungiTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.floatRange(0F, 1F).fieldOf("probability").forGetter(treeDecorator -> treeDecorator.probability),
		Codec.floatRange(0F, 1F).fieldOf("placement_chance").forGetter(treeDecorator -> treeDecorator.placementChance),
		BlockStateProvider.CODEC.fieldOf("shelf_fungi_provider").forGetter(treeDecorator -> treeDecorator.blockStateProvider)
	).apply(instance, ShelfFungiTreeDecorator::new));

	private final float probability;
	private final float placementChance;
	private final BlockStateProvider blockStateProvider;

	public ShelfFungiTreeDecorator(float probability, float placementChance, BlockStateProvider blockStateProvider) {
		this.probability = probability;
		this.placementChance = placementChance;
		this.blockStateProvider = blockStateProvider;
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return WWTreeDecorators.SHELF_FUNGI_TREE_DECORATOR;
	}

	@Override
	public void place(Context context) {
		if (!WWWorldgenConfig.GENERATE_SHELF_FUNGI) return;

		final RandomSource random = context.random();
		if (random.nextFloat() > this.probability) return;

		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (BlockPos pos : context.logs()) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				if (random.nextFloat() > this.placementChance || !context.isAir(mutable.setWithOffset(pos, direction))) continue;
				final BlockState state = this.blockStateProvider.getState(random, mutable)
					.setValue(ShelfFungiBlock.FACE, AttachFace.WALL)
					.setValue(ShelfFungiBlock.FACING, direction);
				context.setBlock(mutable, state);
			}
		}
	}
}
