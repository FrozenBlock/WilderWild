package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.block.entity.PalmCrownBlockEntity;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PalmCrownBlock extends BaseEntityBlock {
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

	public PalmCrownBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new PalmCrownBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
		return !level.isClientSide ? createTickerHelper(blockEntityType, RegisterBlockEntities.PALM_CROWN, (world1, blockPos, blockState1, palmCrownBlockEntity) -> palmCrownBlockEntity.tick()) : null;
	}

	@Override
	public RenderShape getRenderShape(@NotNull BlockState blockState) {
		return RenderShape.MODEL;
	}

	public BlockState rotate(@NotNull BlockState state, @NotNull Rotation rotation) {
		return RotatedPillarBlock.rotatePillar(state, rotation);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
	}
}
