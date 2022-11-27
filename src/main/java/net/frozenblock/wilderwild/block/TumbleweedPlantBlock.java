package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TumbleweedPlantBlock extends BushBlock implements BonemealableBlock {
	//TODO: Shapes for stages 0, 1, 2, 3, and 4
	protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 13.0, 16.0);
	//Starts growing tumbleweed at 3
	public static final IntegerProperty AGE = BlockStateProperties.AGE_5;

	public TumbleweedPlantBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (isFullyGrown(state)) {
			if (random.nextInt(0, 5) == 0) {
				level.setBlockAndUpdate(pos, state.setValue(AGE, 2));
				Tumbleweed weed = new Tumbleweed(RegisterEntities.TUMBLEWEED, level);
				level.addFreshEntity(weed);
				weed.setPos(new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5));
				level.playSound(null, pos, RegisterSounds.ENTITY_TUMBLEWEED_DAMAGE, SoundSource.BLOCKS, 1.0F, 1.0F);
				level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
			}
		} else {
			level.setBlockAndUpdate(pos, state.setValue(AGE, state.getValue(AGE) + 1));
		}
	}

	@Override
	public VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
		return blockState.getValue(AGE) < 4 ? Shapes.empty() : super.getCollisionShape(blockState, blockGetter, blockPos, collisionContext);
	}

	@Override
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return state.is(BlockTags.DEAD_BUSH_MAY_PLACE_ON);
	}

	public static boolean isFullyGrown(BlockState state) {
		return state.getValue(AGE) == 5;
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
		return !isFullyGrown(state);
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		level.setBlockAndUpdate(pos, state.setValue(AGE, Math.min(5, state.getValue(AGE) + random.nextInt(1, 2))));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
