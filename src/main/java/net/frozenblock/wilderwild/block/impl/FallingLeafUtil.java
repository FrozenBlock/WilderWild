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

package net.frozenblock.wilderwild.block.impl;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.entity.FallingLeafTicker;
import net.frozenblock.wilderwild.particle.options.WWFallingLeavesParticleOptions;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FallingLeafUtil {
	private static final LeafParticleData DEFAULT_LEAF_PARTICLE_DATA = new LeafParticleData(
		Blocks.OAK_LEAVES,
		0.0125F,
		() -> 1D,
		4,
		2F,
		10F,
		LeafMovementType.SWIRL
	);
	private static final Map<Block, FallingLeafData> LEAVES_TO_FALLING_LEAF_DATA = new Object2ObjectLinkedOpenHashMap<>();
	private static final Map<ParticleType<WWFallingLeavesParticleOptions>, LeafParticleData> PARTICLE_TO_LEAF_PARTICLE_DATA = new Object2ObjectLinkedOpenHashMap<>();
	private static final Map<ParticleType<WWFallingLeavesParticleOptions>, LeafParticleData> PARTICLE_TO_LITTER_PARTICLE_DATA = new Object2ObjectLinkedOpenHashMap<>();

	public static void registerLeavesWithLitter(
		Block block,
		LeafLitterBlock leafLitterBlock,
		float litterChance,
		ParticleType<WWFallingLeavesParticleOptions> leafParticle,
		float particleChance,
		Supplier<Double> frequencyModifier,
		int textureSize,
		float particleGravityScale,
		float windScale,
		LeafMovementType leafMovementType
	) {
		registerLeaves(
			block,
			false,
			new FallingLeafData(Optional.of(leafLitterBlock), litterChance, leafParticle),
			leafParticle,
			new LeafParticleData(block, particleChance, frequencyModifier, textureSize, particleGravityScale, windScale, leafMovementType)
		);

		registerLeaves(
			leafLitterBlock,
			true,
			leafParticle,
			particleChance,
			frequencyModifier,
			textureSize,
			particleGravityScale,
			windScale,
			leafMovementType.getLitterEquivalent()
		);
	}

	public static void registerLeaves(
		Block block,
		boolean isLitter,
		ParticleType<WWFallingLeavesParticleOptions> leafParticle,
		float particleChance,
		Supplier<Double> frequencyModifier,
		int textureSize,
		float particleGravityScale,
		float windScale,
		LeafMovementType leafMovementType
	) {
		registerLeaves(
			block,
			isLitter,
			new FallingLeafData(Optional.empty(), 0F, leafParticle),
			leafParticle,
			new LeafParticleData(block, particleChance, frequencyModifier, textureSize, particleGravityScale, windScale, leafMovementType)
		);
	}

	private static void registerLeaves(
		Block block,
		boolean isLitter,
		FallingLeafData fallingLeafData,
		ParticleType<WWFallingLeavesParticleOptions> leafParticle,
		@Nullable LeafParticleData leafParticleData
	) {
		LEAVES_TO_FALLING_LEAF_DATA.put(block, fallingLeafData);
		if (leafParticleData != null) (isLitter ? PARTICLE_TO_LITTER_PARTICLE_DATA : PARTICLE_TO_LEAF_PARTICLE_DATA).put(leafParticle, leafParticleData);
	}

	public static @NotNull Optional<FallingLeafData> getFallingLeafData(Block block) {
		return Optional.ofNullable(LEAVES_TO_FALLING_LEAF_DATA.get(block));
	}

	public static LeafParticleData getLeafParticleData(ParticleType<WWFallingLeavesParticleOptions> leafParticle) {
		return PARTICLE_TO_LEAF_PARTICLE_DATA.getOrDefault(leafParticle, DEFAULT_LEAF_PARTICLE_DATA);
	}

	public static LeafParticleData getLitterOrLeafParticleData(ParticleType<WWFallingLeavesParticleOptions> leafParticle) {
		return PARTICLE_TO_LITTER_PARTICLE_DATA.getOrDefault(leafParticle, getLeafParticleData(leafParticle));
	}

	public static void onRandomTick(@NotNull BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		Optional<FallingLeafUtil.FallingLeafData> optionalFallingLeafData = FallingLeafUtil.getFallingLeafData(state.getBlock());
		if (optionalFallingLeafData.isEmpty()) return;

		FallingLeafUtil.FallingLeafData fallingLeafData = optionalFallingLeafData.get();
		BlockPos belowPos = pos.below();
		BlockState belowState = world.getBlockState(belowPos);

		if (Block.isFaceFull(belowState.getCollisionShape(world, belowPos), Direction.UP)) return;
		if (random.nextFloat() > fallingLeafData.litterChance()) return;

		world.sendParticles(
			new BlockParticleOption(ParticleTypes.BLOCK, state),
			pos.getX() + 0.5D,
			pos.getY() - 0.1D,
			pos.getZ() + 0.5D,
			random.nextInt(12, 24),
			0.3D, 0D, 0.3D,
			0.05D
		);
		sendLeafClusterParticle(world, pos);
		fallingLeafData.leafLitterBlock.ifPresent(leafLitterBlock -> FallingLeafTicker.createAndSpawn(
			WWEntityTypes.FALLING_LEAVES,
			world,
			pos,
			leafLitterBlock
		));
	}

	public static void sendLeafClusterParticle(@NotNull ServerLevel world, @NotNull BlockPos pos) {
		world.sendParticles(
			WWParticleTypes.LEAF_CLUSTER_SPAWNER,
			pos.getX(), pos.getY(), pos.getZ(),
			1,
			0D, 0D, 0D,
			0D
		);
	}

	public static boolean isSafePosToPlaceLitter(@NotNull Level world, BlockPos pos, @NotNull BlockState stateToReplace, Block leafLitterBlock) {
		if (stateToReplace.is(Blocks.SNOW) || SnowloggingUtils.isSnowlogged(stateToReplace)) return false;
		if ((stateToReplace.isAir() || stateToReplace.canBeReplaced() || stateToReplace.is(leafLitterBlock)) && stateToReplace.getFluidState().isEmpty()) {
			return leafLitterBlock.canSurvive(leafLitterBlock.defaultBlockState(), world, pos);
		}
		return false;
	}

	public static boolean addFallingLeafParticles(@NotNull BlockState state, Level world, BlockPos pos, RandomSource random) {
		if (!WWAmbienceAndMiscConfig.Client.USE_WILDER_WILD_FALLING_LEAVES) return false;

		Optional<FallingLeafUtil.FallingLeafData> optionalFallingLeafData = FallingLeafUtil.getFallingLeafData(state.getBlock());
		if (optionalFallingLeafData.isEmpty()) return false;

		FallingLeafUtil.FallingLeafData fallingLeafData = optionalFallingLeafData.get();
		ParticleType<WWFallingLeavesParticleOptions> leafParticle = fallingLeafData.particle();
		LeafParticleData leafParticleData = getLeafParticleData(leafParticle);
		if (random.nextFloat() <= leafParticleData.particleChance() * leafParticleData.frequencyModifier().get()) {
			BlockPos blockPos = pos.below();
			BlockState blockState = world.getBlockState(blockPos);
			if (!Block.isFaceFull(blockState.getCollisionShape(world, blockPos), Direction.UP)) {
				ParticleUtils.spawnParticleBelow(world, pos, random, createLeafParticleOptions(fallingLeafData, false));
			}
		}
		return true;
	}

	public static void spawnParticlesFromTop(@NotNull Level level, BlockPos pos, @NotNull BlockState state, Vec3 velocity) {
		boolean litter = false;
		if (state.is(WWBlockTags.LEAF_LITTERS)) {
			litter = true;
			if (!WWAmbienceAndMiscConfig.Client.LEAF_LITTER_WALKING_PARTICLES) return;
		} else if (!WWAmbienceAndMiscConfig.Client.LEAF_WALKING_PARTICLES) {
			return;
		}

		Optional<FallingLeafUtil.FallingLeafData> optionalFallingLeafData = FallingLeafUtil.getFallingLeafData(state.getBlock());
		if (optionalFallingLeafData.isEmpty()) return;

		RandomSource random = level.random;
		double x = pos.getX() + 0.5D + random.nextGaussian() * 0.4D;
		double y = pos.getY() + (!litter ? 1.1D : 0.1D);
		double z = pos.getZ() + 0.5D + random.nextGaussian() * 0.4D;

		WWFallingLeavesParticleOptions options = createLeafParticleOptions(optionalFallingLeafData.get(), velocity, litter);
		if (level instanceof ServerLevel server) {
			server.sendParticles(
				options,
				x, y, z,
				1,
				0D, 0D, 0D, 0D
			);
		} else {
			level.addParticle(
				options,
				x, y, z,
				0D, 0D, 0D
			);
		}
	}

	public static void trySpawnWalkParticles(
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull Entity entity,
		boolean checkCollision
	) {
		if (checkCollision) {
			AABB shape = state.getShape(level, pos, CollisionContext.of(entity)).bounds().move(pos);
			if (!shape.intersects(entity.getBoundingBox())) return;
		}

		Vec3 movement = entity.getDeltaMovement();
		double horizontalDistance = movement.horizontalDistance();
		movement = new Vec3(movement.x * 0.5D, horizontalDistance * 0.1D, movement.z * 0.5D);

		if (level.random.nextFloat() > (horizontalDistance * 0.5D)) return;
		FallingLeafUtil.spawnParticlesFromTop(level, pos, state, movement);
	}

	public static @NotNull WWFallingLeavesParticleOptions createLeafParticleOptions(FallingLeafUtil.@NotNull FallingLeafData fallingLeafData, boolean isLitter) {
		ParticleType<WWFallingLeavesParticleOptions> leafParticle = fallingLeafData.particle();
		LeafParticleData leafParticleData = isLitter ? getLitterOrLeafParticleData(leafParticle) : getLeafParticleData(leafParticle);
		return WWFallingLeavesParticleOptions.create(
			fallingLeafData.particle,
			leafParticleData.textureSize,
			leafParticleData.particleGravityScale,
			leafParticleData.windScale,
			isLitter,
			leafParticleData.leafMovementType
		);
	}

	public static @NotNull WWFallingLeavesParticleOptions createLeafParticleOptions(FallingLeafUtil.@NotNull FallingLeafData fallingLeafData, Vec3 velocity, boolean isLitter) {
		ParticleType<WWFallingLeavesParticleOptions> leafParticle = fallingLeafData.particle();
		LeafParticleData leafParticleData = isLitter ? getLitterOrLeafParticleData(leafParticle) : getLeafParticleData(leafParticle);
		return WWFallingLeavesParticleOptions.createControlledVelocity(
			fallingLeafData.particle,
			velocity,
			leafParticleData.textureSize,
			leafParticleData.particleGravityScale,
			leafParticleData.windScale,
			isLitter,
			leafParticleData.leafMovementType
		);
	}

	public record FallingLeafData(Optional<LeafLitterBlock> leafLitterBlock, float litterChance, ParticleType<WWFallingLeavesParticleOptions> particle) {}

	public record LeafParticleData(
		Block leavesBlock,
		float particleChance,
		Supplier<Double> frequencyModifier,
		int textureSize,
		float particleGravityScale,
		float windScale,
		LeafMovementType leafMovementType
	) {}

	public enum LeafMovementType implements StringRepresentable {
		NONE("none", false, false, false),
		SWIRL("swirl", true, false, false),
		FLOW_AWAY("flow_away", false, true, false),
		SWIRL_AND_FLOW_AWAY("swirl_and_flow_away", true, true, false),
		LITTER("litter_swirl", false, false, true),
		LITTER_SWIRL("litter_swirl", true, false, true),
		LITTER_FLOW_AWAY("litter_flow", true, false, true),
		LITTER_SWIRL_AND_FLOW_AWAY("swirl_and_flow_away", true, true, true);
		public static final Codec<LeafMovementType> CODEC = StringRepresentable.fromEnum(LeafMovementType::values);
		public static final StreamCodec<ByteBuf, LeafMovementType> STREAM_CODEC = new StreamCodec<>() {
			@Override
			public @NotNull LeafMovementType decode(ByteBuf byteBuf) {
				return LeafMovementType.valueOf(ByteBufCodecs.STRING_UTF8.decode(byteBuf));
			}

			@Override
			public void encode(ByteBuf byteBuf, @NotNull LeafMovementType leafMovementType) {
				ByteBufCodecs.STRING_UTF8.encode(byteBuf, leafMovementType.name());
			}
		};

		private final String name;
		private final boolean swirl;
		private final boolean flowAway;
		private final boolean bounceOnFloor;

		LeafMovementType(String name, boolean swirl, boolean flowAway, boolean bounceOnFloor) {
			this.name = name;
			this.swirl = swirl;
			this.flowAway = flowAway;
			this.bounceOnFloor = bounceOnFloor;
		}

		public LeafMovementType getLitterEquivalent() {
			if (this == LeafMovementType.NONE) return LeafMovementType.LITTER;
			if (this == LeafMovementType.SWIRL) return LeafMovementType.LITTER_SWIRL;
			if (this == LeafMovementType.FLOW_AWAY) return LeafMovementType.LITTER_FLOW_AWAY;
			if (this == LeafMovementType.SWIRL_AND_FLOW_AWAY) return LeafMovementType.LITTER_SWIRL_AND_FLOW_AWAY;
			return this;
		}

		public boolean swirl() {
			return this.swirl;
		}

		public boolean flowAway() {
			return this.flowAway;
		}

		public boolean bounceOnFloor() {
			return this.bounceOnFloor;
		}

		@Override
		public @NotNull String getSerializedName() {
			return this.name;
		}
	}
}
