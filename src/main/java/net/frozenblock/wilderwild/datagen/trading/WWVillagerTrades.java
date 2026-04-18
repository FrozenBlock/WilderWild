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

package net.frozenblock.wilderwild.datagen.trading;

import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.PotionTags;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.item.trading.VillagerTrades;
import net.minecraft.world.level.ItemLike;

public final class WWVillagerTrades {
	public static final ResourceKey<VillagerTrade> FISHERMAN_5_CRAB_EMERALD = resourceKey("fisherman/5/crab_emerald");
	public static final ResourceKey<VillagerTrade> FISHERMAN_5_JELLYFISH_EMERALD = resourceKey("fisherman/5/jellyfish_emerald");
	public static final ResourceKey<VillagerTrade> FISHERMAN_5_PALM_BOAT_EMERALD = resourceKey("fisherman/5/palm_boat_emerald");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BAOBAB_LOG = resourceKey("wandering_trader/emerald_baobab_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BAOBAB_NUT = resourceKey("wandering_trader/emerald_baobab_nut");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_WILLOW_LOG = resourceKey("wandering_trader/emerald_willow_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_WILLOW_SAPLING = resourceKey("wandering_trader/emerald_willow_sapling");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_CYPRESS_LOG = resourceKey("wandering_trader/emerald_cypress_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_CYPRESS_SAPLING = resourceKey("wandering_trader/emerald_cypress_sapling");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PALM_LOG = resourceKey("wandering_trader/emerald_palm_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_COCONUT = resourceKey("wandering_trader/emerald_coconut");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_MAPLE_LOG = resourceKey("wandering_trader/emerald_maple_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_YELLOW_MAPLE_SAPLING = resourceKey("wandering_trader/emerald_yellow_maple_sapling");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_ORANGE_MAPLE_SAPLING = resourceKey("wandering_trader/emerald_orange_maple_sapling");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_RED_MAPLE_SAPLING = resourceKey("wandering_trader/emerald_red_maple_sapling");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SEEDING_DANDELION = resourceKey("wandering_trader/emerald_seeding_dandelion");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_CARNATION = resourceKey("wandering_trader/emerald_carnation");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PASQUEFLOWER = resourceKey("wandering_trader/emerald_pasqueflower");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_MARIGOLD = resourceKey("wandering_trader/emerald_marigold");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PINK_HIBISCUS = resourceKey("wandering_trader/emerald_pink_hibiscus");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_RED_HIBISCUS = resourceKey("wandering_trader/emerald_red_hibiscus");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_YELLOW_HIBISCUS = resourceKey("wandering_trader/emerald_yellow_hibiscus");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PURPLE_HIBISCUS = resourceKey("wandering_trader/emerald_purple_hibiscus");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_WHITE_HIBISCUS = resourceKey("wandering_trader/emerald_white_hibiscus");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_TUMBLEWEED_PLANT = resourceKey("wandering_trader/emerald_tumbleweed_plant");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PRICKLY_PEAR = resourceKey("wandering_trader/emerald_prickly_pear");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_ICICLE = resourceKey("wandering_trader/emerald_icicle");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BARNACLES = resourceKey("wandering_trader/emerald_barnacles");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SEA_ANEMONE = resourceKey("wandering_trader/emerald_sea_anemone");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SEA_WHIP = resourceKey("wandering_trader/emerald_sea_whip");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_AUBURN_MOSS_BLOCK = resourceKey("wandering_trader/emerald_auburn_moss_block");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_ALGAE = resourceKey("wandering_trader/emerald_algae");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PLANKTON = resourceKey("wandering_trader/emerald_plankton");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_GEYSER = resourceKey("wandering_trader/emerald_geyser");

	public static void bootstrap(BootstrapContext<VillagerTrade> context) {
		final HolderGetter<Item> items = context.lookup(Registries.ITEM);
		final Optional<HolderSet<Enchantment>> enchantmentsForTradedEquipment = context.lookup(Registries.ENCHANTMENT)
			.get(EnchantmentTags.ON_TRADED_EQUIPMENT)
			.map(named -> named);
		final Optional<HolderSet<Enchantment>> enchantmentsForBooks = context.lookup(Registries.ENCHANTMENT).get(EnchantmentTags.TRADEABLE).map(named -> named);
		final Optional<HolderSet<Enchantment>> doubleTradePrice = context.lookup(Registries.ENCHANTMENT).get(EnchantmentTags.DOUBLE_TRADE_PRICE).map(named -> named);
		final Optional<HolderSet<Potion>> potionsForTippedArrows = context.lookup(Registries.POTION).get(PotionTags.TRADEABLE).map(named -> named);
		final HolderGetter<VillagerType> villagerVariants = context.lookup(Registries.VILLAGER_TYPE);

		// FISHERMAN

		VillagerTrades.register(
			context,
			FISHERMAN_5_CRAB_EMERALD,
			new VillagerTrade(
				new TradeCost(WWItems.CRAB_BUCKET, 4),
				new ItemStackTemplate(Items.EMERALD),
				12,
				30,
				0.05F,
				Optional.empty(),
				List.of()
			)
		);
		VillagerTrades.register(
			context,
			FISHERMAN_5_JELLYFISH_EMERALD,
			new VillagerTrade(
				new TradeCost(WWItems.JELLYFISH_BUCKET, 4),
				new ItemStackTemplate(Items.EMERALD),
				12,
				30,
				0.05F,
				Optional.empty(),
				List.of()
			)
		);

		VillagerTrades.register(
			context,
			FISHERMAN_5_PALM_BOAT_EMERALD,
			new VillagerTrade(
				new TradeCost(WWItems.PALM_BOAT, 1),
				new ItemStackTemplate(Items.EMERALD),
				12,
				30,
				0.05F,
				VillagerTrades.villagerTypeRestriction(VillagerTrades.villagerTypeHolderSet(villagerVariants, List.of(VillagerType.DESERT))),
				List.of()
			)
		);

		// WANDERING TRADER

		context.register(
			WANDERING_TRADER_EMERALD_BAOBAB_LOG,
			emeraldTrade(1, WWItems.BAOBAB_LOG, 8, 4, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_BAOBAB_NUT,
			emeraldTrade(5, WWItems.BAOBAB_NUT, 1, 8, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_WILLOW_LOG,
			emeraldTrade(1, WWItems.WILLOW_LOG, 8, 4, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_WILLOW_SAPLING,
			emeraldTrade(5, WWItems.WILLOW_SAPLING, 1, 8, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_CYPRESS_LOG,
			emeraldTrade(1, WWItems.CYPRESS_LOG, 8, 4, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_CYPRESS_SAPLING,
			emeraldTrade(5, WWItems.CYPRESS_SAPLING, 1, 8, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_PALM_LOG,
			emeraldTrade(1, WWItems.PALM_LOG, 8, 4, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_COCONUT,
			emeraldTrade(5, WWItems.COCONUT, 1, 8, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_MAPLE_LOG,
			emeraldTrade(1, WWItems.MAPLE_LOG, 8, 4, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_YELLOW_MAPLE_SAPLING,
			emeraldTrade(5, WWItems.YELLOW_MAPLE_SAPLING, 1, 8, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_ORANGE_MAPLE_SAPLING,
			emeraldTrade(5, WWItems.ORANGE_MAPLE_SAPLING, 1, 8, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_RED_MAPLE_SAPLING,
			emeraldTrade(5, WWItems.RED_MAPLE_SAPLING, 1, 8, 1)
		);

		context.register(
			WANDERING_TRADER_EMERALD_SEEDING_DANDELION,
			emeraldTrade(1, WWItems.SEEDING_DANDELION, 1, 8, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_CARNATION,
			emeraldTrade(1, WWItems.CARNATION, 1, 8, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_PASQUEFLOWER,
			emeraldTrade(1, WWItems.PASQUEFLOWER, 1, 7, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_MARIGOLD,
			emeraldTrade(1, WWItems.MARIGOLD, 1, 8, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_PINK_HIBISCUS,
			emeraldTrade(1, WWItems.PINK_HIBISCUS, 1, 8, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_RED_HIBISCUS,
			emeraldTrade(1, WWItems.RED_HIBISCUS, 1, 8, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_YELLOW_HIBISCUS,
			emeraldTrade(1, WWItems.YELLOW_HIBISCUS, 1, 8, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_PURPLE_HIBISCUS,
			emeraldTrade(1, WWItems.PURPLE_HIBISCUS, 1, 8, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_WHITE_HIBISCUS,
			emeraldTrade(1, WWItems.WHITE_HIBISCUS, 1, 8, 1)
		);

		context.register(
			WANDERING_TRADER_EMERALD_TUMBLEWEED_PLANT,
			emeraldTrade(1, WWItems.TUMBLEWEED_PLANT, 1, 4, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_PRICKLY_PEAR,
			emeraldTrade(1, WWItems.PRICKLY_PEAR, 1, 12, 1)
		);

		context.register(
			WANDERING_TRADER_EMERALD_ICICLE,
			emeraldTrade(1, WWItems.ICICLE, 2, 5, 1)
		);

		context.register(
			WANDERING_TRADER_EMERALD_BARNACLES,
			emeraldTrade(1, WWItems.BARNACLES, 2, 5, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_SEA_ANEMONE,
			emeraldTrade(1, WWItems.SEA_ANEMONE, 2, 5, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_SEA_WHIP,
			emeraldTrade(1, WWItems.SEA_WHIP, 2, 5, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_AUBURN_MOSS_BLOCK,
			emeraldTrade(1, WWItems.AUBURN_MOSS_BLOCK, 2, 5, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_ALGAE,
			emeraldTrade(1, WWItems.ALGAE, 2, 5, 1)
		);
		context.register(
			WANDERING_TRADER_EMERALD_PLANKTON,
			emeraldTrade(1, WWItems.PLANKTON, 2, 5, 1)
		);

		context.register(
			WANDERING_TRADER_EMERALD_GEYSER,
			emeraldTrade(6, WWItems.GEYSER, 1, 4, 1)
		);
	}

	private static VillagerTrade emeraldTrade(int emeraldCount, ItemLike gives, int givesCount, int maxUses, int xp) {
		return new VillagerTrade(
			new TradeCost(Items.EMERALD, emeraldCount),
			new ItemStackTemplate(gives.asItem(), givesCount),
			maxUses,
			xp,
			0.05F,
			Optional.empty(),
			List.of()
		);
	}

	public static ResourceKey<VillagerTrade> resourceKey(String path) {
		return ResourceKey.create(Registries.VILLAGER_TRADE, WWConstants.id(path));
	}
}
