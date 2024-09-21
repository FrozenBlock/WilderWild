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

package net.frozenblock.wilderwild.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.FrozenBiome;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Humidity;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.worldgen.WWSharedWorldgen;
import net.frozenblock.wilderwild.worldgen.feature.placed.WWCavePlaced;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MesogleaCaves extends FrozenBiome {
	public static final Climate.Parameter TEMPERATURE = Temperature.FULL_RANGE;
	public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.DRY, Humidity.HUMID);
	public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(-1.200F, -0.749F);
	public static final Climate.Parameter EROSION = Climate.Parameter.span(Erosion.EROSION_4, Erosion.EROSION_6);
	public static final Climate.Parameter WEIRDNESS = Weirdness.FULL_RANGE;
	public static final float OFFSET = 0.000F;
	public static final float TEMP = 0.4F;
	public static final float DOWNFALL = 0.4F;
	public static final int WATER_COLOR = 9817343;
	public static final int WATER_FOG_COLOR = 6069471;
	public static final int FOG_COLOR = WWSharedWorldgen.STOCK_FOG_COLOR;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(0F);
	public static final MesogleaCaves INSTANCE = new MesogleaCaves();

	@Override
	public String modID() {
		return WWConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "mesoglea_caves";
	}

	@Override
	public float temperature() {
		return TEMP;
	}

	@Override
	public float downfall() {
		return DOWNFALL;
	}

	@Override
	public boolean hasPrecipitation() {
		return true;
	}

	@Override
	public int skyColor() {
		return SKY_COLOR;
	}

	@Override
	public int fogColor() {
		return FOG_COLOR;
	}

	@Override
	public int waterColor() {
		return WATER_COLOR;
	}

	@Override
	public int waterFogColor() {
		return WATER_FOG_COLOR;
	}

	@Override
	public @Nullable Integer foliageColorOverride() {
		return null;
	}

	@Override
	public @Nullable Integer grassColorOverride() {
		return null;
	}

	@Override
	public @Nullable AmbientParticleSettings ambientParticleSettings() {
		return null;
	}

	@Override
	public @Nullable Holder<SoundEvent> ambientLoopSound() {
		return null;
	}

	@Override
	public @NotNull AmbientMoodSettings ambientMoodSettings() {
		return AmbientMoodSettings.LEGACY_CAVE_SETTINGS;
	}

	@Override
	public @Nullable AmbientAdditionsSettings ambientAdditionsSound() {
		return null;
	}

	@Override
	public @NotNull Music backgroundMusic() {
		return Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_MESOGLEA_CAVES);
	}

	@Override
	public void addFeatures(@NotNull BiomeGenerationSettings.Builder features) {
		BiomeDefaultFeatures.addDefaultCrystalFormations(features);
		BiomeDefaultFeatures.addDefaultMonsterRoom(features);
		features.addFeature(GenerationStep.Decoration.LAKES, WWCavePlaced.MESOGLEA_CAVES_STONE_POOL.getKey());
		BiomeDefaultFeatures.addDefaultUndergroundVariety(features);
		BiomeDefaultFeatures.addSurfaceFreezing(features);
		BiomeDefaultFeatures.addPlainGrass(features);
		BiomeDefaultFeatures.addDefaultOres(features, true);
		BiomeDefaultFeatures.addDefaultSoftDisks(features);
		BiomeDefaultFeatures.addPlainVegetation(features);
		BiomeDefaultFeatures.addDefaultMushrooms(features);
		BiomeDefaultFeatures.addDefaultExtraVegetation(features);
		BiomeDefaultFeatures.addDefaultCarversAndLakes(features);
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.BLUE_MESOGLEA.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.PURPLE_MESOGLEA.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.UPSIDE_DOWN_BLUE_MESOGLEA.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.UPSIDE_DOWN_PURPLE_MESOGLEA.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWCavePlaced.LARGE_MESOGLEA_PURPLE.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWCavePlaced.LARGE_MESOGLEA_BLUE.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWCavePlaced.MESOGLEA_CLUSTER_PURPLE.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWCavePlaced.MESOGLEA_CLUSTER_BLUE.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.MESOGLEA_PILLAR.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.PURPLE_MESOGLEA_PILLAR.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWCavePlaced.BLUE_MESOGLEA_PATH.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWCavePlaced.PURPLE_MESOGLEA_PATH.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWCavePlaced.ORE_CALCITE.getKey());
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWCavePlaced.NEMATOCYST_BLUE.getKey());
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWCavePlaced.NEMATOCYST_PURPLE.getKey());
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.commonSpawns(spawns);
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters) {
		if (WWWorldgenConfig.get().biomeGeneration.generateMesogleaCaves) {
			this.addSemiDeepBiome(
				parameters,
				TEMPERATURE,
				HUMIDITY,
				CONTINENTALNESS,
				EROSION,
				WEIRDNESS,
				OFFSET
			);
			this.addUndergroundBiome(
				parameters,
				TEMPERATURE,
				HUMIDITY,
				CONTINENTALNESS,
				EROSION,
				WEIRDNESS,
				OFFSET
			);
		}
	}

}
