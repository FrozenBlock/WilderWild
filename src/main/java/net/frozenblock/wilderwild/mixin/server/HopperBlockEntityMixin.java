package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
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

}
