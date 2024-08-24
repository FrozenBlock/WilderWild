package net.frozenblock.wilderwild.mixin.client.wind.fallingleaves;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderConstants;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import randommcsomethin.fallingleaves.config.ConfigDefaults;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(ConfigDefaults.class)
public class ConfigDefaultsMixin {

	@Inject(
		method = "isConifer",
		at = @At("HEAD"),
		cancellable = true
	)
	private static void wilderWild$forceAddCompatBecauseTheyDidnt(ResourceLocation blockId, CallbackInfoReturnable<Boolean> info) {
		if (
			blockId.toString().equals(WilderConstants.string("cypress_leaves"))
			|| blockId.toString().equals(WilderConstants.string("palm_fronds"))
		) {
			info.setReturnValue(true);
		}
	}

	@Inject(
		method = "spawnRateFactor",
		at = @At("HEAD"),
		cancellable = true
	)
	private static void wilderWild$forceAddCompatBecauseTheyDidntAgain(ResourceLocation blockId, CallbackInfoReturnable<Double> info) {
		if (blockId.toString().equals(WilderConstants.string("palm_fronds"))) {
			info.setReturnValue(0D);
		}
	}

}
