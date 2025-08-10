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
import net.frozenblock.lib.item.api.ItemBlockStateTagUtils;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EchoGlassBlock extends TransparentBlock {
	public static final int MIN_CRACK_PARTICLES = 18;
	public static final int MAX_DAMAGE_PARTICLES = 25;
	public static final IntegerProperty DAMAGE = WWBlockStateProperties.DAMAGE;
	public static final MapCodec<EchoGlassBlock> CODEC = simpleCodec(EchoGlassBlock::new);

	public EchoGlassBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(DAMAGE, 0));
	}

	public static boolean canDamage(@NotNull BlockState state) {
		return state.hasProperty(DAMAGE) && state.getValue(DAMAGE) < 3;
	}

	public static void setDamagedState(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState blockState) {
		level.setBlockAndUpdate(pos, blockState.cycle(DAMAGE));
	}

	public static void damage(@NotNull Level level, @NotNull BlockPos pos, BlockState blockState, boolean shouldDrop) {
		if (!canDamage(blockState)) {
			level.destroyBlock(pos, shouldDrop);
			return;
		}
		setDamagedState(level, pos, blockState);
		level.playSound(
			null,
			pos,
			WWSounds.BLOCK_ECHO_GLASS_CRACK,
			SoundSource.BLOCKS,
			0.5F,
			0.9F + level.random.nextFloat() * 0.2F
		);
		if (level instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
				new BlockParticleOption(ParticleTypes.BLOCK, blockState),
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				level.random.nextInt(MIN_CRACK_PARTICLES, MAX_DAMAGE_PARTICLES),
				0.3F,
				0.3F,
				0.3F,
				0.05D
			);
		}
	}

	public static void heal(@NotNull Level level, @NotNull BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (!state.hasProperty(DAMAGE)) return;

		int damage = state.getValue(DAMAGE);
		if (damage > 0) {
			level.setBlockAndUpdate(pos, state.setValue(DAMAGE, damage - 1));
			level.playSound(
				null,
				pos,
				WWSounds.BLOCK_ECHO_GLASS_REPAIR,
				SoundSource.BLOCKS,
				1F,
				level.random.nextFloat() * 0.1F + 0.9F
			);
		}
	}

	public static int getLightLevel(@NotNull Level level, @NotNull BlockPos blockPos) {
		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
		int finalLight = 0;
		for (Direction direction : Direction.values()) {
			mutableBlockPos.setWithOffset(blockPos, direction);
			int newLight = !level.isRaining() ? level.getMaxLocalRawBrightness(mutableBlockPos) : level.getBrightness(LightLayer.BLOCK, mutableBlockPos);
			finalLight = Math.max(finalLight, newLight);
		}
		return finalLight;
	}

	@NotNull
	@Override
	public MapCodec<? extends EchoGlassBlock> codec() {
		return CODEC;
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState blockState) {
		return false;
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(DAMAGE);
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (getLightLevel(level, pos) <= 8) {
			if (random.nextBoolean()) heal(level, pos);
		} else {
			if (random.nextFloat() <= 0.75F) damage(level, pos, state, true);
		}
	}

	@Override
	public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack stack) {
		var silkTouch = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
		if (canDamage(state) && EnchantmentHelper.getItemEnchantmentLevel(silkTouch, player.getMainHandItem()) < 1 && !player.isCreative()) {
			level.setBlockAndUpdate(pos, state.setValue(DAMAGE, state.getValue(DAMAGE) + 1));
			player.causeFoodExhaustion(0.005F);
		} else {
			super.playerDestroy(level, player, pos, state, blockEntity, stack);
			level.playSound(
				null,
				pos,
				SoundEvents.GLASS_BREAK,
				SoundSource.BLOCKS,
				0.9F,
				level.random.nextFloat() * 0.1F + 0.8F
			);
		}
	}

	@Override
	public void onProjectileHit(@NotNull Level level, @NotNull BlockState state, @NotNull BlockHitResult hit, @NotNull Projectile projectile) {
		super.onProjectileHit(level, state, hit, projectile);
		damage(level, hit.getBlockPos(), state, true);
	}

	@Override
	protected ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean bl) {
		ItemStack superStack = super.getCloneItemStack(levelReader, blockPos, blockState, bl);
		int damage = blockState.getValue(WWBlockStateProperties.DAMAGE);
		if (damage != 0) ItemBlockStateTagUtils.setProperty(superStack, WWBlockStateProperties.DAMAGE, damage);
		return superStack;
	}
}
