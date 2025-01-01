/*
 * Copyright 2023-2025 FrozenBlock
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
import org.jetbrains.annotations.NotNull;

public class SculkSpreadTicker extends SilentTicker {

	public final SculkSpreader sculkSpreader;

	public SculkSpreadTicker(@NotNull EntityType<?> entityType, @NotNull Level level) {
		super(entityType, level);
		this.sculkSpreader = SculkSpreader.createLevelSpreader();
	}

	public SculkSpreadTicker(@NotNull EntityType<?> entityType, @NotNull Level level, @NotNull BlockPos pos, boolean worldgen) {
		super(entityType, level);
		this.setPos(Vec3.atCenterOf(pos));
		this.sculkSpreader = worldgen ? SculkSpreader.createWorldGenSpreader() : SculkSpreader.createLevelSpreader();
	}

	public static void createAndSpawn(@NotNull EntityType<?> entityType, @NotNull Level level, @NotNull BlockPos pos, boolean worldgen, int charge) {
		SculkSpreadTicker sculkSpreadTicker = new SculkSpreadTicker(entityType, level, pos, worldgen);
		level.addFreshEntity(sculkSpreadTicker);
		sculkSpreadTicker.sculkSpreader.addCursors(pos, charge);
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		this.sculkSpreader.save(compound);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.sculkSpreader.save(compound);
	}

	@Override
	public void tick(@NotNull Level level, @NotNull Vec3 vec3, @NotNull BlockPos pos, int ticks) {
		if (!this.sculkSpreader.getCursors().isEmpty()) {
			this.sculkSpreader.updateCursors(level, pos, level.random, true);
		} else {
			this.discard();
		}
	}

}
