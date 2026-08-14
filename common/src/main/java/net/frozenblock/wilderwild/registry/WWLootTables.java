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

import net.frozenblock.lib.item.api.component.removable.RemovableItemTags;
import net.frozenblock.lib.item.api.loot.LootTableEvents;
import net.frozenblock.lib.item.api.loot.LootTableModification;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.tag.WWStructureTags;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.packs.VanillaChestLoot;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import java.util.List;

public final class WWLootTables {
	public static final ResourceKey<LootTable> SHEAR_MILKWEED = register("shearing/milkweed");
	public static final ResourceKey<LootTable> SHEAR_PRICKLY_PEAR = register("shearing/prickly_pear");
	public static final ResourceKey<LootTable> SHEAR_SPONGE_BUD = register("shearing/sponge_bud");
	public static final ResourceKey<LootTable> SHEAR_SHRUB = register("shearing/shrub");

	// FIXME: I dont work on Neo for some reason :(
	public static void init() {
		// SHIPWRECK
		LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.SHIPWRECK_SUPPLY.equals(id) && source.isBuiltin()) {
				final LootPool.Builder pool = LootPool.lootPool();

				boolean modified = false;
				if (WWWorldgenConfig.ALGAE_GENERATION.get()) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.ALGAE.get().asItem())
							.setWeight(4)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 3F)))
					);
					modified = true;
				}

				if (WWWorldgenConfig.PLANKTON_GENERATION.get()) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.PLANKTON.get().asItem())
							.setWeight(1)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 4F)))
					);
					modified = true;
				}

				if (WWWorldgenConfig.BARNACLES_GENERATION.get()) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.BARNACLES.get().asItem())
							.setWeight(3)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 4F)))
					);
					modified = true;
				}

				if (WWWorldgenConfig.TUBE_WORMS_GENERATION.get()) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.TUBE_WORMS.get().asItem())
							.setWeight(1)
							.setQuality(Rarity.UNCOMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 2F)))
					);
					modified = true;
				}

				if (WWWorldgenConfig.SEA_ANEMONE_GENERATION.get()) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.SEA_ANEMONE.get().asItem())
							.setWeight(2)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 6F)))
					);
					modified = true;
				}

				if (WWWorldgenConfig.OCEAN_AUBURN_MOSS_GENERATION.get()) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.AUBURN_MOSS_BLOCK.get().asItem())
							.setWeight(1)
							.setQuality(Rarity.RARE.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 2F)))
					);

					pool.add(
						LootItem.lootTableItem(WWBlocks.AUBURN_MOSS_CARPET.get().asItem())
							.setWeight(1)
							.setQuality(Rarity.RARE.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 2F)))
					);

					pool.add(
						LootItem.lootTableItem(WWBlocks.AUBURN_CREEPING_MOSS.get().asItem())
							.setWeight(1)
							.setQuality(Rarity.RARE.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 2F)))
					);

					modified = true;
				}

				if (modified) {
					pool.setRolls(UniformGenerator.between(2F, 5F));
					tableBuilder.withPool(pool);
				}
			}
		});

		// SAVANNA VILLAGE
		LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.VILLAGE_SAVANNA_HOUSE.equals(id) && source.isBuiltin() && WWWorldgenConfig.BAOBAB_TREE_GENERATION.get()) {
				tableBuilder.frozenLib$modifyPools(builder -> {
					builder.add(
						LootItem.lootTableItem(WWItems.BAOBAB_NUT)
							.setWeight(2)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
					).add(
						LootItem.lootTableItem(WWBlocks.BAOBAB_LOG.get().asItem())
							.setWeight(2)
							.setQuality(Rarity.COMMON.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
					);
				});
			}
		});

		// DESERT VILLAGE
		LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.VILLAGE_DESERT_HOUSE.equals(id) && source.isBuiltin()) {
				if (WWWorldgenConfig.NEW_DESERT_VILLAGE_GENERATION.get() || WWWorldgenConfig.PALM_TREE_GENERATION.get()) {
					tableBuilder.frozenLib$modifyPools(builder -> {
						builder.add(
							LootItem.lootTableItem(WWItems.COCONUT)
								.setWeight(2)
								.setQuality(Rarity.COMMON.ordinal() + 1)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
						).add(
							LootItem.lootTableItem(WWBlocks.PALM_LOG.get().asItem())
								.setWeight(2)
								.setQuality(Rarity.COMMON.ordinal() + 1)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
						);
					});
				}

				if (WWWorldgenConfig.CACTUS_GENERATION.get()) {
					tableBuilder.frozenLib$modifyPools(builder -> {
						builder.add(
							LootItem.lootTableItem(WWItems.PRICKLY_PEAR)
								.setWeight(1)
								.setQuality(Rarity.COMMON.ordinal() + 1)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 4F)))
						);
					});
				}
			}
		});

		// ABANDONED CAMP
		// TODO: test to make sure this works properly, even with datapacks
		LootTableModification.editTable(BuiltInLootTables.ABANDONED_CAMP_COMMON_CHEST, false, (id, table, registries) -> {
			table.modifyPools(
				pool -> pool.hasItem(Items.ABANDONED_CAMPSITE_MAP),
				pool -> {
					final HolderLookup.RegistryLookup<Structure> structures = registries.lookupOrThrow(Registries.STRUCTURE);
					final HolderLookup.RegistryLookup<Biome> biomes = registries.lookupOrThrow(Registries.BIOME);

					final UniformContainerBase.Builder<?> mapleForestAbandonedCampsiteMap = LootItem.lootTableItem(Items.ABANDONED_CAMPSITE_MAP)
						.setWeight(1)
						.apply(SetNameFunction.setName(Component.translatable("filled_map.maple_forest_abandoned_camp"), SetNameFunction.Target.ITEM_NAME))
						.apply(
							ExplorationMapFunction.makeExplorationMap(structures.getOrThrow(WWStructureTags.ON_ABANDONED_CAMP_MAPLE_FOREST_MAPS))
								.setMapDecoration(MapDecorationTypes.ABANDONED_CAMP)
								.setSkipKnownStructures(true)
						)
						.apply(VanillaChestLoot.discardIfNotValidMap())
						.when(
							LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiomes(HolderSet.direct(biomes.getOrThrow(WWBiomes.MAPLE_FOREST))))
							.invert()
						);

					pool.add(mapleForestAbandonedCampsiteMap);
				}
			);
		});

		// ANCIENT CITY
		LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
			if (BuiltInLootTables.ANCIENT_CITY.equals(id) && source.isBuiltin()) {
				if (!WWBlockConfig.OSSEOUS_SCULK_GENERATION.get() && !WWBlockConfig.TENDRIL_GENERATION.get()) return;

				final LootPool.Builder pool = LootPool.lootPool();
				if (WWBlockConfig.OSSEOUS_SCULK_GENERATION.get()) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.OSSEOUS_SCULK.get().asItem())
							.setWeight(1)
							.setQuality(Rarity.RARE.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 5F)))
					);
				}
				if (WWBlockConfig.TENDRIL_GENERATION.get()) {
					pool.add(
						LootItem.lootTableItem(WWBlocks.HANGING_TENDRIL.get().asItem())
							.setWeight(1)
							.setQuality(Rarity.RARE.ordinal() + 1)
							.apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 3F)))
					);
				}

				pool.setRolls(UniformGenerator.between(1, 5));
				tableBuilder.withPool(pool);
			}
		});

		// STONE CHEST
		LootTableEvents.ON_ITEM_GENERATED_IN_CONTAINER.register((container, itemStack) -> {
			if (!(container instanceof StoneChestBlockEntity)) return;
			CustomData.update(DataComponents.CUSTOM_DATA, itemStack, compoundTag -> compoundTag.putBoolean("wilderwild_is_ancient", true));
		});
		RemovableItemTags.register("wilderwild_is_ancient", (level, entity, equipmentSlot) -> true, true);

		// POPLAR SAPLINGS
		LootTableEvents.MODIFY_DROPS.register((table, context, drops) -> {
			if (!context.hasParameter(LootContextParams.BLOCK_STATE)) return;
			final BlockState state = context.getOptionalParameter(LootContextParams.BLOCK_STATE);
			if (state.is(Blocks.YELLOW_POPLAR_LEAVES)) {
				final List<ItemStack> poplarSaplings = drops.stream().filter(itemStack -> itemStack.is(Items.POPLAR_SAPLING)).toList();
				drops.removeAll(poplarSaplings);
				drops.addAll(poplarSaplings.stream().map(itemStack -> itemStack.transmuteCopy(WWItems.POPLAR_SAPLING.yellow().get())).toList());
				return;
			}

			if (state.is(Blocks.RED_POPLAR_LEAVES)) {
				final List<ItemStack> poplarSaplings = drops.stream().filter(itemStack -> itemStack.is(Items.POPLAR_SAPLING)).toList();
				drops.removeAll(poplarSaplings);
				drops.addAll(poplarSaplings.stream().map(itemStack -> itemStack.transmuteCopy(WWItems.POPLAR_SAPLING.red().get())).toList());
				return;
			}
		});
	}

	private static ResourceKey<LootTable> register(String name) {
		return ResourceKey.create(Registries.LOOT_TABLE, WWConstants.id(name));
	}
}
