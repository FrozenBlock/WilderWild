package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TumbleweedPlantBlock extends BushBlock implements BonemealableBlock {
	private static final VoxelShape FIRST_SHAPE = Block.box(3, 0, 3, 12, 9, 12);
	private static final VoxelShape SECOND_SHAPE = Block.box(2, 0, 2, 14, 12, 14);
	private static final VoxelShape THIRD_SHAPE = Block.box(1, 0, 1, 15, 14, 15);
	private static final VoxelShape FOURTH_SHAPE = Block.box(1, 0, 1, 15, 14, 15);
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

	public TumbleweedPlantBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (isFullyGrown(state)) {
			if (random.nextInt(0, 2) == 0) {
				level.setBlockAndUpdate(pos, state.setValue(AGE, 0));
				Tumbleweed weed = new Tumbleweed(RegisterEntities.TUMBLEWEED, level);
				level.addFreshEntity(weed);
				weed.setPos(new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5));
				if (level.random.nextInt(0, 15) == 0) {
					weed.setItem(new ItemStack(RegisterBlocks.TUMBLEWEED_PLANT), true);
				}
				level.playSound(null, pos, RegisterSounds.ENTITY_TUMBLEWEED_DAMAGE, SoundSource.BLOCKS, 1.0F, 1.0F);
				level.levelEvent(2001, pos, Block.getId(state));
				level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
			}
		} else {
			level.setBlockAndUpdate(pos, state.setValue(AGE, state.getValue(AGE) + 1));
		}
	}

	@Override
	public VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
		return blockState.getValue(AGE) < 2 ? Shapes.empty() : super.getCollisionShape(blockState, blockGetter, blockPos, collisionContext);
	}

	@Override
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return switch (state.getValue(AGE)) {
			case 0 -> FIRST_SHAPE;
			case 1 -> SECOND_SHAPE;
			case 2 -> THIRD_SHAPE;
			default -> FOURTH_SHAPE;
		};
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return state.is(BlockTags.DEAD_BUSH_MAY_PLACE_ON) || state.is(BlockTags.DIRT) || state.is(Blocks.FARMLAND) || state.is(WilderBlockTags.BUSH_MAY_PLACE_ON);
	}

	public static boolean isFullyGrown(BlockState state) {
		return state.getValue(AGE) == 3;
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
		return !isFullyGrown(state);
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		level.setBlockAndUpdate(pos, state.setValue(AGE, Math.min(3, state.getValue(AGE) + random.nextIntBetweenInclusive(1, 2))));
	}

	@Override
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.is(Items.SHEARS) && isFullyGrown(state)) {
			if (!level.isClientSide) {
				level.playSound(null, pos, RegisterSounds.BLOCK_TUMBLEWEED_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
				Tumbleweed weed = new Tumbleweed(RegisterEntities.TUMBLEWEED, level);
				level.addFreshEntity(weed);
				weed.setPos(new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5));
				level.levelEvent(2001, pos, Block.getId(state));
				weed.spawnedFromShears = true;
				level.setBlockAndUpdate(pos, state.setValue(AGE, 0));
				itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
				level.gameEvent(player, GameEvent.SHEAR, pos);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return super.use(state, level, pos, player, hand, hit);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
