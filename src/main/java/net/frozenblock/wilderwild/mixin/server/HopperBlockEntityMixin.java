package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {

    @Inject(at = @At("TAIL"), method = "insert", cancellable = true)
    private static void insert(World world, BlockPos pos, BlockState state, Inventory inventory, CallbackInfoReturnable<Boolean> info) {
        Inventory inventory2 = getOutputInventory(world, pos, state);
        if (inventory2 instanceof StoneChestBlockEntity) {
            info.cancel();
            info.setReturnValue(false);
        }
    }

    @Inject(at = @At("TAIL"), method = "extract", cancellable = true)
    private static void extract(World world, Hopper hopper, CallbackInfoReturnable<Boolean> info) {
        Inventory inventory = getInputInventory(world, hopper);
        if (inventory instanceof StoneChestBlockEntity) {
            info.cancel();
            info.setReturnValue(false);
        }
    }

    @Inject(at = @At("TAIL"), method = "getInventoryAt(Lnet/minecraft/world/World;DDD)Lnet/minecraft/inventory/Inventory;", cancellable = true)
    private static void getInventoryAt(World world, double x, double y, double z, CallbackInfoReturnable<Inventory> info) {
        if (world.getBlockEntity(new BlockPos(x, y, z)) instanceof StoneChestBlockEntity) {
            info.setReturnValue(null);
            info.cancel();
        }
    }

    @Nullable @Shadow
    private static Inventory getOutputInventory(World world, BlockPos pos, BlockState state) {
        Direction direction = state.get(HopperBlock.FACING);
        return HopperBlockEntity.getInventoryAt(world, pos.offset(direction));
    }

    @Nullable @Shadow
    private static Inventory getInputInventory(World world, Hopper hopper) {
        return getInventoryAt(world, hopper.getHopperX(), hopper.getHopperY() + 1.0, hopper.getHopperZ());
    }

    @Nullable @Shadow
    private static Inventory getInventoryAt(World world, double x, double y, double z) {
        List<Entity> list;
        BlockEntity blockEntity;
        Inventory inventory = null;
        BlockPos blockPos = new BlockPos(x, y, z);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof InventoryProvider) {
            inventory = ((InventoryProvider)((Object)block)).getInventory(blockState, world, blockPos);
        } else if (blockState.hasBlockEntity() && (blockEntity = world.getBlockEntity(blockPos)) instanceof Inventory && (inventory = (Inventory)((Object)blockEntity)) instanceof ChestBlockEntity && block instanceof ChestBlock) {
            inventory = ChestBlock.getInventory((ChestBlock)block, blockState, world, blockPos, true);
        }
        if (inventory == null && !(list = world.getOtherEntities(null, new Box(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), EntityPredicates.VALID_INVENTORIES)).isEmpty()) {
            inventory = (Inventory)((Object)list.get(world.random.nextInt(list.size())));
        }
        return inventory;
    }

}
