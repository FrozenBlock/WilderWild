package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {

    @Inject(at = @At("RETURN"), method = "getInventoryAt(Lnet/minecraft/world/World;DDD)V", cancellable = true)
    private static void getInventoryAt(World world, double x, double y, double z, CallbackInfoReturnable<Inventory> info) {
        Inventory inv = info.getReturnValue();
        if (inv != null) {
            if (inv instanceof StoneChestBlockEntity) {
                info.setReturnValue(null);
                info.cancel();
            }
        }
    }

}
