/*package net.frozenblock.wilderwild.misc;

import net.caffeinemc.caffeineconfig.AbstractCaffeineConfigMixinPlugin;
import net.caffeinemc.caffeineconfig.CaffeineConfig;
import net.fabricmc.loader.api.FabricLoader;

public class WilderWildMixinPlugin extends AbstractCaffeineConfigMixinPlugin {

	private static final String MOD_NAME = "Wilder Wild";
	private static final String MIXIN_ROOT = "net.frozenblock.wilderwild.mixin.";
	private static final String FILE_NAME = WilderSharedConstants.MOD_ID + "-mixins.properties";

	@Override
	protected CaffeineConfig createConfig() {
		return CaffeineConfig.builder(MOD_NAME)
				.addMixinOption("client", true)
				.addMixinOption("client.allay", true)
				.addMixinOption("client.easter", true)
				.addMixinOption("client.general", true)
				.addMixinOption("client.shrieker_gargling", true)
				.addMixinOption("client.warden", true)
				.addMixinOption("client.warden.animation", true)
				.addOptionDependency("client.warden.swim", "server.warden.swim", true)
				.addMixinOption("server", true)
				.addMixinOption("server.general", true)
				.addMixinOption("server.warden", true)
				.addMixinOption("server.warden.swim", true)
				.addMixinOption("server", true)
				.addMixinOption("worldgen.general", true)
				.build(FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME));
	}

	@Override
	protected String mixinPackageRoot() {
		return MIXIN_ROOT;
	}
}
*/
