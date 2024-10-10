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
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
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
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SeedingFlowerBlock extends FlowerBlock {
	public static final float SEED_SPAWN_CHANCE = 0.95F;
	public static final double SEED_SPAWN_HEIGHT = 0.5D;
	public static final int MIN_SEEDS = 1;
	public static final int MAX_SEEDS = 3;
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
		@NotNull Properties settings
	) {
		super(suspiciousStewEffect, effectDuration, settings);
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
		return true;
	}

	@SuppressWarnings("SpellCheckingInspection")
	@Override
	@NotNull
	public InteractionResult useItemOn(
		@NotNull ItemStack stack,
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull Player player,
		@NotNull InteractionHand hand,
		@NotNull BlockHitResult hit
	) {
		if (this.canShearIntoOriginalFlower(level, pos, state) && stack.is(Items.SHEARS)) {
			onPlayerShear(level, pos, state, player, hand, stack);
			return InteractionResult.SUCCESS;
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}

	public void onPlayerShear(
		@NotNull Level level, BlockPos pos, @NotNull BlockState state, @NotNull Player player, @NotNull InteractionHand hand, @NotNull ItemStack stack
	) {
		level.setBlockAndUpdate(pos, this.getNonSeedingFlower().defaultBlockState());
		if (!level.isClientSide) {
			onShear(level, pos, state, player);
			stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
		}
	}

	public void onShear(@NotNull Level level, BlockPos pos, BlockState state, @Nullable Entity entity) {
		level.setBlockAndUpdate(pos, this.getNonSeedingFlower().defaultBlockState());
		if (!level.isClientSide) {
			level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1F, 1F);
			this.spawnSeedsFrom(level, pos, state, true);
			level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
			level.gameEvent(entity, GameEvent.SHEAR, pos);
		}
	}

	public void spawnSeedsFrom(Level level, BlockPos pos, @NotNull BlockState blockState, boolean destroyed) {
		Vec3 offset = blockState.getOffset(pos);
		int min = destroyed ? MIN_SEEDS_DESTROY : MIN_SEEDS;
		int max = destroyed ? MAX_SEEDS_DESTROY : MAX_SEEDS;

		double x = pos.getX() + 0.5D + offset.x;
		double y = pos.getY() + SEED_SPAWN_HEIGHT;
		double z = pos.getZ() + 0.5D + offset.z;

		if (level instanceof ServerLevel server) {
			server.sendParticles(
				SeedParticleOptions.unControlled(false),
				x, y, z,
				server.getRandom().nextIntBetweenInclusive(min, max),
				0D, 0D, 0D, 0D
			);
		} else {
			level.addParticle(
				SeedParticleOptions.unControlled(false),
				x, y, z,
				0D, 0D, 0D
			);
		}
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (random.nextFloat() > SEED_SPAWN_CHANCE) {
			this.spawnSeedsFrom(level, pos, state, false);
		}
	}

	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		if (level instanceof ServerLevel server && server.getRandom().nextFloat() > SEED_SPAWN_CHANCE) {
			this.spawnSeedsFrom(level, pos, state, false);
		}
	}

	@NotNull
	@Override
	public BlockState playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
		BlockState original = super.playerWillDestroy(level, pos, state, player);
		if (level instanceof ServerLevel server) {
			this.spawnSeedsFrom(server, pos, state, true);
		}
		return original;
	}
}
