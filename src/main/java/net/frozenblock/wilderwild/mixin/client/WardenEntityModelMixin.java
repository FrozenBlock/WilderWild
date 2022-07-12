package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.render.animations.CustomWardenAnimations;
import net.frozenblock.wilderwild.entity.render.animations.WardenAnimationInterface;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.animation.WardenAnimations;
import net.minecraft.client.render.entity.model.WardenEntityModel;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.tag.FluidTags;
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
    protected ModelPart bone;

    @Final
    @Shadow
    protected ModelPart body;

    @Final
    @Shadow
    protected ModelPart head;

    @Final
    @Shadow
    protected ModelPart rightTendril;

    @Final
    @Shadow
    protected ModelPart leftTendril;

    @Final
    @Shadow
    protected ModelPart leftLeg;

    @Final
    @Shadow
    protected ModelPart leftArm;

    @Final
    @Shadow
    protected ModelPart rightLeg;

    @Final
    @Shadow
    protected ModelPart rightArm;

    @Shadow
    private void setHeadAngle(float i, float j) {}

    @Shadow
    private void setLimbAngles(float f, float g) {}

    @Shadow
    private void setHeadAndBodyAngles(float k) {}

    @Shadow
    private void setTendrilPitches(T warden, float animationProgress, float tickDelta) {}

    private final WardenEntityModel model = WardenEntityModel.class.cast(this);



    private float lerp(float delta, float start, float end) {
        return MathHelper.lerp(delta, start, end);
    }

    private float lerpAngleDegrees(float delta, float start, float end) {
        return MathHelper.lerpAngleDegrees(delta, start, end);
    }


    @Inject(at = @At("HEAD"), method = "setTendrilPitches", cancellable = true)
    private void setTendrilPitches(T warden, float animationProgress, float tickDelta, CallbackInfo info) {
        float cos = warden.getTendrilPitch(tickDelta) * (float) (Math.cos((double) animationProgress * 2.25D) * 3.141592653589793D * 0.10000000149011612D);
        float sin = warden.getTendrilPitch(tickDelta) * (float) (-Math.sin((double) animationProgress * 2.25D) * 3.141592653589793D * 0.12500000149011612D);

        //hecc yeah we're using all axes for this one >:3 -merp
        //hi merp

        this.leftTendril.pitch = cos;
        this.rightTendril.pitch = cos;

        this.leftTendril.yaw = sin / 2f;
        this.rightTendril.yaw = -sin / 2f;

        this.leftTendril.roll = cos / 2f;
        this.rightTendril.roll = -cos / 2f;
        info.cancel();
    }

    @Inject(method = "setAngles(Lnet/minecraft/entity/mob/WardenEntity;FFFFF)V", at = @At("HEAD"), cancellable = true)
    private void setAngles(T wardenEntity, float angle, float distance, float anim, float headYaw, float headPitch, CallbackInfo ci) {
        ci.cancel();
        boolean swimming = this.isSubmerged(wardenEntity) && distance > 0;
        boolean cannotSwim = wardenEntity.isInPose(EntityPose.EMERGING) || wardenEntity.isInPose(EntityPose.DIGGING) || wardenEntity.isInPose(EntityPose.DYING);
        boolean shouldMoveArms = !wardenEntity.isInPose(EntityPose.ROARING) && !wardenEntity.isInPose(EntityPose.EMERGING) && !wardenEntity.isInPose(EntityPose.DIGGING);
        boolean shouldMoveBody = !wardenEntity.isInPose(EntityPose.ROARING) && !wardenEntity.isInPose(EntityPose.EMERGING) && !wardenEntity.isInPose(EntityPose.DIGGING);
        boolean shouldMoveHead = !wardenEntity.isInPose(EntityPose.ROARING) && !wardenEntity.isInPose(EntityPose.EMERGING) && !wardenEntity.isInPose(EntityPose.DIGGING);
        model.getPart().traverse().forEach(ModelPart::resetTransform);
        float k = anim - (float)wardenEntity.age;
        this.setHeadAngle(headYaw, headPitch);
        this.setLimbAngles(angle, distance);
        this.setHeadAndBodyAngles(anim);
        this.setTendrilPitches(wardenEntity, anim, k);
        this.setSwimmingAngles(wardenEntity, angle, distance, anim, k, headYaw, headPitch, swimming, shouldMoveArms, shouldMoveBody, shouldMoveHead, cannotSwim, ci);
        model.updateAnimation(wardenEntity.attackingAnimationState, WardenAnimations.ATTACKING, anim);
        model.updateAnimation(wardenEntity.chargingSonicBoomAnimationState, WardenAnimations.CHARGING_SONIC_BOOM, anim);
        model.updateAnimation(wardenEntity.diggingAnimationState, WardenAnimations.DIGGING, anim);
        model.updateAnimation(wardenEntity.emergingAnimationState, WardenAnimations.EMERGING, anim);
        model.updateAnimation(wardenEntity.roaringAnimationState, WardenAnimations.ROARING, anim);
        model.updateAnimation(wardenEntity.sniffingAnimationState, WardenAnimations.SNIFFING, anim);
        model.updateAnimation(((WardenAnimationInterface) wardenEntity).getDyingAnimationState(), CustomWardenAnimations.DYING, anim);

    }

    private void setSwimmingAngles(T wardenEntity, float angle, float distance, float anim, float k, float headYaw, float headPitch, boolean swimming, boolean moveArms, boolean moveBody, boolean moveHead, boolean cannotSwim, CallbackInfo ci) {

        if (swimming && !cannotSwim) {

            float speed = (float) (angle * (Math.PI * 0.2));

            float time = anim * 0.1F;

            float cos = MathHelper.cos(speed);
            float sin = MathHelper.sin(speed);

            float sin0 = MathHelper.sin(speed * 0.5F);
            float cos0 = MathHelper.cos(speed * 2.0F);

            float o = isSubmerged(wardenEntity) ? Math.min(distance / 0.3F, 1.0F) : 0;

            float rad = (float) (Math.PI / 180);

            this.bone.pitch = this.lerpAngleDegrees(o, this.bone.pitch, headPitch * 0.017453292F + 1.5708F);
            this.bone.yaw = this.lerpAngleDegrees(o, this.bone.yaw, headYaw * 0.017453292F);
            this.bone.pivotY = this.lerp(o, this.bone.pivotZ, 24) + 3;

            if (moveHead) {
                this.head.pitch = this.lerpAngleDegrees(o, this.head.pitch, (sin * -10 - 60) * rad);
                this.head.roll = this.lerpAngleDegrees(o, this.head.roll, 0);
                this.head.yaw = this.lerpAngleDegrees(o, this.head.yaw, 0);
            }

            if (moveBody) {
                this.body.pitch = this.lerpAngleDegrees(o, this.body.pitch, (sin * 15 - 10) * rad);
                this.body.yaw = this.lerpAngleDegrees(o, this.body.yaw, (sin0 * 5) * rad);
                this.body.pivotY = this.lerp(o, this.body.pivotY + 21,0);
                this.body.pivotZ = this.lerp(o, this.body.pivotZ,-cos * 2);
            } else {
                this.body.pivotY = 0;
            }

            if (moveArms) {

                this.rightArm.pitch = this.lerpAngleDegrees(o, this.rightArm.pitch, 0f);
                this.rightArm.yaw = this.lerpAngleDegrees(o, this.rightArm.yaw, (-cos * 25)) * rad;
                this.rightArm.roll = this.lerpAngleDegrees(o, this.rightArm.roll, (sin * -90 + 90)) * rad;
                this.rightArm.pivotX = this.lerp(o, this.rightArm.pivotX, (cos0 * 2 + 2) - 13);

                this.leftArm.pitch = this.lerpAngleDegrees(o, this.leftArm.pitch, 0f);
                this.leftArm.yaw = this.lerpAngleDegrees(o, this.leftArm.yaw, (cos * 25) * rad);
                this.leftArm.roll = this.lerpAngleDegrees(o, this.leftArm.roll, (sin * 90 - 90) * rad);
                this.leftArm.pivotX = this.lerp(o, this.leftArm.pivotX, (cos0 * -2 - 2) + 13);

            }

            this.leftLeg.pitch = this.lerpAngleDegrees(o, this.leftLeg.pitch, (-cos * 35 + 15) * rad);
            this.rightLeg.pitch = this.lerpAngleDegrees(o, this.rightLeg.pitch, (cos * 35 + 15) * rad);

            this.rightLeg.pivotY = 8;
            this.leftLeg.pivotY = 8;

            this.bone.pivotY += MathHelper.cos(time);

            this.head.pitch += (MathHelper.sin(time) * -5) * rad;

            this.body.pitch += (MathHelper.cos(time) * -5) * rad;

            this.leftArm.roll += (-MathHelper.sin(time) * -5 - 5) * rad;
            this.rightArm.roll += (-MathHelper.sin(time) * 5 + 5) * rad;

            this.leftLeg.pitch += (MathHelper.sin(time) * 15 + 15) * rad;
            this.rightLeg.pitch += (MathHelper.sin(time) * -15 + 15) * rad;

        } else if (this.isSubmerged(wardenEntity) && distance <= 0) {

            this.body.pivotY = 0;

            ci.cancel();
            model.getPart().traverse().forEach(ModelPart::resetTransform);
            this.setHeadAngle(headYaw, headPitch);
            this.setLimbAngles(angle, distance);
            this.setHeadAndBodyAngles(anim);
            this.setTendrilPitches(wardenEntity, anim, k);
        }
    }

    private boolean isSubmerged(WardenEntity warden) {
        return warden.isSubmergedIn(FluidTags.WATER) || warden.isSubmergedIn(FluidTags.LAVA);
    }
}
