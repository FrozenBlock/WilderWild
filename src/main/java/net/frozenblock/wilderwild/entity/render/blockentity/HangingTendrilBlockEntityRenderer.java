package net.frozenblock.wilderwild.entity.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.block.api.entity.BillboardBlockEntityRenderer;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HangingTendrilBlockEntityRenderer<T extends HangingTendrilBlockEntity> extends BillboardBlockEntityRenderer<T> {

    public HangingTendrilBlockEntityRenderer(Context ctx) {
        super(ctx);
    }

    public static LayerDefinition getTexturedModelData() {
		return BillboardBlockEntityRenderer.getTexturedModelData();
    }

    public void render(@NotNull T entity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource vertexConsumers, int light, int overlay) {
        if (WilderSharedConstants.CONFIG().billboardTendrils()) {
			super.render(entity, partialTick, poseStack, vertexConsumers, light, overlay);
		}
    }

	@Override
	public ResourceLocation getTexture(T entity) {
		return entity.texture;
	}

	@Override
	public ModelPart getRoot(Context ctx) {
		return ctx.bakeLayer(WilderWildClient.HANGING_TENDRIL);
	}
}
