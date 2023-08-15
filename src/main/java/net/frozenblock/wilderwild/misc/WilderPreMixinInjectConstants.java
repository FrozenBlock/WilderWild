package net.frozenblock.wilderwild.misc;

import net.fabricmc.loader.api.FabricLoader;

/**
 * This class was created to fix issue #289: <a href="https://github.com/FrozenBlock/WilderWild/issues/289">...</a>.
 */
public class WilderPreMixinInjectConstants {
	public static final boolean HAS_FALLINGLEAVES = FabricLoader.getInstance().isModLoaded("fallingleaves");
	public static final boolean HAS_MAKEBUBBLESPOP = FabricLoader.getInstance().isModLoaded("make_bubbles_pop");
	public static final boolean HAS_PARTICLERAIN = FabricLoader.getInstance().isModLoaded("particlerain");
}
