package net.frozenblock.wilderwild.mixin.snowlogging.blocks;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CandleBlock.class)
public abstract class CandleBlockMixin extends AbstractCandleBlock {
	@Shadow
	@Final
	public static BooleanProperty LIT;

	protected CandleBlockMixin(Properties properties) {
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

	@ModifyReturnValue(method = "getStateForPlacement", at = @At("RETURN"))
	public BlockState wilderWild$getStateForPlacement(BlockState original, BlockPlaceContext context) {
		return SnowloggingUtils.getSnowPlacementState(original, context);
	}

	@Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
	public void wilderWild$useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<ItemInteractionResult> cir) {
		if (SnowloggingUtils.shouldHitSnow(state, pos, level, player)) {
			cir.setReturnValue(super.useItemOn(stack, state, level, pos, player, hand, hitResult));
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

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		if (state.getValue(LIT) && (SnowloggingUtils.getSnowLayers(state) >= 3)) {
			CandleBlock.extinguish(null, state, level, pos);
		}
		super.onPlace(state, level, pos, oldState, movedByPiston);
	}

	@Override
	protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
		if (!SnowloggingUtils.shouldHitSnow(state, hit.getBlockPos(), level, hit.getLocation())) {
			super.onProjectileHit(level, state, hit, projectile);
		}
	}
}
