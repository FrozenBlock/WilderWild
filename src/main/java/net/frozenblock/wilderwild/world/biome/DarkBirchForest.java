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
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.world.WilderSharedWorldgen;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
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
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DarkBirchForest extends FrozenBiome {
	public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.125F, 0.2F);
	public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(0.275F, 0.325F);
	public static final float TEMP = 0.65F;
	public static final float DOWNFALL = 0.7F;
	public static final int WATER_COLOR = WilderSharedWorldgen.STOCK_WATER_COLOR;
	public static final int WATER_FOG_COLOR = WilderSharedWorldgen.STOCK_WATER_FOG_COLOR;
	public static final int FOG_COLOR = WilderSharedWorldgen.STOCK_FOG_COLOR;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
	public static final DarkBirchForest INSTANCE = new DarkBirchForest();

	@Override
	public String modID() {
		return WilderConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "dark_birch_forest";
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
	public BiomeSpecialEffects.GrassColorModifier grassColorModifier() {
		return BiomeSpecialEffects.GrassColorModifier.DARK_FOREST;
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
		WilderSharedWorldgen.addBasicFeatures(features, false);
		BiomeDefaultFeatures.addForestFlowers(features);
		BiomeDefaultFeatures.addDefaultOres(features);
		BiomeDefaultFeatures.addDefaultSoftDisks(features);
		BiomeDefaultFeatures.addDefaultFlowers(features);
		BiomeDefaultFeatures.addForestGrass(features);
		BiomeDefaultFeatures.addDefaultMushrooms(features);
		BiomeDefaultFeatures.addDefaultExtraVegetation(features);
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DARK_BIRCH_FOREST_VEGETATION.getKey());
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.farmAnimals(spawns);
		BiomeDefaultFeatures.commonSpawns(spawns);
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters) {
		if (WorldgenConfig.get().biomeGeneration.generateDarkBirchForest) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.DARK_FOREST)) {
				this.addSurfaceBiome(
					parameters,
					TEMPERATURE,
					HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset()
				);
			}
		}
	}

}
