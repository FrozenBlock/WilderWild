package net.frozenblock.wilderwild.mixin.client.warden;

import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.minecraft.client.renderer.entity.state.WardenRenderState;
import net.minecraft.world.entity.AnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WardenRenderState.class)
public class WardenRenderStateMixin implements WilderWarden {

	@Unique
	private final AnimationState dyingAnimationState = new AnimationState();
	@Unique
	private final AnimationState swimmingDyingAnimationState = new AnimationState();
	@Unique
	private final AnimationState kirbyDeathAnimationState = new AnimationState();
	@Unique
	private boolean isStella;
	@Unique
	private int deathTicks;

	@Override
	public AnimationState wilderWild$getDyingAnimationState() {
		return this.dyingAnimationState;
	}

	@Override
	public AnimationState wilderWild$getSwimmingDyingAnimationState() {
		return this.swimmingDyingAnimationState;
	}

	@Override
	public AnimationState wilderWild$getKirbyDeathAnimationState() {
		return this.kirbyDeathAnimationState;
	}

	@Override
	public boolean wilderWild$isStella() {
		return this.isStella;
	}

	@Override
	public void wilderWild$setIsStella(boolean value) {
		this.isStella = value;
	}

	@Override
	public void wilderWild$setDeathTicks(int i) {
		this.deathTicks = i;
	}

	@Override
	public int wilderWild$getDeathTicks() {
		return this.deathTicks;
	}
}
