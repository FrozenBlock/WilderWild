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
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.frozenblock.wilderwild.tag.WWEntityTags;
import net.frozenblock.wilderwild.worldgen.impl.util.IcicleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class FragileIceBlock extends HalfTransparentBlock {
	public static final MapCodec<FragileIceBlock> CODEC = simpleCodec(FragileIceBlock::new);
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	public static final IntProvider SHEET_SHATTER_DELAY = UniformInt.of(1, 5);
	public static final float NEIGHBOR_CHANGE_CHANCE = 0.55F;
	public static final int DELAY_BETWEEN_CRACKS = 20;

	@Override
	public @NotNull MapCodec<FragileIceBlock> codec() {
		return CODEC;
	}

	public FragileIceBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
	}

	private void crackOrDestroy(@NotNull BlockState state, Level level, BlockPos pos) {
		final int age = state.getValue(AGE);
		if (age >= 2) {
			level.destroyBlock(pos, false);
			return;
		}

		level.setBlock(pos, state.setValue(AGE, age + 1), UPDATE_CLIENTS);
		final SoundType soundType = this.getSoundType(state);
		level.playSound(null, pos, soundType.getBreakSound(), SoundSource.BLOCKS, 0.1F, (soundType.getPitch() + 0.2F) + level.getRandom().nextFloat() * 0.2F);
		if (!(level instanceof ServerLevel serverLevel)) return ;
		serverLevel.sendParticles(
			new BlockParticleOption(ParticleTypes.BLOCK, state),
			pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
			level.random.nextInt(20, 30),
			0.3F, 0.3F, 0.3F,
			0.05D
		);
	}

	public void scheduleCrackIfNotScheduled(@NotNull Level level, BlockPos pos) {
		if (!level.getBlockTicks().hasScheduledTick(pos, this)) level.scheduleTick(pos, this, DELAY_BETWEEN_CRACKS);
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		this.crackOrDestroy(state, level, pos);
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean bl) {
		final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		for (Direction direction : Direction.values()) {
			mutableBlockPos.setWithOffset(pos, direction);
			if (!level.getBlockState(mutableBlockPos).is(this)) continue;
			if (level.getRandom().nextFloat() <= NEIGHBOR_CHANGE_CHANCE) this.scheduleShatter(level, mutableBlockPos, state, level.getRandom());
		}
		super.affectNeighborsAfterRemoval(state, level, pos, bl);
	}

	@Override
	protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, BlockPos pos, @NotNull RandomSource random) {
		if (random.nextFloat() <= 0.075F) {
			IcicleUtils.growIcicleOnRandomTick(level, pos);
			return;
		}
		this.heal(state, level, pos);
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, @NotNull Entity entity) {
		if (entity.getType().is(WWEntityTags.FRAGILE_ICE_UNWALKABLE_MOBS)) this.scheduleCrackIfNotScheduled(level, pos);
	}

	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
		super.fallOn(level, state, pos, entity, fallDistance);
		if (entity.getType().is(WWEntityTags.FRAGILE_ICE_DOESNT_CRACK_ON_FALL)) return;
		if (fallDistance >= 4F) {
			level.destroyBlock(pos, false);
			if (entity instanceof ServerPlayer serverPlayer) WWCriteria.FRAGILE_ICE_FAL_ONTO_AND_BREAK.trigger(serverPlayer);
		}
	}

	@Override
	protected void onProjectileHit(Level level, BlockState state, BlockHitResult hitResult, @NotNull Projectile projectile) {
		if (projectile.getType().is(WWEntityTags.FRAGILE_ICE_DOESNT_CRACK_PROJECTILE)) return;
		final double velocity = projectile.getDeltaMovement().length();
		if (velocity >= 1.6D) level.destroyBlock(hitResult.getBlockPos(), false);
	}

	public void scheduleShatter(@NotNull Level level, BlockPos pos, @NotNull BlockState state, RandomSource random) {
		level.setBlock(pos, state.setValue(AGE, 2), UPDATE_CLIENTS);
		level.scheduleTick(pos, this, SHEET_SHATTER_DELAY.sample(random));
	}

	private void heal(@NotNull BlockState state, Level level, BlockPos pos) {
		if (state.getValue(AGE) > 0) level.setBlock(pos, state.setValue(AGE, 0), UPDATE_CLIENTS);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
