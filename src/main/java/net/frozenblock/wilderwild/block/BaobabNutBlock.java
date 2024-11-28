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
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaobabNutBlock extends SaplingBlock {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	public static final int MAX_AGE = 2;
	public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
	public static final MapCodec<BaobabNutBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		TreeGrower.CODEC.fieldOf("tree").forGetter((baobabNutBlock) -> baobabNutBlock.treeGrower),
		propertiesCodec()
	).apply(instance, BaobabNutBlock::new));
	public static final double HANGING_GROWTH_CHANCE = 0.4D;
	private static final VoxelShape[] SHAPES = new VoxelShape[]{
		Shapes.or(Block.box(7D, 13D, 7D, 9D, 16D, 9.0), Block.box(5D, 6D, 5D, 11D, 13D, 11.0)),
		Shapes.or(Block.box(7D, 12D, 7D, 9D, 16D, 9.0), Block.box(4D, 3D, 4D, 12D, 12D, 12.0)),
		Shapes.or(Block.box(7D, 10D, 7D, 9D, 16D, 9.0), Block.box(4D, 0D, 4D, 12D, 10D, 12.0)),
		Block.box(7D, 3D, 7D, 9D, 16D, 9), Block.box(2D, 0D, 2D, 14D, 12D, 14D)
	};

	public BaobabNutBlock(TreeGrower treeGrower, @NotNull BlockBehaviour.Properties settings) {
		super(treeGrower, settings);
		this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0).setValue(HANGING, false));
	}

	private static boolean isHanging(@NotNull BlockState state) {
		return state.getValue(HANGING);
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == MAX_AGE;
	}

	@NotNull
	@Override
	public MapCodec<? extends BaobabNutBlock> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(STAGE, AGE, HANGING);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
		return Objects.requireNonNull(super.getStateForPlacement(ctx)).setValue(AGE, 2);
	}

	@Override
	@NotNull
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		Vec3 vec3d = state.getOffset(pos);
		VoxelShape voxelShape;
		if (!state.getValue(HANGING)) {
			voxelShape = SHAPES[4];
		} else {
			voxelShape = SHAPES[state.getValue(AGE)];
		}

		return voxelShape.move(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		return state.is(this) && (isHanging(state) ? level.getBlockState(pos.above()).is(WWBlocks.BAOBAB_LEAVES) : super.canSurvive(state, level, pos));
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (state.is(this) && level.getMaxLocalRawBrightness(pos.above()) >= 9) {
			if (!isHanging(state)) {
				if (random.nextInt(7) == 0) {
					this.advanceTree(level, pos, state, random);
				}
			} else {
				if (random.nextDouble() <= HANGING_GROWTH_CHANCE && !isFullyGrown(state)) {
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
		return state.is(this) && isHanging(state) ? !isFullyGrown(state) : super.isBonemealSuccess(level, random, pos, state);
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		if (state.is(this) && isHanging(state) && !isFullyGrown(state)) {
			level.setBlock(pos, state.cycle(AGE), UPDATE_CLIENTS);
		} else {
			super.performBonemeal(level, random, pos, state);
		}

	}

	@Override
	public void onProjectileHit(@NotNull Level world, @NotNull BlockState state, @NotNull BlockHitResult hit, @NotNull Projectile projectile) {
		world.destroyBlock(hit.getBlockPos(), true, projectile);
	}

	@Override
	@NotNull
	public VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
		return isHanging(blockState) ? super.getCollisionShape(blockState, blockGetter, blockPos, collisionContext) : Shapes.empty();
	}

	@NotNull
	public BlockState getDefaultHangingState() {
		return getHangingState(0);
	}

	@NotNull
	public BlockState getHangingState(int age) {
		return this.defaultBlockState().setValue(HANGING, true).setValue(AGE, age);
	}
}
