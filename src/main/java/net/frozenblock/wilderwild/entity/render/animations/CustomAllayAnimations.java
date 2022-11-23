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

    public static final AnimationDefinition DANCING = AnimationDefinition.Builder.withLength(2.75F).looping()
            .addAnimation(
                    "head",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0F, -30F, -30F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5833F, KeyframeAnimations.degreeVec(0F, 30F, 30F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.2917F, KeyframeAnimations.degreeVec(0.63F, 540F, 0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.75F, KeyframeAnimations.degreeVec(0F, 720F, 0F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "body", new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4167F, KeyframeAnimations.degreeVec(10F, -16F, -16F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5833F, KeyframeAnimations.degreeVec(-10F, 16F, 16F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.2917F, KeyframeAnimations.degreeVec(0.63F, 540F, 0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.75F, KeyframeAnimations.degreeVec(0F, 720F, 0F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "left_wing",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(2F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.1667F, KeyframeAnimations.degreeVec(45F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5833F, KeyframeAnimations.degreeVec(45F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.75F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    )
            )
            .addAnimation(
                    "right_wing",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(2F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.1667F, KeyframeAnimations.degreeVec(45F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5833F, KeyframeAnimations.degreeVec(45F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.75F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    )
            )
            .build();

}
