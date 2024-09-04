package net.frozenblock.wilderwild.entity.render.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class OstrichRenderState extends LivingEntityRenderState {

	public boolean isInbred;
	public float beakAnimProgress;
	public float targetStraightProgress;
}
