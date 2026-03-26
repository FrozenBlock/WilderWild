/*
 * Copyright 2025-2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.timeline.Timeline;

public final class WWTimelines {
	public static final ResourceKey<Timeline> WILDERWILD_DAY = key("wilderwild_day");

	private WWTimelines() {
		throw new UnsupportedOperationException("WWTimelines contains only static declarations.");
	}

	public static void init() {}

	public static void bootstrap(BootstrapContext<Timeline> context) {
		final HolderGetter<WorldClock> clocks = context.lookup(Registries.WORLD_CLOCK);
		final Holder.Reference<WorldClock> overworldClock = clocks.getOrThrow(WorldClocks.OVERWORLD);
		context.register(
			WILDERWILD_DAY,
			Timeline.builder(overworldClock)
				.setPeriodTicks(24000)
				.addTrack(WWEnvironmentAttributes.PALE_MUSHROOM_ACTIVE, builder -> builder.addKeyframe(12600, true).addKeyframe(23401, false))
				.addTrack(WWEnvironmentAttributes.PLANKTON_GLOWING, builder -> builder.addKeyframe(12600, true).addKeyframe(23401, false))
				.addTrack(WWEnvironmentAttributes.SEA_ANEMONE_GLOWING, builder -> builder.addKeyframe(12600, false).addKeyframe(23401, true))
				.addTrack(WWEnvironmentAttributes.OSTRICH_EGG_HATCH_CHANCE, builder -> builder.addKeyframe(12600, 1F / 300F).addKeyframe(23401, 1F / 18F))
				.build()
		);
	}

	private static ResourceKey<Timeline> key(String path) {
		return ResourceKey.create(Registries.TIMELINE, WWConstants.id(path));
	}

}
