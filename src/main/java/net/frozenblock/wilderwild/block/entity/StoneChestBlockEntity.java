/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.block.entity;

import java.util.ArrayList;
import java.util.Objects;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.frozenblock.lib.storage.api.NoInteractionStorage;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoneChestBlockEntity extends ChestBlockEntity implements NoInteractionStorage<ItemVariant> {
	public float openProgress;
	public float prevOpenProgress;
	public float highestLidPoint;
	public int stillLidTicks;
	public int cooldownTicks;
	public boolean closing;
	public boolean lootGenerated;

	public boolean hasUpdated = false;
	public boolean shouldSkip = false;

	public StoneChestBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(RegisterBlockEntities.STONE_CHEST, blockPos, blockState);
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		this.openProgress = tag.getFloat("openProgress");
		this.prevOpenProgress = tag.getFloat("prevOpenProgress");
		this.highestLidPoint = tag.getFloat("highestLidPoint");
		this.stillLidTicks = tag.getInt("stillLidTicks");
		this.cooldownTicks = tag.getInt("cooldownTicks");
		this.closing = tag.getBoolean("closing");
		this.shouldSkip = tag.getBoolean("shouldSkip");
		this.lootGenerated = tag.getBoolean("lootGenerated");
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putFloat("openProgress", this.openProgress);
		tag.putFloat("prevOpenProgress", this.prevOpenProgress);
		tag.putFloat("highestLidPoint", this.highestLidPoint);
		tag.putInt("stillLidTicks", this.stillLidTicks);
		tag.putInt("cooldownTicks", this.cooldownTicks);
		tag.putBoolean("closing", this.closing);
		tag.putBoolean("shouldSkip", this.shouldSkip);
		tag.putBoolean("lootGenerated", this.lootGenerated);
	}

	public float getOpenProgress(float delta) {
		return Mth.lerp(delta, this.prevOpenProgress, this.openProgress);
	}

	public static void serverStoneTick(Level level, BlockPos pos, BlockState state, StoneChestBlockEntity blockEntity) {
		ServerLevel serverLevel = (ServerLevel) level;
		StoneChestBlockEntity stoneChest = getOtherEntity(serverLevel, pos, state);
		if (!blockEntity.shouldSkip) {
			if (blockEntity.cooldownTicks > 0) {
				--blockEntity.cooldownTicks;
			}
			boolean canClose = level.getGameRules().getBoolean(WilderWild.STONE_CHEST_CLOSES);
			blockEntity.prevOpenProgress = blockEntity.openProgress;
			if (blockEntity.stillLidTicks > 0) {
				blockEntity.stillLidTicks -= 1;
			} else if (blockEntity.openProgress > 0F && canClose) {
				serverLevel.gameEvent(null, GameEvent.CONTAINER_CLOSE, pos);
				blockEntity.openProgress = Math.max(0F, blockEntity.openProgress - 0.0425F);
				if (!blockEntity.closing) {
					blockEntity.closing = true;
					playSound(serverLevel, pos, state, RegisterSounds.BLOCK_STONE_CHEST_CLOSE_START, RegisterSounds.BLOCK_STONE_CHEST_CLOSE_START_UNDERWATER, 0.3F);
				}
				if (blockEntity.openProgress <= 0F) {
					blockEntity.onLidSlam(serverLevel, pos, state, stoneChest);
				}
			}
			if (isLeft(state)) {
				blockEntity.syncLidValuesWith(serverLevel, pos, state, stoneChest);
			}
		}
		blockEntity.shouldSkip = false;
	}

	public static void clientStoneTick(Level level, BlockPos pos, BlockState state, StoneChestBlockEntity blockEntity) {
		StoneChestBlockEntity stoneChest = getOtherEntity(level, pos, state);
		if (!blockEntity.shouldSkip) {
			if (blockEntity.cooldownTicks > 0) {
				--blockEntity.cooldownTicks;
			}
			boolean canClose = level.getGameRules().getBoolean(WilderWild.STONE_CHEST_CLOSES);
			blockEntity.prevOpenProgress = blockEntity.openProgress;
			if (blockEntity.stillLidTicks > 0) {
				blockEntity.stillLidTicks -= 1;
			} else if (blockEntity.openProgress > 0F && canClose) {
				blockEntity.closing = true;
				blockEntity.openProgress = Math.max(0F, blockEntity.openProgress - 0.0425F);
				if (blockEntity.openProgress <= 0F) {
					blockEntity.onLidSlam(level, pos, state, stoneChest);
				}
			}
		}
		blockEntity.shouldSkip = false;
		if (isLeft(state)) {
			blockEntity.syncLidValuesWith(level, pos, state, stoneChest);
		}
	}

	public void liftLid(float liftAmount, boolean ancient) {
		this.openProgress = Mth.clamp(this.openProgress + (!ancient ? liftAmount * 2 : liftAmount), 0.0F, 0.5F);
		this.highestLidPoint = this.openProgress;
		float multiplier = WilderSharedConstants.config().stoneChestTimer() / 100F;
		this.stillLidTicks = (int) (Math.max((this.openProgress), 0.2) * (!ancient ? 220 : 160) * multiplier);
	}

	public void setLid(float liftAmount) {
		this.openProgress = Mth.clamp(liftAmount, 0.0F, 0.5F);
		this.highestLidPoint = this.openProgress;
		float multiplier = WilderSharedConstants.config().stoneChestTimer() / 100F;
		this.stillLidTicks = (int) (Math.max((this.openProgress), 0.2) * 180 * multiplier);
	}

	public void onLidSlam(Level level, BlockPos pos, BlockState state, StoneChestBlockEntity otherStoneChest) {
		if (!level.isClientSide && level instanceof ServerLevel server) {
			if (this.highestLidPoint > 0.2F) {
				server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5, level.random.nextIntBetweenInclusive(3, (int) (this.highestLidPoint * 10) + 2), 0.21875F, 0, 0.21875F, 0.05D);
				if (otherStoneChest != null) {
					BlockPos otherPos = otherStoneChest.worldPosition;
					server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), otherPos.getX() + 0.5, otherPos.getY() + 0.625, otherPos.getZ() + 0.5, level.random.nextIntBetweenInclusive(3, (int) (this.highestLidPoint * 10) + (state.getValue(RegisterProperties.ANCIENT) ? 4 : 2)), 0.21875F, 0, 0.21875F, 0.05D);
				}
			}
			playSound(level, pos, state, RegisterSounds.BLOCK_STONE_CHEST_SLAM, RegisterSounds.BLOCK_STONE_CHEST_SLAM_UNDERWATER, 0.5F + (this.highestLidPoint / 5F));
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
		return !(player.distanceToSqr((double) this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 0.5, (double) this.worldPosition.getZ() + 0.5) > 64.0) && ((!this.closing && this.openProgress >= 0.3));
	}

	public void syncLidValuesWith(Level level, BlockPos pos, BlockState state, StoneChestBlockEntity otherStoneChest) {
		if (otherStoneChest != null) {
			otherStoneChest.openProgress = this.openProgress;
			otherStoneChest.prevOpenProgress = this.prevOpenProgress;
			otherStoneChest.highestLidPoint = this.highestLidPoint;
			otherStoneChest.stillLidTicks = this.stillLidTicks;
			otherStoneChest.cooldownTicks = this.cooldownTicks;
			otherStoneChest.shouldSkip = true;
			otherStoneChest.closing = this.closing;
			otherStoneChest.updateSync();
		}
		this.updateSync();
	}

	public void updateSync() {
		StoneChestBlockEntity stoneChest = getOtherEntity(this.level, this.worldPosition, this.getBlockState());
		if (stoneChest != null) {
			stoneChest.openProgress = this.openProgress;
			stoneChest.prevOpenProgress = this.prevOpenProgress;
			stoneChest.highestLidPoint = this.highestLidPoint;
			stoneChest.stillLidTicks = this.stillLidTicks;
			stoneChest.cooldownTicks = this.cooldownTicks;
			stoneChest.shouldSkip = true;
			stoneChest.closing = this.closing;
			if (!this.level.isClientSide) {
				for (ServerPlayer player : PlayerLookup.tracking(stoneChest)) {
					player.connection.send(Objects.requireNonNull(stoneChest.getUpdatePacket()));
				}
			}
		}
		if (!this.level.isClientSide) {
			for (ServerPlayer player : PlayerLookup.tracking(this)) {
				player.connection.send(Objects.requireNonNull(this.getUpdatePacket()));
			}
		}
	}

	@Override
	public void unpackLootTable(@Nullable Player player) {
		if (this.lootTable != null && this.level != null && this.level.getServer() != null) {
			this.lootGenerated = false;
			LootTable lootTable = this.level.getServer().getLootData().getLootTable(this.lootTable);
			if (player instanceof ServerPlayer) {
				CriteriaTriggers.GENERATE_LOOT.trigger((ServerPlayer) player, this.lootTable);
			}
			this.lootTable = null;
			LootContext.Builder builder = new LootContext.Builder((ServerLevel) this.level).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition)).withOptionalRandomSeed(this.lootTableSeed);
			if (player != null) {
				builder.withLuck(player.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player);
			}
			lootTable.fill(this, builder.create(LootContextParamSets.CHEST));
			for (ItemStack stack : this.getItems()) {
				stack.addTagElement("wilderwild_is_ancient", ByteTag.valueOf(true));
			}
		}
		this.lootGenerated = true;
	}

	@Override
	public void setItem(int slot, @NotNull ItemStack stack) {
		this.unpackLootTable(null);
		this.getItems().set(slot, stack);
		if (stack.getCount() > this.getMaxStackSize()) {
			stack.setCount(this.getMaxStackSize());
		}
		this.setChanged();
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithoutMetadata();
	}

	public static StoneChestBlockEntity getOtherEntity(Level level, BlockPos pos, BlockState state) {
		ChestType chestType = state.getValue(ChestBlock.TYPE);
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		if (chestType == ChestType.RIGHT) {
			Direction direction = ChestBlock.getConnectedDirection(state);
			x += direction.getStepX();
			z += direction.getStepZ();
		} else if (chestType == ChestType.LEFT) {
			Direction direction = ChestBlock.getConnectedDirection(state);
			x += direction.getStepX();
			z += direction.getStepZ();
		} else {
			return null;
		}
		BlockPos newPos = BlockPos.containing(x, y, z);
		BlockEntity be = level.getBlockEntity(newPos);
		StoneChestBlockEntity entity = null;
		if (be instanceof StoneChestBlockEntity stone) {
			entity = stone;
		}
		return entity;
	}

	public static StoneChestBlockEntity getLeftEntity(Level level, BlockPos pos, BlockState state, StoneChestBlockEntity source) {
		ChestType chestType = state.getValue(ChestBlock.TYPE);
		if (chestType == ChestType.SINGLE) {
			return source;
		}
		double d = pos.getX();
		double e = pos.getY();
		double f = pos.getZ();
		if (chestType == ChestType.RIGHT) {
			Direction direction = ChestBlock.getConnectedDirection(state);
			d += direction.getStepX();
			f += direction.getStepZ();
		} else if (chestType == ChestType.LEFT) {
			return source;
		}
		BlockPos newPos = BlockPos.containing(d, e, f);
		BlockEntity be = level.getBlockEntity(newPos);
		StoneChestBlockEntity entity = null;
		if (be instanceof StoneChestBlockEntity stone) {
			entity = stone;
		}
		return entity;
	}

	public static boolean isLeft(BlockState state) {
		ChestType chestType = state.getValue(ChestBlock.TYPE);
		return chestType == ChestType.LEFT;
	}

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

	public ArrayList<ItemStack> nonAncientItems() {
		ArrayList<ItemStack> items = new ArrayList<>();
		for (ItemStack item : this.getItems()) {
			if (item.getOrCreateTag().get("wilderwild_is_ancient") == null && !item.isEmpty()) {
				items.add(item);
			}
		}
		return items;
	}

	public ArrayList<ItemStack> ancientItems() {
		ArrayList<ItemStack> items = new ArrayList<>();
		for (ItemStack item : this.getItems()) {
			if (item.getOrCreateTag().get("wilderwild_is_ancient") != null && !item.isEmpty()) {
				items.add(item);
			}
		}
		return items;
	}

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
		protected boolean isOwnContainer(Player player) {
			if (player.containerMenu instanceof ChestMenu) {
				Container inventory = ((ChestMenu) player.containerMenu).getContainer();
				return inventory == StoneChestBlockEntity.this || inventory instanceof CompoundContainer && ((CompoundContainer) inventory).contains(StoneChestBlockEntity.this);
			}
			return false;
		}
	};

	public static void playSound(Level level, BlockPos pos, BlockState state, SoundEvent soundEvent, SoundEvent waterloggedSoundEvent, float volume) {
		ChestType chestType = state.getValue(ChestBlock.TYPE);
		double x = (double) pos.getX() + 0.5;
		double y = (double) pos.getY() + 0.5;
		double z = (double) pos.getZ() + 0.5;
		if (chestType == ChestType.RIGHT) {
			Direction direction = ChestBlock.getConnectedDirection(state);
			x += (double) direction.getStepX() * 0.5;
			z += (double) direction.getStepZ() * 0.5;
		} else if (chestType == ChestType.LEFT) {
			Direction direction = ChestBlock.getConnectedDirection(state);
			x -= (double) direction.getStepX() * 0.5;
			z -= (double) direction.getStepZ() * 0.5;
		}
		level.playSound(null, x, y, z, state.getValue(BlockStateProperties.WATERLOGGED) ? waterloggedSoundEvent : soundEvent, SoundSource.BLOCKS, volume, level.random.nextFloat() * 0.18F + 0.9F);
	}

}
