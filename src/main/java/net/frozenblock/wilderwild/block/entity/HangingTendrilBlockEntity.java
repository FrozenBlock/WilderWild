/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.block.entity;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

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
	private static final String BASE_TEXTURE = "textures/entity/hanging_tendril/";

	private final VibrationSystem.Listener vibrationListener;
	private final VibrationSystem.User vibrationUser = this.createVibrationUser();
	private VibrationSystem.Data vibrationData;
	private int lastVibrationFrequency;
	public int ticksToStopTwitching;
	private int storedXP;
	public int ringOutTicksLeft;
	private int activeTicks;

	//CLIENT ONLY
	private Identifier texture = WWConstants.id("textures/entity/hanging_tendril/inactive1.png");

	public HangingTendrilBlockEntity(BlockPos pos, BlockState state) {
		super(WWBlockEntityTypes.HANGING_TENDRIL, pos, state);
		this.vibrationData = new VibrationSystem.Data();
		this.vibrationListener = new VibrationSystem.Listener(this);
	}

	public void serverTick(Level level, BlockPos pos, BlockState state) {
		if (this.ticksToStopTwitching <= 0) level.setBlockAndUpdate(pos, state.setValue(HangingTendrilBlock.TWITCHING, false));

		--this.ticksToStopTwitching;
		if (this.ringOutTicksLeft >= 0) {
			--this.ringOutTicksLeft;
		} else if (state.getValue(HangingTendrilBlock.WRINGING_OUT)) {
			state = state.setValue(HangingTendrilBlock.WRINGING_OUT, false);
			level.setBlockAndUpdate(pos, state);
			if (this.storedXP > 0) {
				final int droppedXP = this.storedXP > 1 ? (int) (this.storedXP * MILK_XP_PERCENTAGE) : 1;
				ExperienceOrb.award((ServerLevel) level, Vec3.atBottomCenterOf(pos), droppedXP);
				this.storedXP = this.storedXP - droppedXP;
				level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
			}
		}

		if (this.activeTicks >= HangingTendrilBlock.ACTIVE_TICKS) {
			this.activeTicks = 0;
			HangingTendrilBlock.deactivate(level, pos, state, level.random);
		} else if (state.getValue(HangingTendrilBlock.PHASE) == SculkSensorPhase.ACTIVE) {
			this.activeTicks += 1;
		}
		Ticker.tick(level, this.getVibrationData(), this.getVibrationUser());
	}

	public void clientTick(Level level, BlockState state) {
		if (!level.isClientSide()) return;
		final boolean twitching = this.ticksToStopTwitching > 0;
		final boolean milking = this.ringOutTicksLeft > 0;
		final boolean active = !SculkSensorBlock.canActivate(state);
		final long time = level.getGameTime();
		if (milking) {
			this.texture = WWConstants.id(BASE_TEXTURE + "milk" + (((time / MILK_ANIM_SPEED) % MILK_FRAMES) + 1) + ".png");
		} else if (active) {
			this.texture = WWConstants.id(BASE_TEXTURE + "active" + (((time / ACTIVE_ANIM_SPEED) % ACTIVE_FRAMES) + 1) + ".png");
		} else if (twitching) {
			this.texture = WWConstants.id(BASE_TEXTURE + "twitch" + (((time / TWITCHING_ANIM_SPEED) % TWITCHING_FRAMES) + 1) + ".png");
		} else {
			this.texture = WWConstants.id(BASE_TEXTURE + "inactive" + (((time / INACTIVE_ANIM_SPEED) % INACTIVE_FRAMES) + 1) + ".png");
		}
	}

	public Identifier getClientTexture() {
		return this.texture;
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

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(this.problemPath(), WWConstants.LOGGER)) {
			TagValueOutput tagValueOutput = TagValueOutput.createWithContext(scopedCollector, provider);
			this.saveClientUsableNbt(tagValueOutput);
			return tagValueOutput.buildResult();
		}
	}

	@Override
	public void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);
		this.lastVibrationFrequency = valueInput.getIntOr("last_vibration_frequency", 0);
		this.ticksToStopTwitching = valueInput.getIntOr("ticksToStopTwitching", 0);
		this.storedXP = valueInput.getIntOr("storedXP", 0);
		this.ringOutTicksLeft = valueInput.getIntOr("ringOutTicksLeft", 0);
		this.activeTicks = valueInput.getIntOr("activeTicks", 0);

		this.vibrationData = valueInput.read("listener", VibrationSystem.Data.CODEC).orElseGet(VibrationSystem.Data::new);
	}

	@Override
	protected void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);
		valueOutput.putInt("last_vibration_frequency", this.lastVibrationFrequency);
		if (this.storedXP > 0) valueOutput.putInt("storedXP", this.storedXP);
		if (this.activeTicks > 0) valueOutput.putInt("activeTicks", this.activeTicks);
		this.saveClientUsableNbt(valueOutput);

		valueOutput.store("listener", VibrationSystem.Data.CODEC, this.vibrationData);
	}

	public void saveClientUsableNbt(ValueOutput valueOutput) {
		if (this.ticksToStopTwitching > 0) valueOutput.putInt("ticksToStopTwitching", this.ticksToStopTwitching);
		if (this.ringOutTicksLeft > 0) valueOutput.putInt("ringOutTicksLeft", this.ringOutTicksLeft);
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
		public boolean canReceiveVibration(
			ServerLevel level,
			BlockPos pos,
			Holder<GameEvent> gameEvent,
			@Nullable GameEvent.Context context
		) {
			if (pos.equals(this.blockPos) && (gameEvent == GameEvent.BLOCK_DESTROY || gameEvent == GameEvent.BLOCK_PLACE)) return false;
			final BlockState state = level.getBlockState(HangingTendrilBlockEntity.this.getBlockPos());
			return state.getBlock() instanceof HangingTendrilBlock && HangingTendrilBlock.canActivate(state) && !state.getValue(HangingTendrilBlock.WRINGING_OUT);
		}

		@Override
		public void onReceiveVibration(
			ServerLevel level,
			BlockPos pos,
			Holder<GameEvent> gameEvent,
			@Nullable Entity entity,
			@Nullable Entity entity2,
			float f
		) {
			final BlockState state = HangingTendrilBlockEntity.this.getBlockState();
			if (!SculkSensorBlock.canActivate(state)) return;
			HangingTendrilBlockEntity.this.setLastVibrationFrequency(VibrationSystem.getGameEventFrequency(gameEvent));
			final int redstoneStrength = VibrationSystem.getRedstoneStrengthForDistance(f, this.getListenerRadius());
			if (!(state.getBlock() instanceof HangingTendrilBlock hangingTendril)) return;
			hangingTendril.activate(entity, level, this.blockPos, state, gameEvent, redstoneStrength, HangingTendrilBlockEntity.this.getLastVibrationFrequency());
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
