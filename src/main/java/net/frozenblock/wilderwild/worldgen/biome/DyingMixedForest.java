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
import java.util.function.Consumer;
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

public final class DyingMixedForest extends FrozenBiome {
	public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(-0.465F, -0.255F);
	public static final Climate.Parameter TEMPERATURE_TUNDRA = Climate.Parameter.span(-0.495F, -0.255F);
	public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(0.050F, 0.155F);
	public static final Climate.Parameter TEMPERATURE_WEIRD = Climate.Parameter.span(-0.465F, -0.425F);
	public static final Climate.Parameter TEMPERATURE_WEIRD_TUNDRA = Climate.Parameter.span(-0.495F, -0.425F);
	public static final Climate.Parameter HUMIDITY_WEIRD = Climate.Parameter.span(-0.105F, 0.100F);
	public static final float TEMP = 0.35F;
	public static final float DOWNFALL = 0.55F;
	public static final int WATER_COLOR = WWSharedWorldgen.STOCK_WATER_COLOR;
	public static final int WATER_FOG_COLOR = WWSharedWorldgen.STOCK_WATER_FOG_COLOR;
	public static final int FOG_COLOR = WWSharedWorldgen.STOCK_FOG_COLOR;
	public static final int GRASS_COLOR = 8955746;
	public static final int FOLIAGE_COLOR = 7703076;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
	public static final DyingMixedForest INSTANCE = new DyingMixedForest();

	@Override
	public String modID() {
		return WWConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "dying_mixed_forest";
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
		WWSharedWorldgen.addBasicFeatures(features, false);
		BiomeDefaultFeatures.addDefaultOres(features);
		BiomeDefaultFeatures.addDefaultSoftDisks(features);
		BiomeDefaultFeatures.addForestFlowers(features);
		BiomeDefaultFeatures.addForestGrass(features);
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
		BiomeDefaultFeatures.addDefaultMushrooms(features);
		BiomeDefaultFeatures.addDefaultExtraVegetation(features);
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_DYING_MIXED_FOREST.getKey());
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.plainsSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters) {
		if (WWModIntegrations.BIOLITH_INTEGRATION.modLoaded()) return;
		if (WWWorldgenConfig.get().biomeGeneration.generateDyingMixedForest) {
			boolean generateTundra = WWWorldgenConfig.get().biomeGeneration.generateTundra;

			for (Climate.ParameterPoint point : OverworldBiomeBuilderParameters.points(Biomes.SNOWY_TAIGA)) {
				boolean weird = point.weirdness().max() < 0L;
				this.addSurfaceBiome(
					parameters,
					generateTundra ? TEMPERATURE_TUNDRA : TEMPERATURE,
					HUMIDITY,
					point.continentalness(),
					point.erosion(),
					point.weirdness(),
					point.offset()
				);
				if (weird) {
					this.addSurfaceBiome(
						parameters,
						generateTundra ? TEMPERATURE_WEIRD_TUNDRA : TEMPERATURE_WEIRD,
						HUMIDITY_WEIRD,
						point.continentalness(),
						point.erosion(),
						point.weirdness(),
						point.offset()
					);
				}
			}
		}
	}

}
