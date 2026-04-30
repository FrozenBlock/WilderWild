/*
 * Copyright 2025-2026 FrozenBlock
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
import net.frozenblock.wilderwild.block.entity.GeothermalVentBlockEntity;
import net.frozenblock.wilderwild.block.impl.GeothermalventParticleHandler;
import net.frozenblock.wilderwild.block.state.properties.GeothermalVentType;
import net.frozenblock.wilderwild.block.state.properties.GeothermalVentStage;
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
import org.jetbrains.annotations.Nullable;

public class GeothermalVentBlock extends BaseEntityBlock {
	public static final float BOIL_SOUND_CHANCE_NATURAL = 0.0085F;
	public static final float BOIL_SOUND_CHANCE = 0.002F;
	public static final EnumProperty<GeothermalVentType> GEOTHERMAL_VENT_TYPE = WWBlockStateProperties.VENT_TYPE;
	public static final EnumProperty<GeothermalVentStage> GEOTHERMAL_VENT_STAGE = WWBlockStateProperties.VENT_STATE;
	public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
	public static final BooleanProperty NATURAL = WWBlockStateProperties.NATURAL;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final MapCodec<GeothermalVentBlock> CODEC = simpleCodec(GeothermalVentBlock::new);

	public GeothermalVentBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any()
			.setValue(GEOTHERMAL_VENT_TYPE, GeothermalVentType.NONE)
			.setValue(GEOTHERMAL_VENT_STAGE, GeothermalVentStage.DORMANT)
			.setValue(FACING, Direction.UP)
			.setValue(NATURAL, true)
			.setValue(POWERED, false)
		);
	}

	@Override
	protected MapCodec<? extends GeothermalVentBlock> codec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new GeothermalVentBlockEntity(pos, state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(GEOTHERMAL_VENT_TYPE, GEOTHERMAL_VENT_STAGE, FACING, NATURAL, POWERED);
	}

	@Override
	protected boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
		final GeothermalVentStage stage = state.getValue(GEOTHERMAL_VENT_STAGE);
		if (stage == GeothermalVentStage.DORMANT) return 0;
		if (stage == GeothermalVentStage.ACTIVE) return 5;
		if (stage == GeothermalVentStage.ERUPTING) return 15;
		return super.getAnalogOutputSignal(state, level, pos, direction);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		final Level level = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		final Direction direction = context.getNearestLookingDirection().getOpposite();
		final GeothermalVentType geothermalVentType = getVentTypeForPos(level, direction, pos);
		final boolean canErupt = context.getLevel().hasNeighborSignal(pos) && isActive(geothermalVentType);

		return this.defaultBlockState()
			.setValue(GEOTHERMAL_VENT_STAGE, canErupt ? GeothermalVentStage.ERUPTING : GeothermalVentStage.DORMANT)
			.setValue(GEOTHERMAL_VENT_TYPE, geothermalVentType)
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
			final boolean erupting = state.getValue(GEOTHERMAL_VENT_STAGE) == GeothermalVentStage.ERUPTING;
			if (!erupting && isActive(state.getValue(GEOTHERMAL_VENT_TYPE))) {
				newState = newState.setValue(GEOTHERMAL_VENT_STAGE, GeothermalVentStage.ERUPTING);
			}
		}
		level.setBlockAndUpdate(pos, newState);
	}

	@Override
	protected BlockState updateShape(
		BlockState state,
		LevelReader level,
		ScheduledTickAccess ticks,
		BlockPos pos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource random
	) {
		if (direction.getAxis() == state.getValue(FACING).getAxis()) {
			final GeothermalVentType geothermalVentType = getVentTypeForPos(level, state, pos);
			if (geothermalVentType != state.getValue(GEOTHERMAL_VENT_TYPE)) state = state.setValue(GEOTHERMAL_VENT_TYPE, geothermalVentType);
		}
		return state;
	}

	public static GeothermalVentType getVentTypeForPos(LevelReader level, BlockState state, BlockPos pos) {
		return getVentTypeForPos(level, state.getValue(FACING), pos);
	}

	public static GeothermalVentType getVentTypeForPos(LevelReader level, Direction direction, BlockPos pos) {
		final BlockPos checkPos = pos.relative(direction);
		final BlockState checkState = level.getBlockState(checkPos);
		if (!BlowingHelper.canBlowingPassThrough(level, checkPos, checkState, direction)) return GeothermalVentType.NONE;

		final FluidState fluidState = checkState.getFluidState();
		if (fluidState.is(FluidTags.WATER)) {
			if (level.getBlockState(pos.relative(direction.getOpposite())).is(Blocks.MAGMA_BLOCK)) return GeothermalVentType.HYDROTHERMAL_VENT;
			return GeothermalVentType.WATER;
		}
		if (fluidState.is(FluidTags.LAVA)) return GeothermalVentType.LAVA;
		return GeothermalVentType.AIR;
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		final GeothermalVentType geothermalVentType = getVentTypeForPos(level, state, pos);
		if (geothermalVentType != state.getValue(GEOTHERMAL_VENT_TYPE)) level.setBlockAndUpdate(pos, state.setValue(GEOTHERMAL_VENT_TYPE, geothermalVentType));
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		final GeothermalVentType geothermalVentType = state.getValue(GEOTHERMAL_VENT_TYPE);
		if (!isActive(geothermalVentType)) return;

		final Direction direction = state.getValue(FACING);
		final boolean natural = state.getValue(NATURAL);
		final GeothermalVentStage stage = state.getValue(GEOTHERMAL_VENT_STAGE);
		GeothermalventParticleHandler.spawnBaseGeothermalVentParticles(level, pos, direction, random, geothermalVentType == GeothermalVentType.HYDROTHERMAL_VENT);

		if (geothermalVentType == GeothermalVentType.HYDROTHERMAL_VENT && random.nextInt(27) == 0) {
			level.playLocalSound(
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				WWSounds.BLOCK_GEOTHERMAL_VENT_VENT_AMBIENT,
				SoundSource.BLOCKS,
				0.325F,
				0.85F + random.nextFloat() * 0.3F,
				false
			);
		}

		if (stage == GeothermalVentStage.DORMANT) {
			GeothermalventParticleHandler.spawnDormantParticles(level, pos, geothermalVentType, direction, random);
		} else if (stage == GeothermalVentStage.ACTIVE) {
			GeothermalventParticleHandler.spawnActiveParticles(level, pos, geothermalVentType, direction, random);
		}

		if (natural ? random.nextFloat() <= BOIL_SOUND_CHANCE_NATURAL : random.nextFloat() <= BOIL_SOUND_CHANCE) {
			level.playLocalSound(pos, WWSounds.BLOCK_GEOTHERMAL_VENT_BOIL, SoundSource.BLOCKS, 0.15F, 0.9F + (random.nextFloat() * 0.2F), false);
		}
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return !level.isClientSide() ?
			createTickerHelper(type, WWBlockEntityTypes.GEOTHERMAL_VENT, (levelx, pos, statex, blockEntity) -> blockEntity.tickServer((ServerLevel) levelx, pos, statex, levelx.getRandom()))
			: createTickerHelper(type, WWBlockEntityTypes.GEOTHERMAL_VENT, (levelx, pos, statex, blockEntity) -> blockEntity.tickClient(levelx, pos, statex, levelx.getRandom()));
	}

	public static boolean isActive(GeothermalVentType geothermalVentType) {
		return geothermalVentType != GeothermalVentType.NONE;
	}

	public static Vec3 getParticleVelocity(Direction direction, RandomSource random, double min, double max) {
		final double difference = max - min;
		final double velocity = min + (random.nextDouble() * difference);
		final double x = direction.getStepX() * velocity;
		final double y = direction.getStepY() * velocity;
		final double z = direction.getStepZ() * velocity;
		return new Vec3(x, y, z);
	}

	public static Vec3 getVelocityFromDistance(BlockPos pos, Direction direction, Vec3 vec3, RandomSource random, double max) {
		return vec3.subtract(getParticlePosWithoutRandom(pos, direction, random)).scale(random.nextDouble() * max);
	}

	public static Vec3 getParticlePosWithoutRandom(BlockPos pos, Direction direction, RandomSource random) {
		return Vec3.atLowerCornerOf(pos).add(
			getParticleOffsetX(direction, random, false),
			getParticleOffsetY(direction, random, false),
			getParticleOffsetZ(direction, random, false)
		);
	}

	public static Vec3 getParticlePos(BlockPos pos, Direction direction, RandomSource random) {
		return Vec3.atLowerCornerOf(pos).add(
			getParticleOffsetX(direction, random, true),
			getParticleOffsetY(direction, random, true),
			getParticleOffsetZ(direction, random, true)
		);
	}

	private static double getRandomParticleOffset(RandomSource random) {
		return random.nextDouble() / 3D * (random.nextBoolean() ? 1D : -1D);
	}

	private static double getParticleOffsetX(Direction direction, RandomSource random, boolean useRandom) {
		return switch (direction) {
			case UP, DOWN, SOUTH, NORTH -> 0.5D + (useRandom ? getRandomParticleOffset(random) : 0D);
			case EAST -> 1.05D;
			case WEST -> -0.05D;
		};
	}

	private static double getParticleOffsetY(Direction direction, RandomSource random, boolean useRandom) {
		return switch (direction) {
			case DOWN -> -0.05D;
			case UP -> 1.05D;
			case NORTH, WEST, EAST, SOUTH -> 0.5D + (useRandom ? getRandomParticleOffset(random) : 0D);
		};
	}

	private static double getParticleOffsetZ(Direction direction, RandomSource random, boolean useRandom) {
		return switch (direction) {
			case UP, DOWN, EAST, WEST -> 0.5D + (useRandom ? getRandomParticleOffset(random) : 0D);
			case NORTH -> -0.05D;
			case SOUTH -> 1.05D;
		};
	}
}
