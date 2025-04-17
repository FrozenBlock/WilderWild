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

package net.frozenblock.wilderwild.block.entity;

import java.util.ArrayList;
import java.util.Objects;
import net.frozenblock.wilderwild.block.impl.ChestUtil;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.networking.packet.WWStoneChestLidPacket;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoneChestBlockEntity extends ChestBlockEntity {
	public static final float LID_SLAM_INTERVAL = 0.0425F;
	public static final float MAX_OPEN_PERCENTAGE = 0.5F;
	public static final int MAX_TIME_OPEN = 180;
	public static final double MIN_PERCENTAGE_OF_TIME_OPEN = 0.2D;
	private final ContainerOpenersCounter stoneStateManager = new ContainerOpenersCounter() {

		@Override
		protected void onOpen(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
			//StoneChestBlockEntity.playSound(level, pos, state, RegisterSounds.BLOCK_STONE_CHEST_SEARCH);
		}

		@Override
		protected void onClose(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
		}

		@Override
		protected void openerCountChanged(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, int count, int openCount) {
			StoneChestBlockEntity.this.signalOpenCount(level, pos, state, count, openCount);
		}

		@Override
		protected boolean isOwnContainer(@NotNull Player player) {
			if (player.containerMenu instanceof ChestMenu chest) {
				Container inventory = chest.getContainer();
				return inventory == StoneChestBlockEntity.this || inventory instanceof CompoundContainer container && container.contains(StoneChestBlockEntity.this);
			}
			return false;
		}
	};
	public float openProgress;
	public float prevOpenProgress;
	public float highestLidPoint;
	public int stillLidTicks;
	public int cooldownTicks;
	public boolean closing;

	protected long updateTime;

	public StoneChestBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		super(WWBlockEntityTypes.STONE_CHEST, blockPos, blockState);
	}

	public static void serverStoneTick(@NotNull Level level, BlockPos pos, @NotNull BlockState state, @NotNull StoneChestBlockEntity stoneChest) {
		if (level instanceof ServerLevel serverLevel) {
			StoneChestBlockEntity coupledStoneChest = ChestUtil.getCoupledStoneChestBlockEntity(serverLevel, pos, state).orElse(null);
			long gameTime = level.getGameTime();
			if (gameTime != stoneChest.updateTime) {
				if (stoneChest.cooldownTicks > 0) {
					--stoneChest.cooldownTicks;
				}
				stoneChest.prevOpenProgress = stoneChest.openProgress;
				if (stoneChest.stillLidTicks > 0) {
					stoneChest.stillLidTicks -= 1;
				} else if (stoneChest.openProgress > 0F) {
					level.updateNeighbourForOutputSignal(pos, stoneChest.getBlockState().getBlock());
					serverLevel.gameEvent(null, GameEvent.CONTAINER_CLOSE, pos);
					stoneChest.openProgress = Math.max(0F, stoneChest.openProgress - LID_SLAM_INTERVAL);
					if (!stoneChest.closing) {
						stoneChest.closing = true;
						playSound(serverLevel, pos, state, WWSounds.BLOCK_STONE_CHEST_CLOSE_START, WWSounds.BLOCK_STONE_CHEST_CLOSE_START_UNDERWATER, 0.3F);
					}
					if (stoneChest.openProgress <= 0F) {
						stoneChest.onLidSlam(serverLevel, pos, state, coupledStoneChest);
					}
				}
				stoneChest.updateTime = gameTime;
				stoneChest.syncLidValuesAndUpdate(coupledStoneChest);
			}
		}
	}

	public static void clientStoneTick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull StoneChestBlockEntity stoneChest) {
		StoneChestBlockEntity coupledStoneChest = ChestUtil.getCoupledStoneChestBlockEntity(level, pos, state).orElse(null);
		long gameTime = level.getGameTime();
		if (gameTime != stoneChest.updateTime) {
			stoneChest.prevOpenProgress = stoneChest.openProgress;
			if (stoneChest.cooldownTicks > 0) {
				--stoneChest.cooldownTicks;
			}
			if (stoneChest.stillLidTicks > 0) {
				stoneChest.stillLidTicks -= 1;
			} else if (stoneChest.openProgress > 0F) {
				stoneChest.closing = true;
				stoneChest.openProgress = Math.max(0F, stoneChest.openProgress - LID_SLAM_INTERVAL);
				if (stoneChest.openProgress <= 0F) {
					stoneChest.onLidSlam(level, pos, state, coupledStoneChest);
				}
			}
			stoneChest.updateTime = gameTime;
			stoneChest.syncLidValuesAndUpdate(coupledStoneChest);
		}
	}

	public static void playSound(
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull BlockState state,
		@NotNull SoundEvent soundEvent,
		@NotNull SoundEvent waterloggedSoundEvent,
		float volume
	) {
		ChestType chestType = state.getValue(ChestBlock.TYPE);
		double x = pos.getX() + 0.5D;
		double y = pos.getY() + 0.5D;
		double z = pos.getZ() + 0.5D;
		if (chestType == ChestType.RIGHT) {
			Direction direction = ChestBlock.getConnectedDirection(state);
			x += direction.getStepX() * 0.5D;
			z += direction.getStepZ() * 0.5D;
		} else if (chestType == ChestType.LEFT) {
			Direction direction = ChestBlock.getConnectedDirection(state);
			x -= direction.getStepX() * 0.5D;
			z -= direction.getStepZ() * 0.5D;
		}
		level.playSound(
			null,
			x,
			y,
			z,
			state.getValue(BlockStateProperties.WATERLOGGED) && WWBlockConfig.get().chestBubbling ? waterloggedSoundEvent : soundEvent,
			SoundSource.BLOCKS,
			volume,
			level.random.nextFloat() * 0.18F + 0.9F
		);
	}

	@Override
	public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.loadAdditional(tag, provider);
		this.openProgress = tag.getFloat("openProgress");
		this.highestLidPoint = tag.getFloat("highestLidPoint");
		this.stillLidTicks = tag.getInt("stillLidTicks");
		this.cooldownTicks = tag.getInt("cooldownTicks");
		this.closing = tag.getBoolean("closing");
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.saveAdditional(tag, provider);
		tag.putFloat("openProgress", this.openProgress);
		tag.putFloat("highestLidPoint", this.highestLidPoint);
		tag.putInt("stillLidTicks", this.stillLidTicks);
		tag.putInt("cooldownTicks", this.cooldownTicks);
		tag.putBoolean("closing", this.closing);
	}

	public float getOpenProgress(float delta) {
		return Mth.lerp(delta, this.prevOpenProgress, this.openProgress);
	}

	public void liftLid(float liftAmount) {
		this.openProgress = Mth.clamp(this.openProgress + (liftAmount * 1.5F), 0F, MAX_OPEN_PERCENTAGE);
		this.highestLidPoint = this.openProgress;
		this.stillLidTicks = (int) (Math.max((this.openProgress), MIN_PERCENTAGE_OF_TIME_OPEN) * (MAX_TIME_OPEN) * WWBlockConfig.get().stoneChest.getStoneChestTimer());
		if (this.level != null) {
			this.level.updateNeighbourForOutputSignal(this.getBlockPos(), this.getBlockState().getBlock());
		}
	}

	public void setLid(float liftAmount) {
		this.openProgress = Mth.clamp(liftAmount, 0F, MAX_OPEN_PERCENTAGE);
		this.highestLidPoint = this.openProgress;
		this.stillLidTicks = (int) (Math.max((this.openProgress), MIN_PERCENTAGE_OF_TIME_OPEN) * MAX_TIME_OPEN * WWBlockConfig.get().stoneChest.getStoneChestTimer());
		if (this.level != null) {
			this.level.updateNeighbourForOutputSignal(this.getBlockPos(), this.getBlockState().getBlock());
		}
	}

	public int getComparatorOutput() {
		return (int) (this.openProgress * 30F);
	}

	public void onLidSlam(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable StoneChestBlockEntity otherStoneChest) {
		if (!level.isClientSide && level instanceof ServerLevel server) {
			if (this.highestLidPoint > 0.2F) {
				server.sendParticles(
					new BlockParticleOption(ParticleTypes.BLOCK, state),
					pos.getX() + 0.5D,
					pos.getY() + 0.625D,
					pos.getZ() + 0.5D,
					level.random.nextIntBetweenInclusive(3, (int) (this.highestLidPoint * 10) + 2),
					0.21875D,
					0D,
					0.21875D,
					0.05D
				);
				if (otherStoneChest != null) {
					BlockPos otherPos = otherStoneChest.worldPosition;
					server.sendParticles(
						new BlockParticleOption(ParticleTypes.BLOCK, state),
						otherPos.getX() + 0.5D,
						otherPos.getY() + 0.625D,
						otherPos.getZ() + 0.5,
						level.random.nextIntBetweenInclusive(3, (int) (this.highestLidPoint * 10) + 3),
						0.21875D,
						0D,
						0.21875D,
						0.05D
					);
				}
			}
			playSound(level, pos, state, WWSounds.BLOCK_STONE_CHEST_SLAM, WWSounds.BLOCK_STONE_CHEST_SLAM_UNDERWATER, 0.5F + (this.highestLidPoint / 5F));
		}
		this.closing = false;
		this.cooldownTicks = 15;
		this.highestLidPoint = 0;
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		assert this.level != null;
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		}
		return super.stillValid(player) && !this.closing && this.openProgress >= 0.3;
	}

	public void syncLidValuesAndUpdate(@Nullable StoneChestBlockEntity otherStoneChest) {
		boolean shouldSend = !this.level.isClientSide && this.openProgress != this.prevOpenProgress;
		if (otherStoneChest != null) {
			this.syncValues(otherStoneChest);
			if (shouldSend) {
				WWStoneChestLidPacket.sendToAll(otherStoneChest);
			}
		}
		if (shouldSend) {
			WWStoneChestLidPacket.sendToAll(this);
		}
	}

	private void syncValues(@NotNull StoneChestBlockEntity otherStoneChest) {
		if (otherStoneChest.openProgress != this.openProgress && this.level != null) {
			this.level.updateNeighbourForOutputSignal(otherStoneChest.getBlockPos(), otherStoneChest.getBlockState().getBlock());
		}
		otherStoneChest.openProgress = this.openProgress;
		otherStoneChest.prevOpenProgress = this.prevOpenProgress;
		otherStoneChest.highestLidPoint = this.highestLidPoint;
		otherStoneChest.stillLidTicks = this.stillLidTicks;
		otherStoneChest.cooldownTicks = this.cooldownTicks;
		otherStoneChest.closing = this.closing;
		otherStoneChest.updateTime = this.updateTime;
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@NotNull
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return this.saveWithoutMetadata(provider);
	}

	@NotNull
	@Override
	protected Component getDefaultName() {
		return Component.translatable("container.stone_chest");
	}

	@Override
	public void startOpen(@NotNull Player player) {
	}

	@Override
	public void stopOpen(@NotNull Player player) {
		if (!this.remove && !player.isSpectator()) {
			this.stoneStateManager.decrementOpeners(player, Objects.requireNonNull(this.getLevel()), this.getBlockPos(), this.getBlockState());
		}
	}

	@NotNull
	public ArrayList<ItemStack> nonAncientItems() {
		ArrayList<ItemStack> items = new ArrayList<>();
		for (ItemStack item : this.getItems()) {
			CustomData data = item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
			if (!data.contains("wilderwild_is_ancient") && !item.isEmpty()) {
				items.add(item);
			}
		}
		return items;
	}

	@NotNull
	public ArrayList<ItemStack> ancientItems() {
		ArrayList<ItemStack> items = new ArrayList<>();
		for (ItemStack item : this.getItems()) {
			CustomData data = item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
			if (data.contains("wilderwild_is_ancient") && !item.isEmpty()) {
				items.add(item);
			}
		}
		return items;
	}

}
