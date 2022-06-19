package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.SculkShriekerBlockEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkShriekerBlockEntity.class)
public class SculkShriekerBlockEntityMixin {

    @Inject(at = @At("HEAD"), method = "canWarn", cancellable = true)
    private void canWarn(ServerWorld world, CallbackInfoReturnable<Boolean> info) {
        SculkShriekerBlockEntity entity = SculkShriekerBlockEntity.class.cast(this);
        BlockState blockState = entity.getCachedState();
        if (blockState.get(RegisterProperties.SOULS_TAKEN) == 2) {
            WilderWild.log(Blocks.SCULK_SHRIEKER, entity.getPos(), "All Souls Have Already Been Taken, Cannot Warn", WilderWild.DEV_LOGGING);
            info.setReturnValue(false);
            info.cancel();
        }
    }
}
