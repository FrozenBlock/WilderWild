/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.frozenblock.wilderwild.registry.WWGameEvents;
import net.frozenblock.wilderwild.tag.WWGameEventTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.GameEventTagsProvider;
import net.minecraft.tags.GameEventTags;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public final class WWGameEventTagProvider extends GameEventTagsProvider {

	public WWGameEventTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.tag(WWGameEventTags.CRAB_CAN_ALWAYS_DETECT)
			.add(GameEvent.EXPLODE.key())
			.add(GameEvent.PRIME_FUSE.key())
			.add(GameEvent.BLOCK_PLACE.key())
			.add(GameEvent.BLOCK_DESTROY.key())
			.add(GameEvent.PROJECTILE_LAND.key())
			.add(GameEvent.FLUID_PICKUP.key())
			.add(GameEvent.FLUID_PLACE.key());

		this.tag(WWGameEventTags.CRAB_CAN_DETECT)
			.addTag(WWGameEventTags.CRAB_CAN_ALWAYS_DETECT)
			.addOptionalTag(GameEventTags.VIBRATIONS.location());

		this.tag(WWGameEventTags.MAKES_ICICLE_FALL)
			.add(GameEvent.EXPLODE.key())
			.add(GameEvent.PROJECTILE_LAND.key())
			.add(WWGameEvents.BIG_FALL.key());
	}
}
