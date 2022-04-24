package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.entity.SculkSensorTendrilEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.Angriness;
import net.minecraft.entity.mob.WardenBrain;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Unit;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
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
    @Nullable
    public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty localDifficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbtCompound, CallbackInfoReturnable<?> info) {
        WardenEntity entity = WardenEntity.class.cast(this);
        entity.getBrain().remember(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, 1200L);
        entity.getBrain().remember(MemoryModuleType.TOUCH_COOLDOWN, Unit.INSTANCE, WardenBrain.EMERGE_DURATION);
        if (spawnReason == SpawnReason.SPAWN_EGG || spawnReason == SpawnReason.COMMAND) {
            entity.setPose(EntityPose.EMERGING);
            entity.getBrain().remember(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, WardenBrain.EMERGE_DURATION);
            entity.setPersistent();
        }
        return (EntityData) info.getReturnValue();
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
}
