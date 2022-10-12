package net.frozenblock.wilderwild.mixin.client.easter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
@Mixin(PigModel.class)
public class UppyBallooModel<T extends Entity> extends QuadrupedModel<T> {

    @Unique
    private static final float radians = ((float) Math.PI / 180);
    @Unique
    private static final float non_uppy_balloo_angle = 90 * radians;

    public UppyBallooModel(ModelPart root) {
        super(root, false, 4.0f, 4.0f, 2.0f, 2.0f, 24);
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float h, float i, float j) {
        this.head.xRot = j * 0.017453292F;
        this.head.yRot = i * 0.017453292F;
        float fastLimbAngle = limbAngle * 0.6662F;
        float fastLimbDistance = 1.4F * limbDistance;
        float firstAngle = (float) Math.cos(fastLimbAngle) * fastLimbDistance;
        float secondAngle = (float) Math.cos(fastLimbAngle + 3.1415927F) * fastLimbDistance;
        this.rightHindLeg.xRot = firstAngle;
        this.leftHindLeg.xRot = secondAngle;
        this.rightFrontLeg.xRot = secondAngle;
        this.leftFrontLeg.xRot = firstAngle;
        String string = ChatFormatting.stripFormatting(entity.getName().getString());
        assert string != null;
        if (string.equalsIgnoreCase("a view from the top")) {
            this.body.xRot = 0;
        } else {
            this.body.xRot = non_uppy_balloo_angle;
        }
    }

}
