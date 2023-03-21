/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.world.generation.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class PollenTreeDecorator extends TreeDecorator {
    public static final Codec<PollenTreeDecorator> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("chanceToDecorate").forGetter((treeDecorator) -> treeDecorator.chanceToDecorate),
					Codec.floatRange(0.0F, 1.0F).fieldOf("pollenPlaceChance").forGetter((treeDecorator) -> treeDecorator.pollenPlaceChance),
					Codec.INT.fieldOf("maxPollenCount").forGetter((treeDecorator) -> treeDecorator.maxPollenCount)
			).apply(instance, PollenTreeDecorator::new));

    private final float chanceToDecorate;
    private final float pollenPlaceChance;
	private final int maxPollenCount;

    public PollenTreeDecorator(float chanceToDecorate, float pollenPlaceChance, int maxPollenCount) {
        this.chanceToDecorate = chanceToDecorate;
        this.pollenPlaceChance = pollenPlaceChance;
		this.maxPollenCount = maxPollenCount;
    }

    protected TreeDecoratorType<?> type() {
        return WilderTreeDecorators.POLLEN_TREE_DECORATOR;
    }

    public void place(Context generator) {
        RandomSource random = generator.random();
        if (random.nextFloat() <= this.chanceToDecorate) {
            List<BlockPos> list = new ArrayList<>();
			list.addAll(generator.logs());
			list.addAll(generator.leaves());
			Collections.shuffle(list);
			AtomicInteger placedPollen = new AtomicInteger();
            list.forEach((pos) -> {
				if (placedPollen.get() >= this.maxPollenCount) {
					return;
				}
				for (Direction direction : Direction.values()) {
					BlockPos blockPos = pos.relative(direction);
					if (generator.isAir(blockPos)) {
						if (random.nextFloat() <= this.pollenPlaceChance) {
							generator.setBlock(blockPos, RegisterBlocks.POLLEN_BLOCK.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(direction.getOpposite()), true));
							placedPollen.addAndGet(1);
						}
					}
				}
            });
        }
    }

}
