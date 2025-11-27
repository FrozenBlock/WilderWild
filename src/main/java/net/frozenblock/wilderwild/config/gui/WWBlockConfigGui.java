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

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import static net.frozenblock.wilderwild.WWConstants.text;
import static net.frozenblock.wilderwild.WWConstants.tooltip;
import net.frozenblock.wilderwild.config.WWBlockConfig;

@Environment(EnvType.CLIENT)
public final class WWBlockConfigGui {

	private WWBlockConfigGui() {
		throw new UnsupportedOperationException("WWBlockConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		final var config = WWBlockConfig.get(true);
		final var modifiedConfig = WWBlockConfig.getWithSync();
		final Class<? extends WWBlockConfig> clazz = config.getClass();
		final Config<?> configInstance = WWBlockConfig.INSTANCE;
		final var defaultConfig = WWBlockConfig.INSTANCE.defaultInstance();
		final var blockSounds = config.blockSounds;
		final var modifiedBlockSounds = modifiedConfig.blockSounds;
		final var stoneChest = config.stoneChest;
		final var modifiedStoneChest = modifiedConfig.stoneChest;
		final var termite = config.termite;
		final var modifiedTermite = modifiedConfig.termite;
		final var snowlogging = config.snowlogging;
		final var modifiedSnowlogging = modifiedConfig.snowlogging;
		final var fire = config.fire;
		final var modifiedFire = modifiedConfig.fire;
		final var mesoglea = config.mesoglea;
		final var modifiedMesoglea = modifiedConfig.mesoglea;
		final var sculk = config.sculk;
		final var modifiedSculk = modifiedConfig.sculk;
		final var flower = config.flower;
		final var modifiedFlower = modifiedConfig.flower;

		var reachBoostBeacon = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("reach_boost_beacon"), modifiedConfig.reachBoostBeacon)
					.setDefaultValue(defaultConfig.reachBoostBeacon)
					.setSaveConsumer(newValue -> config.reachBoostBeacon = newValue)
					.setTooltip(tooltip("reach_boost_beacon"))
					.requireRestart()
					.build(),
				clazz,
				"reachBoostBeacon",
				configInstance
			)
		);

		var pollenParticles = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("pollen_particles"), modifiedConfig.pollenParticles)
					.setDefaultValue(defaultConfig.pollenParticles)
					.setSaveConsumer(newValue -> config.pollenParticles = newValue)
					.setTooltip(tooltip("pollen_particles"))
					.build(),
				clazz,
				"pollenParticles",
				configInstance
			)
		);

		var logHollowing = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("log_hollowing"), modifiedConfig.logHollowing)
					.setDefaultValue(defaultConfig.logHollowing)
					.setSaveConsumer(newValue -> config.logHollowing = newValue)
					.setTooltip(tooltip("log_hollowing"))
					.build(),
				clazz,
				"logHollowing",
				configInstance
			)
		);

		var cactusPlacement = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("cactus_placement"), modifiedConfig.cactusPlacement)
					.setDefaultValue(defaultConfig.cactusPlacement)
					.setSaveConsumer(newValue -> config.cactusPlacement = newValue)
					.setTooltip(tooltip("cactus_placement"))
					.build(),
				clazz,
				"cactusPlacement",
				configInstance
			)
		);

		var newReinforcedDeepslate = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("new_reinforced_deepslate"), modifiedConfig.newReinforcedDeepslate)
					.setDefaultValue(defaultConfig.newReinforcedDeepslate)
					.setSaveConsumer(newValue -> config.newReinforcedDeepslate = newValue)
					.setTooltip(tooltip("new_reinforced_deepslate"))
					.build(),
				clazz,
				"newReinforcedDeepslate",
				configInstance
			)
		);

		var froglightGoopGrowth = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("froglight_goop_growth"), modifiedConfig.froglightGoopGrowth)
					.setDefaultValue(defaultConfig.froglightGoopGrowth)
					.setSaveConsumer(newValue -> config.froglightGoopGrowth = newValue)
					.setTooltip(tooltip("froglight_goop_growth"))
					.build(),
				clazz,
				"froglightGoopGrowth",
				configInstance
			)
		);

		var frostedIceCracking = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("frosted_ice_cracking"), modifiedConfig.frostedIceCracking)
					.setDefaultValue(defaultConfig.frostedIceCracking)
					.setSaveConsumer(newValue -> config.frostedIceCracking = newValue)
					.setTooltip(tooltip("frosted_ice_cracking"))
					.build(),
				clazz,
				"frostedIceCracking",
				configInstance
			)
		);

		var chestBubbling = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("chest_bubbling"), modifiedConfig.chestBubbling)
					.setDefaultValue(defaultConfig.chestBubbling)
					.setSaveConsumer(newValue -> config.chestBubbling = newValue)
					.setTooltip(tooltip("chest_bubbling"))
					.build(),
				clazz,
				"chestBubbling",
				configInstance
			)
		);

		var thickBigFungusGrowth = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("thick_big_fungus_growth"), modifiedConfig.thickBigFungusGrowth)
					.setDefaultValue(defaultConfig.thickBigFungusGrowth)
					.setSaveConsumer(newValue -> config.thickBigFungusGrowth = newValue)
					.setTooltip(tooltip("thick_big_fungus_growth"))
					.build(),
				clazz,
				"thickBigFungusGrowth",
				configInstance
			)
		);

		var shriekerGargling = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("shrieker_gargling"), modifiedSculk.shriekerGargling)
				.setDefaultValue(defaultConfig.sculk.shriekerGargling)
				.setSaveConsumer(newValue -> sculk.shriekerGargling = newValue)
				.setTooltip(tooltip("shrieker_gargling"))
				.build(),
			sculk.getClass(),
			"shriekerGargling",
			configInstance
		);

		var shriekerOutline = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("shrieker_outline"), modifiedSculk.shriekerOutline)
				.setDefaultValue(defaultConfig.sculk.shriekerOutline)
				.setSaveConsumer(newValue -> sculk.shriekerOutline = newValue)
				.setTooltip(tooltip("shrieker_outline"))
				.build(),
			sculk.getClass(),
			"shriekerOutline",
			configInstance
		);

		var billboardTendrils = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("billboard_tendrils"), modifiedSculk.billboardTendrils)
				.setDefaultValue(defaultConfig.sculk.billboardTendrils)
				.setSaveConsumer(newValue -> sculk.billboardTendrils = newValue)
				.setTooltip(tooltip("billboard_tendrils"))
				.build(),
			sculk.getClass(),
			"billboardTendrils",
			configInstance
		);

		var tendrilsCarryEvents = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("tendrils_carry_events"), modifiedSculk.tendrilsCarryEvents)
				.setDefaultValue(defaultConfig.sculk.tendrilsCarryEvents)
				.setSaveConsumer(newValue -> sculk.tendrilsCarryEvents = newValue)
				.setTooltip(tooltip("tendrils_carry_events"))
				.build(),
			sculk.getClass(),
			"tendrilsCarryEvents",
			configInstance
		);

		var tendrilGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("hanging_tendril_generation"), modifiedSculk.tendrilGeneration)
				.setDefaultValue(defaultConfig.sculk.tendrilGeneration)
				.setSaveConsumer(newValue -> sculk.tendrilGeneration = newValue)
				.setTooltip(tooltip("hanging_tendril_generation"))
				.build(),
			sculk.getClass(),
			"tendrilGeneration",
			configInstance
		);

		var osseousSculkGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("osseous_sculk_generation"), modifiedSculk.osseousSculkGeneration)
				.setDefaultValue(defaultConfig.sculk.osseousSculkGeneration)
				.setSaveConsumer(newValue -> sculk.osseousSculkGeneration = newValue)
				.setTooltip(tooltip("osseous_sculk_generation"))
				.build(),
			sculk.getClass(),
			"osseousSculkGeneration",
			configInstance
		);

		var sculkBuildingBlocksGeneration = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("sculk_building_blocks_generation"), modifiedSculk.sculkBuildingBlocksGeneration)
				.setDefaultValue(defaultConfig.sculk.sculkBuildingBlocksGeneration)
				.setSaveConsumer(newValue -> sculk.sculkBuildingBlocksGeneration = newValue)
				.setTooltip(tooltip("sculk_building_blocks_generation"))
				.build(),
			sculk.getClass(),
			"sculkBuildingBlocksGeneration",
			configInstance
		);

		var sculkCategory = FrozenClothConfig.createSubCategory(builder, category, text("sculk"),
			false,
			tooltip("sculk"),
			shriekerGargling, shriekerOutline,
			tendrilsCarryEvents, billboardTendrils, tendrilGeneration,
			osseousSculkGeneration,
			sculkBuildingBlocksGeneration
		);

		var mesogleaFluid = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("mesoglea_fluid"), modifiedMesoglea.mesogleaFluid)
				.setDefaultValue(defaultConfig.mesoglea.mesogleaFluid)
				.setSaveConsumer(newValue -> mesoglea.mesogleaFluid = newValue)
				.setTooltip(tooltip("mesoglea_fluid"))
				.build(),
			mesoglea.getClass(),
			"mesogleaFluid",
			configInstance
		);

		var mesogleaBubbleColumns = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("mesoglea_bubble_columns"), modifiedMesoglea.mesogleaBubbleColumns)
				.setDefaultValue(defaultConfig.mesoglea.mesogleaBubbleColumns)
				.setSaveConsumer(newValue -> mesoglea.mesogleaBubbleColumns = newValue)
				.setTooltip(tooltip("mesoglea_bubble_columns"))
				.build(),
			mesoglea.getClass(),
			"mesogleaBubbleColumns",
			configInstance
		);

		var mesogleaCategory = FrozenClothConfig.createSubCategory(builder, category, text("mesoglea"),
			false,
			tooltip("mesoglea"),
			mesogleaBubbleColumns, mesogleaFluid
		);

		var termitesOnlyEatNaturalBlocks = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("termites_only_eat_natural_blocks"), modifiedTermite.onlyEatNaturalBlocks)
				.setDefaultValue(defaultConfig.termite.onlyEatNaturalBlocks)
				.setSaveConsumer(newValue -> termite.onlyEatNaturalBlocks = newValue)
				.setTooltip(tooltip("termites_only_eat_natural_blocks"))
				.requireRestart()
				.build(),
			termite.getClass(),
			"onlyEatNaturalBlocks",
			configInstance
		);

		var maxTermiteDistance = FrozenClothConfig.syncedEntry(
			builder.startIntSlider(text("max_termite_distance"), modifiedTermite.maxDistance, 1, 72)
				.setDefaultValue(defaultConfig.termite.maxDistance)
				.setSaveConsumer(newValue -> termite.maxDistance = newValue)
				.setTooltip(tooltip("max_termite_distance"))
				.build(),
			termite.getClass(),
			"maxDistance",
			configInstance
		);

		var maxNaturalTermiteDistance = FrozenClothConfig.syncedEntry(
			builder.startIntSlider(text("max_natural_termite_distance"), modifiedTermite.maxNaturalDistance, 1, 72)
				.setDefaultValue(defaultConfig.termite.maxNaturalDistance)
				.setSaveConsumer(newValue -> termite.maxNaturalDistance = newValue)
				.setTooltip(tooltip("max_natural_termite_distance"))
				.build(),
			termite.getClass(),
			"maxNaturalDistance",
			configInstance
		);

		var termiteCategory = FrozenClothConfig.createSubCategory(builder, category, text("termite"),
			false,
			tooltip("termite"),
			termitesOnlyEatNaturalBlocks, maxTermiteDistance, maxNaturalTermiteDistance
		);

		var bonemealDandelions = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("bone_meal_dandelions"), modifiedFlower.bonemealDandelions)
				.setDefaultValue(defaultConfig.flower.bonemealDandelions)
				.setSaveConsumer(newValue -> flower.bonemealDandelions = newValue)
				.setTooltip(tooltip("bone_meal_dandelions"))
				.build(),
			flower.getClass(),
			"bonemealDandelions",
			configInstance
		);

		var shearSeedingDandelions = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("shear_seeding_dandelions"), modifiedFlower.shearSeedingDandelions)
				.setDefaultValue(defaultConfig.flower.shearSeedingDandelions)
				.setSaveConsumer(newValue -> flower.shearSeedingDandelions = newValue)
				.setTooltip(tooltip("shear_seeding_dandelions"))
				.build(),
			flower.getClass(),
			"shearSeedingDandelions",
			configInstance
		);

		var bonemealLilypads = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("bone_meal_lilypads"), modifiedFlower.bonemealLilypads)
				.setDefaultValue(defaultConfig.flower.bonemealLilypads)
				.setSaveConsumer(newValue -> flower.bonemealLilypads = newValue)
				.setTooltip(tooltip("bone_meal_lilypads"))
				.build(),
			flower.getClass(),
			"bonemealLilypads",
			configInstance
		);

		var shearFloweringLilypads = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("shear_flowering_lilypads"), modifiedFlower.shearFloweringLilypads)
				.setDefaultValue(defaultConfig.flower.shearFloweringLilypads)
				.setSaveConsumer(newValue -> flower.shearFloweringLilypads = newValue)
				.setTooltip(tooltip("shear_flowering_lilypads"))
				.build(),
			flower.getClass(),
			"shearFloweringLilypads",
			configInstance
		);

		var flowerCategory = FrozenClothConfig.createSubCategory(builder, category, text("flower"),
			false,
			tooltip("flower"),
			bonemealDandelions, shearSeedingDandelions, bonemealLilypads, shearFloweringLilypads
		);

		var stoneChestTimer = FrozenClothConfig.syncedEntry(
			builder.startIntSlider(text("stone_chest_timer"), modifiedStoneChest.stoneChestTimer, 50, 200)
				.setDefaultValue(defaultConfig.stoneChest.stoneChestTimer)
				.setSaveConsumer(newValue -> stoneChest.stoneChestTimer = newValue)
				.setTooltip(tooltip("stone_chest_timer"))
				.build(),
			stoneChest.getClass(),
			"stoneChestTimer",
			configInstance
		);

		var addStoneChests = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("add_stone_chests"), modifiedStoneChest.addStoneChests)
				.setDefaultValue(defaultConfig.stoneChest.addStoneChests)
				.setSaveConsumer(newValue -> stoneChest.addStoneChests = newValue)
				.requireRestart()
				.setTooltip(tooltip("add_stone_chests"))
				.build(),
			stoneChest.getClass(),
			"addStoneChests",
			configInstance
		);

		var stoneChestCategory = FrozenClothConfig.createSubCategory(builder, category, text("stone_chest"),
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
