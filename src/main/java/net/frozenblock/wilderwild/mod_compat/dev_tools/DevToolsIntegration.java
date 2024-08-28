package net.frozenblock.wilderwild.mod_compat.dev_tools;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.devtools.DevToolsClient;
import net.frozenblock.wilderwild.mod_compat.dev_tools.client.OstrichDebugRenderer;
import net.minecraft.client.Minecraft;

public class DevToolsIntegration extends AbstractDevToolsIntegration {

	public DevToolsIntegration() {
		super();
	}

	@Override
	public void init() {
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void clientInitPreFreeze() {
		DevToolsClient.addRenderer("Ostrich", new OstrichDebugRenderer(Minecraft.getInstance())::render);
	}
}
