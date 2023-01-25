package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.integration.api.ModIntegrations;
import net.frozenblock.lib.integration.impl.ModIntegrationSupplier;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import java.util.function.Supplier;

public final class WilderModIntegrations {

	public static final ModIntegration FROZENLIB_INTEGRATION = registerAndGet(FrozenLibIntegration::new, "frozenlib");
	public static final ModIntegration BETTEREND_INTEGRATION = registerAndGet(BetterEndIntegration::new, "betterend");
	public static final ModIntegration BETTERNETHER_INTEGRATION = registerAndGet(BetterNetherIntegration::new, "betternether");
	public static final ModIntegration BLOCKUS_INTEGRATION = registerAndGet(BlockusIntegration::new, "blockus");
	public static final ModIntegration EDENRING_INTEGRATION = registerAndGet(EdenRingIntegration::new, "edenring");
	public static final ModIntegration TECHREBORN_INTEGRATION = registerAndGet(TechRebornIntegration::new, "techreborn");
	public static final ModIntegration TERRALITH_INTEGRATION = registerAndGet(TerralithModIntegration::new, "terralith");

	private WilderModIntegrations() {
		throw new UnsupportedOperationException("WilderModIntegrations contains only static declarations.");
	}

	public static void init() {
	}

	public static ModIntegrationSupplier register(Supplier<? extends ModIntegration> integration, String modID) {
		return ModIntegrations.register(integration, WilderSharedConstants.MOD_ID, modID);
	}

	public static ModIntegration registerAndGet(Supplier<ModIntegration> integration, String modID) {
		return ModIntegrations.register(integration, WilderSharedConstants.MOD_ID, modID).getIntegration();
	}
}
