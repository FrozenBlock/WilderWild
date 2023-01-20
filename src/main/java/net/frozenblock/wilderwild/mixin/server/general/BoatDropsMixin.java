package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.misc.WilderEnumValues;
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

    @Inject(method = "getDropItem", at = @At("RETURN"), cancellable = true)
    public void getModdedBoats(CallbackInfoReturnable<Item> cir) {
		var boat = Boat.class.cast(this);
        if (boat.getBoatType() == WilderEnumValues.BAOBAB) {
            cir.setReturnValue(RegisterItems.BAOBAB_BOAT_ITEM);
        } else if (boat.getBoatType() == WilderEnumValues.CYPRESS) {
            cir.setReturnValue(RegisterItems.CYPRESS_BOAT_ITEM);
        } else if (boat.getBoatType() == WilderEnumValues.PALM) {
			cir.setReturnValue(RegisterItems.PALM_BOAT_ITEM);
		}
    }

}
