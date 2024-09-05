package net.frozenblock.wilderwild.entity.render.renderer;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class CrabRenderState extends LivingEntityRenderState {

	public float climbXRot;
	public float attackTime;
	public boolean isDitto;
	public final AnimationState hidingAnimationState = new AnimationState();
	public final AnimationState diggingAnimationState = new AnimationState();
	public final AnimationState emergingAnimationState = new AnimationState();
}
