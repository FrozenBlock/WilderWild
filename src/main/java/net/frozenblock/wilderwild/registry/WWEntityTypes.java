/*
 * Copyright 2023-2025 FrozenBlock
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

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.frozenblock.lib.entity.api.spawnplacement.FrozenSpawnPlacementTypes;
import net.frozenblock.lib.mobcategory.api.FrozenMobCategories;
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
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;

public final class WWEntityTypes {
	public static final EntityType<Firefly> FIREFLY = register(
		"firefly",
		EntityType.Builder.of(Firefly::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "firefly"))
			.sized(0.3F, 0.3F)
			.eyeHeight(0.3F * 0.85F) // 0.85F is default eye height scaler
			.build(WWConstants.string("firefly"))
	);

	public static final EntityType<Butterfly> BUTTERFLY = register(
		"butterfly",
		EntityType.Builder.of(Butterfly::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "butterfly"))
			.sized(0.3F, 0.3F)
			.eyeHeight(0.3F * 0.85F) // 0.85F is default eye height scaler
			.build(WWConstants.string("butterfly"))
	);

	public static final EntityType<Jellyfish> JELLYFISH = register(
		"jellyfish",
		EntityType.Builder.of(Jellyfish::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "jellyfish"))
			.sized(0.4F, 0.4F)
			.eyeHeight(0.4F * 0.5F) // eye height is the height * 0.5F
			.build(WWConstants.string("jellyfish"))
	);

	public static final EntityType<Tumbleweed> TUMBLEWEED = register(
		"tumbleweed",
		EntityType.Builder.of(Tumbleweed::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "tumbleweed"))
			.sized(0.98F, 0.98F)
			.eyeHeight(0.98F * 0.5F) // eye height is the height * 0.5F
			.build(WWConstants.string("tumbleweed"))
	);

	public static final EntityType<Crab> CRAB = register(
		"crab",
		EntityType.Builder.of(Crab::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "crab"))
			.sized(0.5F, 0.5F)
			.eyeHeight(0.5F * 0.65F) // eye height is the height * 0.65F
			.build(WWConstants.string("crab"))
	);

	public static final EntityType<Ostrich> OSTRICH = register(
		"ostrich",
		EntityType.Builder.of(Ostrich::new, MobCategory.CREATURE)
			.sized(1.1F, 2.3F)
			.eyeHeight(2.3F) // eye height is hitbox height
			.build(WWConstants.string("ostrich"))
	);

	public static final EntityType<Scorched> SCORCHED = register(
		"scorched",
		EntityType.Builder.of(Scorched::new, MobCategory.MONSTER)
			.sized(1.26F, 0.81F)
			.eyeHeight(0.585F)
			.fireImmune()
			.clientTrackingRange(8)
			.build(WWConstants.string("scorched"))
	);

	public static final EntityType<FlowerCow> MOOBLOOM = register(
		"moobloom",
		EntityType.Builder.of(FlowerCow::new, MobCategory.CREATURE)
			.sized(0.9F, 1.4F)
			.eyeHeight(1.3F)
			.passengerAttachments(1.36875F)
			.clientTrackingRange(10)
			.build(WWConstants.string("moobloom"))
	);

	public static final EntityType<Penguin> PENGUIN = register(
		"penguin",
		EntityType.Builder.of(Penguin::new, MobCategory.CREATURE)
			.sized(0.55F, 1F)
			.eyeHeight(0.8F)
			.build(WWConstants.string("penguin"))
	);

	public static final EntityType<CoconutProjectile> COCONUT = register(
		"coconut",
		EntityType.Builder.<CoconutProjectile>of(CoconutProjectile::new, MobCategory.MISC)
			.sized(0.25F, 0.25F)
			.clientTrackingRange(64)
			.updateInterval(10)
			.build(WWConstants.string("coconut"))
	);

	public static final EntityType<FallingLeafTicker> FALLING_LEAVES = register(
		"falling_leaves",
		EntityType.Builder.<FallingLeafTicker>of(FallingLeafTicker::new, MobCategory.MISC)
			.sized(0F, 0F)
			.clientTrackingRange(0)
			.build(WWConstants.string("falling_leaves"))
	);

	private WWEntityTypes() {
		throw new UnsupportedOperationException("WWEntityTypes contains only static declarations.");
	}

	public static void init() {
		WWConstants.logWithModId("Registering Entities for", WWConstants.UNSTABLE_LOGGING);

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
			Crab::checkCrabSpawnRules);

		FabricDefaultAttributeRegistry.register(OSTRICH, Ostrich.createAttributes());
		SpawnPlacements.register(
			OSTRICH,
			SpawnPlacementTypes.ON_GROUND,
			Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
			Ostrich::checkOstrichSpawnRules
		);

		FabricDefaultAttributeRegistry.register(SCORCHED, Scorched.createAttributes());
		SpawnPlacements.register(
			SCORCHED,
			FrozenSpawnPlacementTypes.ON_GROUND_OR_ON_LAVA_SURFACE,
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

	@NotNull
	private static <E extends Entity, T extends EntityType<E>> T register(@NotNull String path, @NotNull T entityType) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, WWConstants.id(path), entityType);
	}
}
