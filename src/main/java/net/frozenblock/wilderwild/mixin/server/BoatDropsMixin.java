package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.WildBoats;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Boat.class)
public class BoatDropsMixin {

    //CREDIT TO nyuppo/fabric-boat-example ON GITHUB

    @Inject(method = "getDropItem", at = @At("HEAD"), cancellable = true)
    public void getDropItem(CallbackInfoReturnable<Item> ci) {
        if (((Boat) (Object) this).getBoatType() == WildBoats.BAOBAB) {
            ci.setReturnValue(RegisterItems.BAOBAB_BOAT_ITEM);
            ci.cancel();
        }
        if (((Boat) (Object) this).getBoatType() == WildBoats.CYPRESS) {
            ci.setReturnValue(RegisterItems.CYPRESS_BOAT_ITEM);
            ci.cancel();
        }
    }

}
