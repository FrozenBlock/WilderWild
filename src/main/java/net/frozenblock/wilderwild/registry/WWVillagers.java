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
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.biome.Biome;

public final class WWVillagers {
	private WWVillagers() {
		throw new UnsupportedOperationException("WWVillagers contains only static declarations.");
	}

	public static void register() {
		Map<ResourceKey<Biome>, VillagerType> villagerTypeMap = VillagerType.BY_BIOME;
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

		TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 5, (factories, rebalanced) -> {
			final WWEntityConfig.VillagerConfig config = WWEntityConfig.get().villager;
			if (config.fishermanJellyfishForEmeralds) factories.add(new VillagerTrades.EmeraldForItems(WWItems.JELLYFISH_BUCKET, 4, 12, 30));
			if (config.fishermanCrabForEmeralds) factories.add(new VillagerTrades.EmeraldForItems(WWItems.CRAB_BUCKET, 4, 12, 30));
		});

		TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
			final WWEntityConfig.VillagerConfig config = WWEntityConfig.get().villager;

			if (config.wanderingBaobabNutTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWItems.BAOBAB_NUT, 5, 1, 8, 1));
			if (config.wanderingWillowSaplingTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.WILLOW_SAPLING.asItem(), 5, 1, 8, 1));
			if (config.wanderingCypressSaplingTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.CYPRESS_SAPLING.asItem(), 5, 1, 8, 1));
			if (config.wanderingCoconutTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWItems.COCONUT, 5, 1, 8, 1));
			if (config.wanderingMapleSaplingTrade) {
				factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.YELLOW_MAPLE_SAPLING.asItem(), 5, 1, 8, 1));
				factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.ORANGE_MAPLE_SAPLING.asItem(), 5, 1, 8, 1));
				factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.RED_MAPLE_SAPLING.asItem(), 5, 1, 8, 1));
			}

			if (config.wanderingSeedingDandelionTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.SEEDING_DANDELION.asItem(), 1, 1, 12, 1));
			if (config.wanderingCarnationTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.CARNATION.asItem(), 1, 1, 12, 1));
			if (config.wanderingPasqueflowerTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.PASQUEFLOWER.asItem(), 1, 1, 8, 1));
			if (config.wanderingMarigoldTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.MARIGOLD.asItem(), 1, 1, 8, 1));
			if (config.wanderingHibiscusTrade) {
				factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.PINK_HIBISCUS.asItem(), 1, 1, 8, 1));
				factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.RED_HIBISCUS.asItem(), 1, 1, 8, 1));
				factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.YELLOW_HIBISCUS.asItem(), 1, 1, 8, 1));
				factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.PURPLE_HIBISCUS.asItem(), 1, 1, 8, 1));
				factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.WHITE_HIBISCUS.asItem(), 1, 1, 8, 1));
			}

			if (config.wanderingTumbleweedTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.TUMBLEWEED_PLANT.asItem(), 1, 1, 4, 1));
			if (config.wanderingPricklyPearTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWItems.PRICKLY_PEAR.asItem(), 1, 1, 12, 1));
			if (config.wanderingIcicleTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.ICICLE.asItem(), 1, 2, 5, 1));

			if (config.wanderingBarnaclesTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.BARNACLES.asItem(), 1, 2, 5, 1));
			if (config.wanderingSeaAnemoneTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.SEA_ANEMONE.asItem(), 1, 2, 5, 1));
			if (config.wanderingSeaWhipTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.SEA_WHIP.asItem(), 1, 2, 5, 1));
			if (config.wanderingAuburnMossTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.AUBURN_MOSS_BLOCK.asItem(), 1, 2, 5, 1));
			if (config.wanderingAlgaeTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.ALGAE.asItem(), 1, 2, 5, 1));
			if (config.wanderingPlanktonTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.PLANKTON.asItem(), 1, 2, 5, 1));
		});

		TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {
			final WWEntityConfig.VillagerConfig config = WWEntityConfig.get().villager;
			if (config.wanderingGeyserTrade) factories.add(new VillagerTrades.ItemsForEmeralds(WWBlocks.GEYSER.asItem(), 6, 1, 4, 1));
		});
	}
}
