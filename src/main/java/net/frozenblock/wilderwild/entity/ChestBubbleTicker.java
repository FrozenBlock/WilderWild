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

package net.frozenblock.wilderwild.entity;

import net.frozenblock.lib.entity.api.SilentTicker;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ChestBubbleTicker extends SilentTicker {

	public ChestBubbleTicker(@NotNull EntityType<?> entityType, @NotNull Level level) {
		super(entityType, level);
	}

	public ChestBubbleTicker(@NotNull EntityType<?> entityType, @NotNull Level level, @NotNull BlockPos pos) {
		super(entityType, level);
		this.setPos(Vec3.atCenterOf(pos));
	}

	public static void createAndSpawn(@NotNull EntityType<?> entityType, @NotNull Level level, @NotNull BlockPos pos) {
		ChestBubbleTicker chestBubbleTicker = new ChestBubbleTicker(entityType, level, pos);
		level.addFreshEntity(chestBubbleTicker);
	}

	@Override
	public void tick(@NotNull Level level, @NotNull Vec3 vec3, @NotNull BlockPos pos, int ticks) {
		if (ticks <= 5) {
			if (level instanceof ServerLevel server) {
				BlockState state = level.getBlockState(pos);
				if (level.getBlockEntity(pos) instanceof ChestBlockEntity && state.getBlock() instanceof ChestBlock) {
					if (state.getFluidState().is(Fluids.WATER) && WWBlockConfig.get().chestBubbling) {
						double additionalX = 0.5D;
						double additionalZ = 0.5D;
						if (state.hasProperty(BlockStateProperties.CHEST_TYPE) && state.getValue(BlockStateProperties.CHEST_TYPE) != ChestType.SINGLE) {
							Direction direction = ChestBlock.getConnectedDirection(state);
							additionalX += (double) direction.getStepX() * 0.125;
							additionalZ += (double) direction.getStepZ() * 0.125;
						}
						server.sendParticles(
							ParticleTypes.BUBBLE,
							pos.getX() + additionalX,
							pos.getY() + 0.625D,
							pos.getZ() + additionalZ,
							level.random.nextInt(4, 10),
							0.21875D,
							0D,
							0.21875D,
							0.2D
						);
						return;
					}
				}
			}
		}
		this.discard();
	}

}
