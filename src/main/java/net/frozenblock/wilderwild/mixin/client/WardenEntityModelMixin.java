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
        boolean swimming = this.isSubmergedInWaterOrLava(wardenEntity) && distance > 0;
        model.getPart().traverse().forEach(ModelPart::resetTransform);
        float k = anim - (float)wardenEntity.age;
        this.setHeadAngle(headYaw, headPitch);
        this.setLimbAngles(angle, distance);
        this.setHeadAndBodyAngles(anim);
        this.setTendrilPitches(wardenEntity, anim, k);
        model.updateAnimation(wardenEntity.attackingAnimationState, WardenAnimations.ATTACKING, anim);
        model.updateAnimation(wardenEntity.chargingSonicBoomAnimationState, WardenAnimations.CHARGING_SONIC_BOOM, anim);
        model.updateAnimation(wardenEntity.diggingAnimationState, WardenAnimations.DIGGING, anim);
        model.updateAnimation(wardenEntity.emergingAnimationState, WardenAnimations.EMERGING, anim);
        model.updateAnimation(wardenEntity.roaringAnimationState, WardenAnimations.ROARING, anim);
        model.updateAnimation(wardenEntity.sniffingAnimationState, swimming ? CustomWardenAnimations.SWIMMING_SNIFFING : WardenAnimations.SNIFFING, anim);
        model.updateAnimation(((WardenAnimationInterface) wardenEntity).getDyingAnimationState(), CustomWardenAnimations.DYING, anim);

        boolean cannotSwim = wardenEntity.isInPose(EntityPose.EMERGING) || wardenEntity.isInPose(EntityPose.DIGGING) || wardenEntity.isInPose(EntityPose.DYING);
        boolean shouldMoveArms = !wardenEntity.isInPose(EntityPose.ROARING) && !wardenEntity.isInPose(EntityPose.EMERGING) && !wardenEntity.isInPose(EntityPose.DIGGING);
        boolean shouldMoveBody = !wardenEntity.isInPose(EntityPose.ROARING) && !wardenEntity.isInPose(EntityPose.EMERGING) && !wardenEntity.isInPose(EntityPose.DIGGING);
        boolean shouldMoveHead = !wardenEntity.isInPose(EntityPose.ROARING) && !wardenEntity.isInPose(EntityPose.EMERGING) && !wardenEntity.isInPose(EntityPose.DIGGING);

        if (swimming && !cannotSwim) {

            this.bone.pitch = MathHelper.clamp(distance * 5, 0,headYaw * 0.017453292F + 1.5708F);
            this.bone.yaw = headPitch * 0.017453292F;
            this.bone.pivotY = -2F;
            this.bone.pivotZ = Math.max(-distance,-24);

            float time = (float) (angle * (Math.PI * 0.2));
            float cos = MathHelper.cos(time);
            float sin = MathHelper.sin(time);
            float sin0 = MathHelper.sin(time * 0.5F);
            float cos0 = MathHelper.cos(time * 2.0F);
            float rad = (float) (Math.PI / 180);

            if (shouldMoveHead) {
                this.head.pitch = Math.max(distance * -10, (sin * -10 - 60) * rad);
                this.head.pivotY = Math.max((distance * -10) - 13, -17);
            }

            if (shouldMoveBody) {
                this.body.pitch = Math.max(distance * -10, (sin * 15 - 10) * rad);
                this.body.yaw = (sin0 * 5) * rad;
                this.body.pivotY = Math.min(distance * 10, -cos * 2);
            } else {
                this.body.pivotY = 0;
            }

            if (shouldMoveArms) {

                this.rightArm.pitch = 0f;
                this.rightArm.yaw = Math.max(distance * -10, (-cos * 25) * rad);
                this.rightArm.roll = Math.min(distance * 10, (sin * -90 + 90) * rad);
                this.rightArm.pivotX = cos0 * 2 - 11;

                this.leftArm.pitch = 0f;
                this.leftArm.yaw = Math.max(distance * -10,(cos * 25) * rad);
                this.leftArm.roll = Math.max(distance * -10,(sin * 90 - 90) * rad);
                this.leftArm.pivotX = cos0 * -2 + 11;

            }

            this.leftLeg.pitch = (-cos * 35 + 15) * rad;
            this.leftLeg.pivotY = 8;

            this.rightLeg.pitch = (cos * 35 + 15) * rad;
            this.rightLeg.pivotY = 8;

        } else if (this.isSubmergedInWaterOrLava(wardenEntity) && distance <= 0){

            this.body.pivotY = 8;

            ci.cancel();
            model.getPart().traverse().forEach(ModelPart::resetTransform);
            this.setHeadAngle(headYaw, headPitch);
            this.setLimbAngles(angle, distance);
            this.setHeadAndBodyAngles(anim);
            this.setTendrilPitches(wardenEntity, anim, k);
        }
    }

    private boolean isSubmergedInWaterOrLava(WardenEntity warden) {
        return warden.isSubmergedIn(FluidTags.WATER) || warden.isSubmergedIn(FluidTags.LAVA);
    }
}
