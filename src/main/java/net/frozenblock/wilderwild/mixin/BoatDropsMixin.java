package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.misc.CustomBoatType;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public class BoatDropsMixin {

    //CREDIT TO nyuppo/fabric-boat-example ON GITHUB

    @Inject(method = "asItem", at = @At("HEAD"), cancellable = true)
    public void asItem(CallbackInfoReturnable<Item> ci) {
        if (((BoatEntity) (Object) this).getBoatType() == CustomBoatType.BAOBAB) {
            ci.setReturnValue(RegisterItems.BAOBAB_BOAT_ITEM);
            ci.cancel();
        }
        if (((BoatEntity) (Object) this).getBoatType() == CustomBoatType.CYPRESS) {
            ci.setReturnValue(RegisterItems.CYPRESS_BOAT_ITEM);
            ci.cancel();
        }
    }

}
