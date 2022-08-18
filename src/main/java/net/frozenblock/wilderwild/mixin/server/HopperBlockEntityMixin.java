package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {

    @Inject(at = @At("HEAD"), method = "ejectItems", cancellable = true)
    private static void ejectItems(Level world, BlockPos pos, BlockState state, Container inventory, CallbackInfoReturnable<Boolean> info) {
        Container inventory2 = getAttachedContainer(world, pos, state);
        if (inventory2 instanceof StoneChestBlockEntity) {
            info.cancel();
            info.setReturnValue(false);
        } else if (inventory2 instanceof CompoundContainer doubleInventory) {
            if (doubleInventory.container1 instanceof StoneChestBlockEntity || doubleInventory.container2 instanceof StoneChestBlockEntity) {
                info.cancel();
                info.setReturnValue(false);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "suckInItems", cancellable = true)
    private static void suckInItems(Level world, Hopper hopper, CallbackInfoReturnable<Boolean> info) {
        Container inventory = getSourceContainer(world, hopper);
        if (inventory instanceof StoneChestBlockEntity) {
            info.cancel();
            info.setReturnValue(false);
        } else if (inventory instanceof CompoundContainer doubleInventory) {
            if (doubleInventory.container1 instanceof StoneChestBlockEntity || doubleInventory.container2 instanceof StoneChestBlockEntity) {
                info.cancel();
                info.setReturnValue(false);
            }
        }
    }

    @Nullable
    @Shadow
    private static Container getAttachedContainer(Level world, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(HopperBlock.FACING);
        return HopperBlockEntity.getContainerAt(world, pos.relative(direction));
    }

    @Nullable
    @Shadow
    private static Container getSourceContainer(Level world, Hopper hopper) {
        return getContainerAt(world, hopper.getLevelX(), hopper.getLevelY() + 1.0, hopper.getLevelZ());
    }

    @Nullable
    @Shadow
    private static Container getContainerAt(Level world, double x, double y, double z) {
        List<Entity> list;
        BlockEntity blockEntity;
        Container inventory = null;
        BlockPos blockPos = new BlockPos(x, y, z);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof WorldlyContainerHolder) {
            inventory = ((WorldlyContainerHolder) block).getContainer(blockState, world, blockPos);
        } else if (blockState.hasBlockEntity() && (blockEntity = world.getBlockEntity(blockPos)) instanceof Container && (inventory = (Container) blockEntity) instanceof ChestBlockEntity && block instanceof ChestBlock) {
            inventory = ChestBlock.getContainer((ChestBlock) block, blockState, world, blockPos, true);
        }
        if (inventory == null && !(list = world.getEntities((Entity) null, new AABB(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), EntitySelector.CONTAINER_ENTITY_SELECTOR)).isEmpty()) {
            inventory = (Container) list.get(world.random.nextInt(list.size()));
        }
        return inventory;
    }

}
