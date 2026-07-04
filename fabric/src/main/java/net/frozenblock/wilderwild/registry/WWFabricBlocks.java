package net.frozenblock.wilderwild.registry;

import java.util.Optional;
import java.util.function.Supplier;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.registry.CompostableRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelValueEvents;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.frozenblock.lib.block.storage.api.NoInteractionStorage;
import net.frozenblock.lib.block.storage.api.hopper.HopperApi;
import net.frozenblock.lib.item.api.bonemeal.BoneMealApi;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredBlock;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.DisplayLanternBlock;
import net.frozenblock.wilderwild.block.GeothermalVentBlock;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.block.HollowedLogBlock;
import net.frozenblock.wilderwild.block.HugePaleMushroomBlock;
import net.frozenblock.wilderwild.block.IcicleBlock;
import net.frozenblock.wilderwild.block.OstrichEggBlock;
import net.frozenblock.wilderwild.block.PenguinEggBlock;
import net.frozenblock.wilderwild.block.ScorchedBlock;
import net.frozenblock.wilderwild.block.SeedingFlowerBlock;
import net.frozenblock.wilderwild.block.StoneChestBlock;
import net.frozenblock.wilderwild.block.TermiteMoundBlock;
import net.frozenblock.wilderwild.block.TumbleweedBlock;
import net.frozenblock.wilderwild.block.TumbleweedPlantBlock;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.data.worldgen.feature.placed.WWMiscPlaced;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.references.WWBlockIds;
import net.frozenblock.wilderwild.references.WWBlockItemIds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import static net.frozenblock.wilderwild.registry.WWBlocks.*;

public final class WWFabricBlocks {
	private static final FrozenDeferredRegister.Blocks REGISTER = FrozenDeferredRegister.createBlocks(
		WWConstants.MOD_ID
	);

	// FLOWERS
	public static final FrozenDeferredBlock<SeedingFlowerBlock> SEEDING_DANDELION = REGISTER.registerBlock(WWBlockItemIds.SEEDING_DANDELION.block(),
		properties -> new SeedingFlowerBlock(MobEffects.SLOW_FALLING, 12, Blocks.DANDELION, properties),
		() -> Properties.ofFullCopy(Blocks.DANDELION)
	);
	public static final FrozenDeferredBlock<Block> POTTED_SEEDING_DANDELION = registerFlowerPot(WWBlockIds.POTTED_SEEDING_DANDELION, SEEDING_DANDELION);

	// VEGETATION
	public static final FrozenDeferredBlock<TumbleweedPlantBlock> TUMBLEWEED_PLANT = REGISTER.registerBlock(WWBlockItemIds.TUMBLEWEED_PLANT.block(),
		TumbleweedPlantBlock::new,
		() -> Properties.of()
			.noOcclusion()
			.sound(WWSoundTypes.TUMBLEWEED_PLANT)
			.randomTicks()
	);
	public static final FrozenDeferredBlock<Block> POTTED_TUMBLEWEED_PLANT = registerFlowerPot(WWBlockIds.POTTED_TUMBLEWEED_PLANT, TUMBLEWEED_PLANT);

	public static final FrozenDeferredBlock<TumbleweedBlock> TUMBLEWEED = REGISTER.registerBlock(WWBlockItemIds.TUMBLEWEED.block(),
		TumbleweedBlock::new,
		() -> Properties.of()
			.instabreak()
			.noOcclusion()
			.sound(WWSoundTypes.TUMBLEWEED_PLANT)
			.randomTicks()
	);
	public static final FrozenDeferredBlock<Block> POTTED_TUMBLEWEED = registerFlowerPot(WWBlockIds.POTTED_TUMBLEWEED, TUMBLEWEED);

	// MUSHROOMS
	public static final FrozenDeferredBlock<HugePaleMushroomBlock> PALE_MUSHROOM_BLOCK = REGISTER.registerBlock(WWBlockItemIds.PALE_MUSHROOM_BLOCK.block(),
		HugePaleMushroomBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.COLOR_GRAY)
			.instrument(NoteBlockInstrument.BASS)
			.strength(0.2F)
			.sound(SoundType.WOOD)
			.ignitedByLava()
	);

	// EGGS
	public static final FrozenDeferredBlock<OstrichEggBlock> OSTRICH_EGG = REGISTER.registerBlock(WWBlockItemIds.OSTRICH_EGG.block(),
		OstrichEggBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.strength(0.5F)
			.sound(SoundType.METAL)
			.noOcclusion()
			.randomTicks()
	);
	public static final FrozenDeferredBlock<PenguinEggBlock> PENGUIN_EGG = REGISTER.registerBlock(WWBlockItemIds.PENGUIN_EGG.block(),
		PenguinEggBlock::new,
		() -> Properties.of()
			.mapColor(MapColor.TERRACOTTA_WHITE)
			.strength(0.5F)
			.sound(SoundType.METAL)
			.noOcclusion()
			.randomTicks()
	);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static FrozenDeferredBlock<Block> registerFlowerPot(ResourceKey<Block> id, Supplier<? extends Block> potted) {
		return REGISTER.registerBlock(id, properties -> new FlowerPotBlock(potted.get(), properties), Blocks::flowerPotProperties);
	}

	public static void registerBlockProperties() {
		registerDispenses();

		var sign = BlockEntityTypes.SIGN;
		sign.addValidBlock(BAOBAB_SIGN.get());
		sign.addValidBlock(BAOBAB_WALL_SIGN.get());
		sign.addValidBlock(WILLOW_SIGN.get());
		sign.addValidBlock(WILLOW_WALL_SIGN.get());
		sign.addValidBlock(CYPRESS_SIGN.get());
		sign.addValidBlock(CYPRESS_WALL_SIGN.get());
		sign.addValidBlock(PALM_SIGN.get());
		sign.addValidBlock(PALM_WALL_SIGN.get());
		sign.addValidBlock(MAPLE_SIGN.get());
		sign.addValidBlock(MAPLE_WALL_SIGN.get());

		var hangingSign = BlockEntityTypes.HANGING_SIGN;
		hangingSign.addValidBlock(BAOBAB_HANGING_SIGN.get());
		hangingSign.addValidBlock(BAOBAB_WALL_HANGING_SIGN.get());
		hangingSign.addValidBlock(WILLOW_HANGING_SIGN.get());
		hangingSign.addValidBlock(WILLOW_WALL_HANGING_SIGN.get());
		hangingSign.addValidBlock(CYPRESS_HANGING_SIGN.get());
		hangingSign.addValidBlock(CYPRESS_WALL_HANGING_SIGN.get());
		hangingSign.addValidBlock(PALM_HANGING_SIGN.get());
		hangingSign.addValidBlock(PALM_WALL_HANGING_SIGN.get());
		hangingSign.addValidBlock(MAPLE_HANGING_SIGN.get());
		hangingSign.addValidBlock(MAPLE_WALL_HANGING_SIGN.get());

		var shelf = BlockEntityTypes.SHELF;
		shelf.addValidBlock(BAOBAB_SHELF.get());
		shelf.addValidBlock(WILLOW_SHELF.get());
		shelf.addValidBlock(CYPRESS_SHELF.get());
		shelf.addValidBlock(PALM_SHELF.get());
		shelf.addValidBlock(MAPLE_SHELF.get());

		registerStrippable();
		registerComposting();
		registerFlammability();
		registerFuels();
		registerBonemeal();
		registerAxe();
		registerInventories();
	}

	private static void registerDispenses() {
		DispenserBlock.registerBehavior(TUMBLEWEED, new DefaultDispenseItemBehavior() {
			@Override
			public ItemStack execute(BlockSource source, ItemStack stack) {
				final Level level = source.level();
				final Direction direction = source.state().getValue(DispenserBlock.FACING);
				final Vec3 position = source.center().add(direction.getStepX(), direction.getStepY(), direction.getStepZ());
				final Tumbleweed tumbleweed = new Tumbleweed(WWFabricEntityTypes.TUMBLEWEED.get(), level);
				final Vec3 vec3 = new Vec3(direction.getStepX(), direction.getStepY() + 0.1D, direction.getStepZ())
					.normalize()
					.add(
						level.getRandom().triangle(0D, 0.0172275D * 6D),
						level.getRandom().triangle(0D, 0.0172275D * 6D),
						level.getRandom().triangle(0D, 0.0172275D * 6D)
					).scale(1.1D);
				tumbleweed.setDeltaMovement(vec3);
				tumbleweed.setPos(position);
				level.addFreshEntity(tumbleweed);
				stack.shrink(1);
				return stack;
			}
		});
	}

	private static void registerStrippable() {
		StrippableBlockRegistry.register(BAOBAB_LOG.get(), STRIPPED_BAOBAB_LOG.get());
		StrippableBlockRegistry.register(BAOBAB_WOOD.get(), STRIPPED_BAOBAB_WOOD.get());
		StrippableBlockRegistry.register(WILLOW_LOG.get(), STRIPPED_WILLOW_LOG.get());
		StrippableBlockRegistry.register(WILLOW_WOOD.get(), STRIPPED_WILLOW_WOOD.get());
		StrippableBlockRegistry.register(CYPRESS_LOG.get(), STRIPPED_CYPRESS_LOG.get());
		StrippableBlockRegistry.register(CYPRESS_WOOD.get(), STRIPPED_CYPRESS_WOOD.get());
		StrippableBlockRegistry.register(PALM_LOG.get(), STRIPPED_PALM_LOG.get());
		StrippableBlockRegistry.register(PALM_WOOD.get(), STRIPPED_PALM_WOOD.get());
		StrippableBlockRegistry.register(MAPLE_LOG.get(), STRIPPED_MAPLE_LOG.get());
		StrippableBlockRegistry.register(MAPLE_WOOD.get(), STRIPPED_MAPLE_WOOD.get());

		StrippableBlockRegistry.register(HOLLOWED_ACACIA_LOG.get(), STRIPPED_HOLLOWED_ACACIA_LOG.get());
		StrippableBlockRegistry.register(HOLLOWED_BIRCH_LOG.get(), STRIPPED_HOLLOWED_BIRCH_LOG.get());
		StrippableBlockRegistry.register(HOLLOWED_CHERRY_LOG.get(), STRIPPED_HOLLOWED_CHERRY_LOG.get());
		StrippableBlockRegistry.register(HOLLOWED_DARK_OAK_LOG.get(), STRIPPED_HOLLOWED_DARK_OAK_LOG.get());
		StrippableBlockRegistry.register(HOLLOWED_OAK_LOG.get(), STRIPPED_HOLLOWED_OAK_LOG.get());
		StrippableBlockRegistry.register(HOLLOWED_SPRUCE_LOG.get(), STRIPPED_HOLLOWED_SPRUCE_LOG.get());
		StrippableBlockRegistry.register(HOLLOWED_JUNGLE_LOG.get(), STRIPPED_HOLLOWED_JUNGLE_LOG.get());
		StrippableBlockRegistry.register(HOLLOWED_MANGROVE_LOG.get(), STRIPPED_HOLLOWED_MANGROVE_LOG.get());
		StrippableBlockRegistry.register(HOLLOWED_PALE_OAK_LOG.get(), STRIPPED_HOLLOWED_PALE_OAK_LOG.get());
		StrippableBlockRegistry.register(HOLLOWED_CRIMSON_STEM.get(), STRIPPED_HOLLOWED_CRIMSON_STEM.get());
		StrippableBlockRegistry.register(HOLLOWED_WARPED_STEM.get(), STRIPPED_HOLLOWED_WARPED_STEM.get());
		StrippableBlockRegistry.register(HOLLOWED_WILLOW_LOG.get(), STRIPPED_HOLLOWED_WILLOW_LOG.get());
		StrippableBlockRegistry.register(HOLLOWED_CYPRESS_LOG.get(), STRIPPED_HOLLOWED_CYPRESS_LOG.get());
		StrippableBlockRegistry.register(HOLLOWED_BAOBAB_LOG.get(), STRIPPED_HOLLOWED_BAOBAB_LOG.get());
		StrippableBlockRegistry.register(HOLLOWED_PALM_LOG.get(), STRIPPED_HOLLOWED_PALM_LOG.get());
		StrippableBlockRegistry.register(HOLLOWED_MAPLE_LOG.get(), STRIPPED_HOLLOWED_MAPLE_LOG.get());
	}

	private static void registerComposting() {
		CompostableRegistry.INSTANCE.add(CARNATION, 0.65F);
		CompostableRegistry.INSTANCE.add(CATTAIL, 0.65F);
		CompostableRegistry.INSTANCE.add(DATURA, 0.65F);
		CompostableRegistry.INSTANCE.add(MILKWEED, 0.65F);
		CompostableRegistry.INSTANCE.add(WWItems.MILKWEED_POD, 0.25F);
		CompostableRegistry.INSTANCE.add(MARIGOLD, 0.3F);
		CompostableRegistry.INSTANCE.add(LANTANAS, 0.3F);
		CompostableRegistry.INSTANCE.add(PHLOX, 0.3F);
		CompostableRegistry.INSTANCE.add(SEEDING_DANDELION, 0.65F);
		CompostableRegistry.INSTANCE.add(FLOWERING_LILY_PAD, 0.65F);
		CompostableRegistry.INSTANCE.add(BROWN_SHELF_FUNGI, 0.65F);
		CompostableRegistry.INSTANCE.add(RED_SHELF_FUNGI, 0.65F);
		CompostableRegistry.INSTANCE.add(WILLOW_LEAVES, 0.3F);
		CompostableRegistry.INSTANCE.add(CYPRESS_LEAVES, 0.3F);
		CompostableRegistry.INSTANCE.add(BAOBAB_LEAVES, 0.3F);
		CompostableRegistry.INSTANCE.add(PALM_FRONDS, 0.3F);
		CompostableRegistry.INSTANCE.add(YELLOW_MAPLE_LEAVES, 0.3F);
		CompostableRegistry.INSTANCE.add(ORANGE_MAPLE_LEAVES, 0.3F);
		CompostableRegistry.INSTANCE.add(RED_MAPLE_LEAVES, 0.3F);
		CompostableRegistry.INSTANCE.add(WILLOW_SAPLING, 0.3F);
		CompostableRegistry.INSTANCE.add(CYPRESS_SAPLING, 0.3F);
		CompostableRegistry.INSTANCE.add(BAOBAB_NUT, 0.3F);
		CompostableRegistry.INSTANCE.add(YELLOW_MAPLE_SAPLING, 0.3F);
		CompostableRegistry.INSTANCE.add(ORANGE_MAPLE_SAPLING, 0.3F);
		CompostableRegistry.INSTANCE.add(RED_MAPLE_SAPLING, 0.3F);
		CompostableRegistry.INSTANCE.add(WWItems.COCONUT, 0.65F);
		CompostableRegistry.INSTANCE.add(WWItems.SPLIT_COCONUT, 0.3F);
		CompostableRegistry.INSTANCE.add(RED_HIBISCUS, 0.65F);
		CompostableRegistry.INSTANCE.add(YELLOW_HIBISCUS, 0.65F);
		CompostableRegistry.INSTANCE.add(WHITE_HIBISCUS, 0.65F);
		CompostableRegistry.INSTANCE.add(PINK_HIBISCUS, 0.65F);
		CompostableRegistry.INSTANCE.add(PURPLE_HIBISCUS, 0.65F);
		CompostableRegistry.INSTANCE.add(ALGAE, 0.3F);
		CompostableRegistry.INSTANCE.add(WWBlocks.PLANKTON, 0.3F);
		CompostableRegistry.INSTANCE.add(MYCELIUM_GROWTH, 0.3F);
		CompostableRegistry.INSTANCE.add(SHRUB, 0.65F);
		CompostableRegistry.INSTANCE.add(TUMBLEWEED_PLANT, 0.5F);
		CompostableRegistry.INSTANCE.add(TUMBLEWEED, 0.3F);
		CompostableRegistry.INSTANCE.add(WWItems.PRICKLY_PEAR, 0.5F);
		CompostableRegistry.INSTANCE.add(WWItems.PEELED_PRICKLY_PEAR, 0.5F);
		CompostableRegistry.INSTANCE.add(ACACIA_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(AZALEA_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(BAOBAB_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(BIRCH_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(CHERRY_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(CYPRESS_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(DARK_OAK_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(JUNGLE_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(MANGROVE_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(PALE_OAK_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(PALM_FROND_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(SPRUCE_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(WILLOW_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(YELLOW_MAPLE_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(ORANGE_MAPLE_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(RED_MAPLE_LEAF_LITTER, 0.3F);
		CompostableRegistry.INSTANCE.add(CLOVERS, 0.3F);
		CompostableRegistry.INSTANCE.add(FROZEN_SHORT_GRASS, 0.3F);
		CompostableRegistry.INSTANCE.add(FROZEN_TALL_GRASS, 0.5F);
		CompostableRegistry.INSTANCE.add(FROZEN_FERN, 0.65F);
		CompostableRegistry.INSTANCE.add(FROZEN_LARGE_FERN, 0.65F);
		CompostableRegistry.INSTANCE.add(FROZEN_BUSH, 0.3F);
		CompostableRegistry.INSTANCE.add(AUBURN_MOSS_BLOCK, 0.65F);
		CompostableRegistry.INSTANCE.add(AUBURN_MOSS_CARPET, 0.3F);
		CompostableRegistry.INSTANCE.add(AUBURN_CREEPING_MOSS, 0.3F);
	}

	private static void registerFlammability() {
		final var flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();
		flammableBlockRegistry.add(POLLEN.get(), 60, 100);
		flammableBlockRegistry.add(SEEDING_DANDELION.get(), 60, 100);
		flammableBlockRegistry.add(CARNATION.get(), 60, 100);
		flammableBlockRegistry.add(CATTAIL.get(), 60, 100);
		flammableBlockRegistry.add(DATURA.get(), 60, 100);
		flammableBlockRegistry.add(MILKWEED.get(), 60, 100);
		flammableBlockRegistry.add(MARIGOLD.get(), 60, 100);
		flammableBlockRegistry.add(RED_HIBISCUS.get(), 60, 100);
		flammableBlockRegistry.add(YELLOW_HIBISCUS.get(), 60, 100);
		flammableBlockRegistry.add(WHITE_HIBISCUS.get(), 60, 100);
		flammableBlockRegistry.add(PINK_HIBISCUS.get(), 60, 100);
		flammableBlockRegistry.add(PURPLE_HIBISCUS.get(), 60, 100);
		flammableBlockRegistry.add(TUMBLEWEED.get(), 60, 100);
		flammableBlockRegistry.add(TUMBLEWEED_PLANT.get(), 60, 100);
		flammableBlockRegistry.add(SHRUB.get(), 40, 90);
		flammableBlockRegistry.add(MYCELIUM_GROWTH.get(), 60, 100);
		flammableBlockRegistry.add(LANTANAS.get(), 60, 100);
		flammableBlockRegistry.add(PHLOX.get(), 60, 100);
		flammableBlockRegistry.add(CLOVERS.get(), 60, 100);

		flammableBlockRegistry.add(FROZEN_SHORT_GRASS.get(), 60, 100);
		flammableBlockRegistry.add(FROZEN_TALL_GRASS.get(), 60, 100);
		flammableBlockRegistry.add(FROZEN_FERN.get(), 60, 100);
		flammableBlockRegistry.add(FROZEN_LARGE_FERN.get(), 60, 100);
		flammableBlockRegistry.add(FROZEN_BUSH.get(), 60, 100);

		flammableBlockRegistry.add(HOLLOWED_BIRCH_LOG.get(), 5, 5);
		flammableBlockRegistry.add(HOLLOWED_CHERRY_LOG.get(), 5, 5);
		flammableBlockRegistry.add(HOLLOWED_OAK_LOG.get(), 5, 5);
		flammableBlockRegistry.add(HOLLOWED_ACACIA_LOG.get(), 5, 5);
		flammableBlockRegistry.add(HOLLOWED_JUNGLE_LOG.get(), 5, 5);
		flammableBlockRegistry.add(HOLLOWED_DARK_OAK_LOG.get(), 5, 5);
		flammableBlockRegistry.add(HOLLOWED_MANGROVE_LOG.get(), 5, 5);
		flammableBlockRegistry.add(HOLLOWED_CHERRY_LOG.get(), 5, 5);
		flammableBlockRegistry.add(HOLLOWED_SPRUCE_LOG.get(), 5, 5);
		flammableBlockRegistry.add(HOLLOWED_PALE_OAK_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_BIRCH_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_CHERRY_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_OAK_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_ACACIA_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_JUNGLE_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_DARK_OAK_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_MANGROVE_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_SPRUCE_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_PALE_OAK_LOG.get(), 5, 5);

		flammableBlockRegistry.add(HOLLOWED_BAOBAB_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_BAOBAB_LOG.get(), 5, 5);
		flammableBlockRegistry.add(BAOBAB_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_BAOBAB_LOG.get(), 5, 5);
		flammableBlockRegistry.add(BAOBAB_WOOD.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_BAOBAB_WOOD.get(), 5, 5);
		flammableBlockRegistry.add(BAOBAB_PLANKS.get(), 5, 20);
		flammableBlockRegistry.add(BAOBAB_STAIRS.get(), 5, 20);
		flammableBlockRegistry.add(BAOBAB_DOOR.get(), 5, 20);
		flammableBlockRegistry.add(BAOBAB_FENCE.get(), 5, 20);
		flammableBlockRegistry.add(BAOBAB_SLAB.get(), 5, 20);
		flammableBlockRegistry.add(BAOBAB_FENCE_GATE.get(), 5, 20);
		flammableBlockRegistry.add(BAOBAB_PRESSURE_PLATE.get(), 5, 20);
		flammableBlockRegistry.add(BAOBAB_TRAPDOOR.get(), 5, 20);
		flammableBlockRegistry.add(BAOBAB_LEAVES.get(), 30, 60);
		flammableBlockRegistry.add(BAOBAB_BUTTON.get(), 5, 20);
		flammableBlockRegistry.add(BAOBAB_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(BAOBAB_WALL_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(BAOBAB_HANGING_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(BAOBAB_WALL_HANGING_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(BAOBAB_SHELF.get(), 30, 20);

		flammableBlockRegistry.add(HOLLOWED_WILLOW_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_WILLOW_LOG.get(), 5, 5);
		flammableBlockRegistry.add(WILLOW_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_WILLOW_LOG.get(), 5, 5);
		flammableBlockRegistry.add(WILLOW_WOOD.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_WILLOW_WOOD.get(), 5, 5);
		flammableBlockRegistry.add(WILLOW_PLANKS.get(), 5, 20);
		flammableBlockRegistry.add(WILLOW_STAIRS.get(), 5, 20);
		flammableBlockRegistry.add(WILLOW_DOOR.get(), 5, 20);
		flammableBlockRegistry.add(WILLOW_FENCE.get(), 5, 20);
		flammableBlockRegistry.add(WILLOW_SLAB.get(), 5, 20);
		flammableBlockRegistry.add(WILLOW_FENCE_GATE.get(), 5, 20);
		flammableBlockRegistry.add(WILLOW_PRESSURE_PLATE.get(), 5, 20);
		flammableBlockRegistry.add(WILLOW_TRAPDOOR.get(), 5, 20);
		flammableBlockRegistry.add(WILLOW_LEAVES.get(), 30, 60);
		flammableBlockRegistry.add(WILLOW_BUTTON.get(), 5, 20);
		flammableBlockRegistry.add(WILLOW_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(WILLOW_WALL_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(WILLOW_HANGING_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(WILLOW_WALL_HANGING_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(WILLOW_SHELF.get(), 30, 20);

		flammableBlockRegistry.add(HOLLOWED_CYPRESS_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_CYPRESS_LOG.get(), 5, 5);
		flammableBlockRegistry.add(CYPRESS_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_CYPRESS_LOG.get(), 5, 5);
		flammableBlockRegistry.add(CYPRESS_WOOD.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_CYPRESS_WOOD.get(), 5, 5);
		flammableBlockRegistry.add(CYPRESS_PLANKS.get(), 5, 20);
		flammableBlockRegistry.add(CYPRESS_STAIRS.get(), 5, 20);
		flammableBlockRegistry.add(CYPRESS_DOOR.get(), 5, 20);
		flammableBlockRegistry.add(CYPRESS_FENCE.get(), 5, 20);
		flammableBlockRegistry.add(CYPRESS_SLAB.get(), 5, 20);
		flammableBlockRegistry.add(CYPRESS_FENCE_GATE.get(), 5, 20);
		flammableBlockRegistry.add(CYPRESS_PRESSURE_PLATE.get(), 5, 20);
		flammableBlockRegistry.add(CYPRESS_TRAPDOOR.get(), 5, 20);
		flammableBlockRegistry.add(CYPRESS_LEAVES.get(), 30, 60);
		flammableBlockRegistry.add(CYPRESS_BUTTON.get(), 5, 20);
		flammableBlockRegistry.add(CYPRESS_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(CYPRESS_WALL_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(CYPRESS_HANGING_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(CYPRESS_WALL_HANGING_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(CYPRESS_SHELF.get(), 30, 20);

		flammableBlockRegistry.add(HOLLOWED_PALM_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_PALM_LOG.get(), 5, 5);
		flammableBlockRegistry.add(PALM_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_PALM_LOG.get(), 5, 5);
		flammableBlockRegistry.add(PALM_WOOD.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_PALM_WOOD.get(), 5, 5);
		flammableBlockRegistry.add(PALM_PLANKS.get(), 5, 20);
		flammableBlockRegistry.add(PALM_STAIRS.get(), 5, 20);
		flammableBlockRegistry.add(PALM_DOOR.get(), 5, 20);
		flammableBlockRegistry.add(PALM_FENCE.get(), 5, 20);
		flammableBlockRegistry.add(PALM_SLAB.get(), 5, 20);
		flammableBlockRegistry.add(PALM_FENCE_GATE.get(), 5, 20);
		flammableBlockRegistry.add(PALM_PRESSURE_PLATE.get(), 5, 20);
		flammableBlockRegistry.add(PALM_TRAPDOOR.get(), 5, 20);
		flammableBlockRegistry.add(PALM_FRONDS.get(), 30, 60);
		flammableBlockRegistry.add(PALM_BUTTON.get(), 5, 20);
		flammableBlockRegistry.add(PALM_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(PALM_WALL_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(PALM_HANGING_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(PALM_WALL_HANGING_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(PALM_SHELF.get(), 30, 20);

		flammableBlockRegistry.add(HOLLOWED_MAPLE_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_HOLLOWED_MAPLE_LOG.get(), 5, 5);
		flammableBlockRegistry.add(MAPLE_LOG.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_MAPLE_LOG.get(), 5, 5);
		flammableBlockRegistry.add(MAPLE_WOOD.get(), 5, 5);
		flammableBlockRegistry.add(STRIPPED_MAPLE_WOOD.get(), 5, 5);
		flammableBlockRegistry.add(MAPLE_PLANKS.get(), 5, 20);
		flammableBlockRegistry.add(MAPLE_STAIRS.get(), 5, 20);
		flammableBlockRegistry.add(MAPLE_DOOR.get(), 5, 20);
		flammableBlockRegistry.add(MAPLE_FENCE.get(), 5, 20);
		flammableBlockRegistry.add(MAPLE_SLAB.get(), 5, 20);
		flammableBlockRegistry.add(MAPLE_FENCE_GATE.get(), 5, 20);
		flammableBlockRegistry.add(MAPLE_PRESSURE_PLATE.get(), 5, 20);
		flammableBlockRegistry.add(MAPLE_TRAPDOOR.get(), 5, 20);
		flammableBlockRegistry.add(YELLOW_MAPLE_LEAVES.get(), 30, 60);
		flammableBlockRegistry.add(ORANGE_MAPLE_LEAVES.get(), 30, 60);
		flammableBlockRegistry.add(RED_MAPLE_LEAVES.get(), 30, 60);
		flammableBlockRegistry.add(YELLOW_MAPLE_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(ORANGE_MAPLE_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(RED_MAPLE_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(MAPLE_BUTTON.get(), 5, 20);
		flammableBlockRegistry.add(MAPLE_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(MAPLE_WALL_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(MAPLE_HANGING_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(MAPLE_WALL_HANGING_SIGN.get(), 5, 20);
		flammableBlockRegistry.add(MAPLE_SHELF.get(), 30, 20);

		flammableBlockRegistry.add(ACACIA_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(AZALEA_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(BAOBAB_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(BIRCH_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(CHERRY_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(CYPRESS_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(DARK_OAK_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(JUNGLE_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(MANGROVE_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(PALE_OAK_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(PALM_FROND_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(SPRUCE_LEAF_LITTER.get(), 60, 100);
		flammableBlockRegistry.add(WILLOW_LEAF_LITTER.get(), 60, 100);
	}

	private static void registerFuels() {
		FuelValueEvents.BUILD.register((builder, context) -> {
			builder.add(WWFabricItems.BAOBAB_BOAT, 1200);
			builder.add(WWFabricItems.BAOBAB_CHEST_BOAT, 1200);
			builder.add(BAOBAB_LOG.asItem(), 300);
			builder.add(STRIPPED_BAOBAB_LOG.asItem(), 300);
			builder.add(BAOBAB_WOOD.asItem(), 300);
			builder.add(STRIPPED_BAOBAB_WOOD.asItem(), 300);
			builder.add(BAOBAB_PLANKS.asItem(), 300);
			builder.add(BAOBAB_SLAB.asItem(), 150);
			builder.add(BAOBAB_STAIRS.asItem(), 300);
			builder.add(BAOBAB_PRESSURE_PLATE.asItem(), 300);
			builder.add(BAOBAB_BUTTON.asItem(), 100);
			builder.add(BAOBAB_TRAPDOOR.asItem(), 300);
			builder.add(BAOBAB_FENCE_GATE.asItem(), 300);
			builder.add(BAOBAB_FENCE.asItem(), 300);
			builder.add(WWItems.BAOBAB_SIGN, 300);
			builder.add(WWItems.BAOBAB_HANGING_SIGN, 800);
			builder.add(WWItems.BAOBAB_NUT, 100);

			builder.add(WWFabricItems.WILLOW_BOAT, 1200);
			builder.add(WWFabricItems.WILLOW_CHEST_BOAT, 1200);
			builder.add(WILLOW_LOG.asItem(), 300);
			builder.add(STRIPPED_WILLOW_LOG.asItem(), 300);
			builder.add(WILLOW_WOOD.asItem(), 300);
			builder.add(STRIPPED_WILLOW_WOOD.asItem(), 300);
			builder.add(WILLOW_PLANKS.asItem(), 300);
			builder.add(WILLOW_SLAB.asItem(), 150);
			builder.add(WILLOW_STAIRS.asItem(), 300);
			builder.add(WILLOW_PRESSURE_PLATE.asItem(), 300);
			builder.add(WILLOW_BUTTON.asItem(), 100);
			builder.add(WILLOW_TRAPDOOR.asItem(), 300);
			builder.add(WILLOW_FENCE_GATE.asItem(), 300);
			builder.add(WILLOW_FENCE.asItem(), 300);
			builder.add(WWItems.WILLOW_SIGN, 300);
			builder.add(WWItems.WILLOW_HANGING_SIGN, 800);
			builder.add(WILLOW_SAPLING.asItem(), 100);

			builder.add(WWFabricItems.CYPRESS_BOAT, 1200);
			builder.add(WWFabricItems.CYPRESS_CHEST_BOAT, 1200);
			builder.add(CYPRESS_LOG.asItem(), 300);
			builder.add(STRIPPED_CYPRESS_LOG.asItem(), 300);
			builder.add(CYPRESS_WOOD.asItem(), 300);
			builder.add(STRIPPED_CYPRESS_WOOD.asItem(), 300);
			builder.add(CYPRESS_PLANKS.asItem(), 300);
			builder.add(CYPRESS_SLAB.asItem(), 150);
			builder.add(CYPRESS_STAIRS.asItem(), 300);
			builder.add(CYPRESS_PRESSURE_PLATE.asItem(), 300);
			builder.add(CYPRESS_BUTTON.asItem(), 100);
			builder.add(CYPRESS_TRAPDOOR.asItem(), 300);
			builder.add(CYPRESS_FENCE_GATE.asItem(), 300);
			builder.add(CYPRESS_FENCE.asItem(), 300);
			builder.add(WWItems.CYPRESS_SIGN, 300);
			builder.add(WWItems.CYPRESS_HANGING_SIGN, 800);
			builder.add(CYPRESS_SAPLING.asItem(), 100);

			builder.add(WWFabricItems.PALM_BOAT, 1200);
			builder.add(WWFabricItems.PALM_CHEST_BOAT, 1200);
			builder.add(PALM_LOG.asItem(), 300);
			builder.add(STRIPPED_PALM_LOG.asItem(), 300);
			builder.add(PALM_WOOD.asItem(), 300);
			builder.add(STRIPPED_PALM_WOOD.asItem(), 300);
			builder.add(PALM_PLANKS.asItem(), 300);
			builder.add(PALM_SLAB.asItem(), 150);
			builder.add(PALM_STAIRS.asItem(), 300);
			builder.add(PALM_PRESSURE_PLATE.asItem(), 300);
			builder.add(PALM_BUTTON.asItem(), 100);
			builder.add(PALM_TRAPDOOR.asItem(), 300);
			builder.add(PALM_FENCE_GATE.asItem(), 300);
			builder.add(PALM_FENCE.asItem(), 300);
			builder.add(WWItems.PALM_SIGN, 300);
			builder.add(WWItems.PALM_HANGING_SIGN, 800);
			builder.add(WWItems.COCONUT, 150); // COCONUT OIL IS KNOWN TO BE FLAMMABLE :)
			builder.add(WWItems.SPLIT_COCONUT, 75);

			builder.add(WWFabricItems.MAPLE_BOAT, 1200);
			builder.add(WWFabricItems.MAPLE_CHEST_BOAT, 1200);
			builder.add(MAPLE_LOG.asItem(), 300);
			builder.add(STRIPPED_MAPLE_LOG.asItem(), 300);
			builder.add(MAPLE_WOOD.asItem(), 300);
			builder.add(STRIPPED_MAPLE_WOOD.asItem(), 300);
			builder.add(MAPLE_PLANKS.asItem(), 300);
			builder.add(MAPLE_SLAB.asItem(), 150);
			builder.add(MAPLE_STAIRS.asItem(), 300);
			builder.add(MAPLE_PRESSURE_PLATE.asItem(), 300);
			builder.add(MAPLE_BUTTON.asItem(), 100);
			builder.add(MAPLE_TRAPDOOR.asItem(), 300);
			builder.add(MAPLE_FENCE_GATE.asItem(), 300);
			builder.add(MAPLE_FENCE.asItem(), 300);
			builder.add(WWItems.MAPLE_SIGN, 300);
			builder.add(WWItems.MAPLE_HANGING_SIGN, 800);
			builder.add(YELLOW_MAPLE_SAPLING.asItem(), 100);
			builder.add(ORANGE_MAPLE_SAPLING.asItem(), 100);
			builder.add(RED_MAPLE_SAPLING.asItem(), 100);

			builder.add(HOLLOWED_WARPED_STEM.asItem(), 300);
			builder.add(HOLLOWED_CRIMSON_STEM.asItem(), 300);
			builder.add(HOLLOWED_MANGROVE_LOG.asItem(), 300);
			builder.add(HOLLOWED_ACACIA_LOG.asItem(), 300);
			builder.add(HOLLOWED_JUNGLE_LOG.asItem(), 300);
			builder.add(HOLLOWED_DARK_OAK_LOG.asItem(), 300);
			builder.add(HOLLOWED_SPRUCE_LOG.asItem(), 300);
			builder.add(HOLLOWED_CHERRY_LOG.asItem(), 300);
			builder.add(HOLLOWED_BIRCH_LOG.asItem(), 300);
			builder.add(HOLLOWED_PALE_OAK_LOG.asItem(), 300);
			builder.add(HOLLOWED_BAOBAB_LOG.asItem(), 300);
			builder.add(HOLLOWED_WILLOW_LOG.asItem(), 300);
			builder.add(HOLLOWED_CYPRESS_LOG.asItem(), 300);
			builder.add(HOLLOWED_PALM_LOG.asItem(), 300);
			builder.add(HOLLOWED_MAPLE_LOG.asItem(), 300);

			builder.add(STRIPPED_HOLLOWED_WARPED_STEM.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_CRIMSON_STEM.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_MANGROVE_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_ACACIA_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_JUNGLE_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_DARK_OAK_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_SPRUCE_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_CHERRY_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_BIRCH_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_PALE_OAK_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_BAOBAB_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_WILLOW_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_CYPRESS_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_PALM_LOG.asItem(), 300);
			builder.add(STRIPPED_HOLLOWED_MAPLE_LOG.asItem(), 300);

			builder.add(ACACIA_LEAF_LITTER, 100);
			builder.add(AZALEA_LEAF_LITTER, 100);
			builder.add(BAOBAB_LEAF_LITTER, 100);
			builder.add(BIRCH_LEAF_LITTER, 100);
			builder.add(CHERRY_LEAF_LITTER, 100);
			builder.add(CYPRESS_LEAF_LITTER, 100);
			builder.add(DARK_OAK_LEAF_LITTER, 100);
			builder.add(JUNGLE_LEAF_LITTER, 100);
			builder.add(MANGROVE_LEAF_LITTER, 100);
			builder.add(PALE_OAK_LEAF_LITTER, 100);
			builder.add(PALM_FROND_LITTER, 100);
			builder.add(SPRUCE_LEAF_LITTER, 100);
			builder.add(WILLOW_LEAF_LITTER, 100);

			builder.add(TUMBLEWEED.asItem(), 150);
			builder.add(TUMBLEWEED_PLANT.asItem(), 150);

			builder.add(SHRUB.asItem(), 150);
		});
	}

	private static void registerBonemeal() {
		BoneMealApi.register(
			Blocks.LILY_PAD,
			new BoneMealApi.BoneMealBehavior() {
				@Override
				public boolean meetsRequirements(LevelReader level, BlockPos pos, BlockState state) {
					return WWBlockConfig.BONE_MEAL_LILY_PADS.get();
				}

				@Override
				public void performBoneMeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
					level.setBlock(pos, FLOWERING_LILY_PAD.get().defaultBlockState(), Block.UPDATE_CLIENTS);
				}
			}
		);

		BoneMealApi.register(
			Blocks.DANDELION,
			new BoneMealApi.BoneMealBehavior() {
				@Override
				public boolean meetsRequirements(LevelReader level, BlockPos pos, BlockState state) {
					return WWBlockConfig.BONE_MEAL_DANDELIONS.get();
				}

				@Override
				public void performBoneMeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
					level.setBlock(pos, SEEDING_DANDELION.get().defaultBlockState(), Block.UPDATE_CLIENTS);
				}
			}
		);

		BoneMealApi.register(
			Blocks.MYCELIUM,
			new BoneMealApi.BoneMealBehavior() {
				@Override
				public boolean meetsRequirements(LevelReader level, BlockPos pos, BlockState state) {
					return level.getBlockState(pos.above()).isAir();
				}

				@Override
				public void performBoneMeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
					final BlockPos blockPos = pos.above();
					final Optional<Holder.Reference<PlacedFeature>> optional = level.registryAccess()
						.lookupOrThrow(Registries.PLACED_FEATURE)
						.get(WWMiscPlaced.MYCELIUM_GROWTH_BONEMEAL.getKey());

					masterLoop:
					for (int i = 0; i < 128; i++) {
						BlockPos blockPos2 = blockPos;

						for (int j = 0; j < i / 16; j++) {
							blockPos2 = blockPos2.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
							if (!level.getBlockState(blockPos2.below()).is(Blocks.MYCELIUM) || level.getBlockState(blockPos2).isCollisionShapeFullBlock(level, blockPos2)) {
								continue masterLoop;
							}
						}

						BlockState blockState2 = level.getBlockState(blockPos2);
						if (blockState2.isAir()) {
							if (optional.isEmpty()) continue;
							optional.get().value().place(level, level.getChunkSource().getGenerator(), random, blockPos2);
						}
					}
				}

				@Override
				public BlockPos getParticlePos(BlockState state, BlockPos pos) {
					return pos.above();
				}

				@Override
				public boolean isNeighborSpreader() {
					return true;
				}
			}
		);
	}

	private static void registerAxe() {
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.OAK_LOG, HOLLOWED_OAK_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.BIRCH_LOG, HOLLOWED_BIRCH_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.CHERRY_LOG, HOLLOWED_CHERRY_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.SPRUCE_LOG, HOLLOWED_SPRUCE_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.DARK_OAK_LOG, HOLLOWED_DARK_OAK_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.JUNGLE_LOG, HOLLOWED_JUNGLE_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.ACACIA_LOG, HOLLOWED_ACACIA_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.MANGROVE_LOG, HOLLOWED_MANGROVE_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.PALE_OAK_LOG, HOLLOWED_PALE_OAK_LOG.get());
		HollowedLogBlock.registerAxeHollowBehaviorStem(Blocks.CRIMSON_STEM, HOLLOWED_CRIMSON_STEM.get());
		HollowedLogBlock.registerAxeHollowBehaviorStem(Blocks.WARPED_STEM, HOLLOWED_WARPED_STEM.get());
		HollowedLogBlock.registerAxeHollowBehavior(BAOBAB_LOG.get(), HOLLOWED_BAOBAB_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(WILLOW_LOG.get(), HOLLOWED_WILLOW_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(CYPRESS_LOG.get(), HOLLOWED_CYPRESS_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(PALM_LOG.get(), HOLLOWED_PALM_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(MAPLE_LOG.get(), HOLLOWED_MAPLE_LOG.get());
		//STRIPPED
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_OAK_LOG, STRIPPED_HOLLOWED_OAK_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_BIRCH_LOG, STRIPPED_HOLLOWED_BIRCH_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_CHERRY_LOG, STRIPPED_HOLLOWED_CHERRY_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_SPRUCE_LOG, STRIPPED_HOLLOWED_SPRUCE_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_DARK_OAK_LOG, STRIPPED_HOLLOWED_DARK_OAK_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_JUNGLE_LOG, STRIPPED_HOLLOWED_JUNGLE_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_ACACIA_LOG, STRIPPED_HOLLOWED_ACACIA_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(Blocks.STRIPPED_MANGROVE_LOG, STRIPPED_HOLLOWED_MANGROVE_LOG.get());
		HollowedLogBlock.registerAxeHollowBehaviorStem(Blocks.STRIPPED_PALE_OAK_LOG, STRIPPED_HOLLOWED_PALE_OAK_LOG.get());
		HollowedLogBlock.registerAxeHollowBehaviorStem(Blocks.STRIPPED_CRIMSON_STEM, STRIPPED_HOLLOWED_CRIMSON_STEM.get());
		HollowedLogBlock.registerAxeHollowBehaviorStem(Blocks.STRIPPED_WARPED_STEM, STRIPPED_HOLLOWED_WARPED_STEM.get());
		HollowedLogBlock.registerAxeHollowBehavior(STRIPPED_BAOBAB_LOG.get(), STRIPPED_HOLLOWED_BAOBAB_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(STRIPPED_WILLOW_LOG.get(), STRIPPED_HOLLOWED_WILLOW_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(STRIPPED_CYPRESS_LOG.get(), STRIPPED_HOLLOWED_CYPRESS_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(STRIPPED_PALM_LOG.get(), STRIPPED_HOLLOWED_PALM_LOG.get());
		HollowedLogBlock.registerAxeHollowBehavior(STRIPPED_MAPLE_LOG.get(), STRIPPED_HOLLOWED_MAPLE_LOG.get());
	}

	private static void registerInventories() {
		ItemStorage.SIDED.registerForBlocks((level, pos, state, blockEntity, direction) -> new NoInteractionStorage<>(), STONE_CHEST.get());
		HopperApi.addBlacklistedType(WWBlockEntityTypes.STONE_CHEST.get());
	}
}
