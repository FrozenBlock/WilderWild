package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class TumbleweedRenderer extends MobRenderer<Tumbleweed, TumbleweedModel<Tumbleweed>> {
	private static final float pi180 = Mth.PI / 180;
	private final ItemRenderer itemRenderer;

	public TumbleweedRenderer(Context context) {
		super(context, new TumbleweedModel<>(context.bakeLayer(WilderWildClient.TUMBLEWEED)), 0.6F);
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(@NotNull Tumbleweed entity, float entityYaw, float partialTick, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTick, matrixStack, buffer, packedLight);
		ItemStack stack = entity.getVisibleItem();
		if (!stack.isEmpty()) {
			matrixStack.pushPose();
			matrixStack.translate(0, 0.4375, 0);
			float xRot = -Mth.lerp(partialTick, entity.prevPitch, entity.pitch) * pi180;
			float zRot = -Mth.lerp(partialTick, entity.prevRoll, entity.roll) * pi180;
			matrixStack.mulPose(Vector3f.XP.rotation(xRot));
			matrixStack.mulPose(Vector3f.ZP.rotation(zRot));
			this.itemRenderer.renderStatic(stack, ItemTransforms.TransformType.GROUND, packedLight, OverlayTexture.NO_OVERLAY, matrixStack, buffer, 1);
			matrixStack.popPose();
		}
	}

	@Override
	public ResourceLocation getTextureLocation(@NotNull Tumbleweed entity) {
		return WilderWild.id("textures/entity/tumbleweed/tumbleweed.png");
	}

}
