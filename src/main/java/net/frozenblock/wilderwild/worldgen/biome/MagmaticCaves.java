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

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.frozenblock.lib.worldgen.biome.api.FrozenBiome;
import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
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
import net.minecraft.world.entity.MobCategory;
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

public final class MagmaticCaves extends FrozenBiome {
	public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.WARM, Temperature.HOT);
	public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(-1F, 0.2F);
	public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(Continentalness.OCEAN, Continentalness.FAR_INLAND);
	public static final Climate.Parameter EROSION = Climate.Parameter.span(Erosion.EROSION_2, Erosion.EROSION_6);
	public static final Climate.Parameter WEIRDNESS = Weirdness.FULL_RANGE;
	public static final float OFFSET = 0.000F;
	public static final float TEMP = 2.0F;
	public static final float DOWNFALL = 0.4F;
	public static final int WATER_COLOR = 4566514;
	public static final int WATER_FOG_COLOR = 267827;
	public static final int FOG_COLOR = WWSharedWorldgen.STOCK_FOG_COLOR;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
	public static final MagmaticCaves INSTANCE = new MagmaticCaves();

	@Override
	public String modID() {
		return WWConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "magmatic_caves";
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
	public @Nullable Integer dryFoliageColorOverride() {
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
		return Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_MAGMATIC_CAVES);
	}

	@Override
	public void addFeatures(@NotNull BiomeGenerationSettings.Builder features) {
		BiomeDefaultFeatures.addFossilDecoration(features);
		BiomeDefaultFeatures.addDefaultCrystalFormations(features);
		BiomeDefaultFeatures.addDefaultMonsterRoom(features);
		features.addFeature(GenerationStep.Decoration.LAKES, WWCavePlaced.MAGMA_LAVA_POOL.getKey());
		features.addFeature(GenerationStep.Decoration.LAKES, WWCavePlaced.LAVA_LAKE_EXTRA.getKey());
		features.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, WWCavePlaced.LAVA_SPRING_EXTRA.getKey());
		BiomeDefaultFeatures.addDefaultUndergroundVariety(features);
		BiomeDefaultFeatures.addSurfaceFreezing(features);
		BiomeDefaultFeatures.addPlainGrass(features);
		BiomeDefaultFeatures.addDefaultOres(features, false);
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWCavePlaced.ORE_GABBRO.getKey());
		BiomeDefaultFeatures.addDefaultSoftDisks(features);
		BiomeDefaultFeatures.addPlainVegetation(features);
		BiomeDefaultFeatures.addDefaultMushrooms(features);
		BiomeDefaultFeatures.addDefaultExtraVegetation(features, false);
		BiomeDefaultFeatures.addDefaultCarversAndLakes(features);
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WWCavePlaced.FOSSIL_LAVA.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.UPSIDE_DOWN_MAGMA.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.MAGMA_DISK.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.MAGMA_PILE.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.GABBRO_DISK.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.OBSIDIAN_DISK.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.GABBRO_PILE.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.DOWNWARDS_GABBRO_COLUMN.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWCavePlaced.MAGMA_PATH.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.FIRE_PATCH_MAGMA.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWCavePlaced.GEYSER_LAVA.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWCavePlaced.GEYSER_UP.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWCavePlaced.GEYSER_DOWN.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.GEYSER_NORTH.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.GEYSER_EAST.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.GEYSER_SOUTH.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.GEYSER_WEST.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWCavePlaced.DOWNWARDS_GEYSER_COLUMN.getKey());
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.MONSTER, 385, new MobSpawnSettings.SpawnerData(WWEntityTypes.SCORCHED, 4, 4));
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters) {
		if (WWWorldgenConfig.get().biomeGeneration.generateMagmaticCaves) {
			this.addBottomBiome(
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
