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

package net.frozenblock.wilderwild.worldgen.modification;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biomes;
import java.util.List;

public final class WWBiomeSettings {

	static void init() {
		BiomeModifications.create(WWConstants.id("fog_deep_dark")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.DEEP_DARK),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().biomeAmbience.deepDarkFog) return;
				setFogColor(modificationContext, 0);
			});

		BiomeModifications.create(WWConstants.id("fog_frozen_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.FROZEN_CAVES),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().biomeAmbience.frozenCavesFog) return;
				setFogColor(modificationContext, 0);
			});

		BiomeModifications.create(WWConstants.id("fog_mesoglea_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.MESOGLEA_CAVES),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().biomeAmbience.mesogleaCavesFog) return;
				setFogColor(modificationContext, 0);
			});

		BiomeModifications.create(WWConstants.id("fog_magmatic_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.MAGMATIC_CAVES),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().biomeAmbience.magmaticCavesFog) return;
				setFogColor(modificationContext, 0);
			});

		BiomeModifications.create(WWConstants.id("particles_magmatic_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.MAGMATIC_CAVES),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().biomeAmbience.magmaticCavesParticles) return;
				modificationContext.getAttributes().set(
					EnvironmentAttributes.AMBIENT_PARTICLES,
					List.of(new AmbientParticle(ParticleTypes.LARGE_SMOKE, 0.00123F))
				);
			});

		BiomeModifications.create(WWConstants.id("foliage_color_badlands")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(BiomeTags.IS_BADLANDS),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().vegetationColors.badlandsFoliage) return;
				modificationContext.getEffects().setFoliageColor(11445290);
			});

		WWBiomeMusicAndAmbience.playMusic();
		WWWaterColors.stirWater();
		WWSpawns.addBugs();
		WWSpawns.addJellyfish();
		WWSpawns.addCrabs();
		WWSpawns.addOstriches();
		WWSpawns.addPenguins();
		WWSpawns.addTumbleweed();
		WWSpawns.addRabbits();
		WWSpawns.addMooblooms();
	}

	private static void setFogColor(BiomeModificationContext context, int color) {
		context.getAttributes().set(EnvironmentAttributes.FOG_COLOR, color);
	}
}
