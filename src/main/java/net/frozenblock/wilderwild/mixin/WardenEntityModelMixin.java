package net.frozenblock.wilderwild.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.WardenEntityModel;
import net.minecraft.entity.mob.WardenEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WardenEntityModel.class)
public class WardenEntityModelMixin<T extends WardenEntity> {
    @Final
    @Shadow
    protected ModelPart rightTendril;
    @Final
    @Shadow
    protected ModelPart leftTendril;

    /**
     * @author FrozenBlock
     * @reason yay tendrils
     */
    @Overwrite
    private void setTendrilPitches(T warden, float animationProgress, float tickDelta) {
        float f = warden.getTendrilPitch(tickDelta) * (float)(Math.cos((double)animationProgress * 2.25D) * 3.141592653589793D * 0.10000000149011612D);
        float g = warden.getTendrilPitch(tickDelta) * (float)(-Math.sin((double)animationProgress * 2.25D) * 3.141592653589793D * 0.12500000149011612D);

    //hecc yeah we're using all axes for this one >:3 -merp

        this.leftTendril.pitch = f;
        this.rightTendril.pitch = f;

        this.leftTendril.yaw = g / 2f;
        this.rightTendril.yaw = -g / 2f;

        this.leftTendril.roll = f / 2f;
        this.rightTendril.roll = -f / 2f;
    }
}
