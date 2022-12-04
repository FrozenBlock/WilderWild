package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.client.ClientMethodInteractionHandler;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEnderpearl.class)
public class ThrownEnderpearlMixin {

    @Inject(method = "onHit", at = @At("HEAD"))
	public void onHit(HitResult result, CallbackInfo info) {
		ThrownEnderpearl pearl = ThrownEnderpearl.class.cast(this);
		Player owner =  pearl.getOwner() instanceof Player ? ((Player)pearl.getOwner()) : null;

		if (ClothConfigInteractionHandler.enderpearlLandingSounds()) {
			boolean silent = pearl.isSilent();
			if (!pearl.level.isClientSide) {
				if (!silent) {
					pearl.level.playSound(owner, pearl.getX(), pearl.getY(), pearl.getZ(), RegisterSounds.ITEM_ENDERPEARL_LAND, SoundSource.NEUTRAL, 0.6F, 0.85F + (pearl.level.random.nextFloat() * 0.2F));
				}
				if (owner == null || !owner.isSilent()) {
					pearl.level.playSound(owner, pearl.getX(), pearl.getY(), pearl.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.NEUTRAL, 0.4F, 0.85F + (pearl.level.random.nextFloat() * 0.2F));
				}
			} else if (owner != null) {
				ClientMethodInteractionHandler.playClientPlayerSound(RegisterSounds.ITEM_ENDERPEARL_LAND, 0.6F, 0.85F + (pearl.level.random.nextFloat() * 0.2F));
				ClientMethodInteractionHandler.playClientPlayerSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 0.4F, 0.85F + (pearl.level.random.nextFloat() * 0.2F));
			}
		}
	}
}
