package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.block.api.friction.BlockFrictionAPI;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.tag.WWBlockTags;

public class WWFrictionModifications {

	public static void init() {
		BlockFrictionAPI.MODIFICATIONS.register(ctx -> {
			if (ctx.entity instanceof Penguin && ctx.state.is(WWBlockTags.PENGUIN_IGNORE_FRICTION)) ctx.friction = 0.6F;
		});
	}
}
