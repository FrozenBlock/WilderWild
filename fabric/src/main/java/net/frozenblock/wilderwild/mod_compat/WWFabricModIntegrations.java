package net.frozenblock.wilderwild.mod_compat;

import java.util.function.Supplier;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.integration.api.ModIntegrationSupplier;
import net.frozenblock.lib.integration.api.ModIntegrations;
import net.frozenblock.wilderwild.WWConstants;

public final class WWFabricModIntegrations {
	public static final ModIntegration FROZENLIB_INTEGRATION = registerAndGet(FrozenLibFabricIntegration::new, "frozenlib");
	public static final ModIntegration SIMPLE_COPPER_PIPES_INTEGRATION = registerAndGet(() -> new SimpleCopperPipesIntegration(), "simple_copper_pipes");
	public static final ModIntegration BIOLITH_INTEGRATION = registerAndGet(() -> new BiolithIntegration(), "biolith");

	private WWFabricModIntegrations() {
		throw new UnsupportedOperationException("WWModIntegrations contains only static declarations.");
	}

	public static void init() {
	}

	public static ModIntegrationSupplier<? extends ModIntegration> register(Supplier<? extends ModIntegration> integration, String modID) {
		return ModIntegrations.register(integration, WWConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> ModIntegrationSupplier<T> register(Supplier<T> integration, Supplier<T> unloadedIntegration, String modID) {
		return ModIntegrations.register(integration, unloadedIntegration, WWConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> ModIntegration registerAndGet(Supplier<T> integration, String modID) {
		return ModIntegrations.register(integration, WWConstants.MOD_ID, modID).getIntegration();
	}
}
