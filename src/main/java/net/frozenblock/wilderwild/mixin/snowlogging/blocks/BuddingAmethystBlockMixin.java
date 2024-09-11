package net.frozenblock.wilderwild.mixin.snowlogging.blocks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BuddingAmethystBlock.class)
public class BuddingAmethystBlockMixin {

	@ModifyReturnValue(method = "canClusterGrowAtState", at = @At("RETURN"))
	private static boolean wilderWild$canClusterGrowAtState(boolean original, BlockState state) {
		return original || state.is(Blocks.SNOW);
	}

	@ModifyExpressionValue(
		method = "randomTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState wilderWild$randomTick(BlockState blockState, @Local(ordinal = 1) BlockState oldBlockState) {
		if (!BlockConfig.canSnowlog()) return blockState;
		return SnowloggingUtils.copySnowLayers(oldBlockState, blockState);
	}
}
