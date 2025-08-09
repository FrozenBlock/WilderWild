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

import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FloweringWaterlilyBlock extends WaterlilyBlock {
	private final Block nonFloweringBlock;

	public FloweringWaterlilyBlock(Block nonFloweringBlock, Properties properties) {
		super(properties);
		this.nonFloweringBlock = nonFloweringBlock;
	}

	public Block getNonFloweringBlock() {
		return this.nonFloweringBlock;
	}

	public boolean canShearIntoOriginalBlock(LevelReader world, BlockPos pos, BlockState state) {
		return WWBlockConfig.get().flower.shearFloweringLilypads;
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
		@NotNull BlockHitResult hit
	) {
		if (this.canShearIntoOriginalBlock(level, pos, state) && stack.is(Items.SHEARS)) {
			onPlayerShear(level, pos, state, player, hand, stack);
			return InteractionResult.SUCCESS;
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}

	public void onPlayerShear(
		@NotNull Level level, BlockPos pos, @NotNull BlockState state, @NotNull Player player, @NotNull InteractionHand hand, @NotNull ItemStack stack
	) {
		level.setBlockAndUpdate(pos, this.getNonFloweringBlock().defaultBlockState());
		if (!level.isClientSide()) {
			onShear(level, pos, state, player);
			stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
		}
	}

	public void onShear(@NotNull Level level, BlockPos pos, BlockState state, @Nullable Entity entity) {
		level.setBlockAndUpdate(pos, this.getNonFloweringBlock().defaultBlockState());
		if (!level.isClientSide()) {
			level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1F, 1F);
			level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
			level.gameEvent(entity, GameEvent.SHEAR, pos);
		}
	}
}
