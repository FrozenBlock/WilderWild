package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
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
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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
        if (!hasLid(world, pos) && !player.isSneaking()) {
            NamedScreenHandlerFactory namedScreenHandlerFactory = this.createScreenHandlerFactory(state, world, pos);
            if (namedScreenHandlerFactory != null) {
                player.openHandledScreen(namedScreenHandlerFactory);
                player.incrementStat(this.getOpenStat());
                PiglinBrain.onGuardedBlockInteracted(player, true);
            }
        } else {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof StoneChestBlockEntity stoneChest) {
                stoneChest.openProgress = stoneChest.openProgress + 0.05F;
                stoneChest.stillLidTicks = (int) ((stoneChest.openProgress) * 200);
                stoneChest.updateSync();
            }
        }
        return ActionResult.CONSUME;
    }

    public static boolean hasLid(World world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof StoneChestBlockEntity stoneChest) {
            return stoneChest.openProgress < 0.2F;
        }
        return false;
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

    @Override
    public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(BlockState state, World world2, BlockPos pos2, boolean ignoreBlocked) {
        BiPredicate<WorldAccess, BlockPos> biPredicate = ignoreBlocked ? (world, pos) -> false : StoneChestBlock::isStoneChestBlocked;
        return DoubleBlockProperties.toPropertySource((BlockEntityType)this.entityTypeRetriever.get(), ChestBlock::getDoubleBlockType, ChestBlock::getFacing, FACING, state, world2, pos2, biPredicate);
    }

    public static boolean isStoneChestBlocked(WorldAccess world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof StoneChestBlockEntity stoneChest) {
            if (stoneChest.openProgress < 0.2F) {
                return true;
            }
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
