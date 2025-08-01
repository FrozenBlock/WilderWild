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

package net.frozenblock.wilderwild.client;

import java.util.function.Function;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.music.api.client.pitch.MusicPitchApi;
import net.frozenblock.lib.music.api.client.structure.StructureMusic;
import net.frozenblock.lib.music.api.client.structure.StructureMusicApi;
import net.frozenblock.lib.resource_pack.api.client.FrozenLibModResourcePackApi;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class WWClientMusicImpl {

	public static void init() {
		FrozenLibModResourcePackApi.downloadResourcePacks(createMusicDownloadGroup(), true, false);

		Function<Long, Float> dyingPitchShifting = l -> WWAmbienceAndMiscConfig.Client.DISTORTED_DYING_FOREST_MUSIC ?
			(0.98F + Mth.sin((float) ((l * Math.PI) / 1200F)) * 0.025F) : 1F;
		MusicPitchApi.registerForBiome(WWBiomes.DYING_FOREST.location(), dyingPitchShifting);
		MusicPitchApi.registerForBiome(WWBiomes.DYING_MIXED_FOREST.location(), dyingPitchShifting);
		MusicPitchApi.registerForBiome(WWBiomes.SNOWY_DYING_FOREST.location(), dyingPitchShifting);
		MusicPitchApi.registerForBiome(WWBiomes.SNOWY_DYING_MIXED_FOREST.location(), dyingPitchShifting);

		if (WWAmbienceAndMiscConfig.get().music.ancientCityMusic) {
			StructureMusicApi.registerMusicForStructure(
				BuiltinStructures.ANCIENT_CITY,
				new StructureMusic(
					new Music(SoundEvents.MUSIC_BIOME_DEEP_DARK, 6000, 12000, false),
					false
				)
			);
		}
	}

	private static FrozenLibModResourcePackApi.PackDownloadGroup createMusicDownloadGroup() {
		FrozenLibModResourcePackApi.PackDownloadGroup musicDownloadGroup = FrozenLibModResourcePackApi.PackDownloadGroup.create("wilderwild_music");
		addMusicDownloadInfo(musicDownloadGroup, "dove", "_v1");
		addMusicDownloadInfo(musicDownloadGroup, "horizon_afoot", "_v1");
		addMusicDownloadInfo(musicDownloadGroup, "serene_sonder", "_v1");
		addMusicDownloadInfo(musicDownloadGroup, "amber", "_v1");
		addMusicDownloadInfo(musicDownloadGroup, "anemone", "_v1");
		addMusicDownloadInfo(musicDownloadGroup, "dahlia", "_v1");
		addMusicDownloadInfo(musicDownloadGroup, "espial_title_mix", "_v1");
		addMusicDownloadInfo(musicDownloadGroup, "espial", "_v1");
		addMusicDownloadInfo(musicDownloadGroup, "molt", "_v1");
		return musicDownloadGroup;
	}

	private static void addMusicDownloadInfo(@NotNull FrozenLibModResourcePackApi.PackDownloadGroup downloadGroup, String trackName, String packSuffix) {
		downloadGroup.add(
			"https://raw.githubusercontent.com/FrozenBlock/PackRepo/refs/heads/master/wilderwild/" + trackName + packSuffix + ".json",
			"ww_" + trackName
		);
	}
}
