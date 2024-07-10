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

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
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
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class HangingTendrilBlockEntity extends BlockEntity implements GameEventListener.Provider<VibrationSystem.Listener>, VibrationSystem {
	public static final int MILK_FRAMES = 4;
	public static final int MILK_ANIM_SPEED = 2;
	public static final int ACTIVE_FRAMES = 5;
	public static final int ACTIVE_ANIM_SPEED = 1;
	public static final int TWITCHING_FRAMES = 4;
	public static final int TWITCHING_ANIM_SPEED = 50;
	public static final int INACTIVE_FRAMES = 6;
	public static final int INACTIVE_ANIM_SPEED = 6;
	public static final double MILK_XP_PERCENTAGE = 0.5D;

	private static final Logger LOGGER = LogUtils.getLogger();
	private static final String BASE_TEXTURE = "textures/entity/hanging_tendril/";
	private final VibrationSystem.Listener vibrationListener;
	private final VibrationSystem.User vibrationUser = this.createVibrationUser();
	public int ticksToStopTwitching;
	private int storedXP;
	public int ringOutTicksLeft;
	private int activeTicks;
	//CLIENT ONLY
	public ResourceLocation texture = WilderConstants.id("textures/entity/hanging_tendril/inactive1.png");
	public boolean twitching;
	public boolean active;
	public boolean milking;
	public int ticks;
	private VibrationSystem.Data vibrationData;
	private int lastVibrationFrequency;

	public HangingTendrilBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		super(RegisterBlockEntities.HANGING_TENDRIL, pos, state);
		this.vibrationData = new VibrationSystem.Data();
		this.vibrationListener = new VibrationSystem.Listener(this);
	}

	public void serverTick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
		if (this.ticksToStopTwitching <= 0) {
			level.setBlockAndUpdate(pos, state.setValue(HangingTendrilBlock.TWITCHING, false));
		}
		--this.ticksToStopTwitching;
		if (this.ringOutTicksLeft >= 0) {
			--this.ringOutTicksLeft;
		} else if (state.getValue(HangingTendrilBlock.WRINGING_OUT)) {
			state = state.setValue(HangingTendrilBlock.WRINGING_OUT, false);
			level.setBlockAndUpdate(pos, state);
			if (this.storedXP > 0) {
				int droppedXP = this.storedXP > 1 ? (int) (this.storedXP * MILK_XP_PERCENTAGE) : 1;
				ExperienceOrb.award((ServerLevel) level, Vec3.atBottomCenterOf(pos), droppedXP);
				this.storedXP = this.storedXP - droppedXP;
				level.gameEvent(null, RegisterGameEvents.TENDRIL_EXTRACT_XP, pos);
			}
		}

		if (this.activeTicks >= HangingTendrilBlock.ACTIVE_TICKS) {
			this.activeTicks = 0;
			HangingTendrilBlock.deactivate(level, pos, state, level.random);
		} else if (state.getValue(HangingTendrilBlock.PHASE) == SculkSensorPhase.ACTIVE) {
			this.activeTicks += 1;
		}
		VibrationSystem.Ticker.tick(level, this.getVibrationData(), this.getVibrationUser());
	}

	public void clientTick(@NotNull BlockState state) {
		this.twitching = this.ticksToStopTwitching > 0;
		this.milking = this.ringOutTicksLeft > 0;
		this.active = !SculkSensorBlock.canActivate(state);
		++this.ticks;
		if (milking) {
			this.texture = WilderConstants.id(BASE_TEXTURE + "milk" + (((this.ticks / MILK_ANIM_SPEED) % MILK_FRAMES) + 1) + ".png");
		} else if (active) {
			this.texture = WilderConstants.id(BASE_TEXTURE + "active" + (((this.ticks / ACTIVE_ANIM_SPEED) % ACTIVE_FRAMES) + 1) + ".png");
		} else if (twitching) {
			this.texture = WilderConstants.id(BASE_TEXTURE + "twitch" + (((this.ticks / TWITCHING_ANIM_SPEED) % TWITCHING_FRAMES) + 1) + ".png");
		} else {
			this.texture = WilderConstants.id(BASE_TEXTURE + "inactive" + (((this.ticks / INACTIVE_ANIM_SPEED) % INACTIVE_FRAMES) + 1) + ".png");
		}
	}

	public int getStoredXP() {
		return this.storedXP;
	}

	public void setStoredXP(int i) {
		this.storedXP = i;
	}

	public int getActiveTicks() {
		return this.activeTicks;
	}

	public void setActiveTicks(int i) {
		this.activeTicks = i;
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
	public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.loadAdditional(tag, provider);
		this.lastVibrationFrequency = tag.getInt("last_vibration_frequency");
		this.ticksToStopTwitching = tag.getInt("ticksToStopTwitching");
		this.storedXP = tag.getInt("storedXP");
		this.ringOutTicksLeft = tag.getInt("ringOutTicksLeft");
		this.activeTicks = tag.getInt("activeTicks");
		if (tag.contains("listener", 10)) {
			VibrationSystem.Data.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.getCompound("listener"))).resultOrPartial(LOGGER::error).ifPresent(data -> this.vibrationData = data);
		}
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.saveAdditional(tag, provider);
		tag.putInt("last_vibration_frequency", this.lastVibrationFrequency);
		if (this.ticksToStopTwitching > 0) tag.putInt("ticksToStopTwitching", this.ticksToStopTwitching);
		if (this.storedXP > 0) tag.putInt("storedXP", this.storedXP);
		if (this.ringOutTicksLeft > 0) tag.putInt("ringOutTicksLeft", this.ringOutTicksLeft);
		if (this.activeTicks > 0) tag.putInt("activeTicks", this.activeTicks);
		VibrationSystem.Data.CODEC.encodeStart(NbtOps.INSTANCE, this.vibrationData).resultOrPartial(LOGGER::error).ifPresent(nbt -> tag.put("listener", nbt));
	}

	@NotNull
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
	@NotNull
	public Listener getListener() {
		return this.vibrationListener;
	}

	@Override
	@NotNull
	public Data getVibrationData() {
		return this.vibrationData;
	}

	@Override
	@NotNull
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
		@NotNull
		public PositionSource getPositionSource() {
			return this.positionSource;
		}

		@Override
		public boolean canTriggerAvoidVibration() {
			return true;
		}

		@Override
		public boolean canReceiveVibration(@NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Holder<GameEvent> gameEvent, @Nullable GameEvent.Context context) {
			if (pos.equals(this.blockPos) && (gameEvent == GameEvent.BLOCK_DESTROY || gameEvent == GameEvent.BLOCK_PLACE)) {
				return false;
			}
			BlockState state = level.getBlockState(HangingTendrilBlockEntity.this.getBlockPos());
			return state.getBlock() instanceof HangingTendrilBlock && HangingTendrilBlock.canActivate(state) && !state.getValue(HangingTendrilBlock.WRINGING_OUT);
		}

		@Override
		public void onReceiveVibration(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull Holder<GameEvent> gameEvent, @Nullable Entity entity, @Nullable Entity entity2, float f) {
			BlockState blockState = HangingTendrilBlockEntity.this.getBlockState();
			if (SculkSensorBlock.canActivate(blockState)) {
				HangingTendrilBlockEntity.this.setLastVibrationFrequency(VibrationSystem.getGameEventFrequency(gameEvent));
				int i = VibrationSystem.getRedstoneStrengthForDistance(f, this.getListenerRadius());
				Block block = blockState.getBlock();
				if (block instanceof HangingTendrilBlock hangingTendrilBlock) {
					hangingTendrilBlock.activate(entity, world, this.blockPos, blockState, gameEvent, i, HangingTendrilBlockEntity.this.getLastVibrationFrequency());
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
