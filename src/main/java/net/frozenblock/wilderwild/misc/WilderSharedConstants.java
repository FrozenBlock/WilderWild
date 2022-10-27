package net.frozenblock.wilderwild.misc;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

public class WilderSharedConstants {
	public static final String MOD_ID = "wilderwild";
	public static final ModContainer MOD_CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow();
}
