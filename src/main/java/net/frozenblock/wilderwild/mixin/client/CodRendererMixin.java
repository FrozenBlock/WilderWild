package net.frozenblock.wilderwild.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.frozenblock.wilderwild.misc.FishRotationInterface;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cod;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CodRenderer.class)
public class CodRendererMixin {

    @Inject(method = "setupRotations", at = @At("INVOKE"), cancellable = true)
    public void setupRotations(Cod entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, CallbackInfo info) {
        info.cancel();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0f - rotationYaw));
    }

}
