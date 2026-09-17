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

import net.frozenblock.lib.entity.api.suffocation.AirBehavior;
import net.frozenblock.lib.entity.api.suffocation.MeterStyle;
import net.frozenblock.lib.entity.api.suffocation.SuffocationTypes;
import net.frozenblock.lib.entity.impl.suffocation.SuffocationType;
import net.frozenblock.lib.platform.api.registry.DeferredBlock;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public final class WWSuffocationTypes {

	public static void bootstrap(BootstrapContext<SuffocationType> context) {
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		registerMesoglea(context, WWBlocks.PEARLESCENT_BLUE_MESOGLEA, "pearlescent_blue");
		registerMesoglea(context, WWBlocks.PEARLESCENT_PURPLE_MESOGLEA, "pearlescent_purple");
		registerMesoglea(context, WWBlocks.YELLOW_MESOGLEA, "yellow");
		registerMesoglea(context, WWBlocks.BLUE_MESOGLEA, "blue");
		registerMesoglea(context, WWBlocks.LIME_MESOGLEA, "lime");
		registerMesoglea(context, WWBlocks.RED_MESOGLEA, "red");
		registerMesoglea(context, WWBlocks.PINK_MESOGLEA, "pink");
	}

	private static void registerMesoglea(BootstrapContext<SuffocationType> context, DeferredBlock<? extends Block> sourceBlock, String color) {
		SuffocationTypes.register(
			context,
			createKey("mesoglea_" + color),
			SuffocationType.builder(MeterStyle.DRAIN, 15, 300)
				.sourceBlocks(HolderSet.direct(sourceBlock.asHolder()))
				.airBehavior(AirBehavior.DISPLAY_ONLY)
				.capacity(300)
				.meter(
					WWConstants.id("hud/mesoglea_bubble_" + color),
					null,
					WWConstants.id("hud/mesoglea_bubble_empty_" + color),
					WWConstants.id("hud/mesoglea_bubble_bursting_" + color)
				)
		);
	}

	private static ResourceKey<SuffocationType> createKey(String name) {
		return ResourceKey.create(FrozenLibRegistries.SUFFOCATION_TYPE, WWConstants.id(name));
	}

	private WWSuffocationTypes() {}
}
