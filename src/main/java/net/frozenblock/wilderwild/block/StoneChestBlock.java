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
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.entity.impl.ChestBlockEntityInterface;
import net.frozenblock.wilderwild.block.impl.ChestUtil;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class StoneChestBlock extends ChestBlock {
	public static final MapCodec<StoneChestBlock> CODEC = simpleCodec(properties -> new StoneChestBlock(() -> WWBlockEntityTypes.STONE_CHEST, properties));
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
	public MapCodec<? extends StoneChestBlock> codec() {
		return CODEC;
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide()) return InteractionResult.SUCCESS;

		if (!(level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChest)) return InteractionResult.CONSUME;
		if (stoneChest.closing) return InteractionResult.FAIL;

		if (canInteract(level, pos)) {
			final MenuProvider menuProvider = this.getMenuProvider(state, level, pos);
			if (!hasLid(level, pos) && (!player.isShiftKeyDown() || stoneChest.openProgress >= MAX_OPENABLE_PROGRESS) && menuProvider != null) {
				player.openMenu(menuProvider);
				player.awardStat(this.getOpenChestStat());
				PiglinAi.angerNearbyPiglins((ServerLevel) level, player, true);
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
					((ChestBlockEntityInterface) stoneChest).wilderWild$bubble(level, pos, state);
					final ResourceKey<LootTable> lootTable = stoneChest.lootTable;
					if (lootTable != null && state.getValueOrElse(BlockStateProperties.WATERLOGGED, false)
						&& lootTable.identifier().getPath().toLowerCase().contains("shipwreck")
					) {
						Jellyfish.spawnFromChest(level, state, pos, true);
					}
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

		Optional<StoneChestBlockEntity> possibleCoupledStoneChest = ChestUtil.getCoupledStoneChestBlockEntity(level, pos, state);
		possibleCoupledStoneChest.ifPresent(otherStoneChest -> {
			if (otherStoneChest instanceof ChestBlockEntityInterface chestInterface) chestInterface.wilderWild$syncBubble(stoneChest, otherStoneChest);
		});
		stoneChest.syncLidValuesAndUpdate(possibleCoupledStoneChest.orElse(null));

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
	protected BlockState updateShape(
		BlockState state,
		LevelReader level,
		ScheduledTickAccess scheduledTickAccess,
		BlockPos pos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource random
	) {
		if (state.getValue(WATERLOGGED)) scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

		BlockState retState = state;
		if (neighborState.is(this) && direction.getAxis().isHorizontal()) {
			final ChestType chestType = neighborState.getValue(TYPE);
			if (state.getValue(TYPE) == ChestType.SINGLE
				&& chestType != ChestType.SINGLE
				&& state.getValue(FACING) == neighborState.getValue(FACING)
				&& getConnectedDirection(neighborState) == direction.getOpposite()
			) {
				retState = state.setValue(TYPE, chestType.getOpposite());
			}
		} else if (getConnectedDirection(state) == direction) {
			retState = state.setValue(TYPE, ChestType.SINGLE);
		} else {
			retState = super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
		}

		ChestUtil.updateBubbles(state, retState, level, pos);
		return retState;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		final Level level = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		final FluidState fluidState = context.getLevel().getFluidState(pos);
		final boolean secondaryUseActive = context.isSecondaryUseActive();
		final Direction clickedFace = context.getClickedFace();

		Direction direction3;
		ChestType chestType = ChestType.SINGLE;
		Direction direction = context.getHorizontalDirection().getOpposite();

		if (clickedFace.getAxis().isHorizontal()
			&& secondaryUseActive
			&& (direction3 = this.candidatePartnerFacing(context, clickedFace.getOpposite())) != null
			&& direction3.getAxis() != clickedFace.getAxis()
		) {
			direction = direction3;
			chestType = direction.getCounterClockWise() == clickedFace.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
		}
		if (chestType == ChestType.SINGLE && !secondaryUseActive) {
			if (direction == this.candidatePartnerFacing(context, direction.getClockWise())) {
				chestType = ChestType.LEFT;
			} else if (direction == this.candidatePartnerFacing(context, direction.getCounterClockWise())) {
				chestType = ChestType.RIGHT;
			}
		}

		final BlockState retState = this.defaultBlockState()
			.setValue(FACING, direction)
			.setValue(TYPE, chestType)
			.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);

		ChestUtil.getCoupledStoneChestBlockEntity(level, pos, retState).ifPresent(coupledStoneChest -> {
			if (coupledStoneChest instanceof ChestBlockEntityInterface otherChestInterface
				&& level.getBlockEntity(pos) instanceof StoneChestBlockEntity chest
				&& chest instanceof ChestBlockEntityInterface chestInterface
			) {
				chestInterface.wilderWild$setCanBubble(otherChestInterface.wilderWild$getCanBubble());
				chestInterface.wilderWild$syncBubble(chest, coupledStoneChest);
			}
		});
		return retState;
	}

	@Nullable
	private Direction candidatePartnerFacing(BlockPlaceContext context, Direction direction) {
		final BlockState state = context.getLevel().getBlockState(context.getClickedPos().relative(direction));
		return state.is(this) && state.getValue(TYPE) == ChestType.SINGLE ? state.getValue(FACING) : null;
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean bl) {
		if (!(level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChest)) return;
		if (stoneChest instanceof ChestBlockEntityInterface chestInterface) chestInterface.wilderWild$bubbleBurst(state);
		level.updateNeighbourForOutputSignal(pos, this);
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
