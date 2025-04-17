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

import java.util.Map;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.biome.Biome;

public final class WWVillagers {
	private WWVillagers() {
		throw new UnsupportedOperationException("WWVillagers contains only static declarations.");
	}

	public static void register() {
		Map<ResourceKey<Biome>, ResourceKey<VillagerType>> villagerTypeMap = VillagerType.BY_BIOME;
		villagerTypeMap.put(WWBiomes.CYPRESS_WETLANDS, VillagerType.SWAMP);
		villagerTypeMap.put(WWBiomes.OASIS, VillagerType.DESERT);
		villagerTypeMap.put(WWBiomes.FROZEN_CAVES, VillagerType.SNOW);
		villagerTypeMap.put(WWBiomes.ARID_FOREST, VillagerType.DESERT);
		villagerTypeMap.put(WWBiomes.ARID_SAVANNA, VillagerType.SAVANNA);
		villagerTypeMap.put(WWBiomes.PARCHED_FOREST, VillagerType.SAVANNA);
		villagerTypeMap.put(WWBiomes.BIRCH_JUNGLE, VillagerType.JUNGLE);
		villagerTypeMap.put(WWBiomes.SPARSE_BIRCH_JUNGLE, VillagerType.JUNGLE);
		villagerTypeMap.put(WWBiomes.BIRCH_TAIGA, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.TEMPERATE_RAINFOREST, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.DARK_TAIGA, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.MIXED_FOREST, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.DYING_MIXED_FOREST, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.SNOWY_DYING_MIXED_FOREST, VillagerType.SNOW);
		villagerTypeMap.put(WWBiomes.SNOWY_DYING_FOREST, VillagerType.SNOW);
		villagerTypeMap.put(WWBiomes.OLD_GROWTH_BIRCH_TAIGA, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA, VillagerType.SNOW);
		villagerTypeMap.put(WWBiomes.FLOWER_FIELD, VillagerType.PLAINS);

		TradeOfferHelper.registerWanderingTraderOffers(factories -> {
			// SELL SPECIAL ITEMS
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.BAOBAB_LOG.asItem(), 1, 8, 4, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.WILLOW_LOG.asItem(), 1, 8, 4, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.CYPRESS_LOG.asItem(), 1, 8, 4, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.PALM_LOG.asItem(), 1, 8, 4, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.MAPLE_LOG.asItem(), 1, 8, 4, 1)
			);

			// SELL COMMON ITEMS
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWItems.BAOBAB_NUT, 5, 1, 8, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.WILLOW_SAPLING.asItem(), 5, 1, 8, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.CYPRESS_SAPLING.asItem(), 5, 1, 8, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWItems.COCONUT, 5, 1, 8, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.MAPLE_SAPLING.asItem(), 5, 1, 8, 1)
			);

			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.AUBURN_CREEPING_MOSS.asItem(), 1, 3, 4, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.AUBURN_CREEPING_MOSS.asItem(), 1, 2, 5, 1)
			);

			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.SEA_ANEMONE.asItem(), 3, 1, 4, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.SEA_WHIP.asItem(), 3, 1, 4, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.BARNACLES.asItem(), 3, 1, 4, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.PLANKTON.asItem(), 3, 1, 4, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.TUBE_WORMS.asItem(), 3, 1, 4, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.SPONGE_BUD.asItem(), 3, 1, 4, 1)
			);

			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.SEEDING_DANDELION.asItem(), 1, 1, 12, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.CARNATION.asItem(), 1, 1, 12, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.MARIGOLD.asItem(), 1, 1, 12, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.PASQUEFLOWER.asItem(), 1, 1, 12, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.RED_HIBISCUS.asItem(), 1, 1, 12, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.YELLOW_HIBISCUS.asItem(), 1, 1, 12, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.WHITE_HIBISCUS.asItem(), 1, 1, 12, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.PINK_HIBISCUS.asItem(), 1, 1, 12, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.PURPLE_HIBISCUS.asItem(), 1, 1, 12, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.PHLOX.asItem(), 1, 1, 12, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.LANTANAS.asItem(), 1, 1, 12, 1)
			);

			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.SHRUB.asItem(), 3, 1, 12, 1)
			);
			factories.addOffersToPool(
				TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
				new VillagerTrades.ItemsForEmeralds(WWBlocks.CATTAIL.asItem(), 3, 1, 12, 1)
			);
		});
	}
}
