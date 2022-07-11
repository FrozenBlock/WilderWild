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
    private ModelPart root;

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

    @Inject(method = "setAngles(Lnet/minecraft/entity/mob/WardenEntity;FFFFF)V", at = @At("HEAD"), cancellable = true)
    private void setAngles(T wardenEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        ci.cancel();
        boolean swimming = this.isSubmergedInWaterOrLava(wardenEntity) && g > 0;
        model.getPart().traverse().forEach(ModelPart::resetTransform);
        float k = h - (float)wardenEntity.age;
        this.setHeadAngle(i, j);
        this.setLimbAngles(f, g);
        this.setHeadAndBodyAngles(h);
        this.setTendrilPitches(wardenEntity, h, k);
        model.updateAnimation(wardenEntity.attackingAnimationState, WardenAnimations.ATTACKING, h);
        model.updateAnimation(wardenEntity.chargingSonicBoomAnimationState, WardenAnimations.CHARGING_SONIC_BOOM, h);
        model.updateAnimation(wardenEntity.diggingAnimationState, WardenAnimations.DIGGING, h);
        model.updateAnimation(wardenEntity.emergingAnimationState, WardenAnimations.EMERGING, h);
        model.updateAnimation(wardenEntity.roaringAnimationState, WardenAnimations.ROARING, h);
        model.updateAnimation(wardenEntity.sniffingAnimationState, swimming ? CustomWardenAnimations.SWIMMING_SNIFFING : WardenAnimations.SNIFFING, h);
        model.updateAnimation(((WardenAnimationInterface) wardenEntity).getDyingAnimationState(), CustomWardenAnimations.DYING, h);

        boolean cannotSwim = wardenEntity.isInPose(EntityPose.EMERGING) || wardenEntity.isInPose(EntityPose.DIGGING) || wardenEntity.isInPose(EntityPose.DYING) || !wardenEntity.chargingSonicBoomAnimationState.isRunning();
        boolean shouldMoveArms = !wardenEntity.isInPose(EntityPose.ROARING) && !wardenEntity.isInPose(EntityPose.EMERGING) && !wardenEntity.isInPose(EntityPose.DIGGING) && !wardenEntity.chargingSonicBoomAnimationState.isRunning();
        boolean shouldMoveBody = !wardenEntity.isInPose(EntityPose.ROARING) && !wardenEntity.isInPose(EntityPose.EMERGING) && !wardenEntity.isInPose(EntityPose.DIGGING);
        boolean shouldMoveHead = !wardenEntity.isInPose(EntityPose.ROARING) && !wardenEntity.isInPose(EntityPose.EMERGING) && !wardenEntity.isInPose(EntityPose.DIGGING);

        //TODO: FIX ARMS AFTER SONIC BOOM ANIMATION
        if (swimming && !cannotSwim) {

            this.bone.pitch = MathHelper.clamp(g * 5, 0,j * 0.017453292F + 1.5708F);
            this.bone.yaw = i * 0.017453292F;
            this.bone.pivotY = 0F;
            this.bone.pivotZ = Math.max(-g,-24);

            float e = (float) (f * (Math.PI * 0.2));
            float l = MathHelper.cos(e);
            float m = MathHelper.sin(e);
            float n = Math.min(0.5F, 3.0F * l);
            float o = MathHelper.sin(n * 0.5F);
            float p = MathHelper.cos(n * 2.0F);
            float rad = (float) (Math.PI / 180);

            if (shouldMoveHead) {
                this.head.pitch = Math.max(g * -10, (m * -10 - 60) * rad);
                this.head.pivotY = Math.max((g * -10) - 13, -17);
            }

            if (shouldMoveBody) {
                this.body.pitch = Math.max(g * -10, (m * 15 - 10) * rad);
                this.body.yaw = (o * 5) * rad;
                this.body.pivotY = Math.min(g * 10, -l * 2);
            } else {
                this.body.pivotY = 0;
            }

            if (shouldMoveArms) {

                this.rightArm.pitch = 0f;
                this.rightArm.yaw = (-l * 25) * rad;
                this.rightArm.roll = (m * -90 + 90) * rad;
                this.rightArm.pivotX = p * 2 - 11;

                this.leftArm.pitch = 0f;
                this.leftArm.yaw = (l * 25) * rad;
                this.leftArm.roll = (m * 90 - 90) * rad;
                this.leftArm.pivotX = p * -2 + 11;

            }

            this.leftLeg.pitch = (-l * 35 + 15) * rad;
            this.leftLeg.pivotY = 8;

            this.rightLeg.pitch = (l * 35 + 15) * rad;
            this.rightLeg.pivotY = 8;

        } else if (this.isSubmergedInWaterOrLava(wardenEntity) && g <= 0){

            this.body.pivotY = 8;

            ci.cancel();
            model.getPart().traverse().forEach(ModelPart::resetTransform);
            this.setHeadAngle(i, j);
            this.setLimbAngles(f, g);
            this.setHeadAndBodyAngles(h);
            this.setTendrilPitches(wardenEntity, h, k);
        }
    }

    private boolean isSubmergedInWaterOrLava(WardenEntity warden) {
        return warden.isSubmergedIn(FluidTags.WATER) || warden.isSubmergedIn(FluidTags.LAVA);
    }
}
