package net.frozenblock.wilderwild.entity.impl;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface SwimmingWardenState {

	float wilderWild$getSwimAmount();
	float wilderWild$getWadingProgress();

	void wilderWild$setSwimAmount(float amount);
	void wilderWild$setWadingProgress(float progress);
}
