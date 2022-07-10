package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.render.animations.CustomWardenAnimations;
import net.frozenblock.wilderwild.entity.render.animations.WardenAnimationInterface;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.WardenEntityModel;
import net.minecraft.entity.mob.WardenEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WardenEntityModel.class)
public class WardenEntityModelMixin<T extends WardenEntity> {
    @Final
    @Shadow
    private ModelPart root;
    @Final
    @Shadow
    protected ModelPart rightTendril;
    @Final
    @Shadow
    protected ModelPart leftTendril;

    private final WardenEntityModel<T> model = WardenEntityModel.class.cast(this);

    @Inject(at = @At("HEAD"), method = "setTendrilPitches", cancellable = true)
    private void setTendrilPitches(T warden, float animationProgress, float tickDelta, CallbackInfo info) {
        float f = warden.getTendrilPitch(tickDelta) * (float) (Math.cos((double) animationProgress * 2.25D) * 3.141592653589793D * 0.10000000149011612D);
        float g = warden.getTendrilPitch(tickDelta) * (float) (-Math.sin((double) animationProgress * 2.25D) * 3.141592653589793D * 0.12500000149011612D);

        //hecc yeah we're using all axes for this one >:3 -merp
        //hi merp

        this.leftTendril.pitch = f;
        this.rightTendril.pitch = f;

        this.leftTendril.yaw = g / 2f;
        this.rightTendril.yaw = -g / 2f;

        this.leftTendril.roll = f / 2f;
        this.rightTendril.roll = -f / 2f;
        info.cancel();
    }

    @Inject(method = "setAngles(Lnet/minecraft/entity/mob/WardenEntity;FFFFF)V", at = @At("TAIL"))
    private void setAngles(T wardenEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        model.updateAnimation(((WardenAnimationInterface) wardenEntity).getDyingAnimationState(), CustomWardenAnimations.DYING, h);
        if (wardenEntity.isInSwimmingPose()) {
            this.root.pitch = wardenEntity.getHeadYaw();
        }
        model.updateAnimation(((WardenAnimationInterface) wardenEntity).getSwimmingAnimationState(), CustomWardenAnimations.SWIMMING, h);
    }

    @Inject(method = "setHeadAndBodyAngles", at = @At("TAIL"))
    private void setHeadAndBodyAngles(float animationProgress, CallbackInfo ci) {
        float a = (float) (Math.cos(animationProgress * 500D) * 15D - 10D);
        float b = (float) (Math.sin(animationProgress * 250D) * 5D);
        float c = (float) (Math.cos(animationProgress * 500D) * -2D);

        float d = (float) (Math.sin(animationProgress * 500D) * -10D - 60D);

        float e = (float) (-Math.cos(animationProgress * 500D) * 25D);
        float f = (float) (Math.sin(animationProgress * 500D) * -90D + 90D);
        float g = (float) (Math.sin(animationProgress * 1000D) * 2D + 2D);

        float h = (float) (Math.cos(animationProgress * 500D) * 25D);
        float i = (float) (-Math.sin(animationProgress * 500D) * -90D - 90D);
        float j = (float) (Math.sin(animationProgress * 1000D) * 2D - 2D);

        float k = (float) (Math.cos(animationProgress * 500D) * 35D + 15D);

        float l = (float) (-Math.cos(animationProgress * 500D) * 35D + 15D);
        // im stupid im still trying to figure out how to implement these i hate entity models
    }
}
