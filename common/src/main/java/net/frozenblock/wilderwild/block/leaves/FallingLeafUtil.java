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

package net.frozenblock.wilderwild.block.leaves;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.networking.packet.WWLeavesExplosionParticlePacket;
import net.frozenblock.wilderwild.particle.options.LeafClusterSeedParticleOptions;
import net.frozenblock.wilderwild.particle.options.WWFallingLeavesParticleOptions;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBlockItemTags;
import net.frozenblock.wilderwild.tag.WWEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerExplosion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public class FallingLeafUtil {
	private static final Function<Float, Float> SLIGHTLY_HEAVY_GRAVITY_MODIFIER = gravity -> Math.max(2.5F, gravity * 1.5F);

	public static Optional<FallingLeafData> getFallingLeafDataForLeavesBlock(RegistryAccess registryAccess, Block leavesBlock) {
		return registryAccess.lookup(WilderWildRegistries.FALLING_LEAF)
			.flatMap(registry -> registry.stream()
				.filter(fallingLeafData -> fallingLeafData.leavesBlock().contains(leavesBlock.builtInRegistryHolder()))
				.findFirst());
	}

	public static Optional<FallingLeafData> getFallingLeafDataForLeafLitterBlock(RegistryAccess registryAccess, Block leafLitterBlock) {
		return registryAccess.lookup(WilderWildRegistries.FALLING_LEAF)
			.flatMap(registry -> registry.stream()
				.filter(fallingLeafData -> fallingLeafData.leafLitterBlock().contains(leafLitterBlock.builtInRegistryHolder()))
				.findFirst());
	}

	public static Optional<FallingLeafData> getFallingLeafDataForBlock(RegistryAccess registryAccess, Block block) {
		return block.builtInRegistryHolder().is(WWBlockItemTags.LEAF_LITTERS.block())
			? getFallingLeafDataForLeafLitterBlock(registryAccess, block)
			: getFallingLeafDataForLeavesBlock(registryAccess, block);
	}

	public static Optional<FallingLeafData.ParticleData> getLeafParticleDataForBlock(RegistryAccess registryAccess, Block block) {
		return block.builtInRegistryHolder().is(WWBlockItemTags.LEAF_LITTERS.block())
			? getFallingLeafDataForLeafLitterBlock(registryAccess, block).flatMap(FallingLeafData::leafLitterParticleData)
			: getFallingLeafDataForLeavesBlock(registryAccess, block).flatMap(FallingLeafData::leafParticleData);
	}

	public static void tryRandomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		final Optional<FallingLeafData> fallingLeafData = getFallingLeafDataForLeavesBlock(level.registryAccess(), state.getBlock());
		fallingLeafData
			.flatMap(FallingLeafData::fallingLeafLitterData)
			.ifPresent(fallingLeafLitterData -> fallingLeafLitterData.randomTick(state, level, pos, random, fallingLeafData.get()));
	}

	public static void sendLeafClusterParticle(ServerLevel level, BlockPos pos, FallingLeafData fallingLeafData) {
		level.sendParticles(
			new LeafClusterSeedParticleOptions(Holder.direct(fallingLeafData)),
			pos.getX(), pos.getY(), pos.getZ(),
			1,
			0D, 0D, 0D,
			0D
		);
	}

	public static Optional<FallingLeafData> tryAnimateTickAndGetFallingLeafData(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (!WWAmbienceAndMiscConfig.USE_WILDER_WILD_FALLING_LEAVES.get()) return Optional.empty();

		final Optional<FallingLeafData> fallingLeafData = getFallingLeafDataForLeavesBlock(level.registryAccess(), state.getBlock());
		if (fallingLeafData.isEmpty()) return Optional.empty();

		fallingLeafData
			.flatMap(FallingLeafData::leafParticleData)
			.ifPresent(particleData -> particleData.animateTick(level, pos, random));
		return fallingLeafData;
	}

	public static void trySpawnWalkParticles(BlockState state, Level level, BlockPos pos, Entity entity, boolean checkCollision) {
		if (checkCollision) {
			final AABB shape = state.getShape(level, pos, CollisionContext.of(entity)).bounds().move(pos);
			if (!shape.intersects(entity.getBoundingBox())) return;
		}

		final boolean franticSpawn = entity.is(WWEntityTypeTags.LEAF_PARTICLES_FRANTIC_SPAWN);
		final double horizontalScale = franticSpawn ? 0.1D : 0.5D;
		final double additionalY = franticSpawn ? 0.1D : 0D;
		Vec3 movement = entity.getDeltaMovement();
		final double horizontalDistance = movement.horizontalDistance();
		movement = new Vec3(movement.x * horizontalScale, (horizontalDistance * 0.1D) + additionalY, movement.z * horizontalScale);

		if (!franticSpawn) {
			if (level.getRandom().nextFloat() > (horizontalDistance * 0.5D)) return;
		} else {
			if (level.getRandom().nextFloat() > 0.05F) return;
		}

		spawnWalkingParticles(level, pos, state, movement);
	}

	private static void spawnWalkingParticles(Level level, BlockPos pos, BlockState state, Vec3 velocity) {
		boolean isLitter = false;
		if (state.is(WWBlockItemTags.LEAF_LITTERS.block())) {
			isLitter = true;
			if (!WWAmbienceAndMiscConfig.LEAF_LITTER_WALKING_PARTICLES.get()) return;
		} else if (!WWAmbienceAndMiscConfig.LEAF_WALKING_PARTICLES.get()) {
			return;
		}

		final Optional<FallingLeafData> fallingLeafData = isLitter
			? getFallingLeafDataForLeafLitterBlock(level.registryAccess(), state.getBlock())
			: getFallingLeafDataForLeavesBlock(level.registryAccess(), state.getBlock());
		if (fallingLeafData.isEmpty()) return;

		final Optional<FallingLeafData.ParticleData> particleData = isLitter
			? fallingLeafData.get().leafLitterParticleData()
			: fallingLeafData.get().leafParticleData();
		if (particleData.isEmpty()) return;

		final RandomSource random = level.getRandom();
		final double x = pos.getX() + 0.5D + random.nextGaussian() * 0.4D;
		final double y = pos.getY() + (!isLitter ? 1.1D : 0.1D);
		final double z = pos.getZ() + 0.5D + random.nextGaussian() * 0.4D;

		final WWFallingLeavesParticleOptions particle = particleData.get().createLeafParticleOptions(velocity, true, SLIGHTLY_HEAVY_GRAVITY_MODIFIER);
		if (level instanceof ServerLevel server) {
			server.sendParticles(particle, x, y, z, 1, 0D, 0D, 0D, 0D);
			return;
		}
		level.addParticle(particle, x, y, z, 0D, 0D, 0D);
	}

	public static void trySendExplosionParticles(BlockState state, Level level, BlockPos pos, Explosion explosion) {
		if (!(level instanceof ServerLevel serverLevel)) return;
		if (!(explosion instanceof ServerExplosion serverExplosion)) return;

		if (getFallingLeafDataForBlock(level.registryAccess(), state.getBlock()).isEmpty()) return;

		final float radius = serverExplosion.radius();
		final Vec3 difference = Vec3.atCenterOf(pos).subtract(serverExplosion.center());
		final double leafPower = (radius - difference.length()) / radius;

		final List<Direction> validDirections = new ArrayList<>();
		Supplier<Integer> count;

		if (state.is(WWBlockItemTags.LEAF_LITTERS.block())) {
			count = () -> Math.max((int) (leafPower) * state.getOptionalValue(LeafLitterBlock.AMOUNT).orElse(2), 1);
		} else {
			if (!serverExplosion.getBlockInteraction().shouldAffectBlocklikeEntities()) {
				final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
				for (Direction direction : Direction.values()) {
					final BlockState offsetState = level.getBlockState(mutable.setWithOffset(pos, direction));
					if (!Block.isFaceFull(offsetState.getCollisionShape(level, mutable), direction.getOpposite())) validDirections.add(direction);
				}
				if (validDirections.isEmpty()) return;
			}
			count = () -> Math.clamp((int) (leafPower * 4F), 1, 4);
		}

		WWLeavesExplosionParticlePacket.sendToAll(state, pos, difference.scale((leafPower * leafPower) * 0.2D), validDirections, count.get(), serverLevel);
	}

	public static void clientSpawnExplosionParticlesFromPacket(Level level, WWLeavesExplosionParticlePacket packet) {
		if (!WWAmbienceAndMiscConfig.LEAF_EXPLOSION_PARTICLES.get()) return;

		final BlockState state = packet.state();
		final Optional<FallingLeafData.ParticleData> particleData = getLeafParticleDataForBlock(level.registryAccess(), state.getBlock());
		if (particleData.isEmpty()) return;

		final BlockPos pos = packet.pos();
		final List<Direction> directions = packet.directions();

		final RandomSource random = level.getRandom();
		boolean litter = false;
		Supplier<Vec3> posSupplier;

		if (state.is(WWBlockItemTags.LEAF_LITTERS.block())) {
			litter = true;
			posSupplier = () -> new Vec3(
				pos.getX() + 0.5D + random.nextGaussian() * 0.4D,
				pos.getY() + 0.1D,
				pos.getZ() + 0.5D + random.nextGaussian() * 0.4D
			);
		} else {
			if (directions.isEmpty()) {
				posSupplier = () -> new Vec3(
					pos.getX() + 0.5D + random.nextGaussian() * 0.4D,
					pos.getY() + 0.5D + random.nextGaussian() * 0.4D,
					pos.getZ() + 0.5D + random.nextGaussian() * 0.4D
				);
			} else {
				posSupplier = () -> {
					Direction direction = Util.getRandom(directions, random);
					double x = pos.getX() + 0.5D + (random.nextGaussian() * 0.4D);
					double y = pos.getY() + 0.5D + (random.nextGaussian() * 0.4D);
					double z = pos.getZ() + 0.5D + (random.nextGaussian() * 0.4D);

					if (direction.getAxis() == Direction.Axis.X) x = pos.getX() + 0.5D + (direction.getStepX() * 0.6D);
					if (direction.getAxis() == Direction.Axis.Y) y = pos.getY() + 0.5D + (direction.getStepY() * 0.6D);
					if (direction.getAxis() == Direction.Axis.Z) z = pos.getZ() + 0.5D + (direction.getStepZ() * 0.6D);

					return new Vec3(x, y, z);
				};
			}
		}

		final WWFallingLeavesParticleOptions particle = particleData.get().createLeafParticleOptions(
			packet.velocity().scale(WWAmbienceAndMiscConfig.LEAF_EXPLOSION_VELOCITY.get()),
			SLIGHTLY_HEAVY_GRAVITY_MODIFIER
		);

		for (int i = 0; i < packet.count(); i++) {
			final Vec3 particlePos = posSupplier.get();
			level.addParticle(particle, particlePos.x, particlePos.y, particlePos.z, 0D, 0D, 0D);
		}
	}
}
