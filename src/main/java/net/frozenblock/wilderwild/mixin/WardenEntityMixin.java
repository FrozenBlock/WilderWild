package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.WardenBrain;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WardenEntity.class)
public class WardenEntityMixin {

    @Inject(at = @At("HEAD"), method = "accept")
    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, int delay, CallbackInfo info) {
        if (WilderWild.wardenEmitsSensorClicking) {
            WardenEntity warden = WardenEntity.class.cast(this);
            if (sourceEntity!=null) {
                world.emitGameEvent(sourceEntity, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, warden.getEyePos());
            } else {
                world.emitGameEvent(entity, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, warden.getEyePos());

            }
        }
    }

    @Inject(at = @At("HEAD"), method = "initialize")
    @Nullable
    public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty localDifficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbtCompound, CallbackInfoReturnable info) {
        WardenEntity entity = WardenEntity.class.cast(this);
        entity.getBrain().remember(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, 1200L);
        if (spawnReason == SpawnReason.SPAWN_EGG) {
            entity.setPose(EntityPose.EMERGING);
            entity.getBrain().remember(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, WardenBrain.EMERGE_DURATION);
            entity.setPersistent();
        }
        return (EntityData) info.getReturnValue();
    }

    @Inject(at = @At("HEAD"), method = "pushAway")
    protected void pushAway(Entity entity, CallbackInfo info) {
        WardenEntity warden = WardenEntity.class.cast(this);
        if (!warden.getBrain().hasMemoryModule(MemoryModuleType.TOUCH_COOLDOWN) && !(entity instanceof WardenEntity)) {
            if (!entity.isInvulnerable()) {
                if (!(entity instanceof PlayerEntity player)) {
                    warden.tryAttack(entity);
                } else {
                    if (!player.isCreative()) {
                        warden.tryAttack(entity);
                    }
                }
            }
        }
    }

}
