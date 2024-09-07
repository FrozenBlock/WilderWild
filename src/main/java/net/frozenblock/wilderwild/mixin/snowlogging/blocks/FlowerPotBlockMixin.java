package net.frozenblock.wilderwild.mixin.snowlogging.blocks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FlowerPotBlock.class)
public abstract class FlowerPotBlockMixin extends Block {

	public FlowerPotBlockMixin(Properties settings) {
		super(settings);
	}

	@Unique
	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state) || SnowloggingUtils.isSnowlogged(state);
	}

	@Unique
	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		if (BlockConfig.get().snowlogging.snowlogging) builder.add(SnowloggingUtils.SNOW_LAYERS);
	}

	@Unique
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return SnowloggingUtils.getSnowPlacementState(super.getStateForPlacement(context), context);
	}

	@WrapOperation(method = "useItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	public boolean wilderWild$useItemOn(Level instance, BlockPos pos, BlockState state, int flags, Operation<Boolean> original,
										ItemStack stack, BlockState originalState) {
		if (SnowloggingUtils.isSnowlogged(originalState)) {
			state = SnowloggingUtils.copySnowLayers(originalState, state);
		}
		return original.call(instance, pos, state, flags);
	}

	@WrapOperation(method = "useWithoutItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	public boolean wilderWild$useWithoutItem(Level instance, BlockPos pos, BlockState state, int flags, Operation<Boolean> original,
											 BlockState originalState) {
		if (SnowloggingUtils.isSnowlogged(originalState)) {
			state = SnowloggingUtils.copySnowLayers(originalState, state);
		}
		return original.call(instance, pos, state, flags);
	}
}
