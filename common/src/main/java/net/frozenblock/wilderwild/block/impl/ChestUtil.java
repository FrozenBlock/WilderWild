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

package net.frozenblock.wilderwild.block.impl;

import java.util.Optional;
import net.frozenblock.lib.platform.api.attachment.DataAttachmentTarget;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

public class ChestUtil {

	public static Optional<ChestBlockEntity> getCoupledChestBlockEntity(LevelReader level, BlockPos pos, BlockState state) {
		if (!state.hasProperty(ChestBlock.TYPE)) return Optional.empty();

		final BlockPos.MutableBlockPos mutable = pos.mutable();
		final ChestType chestType = state.getValue(ChestBlock.TYPE);
		if (chestType == ChestType.RIGHT) {
			mutable.move(ChestBlock.getConnectedDirection(state));
		} else if (chestType == ChestType.LEFT) {
			mutable.move(ChestBlock.getConnectedDirection(state));
		} else {
			return Optional.empty();
		}

		if (level.getBlockEntity(mutable) instanceof ChestBlockEntity chest) return Optional.of(chest);
		return Optional.empty();
	}

	public static Optional<StoneChestBlockEntity> getCoupledStoneChestBlockEntity(LevelAccessor level, BlockPos pos, BlockState state) {
		final Optional<ChestBlockEntity> possibleCoupledChest = getCoupledChestBlockEntity(level, pos, state);
		if (possibleCoupledChest.isPresent() && possibleCoupledChest.get() instanceof StoneChestBlockEntity stoneChest) return Optional.of(stoneChest);
		return Optional.empty();
	}

	public static void trySpawnJellyfish(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if (!(blockEntity instanceof RandomizableContainerBlockEntity containerBlockEntity)) return;
		if (!WWEntityConfig.SPAWN_JELLYFISH.get()) return;
		if (containerBlockEntity.getLootTable() == null) return;
		if (!containerBlockEntity.getLootTable().identifier().getPath().toLowerCase().contains("shipwreck")) return;
		if (!state.getFluidState().is(Fluids.WATER) || level.getRandom().nextInt(0, 3) != 1) return;

		Jellyfish.spawnFromChest(level, state, pos, true);
	}

	public static void onPlaced(Level level, BlockPos pos, BlockState state) {
		ChestUtil.getCoupledChestBlockEntity(level, pos, state).ifPresent(coupledChest -> {
			final BlockEntity chest = level.getBlockEntity(pos);
			if (chest == null) return;
			setCanBubble(chest, canBubble(coupledChest));
			syncBubbles(chest, coupledChest);
		});
	}

	public static void tryTriggerBubble(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if (level == null) return;

		final boolean canBubble = canBubble(blockEntity);
		if (!canBubble || !state.getValueOrElse(BlockStateProperties.WATERLOGGED, false)) return;

		sendBubbleSeedParticle(level, pos);

		setCanBubble(blockEntity, false);

		final Optional<ChestBlockEntity> possibleCoupledChest = ChestUtil.getCoupledChestBlockEntity(level, pos, state);
		possibleCoupledChest.ifPresent(coupledChest -> {
			sendBubbleSeedParticle(level, coupledChest.getBlockPos());
			setCanBubble(coupledChest, false);
		});
	}

	private static void sendBubbleSeedParticle(Level level, BlockPos pos) {
		if (!(level instanceof ServerLevel serverLevel)) return;
		final Vec3 centerPos = Vec3.atCenterOf(pos);
		serverLevel.sendParticles(
			WWParticleTypes.CHEST_BUBBLE_SPAWNER.get(),
			centerPos.x(), centerPos.y(), centerPos.z(),
			1,
			0D, 0D, 0D,
			0D
		);
	}

	public static void bubbleBurst(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if (!(level instanceof ServerLevel serverLevel) || !WWBlockConfig.CHEST_BUBBLING.get()) return;
		if (!state.getFluidState().is(Fluids.WATER) || !canBubble(blockEntity)) return;

		serverLevel.sendParticles(
			ParticleTypes.BUBBLE,
			pos.getX() + 0.5D, pos.getY() + 0.625D, pos.getZ() + 0.5D,
			serverLevel.getRandom().nextInt(18, 25),
			0.21875D, 0D, 0.21875D,
			0.25D
		);
	}

	public static void updateBubbles(BlockState oldState, BlockState state, LevelReader level, BlockPos pos) {
		final BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity == null) return;

		final Optional<ChestBlockEntity> possibleCoupledChest = ChestUtil.getCoupledChestBlockEntity(level, pos, state);
		if (possibleCoupledChest.isPresent()) {
			final ChestBlockEntity coupledChest = possibleCoupledChest.get();
			final BlockState otherState = level.getBlockState(coupledChest.getBlockPos());
			final boolean wasWaterlogged = oldState.getFluidState().is(Fluids.WATER);

			if (wasWaterlogged != state.getFluidState().is(Fluids.WATER) && wasWaterlogged) {
				if (!otherState.getFluidState().is(Fluids.WATER)) {
					setCanBubble(blockEntity, true);
					setCanBubble(coupledChest, true);
				} else if (!canBubble(coupledChest)) {
					setCanBubble(blockEntity, false);
				}
			}

			syncBubbles(blockEntity, coupledChest);
		} else {
			final boolean wasWaterlogged = oldState.getFluidState().is(Fluids.WATER);
			if (wasWaterlogged && !state.getFluidState().is(Fluids.WATER)) setCanBubble(blockEntity, true);
		}
	}

	public static void syncBubbles(DataAttachmentTarget a, DataAttachmentTarget b) {
		if (!canBubble(a) || !canBubble(b)) {
			setCanBubble(a, false);
			setCanBubble(b, false);
		}
	}

	public static boolean canBubble(DataAttachmentTarget target) {
		return target.frozenLib$getAttachedOrElse(WWAttachmentTypes.CHEST_CAN_BUBBLE, true);
	}

	public static void setCanBubble(DataAttachmentTarget target, boolean canBubble) {
		target.frozenLib$setAttached(WWAttachmentTypes.CHEST_CAN_BUBBLE, canBubble);
	}
}
