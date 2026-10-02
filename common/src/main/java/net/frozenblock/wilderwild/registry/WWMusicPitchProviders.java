/*
 * Copyright 2026 FrozenBlock
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

import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.music.pitch.provider.PitchProvider;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public final class WWMusicPitchProviders {

	public static void bootstrap(BootstrapContext<PitchProvider> context) {
		final HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);

		context.register(
			createKey("dying_forest_distortion"),
			PitchProvider.whenTrue(
				configPredicates.getOrThrow(WWConfigPredicates.MUSIC_PITCH_SHIFT_DYING_FORESTS),
				PitchProvider.biomes(
					biomes.getOrThrow(WWBiomeTags.MUSIC_PITCH_SHIFT_DYING_FOREST),
					PitchProvider.sine(2400F, 0.015F, 0.98F)
				)
			)
		);
	}

	private static ResourceKey<PitchProvider> createKey(String name) {
		return ResourceKey.create(FrozenLibRegistries.MUSIC_PITCH_PROVIDER, WWConstants.id(name));
	}

	private WWMusicPitchProviders() {}
}
