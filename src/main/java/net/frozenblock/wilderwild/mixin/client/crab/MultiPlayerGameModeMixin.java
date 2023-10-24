package net.frozenblock.wilderwild.mixin.client.crab;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.registry.RegisterMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

	/*
	@Shadow @Final
	private Minecraft minecraft;

	@ModifyReturnValue(method = "getPickRange", at = @At("RETURN"))
	public float wilderWild$getPickRange(float original) {
		if (this.minecraft.player != null && this.minecraft.player.hasEffect(RegisterMobEffects.REACH)) {
			int amplifier = this.minecraft.player.getEffect(RegisterMobEffects.REACH).getAmplifier() + 1;
			return original + (amplifier * 0.75F);
		}
		return original;
	}
	 */

}
