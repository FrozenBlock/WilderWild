package net.frozenblock.wilderwild.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.WardenEntityModel;
import net.minecraft.entity.mob.WardenEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WardenEntityModel.class)
public class WardenEntityModelMixin<T extends WardenEntity> {
    @Final
    @Shadow
    protected ModelPart head;
    @Final
    @Shadow
    protected ModelPart body;
    @Final
    @Shadow
    protected ModelPart leftArm;
    @Final
    @Shadow
    protected ModelPart rightArm;
    @Final
    @Shadow
    protected ModelPart rightTendril;
    @Final
    @Shadow
    protected ModelPart leftTendril;
    /**
     * @author FrozenBlock
     * @reason better head looking???
     */
    @Overwrite
    private void setHeadAngle(float yaw, float pitch) {

        float headX = pitch * 0.017453292F;
        float headY = yaw * 0.017453292F;
        float r = (float) (180 / Math.PI);

        this.head.pitch = headX;
        this.head.yaw = headY;
        this.head.roll = headY / 2F;

        this.body.pitch = headY / 2F;
        this.body.yaw = Math.abs(headY / 2) + (headX / 2);
        this.body.pivotX = r * -(headY / 32);
        this.body.pivotZ = r * (-Math.abs(headY / 16) - (headX / 16));

        this.rightArm.pitch = Math.abs(headY / 2) + Math.abs(headX / 2);
        this.rightArm.roll = Math.abs(headY / 5) + Math.abs(headX / 5);
        this.rightArm.pivotZ = r * -(headY / 32);

        this.leftArm.pitch = Math.abs(headY / 2) + Math.abs(headX / 2);
        this.leftArm.roll = -Math.abs(headY / 5) - Math.abs(headX / 5);
        this.leftArm.pivotZ = r * -(headY / 32);

    }

    /**
     * @author FrozenBlock
     * @reason yay tendrils
     */
    @Overwrite
    private void setTendrilPitches(T warden, float animationProgress, float tickDelta) {
        float f = warden.getTendrilPitch(tickDelta) * (float)(Math.cos((double)animationProgress * 2.25D) * 3.141592653589793D * 0.10000000149011612D);
        float g = warden.getTendrilPitch(tickDelta) * (float)(-Math.sin((double)animationProgress * 2.25D) * 3.141592653589793D * 0.12500000149011612D);

    //hecc yeah we're using all axes for this one >:3 -merp

        this.leftTendril.pitch = f;
        this.rightTendril.pitch = f;

        this.leftTendril.yaw = g / 2f;
        this.rightTendril.yaw = -g / 2f;

        this.leftTendril.roll = f / 2f;
        this.rightTendril.roll = -f / 2f;
    }
}
