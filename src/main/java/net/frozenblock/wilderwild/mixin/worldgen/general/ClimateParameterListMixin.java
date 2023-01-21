package net.frozenblock.wilderwild.mixin.worldgen.general;

import net.minecraft.world.level.biome.Climate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Climate.ParameterList.class)
public class ClimateParameterListMixin<T> {

	@Inject(method = "findValue", at = @At("HEAD"), cancellable = true)
	public void findValue(Climate.TargetPoint targetPoint, CallbackInfoReturnable<T> info) {
		info.setReturnValue(this.findValueBruteForce(targetPoint));
	}

	@Shadow
	public T findValueBruteForce(Climate.TargetPoint targetPoint) {
		throw new AssertionError("Mixin injection failed - WilderWild ClimeParameterListMixin");
	}

}
