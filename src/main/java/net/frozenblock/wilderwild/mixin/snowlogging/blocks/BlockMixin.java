package net.frozenblock.wilderwild.mixin.snowlogging.blocks;

import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SporeBlossomBlock;
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
		Block block = Block.class.cast(this);
		if (
			block instanceof BambooSaplingBlock
			|| block instanceof BushBlock
			|| block instanceof FlowerPotBlock
			|| block instanceof SporeBlossomBlock
			|| block instanceof SugarCaneBlock
		) {
			SnowloggingUtils.addSnowLayersToDefinitionAndBlock(builder, block);
		}
	}
}
