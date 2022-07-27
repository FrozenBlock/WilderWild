package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.WilderBoats;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoatEntity.class)
public class ChestBoatDropsMixin {

    //CREDIT TO nyuppo/fabric-boat-example ON GITHUB

    @Inject(method = "asItem", at = @At("HEAD"), cancellable = true)
    public void asItem(CallbackInfoReturnable<Item> ci) {
        if (((ChestBoatEntity) (Object) this).getBoatType() == WilderBoats.BAOBAB) {
            ci.setReturnValue(RegisterItems.BAOBAB_CHEST_BOAT_ITEM);
            ci.cancel();
        }
        if (((ChestBoatEntity) (Object) this).getBoatType() == WilderBoats.CYPRESS) {
            ci.setReturnValue(RegisterItems.CYPRESS_CHEST_BOAT_ITEM);
            ci.cancel();
        }
    }

}
