package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.entity.render.animations.WilderAllay;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.animal.allay.Allay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Allay.class)
public class AllayMixin implements WilderAllay {

    @Shadow private float dancingAnimationTicks;
    @Unique
    private final Allay allay = Allay.class.cast(this);

    private final AnimationState dancingAnimationState = new AnimationState();

    @Override
    public AnimationState getDancingAnimationState() {
        return this.dancingAnimationState;
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void tick(CallbackInfo ci) {
        if (allay.level.isClientSide) {
            if (allay.isDancing()) {
                this.getDancingAnimationState().startIfStopped((int) this.dancingAnimationTicks);
            } else {
                this.getDancingAnimationState().stop();
            }
        }
    }
}
