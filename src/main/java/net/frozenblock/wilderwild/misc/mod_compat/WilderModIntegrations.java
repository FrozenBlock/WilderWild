package net.frozenblock.wilderwild.misc.mod_compat;

import java.util.function.Supplier;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.integration.api.ModIntegrationSupplier;
import net.frozenblock.lib.integration.api.ModIntegrations;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;

public final class WilderModIntegrations {

    public static final ModIntegration FROZENLIB_INTEGRATION = registerAndGet(FrozenLibIntegration::new, "frozenlib");
    public static final ModIntegration BETTEREND_INTEGRATION = registerAndGet(BetterEndIntegration::new, "betterend");
    public static final ModIntegration BETTERNETHER_INTEGRATION = registerAndGet(BetterNetherIntegration::new, "betternether");
    public static final ModIntegration BLOCKUS_INTEGRATION = registerAndGet(BlockusIntegration::new, "blockus");
    public static final ModIntegration EDENRING_INTEGRATION = registerAndGet(EdenRingIntegration::new, "edenring");
    public static final ModIntegration TECHREBORN_INTEGRATION = registerAndGet(TechRebornIntegration::new, "techreborn");
    public static final ModIntegration TERRALITH_INTEGRATION = registerAndGet(TerralithModIntegration::new, "terralith");
	public static final ModIntegrationSupplier<SimpleCopperPipesIntegration> SIMPLE_COPPER_PIPES_INTEGRATION = register(SimpleCopperPipesIntegration::new, "copper_pipe");

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
