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

package net.frozenblock.wilderwild.world.biome;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.FrozenBiome;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Humidity;
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.world.WilderSharedWorldgen;
import net.frozenblock.wilderwild.world.feature.placed.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.feature.placed.WilderPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MapleGrove extends FrozenBiome {
	public static final Climate.Parameter TEMPERATURE = Temperature.COOL;
	public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.DRY, Humidity.NEUTRAL);
	public static final Climate.Parameter WEIRDNESS = Climate.Parameter.span(-1F, -0.4F);
	public static final Climate.Parameter EROSION = Climate.Parameter.span(0.4F, Erosion.EROSION_6.max());
	public static final float TEMP = 0.55F;
	public static final float DOWNFALL = 0.5F;
	public static final int WATER_COLOR = WilderSharedWorldgen.STOCK_WATER_COLOR;
	public static final int WATER_FOG_COLOR = WilderSharedWorldgen.STOCK_WATER_FOG_COLOR;
	public static final int FOG_COLOR = WilderSharedWorldgen.STOCK_FOG_COLOR;
	public static final int GRASS_COLOR = 9807706;
	public static final int FOLIAGE_COLOR = 9342777;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
	public static final MapleGrove INSTANCE = new MapleGrove();

	@Override
	public String modID() {
		return WilderConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "maple_grove";
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
		return false;
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
		return FOLIAGE_COLOR;
	}

	@Override
	public @Nullable Integer grassColorOverride() {
		return GRASS_COLOR;
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
	public @Nullable AmbientMoodSettings ambientMoodSettings() {
		return AmbientMoodSettings.LEGACY_CAVE_SETTINGS;
	}

	@Override
	public @Nullable AmbientAdditionsSettings ambientAdditionsSound() {
		return null;
	}

	@Override
	public @Nullable Music backgroundMusic() {
		return Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST);
	}

	@Override
	public void addFeatures(@NotNull BiomeGenerationSettings.Builder features) {
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MAPLE_TREES.getKey());
		WilderSharedWorldgen.addBasicFeatures(features, false);
		BiomeDefaultFeatures.addSavannaGrass(features);
		BiomeDefaultFeatures.addDefaultOres(features);
		BiomeDefaultFeatures.addDefaultSoftDisks(features);
		BiomeDefaultFeatures.addWarmFlowers(features);
		BiomeDefaultFeatures.addSavannaExtraGrass(features);
		BiomeDefaultFeatures.addDefaultMushrooms(features);
		BiomeDefaultFeatures.addDefaultExtraVegetation(features);
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderMiscPlaced.FOLIATED_GRASS_DISK.getKey());
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.commonSpawns(spawns);
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters) {
		if (WorldgenConfig.get().biomeGeneration.generateMapleGrove) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.TAIGA)) {
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE,
					HUMIDITY,
					point.continentalness(),
					EROSION,
					WEIRDNESS,
					point.offset()
				);
			}
		}
	}

}
