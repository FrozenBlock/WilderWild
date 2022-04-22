package net.frozenblock.wilderwild.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.block.SculkEchoerBlock;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WildEventTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import net.minecraft.world.event.listener.SculkSensorListener;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Objects;

public class SculkEchoerBlockEntity extends BlockEntity implements SculkSensorListener.Callback {
    private static final Logger LOGGER = LogUtils.getLogger();
    private SculkSensorListener listener;
    private int lastVibrationFreq;
    public SculkEchoerBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntityType.SCULK_ECHOER, pos, state);
        this.listener = new SculkSensorListener(new BlockPositionSource(this.pos), ((SculkEchoerBlock)state.getBlock()).getTendrilRange(), this, null, 0, 0);
    }

    public SculkSensorListener getListener() {
        return this.listener;
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.lastVibrationFreq = nbt.getInt("last_vibration_frequency");

        if (nbt.contains("listener", 10)) {
            DataResult<?> var10000 = SculkSensorListener.createCodec(this).parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("listener")));
            Logger var10001 = LOGGER;
            Objects.requireNonNull(var10001);
            var10000.resultOrPartial(var10001::error).ifPresent((vibrationListener) -> {
                this.listener = (SculkSensorListener) vibrationListener;
            });
        }
    }

    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("last_vibration_frequency", this.lastVibrationFreq);
        DataResult<?> var10000 = SculkSensorListener.createCodec(this).encodeStart(NbtOps.INSTANCE, this.listener);
        Logger var10001 = LOGGER;
        Objects.requireNonNull(var10001);
        var10000.resultOrPartial(var10001::error).ifPresent((nbtElement) -> {
            nbt.put("listener", (NbtElement)nbtElement);
        });
    }

    public TagKey<GameEvent> getTag() {
        return WildEventTags.ECHOER_CAN_LISTEN;
    }

    public SculkSensorListener getEventListener() {
        return this.listener;
    }

    public int getLastVibrationFreq() {
        return this.lastVibrationFreq;
    }

    @Override
    public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, GameEvent.Emitter arg) {
        if (world.getBlockState(this.getPos()).getBlock() instanceof SculkEchoerBlock echoer) {
            boolean accepts = this.getPos().isWithinDistance(pos, echoer.getRange()+1);
            if (world.getBlockState(pos).isOf(RegisterBlocks.HANGING_TENDRIL)) { accepts=true; }
            return (!pos.equals(this.getPos()) && (event != GameEvent.BLOCK_DESTROY || event != GameEvent.BLOCK_PLACE)) && SculkEchoerBlock.isInactive(this.getCachedState()) && accepts;
        } return false;
    }

    @Override
    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, int delay) {
        BlockState blockState = this.getCachedState();
        if (SculkEchoerBlock.isInactive(blockState)) {
            this.lastVibrationFreq = SculkEchoerBlock.FREQUENCIES.getInt(event);
            SculkEchoerBlock.setActive(entity, world, this.pos, blockState, getPower(delay, listener.getRange()));
        }
    }


    public void onListen() {
        this.markDirty();
    }

    public static int getPower(int distance, int range) {
        double d = (double)distance / (double)range;
        return Math.max(1, 15 - MathHelper.floor(d * 15.0));
    }
}