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

package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.frozenblock.lib.entity.api.category.FrozenMobCategories;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.Butterfly;
import net.frozenblock.wilderwild.entity.CoconutProjectile;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.FallingLeafTicker;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.FlowerCow;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.Ostrich;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.entity.Scorched;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.entity.ZombieOstrich;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;

public final class WWEntityTypes {
	public static final EntityType<Firefly> FIREFLY = register(
		"firefly",
		EntityType.Builder.of(Firefly::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "firefly"))
			.sized(0.3F, 0.3F)
			.eyeHeight(0.3F * 0.85F) // 0.85F is default eye height scaler
			.clientTrackingRange(5)
	);

	public static final EntityType<Butterfly> BUTTERFLY = register(
		"butterfly",
		EntityType.Builder.of(Butterfly::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "butterfly"))
			.sized(0.3F, 0.3F)
			.eyeHeight(0.3F * 0.85F) // 0.85F is default eye height scaler
			.clientTrackingRange(5)
	);

	public static final EntityType<Jellyfish> JELLYFISH = register(
		"jellyfish",
		EntityType.Builder.of(Jellyfish::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "jellyfish"))
			.sized(0.4F, 0.4F)
			.eyeHeight(0.4F * 0.5F) // eye height is the height * 0.5F
			.clientTrackingRange(10)
	);

	public static final EntityType<Tumbleweed> TUMBLEWEED = register(
		"tumbleweed",
		EntityType.Builder.of(Tumbleweed::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "tumbleweed"))
			.sized(0.98F, 0.98F)
			.eyeHeight(0.98F * 0.5F) // eye height is the height * 0.5F
	);

	public static final EntityType<Crab> CRAB = register(
		"crab",
		EntityType.Builder.of(Crab::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "crab"))
			.sized(0.5F, 0.5F)
			.eyeHeight(0.5F * 0.65F) // eye height is the height * 0.65F
	);

	public static final EntityType<Ostrich> OSTRICH = register(
		"ostrich",
		EntityType.Builder.of(Ostrich::new, MobCategory.CREATURE)
			.sized(1.1F, 2.3F)
			.eyeHeight(2.3F) // eye height is hitbox height
			.clientTrackingRange(10)
	);

	public static final EntityType<ZombieOstrich> ZOMBIE_OSTRICH = register(
		"zombie_ostrich",
		EntityType.Builder.of(ZombieOstrich::new, MobCategory.MONSTER)
			.sized(1.1F, 2.3F)
			.eyeHeight(2.3F) // eye height is hitbox height
			.clientTrackingRange(10)
	);

	public static final EntityType<Scorched> SCORCHED = register(
		"scorched",
		EntityType.Builder.of(Scorched::new, MobCategory.MONSTER)
			.sized(1.26F, 0.81F)
			.eyeHeight(0.585F)
			.fireImmune()
			.clientTrackingRange(8)
	);

	public static final EntityType<FlowerCow> MOOBLOOM = register(
		"moobloom",
		EntityType.Builder.of(FlowerCow::new, MobCategory.CREATURE)
			.sized(0.9F, 1.4F)
			.eyeHeight(1.3F)
			.passengerAttachments(1.36875F)
			.clientTrackingRange(10)
	);

	public static final EntityType<Penguin> PENGUIN = register(
		"penguin",
		EntityType.Builder.of(Penguin::new, MobCategory.CREATURE)
			.sized(0.55F, 1F)
			.eyeHeight(0.8F)
			.clientTrackingRange(10)
			.immuneTo(Blocks.POWDER_SNOW)
	);

	public static final EntityType<CoconutProjectile> COCONUT = register(
		"coconut",
		EntityType.Builder.<CoconutProjectile>of(CoconutProjectile::new, MobCategory.MISC)
			.sized(0.25F, 0.25F)
			.clientTrackingRange(4)
			.updateInterval(20)
	);

	public static final EntityType<FallingLeafTicker> FALLING_LEAVES = register(
		"falling_leaves",
		EntityType.Builder.<FallingLeafTicker>of(FallingLeafTicker::new, MobCategory.MISC)
			.sized(0F, 0F)
			.clientTrackingRange(0)
	);

	// BOATS

	public static final EntityType<Boat> BAOBAB_BOAT = register(
		"baobab_boat",
		EntityType.Builder.of(EntityType.boatFactory(() -> WWItems.BAOBAB_BOAT), MobCategory.MISC)
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static final EntityType<ChestBoat> BAOBAB_CHEST_BOAT = register(
		"baobab_chest_boat",
		EntityType.Builder.of(EntityType.chestBoatFactory(() -> WWItems.BAOBAB_CHEST_BOAT), MobCategory.MISC)
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static final EntityType<Boat> WILLOW_BOAT = register(
		"willow_boat",
		EntityType.Builder.of(EntityType.boatFactory(() -> WWItems.WILLOW_BOAT), MobCategory.MISC)
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static final EntityType<ChestBoat> WILLOW_CHEST_BOAT = register(
		"willow_chest_boat",
		EntityType.Builder.of(EntityType.chestBoatFactory(() -> WWItems.WILLOW_CHEST_BOAT), MobCategory.MISC)
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static final EntityType<Boat> CYPRESS_BOAT = register(
		"cypress_boat",
		EntityType.Builder.of(EntityType.boatFactory(() -> WWItems.CYPRESS_BOAT), MobCategory.MISC)
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static final EntityType<ChestBoat> CYPRESS_CHEST_BOAT = register(
		"cypress_chest_boat",
		EntityType.Builder.of(EntityType.chestBoatFactory(() -> WWItems.CYPRESS_CHEST_BOAT), MobCategory.MISC)
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static final EntityType<Boat> PALM_BOAT = register(
		"palm_boat",
		EntityType.Builder.of(EntityType.boatFactory(() -> WWItems.PALM_BOAT), MobCategory.MISC)
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static final EntityType<ChestBoat> PALM_CHEST_BOAT = register(
		"palm_chest_boat",
		EntityType.Builder.of(EntityType.chestBoatFactory(() -> WWItems.PALM_CHEST_BOAT), MobCategory.MISC)
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static final EntityType<Boat> MAPLE_BOAT = register(
		"maple_boat",
		EntityType.Builder.of(EntityType.boatFactory(() -> WWItems.MAPLE_BOAT), MobCategory.MISC)
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static final EntityType<ChestBoat> MAPLE_CHEST_BOAT = register(
		"maple_chest_boat",
		EntityType.Builder.of(EntityType.chestBoatFactory(() -> WWItems.MAPLE_CHEST_BOAT), MobCategory.MISC)
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	private WWEntityTypes() {
		throw new UnsupportedOperationException("WWEntityTypes contains only static declarations.");
	}

	public static void init() {
		WWConstants.logWithModId("Registering Entities for", WWConstants.UNSTABLE_LOGGING);
	}

	static {
		FabricDefaultAttributeRegistry.register(FIREFLY, Firefly.createAttributes());
		SpawnPlacements.register(
			FIREFLY,
			SpawnPlacementTypes.NO_RESTRICTIONS,
			Heightmap.Types.MOTION_BLOCKING,
			Firefly::checkFireflySpawnRules
		);

		FabricDefaultAttributeRegistry.register(BUTTERFLY, Butterfly.createAttributes());
		SpawnPlacements.register(
			BUTTERFLY,
			SpawnPlacementTypes.NO_RESTRICTIONS,
			Heightmap.Types.MOTION_BLOCKING,
			Butterfly::checkButterflySpawnRules
		);

		FabricDefaultAttributeRegistry.register(JELLYFISH, Jellyfish.createAttributes());
		SpawnPlacements.register(
			JELLYFISH,
			SpawnPlacementTypes.IN_WATER,
			Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
			Jellyfish::checkJellyfishSpawnRules
		);

		FabricDefaultAttributeRegistry.register(TUMBLEWEED, Tumbleweed.createAttributes());
		SpawnPlacements.register(
			TUMBLEWEED,
			SpawnPlacementTypes.ON_GROUND,
			Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
			Tumbleweed::checkTumbleweedSpawnRules
		);

		FabricDefaultAttributeRegistry.register(CRAB, Crab.createAttributes());
		SpawnPlacements.register(
			CRAB,
			SpawnPlacementTypes.IN_WATER,
			Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
			Crab::checkCrabSpawnRules
		);

		FabricDefaultAttributeRegistry.register(OSTRICH, Ostrich.createAttributes());
		SpawnPlacements.register(
			OSTRICH,
			SpawnPlacementTypes.ON_GROUND,
			Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
			Ostrich::checkOstrichSpawnRules
		);

		FabricDefaultAttributeRegistry.register(ZOMBIE_OSTRICH, ZombieOstrich.createAttributes());
		SpawnPlacements.register(
			ZOMBIE_OSTRICH,
			SpawnPlacementTypes.ON_GROUND,
			Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
			ZombieOstrich::checkZombieOstrichSpawnRules
		);

		FabricDefaultAttributeRegistry.register(SCORCHED, Scorched.createAttributes());
		SpawnPlacements.register(
			SCORCHED,
			WWSpawnTypes.SCORCHED,
			Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
			Scorched::checkScorchedSpawnRules
		);

		FabricDefaultAttributeRegistry.register(MOOBLOOM, FlowerCow.createAttributes());
		SpawnPlacements.register(
			MOOBLOOM,
			SpawnPlacementTypes.ON_GROUND,
			Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
			FlowerCow::checkFlowerCowSpawnRules
		);

		FabricDefaultAttributeRegistry.register(PENGUIN, Penguin.createAttributes());
		SpawnPlacements.register(
			PENGUIN,
			SpawnPlacementTypes.ON_GROUND,
			Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
			Penguin::checkPenguinSpawnRules
		);
	}

	static {
		WWConstants.logWithModId("Registering Entities for", WWConstants.UNSTABLE_LOGGING);
	}

	private static <T extends Entity> EntityType<T> register(String string, EntityType.Builder<T> builder) {
		ResourceKey<EntityType<?>> resourceKey = ResourceKey.create(Registries.ENTITY_TYPE, WWConstants.id(string));
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, resourceKey, builder.build(resourceKey));
	}
}
