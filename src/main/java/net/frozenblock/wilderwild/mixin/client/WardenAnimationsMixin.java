package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.render.animations.CustomWardenAnimations;
import net.minecraft.client.render.entity.animation.*;
import net.minecraft.entity.AnimationState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(WardenAnimations.class)
public class WardenAnimationsMixin {

    @Final
    @Mutable
    @Shadow
    public static final Animation EMERGING = Animation.Builder.create(6.68F)
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41829(0.0F, 0.0F, -22.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.2F, AnimationHelper.method_41829(0.0F, 0.0F, -7.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41829(0.0F, 0.0F, 10.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.8F, AnimationHelper.method_41829(0.0F, 0.0F, 10.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41829(0.0F, 0.0F, 10.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41829(0.0F, 0.0F, 10.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.76F, AnimationHelper.method_41829(25.0F, 0.0F, -7.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.92F, AnimationHelper.method_41829(35.0F, 0.0F, -7.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.08F, AnimationHelper.method_41829(25.0F, 0.0F, -7.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.44F, AnimationHelper.method_41829(47.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.56F, AnimationHelper.method_41829(47.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.68F, AnimationHelper.method_41829(47.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41829(70.0F, 0.0F, 2.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.8F, AnimationHelper.method_41829(70.0F, 0.0F, 2.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, -63.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41823(0.0F, -56.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.2F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.8F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.16F, AnimationHelper.method_41823(0.0F, -27.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.76F, AnimationHelper.method_41823(0.0F, -14.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.92F, AnimationHelper.method_41823(0.0F, -11.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.08F, AnimationHelper.method_41823(0.0F, -14.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.44F, AnimationHelper.method_41823(0.0F, -6.0F, -3.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.56F, AnimationHelper.method_41823(0.0F, -4.0F, -3.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.68F, AnimationHelper.method_41823(0.0F, -6.0F, -3.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41823(0.0F, -3.0F, -4.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.8F, AnimationHelper.method_41823(0.0F, -3.0F, -4.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.92F, AnimationHelper.method_41829(0.74F, 0.0F, -40.38F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.16F, AnimationHelper.method_41829(-67.5F, 0.0F, -2.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.24F, AnimationHelper.method_41829(-67.5F, 0.0F, -2.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.32F, AnimationHelper.method_41829(-47.5F, 0.0F, -2.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.4F, AnimationHelper.method_41829(-67.5F, 0.0F, -2.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41829(-67.5F, 0.0F, 15.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.76F, AnimationHelper.method_41829(-67.5F, 0.0F, -5.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.84F, AnimationHelper.method_41829(-52.5F, 0.0F, -5.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.92F, AnimationHelper.method_41829(-67.5F, 0.0F, -5.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41829(-17.5F, 0.0F, -10.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.76F, AnimationHelper.method_41829(70.0F, 0.0F, 12.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.04F, AnimationHelper.method_41829(70.0F, 0.0F, 12.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.12F, AnimationHelper.method_41829(80.0F, 0.0F, 12.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.24F, AnimationHelper.method_41829(70.0F, 0.0F, 12.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41829(77.5F, 0.0F, -2.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41823(-8.0F, -11.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.92F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.24F, AnimationHelper.method_41823(0.0F, 0.47F, -0.95F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.32F, AnimationHelper.method_41823(0.0F, 0.47F, -0.95F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.4F, AnimationHelper.method_41823(0.0F, 0.47F, -0.95F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41823(0.0F, 1.0F, -2.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.76F, AnimationHelper.method_41823(0.0F, 1.0F, -2.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.84F, AnimationHelper.method_41823(0.0F, 1.0F, -2.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.92F, AnimationHelper.method_41823(0.0F, 1.0F, -2.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41823(0.0F, -2.0F, -2.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.76F, AnimationHelper.method_41823(0.0F, -4.0F, 1.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.04F, AnimationHelper.method_41823(0.0F, -1.0F, 1.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.12F, AnimationHelper.method_41823(0.0F, -1.0F, 1.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.24F, AnimationHelper.method_41823(0.0F, -1.0F, 1.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41823(0.0F, -1.0F, 1.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_ear",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.36F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.56F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.8F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_ear",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.36F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.56F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.8F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_ear",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.36F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.56F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.8F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_ear",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.36F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.56F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.8F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.2F, AnimationHelper.method_41829(-152.5F, 2.5F, 7.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41829(-180.0F, 12.5F, -10.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.8F, AnimationHelper.method_41829(-90.0F, 12.5F, -10.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41829(-90.0F, 12.5F, -10.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41829(-90.0F, 12.5F, -10.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.08F, AnimationHelper.method_41829(-95.0F, 12.5F, -10.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.24F, AnimationHelper.method_41829(-83.93F, 3.93F, 5.71F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.36F, AnimationHelper.method_41829(-80.0F, 7.5F, 17.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.76F, AnimationHelper.method_41829(-67.5F, 2.5F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.08F, AnimationHelper.method_41829(-67.5F, 2.5F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.44F, AnimationHelper.method_41829(-55.0F, 2.5F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.56F, AnimationHelper.method_41829(-60.0F, 2.5F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.68F, AnimationHelper.method_41829(-55.0F, 2.5F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41829(-67.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.56F, AnimationHelper.method_41829(-50.45F, 0.0F, 2.69F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.08F, AnimationHelper.method_41829(-62.72F, 0.0F, 4.3F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.2F, AnimationHelper.method_41823(0.0F, -21.0F, 9.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41823(2.0F, -2.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.8F, AnimationHelper.method_41823(2.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41823(2.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41823(2.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.08F, AnimationHelper.method_41823(2.0F, -2.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.24F, AnimationHelper.method_41823(2.0F, 2.71F, 3.86F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.36F, AnimationHelper.method_41823(2.0F, 1.0F, 5.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.76F, AnimationHelper.method_41823(2.0F, 3.0F, 3.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.08F, AnimationHelper.method_41823(2.0F, 3.0F, 3.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.44F, AnimationHelper.method_41823(2.67F, 4.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.56F, AnimationHelper.method_41823(2.67F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.68F, AnimationHelper.method_41823(2.67F, 4.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41823(0.67F, 3.0F, 4.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.12F, AnimationHelper.method_41829(-167.5F, -17.5F, -7.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.6F, AnimationHelper.method_41829(-167.5F, -17.5F, -7.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.88F, AnimationHelper.method_41829(-175.0F, -17.5F, 15.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.16F, AnimationHelper.method_41829(-190.0F, -17.5F, 5.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.28F, AnimationHelper.method_41829(-90.0F, -5.0F, 5.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41829(-90.0F, -17.5F, -12.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.8F, AnimationHelper.method_41829(-90.0F, -17.5F, -12.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41829(-90.0F, -17.5F, -12.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41829(-90.0F, -17.5F, -12.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.04F, AnimationHelper.method_41829(-81.29F, -10.64F, -14.21F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.16F, AnimationHelper.method_41829(-83.5F, -5.5F, -15.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.76F, AnimationHelper.method_41829(-62.5F, -7.5F, 5.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.92F, AnimationHelper.method_41829(-58.75F, -3.75F, 5.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.08F, AnimationHelper.method_41829(-55.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.44F, AnimationHelper.method_41829(-52.5F, 0.0F, 5.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.56F, AnimationHelper.method_41829(-50.0F, 0.0F, 5.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.68F, AnimationHelper.method_41829(-52.5F, 0.0F, 5.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41829(-72.5F, -2.5F, 5.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.56F, AnimationHelper.method_41829(-57.5F, -4.54F, 2.99F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.08F, AnimationHelper.method_41829(-70.99F, -5.77F, 1.78F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.12F, AnimationHelper.method_41823(0.0F, -8.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.6F, AnimationHelper.method_41823(0.0F, -8.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.88F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.2F, AnimationHelper.method_41823(-2.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41823(-4.0F, 3.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.8F, AnimationHelper.method_41823(-4.0F, 3.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41823(-4.0F, 3.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41823(-4.0F, 3.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.04F, AnimationHelper.method_41823(-3.23F, 5.7F, 4.97F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.16F, AnimationHelper.method_41823(-1.49F, 2.22F, 5.25F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.76F, AnimationHelper.method_41823(-1.14F, 1.71F, 1.86F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.92F, AnimationHelper.method_41823(-1.14F, 1.21F, 3.86F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.08F, AnimationHelper.method_41823(-1.14F, 2.71F, 4.86F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.44F, AnimationHelper.method_41823(-1.0F, 1.0F, 3.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.56F, AnimationHelper.method_41823(0.0F, 1.0F, 1.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.68F, AnimationHelper.method_41823(0.0F, 1.0F, 3.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41823(-2.0F, 0.0F, 4.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.36F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.32F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.48F, AnimationHelper.method_41829(55.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.6F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.8F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, -63.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41823(0.0F, -56.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.2F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.8F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.36F, AnimationHelper.method_41823(0.0F, -22.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.76F, AnimationHelper.method_41823(0.0F, -12.28F, 2.48F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.92F, AnimationHelper.method_41823(0.0F, -9.28F, 2.48F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.08F, AnimationHelper.method_41823(0.0F, -12.28F, 2.48F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.32F, AnimationHelper.method_41823(0.0F, -4.14F, 4.14F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.48F, AnimationHelper.method_41823(0.0F, -0.57F, -8.43F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.6F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.8F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.36F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.84F, AnimationHelper.method_41829(20.0F, 0.0F, -17.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.68F, AnimationHelper.method_41829(20.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.84F, AnimationHelper.method_41829(10.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.8F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, -63.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41823(0.0F, -56.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.2F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.8F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41823(0.0F, -32.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.36F, AnimationHelper.method_41823(0.0F, -22.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.84F, AnimationHelper.method_41823(-4.0F, 2.0F, -7.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.0F, AnimationHelper.method_41823(-4.0F, 0.0F, -5.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.68F, AnimationHelper.method_41823(-4.0F, 0.0F, -9.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(4.84F, AnimationHelper.method_41823(-2.0F, 2.0F, -3.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(5.8F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(6.64F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .build();


}
