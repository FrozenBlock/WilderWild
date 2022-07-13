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
public class CustomWardenAnimations {

    public static final Animation DYING = Builder.create(3.5F)
            .addBoneAnimation(
                    "bone",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37884),
                            new Keyframe(1.56F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37884),
                            new Keyframe(2.44F, AnimationHelper.method_41823(-37.7838F, -14.24F, -17.6045F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "bone",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.36F, AnimationHelper.method_41823(0.0F, -100F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.92F, AnimationHelper.method_41823(0.0F, -190F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.48F, AnimationHelper.method_41823(0.0F, -500F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.56F, AnimationHelper.method_41829(-12.59F, 0F, 6.67F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.2F, AnimationHelper.method_41829(-42.5F, 0.0F, 22.5F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.36F, AnimationHelper.method_41823(5.7F, 0F, 4F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.44F, AnimationHelper.method_41823(12.0F, 0F, 11F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.44F, AnimationHelper.method_41823(0F, 0F, 0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41829(9.5459F, 2.9932F, -17.25F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.32F, AnimationHelper.method_41829(-17.5093F, -0.434F, 2.4621F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.88F, AnimationHelper.method_41829(37.3656F, -0.1965F, -5.1569F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41829(37.3656F, -0.1965F, -5.1569F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.48F, AnimationHelper.method_41829(-52.5F, -0F, 50F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.96F, AnimationHelper.method_41829(-59.5544F, 33.4855F, 67.9672F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41829(-73.7812F, -48.0134F, 72.9396F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.12F, AnimationHelper.method_41829(-72.5422F, 17.6713F, -9.7739F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.48F, AnimationHelper.method_41823(1F, 0.0F, 2F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.12F, AnimationHelper.method_41823(2F, -1F, 1F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41823(2.66F, -0.22F, 0.34F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.12F, AnimationHelper.method_41823(2F, -1F, 3F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.2F, AnimationHelper.method_41829(0, 0, -12.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.56F, AnimationHelper.method_41829(-17.1773F, -40.7763F, -47.1799F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.0F, AnimationHelper.method_41829(-24.0177F, 6.4316F, -116.2232F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41829(-79.9936F, -13.4086F, -27.2842F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.08F, AnimationHelper.method_41829(-80.2279F, -3.0828F, -21.3852F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.88F, AnimationHelper.method_41823(-3.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.16F, AnimationHelper.method_41829(-20.2134F, 4.3897F, 7.6436F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.36F, AnimationHelper.method_41829(-48.42F, 5.21F, -0.33F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.88F, AnimationHelper.method_41829(-133.8547F, 12.8091F, -12.025F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.36F, AnimationHelper.method_41823(4.78F, 6F, 0.42F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.84F, AnimationHelper.method_41823(8.0F, 6F, 2F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.44F, AnimationHelper.method_41823(12F, 8F, 5F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.16F, AnimationHelper.method_41829(1F, 6F, -1F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.36F, AnimationHelper.method_41829(-12.4952F, -9.8466F, -5.9812F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.88F, AnimationHelper.method_41829(-77.4825F, -0.8548F, 20.1506F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.52F, AnimationHelper.method_41829(-107.48F, -0.85F, 20.15F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.08F, AnimationHelper.method_41823(10F, 0F, 6F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.44F, AnimationHelper.method_41823(0F, 3F, -6F), Transformation.Interpolations.field_37885)
                    )
            )
            .build();

    static float rad = (float) (Math.PI / 180);

    public static final Animation WATER_DYING = Builder.create(3.5F)
            .addBoneAnimation(
                    "bone",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.36F, AnimationHelper.method_41823(-47.5F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.4F, AnimationHelper.method_41823(-47.5F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "bone",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.16F, AnimationHelper.method_41829(0.0F, 0.0F, 6.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.08F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.72F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.12F, AnimationHelper.method_41823(25.0F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.44F, AnimationHelper.method_41823(-15F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.48F, AnimationHelper.method_41823(-27.4615F * rad, -4.7235F * rad, 10.7F * rad), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.44F, AnimationHelper.method_41823(0F, 0F, 0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.48F, AnimationHelper.method_41823(1F, -1F, 1F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.04F, AnimationHelper.method_41823(-32.5F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.24F, AnimationHelper.method_41823(27.5F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.68F, AnimationHelper.method_41823(2.5F * rad, 0.0F, 0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.6F, AnimationHelper.method_41823(-30.0214F * rad, 0.3262F * rad, -7.4929F * rad), Transformation.Interpolations.field_37885),
                            new Keyframe(2.84F, AnimationHelper.method_41823(-37.5379F * rad, -0.434F * rad, 9.9907F * rad), Transformation.Interpolations.field_37885),
                            new Keyframe(3.28F, AnimationHelper.method_41823(-37.5379F * rad, -0.434F * rad, 9.9907F * rad), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.28F, AnimationHelper.method_41823(-90.6636F * rad, 9.917F * rad, -1.8476F * rad), Transformation.Interpolations.field_37885),
                            new Keyframe(0.84F, AnimationHelper.method_41823(-4.1947F * rad, 21.3089F * rad, 6.3256F * rad), Transformation.Interpolations.field_37885),
                            new Keyframe(1.44F, AnimationHelper.method_41823(42.5707F * rad, -3.6382F * rad, 15.171F * rad), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41823(49.4331F * rad, -3.0042F * rad, 15.7028F * rad), Transformation.Interpolations.field_37885),
                            new Keyframe(3.28F, AnimationHelper.method_41823(79.4684F * rad, 3.3016F * rad, 9.7983F * rad), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41823(1F, 0F, -4F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.28F, AnimationHelper.method_41823(0F, -3F, -4F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.24F, AnimationHelper.method_41823(-55.6563F * rad, -5.082F * rad, -1.6751F * rad), Transformation.Interpolations.field_37885),
                            new Keyframe(1.24F, AnimationHelper.method_41823(47.9174F * rad, 14.298F * rad, -25.7058F * rad), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41823(47.9174F * rad, 14.298F * rad, -25.7058F * rad), Transformation.Interpolations.field_37885),
                            new Keyframe(3.28F, AnimationHelper.method_41823(76.9331F * rad, 3.0042F * rad, -15.7028F * rad), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41823(-2F, 0F, -4F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.2F, AnimationHelper.method_41823(-80.0F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.72F, AnimationHelper.method_41823(-42.5F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.24F, AnimationHelper.method_41823(35F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.72F, AnimationHelper.method_41823(35F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.44F, AnimationHelper.method_41823(42.5F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.96F, AnimationHelper.method_41823(35F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.08F, AnimationHelper.method_41823(-80.0F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.36F, AnimationHelper.method_41823(-42.5F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.84F, AnimationHelper.method_41823(35F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.36F, AnimationHelper.method_41823(35F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.4F, AnimationHelper.method_41823(42.5F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.92F, AnimationHelper.method_41823(35F * rad, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .build();

}
