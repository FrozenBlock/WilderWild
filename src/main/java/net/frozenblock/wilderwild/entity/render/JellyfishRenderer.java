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

    public JellyfishRenderer(Context context) {
        super(context, new JellyfishModel<>(context.bakeLayer(WilderWildClient.JELLYFISH)), 0.9F);
    }

    @Override
    public void setupRotations(Jellyfish jelly, PoseStack poseStack, float f, float g, float h) {
        float i = Mth.lerp(h, jelly.xBodyRotO, jelly.xBodyRot);
        float j = Mth.lerp(h, jelly.zBodyRotO, jelly.zBodyRot);
        poseStack.translate(0.0, 0.5, 0.0);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0f - g));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(i));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(j));
        poseStack.translate(0.0, -1.2f, 0.0);
    }

    @Override
    protected float getBob(Jellyfish jelly, float f) {
        if (jelly.getPushingTicks() > 0) {
           return Mth.lerp(f, jelly.getPrevPushingTicks(), jelly.getPushingTicks());
        } else {
            float lerpedInhale = Mth.lerp(f, jelly.getPrevInhaleTicks(), jelly.getInhaleTicks());
            return lerpedInhale / jelly.getInhaleLength();
        }
        //TODO: JELLYFISH TENTACLE MOVEMENT BASED ON PUSH AND INHALE TICKS
        //return Mth.lerp(f, jelly.getPrevTentacleAngle(), jelly.getTentacleAngle());
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
