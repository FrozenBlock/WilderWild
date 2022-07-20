package net.frozenblock.wilderwild.block.entity;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
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
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Objects;

public class HangingTendrilBlockEntity extends BlockEntity implements VibrationListener.VibrationListenerConfig {
    private VibrationListener listener;
    private int lastVibrationFrequency;
    public int ticksToStopTwitching;
    public int storedXP;
    public int ringOutTicksLeft;

    public HangingTendrilBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.HANGING_TENDRIL, pos, state);
        this.listener = new VibrationListener(new BlockPositionSource(this.worldPosition), 4, this, null, 0, 0);
    }

    public void serverTick(Level world, BlockPos pos, BlockState state) {
        if (this.ticksToStopTwitching > 0) {
            --this.ticksToStopTwitching;
        } else if (this.ticksToStopTwitching == 0) {
            world.setBlockAndUpdate(pos, state.setValue(HangingTendrilBlock.TWITCHING, false));
            --this.ticksToStopTwitching;
        }
        if (this.ringOutTicksLeft >= 0) {
            --this.ringOutTicksLeft;
        } else if (state.getValue(HangingTendrilBlock.WRINGING_OUT)) {
            world.setBlockAndUpdate(pos, state.setValue(HangingTendrilBlock.WRINGING_OUT, false));
            if (this.storedXP > 0) {
                int droppedXP = this.storedXP > 1 ? this.storedXP / 2 : 1;
                ExperienceOrb.award((ServerLevel) world, Vec3.atCenterOf(pos).add(0, -0.5, 0), droppedXP);
                this.storedXP = this.storedXP - droppedXP;
                world.gameEvent(null, RegisterGameEvents.TENDRIL_EXTRACT_XP, pos);
            }
        }
        this.listener.tick(world);
    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.lastVibrationFrequency = nbt.getInt("last_vibration_frequency");
        this.ticksToStopTwitching = nbt.getInt("ticksToStopTwitching");
        this.storedXP = nbt.getInt("storedXP");
        this.ringOutTicksLeft = nbt.getInt("ringOutTicksLeft");
        if (nbt.contains("listener", 10)) {
            DataResult<?> var10000 = VibrationListener.codec(this).parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("listener")));
            Logger var10001 = WilderWild.LOGGER;
            Objects.requireNonNull(var10001);
            var10000.resultOrPartial(var10001::error).ifPresent((vibrationListener) -> this.listener = (VibrationListener) vibrationListener);
        }
    }

    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("last_vibration_frequency", this.lastVibrationFrequency);
        nbt.putInt("ticksToStopTwitching", this.ticksToStopTwitching);
        nbt.putInt("storedXP", this.storedXP);
        nbt.putInt("ringOutTicksLeft", this.ringOutTicksLeft);
        DataResult<?> var10000 = VibrationListener.codec(this).encodeStart(NbtOps.INSTANCE, this.listener);
        Logger var10001 = WilderWild.LOGGER;
        Objects.requireNonNull(var10001);
        var10000.resultOrPartial(var10001::error).ifPresent((nbtElement) -> nbt.put("listener", (Tag) nbtElement));
    }

    public VibrationListener getEventListener() {
        return this.listener;
    }

    public int getLastVibrationFrequency() {
        return this.lastVibrationFrequency;
    }

    public boolean shouldListen(ServerLevel world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable GameEvent.Context emitter) {
        return (!pos.equals(this.getBlockPos()) || event != GameEvent.BLOCK_DESTROY && event != GameEvent.BLOCK_PLACE) && HangingTendrilBlock.isInactive(this.getBlockState()) && !this.getBlockState().getValue(HangingTendrilBlock.WRINGING_OUT);
    }

    @Override
    public void onSignalReceive(ServerLevel world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float f) {
        BlockState blockState = this.getBlockState();
        if (HangingTendrilBlock.isInactive(blockState)) {
            this.lastVibrationFrequency = SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT.getInt(event);
            HangingTendrilBlock.setActive(world, this.worldPosition, blockState, getPower(f, listener.getListenerRadius()));
        }
    }

    public void onSignalSchedule() {
        this.setChanged();
    }

    public static int getPower(float f, int range) {
        double d = (double) f / (double) range;
        return Math.max(1, 15 - Mth.floor(d * 15.0D));
    }
}