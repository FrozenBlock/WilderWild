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

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.config.ItemConfig;
import net.frozenblock.wilderwild.config.AmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.MixinsConfig;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.text;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

public final class MainConfigGui {

	public static Screen buildScreen(@NotNull Screen parent) {
		var configBuilder = ConfigBuilder.create().setParentScreen(parent).setTitle(text("component.title"));
		configBuilder.setSavingRunnable(() -> {
			BlockConfig.INSTANCE.save();
			EntityConfig.INSTANCE.save();
			ItemConfig.INSTANCE.save();
			WorldgenConfig.INSTANCE.save();
			AmbienceAndMiscConfig.INSTANCE.save();
			MixinsConfig.INSTANCE.save();
		});

		ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();

		var block = configBuilder.getOrCreateCategory(text("block"));
		BlockConfigGui.setupEntries(block, entryBuilder);
		var entity = configBuilder.getOrCreateCategory(text("entity"));
		EntityConfigGui.setupEntries(entity, entryBuilder);
		var item = configBuilder.getOrCreateCategory(text("item"));
		ItemConfigGui.setupEntries(item, entryBuilder);
		var worldgen = configBuilder.getOrCreateCategory(text("worldgen"));
		WorldgenConfigGui.setupEntries(worldgen, entryBuilder);
		var misc = configBuilder.getOrCreateCategory(text("misc"));
		AmbienceAndMiscConfigGui.setupEntries(misc, entryBuilder);
		return configBuilder.build();
	}

}
