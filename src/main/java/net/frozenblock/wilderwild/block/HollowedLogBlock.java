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
import net.frozenblock.lib.item.api.axe.AxeBehaviors;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class HollowedLogBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
	public static final double HOLLOW_PARTICLE_DIRECTION_OFFSET = 0.3375D;
	public static final int HOLLOW_PARTICLES_MIN = 12;
	public static final int HOLLOW_PARTICLES_MAX = 28;
	public static final double ENTRANCE_DIRECTION_STEP_SCALE = 0.475D;
	private static final double HOLLOWED_AMOUNT = 0.71875D;
	private static final double EDGE_AMOUNT = 0.140625D;
	private static final double CRAWL_HEIGHT = EDGE_AMOUNT + HOLLOWED_AMOUNT;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final MapCodec<HollowedLogBlock> CODEC = simpleCodec(HollowedLogBlock::new);
	protected static final VoxelShape X_SHAPE = Shapes.or(
		Block.box(0D, 0D, 0D, 16D, 16D, 3D),
		Block.box(0D, 13D, 0D, 16D, 16D, 16D),
		Block.box(0D, 0D, 13D, 16D, 16D, 16D),
		Block.box(0D, 0D, 0D, 16D, 3D, 16D)
	);
	protected static final VoxelShape Y_SHAPE = Shapes.or(
		Block.box(0D, 0D, 0D, 16D, 16D, 3D),
		Block.box(0D, 0D, 0D, 3D, 16D, 16D),
		Block.box(0D, 0D, 13D, 16D, 16D, 16D),
		Block.box(13D, 0D, 0D, 16D, 16D, 16D)
	);
	protected static final VoxelShape Z_SHAPE = Shapes.or(
		Block.box(13D, 0D, 0D, 16D, 16D, 16D),
		Block.box(0D, 0D, 0D, 3D, 16D, 16D),
		Block.box(0D, 13D, 0D, 16D, 16D, 16D),
		Block.box(0D, 0D, 0D, 16D, 3D, 16D)
	);
	protected static final VoxelShape X_COLLISION_SHAPE = Shapes.or(
		Block.box(0D, 0, 0, 16, 16D, 2.25D),
		Block.box(0D, 13.75D, 0, 16D, 16D, 16D),
		Block.box(0D, 0D, 13D, 16D, 16D, 16D),
		Block.box(0D, 0D, 0D, 16D, 2.25D, 16D)
	);
	protected static final VoxelShape Y_COLLISION_SHAPE = Shapes.or(
		Block.box(0D, 0D, 0D, 16D, 16D, 2.25D),
		Block.box(0D, 0D, 0D, 2.25D, 16D, 16),
		Block.box(0D, 0D, 13.75D, 16D, 16D, 16),
		Block.box(13.75D, 0D, 0D, 16D, 16D, 16)
	);
	protected static final VoxelShape Z_COLLISION_SHAPE = Shapes.or(
		Block.box(13.75D, 0D, 0D, 16D, 16D, 16D),
		Block.box(0D, 0D, 0D, 2.25D, 16D, 16),
		Block.box(0D, 13.75D, 0D, 16D, 16D, 16),
		Block.box(0D, 0D, 0D, 16D, 2.25D, 16)
	);
	protected static final VoxelShape RAYCAST_SHAPE = Shapes.block();

	public HollowedLogBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(AXIS, Direction.Axis.Y));
	}

	@Contract(value = "_, _ -> new", pure = true)
	public static AxeBehaviors.@NotNull AxeBehavior createHollowBehavior(
		@NotNull Block result,
		boolean isStem
	) {
		return new AxeBehaviors.AxeBehavior() {
			@Override
			public boolean meetsRequirements(LevelReader level, BlockPos pos, Direction direction, BlockState state) {
				return BlockConfig.get().logHollowing && state.hasProperty(BlockStateProperties.AXIS) && direction.getAxis().equals(state.getValue(BlockStateProperties.AXIS));
			}

			@Override
			public BlockState getOutputBlockState(BlockState state) {
				return result.withPropertiesOf(state);
			}

			@Override
			public void onSuccess(Level level, BlockPos pos, Direction direction, BlockState state, BlockState oldState) {
				hollowEffects(level, direction, oldState, pos, isStem);
			}
		};
	}

	public static void hollowEffects(@NotNull Level level, @NotNull Direction face, @NotNull BlockState state, @NotNull BlockPos pos, boolean isStem) {
		if (level instanceof ServerLevel serverLevel) {
			double offsetX = Math.abs(face.getStepX()) * HOLLOW_PARTICLE_DIRECTION_OFFSET;
			double offsetY = Math.abs(face.getStepY()) * HOLLOW_PARTICLE_DIRECTION_OFFSET;
			double offsetZ = Math.abs(face.getStepZ()) * HOLLOW_PARTICLE_DIRECTION_OFFSET;
			serverLevel.sendParticles(
				new BlockParticleOption(ParticleTypes.BLOCK, state),
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				level.random.nextInt(HOLLOW_PARTICLES_MIN, HOLLOW_PARTICLES_MAX),
				0.1625D + offsetX,
				0.1625D + offsetY,
				0.1625D + offsetZ,
				0.05D
			);
			SoundEvent hollowedSound = isStem ? RegisterSounds.STEM_HOLLOWED : RegisterSounds.LOG_HOLLOWED;
			level.playSound(null, pos, hollowedSound, SoundSource.BLOCKS, 0.7F, 0.95F + (level.random.nextFloat() * 0.2F));
		}
	}

	@NotNull
	@Override
	public MapCodec<? extends HollowedLogBlock> codec() {
		return CODEC;
	}

	@Override
	@NotNull
	public InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
		Direction direction = player.getMotionDirection();
		Direction hitDirection = hit.getDirection();
		Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
		double crawlingHeight = player.getDimensions(Pose.SWIMMING).height();
		double playerY = player.getY();

		if (player.isShiftKeyDown()
			&& player.getPose() != Pose.SWIMMING
			&& !player.isPassenger()
			&& direction.getAxis() != Direction.Axis.Y
			&& direction.getAxis() == axis
			&& player.getBbWidth() <= HOLLOWED_AMOUNT
			&& player.getBbHeight() >= HOLLOWED_AMOUNT
			&& player.blockPosition().relative(direction).equals(pos)
			&& player.position().distanceTo(Vec3.atBottomCenterOf(pos)) <= (0.5D + player.getBbWidth())
			&& playerY >= pos.getY()
			&& playerY + crawlingHeight <= pos.getY() + CRAWL_HEIGHT
			&& hitDirection.getAxis() == axis
			&& hitDirection.getOpposite() == direction
		) {
			player.setPos(
				pos.getX() + 0.5D - direction.getStepX() * ENTRANCE_DIRECTION_STEP_SCALE,
				pos.getY() + EDGE_AMOUNT,
				pos.getZ() + 0.5D - direction.getStepZ() * ENTRANCE_DIRECTION_STEP_SCALE
			);
			player.setPose(Pose.SWIMMING);
			player.setSwimming(true);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	@Override
	@NotNull
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return switch (state.getValue(AXIS)) {
			default -> X_SHAPE;
			case Y -> Y_SHAPE;
			case Z -> Z_SHAPE;
		};
	}

	@Override
	@NotNull
	public VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return switch (state.getValue(AXIS)) {
			default -> X_COLLISION_SHAPE;
			case Y -> Y_COLLISION_SHAPE;
			case Z -> Z_COLLISION_SHAPE;
		};
	}

	@Override
	@NotNull
	public VoxelShape getInteractionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return RAYCAST_SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
		BlockState superState = super.getStateForPlacement(ctx);
		return superState != null ? superState.setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).is(Fluids.WATER)) : null;
	}

	@Override
	@NotNull
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		level.scheduleTick(currentPos, this, 1);
		return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
	}

	@Override
	@NotNull
	public FluidState getFluidState(@NotNull BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED);
	}

	@Override
	public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return !state.getValue(WATERLOGGED);
	}

	@Override
	public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
		return true;
	}
}
