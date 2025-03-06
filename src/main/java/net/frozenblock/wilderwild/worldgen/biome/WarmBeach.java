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
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.worldgen.WWSharedWorldgen;
import net.frozenblock.wilderwild.worldgen.features.placed.WWMiscPlaced;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WarmBeach extends FrozenBiome {
	public static final float TEMP = 1.1F;
	public static final float DOWNFALL = 0.6F;
	public static final int WATER_COLOR = WWSharedWorldgen.STOCK_WATER_COLOR;
	public static final int WATER_FOG_COLOR = WWSharedWorldgen.STOCK_WATER_FOG_COLOR;
	public static final int FOG_COLOR = WWSharedWorldgen.STOCK_FOG_COLOR;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(TEMP);
	public static final WarmBeach INSTANCE = new WarmBeach();

	@Override
	public String modID() {
		return WWConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "warm_beach";
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
	public @Nullable AmbientMoodSettings ambientMoodSettings() {
		return AmbientMoodSettings.LEGACY_CAVE_SETTINGS;
	}

	@Override
	public @Nullable AmbientAdditionsSettings ambientAdditionsSound() {
		return null;
	}

	@Override
	public @Nullable Music backgroundMusic() {
		return null;
	}

	@Override
	public void addFeatures(@NotNull BiomeGenerationSettings.Builder features) {
		BiomeDefaultFeatures.addDefaultCarversAndLakes(features);
		BiomeDefaultFeatures.addDefaultCrystalFormations(features);
		BiomeDefaultFeatures.addDefaultMonsterRoom(features);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(features);
		BiomeDefaultFeatures.addDefaultSprings(features);
		BiomeDefaultFeatures.addSurfaceFreezing(features);
		BiomeDefaultFeatures.addDefaultOres(features);
		BiomeDefaultFeatures.addDefaultSoftDisks(features);
		BiomeDefaultFeatures.addDefaultFlowers(features);
		BiomeDefaultFeatures.addDefaultGrass(features);
		BiomeDefaultFeatures.addDefaultMushrooms(features);
		BiomeDefaultFeatures.addDefaultExtraVegetation(features);
		features.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.UNDER_WATER_GRAVEL_PATH_RIVER.getKey());
	}

	@Override
	public void addSpawns(MobSpawnSettings.@NotNull Builder spawns) {
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.TURTLE, 5, 2, 5));
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters, OverworldBiomeBuilder.Modifier modifier) {

	}

}
