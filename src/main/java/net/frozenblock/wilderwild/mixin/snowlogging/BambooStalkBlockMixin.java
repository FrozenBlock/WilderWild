package net.frozenblock.wilderwild.mixin.snowlogging;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BambooStalkBlock.class)
public abstract class BambooStalkBlockMixin {
	@ModifyReturnValue(method = "isRandomlyTicking", at = @At("RETURN"))
	public boolean wilderWild$isRandomlyTicking(boolean original, BlockState state) {
		return original || SnowloggingUtils.isSnowlogged(state);
	}

	@Inject(method = "createBlockStateDefinition", at = @At(value = "TAIL"))
	public void wilderWild$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		if (BlockConfig.get().snowlogging.snowlogging) builder.add(SnowloggingUtils.SNOW_LAYERS);
	}

	@ModifyReturnValue(method = "getStateForPlacement", at = @At("RETURN"))
	public BlockState wilderWild$getStateForPlacement(BlockState original, BlockPlaceContext context) {
		return SnowloggingUtils.getSnowPlacementState(original, context);
	}

	@WrapOperation(
		method = "randomTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;isEmptyBlock(Lnet/minecraft/core/BlockPos;)Z")
	)
	public boolean wilderWild$randomTick(ServerLevel instance, BlockPos pos, Operation<Boolean> original) {
		return original.call(instance, pos) || instance.getBlockState(pos).is(Blocks.SNOW);
	}

	@WrapOperation(
		method = "performBonemeal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;isEmptyBlock(Lnet/minecraft/core/BlockPos;)Z")
	)
	public boolean wilderWild$performBonemeal(ServerLevel instance, BlockPos pos, Operation<Boolean> original) {
		return original.call(instance, pos) || instance.getBlockState(pos).is(Blocks.SNOW);
	}

	@WrapOperation(
		method = "growBamboo",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z")
	)
	public boolean wilderWild$growBamboo(Level instance, BlockPos pos, BlockState state, int flags, Operation<Boolean> original, @Local(argsOnly = true) Level level) {
		BlockState originalState = level.getBlockState(pos);
		state = SnowloggingUtils.copySnowLayers(originalState, state);
		return original.call(instance, pos, state, flags);
	}
}
