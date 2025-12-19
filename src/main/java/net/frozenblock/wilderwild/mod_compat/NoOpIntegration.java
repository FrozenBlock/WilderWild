package net.frozenblock.wilderwild.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;

public class NoOpIntegration extends ModIntegration {
	public NoOpIntegration(String modID) {
		super(modID);
	}

	@Override
	public void init() {
	}
}
