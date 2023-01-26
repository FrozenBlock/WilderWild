package net.frozenblock.wilderwild.mixin.client.allay;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.render.animations.CustomAllayAnimations;
import net.frozenblock.wilderwild.entity.render.animations.WilderAllay;
import net.frozenblock.wilderwild.misc.mod_compat.WilderModIntegrations;
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

@Environment(EnvType.CLIENT)
@Mixin(AllayModel.class)
public abstract class AllayModelMixin extends HierarchicalModel<Allay> implements ArmedModel {

    @Unique
    private final AllayModel wilderWild$model = AllayModel.class.cast(this);

    @Shadow
    @Final
    private ModelPart head;

    @Shadow
    @Final
    private ModelPart root;

	@Unique
    private static final float WILDERWILD$PI180 = Mth.PI / 180;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/allay/Allay;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(FFF)F"))
    private void setupAnim(Allay allay, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().keyframeAllayDance()) {
			this.root.yRot = 0.0F;
			this.root.zRot = 0.0F;
			this.head.xRot = headPitch * WILDERWILD$PI180;
			this.head.yRot = netHeadYaw * WILDERWILD$PI180;
			wilderWild$model.animate(((WilderAllay) allay).getDancingAnimationState(), CustomAllayAnimations.DANCING, ageInTicks);
		}
	}
}
