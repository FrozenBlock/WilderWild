package net.frozenblock.wilderwild.mixin.server.general;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BigDripleafBlock;
import net.minecraft.world.level.block.BigDripleafStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public final class BlockMixin {

	@Inject(at = @At("RETURN"), method = "getStateForPlacement", cancellable = true)
	public void wilderWild$getStateForPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> info) {
		if (BlockBehaviour.class.cast(this) instanceof BigDripleafStemBlock bigDripleafStemBlock) {
			info.setReturnValue(bigDripleafStemBlock.defaultBlockState().setValue(BlockStateProperties.POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos())));
		} else if (BlockBehaviour.class.cast(this) instanceof BigDripleafBlock bigDripleafBlock) {
			info.setReturnValue(bigDripleafBlock.defaultBlockState().setValue(BlockStateProperties.POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos())));
		}
	}

}
