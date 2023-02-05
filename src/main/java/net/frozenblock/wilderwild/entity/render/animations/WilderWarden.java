package net.frozenblock.wilderwild.entity.render.animations;

import net.minecraft.world.entity.AnimationState;

public interface WilderWarden {

    AnimationState wilderWild$getDyingAnimationState();

    AnimationState wilderWild$getSwimmingDyingAnimationState();

    AnimationState wilderWild$getKirbyDeathAnimationState();

    boolean wilderWild$isStella();

	void wilderWild$setDeathTicks(int i);

	int wilderWild$getDeathTicks();

}
