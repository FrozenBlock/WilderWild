package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

	@Inject(method = "discard", at = @At(value = "HEAD"))
	public void wilderWild$discard(CallbackInfo info) {
		Entity entity = Entity.class.cast(this);
		if (entity instanceof Tumbleweed tumbleweed) {
			tumbleweed.dropItem(false);
		}
	}

}
