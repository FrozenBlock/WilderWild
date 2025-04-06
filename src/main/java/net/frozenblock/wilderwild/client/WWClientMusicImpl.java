/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.music.api.client.pitch.MusicPitchApi;
import net.frozenblock.lib.music.api.client.structure.StructureMusicInfo;
import net.frozenblock.lib.music.api.client.structure.StructureMusicApi;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.minecraft.client.sounds.MusicInfo;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public final class WWClientMusicImpl {

	public static void addMusicChanges() {
		Function<Long, Float> dyingPitchShifting = (l) -> WWAmbienceAndMiscConfig.Client.DISTORTED_DYING_FOREST_MUSIC ?
			(0.98F + Mth.sin((float) ((l * Math.PI) / 1200F)) * 0.025F) : 1F;
		MusicPitchApi.registerForBiome(WWBiomes.DYING_FOREST.location(), dyingPitchShifting);
		MusicPitchApi.registerForBiome(WWBiomes.DYING_MIXED_FOREST.location(), dyingPitchShifting);
		MusicPitchApi.registerForBiome(WWBiomes.SNOWY_DYING_FOREST.location(), dyingPitchShifting);
		MusicPitchApi.registerForBiome(WWBiomes.SNOWY_DYING_MIXED_FOREST.location(), dyingPitchShifting);

		if (WWAmbienceAndMiscConfig.get().music.ancientCityMusic) {
			StructureMusicApi.registerMusicInfoForStructure(
				BuiltinStructures.ANCIENT_CITY,
				new StructureMusicInfo(
					new MusicInfo(new Music(SoundEvents.MUSIC_BIOME_DEEP_DARK, 6000, 12000, false)),
					false
				)
			);
		}
	}
}
