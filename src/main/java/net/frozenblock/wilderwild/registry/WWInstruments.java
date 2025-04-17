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
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.registry;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Instruments;

public class WWInstruments {

	public static void bootstrap(BootstrapContext<Instrument> registry) {
		Instruments.register(registry, WWItems.RECORDER_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_RECORDER, 32767, 64F);
		Instruments.register(registry, WWItems.FLUTE_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_FLUTE, 32767, 64F);
		Instruments.register(registry, WWItems.OBOE_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_OBOE, 32767, 64F);
		Instruments.register(registry, WWItems.CLARINET_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_CLARINET, 32767, 64F);
		Instruments.register(registry, WWItems.SAX_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_SAX, 32767, 64F);
		Instruments.register(registry, WWItems.TRUMPET_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_TRUMPET, 32767, 64F);
		Instruments.register(registry, WWItems.TROMBONE_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_TROMBONE, 32767, 64F);
		Instruments.register(registry, WWItems.TUBA_COPPER_HORN, WWSounds.ITEM_COPPER_HORN_TUBA, 32767, 64F);
	}
}
