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

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import static net.frozenblock.wilderwild.WWConstants.text;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.config.WWMixinsConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.minecraft.client.gui.screens.Screen;

public final class WWMainConfigGui {

	public static Screen buildScreen(Screen parent) {
		final ConfigBuilder configBuilder = ConfigBuilder.create().setParentScreen(parent).setTitle(text("component.title"));
		configBuilder.setSavingRunnable(() -> {
			WWBlockConfig.CONFIG.save();
			WWEntityConfig.CONFIG.save();
			WWItemConfig.CONFIG.save();
			WWWorldgenConfig.CONFIG.save();
			WWAmbienceAndMiscConfig.CONFIG.save();
			WWMixinsConfig.INSTANCE.save();
		});

		final ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
		WWBlockConfigGui.setupEntries(configBuilder.getOrCreateCategory(text("block")), entryBuilder);
		WWEntityConfigGui.setupEntries(configBuilder.getOrCreateCategory(text("entity")), entryBuilder);
		WWItemConfigGui.setupEntries(configBuilder.getOrCreateCategory(text("item")), entryBuilder);
		WWWorldgenConfigGui.setupEntries(configBuilder.getOrCreateCategory(text("worldgen")), entryBuilder);
		WWAmbienceAndMiscConfigGui.setupEntries(configBuilder.getOrCreateCategory(text("misc")), entryBuilder);

		return configBuilder.build();
	}

}
