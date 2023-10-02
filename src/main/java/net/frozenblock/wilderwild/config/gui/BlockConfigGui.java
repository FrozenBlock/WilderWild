/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.config.defaults.DefaultBlockConfig;
import net.frozenblock.wilderwild.WilderSharedConstants;
import static net.frozenblock.wilderwild.WilderSharedConstants.text;
import static net.frozenblock.wilderwild.WilderSharedConstants.tooltip;

@Environment(EnvType.CLIENT)
public final class BlockConfigGui {
	private BlockConfigGui() {
		throw new UnsupportedOperationException("BlockConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder) {
		var config = BlockConfig.get();
		var blockSounds = config.blockSounds;
		var stoneChest = config.stoneChest;
		var termite = config.termite;
		category.setBackground(WilderSharedConstants.id("textures/config/block.png"));
		var shriekerGargling = category.addEntry(entryBuilder.startBooleanToggle(text("shrieker_gargling"), config.shriekerGargling)
			.setDefaultValue(DefaultBlockConfig.SHRIEKER_GARGLING)
			.setSaveConsumer(newValue -> config.shriekerGargling = newValue)
			.setTooltip(tooltip("shrieker_gargling"))
			.build()
		);
		var soulFireSounds = category.addEntry(entryBuilder.startBooleanToggle(text("soul_fire_sounds"), config.soulFireSounds)
			.setDefaultValue(DefaultBlockConfig.SOUL_FIRE_SOUNDS)
			.setSaveConsumer(newValue -> config.soulFireSounds = newValue)
			.setTooltip(tooltip("soul_fire_sounds"))
			.build()
		);
		var billboardTendrils = category.addEntry(entryBuilder.startBooleanToggle(text("billboard_tendrils"), config.billboardTendrils)
			.setDefaultValue(DefaultBlockConfig.BILLBOARD_TENDRILS)
			.setSaveConsumer(newValue -> config.billboardTendrils = newValue)
			.setTooltip(tooltip("billboard_tendrils"))
			.build()
		);
		var tendrilsCarryEvents = category.addEntry(entryBuilder.startBooleanToggle(text("tendrils_carry_events"), config.tendrilsCarryEvents)
			.setDefaultValue(DefaultBlockConfig.TENDRILS_CARRY_EVENTS)
			.setSaveConsumer(newValue -> config.tendrilsCarryEvents = newValue)
			.setTooltip(tooltip("tendrils_carry_events"))
			.build()
		);
		var pollenParticles = category.addEntry(entryBuilder.startBooleanToggle(text("pollen_particles"), config.pollenParticles)
			.setDefaultValue(DefaultBlockConfig.POLLEN_PARTICLES)
			.setSaveConsumer(newValue -> config.pollenParticles = newValue)
			.setTooltip(tooltip("pollen_particles"))
			.build()
		);
		var logHollowing = category.addEntry(entryBuilder.startBooleanToggle(text("log_hollowing"), config.logHollowing)
			.setDefaultValue(DefaultBlockConfig.LOG_HOLLOWING)
			.setSaveConsumer(newValue -> config.logHollowing = newValue)
			.setTooltip(tooltip("log_hollowing"))
			.build()
		);
		var cactusPlacement = category.addEntry(entryBuilder.startBooleanToggle(text("cactus_placement"), config.cactusPlacement)
			.setDefaultValue(DefaultBlockConfig.CACTUS_PLACEMENT)
			.setSaveConsumer(newValue -> config.cactusPlacement = newValue)
			.setTooltip(tooltip("cactus_placement"))
			.build()
		);

		var cactusSounds = entryBuilder.startBooleanToggle(text("cactus_sounds"), blockSounds.cactusSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.CACTUS_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.cactusSounds = newValue)
			.setTooltip(tooltip("cactus_sounds"))
			.build();

		var claySounds = entryBuilder.startBooleanToggle(text("clay_sounds"), blockSounds.claySounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.CLAY_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.claySounds = newValue)
			.setTooltip(tooltip("clay_sounds"))
			.build();

		var coarseDirtSounds = entryBuilder.startBooleanToggle(text("coarse_dirt_sounds"), blockSounds.coarseDirtSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.COARSE_DIRT_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.coarseDirtSounds = newValue)
			.setTooltip(tooltip("coarse_dirt_sounds"))
			.build();

		var cobwebSounds = entryBuilder.startBooleanToggle(text("cobweb_sounds"), blockSounds.cobwebSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.COBWEB_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.cobwebSounds = newValue)
			.setTooltip(tooltip("cobweb_sounds"))
			.build();

		var deadBushSounds = entryBuilder.startBooleanToggle(text("dead_bush_sounds"), blockSounds.deadBushSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.DEAD_BUSH_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.deadBushSounds = newValue)
			.setTooltip(tooltip("dead_bush_sounds"))
			.build();

		var flowerSounds = entryBuilder.startBooleanToggle(text("flower_sounds"), blockSounds.flowerSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.FLOWER_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.flowerSounds = newValue)
			.setTooltip(tooltip("flower_sounds"))
			.build();

		var saplingSounds = entryBuilder.startBooleanToggle(text("sapling_sounds"), blockSounds.saplingSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.SAPLING_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.saplingSounds = newValue)
			.setTooltip(tooltip("sapling_sounds"))
			.build();

		var gravelSounds = entryBuilder.startBooleanToggle(text("gravel_sounds"), blockSounds.gravelSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.GRAVEL_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.gravelSounds = newValue)
			.setTooltip(tooltip("gravel_sounds"))
			.build();

		var iceSounds = entryBuilder.startBooleanToggle(text("ice_sounds"), blockSounds.iceSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.ICE_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.iceSounds = newValue)
			.setTooltip(tooltip("ice_sounds"))
			.build();

		var frostedIceSounds = entryBuilder.startBooleanToggle(text("frosted_ice_sounds"), blockSounds.frostedIceSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.FROSTED_ICE_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.frostedIceSounds = newValue)
			.setTooltip(tooltip("frosted_ice_sounds"))
			.build();

		var leafSounds = entryBuilder.startBooleanToggle(text("leaf_sounds"), blockSounds.leafSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.LEAF_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.leafSounds = newValue)
			.setTooltip(tooltip("leaf_sounds"))
			.build();

		var lilyPadSounds = entryBuilder.startBooleanToggle(text("lily_pad_sounds"), blockSounds.lilyPadSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.LILY_PAD_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.lilyPadSounds = newValue)
			.setTooltip(tooltip("lily_pad_sounds"))
			.build();

		var mushroomBlockSounds = entryBuilder.startBooleanToggle(text("mushroom_block_sounds"), blockSounds.mushroomBlockSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.MUSHROOM_BLOCK_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.mushroomBlockSounds = newValue)
			.setTooltip(tooltip("mushroom_block_sounds"))
			.build();

		var podzolSounds = entryBuilder.startBooleanToggle(text("podzol_sounds"), blockSounds.podzolSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.PODZOL_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.podzolSounds = newValue)
			.setTooltip(tooltip("podzol_sounds"))
			.build();

		var reinforcedDeepslateSounds = entryBuilder.startBooleanToggle(text("reinforced_deepslate_sounds"), blockSounds.reinforcedDeepslateSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.REINFORCED_DEEPSLATE_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.reinforcedDeepslateSounds = newValue)
			.setTooltip(tooltip("reinforced_deepslate_sounds"))
			.build();

		var sandstoneSounds = entryBuilder.startBooleanToggle(text("sandstone_sounds"), blockSounds.sandstoneSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.SANDSTONE_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.sandstoneSounds = newValue)
			.setTooltip(tooltip("sandstone_sounds"))
			.build();

		var sugarCaneSounds = entryBuilder.startBooleanToggle(text("sugar_cane_sounds"), blockSounds.sugarCaneSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.SUGAR_CANE_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.sugarCaneSounds = newValue)
			.setTooltip(tooltip("sugar_cane_sounds"))
			.build();

		var witherRoseSounds = entryBuilder.startBooleanToggle(text("wither_rose_sounds"), blockSounds.witherRoseSounds)
			.setDefaultValue(DefaultBlockConfig.BlockSoundsConfig.WITHER_ROSE_SOUNDS)
			.setSaveConsumer(newValue -> blockSounds.witherRoseSounds = newValue)
			.setTooltip(tooltip("wither_rose_sounds"))
			.build();

		var blockSoundsCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("block_sounds"),
			false,
			tooltip("block_sounds"),
			cactusSounds, claySounds, coarseDirtSounds, cobwebSounds, deadBushSounds,
			flowerSounds, saplingSounds, gravelSounds, iceSounds, frostedIceSounds, leafSounds,
			lilyPadSounds, mushroomBlockSounds, podzolSounds, reinforcedDeepslateSounds,
			sugarCaneSounds, witherRoseSounds, sandstoneSounds
		);

		var mesogleaLiquid = entryBuilder.startBooleanToggle(text("mesoglea_liquid"), config.mesoglea.mesogleaLiquid)
			.setDefaultValue(DefaultBlockConfig.MesogleaConfig.MESOGLEA_LIQUID)
			.setSaveConsumer(newValue -> config.mesoglea.mesogleaLiquid = newValue)
			.setTooltip(tooltip("mesoglea_liquid"))
			.build();

		var mesogleaBubbleColumns = entryBuilder.startBooleanToggle(text("mesoglea_bubble_columns"), config.mesoglea.mesogleaBubbleColumns)
			.setDefaultValue(DefaultBlockConfig.MesogleaConfig.MESOGLEA_BUBBLE_COLUMNS)
			.setSaveConsumer(newValue -> config.mesoglea.mesogleaBubbleColumns = newValue)
			.setTooltip(tooltip("mesoglea_bubble_columns"))
			.build();

		var mesogleaCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("mesoglea"),
			false,
			tooltip("mesoglea"),
			mesogleaBubbleColumns, mesogleaLiquid
		);

		var termitesOnlyEatNaturalBlocks = entryBuilder.startBooleanToggle(text("termites_only_eat_natural_blocks"), termite.onlyEatNaturalBlocks)
			.setDefaultValue(DefaultBlockConfig.TermiteConfig.ONLY_EAT_NATURAL_BLOCKS)
			.setSaveConsumer(newValue -> termite.onlyEatNaturalBlocks = newValue)
			.setTooltip(tooltip("termites_only_eat_natural_blocks"))
			.build();

		var maxTermiteDistance = entryBuilder.startIntSlider(text("max_termite_distance"), termite.maxDistance, 1, 72)
			.setDefaultValue(DefaultBlockConfig.TermiteConfig.MAX_DISTANCE)
			.setSaveConsumer(newValue -> termite.maxDistance = newValue)
			.setTooltip(tooltip("max_termite_distance"))
			.build();

		var maxNaturalTermiteDistance = entryBuilder.startIntSlider(text("max_natural_termite_distance"), termite.maxNaturalDistance, 1, 72)
			.setDefaultValue(DefaultBlockConfig.TermiteConfig.MAX_NATURAL_DISTANCE)
			.setSaveConsumer(newValue -> termite.maxNaturalDistance = newValue)
			.setTooltip(tooltip("max_natural_termite_distance"))
			.build();

		var termiteCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("termite"),
			false,
			tooltip("termite"),
			termitesOnlyEatNaturalBlocks, maxTermiteDistance, maxNaturalTermiteDistance
		);

		var stoneChestTimer = entryBuilder.startIntSlider(text("stone_chest_timer"), stoneChest.stoneChestTimer, 50, 200)
			.setDefaultValue(DefaultBlockConfig.StoneChestConfig.STONE_CHEST_TIMER)
			.setSaveConsumer(newValue -> stoneChest.stoneChestTimer = newValue)
			.setTooltip(tooltip("stone_chest_timer"))
			.build();

		var stoneChestCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("stone_chest"),
			false,
			tooltip("stone_chest"),
			stoneChestTimer
		);
	}


}
