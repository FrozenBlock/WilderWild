package net.frozenblock.wilderwild.mixin.snowlogging;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TallGrassBlock.class)
public class TallGrassBlockMixin {
	@WrapOperation(
		method = "performBonemeal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/DoublePlantBlock;placeAt(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;I)V")
	)
	public void wilderWild$performBonemeal(LevelAccessor world, BlockState state, BlockPos pos, int flags, Operation<Void> original,
										   @Local(argsOnly = true) BlockState originalState
	) {
		state = SnowloggingUtils.copySnowLayers(originalState, state);
		original.call(world, state, pos, flags);
	}
}
