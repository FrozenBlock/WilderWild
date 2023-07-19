/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.block.entity;

import java.util.ArrayList;
import net.frozenblock.wilderwild.block.PalmCrownBlock;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class PalmCrownBlockEntity extends BlockEntity {

	public PalmCrownBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		super(RegisterBlockEntities.PALM_CROWN, pos, state);
	}

	public void tick() {
		if (this.level != null) {
			PalmCrownPositions.addPos(this.level, this.worldPosition);
		}
	}

	public static class PalmCrownPositions {
		private static final ArrayList<CrownPos> crownPosesOne = new ArrayList<>();
		private static final ArrayList<CrownPos> crownPosesTwo = new ArrayList<>();

		private static boolean isAlt;

		public static double distanceToClosestPalmCrown(@NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, int i) {
			ArrayList<CrownPos> copiedList = (ArrayList<CrownPos>) getPoses().clone();
			ArrayList<BlockPos> posList = new ArrayList<>();
			int x = blockPos.getX();
			int y = blockPos.getY();
			int z = blockPos.getZ();
			BlockState state;
			for (CrownPos crownPos : copiedList) {
				int xVal = crownPos.pos.getX() - x;
				if (xVal >= -i && xVal <= i) {
					int zVal = crownPos.pos.getZ() - z;
					if (zVal >= -i && zVal <= i) {
						int yVal = crownPos.pos.getY() - y;
						if (yVal >= -i && yVal <= i) {
							state = blockGetter.getBlockState(crownPos.pos);
							if (state.getBlock() instanceof PalmCrownBlock) {
								posList.add(crownPos.pos);
							}
						}
					}
				}
			}
			Vec3 startPos = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
			if (!posList.isEmpty()) {
				double lowestDist = 99;
				for (BlockPos pos : posList) {
					double checkDist = Math.sqrt(pos.distToCenterSqr(startPos));
					if (checkDist < lowestDist) {
						lowestDist = checkDist;
					}
				}
				return lowestDist;
			}
			return 99;
		}

		public static ArrayList<CrownPos> getPoses() {
			return !isAlt ? crownPosesOne : crownPosesTwo;
		}

		public static ArrayList<CrownPos> getAltList() {
			return isAlt ? crownPosesOne : crownPosesTwo;
		}

		public static void clear() {
			getPoses().clear();
		}

		public static void clearAll() {
			crownPosesOne.clear();
			crownPosesTwo.clear();
		}

		public static void clearAndSwitch() {
			clear();
			isAlt = !isAlt;
		}

		public static void addPos(@NotNull Level level, @NotNull BlockPos pos) {
			getAltList().add(new CrownPos(pos, level.dimension().location()));
		}

		public record CrownPos(@NotNull BlockPos pos, @NotNull ResourceLocation dimension) {

		}
	}

}
