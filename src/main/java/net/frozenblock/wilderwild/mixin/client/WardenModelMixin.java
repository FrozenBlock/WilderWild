package net.frozenblock.wilderwild.mixin.client;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.render.WilderWardenModel;
import net.frozenblock.wilderwild.entity.render.animations.CustomWardenAnimations;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(WardenModel.class)
public abstract class WardenModelMixin<T extends Warden> implements WilderWardenModel {

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

    @Unique
    private List<ModelPart> headAndTendrils;

    @Unique
    private final WardenModel model = WardenModel.class.cast(this);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void WardenEntityModel(ModelPart root, CallbackInfo ci) {
        this.headAndTendrils = ImmutableList.of(this.head, this.leftTendril, this.rightTendril);
    }


    @Inject(at = @At("TAIL"), method = "animateTendrils")
    private void animateTendrils(T warden, float animationProgress, float tickDelta, CallbackInfo info) { //CUSTOM TENDRIL ANIMATION

        float cos = warden.getTendrilAnimation(tickDelta) * (float) (Math.cos((double) animationProgress * 2.25D) * 3.141592653589793D * 0.10000000149011612D);
        float sin = warden.getTendrilAnimation(tickDelta) * (float) (-Math.sin((double) animationProgress * 2.25D) * 3.141592653589793D * 0.12500000149011612D);

        if (ClothConfigInteractionHandler.wardenCustomTendrils()) {
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
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/monster/warden/Warden;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/WardenModel;animate(Lnet/minecraft/world/entity/AnimationState;Lnet/minecraft/client/animation/AnimationDefinition;F)V", ordinal = 0, shift = At.Shift.BEFORE))
    private void setupAnim(T wardenEntity, float angle, float distance, float anim, float headYaw, float headPitch, CallbackInfo ci) {
        boolean canSwim = !wardenEntity.hasPose(Pose.EMERGING) && !wardenEntity.hasPose(Pose.DIGGING) && !wardenEntity.hasPose(Pose.DYING) && !((WilderWarden) wardenEntity).getSwimmingDyingAnimationState().isStarted() && !((WilderWarden) wardenEntity).getKirbyDeathAnimationState().isStarted();
        boolean shouldMoveLimbs = !wardenEntity.hasPose(Pose.ROARING) && !wardenEntity.hasPose(Pose.EMERGING) && !wardenEntity.hasPose(Pose.DIGGING);
        if (ClothConfigInteractionHandler.wardenSwimAnimation() && isSubmerged(wardenEntity)) {
            this.animateSwimming(wardenEntity, angle, distance, anim, headYaw, headPitch, shouldMoveLimbs, canSwim);
        }
        model.animate(((WilderWarden) wardenEntity).getDyingAnimationState(), CustomWardenAnimations.DYING, anim);
        model.animate(((WilderWarden) wardenEntity).getSwimmingDyingAnimationState(), CustomWardenAnimations.WATER_DYING, anim);
        model.animate(((WilderWarden) wardenEntity).getKirbyDeathAnimationState(), CustomWardenAnimations.KIRBY_DEATH, anim);
    }

    @Unique
    private static final float rad = (float) (Math.PI / 180);

    private void animateSwimming(T warden, float angle, float distance, float anim, float headYaw, float headPitch, boolean moveLimbs, boolean canSwim) {

        float swimming = (warden.isVisuallySwimming() && distance > 0) ? 1 : 0;
        float notSwimming = (warden.isVisuallySwimming() && distance > 0) ? 0 : 1;

        float submerged = isSubmerged(warden) ? 1 : 0;
        float notSubmerged = isSubmerged(warden) ? 0 : 1;

        float lerpTime = anim - (float) warden.tickCount;

        float submergedLerp = Mth.rotLerp(warden.getSwimAmount(lerpTime), notSubmerged, submerged);
        float speedDelta = Math.min(distance / 0.3F, 1.0F) * submergedLerp;
        float swimLerp = Mth.rotLerp(warden.getSwimAmount(lerpTime), notSwimming, swimming) * speedDelta;

        if (((warden.isVisuallySwimming() && canSwim) || (swimLerp > 0)) && distance > 0) {

            //TODO: make swim animation last until lerp is done when exiting water. how.
            float angles = (float) (angle * (Math.PI * 0.2));

            float cos = (float) Math.cos(angles);
            float sin = (float) Math.sin(angles);

            float sin0 = (float) Math.sin(angles * 0.5F);
            float cos0 = (float) Math.cos(angles * 2.0F);

            this.bone.xRot = Mth.rotLerp(swimLerp, this.bone.xRot, (headPitch * 0.017453292F + 1.5708F));
            this.bone.yRot = Mth.rotLerp(swimLerp, this.bone.yRot, (headYaw * 0.017453292F));
            this.bone.y = Mth.lerp(swimLerp, this.bone.z, 21) + 3;

            this.leftLeg.xRot = Mth.rotLerp(swimLerp, this.leftLeg.xRot, ((-cos * 35 - 5) * rad));
            this.rightLeg.xRot = Mth.rotLerp(swimLerp, this.rightLeg.xRot, ((cos * 35 - 5) * rad));

            if (moveLimbs) {
                this.head.xRot = Mth.rotLerp(swimLerp, this.head.xRot, ((sin * -10 - 60) * rad));
                this.head.zRot = Mth.rotLerp(swimLerp, this.head.zRot, 0);
                this.head.yRot = Mth.rotLerp(swimLerp, this.head.yRot, 0);

                this.body.xRot = Mth.rotLerp(swimLerp, this.body.xRot, ((sin * 15 - 10) * rad));
                this.body.yRot = Mth.rotLerp(swimLerp, this.body.yRot, ((sin0 * 5) * rad));

                this.body.y = Mth.lerp(swimLerp, this.body.y + 21, 0);
                this.body.z = Mth.lerp(swimLerp, this.body.z, (cos * 2));

                this.rightArm.xRot = Mth.rotLerp(swimLerp, this.rightArm.xRot, 0f);
                this.rightArm.yRot = Mth.rotLerp(swimLerp, this.rightArm.yRot, ((-cos * 25) * rad));
                this.rightArm.zRot = Mth.rotLerp(swimLerp, this.rightArm.zRot, ((sin * -90 + 90) * rad));

                this.rightArm.x = Mth.lerp(swimLerp, this.rightArm.x, ((cos0 * 2 + 2) - 13));

                this.leftArm.xRot = Mth.rotLerp(swimLerp, this.leftArm.xRot, 0f);
                this.leftArm.yRot = Mth.rotLerp(swimLerp, this.leftArm.yRot, ((cos * 25) * rad));
                this.leftArm.zRot = Mth.rotLerp(swimLerp, this.leftArm.zRot, ((sin * 90 - 90) * rad));

                this.leftArm.x = Mth.lerp(swimLerp, this.leftArm.x, ((cos0 * -2 - 2) + 13));
            } else {
                this.body.y = 0;
            }

            this.rightLeg.y = 8;
            this.leftLeg.y = 8;

        } else if (this.isSubmerged(warden) && distance <= 0) {

            this.body.y = 0;

            model.root().getAllParts().forEach(ModelPart::resetPose);

        } else {
            float time = anim * 0.1F;

            this.bone.y += Math.cos(time);

            this.head.xRot += (Math.sin(time) * -5) * rad;

            this.body.xRot += ((Math.cos(time) * -5) * rad);

            this.leftArm.zRot += ((-Math.sin(time) * -5 - 5) * rad);
            this.rightArm.zRot += (-Math.sin(time) * 5 + 5) * rad;

            this.leftLeg.xRot += (Math.sin(time) * 15 + 15) * rad;
            this.rightLeg.xRot += (Math.sin(time) * -15 + 15) * rad;
        }
    }

    @Unique
    private boolean isSubmerged(Warden warden) {
        return warden.isInWaterOrBubble() || warden.isEyeInFluid(FluidTags.LAVA);
    }

    @Override
    public List<ModelPart> getHeadAndTendrils() {
        return this.headAndTendrils;
    }
}
