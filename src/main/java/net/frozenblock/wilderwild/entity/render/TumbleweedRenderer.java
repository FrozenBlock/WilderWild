package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
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
	public Vec3 getRenderOffset(@NotNull Tumbleweed entity, float partialTicks) {
		return new Vec3(0, 0.2375, 0);
	}

	@Override
	public void render(@NotNull Tumbleweed entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
		ItemStack stack = entity.getVisibleItem();
		if (!stack.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(entity.itemX, 0.4375, entity.itemZ);
			poseStack.mulPose(Vector3f.XP.rotation(-Mth.lerp(partialTick, entity.prevPitch, entity.pitch) * pi180));
			poseStack.pushPose();
			poseStack.mulPose(Vector3f.ZP.rotation(Mth.lerp(partialTick, entity.prevRoll, entity.roll) * pi180));
			this.itemRenderer.renderStatic(stack, ItemTransforms.TransformType.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, 1);
			poseStack.popPose();
			poseStack.popPose();
		}
	}

	@Override
	protected void setupRotations(@NotNull Tumbleweed entityLiving, @NotNull PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {

	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull Tumbleweed entity) {
		return WilderSharedConstants.id("textures/entity/tumbleweed/tumbleweed.png");
	}

}
