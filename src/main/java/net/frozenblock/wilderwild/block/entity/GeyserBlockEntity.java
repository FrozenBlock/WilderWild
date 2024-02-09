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

import java.util.Arrays;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.block.GeyserBlock;
import net.frozenblock.wilderwild.block.impl.GeyserType;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GeyserBlockEntity extends BlockEntity {
	private boolean hasRunFirstCheck = false;
	private int tickUntilNextEvent;
	private GeyserStage geyserStage;

	public GeyserBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		super(RegisterBlockEntities.GEYSER, pos, state);
	}

	public void tickServer(@NotNull Level level, @NotNull BlockPos pos, GeyserType geyserType, RandomSource random) {
		if (!this.hasRunFirstCheck) {
			level.scheduleTick(pos, this.getBlockState().getBlock(), level.random.nextInt(20, 100));
			this.hasRunFirstCheck = true;
		} else if (!GeyserBlock.isInactive(geyserType)) {
			this.tickUntilNextEvent -= 1;
			if (this.tickUntilNextEvent <= 0) {
				this.advanceStage(random);
			}
		} else {
			this.setDormant(random);
		}
		this.updateSync();
	}

	public void tickClient(@NotNull Level level, @NotNull BlockPos pos, GeyserType geyserType, RandomSource random) {
		if (!GeyserBlock.isInactive(geyserType)) {
			if (this.geyserStage == GeyserStage.DORMANT) {
				spawnDormantParticles(level, pos.above(), geyserType, random);
			} else if (this.geyserStage == GeyserStage.ACTIVE) {
				spawnActiveParticles(level, pos.above(), geyserType, random);
			} else if (this.geyserStage == GeyserStage.ERUPTING) {

			}
		}
	}

	public static void spawnDormantParticles(@NotNull Level level, BlockPos blockPos, GeyserType geyserType, RandomSource random) {
		if (geyserType == GeyserType.WATER && random.nextFloat() <= 0.0235F) {
			level.addAlwaysVisibleParticle(
				ParticleTypes.BUBBLE,
				true,
				blockPos.getX() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
				blockPos.getY(),
				blockPos.getZ() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
				0D,
				(random.nextFloat() * 0.04D) + 0.06D,
				0D
			);
		}
		if (random.nextFloat() <= 0.0125F) {
			level.addAlwaysVisibleParticle(
				ParticleTypes.CAMPFIRE_COSY_SMOKE,
				true,
				blockPos.getX() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
				blockPos.getY(),
				blockPos.getZ() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
				0D,
				(random.nextFloat() * 0.04D) + 0.06D,
				0D
			);
		}
	}

	public static void spawnActiveParticles(@NotNull Level level, BlockPos blockPos, GeyserType geyserType, RandomSource random) {
		if (geyserType == GeyserType.WATER) {
			if (random.nextFloat() <= 0.0575F) {
				level.addAlwaysVisibleParticle(
					ParticleTypes.BUBBLE,
					true,
					blockPos.getX() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
					blockPos.getY(),
					blockPos.getZ() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
					0D,
					(random.nextFloat() * 0.04D) + 0.06D,
					0D
				);
			}
		} else {
			if (random.nextFloat() <= 0.0575F) {
				int count = random.nextInt(1, 2);
				for (int i = 0; i < count; i++) {
					level.addAlwaysVisibleParticle(
						ParticleTypes.LARGE_SMOKE,
						true,
						blockPos.getX() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
						blockPos.getY(),
						blockPos.getZ() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
						0D,
						(random.nextFloat() * 0.04D) + 0.06D,
						0D
					);
				}
			}
			if (random.nextFloat() <= 0.0575F) {
				int count = random.nextInt(1, 4);
				for (int i = 0; i < count; i++) {
					level.addAlwaysVisibleParticle(
						ParticleTypes.DUST_PLUME,
						true,
						blockPos.getX() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
						blockPos.getY(),
						blockPos.getZ() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
						0D,
						(random.nextFloat() * 0.04D) + 0.06D,
						0D
					);
				}
			}
			if (geyserType == GeyserType.LAVA) {
				if (random.nextFloat() <= 0.0575F) {
					level.addAlwaysVisibleParticle(
						ParticleTypes.LAVA,
						true,
						blockPos.getX() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
						blockPos.getY(),
						blockPos.getZ() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
						0D,
						(random.nextFloat() * 0.08D) + 0.06D,
						0D
					);
				}
			}
		}
		if (random.nextFloat() <= 0.0375F) {
			level.addAlwaysVisibleParticle(
				ParticleTypes.CAMPFIRE_COSY_SMOKE,
				true,
				blockPos.getX() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
				blockPos.getY(),
				blockPos.getZ() + 0.5D + random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D),
				0D,
				(random.nextFloat() * 0.04D) + 0.06D,
				0D
			);
		}
	}

	public void advanceStage(RandomSource random) {
		if (this.geyserStage == GeyserStage.DORMANT) {
			this.setStageAndCooldown(GeyserStage.ACTIVE, random);
		} else if (this.geyserStage == GeyserStage.ACTIVE) {
			this.setStageAndCooldown(GeyserStage.ERUPTING, random);
		} else if (this.geyserStage == GeyserStage.ERUPTING) {
			this.setStageAndCooldown(GeyserStage.DORMANT, random);
		}
	}

	public void setDormant(RandomSource random) {
		this.setStageAndCooldown(GeyserStage.DORMANT, random);
	}

	public void setStageAndCooldown(GeyserStage geyserStage, RandomSource random) {
		this.geyserStage = geyserStage;
		if (geyserStage == GeyserStage.ACTIVE) {
			this.tickUntilNextEvent = random.nextInt(100, 200);
		} else if (geyserStage == GeyserStage.ERUPTING) {
			this.tickUntilNextEvent = random.nextInt(20, 60);
		} else {
			this.tickUntilNextEvent = random.nextInt(200, 400);
		}
	}

	public void updateSync() {
		ClientboundBlockEntityDataPacket updatePacket = this.getUpdatePacket();
		if (updatePacket != null) {
			for (ServerPlayer player : PlayerLookup.tracking(this)) {
				player.connection.send(this.getUpdatePacket());
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
		tag.putBoolean("HasRunFirstCheck", this.hasRunFirstCheck);
		tag.putInt("TicksUntilNextEvent", this.tickUntilNextEvent);
		tag.putString("GeyserStage", this.geyserStage.name());
	}

	@Override
	public void load(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.load(tag, provider);
		this.hasRunFirstCheck = tag.getBoolean("HasRunFirstCheck");
		this.tickUntilNextEvent = tag.getInt("TicksUntilNextEvent");
		if (tag.contains("GeyserStage") && (Arrays.stream(GeyserStage.values()).anyMatch(geyserStage -> geyserStage.name().equals(tag.getString("GeyserStage"))))) {
			this.geyserStage = GeyserStage.valueOf(tag.getString("GeyserStage"));
		}
	}

	public enum GeyserStage implements StringRepresentable {
		DORMANT("dormant"),
		ACTIVE("active"),
		ERUPTING("erupting");

		private final String name;

		GeyserStage(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}

		@Override
		@NotNull
		public String getSerializedName() {
			return this.name;
		}
	}
}
