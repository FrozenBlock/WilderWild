package net.frozenblock.wilderwild.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.frozenblock.wilderwild.block.SculkEchoerBlock;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.tag.WildEventTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.TagKey;
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

public class SculkEchoerBlockEntity extends BlockEntity implements VibrationListener.Callback {
    private static final Logger LOGGER = LogUtils.getLogger();
    private VibrationListener listener;
    private int lastVibrationFrequency;
    public int echoBubblesLeft;
    public boolean bigBubble;
    public IntArrayList bubbleTicks = new IntArrayList();
    public IntArrayList bubbleSizes = new IntArrayList();
    public Entity vibrationEntity = null;
    public SculkEchoerBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntityType.SCULK_ECHOER, pos, state);
        this.listener = new VibrationListener(new BlockPositionSource(this.pos), ((SculkEchoerBlock)state.getBlock()).getRange(), this, null, 0.0F, 0);
    }

    public void tick(World world, BlockPos pos, BlockState state) {

        if (world instanceof ServerWorld server) {
            boolean upsidedown = state.get(RegisterProperties.UPSIDE_DOWN);
            boolean waterlogged = state.get(Properties.WATERLOGGED);
            this.getEventListener().tick(world);
            if (this.echoBubblesLeft > 0) {
                int size = this.bigBubble ? 1 : 0;
                int age = waterlogged ? 60 : 30;
                if (this.bigBubble) {this.bigBubble = false; }
                --this.echoBubblesLeft;
                double offest = upsidedown ? (waterlogged ? -0.05 : 0.2) : (waterlogged ? 1.05 : 0.8);
                EasyPacket.EasyFloatingSculkBubblePacket.createParticle(server, new Vec3d(pos.getX() + 0.5D, pos.getY() + offest, pos.getZ() + 0.5D), size, age,
                        upsidedown ? (size>0 ? Math.max((Math.random())*0.065, 0.045)*-1 : Math.max((Math.random())*0.06, 0.035)*-1) : (size>0 ? Math.max((Math.random())*0.065, 0.045) : Math.max((Math.random())*0.06, 0.035)), 1);
                this.bubbleTicks.add(age-3);
                this.bubbleSizes.add(size);
            }

            if (!bubbleTicks.isEmpty() && !bubbleSizes.isEmpty()) {
                for (int i : bubbleTicks) {
                    int index = bubbleTicks.indexOf(i);
                    int size = bubbleSizes.getInt(index);
                    bubbleTicks.set(index, i - 1);
                    if (i - 1 <= 0) {
                        GameEvent event = size>0 ? RegisterGameEvents.SCULK_ECHOER_LOUD_ECHO : RegisterGameEvents.SCULK_ECHOER_ECHO;
                        double offset = upsidedown ? -0.5 : 1.5;
                        if (this.vibrationEntity != null) {
                            world.emitGameEvent(this.vibrationEntity, event, pos.add(0.5, offset, 0.5));
                        } else {
                            world.emitGameEvent(null, event, pos.add(0.5, offset, 0.5));
                        }
                        bubbleTicks.removeInt(index);
                        bubbleSizes.removeInt(index);
                        this.vibrationEntity = null;
                    }
                }
            }
        }
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.lastVibrationFrequency = nbt.getInt("last_vibration_frequency");
        this.echoBubblesLeft = nbt.getInt("echoBubblesLeft");
        this.bubbleTicks = IntArrayList.wrap(nbt.getIntArray("bubbleTicksLeft"));
        this.bubbleSizes = IntArrayList.wrap(nbt.getIntArray("bubbleSizes"));
        this.bigBubble = nbt.getBoolean("bigBubble");
        if (nbt.contains("listener", 10)) {
            VibrationListener.createCodec(this)
                .parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("listener")))
                .resultOrPartial(LOGGER::error)
                .ifPresent(listener -> this.listener = listener);
        }
    }

    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("last_vibration_frequency", this.lastVibrationFrequency);
        nbt.putInt("echoBubblesLeft", this.echoBubblesLeft);
        nbt.putIntArray("bubbleTicksLeft", this.bubbleTicks);
        nbt.putIntArray("bubbleSizes", this.bubbleSizes);
        nbt.putBoolean("bigBubble", this.bigBubble);
        VibrationListener.createCodec(this)
            .encodeStart(NbtOps.INSTANCE, this.listener)
            .resultOrPartial(LOGGER::error)
            .ifPresent(listenerNbt -> nbt.put("listener", listenerNbt));
    }

    public TagKey<GameEvent> getTag() {
        return WildEventTags.ECHOER_CAN_LISTEN;
    }

    public VibrationListener getEventListener() {
        return this.listener;
    }

    public int getLastVibrationFrequency() {
        return this.lastVibrationFrequency;
    }

    public boolean triggersAvoidCriterion() {
        return true;
    }

    @Override
    public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable GameEvent.Emitter emitter) {
        return (!pos.equals(this.getPos()) && (event != GameEvent.BLOCK_DESTROY || event != GameEvent.BLOCK_PLACE)) && SculkEchoerBlock.isInactive(this.getCachedState());
    }

    @Override
    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float distance) {
        BlockState blockState = this.getCachedState();
        if (SculkEchoerBlock.isInactive(blockState)) {
            this.lastVibrationFrequency = SculkEchoerBlock.FREQUENCIES.getInt(event);
            SculkEchoerBlock.setActive(entity, world, this.pos, blockState, getBubbles(distance, listener.getRange()));
        }
    }

    public void onListen() {
        this.markDirty();
    }

    public static int getBubbles(float distance, int range) {
        double d = (double)distance / (double)range;
        return Math.max(1, 15 - MathHelper.floor(d * 15.0));
    }

    public void setLastVibrationFrequency(int lastVibrationFrequency) {
        this.lastVibrationFrequency = lastVibrationFrequency;
    }
}