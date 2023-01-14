package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;

public final class RegisterResources {
	private RegisterResources() {
		throw new UnsupportedOperationException("RegisterResources contains only static declarations.");
	}

	public static void register() {
		ResourceManagerHelper.registerBuiltinResourcePack(WilderSharedConstants.id("new_main_menu"), WilderSharedConstants.MOD_CONTAINER, ResourcePackActivationType.DEFAULT_ENABLED);
		//ResourceManagerHelper.registerBuiltinResourcePack(WilderSharedConstants.id("old_wilder_wild_panoramas"), WilderSharedConstants.MOD_CONTAINER, ResourcePackActivationType.NORMAL);
		//ResourceManagerHelper.registerBuiltinResourcePack(WilderSharedConstants.id("update_1_20_additions"), WilderSharedConstants.MOD_CONTAINER, ResourcePackActivationType.NORMAL);
		//WilderFeatureFlags.init();
		//FrozenFeatureFlags.rebuild();
	}
}
