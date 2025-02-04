package net.frozenblock.wilderwild.mixin.entity.penguin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.entity.Penguin;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SmoothSwimmingMoveControl.class)
public class SmoothSwimmingMoveControlMixin {

	@WrapOperation(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Mob;isInWater()Z",
			ordinal = 1
		)
	)
	public boolean wilderWild$modifyPenguinWadeSpeed(Mob instance, Operation<Boolean> original) {
		if (instance instanceof Penguin) return instance.isUnderWater();
		return original.call(instance);
	}

}
