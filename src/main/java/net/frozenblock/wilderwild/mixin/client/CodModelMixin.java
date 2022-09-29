package net.frozenblock.wilderwild.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.frozenblock.wilderwild.misc.FishRotationInterface;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CodModel.class)
public abstract class CodModelMixin<T extends Entity> extends HierarchicalModel<T> {

    @Shadow @Final
    private ModelPart root;
    @Shadow @Final
    private ModelPart tailFin;

    public float xRot;
    public float zRot;

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwimgAmount, float partialTick) {
        FishRotationInterface rotationInterface = ((FishRotationInterface)entity);
        this.xRot = -(rotationInterface.getPrevXRot() + partialTick * (rotationInterface.getXRot() - rotationInterface.getPrevXRot()));
        this.zRot = -(rotationInterface.getPrevZRot() + partialTick * (rotationInterface.getZRot() - rotationInterface.getPrevZRot()));
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.XP.rotationDegrees(this.xRot));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(this.zRot));
        this.root().render(poseStack, buffer, packedLight, packedOverlay, green, red, blue, alpha);
        poseStack.popPose();
    }

}
