package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.integration.api.ModIntegrations;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;

public final class WilderModIntegrations {

    public static final ModIntegration FROZENLIB_INTEGRATION = ModIntegrations.register(new FrozenLibIntegration(), WilderSharedConstants.MOD_ID);

    private WilderModIntegrations() {
        throw new UnsupportedOperationException("WilderModIntegrations contains only static declarations.");
    }

    public static void init() {
    }
}
