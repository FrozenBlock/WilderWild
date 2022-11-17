package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// REMOVE THIS MIXIN WHEN EVERYTHING WORKS
@Mixin(CreativeModeTab.ItemDisplayBuilder.class)
public class ItemDisplayBuilderMixin {

	@Inject(method = "accept", at = @At("HEAD"), cancellable = true)
	private void preventAir(ItemStack stack, CreativeModeTab.TabVisibility visibility, CallbackInfo ci) {
		if (stack.getItem() == Items.AIR) {
			ci.cancel();
		}
	}

	@Inject(method = "accept", at = @At(value = "INVOKE", target = "Ljava/lang/IllegalArgumentException;<init>(Ljava/lang/String;)V", ordinal = 0))
	private void accept(ItemStack stack, CreativeModeTab.TabVisibility visibility, CallbackInfo ci) {
		WilderSharedConstants.LOGGER.info(BuiltInRegistries.ITEM.getKey(stack.getItem()) + " failed");
	}
}
