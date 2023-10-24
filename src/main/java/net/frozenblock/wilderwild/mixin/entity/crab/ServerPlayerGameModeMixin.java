package net.frozenblock.wilderwild.mixin.entity.crab;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.registry.RegisterMobEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

	@Shadow @Final
	protected ServerPlayer player;

	@ModifyExpressionValue(method = "handleBlockBreakAction", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;MAX_INTERACTION_DISTANCE:D"))
	public double wilderWild$handleBlockBreakAction(double original) {
		if (this.player != null && this.player.hasEffect(RegisterMobEffects.REACH)) {
			int amplifier = this.player.getEffect(RegisterMobEffects.REACH).getAmplifier() + 1;
			return original + (amplifier * 0.75);
		}
		return original;
	}

}
