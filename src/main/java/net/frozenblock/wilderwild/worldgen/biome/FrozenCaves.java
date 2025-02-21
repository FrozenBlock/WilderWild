/*
 * Copyright 2023-2025 FrozenBlock
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
import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
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
import net.minecraft.sounds.SoundEvents;
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
	public static final Climate.Parameter EROSION_PEAK = Climate.Parameter.span(-1F, -0.6F);
	public static final ImmutableList<Float> DEPTHS = ImmutableList.of(0.065F, 0.1F, 0.15F);
	public static final float TEMP = -2.0F;
	public static final float DOWNFALL = 0.4F;
	public static final int WATER_COLOR = 10601471;
	public static final int WATER_FOG_COLOR = 8168948;
	public static final int FOG_COLOR = WWSharedWorldgen.STOCK_FOG_COLOR;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
	public static final FrozenCaves INSTANCE = new FrozenCaves();

	@Override
	public String modID() {
		return WWConstants.MOD_ID;
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
		return Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FROZEN_PEAKS);
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
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWCavePlaced.ORE_DIORITE_EXTRA.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWCavePlaced.PACKED_ICE_PATH.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.SNOW_DISK_UPPER.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.POWDER_SNOW_DISK_UPPER.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.SNOW_DISK_LOWER.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.POWDER_SNOW_DISK_LOWER.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.PACKED_ICE_DISK.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.ICE_PILE.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.ICE_DISK.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.ICICLE_PATCH.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.ICE_PATCH_CEILING.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.ICE_COLUMN_PATCH.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.ICE_PATCH.getKey());
		features.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWCavePlaced.DIORITE_PATCH.getKey());
		features.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWCavePlaced.DIORITE_PATCH_CEILING.getKey());
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.snowySpawns(spawns);
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters) {
		if (WWWorldgenConfig.get().biomeGeneration.generateFrozenCaves) {
			for (float depth : DEPTHS) {
				this.addFrozenCavesAtDepth(parameters, depth);
			}
		}
	}

	public void addFrozenCavesAtDepth(@NotNull Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, float depth) {
		parameters.accept(
			Pair.of(
				Climate.parameters(
					TEMPERATURE,
					HUMIDITY,
					CONTINENTALNESS,
					EROSION_PEAK,
					Climate.Parameter.point(depth),
					Climate.Parameter.span(Weirdness.HIGH_SLICE_NORMAL_ASCENDING, Weirdness.HIGH_SLICE_NORMAL_DESCENDING),
					-depth
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
					Climate.Parameter.point(depth),
					Climate.Parameter.span(Weirdness.HIGH_SLICE_VARIANT_ASCENDING, Weirdness.HIGH_SLICE_VARIANT_DESCENDING),
					-depth
				),
				this.getKey()
			)
		);
	}

}
