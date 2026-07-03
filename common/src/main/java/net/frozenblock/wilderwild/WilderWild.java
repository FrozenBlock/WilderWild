package net.frozenblock.wilderwild;

import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWActivities;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.frozenblock.wilderwild.registry.WWEnvironmentAttributes;
import net.frozenblock.wilderwild.registry.WWGameEvents;
import net.frozenblock.wilderwild.registry.WWSoundTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;

public final class WilderWild {

	public static void init() {
		WilderWildRegistries.init();
		WWFeatureFlags.init();

		WWAttachmentTypes.init();
		WWActivities.init();
		WWEnvironmentAttributes.init();

		WWGameEvents.init();
		WWSounds.init();

		WWAmbienceAndMiscConfig.CONFIG.load(true);
		WWBlockConfig.CONFIG.load(true);
		WWEntityConfig.CONFIG.load(true);
		WWItemConfig.CONFIG.load(true);
		WWWorldgenConfig.CONFIG.load(true);
	}
}
