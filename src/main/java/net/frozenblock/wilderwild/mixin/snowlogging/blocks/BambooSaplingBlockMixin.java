package net.frozenblock.wilderwild.mixin.snowlogging.blocks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BambooSaplingBlock.class)
public class BambooSaplingBlockMixin {

	@WrapOperation(
		method = "randomTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;isEmptyBlock(Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	public boolean wilderWild$randomTick(ServerLevel instance, BlockPos pos, Operation<Boolean> original) {
		return original.call(instance, pos) || instance.getBlockState(pos).is(Blocks.SNOW);
	}

	@WrapOperation(
		method = "isValidBonemealTarget",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z"
		)
	)
	public boolean wilderWild$isValidBonemealTarget(BlockState instance, Operation<Boolean> original) {
		return original.call(instance) || instance.is(Blocks.SNOW);
	}

	@WrapOperation(
		method = "updateShape",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/LevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
		)
	)
	public boolean wilderWild$updateShape(
		LevelAccessor instance, BlockPos pos, BlockState state, int flags, Operation<Boolean> original,
		BlockState originalState
	) {
		if (SnowloggingUtils.isSnowlogged(originalState)) {
			state = SnowloggingUtils.copySnowLayers(originalState, state);
		}
		return original.call(instance, pos, state, flags);
	}

	@WrapOperation(
		method = "growBamboo",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
		)
	)
	public boolean wilderWild$growBamboo(Level instance, BlockPos pos, BlockState state, int flags, Operation<Boolean> original, Level level) {
		BlockState originalState = level.getBlockState(pos);
		state = SnowloggingUtils.copySnowLayers(originalState, state);
		return original.call(instance, pos, state, flags);
	}
}
