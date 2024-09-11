package net.frozenblock.wilderwild.mixin.snowlogging.blocks.candle;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CandleBlock.class)
public abstract class CandleBlockMixin extends AbstractCandleBlock {

	protected CandleBlockMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "createBlockStateDefinition", at = @At(value = "TAIL"))
	public void wilderWild$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		SnowloggingUtils.addSnowLayersToDefinitionAndBlock(builder, CandleBlock.class.cast(this));
	}

	@ModifyReturnValue(method = "getStateForPlacement", at = @At("RETURN"))
	public BlockState wilderWild$getStateForPlacement(BlockState original, BlockPlaceContext context) {
		return SnowloggingUtils.getSnowPlacementState(original, context);
	}

	@Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
	public void wilderWild$useItemOn(
		ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult,
		CallbackInfoReturnable<ItemInteractionResult> info
	) {
		if (SnowloggingUtils.shouldHitSnow(state, pos, level, player)) {
			info.setReturnValue(super.useItemOn(stack, state, level, pos, player, hand, hitResult));
		}
	}

	@ModifyReturnValue(method = "canLight", at = @At("RETURN"))
	private static boolean wilderWild$canLight(boolean original, BlockState state) {
		return original && (SnowloggingUtils.getSnowLayers(state) < 3);
	}

	@ModifyReturnValue(method = "canBeLit", at = @At("RETURN"))
	public boolean wilderWild$canBeLit(boolean original, BlockState state) {
		return original && (SnowloggingUtils.getSnowLayers(state) < 3);
	}
}
