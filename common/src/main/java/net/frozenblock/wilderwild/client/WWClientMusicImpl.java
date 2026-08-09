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

package net.frozenblock.wilderwild.client;

import java.util.function.Function;
import net.frozenblock.lib.music.api.client.pitch.MusicPitchApi;
import net.frozenblock.lib.resource.client.api.pack.ModResourcePackApi;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.util.Mth;

@ClientOnly
public final class WWClientMusicImpl {
	private static final String MUSIC_PACK_SUFFIX = "_v2";

	public static void init() {
		ModResourcePackApi.downloadResourcePacks(createMusicDownloadGroup(), true, false);

		final Function<Long, Float> dyingPitchShifting = time -> WWAmbienceAndMiscConfig.DISTORTED_DYING_FOREST_MUSIC.get() ?
			(0.98F + Mth.sin((float) ((time * Math.PI) / 1200F)) * 0.025F) : 1F;
		MusicPitchApi.registerForBiome(WWBiomes.DYING_FOREST.identifier(), dyingPitchShifting);
		MusicPitchApi.registerForBiome(WWBiomes.DYING_MIXED_FOREST.identifier(), dyingPitchShifting);
		MusicPitchApi.registerForBiome(WWBiomes.SNOWY_DYING_FOREST.identifier(), dyingPitchShifting);
		MusicPitchApi.registerForBiome(WWBiomes.SNOWY_DYING_MIXED_FOREST.identifier(), dyingPitchShifting);
	}

	private static ModResourcePackApi.PackDownloadGroup createMusicDownloadGroup() {
		final ModResourcePackApi.PackDownloadGroup downloadGroup = ModResourcePackApi.PackDownloadGroup.create("wilderwild_music");
		addMusicDownloadInfo(downloadGroup, "better_days_ahead");
		addMusicDownloadInfo(downloadGroup, "dove");
		addMusicDownloadInfo(downloadGroup, "horizon_afoot");
		addMusicDownloadInfo(downloadGroup, "serene_sonder");
		addMusicDownloadInfo(downloadGroup, "amber");
		addMusicDownloadInfo(downloadGroup, "anemone");
		addMusicDownloadInfo(downloadGroup, "dahlia");
		addMusicDownloadInfo(downloadGroup, "espial_title_mix");
		addMusicDownloadInfo(downloadGroup, "espial");
		addMusicDownloadInfo(downloadGroup, "molt");
		addMusicDownloadInfo(downloadGroup, "frozen_blocks");
		addMusicDownloadInfo(downloadGroup, "pressure");

		if (WWAmbienceAndMiscConfig.DAN_MUSIC.get()) {
			addMusicDownloadInfo(downloadGroup, "excuse");
			addMusicDownloadInfo(downloadGroup, "flake");
		}

		return downloadGroup;
	}

	private static void addMusicDownloadInfo(ModResourcePackApi.PackDownloadGroup downloadGroup, String trackName) {
		downloadGroup.add(
			"https://raw.githubusercontent.com/FrozenBlock/PackRepo/refs/heads/master/wilderwild/" + trackName + MUSIC_PACK_SUFFIX + ".json",
			"ww_" + trackName
		);
	}
}
