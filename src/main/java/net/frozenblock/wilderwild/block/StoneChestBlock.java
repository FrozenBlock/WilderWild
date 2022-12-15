package net.frozenblock.wilderwild.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoneChestBlock extends ChestBlock {
    public static final BooleanProperty ANCIENT = RegisterProperties.ANCIENT;
    public static final BooleanProperty SCULK = RegisterProperties.HAS_SCULK;

    public StoneChestBlock(Properties settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(settings, supplier);
        this.registerDefaultState(this.defaultBlockState().setValue(ANCIENT, false).setValue(SCULK, false));
    }

    @Override
	@NotNull
    public InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof StoneChestBlockEntity stoneChest) {
            if (stoneChest.closing) {
                return InteractionResult.FAIL;
            }
            boolean ancient = state.getValue(ANCIENT);
            StoneChestBlockEntity stoneEntity = StoneChestBlockEntity.getLeftEntity(level, pos, state, stoneChest);
            if (canInteract(level, pos)) {
                MenuProvider namedScreenHandlerFactory = this.getMenuProvider(state, level, pos);
                if (!hasLid(level, pos) && (!player.isShiftKeyDown() || stoneEntity.openProgress >= 0.5F) && namedScreenHandlerFactory != null) {
                    player.openMenu(namedScreenHandlerFactory);
                    player.awardStat(this.getOpenChestStat());
                    PiglinAi.angerNearbyPiglins(player, true);
                } else if (stoneEntity.openProgress < 0.5F) {
                    MenuProvider lidCheck = this.getBlockEntitySourceIgnoreLid(state, level, pos, false).apply(STONE_NAME_RETRIEVER).orElse(null);
                    boolean first = stoneEntity.openProgress == 0F;
                    if (lidCheck == null) {
                        if (stoneEntity.openProgress < 0.05F) {
                            stoneEntity.setLid(!ancient ? stoneEntity.openProgress + 0.025F : 0.05F);
                        } else {
                            return InteractionResult.PASS;
                        }
                    } else {
                        stoneEntity.liftLid(0.025F, ancient);
                    }
					if (first) {
						stoneEntity.bubble();
					}
                    StoneChestBlockEntity.playSound(level, pos, state, first ? RegisterSounds.BLOCK_STONE_CHEST_OPEN : RegisterSounds.BLOCK_STONE_CHEST_LIFT, first ? RegisterSounds.BLOCK_STONE_CHEST_OPEN_UNDERWATER : RegisterSounds.BLOCK_STONE_CHEST_LIFT_UNDERWATER, 0.35F);
                    level.gameEvent(player, GameEvent.CONTAINER_OPEN, pos);
                    stoneEntity.updateSync();
                }
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new StoneChestBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return level.isClientSide ? BaseEntityBlock.createTickerHelper(type, RegisterBlockEntities.STONE_CHEST, StoneChestBlockEntity::clientStoneTick) : BaseEntityBlock.createTickerHelper(type, RegisterBlockEntities.STONE_CHEST, StoneChestBlockEntity::serverStoneTick);
    }

    @Nullable
    public MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        return this.combine(state, level, pos, false).apply(STONE_NAME_RETRIEVER).orElse(null);
    }

    @Override
    public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(@NotNull BlockState state, @NotNull Level level2, @NotNull BlockPos pos2, boolean ignoreBlocked) {
        BiPredicate<LevelAccessor, BlockPos> biPredicate = ignoreBlocked ? (level, pos) -> false : StoneChestBlock::isStoneChestBlocked;
        return DoubleBlockCombiner.combineWithNeigbour(this.blockEntityType.get(), ChestBlock::getBlockType, ChestBlock::getConnectedDirection, FACING, state, level2, pos2, biPredicate);
    }

    public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> getBlockEntitySourceIgnoreLid(BlockState state, Level level2, BlockPos pos2, boolean ignoreBlocked) {
        BiPredicate<LevelAccessor, BlockPos> biPredicate = ignoreBlocked ? (level, pos) -> false : StoneChestBlock::isStoneChestBlockedNoLid;
        return DoubleBlockCombiner.combineWithNeigbour(this.blockEntityType.get(), ChestBlock::getBlockType, ChestBlock::getConnectedDirection, FACING, state, level2, pos2, biPredicate);
    }

    public static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>> STONE_NAME_RETRIEVER = new DoubleBlockCombiner.Combiner<>() {

        @Override
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
        public Optional<MenuProvider> acceptSingle(@NotNull ChestBlockEntity chestBlockEntity) {
            return Optional.of(chestBlockEntity);
        }

        @Override
        public Optional<MenuProvider> acceptNone() {
            return Optional.empty();
        }

    };

	public static boolean hasLid(Level level, BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChest) {
			return stoneChest.openProgress < 0.3F;
		}
		return false;
	}

	public static boolean canInteract(LevelAccessor level, BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChest) {
			return !(stoneChest.closing || stoneChest.cooldownTicks > 0);
		}
		return true;
	}

	public static boolean hasLid(LevelAccessor level, BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof StoneChestBlockEntity stoneChest) {
			return stoneChest.openProgress < 0.3F;
		}
		return false;
	}

    public static boolean isStoneChestBlocked(LevelAccessor level, BlockPos pos) {
        if (hasLid(level, pos)) {
            return true;
        }
        return isBlockedChestByBlock(level, pos) || isCatSittingOnChest(level, pos) || !canInteract(level, pos);
    }

    public static boolean isStoneChestBlockedNoLid(LevelAccessor level, BlockPos pos) {
        return isBlockedChestByBlock(level, pos) || isCatSittingOnChest(level, pos) || !canInteract(level, pos);
    }

    private static boolean isBlockedChestByBlock(BlockGetter level, BlockPos pos) {
        BlockPos blockPos = pos.above();
        return level.getBlockState(blockPos).isRedstoneConductor(level, blockPos);
    }

    private static boolean isCatSittingOnChest(LevelAccessor level, BlockPos pos) {
        List<Cat> list = level.getEntitiesOfClass(Cat.class, new AABB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));
        if (!list.isEmpty()) {
            for (Cat catEntity : list) {
                if (!catEntity.isInSittingPose()) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
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
		BlockState retState = super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);;
		updateBubbles(state, retState, level, currentPos);
        return retState;
    }

	public void updateBubbles(BlockState oldState, BlockState state, LevelAccessor level, BlockPos currentPos) {
		StoneChestBlockEntity otherChest = getOtherChest(level, currentPos, state);
		if (otherChest != null) {
			BlockState otherState = otherChest.getBlockState();
			boolean wasLogged = oldState.getValue(BlockStateProperties.WATERLOGGED);
			if (wasLogged != state.getValue(BlockStateProperties.WATERLOGGED) && wasLogged) {
				if (!otherState.getValue(BlockStateProperties.WATERLOGGED)) {
					if (level.getBlockEntity(currentPos) instanceof StoneChestBlockEntity chest) {
						chest.canStoneBubble = true;
						otherChest.canStoneBubble = true;
					}
				} else if (!otherChest.canStoneBubble) {
					if (level.getBlockEntity(currentPos) instanceof StoneChestBlockEntity chest) {
						chest.canStoneBubble = false;
						otherChest.canStoneBubble = false;
					}
				}
			}
		} else {
			boolean wasLogged = oldState.getValue(BlockStateProperties.WATERLOGGED);
			if (level.getBlockEntity(currentPos) instanceof StoneChestBlockEntity chest) {
				if (wasLogged != state.getValue(BlockStateProperties.WATERLOGGED) && wasLogged) {
					chest.canStoneBubble = true;
				}
			}
		}
	}

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
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
				chest.canStoneBubble = otherChest.canStoneBubble;
			}
		}
		return retState;
    }

    @Nullable
    private Direction candidatePartnerFacing(BlockPlaceContext ctx, Direction dir) {
        BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos().relative(dir));
        return blockState.is(this) && !blockState.getValue(ANCIENT) && blockState.getValue(TYPE) == ChestType.SINGLE ? blockState.getValue(FACING) : null;
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean moved) {
        if (state.is(newState.getBlock())) {
            return;
        }
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof StoneChestBlockEntity stoneChestBlock) {
                stoneChestBlock.unpackLootTable(null);
                ArrayList<ItemStack> ancientItems = stoneChestBlock.ancientItems();
                if (!ancientItems.isEmpty()) {
                    level.playSound(null, pos, RegisterSounds.BLOCK_STONE_CHEST_ITEM_CRUMBLE, SoundSource.BLOCKS, 0.4F, 0.9F + (level.random.nextFloat() / 10F));
                    for (ItemStack taunt : ancientItems) {
                        for (int taunts = 0; taunts < taunt.getCount(); taunts += 1) {
                            spawnBreakParticles(level, taunt, pos);
                        }
                    }
                }
                for (ItemStack item : stoneChestBlock.nonAncientItems()) {
                    double d = EntityType.ITEM.getWidth();
                    double e = 1.0 - d;
                    double f = d / 2.0;
                    double g = Math.floor(pos.getX()) + level.random.nextDouble() * e + f;
                    double h = Math.floor(pos.getY()) + level.random.nextDouble() * e;
                    double i = Math.floor(pos.getZ()) + level.random.nextDouble() * e + f;
                    while (!item.isEmpty()) {
                        ItemEntity itemEntity = new ItemEntity(level, g, h, i, item.split(level.random.nextInt(21) + 10));
                        itemEntity.setDeltaMovement(level.random.triangle(0.0, 0.11485000171139836), level.random.triangle(0.2, 0.11485000171139836), level.random.triangle(0.0, 0.11485000171139836));
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

    public static void spawnBreakParticles(Level level, ItemStack stack, BlockPos pos) {
        if (level instanceof ServerLevel server) {
            server.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, level.random.nextIntBetweenInclusive(1, 3), 0.21875F, 0.21875F, 0.21875F, 0.05D);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, WATERLOGGED, ANCIENT, SCULK);
    }

	private static StoneChestBlockEntity getOtherChest(LevelAccessor level, BlockPos pos, BlockState state) {
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
		BlockPos newPos = new BlockPos(x, y, z);
		BlockEntity be = level.getBlockEntity(newPos);
		StoneChestBlockEntity entity = null;
		if (be instanceof StoneChestBlockEntity chest) {
			entity = chest;
		}
		return entity;
	}

}
