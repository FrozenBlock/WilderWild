package net.frozenblock.wilderwild.mixin.client.allay;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.render.animation.WilderAllay;
import net.minecraft.client.renderer.entity.state.AllayRenderState;
import net.minecraft.world.entity.AnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
@Mixin(AllayRenderState.class)
public class AllayRenderStateMixin implements WilderAllay {

	@Unique
	private final AnimationState dancingAnimationState = new AnimationState();

	@Override
	public AnimationState wilderWild$getDancingAnimationState() {
		return this.dancingAnimationState;
	}
}
