package net.frozenblock.wilderwild.mixin.server.general;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.SculkSensorTickInterface;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkSensorBlockEntity.class)
public final class SculkSensorBlockEntityMixin extends BlockEntity implements SculkSensorTickInterface {

    public int animTicks;
    public int prevAnimTicks;
    public int age;
    public boolean active;
    public boolean prevActive;

    private SculkSensorBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(at = @At("HEAD"), method = "onSignalReceive")
    private void onSignalReceive(ServerLevel level, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float f, CallbackInfo info) {
        SculkSensorBlockEntity sculkSensorBlockEntity = SculkSensorBlockEntity.class.cast(this);
        BlockState blockState = sculkSensorBlockEntity.getBlockState();
        if (SculkSensorBlock.canActivate(blockState)) {
            level.gameEvent(entity, RegisterGameEvents.SCULK_SENSOR_ACTIVATE, sculkSensorBlockEntity.getBlockPos());
            BlockState state = level.getBlockState(sculkSensorBlockEntity.getBlockPos());
            level.setBlockAndUpdate(sculkSensorBlockEntity.getBlockPos(), state.setValue(RegisterProperties.HICCUPPING, false));
        }
    }

	@Unique
    @Override
    public void tickServer(ServerLevel level, BlockPos pos, BlockState state) {
        SculkSensorBlockEntity sensor = SculkSensorBlockEntity.class.cast(this);
        sensor.getListener().tick(level);
        boolean bl2 = level.random.nextBoolean();
        if (state.getValue(RegisterProperties.HICCUPPING)) {
            if (bl2) {
                double x = (pos.getX() - 0.1) + (level.random.nextFloat() * 1.2);
                double y = pos.getY() + level.random.nextFloat();
                double z = (pos.getZ() - 0.1) + (level.random.nextFloat() * 1.2);
                EasyPacket.EasySensorHiccupPacket.createParticle(level, new Vec3(x, y, z));
            }
            if (SculkSensorBlock.canActivate(state) && level.random.nextInt(320) <= 1) {
                WilderSharedConstants.log("Sensor Hiccups " + pos, WilderSharedConstants.DEV_LOGGING);
                SculkSensorBlock.activate(null, level, pos, state, (int) (Math.random() * 15));
                level.gameEvent(null, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
                level.gameEvent(null, RegisterGameEvents.SCULK_SENSOR_ACTIVATE, pos);
                level.playSound(null, pos, RegisterSounds.BLOCK_SCULK_SENSOR_HICCUP, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.7F);
            }
        }
        this.prevAnimTicks = this.animTicks;
        if (this.animTicks > 0) {
            --this.animTicks;
        }
        ++this.age;
        this.active = state.getValue(BlockStateProperties.SCULK_SENSOR_PHASE) == SculkSensorPhase.ACTIVE;
        if (this.active != this.prevActive || this.animTicks == 10) {
            for (ServerPlayer player : PlayerLookup.tracking(level, pos)) {
                player.connection.send(sensor.getUpdatePacket());
            }
        }
        this.prevActive = this.active;
    }

	@Unique
    @Override
    public void tickClient(Level level, BlockPos pos, BlockState state) {
		this.prevAnimTicks = this.animTicks;
        if (this.animTicks > 0) {
            --this.animTicks;
        }
        ++this.age;
    }

	@Unique
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(SculkSensorBlockEntity.class.cast(this));
    }

	@Unique
    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    /*@Override
    public boolean onSyncedBlockEvent(int type, int data) {
        this.animTicks = 10;
        return true;
    }*/

	@Unique
    @Override
    public int getAge() {
        return this.age;
    }

	@Unique
    @Override
    public int getAnimTicks() {
        return this.animTicks;
    }

	@Unique
    @Override
    public int getPrevAnimTicks() {
        return this.prevAnimTicks;
    }

	@Unique
    @Override
    public boolean isActive() {
        return this.active;
    }

	@Unique
    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

	@Unique
    @Override
    public void setAnimTicks(int i) {
        this.animTicks = i;
    }

    @Inject(at = @At("TAIL"), method = "load")
    private void load(CompoundTag nbt, CallbackInfo info) {
        this.age = nbt.getInt("age");
        this.animTicks = nbt.getInt("animTicks");
        this.prevAnimTicks = nbt.getInt("prevAnimTicks");
        this.active = nbt.getBoolean("active");
        this.prevActive = nbt.getBoolean("prevActive");
    }

    @Inject(at = @At("TAIL"), method = "saveAdditional")
	private void saveAdditional(CompoundTag nbt, CallbackInfo info) {
        nbt.putInt("age", this.age);
        nbt.putInt("animTicks", this.animTicks);
        nbt.putInt("prevAnimTicks", this.prevAnimTicks);
        nbt.putBoolean("active", this.active);
        nbt.putBoolean("prevActive", this.prevActive);
    }

}
