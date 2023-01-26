package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.misc.mod_compat.WilderModIntegrations;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownExperienceBottle.class)
public class ThrownExperienceBottleMixin {

    @Inject(method = "onHit", at = @At("HEAD"))
	public void onHit(HitResult result, CallbackInfo info) {
		ThrownExperienceBottle expBottle = ThrownExperienceBottle.class.cast(this);
		if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().potionLandingSounds()) {
			expBottle.playSound(RegisterSounds.ITEM_EXPERIENCE_BOTTLE_SPLASH, 1.0F, 0.85F + (expBottle.level.random.nextFloat() * 0.2F));
		}
	}
}
