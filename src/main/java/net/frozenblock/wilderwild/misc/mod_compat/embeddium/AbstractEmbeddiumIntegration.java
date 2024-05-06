package net.frozenblock.wilderwild.misc.mod_compat.embeddium;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.integration.api.ModIntegration;

public class AbstractEmbeddiumIntegration extends ModIntegration {

	public AbstractEmbeddiumIntegration() {
		super("embeddium");
	}

	@Override
	public void init() {
	}

	@Environment(EnvType.CLIENT)
	public void clientInit() {
	}
}
