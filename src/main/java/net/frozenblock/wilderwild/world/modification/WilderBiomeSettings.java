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

package net.frozenblock.wilderwild.world.modification;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.config.AmbienceAndMiscConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biomes;

public final class WilderBiomeSettings {

	static void init() {
		BiomeModifications.create(WilderSharedConstants.id("fog_deep_dark")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.DEEP_DARK),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().biomeAmbience.deepDarkFog) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setFogColor(0);
				}
			});

		BiomeModifications.create(WilderSharedConstants.id("fog_frozen_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(RegisterWorldgen.FROZEN_CAVES),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().biomeAmbience.frozenCavesFog) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setFogColor(0);
				}
			});

		BiomeModifications.create(WilderSharedConstants.id("fog_jellyfish_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(RegisterWorldgen.JELLYFISH_CAVES),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().biomeAmbience.jellyfishCavesFog) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setFogColor(0);
				}
			});

		BiomeModifications.create(WilderSharedConstants.id("fog_magmatic_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(RegisterWorldgen.MAGMATIC_CAVES),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().biomeAmbience.magmaticCavesFog) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setFogColor(0);
				}
			});

		BiomeModifications.create(WilderSharedConstants.id("particles_magmatic_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(RegisterWorldgen.MAGMATIC_CAVES),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().biomeAmbience.magmaticCavesParticles) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setParticleConfig(new AmbientParticleSettings(ParticleTypes.LARGE_SMOKE, 0.00123F));
				}
			});

		BiomeModifications.create(WilderSharedConstants.id("foliage_color_badlands")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(BiomeTags.IS_BADLANDS),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().vegetationColors.badlandsFoliage) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setFoliageColor(11445290);
				}
			});

		WilderMusic.playMusic();
		WilderWaterColors.stirWater();
		WilderSpawns.addFireflies();
		WilderSpawns.addJellyfish();
		WilderSpawns.addCrabs();
		WilderSpawns.addOstriches();
		WilderSpawns.addTumbleweed();
		WilderSpawns.addRabbits();
	}
}
