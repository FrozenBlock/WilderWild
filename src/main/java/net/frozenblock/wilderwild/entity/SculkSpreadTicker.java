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

package net.frozenblock.wilderwild.entity;

import net.frozenblock.lib.entity.api.SilentTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.phys.Vec3;

public class SculkSpreadTicker extends SilentTicker {

	public final SculkSpreader sculkSpreader;

	public SculkSpreadTicker(EntityType<?> entityType, Level level) {
		super(entityType, level);
		this.sculkSpreader = SculkSpreader.createLevelSpreader();
	}

	public SculkSpreadTicker(EntityType<?> entityType, Level level, boolean worldgen) {
		super(entityType, level);
		this.sculkSpreader = worldgen ? SculkSpreader.createWorldGenSpreader() : SculkSpreader.createLevelSpreader();
	}

	public SculkSpreadTicker(EntityType<?> entityType, Level level, BlockPos pos, boolean worldgen) {
		super(entityType, level);
		this.setPos(Vec3.atCenterOf(pos));
		this.sculkSpreader = worldgen ? SculkSpreader.createWorldGenSpreader() : SculkSpreader.createLevelSpreader();
	}

	public static void createAndSpawn(EntityType<?> entityType, Level level, BlockPos pos, boolean worldgen, int charge) {
		SculkSpreadTicker sculkSpreadTicker = new SculkSpreadTicker(entityType, level, pos, worldgen);
		level.addFreshEntity(sculkSpreadTicker);
		sculkSpreadTicker.sculkSpreader.addCursors(pos, charge);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		this.sculkSpreader.save(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.sculkSpreader.save(compound);
	}

	@Override
	public void tick(Level level, Vec3 vec3, BlockPos pos, int ticks) {
		if (!this.sculkSpreader.getCursors().isEmpty()) {
			this.sculkSpreader.updateCursors(level, pos, level.random, true);
		} else {
			this.discard();
		}
	}

}
