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

package net.frozenblock.wilderwild.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import net.frozenblock.lib.worldgen.biome.api.FrozenBiome;
import net.frozenblock.lib.worldgen.biome.api.parameters.OverworldBiomeBuilderParameters;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.mod_compat.WWModIntegrations;
import net.frozenblock.wilderwild.worldgen.WWSharedWorldgen;
import net.frozenblock.wilderwild.worldgen.features.placed.WWPlacedFeatures;
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
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;

public final class ZhenLand extends FrozenBiome {
	public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.175F, -0.125F);
	public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(0.100F, 0.325F);
	public static final float TEMP = 0.6F;
	public static final float DOWNFALL = 0.8F;
	public static final int WATER_COLOR = WWSharedWorldgen.STOCK_WATER_COLOR;
	public static final int WATER_FOG_COLOR = WWSharedWorldgen.STOCK_WATER_FOG_COLOR;
	public static final int FOG_COLOR = WWSharedWorldgen.STOCK_FOG_COLOR;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
	public static final ZhenLand INSTANCE = new ZhenLand();

	@Override
	public String modID() {
		return WWConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "zhen_land";
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
		WWSharedWorldgen.addBasicFeatures(features, false);
		BiomeDefaultFeatures.addFerns(features);
		BiomeDefaultFeatures.addDefaultOres(features);
		BiomeDefaultFeatures.addDefaultSoftDisks(features);
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.ZHEN_LAND_TREES.getKey());
		BiomeDefaultFeatures.addBirchForestFlowers(features);
		BiomeDefaultFeatures.addDefaultFlowers(features);
		BiomeDefaultFeatures.addTaigaGrass(features);
		BiomeDefaultFeatures.addDefaultExtraVegetation(features, true);
		BiomeDefaultFeatures.addCommonBerryBushes(features);
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.commonSpawns(spawns);
		BiomeDefaultFeatures.farmAnimals(spawns);
		spawns.addSpawn(MobCategory.CREATURE, 2, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 4, 4))
			.addSpawn(MobCategory.CREATURE, 2, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters) {
		if (WWModIntegrations.BIOLITH_INTEGRATION.modLoaded()) return;
		if (WWWorldgenConfig.get().biomeGeneration.generateBirchTaiga) {
			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.TAIGA)) {
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
