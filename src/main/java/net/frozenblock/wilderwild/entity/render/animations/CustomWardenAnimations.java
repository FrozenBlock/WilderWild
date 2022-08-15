package net.frozenblock.wilderwild.entity.render.animations;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Animation.Builder;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

/**
 * put custom warden animations in here. for example, death, flying, swimming. not for overrides
 */
@Environment(EnvType.CLIENT)
public final class CustomWardenAnimations {

    public static final Animation DYING = Builder.create(3.5F)
            .addBoneAnimation(
                    "bone",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                            new Keyframe(1.56F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                            new Keyframe(2.44F, AnimationHelper.rotate(-37.7838F, -14.24F, -17.6045F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "bone",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.translate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.36F, AnimationHelper.translate(0.0F, -100F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.92F, AnimationHelper.translate(0.0F, -190F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(3.48F, AnimationHelper.translate(0.0F, -500F, 0.0F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.56F, AnimationHelper.rotate(-12.59F, 0F, 6.67F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.2F, AnimationHelper.rotate(-42.5F, 0.0F, 22.5F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.translate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.36F, AnimationHelper.translate(5.7F, 0F, 4F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.44F, AnimationHelper.translate(12.0F, 0F, 11F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.44F, AnimationHelper.translate(0F, 0F, 0F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.52F, AnimationHelper.rotate(9.5459F, 2.9932F, -17.25F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.32F, AnimationHelper.rotate(-17.5093F, -0.434F, 2.4621F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.88F, AnimationHelper.rotate(37.3656F, -0.1965F, -5.1569F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.28F, AnimationHelper.rotate(37.3656F, -0.1965F, -5.1569F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.translate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.48F, AnimationHelper.rotate(-52.5F, -0F, 50F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.96F, AnimationHelper.rotate(-59.5544F, 33.4855F, 67.9672F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.68F, AnimationHelper.rotate(-73.7812F, -48.0134F, 72.9396F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.12F, AnimationHelper.rotate(-72.5422F, 17.6713F, -9.7739F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.translate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.48F, AnimationHelper.translate(1F, 0.0F, 2F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.12F, AnimationHelper.translate(2F, -1F, 1F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.68F, AnimationHelper.translate(2.66F, -0.22F, 0.34F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.12F, AnimationHelper.translate(2F, -1F, 3F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.2F, AnimationHelper.rotate(0, 0, -12.5F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.56F, AnimationHelper.rotate(-17.1773F, -40.7763F, -47.1799F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.0F, AnimationHelper.rotate(-24.0177F, 6.4316F, -116.2232F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.68F, AnimationHelper.rotate(-79.9936F, -13.4086F, -27.2842F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.08F, AnimationHelper.rotate(-80.2279F, -3.0828F, -21.3852F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.translate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.88F, AnimationHelper.translate(-3.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.16F, AnimationHelper.rotate(-20.2134F, 4.3897F, 7.6436F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.36F, AnimationHelper.rotate(-48.42F, 5.21F, -0.33F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.88F, AnimationHelper.rotate(-133.8547F, 12.8091F, -12.025F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.translate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.36F, AnimationHelper.translate(4.78F, 6F, 0.42F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.84F, AnimationHelper.translate(8.0F, 6F, 2F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.44F, AnimationHelper.translate(12F, 8F, 5F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.16F, AnimationHelper.rotate(1F, 6F, -1F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.36F, AnimationHelper.rotate(-12.4952F, -9.8466F, -5.9812F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.88F, AnimationHelper.rotate(-77.4825F, -0.8548F, 20.1506F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.52F, AnimationHelper.rotate(-107.48F, -0.85F, 20.15F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.translate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.08F, AnimationHelper.translate(10F, 0F, 6F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.44F, AnimationHelper.translate(0F, 3F, -6F), Transformation.Interpolations.SPLINE)
                    )
            )
            .build();

    public static final Animation WATER_DYING = Builder.create(3.5F)
            .addBoneAnimation(
                    "bone",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.36F, AnimationHelper.rotate(-47.5F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(3.4F, AnimationHelper.rotate(-47.5F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "bone",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.translate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.16F, AnimationHelper.translate(0.0F, 0.0F, 6.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.08F, AnimationHelper.translate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.72F, AnimationHelper.translate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.12F, AnimationHelper.rotate(25.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.44F, AnimationHelper.rotate(-15F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.48F, AnimationHelper.rotate(-27.4615F, -4.7235F, 10.7F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.translate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.44F, AnimationHelper.translate(0F, 0F, 0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.48F, AnimationHelper.translate(1F, -1F, 1F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.04F, AnimationHelper.rotate(-32.5F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.24F, AnimationHelper.rotate(27.5F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.68F, AnimationHelper.rotate(2.5F, 0.0F, 0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.6F, AnimationHelper.rotate(-30.0214F, 0.3262F, -7.4929F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.84F, AnimationHelper.rotate(-37.5379F, -0.434F, 9.9907F), Transformation.Interpolations.SPLINE),
                            new Keyframe(3.28F, AnimationHelper.rotate(-37.5379F, -0.434F, 9.9907F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.28F, AnimationHelper.rotate(-90.6636F, 9.917F, -1.8476F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.84F, AnimationHelper.rotate(-4.1947F, 21.3089F, 6.3256F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.44F, AnimationHelper.rotate(42.5707F, -3.6382F, 15.171F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.28F, AnimationHelper.rotate(49.4331F, -3.0042F, 15.7028F), Transformation.Interpolations.SPLINE),
                            new Keyframe(3.28F, AnimationHelper.rotate(79.4684F, 3.3016F, 9.7983F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.translate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.28F, AnimationHelper.translate(1F, 0F, -4F), Transformation.Interpolations.SPLINE),
                            new Keyframe(3.28F, AnimationHelper.translate(0F, -3F, -4F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.24F, AnimationHelper.rotate(-55.6563F, -5.082F, -1.6751F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.24F, AnimationHelper.rotate(47.9174F, 14.298F, -25.7058F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.28F, AnimationHelper.rotate(47.9174F, 14.298F, -25.7058F), Transformation.Interpolations.SPLINE),
                            new Keyframe(3.28F, AnimationHelper.rotate(76.9331F, 3.0042F, -15.7028F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.translate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.28F, AnimationHelper.translate(-2F, 0F, -4F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.2F, AnimationHelper.rotate(-80.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.72F, AnimationHelper.rotate(-42.5F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.24F, AnimationHelper.rotate(35F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.72F, AnimationHelper.rotate(35F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.44F, AnimationHelper.rotate(42.5F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.96F, AnimationHelper.rotate(35F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.08F, AnimationHelper.rotate(-80.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.36F, AnimationHelper.rotate(-42.5F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.84F, AnimationHelper.rotate(35F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.36F, AnimationHelper.rotate(35F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.4F, AnimationHelper.rotate(42.5F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.92F, AnimationHelper.rotate(35F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE)
                    )
            )
            .build();
    public static final Animation KIRBY_DEATH = Builder.create(3.5F)
            .addBoneAnimation(
                    "bone",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.translate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.16f, AnimationHelper.translate(0f, -1f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.28f, AnimationHelper.translate(0f, 32f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.68f, AnimationHelper.translate(0f, 29f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.92f, AnimationHelper.translate(0f, -12f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.04f, AnimationHelper.translate(0f, -12f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.2f, AnimationHelper.translate(0f, -12f, 0f), Transformation.Interpolations.LINEAR),
                            new Keyframe(1.32f, AnimationHelper.translate(0f, -12f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.08f, AnimationHelper.translate(0f, 50f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.96f, AnimationHelper.translate(0f, -500f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(3.28f, AnimationHelper.translate(0f, -500f, 0f), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "bone",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.rotate(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                            new Keyframe(1.32f, AnimationHelper.rotate(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                            new Keyframe(3.48f, AnimationHelper.rotate(0f, 0f, -2465f), Transformation.Interpolations.LINEAR)
                    )
            )
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0f, AnimationHelper.translate(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                            new Keyframe(1.32f, AnimationHelper.translate(0f, 0f, 0f), Transformation.Interpolations.LINEAR),
                            new Keyframe(1.48f, AnimationHelper.translate(0f, -21f, 0f), Transformation.Interpolations.LINEAR)
                    )
            )
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.rotate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.16f, AnimationHelper.rotate(35f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.32f, AnimationHelper.rotate(-7.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.92f, AnimationHelper.rotate(2.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.96f, AnimationHelper.rotate(-32.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.12f, AnimationHelper.rotate(0f, 0f, 0f), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.rotate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.2f, AnimationHelper.rotate(47.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.32f, AnimationHelper.rotate(-15f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.92f, AnimationHelper.rotate(-5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.96f, AnimationHelper.rotate(15f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.04f, AnimationHelper.rotate(-25f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.2f, AnimationHelper.rotate(35f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.32f, AnimationHelper.rotate(35f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.52f, AnimationHelper.rotate(0f, 0f, 0f), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.translate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.36f, AnimationHelper.translate(2f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.76f, AnimationHelper.translate(3f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.24f, AnimationHelper.translate(3f, 0f, 0f), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.rotate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.24f, AnimationHelper.rotate(-35.6253f, -25.29298f, -11.45438f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.36f, AnimationHelper.rotate(4.33362f, 22.13475f, 109.16203f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.4f, AnimationHelper.rotate(-19.18171f, 12.05536f, 38.76962f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.44f, AnimationHelper.rotate(10.48032f, 10.94952f, 120.74846f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.48f, AnimationHelper.rotate(-11.56478f, 9.79544f, 26.24686f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.56f, AnimationHelper.rotate(10.9453f, 10.48472f, 123.2485f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.68f, AnimationHelper.rotate(-8.64542f, 12.44278f, 41.29697f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.76f, AnimationHelper.rotate(4.18854f, 14.5334f, 92.77552f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.96f, AnimationHelper.rotate(-5.09172f, 14.2477f, 56.60443f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1f, AnimationHelper.rotate(-0.47662f, 15.10509f, 74.678f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.04f, AnimationHelper.rotate(-14.59871f, 12.24024f, 67.32805f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.12f, AnimationHelper.rotate(-28.94953f, 12.06189f, 63.50492f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.24f, AnimationHelper.rotate(-14.6f, 12.24f, 67.33f), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.translate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.36f, AnimationHelper.translate(-2f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.76f, AnimationHelper.translate(-3f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.24f, AnimationHelper.translate(-3f, 0f, 0f), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.rotate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.24f, AnimationHelper.rotate(-35.6253f, 25.29298f, 11.45438f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.36f, AnimationHelper.rotate(4.33362f, -22.13475f, -109.16203f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.4f, AnimationHelper.rotate(-19.18171f, -12.05536f, -38.76962f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.44f, AnimationHelper.rotate(10.48032f, -10.94952f, -120.74846f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.48f, AnimationHelper.rotate(-11.56478f, -9.79544f, -26.24686f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.56f, AnimationHelper.rotate(10.9453f, -10.48472f, -123.2485f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.68f, AnimationHelper.rotate(-8.64542f, -12.44278f, -41.29697f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.76f, AnimationHelper.rotate(4.18854f, -14.5334f, -92.77552f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.96f, AnimationHelper.rotate(-5.09172f, -14.2477f, -56.60443f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1f, AnimationHelper.rotate(-0.47662f, -15.10509f, -74.678f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.04f, AnimationHelper.rotate(15.20535f, -12.39428f, -63.49328f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.12f, AnimationHelper.rotate(-28.94953f, -12.06189f, -63.50492f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.24f, AnimationHelper.rotate(-14.6f, -12.24f, -67.33f), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.translate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.8f, AnimationHelper.translate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.88f, AnimationHelper.translate(-2f, 13f, -3f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.96f, AnimationHelper.translate(-2f, 8f, -8f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1f, AnimationHelper.translate(-2f, 2f, -5f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.2f, AnimationHelper.translate(-2f, 2f, -5f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.32f, AnimationHelper.translate(-2f, 2f, -5f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.48f, AnimationHelper.translate(-2f, -18f, -3f), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.rotate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.16f, AnimationHelper.rotate(12.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.24f, AnimationHelper.rotate(-22.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.28f, AnimationHelper.rotate(12.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.32f, AnimationHelper.rotate(-37.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.36f, AnimationHelper.rotate(42.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.4f, AnimationHelper.rotate(-27.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.44f, AnimationHelper.rotate(17.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.48f, AnimationHelper.rotate(-52.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.52f, AnimationHelper.rotate(32.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.64f, AnimationHelper.rotate(-20f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.68f, AnimationHelper.rotate(20f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.76f, AnimationHelper.rotate(2.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.84f, AnimationHelper.rotate(2.44081f, -0.54094f, 12.48848f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.04f, AnimationHelper.rotate(-92.48338f, 2.99703f, 16.02295f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.24f, AnimationHelper.rotate(-92.48f, 3f, 16.02f), Transformation.Interpolations.LINEAR)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.translate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.8f, AnimationHelper.translate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.96f, AnimationHelper.translate(0f, 10f, -3f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.04f, AnimationHelper.translate(0f, 8f, -10f), Transformation.Interpolations.LINEAR),
                            new Keyframe(1.08f, AnimationHelper.translate(0f, 2f, -6f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.28f, AnimationHelper.translate(0f, 2f, -6f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.36f, AnimationHelper.translate(0f, 2f, -6f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.48f, AnimationHelper.translate(0f, -18f, -3f), Transformation.Interpolations.SPLINE)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.rotate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.12f, AnimationHelper.rotate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.16f, AnimationHelper.rotate(10f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.2f, AnimationHelper.rotate(-5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.24f, AnimationHelper.rotate(45f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.28f, AnimationHelper.rotate(-35f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.32f, AnimationHelper.rotate(35f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.36f, AnimationHelper.rotate(-37.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.4f, AnimationHelper.rotate(25f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.44f, AnimationHelper.rotate(-40f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.48f, AnimationHelper.rotate(52.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.52f, AnimationHelper.rotate(-40f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.64f, AnimationHelper.rotate(37.5f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.76f, AnimationHelper.rotate(-15f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.84f, AnimationHelper.rotate(0f, 0f, 0f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1f, AnimationHelper.rotate(-33.43738f, -19.53723f, -9.46391f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.08f, AnimationHelper.rotate(-88.43738f, -19.53723f, -9.46391f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.12f, AnimationHelper.rotate(-100.93738f, -19.53723f, -9.46391f), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.32f, AnimationHelper.rotate(-100.94f, -19.54f, -9.46f), Transformation.Interpolations.SPLINE)
                    )
            )
            .build();
}
