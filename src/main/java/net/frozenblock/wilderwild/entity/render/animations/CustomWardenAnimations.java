package net.frozenblock.wilderwild.entity.render.animations;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.AnimationDefinition.Builder;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * put custom warden animations in here. for example, death, flying, swimming. not for overrides
 */
@Environment(EnvType.CLIENT)
public final class CustomWardenAnimations {

    public static final AnimationDefinition DYING = Builder.withLength(3.5F)
            .addAnimation(
                    "bone",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.56F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.44F, KeyframeAnimations.degreeVec(-37.7838F, -14.24F, -17.6045F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "bone",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.36F, KeyframeAnimations.posVec(0.0F, -100F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.92F, KeyframeAnimations.posVec(0.0F, -190F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.48F, KeyframeAnimations.posVec(0.0F, -500F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "body",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.56F, KeyframeAnimations.degreeVec(-12.59F, 0F, 6.67F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.2F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 22.5F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "body",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.36F, KeyframeAnimations.posVec(5.7F, 0F, 4F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.44F, KeyframeAnimations.posVec(12.0F, 0F, 11F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.44F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "head",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.52F, KeyframeAnimations.degreeVec(9.5459F, 2.9932F, -17.25F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.32F, KeyframeAnimations.degreeVec(-17.5093F, -0.434F, 2.4621F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.88F, KeyframeAnimations.degreeVec(37.3656F, -0.1965F, -5.1569F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.28F, KeyframeAnimations.degreeVec(37.3656F, -0.1965F, -5.1569F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "head",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "right_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.48F, KeyframeAnimations.degreeVec(-52.5F, -0F, 50F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.96F, KeyframeAnimations.degreeVec(-59.5544F, 33.4855F, 67.9672F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.68F, KeyframeAnimations.degreeVec(-73.7812F, -48.0134F, 72.9396F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.12F, KeyframeAnimations.degreeVec(-72.5422F, 17.6713F, -9.7739F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "right_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.48F, KeyframeAnimations.posVec(1F, 0.0F, 2F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.12F, KeyframeAnimations.posVec(2F, -1F, 1F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.68F, KeyframeAnimations.posVec(2.66F, -0.22F, 0.34F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.12F, KeyframeAnimations.posVec(2F, -1F, 3F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "left_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.2F, KeyframeAnimations.degreeVec(0, 0, -12.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.56F, KeyframeAnimations.degreeVec(-17.1773F, -40.7763F, -47.1799F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-24.0177F, 6.4316F, -116.2232F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.68F, KeyframeAnimations.degreeVec(-79.9936F, -13.4086F, -27.2842F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.08F, KeyframeAnimations.degreeVec(-80.2279F, -3.0828F, -21.3852F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "left_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.88F, KeyframeAnimations.posVec(-3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "right_leg",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.16F, KeyframeAnimations.degreeVec(-20.2134F, 4.3897F, 7.6436F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.36F, KeyframeAnimations.degreeVec(-48.42F, 5.21F, -0.33F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.88F, KeyframeAnimations.degreeVec(-133.8547F, 12.8091F, -12.025F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "right_leg",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.36F, KeyframeAnimations.posVec(4.78F, 6F, 0.42F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.84F, KeyframeAnimations.posVec(8.0F, 6F, 2F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.44F, KeyframeAnimations.posVec(12F, 8F, 5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.16F, KeyframeAnimations.degreeVec(1F, 6F, -1F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "left_leg",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.36F, KeyframeAnimations.degreeVec(-12.4952F, -9.8466F, -5.9812F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.88F, KeyframeAnimations.degreeVec(-77.4825F, -0.8548F, 20.1506F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.52F, KeyframeAnimations.degreeVec(-107.48F, -0.85F, 20.15F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "left_leg",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.08F, KeyframeAnimations.posVec(10F, 0F, 6F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.44F, KeyframeAnimations.posVec(0F, 3F, -6F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .build();

    public static final AnimationDefinition WATER_DYING = Builder.withLength(3.5F)
            .addAnimation(
                    "bone",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.36F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.4F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "bone",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.16F, KeyframeAnimations.posVec(0.0F, 0.0F, 6.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.08F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.72F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "body",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.12F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.44F, KeyframeAnimations.degreeVec(-15F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.48F, KeyframeAnimations.degreeVec(-27.4615F, -4.7235F, 10.7F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "body",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.44F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.48F, KeyframeAnimations.posVec(1F, -1F, 1F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "head",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.04F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.24F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.68F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.6F, KeyframeAnimations.degreeVec(-30.0214F, 0.3262F, -7.4929F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.84F, KeyframeAnimations.degreeVec(-37.5379F, -0.434F, 9.9907F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.28F, KeyframeAnimations.degreeVec(-37.5379F, -0.434F, 9.9907F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "right_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.28F, KeyframeAnimations.degreeVec(-90.6636F, 9.917F, -1.8476F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.84F, KeyframeAnimations.degreeVec(-4.1947F, 21.3089F, 6.3256F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.44F, KeyframeAnimations.degreeVec(42.5707F, -3.6382F, 15.171F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.28F, KeyframeAnimations.degreeVec(49.4331F, -3.0042F, 15.7028F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.28F, KeyframeAnimations.degreeVec(79.4684F, 3.3016F, 9.7983F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "right_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.28F, KeyframeAnimations.posVec(1F, 0F, -4F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.28F, KeyframeAnimations.posVec(0F, -3F, -4F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "left_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.24F, KeyframeAnimations.degreeVec(-55.6563F, -5.082F, -1.6751F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.24F, KeyframeAnimations.degreeVec(47.9174F, 14.298F, -25.7058F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.28F, KeyframeAnimations.degreeVec(47.9174F, 14.298F, -25.7058F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.28F, KeyframeAnimations.degreeVec(76.9331F, 3.0042F, -15.7028F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "left_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.28F, KeyframeAnimations.posVec(-2F, 0F, -4F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "right_leg",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.2F, KeyframeAnimations.degreeVec(-80.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.72F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.24F, KeyframeAnimations.degreeVec(35F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.72F, KeyframeAnimations.degreeVec(35F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.44F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.96F, KeyframeAnimations.degreeVec(35F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "left_leg",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.08F, KeyframeAnimations.degreeVec(-80.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.36F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.84F, KeyframeAnimations.degreeVec(35F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.36F, KeyframeAnimations.degreeVec(35F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.4F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.92F, KeyframeAnimations.degreeVec(35F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .build();
    public static final AnimationDefinition KIRBY_DEATH = Builder.withLength(3.5F)
            .addAnimation(
                    "bone",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.16f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.28f, KeyframeAnimations.posVec(0f, 32f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.68f, KeyframeAnimations.posVec(0f, 29f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.92f, KeyframeAnimations.posVec(0f, -12f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.04f, KeyframeAnimations.posVec(0f, -12f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.2f, KeyframeAnimations.posVec(0f, -12f, 0f), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.32f, KeyframeAnimations.posVec(0f, -12f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.08f, KeyframeAnimations.posVec(0f, 50f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.96f, KeyframeAnimations.posVec(0f, -500f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.28f, KeyframeAnimations.posVec(0f, -500f, 0f), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "bone",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.32f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.48f, KeyframeAnimations.degreeVec(0f, 0f, -2465f), AnimationChannel.Interpolations.LINEAR)
                    )
            )
            .addAnimation(
                    "body",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.32f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.48f, KeyframeAnimations.posVec(0f, -21f, 0f), AnimationChannel.Interpolations.LINEAR)
                    )
            )
            .addAnimation(
                    "body",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.16f, KeyframeAnimations.degreeVec(35f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.32f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.92f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.96f, KeyframeAnimations.degreeVec(-32.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.12f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "head",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(47.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.32f, KeyframeAnimations.degreeVec(-15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.92f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.96f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.04f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.2f, KeyframeAnimations.degreeVec(35f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.32f, KeyframeAnimations.degreeVec(35f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.52f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "right_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.36f, KeyframeAnimations.posVec(2f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.76f, KeyframeAnimations.posVec(3f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.24f, KeyframeAnimations.posVec(3f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "right_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.24f, KeyframeAnimations.degreeVec(-35.6253f, -25.29298f, -11.45438f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.36f, KeyframeAnimations.degreeVec(4.33362f, 22.13475f, 109.16203f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4f, KeyframeAnimations.degreeVec(-19.18171f, 12.05536f, 38.76962f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.44f, KeyframeAnimations.degreeVec(10.48032f, 10.94952f, 120.74846f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.48f, KeyframeAnimations.degreeVec(-11.56478f, 9.79544f, 26.24686f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.56f, KeyframeAnimations.degreeVec(10.9453f, 10.48472f, 123.2485f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.68f, KeyframeAnimations.degreeVec(-8.64542f, 12.44278f, 41.29697f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.76f, KeyframeAnimations.degreeVec(4.18854f, 14.5334f, 92.77552f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.96f, KeyframeAnimations.degreeVec(-5.09172f, 14.2477f, 56.60443f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(-0.47662f, 15.10509f, 74.678f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.04f, KeyframeAnimations.degreeVec(-14.59871f, 12.24024f, 67.32805f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.12f, KeyframeAnimations.degreeVec(-28.94953f, 12.06189f, 63.50492f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.24f, KeyframeAnimations.degreeVec(-14.6f, 12.24f, 67.33f), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "left_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.36f, KeyframeAnimations.posVec(-2f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.76f, KeyframeAnimations.posVec(-3f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.24f, KeyframeAnimations.posVec(-3f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "left_arm",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.24f, KeyframeAnimations.degreeVec(-35.6253f, 25.29298f, 11.45438f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.36f, KeyframeAnimations.degreeVec(4.33362f, -22.13475f, -109.16203f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4f, KeyframeAnimations.degreeVec(-19.18171f, -12.05536f, -38.76962f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.44f, KeyframeAnimations.degreeVec(10.48032f, -10.94952f, -120.74846f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.48f, KeyframeAnimations.degreeVec(-11.56478f, -9.79544f, -26.24686f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.56f, KeyframeAnimations.degreeVec(10.9453f, -10.48472f, -123.2485f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.68f, KeyframeAnimations.degreeVec(-8.64542f, -12.44278f, -41.29697f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.76f, KeyframeAnimations.degreeVec(4.18854f, -14.5334f, -92.77552f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.96f, KeyframeAnimations.degreeVec(-5.09172f, -14.2477f, -56.60443f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(-0.47662f, -15.10509f, -74.678f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.04f, KeyframeAnimations.degreeVec(15.20535f, -12.39428f, -63.49328f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.12f, KeyframeAnimations.degreeVec(-28.94953f, -12.06189f, -63.50492f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.24f, KeyframeAnimations.degreeVec(-14.6f, -12.24f, -67.33f), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "right_leg",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.8f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.88f, KeyframeAnimations.posVec(-2f, 13f, -3f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.96f, KeyframeAnimations.posVec(-2f, 8f, -8f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.posVec(-2f, 2f, -5f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.2f, KeyframeAnimations.posVec(-2f, 2f, -5f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.32f, KeyframeAnimations.posVec(-2f, 2f, -5f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.48f, KeyframeAnimations.posVec(-2f, -18f, -3f), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "right_leg",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.16f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.24f, KeyframeAnimations.degreeVec(-22.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.28f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.32f, KeyframeAnimations.degreeVec(-37.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.36f, KeyframeAnimations.degreeVec(42.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.44f, KeyframeAnimations.degreeVec(17.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.48f, KeyframeAnimations.degreeVec(-52.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.52f, KeyframeAnimations.degreeVec(32.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.64f, KeyframeAnimations.degreeVec(-20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.68f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.76f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.84f, KeyframeAnimations.degreeVec(2.44081f, -0.54094f, 12.48848f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.04f, KeyframeAnimations.degreeVec(-92.48338f, 2.99703f, 16.02295f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.24f, KeyframeAnimations.degreeVec(-92.48f, 3f, 16.02f), AnimationChannel.Interpolations.LINEAR)
                    )
            )
            .addAnimation(
                    "left_leg",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.8f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.96f, KeyframeAnimations.posVec(0f, 10f, -3f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.04f, KeyframeAnimations.posVec(0f, 8f, -10f), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.08f, KeyframeAnimations.posVec(0f, 2f, -6f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.28f, KeyframeAnimations.posVec(0f, 2f, -6f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.36f, KeyframeAnimations.posVec(0f, 2f, -6f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.48f, KeyframeAnimations.posVec(0f, -18f, -3f), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .addAnimation(
                    "left_leg",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.12f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.16f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.24f, KeyframeAnimations.degreeVec(45f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.28f, KeyframeAnimations.degreeVec(-35f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.32f, KeyframeAnimations.degreeVec(35f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.36f, KeyframeAnimations.degreeVec(-37.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4f, KeyframeAnimations.degreeVec(25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.44f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.48f, KeyframeAnimations.degreeVec(52.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.52f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.64f, KeyframeAnimations.degreeVec(37.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.76f, KeyframeAnimations.degreeVec(-15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.84f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(-33.43738f, -19.53723f, -9.46391f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.08f, KeyframeAnimations.degreeVec(-88.43738f, -19.53723f, -9.46391f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.12f, KeyframeAnimations.degreeVec(-100.93738f, -19.53723f, -9.46391f), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.32f, KeyframeAnimations.degreeVec(-100.94f, -19.54f, -9.46f), AnimationChannel.Interpolations.CATMULLROM)
                    )
            )
            .build();
}
