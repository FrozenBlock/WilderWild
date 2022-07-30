package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(PigEntityModel.class)
public abstract class UppyBallooModel<T extends Entity> extends QuadrupedEntityModel<T> {

    private static final float radians = ((float) Math.PI / 180);
    private static final float non_uppy_balloo_angle = 90 * radians;

    public UppyBallooModel(ModelPart root) {
        super(root, false, 4.0f, 4.0f, 2.0f, 2.0f, 24);
    }

    @Override
    public void setAngles(Entity entity, float limbAngle, float limbDistance, float h, float i, float j) {
        this.head.pitch = j * 0.017453292F;
        this.head.yaw = i * 0.017453292F;
        float fastLimbAngle = limbAngle * 0.6662F;
        float fastLimbDistance = 1.4F * limbDistance;
        float firstAngle = MathHelper.cos(fastLimbAngle) * fastLimbDistance;
        float secondAngle = MathHelper.cos(fastLimbAngle + 3.1415927F) * fastLimbDistance;
        this.rightHindLeg.pitch = firstAngle;
        this.leftHindLeg.pitch = secondAngle;
        this.rightFrontLeg.pitch = secondAngle;
        this.leftFrontLeg.pitch = firstAngle;
        String string = Formatting.strip(entity.getName().getString());
        assert string != null;
        if (string.equalsIgnoreCase("a view from the top")) {
            this.body.pitch = 0;
        } else {
            this.body.pitch = non_uppy_balloo_angle;
        }
    }

}
