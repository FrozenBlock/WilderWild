package net.frozenblock.wilderwild.mixin.client;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.render.WilderWardenModel;
import net.frozenblock.wilderwild.entity.render.animations.CustomWardenAnimations;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.frozenblock.wilderwild.misc.config.ModMenuInteractionHandler;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(WardenModel.class)
public abstract class WardenEntityModelMixin<T extends Warden> implements WilderWardenModel {

    @Final @Shadow protected ModelPart bone;

    @Final @Shadow protected ModelPart body;

    @Final @Shadow protected ModelPart head;

    @Final @Shadow protected ModelPart rightTendril;

    @Final @Shadow protected ModelPart leftTendril;

    @Final @Shadow protected ModelPart leftLeg;

    @Final @Shadow protected ModelPart leftArm;

    @Final @Shadow protected ModelPart rightLeg;

    @Final @Shadow protected ModelPart rightArm;

    private List<ModelPart> headAndTendrils;

    private final WardenModel model = WardenModel.class.cast(this);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void WardenEntityModel(ModelPart root, CallbackInfo ci) {
        this.headAndTendrils = ImmutableList.of(this.head, this.leftTendril, this.rightTendril);
    }

    private float lerp(float delta, float start, float end) {
        return Mth.lerp(delta, start, end);
    }

    private float lerpAngleDegrees(float delta, float start, float end) {
        return Mth.rotLerp(delta, start, end);
    }


    @Inject(at = @At("HEAD"), method = "animateTendrils", cancellable = true)
    private void animateTendrils(T warden, float animationProgress, float tickDelta, CallbackInfo info) { //CUSTOM TENDRIL ANIMATION

            float cos = warden.getTendrilAnimation(tickDelta) * (float) (Math.cos((double) animationProgress * 2.25D) * 3.141592653589793D * 0.10000000149011612D);
            float sin = warden.getTendrilAnimation(tickDelta) * (float) (-Math.sin((double) animationProgress * 2.25D) * 3.141592653589793D * 0.12500000149011612D);

        if (ModMenuInteractionHandler.customWardenTendrils()) {
            this.leftTendril.xRot = cos;
            this.rightTendril.xRot = cos;

            this.leftTendril.yRot = sin / 2f;
            this.rightTendril.yRot = -sin / 2f;

            this.leftTendril.zRot = cos / 2f;
            this.rightTendril.zRot = -cos / 2f;
        } else {
            this.leftTendril.xRot = cos;
            this.rightTendril.xRot = -cos;
        }
        info.cancel();
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/monster/warden/Warden;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/WardenModel;animate(Lnet/minecraft/world/entity/AnimationState;Lnet/minecraft/client/animation/AnimationDefinition;F)V", ordinal = 0, shift = At.Shift.BEFORE))
    private void setupAnim(T wardenEntity, float angle, float distance, float anim, float headYaw, float headPitch, CallbackInfo ci) {
        boolean canSwim = !wardenEntity.hasPose(Pose.EMERGING) && !wardenEntity.hasPose(Pose.DIGGING) && !wardenEntity.hasPose(Pose.DYING) && !((WilderWarden) wardenEntity).getSwimmingDyingAnimationState().isStarted() && !((WilderWarden) wardenEntity).getKirbyDeathAnimationState().isStarted();
        boolean shouldMoveArms = !wardenEntity.hasPose(Pose.ROARING) && !wardenEntity.hasPose(Pose.EMERGING) && !wardenEntity.hasPose(Pose.DIGGING);
        boolean shouldMoveBody = !wardenEntity.hasPose(Pose.ROARING) && !wardenEntity.hasPose(Pose.EMERGING) && !wardenEntity.hasPose(Pose.DIGGING);
        boolean shouldMoveHead = !wardenEntity.hasPose(Pose.ROARING) && !wardenEntity.hasPose(Pose.EMERGING) && !wardenEntity.hasPose(Pose.DIGGING);
        if (ModMenuInteractionHandler.wardenSwimAnimation()){
            this.animateSwimming(wardenEntity, angle, distance, anim, headYaw, headPitch, shouldMoveArms, shouldMoveBody, shouldMoveHead, canSwim);
        }
        model.animate(((WilderWarden) wardenEntity).getDyingAnimationState(), CustomWardenAnimations.DYING, anim);
        model.animate(((WilderWarden) wardenEntity).getSwimmingDyingAnimationState(), CustomWardenAnimations.WATER_DYING, anim);
        model.animate(((WilderWarden) wardenEntity).getKirbyDeathAnimationState(), CustomWardenAnimations.KIRBY_DEATH, anim);

    }

    private void animateSwimming(T warden, float angle, float distance, float anim, float headYaw, float headPitch, boolean moveArms, boolean moveBody, boolean moveHead, boolean canSwim) {

        if (warden.isVisuallySwimming() && this.isSubmerged(warden) && canSwim) {

            float angles = (float) (angle * (Math.PI * 0.2));
            float time = anim * 0.1F;
            float cos = Mth.cos(angles);
            float sin = Mth.sin(angles);
            float sin0 = Mth.sin(angles * 0.5F);
            float cos0 = Mth.cos(angles * 2.0F);

            float speedDelta = isSubmerged(warden) ? Math.min(distance / 0.3F, 1.0F) : 0;

            //float speedDelta = this.isSubmerged(warden) ? o : this.lerp(MathHelper.cos(time * 10), o, 0);

            float rad = (float) (Math.PI / 180);

            this.bone.xRot = this.lerpAngleDegrees(speedDelta, this.bone.xRot, headPitch * 0.017453292F + 1.5708F);
            this.bone.yRot = this.lerpAngleDegrees(speedDelta, this.bone.yRot, headYaw * 0.017453292F);
            this.bone.y = this.lerp(speedDelta, this.bone.z, 21) + 3;

            if (moveHead) {
                this.head.xRot = this.lerpAngleDegrees(speedDelta, this.head.xRot, (sin * -10 - 60) * rad);
                this.head.zRot = this.lerpAngleDegrees(speedDelta, this.head.zRot, 0);
                this.head.yRot = this.lerpAngleDegrees(speedDelta, this.head.yRot, 0);
            }

            if (moveBody) {
                this.body.xRot = this.lerpAngleDegrees(speedDelta, this.body.xRot, (sin * 15 - 10) * rad);
                this.body.yRot = this.lerpAngleDegrees(speedDelta, this.body.yRot, (sin0 * 5) * rad);
                this.body.y = this.lerp(speedDelta, this.body.y + 21, 0);
                this.body.z = this.lerp(speedDelta, this.body.z, cos * 2);
            } else {
                this.body.y = 0;
            }

            if (moveArms) {

                this.rightArm.xRot = this.lerpAngleDegrees(speedDelta, this.rightArm.xRot, 0f);
                this.rightArm.yRot = this.lerpAngleDegrees(speedDelta, this.rightArm.yRot, (-cos * 25)) * rad;
                this.rightArm.zRot = this.lerpAngleDegrees(speedDelta, this.rightArm.zRot, (sin * -90 + 90)) * rad;
                this.rightArm.x = this.lerp(speedDelta, this.rightArm.x, (cos0 * 2 + 2) - 13);

                this.leftArm.xRot = this.lerpAngleDegrees(speedDelta, this.leftArm.xRot, 0f);
                this.leftArm.yRot = this.lerpAngleDegrees(speedDelta, this.leftArm.yRot, (cos * 25) * rad);
                this.leftArm.zRot = this.lerpAngleDegrees(speedDelta, this.leftArm.zRot, (sin * 90 - 90) * rad);
                this.leftArm.x = this.lerp(speedDelta, this.leftArm.x, (cos0 * -2 - 2) + 13);

            }

            this.leftLeg.xRot = this.lerpAngleDegrees(speedDelta, this.leftLeg.xRot, (-cos * 35 - 5) * rad);
            this.rightLeg.xRot = this.lerpAngleDegrees(speedDelta, this.rightLeg.xRot, (cos * 35 - 5) * rad);

            this.rightLeg.y = 8;
            this.leftLeg.y = 8;

            this.bone.y += Mth.cos(time);

            this.head.xRot += (Mth.sin(time) * -5) * rad;

            this.body.xRot += (Mth.cos(time) * -5) * rad;

            this.leftArm.zRot += (-Mth.sin(time) * -5 - 5) * rad;
            this.rightArm.zRot += (-Mth.sin(time) * 5 + 5) * rad;

            this.leftLeg.xRot += (Mth.sin(time) * 15 + 15) * rad;
            this.rightLeg.xRot += (Mth.sin(time) * -15 + 15) * rad;

        } else if (this.isSubmerged(warden) && distance <= 0) {

            this.body.y = 0;

            model.root().getAllParts().forEach(ModelPart::resetPose);
        }
    }

    private boolean isSubmerged(Warden warden) {
        return warden.isInWaterOrBubble() || warden.isEyeInFluid(FluidTags.LAVA);
    }

    @Override
    public List<ModelPart> getHeadAndTendrils() {
        return this.headAndTendrils;
    }
}
