package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.misc.WilderEnumValues;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoat.class)
public final class ChestBoatDropsMixin {

    //CREDIT TO nyuppo/fabric-boat-example ON GITHUB

    @Inject(method = "getDropItem", at = @At("RETURN"), cancellable = true)
    public void getModdedChestBoats(CallbackInfoReturnable<Item> ci) {
		var boat = ChestBoat.class.cast(this);
        if (boat.getBoatType() == WilderEnumValues.BAOBAB) {
            ci.setReturnValue(RegisterItems.BAOBAB_CHEST_BOAT_ITEM);
        } else if (boat.getBoatType() == WilderEnumValues.CYPRESS) {
            ci.setReturnValue(RegisterItems.CYPRESS_CHEST_BOAT_ITEM);
        }
    }

}
