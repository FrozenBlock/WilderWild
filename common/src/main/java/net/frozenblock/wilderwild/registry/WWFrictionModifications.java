package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.block.api.friction.BlockFrictionModification;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.tag.WWBlockTags;

public final class WWFrictionModifications {

	public static void init() {
		BlockFrictionModification.MODIFY.register(ctx -> {
			if (ctx.entity instanceof Penguin && ctx.state.is(WWBlockTags.PENGUIN_IGNORE_FRICTION)) ctx.friction = 0.6F;
		});
	}

	private WWFrictionModifications() {}
}
