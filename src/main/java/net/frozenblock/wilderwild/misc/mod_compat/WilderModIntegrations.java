package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.integration.api.ModIntegrations;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;

public final class WilderModIntegrations {

    public static final ModIntegration FROZENLIB_INTEGRATION = register(new FrozenLibIntegration());
    public static final ModIntegration TERRALITH_INTEGRATION = register(new TerralithModIntegration());

    private WilderModIntegrations() {
        throw new UnsupportedOperationException("WilderModIntegrations contains only static declarations.");
    }

    public static void init() {
    }

    public static ModIntegration register(ModIntegration integration) {
        return ModIntegrations.register(integration, WilderSharedConstants.MOD_ID);
    }
}
