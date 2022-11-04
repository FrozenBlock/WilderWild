package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.world.gen.sapling.ShrubBushGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShrubBlock extends BushBlock implements BonemealableBlock {
	protected static final float AABB_OFFSET = 6.0F;
	protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);
	private static final ShrubBushGrower TREE_GROWER = new ShrubBushGrower();

	public ShrubBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(BlockTags.DEAD_BUSH_MAY_PLACE_ON);
	}
	public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
		return level.getFluidState(pos.above()).isEmpty();
	}

	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return (double)level.random.nextFloat() < 0.45;
	}

	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		TREE_GROWER.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
	}
}
