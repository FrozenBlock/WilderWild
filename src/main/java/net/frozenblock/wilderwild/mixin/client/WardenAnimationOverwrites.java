package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(WardenAnimations.class)
public class WardenAnimationOverwrites {

    @Final
    @Mutable
    @Shadow
    public static final Animation SNIFFING = Animation.Builder.create(4.16F)
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.56F, AnimationHelper.rotate(17.5F, 32.5F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.96F, AnimationHelper.rotate(0.0F, 32.5F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.2F, AnimationHelper.rotate(10.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.8F, AnimationHelper.rotate(10.0F, -30.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(3.32F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE))
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.68F, AnimationHelper.rotate(0.0F, 40.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.96F, AnimationHelper.rotate(-22.5F, 40.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.24F, AnimationHelper.rotate(0.0F, 20.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.52F, AnimationHelper.rotate(-35.0F, 20.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(1.76F, AnimationHelper.rotate(0.0F, 20.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.28F, AnimationHelper.rotate(0.0F, -20.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.6F, AnimationHelper.rotate(-25F, -23.6504F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.88F, AnimationHelper.rotate(0.0F, -20.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(3.32F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE)))
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.96F, AnimationHelper.rotate(17.5F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.2F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.76F, AnimationHelper.rotate(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(3.32F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE)))
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(0.96F, AnimationHelper.rotate(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.2F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(2.76F, AnimationHelper.rotate(17.5F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE),
                            new Keyframe(3.32F, AnimationHelper.rotate(0.0F, 0.0F, 0.0F), Transformation.Interpolations.SPLINE)))
            .build();

}
