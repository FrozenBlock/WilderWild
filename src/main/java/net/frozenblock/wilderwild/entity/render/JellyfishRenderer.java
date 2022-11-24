package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class JellyfishRenderer extends MobRenderer<Jellyfish, JellyfishModel<Jellyfish>> {
    private static final String WHITE_TEXTURE = "textures/entity/jellyfish/white.png";

    public JellyfishRenderer(Context context) {
        super(context, new JellyfishModel<>(context.bakeLayer(WilderWildClient.JELLYFISH)), 0.3F);
    }

    @Override
    public void setupRotations(@NotNull Jellyfish jelly, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
        poseStack.translate(0, -1, 0);
        poseStack.scale(0.8F, 0.8F, 0.8F);
        JellyfishModel<Jellyfish> model = this.getModel();

		if (this.isShaking(jelly)) {
			poseStack.mulPose(Vector3f.YP.rotationDegrees((float)(Math.cos((double)jelly.tickCount * 3.25D) * 3.141592653589793D * 0.4000000059604645D)));
		}

		if (isEntityUpsideDown(jelly)) {
			poseStack.translate(0.0F, jelly.getBbHeight() + 0.1F, 0.0F);
			poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
		}

        if (jelly.isRGB()) {
            float time = (jelly.level.getGameTime() + partialTicks) * 0.05F;
            model.red = Mth.clamp(Math.abs((time % 6) - 3) - 1, 0, 1);
            model.green = Mth.clamp(Math.abs(((time - 2) % 6) - 3) - 1, 0, 1);
            model.blue = Mth.clamp(Math.abs(((time - 4) % 6) - 3) - 1, 0, 1);
        } else {
            model.red = 1;
            model.green = 1;
            model.blue = 1;
        }
    }

    @Override
    protected int getBlockLightLevel(@NotNull Jellyfish jellyfish, @NotNull BlockPos blockPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull Jellyfish jellyfish) {
        if (jellyfish.isRGB()) {
            return WilderWild.id(WHITE_TEXTURE);
        }
        return jellyfish.getVariant().getTexture();
    }
}
