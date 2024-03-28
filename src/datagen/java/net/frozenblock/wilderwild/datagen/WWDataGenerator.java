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

package net.frozenblock.wilderwild.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.frozenblock.lib.config.api.instance.ConfigModification;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.datagen.advancement.WWAdvancementProvider;
import net.frozenblock.wilderwild.datagen.loot.WWBlockLootProvider;
import net.frozenblock.wilderwild.datagen.loot.WWEntityLootProvider;
import net.frozenblock.wilderwild.datagen.model.WWModelProvider;
import net.frozenblock.wilderwild.datagen.recipe.WWRecipeProvider;
import net.frozenblock.wilderwild.datagen.tag.WWBiomeTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWBlockTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWDamageTypeTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWEntityTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWGameEventTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWItemTagProvider;
import net.frozenblock.wilderwild.misc.WilderFeatureFlags;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterDamageTypes;
import net.frozenblock.wilderwild.registry.RegisterStructures;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.impl.WilderFeatureBootstrap;
import net.frozenblock.wilderwild.world.impl.noise.WilderNoise;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

public final class WWDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(@NotNull FabricDataGenerator dataGenerator) {
		ConfigRegistry.register(BlockConfig.INSTANCE, new ConfigModification<>(config -> config.snowlogging.snowlogging = false));
		WilderFeatureFlags.init();
		FrozenFeatureFlags.rebuild();
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();

		// ASSETS

		pack.addProvider(WWModelProvider::new);

		// DATA

		pack.addProvider(WWBlockLootProvider::new);
		pack.addProvider(WWRegistryProvider::new);
		pack.addProvider(WWBiomeTagProvider::new);
		pack.addProvider(WWBlockTagProvider::new);
		pack.addProvider(WWDamageTypeTagProvider::new);
		pack.addProvider(WWItemTagProvider::new);
		pack.addProvider(WWEntityTagProvider::new);
		pack.addProvider(WWGameEventTagProvider::new);
		pack.addProvider(WWEntityLootProvider::new);
		pack.addProvider(WWRecipeProvider::new);
		pack.addProvider(WWAdvancementProvider::new);
	}

	@Override
	public void buildRegistry(@NotNull RegistrySetBuilder registryBuilder) {
		WilderSharedConstants.logWithModId("Registering Biomes for", WilderSharedConstants.UNSTABLE_LOGGING);

		registryBuilder.add(Registries.DAMAGE_TYPE, RegisterDamageTypes::bootstrap);
		registryBuilder.add(Registries.CONFIGURED_FEATURE, WilderFeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, WilderFeatureBootstrap::bootstrapPlaced);
		registryBuilder.add(Registries.BIOME, RegisterWorldgen::bootstrap);
		registryBuilder.add(Registries.NOISE, WilderNoise::bootstrap);
		registryBuilder.add(Registries.PROCESSOR_LIST, RegisterStructures::bootstrapProcessor);
		registryBuilder.add(Registries.TEMPLATE_POOL, RegisterStructures::bootstrapTemplatePool);
		registryBuilder.add(Registries.STRUCTURE, RegisterStructures::bootstrap);
		registryBuilder.add(Registries.STRUCTURE_SET, RegisterStructures::bootstrapStructureSet);
	}

}
