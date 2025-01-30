package net.frozenblock.wilderwild.mixin.entity.firefly;

import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.world.level.block.FireflyBushBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireflyBushBlock.class)
public class FireflyBushMixin {

	@Inject(method = "animateTick", at = @At("HEAD"), cancellable = true)
	public void wilderWild$animateTick(CallbackInfo info) {
		if (!WWEntityConfig.Client.SPAWN_FIREFLY_PARTICLES) {
			info.cancel();
		}
	}

}
