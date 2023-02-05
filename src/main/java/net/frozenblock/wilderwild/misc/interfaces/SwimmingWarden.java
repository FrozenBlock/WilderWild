package net.frozenblock.wilderwild.misc.interfaces;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.monster.warden.Warden;

public interface SwimmingWarden {

	default boolean wilderWild$isTouchingWaterOrLava() {
		Warden warden = Warden.class.cast(this);
		return warden.isInWaterOrBubble() || warden.isInLava();
	}

	default boolean wilderWild$isSubmergedInWaterOrLava() {
		Warden warden = Warden.class.cast(this);
		return warden.isEyeInFluid(FluidTags.WATER) || warden.isEyeInFluid(FluidTags.LAVA);
	}

}
