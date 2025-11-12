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
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class PollenTreeDecorator extends TreeDecorator {
	public static final MapCodec<PollenTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.floatRange(0F, 1F).fieldOf("probability").forGetter(treeDecorator -> treeDecorator.probability),
		Codec.floatRange(0F, 1F).fieldOf("placement_chance").forGetter(treeDecorator -> treeDecorator.placementChance),
		Codec.INT.fieldOf("max_count").forGetter(treeDecorator -> treeDecorator.maxCount)
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
		return WWTreeDecorators.POLLEN_TREE_DECORATOR;
	}

	@Override
	public void place(@NotNull Context context) {
		if (!WWWorldgenConfig.GENERATE_POLLEN) return;

		RandomSource random = context.random();
		if (random.nextFloat() > this.probability) return;

		ObjectArrayList<BlockPos> poses = new ObjectArrayList<>(context.logs());
		poses.addAll(context.leaves());
		Util.shuffle(poses, random);
		BlockState pollenState = WWBlocks.POLLEN.defaultBlockState();

		int placedPollen = 0;
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		for (BlockPos pos : poses) {
			if (placedPollen >= this.maxCount) return;
			for (Direction direction : Direction.values()) {
				if (random.nextFloat() > this.placementChance || !context.isAir(mutableBlockPos.setWithOffset(pos, direction))) continue;
				context.setBlock(mutableBlockPos, pollenState.setValue(MultifaceBlock.getFaceProperty(direction.getOpposite()), true));
				placedPollen += 1;
			}
		}
	}
}
