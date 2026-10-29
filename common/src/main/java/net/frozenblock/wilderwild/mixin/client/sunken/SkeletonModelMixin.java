package net.frozenblock.wilderwild.mixin.client.sunken;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.client.model.monster.skeleton.SunkenModel;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.monster.skeleton.SkeletonModel;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(SkeletonModel.class)
public class SkeletonModelMixin<S extends SkeletonRenderState> {

	@ModifyExpressionValue(
		method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/SkeletonRenderState;)V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/renderer/entity/state/SkeletonRenderState;isHoldingBow:Z",
			opcode = Opcodes.GETFIELD
		)
	)
	public boolean wilderWild$disableZombieArmsOnSunkenIfHoldingCrossbow(boolean original, S state) {
		if (original) return true;

		if (((SkeletonModel) (Object) this) instanceof SunkenModel) {
			final HumanoidModel.ArmPose armPose = state.getArmPose(state.mainArm);
			return armPose == HumanoidModel.ArmPose.CROSSBOW_CHARGE || armPose == HumanoidModel.ArmPose.CROSSBOW_HOLD;
		}

		return original;
	}
}
