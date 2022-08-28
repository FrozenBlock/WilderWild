package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.render.feature.JellyfishFeatureRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class JellyfishRenderer extends MobRenderer<Jellyfish, JellyfishModel<Jellyfish>> {

    private static final ResourceLocation TEXTURE = WilderWild.id("textures/entity/jellyfish/jellyfish.png");
    private static final ResourceLocation PINK_TEXTURE = WilderWild.id("textures/entity/jellyfish/pink_jellyfish.png");

    public JellyfishRenderer(Context context) {
        super(context, new JellyfishModel<>(context.bakeLayer(WilderWildClient.JELLYFISH)), 0.9F);
        this.addLayer(new JellyfishFeatureRenderer<>(this, PINK_TEXTURE));

    }

    @Override
    public void setupRotations(@NotNull Jellyfish jelly, PoseStack poseStack, float f, float g, float h) {
        //poseStack.translate(0.0, 0.5, 0.0);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0f - g));
        poseStack.translate(0.0, -1.5, 0.0);
        poseStack.scale(0.8F, 0.8F, 0.8F);
        JellyfishModel<Jellyfish> model = this.getModel();
        model.xRot = Mth.lerp(h, jelly.xRot1, jelly.xBodyRot);
        model.zRot = Mth.lerp(h, jelly.zRot1, jelly.zBodyRot);
        model.tentXRot = Mth.lerp(h, jelly.xRot3, jelly.xRot2);
        model.tentZRot = Mth.lerp(h, jelly.zRot3, jelly.zRot2);
        model.lightProg = Mth.lerp(h, jelly.previousLight, jelly.currentLight) / 15;
    }

    @Override
    protected int getBlockLightLevel(@NotNull Jellyfish jellyfish, @NotNull BlockPos blockPos) {
        return 15;
    }

    @Override
    @Nullable
    protected RenderType getRenderType(@NotNull Jellyfish jellyfish, boolean bl, boolean bl2, boolean bl3) {
        return WilderWildClient.ENTITY_TRANSLUCENT_EMISSIVE_FIXED.apply(TEXTURE, false);
    }


    @Override
    public ResourceLocation getTextureLocation(@NotNull Jellyfish jellyfish) {
        return TEXTURE;
    }
}
