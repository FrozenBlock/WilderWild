package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;

public class FrozenLibIntegration extends ModIntegration {
    public FrozenLibIntegration() {
        super("frozenlib");
    }

    @Override
    public void init() {
        WilderSharedConstants.log("FrozenLib mod integration ran!", true);
    }
}
