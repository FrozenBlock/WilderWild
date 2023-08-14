package net.frozenblock.wilderwild.misc;

import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.wilderwild.mixin.WilderWildMixinPlugin;

/**
 * This class was created to fix issue #289: https://github.com/FrozenBlock/WilderWild/issues/289.
 * <p>
 * If a variable is going to be loaded prior to Mixin Injection (e.g. {@link WilderWildMixinPlugin},) please put said variable here instead of {@link WilderSharedConstants}.
 */
public class WilderPreMixinInjectConstants {
	public static final boolean HAS_FALLINGLEAVES = FabricLoader.getInstance().isModLoaded("fallingleaves");
	public static final boolean HAS_MAKEBUBBLESPOP = FabricLoader.getInstance().isModLoaded("make_bubbles_pop");
	public static final boolean HAS_PARTICLERAIN = FabricLoader.getInstance().isModLoaded("particlerain");
}
