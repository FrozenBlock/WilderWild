package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class JellyfishRenderer extends MobRenderer<Jellyfish, JellyfishModel<Jellyfish>> {

    private static final String BASE_TEXTURE = "textures/entity/jellyfish/";

    public JellyfishRenderer(Context context) {
        super(context, new JellyfishModel<>(context.bakeLayer(WilderWildClient.JELLYFISH)), 0.3F);
    }

    @Override
    public void setupRotations(@NotNull Jellyfish jelly, PoseStack poseStack, float f, float g, float h) {
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0f - g));
        poseStack.translate(0, -1, 0);
        poseStack.scale(0.8F, 0.8F, 0.8F);
    }

    @Override
    protected int getBlockLightLevel(@NotNull Jellyfish jellyfish, @NotNull BlockPos blockPos) {
        return 15;
    }

    @Override
    @Nullable
    protected RenderType getRenderType(@NotNull Jellyfish jellyfish, boolean bl, boolean bl2, boolean bl3) {
        return WilderWildClient.ENTITY_TRANSLUCENT_EMISSIVE_FIXED.apply(WilderWild.id(BASE_TEXTURE + jellyfish.getVariant() + ".png"), false);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull Jellyfish jellyfish) {
        return WilderWild.id(BASE_TEXTURE + jellyfish.getVariant() + ".png");
    }

}
