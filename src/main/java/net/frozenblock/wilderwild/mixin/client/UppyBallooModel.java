package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(PigEntityModel.class)
public abstract class UppyBallooModel<T extends Entity>
        extends QuadrupedEntityModel<T> {

    private static final float radians = ((float)Math.PI / 180);
    private static final float non_uppy_balloo_angle = 90 * radians;

    public UppyBallooModel(ModelPart root) {
        super(root, false, 4.0f, 4.0f, 2.0f, 2.0f, 24);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * radians;
        this.head.yaw = headYaw * radians;
        float limbs = limbAngle * 0.6662f;
        this.rightHindLeg.pitch = MathHelper.cos(limbs) * 1.4f * limbDistance;
        this.leftHindLeg.pitch = MathHelper.cos(limbs + (float)Math.PI) * 1.4f * limbDistance;
        this.rightFrontLeg.pitch = this.leftHindLeg.pitch;
        this.leftFrontLeg.pitch = this.rightHindLeg.pitch;
        String string = Formatting.strip(entity.getName().getString());
        assert string != null;
        if (string.equalsIgnoreCase("a view from the top")) {
            this.body.pitch = 0;
        } else {
            this.body.pitch = non_uppy_balloo_angle;
        }
    }

}
