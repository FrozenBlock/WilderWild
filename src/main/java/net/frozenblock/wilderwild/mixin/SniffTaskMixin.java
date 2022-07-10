package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ai.brain.task.SniffTask;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SniffTask.class)
public class SniffTaskMixin<E extends WardenEntity> {

    @Inject(at = @At("HEAD"), method = "finishRunning", cancellable = true)
    protected void finishRunning(ServerWorld serverWorld, E wardenEntity, long l, CallbackInfo info) {
        if (wardenEntity.isInPose(WilderWild.SWIMMING_SNIFFING)) {
            wardenEntity.setPose(EntityPose.STANDING);
        }
    }

}
