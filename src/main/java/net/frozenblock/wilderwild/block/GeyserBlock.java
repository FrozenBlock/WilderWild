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
import net.frozenblock.lib.wind.api.BlowingHelper;
import net.frozenblock.wilderwild.block.entity.GeyserBlockEntity;
import net.frozenblock.wilderwild.block.impl.GeyserParticleHandler;
import net.frozenblock.wilderwild.block.state.properties.GeyserStage;
import net.frozenblock.wilderwild.block.state.properties.GeyserType;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GeyserBlock extends BaseEntityBlock {
	public static final float BOIL_SOUND_CHANCE_NATURAL = 0.0085F;
	public static final float BOIL_SOUND_CHANCE = 0.002F;
	public static final EnumProperty<GeyserType> GEYSER_TYPE = WWBlockStateProperties.GEYSER_TYPE;
	public static final EnumProperty<GeyserStage> GEYSER_STAGE = WWBlockStateProperties.GEYSER_STAGE;
	public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
	public static final BooleanProperty NATURAL = WWBlockStateProperties.NATURAL;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final MapCodec<GeyserBlock> CODEC = simpleCodec(GeyserBlock::new);

	public GeyserBlock(@NotNull Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any()
			.setValue(GEYSER_TYPE, GeyserType.NONE)
			.setValue(GEYSER_STAGE, GeyserStage.DORMANT)
			.setValue(FACING, Direction.UP)
			.setValue(NATURAL, true)
			.setValue(POWERED, false)
		);
	}

	@NotNull
	@Override
	protected MapCodec<? extends GeyserBlock> codec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new GeyserBlockEntity(pos, state);
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(GEYSER_TYPE, GEYSER_STAGE, FACING, NATURAL, POWERED);
	}

	@Override
	protected boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	protected int getAnalogOutputSignal(@NotNull BlockState state, Level level, BlockPos pos, Direction direction) {
		final GeyserStage stage = state.getValue(GEYSER_STAGE);
		if (stage == GeyserStage.DORMANT) return 0;
		if (stage == GeyserStage.ACTIVE) return 5;
		if (stage == GeyserStage.ERUPTING) return 15;
		return super.getAnalogOutputSignal(state, level, pos, direction);
	}

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		final Level level = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		final Direction direction = context.getNearestLookingDirection().getOpposite();
		final GeyserType geyserType = getGeyserTypeForPos(level, direction, pos);
		final boolean canErupt = context.getLevel().hasNeighborSignal(pos) && isActive(geyserType);

		return this.defaultBlockState()
			.setValue(GEYSER_STAGE, canErupt ? GeyserStage.ERUPTING : GeyserStage.DORMANT)
			.setValue(GEYSER_TYPE, geyserType)
			.setValue(FACING, direction)
			.setValue(NATURAL, false);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
		if (level.isClientSide()) return;
		final boolean hasNeighborSignal = level.hasNeighborSignal(pos);
		if (hasNeighborSignal == state.getValue(POWERED)) return;

		BlockState newState = state.setValue(POWERED, hasNeighborSignal);
		if (hasNeighborSignal) {
			final boolean erupting = state.getValue(GEYSER_STAGE) == GeyserStage.ERUPTING;
			if (!erupting && isActive(state.getValue(GEYSER_TYPE))) {
				newState = newState.setValue(GEYSER_STAGE, GeyserStage.ERUPTING);
			}
		}
		level.setBlockAndUpdate(pos, newState);
	}

	@Override
	protected @NotNull BlockState updateShape(
		@NotNull BlockState state,
		LevelReader level,
		ScheduledTickAccess scheduledTickAccess,
		BlockPos pos,
		@NotNull Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource random
	) {
		if (direction.getAxis() == state.getValue(FACING).getAxis()) {
			final GeyserType geyserType = getGeyserTypeForPos(level, state, pos);
			if (geyserType != state.getValue(GEYSER_TYPE)) state = state.setValue(GEYSER_TYPE, geyserType);
		}
		return state;
	}

	public static GeyserType getGeyserTypeForPos(@NotNull LevelReader level, @NotNull BlockState state, @NotNull BlockPos pos) {
		return getGeyserTypeForPos(level, state.getValue(FACING), pos);
	}

	public static GeyserType getGeyserTypeForPos(@NotNull LevelReader level, @NotNull Direction direction, @NotNull BlockPos pos) {
		final BlockPos checkPos = pos.relative(direction);
		final BlockState checkState = level.getBlockState(checkPos);
		if (!BlowingHelper.canBlowingPassThrough(level, checkPos, checkState, direction)) return GeyserType.NONE;

		final FluidState fluidState = checkState.getFluidState();
		if (fluidState.is(FluidTags.WATER)) {
			if (level.getBlockState(pos.relative(direction.getOpposite())).is(Blocks.MAGMA_BLOCK)) return GeyserType.HYDROTHERMAL_VENT;
			return GeyserType.WATER;
		}
		if (fluidState.is(FluidTags.LAVA)) return GeyserType.LAVA;
		return GeyserType.AIR;
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		final GeyserType geyserType = getGeyserTypeForPos(level, state, pos);
		if (geyserType != state.getValue(GEYSER_TYPE)) level.setBlockAndUpdate(pos, state.setValue(GEYSER_TYPE, geyserType));
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		final GeyserType geyserType = state.getValue(GEYSER_TYPE);
		if (!isActive(geyserType)) return;

		final Direction direction = state.getValue(FACING);
		final boolean natural = state.getValue(NATURAL);
		final GeyserStage stage = state.getValue(GEYSER_STAGE);
		GeyserParticleHandler.spawnBaseGeyserParticles(level, pos, direction, random, geyserType == GeyserType.HYDROTHERMAL_VENT);

		if (geyserType == GeyserType.HYDROTHERMAL_VENT && random.nextInt(27) == 0) {
			level.playLocalSound(
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				WWSounds.BLOCK_GEYSER_VENT_AMBIENT,
				SoundSource.BLOCKS,
				0.325F,
				0.85F + random.nextFloat() * 0.3F,
				false
			);
		}

		if (stage == GeyserStage.DORMANT) {
			GeyserParticleHandler.spawnDormantParticles(level, pos, geyserType, direction, random);
		} else if (stage == GeyserStage.ACTIVE) {
			GeyserParticleHandler.spawnActiveParticles(level, pos, geyserType, direction, random);
		}

		if (natural ? random.nextFloat() <= BOIL_SOUND_CHANCE_NATURAL : random.nextFloat() <= BOIL_SOUND_CHANCE) {
			level.playLocalSound(pos, WWSounds.BLOCK_GEYSER_BOIL, SoundSource.BLOCKS, 0.15F, 0.9F + (random.nextFloat() * 0.2F), false);
		}
	}

	@NotNull
	@Override
	public RenderShape getRenderShape(@NotNull BlockState state) {
		return RenderShape.MODEL;
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return !level.isClientSide() ?
			createTickerHelper(type, WWBlockEntityTypes.GEYSER, (worldx, pos, statex, blockEntity) -> blockEntity.tickServer(worldx, pos, statex, worldx.random))
			: createTickerHelper(type, WWBlockEntityTypes.GEYSER, (worldx, pos, statex, blockEntity) -> blockEntity.tickClient(worldx, pos, statex, worldx.random));
	}

	public static boolean isActive(GeyserType geyserType) {
		return geyserType != GeyserType.NONE;
	}

	@NotNull
	public static Vec3 getParticleVelocity(@NotNull Direction direction, @NotNull RandomSource random, double min, double max) {
		final double difference = max - min;
		final double velocity = min + (random.nextDouble() * difference);
		final double x = direction.getStepX() * velocity;
		final double y = direction.getStepY() * velocity;
		final double z = direction.getStepZ() * velocity;
		return new Vec3(x, y, z);
	}

	@NotNull
	public static Vec3 getVelocityFromDistance(BlockPos pos, Direction direction, @NotNull Vec3 vec3, @NotNull RandomSource random, double max) {
		return vec3.subtract(getParticlePosWithoutRandom(pos, direction, random)).scale(random.nextDouble() * max);
	}

	@NotNull
	public static Vec3 getParticlePosWithoutRandom(BlockPos pos, Direction direction, RandomSource random) {
		return Vec3.atLowerCornerOf(pos).add(
			getParticleOffsetX(direction, random, false),
			getParticleOffsetY(direction, random, false),
			getParticleOffsetZ(direction, random, false)
		);
	}

	@NotNull
	public static Vec3 getParticlePos(BlockPos pos, Direction direction, RandomSource random) {
		return Vec3.atLowerCornerOf(pos).add(
			getParticleOffsetX(direction, random, true),
			getParticleOffsetY(direction, random, true),
			getParticleOffsetZ(direction, random, true)
		);
	}

	private static double getRandomParticleOffset(@NotNull RandomSource random) {
		return random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D);
	}

	private static double getParticleOffsetX(@NotNull Direction direction, RandomSource random, boolean useRandom) {
		return switch (direction) {
			case UP, DOWN, SOUTH, NORTH -> 0.5D + (useRandom ? getRandomParticleOffset(random) : 0D);
			case EAST -> 1.05D;
			case WEST -> -0.05D;
		};
	}

	private static double getParticleOffsetY(@NotNull Direction direction, RandomSource random, boolean useRandom) {
		return switch (direction) {
			case DOWN -> -0.05D;
			case UP -> 1.05D;
			case NORTH, WEST, EAST, SOUTH -> 0.5D + (useRandom ? getRandomParticleOffset(random) : 0D);
		};
	}

	private static double getParticleOffsetZ(@NotNull Direction direction, RandomSource random, boolean useRandom) {
		return switch (direction) {
			case UP, DOWN, EAST, WEST -> 0.5D + (useRandom ? getRandomParticleOffset(random) : 0D);
			case NORTH -> -0.05D;
			case SOUTH -> 1.05D;
		};
	}
}
