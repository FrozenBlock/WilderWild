/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.tag.WWInstrumentTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Instrument;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public final class WWInstrumentTagProvider extends FabricTagProvider<Instrument> {

	public WWInstrumentTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, Registries.INSTRUMENT, registries);
	}

	@Override
	public void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(WWInstrumentTags.COPPER_HORNS)
			.add(WWItems.RECORDER_COPPER_HORN)
			.add(WWItems.FLUTE_COPPER_HORN)
			.add(WWItems.OBOE_COPPER_HORN)
			.add(WWItems.CLARINET_COPPER_HORN)
			.add(WWItems.SAX_COPPER_HORN)
			.add(WWItems.TRUMPET_COPPER_HORN)
			.add(WWItems.TROMBONE_COPPER_HORN)
			.add(WWItems.TUBA_COPPER_HORN);
	}
}
