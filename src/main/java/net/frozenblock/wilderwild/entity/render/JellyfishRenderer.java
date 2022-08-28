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
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class JellyfishRenderer extends MobRenderer<Jellyfish, JellyfishModel<Jellyfish>> {

    private static final ResourceLocation TEXTURE = WilderWild.id("textures/entity/jellyfish/jellyfish.png");
    private static final ResourceLocation PINK_TEXTURE = WilderWild.id("textures/entity/jellyfish/pink_jellyfish.png");;

    public JellyfishRenderer(Context context) {
        super(context, new JellyfishModel<>(context.bakeLayer(WilderWildClient.JELLYFISH)), 0.9F);
    }

    @Override
    public void setupRotations(Jellyfish jelly, PoseStack poseStack, float f, float g, float h) {
        this.getModel().rotX = Mth.lerp(h, jelly.prevXRots.get(0), jelly.xBodyRot);
        this.getModel().rotZ = Mth.lerp(h, jelly.prevZRots.get(0), jelly.zBodyRot);
        this.getModel().tentRotX = Mth.lerp(h, jelly.prevXRots.get(10), jelly.xBodyRot);
        this.getModel().tentRotZ = Mth.lerp(h, jelly.prevZRots.get(10), jelly.zBodyRot);
        this.getModel().whateverGIs = g;
        poseStack.translate(0.0, 0.5, 0.0);
        poseStack.translate(0.0, -1.2f, 0.0);
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
