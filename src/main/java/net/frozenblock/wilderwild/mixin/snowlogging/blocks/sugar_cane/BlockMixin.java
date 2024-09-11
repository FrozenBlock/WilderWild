package net.frozenblock.wilderwild.mixin.snowlogging.blocks.sugar_cane;

import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {

	@Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
	public void wilderWild$addSnowLayersToBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		if (Block.class.cast(this) instanceof SugarCaneBlock sugarCaneBlock) {
			SnowloggingUtils.addSnowLayersToDefinitionAndBlock(builder, sugarCaneBlock);
		}
	}
}
