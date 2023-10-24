/*
 * Copyright 2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.wilderwild.tag.WilderGameEventTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.GameEventTags;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

final class WWGameEventTagProvider extends FabricTagProvider.GameEventTagProvider {

	public WWGameEventTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(WilderGameEventTags.CRAB_CAN_ALWAYS_DETECT)
			.add(GameEvent.EXPLODE)
			.add(GameEvent.PRIME_FUSE)
			.add(GameEvent.BLOCK_PLACE)
			.add(GameEvent.BLOCK_DESTROY)
			.add(GameEvent.PROJECTILE_LAND)
			.add(GameEvent.FLUID_PICKUP)
			.add(GameEvent.FLUID_PLACE);

		this.getOrCreateTagBuilder(WilderGameEventTags.CRAB_CAN_DETECT)
			.addOptionalTag(WilderGameEventTags.CRAB_CAN_ALWAYS_DETECT)
			.addOptionalTag(GameEventTags.VIBRATIONS);
	}
}
