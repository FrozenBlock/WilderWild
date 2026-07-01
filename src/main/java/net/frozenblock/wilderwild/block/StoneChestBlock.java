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

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.impl.ChestUtil;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class StoneChestBlock extends ChestBlock {
	public static final float MIN_OPENABLE_PROGRESS = 0.3F;
	public static final float MAX_OPENABLE_PROGRESS = 0.5F;
	public static final float LIFT_AMOUNT = 0.025F;
	public static final float MAX_LIFT_AMOUNT_UNDER_SOLID_BLOCK = 0.05F;
	public static final BooleanProperty SCULK = WWBlockStateProperties.HAS_SCULK;
	public static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>> STONE_NAME_RETRIEVER = new DoubleBlockCombiner.Combiner<>() {

		@Override
		public Optional<MenuProvider> acceptDouble(final ChestBlockEntity chest1, final ChestBlockEntity chest2) {
			final CompoundContainer inventory = new CompoundContainer(chest1, chest2);
			return Optional.of(new MenuProvider() {

				@Override
				@Nullable
				public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player player) {
					if (!chest1.canOpen(player) || !chest2.canOpen(player)) return null;
					chest1.unpackLootTable(playerInventory.player);
					chest2.unpackLootTable(playerInventory.player);
					return ChestMenu.sixRows(i, playerInventory, inventory);
				}

				@Override
				public Component getDisplayName() {
					if (chest1.hasCustomName()) return chest1.getDisplayName();
					if (chest2.hasCustomName()) return chest2.getDisplayName();
					return Component.translatable("container.double_stone_chest");
				}
			});
		}

		@Override
		public Optional<MenuProvider> acceptSingle(ChestBlockEntity chest) {
			return Optional.of(chest);
		}

		@Override
		public Optional<MenuProvider> acceptNone() {
			return Optional.empty();
		}

	};

	public StoneChestBlock(Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier, Properties properties) {
		super(supplier, WWSounds.BLOCK_STONE_CHEST_OPEN, WWSounds.BLOCK_STONE_CHEST_CLOSE_START, properties);
		this.registerDefaultState(this.defaultBlockState().setValue(SCULK, false));
	}

	public static boolean hasLid(Level level, BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChest) return stoneChest.openProgress < MIN_OPENABLE_PROGRESS;
		return false;
	}

	public static boolean canInteract(LevelAccessor level, BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChest) return !(stoneChest.closing || stoneChest.cooldownTicks > 0);
		return true;
	}

	public static boolean hasLid(LevelAccessor level, BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChest) return stoneChest.openProgress < MIN_OPENABLE_PROGRESS;
		return false;
	}

	public static boolean isStoneChestBlocked(LevelAccessor level, BlockPos pos) {
		if (hasLid(level, pos)) return true;
		return ChestBlock.isChestBlockedAt(level, pos) || !canInteract(level, pos);
	}

	public static boolean isStoneChestBlockedNoLid(LevelAccessor level, BlockPos pos) {
		return ChestBlock.isChestBlockedAt(level, pos) || !canInteract(level, pos);
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide()) return InteractionResult.SUCCESS;

		if (!(level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChest)) return InteractionResult.CONSUME;
		if (stoneChest.closing) return InteractionResult.FAIL;

		if (canInteract(level, pos)) {
			final MenuProvider menuProvider = this.getMenuProvider(state, level, pos);
			if (!hasLid(level, pos) && (!player.isShiftKeyDown() || stoneChest.openProgress >= MAX_OPENABLE_PROGRESS) && menuProvider != null) {
				return super.useWithoutItem(state, level, pos, player, hitResult);
			} else if (stoneChest.openProgress < MAX_OPENABLE_PROGRESS) {
				final MenuProvider lidCheck = this.getBlockEntitySourceIgnoreLid(state, level, pos, false).apply(STONE_NAME_RETRIEVER).orElse(null);
				final boolean isFirstLift = stoneChest.openProgress == 0F;
				if (lidCheck == null) {
					if (stoneChest.openProgress >= MAX_LIFT_AMOUNT_UNDER_SOLID_BLOCK) return InteractionResult.PASS;
					stoneChest.setLid(stoneChest.openProgress + LIFT_AMOUNT);
				} else {
					stoneChest.liftLid(LIFT_AMOUNT);
				}

				if (isFirstLift) {
					ChestUtil.trySpawnJellyfish(level, pos, state, stoneChest);
					ChestUtil.tryTriggerBubble(level, pos, state, stoneChest);
				}
				StoneChestBlockEntity.playSound(
					level,
					pos,
					state,
					isFirstLift ? WWSounds.BLOCK_STONE_CHEST_OPEN : WWSounds.BLOCK_STONE_CHEST_LIFT,
					isFirstLift ? WWSounds.BLOCK_STONE_CHEST_OPEN_UNDERWATER : WWSounds.BLOCK_STONE_CHEST_LIFT_UNDERWATER,
					0.35F
				);
				level.gameEvent(player, GameEvent.CONTAINER_OPEN, pos);
			}
		}

		stoneChest.syncLidValuesAndUpdate(ChestUtil.getCoupledStoneChestBlockEntity(level, pos, state).orElse(null));

		return InteractionResult.CONSUME;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new StoneChestBlockEntity(pos, state);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return level.isClientSide()
			? BaseEntityBlock.createTickerHelper(type, WWBlockEntityTypes.STONE_CHEST, StoneChestBlockEntity::clientStoneTick)
			: BaseEntityBlock.createTickerHelper(type, WWBlockEntityTypes.STONE_CHEST, StoneChestBlockEntity::serverStoneTick);
	}

	@Override
	@Nullable
	public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return this.combine(state, level, pos, false).apply(STONE_NAME_RETRIEVER).orElse(null);
	}

	@Override
	public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState state, Level level, BlockPos pos, boolean ignoreBlocked) {
		final BiPredicate<LevelAccessor, BlockPos> isBlocked = ignoreBlocked ? (levelx, posx) -> false : StoneChestBlock::isStoneChestBlocked;
		return DoubleBlockCombiner.combineWithNeigbour(
			this.blockEntityType.get(),
			ChestBlock::getBlockType,
			ChestBlock::getConnectedDirection,
			FACING,
			state,
			level,
			pos,
			isBlocked
		);
	}

	public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> getBlockEntitySourceIgnoreLid(BlockState state, Level level, BlockPos pos, boolean ignoreBlocked) {
		final BiPredicate<LevelAccessor, BlockPos> isBlocked = ignoreBlocked ? (levelx, posx) -> false : StoneChestBlock::isStoneChestBlockedNoLid;
		return DoubleBlockCombiner.combineWithNeigbour(
			this.blockEntityType.get(),
			ChestBlock::getBlockType,
			ChestBlock::getConnectedDirection,
			FACING,
			state,
			level,
			pos,
			isBlocked
		);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
		if (level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChestBlockEntity) return stoneChestBlockEntity.getComparatorOutput();
		return 0;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(SCULK);
	}
}
