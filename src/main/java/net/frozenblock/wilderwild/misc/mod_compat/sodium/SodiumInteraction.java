package net.frozenblock.wilderwild.misc.mod_compat.sodium;

import me.jellysquid.mods.sodium.client.render.chunk.passes.BlockRenderPass;
import net.minecraft.client.renderer.RenderType;

public interface SodiumInteraction {

	void frozenAddMapping(RenderType layer, BlockRenderPass type);
}
