package net.frozenblock.wilderwild.mixin.server;

import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SculkVeinBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkVeinBlock.SculkVeinSpreaderConfig.class)
public class SculkVeinGrowCheckerMixin {

    @Inject(at = @At("RETURN"), method = "stateCanBeReplaced", cancellable = true)
    public void newBlocks(BlockGetter world, BlockPos pos, BlockPos growPos, Direction direction, BlockState state, CallbackInfoReturnable<Boolean> info) {
        BlockState blockState = world.getBlockState(growPos.relative(direction));
        if (blockState.is(RegisterBlocks.OSSEOUS_SCULK)) {
            info.setReturnValue(false);
            info.cancel();
        }
    }

}
