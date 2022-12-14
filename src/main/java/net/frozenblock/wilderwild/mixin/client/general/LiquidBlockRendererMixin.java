package net.frozenblock.wilderwild.mixin.client.general;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(value = LiquidBlockRenderer.class, priority = 69420)
public class LiquidBlockRendererMixin {

    @Inject(method = "shouldRenderFace", at = @At(value = "HEAD"), cancellable = true)
    private static void shouldRenderFace(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, FluidState fluidState, BlockState blockState, Direction side, FluidState fluidState2, CallbackInfoReturnable<Boolean> info) {
        if (blockState.getBlock() instanceof MesogleaBlock && side != Direction.UP && !FrozenBools.HAS_SODIUM) {
            info.setReturnValue(false);
        }
    }

	@Inject(method = "isFaceOccludedByState", at = @At(value = "HEAD"), cancellable = true)
	private static void isFaceOccludedByState(BlockGetter level, Direction face, float height, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> info) {
		if (state.getBlock() instanceof GlassBlock) {
			info.setReturnValue(true);
		}
	}

}
