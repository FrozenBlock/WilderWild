package net.frozenblock.wilderwild.registry;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import net.frozenblock.lib.entity.api.attribute.FrozenDefaultAttributeRegistry;
import net.frozenblock.lib.entity.api.category.FrozenMobCategories;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
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
import net.frozenblock.wilderwild.references.WWEntityTypeIds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.level.levelgen.Heightmap;

public final class WWEntityTypes {
	private static final FrozenDeferredRegister.Entities REGISTER = FrozenDeferredRegister.createEntities(
		WWConstants.MOD_ID
	);

	public static final FrozenHolder<EntityType<?>, EntityType<Firefly>> FIREFLY = register(WWEntityTypeIds.FIREFLY,
		Firefly::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "firefly"),
		builder -> builder
			.sized(0.3F, 0.3F)
			.eyeHeight(0.3F * 0.85F) // 0.85F is default eye height scaler
			.clientTrackingRange(5),
		fireflyType -> {
			FrozenDefaultAttributeRegistry.register(fireflyType, Firefly.createAttributes());
			SpawnPlacements.register(
				fireflyType,
				SpawnPlacementTypes.NO_RESTRICTIONS,
				Heightmap.Types.MOTION_BLOCKING,
				Firefly::checkFireflySpawnRules
			);
		}
	);

	public static final FrozenHolder<EntityType<?>, EntityType<Butterfly>> BUTTERFLY = register(WWEntityTypeIds.BUTTERFLY,
		Butterfly::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "butterfly"),
		builder -> builder
			.sized(0.3F, 0.3F)
			.eyeHeight(0.3F * 0.85F) // 0.85F is default eye height scaler
			.clientTrackingRange(5),
		entityType -> {
			FrozenDefaultAttributeRegistry.register(entityType, Butterfly.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.NO_RESTRICTIONS,
				Heightmap.Types.MOTION_BLOCKING,
				Butterfly::checkButterflySpawnRules
			);
		}
	);

	public static final FrozenHolder<EntityType<?>, EntityType<Jellyfish>> JELLYFISH = register(WWEntityTypeIds.JELLYFISH,
		Jellyfish::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "jellyfish"),
		builder -> builder
			.sized(0.4F, 0.4F)
			.eyeHeight(0.4F * 0.5F) // eye height is the height * 0.5F
			.clientTrackingRange(10),
		entityType -> {
			FrozenDefaultAttributeRegistry.register(entityType, Jellyfish.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.IN_WATER,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Jellyfish::checkJellyfishSpawnRules
			);
		}
	);

	public static final FrozenHolder<EntityType<?>, EntityType<Tumbleweed>> TUMBLEWEED = register(WWEntityTypeIds.TUMBLEWEED,
		Tumbleweed::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "tumbleweed"),
		builder -> builder
			.sized(0.98F, 0.98F)
			.eyeHeight(0.98F * 0.5F) // eye height is the height * 0.5F
			.updateInterval(2),
		entityType -> {
			FrozenDefaultAttributeRegistry.register(entityType, Tumbleweed.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Tumbleweed::checkTumbleweedSpawnRules
			);
		}
	);

	public static final FrozenHolder<EntityType<?>, EntityType<Crab>> CRAB = register(WWEntityTypeIds.CRAB,
		Crab::new, FrozenMobCategories.getCategory(WWConstants.MOD_ID, "crab"),
		builder -> builder
			.sized(0.5F, 0.5F)
			.eyeHeight(0.5F * 0.65F), // eye height is the height * 0.65F
		entityType -> {
			FrozenDefaultAttributeRegistry.register(entityType, Crab.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.IN_WATER,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Crab::checkCrabSpawnRules
			);
		}
	);

	public static final FrozenHolder<EntityType<?>, EntityType<Ostrich>> OSTRICH = register(WWEntityTypeIds.OSTRICH,
		Ostrich::new, MobCategory.CREATURE,
		builder -> builder
			.sized(1.1F, 2.3F)
			.eyeHeight(2.3F) // eye height is hitbox height
			.clientTrackingRange(10),
		entityType -> {
			FrozenDefaultAttributeRegistry.register(entityType, Ostrich.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Ostrich::checkOstrichSpawnRules
			);
		}
	);

	public static final FrozenHolder<EntityType<?>, EntityType<ZombieOstrich>> ZOMBIE_OSTRICH = register(WWEntityTypeIds.ZOMBIE_OSTRICH,
		ZombieOstrich::new, MobCategory.MONSTER,
		builder -> builder
			.sized(1.1F, 2.3F)
			.eyeHeight(2.3F) // eye height is hitbox height
			.clientTrackingRange(10),
		entityType -> {
			FrozenDefaultAttributeRegistry.register(entityType, ZombieOstrich.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				ZombieOstrich::checkZombieOstrichSpawnRules
			);
		}
	);

	public static final FrozenHolder<EntityType<?>, EntityType<Scorched>> SCORCHED = register(WWEntityTypeIds.SCORCHED,
		Scorched::new, MobCategory.MONSTER,
		builder -> builder
			.sized(1.26F, 0.81F)
			.eyeHeight(0.585F)
			.fireImmune()
			.clientTrackingRange(8)
			.notInPeaceful(),
		entityType -> {
			FrozenDefaultAttributeRegistry.register(entityType, Scorched.createAttributes());
			SpawnPlacements.register(
				entityType,
				WWSpawnTypes.ON_GROUND_OR_IN_LAVA,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Scorched::checkScorchedSpawnRules
			);
		}
	);

	public static final FrozenHolder<EntityType<?>, EntityType<FlowerCow>> MOOBLOOM = register(WWEntityTypeIds.MOOBLOOM,
		FlowerCow::new, MobCategory.CREATURE,
		builder -> builder
			.sized(0.9F, 1.4F)
			.eyeHeight(1.3F)
			.passengerAttachments(1.36875F)
			.clientTrackingRange(10),
		entityType -> {
			FrozenDefaultAttributeRegistry.register(entityType, FlowerCow.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				FlowerCow::checkFlowerCowSpawnRules
			);
		}
	);

	public static final FrozenHolder<EntityType<?>, EntityType<Penguin>> PENGUIN = register(WWEntityTypeIds.PENGUIN,
		Penguin::new, MobCategory.CREATURE,
		builder -> builder
			.sized(0.55F, 1F)
			.eyeHeight(0.8F)
			.clientTrackingRange(10)
			.immuneTo(WWBlockTags.PENGUIN_IMMUNE_TO),
		entityType -> {
			FrozenDefaultAttributeRegistry.register(entityType, Penguin.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Penguin::checkPenguinSpawnRules
			);
		}
	);

	public static final FrozenHolder<EntityType<?>, EntityType<CoconutProjectile>> COCONUT = register(WWEntityTypeIds.COCONUT,
		CoconutProjectile::new, MobCategory.MISC,
		builder -> builder
			.sized(0.25F, 0.25F)
			.clientTrackingRange(4)
			.updateInterval(20),
		entityType -> {}
	);

	public static final FrozenHolder<EntityType<?>, EntityType<FallingLeafTicker>> FALLING_LEAVES = register(WWEntityTypeIds.FALLING_LEAVES,
		FallingLeafTicker::new, MobCategory.MISC,
		builder -> builder
			.sized(0F, 0F)
			.clientTrackingRange(0),
		entityType -> {}
	);

	// BOATS
	public static final FrozenHolder<EntityType<?>, EntityType<Boat>> BAOBAB_BOAT = register(WWEntityTypeIds.BAOBAB_BOAT,
		EntityTypes.boatFactory(() -> WWItems.BAOBAB_BOAT.get()), MobCategory.MISC,
		builder -> builder
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);
	public static final FrozenHolder<EntityType<?>, EntityType<ChestBoat>> BAOBAB_CHEST_BOAT = register(WWEntityTypeIds.BAOBAB_CHEST_BOAT,
		EntityTypes.chestBoatFactory(() -> WWItems.BAOBAB_CHEST_BOAT.get()), MobCategory.MISC,
		builder -> builder
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static final FrozenHolder<EntityType<?>, EntityType<Boat>> WILLOW_BOAT = register(WWEntityTypeIds.WILLOW_BOAT,
		EntityTypes.boatFactory(() -> WWItems.WILLOW_BOAT.get()), MobCategory.MISC,
		builder -> builder
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);
	public static final FrozenHolder<EntityType<?>, EntityType<ChestBoat>> WILLOW_CHEST_BOAT = register(WWEntityTypeIds.WILLOW_CHEST_BOAT,
		EntityTypes.chestBoatFactory(() -> WWItems.WILLOW_CHEST_BOAT.get()), MobCategory.MISC,
		builder -> builder
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static final FrozenHolder<EntityType<?>, EntityType<Boat>> CYPRESS_BOAT = register(WWEntityTypeIds.CYPRESS_BOAT,
		EntityTypes.boatFactory(() -> WWItems.CYPRESS_BOAT.get()), MobCategory.MISC,
		builder -> builder
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);
	public static final FrozenHolder<EntityType<?>, EntityType<ChestBoat>> CYPRESS_CHEST_BOAT = register(WWEntityTypeIds.CYPRESS_CHEST_BOAT,
		EntityTypes.chestBoatFactory(() -> WWItems.CYPRESS_CHEST_BOAT.get()), MobCategory.MISC,
		builder -> builder
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static final FrozenHolder<EntityType<?>, EntityType<Boat>> PALM_BOAT = register(WWEntityTypeIds.PALM_BOAT,
		EntityTypes.boatFactory(() -> WWItems.PALM_BOAT.get()), MobCategory.MISC,
		builder -> builder
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);
	public static final FrozenHolder<EntityType<?>, EntityType<ChestBoat>> PALM_CHEST_BOAT = register(WWEntityTypeIds.PALM_CHEST_BOAT,
		EntityTypes.chestBoatFactory(() -> WWItems.PALM_CHEST_BOAT.get()), MobCategory.MISC,
		builder -> builder
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static final FrozenHolder<EntityType<?>, EntityType<Boat>> MAPLE_BOAT = register(WWEntityTypeIds.MAPLE_BOAT,
		EntityTypes.boatFactory(() -> WWItems.MAPLE_BOAT.get()), MobCategory.MISC,
		builder -> builder
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);
	public static final FrozenHolder<EntityType<?>, EntityType<ChestBoat>> MAPLE_CHEST_BOAT = register(WWEntityTypeIds.MAPLE_CHEST_BOAT,
		EntityTypes.chestBoatFactory(() -> WWItems.MAPLE_CHEST_BOAT.get()), MobCategory.MISC,
		builder -> builder
			.noLootTable()
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
	);

	public static void init() {}

	static {
		REGISTER.register();
	}

	private static <E extends Entity> FrozenHolder<EntityType<?>, EntityType<E>> register(ResourceKey<EntityType<?>> id, EntityType.EntityFactory<E> factory, MobCategory category, UnaryOperator<EntityType.Builder<E>> builder) {
		return REGISTER.registerEntityType(id.identifier().getPath(), factory, category, builder, null);
	}

	private static <E extends Entity> FrozenHolder<EntityType<?>, EntityType<E>> register(ResourceKey<EntityType<?>> id, EntityType.EntityFactory<E> factory, MobCategory category, UnaryOperator<EntityType.Builder<E>> builder, Consumer<EntityType<E>> also) {
		return REGISTER.registerEntityType(id.identifier().getPath(), factory, category, builder, also);
	}
}
