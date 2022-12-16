package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.misc.WilderBoats;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Boat.class)
public abstract class BoatDropsMixin extends Entity {

	public BoatDropsMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	//CREDIT TO nyuppo/fabric-boat-example ON GITHUB

    @Inject(method = "getDropItem", at = @At("RETURN"), cancellable = true)
    public void getModdedBoats(CallbackInfoReturnable<Item> cir) {
		var boat = Boat.class.cast(this);
        if (boat.getBoatType() == WilderBoats.BAOBAB) {
            cir.setReturnValue(RegisterItems.BAOBAB_BOAT_ITEM);
        } else if (boat.getBoatType() == WilderBoats.CYPRESS) {
            cir.setReturnValue(RegisterItems.CYPRESS_BOAT_ITEM);
        } else if (boat.getBoatType() == WilderBoats.PALM) {
			cir.setReturnValue(RegisterItems.PALM_BOAT_ITEM);
		}
    }

	@Override
	public boolean rideableUnderWater() {
		return super.rideableUnderWater() || this.getFeetBlockState().getBlock() instanceof MesogleaBlock;
	}
}
