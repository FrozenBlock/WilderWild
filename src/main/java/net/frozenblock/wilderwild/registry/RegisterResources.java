package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.wilderwild.WilderWild;

public final class RegisterResources {
	private RegisterResources() {
		throw new UnsupportedOperationException("RegisterResources contains only static declarations.");
	}

	public static void register() {
		FabricLoader.getInstance().getModContainer(WilderWild.MOD_ID).ifPresent(modContainer -> {
			ResourceManagerHelper.registerBuiltinResourcePack(WilderWild.id("new_main_menu"), modContainer, ResourcePackActivationType.DEFAULT_ENABLED);
			//ResourceManagerHelper.registerBuiltinResourcePack(WilderWild.id("old_wilder_wild_panoramas"), modContainer, ResourcePackActivationType.NORMAL);
		});
	}
}
