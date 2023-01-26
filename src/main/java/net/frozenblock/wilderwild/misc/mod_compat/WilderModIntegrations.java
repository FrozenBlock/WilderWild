package net.frozenblock.wilderwild.misc.mod_compat;

import java.util.function.Supplier;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.integration.api.ModIntegrationSupplier;
import net.frozenblock.lib.integration.api.ModIntegrations;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.mod_compat.clothconfig.AbstractClothConfigIntegration;
import net.frozenblock.wilderwild.misc.mod_compat.clothconfig.ClothConfigIntegration;
import net.frozenblock.wilderwild.misc.mod_compat.scp.AbstractSimpleCopperPipesIntegration;
import net.frozenblock.wilderwild.misc.mod_compat.scp.SimpleCopperPipesIntegration;

public final class WilderModIntegrations {

    public static final ModIntegration FROZENLIB_INTEGRATION = registerAndGet(FrozenLibIntegration::new, "frozenlib");
    public static final ModIntegration BETTEREND_INTEGRATION = registerAndGet(BetterEndIntegration::new, "betterend");
    public static final ModIntegration BETTERNETHER_INTEGRATION = registerAndGet(BetterNetherIntegration::new, "betternether");
    public static final ModIntegration BLOCKUS_INTEGRATION = registerAndGet(BlockusIntegration::new, "blockus");
    public static final ModIntegration EDENRING_INTEGRATION = registerAndGet(EdenRingIntegration::new, "edenring");
    public static final ModIntegration TECHREBORN_INTEGRATION = registerAndGet(TechRebornIntegration::new, "techreborn");
    public static final ModIntegration TERRALITH_INTEGRATION = registerAndGet(TerralithModIntegration::new, "terralith");
	public static final ModIntegrationSupplier<AbstractSimpleCopperPipesIntegration> SIMPLE_COPPER_PIPES_INTEGRATION = register(SimpleCopperPipesIntegration::new, AbstractSimpleCopperPipesIntegration::new, "copper_pipe");
	public static final ModIntegrationSupplier<AbstractClothConfigIntegration> CLOTH_CONFIG_INTEGRATION = register(ClothConfigIntegration::new, AbstractClothConfigIntegration::new, "cloth-config");

	private WilderModIntegrations() {
		throw new UnsupportedOperationException("WilderModIntegrations contains only static declarations.");
	}

	public static void init() {
	}

	public static ModIntegrationSupplier<? extends ModIntegration> register(Supplier<? extends ModIntegration> integration, String modID) {
		return ModIntegrations.register(integration, WilderSharedConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> ModIntegrationSupplier<T> register(Supplier<T> integration, Supplier<T> unloadedIntegration, String modID) {
		return ModIntegrations.register(integration, unloadedIntegration, WilderSharedConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> ModIntegration registerAndGet(Supplier<T> integration, String modID) {
		return ModIntegrations.register(integration, WilderSharedConstants.MOD_ID, modID).getIntegration();
	}
}
