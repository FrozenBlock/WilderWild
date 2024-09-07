package net.frozenblock.wilderwild.mixin.snowlogging.blocks;

import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GlowLichenBlock.class)
public abstract class GlowLichenBlockMixin extends MultifaceBlock {
	public GlowLichenBlockMixin(Properties properties) {
		super(properties);
	}

	@Unique
	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state) || SnowloggingUtils.isSnowlogged(state);
	}

	@Inject(method = "createBlockStateDefinition", at = @At(value = "TAIL"))
	public void wilderWild$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		SnowloggingUtils.addSnowLayersToDefinition(builder);
	}

	@Unique
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return SnowloggingUtils.getSnowPlacementState(super.getStateForPlacement(context), context);
	}
}
