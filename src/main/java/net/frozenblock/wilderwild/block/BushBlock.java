package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.frozenblock.wilderwild.world.generation.sapling.ShrubBushGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class BushBlock extends net.minecraft.world.level.block.BushBlock implements BonemealableBlock {
	private static final ShrubBushGrower TREE_GROWER = new ShrubBushGrower();
	public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
	public BushBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
	}
	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (state.getValue(AGE) == 0) {
			level.setBlock(pos, state.cycle(AGE), 4);
		}
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return state.is(WilderBlockTags.BUSH_MAY_PLACE_ON) || state.is(BlockTags.DEAD_BUSH_MAY_PLACE_ON);
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, @NotNull BlockState state, boolean isClient) {
		return level.getFluidState(pos.above()).isEmpty();
	}

	@Override
	public boolean isBonemealSuccess(Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return (double)level.random.nextFloat() < 0.45;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		TREE_GROWER.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
	}
}
