package net.frozenblock.wilderwild.mixin.snowlogging.blocks;

import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SeaPickleBlock.class)
public abstract class SeaPickleBlockMixin {
	@Inject(method = "createBlockStateDefinition", at = @At(value = "TAIL"))
	public void wilderWild$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		SnowloggingUtils.addSnowLayersToDefinition(builder);
	}
}
