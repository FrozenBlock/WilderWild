/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoconutBlock extends FallingBlock implements BonemealableBlock {
	public static final double GROWTH_CHANCE_HANGING = 0.4D;
	public static final int VALID_FROND_DISTANCE = 2;
	public static final int MAX_AGE = 2;
	public static final float FALL_DAMAGE_PER_DISTANCE = 0.5F;
	public static final int MAX_FALL_DAMAGE = 3;
	public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
	public static final MapCodec<CoconutBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		TreeGrower.CODEC.fieldOf("tree").forGetter((coconutBlock) -> coconutBlock.treeGrower),
		propertiesCodec()
	).apply(instance, CoconutBlock::new));
	private static final VoxelShape[] SHAPES = new VoxelShape[]{
		Shapes.or(Block.box(2D, 9D, 2D, 14D, 16D, 14D)),
		Shapes.or(Block.box(1D, 8D, 1D, 15D, 16D, 15D)),
		Shapes.or(Block.box(0D, 5D, 0D, 16D, 16D, 15D)),
		Block.box(2D, 0D, 2D, 14D, 12D, 14D)
	};
	private final TreeGrower treeGrower;

	public CoconutBlock(TreeGrower treeGrower, @NotNull Properties settings) {
		super(settings);
		this.treeGrower = treeGrower;
		this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0).setValue(AGE, 0).setValue(HANGING, false));
	}

	private static boolean isHanging(@NotNull BlockState state) {
		return state.getValue(HANGING);
	}

	private static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == MAX_AGE;
	}

	@NotNull
	public static BlockState getDefaultHangingState() {
		return getHangingState(0);
	}

	@NotNull
	public static BlockState getHangingState(int age) {
		return WWBlocks.COCONUT.defaultBlockState().setValue(HANGING, true).setValue(AGE, age);
	}

	@NotNull
	@Override
	protected MapCodec<? extends CoconutBlock> codec() {
		return CODEC;
	}

	public void advanceTree(@NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull RandomSource random) {
		if (state.getValue(STAGE) == 0) {
			level.setBlock(pos, state.cycle(STAGE), UPDATE_INVISIBLE);
		} else {
			this.treeGrower.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
		}
	}

	@Override
	@NotNull
	public VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return state.is(this) && !isHanging(state) ? Shapes.empty() : super.getCollisionShape(state, level, pos, context);
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(STAGE, AGE, HANGING);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
		return Objects.requireNonNull(super.getStateForPlacement(ctx)).setValue(AGE, MAX_AGE);
	}

	@Override
	@NotNull
	public BlockState updateShape(
		@NotNull BlockState state,
		@NotNull Direction direction,
		@NotNull BlockState neighborState,
		@NotNull LevelAccessor level,
		@NotNull BlockPos currentPos,
		@NotNull BlockPos neighborPos
	) {
		if (state.is(this) && !state.canSurvive(level, currentPos)) {
			if (!isHanging(state)) {
				return Blocks.AIR.defaultBlockState();
			} else {
				level.scheduleTick(currentPos, this, this.getDelayAfterPlace());
			}
		}
		return state;
	}

	@Override
	@NotNull
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		Vec3 vec3d = state.getOffset(level, pos);
		VoxelShape voxelShape;
		if (!state.getValue(HANGING)) {
			voxelShape = SHAPES[3];
		} else {
			voxelShape = SHAPES[state.getValue(AGE)];
		}

		return voxelShape.move(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		return state.is(this) && isHanging(state) ? level.getBlockState(pos.above()).is(WWBlocks.PALM_FRONDS) : this.mayPlaceOn(level.getBlockState(pos.below()));
	}

	protected boolean mayPlaceOn(@NotNull BlockState state) {
		return state.is(BlockTags.DIRT) || state.is(Blocks.FARMLAND) || state.is(BlockTags.SAND);
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (state.is(this) && level.getMaxLocalRawBrightness(pos.above()) >= 9) {
			if (!isHanging(state)) {
				if (random.nextInt(7) == 0) {
					this.advanceTree(level, pos, state, random);
				}
			} else {
				if (random.nextDouble() <= GROWTH_CHANCE_HANGING && !isFullyGrown(state)) {
					level.setBlock(pos, state.cycle(AGE), UPDATE_CLIENTS);
				}
			}
		}
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader world, @NotNull BlockPos pos, @NotNull BlockState state) {
		return state.is(this) && (!isHanging(state) || !isFullyGrown(state));
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return state.is(this) && (isHanging(state) ? !isFullyGrown(state) : (double) level.random.nextFloat() < 0.45F);
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		if (isHanging(state) && !isFullyGrown(state)) {
			level.setBlock(pos, state.cycle(AGE), UPDATE_CLIENTS);
		} else {
			this.advanceTree(level, pos, state, random);
		}
	}

	@Override
	public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType type) {
		if (type == PathComputationType.AIR && state.is(this) && !isHanging(state)) return true;
		return super.isPathfindable(state, type);
	}

	@Override
	public void onProjectileHit(@NotNull Level level, @NotNull BlockState state, @NotNull BlockHitResult hit, @NotNull Projectile projectile) {
		if (state.is(this) && isHanging(state)) {
			if (isFullyGrown(state)) {
				FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(level, hit.getBlockPos(), state);
				this.falling(fallingBlockEntity);
			} else {
				level.destroyBlock(hit.getBlockPos(), true);
			}
		}
	}

	@Override
	protected void falling(@NotNull FallingBlockEntity entity) {
		entity.setHurtsEntities(FALL_DAMAGE_PER_DISTANCE, MAX_FALL_DAMAGE);
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (pos.getY() <= level.getMinBuildHeight() || state.is(this) && !isHanging(state)) return;
		if (state.is(this) && isHanging(state) && !state.canSurvive(level, pos)) {
			if (isFullyGrown(state)) {
				FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(level, pos, state);
				this.falling(fallingBlockEntity);
			} else {
				level.destroyBlock(pos, true);
			}
		}
	}

	@Override
	public void onLand(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull BlockState replaceableState, @NotNull FallingBlockEntity fallingBlock) {
		if (!level.isClientSide) {
			level.setBlockAndUpdate(pos, replaceableState);
		}
	}

	@Override
	public void onBrokenAfterFall(@NotNull Level level, @NotNull BlockPos pos, @NotNull FallingBlockEntity fallingBlock) {
		level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, new ItemStack(WWItems.COCONUT, 3)));
		level.playSound(null, pos, WWSounds.BLOCK_COCONUT_BREAK, SoundSource.BLOCKS, 1F, 1F);
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
	}
}
