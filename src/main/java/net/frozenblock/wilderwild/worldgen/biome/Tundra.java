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

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.FrozenBiome;
import net.frozenblock.lib.worldgen.biome.api.FrozenGrassColorModifiers;
import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.worldgen.WWSharedWorldgen;
import net.frozenblock.wilderwild.worldgen.feature.placed.WWPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Tundra extends FrozenBiome {
	public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.45F, -0.255F);
	public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(-1F, -0.2F);
	public static final Climate.Parameter WEIRDNESS_A = Climate.Parameter.span(-0.4F, -0.05F);
	public static final Climate.Parameter WEIRDNESS_B = Climate.Parameter.span(0.05F, 0.4F);
	public static final Climate.Parameter EROSION_A = Climate.Parameter.span(-0.223F, 0.450F);
	public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(0.030F, 0.550F);

	public static final Climate.Parameter TEMPERATURE_B = Climate.Parameter.span(-1.000F, -0.450F);
	public static final Climate.Parameter HUMIDITY_C = Climate.Parameter.span(0.300F, 0.700F);
	public static final Climate.Parameter WEIRDNESS_C = Climate.Parameter.span(-0.6F, -0.05F);
	public static final Climate.Parameter EROSION_B = Climate.Parameter.span(0.050F, 0.450F);
	public static final Climate.Parameter CONTINENTALNESS_B = Climate.Parameter.span(-0.110F, 0.030F);

	public static final Climate.Parameter TEMPERATURE_C = Climate.Parameter.span(-0.450F, -0.200F);
	public static final Climate.Parameter HUMIDITY_D = Climate.Parameter.span(-1.0F, -0.100F);
	public static final Climate.Parameter WEIRDNESS_D = Climate.Parameter.span(-0.750F, -0.05F);
	public static final Climate.Parameter EROSION_C = Climate.Parameter.span(-0.223F, 0.450F);
	public static final Climate.Parameter CONTINENTALNESS_C = Climate.Parameter.span(0.030F, 0.800F);
	public static final float TEMP = 0.25F;
	public static final float DOWNFALL = 0.8F;
	public static final int WATER_COLOR = WWSharedWorldgen.STOCK_WATER_COLOR;
	public static final int WATER_FOG_COLOR = WWSharedWorldgen.STOCK_WATER_FOG_COLOR;
	public static final int FOG_COLOR = WWSharedWorldgen.STOCK_FOG_COLOR;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
	public static final int GRASS_COLOR_MAPLE = 13023096;
	public static final int GRASS_COLOR_ORANGE = 15452037;
	public static final int GRASS_COLOR_LIGHTER_GREEN = 12632212;
	public static final int GRASS_COLOR_BLUE_GREENISH = 10990217;
	public static final int GRASS_COLOR_BROWN = 15712649;
	public static final int GRASS_COLOR_RED = 14909535;
	public static final int FOLIAGE_COLOR = 14995819;
		//15648619;
	public static final Tundra INSTANCE = new Tundra();

	public Tundra() {
		super();
		FrozenGrassColorModifiers.addGrassColorModifier(
			this.getKey().location(),
			(x, y, grassColor) -> {
				double noise = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2525L)), ImmutableList.of(0)).getValue(x * 0.0225D, y * 0.0225D, false);
				if (noise < -0.5D) {
					return GRASS_COLOR_BROWN;
				} else if (noise < -0.35D) {
					return GRASS_COLOR_ORANGE;
				} else if (noise > 0.8D) {
					return GRASS_COLOR_BLUE_GREENISH;
				} else if (noise > 0.5D) {
					return GRASS_COLOR_LIGHTER_GREEN;
				} else if (noise > 0.34222D) {
					return GRASS_COLOR_RED;
				}
				return grassColor;
			}
		);
	}

	@Override
	public String modID() {
		return WWConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "tundra";
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
	public @NotNull Integer foliageColorOverride() {
		return FOLIAGE_COLOR;
	}

	@Override
	public @NotNull Integer grassColorOverride() {
		return GRASS_COLOR_MAPLE;
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
		return Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FLOWER_FOREST);
	}

	@Override
	public void addFeatures(@NotNull BiomeGenerationSettings.Builder features) {
		WWSharedWorldgen.addBasicFeatures(features, false);
		BiomeDefaultFeatures.addDefaultOres(features);
		BiomeDefaultFeatures.addDefaultSoftDisks(features);
		BiomeDefaultFeatures.addForestFlowers(features);
		BiomeDefaultFeatures.addForestGrass(features);
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
		BiomeDefaultFeatures.addDefaultMushrooms(features);
		BiomeDefaultFeatures.addDefaultExtraVegetation(features);
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 6, 4, 4));
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 6, 4, 4));
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 8, 2, 5));
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, OverworldBiomeBuilder.Modifier modifier) {
		if (WWWorldgenConfig.get().biomeGeneration.generateTundra) {
			this.addSurfaceBiome(
				parameters,
				TEMPERATURE,
				HUMIDITY,
				CONTINENTALNESS,
				EROSION_A,
				WEIRDNESS_A,
				0F
			);
			this.addSurfaceBiome(
				parameters,
				TEMPERATURE,
				HUMIDITY,
				CONTINENTALNESS,
				EROSION_A,
				WEIRDNESS_B,
				0F
			);
			this.addSurfaceBiome(
				parameters,
				TEMPERATURE_B,
				HUMIDITY_C,
				CONTINENTALNESS_B,
				EROSION_B,
				WEIRDNESS_C,
				0F
			);
			this.addSurfaceBiome(
				parameters,
				TEMPERATURE_C,
				HUMIDITY_D,
				CONTINENTALNESS_C,
				EROSION_C,
				WEIRDNESS_D,
				0F
			);

		}
	}

}

