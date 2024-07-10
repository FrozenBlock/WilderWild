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

package net.frozenblock.wilderwild;

import java.util.ArrayList;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.config.api.instance.ConfigModification;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.frozenlib_config.FrozenLibConfig;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.lib.mobcategory.api.entrypoint.FrozenMobCategoryEntrypoint;
import net.frozenblock.lib.mobcategory.impl.FrozenMobCategory;
import net.frozenblock.wilderwild.command.SpreadSculkCommand;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.datafix.minecraft.WWMinecraftDataFixer;
import net.frozenblock.wilderwild.datafix.wilderwild.WWDataFixer;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.ai.TermiteManager;
import net.frozenblock.wilderwild.mod_compat.WilderModIntegrations;
import net.frozenblock.wilderwild.networking.WilderNetworking;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterCriteria;
import net.frozenblock.wilderwild.registry.RegisterDataComponents;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterFeatures;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterLootTables;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.RegisterMobEffects;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.frozenblock.wilderwild.registry.RegisterPotions;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterResources;
import net.frozenblock.wilderwild.registry.RegisterSensorTypes;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.registry.RegisterVillagerTypes;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.frozenblock.wilderwild.world.modification.WilderWorldGen;
import org.jetbrains.annotations.NotNull;

public final class WilderWild extends FrozenModInitializer implements FrozenMobCategoryEntrypoint {

	public WilderWild() {
		super(WilderConstants.MOD_ID);
	}

	@Override //Alan Wilder Wild
	public void onInitialize(String modId, ModContainer container) {
		WilderConstants.startMeasuring(this);
		if (WilderPreMixinInjectConstants.IS_DATAGEN) {
			ConfigRegistry.register(BlockConfig.INSTANCE, new ConfigModification<>(config -> config.snowlogging.snowlogging = false));
		}
		WWMinecraftDataFixer.applyDataFixes(container);
		WWDataFixer.applyDataFixes(container);

		RegisterDataComponents.init();
		WilderRegistry.initRegistry();
		RegisterBlocks.registerBlocks();
		RegisterItems.registerItems();
		RegisterItems.registerBlockItems();
		RegisterGameEvents.registerEvents();

		RegisterSounds.init();
		RegisterBlockSoundTypes.init();
		RegisterBlockEntities.register();
		RegisterEntities.init();
		RegisterMemoryModuleTypes.register();
		RegisterSensorTypes.register();
		RegisterLootTables.init();
		RegisterParticles.registerParticles();
		RegisterResources.register(container);
		RegisterProperties.init();
		RegisterMobEffects.init();
		RegisterPotions.init();
		RegisterCriteria.init();

		RegisterFeatures.init();
		RegisterWorldgen.init();
		WilderWorldGen.generateWildWorldGen();

		TermiteManager.Termite.addDegradableBlocks();
		TermiteManager.Termite.addNaturalDegradableBlocks();
		RegisterBlocks.registerBlockProperties();
		RegisterVillagerTypes.register();

		ServerLifecycleEvents.SERVER_STOPPED.register(listener -> {
			Jellyfish.clearLevelToNonPearlescentCount();
			Crab.clearLevelToCrabCount();
		});
		ServerTickEvents.START_SERVER_TICK.register(listener -> {
			Jellyfish.clearLevelToNonPearlescentCount();
			Crab.clearLevelToCrabCount();
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> SpreadSculkCommand.register(dispatcher));

		WilderModIntegrations.init();
		// TODO replace this with a config option at some point
		ConfigRegistry.register(FrozenLibConfig.INSTANCE, new ConfigModification<>(config -> config.saveItemCooldowns = true));

		RegisterBlocks.registerBlockProperties();
		WilderNetworking.init();

		WilderConstants.stopMeasuring(this);
	}

	@Override
	public void newCategories(@NotNull ArrayList<FrozenMobCategory> context) {
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("fireflies"), EntityConfig.get().firefly.fireflySpawnCap, true, false, 80));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("jellyfish"), EntityConfig.get().jellyfish.jellyfishSpawnCap, true, false, 64));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("crab"), EntityConfig.get().crab.crabSpawnCap, true, false, 84));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("tumbleweed"), EntityConfig.get().tumbleweed.tumbleweedSpawnCap, true, false, 64));
	}
}
