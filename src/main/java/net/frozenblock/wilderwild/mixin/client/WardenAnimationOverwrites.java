package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.definitions.WardenAnimation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(value = WardenAnimation.class, priority = 999)
public class WardenAnimationOverwrites {

    @Final
    @Mutable
    @Shadow
    public static final AnimationDefinition WARDEN_SNIFF = AnimationDefinition.Builder.withLength(4.16F)
            .addAnimation(
                    "body",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.56F, KeyframeAnimations.degreeVec(17.5F, 32.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.96F, KeyframeAnimations.degreeVec(0.0F, 32.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.2F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.8F, KeyframeAnimations.degreeVec(10.0F, -30.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.32F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM))
            )
            .addAnimation(
                    "head",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.68F, KeyframeAnimations.degreeVec(0.0F, 40.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.96F, KeyframeAnimations.degreeVec(-22.5F, 40.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.24F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.52F, KeyframeAnimations.degreeVec(-35.0F, 20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.76F, KeyframeAnimations.degreeVec(0.0F, 20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.28F, KeyframeAnimations.degreeVec(0.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.6F, KeyframeAnimations.degreeVec(-25F, -23.6504F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.88F, KeyframeAnimations.degreeVec(0.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.32F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation(
                    "right_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.96F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.76F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.32F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation(
                    "left_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.96F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.76F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.32F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
            .build();

}
