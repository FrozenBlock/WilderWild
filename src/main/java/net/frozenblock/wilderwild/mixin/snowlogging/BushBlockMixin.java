package net.frozenblock.wilderwild.mixin.snowlogging;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BushBlock.class)
public class BushBlockMixin extends Block {
	@Unique
	private static final BooleanProperty WILDERWILD$SNOWLOGGED = RegisterProperties.SNOWLOGGED;

	public BushBlockMixin(Properties properties) {
		super(properties);
	}

	@Unique
	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state) || (state.hasProperty(WILDERWILD$SNOWLOGGED) && state.getValue(WILDERWILD$SNOWLOGGED));
	}

	@Unique
	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WILDERWILD$SNOWLOGGED);
	}

	@Unique
	@Override
	protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		return context.getItemInHand().is(Blocks.SNOW.asItem()) || super.canBeReplaced(state, context);
	}

	@Unique
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
		if (wilderWild$canBeSnowlogged(blockState)) {
			if (blockState.is(this)) {
				if (!wilderWild$isSnowlogged(blockState)) {
					return blockState.setValue(WILDERWILD$SNOWLOGGED, true);
				} else {
					context.getLevel().destroyBlock(context.getClickedPos(), true);
					return Blocks.SNOW.defaultBlockState().setValue(BlockStateProperties.LAYERS, 2);
				}
			}
		}
		return super.getStateForPlacement(context);
	}


	@WrapOperation(
		method = "updateShape",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;updateShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState wilderWild$updateShape(BushBlock instance, BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos, Operation<BlockState> original) {
		if (state.hasProperty(WILDERWILD$SNOWLOGGED) && state.getValue(WILDERWILD$SNOWLOGGED)) {
			if (!Blocks.SNOW.canSurvive(Blocks.SNOW.defaultBlockState(), level, pos)) {
				state = state.setValue(WILDERWILD$SNOWLOGGED, false);
			}
		}
		return original.call(instance, state, direction, neighborState, level, pos, neighborPos);
	}

	@Unique
	private static boolean wilderWild$canBeSnowlogged(BlockState state) {
		return state.hasProperty(WILDERWILD$SNOWLOGGED);
	}

	@Unique
	private static boolean wilderWild$isSnowlogged(BlockState state) {
		return wilderWild$canBeSnowlogged(state) && state.getValue(WILDERWILD$SNOWLOGGED);
	}

}
