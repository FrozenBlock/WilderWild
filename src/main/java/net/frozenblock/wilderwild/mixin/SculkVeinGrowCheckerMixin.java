package net.frozenblock.wilderwild.mixin;

import net.minecraft.block.SculkVeinBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.MemoryModuleType;
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

@Mixin(SculkVeinBlock.SculkVeinGrowChecker.class)
public class SculkVeinGrowCheckerMixin {

    @Inject(at = @At("HEAD"), method = "initialize")
    @Nullable
    public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty localDifficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbtCompound, CallbackInfoReturnable<?> info) {
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
        if (!warden.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_COOLING_DOWN) && !warden.getBrain().hasMemoryModule(MemoryModuleType.TOUCH_COOLDOWN) && !(entity instanceof WardenEntity) && !warden.isInPose(EntityPose.EMERGING) && !warden.isInPose(EntityPose.DIGGING)) {
            if (!entity.isInvulnerable()) {
                if (!(entity instanceof PlayerEntity player)) {
                    warden.tryAttack(entity);
                    warden.increaseAngerAt(entity, 45, false);
                } else {
                    if (!player.isCreative()) {
                        warden.tryAttack(entity);
                        warden.increaseAngerAt(entity, 45, false);
                    }
                }
            }
        }
    }

}
