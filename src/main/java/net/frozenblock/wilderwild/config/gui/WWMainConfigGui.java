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

// TODO: Re-enable when cloth config is unobfuscated

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
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
			WWEntityConfig.INSTANCE.save();
			WWItemConfig.CONFIG.save();
			WWWorldgenConfig.INSTANCE.save();
			WWAmbienceAndMiscConfig.CONFIG.save();
			WWMixinsConfig.INSTANCE.save();
		});

		final ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
		final ConfigCategory block = configBuilder.getOrCreateCategory(text("block"));
		WWBlockConfigGui.setupEntries(block, entryBuilder);
		final ConfigCategory entity = configBuilder.getOrCreateCategory(text("entity"));
		WWEntityConfigGui.setupEntries(entity, entryBuilder);
		final ConfigCategory item = configBuilder.getOrCreateCategory(text("item"));
		WWItemConfigGui.setupEntries(item, entryBuilder);
		final ConfigCategory worldgen = configBuilder.getOrCreateCategory(text("worldgen"));
		WWWorldgenConfigGui.setupEntries(worldgen, entryBuilder);
		final ConfigCategory misc = configBuilder.getOrCreateCategory(text("misc"));
		WWAmbienceAndMiscConfigGui.setupEntries(misc, entryBuilder);

		return configBuilder.build();
	}

}
