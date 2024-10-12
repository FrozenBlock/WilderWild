package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class MyceliumGrowthBlock extends BushBlock {
	protected static final VoxelShape SHAPE = Block.box(2D, 0D, 2D, 14D, 7D, 14D);
	public static final MapCodec<MyceliumGrowthBlock> CODEC = simpleCodec(MyceliumGrowthBlock::new);

	public MyceliumGrowthBlock(BlockBehaviour.Properties settings) {
		super(settings);
	}

	@Override
	public @NotNull MapCodec<MyceliumGrowthBlock> codec() {
		return CODEC;
	}

	@Override
	protected @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
