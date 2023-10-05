package net.frozenblock.wilderwild.entity.render.renderer;

import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.render.model.CrabModel;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CrabRenderer<T extends Crab> extends MobRenderer<T, CrabModel<T>> {
	private static final ResourceLocation CRAB_LOCATION = WilderSharedConstants.id("textures/entity/crab/crab.png");

	public CrabRenderer(EntityRendererProvider.Context context) {
		this(context, WilderWildClient.CRAB);
	}

	public CrabRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		super(context, new CrabModel<>(context.bakeLayer(layer)), 0.6F);
	}

	@Override
	protected float getFlipDegrees(T livingEntity) {
		return 180.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return CRAB_LOCATION;
	}

}

