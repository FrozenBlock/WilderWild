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

package net.frozenblock.wilderwild.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public final class WWItemTagProvider extends FabricTagProvider.ItemTagProvider {

	public WWItemTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@NotNull
	private TagKey<Item> getTag(String id) {
		return TagKey.create(this.registryKey, ResourceLocation.parse(id));
	}

	@NotNull private ResourceKey<Item> getKey(String namespace, String path) {
		return ResourceKey.create(this.registryKey, ResourceLocation.fromNamespaceAndPath(namespace, path));
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.valueLookupBuilder(ItemTags.STAIRS)
			.add(WWBlocks.SCULK_STAIRS.asItem())
			.add(WWBlocks.MOSSY_MUD_BRICK_STAIRS.asItem())
			.add(WWBlocks.GABBRO_STAIRS.asItem())
			.add(WWBlocks.POLISHED_GABBRO_STAIRS.asItem())
			.add(WWBlocks.GABBRO_BRICK_STAIRS.asItem())
			.add(WWBlocks.MOSSY_GABBRO_BRICK_STAIRS.asItem());

		this.valueLookupBuilder(ItemTags.SLABS)
			.add(WWBlocks.SCULK_SLAB.asItem())
			.add(WWBlocks.MOSSY_MUD_BRICK_SLAB.asItem())
			.add(WWBlocks.GABBRO_SLAB.asItem())
			.add(WWBlocks.POLISHED_GABBRO_SLAB.asItem())
			.add(WWBlocks.GABBRO_BRICK_SLAB.asItem())
			.add(WWBlocks.MOSSY_GABBRO_BRICK_SLAB.asItem());

		this.valueLookupBuilder(ItemTags.WALLS)
			.add(WWBlocks.SCULK_WALL.asItem())
			.add(WWBlocks.MOSSY_MUD_BRICK_WALL.asItem())
			.add(WWBlocks.GABBRO_WALL.asItem())
			.add(WWBlocks.POLISHED_GABBRO_WALL.asItem())
			.add(WWBlocks.GABBRO_BRICK_WALL.asItem())
			.add(WWBlocks.MOSSY_GABBRO_BRICK_WALL.asItem());

		this.valueLookupBuilder(ItemTags.WOODEN_SHELVES)
			.add(WWBlocks.BAOBAB_SHELF.asItem())
			.add(WWBlocks.WILLOW_SHELF.asItem())
			.add(WWBlocks.CYPRESS_SHELF.asItem())
			.add(WWBlocks.PALM_SHELF.asItem())
			.add(WWBlocks.MAPLE_SHELF.asItem());

		this.valueLookupBuilder(ItemTags.ARMADILLO_FOOD)
			.add(WWItems.SCORCHED_EYE);

		this.builder(WWItemTags.BROWN_MUSHROOM_STEW_INGREDIENTS)
			.add(Items.BROWN_MUSHROOM.builtInRegistryHolder().key())
			.addOptional(this.getKey(WWConstants.MOD_ID, "brown_shelf_fungus"));

		this.builder(WWItemTags.RED_MUSHROOM_STEW_INGREDIENTS)
			.add(Items.RED_MUSHROOM.builtInRegistryHolder().key())
			.addOptional(this.getKey(WWConstants.MOD_ID, "red_shelf_fungus"));

		this.valueLookupBuilder(WWItemTags.TUMBLEWEED_COMMON)
			.add(Items.ROTTEN_FLESH)
			.add(Items.BONE_MEAL)
			.add(Items.WHEAT_SEEDS)
			.add(Items.STICK)
			.add(Items.DEAD_BUSH);

		this.valueLookupBuilder(WWItemTags.TUMBLEWEED_MEDIUM)
			.add(Items.BONE)
			.add(Items.GOLD_NUGGET)
			.add(Items.WHEAT_SEEDS)
			.add(Items.POTATO)
			.add(Items.CARROT)
			.add(Items.BEETROOT_SEEDS)
			.add(Items.STRING);

		this.valueLookupBuilder(WWItemTags.TUMBLEWEED_RARE)
			.add(Items.IRON_NUGGET)
			.add(Items.POTATO)
			.add(Items.CARROT)
			.add(Items.MELON_SEEDS)
			.add(Items.PUMPKIN_SEEDS);

		this.valueLookupBuilder(WWItemTags.MESOGLEA)
			.add(WWBlocks.BLUE_MESOGLEA.asItem())
			.add(WWBlocks.BLUE_PEARLESCENT_MESOGLEA.asItem())
			.add(WWBlocks.LIME_MESOGLEA.asItem())
			.add(WWBlocks.PINK_MESOGLEA.asItem())
			.add(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.asItem())
			.add(WWBlocks.RED_MESOGLEA.asItem())
			.add(WWBlocks.YELLOW_MESOGLEA.asItem());

		this.valueLookupBuilder(WWItemTags.NEMATOCYSTS)
			.add(WWBlocks.BLUE_NEMATOCYST.asItem())
			.add(WWBlocks.BLUE_PEARLESCENT_NEMATOCYST.asItem())
			.add(WWBlocks.LIME_NEMATOCYST.asItem())
			.add(WWBlocks.PINK_NEMATOCYST.asItem())
			.add(WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST.asItem())
			.add(WWBlocks.RED_NEMATOCYST.asItem())
			.add(WWBlocks.YELLOW_NEMATOCYST.asItem());

		this.valueLookupBuilder(WWItemTags.PEARLESCENT_NEMATOCYSTS)
			.add(WWBlocks.BLUE_PEARLESCENT_NEMATOCYST.asItem())
			.add(WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST.asItem());

		this.valueLookupBuilder(WWItemTags.FROGLIGHT_GOOP)
			.add(WWBlocks.PEARLESCENT_FROGLIGHT_GOOP.asItem())
			.add(WWBlocks.VERDANT_FROGLIGHT_GOOP.asItem())
			.add(WWBlocks.OCHRE_FROGLIGHT_GOOP.asItem());

		this.valueLookupBuilder(WWItemTags.JELLYFISH_FOOD)
			.add(Items.COD, Items.SALMON);

		this.valueLookupBuilder(WWItemTags.PEARLESCENT_JELLYFISH_FOOD)
			.add(Items.COD, Items.SALMON);

		this.valueLookupBuilder(WWItemTags.CRAB_FOOD)
			.add(Items.KELP);

		this.valueLookupBuilder(WWItemTags.OSTRICH_FOOD)
			.add(WWBlocks.SHRUB.asItem());

		this.valueLookupBuilder(WWItemTags.PENGUIN_FOOD)
			.add(Items.INK_SAC, Items.GLOW_INK_SAC);

		this.valueLookupBuilder(ItemTags.BOATS)
			.add(WWItems.BAOBAB_BOAT)
			.add(WWItems.WILLOW_BOAT)
			.add(WWItems.CYPRESS_BOAT)
			.add(WWItems.PALM_BOAT)
			.add(WWItems.MAPLE_BOAT);

		this.valueLookupBuilder(ItemTags.CHEST_BOATS)
			.add(WWItems.BAOBAB_CHEST_BOAT)
			.add(WWItems.WILLOW_CHEST_BOAT)
			.add(WWItems.CYPRESS_CHEST_BOAT)
			.add(WWItems.PALM_CHEST_BOAT)
			.add(WWItems.MAPLE_CHEST_BOAT);

		this.valueLookupBuilder(ItemTags.SMALL_FLOWERS)
			.add(WWBlocks.CARNATION.asItem())
			.add(WWBlocks.MARIGOLD.asItem())
			.add(WWBlocks.SEEDING_DANDELION.asItem())
			.add(WWBlocks.PASQUEFLOWER.asItem())
			.add(WWBlocks.RED_HIBISCUS.asItem())
			.add(WWBlocks.YELLOW_HIBISCUS.asItem())
			.add(WWBlocks.WHITE_HIBISCUS.asItem())
			.add(WWBlocks.PINK_HIBISCUS.asItem())
			.add(WWBlocks.PURPLE_HIBISCUS.asItem())
			.add(WWBlocks.FLOWERING_LILY_PAD.asItem());

		this.valueLookupBuilder(ItemTags.BEE_FOOD)
			.add(WWBlocks.CARNATION.asItem())
			.add(WWBlocks.MARIGOLD.asItem())
			.add(WWBlocks.SEEDING_DANDELION.asItem())
			.add(WWBlocks.RED_HIBISCUS.asItem())
			.add(WWBlocks.YELLOW_HIBISCUS.asItem())
			.add(WWBlocks.WHITE_HIBISCUS.asItem())
			.add(WWBlocks.PINK_HIBISCUS.asItem())
			.add(WWBlocks.PURPLE_HIBISCUS.asItem())
			.add(WWBlocks.FLOWERING_LILY_PAD.asItem())
			.add(WWBlocks.DATURA.asItem())
			.add(WWBlocks.CATTAIL.asItem())
			.add(WWBlocks.MILKWEED.asItem())
			.add(WWBlocks.POLLEN.asItem())
			.add(WWBlocks.PHLOX.asItem())
			.add(WWBlocks.LANTANAS.asItem());

		this.valueLookupBuilder(ItemTags.ACACIA_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_ACACIA_LOGS);

		this.valueLookupBuilder(ItemTags.BIRCH_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_BIRCH_LOGS);

		this.valueLookupBuilder(ItemTags.CHERRY_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_CHERRY_LOGS);

		this.valueLookupBuilder(ItemTags.CRIMSON_STEMS)
			.addOptionalTag(WWItemTags.HOLLOWED_CRIMSON_STEMS);

		this.valueLookupBuilder(ItemTags.DARK_OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_DARK_OAK_LOGS);

		this.valueLookupBuilder(ItemTags.JUNGLE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_JUNGLE_LOGS);

		this.valueLookupBuilder(ItemTags.ACACIA_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_ACACIA_LOGS);

		this.valueLookupBuilder(ItemTags.MANGROVE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_MANGROVE_LOGS);

		this.valueLookupBuilder(ItemTags.OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_OAK_LOGS);

		this.valueLookupBuilder(ItemTags.ACACIA_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_ACACIA_LOGS);

		this.valueLookupBuilder(ItemTags.SPRUCE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_SPRUCE_LOGS);

		this.valueLookupBuilder(ItemTags.WARPED_STEMS)
			.addOptionalTag(WWItemTags.HOLLOWED_WARPED_STEMS);

		this.valueLookupBuilder(WWItemTags.BAOBAB_LOGS)
			.add(WWBlocks.BAOBAB_LOG.asItem(), WWBlocks.STRIPPED_BAOBAB_LOG.asItem())
			.add(WWBlocks.BAOBAB_WOOD.asItem(), WWBlocks.STRIPPED_BAOBAB_WOOD.asItem())
			.addOptionalTag(WWItemTags.HOLLOWED_BAOBAB_LOGS);

		this.valueLookupBuilder(WWItemTags.WILLOW_LOGS)
			.add(WWBlocks.WILLOW_LOG.asItem(), WWBlocks.STRIPPED_WILLOW_LOG.asItem())
			.add(WWBlocks.WILLOW_WOOD.asItem(), WWBlocks.STRIPPED_WILLOW_WOOD.asItem())
			.addOptionalTag(WWItemTags.HOLLOWED_WILLOW_LOGS);

		this.valueLookupBuilder(WWItemTags.CYPRESS_LOGS)
			.add(WWBlocks.CYPRESS_LOG.asItem(), WWBlocks.STRIPPED_CYPRESS_LOG.asItem())
			.add(WWBlocks.CYPRESS_WOOD.asItem(), WWBlocks.STRIPPED_CYPRESS_WOOD.asItem())
			.addOptionalTag(WWItemTags.HOLLOWED_CYPRESS_LOGS);

		this.valueLookupBuilder(WWItemTags.PALM_LOGS)
			.add(WWBlocks.PALM_LOG.asItem(), WWBlocks.STRIPPED_PALM_LOG.asItem())
			.add(WWBlocks.PALM_WOOD.asItem(), WWBlocks.STRIPPED_PALM_WOOD.asItem())
			.addOptionalTag(WWItemTags.HOLLOWED_PALM_LOGS);

		this.valueLookupBuilder(WWItemTags.MAPLE_LOGS)
			.add(WWBlocks.MAPLE_LOG.asItem(), WWBlocks.STRIPPED_MAPLE_LOG.asItem())
			.add(WWBlocks.MAPLE_WOOD.asItem(), WWBlocks.STRIPPED_MAPLE_WOOD.asItem())
			.addOptionalTag(WWItemTags.HOLLOWED_MAPLE_LOGS);

		this.valueLookupBuilder(ItemTags.PALE_OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_PALE_OAK_LOGS);

		this.valueLookupBuilder(ItemTags.LEAVES)
			.add(WWBlocks.BAOBAB_LEAVES.asItem())
			.add(WWBlocks.WILLOW_LEAVES.asItem())
			.add(WWBlocks.CYPRESS_LEAVES.asItem())
			.add(WWBlocks.PALM_FRONDS.asItem())
			.add(WWBlocks.YELLOW_MAPLE_LEAVES.asItem())
			.add(WWBlocks.ORANGE_MAPLE_LEAVES.asItem())
			.add(WWBlocks.RED_MAPLE_LEAVES.asItem());

		this.valueLookupBuilder(ConventionalItemTags.STRIPPED_LOGS)
			.add(WWBlocks.STRIPPED_BAOBAB_LOG.asItem())
			.add(WWBlocks.STRIPPED_WILLOW_LOG.asItem())
			.add(WWBlocks.STRIPPED_CYPRESS_LOG.asItem())
			.add(WWBlocks.STRIPPED_PALM_LOG.asItem())
			.add(WWBlocks.STRIPPED_MAPLE_LOG.asItem());

		this.valueLookupBuilder(ConventionalItemTags.STRIPPED_WOODS)
			.add(WWBlocks.STRIPPED_BAOBAB_WOOD.asItem())
			.add(WWBlocks.STRIPPED_WILLOW_WOOD.asItem())
			.add(WWBlocks.STRIPPED_CYPRESS_WOOD.asItem())
			.add(WWBlocks.STRIPPED_PALM_WOOD.asItem())
			.add(WWBlocks.STRIPPED_MAPLE_WOOD.asItem());

		this.valueLookupBuilder(ItemTags.LOGS)
			.addOptionalTag(WWItemTags.BAOBAB_LOGS)
			.addOptionalTag(WWItemTags.WILLOW_LOGS)
			.addOptionalTag(WWItemTags.CYPRESS_LOGS)
			.addOptionalTag(WWItemTags.PALM_LOGS)
			.addOptionalTag(WWItemTags.MAPLE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS);

		this.valueLookupBuilder(ItemTags.LOGS_THAT_BURN)
			.addOptionalTag(WWItemTags.BAOBAB_LOGS)
			.addOptionalTag(WWItemTags.WILLOW_LOGS)
			.addOptionalTag(WWItemTags.CYPRESS_LOGS)
			.addOptionalTag(WWItemTags.PALM_LOGS)
			.addOptionalTag(WWItemTags.MAPLE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS_THAT_BURN);

		this.valueLookupBuilder(ItemTags.NON_FLAMMABLE_WOOD)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS_DONT_BURN);

		this.valueLookupBuilder(ItemTags.PLANKS)
			.add(WWBlocks.BAOBAB_PLANKS.asItem())
			.add(WWBlocks.WILLOW_PLANKS.asItem())
			.add(WWBlocks.CYPRESS_PLANKS.asItem())
			.add(WWBlocks.PALM_PLANKS.asItem())
			.add(WWBlocks.MAPLE_PLANKS.asItem());

		this.valueLookupBuilder(ItemTags.SAPLINGS)
			.add(WWItems.BAOBAB_NUT)
			.add(WWBlocks.WILLOW_SAPLING.asItem())
			.add(WWBlocks.CYPRESS_SAPLING.asItem())
			.add(WWItems.COCONUT)
			.add(WWBlocks.MAPLE_SAPLING.asItem());

		this.valueLookupBuilder(ItemTags.SIGNS)
			.add(WWItems.BAOBAB_SIGN)
			.add(WWItems.WILLOW_SIGN)
			.add(WWItems.CYPRESS_SIGN)
			.add(WWItems.PALM_SIGN)
			.add(WWItems.MAPLE_SIGN);

		this.valueLookupBuilder(ItemTags.HANGING_SIGNS)
			.add(WWItems.BAOBAB_HANGING_SIGN)
			.add(WWItems.WILLOW_HANGING_SIGN)
			.add(WWItems.CYPRESS_HANGING_SIGN)
			.add(WWItems.PALM_HANGING_SIGN)
			.add(WWItems.MAPLE_HANGING_SIGN);

		this.valueLookupBuilder(ItemTags.WOODEN_BUTTONS)
			.add(WWBlocks.BAOBAB_BUTTON.asItem())
			.add(WWBlocks.WILLOW_BUTTON.asItem())
			.add(WWBlocks.CYPRESS_BUTTON.asItem())
			.add(WWBlocks.PALM_BUTTON.asItem())
			.add(WWBlocks.MAPLE_BUTTON.asItem());

		this.valueLookupBuilder(ItemTags.WOODEN_DOORS)
			.add(WWBlocks.BAOBAB_DOOR.asItem())
			.add(WWBlocks.WILLOW_DOOR.asItem())
			.add(WWBlocks.CYPRESS_DOOR.asItem())
			.add(WWBlocks.PALM_DOOR.asItem())
			.add(WWBlocks.MAPLE_DOOR.asItem());

		this.valueLookupBuilder(ItemTags.WOODEN_FENCES)
			.add(WWBlocks.BAOBAB_FENCE.asItem())
			.add(WWBlocks.WILLOW_FENCE.asItem())
			.add(WWBlocks.CYPRESS_FENCE.asItem())
			.add(WWBlocks.PALM_FENCE.asItem())
			.add(WWBlocks.MAPLE_FENCE.asItem());

		this.valueLookupBuilder(ItemTags.FENCE_GATES)
			.add(WWBlocks.BAOBAB_FENCE_GATE.asItem())
			.add(WWBlocks.WILLOW_FENCE_GATE.asItem())
			.add(WWBlocks.CYPRESS_FENCE_GATE.asItem())
			.add(WWBlocks.PALM_FENCE_GATE.asItem())
			.add(WWBlocks.MAPLE_FENCE_GATE.asItem());

		this.valueLookupBuilder(ItemTags.WOODEN_PRESSURE_PLATES)
			.add(WWBlocks.BAOBAB_PRESSURE_PLATE.asItem())
			.add(WWBlocks.WILLOW_PRESSURE_PLATE.asItem())
			.add(WWBlocks.CYPRESS_PRESSURE_PLATE.asItem())
			.add(WWBlocks.PALM_PRESSURE_PLATE.asItem())
			.add(WWBlocks.MAPLE_PRESSURE_PLATE.asItem());

		this.valueLookupBuilder(ItemTags.WOODEN_SLABS)
			.add(WWBlocks.BAOBAB_SLAB.asItem())
			.add(WWBlocks.WILLOW_SLAB.asItem())
			.add(WWBlocks.CYPRESS_SLAB.asItem())
			.add(WWBlocks.PALM_SLAB.asItem())
			.add(WWBlocks.MAPLE_SLAB.asItem());

		this.valueLookupBuilder(ItemTags.WOODEN_STAIRS)
			.add(WWBlocks.BAOBAB_STAIRS.asItem())
			.add(WWBlocks.WILLOW_STAIRS.asItem())
			.add(WWBlocks.CYPRESS_STAIRS.asItem())
			.add(WWBlocks.PALM_STAIRS.asItem())
			.add(WWBlocks.MAPLE_STAIRS.asItem());

		this.valueLookupBuilder(ItemTags.WOODEN_TRAPDOORS)
			.add(WWBlocks.BAOBAB_TRAPDOOR.asItem())
			.add(WWBlocks.WILLOW_TRAPDOOR.asItem())
			.add(WWBlocks.CYPRESS_TRAPDOOR.asItem())
			.add(WWBlocks.PALM_TRAPDOOR.asItem())
			.add(WWBlocks.MAPLE_TRAPDOOR.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_ACACIA_LOGS)
			.add(WWBlocks.HOLLOWED_ACACIA_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_BIRCH_LOGS)
			.add(WWBlocks.HOLLOWED_BIRCH_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_CHERRY_LOGS)
			.add(WWBlocks.HOLLOWED_CHERRY_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_CRIMSON_STEMS)
			.add(WWBlocks.HOLLOWED_CRIMSON_STEM.asItem(), WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_DARK_OAK_LOGS)
			.add(WWBlocks.HOLLOWED_DARK_OAK_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_JUNGLE_LOGS)
			.add(WWBlocks.HOLLOWED_JUNGLE_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_MANGROVE_LOGS)
			.add(WWBlocks.HOLLOWED_MANGROVE_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_OAK_LOGS)
			.add(WWBlocks.HOLLOWED_OAK_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_OAK_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_SPRUCE_LOGS)
			.add(WWBlocks.HOLLOWED_SPRUCE_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_WARPED_STEMS)
			.add(WWBlocks.HOLLOWED_WARPED_STEM.asItem(), WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_BAOBAB_LOGS)
			.add(WWBlocks.HOLLOWED_BAOBAB_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_WILLOW_LOGS)
			.add(WWBlocks.HOLLOWED_WILLOW_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_CYPRESS_LOGS)
			.add(WWBlocks.HOLLOWED_CYPRESS_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_PALM_LOGS)
			.add(WWBlocks.HOLLOWED_PALM_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_PALM_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_PALE_OAK_LOGS)
			.add(WWBlocks.HOLLOWED_PALE_OAK_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_MAPLE_LOGS)
			.add(WWBlocks.HOLLOWED_MAPLE_LOG.asItem(), WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWItemTags.HOLLOWED_ACACIA_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_BIRCH_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_CHERRY_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_CRIMSON_STEMS)
			.addOptionalTag(WWItemTags.HOLLOWED_DARK_OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_JUNGLE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_MANGROVE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_SPRUCE_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_WARPED_STEMS)
			.addOptionalTag(WWItemTags.HOLLOWED_BAOBAB_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_WILLOW_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_CYPRESS_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_PALM_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_PALE_OAK_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_MAPLE_LOGS);

		this.valueLookupBuilder(WWItemTags.HOLLOWED_LOGS_DONT_BURN)
			.addOptionalTag(WWItemTags.HOLLOWED_CRIMSON_STEMS)
			.addOptionalTag(WWItemTags.HOLLOWED_WARPED_STEMS);

		this.valueLookupBuilder(WWItemTags.HOLLOWED_LOGS)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWItemTags.HOLLOWED_LOGS_DONT_BURN);

		this.valueLookupBuilder(WWItemTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.add(WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_PALM_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG.asItem());

		this.valueLookupBuilder(WWItemTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN)
			.add(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM.asItem())
			.add(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM.asItem());

		this.valueLookupBuilder(WWItemTags.STRIPPED_HOLLOWED_LOGS)
			.addOptionalTag(WWItemTags.STRIPPED_HOLLOWED_LOGS_THAT_BURN)
			.addOptionalTag(WWItemTags.STRIPPED_HOLLOWED_LOGS_DONT_BURN);
	}
}
