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
import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Humidity;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.world.WilderSharedWorldgen;
import net.frozenblock.wilderwild.world.features.feature.WilderMiscPlaced;
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

public final class FrozenCaves extends FrozenBiome {
	public static final Climate.Parameter TEMPERATURE = Temperature.ICY;
	public static final Climate.Parameter HUMIDITY = Humidity.FULL_RANGE;
	public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(Continentalness.INLAND, Continentalness.FAR_INLAND);
	public static final Climate.Parameter EROSION_PEAK = Erosion.EROSION_0;
	public static final Climate.Parameter DEPTH = Climate.Parameter.span(0.3F, 0.3F);
	public static final float OFFSET = 0.000F;
	public static final float TEMP = -2.0F;
	public static final float DOWNFALL = 0.4F;
	public static final int WATER_COLOR = 10601471;
	public static final int WATER_FOG_COLOR = 8168948;
	public static final int FOG_COLOR = WilderSharedWorldgen.STOCK_FOG_COLOR;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
	public static final FrozenCaves INSTANCE = new FrozenCaves();

	@Override
	public String modID() {
		return WilderSharedConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "frozen_caves";
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
		return Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_FROZEN_CAVES);
	}

	@Override
	public void addFeatures(@NotNull BiomeGenerationSettings.Builder features) {
		BiomeDefaultFeatures.addFossilDecoration(features);
		BiomeDefaultFeatures.addDefaultCrystalFormations(features);
		BiomeDefaultFeatures.addDefaultMonsterRoom(features);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(features);
		BiomeDefaultFeatures.addSurfaceFreezing(features);
		BiomeDefaultFeatures.addPlainGrass(features);
		BiomeDefaultFeatures.addDefaultOres(features, false);
		BiomeDefaultFeatures.addDefaultSoftDisks(features);
		BiomeDefaultFeatures.addPlainVegetation(features);
		BiomeDefaultFeatures.addDefaultMushrooms(features);
		BiomeDefaultFeatures.addDefaultExtraVegetation(features);
		BiomeDefaultFeatures.addDefaultCarversAndLakes(features);
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.PACKED_ICE_PATH.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ORE_ICE.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.SNOW_DISK_UPPER.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.POWDER_SNOW_DISK_UPPER.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.SNOW_DISK_LOWER.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.POWDER_SNOW_DISK_LOWER.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.PACKED_ICE_DISK.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.PACKED_ICE_COLUMN.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.PACKED_ICE_BIG_COLUMN.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.DOWNWARDS_PACKED_ICE_COLUMN.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.ICE_PILE.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.ICE_COLUMN.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.DOWNWARDS_ICE_COLUMN.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.ICE_DISK.getKey());
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.snowySpawns(spawns);
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters) {
		if (WorldgenConfig.get().biomeGeneration.generateFrozenCaves) {
			parameters.accept(
				Pair.of(
					Climate.parameters(
						TEMPERATURE,
						HUMIDITY,
						CONTINENTALNESS,
						EROSION_PEAK,
						DEPTH,
						Weirdness.PEAK_NORMAL,
						OFFSET
					),
					this.getKey()
				)
			);
			parameters.accept(
				Pair.of(
					Climate.parameters(
						TEMPERATURE,
						HUMIDITY,
						CONTINENTALNESS,
						EROSION_PEAK,
						DEPTH,
						Weirdness.PEAK_VARIANT,
						OFFSET
					),
					this.getKey()
				)
			);
		}
	}

}
