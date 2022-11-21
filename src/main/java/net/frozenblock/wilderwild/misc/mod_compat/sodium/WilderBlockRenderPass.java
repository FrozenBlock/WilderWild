package net.frozenblock.wilderwild.misc.mod_compat.sodium;

import me.jellysquid.mods.sodium.client.render.chunk.passes.BlockRenderPass;

public final class WilderBlockRenderPass {

	static {
		BlockRenderPass.values();
	}

	public static BlockRenderPass WILDERTRANSLUCENT;
}
