package net.frozenblock.wilderwild.block.entity;

import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HangingTendrilBlockEntity extends BlockEntity implements VibrationListener.VibrationListenerConfig {
	private VibrationListener listener;
	private int lastVibrationFrequency;
	public int ticksToStopTwitching;
	public int storedXP;
	public int ringOutTicksLeft;

	public HangingTendrilBlockEntity(BlockPos pos, BlockState state) {
		super(RegisterBlockEntities.HANGING_TENDRIL, pos, state);
		this.listener = new VibrationListener(new BlockPositionSource(this.worldPosition), ((HangingTendrilBlock) state.getBlock()).getRange(), this, null, 0.0F, 0);
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
		this.listener.tick(level);
	}

	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		this.lastVibrationFrequency = tag.getInt("last_vibration_frequency");
		this.ticksToStopTwitching = tag.getInt("ticksToStopTwitching");
		this.storedXP = tag.getInt("storedXP");
		this.ringOutTicksLeft = tag.getInt("ringOutTicksLeft");
		if (tag.contains("listener", 10)) {
			VibrationListener.codec(this)
					.parse(new Dynamic<>(NbtOps.INSTANCE, tag.getCompound("listener")))
					.resultOrPartial(WilderWild.LOGGER::error)
					.ifPresent(listener -> this.listener = listener);
		}
	}

	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putInt("last_vibration_frequency", this.lastVibrationFrequency);
		tag.putInt("ticksToStopTwitching", this.ticksToStopTwitching);
		tag.putInt("storedXP", this.storedXP);
		tag.putInt("ringOutTicksLeft", this.ringOutTicksLeft);
		VibrationListener.codec(this)
				.encodeStart(NbtOps.INSTANCE, this.listener)
				.resultOrPartial(WilderWild.LOGGER::error)
				.ifPresent(listenerNbt -> tag.put("listener", listenerNbt));
	}

	public VibrationListener getEventListener() {
		return this.listener;
	}

	public int getLastVibrationFrequency() {
		return this.lastVibrationFrequency;
	}

	@Override
	public boolean canTriggerAvoidVibration() {
		return true;
	}

	@Override
	public boolean shouldListen(@NotNull ServerLevel level, @NotNull GameEventListener listener, @NotNull BlockPos pos, @NotNull GameEvent gameEvent, @Nullable GameEvent.Context context) {
		return !this.isRemoved() && (!pos.equals(this.getBlockPos()) || gameEvent != GameEvent.BLOCK_DESTROY && gameEvent != GameEvent.BLOCK_PLACE) && HangingTendrilBlock.isInactive(this.getBlockState()) && !this.getBlockState().getValue(HangingTendrilBlock.WRINGING_OUT);
	}

	@Override
	public void onSignalReceive(@NotNull ServerLevel level, @NotNull GameEventListener listener, @NotNull BlockPos sourcePos, @NotNull GameEvent gameEvent, @Nullable Entity sourceEntity, @Nullable Entity projectileOwner, float distance) {
		BlockState blockState = this.getBlockState();
		if (HangingTendrilBlock.isInactive(blockState)) {
			this.lastVibrationFrequency = SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT.getInt(gameEvent);
			HangingTendrilBlock.setActive(sourceEntity, level, this.worldPosition, blockState, gameEvent, getPower(distance, listener.getListenerRadius()));
		}
	}

	@Override
	public void onSignalSchedule() {
		this.setChanged();
	}

	public static int getPower(float distance, int range) {
		double d = (double) distance / (double) range;
		return Math.max(1, 15 - Mth.floor(d * 15.0D));
	}
}
