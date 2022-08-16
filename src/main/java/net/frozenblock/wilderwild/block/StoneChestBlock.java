package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class StoneChestBlock extends ChestBlock {

    public StoneChestBlock(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(settings, () -> RegisterBlockEntities.STONE_CHEST);
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
                } else {
                    DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> source = getBlockEntitySourceIgnoreLid(state, world, pos, false);
                    boolean first = stoneEntity.openProgress == 0F;
                    if (source == null) {
                        if (stoneEntity.openProgress < 0.05F) {
                            stoneEntity.openProgress = stoneEntity.openProgress + 0.025F;
                        } else {
                            return ActionResult.PASS;
                        }
                    } else {
                        stoneEntity.openProgress = stoneEntity.openProgress + 0.025F;
                    }
                    stoneEntity.stillLidTicks = (int) (Math.max((stoneEntity.openProgress), 0.2) * 120);
                    StoneChestBlockEntity.playSound(world, pos, state, first ? RegisterSounds.BLOCK_STONE_CHEST_OPEN : RegisterSounds.BLOCK_STONE_CHEST_LIFT);
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
        return (NamedScreenHandlerFactory)((Optional)this.getBlockEntitySource(state, world, pos, false).apply(STONE_NAME_RETRIEVER)).orElse((Object)null);
    }

    @Override
    public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(BlockState state, World world2, BlockPos pos2, boolean ignoreBlocked) {
        BiPredicate<WorldAccess, BlockPos> biPredicate = ignoreBlocked ? (world, pos) -> false : StoneChestBlock::isStoneChestBlocked;
        return DoubleBlockProperties.toPropertySource((BlockEntityType)this.entityTypeRetriever.get(), ChestBlock::getDoubleBlockType, ChestBlock::getFacing, FACING, state, world2, pos2, biPredicate);
    }

    public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySourceIgnoreLid(BlockState state, World world2, BlockPos pos2, boolean ignoreBlocked) {
        BiPredicate<WorldAccess, BlockPos> biPredicate = ignoreBlocked ? (world, pos) -> false : StoneChestBlock::isStoneChestBlockedNoLid;
        return DoubleBlockProperties.toPropertySource((BlockEntityType)this.entityTypeRetriever.get(), ChestBlock::getDoubleBlockType, ChestBlock::getFacing, FACING, state, world2, pos2, biPredicate);
    }

    public static final DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Optional<NamedScreenHandlerFactory>> STONE_NAME_RETRIEVER = new DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Optional<NamedScreenHandlerFactory>>(){

        @Override
        public Optional<NamedScreenHandlerFactory> getFromBoth(final ChestBlockEntity chestBlockEntity, final ChestBlockEntity chestBlockEntity2) {
            final DoubleInventory inventory = new DoubleInventory(chestBlockEntity, chestBlockEntity2);
            return Optional.of(new NamedScreenHandlerFactory(){

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
                    return Text.translatable("container.stoneChestDouble");
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
        if (!canInteract(world, pos) || hasLid(world, pos)) {
            return true;
        }
        return hasBlockOnTop(world, pos) || hasCatOnTop(world, pos);
    }

    public static boolean isStoneChestBlockedNoLid(WorldAccess world, BlockPos pos) {
        if (!canInteract(world, pos)) {
            return true;
        }
        return hasBlockOnTop(world, pos) || hasCatOnTop(world, pos);
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
}
