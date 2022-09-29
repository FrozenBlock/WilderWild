package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.FishRotationInterface;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFish.class)
public class AbstractFishMixin implements FishRotationInterface {

    public float xBodyRot;
    public float prevXRot;
    public float zBodyRot;
    public float prevZRot;
    private float rotateSpeed;

    @Inject(method = "aiStep", at = @At("RETURN"))
    public void aiStep(CallbackInfo info) {
        AbstractFish fish = AbstractFish.class.cast(this);
        this.prevXRot = this.xBodyRot;
        this.prevZRot = this.zBodyRot;
        if (fish.isInWaterOrBubble()) {
            this.rotateSpeed *= 0.8f;
            Vec3 vec3 = fish.getDeltaMovement();
            fish.yBodyRot += (-(Mth.atan2(vec3.x, vec3.z)) * 57.295776f - fish.yBodyRot) * 0.1f;
            fish.setYRot(fish.yBodyRot);
            this.zBodyRot += (float) Math.PI * this.rotateSpeed * 1.5f;
            this.xBodyRot += (-(Mth.atan2(vec3.horizontalDistance(), vec3.y)) * 57.295776f - this.xBodyRot) * 0.1f;
        } else {
            this.xBodyRot += (-90.0f - this.xBodyRot) * 0.02f;
        }
    }

    @Override
    public float getXRot() {
        return this.xBodyRot;
    }

    @Override
    public float getPrevXRot() {
        return this.prevXRot;
    }

    @Override
    public float getZRot() {
        return this.zBodyRot;
    }

    @Override
    public float getPrevZRot() {
        return this.prevZRot;
    }

    @Override
    public float getRotateSpeed() {
        return this.rotateSpeed;
    }

}
