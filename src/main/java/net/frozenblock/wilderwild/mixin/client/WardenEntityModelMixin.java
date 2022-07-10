package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.render.animations.CustomWardenAnimations;
import net.frozenblock.wilderwild.entity.render.animations.WardenAnimationInterface;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.WardenEntityModel;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WardenEntityModel.class)
public class WardenEntityModelMixin<T extends WardenEntity> {
    @Final
    @Shadow
    private ModelPart root;

    @Final
    @Shadow
    protected ModelPart body;

    @Final
    @Shadow
    private ModelPart head;

    @Final
    @Shadow
    protected ModelPart rightTendril;

    @Final
    @Shadow
    protected ModelPart leftTendril;

    @Final
    @Shadow
    private ModelPart leftLeg;

    @Final
    @Shadow
    protected ModelPart leftArm;

    @Final
    @Shadow
    protected ModelPart rightLeg;

    @Final
    @Shadow
    protected ModelPart rightArm;

    private final WardenEntityModel<T> model = WardenEntityModel.class.cast(this);

    @Inject(at = @At("HEAD"), method = "setTendrilPitches", cancellable = true)
    private void setTendrilPitches(T warden, float animationProgress, float tickDelta, CallbackInfo info) {
        float f = warden.getTendrilPitch(tickDelta) * (float) (Math.cos((double) animationProgress * 2.25D) * 3.141592653589793D * 0.10000000149011612D);
        float g = warden.getTendrilPitch(tickDelta) * (float) (-Math.sin((double) animationProgress * 2.25D) * 3.141592653589793D * 0.12500000149011612D);

        //hecc yeah we're using all axes for this one >:3 -merp
        //hi merp

        this.leftTendril.pitch = f;
        this.rightTendril.pitch = f;

        this.leftTendril.yaw = g / 2f;
        this.rightTendril.yaw = -g / 2f;

        this.leftTendril.roll = f / 2f;
        this.rightTendril.roll = -f / 2f;
        info.cancel();
    }

    @Inject(method = "setAngles(Lnet/minecraft/entity/mob/WardenEntity;FFFFF)V", at = @At("TAIL"))
    private void setAngles(T wardenEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        model.updateAnimation(((WardenAnimationInterface) wardenEntity).getDyingAnimationState(), CustomWardenAnimations.DYING, h);
        if (wardenEntity.isSubmergedInWater()) {

            this.root.pitch = j;
            this.root.yaw = i;

            float e = f * 0.8662F;
            float l = MathHelper.cos(e);
            float m = Math.min(0.5F, 3.0F * l);
            float n = MathHelper.sin(e);
            float o = MathHelper.sin(m * 0.5F);
            float p = MathHelper.cos(m * 2.0F);
            float rad = (float) (Math.PI / 180);

            this.head.pitch = (n * -10 - 60) * rad;
            this.head.roll = 0;
            this.head.pivotY = 2;

            this.body.pitch = (n * 15 - 10) * rad;
            this.body.yaw = (o * 5) * rad;
            this.body.pivotY = -l * 2;

            this.rightArm.pitch = 0f;
            this.rightArm.yaw = (-l * 25) * rad;
            this.rightArm.roll = (n * -90 + 90) * rad;
            this.rightArm.pivotX = p * 2 + 2;

            this.leftArm.pitch = 0f;
            this.leftArm.yaw = (l * 25) * rad;
            this.leftArm.roll = (-n * -90 + 90) * rad;
            this.leftArm.pivotX = p * -2 - 2;

            this.leftLeg.pitch = (-l * 35 + 15) * rad;
            this.rightLeg.pitch = (l * 35 + 15) * rad;
        }
    }
}
