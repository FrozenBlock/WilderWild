package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.lib.sound.api.FrozenSoundPackets;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
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

		if (ClothConfigInteractionHandler.enderpearlLandingSounds()) {
			if (!pearl.level.isClientSide) {
				Entity owner = pearl.getOwner();

				if (owner instanceof ServerPlayer player) {
					if (!pearl.isSilent()) {
						float pitch = 0.9F + (pearl.level.random.nextFloat() * 0.2F);
						pearl.level.playSound(player, pearl.getX(), pearl.getY(), pearl.getZ(), RegisterSounds.ITEM_ENDERPEARL_LAND, SoundSource.NEUTRAL, 0.6F, pitch);
						FrozenSoundPackets.createLocalPlayerSound(player, RegisterSounds.ITEM_ENDERPEARL_LAND, 0.6F, pitch);
					}
					if (!player.isSilent()) {
						float pitch = 0.9F + (pearl.level.random.nextFloat() * 0.2F);
						pearl.level.playSound(player, pearl.getX(), pearl.getY(), pearl.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.NEUTRAL, 0.4F, pitch);
						FrozenSoundPackets.createLocalPlayerSound(player, SoundEvents.CHORUS_FRUIT_TELEPORT, 0.4F, pitch);
					}
				} else {
					if (!pearl.isSilent()) {
						pearl.level.playSound(null, pearl.getX(), pearl.getY(), pearl.getZ(), RegisterSounds.ITEM_ENDERPEARL_LAND, SoundSource.NEUTRAL, 0.6F, 0.85F + (pearl.level.random.nextFloat() * 0.2F));
					}
					if (owner != null && !owner.isSilent()) {
						pearl.level.playSound(null, pearl.getX(), pearl.getY(), pearl.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.NEUTRAL, 0.4F, 0.85F + (pearl.level.random.nextFloat() * 0.2F));
					}
				}
			}
		}
	}
}
