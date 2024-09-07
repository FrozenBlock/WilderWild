package net.frozenblock.wilderwild.entity.render.renderer.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.variant.JellyfishVariant;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class JellyfishRenderState extends LivingEntityRenderState {

	public int tickCount;
	public boolean isRGB;
	public JellyfishVariant variant;
	public float windTime;

	public float jellyXRot;
	public float tentXRot;
	public float jellyScale;
}
