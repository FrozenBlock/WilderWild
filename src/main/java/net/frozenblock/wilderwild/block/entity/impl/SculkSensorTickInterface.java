/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.block.entity.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface SculkSensorTickInterface {

	void wilderWild$tickServer(ServerLevel level, BlockPos pos, BlockState state);

	void wilderWild$tickClient(Level level, BlockPos pos, BlockState state);

	int wilderWild$getAge();

	void wilderWild$setAge(int i);

	int wilderWild$getAnimTicks();

	void wilderWild$setAnimTicks(int i);

	int wilderWild$getPrevAnimTicks();

	void wilderWild$setPrevAnimTicks(int i);

	boolean wilderWild$isActive();

	void wilderWild$setActive(boolean active);

	boolean wilderWild$isPrevActive();

	void wilderWild$setPrevActive(boolean active);

	Direction wilderWild$getFacing();

	void wilderWild$setFacing(Direction facing);

}
