/*
 * Copyright 2023 FrozenBlock
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

import net.frozenblock.lib.item.api.ItemBlockStateTagUtils;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TintedGlassBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EchoGlassBlock extends TintedGlassBlock {
	public static final IntegerProperty DAMAGE = RegisterProperties.DAMAGE;

	public EchoGlassBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(DAMAGE, 0));
	}

	public static void damage(@NotNull Level level, @NotNull BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		WilderSharedConstants.log("Echo Glass Damaged @ " + pos, WilderSharedConstants.UNSTABLE_LOGGING);
		if (state.getValue(DAMAGE) < 3) {
			level.setBlockAndUpdate(pos, state.setValue(DAMAGE, state.getValue(DAMAGE) + 1));
			level.playSound(null, pos, RegisterSounds.BLOCK_ECHO_GLASS_CRACK, SoundSource.BLOCKS, 0.5F, 0.9F + level.getRandom().nextFloat() * 0.2F);
			if (level instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, level.random.nextInt(18, 25), 0.3F, 0.3F, 0.3F, 0.05D);
			}
		} else {
			level.destroyBlock(pos, false);
		}
	}

	public static void heal(@NotNull Level level, @NotNull BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.getValue(DAMAGE) > 0) {
			WilderSharedConstants.log("Echo Glass Healed @ " + pos, WilderSharedConstants.UNSTABLE_LOGGING);
			level.setBlockAndUpdate(pos, state.setValue(DAMAGE, state.getValue(DAMAGE) - 1));
			level.playSound(
				null,
				pos,
				RegisterSounds.BLOCK_ECHO_GLASS_REPAIR,
				SoundSource.BLOCKS,
				1.0F,
				level.random.nextFloat() * 0.1F + 0.9F
			);
		}
	}

	public static int getLightLevel(@NotNull Level level, @NotNull BlockPos blockPos) {
		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
		int finalLight = 0;
		for (Direction direction : Direction.values()) {
			mutableBlockPos.move(direction);
			int newLight = !level.isRaining() ? level.getMaxLocalRawBrightness(mutableBlockPos) : level.getBrightness(LightLayer.BLOCK, mutableBlockPos);
			finalLight = Math.max(finalLight, newLight);
			mutableBlockPos.move(direction, -1);
		}
		return finalLight;
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(DAMAGE);
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		int light = getLightLevel(level, pos);
		if (light <= 7) {
			if (random.nextBoolean()) {
				heal(level, pos);
			}
		} else {
			damage(level, pos);
		}
	}

	@Override
	public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack stack) {
		if (state.getValue(DAMAGE) < 3 && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem()) < 1 && !player.isCreative()) {
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
		if (projectile instanceof AncientHornProjectile) {
			damage(level, hit.getBlockPos());
		}
		super.onProjectileHit(level, state, hit, projectile);
	}

	@Override
	@NotNull
	public ItemStack getCloneItemStack(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state) {
		ItemStack superStack = super.getCloneItemStack(level, pos, state);
		int damage = state.getValue(RegisterProperties.DAMAGE);
		if (damage != 0) {
			ItemBlockStateTagUtils.setProperty(superStack, RegisterProperties.DAMAGE, damage);
		}
		return superStack;
	}
}
