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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class HeightBasedVineTreeDecorator extends TreeDecorator {
    public static final Codec<HeightBasedVineTreeDecorator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Codec.floatRange(0.0F, 1.0F).fieldOf("chanceToDecorate").forGetter((treeDecorator) -> treeDecorator.chanceToDecorate),
			Codec.intRange(-63, 319).fieldOf("maxHeight").forGetter((treeDecorator) -> treeDecorator.maxHeight),
			Codec.floatRange(0.0F, 1.0F).fieldOf("vinePlaceChance").forGetter((treeDecorator) -> treeDecorator.vinePlaceChance)
	).apply(instance, HeightBasedVineTreeDecorator::new));

    private final float chanceToDecorate;
    private final int maxHeight;
    private final float vinePlaceChance;

    public HeightBasedVineTreeDecorator(float chanceToDecorate, int maxHeight, float vinePlaceChance) {
        this.chanceToDecorate = chanceToDecorate;
        this.maxHeight = maxHeight;
        this.vinePlaceChance = vinePlaceChance;
    }

    protected TreeDecoratorType<?> type() {
        return WilderTreeDecorators.HEIGHT_BASED_VINE_TREE_DECORATOR;
    }

    public void place(Context generator) {
        RandomSource random = generator.random();
        if (random.nextFloat() <= this.chanceToDecorate) {
			List<BlockPos> list = new ArrayList<>();
			list.addAll(generator.logs());
			list.addAll(generator.roots());
			Collections.shuffle(list);
            list.forEach((pos) -> {
                if (pos.getY() <= this.maxHeight) {
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        if (random.nextFloat() <= this.vinePlaceChance) {
                            BlockPos blockPos = pos.offset(direction.getStepX(), 0, direction.getStepZ());
                            if (generator.isAir(blockPos)) {
                                BooleanProperty dir = direction == Direction.NORTH ? VineBlock.SOUTH : direction == Direction.SOUTH ? VineBlock.NORTH : direction == Direction.WEST ? VineBlock.EAST : VineBlock.WEST;
                                generator.setBlock(blockPos, Blocks.VINE.defaultBlockState().setValue(dir, true));
                            }
                        }
                    }
                }
            });
        }
    }

}
