/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.client;

import net.frozenblock.lib.renderer.blockentity.BillboardBlockEntityRenderer;
import net.frozenblock.lib.renderer.model.ModelLayerRegistry;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.model.ambient.ButterflyModel;
import net.frozenblock.wilderwild.client.model.animal.crab.AdultCrabModel;
import net.frozenblock.wilderwild.client.model.animal.crab.BabyCrabModel;
import net.frozenblock.wilderwild.client.model.animal.jellyfish.BabyJellyfishModel;
import net.frozenblock.wilderwild.client.model.animal.jellyfish.JellyfishModel;
import net.frozenblock.wilderwild.client.model.animal.ostrich.BabyOstrichModel;
import net.frozenblock.wilderwild.client.model.animal.ostrich.OstrichInbredModel;
import net.frozenblock.wilderwild.client.model.animal.ostrich.OstrichModel;
import net.frozenblock.wilderwild.client.model.animal.penguin.AdultPenguinModel;
import net.frozenblock.wilderwild.client.model.animal.penguin.BabyPenguinModel;
import net.frozenblock.wilderwild.client.model.monster.skeleton.SunkenModel;
import net.frozenblock.wilderwild.client.model.object.chest.StoneChestModel;
import net.frozenblock.wilderwild.client.model.object.sculksensor.SculkSensorModel;
import net.frozenblock.wilderwild.client.renderer.blockentity.DisplayLanternRenderer;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.model.animal.cow.BabyCowModel;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.monster.spider.SpiderModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;

@ClientOnly
public final class WWModelLayers {
	// BLOCK ENTITY
	public static final ModelLayerLocation SCULK_SENSOR = new ModelLayerLocation(WWConstants.id("sculk_sensor"), "main");
	public static final ModelLayerLocation HANGING_TENDRIL = new ModelLayerLocation(WWConstants.id("hanging_tendril"), "main");
	public static final ModelLayerLocation DISPLAY_LANTERN = new ModelLayerLocation(WWConstants.id("display_lantern"), "main");
	public static final ModelLayerLocation STONE_CHEST = new ModelLayerLocation(WWConstants.id("stone_chest"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_LEFT = new ModelLayerLocation(WWConstants.id("double_stone_chest_left"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_RIGHT = new ModelLayerLocation(WWConstants.id("double_stone_chest_right"), "main");

	// ENTITY
	public static final ModelLayerLocation JELLYFISH = new ModelLayerLocation(WWConstants.id("jellyfish"), "main");
	public static final ModelLayerLocation JELLYFISH_BABY = new ModelLayerLocation(WWConstants.id("jellyfish_baby"), "main");

	public static final ModelLayerLocation CRAB = new ModelLayerLocation(WWConstants.id("crab"), "main");
	public static final ModelLayerLocation CRAB_MOJANG = new ModelLayerLocation(WWConstants.id("crab"), "mojang");
	public static final ModelLayerLocation CRAB_BABY = new ModelLayerLocation(WWConstants.id("crab_baby"), "main");

	public static final ModelLayerLocation OSTRICH = new ModelLayerLocation(WWConstants.id("ostrich"), "main");
	public static final ModelLayerLocation OSTRICH_BABY = new ModelLayerLocation(WWConstants.id("ostrich_baby"), "main");
	public static final ModelLayerLocation OSTRICH_INBRED = new ModelLayerLocation(WWConstants.id("ostrich"), "inbred");
	public static final ModelLayerLocation OSTRICH_BABY_INBRED = new ModelLayerLocation(WWConstants.id("ostrich_baby"), "inbred");
	public static final ModelLayerLocation OSTRICH_SADDLE = new ModelLayerLocation(WWConstants.id("ostrich"), "saddle");
	public static final ModelLayerLocation OSTRICH_BABY_SADDLE = new ModelLayerLocation(WWConstants.id("ostrich_baby"), "saddle");

	public static final ModelLayerLocation PENGUIN = new ModelLayerLocation(WWConstants.id("penguin"), "main");
	public static final ModelLayerLocation PENGUIN_BABY = new ModelLayerLocation(WWConstants.id("penguin_baby"), "main");

	public static final ModelLayerLocation SCORCHED = new ModelLayerLocation(WWConstants.id("scorched"), "main");

	public static final ModelLayerLocation SUNKEN = new ModelLayerLocation(WWConstants.id("sunken"), "main");
	public static final ArmorModelSet<ModelLayerLocation> SUNKEN_ARMOR = createArmorSet("sunken");

	public static final ModelLayerLocation BUTTERFLY = new ModelLayerLocation(WWConstants.id("butterfly"), "main");

	public static final ModelLayerLocation MOOBLOOM = new ModelLayerLocation(WWConstants.id("moobloom"), "main");
	public static final ModelLayerLocation MOOBLOOM_BABY = new ModelLayerLocation(WWConstants.id("moobloom_baby"), "main");

	// BOATS
	public static final ModelLayerLocation BAOBAB_BOAT = new ModelLayerLocation(WWConstants.id("boat/baobab"), "main");
	public static final ModelLayerLocation BAOBAB_CHEST_BOAT = new ModelLayerLocation(WWConstants.id("chest_boat/baobab"), "main");
	public static final ModelLayerLocation WILLOW_BOAT = new ModelLayerLocation(WWConstants.id("boat/willow"), "main");
	public static final ModelLayerLocation WILLOW_CHEST_BOAT = new ModelLayerLocation(WWConstants.id("chest_boat/willow"), "main");
	public static final ModelLayerLocation CYPRESS_BOAT = new ModelLayerLocation(WWConstants.id("boat/cypress"), "main");
	public static final ModelLayerLocation CYPRESS_CHEST_BOAT = new ModelLayerLocation(WWConstants.id("chest_boat/cypress"), "main");
	public static final ModelLayerLocation PALM_BOAT = new ModelLayerLocation(WWConstants.id("boat/palm"), "main");
	public static final ModelLayerLocation PALM_CHEST_BOAT = new ModelLayerLocation(WWConstants.id("chest_boat/palm"), "main");
	public static final ModelLayerLocation MAPLE_BOAT = new ModelLayerLocation(WWConstants.id("boat/maple"), "main");
	public static final ModelLayerLocation MAPLE_CHEST_BOAT = new ModelLayerLocation(WWConstants.id("chest_boat/maple"), "main");

	public static void init() {
		// BLOCK ENTITY
		ModelLayerRegistry.register(SCULK_SENSOR, SculkSensorModel::createModelLayer);

		ModelLayerRegistry.register(HANGING_TENDRIL, BillboardBlockEntityRenderer::createModelLayer);

		ModelLayerRegistry.register(DISPLAY_LANTERN, DisplayLanternRenderer::getTexturedModelData);

		ModelLayerRegistry.register(STONE_CHEST, StoneChestModel::createSingleBodyLayer);
		ModelLayerRegistry.register(DOUBLE_STONE_CHEST_LEFT, StoneChestModel::createDoubleBodyLeftLayer);
		ModelLayerRegistry.register(DOUBLE_STONE_CHEST_RIGHT, StoneChestModel::createDoubleBodyRightLayer);

		// ENTITY
		ModelLayerRegistry.register(BUTTERFLY, ButterflyModel::createBodyLayer);

		ModelLayerRegistry.register(JELLYFISH, JellyfishModel::createBodyLayer);
		ModelLayerRegistry.register(JELLYFISH_BABY, BabyJellyfishModel::createBodyLayer);

		ModelLayerRegistry.register(CRAB, AdultCrabModel::createBodyLayer);
		ModelLayerRegistry.register(CRAB_MOJANG, AdultCrabModel::createMojangBodyLayer);
		ModelLayerRegistry.register(CRAB_BABY, BabyCrabModel::createBodyLayer);

		ModelLayerRegistry.register(OSTRICH, OstrichModel::createBodyLayer);
		ModelLayerRegistry.register(OSTRICH_BABY, BabyOstrichModel::createBodyLayer);
		ModelLayerRegistry.register(OSTRICH_INBRED, OstrichInbredModel::createBodyLayer);
		ModelLayerRegistry.register(OSTRICH_BABY_INBRED, OstrichInbredModel::createLegacyBabyBodyLayer);
		ModelLayerRegistry.register(OSTRICH_SADDLE, OstrichModel::createBodyLayer);
		ModelLayerRegistry.register(OSTRICH_BABY_SADDLE, BabyOstrichModel::createBodyLayer);

		ModelLayerRegistry.register(SCORCHED, SpiderModel::createSpiderBodyLayer);

		ModelLayerRegistry.register(SUNKEN, SunkenModel::createBodyLayer);
		ModelLayerRegistry.registerHumanoidArmorSet(SUNKEN_ARMOR);

		ModelLayerRegistry.register(MOOBLOOM, CowModel::createBodyLayer);
		ModelLayerRegistry.register(MOOBLOOM_BABY, BabyCowModel::createBodyLayer);

		ModelLayerRegistry.register(PENGUIN, AdultPenguinModel::createBodyLayer);
		ModelLayerRegistry.register(PENGUIN_BABY, BabyPenguinModel::createBodyLayer);

		// BOATS
		ModelLayerRegistry.registerBoats(BAOBAB_BOAT, BAOBAB_CHEST_BOAT);
		ModelLayerRegistry.registerBoats(WILLOW_BOAT, WILLOW_CHEST_BOAT);
		ModelLayerRegistry.registerBoats(CYPRESS_BOAT, CYPRESS_CHEST_BOAT);
		ModelLayerRegistry.registerBoats(PALM_BOAT, PALM_CHEST_BOAT);
		ModelLayerRegistry.registerBoats(MAPLE_BOAT, MAPLE_CHEST_BOAT);
	}

	private WWModelLayers() {}

	private static ArmorModelSet<ModelLayerLocation> createArmorSet(String modelId) {
		return new ArmorModelSet<>(
			new ModelLayerLocation(WWConstants.id(modelId), "helmet"),
			new ModelLayerLocation(WWConstants.id(modelId), "chestplate"),
			new ModelLayerLocation(WWConstants.id(modelId), "leggings"),
			new ModelLayerLocation(WWConstants.id(modelId), "boots")
		);
	}
}
