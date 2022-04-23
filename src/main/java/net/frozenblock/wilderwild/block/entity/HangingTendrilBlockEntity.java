package net.frozenblock.wilderwild.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import net.minecraft.world.event.listener.SculkSensorListener;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Objects;

public class HangingTendrilBlockEntity extends BlockEntity implements SculkSensorListener.Callback {
    private static final Logger LOGGER = LogUtils.getLogger();
    private SculkSensorListener listener;
    private int lastVibrationFrequency;
    public int ticksToStopTwitching;

    public HangingTendrilBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntityType.HANGING_TENDRIL, pos, state);
        this.listener = new SculkSensorListener(new BlockPositionSource(this.pos), ((HangingTendrilBlock)state.getBlock()).getRange(), this, null, 0, 0);
    }

    public void serverTick(World world, BlockPos pos, BlockState state) {
        if (this.ticksToStopTwitching>=0) {--this.ticksToStopTwitching;} else if (state.get(HangingTendrilBlock.TWITCHING)) {
            world.setBlockState(pos, state.with(HangingTendrilBlock.TWITCHING, false));
        }
        this.listener.tick(world);
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.lastVibrationFrequency = nbt.getInt("last_vibration_frequency");
        this.ticksToStopTwitching = nbt.getInt("ticksToStopTwitching");
        if (nbt.contains("listener", 10)) {
            DataResult<?> var10000 = SculkSensorListener.createCodec(this).parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("listener")));
            Logger var10001 = LOGGER;
            Objects.requireNonNull(var10001);
            var10000.resultOrPartial(var10001::error).ifPresent((vibrationListener) -> {
                this.listener = (SculkSensorListener) vibrationListener;
            });
        }
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("last_vibration_frequency", this.lastVibrationFrequency);
        nbt.putInt("ticksToStopTwitching", this.ticksToStopTwitching);
        DataResult<?> var10000 = SculkSensorListener.createCodec(this).encodeStart(NbtOps.INSTANCE, this.listener);
        Logger var10001 = LOGGER;
        Objects.requireNonNull(var10001);
        var10000.resultOrPartial(var10001::error).ifPresent((nbtElement) -> {
            nbt.put("listener", (NbtElement)nbtElement);
        });
    }

    public SculkSensorListener getEventListener() {
        return this.listener;
    }

    public int getLastVibrationFrequency() {
        return this.lastVibrationFrequency;
    }

    public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable GameEvent.Emitter emitter) {
        return (!pos.equals(this.getPos()) || event != GameEvent.BLOCK_DESTROY && event != GameEvent.BLOCK_PLACE) && HangingTendrilBlock.isInactive(this.getCachedState());
    }

    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, int delay) {
        BlockState blockState = this.getCachedState();
        if (HangingTendrilBlock.isInactive(blockState)) {
            this.lastVibrationFrequency = HangingTendrilBlock.FREQUENCIES.getInt(event);
            HangingTendrilBlock.setActive(entity, world, this.pos, blockState, getPower(delay, listener.getRange()));
        }

    }

    public void onListen() {
        this.markDirty();
    }

    public static int getPower(int distance, int range) {
        double d = (double)distance / (double)range;
        return Math.max(1, 15 - MathHelper.floor(d * 15.0D));
    }
}