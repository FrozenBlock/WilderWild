package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkBlock;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkBlock.class)
public class SculkBlockMixin {

	@Inject(at = @At("TAIL"), method = "getExtraBlockState")
	private BlockState getExtraBlockState(WorldAccess world, BlockPos pos, AbstractRandom random, boolean allowShrieker, CallbackInfoReturnable info) {
		BlockState blockState;
		if (random.nextInt(11) == 0) {
			blockState = (BlockState) Blocks.SCULK_SHRIEKER.getDefaultState().with(SculkShriekerBlock.CAN_SUMMON, allowShrieker);
		} else {
			if (random.nextInt(2) == 0) {
				blockState = RegisterBlocks.SCULK_JAW.getDefaultState();
			} else { blockState = Blocks.SCULK_SENSOR.getDefaultState(); }
		}

		return blockState.contains(Properties.WATERLOGGED) && !world.getFluidState(pos).isEmpty() ? blockState.with(Properties.WATERLOGGED, true) : blockState;
	}
}
