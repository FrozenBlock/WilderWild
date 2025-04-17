/*
 * Copyright 2025 FrozenBlock
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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.tag.WWInstrumentTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Instrument;
import org.jetbrains.annotations.NotNull;

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
