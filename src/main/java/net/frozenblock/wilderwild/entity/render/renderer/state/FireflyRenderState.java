package net.frozenblock.wilderwild.entity.render.renderer.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class FireflyRenderState extends LivingEntityRenderState {

	public int overlay;
	public int flickerAge;
	public boolean flickers;

	public float animScale;
	public FireflyColor color;
	public float calcColor;

	public boolean shouldShowName;
}
