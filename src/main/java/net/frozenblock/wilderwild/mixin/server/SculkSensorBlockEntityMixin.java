package net.frozenblock.wilderwild.mixin.server;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.SculkSensorTickInterface;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SculkSensorBlockEntity;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkSensorBlockEntity.class)
public abstract class SculkSensorBlockEntityMixin extends BlockEntity implements SculkSensorTickInterface {

    public int animTicks;
    public int prevAnimTicks;
    public int age;
    public boolean active;
    public boolean prevActive;

    public SculkSensorBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(at = @At("HEAD"), method = "accept")
    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float f, CallbackInfo info) {
        SculkSensorBlockEntity sculkSensorBlockEntity = SculkSensorBlockEntity.class.cast(this);
        BlockState blockState = sculkSensorBlockEntity.getCachedState();
        if (SculkSensorBlock.isInactive(blockState)) {
            world.emitGameEvent(entity, RegisterGameEvents.SCULK_SENSOR_ACTIVATE, sculkSensorBlockEntity.getPos());
            BlockState state = world.getBlockState(sculkSensorBlockEntity.getPos());
            //world.setBlockState(sculkSensorBlockEntity.getPos(), state.with(RegisterProperties.HICCUPPING, false));
        }
    }

    @Override
    public void tickServer(ServerWorld world, BlockPos pos, BlockState state) {
        SculkSensorBlockEntity sensor = SculkSensorBlockEntity.class.cast(this);
        sensor.getEventListener().tick(world);
        boolean bl2 = world.random.nextBoolean();
        if (state.get(RegisterProperties.HICCUPPING)) {
            if (bl2) {
                double x = (pos.getX() - 0.1) + (world.random.nextFloat() * 1.2);
                double y = pos.getY() + world.random.nextFloat();
                double z = (pos.getZ() - 0.1) + (world.random.nextFloat() * 1.2);
                EasyPacket.EasySensorHiccupPacket.createParticle(world, new Vec3d(x, y, z));
            }
            if (SculkSensorBlock.isInactive(state) && world.random.nextInt(320) <= 1) {
                WilderWild.log("Sensor Hiccups " + pos, WilderWild.DEV_LOGGING);
                SculkSensorBlock.setActive(null, world, pos, state, (int) (Math.random() * 15));
                world.emitGameEvent(null, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
                world.emitGameEvent(null, RegisterGameEvents.SCULK_SENSOR_ACTIVATE, pos);
                world.playSound(null, pos, RegisterSounds.BLOCK_SCULK_SENSOR_HICCUP, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.7F);
            }
        }
        this.prevAnimTicks = this.animTicks;
        if (this.animTicks > 0) {
            --this.animTicks;
        }
        ++this.age;
        this.active = state.get(Properties.SCULK_SENSOR_PHASE) == SculkSensorPhase.ACTIVE;
        if (this.active != this.prevActive || this.animTicks == 10) {
            for (ServerPlayerEntity player : PlayerLookup.tracking(world, pos)) {
                player.networkHandler.sendPacket(sensor.toUpdatePacket());
            }
        }
        this.prevActive = this.active;
    }

    @Override
    public void tickClient(World world, BlockPos pos, BlockState state) {
        SculkSensorBlockEntity sensor = SculkSensorBlockEntity.class.cast(this);
        this.prevAnimTicks = this.animTicks;
        if (this.animTicks > 0) {
            --this.animTicks;
        }
        ++this.age;
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(SculkSensorBlockEntity.class.cast(this));
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    /*@Override
    public boolean onSyncedBlockEvent(int type, int data) {
        this.animTicks = 10;
        return true;
    }*/

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public int getAnimTicks() {
        return this.animTicks;
    }

    @Override
    public int getPrevAnimTicks() {
        return this.prevAnimTicks;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void setAnimTicks(int i) {
        this.animTicks = i;
    }

    @Inject(at = @At("TAIL"), method = "readNbt")
    public void readNbt(NbtCompound nbt, CallbackInfo info) {
        this.age = nbt.getInt("age");
        this.animTicks = nbt.getInt("animTicks");
        this.prevAnimTicks = nbt.getInt("prevAnimTicks");
        this.active = nbt.getBoolean("active");
        this.prevActive = nbt.getBoolean("prevActive");
    }

    @Inject(at = @At("TAIL"), method = "writeNbt")
    protected void writeNbt(NbtCompound nbt, CallbackInfo info) {
        nbt.putInt("age", this.age);
        nbt.putInt("animTicks", this.animTicks);
        nbt.putInt("prevAnimTicks", this.prevAnimTicks);
        nbt.putBoolean("active", this.active);
        nbt.putBoolean("prevActive", this.prevActive);
    }

}