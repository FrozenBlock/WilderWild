/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
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

@SuppressWarnings("deprecation")
public class CoconutBlock extends FallingBlock implements BonemealableBlock {
	public static final int VALID_FROND_DISTANCE = 2;
	public static final int MAX_AGE = 2;
	public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
	public static final MapCodec<CoconutBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		TreeGrower.CODEC.fieldOf("tree").forGetter((coconutBlock) -> coconutBlock.treeGrower),
		propertiesCodec()
	).apply(instance, CoconutBlock::new));
	private static final VoxelShape[] SHAPES = new VoxelShape[]{
		Shapes.or(Block.box(2, 9, 2, 14, 16, 14)),
		Shapes.or(Block.box(1, 8, 1, 15, 16, 15)),
		Shapes.or(Block.box(0, 5, 0, 16, 16, 15)),
		Block.box(2, 0, 2, 14, 12, 14)
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
	@Override
	protected MapCodec<? extends CoconutBlock> codec() {
		return CODEC;
	}

	@NotNull
	public static BlockState getHangingState(int age) {
		return RegisterBlocks.COCONUT.defaultBlockState().setValue(HANGING, true).setValue(AGE, age);
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
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
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
		BlockState stateAbove = level.getBlockState(pos.above());
		return state.is(this) && isHanging(state) ? (stateAbove.is(RegisterBlocks.PALM_FRONDS) && (stateAbove.getValue(BlockStateProperties.DISTANCE) <= VALID_FROND_DISTANCE || stateAbove.getValue(BlockStateProperties.PERSISTENT))) : this.mayPlaceOn(level.getBlockState(pos.below()));
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
				if (random.nextDouble() < 0.4 && !isFullyGrown(state)) {
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
		return state.is(this) && (isHanging(state) ? !isFullyGrown(state) : (double) level.random.nextFloat() < 0.45);
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
		if (type == PathComputationType.AIR && state.is(this) && !isHanging(state)) {
			return true;
		}
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
		entity.setHurtsEntities(0.5f, 3);
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (pos.getY() < level.getMinBuildHeight() || state.is(this) && !isHanging(state)) {
			return;
		}
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
			level.setBlock(pos, replaceableState, UPDATE_ALL);
		}
	}

	@Override
	public void onBrokenAfterFall(@NotNull Level level, @NotNull BlockPos pos, @NotNull FallingBlockEntity fallingBlock) {
		level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(RegisterItems.COCONUT, 3)));
		level.playSound(null, pos, RegisterSounds.BLOCK_COCONUT_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
	}
}
