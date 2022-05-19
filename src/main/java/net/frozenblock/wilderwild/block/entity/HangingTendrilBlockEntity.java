package net.frozenblock.wilderwild.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import net.minecraft.world.event.listener.VibrationListener;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Objects;

public class HangingTendrilBlockEntity extends BlockEntity implements VibrationListener.Callback {
    private static final Logger LOGGER = LogUtils.getLogger();
    private VibrationListener listener;
    private int lastVibrationFrequency;
    public int ticksToStopTwitching;
    public int storedXP;
    public int ringOutTicksLeft;

    public HangingTendrilBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntityType.HANGING_TENDRIL, pos, state);
        this.listener = new VibrationListener(new BlockPositionSource(this.pos), ((HangingTendrilBlock)state.getBlock()).getRange(), this, null, 0, 0);
    }

    public void serverTick(World world, BlockPos pos, BlockState state) {
        if (this.ticksToStopTwitching>0) {--this.ticksToStopTwitching;} else if (this.ticksToStopTwitching==0) {
            world.setBlockState(pos, state.with(HangingTendrilBlock.TWITCHING, false));
            --this.ticksToStopTwitching;
        }
        if (this.ringOutTicksLeft>=0) {--this.ringOutTicksLeft;} else if (state.get(HangingTendrilBlock.WRINGING_OUT)) {
            world.setBlockState(pos, state.with(HangingTendrilBlock.WRINGING_OUT, false));
            if (this.storedXP>0) {
                int droppedXP = this.storedXP>1 ? this.storedXP/2 : 1;
                ExperienceOrbEntity.spawn((ServerWorld)world, Vec3d.ofCenter(pos).add(0, -0.5, 0), droppedXP);
                this.storedXP=this.storedXP-droppedXP;
                world.emitGameEvent(null, RegisterGameEvents.TENDRIL_EXTRACT_XP, pos);
            }
        }
        this.listener.tick(world);
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.lastVibrationFrequency = nbt.getInt("last_vibration_frequency");
        this.ticksToStopTwitching = nbt.getInt("ticksToStopTwitching");
        this.storedXP = nbt.getInt("storedXP");
        this.ringOutTicksLeft = nbt.getInt("ringOutTicksLeft");
        if (nbt.contains("listener", 10)) {
            DataResult<?> var10000 = VibrationListener.createCodec(this).parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("listener")));
            Logger var10001 = LOGGER;
            Objects.requireNonNull(var10001);
            var10000.resultOrPartial(var10001::error).ifPresent((vibrationListener) -> this.listener = (VibrationListener)vibrationListener);
        }
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("last_vibration_frequency", this.lastVibrationFrequency);
        nbt.putInt("ticksToStopTwitching", this.ticksToStopTwitching);
        nbt.putInt("storedXP", this.storedXP);
        nbt.putInt("ringOutTicksLeft", this.ringOutTicksLeft);
        DataResult<?> var10000 = VibrationListener.createCodec(this).encodeStart(NbtOps.INSTANCE, this.listener);
        Logger var10001 = LOGGER;
        Objects.requireNonNull(var10001);
        var10000.resultOrPartial(var10001::error).ifPresent((nbtElement) -> nbt.put("listener", (NbtElement)nbtElement));
    }

    public VibrationListener getEventListener() {
        return this.listener;
    }

    public int getLastVibrationFrequency() {
        return this.lastVibrationFrequency;
    }

    public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable GameEvent.Emitter emitter) {
        return (!pos.equals(this.getPos()) || event != GameEvent.BLOCK_DESTROY && event != GameEvent.BLOCK_PLACE) && HangingTendrilBlock.isInactive(this.getCachedState()) && !this.getCachedState().get(HangingTendrilBlock.WRINGING_OUT);
    }

    @Override
    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float f) {
        BlockState blockState = this.getCachedState();
        if (HangingTendrilBlock.isInactive(blockState)) {
            this.lastVibrationFrequency = SculkSensorBlock.FREQUENCIES.getInt(event);
            HangingTendrilBlock.setActive(world, this.pos, blockState, getPower(f, listener.getRange()));
        }
    }

    public void onListen() {
        this.markDirty();
    }

    public static int getPower(float f, int range) {
        double d = (double)f / (double)range;
        return Math.max(1, 15 - MathHelper.floor(d * 15.0D));
    }
}