package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.entity.render.animations.WilderAllay;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
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

    @Shadow
    private float dancingAnimationTicks;
    @Unique
    private final Allay wilderWild$allay = Allay.class.cast(this);

	@Unique
    private final AnimationState wilderWild$dancingAnimationState = new AnimationState();

	@Unique
    @Override
    public AnimationState wilderWild$getDancingAnimationState() {
        return this.wilderWild$dancingAnimationState;
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void wilderWild$tickDancing(CallbackInfo info) {
        if (wilderWild$allay.level.isClientSide && WilderSharedConstants.CONFIG().keyframeAllayDance()) {
            if (wilderWild$allay.isDancing()) {
                this.wilderWild$getDancingAnimationState().startIfStopped((int) this.dancingAnimationTicks);
            } else {
                this.wilderWild$getDancingAnimationState().stop();
            }
        }
    }

}
