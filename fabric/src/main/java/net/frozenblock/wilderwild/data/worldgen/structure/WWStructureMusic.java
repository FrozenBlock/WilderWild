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

package net.frozenblock.wilderwild.data.worldgen.structure;

import java.util.List;
import java.util.Optional;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.music.api.structure.StructureMusic;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

public final class WWStructureMusic {
	public static final ResourceKey<StructureMusic> ANCIENT_CITY = createKey("ancient_city");

	public static void bootstrap(BootstrapContext<StructureMusic> context) {
		StructureMusic.register(
			context,
			ANCIENT_CITY,
			List.of(BuiltinStructures.ANCIENT_CITY.identifier()),
			new BackgroundMusic(Optional.of(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DEEP_DARK)), Optional.of(Musics.CREATIVE), Optional.empty()),
			false,
			ConfigPredicate.equalTo(WWAmbienceAndMiscConfig.ANCIENT_CITY_MUSIC, true)
		);
	}

	private static ResourceKey<StructureMusic> createKey(String name) {
		return StructureMusic.createKey(WWConstants.id(name));
	}

	private WWStructureMusic() {}
}
