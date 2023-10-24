package net.frozenblock.wilderwild.mixin.entity.crab;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.registry.RegisterMobEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

	@Shadow
	public ServerPlayer player;

	@ModifyExpressionValue(method = "handleUseItemOn", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;MAX_INTERACTION_DISTANCE:D"))
	public double wilderWild$handleUseItemOn(double original) {
		if (this.player != null && this.player.hasEffect(RegisterMobEffects.REACH)) {
			int amplifier = this.player.getEffect(RegisterMobEffects.REACH).getAmplifier() + 1;
			return original + (amplifier * 0.75D);
		}
		return original;
	}

	@ModifyExpressionValue(method = "handleUseItemOn", at = @At(value = "CONSTANT", args = "doubleValue=64", ordinal = 0))
	public double wilderWild$handleUseItemOn2(double original) {
		if (this.player != null && this.player.hasEffect(RegisterMobEffects.REACH)) {
			int amplifier = this.player.getEffect(RegisterMobEffects.REACH).getAmplifier() + 1;
			return Mth.square(Math.sqrt(original) + amplifier * 0.75D);
		}
		return original;
	}

	@ModifyExpressionValue(method = "handleInteract", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;MAX_INTERACTION_DISTANCE:D"))
	public double wilderWild$handleInteract(double original) {
		if (this.player != null && this.player.hasEffect(RegisterMobEffects.REACH)) {
			int amplifier = this.player.getEffect(RegisterMobEffects.REACH).getAmplifier() + 1;
			return original + (amplifier * 0.75D);
		}
		return original;
	}

}
