package net.frozenblock.wilderwild.mixin.client;

import net.frozenblock.wilderwild.entity.render.animations.CustomAllayAnimations;
import net.frozenblock.wilderwild.entity.render.animations.WilderAllay;
import net.minecraft.client.model.AllayModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.allay.Allay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AllayModel.class)
public abstract class AllayModelMixin extends HierarchicalModel<Allay>
        implements ArmedModel {

    @Unique
    private final AllayModel model = AllayModel.class.cast(this);

    @Shadow
    @Final
    private ModelPart head;

    @Shadow
    @Final
    private ModelPart root;

    private static final float PI180 = Mth.PI / 180;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/allay/Allay;FFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(FFF)F")
    )
    private void setupAnim(
            final Allay allay,
            final float limbSwing,
            final float limbSwingAmount,
            final float ageInTicks,
            final float netHeadYaw,
            final float headPitch,
            final CallbackInfo ci
    ) {
        this.root.yRot = 0;
        this.root.zRot = 0;
        this.head.xRot = headPitch * PI180;
        this.head.yRot = netHeadYaw * PI180;
        model.animate(((WilderAllay) allay).getDancingAnimationState(),
                CustomAllayAnimations.DANCING, ageInTicks);
    }
}
