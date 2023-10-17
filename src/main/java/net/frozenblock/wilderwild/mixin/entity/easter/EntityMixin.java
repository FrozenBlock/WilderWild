package net.frozenblock.wilderwild.mixin.entity.easter;

import net.frozenblock.lib.spotting_icons.impl.EntitySpottingIconInterface;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

	@Inject(method = "setCustomName", at = @At(value = "HEAD"))
	public void wilderWild$setCustomName(@Nullable Component name, CallbackInfo info) {
		if (name != null && name.getString().equalsIgnoreCase("Stella")) {
			((EntitySpottingIconInterface) Entity.class.cast(this)).getSpottingIconManager()
				.setIcon(WilderSharedConstants.id("textures/spotting_icons/stella.png"), 5F, 8F, WilderSharedConstants.id("stella"));
		}
	}
}
