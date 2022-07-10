package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ai.brain.task.RoarTask;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RoarTask.class)
public class RoarTaskMixin {

    @Inject(at = @At("TAIL"), method = "run", cancellable = true)
    private void run(ServerWorld serverWorld, WardenEntity wardenEntity, long l, CallbackInfo info) {
        wardenEntity.setPose(wardenEntity.isSwimming() ? WilderWild.SWIMMING_ROARING : EntityPose.ROARING);
    }

    @Inject(at = @At("HEAD"), method = "finishRunning", cancellable = true)
    private void finishRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l, CallbackInfo info) {
        if (wardenEntity.isInPose(WilderWild.SWIMMING_ROARING)) {
            wardenEntity.setPose(EntityPose.STANDING);
        }
    }

}
