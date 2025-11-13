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
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SeedingFlowerBlock extends FlowerBlock {
	public static final float SEED_SPAWN_CHANCE = 0.04F;
	public static final double SEED_SPAWN_HEIGHT = 0.5D;
	public static final int MIN_SEEDS = 1;
	public static final int MAX_SEEDS = 2;
	public static final int MIN_SEEDS_DESTROY = 3;
	public static final int MAX_SEEDS_DESTROY = 7;
	public static final MapCodec<SeedingFlowerBlock> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
			EFFECTS_FIELD.forGetter(FlowerBlock::getSuspiciousEffects),
			BuiltInRegistries.BLOCK.byNameCodec().fieldOf("non_seeding_flower").forGetter(SeedingFlowerBlock::getNonSeedingFlower),
			propertiesCodec()
		).apply(instance, SeedingFlowerBlock::new)
	);

	private final Block nonSeedingFlower;

	public SeedingFlowerBlock(
		@NotNull Holder<MobEffect> suspiciousStewEffect,
		int effectDuration,
		Block nonSeedingFlower,
		@NotNull Properties properties
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

	@Override
	public @NotNull MapCodec<? extends SeedingFlowerBlock> codec() {
		return CODEC;
	}

	public Block getNonSeedingFlower() {
		return this.nonSeedingFlower;
	}

	public boolean canShearIntoOriginalFlower(LevelReader world, BlockPos pos, BlockState state) {
		if (this == WWBlocks.SEEDING_DANDELION) return WWBlockConfig.get().flower.shearSeedingDandelions;
		return true;
	}

	@Override
	@NotNull
	public InteractionResult useItemOn(
		@NotNull ItemStack stack,
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull Player player,
		@NotNull InteractionHand hand,
		@NotNull BlockHitResult hitResult
	) {
		if (this.canShearIntoOriginalFlower(level, pos, state) && stack.is(Items.SHEARS)) {
			onPlayerShear(level, pos, state, player, hand, stack);
			return InteractionResult.SUCCESS;
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	public void onPlayerShear(
		@NotNull Level level, BlockPos pos, @NotNull BlockState state, @NotNull Player player, @NotNull InteractionHand hand, @NotNull ItemStack stack
	) {
		level.setBlockAndUpdate(pos, this.getNonSeedingFlower().defaultBlockState());
		if (level.isClientSide()) return;
		onShear(level, pos, state, player);
		stack.hurtAndBreak(1, player, hand);
	}

	public void onShear(@NotNull Level level, BlockPos pos, BlockState state, @Nullable Entity entity) {
		level.setBlockAndUpdate(pos, this.getNonSeedingFlower().defaultBlockState());
		if (level.isClientSide()) return;
		level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1F, 1F);
		this.spawnSeedsFrom(level, pos, state, MIN_SEEDS_DESTROY, MAX_SEEDS_DESTROY, null);
		level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
		level.gameEvent(entity, GameEvent.SHEAR, pos);
	}

	public void spawnSeedsFrom(@NotNull Level level, BlockPos pos, @NotNull BlockState state, int minSeeds, int maxSeeds, Vec3 velocity) {
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
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (random.nextFloat() <= SEED_SPAWN_CHANCE) this.spawnSeedsFrom(level, pos, state, MIN_SEEDS, MAX_SEEDS, null);
	}

	@Override
	public void entityInside(
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull Entity entity,
		InsideBlockEffectApplier insideBlockEffectApplier,
		boolean bl
	) {
		if (!level.isClientSide()) return;
		final AABB shape = this.getShape(state, level, pos, CollisionContext.of(entity)).bounds().move(pos);
		if (!shape.intersects(entity.getBoundingBox())) return;

		final Vec3 movement = entity.getDeltaMovement();
		final double horizontalDistance = movement.horizontalDistance();
		final double horizontalVelocity = horizontalDistance * 1.9D;

		if (level.random.nextFloat() < (horizontalVelocity * 1.45D)) {
			final int min = Math.min((int) (horizontalVelocity * 2.5D), 3);
			final int max = Math.min((int) (horizontalVelocity * 3.5D), 5);
			this.spawnSeedsFrom(level, pos, state, min, max, movement.normalize().scale(Math.min(horizontalDistance, 0.5D)));
		}
	}

	@NotNull
	@Override
	public BlockState playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
		if (level instanceof ServerLevel server) this.spawnSeedsFrom(server, pos, state, MIN_SEEDS_DESTROY, MAX_SEEDS_DESTROY, null);
		return super.playerWillDestroy(level, pos, state, player);
	}
}
