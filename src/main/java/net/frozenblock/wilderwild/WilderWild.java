/*
 * Copyright 2025-2026 FrozenBlock
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
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.entity.api.category.entrypoint.FrozenMobCategoryEntrypoint;
import net.frozenblock.lib.entity.impl.category.FrozenMobCategory;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.lib.feature_flag.api.FeatureFlagApi;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.wilderwild.command.SpreadSculkCommand;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.datafix.minecraft.WWMinecraftDataFixer;
import net.frozenblock.wilderwild.datafix.wilderwild.WWDataFixer;
import net.frozenblock.wilderwild.mod_compat.WWModIntegrations;
import net.frozenblock.wilderwild.networking.WWNetworking;
import net.frozenblock.wilderwild.registry.WWActivities;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlockSoundTypeOverwrites;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWCreativeInventorySorting;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.frozenblock.wilderwild.registry.WWDamageTypes;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWEnvironmentAttributes;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.registry.WWGameEvents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWLootTables;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWMobEffects;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWPotions;
import net.frozenblock.wilderwild.registry.WWSensorTypes;
import net.frozenblock.wilderwild.registry.WWSoundPredicates;
import net.frozenblock.wilderwild.registry.WWSoundTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WWTimelines;
import net.frozenblock.wilderwild.registry.WWWindDisturbances;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.wind.WWWindManager;
import net.frozenblock.wilderwild.worldgen.modification.WWWorldgen;

public final class WilderWild extends FrozenModInitializer implements FrozenMobCategoryEntrypoint {

	public WilderWild() {
		super(WWConstants.MOD_ID);
	}

	@Override //Alan Wilder Wild
	public void onInitialize(String modId, ModContainer container) {
		WWFeatureFlags.init();
		FeatureFlagApi.rebuild();

		WWMinecraftDataFixer.applyDataFixes(container);
		WWDataFixer.applyDataFixes(container);

		WilderWildRegistries.init();

		WWDataComponents.init();
		WWAttachmentTypes.init();
		WWBlocks.init();
		WWItems.init();
		WWGameEvents.init();
		WWSounds.init();
		WWSoundTypes.init();
		WWBlockEntityTypes.init();
		WWEntityTypes.init();
		WWDamageTypes.init();
		WWActivities.init();
		WWMemoryModuleTypes.init();
		WWSensorTypes.init();
		WWLootTables.init();
		WWParticleTypes.init();
		WWMobEffects.init();
		WWPotions.init();
		WWCriteria.init();

		WWEnvironmentAttributes.init();
		WWTimelines.init();

		WWFeatures.init();
		WWBiomes.init();
		WWWorldgen.init();
		WWBlocks.registerBlockProperties();
		WWBlockSoundTypeOverwrites.init();

		WWWindDisturbances.init();
		WWSoundPredicates.init();

		WWModIntegrations.init();
		WWNetworking.init();
		WWCreativeInventorySorting.init();
		WindManager.addExtension(WWWindManager.TYPE);

		WWAmbienceAndMiscConfig.CONFIG.load(true);
		WWBlockConfig.CONFIG.load(true);
		WWEntityConfig.CONFIG.load(true);
		WWItemConfig.CONFIG.load(true);
		WWWorldgenConfig.CONFIG.load(true);

		CommandRegistrationCallback.EVENT.register(
			(dispatcher, context, selection) -> SpreadSculkCommand.register(dispatcher)
		);
	}

	@Override
	public void newCategories(ArrayList<FrozenMobCategory> context) {
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("firefly"), "FF", WWEntityConfig.FIREFLY_SPAWN_CAP.get(), true, false, 40));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("butterfly"), "BF", WWEntityConfig.BUTTERFLY_SPAWN_CAP.get(), true, false, 80));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("jellyfish"), "JF", WWEntityConfig.JELLYFISH_SPAWN_CAP.get(), true, false, 64));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("crab"), "CR", WWEntityConfig.CRAB_SPAWN_CAP.get(), true, false, 64));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("tumbleweed"), "TW", WWEntityConfig.TUMBLEWEED_SPAWN_CAP.get(), true, false, 64));
	}
}
