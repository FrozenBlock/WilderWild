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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.lib.platform.api.registry.DeferredSoundEvent;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.sounds.BlockSoundCollection;
import java.util.Optional;

public final class WWSounds {
	private static final DeferredRegister.SoundEvents REGISTER = DeferredRegister.createSoundEvents(WWConstants.MOD_ID);

	// AMBIENT
	public static final DeferredSoundEvent AMBIENT_DEEP_DARK_LOOP = register("ambient.deep_dark.loop");
	public static final DeferredSoundEvent AMBIENT_DEEP_DARK_ADDITIONS = register("ambient.deep_dark.additions");
	public static final DeferredSoundEvent AMBIENT_DRIPSTONE_CAVES_ADDITIONS = register("ambient.dripstone_caves.additions");
	public static final DeferredSoundEvent AMBIENT_DRIPSTONE_CAVES_LOOP = register("ambient.dripstone_caves.loop");
	public static final DeferredSoundEvent AMBIENT_MESOGLEA_CAVES_ADDITIONS = register("ambient.mesoglea_caves.additions");
	public static final DeferredSoundEvent AMBIENT_MESOGLEA_CAVES_LOOP = register("ambient.mesoglea_caves.loop");
	public static final DeferredSoundEvent AMBIENT_LUSH_CAVES_ADDITIONS = register("ambient.lush_caves.additions");
	public static final DeferredSoundEvent AMBIENT_LUSH_CAVES_LOOP = register("ambient.lush_caves.loop");
	public static final DeferredSoundEvent AMBIENT_SULFUR_CAVES_ADDITIONS = register("ambient.sulfur_caves.additions");
	public static final DeferredSoundEvent AMBIENT_SULFUR_CAVES_LOOP = register("ambient.sulfur_caves.loop");
	public static final DeferredSoundEvent AMBIENT_MAGMATIC_CAVES_ADDITIONS = register("ambient.magmatic_caves.additions");
	public static final DeferredSoundEvent AMBIENT_MAGMATIC_CAVES_LOOP = register("ambient.magmatic_caves.loop");
	public static final DeferredSoundEvent AMBIENT_ICE_CAVES_ADDITIONS = register("ambient.ice_caves.additions");
	public static final DeferredSoundEvent AMBIENT_ICE_CAVES_LOOP = register("ambient.ice_caves.loop");

	// BLOCK
	public static final DeferredSoundEvent BLOCK_CHEST_CLOSE_UNDERWATER = register("block.chest.close_underwater");
	public static final DeferredSoundEvent BLOCK_CHEST_OPEN_UNDERWATER = register("block.chest.open_underwater");
	public static final DeferredSoundEvent BLOCK_COPPER_CHEST_CLOSE_UNDERWATER = register("block.copper_chest.close_underwater");
	public static final DeferredSoundEvent BLOCK_COPPER_CHEST_OPEN_UNDERWATER = register("block.copper_chest.open_underwater");
	public static final DeferredSoundEvent BLOCK_COPPER_CHEST_OXIDIZED_CLOSE_UNDERWATER = register("block.copper_chest_oxidized.close_underwater");
	public static final DeferredSoundEvent BLOCK_COPPER_CHEST_OXIDIZED_OPEN_UNDERWATER = register("block.copper_chest_oxidized.open_underwater");
	public static final DeferredSoundEvent BLOCK_COPPER_CHEST_WEATHERED_CLOSE_UNDERWATER = register("block.copper_chest_weathered.close_underwater");
	public static final DeferredSoundEvent BLOCK_COPPER_CHEST_WEATHERED_OPEN_UNDERWATER = register("block.copper_chest_weathered.open_underwater");
	public static final DeferredSoundEvent BLOCK_TERMITE_MOUND_HOLLOW = register("block.termite_mound.hollow");
	public static final DeferredSoundEvent BLOCK_ICICLE_LAND = register("block.icicle.land");
	public static final DeferredSoundEvent BLOCK_FALLING_LEAF_LITTER_MAPLE_LAND = register("block.falling_leaf_litter.maple.land");
	public static final DeferredSoundEvent BLOCK_MAPLE_WOOD_DOOR_CLOSE = register("block.maple_wood_door.close");
	public static final DeferredSoundEvent BLOCK_MAPLE_WOOD_DOOR_OPEN = register("block.maple_wood_door.open");
	public static final DeferredSoundEvent BLOCK_MAPLE_WOOD_FENCE_GATE_CLOSE = register("block.maple_wood_fence_gate.close");
	public static final DeferredSoundEvent BLOCK_MAPLE_WOOD_FENCE_GATE_OPEN = register("block.maple_wood_fence_gate.open");
	public static final DeferredSoundEvent BLOCK_MAPLE_BUTTON_CLICK_OFF = register("block.maple_wood_button.click_off");
	public static final DeferredSoundEvent BLOCK_MAPLE_BUTTON_CLICK_ON = register("block.maple_wood_button.click_on");
	public static final DeferredSoundEvent BLOCK_MAPLE_WOOD_PRESSURE_PLATE_CLICK_OFF = register("block.maple_wood_pressure_plate.click_off");
	public static final DeferredSoundEvent BLOCK_MAPLE_WOOD_PRESSURE_PLATE_CLICK_ON = register("block.maple_wood_pressure_plate.click_on");
	public static final DeferredSoundEvent BLOCK_MAPLE_WOOD_TRAPDOOR_CLOSE = register("block.maple_wood_trapdoor.close");
	public static final DeferredSoundEvent BLOCK_MAPLE_WOOD_TRAPDOOR_OPEN = register("block.maple_wood_trapdoor.open");
	public static final DeferredSoundEvent BLOCK_PALE_OAK_WOOD_DOOR_CLOSE = register("block.pale_oak_wood_door.close");
	public static final DeferredSoundEvent BLOCK_PALE_OAK_WOOD_DOOR_OPEN = register("block.pale_oak_wood_door.open");
	public static final DeferredSoundEvent BLOCK_PALE_OAK_WOOD_FENCE_GATE_CLOSE = register("block.pale_oak_wood_fence_gate.close");
	public static final DeferredSoundEvent BLOCK_PALE_OAK_WOOD_FENCE_GATE_OPEN = register("block.pale_oak_wood_fence_gate.open");
	public static final DeferredSoundEvent BLOCK_PALE_OAK_BUTTON_CLICK_OFF = register("block.pale_oak_wood_button.click_off");
	public static final DeferredSoundEvent BLOCK_PALE_OAK_BUTTON_CLICK_ON = register("block.pale_oak_wood_button.click_on");
	public static final DeferredSoundEvent BLOCK_PALE_OAK_WOOD_PRESSURE_PLATE_CLICK_OFF = register("block.pale_oak_wood_pressure_plate.click_off");
	public static final DeferredSoundEvent BLOCK_PALE_OAK_WOOD_PRESSURE_PLATE_CLICK_ON = register("block.pale_oak_wood_pressure_plate.click_on");
	public static final DeferredSoundEvent BLOCK_PALE_OAK_WOOD_TRAPDOOR_CLOSE = register("block.pale_oak_wood_trapdoor.close");
	public static final DeferredSoundEvent BLOCK_PALE_OAK_WOOD_TRAPDOOR_OPEN = register("block.pale_oak_wood_trapdoor.open");
	public static final DeferredSoundEvent BLOCK_MESOGLEA_EVAPORATE = register("block.mesoglea.evaporate");
	public static final DeferredSoundEvent BLOCK_MILKWEED_RUSTLE = register("block.milkweed.rustle");
	public static final DeferredSoundEvent BLOCK_SOUL_FIRE_AMBIENT = register("block.soul_fire.ambient");
	public static final DeferredSoundEvent BLOCK_ECHO_GLASS_CRACK = register("block.echo_glass.crack");
	public static final DeferredSoundEvent BLOCK_ECHO_GLASS_REPAIR = register("block.echo_glass.repair");
	public static final DeferredSoundEvent BLOCK_HANGING_TENDRIL_CLICKING = register("block.hanging_tendril.clicking");
	public static final DeferredSoundEvent BLOCK_HANGING_TENDRIL_CLICKING_STOP = register("block.hanging_tendril.clicking_stop");
	public static final DeferredSoundEvent BLOCK_HANGING_TENDRIL_WRING = register("block.hanging_tendril.wring");
	public static final DeferredSoundEvent BLOCK_SCULK_SHRIEKER_GARGLE = register("block.sculk_shrieker.gargle");
	public static final DeferredSoundEvent BLOCK_STONE_CHEST_CLOSE_START = register("block.stone_chest.close_start");
	public static final DeferredSoundEvent BLOCK_STONE_CHEST_CLOSE_START_UNDERWATER = register("block.stone_chest.close_start_underwater");
	public static final DeferredSoundEvent BLOCK_STONE_CHEST_SLAM = register("block.stone_chest.slam");
	public static final DeferredSoundEvent BLOCK_STONE_CHEST_SLAM_UNDERWATER = register("block.stone_chest.slam_underwater");
	public static final DeferredSoundEvent BLOCK_STONE_CHEST_OPEN = register("block.stone_chest.open");
	public static final DeferredSoundEvent BLOCK_STONE_CHEST_OPEN_UNDERWATER = register("block.stone_chest.open_underwater");
	public static final DeferredSoundEvent BLOCK_STONE_CHEST_LIFT = register("block.stone_chest.lift");
	public static final DeferredSoundEvent BLOCK_STONE_CHEST_LIFT_UNDERWATER = register("block.stone_chest.lift_underwater");
	public static final DeferredSoundEvent BLOCK_STONE_CHEST_ITEM_CRUMBLE = register("block.stone_chest.item_crumble");
	public static final DeferredSoundEvent BLOCK_TERMITE_MOUND_TERMITE_IDLE = register("block.termite_mound.termite_idle");
	public static final DeferredSoundEvent BLOCK_TERMITE_MOUND_TERMITE_GNAW = register("block.termite_mound.termite_gnaw");
	public static final DeferredSoundEvent BLOCK_TERMITE_MOUND_TERMITE_GNAW_FINISH = register("block.termite_mound.termite_gnaw_finish");
	public static final DeferredSoundEvent BLOCK_TERMITE_MOUND_ENTER = register("block.termite_mound.enter");
	public static final DeferredSoundEvent BLOCK_TERMITE_MOUND_EXIT = register("block.termite_mound.exit");
	public static final DeferredSoundEvent BLOCK_TUMBLEWEED_PLANT_SHEAR = register("block.tumbleweed_plant.shear");
	public static final DeferredSoundEvent BLOCK_PRICKLY_PEAR_PICK = register("block.prickly_pear.pick");
	public static final DeferredSoundEvent BLOCK_OSTRICH_EGG_CRACK = register("block.ostrich_egg.crack");
	public static final DeferredSoundEvent BLOCK_OSTRICH_EGG_HATCH = register("block.ostrich_egg.hatch");
	public static final DeferredSoundEvent BLOCK_PENGUIN_EGG_CRACK = register("block.penguin_egg.crack");
	public static final DeferredSoundEvent BLOCK_PENGUIN_EGG_HATCH = register("block.penguin_egg.hatch");
	public static final DeferredSoundEvent BLOCK_GEOTHERMAL_VENT_BOIL = register("block.geothermal_vent.boil");
	public static final DeferredSoundEvent BLOCK_GEOTHERMAL_VENT_ERUPT_AIR = register("block.geothermal_vent.erupt.air");
	public static final DeferredSoundEvent BLOCK_GEOTHERMAL_VENT_ERUPT_WATER = register("block.geothermal_vent.erupt.water");
	public static final DeferredSoundEvent BLOCK_GEOTHERMAL_VENT_ERUPT_LAVA = register("block.geothermal_vent.erupt.lava");
	public static final DeferredSoundEvent BLOCK_GEOTHERMAL_VENT_VENT_AMBIENT = register("block.geothermal_vent.vent.ambient");
	public static final DeferredSoundEvent BLOCK_FIRE_IGNITE = register("block.fire.ignite");

	// BLOCK SOUND SET
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> ALGAE = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.ALGAE);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> BAOBAB_NUT = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.BAOBAB_NUT);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> CACTUS = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.CACTUS);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> CLAY = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.CLAY);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> COARSE_DIRT = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.COARSE_DIRT);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> COCONUT = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.COCONUT);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> ECHO_GLASS = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.ECHO_GLASS);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> GRAVEL = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.GRAVEL);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> HANGING_TENDRIL = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.HANGING_TENDRIL);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> HOLLOWED_LOG = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.HOLLOWED_LOG);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> HOLLOWED_CHERRY_LOG = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.HOLLOWED_CHERRY_LOG);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> HOLLOWED_MAPLE_LOG = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.HOLLOWED_MAPLE_LOG);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> HOLLOWED_PALE_OAK_LOG = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.HOLLOWED_PALE_OAK_LOG);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> HOLLOWED_STEM = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.HOLLOWED_STEM);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> ICE = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.ICE);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> FROSTED_ICE = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.FROSTED_ICE);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> AUBURN_MOSS = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.AUBURN_MOSS);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> AUBURN_MOSS_CARPET = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.AUBURN_MOSS_CARPET);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> MAPLE_LEAVES = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.MAPLE_LEAVES);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> MAPLE_LEAF_LITTER = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.MAPLE_LEAF_LITTER);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> MAPLE_WOOD = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.MAPLE_WOOD);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> MAPLE_WOOD_HANGING_SIGN = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.MAPLE_WOOD_HANGING_SIGN);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> PALE_OAK_LEAVES = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.PALE_OAK_LEAVES);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> PALE_OAK_LEAF_LITTER = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.PALE_OAK_LEAF_LITTER);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> PALE_MOSS = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.PALE_MOSS);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> PALE_MOSS_CARPET = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.PALE_MOSS_CARPET);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> PALE_OAK_WOOD = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.PALE_OAK_WOOD);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> PALE_OAK_WOOD_HANGING_SIGN = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.PALE_OAK_WOOD_HANGING_SIGN);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> CHERRY_LEAF_LITTER = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.CHERRY_LEAF_LITTER);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> CONIFER_LEAVES = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.CONIFER_LEAVES);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> CONIFER_LEAF_LITTER = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.CONIFER_LEAF_LITTER);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> MESOGLEA = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.MESOGLEA);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> NEMATOCYST = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.NEMATOCYST);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> MUSHROOM = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.MUSHROOM);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> MUSHROOM_BLOCK = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.MUSHROOM_BLOCK);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> NULL_BLOCK = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.NULL_BLOCK);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> OSSEOUS_SCULK = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.OSSEOUS_SCULK);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> POLLEN = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.POLLEN);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> REINFORCED_DEEPSLATE = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.REINFORCED_DEEPSLATE);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> SAPLING = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.SAPLING);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> SANDSTONE = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.SANDSTONE);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> SCORCHED_SAND = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.SCORCHED_SAND);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> SUGAR_CANE = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.SUGAR_CANE);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> TERMITE_MOUND = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.TERMITE_MOUND);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> TUMBLEWEED_PLANT = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.TUMBLEWEED_PLANT);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> GABBRO = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.GABBRO);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> GABBRO_BRICKS = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.GABBRO_BRICKS);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> GEOTHERMAL_VENT = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.GEOTHERMAL_VENT);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> MAGMA = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.MAGMA);
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> MELON = REGISTER.registerBlockSoundSetForHolder(WWBlockSoundSets.MELON);

	public static final DeferredSoundEvent BLOCK_SHORT_GRASS_PLACE = register("block.short_grass.place");
	public static final DeferredSoundEvent BLOCK_SHORT_GRASS_BREAK = register("block.short_grass.break");
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> SHORT_GRASS = SoundEvents.GRASS
		.withPlace(Optional.of(BLOCK_SHORT_GRASS_PLACE.asHolder()))
		.withBreak(Optional.of(BLOCK_SHORT_GRASS_BREAK.asHolder()));

	public static final DeferredSoundEvent BLOCK_FROZEN_GRASS_PLACE = register("block.frozen_grass.place");
	public static final DeferredSoundEvent BLOCK_FROZEN_GRASS_BREAK = register("block.frozen_grass.break");
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> FROZEN_GRASS = SoundEvents.GRASS
		.withPlace(Optional.of(BLOCK_FROZEN_GRASS_PLACE.asHolder()))
		.withBreak(Optional.of(BLOCK_FROZEN_GRASS_BREAK.asHolder()));

	public static final DeferredSoundEvent BLOCK_DRY_GRASS_PLACE = register("block.dry_grass.place");
	public static final DeferredSoundEvent BLOCK_DRY_GRASS_BREAK = register("block.dry_grass.break");
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> DRY_GRASS = SoundEvents.GRASS
		.withPlace(Optional.of(BLOCK_DRY_GRASS_PLACE.asHolder()))
		.withBreak(Optional.of(BLOCK_DRY_GRASS_BREAK.asHolder()));

	public static final DeferredSoundEvent BLOCK_BARNACLES_PLACE = register("block.barnacles.place");
	public static final DeferredSoundEvent BLOCK_BARNACLES_BREAK = register("block.barnacles.break");
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> BARNACLES = SoundEvents.CORAL_BLOCK
		.withPlace(Optional.of(BLOCK_BARNACLES_PLACE.asHolder()))
		.withBreak(Optional.of(BLOCK_BARNACLES_BREAK.asHolder()));

	public static final DeferredSoundEvent BLOCK_SEA_ANEMONE_PLACE = register("block.sea_anemone.place");
	public static final DeferredSoundEvent BLOCK_SEA_ANEMONE_BREAK = register("block.sea_anemone.break");
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> SEA_ANEMONE = ALGAE
		.withPlace(Optional.of(BLOCK_SEA_ANEMONE_PLACE.asHolder()))
		.withBreak(Optional.of(BLOCK_SEA_ANEMONE_BREAK.asHolder()));

	public static final DeferredSoundEvent BLOCK_TUBE_WORMS_PLACE = register("block.tube_worms.place");
	public static final DeferredSoundEvent BLOCK_TUBE_WORM_BREAK = register("block.tube_worms.break");
	public static final BlockSoundCollection<Optional<Holder<SoundEvent>>> TUBE_WORMS = SoundEvents.CORAL_BLOCK
		.withPlace(Optional.of(BLOCK_TUBE_WORM_BREAK.asHolder()))
		.withBreak(Optional.of(BLOCK_TUBE_WORMS_PLACE.asHolder()));

	//ENTITY
	public static final DeferredSoundEvent ENTITY_FIREFLY_HIDE = register("entity.firefly.hide");
	public static final DeferredSoundEvent ENTITY_FIREFLY_HURT = register("entity.firefly.hurt");
	public static final DeferredSoundEvent ENTITY_BUTTERFLY_HURT = register("entity.butterfly.hurt");
	public static final DeferredSoundEvent ENTITY_BUTTERFLY_DEATH = register("entity.butterfly.death");
	public static final DeferredSoundEvent ENTITY_JELLYFISH_STING = register("entity.jellyfish.sting");
	public static final DeferredSoundEvent ENTITY_JELLYFISH_AMBIENT_WATER = register("entity.jellyfish.ambient_water");
	public static final DeferredSoundEvent ENTITY_JELLYFISH_HURT_WATER = register("entity.jellyfish.hurt_water");
	public static final DeferredSoundEvent ENTITY_JELLYFISH_HURT = register("entity.jellyfish.hurt");
	public static final DeferredSoundEvent ENTITY_JELLYFISH_SWIM = register("entity.jellyfish.swim");
	public static final DeferredSoundEvent ENTITY_JELLYFISH_HIDE = register("entity.jellyfish.hide");
	public static final DeferredSoundEvent ENTITY_JELLYFISH_DEATH_WATER = register("entity.jellyfish.death_water");
	public static final DeferredSoundEvent ENTITY_JELLYFISH_DEATH = register("entity.jellyfish.death");
	public static final DeferredSoundEvent ENTITY_CRAB_IDLE = register("entity.crab.idle");
	public static final DeferredSoundEvent ENTITY_CRAB_STEP = register("entity.crab.step");
	public static final DeferredSoundEvent ENTITY_CRAB_ATTACK = register("entity.crab.attack");
	public static final DeferredSoundEvent ENTITY_CRAB_HURT = register("entity.crab.hurt");
	public static final DeferredSoundEvent ENTITY_CRAB_DEATH = register("entity.crab.death");
	public static final DeferredSoundEvent ENTITY_CRAB_DIG = register("entity.crab.dig");
	public static final DeferredSoundEvent ENTITY_CRAB_EMERGE = register("entity.crab.emerge");
	public static final DeferredSoundEvent ENTITY_OSTRICH_IDLE = register("entity.ostrich.idle");
	public static final DeferredSoundEvent ENTITY_OSTRICH_GRUNT = register("entity.ostrich.grunt");
	public static final DeferredSoundEvent ENTITY_OSTRICH_HISS = register("entity.ostrich.hiss");
	public static final DeferredSoundEvent ENTITY_OSTRICH_HURT = register("entity.ostrich.hurt");
	public static final DeferredSoundEvent ENTITY_OSTRICH_DEATH = register("entity.ostrich.death");
	public static final DeferredSoundEvent ENTITY_OSTRICH_STEP = register("entity.ostrich.step");
	public static final DeferredSoundEvent ENTITY_OSTRICH_SADDLE = register("entity.ostrich.saddle");
	public static final DeferredSoundEvent ENTITY_OSTRICH_BEAK_STUCK = register("entity.ostrich.beak.stuck");
	public static final DeferredSoundEvent ENTITY_OSTRICH_SWING = register("entity.ostrich.beak.swing");
	public static final DeferredSoundEvent ENTITY_OSTRICH_EAT = register("entity.ostrich.eat");
	public static final DeferredSoundEvent ENTITY_OSTRICH_LAY_EGG = register("entity.ostrich.lay_egg");
	public static final DeferredSoundEvent ENTITY_OSTRICH_INBRED_IDLE_BOCK = register("entity.ostrich.inbred.idle.bock");
	public static final DeferredSoundEvent ENTITY_OSTRICH_INBRED_IDLE_AH = register("entity.ostrich.inbred.idle.ah");
	public static final DeferredSoundEvent ENTITY_OSTRICH_INBRED_HURT = register("entity.ostrich.inbred.hurt");
	public static final DeferredSoundEvent ENTITY_OSTRICH_INBRED_DEATH = register("entity.ostrich.inbred.death");
	public static final DeferredSoundEvent ENTITY_OSTRICH_INBRED_STEP = register("entity.ostrich.inbred.step");
	public static final DeferredSoundEvent ENTITY_OSTRICH_INBRED_BEAK_STUCK = register("entity.ostrich.inbred.beak.stuck");
	public static final DeferredSoundEvent ENTITY_OSTRICH_INBRED_SWING = register("entity.ostrich.inbred.beak.swing");
	public static final DeferredSoundEvent ENTITY_ZOMBIE_OSTRICH_IDLE = register("entity.zombie_ostrich.idle");
	public static final DeferredSoundEvent ENTITY_ZOMBIE_OSTRICH_GRUNT = register("entity.zombie_ostrich.grunt");
	public static final DeferredSoundEvent ENTITY_ZOMBIE_OSTRICH_HISS = register("entity.zombie_ostrich.hiss");
	public static final DeferredSoundEvent ENTITY_ZOMBIE_OSTRICH_HURT = register("entity.zombie_ostrich.hurt");
	public static final DeferredSoundEvent ENTITY_ZOMBIE_OSTRICH_DEATH = register("entity.zombie_ostrich.death");
	public static final DeferredSoundEvent ENTITY_ZOMBIE_OSTRICH_STEP = register("entity.zombie_ostrich.step");
	public static final DeferredSoundEvent ENTITY_ZOMBIE_OSTRICH_SADDLE = register("entity.zombie_ostrich.saddle");
	public static final DeferredSoundEvent ENTITY_ZOMBIE_OSTRICH_BEAK_STUCK = register("entity.zombie_ostrich.beak.stuck");
	public static final DeferredSoundEvent ENTITY_ZOMBIE_OSTRICH_SWING = register("entity.zombie_ostrich.beak.swing");
	public static final DeferredSoundEvent ENTITY_ZOMBIE_OSTRICH_EAT = register("entity.zombie_ostrich.eat");
	public static final DeferredSoundEvent ENTITY_PENGUIN_IDLE = register("entity.penguin.idle");
	public static final DeferredSoundEvent ENTITY_PENGUIN_CALL = register("entity.penguin.call");
	public static final DeferredSoundEvent ENTITY_PENGUIN_HURT = register("entity.penguin.hurt");
	public static final DeferredSoundEvent ENTITY_PENGUIN_DEATH = register("entity.penguin.death");
	public static final DeferredSoundEvent ENTITY_PENGUIN_LAY_EGG = register("entity.penguin.lay_egg");
	public static final DeferredSoundEvent ENTITY_PENGUIN_STEP = register("entity.penguin.step");
	public static final DeferredSoundEvent ENTITY_LINUX_IDLE = register("entity.penguin.linux.idle");
	public static final DeferredSoundEvent ENTITY_LINUX_CALL = register("entity.penguin.linux.call");
	public static final DeferredSoundEvent ENTITY_LINUX_HURT = register("entity.penguin.linux.hurt");
	public static final DeferredSoundEvent ENTITY_LINUX_DEATH = register("entity.penguin.linux.death");
	public static final DeferredSoundEvent ENTITY_LINUX_LAY_EGG = register("entity.penguin.linux.lay_egg");
	public static final DeferredSoundEvent ENTITY_LINUX_STEP = register("entity.penguin.linux.step");
	public static final DeferredSoundEvent ENTITY_SCORCHED_AMBIENT = register("entity.scorched.ambient");
	public static final DeferredSoundEvent ENTITY_SCORCHED_HURT = register("entity.scorched.hurt");
	public static final DeferredSoundEvent ENTITY_SCORCHED_DEATH = register("entity.scorched.death");
	public static final DeferredSoundEvent ENTITY_SCORCHED_STEP = register("entity.scorched.step");
	public static final DeferredSoundEvent ENTITY_SCORCHED_STEP_LAVA = register("entity.scorched.step_lava");
	public static final DeferredSoundEvent ENTITY_MOOBLOOM_SHEAR = register("entity.moobloom.shear");
	public static final DeferredSoundEvent ENTITY_TUMBLEWEED_BOUNCE = register("entity.tumbleweed.bounce");
	public static final DeferredSoundEvent ENTITY_TUMBLEWEED_BREAK = register("entity.tumbleweed.break");
	public static final DeferredSoundEvent ENTITY_TUMBLEWEED_DAMAGE = register("entity.tumbleweed.damage");
	public static final DeferredSoundEvent ENTITY_WARDEN_KIRBY_DEATH = register("entity.warden.kirby_death");
	public static final DeferredSoundEvent ENTITY_WARDEN_DYING = register("entity.warden.dying");
	public static final DeferredSoundEvent ENTITY_WARDEN_UNDERWATER_DYING = register("entity.warden.dying_underwater");
	public static final DeferredSoundEvent ENTITY_WARDEN_SWIM = register("entity.warden.swim");
	public static final DeferredSoundEvent ENTITY_WARDEN_BRAP = register("entity.warden.brap");
	public static final DeferredSoundEvent ENTITY_WARDEN_STELLA_HEARTBEAT = register("entity.warden.stella_heartbeat");
	public static final DeferredSoundEvent ENTITY_FROG_SUS_DEATH = register("entity.frog.sus_death");

	//ITEM
	public static final DeferredSoundEvent ITEM_AXE_HOLLOW = register("item.axe.hollow");
	public static final DeferredSoundEvent ITEM_BOTTLE_CATCH_FIREFLY = register("item.bottle.catch_firefly");
	public static final DeferredSoundEvent ITEM_BOTTLE_RELEASE_FIREFLY = register("item.bottle.release_firefly");
	public static final DeferredSoundEvent ITEM_BOTTLE_PUT_IN_LANTERN_FIREFLY = register("item.bottle.put_in_lantern_firefly");
	public static final DeferredSoundEvent ITEM_BOTTLE_CATCH_BUTTERFLY = register("item.bottle.catch_butterfly");
	public static final DeferredSoundEvent ITEM_BOTTLE_RELEASE_BUTTERFLY = register("item.bottle.release_butterfly");
	public static final DeferredSoundEvent ITEM_BUCKET_FILL_JELLYFISH = register("item.bucket.fill_jellyfish");
	public static final DeferredSoundEvent ITEM_BUCKET_EMPTY_JELLYFISH = register("item.bucket.empty_jellyfish");
	public static final DeferredSoundEvent ITEM_BUCKET_FILL_CRAB = register("item.bucket.fill_crab");
	public static final DeferredSoundEvent ITEM_BUCKET_EMPTY_CRAB = register("item.bucket.empty_crab");
	public static final DeferredSoundEvent ITEM_COCONUT_THROW = register("item.coconut.throw");
	public static final DeferredSoundEvent ITEM_COCONUT_LAND_BREAK = register("item.coconut.land_break");
	public static final DeferredSoundEvent ITEM_COCONUT_LAND = register("item.coconut.land");
	public static final DeferredSoundEvent ITEM_COCONUT_HIT_HEAD = register("item.coconut.hit_head");
	public static final DeferredSoundEvent ITEM_ENDER_PEARL_LAND = register("item.ender_pearl.land");
	public static final DeferredSoundEvent ITEM_EXPERIENCE_BOTTLE_SPLASH = register("item.experience_bottle.splash");
	public static final DeferredSoundEvent ITEM_EGG_LAND = register("item.egg.land");
	public static final DeferredSoundEvent ITEM_MILKWEED_POD_BLOWN = register("item.milkweed_pod.blow");
	public static final DeferredSoundEvent ITEM_POTION_SPLASH = register("item.potion.splash");
	public static final DeferredSoundEvent ITEM_POTION_MAGIC = register("item.potion.magic");
	public static final DeferredSoundEvent ITEM_POTION_LINGERING = register("item.potion.lingering");
	public static final DeferredSoundEvent ITEM_SNOWBALL_LAND = register("item.snowball.land");

	// MESOGLEA
	public static final DeferredSoundEvent AMBIENT_MESOGLEA_LOOP = register("ambient.mesoglea.loop");
	public static final DeferredSoundEvent AMBIENT_MESOGLEA_ENTER = register("ambient.mesoglea.enter");
	public static final DeferredSoundEvent AMBIENT_MESOGLEA_EXIT = register("ambient.mesoglea.exit");
	public static final DeferredSoundEvent ENTITY_GENERIC_SWIM_MESOGLEA = register("entity.generic.swim_mesoglea");
	public static final DeferredSoundEvent ENTITY_HOSTILE_SWIM_MESOGLEA = register("entity.hostile.swim_mesoglea");
	public static final DeferredSoundEvent ENTITY_PLAYER_SWIM_MESOGLEA = register("entity.player.swim_mesoglea");
	public static final DeferredSoundEvent ENTITY_GENERIC_SPLASH_MESOGLEA = register("entity.generic.splash_mesoglea");
	public static final DeferredSoundEvent ENTITY_HOSTILE_SPLASH_MESOGLEA = register("entity.hostile.splash_mesoglea");
	public static final DeferredSoundEvent ENTITY_PLAYER_SPLASH_MESOGLEA = register("entity.player.splash_mesoglea");
	public static final DeferredSoundEvent ENTITY_PLAYER_SPLASH_HIGH_SPEED_MESOGLEA = register("entity.player.splash.high_speed_mesoglea");
	public static final DeferredSoundEvent PARTICLE_MESOGLEA_DRIP_LAND = register("particle.mesoglea_drip.land");
	public static final DeferredSoundEvent PARTICLE_MESOGLEA_BUBBLE_POP = register("particle.mesoglea_bubble.pop");

	//MISC
	public static final DeferredSoundEvent PARTICLE_FLOATING_SCULK_BUBBLE_POP = register("particle.floating_sculk_bubble.pop");
	public static final DeferredSoundEvent PLAYER_HURT_CACTUS = register("entity.player.hurt.cactus");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_FORESTS = register("music.overworld.wilder_forests");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_FLOWER_FORESTS = register("music.overworld.wilder_flower_forests");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_DAPPLED_FORESTS = register("music.overworld.wilder_dappled_forests");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_LUSH_CAVES = register("music.overworld.wilder_lush_caves");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_DRIPSTONE_CAVES = register("music.overworld.wilder_dripstone_caves");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_CHERRY_GROVES = register("music.overworld.wilder_cherry_groves");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_GROVES = register("music.overworld.wilder_groves");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_JUNGLES = register("music.overworld.wilder_jungles");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_BAMBOO_JUNGLES = register("music.overworld.wilder_bamboo_jungles");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_SPARSE_JUNGLES = register("music.overworld.wilder_sparse_jungles");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_BADLANDS = register("music.overworld.wilder_badlands");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_DESERTS = register("music.overworld.wilder_deserts");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_TAIGAS = register("music.overworld.wilder_taigas");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_OLD_GROWTH_TAIGAS = register("music.overworld.wilder_old_growth_taigas");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_SNOWY = register("music.overworld.wilder_snowy");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_OCEANS = register("music.overworld.wilder_oceans");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_FROZEN_OCEANS = register("music.overworld.wilder_frozen_oceans");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_WILD_WARM_OCEANS = register("music.overworld.wilder_warm_oceans");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_FROZEN_CAVES = register("music.overworld.frozen_caves");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_MESOGLEA_CAVES = register("music.overworld.mesoglea_caves");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_MAGMATIC_CAVES = register("music.overworld.magmatic_caves");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_MAPLE_FOREST = register("music.overworld.maple_forest");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_DYING_FOREST = register("music.overworld.dying_forest");
	public static final DeferredSoundEvent MUSIC_OVERWORLD_SNOWY_DYING_FOREST = register("music.overworld.snowy_dying_forest");

	static {
		REGISTER.register();
	}

	public static void init() {}

	public static DeferredSoundEvent register(String path) {
		return REGISTER.register(path);
	}

	private WWSounds() {}
}
