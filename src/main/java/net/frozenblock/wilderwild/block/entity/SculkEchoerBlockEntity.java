package net.frozenblock.wilderwild.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.frozenblock.wilderwild.block.SculkEchoerBlock;
import net.frozenblock.wilderwild.misc.SaveableGameEvent;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.tag.WildEventTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class SculkEchoerBlockEntity extends BlockEntity implements VibrationListener.VibrationListenerConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
    private VibrationListener listener;
    private int lastVibrationFrequency;
    public int echoBubblesLeft;
    public boolean bigBubble;
    public IntArrayList bubbleTicks = new IntArrayList();
    public IntArrayList bubbleSizes = new IntArrayList();

    public SaveableGameEvent savedEvent;

    public SculkEchoerBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.SCULK_ECHOER, pos, state);
        this.listener = new VibrationListener(new BlockPositionSource(this.worldPosition), ((SculkEchoerBlock) state.getBlock()).getRange(), this, null, 0.0F, 0);
    }

    public void tick(Level world, BlockPos pos, BlockState state) {
        if (world instanceof ServerLevel server) {
            boolean upsidedown = state.getValue(RegisterProperties.UPSIDE_DOWN);
            boolean waterlogged = state.getValue(BlockStateProperties.WATERLOGGED);
            this.getEventListener().tick(world);
            if (this.echoBubblesLeft > 0) {
                int size = this.bigBubble ? 1 : 0;
                int age = waterlogged ? 60 : 30;
                if (this.bigBubble) {
                    this.bigBubble = false;
                }
                --this.echoBubblesLeft;
                double offest = upsidedown ? (waterlogged ? -0.05 : 0.2) : (waterlogged ? 1.05 : 0.8);
                EasyPacket.EasyFloatingSculkBubblePacket.createParticle(server, new Vec3(pos.getX() + 0.5D, pos.getY() + offest, pos.getZ() + 0.5D), size, age,
                        upsidedown ? (size > 0 ? Math.max((Math.random()) * 0.065, 0.045) * -1 : Math.max((Math.random()) * 0.06, 0.035) * -1) : (size > 0 ? Math.max((Math.random()) * 0.065, 0.045) : Math.max((Math.random()) * 0.06, 0.035)), 1);
                this.bubbleTicks.add(age - 3);
                this.bubbleSizes.add(size);
            }

            if (!bubbleTicks.isEmpty() && !bubbleSizes.isEmpty()) {
                for (int i : bubbleTicks) {
                    int index = bubbleTicks.indexOf(i);
                    int size = bubbleSizes.getInt(index);
                    bubbleTicks.set(index, i - 1);
                    if (i - 1 <= 0) {
                        GameEvent event = size > 0 ? RegisterGameEvents.SCULK_ECHOER_LOUD_ECHO : RegisterGameEvents.SCULK_ECHOER_ECHO;
                        Vec3 emitPos = Vec3.atCenterOf(this.worldPosition).add(0, upsidedown ? -1 : 1, 0);
                        if (this.savedEvent != null) {
                            if (this.savedEvent.isViable()) {
                                world.gameEvent(this.savedEvent.getEntity(world), event, emitPos);
                            }
                            this.savedEvent = null;
                        } else {
                            world.gameEvent(null, event, emitPos);
                        }
                        bubbleTicks.removeInt(index);
                        bubbleSizes.removeInt(index);
                    }
                }
            }
        }
    }

	@Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        this.lastVibrationFrequency = nbt.getInt("last_vibration_frequency");
        this.echoBubblesLeft = nbt.getInt("echoBubblesLeft");
        this.bubbleTicks = IntArrayList.wrap(nbt.getIntArray("bubbleTicksLeft"));
        this.bubbleSizes = IntArrayList.wrap(nbt.getIntArray("bubbleSizes"));
        this.bigBubble = nbt.getBoolean("bigBubble");
        if (nbt.contains("listener", 10)) {
            VibrationListener.codec(this)
                    .parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("listener")))
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(listener -> this.listener = listener);
        }
        this.savedEvent = SaveableGameEvent.readNbt(nbt);
    }

	@Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("last_vibration_frequency", this.lastVibrationFrequency);
        nbt.putInt("echoBubblesLeft", this.echoBubblesLeft);
        nbt.putIntArray("bubbleTicksLeft", this.bubbleTicks);
        nbt.putIntArray("bubbleSizes", this.bubbleSizes);
        nbt.putBoolean("bigBubble", this.bigBubble);
        VibrationListener.codec(this)
                .encodeStart(NbtOps.INSTANCE, this.listener)
                .resultOrPartial(LOGGER::error)
                .ifPresent(listenerNbt -> nbt.put("listener", listenerNbt));
        SaveableGameEvent.writeNbt(nbt, this.savedEvent);
    }

	@Override
    public TagKey<GameEvent> getListenableEvents() {
        return WildEventTags.ECHOER_CAN_LISTEN;
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
    public boolean shouldListen(@NotNull ServerLevel world, @NotNull GameEventListener listener, BlockPos pos, @NotNull GameEvent event, @Nullable GameEvent.Context emitter) {
        return !this.isRemoved() && (!pos.equals(this.getBlockPos()) && (event != GameEvent.BLOCK_DESTROY || event != GameEvent.BLOCK_PLACE)) && SculkEchoerBlock.isInactive(this.getBlockState());
    }

    @Override
    public void onSignalReceive(ServerLevel world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float distance) {
        BlockState blockState = this.getBlockState();
        if (SculkEchoerBlock.isInactive(blockState)) {
            this.lastVibrationFrequency = SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT.getInt(event);
            SculkEchoerBlock.setActive(entity, world, this.worldPosition, blockState, getBubbles(distance, listener.getListenerRadius()));
            this.savedEvent = new SaveableGameEvent(event, Vec3.atCenterOf(pos), sourceEntity != null ? sourceEntity : entity);
        }
    }

	@Override
    public void onSignalSchedule() {
        this.setChanged();
    }

    public static int getBubbles(float distance, int range) {
        double d = (double) distance / (double) range;
        return Math.max(1, 15 - Mth.floor(d * 15.0));
    }

    public void setLastVibrationFrequency(int lastVibrationFrequency) {
        this.lastVibrationFrequency = lastVibrationFrequency;
    }
}
