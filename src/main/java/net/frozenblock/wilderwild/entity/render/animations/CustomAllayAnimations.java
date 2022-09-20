package net.frozenblock.wilderwild.entity.render.animations;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * put custom allay animations in here. for example, dancing, swimming. not for overrides
 */
@Environment(EnvType.CLIENT)
public final class CustomAllayAnimations {

    public static final AnimationDefinition DANCING = AnimationDefinition.Builder.withLength(2.75f).looping()
            .addAnimation(
                    "head",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4167f, KeyframeAnimations.degreeVec(0f, -30f, -30f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5833f, KeyframeAnimations.degreeVec(0f, 30f, 30f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.2917f, KeyframeAnimations.degreeVec(0.63f, 540f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.75f, KeyframeAnimations.degreeVec(0f, 720f, 0f), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "body", new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4167f, KeyframeAnimations.degreeVec(10f, -16f, -16f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5833f, KeyframeAnimations.degreeVec(-10f, 16f, 16f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.2917f, KeyframeAnimations.degreeVec(0.63f, 540f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.75f, KeyframeAnimations.degreeVec(0f, 720f, 0f), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "left_wing",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.1667f, KeyframeAnimations.degreeVec(45f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5833f, KeyframeAnimations.degreeVec(45f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
                    )
            )
            .addAnimation(
                    "right_wing",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.1667f, KeyframeAnimations.degreeVec(45f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5833f, KeyframeAnimations.degreeVec(45f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
                    )
            )
            .build();

}
