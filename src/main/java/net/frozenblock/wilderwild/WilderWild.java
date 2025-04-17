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

package net.frozenblock.wilderwild;

import java.util.ArrayList;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.config.api.instance.ConfigModification;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.entity.api.category.entrypoint.FrozenMobCategoryEntrypoint;
import net.frozenblock.lib.entity.impl.category.FrozenMobCategory;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.lib.feature_flag.api.FeatureFlagApi;
import net.frozenblock.wilderwild.command.SpreadSculkCommand;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.datafix.minecraft.WWMinecraftDataFixer;
import net.frozenblock.wilderwild.datafix.wilderwild.WWDataFixer;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.mod_compat.WWModIntegrations;
import net.frozenblock.wilderwild.networking.WWNetworking;
import net.frozenblock.wilderwild.registry.WWActivities;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWCreativeInventorySorting;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.frozenblock.wilderwild.registry.WWDamageTypes;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.registry.WWGameEvents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWLootTables;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWMobEffects;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWPotions;
import net.frozenblock.wilderwild.registry.WWResources;
import net.frozenblock.wilderwild.registry.WWSensorTypes;
import net.frozenblock.wilderwild.registry.WWSoundTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WWVillagers;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.worldgen.modification.WWWorldGen;
import org.jetbrains.annotations.NotNull;

public final class WilderWild extends FrozenModInitializer implements FrozenMobCategoryEntrypoint {

	public WilderWild() {
		super(WWConstants.MOD_ID);
	}

	@Override //Alan Wilder Wild
	public void onInitialize(String modId, ModContainer container) {
		if (FrozenBools.IS_DATAGEN) {
			ConfigRegistry.register(WWBlockConfig.INSTANCE, new ConfigModification<>(config -> config.snowlogging.snowlogging = false));
		}

		WWFeatureFlags.init();
		FeatureFlagApi.rebuild();

		WWMinecraftDataFixer.applyDataFixes(container);
		WWDataFixer.applyDataFixes(container);

		WilderWildRegistries.initRegistry();

		WWDataComponents.init();
		WWBlocks.registerBlocks();
		WWCreativeInventorySorting.init();
		WWItems.registerItems();
		WWGameEvents.registerEvents();

		WWSounds.init();
		WWSoundTypes.init();
		WWBlockEntityTypes.register();
		WWEntityTypes.init();
		WWDamageTypes.init();
		WWActivities.init();
		WWMemoryModuleTypes.register();
		WWSensorTypes.register();
		WWLootTables.init();
		WWParticleTypes.registerParticles();
		WWResources.register(container);
		WWMobEffects.init();
		WWPotions.init();
		WWCriteria.init();

		WWFeatures.init();
		WWBiomes.init();
		WWWorldGen.generateWildWorldGen();
		WWBlocks.registerBlockProperties();
		WWVillagers.register();

		ServerLifecycleEvents.SERVER_STOPPED.register(listener -> Jellyfish.clearLevelToNonPearlescentCount());
		ServerTickEvents.START_SERVER_TICK.register(listener -> Jellyfish.clearLevelToNonPearlescentCount());

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> SpreadSculkCommand.register(dispatcher));

		WWModIntegrations.init();

		WWNetworking.init();
	}

	@Override
	public void newCategories(@NotNull ArrayList<FrozenMobCategory> context) {
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("firefly"), WWEntityConfig.get().firefly.fireflySpawnCap, true, false, 80));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("butterfly"), WWEntityConfig.get().butterfly.butterflySpawnCap, true, false, 80));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("jellyfish"), WWEntityConfig.get().jellyfish.jellyfishSpawnCap, true, false, 64));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("crab"), WWEntityConfig.get().crab.crabSpawnCap, true, false, 64));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("tumbleweed"), WWEntityConfig.get().tumbleweed.tumbleweedSpawnCap, true, false, 64));
	}
}
