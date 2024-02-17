package net.frozenblock.wilderwild.entity.render.layer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ScorchedGlowingLayer<T extends Entity, M extends SpiderModel<T>> extends EyesLayer<T, M> {
	private static final RenderType SCORCHED_GLOWING = RenderType.eyes(WilderSharedConstants.id("textures/entity/scorched/scorched_glowing.png"));

	public ScorchedGlowingLayer(RenderLayerParent<T, M> renderLayerParent) {
		super(renderLayerParent);
	}

	@NotNull
	@Override
	public RenderType renderType() {
		return SCORCHED_GLOWING;
	}
}
