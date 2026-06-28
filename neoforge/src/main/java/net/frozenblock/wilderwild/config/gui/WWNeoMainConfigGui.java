package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.config.WWMixinsConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.minecraft.client.gui.screens.Screen;
import static net.frozenblock.wilderwild.WWConstants.text;

public final class WWNeoMainConfigGui {

	public static Screen buildScreen(Screen parent) {
		final ConfigBuilder configBuilder = ConfigBuilder.create().setParentScreen(parent).setTitle(text("components.title"));
		configBuilder.setSavingRunnable(() -> {
			WWBlockConfig.CONFIG.save();
			WWEntityConfig.CONFIG.save();
			WWItemConfig.CONFIG.save();
			WWWorldgenConfig.CONFIG.save();
			WWAmbienceAndMiscConfig.CONFIG.save();
			WWMixinsConfig.INSTANCE.save();
		});

		final ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
		//WWBlockConfigGui.setupEntries(configBuilder.getOrCreateCategory(text("block")), entryBuilder);
		//WWEntityConfigGui.setupEntries(configBuilder.getOrCreateCategory(text("entity")), entryBuilder);
		WWItemConfigGui.setupEntries(configBuilder.getOrCreateCategory(text("item")), entryBuilder);
		//WWWorldgenConfigGui.setupEntries(configBuilder.getOrCreateCategory(text("worldgen")), entryBuilder);
		WWAmbienceAndMiscConfigGui.setupEntries(configBuilder.getOrCreateCategory(text("misc")), entryBuilder);

		return configBuilder.build();
	}
}
