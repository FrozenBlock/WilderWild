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

package net.frozenblock.wilderwild.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.frozenblock.wilderwild.datagen.trading.WWVillagerTrades;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.VillagerTradeTags;
import net.minecraft.world.item.trading.VillagerTrade;

public final class WWVillagerTradesTagsProvider extends FabricTagsProvider<VillagerTrade> {

	public WWVillagerTradesTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, Registries.VILLAGER_TRADE, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		this.builder(VillagerTradeTags.FISHERMAN_LEVEL_5)
			.add(WWVillagerTrades.FISHERMAN_5_CRAB_EMERALD)
			.add(WWVillagerTrades.FISHERMAN_5_JELLYFISH_EMERALD)
			.add(WWVillagerTrades.FISHERMAN_5_PALM_BOAT_EMERALD);

		this.builder(VillagerTradeTags.WANDERING_TRADER_COMMON)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_BAOBAB_LOG, WWVillagerTrades.WANDERING_TRADER_EMERALD_BAOBAB_NUT)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_WILLOW_LOG, WWVillagerTrades.WANDERING_TRADER_EMERALD_WILLOW_SAPLING)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_CYPRESS_LOG, WWVillagerTrades.WANDERING_TRADER_EMERALD_CYPRESS_SAPLING)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_PALM_LOG, WWVillagerTrades.WANDERING_TRADER_EMERALD_COCONUT)
			.add(
				WWVillagerTrades.WANDERING_TRADER_EMERALD_MAPLE_LOG,
				WWVillagerTrades.WANDERING_TRADER_EMERALD_YELLOW_MAPLE_SAPLING,
				WWVillagerTrades.WANDERING_TRADER_EMERALD_ORANGE_MAPLE_SAPLING,
				WWVillagerTrades.WANDERING_TRADER_EMERALD_RED_MAPLE_SAPLING
			).add(WWVillagerTrades.WANDERING_TRADER_EMERALD_SEEDING_DANDELION)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_CARNATION)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_PASQUEFLOWER)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_MARIGOLD)
			.add(
				WWVillagerTrades.WANDERING_TRADER_EMERALD_PINK_HIBISCUS,
				WWVillagerTrades.WANDERING_TRADER_EMERALD_RED_HIBISCUS,
				WWVillagerTrades.WANDERING_TRADER_EMERALD_YELLOW_HIBISCUS,
				WWVillagerTrades.WANDERING_TRADER_EMERALD_PURPLE_HIBISCUS,
				WWVillagerTrades.WANDERING_TRADER_EMERALD_WHITE_HIBISCUS
			).add(WWVillagerTrades.WANDERING_TRADER_EMERALD_TUMBLEWEED_PLANT)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_PRICKLY_PEAR)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_ICICLE)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_BARNACLES)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_SEA_ANEMONE)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_SEA_WHIP)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_AUBURN_MOSS_BLOCK)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_ALGAE)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_PLANKTON);

		this.builder(VillagerTradeTags.WANDERING_TRADER_UNCOMMON)
			.add(WWVillagerTrades.WANDERING_TRADER_EMERALD_GEYSER);
	}
}
