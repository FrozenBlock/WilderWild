package net.frozenblock.wilderwild.mixin.snowlogging;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.CampfireBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends BaseEntityBlock {
	protected CampfireBlockMixin(Properties properties) {
		super(properties);
	}

//	@Unique
//	@Override
//	protected boolean isRandomlyTicking(BlockState state) {
//		return super.isRandomlyTicking(state) || SnowloggingUtils.isSnowlogged(state);
//	}
//
//	@Inject(method = "createBlockStateDefinition", at = @At(value = "TAIL"))
//	public void wilderWild$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
//		if (BlockConfig.canSnowlog()) builder.add(SnowloggingUtils.SNOW_LAYERS);
//	}
//
//	@ModifyReturnValue(method = "getStateForPlacement", at = @At("RETURN"))
//	private BlockState getStateForPlacement(BlockState original, BlockPlaceContext context) {
//		return SnowloggingUtils.getSnowPlacementState(original, context);
//	}
}
