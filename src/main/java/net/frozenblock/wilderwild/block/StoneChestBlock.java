package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class StoneChestBlock extends ChestBlock {
    //public static final BooleanProperty ANCIENT = RegisterProperties.ANCIENT;

    public StoneChestBlock(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(settings, supplier);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(CHEST_TYPE, ChestType.SINGLE).with(WATERLOGGED, false));

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof StoneChestBlockEntity stoneChest) {
            if (stoneChest.closing) {
                return ActionResult.FAIL;
            }
            StoneChestBlockEntity stoneEntity = stoneChest.getLeftEntity(world, pos, state, stoneChest);
            if (canInteract(world, pos)) {
                NamedScreenHandlerFactory namedScreenHandlerFactory = this.createScreenHandlerFactory(state, world, pos);
                if (!hasLid(world, pos) && (!player.isSneaking() || stoneEntity.openProgress >= 0.5F) && namedScreenHandlerFactory != null) {
                    player.openHandledScreen(namedScreenHandlerFactory);
                    player.incrementStat(this.getOpenStat());
                    PiglinBrain.onGuardedBlockInteracted(player, true);
                } else if (stoneEntity.openProgress < 0.5F) {
                    NamedScreenHandlerFactory lidCheck = (NamedScreenHandlerFactory) ((Optional) this.getBlockEntitySourceIgnoreLid(state, world, pos, false).apply(STONE_NAME_RETRIEVER)).orElse(null);
                    boolean first = stoneEntity.openProgress == 0F;
                    if (lidCheck == null) {
                        if (stoneEntity.openProgress < 0.05F) {
                            stoneEntity.openProgress = !player.isCreative() ? stoneEntity.openProgress + 0.025F : 0.05F;
                        } else {
                            return ActionResult.PASS;
                        }
                    } else {
                        stoneEntity.openProgress = !player.isCreative() ? stoneEntity.openProgress + 0.025F : stoneEntity.openProgress + 0.05F;
                    }
                    stoneEntity.stillLidTicks = (int) (Math.max((stoneEntity.openProgress), 0.2) * 120);
                    StoneChestBlockEntity.playSound(world, pos, state, first ? RegisterSounds.BLOCK_STONE_CHEST_OPEN : RegisterSounds.BLOCK_STONE_CHEST_LIFT, 0.5F);
                    world.emitGameEvent(player, GameEvent.CONTAINER_OPEN, pos);
                    stoneEntity.updateSync();
                }
            }
        }
        return ActionResult.CONSUME;
    }

    public static boolean hasLid(World world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof StoneChestBlockEntity stoneChest) {
            return stoneChest.openProgress < 0.3F;
        }
        return false;
    }

    public static boolean canInteract(World world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof StoneChestBlockEntity stoneChest) {
            return !(stoneChest.closing || stoneChest.cooldownTicks > 0);
        }
        return true;
    }

    public static boolean hasLid(WorldAccess world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof StoneChestBlockEntity stoneChest) {
            return stoneChest.openProgress < 0.3F;
        }
        return false;
    }

    public static boolean canInteract(WorldAccess world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof StoneChestBlockEntity stoneChest) {
            return !(stoneChest.closing || stoneChest.cooldownTicks > 0);
        }
        return true;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StoneChestBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? BlockWithEntity.checkType(type, RegisterBlockEntities.STONE_CHEST, StoneChestBlockEntity::clientStoneTick) : BlockWithEntity.checkType(type, RegisterBlockEntities.STONE_CHEST, StoneChestBlockEntity::serverStoneTick);
    }

    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return (NamedScreenHandlerFactory) ((Optional) this.getBlockEntitySource(state, world, pos, false).apply(STONE_NAME_RETRIEVER)).orElse(null);
    }

    @Override
    public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(BlockState state, World world2, BlockPos pos2, boolean ignoreBlocked) {
        BiPredicate<WorldAccess, BlockPos> biPredicate = ignoreBlocked ? (world, pos) -> false : StoneChestBlock::isStoneChestBlocked;
        return DoubleBlockProperties.toPropertySource((BlockEntityType) this.entityTypeRetriever.get(), ChestBlock::getDoubleBlockType, ChestBlock::getFacing, FACING, state, world2, pos2, biPredicate);
    }

    public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySourceIgnoreLid(BlockState state, World world2, BlockPos pos2, boolean ignoreBlocked) {
        BiPredicate<WorldAccess, BlockPos> biPredicate = ignoreBlocked ? (world, pos) -> false : StoneChestBlock::isStoneChestBlockedNoLid;
        return DoubleBlockProperties.toPropertySource((BlockEntityType) this.entityTypeRetriever.get(), ChestBlock::getDoubleBlockType, ChestBlock::getFacing, FACING, state, world2, pos2, biPredicate);
    }

    public static final DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Optional<NamedScreenHandlerFactory>> STONE_NAME_RETRIEVER = new DoubleBlockProperties.PropertyRetriever<>() {

        @Override
        public Optional<NamedScreenHandlerFactory> getFromBoth(final ChestBlockEntity chestBlockEntity, final ChestBlockEntity chestBlockEntity2) {
            final DoubleInventory inventory = new DoubleInventory(chestBlockEntity, chestBlockEntity2);
            return Optional.of(new NamedScreenHandlerFactory() {

                @Override
                @Nullable
                public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    if (chestBlockEntity.checkUnlocked(playerEntity) && chestBlockEntity2.checkUnlocked(playerEntity)) {
                        chestBlockEntity.checkLootInteraction(playerInventory.player);
                        chestBlockEntity2.checkLootInteraction(playerInventory.player);
                        return GenericContainerScreenHandler.createGeneric9x6(i, playerInventory, inventory);
                    }
                    return null;
                }

                @Override
                public Text getDisplayName() {
                    if (chestBlockEntity.hasCustomName()) {
                        return chestBlockEntity.getDisplayName();
                    }
                    if (chestBlockEntity2.hasCustomName()) {
                        return chestBlockEntity2.getDisplayName();
                    }
                    return Text.translatable("container.double_stone_chest");
                }
            });
        }

        @Override
        public Optional<NamedScreenHandlerFactory> getFrom(ChestBlockEntity chestBlockEntity) {
            return Optional.of(chestBlockEntity);
        }

        @Override
        public Optional<NamedScreenHandlerFactory> getFallback() {
            return Optional.empty();
        }

    };


    public static boolean isStoneChestBlocked(WorldAccess world, BlockPos pos) {
        if (hasLid(world, pos)) {
            return true;
        }
        return hasBlockOnTop(world, pos) || hasCatOnTop(world, pos) || !canInteract(world, pos);
    }

    public static boolean isStoneChestBlockedNoLid(WorldAccess world, BlockPos pos) {
        return hasBlockOnTop(world, pos) || hasCatOnTop(world, pos) || !canInteract(world, pos);
    }

    private static boolean hasBlockOnTop(BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return world.getBlockState(blockPos).isSolidBlock(world, blockPos);
    }

    private static boolean hasCatOnTop(WorldAccess world, BlockPos pos) {
        List<CatEntity> list = world.getNonSpectatingEntities(CatEntity.class, new Box(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));
        if (!list.isEmpty()) {
            for (CatEntity catEntity : list) {
                if (!catEntity.isInSittingPose()) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (neighborState.isOf(this) && direction.getAxis().isHorizontal()) {
            //if (neighborState.get(ANCIENT) == state.get(ANCIENT)) {
                ChestType chestType = neighborState.get(CHEST_TYPE);
                if (state.get(CHEST_TYPE) == ChestType.SINGLE && chestType != ChestType.SINGLE && state.get(FACING) == neighborState.get(FACING) && ChestBlock.getFacing(neighborState) == direction.getOpposite()) {
                    return state.with(CHEST_TYPE, chestType.getOpposite());
                }
            //}
        } else if (ChestBlock.getFacing(state) == direction) {
            return state.with(CHEST_TYPE, ChestType.SINGLE);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction3;
        ChestType chestType = ChestType.SINGLE;
        Direction direction = ctx.getPlayerFacing().getOpposite();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = ctx.shouldCancelInteraction();
        Direction direction2 = ctx.getSide();
        if (direction2.getAxis().isHorizontal() && bl && (direction3 = this.getNeighborChestDirection(ctx, direction2.getOpposite())) != null && direction3.getAxis() != direction2.getAxis()) {
            direction = direction3;
            ChestType chestType2 = chestType = direction.rotateYCounterclockwise() == direction2.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
        }
        if (chestType == ChestType.SINGLE && !bl) {
            if (direction == this.getNeighborChestDirection(ctx, direction.rotateYClockwise())) {
                chestType = ChestType.LEFT;
            } else if (direction == this.getNeighborChestDirection(ctx, direction.rotateYCounterclockwise())) {
                chestType = ChestType.RIGHT;
            }
        }
        return this.getDefaultState().with(FACING, direction).with(CHEST_TYPE, chestType).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Nullable
    private Direction getNeighborChestDirection(ItemPlacementContext ctx, Direction dir) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(dir));
        return blockState.isOf(this) && blockState.get(CHEST_TYPE) == ChestType.SINGLE ? blockState.get(FACING) : null;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof StoneChestBlockEntity stoneChestBlock) {
                stoneChestBlock.checkLootInteraction(null);
                ArrayList<ItemStack> ancientItems = stoneChestBlock.ancientItems();
                if (!ancientItems.isEmpty()) {
                    world.playSound(null, pos, RegisterSounds.BLOCK_STONE_CHEST_ITEM_CRUMBLE, SoundCategory.BLOCKS, 0.5F, 0.9F + (world.random.nextFloat() / 10F));
                }
                for (ItemStack item : stoneChestBlock.nonAncientItems()) {
                    double d = EntityType.ITEM.getWidth();
                    double e = 1.0 - d;
                    double f = d / 2.0;
                    double g = Math.floor(pos.getX()) + world.random.nextDouble() * e + f;
                    double h = Math.floor(pos.getY()) + world.random.nextDouble() * e;
                    double i = Math.floor(pos.getZ()) + world.random.nextDouble() * e + f;
                    while (!item.isEmpty()) {
                        ItemEntity itemEntity = new ItemEntity(world, g, h, i, item.split(world.random.nextInt(21) + 10));
                        itemEntity.setVelocity(world.random.nextTriangular(0.0, 0.11485000171139836), world.random.nextTriangular(0.2, 0.11485000171139836), world.random.nextTriangular(0.0, 0.11485000171139836));
                        world.spawnEntity(itemEntity);
                    }
                }
                world.updateComparators(pos, this);
            }
        }
        if (state.hasBlockEntity() && !state.isOf(newState.getBlock())) {
            world.removeBlockEntity(pos);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, CHEST_TYPE, WATERLOGGED);
    }


}
