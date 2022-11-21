package net.frozenblock.wilderwild.entity.render.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.WardenEmissiveLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.warden.Warden;

public class OsmioooWardenFeatureRenderer<T extends Warden, M extends WardenModel<T>> extends WardenEmissiveLayer<T, M> {
	public OsmioooWardenFeatureRenderer(RenderLayerParent<T, M> context, ResourceLocation texture, AlphaFunction<T> animationAngleAdjuster, DrawSelector<T, M> modelPartVisibility) {
		super(context, texture, animationAngleAdjuster, modelPartVisibility);
	}

	@Override
	public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, T wardenEntity, float f, float g, float h, float j, float k, float l) {
		if (!wardenEntity.isInvisible() && ((WilderWarden) wardenEntity).isOsmiooo()) {
			this.onlyDrawSelectedParts();
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityTranslucentEmissive(this.texture));
			this.getParentModel()
					.renderToBuffer(
							matrixStack,
							vertexConsumer,
							i,
							LivingEntityRenderer.getOverlayCoords(wardenEntity, 0.0F),
							1.0F,
							1.0F,
							1.0F,
							this.alphaFunction.apply(wardenEntity, h, j)
					);
			this.resetDrawForAllParts();
		}
	}

	private void onlyDrawSelectedParts() {
		List<ModelPart> list = this.drawSelector.getPartsToDraw(this.getParentModel());
		this.getParentModel().root().getAllParts().forEach(part -> part.skipDraw = true);
		list.forEach(part -> part.skipDraw = false);
	}

	private void resetDrawForAllParts() {
		this.getParentModel().root().getAllParts().forEach(part -> part.skipDraw = false);
	}
}
