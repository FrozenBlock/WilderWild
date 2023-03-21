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
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class MossCarpetTreeDecorator extends TreeDecorator {
    public static final Codec<MossCarpetTreeDecorator> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("chanceToDecorate").forGetter((treeDecorator) -> treeDecorator.chanceToDecorate),
					Codec.floatRange(0.0F, 1.0F).fieldOf("mossPlaceChance").forGetter((treeDecorator) -> treeDecorator.mossPlaceChance)
			).apply(instance, MossCarpetTreeDecorator::new));

    private final float chanceToDecorate;
    private final float mossPlaceChance;

    public MossCarpetTreeDecorator(float chanceToDecorate, float mossPlaceChance) {
        this.chanceToDecorate = chanceToDecorate;
        this.mossPlaceChance = mossPlaceChance;
    }

    protected TreeDecoratorType<?> type() {
        return WilderTreeDecorators.MOSS_CARPET_TREE_DECORATOR;
    }

    public void place(Context generator) {
        RandomSource random = generator.random();
        if (random.nextFloat() <= this.chanceToDecorate) {
            List<BlockPos> list = generator.logs();
			list.addAll(generator.leaves());
			Collections.shuffle(list);
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            list.forEach((pos) -> {
				mutableBlockPos.set(pos).move(Direction.UP);
				if (generator.isAir(mutableBlockPos)) {
					if (random.nextFloat() <= this.mossPlaceChance) {
						generator.setBlock(mutableBlockPos, Blocks.MOSS_CARPET.defaultBlockState());
					}
				}
            });
        }
    }

}
