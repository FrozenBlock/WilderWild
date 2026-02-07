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

package net.frozenblock.wilderwild.config.gui;

// TODO: Re-enable when cloth config is unobfuscated
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import static net.frozenblock.wilderwild.WWConstants.text;
import static net.frozenblock.wilderwild.WWConstants.tooltip;
import static net.frozenblock.wilderwild.config.WWConfigHelper.booleanEntry;
import static net.frozenblock.wilderwild.config.WWConfigHelper.intSliderEntry;
import net.frozenblock.wilderwild.config.WWBlockConfig;

@Environment(EnvType.CLIENT)
public final class WWBlockConfigGui {

	private WWBlockConfigGui() {
		throw new UnsupportedOperationException("WWBlockConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		category.addEntry(booleanEntry(builder, "reach_boost_beacon", WWBlockConfig.REACH_BOOST_BEACON));
		category.addEntry(booleanEntry(builder, "pollen_particles", WWBlockConfig.POLLEN_PARTICLES));
		category.addEntry(booleanEntry(builder, "log_hollowing", WWBlockConfig.LOG_HOLLOWING));
		category.addEntry(booleanEntry(builder, "cactus_placement", WWBlockConfig.CACTUS_PLACEMENT));
		category.addEntry(booleanEntry(builder, "new_reinforced_deepslate", WWBlockConfig.NEW_REINFORCED_DEEPSLATE));
		category.addEntry(booleanEntry(builder, "froglight_goop_growth", WWBlockConfig.FROGLIGHT_GOOP_GROWTH));
		category.addEntry(booleanEntry(builder, "new_reinforced_deepslate", WWBlockConfig.NEW_REINFORCED_DEEPSLATE));
		category.addEntry(booleanEntry(builder, "frosted_ice_cracking", WWBlockConfig.FROSTED_ICE_CRACKING));
		category.addEntry(booleanEntry(builder, "chest_bubbling", WWBlockConfig.CHEST_BUBBLING));
		category.addEntry(booleanEntry(builder, "thick_big_fungus_growth", WWBlockConfig.THICK_BIG_FUNGUS_GROWTH));

		// SCULK
		var shriekerGargling = booleanEntry(builder, "shrieker_gargling", WWBlockConfig.SHRIEKER_GARGLING);
		var shriekerOutline = booleanEntry(builder, "shrieker_outline", WWBlockConfig.SHRIEKER_OUTLINE);
		var billboardTendrils = booleanEntry(builder, "billboard_tendrils", WWBlockConfig.BILLBOARD_TENDRILS);
		var tendrilsCarryEvents = booleanEntry(builder, "tendrils_carry_events", WWBlockConfig.TENDRILS_CARRY_EVENTS);
		var tendrilGeneration = booleanEntry(builder, "hanging_tendril_generation", WWBlockConfig.TENDRIL_GENERATION);
		var osseousSculkGeneration = booleanEntry(builder, "osseous_sculk_generation", WWBlockConfig.OSSEOUS_SCULK_GENERATION);
		var sculkBuildingBlocksGeneration = booleanEntry(builder, "sculk_building_blocks_generation", WWBlockConfig.SCULK_BUILDING_BLOCKS_GENERATION);

		FrozenClothConfig.createSubCategory(builder, category, text("sculk"),
			false,
			tooltip("sculk"),
			shriekerGargling, shriekerOutline,
			tendrilsCarryEvents, billboardTendrils, tendrilGeneration,
			osseousSculkGeneration,
			sculkBuildingBlocksGeneration
		);

		// MESOGLEA
		var mesogleaFluid = booleanEntry(builder, "mesoglea_fluid", WWBlockConfig.MESOGLEA_RENDERS_AS_FLUID);
		var mesogleaBubbleColumns = booleanEntry(builder, "mesoglea_bubble_columns", WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS);

		FrozenClothConfig.createSubCategory(builder, category, text("mesoglea"),
			false,
			tooltip("mesoglea"),
			mesogleaBubbleColumns, mesogleaFluid
		);

		// TERMITE
		var termitesOnlyEatNaturalBlocks = booleanEntry(builder, "termites_only_eat_natural_blocks", WWBlockConfig.TERMITE_ONLY_EATS_NATURAL_BLOCKS);
		var maxTermiteDistance = intSliderEntry(builder, "max_termite_distance", WWBlockConfig.TERMITE_MAX_DISTANCE, 1, 72);
		var maxNaturalTermiteDistance = intSliderEntry(builder, "max_natural_termite_distance", WWBlockConfig.TERMITE_MAX_DISTANCE, 1, 72);

		FrozenClothConfig.createSubCategory(builder, category, text("termite"),
			false,
			tooltip("termite"),
			termitesOnlyEatNaturalBlocks, maxTermiteDistance, maxNaturalTermiteDistance
		);

		// FLOWER
		var bonemealDandelions = booleanEntry(builder, "bone_meal_dandelions", WWBlockConfig.BONE_MEAL_DANDELIONS);
		var shearSeedingDandelions = booleanEntry(builder, "shear_seeding_dandelions", WWBlockConfig.SHEAR_SEEDING_DANDELIONS);
		var bonemealLilypads = booleanEntry(builder, "bone_meal_lilypads", WWBlockConfig.BONE_MEAL_LILY_PADS);
		var shearFloweringLilypads = booleanEntry(builder, "shear_flowering_lilypads", WWBlockConfig.SHEAR_FLOWERING_LILY_PADS);

		FrozenClothConfig.createSubCategory(builder, category, text("flower"),
			false,
			tooltip("flower"),
			bonemealDandelions, shearSeedingDandelions, bonemealLilypads, shearFloweringLilypads
		);

		// STONE CHEST
		var stoneChestTimer = intSliderEntry(builder, "stone_chest_timer", WWBlockConfig.STONE_CHEST_TIMER, 50, 200);
		var addStoneChests = booleanEntry(builder, "add_stone_chests", WWBlockConfig.ADD_STONE_CHESTS);

		FrozenClothConfig.createSubCategory(builder, category, text("stone_chest"),
			false,
			tooltip("stone_chest"),
			stoneChestTimer, addStoneChests
		);

		var allowSnowlogging = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("allow_snowlogging"), modifiedSnowlogging.snowlogging)
				.setDefaultValue(defaultConfig.snowlogging.snowlogging)
				.setSaveConsumer(newValue -> snowlogging.snowlogging = newValue)
				.setTooltip(tooltip("allow_snowlogging"))
				.requireRestart()
				.build(),
			snowlogging.getClass(),
			"snowlogging",
			configInstance
		);

		var snowlogWalls = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("snowlog_walls"), modifiedSnowlogging.snowlogWalls)
				.setDefaultValue(defaultConfig.snowlogging.snowlogWalls)
				.setSaveConsumer(newValue -> snowlogging.snowlogWalls = newValue)
				.setTooltip(tooltip("snowlog_walls"))
				.requireRestart()
				.build(),
			snowlogging.getClass(),
			"snowlogWalls",
			configInstance
		);

		var naturalSnowlogging = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("natural_snowlogging"), modifiedSnowlogging.naturalSnowlogging)
				.setDefaultValue(defaultConfig.snowlogging.naturalSnowlogging)
				.setSaveConsumer(newValue -> snowlogging.naturalSnowlogging = newValue)
				.setTooltip(tooltip("natural_snowlogging"))
				.build(),
			snowlogging.getClass(),
			"naturalSnowlogging",
			configInstance
		);

		var snowloggingCategory = FrozenClothConfig.createSubCategory(builder, category, text("snowlogging"),
			false,
			tooltip("snowlogging"),
			allowSnowlogging, snowlogWalls, naturalSnowlogging
		);

		var extraMagmaParticles =
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("extra_magma_particles"), modifiedFire.extraMagmaParticles)
					.setDefaultValue(defaultConfig.fire.extraMagmaParticles)
					.setSaveConsumer(newValue -> config.fire.extraMagmaParticles = newValue)
					.setTooltip(tooltip("extra_magma_particles"))
					.build(),
				fire.getClass(),
				"extraMagmaParticles",
				configInstance
			);

		var soulFireSounds =
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("soul_fire_sounds"), modifiedFire.soulFireSounds)
					.setDefaultValue(defaultConfig.fire.soulFireSounds)
					.setSaveConsumer(newValue -> config.fire.soulFireSounds = newValue)
					.setTooltip(tooltip("soul_fire_sounds"))
					.build(),
				fire.getClass(),
				"soulFireSounds",
				configInstance
			);

		var fireCategory = FrozenClothConfig.createSubCategory(builder, category, text("fire"),
			false,
			tooltip("snowlogging"),
			extraMagmaParticles, soulFireSounds
		);

		var cactusSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("cactus_sounds"), modifiedBlockSounds.cactusSounds)
				.setDefaultValue(defaultConfig.blockSounds.cactusSounds)
				.setSaveConsumer(newValue -> blockSounds.cactusSounds = newValue)
				.setTooltip(tooltip("cactus_sounds"))
				.build(),
			blockSounds.getClass(),
			"cactusSounds",
			configInstance
		);

		var claySounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("clay_sounds"), modifiedBlockSounds.claySounds)
				.setDefaultValue(defaultConfig.blockSounds.claySounds)
				.setSaveConsumer(newValue -> blockSounds.claySounds = newValue)
				.setTooltip(tooltip("clay_sounds"))
				.build(),
			blockSounds.getClass(),
			"claySounds",
			configInstance
		);

		var coarseDirtSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("coarse_dirt_sounds"), modifiedBlockSounds.coarseDirtSounds)
				.setDefaultValue(defaultConfig.blockSounds.coarseDirtSounds)
				.setSaveConsumer(newValue -> blockSounds.coarseDirtSounds = newValue)
				.setTooltip(tooltip("coarse_dirt_sounds"))
				.build(),
			blockSounds.getClass(),
			"coarseDirtSounds",
			configInstance
		);

		var deadBushSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("dead_bush_sounds"), modifiedBlockSounds.deadBushSounds)
				.setDefaultValue(defaultConfig.blockSounds.deadBushSounds)
				.setSaveConsumer(newValue -> blockSounds.deadBushSounds = newValue)
				.setTooltip(tooltip("dead_bush_sounds"))
				.build(),
			blockSounds.getClass(),
			"deadBushSounds",
			configInstance
		);

		var flowerSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("flower_sounds"), modifiedBlockSounds.flowerSounds)
				.setDefaultValue(defaultConfig.blockSounds.flowerSounds)
				.setSaveConsumer(newValue -> blockSounds.flowerSounds = newValue)
				.setTooltip(tooltip("flower_sounds"))
				.build(),
			blockSounds.getClass(),
			"flowerSounds",
			configInstance
		);

		var grassSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("grass_sounds"), modifiedBlockSounds.grassSounds)
				.setDefaultValue(defaultConfig.blockSounds.grassSounds)
				.setSaveConsumer(newValue -> blockSounds.grassSounds = newValue)
				.setTooltip(tooltip("grass_sounds"))
				.build(),
			blockSounds.getClass(),
			"grassSounds",
			configInstance
		);

		var magmaSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("magma_sounds"), modifiedBlockSounds.magmaSounds)
				.setDefaultValue(defaultConfig.blockSounds.magmaSounds)
				.setSaveConsumer(newValue -> blockSounds.magmaSounds = newValue)
				.setTooltip(tooltip("magma_sounds"))
				.build(),
			blockSounds.getClass(),
			"magmaSounds",
			configInstance
		);

		var saplingSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("sapling_sounds"), modifiedBlockSounds.saplingSounds)
				.setDefaultValue(defaultConfig.blockSounds.saplingSounds)
				.setSaveConsumer(newValue -> blockSounds.saplingSounds = newValue)
				.setTooltip(tooltip("sapling_sounds"))
				.build(),
			blockSounds.getClass(),
			"saplingSounds",
			configInstance
		);

		var gravelSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("gravel_sounds"), modifiedBlockSounds.gravelSounds)
				.setDefaultValue(defaultConfig.blockSounds.gravelSounds)
				.setSaveConsumer(newValue -> blockSounds.gravelSounds = newValue)
				.setTooltip(tooltip("gravel_sounds"))
				.build(),
			blockSounds.getClass(),
			"gravelSounds",
			configInstance
		);

		var iceSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("ice_sounds"), modifiedBlockSounds.iceSounds)
				.setDefaultValue(defaultConfig.blockSounds.iceSounds)
				.setSaveConsumer(newValue -> blockSounds.iceSounds = newValue)
				.setTooltip(tooltip("ice_sounds"))
				.build(),
			blockSounds.getClass(),
			"iceSounds",
			configInstance
		);

		var frostedIceSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("frosted_ice_sounds"), modifiedBlockSounds.frostedIceSounds)
				.setDefaultValue(defaultConfig.blockSounds.frostedIceSounds)
				.setSaveConsumer(newValue -> blockSounds.frostedIceSounds = newValue)
				.setTooltip(tooltip("frosted_ice_sounds"))
				.build(),
			blockSounds.getClass(),
			"frostedIceSounds",
			configInstance
		);

		var leafSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("leaf_sounds"), modifiedBlockSounds.leafSounds)
				.setDefaultValue(defaultConfig.blockSounds.leafSounds)
				.setSaveConsumer(newValue -> blockSounds.leafSounds = newValue)
				.setTooltip(tooltip("leaf_sounds"))
				.build(),
			blockSounds.getClass(),
			"leafSounds",
			configInstance
		);

		var lilyPadSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("lily_pad_sounds"), modifiedBlockSounds.lilyPadSounds)
				.setDefaultValue(defaultConfig.blockSounds.lilyPadSounds)
				.setSaveConsumer(newValue -> blockSounds.lilyPadSounds = newValue)
				.setTooltip(tooltip("lily_pad_sounds"))
				.build(),
			blockSounds.getClass(),
			"lilyPadSounds",
			configInstance
		);

		var melonSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("melon_sounds"), modifiedBlockSounds.melonSounds)
				.setDefaultValue(defaultConfig.blockSounds.melonSounds)
				.setSaveConsumer(newValue -> blockSounds.melonSounds = newValue)
				.setTooltip(tooltip("melon_sounds"))
				.build(),
			blockSounds.getClass(),
			"melonSounds",
			configInstance
		);

		var mossSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("moss_sounds"), modifiedBlockSounds.mossSounds)
				.setDefaultValue(defaultConfig.blockSounds.mossSounds)
				.setSaveConsumer(newValue -> blockSounds.mossSounds = newValue)
				.setTooltip(tooltip("moss_sounds"))
				.build(),
			blockSounds.getClass(),
			"mossSounds",
			configInstance
		);

		var mushroomBlockSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("mushroom_block_sounds"), modifiedBlockSounds.mushroomBlockSounds)
				.setDefaultValue(defaultConfig.blockSounds.mushroomBlockSounds)
				.setSaveConsumer(newValue -> blockSounds.mushroomBlockSounds = newValue)
					.setTooltip(tooltip("mushroom_block_sounds"))
			.build(),
			blockSounds.getClass(),
			"mushroomBlockSounds",
			configInstance
		);

		var podzolSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("podzol_sounds"), modifiedBlockSounds.podzolSounds)
				.setDefaultValue(defaultConfig.blockSounds.podzolSounds)
				.setSaveConsumer(newValue -> blockSounds.podzolSounds = newValue)
				.setTooltip(tooltip("podzol_sounds"))
				.build(),
			blockSounds.getClass(),
			"podzolSounds",
			configInstance
		);

		var paleOakSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("pale_oak_sounds"), modifiedBlockSounds.paleOakSounds)
				.setDefaultValue(defaultConfig.blockSounds.paleOakSounds)
				.setSaveConsumer(newValue -> blockSounds.paleOakSounds = newValue)
				.setTooltip(tooltip("pale_oak_sounds"))
				.build(),
			blockSounds.getClass(),
			"paleOakSounds",
			configInstance
		);

		var reinforcedDeepslateSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("reinforced_deepslate_sounds"), modifiedBlockSounds.reinforcedDeepslateSounds)
				.setDefaultValue(defaultConfig.blockSounds.reinforcedDeepslateSounds)
				.setSaveConsumer(newValue -> blockSounds.reinforcedDeepslateSounds = newValue)
				.setTooltip(tooltip("reinforced_deepslate_sounds"))
				.build(),
			blockSounds.getClass(),
			"reinforcedDeepslateSounds",
			configInstance
		);

		var sandstoneSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("sandstone_sounds"), modifiedBlockSounds.sandstoneSounds)
				.setDefaultValue(defaultConfig.blockSounds.sandstoneSounds)
				.setSaveConsumer(newValue -> blockSounds.sandstoneSounds = newValue)
				.setTooltip(tooltip("sandstone_sounds"))
				.build(),
			blockSounds.getClass(),
			"sandstoneSounds",
			configInstance
		);

		var sugarCaneSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("sugar_cane_sounds"), modifiedBlockSounds.sugarCaneSounds)
				.setDefaultValue(defaultConfig.blockSounds.sugarCaneSounds)
				.setSaveConsumer(newValue -> blockSounds.sugarCaneSounds = newValue)
				.setTooltip(tooltip("sugar_cane_sounds"))
				.build(),
			blockSounds.getClass(),
			"sugarCaneSounds",
			configInstance
		);

		var witherRoseSounds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("wither_rose_sounds"), modifiedBlockSounds.witherRoseSounds)
				.setDefaultValue(defaultConfig.blockSounds.witherRoseSounds)
				.setSaveConsumer(newValue -> blockSounds.witherRoseSounds = newValue)
				.setTooltip(tooltip("wither_rose_sounds"))
				.build(),
			blockSounds.getClass(),
			"witherRoseSounds",
			configInstance
		);

		var blockSoundsCategory = FrozenClothConfig.createSubCategory(builder, category, text("block_sounds"),
			false,
			tooltip("block_sounds"),
			cactusSounds, claySounds, coarseDirtSounds, deadBushSounds, flowerSounds, frostedIceSounds,
			grassSounds, gravelSounds, iceSounds, leafSounds, lilyPadSounds, magmaSounds, melonSounds, mossSounds,
			mushroomBlockSounds, paleOakSounds, podzolSounds, reinforcedDeepslateSounds, sandstoneSounds, saplingSounds,
			sugarCaneSounds, witherRoseSounds
		);
	}

}
