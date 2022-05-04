package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.entity.SculkSensorTendrilEntity;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.Angriness;
import net.minecraft.entity.mob.WardenBrain;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WardenEntity.class)
public class WardenEntityMixin {

    @Inject(at = @At("HEAD"), method = "isValidTarget", cancellable = true)
    public void isValidTarget(@Nullable Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (entity instanceof SculkSensorTendrilEntity) {
            info.setReturnValue(false);
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "initialize")
    public void initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty localDifficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbtCompound, CallbackInfoReturnable<EntityData> info) {
        WardenEntity entity = WardenEntity.class.cast(this);
        entity.getBrain().remember(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, 1200L);
        entity.getBrain().remember(MemoryModuleType.TOUCH_COOLDOWN, Unit.INSTANCE, WardenBrain.EMERGE_DURATION);
        if (spawnReason == SpawnReason.SPAWN_EGG || spawnReason == SpawnReason.COMMAND) {
            entity.setPose(EntityPose.EMERGING);
            entity.getBrain().remember(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, WardenBrain.EMERGE_DURATION);
            entity.setPersistent();
        }
    }

    @Inject(at = @At("HEAD"), method = "pushAway")
    protected void pushAway(Entity entity, CallbackInfo info) {
        WardenEntity warden = WardenEntity.class.cast(this);
        if (!warden.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_COOLING_DOWN) && !warden.getBrain().hasMemoryModule(MemoryModuleType.TOUCH_COOLDOWN) && !(entity instanceof WardenEntity) && !warden.isInPose(EntityPose.EMERGING) && !warden.isInPose(EntityPose.DIGGING) && !warden.isInPose(EntityPose.DYING)) {
            if (!entity.isInvulnerable()) {
                LivingEntity livingEntity = (LivingEntity)entity;
                if (!(entity instanceof PlayerEntity player)) {
                    warden.increaseAngerAt(entity, Angriness.ANGRY.getThreshold() + 20, false);
                    if (warden.getBrain().getOptionalMemory(MemoryModuleType.ROAR_TARGET).isEmpty()) {
                        warden.getBrain().remember(MemoryModuleType.ROAR_TARGET, livingEntity);
                        warden.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
                    }
                    if (!warden.isInPose(EntityPose.ROARING) && !warden.chargingSonicBoomAnimationState.isRunning()) {
                        warden.tryAttack(entity);
                    }
                } else {
                    if (!player.isCreative()) {
                        warden.increaseAngerAt(entity, Angriness.ANGRY.getThreshold() + 20, false);
                        if (warden.getBrain().getOptionalMemory(MemoryModuleType.ROAR_TARGET).isEmpty()) {
                            warden.getBrain().remember(MemoryModuleType.ROAR_TARGET, livingEntity);
                            warden.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
                        }
                        if (!warden.isInPose(EntityPose.ROARING) && !warden.chargingSonicBoomAnimationState.isRunning()) {
                            warden.tryAttack(entity);
                        }
                    }
                }
            }
        }
    }
    /**
     * @author FrozenBlock
     * @reason Proper interaction with hiccuping Sculk Sensors
     */
    @Overwrite
    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, int delay) {
        WardenEntity warden = WardenEntity.class.cast(this);
        int additionalAnger = 0;
        if (world.getBlockState(pos).isOf(Blocks.SCULK_SENSOR)) {
            if (!world.getBlockState(pos).get(RegisterProperties.NOT_HICCUPPING)) { additionalAnger=65; }
        }
        warden.getBrain().remember(MemoryModuleType.VIBRATION_COOLDOWN, Unit.INSTANCE, 40L);
        world.sendEntityStatus(warden, (byte)61);
        warden.playSound(SoundEvents.ENTITY_WARDEN_TENDRIL_CLICKS, 5.0F, warden.getSoundPitch());
        BlockPos blockPos = pos;
        if (sourceEntity != null) {
            if (warden.isInRange(sourceEntity, 30.0D)) {
                if (warden.getBrain().hasMemoryModule(MemoryModuleType.RECENT_PROJECTILE)) {
                    if (warden.isValidTarget(sourceEntity)) {
                        blockPos = sourceEntity.getBlockPos();
                    }

                    warden.increaseAngerAt(sourceEntity);
                    warden.increaseAngerAt(sourceEntity, additionalAnger, false);
                } else {
                    warden.increaseAngerAt(sourceEntity, 10, true);
                    warden.increaseAngerAt(sourceEntity, additionalAnger, false);
                }
            }

            warden.getBrain().remember(MemoryModuleType.RECENT_PROJECTILE, Unit.INSTANCE, 100L);
        } else {
            warden.increaseAngerAt(entity);
            warden.increaseAngerAt(entity, additionalAnger, false);
        }

        if (warden.getAngriness() != Angriness.ANGRY && (sourceEntity != null || (Boolean) warden.getAngerManager().getPrimeSuspect().map((suspect) -> {
            return suspect == entity;
        }).orElse(true))) {
            WardenBrain.lookAtDisturbance(warden, blockPos);
        }

    }
}
