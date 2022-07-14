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
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.56F, AnimationHelper.method_41829(17.5F, 32.5F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.96F, AnimationHelper.method_41829(0.0F, 32.5F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.2F, AnimationHelper.method_41829(10.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.8F, AnimationHelper.method_41829(10.0F, -30.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.32F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885))
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.68F, AnimationHelper.method_41829(0.0F, 40.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.96F, AnimationHelper.method_41829(-22.5F, 40.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.24F, AnimationHelper.method_41829(0.0F, 20.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.52F, AnimationHelper.method_41829(-35.0F, 20.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.76F, AnimationHelper.method_41829(0.0F, 20.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.28F, AnimationHelper.method_41829(0.0F, -20.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.6F, AnimationHelper.method_41829(-25F, -23.6504F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.88F, AnimationHelper.method_41829(0.0F, -20.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.32F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)))
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.96F, AnimationHelper.method_41829(17.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.2F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.76F, AnimationHelper.method_41829(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.32F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)))
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.96F, AnimationHelper.method_41829(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.2F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.76F, AnimationHelper.method_41829(17.5F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.32F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)))
            .build();

}
