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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

public final class RegisterSounds {
	public static final Holder.Reference<SoundEvent> AMBIENT_DEEP_DARK_ADDITIONS = registerForHolder("ambient.deep_dark.additions");

	//AMBIENT
	public static final Holder.Reference<SoundEvent> AMBIENT_DEEP_DARK_LOOP = registerForHolder("ambient.deep_dark.loop");
	public static final Holder.Reference<SoundEvent> AMBIENT_DRIPSTONE_CAVES_ADDITIONS = registerForHolder("ambient.dripstone_caves.additions");
	public static final Holder.Reference<SoundEvent> AMBIENT_DRIPSTONE_CAVES_LOOP = registerForHolder("ambient.dripstone_caves.loop");
	public static final Holder.Reference<SoundEvent> AMBIENT_GENERIC_CAVES_LOOP = registerForHolder("ambient.generic_caves.loop");
	public static final Holder.Reference<SoundEvent> AMBIENT_JELLYFISH_CAVES_ADDITIONS = registerForHolder("ambient.jellyfish_caves.additions");
	public static final Holder.Reference<SoundEvent> AMBIENT_JELLYFISH_CAVES_LOOP = registerForHolder("ambient.jellyfish_caves.loop");
	public static final Holder.Reference<SoundEvent> AMBIENT_LUSH_CAVES_ADDITIONS = registerForHolder("ambient.lush_caves.additions");
	public static final Holder.Reference<SoundEvent> AMBIENT_LUSH_CAVES_LOOP = registerForHolder("ambient.lush_caves.loop");
	public static final Holder.Reference<SoundEvent> AMBIENT_MAGMATIC_CAVES_ADDITIONS = registerForHolder("ambient.magmatic_caves.additions");
	public static final Holder.Reference<SoundEvent> AMBIENT_MAGMATIC_CAVES_LOOP = registerForHolder("ambient.magmatic_caves.loop");
	public static final Holder.Reference<SoundEvent> AMBIENT_FROZEN_CAVES_ADDITIONS = registerForHolder("ambient.frozen_caves.additions");
	public static final Holder.Reference<SoundEvent> AMBIENT_FROZEN_CAVES_LOOP = registerForHolder("ambient.frozen_caves.loop");
	public static final SoundEvent BLOCK_ALGAE_PLACE = register("block.algae.place");

	//BLOCK
	public static final SoundEvent BLOCK_ALGAE_HIT = register("block.algae.hit");
	public static final SoundEvent BLOCK_ALGAE_BREAK = register("block.algae.break");
	public static final SoundEvent BLOCK_ALGAE_STEP = register("block.algae.step");
	public static final SoundEvent BLOCK_ALGAE_FALL = register("block.algae.fall");
	public static final SoundEvent BLOCK_BAOBAB_NUT_PLACE = register("block.baobab_nut.place");
	public static final SoundEvent BLOCK_BAOBAB_NUT_HIT = register("block.baobab_nut.hit");
	public static final SoundEvent BLOCK_BAOBAB_NUT_BREAK = register("block.baobab_nut.break");
	public static final SoundEvent BLOCK_BAOBAB_NUT_STEP = register("block.baobab_nut.step");
	public static final SoundEvent BLOCK_BAOBAB_NUT_FALL = register("block.baobab_nut.fall");
	public static final SoundEvent BLOCK_CACTUS_PLACE = register("block.cactus.place");
	public static final SoundEvent BLOCK_CACTUS_HIT = register("block.cactus.hit");
	public static final SoundEvent BLOCK_CACTUS_BREAK = register("block.cactus.break");
	public static final SoundEvent BLOCK_CACTUS_STEP = register("block.cactus.step");
	public static final SoundEvent BLOCK_CACTUS_FALL = register("block.cactus.fall");
	public static final SoundEvent BLOCK_CHEST_CLOSE_UNDERWATER = register("block.chest.close_underwater");
	public static final SoundEvent BLOCK_CHEST_OPEN_UNDERWATER = register("block.chest.open_underwater");
	public static final SoundEvent BLOCK_CLAY_PLACE = register("block.clay.place");
	public static final SoundEvent BLOCK_CLAY_HIT = register("block.clay.hit");
	public static final SoundEvent BLOCK_CLAY_BREAK = register("block.clay.break");
	public static final SoundEvent BLOCK_CLAY_STEP = register("block.clay.step");
	public static final SoundEvent BLOCK_CLAY_FALL = register("block.clay.fall");
	public static final SoundEvent BLOCK_COARSE_DIRT_PLACE = register("block.coarse_dirt.place");
	public static final SoundEvent BLOCK_COARSE_DIRT_HIT = register("block.coarse_dirt.hit");
	public static final SoundEvent BLOCK_COARSE_DIRT_BREAK = register("block.coarse_dirt.break");
	public static final SoundEvent BLOCK_COARSE_DIRT_STEP = register("block.coarse_dirt.step");
	public static final SoundEvent BLOCK_COARSE_DIRT_FALL = register("block.coarse_dirt.fall");
	public static final SoundEvent BLOCK_COCONUT_PLACE = register("block.coconut.place");
	public static final SoundEvent BLOCK_COCONUT_HIT = register("block.coconut.hit");
	public static final SoundEvent BLOCK_COCONUT_BREAK = register("block.coconut.break");
	public static final SoundEvent BLOCK_COCONUT_STEP = register("block.coconut.step");
	public static final SoundEvent BLOCK_COCONUT_FALL = register("block.coconut.fall");
	public static final SoundEvent BLOCK_DISPLAY_LANTERN_NECTAR_LOOP = register("block.display_lantern.nectar_inside_loop");
	public static final SoundEvent BLOCK_ECHO_GLASS_PLACE = register("block.echo_glass.place");
	public static final SoundEvent BLOCK_ECHO_GLASS_BREAK = register("block.echo_glass.break");
	public static final SoundEvent BLOCK_ECHO_GLASS_STEP = register("block.echo_glass.step");
	public static final SoundEvent BLOCK_ECHO_GLASS_FALL = register("block.echo_glass.fall");
	public static final SoundEvent BLOCK_ECHO_GLASS_CRACK = register("block.echo_glass.crack");
	public static final SoundEvent BLOCK_ECHO_GLASS_REPAIR = register("block.echo_glass.repair");
	public static final SoundEvent BLOCK_FLOWER_PLACE = register("block.flower.place");
	public static final SoundEvent BLOCK_FLOWER_HIT = register("block.flower.hit");
	public static final SoundEvent BLOCK_FLOWER_BREAK = register("block.flower.break");
	public static final SoundEvent BLOCK_FLOWER_STEP = register("block.flower.step");
	public static final SoundEvent BLOCK_FLOWER_FALL = register("block.flower.fall");
	public static final SoundEvent BLOCK_GRAVEL_PLACE = register("block.gravel.place");
	public static final SoundEvent BLOCK_GRAVEL_HIT = register("block.gravel.hit");
	public static final SoundEvent BLOCK_GRAVEL_BREAK = register("block.gravel.break");
	public static final SoundEvent BLOCK_GRAVEL_STEP = register("block.gravel.step");
	public static final SoundEvent BLOCK_GRAVEL_FALL = register("block.gravel.fall");
	public static final SoundEvent BLOCK_HANGING_TENDRIL_PLACE = register("block.hanging_tendril.place");
	public static final SoundEvent BLOCK_HANGING_TENDRIL_HIT = register("block.hanging_tendril.hit");
	public static final SoundEvent BLOCK_HANGING_TENDRIL_BREAK = register("block.hanging_tendril.break");
	public static final SoundEvent BLOCK_HANGING_TENDRIL_STEP = register("block.hanging_tendril.step");
	public static final SoundEvent BLOCK_HANGING_TENDRIL_FALL = register("block.hanging_tendril.fall");
	public static final SoundEvent BLOCK_HANGING_TENDRIL_WRING = register("block.hanging_tendril.wring");
	public static final SoundEvent BLOCK_HANGING_TENDRIL_CLICKING = register("block.hanging_tendril.clicking");
	public static final SoundEvent BLOCK_HANGING_TENDRIL_CLICKING_STOP = register("block.hanging_tendril.clicking_stop");
	public static final SoundEvent BLOCK_HOLLOWED_LOG_PLACE = register("block.hollowed_log.place");
	public static final SoundEvent BLOCK_HOLLOWED_LOG_HIT = register("block.hollowed_log.hit");
	public static final SoundEvent BLOCK_HOLLOWED_LOG_BREAK = register("block.hollowed_log.break");
	public static final SoundEvent BLOCK_HOLLOWED_LOG_STEP = register("block.hollowed_log.step");
	public static final SoundEvent BLOCK_HOLLOWED_LOG_FALL = register("block.hollowed_log.fall");
	public static final SoundEvent BLOCK_HOLLOWED_CHERRY_LOG_PLACE = register("block.hollowed_cherry_log.place");
	public static final SoundEvent BLOCK_HOLLOWED_CHERRY_LOG_HIT = register("block.hollowed_cherry_log.hit");
	public static final SoundEvent BLOCK_HOLLOWED_CHERRY_LOG_BREAK = register("block.hollowed_cherry_log.break");
	public static final SoundEvent BLOCK_HOLLOWED_CHERRY_LOG_STEP = register("block.hollowed_cherry_log.step");
	public static final SoundEvent BLOCK_HOLLOWED_CHERRY_LOG_FALL = register("block.hollowed_cherry_log.fall");
	public static final SoundEvent BLOCK_HOLLOWED_STEM_PLACE = register("block.hollowed_stem.place");
	public static final SoundEvent BLOCK_HOLLOWED_STEM_HIT = register("block.hollowed_stem.hit");
	public static final SoundEvent BLOCK_HOLLOWED_STEM_BREAK = register("block.hollowed_stem.break");
	public static final SoundEvent BLOCK_HOLLOWED_STEM_STEP = register("block.hollowed_stem.step");
	public static final SoundEvent BLOCK_HOLLOWED_STEM_FALL = register("block.hollowed_stem.fall");
	public static final SoundEvent LOG_HOLLOWED = register("block.log_hollowed");
	public static final SoundEvent STEM_HOLLOWED = register("block.stem_hollowed");
	public static final SoundEvent BLOCK_ICE_PLACE = register("block.ice.place");
	public static final SoundEvent BLOCK_ICE_HIT = register("block.ice.hit");
	public static final SoundEvent BLOCK_ICE_BREAK = register("block.ice.break");
	public static final SoundEvent BLOCK_ICE_STEP = register("block.ice.step");
	public static final SoundEvent BLOCK_ICE_FALL = register("block.ice.fall");
	public static final SoundEvent BLOCK_FROSTED_ICE_PLACE = register("block.frosted_ice.place");
	public static final SoundEvent BLOCK_FROSTED_ICE_HIT = register("block.frosted_ice.hit");
	public static final SoundEvent BLOCK_FROSTED_ICE_BREAK = register("block.frosted_ice.break");
	public static final SoundEvent BLOCK_FROSTED_ICE_STEP = register("block.frosted_ice.step");
	public static final SoundEvent BLOCK_FROSTED_ICE_FALL = register("block.frosted_ice.fall");
	public static final SoundEvent BLOCK_LEAVES_PLACE = register("block.leaves.place");
	public static final SoundEvent BLOCK_LEAVES_HIT = register("block.leaves.hit");
	public static final SoundEvent BLOCK_LEAVES_BREAK = register("block.leaves.break");
	public static final SoundEvent BLOCK_LEAVES_STEP = register("block.leaves.step");
	public static final SoundEvent BLOCK_LEAVES_FALL = register("block.leaves.fall");
	public static final SoundEvent BLOCK_MESOGLEA_PLACE = register("block.mesoglea.place");
	public static final SoundEvent BLOCK_MESOGLEA_HIT = register("block.mesoglea.hit");
	public static final SoundEvent BLOCK_MESOGLEA_BREAK = register("block.mesoglea.break");
	public static final SoundEvent BLOCK_MESOGLEA_STEP = register("block.mesoglea.step");
	public static final SoundEvent BLOCK_MESOGLEA_FALL = register("block.mesoglea.fall");
	public static final SoundEvent BLOCK_MUSHROOM_PLACE = register("block.mushroom.place");
	public static final SoundEvent BLOCK_MUSHROOM_HIT = register("block.mushroom.hit");
	public static final SoundEvent BLOCK_MUSHROOM_BREAK = register("block.mushroom.break");
	public static final SoundEvent BLOCK_MUSHROOM_STEP = register("block.mushroom.step");
	public static final SoundEvent BLOCK_MUSHROOM_FALL = register("block.mushroom.fall");
	public static final SoundEvent BLOCK_MUSHROOM_BLOCK_PLACE = register("block.mushroom_block.place");
	public static final SoundEvent BLOCK_MUSHROOM_BLOCK_HIT = register("block.mushroom_block.hit");
	public static final SoundEvent BLOCK_MUSHROOM_BLOCK_BREAK = register("block.mushroom_block.break");
	public static final SoundEvent BLOCK_MUSHROOM_BLOCK_STEP = register("block.mushroom_block.step");
	public static final SoundEvent BLOCK_MUSHROOM_BLOCK_FALL = register("block.mushroom_block.fall");
	public static final SoundEvent BLOCK_MILKWEED_RUSTLE = register("block.milkweed.rustle");
	public static final SoundEvent BLOCK_NEMATOCYST_PLACE = register("block.nematocyst.place");
	public static final SoundEvent BLOCK_NEMATOCYST_HIT = register("block.nematocyst.hit");
	public static final SoundEvent BLOCK_NEMATOCYST_BREAK = register("block.nematocyst.break");
	public static final SoundEvent BLOCK_NEMATOCYST_STEP = register("block.nematocyst.step");
	public static final SoundEvent BLOCK_NEMATOCYST_FALL = register("block.nematocyst.fall");
	public static final SoundEvent BLOCK_NULL_BLOCK_PLACE = register("block.null_block.place");
	public static final SoundEvent BLOCK_NULL_BLOCK_HIT = register("block.null_block.hit");
	public static final SoundEvent BLOCK_NULL_BLOCK_BREAK = register("block.null_block.break");
	public static final SoundEvent BLOCK_NULL_BLOCK_STEP = register("block.null_block.step");
	public static final SoundEvent BLOCK_NULL_BLOCK_FALL = register("block.null_block.fall");
	public static final SoundEvent BLOCK_OSSEOUS_SCULK_PLACE = register("block.osseous_sculk.place");
	public static final SoundEvent BLOCK_OSSEOUS_SCULK_HIT = register("block.osseous_sculk.hit");
	public static final SoundEvent BLOCK_OSSEOUS_SCULK_BREAK = register("block.osseous_sculk.break");
	public static final SoundEvent BLOCK_OSSEOUS_SCULK_STEP = register("block.osseous_sculk.step");
	public static final SoundEvent BLOCK_OSSEOUS_SCULK_FALL = register("block.osseous_sculk.fall");
	public static final SoundEvent BLOCK_PALM_CROWN_PLACE = register("block.palm_crown.place");
	public static final SoundEvent BLOCK_PALM_CROWN_HIT = register("block.palm_crown.hit");
	public static final SoundEvent BLOCK_PALM_CROWN_BREAK = register("block.palm_crown.break");
	public static final SoundEvent BLOCK_PALM_CROWN_STEP = register("block.palm_crown.step");
	public static final SoundEvent BLOCK_PALM_CROWN_FALL = register("block.palm_crown.fall");
	public static final SoundEvent BLOCK_POLLEN_PLACE = register("block.pollen.place");
	public static final SoundEvent BLOCK_POLLEN_HIT = register("block.pollen.hit");
	public static final SoundEvent BLOCK_POLLEN_BREAK = register("block.pollen.break");
	public static final SoundEvent BLOCK_POLLEN_STEP = register("block.pollen.step");
	public static final SoundEvent BLOCK_POLLEN_FALL = register("block.pollen.fall");
	public static final SoundEvent BLOCK_REINFORCED_DEEPSLATE_PLACE = register("block.reinforced_deepslate.place");
	public static final SoundEvent BLOCK_REINFORCED_DEEPSLATE_HIT = register("block.reinforced_deepslate.hit");
	public static final SoundEvent BLOCK_REINFORCED_DEEPSLATE_BREAK = register("block.reinforced_deepslate.break");
	public static final SoundEvent BLOCK_REINFORCED_DEEPSLATE_STEP = register("block.reinforced_deepslate.step");
	public static final SoundEvent BLOCK_REINFORCED_DEEPSLATE_FALL = register("block.reinforced_deepslate.fall");
	public static final SoundEvent BLOCK_SOUL_FIRE_AMBIENT = register("block.soul_fire.ambient");
	public static final SoundEvent BLOCK_SAPLING_PLACE = register("block.sapling.place");
	public static final SoundEvent BLOCK_SAPLING_HIT = register("block.sapling.hit");
	public static final SoundEvent BLOCK_SAPLING_BREAK = register("block.sapling.break");
	public static final SoundEvent BLOCK_SAPLING_STEP = register("block.sapling.step");
	public static final SoundEvent BLOCK_SAPLING_FALL = register("block.sapling.fall");
	public static final SoundEvent BLOCK_SANDSTONE_PLACE = register("block.sandstone.place");
	public static final SoundEvent BLOCK_SANDSTONE_HIT = register("block.sandstone.hit");
	public static final SoundEvent BLOCK_SANDSTONE_BREAK = register("block.sandstone.break");
	public static final SoundEvent BLOCK_SANDSTONE_STEP = register("block.sandstone.step");
	public static final SoundEvent BLOCK_SANDSTONE_FALL = register("block.sandstone.fall");
	public static final SoundEvent BLOCK_SCORCHED_SAND_PLACE = register("block.scorched_sand.place");
	public static final SoundEvent BLOCK_SCORCHED_SAND_HIT = register("block.scorched_sand.hit");
	public static final SoundEvent BLOCK_SCORCHED_SAND_BREAK = register("block.scorched_sand.break");
	public static final SoundEvent BLOCK_SCORCHED_SAND_STEP = register("block.scorched_sand.step");
	public static final SoundEvent BLOCK_SCORCHED_SAND_FALL = register("block.scorched_sand.fall");
	public static final SoundEvent BLOCK_SCULK_SENSOR_HICCUP = register("block.sculk_sensor.hiccup");
	public static final SoundEvent BLOCK_SCULK_SHRIEKER_GARGLE = register("block.sculk_shrieker.gargle");
	public static final SoundEvent BLOCK_STONE_CHEST_CLOSE_START = register("block.stone_chest.close_start");
	public static final SoundEvent BLOCK_STONE_CHEST_CLOSE_START_UNDERWATER = register("block.stone_chest.close_start_underwater");
	public static final SoundEvent BLOCK_STONE_CHEST_SLAM = register("block.stone_chest.slam");
	public static final SoundEvent BLOCK_STONE_CHEST_SLAM_UNDERWATER = register("block.stone_chest.slam_underwater");
	public static final SoundEvent BLOCK_STONE_CHEST_OPEN = register("block.stone_chest.open");
	public static final SoundEvent BLOCK_STONE_CHEST_OPEN_UNDERWATER = register("block.stone_chest.open_underwater");
	public static final SoundEvent BLOCK_STONE_CHEST_LIFT = register("block.stone_chest.lift");
	public static final SoundEvent BLOCK_STONE_CHEST_LIFT_UNDERWATER = register("block.stone_chest.lift_underwater");
	public static final SoundEvent BLOCK_STONE_CHEST_ITEM_CRUMBLE = register("block.stone_chest.item_crumble");
	public static final SoundEvent BLOCK_SUGAR_CANE_PLACE = register("block.sugar_cane.place");
	public static final SoundEvent BLOCK_SUGAR_CANE_HIT = register("block.sugar_cane.hit");
	public static final SoundEvent BLOCK_SUGAR_CANE_BREAK = register("block.sugar_cane.break");
	public static final SoundEvent BLOCK_SUGAR_CANE_STEP = register("block.sugar_cane.step");
	public static final SoundEvent BLOCK_SUGAR_CANE_FALL = register("block.sugar_cane.fall");
	public static final SoundEvent BLOCK_TERMITE_MOUND_TERMITE_IDLE = register("block.termite_mound.termite_idle");
	public static final SoundEvent BLOCK_TERMITE_MOUND_TERMITE_GNAW = register("block.termite_mound.termite_gnaw");
	public static final SoundEvent BLOCK_TERMITE_MOUND_TERMITE_GNAW_FINISH = register("block.termite_mound.termite_gnaw_finish");
	public static final SoundEvent BLOCK_TERMITE_MOUND_ENTER = register("block.termite_mound.enter");
	public static final SoundEvent BLOCK_TERMITE_MOUND_EXIT = register("block.termite_mound.exit");
	public static final SoundEvent BLOCK_TERMITE_MOUND_PLACE = register("block.termite_mound.place");
	public static final SoundEvent BLOCK_TERMITE_MOUND_HIT = register("block.termite_mound.hit");
	public static final SoundEvent BLOCK_TERMITE_MOUND_BREAK = register("block.termite_mound.break");
	public static final SoundEvent BLOCK_TERMITE_MOUND_STEP = register("block.termite_mound.step");
	public static final SoundEvent BLOCK_TERMITE_MOUND_FALL = register("block.termite_mound.fall");
	public static final SoundEvent BLOCK_TUMBLEWEED_PLANT_PLACE = register("block.tumbleweed_plant.place");
	public static final SoundEvent BLOCK_TUMBLEWEED_PLANT_HIT = register("block.tumbleweed_plant.hit");
	public static final SoundEvent BLOCK_TUMBLEWEED_PLANT_BREAK = register("block.tumbleweed_plant.break");
	public static final SoundEvent BLOCK_TUMBLEWEED_PLANT_STEP = register("block.tumbleweed_plant.step");
	public static final SoundEvent BLOCK_TUMBLEWEED_PLANT_FALL = register("block.tumbleweed_plant.fall");
	public static final SoundEvent BLOCK_TUMBLEWEED_SHEAR = register("block.tumbleweed_plant.shear");
	public static final SoundEvent BLOCK_PRICKLY_PEAR_PICK = register("block.prickly_pear.pick");
	public static final SoundEvent BLOCK_OSTRICH_EGG_CRACK = register("block.ostrich_egg.crack");
	public static final SoundEvent BLOCK_OSTRICH_EGG_HATCH = register("block.ostrich_egg.hatch");
	public static final SoundEvent BLOCK_GEYSER_PLACE = register("block.geyser.place");
	public static final SoundEvent BLOCK_GEYSER_HIT = register("block.geyser.hit");
	public static final SoundEvent BLOCK_GEYSER_BREAK = register("block.geyser.break");
	public static final SoundEvent BLOCK_GEYSER_STEP = register("block.geyser.step");
	public static final SoundEvent BLOCK_GEYSER_FALL = register("block.geyser.fall");
	public static final SoundEvent BLOCK_GEYSER_BOIL = register("block.geyser.boil");
	public static final SoundEvent BLOCK_GEYSER_ERUPT_AIR = register("block.geyser.erupt.air");
	public static final SoundEvent BLOCK_GEYSER_ERUPT_WATER = register("block.geyser.erupt.water");
	public static final SoundEvent BLOCK_GEYSER_ERUPT_LAVA = register("block.geyser.erupt.lava");
	public static final SoundEvent BLOCK_MAGMA_PLACE = register("block.magma.place");
	public static final SoundEvent BLOCK_MAGMA_HIT = register("block.magma.hit");
	public static final SoundEvent BLOCK_MAGMA_BREAK = register("block.magma.break");
	public static final SoundEvent BLOCK_MAGMA_STEP = register("block.magma.step");
	public static final SoundEvent BLOCK_MAGMA_FALL = register("block.magma.fall");

	//ENTITY
	public static final SoundEvent ENTITY_ANCIENT_HORN_VIBRATION_BLAST = register("entity.ancient_horn_vibration.blast");
	public static final SoundEvent ENTITY_ANCIENT_HORN_VIBRATION_LOOP = register("entity.ancient_horn_vibration.loop");
	public static final SoundEvent ENTITY_ANCIENT_HORN_VIBRATION_FLYBY = register("entity.ancient_horn_vibration.flyby");
	public static final SoundEvent ENTITY_ANCIENT_HORN_VIBRATION_DISSIPATE = register("entity.ancient_horn_vibration.dissipate");
	public static final SoundEvent ENTITY_FIREFLY_HIDE = register("entity.firefly.hide");
	public static final SoundEvent ENTITY_FIREFLY_HURT = register("entity.firefly.hurt");
	public static final SoundEvent ENTITY_FIREFLY_NECTAR = register("entity.firefly.nectar");
	public static final SoundEvent ENTITY_ENDERMAN_ANGER_LOOP = register("entity.enderman.anger_loop");
	public static final SoundEvent ENTITY_JELLYFISH_STING = register("entity.jellyfish.sting");
	public static final SoundEvent ENTITY_JELLYFISH_AMBIENT_WATER = register("entity.jellyfish.ambient_water");
	public static final SoundEvent ENTITY_JELLYFISH_HURT_WATER = register("entity.jellyfish.hurt_water");
	public static final SoundEvent ENTITY_JELLYFISH_HURT = register("entity.jellyfish.hurt");
	public static final SoundEvent ENTITY_JELLYFISH_SWIM = register("entity.jellyfish.swim");
	public static final SoundEvent ENTITY_JELLYFISH_HIDE = register("entity.jellyfish.hide");
	public static final SoundEvent ENTITY_JELLYFISH_DEATH_WATER = register("entity.jellyfish.death_water");
	public static final SoundEvent ENTITY_JELLYFISH_DEATH = register("entity.jellyfish.death");
	public static final SoundEvent ENTITY_CRAB_IDLE = register("entity.crab.idle");
	public static final SoundEvent ENTITY_CRAB_ATTACK = register("entity.crab.attack");
	public static final SoundEvent ENTITY_CRAB_HURT = register("entity.crab.hurt");
	public static final SoundEvent ENTITY_CRAB_DEATH = register("entity.crab.death");
	public static final SoundEvent ENTITY_CRAB_DIG = register("entity.crab.dig");
	public static final SoundEvent ENTITY_CRAB_EMERGE = register("entity.crab.emerge");
	public static final SoundEvent ENTITY_OSTRICH_IDLE = register("entity.ostrich.idle");
	public static final SoundEvent ENTITY_OSTRICH_GRUNT = register("entity.ostrich.grunt");
	public static final SoundEvent ENTITY_OSTRICH_HISS = register("entity.ostrich.hiss");
	public static final SoundEvent ENTITY_OSTRICH_HURT = register("entity.ostrich.hurt");
	public static final SoundEvent ENTITY_OSTRICH_DEATH = register("entity.ostrich.death");
	public static final SoundEvent ENTITY_OSTRICH_STEP = register("entity.ostrich.step");
	public static final SoundEvent ENTITY_OSTRICH_SADDLE = register("entity.ostrich.saddle");
	public static final SoundEvent ENTITY_OSTRICH_BEAK_STUCK = register("entity.ostrich.beak.stuck");
	public static final SoundEvent ENTITY_OSTRICH_SWING = register("entity.ostrich.beak.swing");
	public static final SoundEvent ENTITY_OSTRICH_EAT = register("entity.ostrich.eat");
	public static final SoundEvent ENTITY_OSTRICH_INBRED_IDLE_BOCK = register("entity.ostrich.inbred.idle.bock");
	public static final SoundEvent ENTITY_OSTRICH_INBRED_IDLE_AH = register("entity.ostrich.inbred.idle.ah");
	public static final SoundEvent ENTITY_OSTRICH_INBRED_HURT = register("entity.ostrich.inbred.hurt");
	public static final SoundEvent ENTITY_OSTRICH_INBRED_DEATH = register("entity.ostrich.inbred.death");
	public static final SoundEvent ENTITY_OSTRICH_INBRED_STEP = register("entity.ostrich.inbred.step");
	public static final SoundEvent ENTITY_OSTRICH_INBRED_BEAK_STUCK = register("entity.ostrich.inbred.beak.stuck");
	public static final SoundEvent ENTITY_OSTRICH_INBRED_SWING = register("entity.ostrich.inbred.beak.swing");
	public static final SoundEvent ENTITY_SCORCHED_AMBIENT = register("entity.scorched.ambient");
	public static final SoundEvent ENTITY_SCORCHED_HURT = register("entity.scorched.hurt");
	public static final SoundEvent ENTITY_SCORCHED_DEATH = register("entity.scorched.death");
	public static final SoundEvent ENTITY_SCORCHED_STEP = register("entity.scorched.step");
	public static final SoundEvent ENTITY_SCORCHED_STEP_LAVA = register("entity.scorched.step_lava");
	public static final SoundEvent ENTITY_TUMBLEWEED_BOUNCE = register("entity.tumbleweed.bounce");
	public static final SoundEvent ENTITY_TUMBLEWEED_BREAK = register("entity.tumbleweed.break");
	public static final SoundEvent ENTITY_TUMBLEWEED_DAMAGE = register("entity.tumbleweed.damage");
	public static final SoundEvent ENTITY_WARDEN_KIRBY_DEATH = register("entity.warden.kirby_death");
	public static final SoundEvent ENTITY_WARDEN_DYING = register("entity.warden.dying");
	public static final SoundEvent ENTITY_WARDEN_UNDERWATER_DYING = register("entity.warden.dying_underwater");
	public static final SoundEvent ENTITY_WARDEN_SWIM = register("entity.warden.swim");
	public static final SoundEvent ENTITY_WARDEN_BRAP = register("entity.warden.brap");
	public static final SoundEvent ENTITY_WARDEN_STELLA_HEARTBEAT = register("entity.warden.stella_heartbeat");
	public static final SoundEvent ENTITY_FROG_SUS_DEATH = register("entity.frog.sus_death");
	public static final Holder.Reference<SoundEvent> ITEM_ANCIENT_HORN_CALL = registerForHolder("item.ancient_horn.call");

	//ITEM
	public static final SoundEvent ITEM_BOTTLE_CATCH_FIREFLY = register("item.bottle.catch_firefly");
	public static final SoundEvent ITEM_BOTTLE_RELEASE_FIREFLY = register("item.bottle.release_firefly");
	public static final SoundEvent ITEM_BOTTLE_PUT_IN_LANTERN_FIREFLY = register("item.bottle.put_in_lantern_firefly");
	public static final SoundEvent ITEM_BUCKET_FILL_JELLYFISH = register("item.bucket.fill_jellyfish");
	public static final SoundEvent ITEM_BUCKET_EMPTY_JELLYFISH = register("item.bucket.empty_jellyfish");
	public static final SoundEvent ITEM_BUCKET_FILL_CRAB = register("item.bucket.fill_crab");
	public static final SoundEvent ITEM_BUCKET_EMPTY_CRAB = register("item.bucket.empty_crab");
	public static final SoundEvent ITEM_COCONUT_THROW = register("item.coconut.throw");
	public static final SoundEvent ITEM_COCONUT_LAND_BREAK = register("item.coconut.land_break");
	public static final SoundEvent ITEM_COCONUT_LAND = register("item.coconut.land");
	public static final SoundEvent ITEM_COCONUT_HIT_HEAD = register("item.coconut.hit_head");
	public static final Holder.Reference<SoundEvent> ITEM_COPPER_HORN_SAX_LOOP = registerForHolder("item.copper_horn.sax.loop");
	public static final Holder.Reference<SoundEvent> ITEM_COPPER_HORN_TUBA_LOOP = registerForHolder("item.copper_horn.tuba.loop");
	public static final Holder.Reference<SoundEvent> ITEM_COPPER_HORN_FLUTE_LOOP = registerForHolder("item.copper_horn.flute.loop");
	public static final Holder.Reference<SoundEvent> ITEM_COPPER_HORN_OBOE_LOOP = registerForHolder("item.copper_horn.oboe.loop");
	public static final Holder.Reference<SoundEvent> ITEM_COPPER_HORN_CLARINET_LOOP = registerForHolder("item.copper_horn.clarinet.loop");
	public static final Holder.Reference<SoundEvent> ITEM_COPPER_HORN_TRUMPET_LOOP = registerForHolder("item.copper_horn.trumpet.loop");
	public static final Holder.Reference<SoundEvent> ITEM_COPPER_HORN_TROMBONE_LOOP = registerForHolder("item.copper_horn.trombone.loop");
	public static final SoundEvent ITEM_ENDER_PEARL_LAND = register("item.ender_pearl.land");
	public static final SoundEvent ITEM_EGG_LAND = register("item.egg.land");
	public static final SoundEvent ITEM_SNOWBALL_LAND = register("item.snowball.land");
	public static final SoundEvent ITEM_POTION_SPLASH = register("item.potion.splash");
	public static final SoundEvent ITEM_EXPERIENCE_BOTTLE_SPLASH = register("item.experience_bottle.splash");
	public static final SoundEvent ITEM_POTION_MAGIC = register("item.potion.magic");
	public static final SoundEvent ITEM_POTION_LINGERING = register("item.potion.lingering");
	public static final SoundEvent PARTICLE_MESOGLEA_DRIP_LAND = register("particle.mesoglea_drip.land");

	//MISC
	public static final SoundEvent PARTICLE_FLOATING_SCULK_BUBBLE_POP = register("particle.floating_sculk_bubble.pop");
	public static final SoundEvent PARTICLE_FLOATING_SCULK_BUBBLE_BIG_POP = register("particle.floating_sculk_bubble.big_pop");
	public static final SoundEvent PLAYER_HURT_CACTUS = register("entity.player.hurt.cactus");
	public static final Holder.Reference<SoundEvent> MUSIC_OVERWORLD_WILD_FORESTS = registerForHolder("music.overworld.wild_forests");
	public static final Holder.Reference<SoundEvent> MUSIC_OVERWORLD_FROZEN_CAVES = registerForHolder("music.overworld.frozen_caves");
	public static final Holder.Reference<SoundEvent> MUSIC_OVERWORLD_JELLYFISH_CAVES = registerForHolder("music.overworld.jellyfish_caves");
	public static final Holder.Reference<SoundEvent> MUSIC_OVERWORLD_MAGMATIC_CAVES = registerForHolder("music.overworld.magmatic_caves");

	private RegisterSounds() {
		throw new UnsupportedOperationException("RegisterSounds contains only static declarations.");
	}

	@NotNull
	private static Holder.Reference<SoundEvent> registerForHolder(@NotNull String string) {
		return registerForHolder(WilderConstants.id(string));
	}

	@NotNull
	private static Holder.Reference<SoundEvent> registerForHolder(@NotNull ResourceLocation resourceLocation) {
		return registerForHolder(resourceLocation, resourceLocation);
	}

	@NotNull
	public static SoundEvent register(@NotNull String path) {
		var id = WilderConstants.id(path);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, new SoundEvent(id, 16.0F, false));
	}

	@NotNull
	private static Holder.Reference<SoundEvent> registerForHolder(@NotNull ResourceLocation resourceLocation, @NotNull ResourceLocation resourceLocation2) {
		return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, resourceLocation, SoundEvent.createVariableRangeEvent(resourceLocation2));
	}

	public static void init() {
		WilderConstants.logWithModId("Registering SoundEvents for", WilderConstants.UNSTABLE_LOGGING);
	}

}
