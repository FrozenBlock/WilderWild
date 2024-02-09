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

package net.frozenblock.wilderwild.block.entity;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GeyserBlockEntity extends BlockEntity {


	public GeyserBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		super(RegisterBlockEntities.GEYSER, pos, state);
	}

	public void tickServer(@NotNull Level level, @NotNull BlockPos pos, boolean active) {

		this.updateSync();
	}

	public void tickClient(@NotNull Level level, @NotNull BlockPos pos, boolean active) {
		if (active) {
			particleTick(level, pos.above());
		}
	}

	public static void particleTick(@NotNull Level level, BlockPos blockPos) {
		RandomSource randomSource = level.random;
		if (randomSource.nextFloat() <= 0.1F) {
			level.addAlwaysVisibleParticle(
				ParticleTypes.CAMPFIRE_COSY_SMOKE,
				true,
				(double)blockPos.getX() + 0.5D + randomSource.nextDouble() / 3D * (randomSource.nextBoolean() ? 1D : -1D),
				blockPos.getY(),
				(double)blockPos.getZ() + 0.5D + randomSource.nextDouble() / 3D * (randomSource.nextBoolean() ? 1D : -1D),
				0.0,
				(randomSource.nextFloat() * 0.04D) + 0.06D,
				0.0
			);
		}
	}

	public void updateSync() {
		ClientboundBlockEntityDataPacket updatePacket = this.getUpdatePacket();
		if (updatePacket != null) {
			for (ServerPlayer player : PlayerLookup.tracking(this)) {
				player.connection.send(updatePacket);
			}
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@NotNull
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return this.saveWithoutMetadata(provider);
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.saveAdditional(tag, provider);
	}

	@Override
	public void load(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.load(tag, provider);
	}
}
