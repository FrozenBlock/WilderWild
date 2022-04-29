package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.block.entity.SculkShriekerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkShriekerBlockEntity.class)
public class SculkShriekerBlockEntityMixin {
    @Shadow
    private int warningLevel;

    @Inject(at = @At("HEAD"), method = "canWarn", cancellable = true)
    private void canWarn(ServerWorld world, CallbackInfoReturnable<Boolean> info) {
        SculkShriekerBlockEntity entity = SculkShriekerBlockEntity.class.cast(this);
        BlockState blockState = entity.getCachedState();
        if (blockState.get(RegisterProperties.SOULS_TAKEN) == 2) {
            info.setReturnValue(false);
            info.cancel();
        }
    }

    public void warn(ServerWorld world) {
        SculkShriekerBlockEntity entity = SculkShriekerBlockEntity.class.cast(this);
        if (entity.getCachedState().get(SculkShriekerBlock.CAN_SUMMON) || entity.getCachedState().get(RegisterProperties.SOULS_TAKEN) > 0) {
            WardenEntity.addDarknessToClosePlayers(world, Vec3d.ofCenter(entity.getPos()), null, 40);
            if (this.warningLevel >= 3 || entity.getCachedState().get(RegisterProperties.SOULS_TAKEN) > 0) {
                trySpawnWarden(world, entity.getPos());
                return;
            }
        }

        this.playWarningSound();
    }

    @Shadow
    private void playWarningSound() {
    }
    @Shadow
    private static void trySpawnWarden(ServerWorld world, BlockPos pos) {}
}
