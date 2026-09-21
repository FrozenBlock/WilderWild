/*
 * Copyright 2026 FrozenBlock
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

import net.frozenblock.lib.FrozenLibEarlyConstants;
import net.frozenblock.wilderwild.advancements.modification.WWAdvancementModifications;
import net.frozenblock.wilderwild.block.leaves.FallingLeafUtil;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.datafix.minecraft.WWMinecraftDataFixer;
import net.frozenblock.wilderwild.datafix.wilderwild.WWDataFixer;
import net.frozenblock.wilderwild.levelgen.modification.WWWorldgen;
import net.frozenblock.wilderwild.levelgen.structure.modification.WWStructureModifications;
import net.frozenblock.wilderwild.mod_compat.WWModIntegrations;
import net.frozenblock.wilderwild.mod_compat.WWSimpleCopperPipesCompat;
import net.frozenblock.wilderwild.registry.WWActivities;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlockTransformers;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWCreativeInventorySorting;
import net.frozenblock.wilderwild.registry.WWCriteria;
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
import net.frozenblock.wilderwild.registry.WWResources;
import net.frozenblock.wilderwild.registry.WWSensorTypes;
import net.frozenblock.wilderwild.registry.WWShearsDispenseItemBehaviors;
import net.frozenblock.wilderwild.registry.WWSoundPredicates;
import net.frozenblock.wilderwild.registry.WWSoundTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WWStructureProcessorTypes;
import net.frozenblock.wilderwild.registry.WWWindDisturbances;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.wind.WWWindManagerExtension;

public final class WilderWild {

	public static void init() { // Alan Wilder Wild
		WilderWildRegistries.init();
		WWFeatureFlags.init();

		WWMinecraftDataFixer.applyDataFixes();
		WWDataFixer.applyDataFixes();

		WWAdvancementModifications.init();
		WWDataComponents.init();
		WWMemoryModuleTypes.init();
		WWSensorTypes.init();
		WWAttachmentTypes.init();
		WWActivities.init();
		WWEnvironmentAttributes.init();

		WWLootTables.init();
		WWModIntegrations.init();

		WWGameEvents.init();
		WWSounds.init();
		WWBlocks.init();
		WWItems.init();
		WWEntityTypes.init();
		WWShearsDispenseItemBehaviors.init();
		WWBlockEntityTypes.init();
		WWParticleTypes.init();
		FallingLeafUtil.init();
		WWMobEffects.init();
		WWPotions.init();
		WWCriteria.init();
		WWFeatures.init();
		WWWorldgen.init();
		WWStructureProcessorTypes.init();
		WWStructureModifications.init();
		WWBlockTransformers.init();

		WWWindDisturbances.init();
		WWWindManagerExtension.init();
		WWSoundPredicates.init();

		WWResources.init();

		WWAmbienceAndMiscConfig.CONFIG.load(true);
		WWBlockConfig.CONFIG.load(true);
		WWEntityConfig.CONFIG.load(true);
		WWItemConfig.CONFIG.load(true);
		WWWorldgenConfig.CONFIG.load(true);
	}

	public static void setup() {
		WWSoundTypes.setup();
		WWItems.setup();
		WWBlocks.setup();
		WWWorldgen.setup();
		WWCreativeInventorySorting.setup();

		if (FrozenLibEarlyConstants.HAS_SIMPLE_COPPER_PIPES) WWSimpleCopperPipesCompat.setup();
	}

	private WilderWild() {}
}
