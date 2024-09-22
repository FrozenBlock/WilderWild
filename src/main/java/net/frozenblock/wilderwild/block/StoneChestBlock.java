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
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.entity.impl.ChestBlockEntityInterface;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoneChestBlock extends ChestBlock {
	public static final MapCodec<StoneChestBlock> CODEC = simpleCodec((properties) ->
		new StoneChestBlock(properties, () -> WWBlockEntityTypes.STONE_CHEST)
	);
	public static final float MIN_OPENABLE_PROGRESS = 0.3F;
	public static final float MAX_OPENABLE_PROGRESS = 0.5F;
	public static final float LIFT_AMOUNT = 0.025F;
	public static final float MAX_LIFT_AMOUNT_UNDER_SOLID_BLOCK = 0.05F;
	public static final double BREAK_PARTICLE_Y_OFFSET = 0.3D;
	public static final float BREAK_PARTICLE_OFFSET = 0.21875F;
	public static final double BREAK_PARTICLE_SPEED = 0.05D;
	public static final int MIN_BREAK_PARTICLES = 1;
	public static final int MAX_BREAK_PARTICLES = 3;
	public static final double ITEM_DELTA_TRIANGLE_B = 0.11485D;
	public static final double ITEM_DELTA_TRIANGLE_A_Y = 0.2D;
	public static final double ITEM_DELTA_TRIANGLE_A_XZ = 0D;
	public static final BooleanProperty ANCIENT = WWBlockStateProperties.ANCIENT;
	public static final BooleanProperty SCULK = WWBlockStateProperties.HAS_SCULK;
	public static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>> STONE_NAME_RETRIEVER = new DoubleBlockCombiner.Combiner<>() {

		@Override
		@NotNull
		public Optional<MenuProvider> acceptDouble(final @NotNull ChestBlockEntity chestBlockEntity, final @NotNull ChestBlockEntity chestBlockEntity2) {
			final CompoundContainer inventory = new CompoundContainer(chestBlockEntity, chestBlockEntity2);
			return Optional.of(new MenuProvider() {

				@Override
				@Nullable
				public AbstractContainerMenu createMenu(int i, @NotNull Inventory playerInventory, @NotNull Player playerEntity) {
					if (chestBlockEntity.canOpen(playerEntity) && chestBlockEntity2.canOpen(playerEntity)) {
						chestBlockEntity.unpackLootTable(playerInventory.player);
						chestBlockEntity2.unpackLootTable(playerInventory.player);
						return ChestMenu.sixRows(i, playerInventory, inventory);
					}
					return null;
				}

				@Override
				@NotNull
				public Component getDisplayName() {
					if (chestBlockEntity.hasCustomName()) {
						return chestBlockEntity.getDisplayName();
					}
					if (chestBlockEntity2.hasCustomName()) {
						return chestBlockEntity2.getDisplayName();
					}
					return Component.translatable("container.double_stone_chest");
				}
			});
		}

		@Override
		@NotNull
		public Optional<MenuProvider> acceptSingle(@NotNull ChestBlockEntity chestBlockEntity) {
			return Optional.of(chestBlockEntity);
		}

		@Override
		@NotNull
		public Optional<MenuProvider> acceptNone() {
			return Optional.empty();
		}

	};

	public StoneChestBlock(@NotNull Properties settings, @NotNull Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
		super(settings, supplier);
		this.registerDefaultState(this.defaultBlockState().setValue(ANCIENT, false).setValue(SCULK, false));
	}

	public static boolean hasLid(@NotNull Level level, @NotNull BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChest) {
			return stoneChest.openProgress < MIN_OPENABLE_PROGRESS;
		}
		return false;
	}

	public static boolean canInteract(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChest) {
			return !(stoneChest.closing || stoneChest.cooldownTicks > 0);
		}
		return true;
	}

	public static boolean hasLid(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChest) {
			return stoneChest.openProgress < MIN_OPENABLE_PROGRESS;
		}
		return false;
	}

	public static boolean isStoneChestBlocked(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		if (hasLid(level, pos)) {
			return true;
		}
		return ChestBlock.isChestBlockedAt(level, pos) || !canInteract(level, pos);
	}

	public static boolean isStoneChestBlockedNoLid(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		return ChestBlock.isChestBlockedAt(level, pos) || !canInteract(level, pos);
	}

	public static void spawnBreakParticles(@NotNull Level level, @NotNull ItemStack stack, @NotNull BlockPos pos) {
		if (level instanceof ServerLevel server) {
			server.sendParticles(
				new ItemParticleOption(ParticleTypes.ITEM, stack),
				pos.getX() + 0.5D,
				pos.getY() + BREAK_PARTICLE_Y_OFFSET,
				pos.getZ() + 0.5D,
				level.getRandom().nextIntBetweenInclusive(MIN_BREAK_PARTICLES, MAX_BREAK_PARTICLES),
				BREAK_PARTICLE_OFFSET,
				BREAK_PARTICLE_OFFSET,
				BREAK_PARTICLE_OFFSET,
				BREAK_PARTICLE_SPEED
			);
		}
	}

	@Nullable
	public static StoneChestBlockEntity getOtherChest(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		ChestType chestType = state.getValue(ChestBlock.TYPE);
		if (chestType == ChestType.RIGHT) {
			mutableBlockPos.move(ChestBlock.getConnectedDirection(state));
		} else if (chestType == ChestType.LEFT) {
			mutableBlockPos.move(ChestBlock.getConnectedDirection(state));
		} else {
			return null;
		}
		if (level.getBlockEntity(mutableBlockPos) instanceof StoneChestBlockEntity chest) {
			return chest;
		}
		return null;
	}

	@NotNull
	@Override
	public MapCodec<? extends StoneChestBlock> codec() {
		return CODEC;
	}

	@NotNull
	@Override
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		if (level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChest) {
			if (stoneChest.closing) {
				return InteractionResult.FAIL;
			}
			boolean ancient = state.getValue(ANCIENT);
			if (canInteract(level, pos)) {
				MenuProvider namedScreenHandlerFactory = this.getMenuProvider(state, level, pos);
				if (!hasLid(level, pos) && (!player.isShiftKeyDown() || stoneChest.openProgress >= MAX_OPENABLE_PROGRESS) && namedScreenHandlerFactory != null) {
					player.openMenu(namedScreenHandlerFactory);
					player.awardStat(this.getOpenChestStat());
					PiglinAi.angerNearbyPiglins(player, true);
				} else if (stoneChest.openProgress < MAX_OPENABLE_PROGRESS) {
					MenuProvider lidCheck = this.getBlockEntitySourceIgnoreLid(state, level, pos, false).apply(STONE_NAME_RETRIEVER).orElse(null);
					boolean first = stoneChest.openProgress == 0F;
					if (lidCheck == null) {
						if (stoneChest.openProgress < MAX_LIFT_AMOUNT_UNDER_SOLID_BLOCK) {
							stoneChest.setLid(!ancient ? stoneChest.openProgress + LIFT_AMOUNT : MAX_LIFT_AMOUNT_UNDER_SOLID_BLOCK);
						} else {
							return InteractionResult.PASS;
						}
					} else {
						stoneChest.liftLid(LIFT_AMOUNT, ancient);
					}
					if (first) {
						((ChestBlockEntityInterface) stoneChest).wilderWild$bubble(level, pos, state);
						ResourceLocation lootTable = stoneChest.lootTable;
						if (lootTable != null && state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) && lootTable.getPath().toLowerCase().contains("shipwreck")) {
							Jellyfish.spawnFromChest(level, state, pos, true);
						}
					}
					StoneChestBlockEntity.playSound(level, pos, state, first ? WWSounds.BLOCK_STONE_CHEST_OPEN : WWSounds.BLOCK_STONE_CHEST_LIFT, first ? WWSounds.BLOCK_STONE_CHEST_OPEN_UNDERWATER : WWSounds.BLOCK_STONE_CHEST_LIFT_UNDERWATER, 0.35F);
					level.gameEvent(player, GameEvent.CONTAINER_OPEN, pos);
				}
			}
			StoneChestBlockEntity otherChest = getOtherChest(level, pos, state);
			if (otherChest != null) {
				((ChestBlockEntityInterface) stoneChest).wilderWild$syncBubble(stoneChest, otherChest);
			}
			stoneChest.syncLidValuesAndUpdate(otherChest);
		}
		return InteractionResult.CONSUME;
	}

	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new StoneChestBlockEntity(pos, state);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return level.isClientSide ? BaseEntityBlock.createTickerHelper(type, WWBlockEntityTypes.STONE_CHEST, StoneChestBlockEntity::clientStoneTick) : BaseEntityBlock.createTickerHelper(type, WWBlockEntityTypes.STONE_CHEST, StoneChestBlockEntity::serverStoneTick);
	}

	@Override
	@Nullable
	public MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		return this.combine(state, level, pos, false).apply(STONE_NAME_RETRIEVER).orElse(null);
	}

	@Override
	@NotNull
	public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(@NotNull BlockState state, @NotNull Level level2, @NotNull BlockPos pos2, boolean ignoreBlocked) {
		BiPredicate<LevelAccessor, BlockPos> biPredicate = ignoreBlocked ? (level, pos) -> false : StoneChestBlock::isStoneChestBlocked;
		return DoubleBlockCombiner.combineWithNeigbour(this.blockEntityType.get(), ChestBlock::getBlockType, ChestBlock::getConnectedDirection, FACING, state, level2, pos2, biPredicate);
	}

	@NotNull
	public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> getBlockEntitySourceIgnoreLid(BlockState state, Level level2, BlockPos pos2, boolean ignoreBlocked) {
		BiPredicate<LevelAccessor, BlockPos> biPredicate = ignoreBlocked ? (level, pos) -> false : StoneChestBlock::isStoneChestBlockedNoLid;
		return DoubleBlockCombiner.combineWithNeigbour(this.blockEntityType.get(), ChestBlock::getBlockType, ChestBlock::getConnectedDirection, FACING, state, level2, pos2, biPredicate);
	}

	@Override
	@NotNull
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		if (!state.hasProperty(WATERLOGGED)) return state;
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		if (neighborState.is(this) && direction.getAxis().isHorizontal()) {
			//if (neighborState.get(ANCIENT) == state.get(ANCIENT)) {
			ChestType chestType = neighborState.getValue(TYPE);
			if (state.getValue(TYPE) == ChestType.SINGLE && chestType != ChestType.SINGLE && state.getValue(FACING) == neighborState.getValue(FACING) && state.getValue(ANCIENT) == neighborState.getValue(ANCIENT) && ChestBlock.getConnectedDirection(neighborState) == direction.getOpposite()) {
				BlockState retState = state.setValue(TYPE, chestType.getOpposite());
				updateBubbles(state, retState, level, currentPos);
				return retState;
			}
			//}
		} else if (ChestBlock.getConnectedDirection(state) == direction) {
			BlockState retState = state.setValue(TYPE, ChestType.SINGLE);
			updateBubbles(state, retState, level, currentPos);
			return retState;
		}
		BlockState retState = super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
		updateBubbles(state, retState, level, currentPos);
		return retState;
	}

	public void updateBubbles(@NotNull BlockState oldState, @NotNull BlockState state, @NotNull LevelAccessor level, @NotNull BlockPos currentPos) {
		if (level.getBlockEntity(currentPos) instanceof StoneChestBlockEntity chest) {
			StoneChestBlockEntity otherChest = getOtherChest(level, currentPos, state);
			if (otherChest != null) {
				BlockState otherState = level.getBlockState(otherChest.getBlockPos());
				boolean wasLogged = oldState.getValue(BlockStateProperties.WATERLOGGED);
				if (wasLogged != state.getValue(BlockStateProperties.WATERLOGGED) && wasLogged) {
					if (!otherState.getValue(BlockStateProperties.WATERLOGGED)) {
						((ChestBlockEntityInterface) chest).wilderWild$setCanBubble(true);
						((ChestBlockEntityInterface) otherChest).wilderWild$setCanBubble(true);
					} else if (!((ChestBlockEntityInterface) otherChest).wilderWild$getCanBubble()) {
						((ChestBlockEntityInterface) chest).wilderWild$setCanBubble(false);
						((ChestBlockEntityInterface) otherChest).wilderWild$setCanBubble(false);
					}
				}
			} else {
				boolean wasLogged = oldState.getValue(BlockStateProperties.WATERLOGGED);
				if (wasLogged != state.getValue(BlockStateProperties.WATERLOGGED) && wasLogged) {
					((ChestBlockEntityInterface) chest).wilderWild$setCanBubble(true);
				}
			}
			if (otherChest != null) {
				((ChestBlockEntityInterface) chest).wilderWild$syncBubble(chest, otherChest);
			}
		}
	}

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
		Direction direction3;
		ChestType chestType = ChestType.SINGLE;
		Level level = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		Direction direction = ctx.getHorizontalDirection().getOpposite();
		FluidState fluidState = ctx.getLevel().getFluidState(pos);
		boolean bl = ctx.isSecondaryUseActive();
		Direction direction2 = ctx.getClickedFace();
		if (direction2.getAxis().isHorizontal() && bl && (direction3 = this.candidatePartnerFacing(ctx, direction2.getOpposite())) != null && direction3.getAxis() != direction2.getAxis()) {
			direction = direction3;
			chestType = direction.getCounterClockWise() == direction2.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
		}
		if (chestType == ChestType.SINGLE && !bl) {
			if (direction == this.candidatePartnerFacing(ctx, direction.getClockWise())) {
				chestType = ChestType.LEFT;
			} else if (direction == this.candidatePartnerFacing(ctx, direction.getCounterClockWise())) {
				chestType = ChestType.RIGHT;
			}
		}
		BlockState retState = this.defaultBlockState().setValue(FACING, direction).setValue(TYPE, chestType).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
		StoneChestBlockEntity otherChest = getOtherChest(level, pos, retState);
		if (otherChest != null) {
			if (level.getBlockEntity(pos) instanceof StoneChestBlockEntity chest) {
				((ChestBlockEntityInterface) chest).wilderWild$setCanBubble(((ChestBlockEntityInterface) otherChest).wilderWild$getCanBubble());
				((ChestBlockEntityInterface) chest).wilderWild$syncBubble(chest, otherChest);
			}
		}
		return retState;
	}

	@Nullable
	private Direction candidatePartnerFacing(@NotNull BlockPlaceContext ctx, @NotNull Direction dir) {
		BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos().relative(dir));
		return blockState.is(this) && !blockState.getValue(ANCIENT) && blockState.getValue(TYPE) == ChestType.SINGLE ? blockState.getValue(FACING) : null;
	}

	@Override
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean moved) {
		if (state.is(newState.getBlock())) {
			return;
		}
		if (!level.isClientSide) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof StoneChestBlockEntity stoneChestBlock) {
				((ChestBlockEntityInterface) stoneChestBlock).wilderWild$bubbleBurst(state);

				stoneChestBlock.unpackLootTable(null);
				ArrayList<ItemStack> ancientItems = stoneChestBlock.ancientItems();
				if (!ancientItems.isEmpty()) {
					level.playSound(null, pos, WWSounds.BLOCK_STONE_CHEST_ITEM_CRUMBLE, SoundSource.BLOCKS, 0.4F, 0.9F + (level.random.nextFloat() / 10F));
					for (ItemStack taunt : ancientItems) {
						for (int taunts = 0; taunts < taunt.getCount(); taunts += 1) {
							spawnBreakParticles(level, taunt, pos);
						}
					}
				}
				RandomSource random = level.getRandom();
				for (ItemStack item : stoneChestBlock.nonAncientItems()) {
					double d = EntityType.ITEM.getWidth();
					double e = 1D - d;
					double f = d / 2D;
					double g = pos.getX() + random.nextDouble() * e + f;
					double h = pos.getY() + random.nextDouble() * e;
					double i = pos.getZ() + random.nextDouble() * e + f;
					while (!item.isEmpty()) {
						ItemEntity itemEntity = new ItemEntity(level, g, h, i, item.split(random.nextInt(21) + 10));
						itemEntity.setDeltaMovement(
							random.triangle(ITEM_DELTA_TRIANGLE_A_XZ, ITEM_DELTA_TRIANGLE_B),
							random.triangle(ITEM_DELTA_TRIANGLE_A_Y, ITEM_DELTA_TRIANGLE_B),
							random.triangle(ITEM_DELTA_TRIANGLE_A_XZ, ITEM_DELTA_TRIANGLE_B)
						);
						level.addFreshEntity(itemEntity);
					}
				}
				level.updateNeighbourForOutputSignal(pos, this);
			}
		}
		if (state.hasBlockEntity() && !state.is(newState.getBlock())) {
			level.removeBlockEntity(pos);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof StoneChestBlockEntity stoneChestBlockEntity) {
			return stoneChestBlockEntity.getComparatorOutput();
		}
		return 0;
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, TYPE, WATERLOGGED, ANCIENT, SCULK);
	}

}
