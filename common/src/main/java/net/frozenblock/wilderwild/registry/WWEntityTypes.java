package net.frozenblock.wilderwild.registry;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import net.frozenblock.lib.entity.api.attribute.DefaultAttributeRegistry;
import net.frozenblock.lib.platform.api.registry.DeferredEntityType;
import net.frozenblock.lib.platform.api.registry.DeferredItem;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
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
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.level.levelgen.Heightmap;

public final class WWEntityTypes {
	private static final DeferredRegister.Entities REGISTER = DeferredRegister.createEntities(WWConstants.MOD_ID);

	public static final DeferredEntityType<Firefly> FIREFLY = register(WWEntityTypeIds.FIREFLY,
		Firefly::new,
		WWMobCategories.FIREFLY,
		builder -> builder
			.sized(0.3F, 0.3F)
			.eyeHeight(0.3F * 0.85F) // 0.85F is default eye height scaler
			.clientTrackingRange(5),
		fireflyType -> {
			DefaultAttributeRegistry.register(fireflyType, Firefly.createAttributes());
			SpawnPlacements.register(
				fireflyType,
				SpawnPlacementTypes.NO_RESTRICTIONS,
				Heightmap.Types.MOTION_BLOCKING,
				Firefly::checkFireflySpawnRules
			);
		}
	);

	public static final DeferredEntityType<Butterfly> BUTTERFLY = register(WWEntityTypeIds.BUTTERFLY,
		Butterfly::new,
		WWMobCategories.BUTTERFLY,
		builder -> builder
			.sized(0.3F, 0.3F)
			.eyeHeight(0.3F * 0.85F) // 0.85F is default eye height scaler
			.clientTrackingRange(5),
		entityType -> {
			DefaultAttributeRegistry.register(entityType, Butterfly.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.NO_RESTRICTIONS,
				Heightmap.Types.MOTION_BLOCKING,
				Butterfly::checkButterflySpawnRules
			);
		}
	);

	public static final DeferredEntityType<Jellyfish> JELLYFISH = register(WWEntityTypeIds.JELLYFISH,
		Jellyfish::new,
		WWMobCategories.JELLYFISH,
		builder -> builder
			.sized(0.4F, 0.4F)
			.eyeHeight(0.4F * 0.5F) // eye height is the height * 0.5F
			.clientTrackingRange(10),
		entityType -> {
			DefaultAttributeRegistry.register(entityType, Jellyfish.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.IN_WATER,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Jellyfish::checkJellyfishSpawnRules
			);
		}
	);

	public static final DeferredEntityType<Tumbleweed> TUMBLEWEED = register(WWEntityTypeIds.TUMBLEWEED,
		Tumbleweed::new,
		WWMobCategories.TUMBLEWEED,
		builder -> builder
			.sized(0.98F, 0.98F)
			.eyeHeight(0.98F * 0.5F) // eye height is the height * 0.5F
			.updateInterval(2),
		entityType -> {
			DefaultAttributeRegistry.register(entityType, Tumbleweed.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Tumbleweed::checkTumbleweedSpawnRules
			);
		}
	);

	public static final DeferredEntityType<Crab> CRAB = register(WWEntityTypeIds.CRAB,
		Crab::new,
		WWMobCategories.CRAB,
		builder -> builder
			.sized(0.5F, 0.5F)
			.eyeHeight(0.5F * 0.65F), // eye height is the height * 0.65F
		entityType -> {
			DefaultAttributeRegistry.register(entityType, Crab.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.IN_WATER,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Crab::checkCrabSpawnRules
			);
		}
	);

	public static final DeferredEntityType<Ostrich> OSTRICH = register(WWEntityTypeIds.OSTRICH,
		Ostrich::new,
		MobCategory.CREATURE,
		builder -> builder
			.sized(1.1F, 2.3F)
			.eyeHeight(2.3F) // eye height is hitbox height
			.clientTrackingRange(10),
		entityType -> {
			DefaultAttributeRegistry.register(entityType, Ostrich.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Ostrich::checkOstrichSpawnRules
			);
		}
	);

	public static final DeferredEntityType<ZombieOstrich> ZOMBIE_OSTRICH = register(WWEntityTypeIds.ZOMBIE_OSTRICH,
		ZombieOstrich::new,
		MobCategory.MONSTER,
		builder -> builder
			.sized(1.1F, 2.3F)
			.eyeHeight(2.3F) // eye height is hitbox height
			.clientTrackingRange(10),
		entityType -> {
			DefaultAttributeRegistry.register(entityType, ZombieOstrich.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				ZombieOstrich::checkZombieOstrichSpawnRules
			);
		}
	);

	public static final DeferredEntityType<Scorched> SCORCHED = register(WWEntityTypeIds.SCORCHED,
		Scorched::new,
		MobCategory.MONSTER,
		builder -> builder
			.sized(1.26F, 0.81F)
			.eyeHeight(0.585F)
			.fireImmune()
			.clientTrackingRange(8)
			.notInPeaceful(),
		entityType -> {
			DefaultAttributeRegistry.register(entityType, Scorched.createAttributes());
			SpawnPlacements.register(
				entityType,
				WWSpawnTypes.ON_GROUND_OR_IN_LAVA,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Scorched::checkScorchedSpawnRules
			);
		}
	);

	public static final DeferredEntityType<FlowerCow> MOOBLOOM = register(WWEntityTypeIds.MOOBLOOM,
		FlowerCow::new,
		MobCategory.CREATURE,
		builder -> builder
			.sized(0.9F, 1.4F)
			.eyeHeight(1.3F)
			.passengerAttachments(1.36875F)
			.clientTrackingRange(10),
		entityType -> {
			DefaultAttributeRegistry.register(entityType, FlowerCow.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				FlowerCow::checkFlowerCowSpawnRules
			);
		}
	);

	public static final DeferredEntityType<Penguin> PENGUIN = register(WWEntityTypeIds.PENGUIN,
		Penguin::new,
		MobCategory.CREATURE,
		builder -> builder
			.sized(0.55F, 1F)
			.eyeHeight(0.8F)
			.clientTrackingRange(10)
			.immuneTo(WWBlockTags.PENGUIN_IMMUNE_TO),
		entityType -> {
			DefaultAttributeRegistry.register(entityType, Penguin.createAttributes());
			SpawnPlacements.register(
				entityType,
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Penguin::checkPenguinSpawnRules
			);
		}
	);

	public static final DeferredEntityType<CoconutProjectile> COCONUT = register(WWEntityTypeIds.COCONUT,
		CoconutProjectile::new,
		MobCategory.MISC,
		builder -> builder
			.sized(0.25F, 0.25F)
			.clientTrackingRange(4)
			.updateInterval(20),
		entityType -> {}
	);

	public static final DeferredEntityType<FallingLeafTicker> FALLING_LEAVES = register(WWEntityTypeIds.FALLING_LEAVES,
		FallingLeafTicker::new,
		MobCategory.MISC,
		builder -> builder
			.sized(0F, 0F)
			.clientTrackingRange(0),
		entityType -> {}
	);

	// BOATS
	public static final DeferredEntityType<Boat> BAOBAB_BOAT = registerBoat(WWEntityTypeIds.BAOBAB_BOAT, WWItems.BAOBAB_BOAT);
	public static final DeferredEntityType<ChestBoat> BAOBAB_CHEST_BOAT = registerChestBoat(WWEntityTypeIds.BAOBAB_CHEST_BOAT, WWItems.BAOBAB_CHEST_BOAT);

	public static final DeferredEntityType<Boat> WILLOW_BOAT = registerBoat(WWEntityTypeIds.WILLOW_BOAT, WWItems.WILLOW_BOAT);
	public static final DeferredEntityType<ChestBoat> WILLOW_CHEST_BOAT = registerChestBoat(WWEntityTypeIds.WILLOW_CHEST_BOAT, WWItems.WILLOW_CHEST_BOAT);

	public static final DeferredEntityType<Boat> CYPRESS_BOAT = registerBoat(WWEntityTypeIds.CYPRESS_BOAT, WWItems.CYPRESS_BOAT);
	public static final DeferredEntityType<ChestBoat> CYPRESS_CHEST_BOAT = registerChestBoat(WWEntityTypeIds.CYPRESS_CHEST_BOAT, WWItems.CYPRESS_CHEST_BOAT);

	public static final DeferredEntityType<Boat> PALM_BOAT = registerBoat(WWEntityTypeIds.PALM_BOAT, WWItems.PALM_BOAT);
	public static final DeferredEntityType<ChestBoat> PALM_CHEST_BOAT = registerChestBoat(WWEntityTypeIds.PALM_CHEST_BOAT, WWItems.PALM_CHEST_BOAT);

	public static final DeferredEntityType<Boat> MAPLE_BOAT = registerBoat(WWEntityTypeIds.MAPLE_BOAT, WWItems.MAPLE_BOAT);
	public static final DeferredEntityType<ChestBoat> MAPLE_CHEST_BOAT = registerChestBoat(WWEntityTypeIds.MAPLE_CHEST_BOAT, WWItems.MAPLE_CHEST_BOAT);

	public static void init() {}

	static {
		REGISTER.register();
	}

	private static <E extends Entity> DeferredEntityType<E> register(
		ResourceKey<EntityType<?>> id,
		EntityType.EntityFactory<E> factory,
		MobCategory category,
		UnaryOperator<EntityType.Builder<E>> builder
	) {
		return REGISTER.register(id, factory, category, builder, null);
	}

	private static <E extends AbstractBoat> DeferredEntityType<E> registerAbstractBoat(ResourceKey<EntityType<?>> id, EntityType.EntityFactory<E> factory) {
		return register(
			id,
			factory,
			MobCategory.MISC,
			builder -> builder
				.noLootTable()
				.sized(1.375F, 0.5625F)
				.eyeHeight(0.5625F)
				.clientTrackingRange(10)
		);
	}

	private static <E extends Boat> DeferredEntityType<E> registerBoat(ResourceKey<EntityType<?>> id, DeferredItem<?> item) {
		return registerAbstractBoat(id, (EntityType.EntityFactory<E>) EntityTypes.boatFactory(() -> item.get()));
	}

	private static <E extends ChestBoat> DeferredEntityType<E> registerChestBoat(ResourceKey<EntityType<?>> id, DeferredItem<?> item) {
		return registerAbstractBoat(id, (EntityType.EntityFactory<E>) EntityTypes.chestBoatFactory(() -> item.get()));
	}

	private static <E extends Entity> DeferredEntityType<E> register(
		ResourceKey<EntityType<?>> id,
		EntityType.EntityFactory<E> factory,
		MobCategory category,
		UnaryOperator<EntityType.Builder<E>> builder,
		Consumer<EntityType<E>> also
	) {
		return REGISTER.register(id, factory, category, builder, also);
	}

	private WWEntityTypes() {}
}
