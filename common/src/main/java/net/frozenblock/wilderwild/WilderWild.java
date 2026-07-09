package net.frozenblock.wilderwild;

import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.datafix.minecraft.WWMinecraftDataFixer;
import net.frozenblock.wilderwild.datafix.wilderwild.WWDataFixer;
import net.frozenblock.wilderwild.levelgen.modification.WWWorldgen;
import net.frozenblock.wilderwild.levelgen.structure.modification.WWStructureModifications;
import net.frozenblock.wilderwild.registry.WWActivities;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWEnvironmentAttributes;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.registry.WWGameEvents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.frozenblock.wilderwild.registry.WWMobEffects;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWResources;
import net.frozenblock.wilderwild.registry.WWSensorTypes;
import net.frozenblock.wilderwild.registry.WWShearsDispenseItemBehaviors;
import net.frozenblock.wilderwild.registry.WWSoundPredicates;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WWWindDisturbances;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.wind.WWWindManagerExtension;

public final class WilderWild {

	// TODO: potion recipes
	// TODO: composting
	// TODO: check if block modification works on neo

	public static void init() { //Alan Wilder Wild
		WilderWildRegistries.init();
		WWFeatureFlags.init();

		WWMinecraftDataFixer.applyDataFixes();
		WWDataFixer.applyDataFixes();

		WWEntityTypes.init();
		WWDataComponents.init();
		WWMemoryModuleTypes.init();
		WWSensorTypes.init();
		WWAttachmentTypes.init();
		WWActivities.init();
		WWEnvironmentAttributes.init();

		WWGameEvents.init();
		WWSounds.init();
		WWBlocks.init();
		WWItems.init();
		WWShearsDispenseItemBehaviors.init();
		WWBlockEntityTypes.init();
		WWParticleTypes.init();
		WWMobEffects.init();
		WWCriteria.init();
		WWFeatures.init();
		WWBiomes.init();
		WWWorldgen.init();
		WWStructureModifications.init();

		WWWindDisturbances.init();
		WWWindManagerExtension.init();
		WWSoundPredicates.init();

		WWResources.register();

		WWAmbienceAndMiscConfig.CONFIG.load(true);
		WWBlockConfig.CONFIG.load(true);
		WWEntityConfig.CONFIG.load(true);
		WWItemConfig.CONFIG.load(true);
		WWWorldgenConfig.CONFIG.load(true);
	}
}
