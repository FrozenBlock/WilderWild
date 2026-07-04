package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.platform.api.registry.FrozenDeferredItem;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.item.CrabClawItem;
import net.frozenblock.wilderwild.item.MobBottleItem;
import net.frozenblock.wilderwild.references.WWBlockItemIds;
import net.frozenblock.wilderwild.references.WWItemIds;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.Fluids;

public final class WWFabricItems {
	private static final FrozenDeferredRegister.Items REGISTER = FrozenDeferredRegister.createItems(
		WWConstants.MOD_ID
	);

	// FLOWERS
	public static final FrozenDeferredItem<BlockItem> SEEDING_DANDELION = REGISTER.registerSimpleBlockItem(WWBlockItemIds.SEEDING_DANDELION, WWFabricBlocks.SEEDING_DANDELION);

	// VEGETATION
	public static final FrozenDeferredItem<BlockItem> TUMBLEWEED_PLANT = REGISTER.registerSimpleBlockItem(WWBlockItemIds.TUMBLEWEED_PLANT, WWFabricBlocks.TUMBLEWEED_PLANT);
	public static final FrozenDeferredItem<SpawnEggItem> TUMBLEWEED = REGISTER.registerItem(
		WWBlockItemIds.TUMBLEWEED.item(),
		SpawnEggItem::new,
		() -> new Item.Properties()
			.useBlockDescriptionPrefix()
			.requiredFeatures(WWFabricBlocks.TUMBLEWEED.get().requiredFeatures())
			.spawnEgg(WWFabricEntityTypes.TUMBLEWEED.get())
	);

	// MUSHROOMS
	public static final FrozenDeferredItem<BlockItem> PALE_MUSHROOM_BLOCK = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PALE_MUSHROOM_BLOCK, WWFabricBlocks.PALE_MUSHROOM_BLOCK);

	// EGGS
	public static final FrozenDeferredItem<BlockItem> OSTRICH_EGG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.OSTRICH_EGG, WWFabricBlocks.OSTRICH_EGG);
	public static final FrozenDeferredItem<BlockItem> PENGUIN_EGG = REGISTER.registerSimpleBlockItem(WWBlockItemIds.PENGUIN_EGG, WWFabricBlocks.PENGUIN_EGG);

	// BOATS
	public static final FrozenDeferredItem<BoatItem> BAOBAB_BOAT = registerBoatItem(WWItemIds.BAOBAB_BOAT, WWFabricEntityTypes.BAOBAB_BOAT.get());
	public static final FrozenDeferredItem<BoatItem> BAOBAB_CHEST_BOAT = registerBoatItem(WWItemIds.BAOBAB_CHEST_BOAT, WWFabricEntityTypes.BAOBAB_CHEST_BOAT.get());
	public static final FrozenDeferredItem<BoatItem> WILLOW_BOAT = registerBoatItem(WWItemIds.WILLOW_BOAT, WWFabricEntityTypes.WILLOW_BOAT.get());
	public static final FrozenDeferredItem<BoatItem> WILLOW_CHEST_BOAT = registerBoatItem(WWItemIds.WILLOW_CHEST_BOAT, WWFabricEntityTypes.WILLOW_CHEST_BOAT.get());
	public static final FrozenDeferredItem<BoatItem> CYPRESS_BOAT = registerBoatItem(WWItemIds.CYPRESS_BOAT, WWFabricEntityTypes.CYPRESS_BOAT.get());
	public static final FrozenDeferredItem<BoatItem> CYPRESS_CHEST_BOAT = registerBoatItem(WWItemIds.CYPRESS_CHEST_BOAT, WWFabricEntityTypes.CYPRESS_CHEST_BOAT.get());
	public static final FrozenDeferredItem<BoatItem> PALM_BOAT = registerBoatItem(WWItemIds.PALM_BOAT, WWFabricEntityTypes.PALM_BOAT.get());
	public static final FrozenDeferredItem<BoatItem> PALM_CHEST_BOAT = registerBoatItem(WWItemIds.PALM_CHEST_BOAT, WWFabricEntityTypes.PALM_CHEST_BOAT.get());
	public static final FrozenDeferredItem<BoatItem> MAPLE_BOAT = registerBoatItem(WWItemIds.MAPLE_BOAT, WWFabricEntityTypes.MAPLE_BOAT.get());
	public static final FrozenDeferredItem<BoatItem> MAPLE_CHEST_BOAT = registerBoatItem(WWItemIds.MAPLE_CHEST_BOAT, WWFabricEntityTypes.MAPLE_CHEST_BOAT.get());

	// ITEMS
	public static final FrozenDeferredItem<MobBottleItem> BUTTERFLY_BOTTLE = REGISTER.registerItem(WWItemIds.BUTTERFLY_BOTTLE,
		properties -> new MobBottleItem(
			WWFabricEntityTypes.BUTTERFLY.get(),
			WWSounds.ITEM_BOTTLE_RELEASE_BUTTERFLY.get(),
			properties
		),
		() -> new Item.Properties().stacksTo(1).component(WWDataComponents.BOTTLE_ENTITY_DATA.get(), CustomData.EMPTY)
	);

	// FOOD
	public static final FrozenDeferredItem<CrabClawItem> CRAB_CLAW = REGISTER.registerItem(WWItemIds.CRAB_CLAW, CrabClawItem::new, () -> new Item.Properties().food(WWFoods.CRAB_CLAW));

	// SPAWN EGGS & BUCKETS
	public static final FrozenDeferredItem<MobBucketItem> CRAB_BUCKET = REGISTER.registerItem(
		WWItemIds.CRAB_BUCKET,
		properties -> new MobBucketItem(WWFabricEntityTypes.CRAB.get(), Fluids.WATER, WWSounds.ITEM_BUCKET_EMPTY_CRAB.get(), properties),
		() -> new Item.Properties().stacksTo(1).component(DataComponents.FOOD, WWFoods.CRAB_CLAW)
	);

	public static final FrozenDeferredItem<SpawnEggItem> CRAB_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.CRAB_SPAWN_EGG, WWFabricEntityTypes.CRAB::get);
	public static final FrozenDeferredItem<SpawnEggItem> OSTRICH_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.OSTRICH_SPAWN_EGG, WWFabricEntityTypes.OSTRICH::get);
	public static final FrozenDeferredItem<SpawnEggItem> ZOMBIE_OSTRICH_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.ZOMBIE_OSTRICH_SPAWN_EGG, WWFabricEntityTypes.ZOMBIE_OSTRICH::get);
	public static final FrozenDeferredItem<SpawnEggItem> SCORCHED_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.SCORCHED_SPAWN_EGG, WWFabricEntityTypes.SCORCHED::get);
	public static final FrozenDeferredItem<SpawnEggItem> BUTTERFLY_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.BUTTERFLY_SPAWN_EGG, WWFabricEntityTypes.BUTTERFLY::get);
	public static final FrozenDeferredItem<SpawnEggItem> MOOBLOOM_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.MOOBLOOM_SPAWN_EGG, WWFabricEntityTypes.MOOBLOOM::get);
	public static final FrozenDeferredItem<SpawnEggItem> PENGUIN_SPAWN_EGG = REGISTER.registerSpawnEgg(WWItemIds.PENGUIN_SPAWN_EGG, WWFabricEntityTypes.PENGUIN::get);

	public static void init() {
		Item.BY_BLOCK.put(WWFabricBlocks.TUMBLEWEED.get(), TUMBLEWEED.get());

		DispenserBlock.registerBehavior(BAOBAB_BOAT.get(), new BoatDispenseItemBehavior(WWFabricEntityTypes.BAOBAB_BOAT.get()));
		DispenserBlock.registerBehavior(BAOBAB_CHEST_BOAT.get(), new BoatDispenseItemBehavior(WWFabricEntityTypes.BAOBAB_CHEST_BOAT.get()));
		DispenserBlock.registerBehavior(WILLOW_BOAT.get(), new BoatDispenseItemBehavior(WWFabricEntityTypes.WILLOW_BOAT.get()));
		DispenserBlock.registerBehavior(WILLOW_CHEST_BOAT.get(), new BoatDispenseItemBehavior(WWFabricEntityTypes.WILLOW_CHEST_BOAT.get()));
		DispenserBlock.registerBehavior(CYPRESS_BOAT.get(), new BoatDispenseItemBehavior(WWFabricEntityTypes.CYPRESS_BOAT.get()));
		DispenserBlock.registerBehavior(CYPRESS_CHEST_BOAT.get(), new BoatDispenseItemBehavior(WWFabricEntityTypes.CYPRESS_CHEST_BOAT.get()));
		DispenserBlock.registerBehavior(PALM_BOAT.get(), new BoatDispenseItemBehavior(WWFabricEntityTypes.PALM_BOAT.get()));
		DispenserBlock.registerBehavior(PALM_CHEST_BOAT.get(), new BoatDispenseItemBehavior(WWFabricEntityTypes.PALM_CHEST_BOAT.get()));
		DispenserBlock.registerBehavior(MAPLE_BOAT.get(), new BoatDispenseItemBehavior(WWFabricEntityTypes.MAPLE_BOAT.get()));
		DispenserBlock.registerBehavior(MAPLE_CHEST_BOAT.get(), new BoatDispenseItemBehavior(WWFabricEntityTypes.MAPLE_CHEST_BOAT.get()));
	}

	private static FrozenDeferredItem<BoatItem> registerBoatItem(ResourceKey<Item> id, EntityType<? extends AbstractBoat> boat) {
		return REGISTER.registerItem(id, properties -> new BoatItem(boat, properties), () -> new Item.Properties().stacksTo(1));
	}

	static {
		REGISTER.register();
	}
}
