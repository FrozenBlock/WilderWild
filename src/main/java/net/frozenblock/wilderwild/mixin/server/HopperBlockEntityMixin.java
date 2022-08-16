package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {

    @Inject(at = @At("TAIL"), method = "canInsert", cancellable = true)
    private static void canInsert(Inventory inventory, ItemStack stack, int slot, @Nullable Direction side, CallbackInfoReturnable<Boolean> info) {
        if (inventory instanceof StoneChestBlockEntity) {
            info.cancel();
            info.setReturnValue(false);
        }
    }

    @Inject(at = @At("TAIL"), method = "canExtract", cancellable = true)
    private static void canExtract(Inventory inv, ItemStack stack, int slot, Direction facing, CallbackInfoReturnable<Boolean> info) {
        if (inv instanceof StoneChestBlockEntity) {
            info.cancel();
            info.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "getInventoryAt(Lnet/minecraft/world/World;DDD)Lnet/minecraft/inventory/Inventory;", cancellable = true)
    private static void getInventoryAt(World world, double x, double y, double z, CallbackInfoReturnable<Inventory> info) {
        Inventory inv = info.getReturnValue();
        if (world.getBlockEntity(new BlockPos(x, y, z)) instanceof StoneChestBlockEntity) {
            info.setReturnValue(null);
            info.cancel();
        }
    }

}
