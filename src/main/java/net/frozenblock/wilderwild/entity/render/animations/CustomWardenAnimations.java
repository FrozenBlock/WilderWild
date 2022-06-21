package net.frozenblock.wilderwild.entity.render.animations;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

/**
 * put custom warden animations in here. for example, death, flying, swimming. not for overrides
 */
@Environment(EnvType.CLIENT)
public class CustomWardenAnimations {

    public static final Animation DYING = Animation.Builder.create(5.0F)
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.25F, AnimationHelper.method_41829(4.13441F, 0.94736F, 1.2694F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.5F, AnimationHelper.method_41829(50.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.7083F, AnimationHelper.method_41829(54.45407F, -13.53935F, -18.14183F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.0417F, AnimationHelper.method_41829(59.46442F, -10.8885F, 35.7954F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.3333F, AnimationHelper.method_41829(82.28261F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.625F, AnimationHelper.method_41829(53.23606F, 10.04715F, -29.72932F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.2083F, AnimationHelper.method_41829(-17.71739F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.5417F, AnimationHelper.method_41829(112.28261F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.6667F, AnimationHelper.method_41829(116.06889F, 5.11581F, -24.50117F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.8333F, AnimationHelper.method_41829(121.56244F, -4.17248F, 19.57737F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.0417F, AnimationHelper.method_41829(138.5689F, 5.11581F, -24.50117F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.25F, AnimationHelper.method_41829(144.06244F, -4.17248F, 19.57737F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.375F, AnimationHelper.method_41829(147.28261F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.625F, AnimationHelper.method_41829(147.28261F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.875F, AnimationHelper.method_41829(134.36221F, 8.81113F, -8.90172F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.0417F, AnimationHelper.method_41829(132.05966F, -8.35927F, 9.70506F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.25F, AnimationHelper.method_41829(134.36221F, 8.81113F, -8.90172F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.5F, AnimationHelper.method_41829(147.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37884)
                    )
            )
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.5F, AnimationHelper.method_41823(0.0F, -16.48454F, -6.5784F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.7083F, AnimationHelper.method_41823(0.0F, -16.48454F, -6.5784F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.0417F, AnimationHelper.method_41823(0.0F, -16.97F, -7.11F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.625F, AnimationHelper.method_41823(0.0F, -13.97F, -7.11F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.2083F, AnimationHelper.method_41823(0.0F, -11.48454F, -0.5784F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.5417F, AnimationHelper.method_41823(0.0F, -16.48454F, -6.5784F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.6667F, AnimationHelper.method_41823(0.0F, -20.27F, -5.42F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.375F, AnimationHelper.method_41823(0.0F, -21.48454F, -5.5784F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.0417F, AnimationHelper.method_41823(0.0F, -22.48454F, -5.5784F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.5F, AnimationHelper.method_41823(0.0F, -40.0F, -8.0F), Transformation.Interpolations.field_37884)
                    )
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.6667F, AnimationHelper.method_41829(12.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.2083F, AnimationHelper.method_41829(12.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.75F, AnimationHelper.method_41829(45.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.375F, AnimationHelper.method_41829(-22.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.5417F, AnimationHelper.method_41829(67.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.375F, AnimationHelper.method_41829(67.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.375F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37884)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.5F, AnimationHelper.method_41829(-101.8036F, -21.29587F, 30.61478F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.7083F, AnimationHelper.method_41829(-101.8036F, -21.29587F, 30.61478F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.0F, AnimationHelper.method_41829(48.7585F, -17.61941F, 9.9865F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.1667F, AnimationHelper.method_41829(48.7585F, -17.61941F, 9.9865F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.4583F, AnimationHelper.method_41829(-101.8036F, -21.29587F, 30.61478F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.75F, AnimationHelper.method_41829(-89.04994F, -4.19657F, -1.47845F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.2083F, AnimationHelper.method_41829(-158.30728F, 3.7152F, -1.52352F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.5417F, AnimationHelper.method_41829(-89.04994F, -4.19657F, -1.47845F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.375F, AnimationHelper.method_41829(-120.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.7083F, AnimationHelper.method_41823(2.22F, 0.0F, 0.86F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.0F, AnimationHelper.method_41823(3.12F, 0.0F, 4.29F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.2083F, AnimationHelper.method_41823(1.0F, 0.0F, 4.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.375F, AnimationHelper.method_41823(0.0F, 0.0F, 4.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.2917F, AnimationHelper.method_41829(-63.89288F, -0.52011F, 2.09491F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.5F, AnimationHelper.method_41829(-63.89288F, -0.52011F, 2.09491F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.7083F, AnimationHelper.method_41829(-62.87857F, 15.15061F, 9.97445F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.9167F, AnimationHelper.method_41829(-86.93642F, 17.45026F, 4.05284F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.1667F, AnimationHelper.method_41829(-86.93642F, 17.45026F, 4.05284F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.4583F, AnimationHelper.method_41829(-86.93642F, 17.45026F, 4.05284F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.6667F, AnimationHelper.method_41829(63.0984F, 8.83573F, -8.71284F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.8333F, AnimationHelper.method_41829(35.5984F, 8.83573F, -8.71284F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.2083F, AnimationHelper.method_41829(-153.27473F, -0.02953F, 3.5235F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.5417F, AnimationHelper.method_41829(-87.07754F, -0.02625F, 3.132F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.375F, AnimationHelper.method_41829(-120.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37884)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.5F, AnimationHelper.method_41823(-0.28F, 5.0F, 10.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.7083F, AnimationHelper.method_41823(-1.51F, 4.35F, 4.33F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.9167F, AnimationHelper.method_41823(-0.6F, 3.61F, 4.63F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.1667F, AnimationHelper.method_41823(-0.6F, 3.61F, 0.63F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.6667F, AnimationHelper.method_41823(-2.85F, -0.1F, 3.33F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.2083F, AnimationHelper.method_41823(-1.0F, 0.0F, 4.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.375F, AnimationHelper.method_41823(0.0F, 0.0F, 4.0F), Transformation.Interpolations.field_37884)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.5F, AnimationHelper.method_41829(113.27F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.7083F, AnimationHelper.method_41829(113.27F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.3333F, AnimationHelper.method_41829(113.27F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.5833F, AnimationHelper.method_41829(182.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.8333F, AnimationHelper.method_41829(120.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.0833F, AnimationHelper.method_41829(182.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.2917F, AnimationHelper.method_41829(120.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.5F, AnimationHelper.method_41829(90.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37884)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.5F, AnimationHelper.method_41823(0.0F, -13.98F, -2.37F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.7083F, AnimationHelper.method_41823(0.0F, -13.98F, -2.37F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.3333F, AnimationHelper.method_41823(0.0F, -13.98F, -2.37F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.5833F, AnimationHelper.method_41823(0.0F, -7.0F, -3.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.8333F, AnimationHelper.method_41823(0.0F, -9.0F, -3.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.0833F, AnimationHelper.method_41823(0.0F, -16.71F, -3.69F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.2917F, AnimationHelper.method_41823(0.0F, -28.0F, -5.0F), Transformation.Interpolations.field_37884)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.5F, AnimationHelper.method_41829(114.98F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.7083F, AnimationHelper.method_41829(114.98F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.3333F, AnimationHelper.method_41829(114.98F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.5833F, AnimationHelper.method_41829(90.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.8333F, AnimationHelper.method_41829(172.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.0833F, AnimationHelper.method_41829(90.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.2917F, AnimationHelper.method_41829(197.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.5F, AnimationHelper.method_41829(90.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37884)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.5F, AnimationHelper.method_41823(0.0F, -14.01F, -2.35F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.7083F, AnimationHelper.method_41823(0.0F, -14.01F, -2.35F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.3333F, AnimationHelper.method_41823(0.0F, -14.01F, -2.35F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.5833F, AnimationHelper.method_41823(0.0F, -5.0F, -4.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.8333F, AnimationHelper.method_41823(0.0F, -7.0F, -4.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.0833F, AnimationHelper.method_41823(0.0F, -15.5F, -3.76F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.2917F, AnimationHelper.method_41823(0.0F, -28.0F, -5.0F), Transformation.Interpolations.field_37884)
                    )
            )
            .build();
}
