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

package net.frozenblock.wilderwild.worldgen.modification;

import java.util.List;
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

public final class WWBiomeSettings {

	static void init() {
		BiomeModifications.create(WWConstants.id("fog_deep_dark")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.DEEP_DARK),
			context -> {
				if (!WWAmbienceAndMiscConfig.DEEP_DARK_FOG.get()) return;
				setFogColor(context, 0);
			});

		BiomeModifications.create(WWConstants.id("fog_dripstone_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.DRIPSTONE_CAVES),
			context -> {
				if (!WWAmbienceAndMiscConfig.DRIPSTONE_CAVES_FOG.get()) return;
				setFogColor(context, 0);
			});

		BiomeModifications.create(WWConstants.id("fog_lush_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.LUSH_CAVES),
			context -> {
				if (!WWAmbienceAndMiscConfig.MESOGLEA_CAVES_FOG.get()) return;
				setFogColor(context, 0);
			});

		BiomeModifications.create(WWConstants.id("fog_sulfur_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.SULFUR_CAVES),
			context -> {
				if (!WWAmbienceAndMiscConfig.SULFUR_CAVES_FOG.get()) return;
				setFogColor(context, 0);
			});

		BiomeModifications.create(WWConstants.id("fog_frozen_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.FROZEN_CAVES),
			context -> {
				if (!WWAmbienceAndMiscConfig.FROZEN_CAVES_FOG.get()) return;
				setFogColor(context, 0);
			});

		BiomeModifications.create(WWConstants.id("fog_mesoglea_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.MESOGLEA_CAVES),
			context -> {
				if (!WWAmbienceAndMiscConfig.MESOGLEA_CAVES_FOG.get()) return;
				setFogColor(context, 0);
			});

		BiomeModifications.create(WWConstants.id("fog_magmatic_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.MAGMATIC_CAVES),
			context -> {
				if (!WWAmbienceAndMiscConfig.MAGMATIC_CAVES_FOG.get()) return;
				setFogColor(context, 0);
			});

		BiomeModifications.create(WWConstants.id("particles_magmatic_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.MAGMATIC_CAVES),
			context -> {
				if (!WWAmbienceAndMiscConfig.MAGMATIC_CAVES_PARTICLES.get()) return;
				context.getAttributes().set(
					EnvironmentAttributes.AMBIENT_PARTICLES,
					List.of(new AmbientParticle(ParticleTypes.LARGE_SMOKE, 0.00123F))
				);
			});

		BiomeModifications.create(WWConstants.id("foliage_color_badlands")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(BiomeTags.IS_BADLANDS),
			context -> {
				if (!WWAmbienceAndMiscConfig.BADLANDS_FOLIAGE_COLOR.get()) return;
				context.getEffects().setFoliageColorOverride(11445290);
			});

		WWBiomeMusicAndAmbience.init();
		WWWaterColors.init();
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
