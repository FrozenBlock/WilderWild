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

import net.frozenblock.lib.particle.api.ParticleSpawner;
import net.frozenblock.lib.wind.WindManager;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SeedingFlowerBlock extends FlowerBlock {
	public static final float SEED_SPAWN_CHANCE = 0.04F;
	public static final double SEED_SPAWN_HEIGHT = 0.5D;
	public static final int MIN_SEEDS = 1;
	public static final int MAX_SEEDS = 2;
	public static final int MIN_SEEDS_DESTROY = 3;
	public static final int MAX_SEEDS_DESTROY = 7;
	public static final int AMBIENT_SEED_MIN_SKY_LIGHT_LEVEL = 13;
	private static final ParticleSpawner PARTICLE_SPAWNER = new ParticleSpawner(0.04F, 3, 5, -1, 5) {
		@Override
		public boolean canSpawnAtPos(Level level, BlockPos pos, BlockState state) {
			if (!state.isAir()
				|| level.isRainingAt(pos)
				|| level.getBrightness(LightLayer.SKY, pos) < AMBIENT_SEED_MIN_SKY_LIGHT_LEVEL
			) return false;

			final Vec3 center = Vec3.atCenterOf(pos);
			final WindManager windManager = WindManager.getOrCreate(level);
			final Vec3 wind = windManager.getWindMovement(center, 1D, 2D, 5D);

			final RandomSource random = level.getRandom();
			return wind.horizontalDistance() >= random.nextFloat() - (random.nextFloat() * 0.25F);
		}

		@Override
		public ParticleOptions selectParticleOptions(Level level, BlockPos pos, RandomSource random) {
			return SeedParticleOptions.unControlled(false);
		}
	};

	private final Block nonSeedingFlower;

	public SeedingFlowerBlock(
		Holder<MobEffect> suspiciousStewEffect,
		int effectDuration,
		Block nonSeedingFlower,
		Properties properties
	) {
		super(suspiciousStewEffect, effectDuration, properties);
		this.nonSeedingFlower = nonSeedingFlower;
	}

	public SeedingFlowerBlock(
		SuspiciousStewEffects suspiciousStewEffects,
		Block nonSeedingFlower,
		BlockBehaviour.Properties settings
	) {
		super(suspiciousStewEffects, settings);
		this.nonSeedingFlower = nonSeedingFlower;
	}

	public Block getNonSeedingFlower() {
		return this.nonSeedingFlower;
	}

	public boolean canShearIntoOriginalFlower(LevelReader level, BlockPos pos, BlockState state) {
		if (this == WWBlocks.SEEDING_DANDELION.get()) return WWBlockConfig.SHEAR_SEEDING_DANDELIONS.get();
		return true;
	}

	@Override
	public InteractionResult useItemOn(
		ItemStack stack,
		BlockState state,
		Level level,
		BlockPos pos,
		Player player,
		InteractionHand hand,
		BlockHitResult hitResult
	) {
		if (this.canShearIntoOriginalFlower(level, pos, state) && stack.is(Items.SHEARS)) {
			onPlayerShear(level, pos, state, player, hand, stack);
			return InteractionResult.SUCCESS;
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	public void onPlayerShear(
		Level level, BlockPos pos, BlockState state, Player user, InteractionHand hand, ItemStack stack
	) {
		level.setBlockAndUpdate(pos, this.getNonSeedingFlower().defaultBlockState());
		if (level.isClientSide()) return;

		this.onShear(level, pos, state, user);
		stack.hurtAndBreak(1, user, hand);
	}

	public void onShear(Level level, BlockPos pos, BlockState state, @Nullable Entity user) {
		level.setBlockAndUpdate(pos, this.getNonSeedingFlower().defaultBlockState());
		if (level.isClientSide()) return;

		level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1F, 1F);
		this.spawnSeedsFrom(level, pos, state, MIN_SEEDS_DESTROY, MAX_SEEDS_DESTROY, null);
		level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
		level.gameEvent(user, GameEvent.SHEAR, pos);
	}

	public void spawnSeedsFrom(Level level, BlockPos pos, BlockState state, int minSeeds, int maxSeeds, Vec3 velocity) {
		final Vec3 offset = state.getOffset(pos);

		final double x = pos.getX() + 0.5D + offset.x;
		final double y = pos.getY() + SEED_SPAWN_HEIGHT;
		final double z = pos.getZ() + 0.5D + offset.z;
		final int count = level.getRandom().nextIntBetweenInclusive(minSeeds, maxSeeds);

		final SeedParticleOptions options = velocity != null
			? SeedParticleOptions.controlled(false, velocity.x, 0.3D, velocity.z)
			: SeedParticleOptions.unControlled(false);

		if (level instanceof ServerLevel server) {
			server.sendParticles(
				options,
				x, y, z,
				count,
				0D, 0D, 0D, 0D
			);
		} else {
			for (int i = 0; i < count; i++) {
				level.addParticle(
					options,
					x, y, z,
					0D, 0D, 0D
				);
			}
		}
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);
		if (random.nextFloat() <= SEED_SPAWN_CHANCE) this.spawnSeedsFrom(level, pos, state, MIN_SEEDS, MAX_SEEDS, null);
		PARTICLE_SPAWNER.tick(level, pos, random);
	}

	@Override
	protected VoxelShape getEntityInsideCollisionShape(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
		return this.getShape(state, level, pos, CollisionContext.of(entity));
	}

	@Override
	public void entityInside(
		BlockState state,
		Level level,
		BlockPos pos,
		Entity entity,
		InsideBlockEffectApplier effectApplier,
		boolean isPrecise
	) {
		if (!level.isClientSide()) return;
		final Vec3 movement = entity.getDeltaMovement();
		final double horizontalDistance = movement.horizontalDistance();
		final double horizontalVelocity = horizontalDistance * 1.9D;

		if (level.getRandom().nextFloat() < (horizontalVelocity * 1.45D)) {
			final int min = Math.min((int) (horizontalVelocity * 2.5D), 3);
			final int max = Math.min((int) (horizontalVelocity * 3.5D), 5);
			this.spawnSeedsFrom(level, pos, state, min, max, movement.normalize().scale(Math.min(horizontalDistance, 0.5D)));
		}
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (level instanceof ServerLevel serverLevel) this.spawnSeedsFrom(serverLevel, pos, state, MIN_SEEDS_DESTROY, MAX_SEEDS_DESTROY, null);
		return super.playerWillDestroy(level, pos, state, player);
	}
}
