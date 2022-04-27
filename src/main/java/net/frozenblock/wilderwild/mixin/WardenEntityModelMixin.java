package net.frozenblock.wilderwild.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.WardenEntityModel;
import net.minecraft.entity.mob.WardenEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WardenEntityModel.class)
public class WardenEntityModelMixin<T extends WardenEntity> {
    @Shadow
    protected ModelPart rightTendril;
    @Shadow
    protected ModelPart leftTendril;
    /**
     * @author FrozenBlock
     * @reason yay pitches
     */
    @Overwrite
    private void setTendrilPitches(T warden, float animationProgress, float tickDelta) {
        float f = warden.getTendrilPitch(tickDelta) * (float)(Math.cos((double)animationProgress * 2.25D) * 3.141592653589793D * 0.10000000149011612D);
        this.leftTendril.pitch = f;
        this.rightTendril.pitch = -f;
        this.leftTendril.yaw = f;
        this.rightTendril.yaw = -f;
    }
}
