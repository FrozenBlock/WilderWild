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

package net.frozenblock.wilderwild.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class HangingTendrilBlockEntity extends BlockEntity implements GameEventListener.Holder<VibrationSystem.Listener>, VibrationSystem {

	private static final Logger LOGGER = LogUtils.getLogger();
	private VibrationSystem.Data vibrationData;
	private final VibrationSystem.Listener vibrationListener;
	private final VibrationSystem.User vibrationUser = this.createVibrationUser();
	private int lastVibrationFrequency;
	private static final String BASE_TEXTURE = "textures/entity/hanging_tendril/";

	public int ticksToStopTwitching;
	public int storedXP;
	public int ringOutTicksLeft;
	//CLIENT ONLY
	public ResourceLocation texture = WilderSharedConstants.id("textures/entity/hanging_tendril/inactive1.png");
	public boolean twitching;
	public boolean active;
	public boolean milk;
	public int ticks;

	public HangingTendrilBlockEntity(BlockPos pos, BlockState state) {
		super(RegisterBlockEntities.HANGING_TENDRIL, pos, state);
		this.vibrationData = new VibrationSystem.Data();
		this.vibrationListener = new VibrationSystem.Listener(this);
	}

	public void serverTick(Level level, BlockPos pos, BlockState state) {
		if (this.ticksToStopTwitching == 0) {
			level.setBlockAndUpdate(pos, state.setValue(HangingTendrilBlock.TWITCHING, false));
		}
		--this.ticksToStopTwitching;
		if (this.ringOutTicksLeft >= 0) {
			--this.ringOutTicksLeft;
		} else if (state.getValue(HangingTendrilBlock.WRINGING_OUT)) {
			level.setBlockAndUpdate(pos, state.setValue(HangingTendrilBlock.WRINGING_OUT, false));
			if (this.storedXP > 0) {
				int droppedXP = this.storedXP > 1 ? this.storedXP / 2 : 1;
				ExperienceOrb.award((ServerLevel) level, Vec3.atCenterOf(pos).add(0, -0.5, 0), droppedXP);
				this.storedXP = this.storedXP - droppedXP;
				level.gameEvent(null, RegisterGameEvents.TENDRIL_EXTRACT_XP, pos);
			}
		}
		VibrationSystem.Ticker.tick(level, this.getVibrationData(), this.getVibrationUser());
	}

	public void clientTick(BlockState state) {
		this.twitching = this.ticksToStopTwitching > 0;
		this.milk = this.ringOutTicksLeft > 0;
		this.active = !SculkSensorBlock.canActivate(state);
		++this.ticks;
		int animSpeed = 6;
		if (milk) {
			animSpeed = 2;
			this.texture = WilderSharedConstants.id(BASE_TEXTURE + "milk" + (((this.ticks / animSpeed) % 4) + 1) + ".png");
		} else if (active) {
			animSpeed = 1;
			this.texture = WilderSharedConstants.id(BASE_TEXTURE + "active" + (((this.ticks / animSpeed) % 5) + 1) + ".png");
		} else if (twitching) {
			animSpeed = 50;
			this.texture = WilderSharedConstants.id(BASE_TEXTURE + "twitch" + (((this.ticks / animSpeed) % 4) + 1) + ".png");
		} else {
			this.texture = WilderSharedConstants.id(BASE_TEXTURE + "inactive" + (((this.ticks / animSpeed) % 6) + 1) + ".png");
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	@NotNull
	public CompoundTag getUpdateTag() {
		return this.saveWithoutMetadata();
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		this.lastVibrationFrequency = tag.getInt("last_vibration_frequency");
		this.ticksToStopTwitching = tag.getInt("ticksToStopTwitching");
		this.storedXP = tag.getInt("storedXP");
		this.ringOutTicksLeft = tag.getInt("ringOutTicksLeft");
		if (tag.contains("listener", 10)) {
			VibrationSystem.Data.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.getCompound("listener"))).resultOrPartial(LOGGER::error).ifPresent(data -> this.vibrationData = data);
		}
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putInt("last_vibration_frequency", this.lastVibrationFrequency);
		tag.putInt("ticksToStopTwitching", this.ticksToStopTwitching);
		tag.putInt("storedXP", this.storedXP);
		tag.putInt("ringOutTicksLeft", this.ringOutTicksLeft);
		VibrationSystem.Data.CODEC.encodeStart(NbtOps.INSTANCE, this.vibrationData).resultOrPartial(LOGGER::error).ifPresent(nbt -> tag.put("listener", nbt));

	}

	public VibrationSystem.User createVibrationUser() {
		return new HangingTendrilBlockEntity.VibrationUser(this.getBlockPos());
	}

	public int getLastVibrationFrequency() {
		return this.lastVibrationFrequency;
	}

	public void setLastVibrationFrequency(int lastVibrationFrequency) {
		this.lastVibrationFrequency = lastVibrationFrequency;
	}

	@Override
	public Listener getListener() {
		return this.vibrationListener;
	}

	@Override
	public Data getVibrationData() {
		return this.vibrationData;
	}

	@Override
	public User getVibrationUser() {
		return this.vibrationUser;
	}

	public class VibrationUser
		implements VibrationSystem.User {
		public static final int LISTENER_RANGE = 4;
		protected final BlockPos blockPos;
		private final PositionSource positionSource;

		public VibrationUser(BlockPos pos) {
			this.blockPos = pos;
			this.positionSource = new BlockPositionSource(pos);
		}

		@Override
		public int getListenerRadius() {
			return LISTENER_RANGE;
		}

		@Override
		public PositionSource getPositionSource() {
			return this.positionSource;
		}

		@Override
		public boolean canTriggerAvoidVibration() {
			return true;
		}

		@Override
		public boolean canReceiveVibration(@NotNull ServerLevel world, BlockPos pos, @NotNull GameEvent gameEvent, @Nullable GameEvent.Context context) {
			if (pos.equals(this.blockPos) && (gameEvent == GameEvent.BLOCK_DESTROY || gameEvent == GameEvent.BLOCK_PLACE)) {
				return false;
			}
			return HangingTendrilBlock.canActivate(HangingTendrilBlockEntity.this.getBlockState()) && !HangingTendrilBlockEntity.this.getBlockState().getValue(HangingTendrilBlock.WRINGING_OUT);
		}

		@Override
		public void onReceiveVibration(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity entity2, float f) {
			BlockState blockState = HangingTendrilBlockEntity.this.getBlockState();
			if (SculkSensorBlock.canActivate(blockState)) {
				HangingTendrilBlockEntity.this.setLastVibrationFrequency(VibrationSystem.getGameEventFrequency(gameEvent));
				int i = VibrationSystem.getRedstoneStrengthForDistance(f, this.getListenerRadius());
				Block block = blockState.getBlock();
				if (block instanceof HangingTendrilBlock hangingTendrilBlock) {
					hangingTendrilBlock.activate(entity, world, this.blockPos, blockState, i, HangingTendrilBlockEntity.this.getLastVibrationFrequency());
				}
			}
		}

		@Override
		public void onDataChanged() {
			HangingTendrilBlockEntity.this.setChanged();
		}

		@Override
		public boolean requiresAdjacentChunksToBeTicking() {
			return true;
		}
	}
}
