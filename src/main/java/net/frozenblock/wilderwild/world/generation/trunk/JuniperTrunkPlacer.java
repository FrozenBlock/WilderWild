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

package net.frozenblock.wilderwild.world.generation.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class JuniperTrunkPlacer extends TrunkPlacer {
	public static final Codec<JuniperTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> JuniperTrunkPlacer.trunkPlacerParts(instance).apply(instance, JuniperTrunkPlacer::new));

	public JuniperTrunkPlacer(int i, int j, int k) {
		super(i, j, k);
	}
	@Override
	protected TrunkPlacerType<?> type() {
		return WilderWild.JUNIPER_TRUNK_PLACER;
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> replacer, @NotNull RandomSource random, int height, @NotNull BlockPos startPos, @NotNull TreeConfiguration config) {
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		BlockPos center = new BlockPos(startPos.getX() - 1, startPos.getY(), startPos.getZ() - 1);
		List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
		int branchLenght = 20;
		BlockPos updateablePos = center;
		for(int i = 0; i < branchLenght; i++) {
			setLog(level, replacer, random, mutable, config, updateablePos, 0, 0, 0);
			updateablePos = updateablePos.relative(rndmDir());
		}
		return null;
	}

	public static Direction rndmDir() {
		double rndm = Math.random();
		Direction[] directions = Direction.values();
		return directions[(int)(rndm * directions.length)];
	}
	private void setLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config, BlockPos startPos, int x, int y, int z) {
		this.setLog(level, replacer, random, pos, config, startPos, x, y, z);
	}

}
