/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.model.ButterflyModel;
import net.frozenblock.wilderwild.client.model.CrabModel;
import net.frozenblock.wilderwild.client.model.JellyfishModel;
import net.frozenblock.wilderwild.client.model.OstrichInbredModel;
import net.frozenblock.wilderwild.client.model.OstrichModel;
import net.frozenblock.wilderwild.client.model.TumbleweedModel;
import net.frozenblock.wilderwild.client.renderer.blockentity.DisplayLanternRenderer;
import net.frozenblock.wilderwild.client.renderer.blockentity.HangingTendrilRenderer;
import net.frozenblock.wilderwild.client.renderer.blockentity.SculkSensorRenderer;
import net.frozenblock.wilderwild.client.renderer.blockentity.StoneChestRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.ButterflyRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.CrabRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.FireflyRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.FlowerCowRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.JellyfishRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.OstrichRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.ScorchedRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.TumbleweedRenderer;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;

@Environment(EnvType.CLIENT)
public final class WWModelLayers {
	public static final ModelLayerLocation SCULK_SENSOR = new ModelLayerLocation(WWConstants.id("sculk_sensor"), "main");
	public static final ModelLayerLocation HANGING_TENDRIL = new ModelLayerLocation(WWConstants.id("hanging_tendril"), "main");
	public static final ModelLayerLocation DISPLAY_LANTERN = new ModelLayerLocation(WWConstants.id("display_lantern"), "main");
	public static final ModelLayerLocation STONE_CHEST = new ModelLayerLocation(WWConstants.id("stone_chest"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_LEFT = new ModelLayerLocation(WWConstants.id("double_stone_chest_left"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_RIGHT = new ModelLayerLocation(WWConstants.id("double_stone_chest_right"), "main");
	public static final ModelLayerLocation JELLYFISH = new ModelLayerLocation(WWConstants.id("jellyfish"), "main");
	public static final ModelLayerLocation TUMBLEWEED = new ModelLayerLocation(WWConstants.id("tumbleweed"), "main");
	public static final ModelLayerLocation CRAB = new ModelLayerLocation(WWConstants.id("crab"), "main");
	public static final ModelLayerLocation OSTRICH = new ModelLayerLocation(WWConstants.id("ostrich"), "main");
	public static final ModelLayerLocation OSTRICH_BABY = new ModelLayerLocation(WWConstants.id("ostrich_baby"), "main");
	public static final ModelLayerLocation OSTRICH_INBRED = new ModelLayerLocation(WWConstants.id("ostrich"), "inbred");
	public static final ModelLayerLocation OSTRICH_BABY_INBRED = new ModelLayerLocation(WWConstants.id("ostrich_baby"), "inbred");
	public static final ModelLayerLocation OSTRICH_SADDLE = new ModelLayerLocation(WWConstants.id("ostrich"), "saddle");
	public static final ModelLayerLocation OSTRICH_BABY_SADDLE = new ModelLayerLocation(WWConstants.id("ostrich_baby"), "saddle");
	public static final ModelLayerLocation SCORCHED = new ModelLayerLocation(WWConstants.id("scorched"), "main");
	public static final ModelLayerLocation BUTTERFLY = new ModelLayerLocation(WWConstants.id("butterfly"), "main");
	public static final ModelLayerLocation MOOBLOOM = new ModelLayerLocation(WWConstants.id("moobloom"), "main");
	public static final ModelLayerLocation MOOBLOOM_BABY = new ModelLayerLocation(WWConstants.id("moobloom_baby"), "main");

	// BOATS
	public static final ModelLayerLocation BAOBAB_BOAT = new ModelLayerLocation(WWConstants.id("boat/baobab"), "main");
	public static final ModelLayerLocation BAOBAB_CHEST_BOAT = new ModelLayerLocation(WWConstants.id("chest_boat/baobab"), "main");
	public static final ModelLayerLocation CYPRESS_BOAT = new ModelLayerLocation(WWConstants.id("boat/cypress"), "main");
	public static final ModelLayerLocation CYPRESS_CHEST_BOAT = new ModelLayerLocation(WWConstants.id("chest_boat/cypress"), "main");
	public static final ModelLayerLocation PALM_BOAT = new ModelLayerLocation(WWConstants.id("boat/palm"), "main");
	public static final ModelLayerLocation PALM_CHEST_BOAT = new ModelLayerLocation(WWConstants.id("chest_boat/palm"), "main");
	public static final ModelLayerLocation MAPLE_BOAT = new ModelLayerLocation(WWConstants.id("boat/maple"), "main");
	public static final ModelLayerLocation MAPLE_CHEST_BOAT = new ModelLayerLocation(WWConstants.id("chest_boat/maple"), "main");

	public static void init() {
		EntityRendererRegistry.register(WWEntityTypes.FIREFLY, FireflyRenderer::new);

		EntityRendererRegistry.register(WWEntityTypes.BUTTERFLY, ButterflyRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(BUTTERFLY, ButterflyModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntityTypes.JELLYFISH, JellyfishRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(JELLYFISH, JellyfishModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntityTypes.TUMBLEWEED, TumbleweedRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(TUMBLEWEED, TumbleweedModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntityTypes.CRAB, CrabRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(CRAB, CrabModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntityTypes.OSTRICH, OstrichRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(OSTRICH, OstrichModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH_BABY, OstrichModel::createBabyBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH_INBRED, OstrichInbredModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH_BABY_INBRED, OstrichInbredModel::createBabyBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH_SADDLE, OstrichModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH_BABY_SADDLE, OstrichModel::createBabyBodyLayer);

		EntityRendererRegistry.register(WWEntityTypes.SCORCHED, ScorchedRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(SCORCHED, SpiderModel::createSpiderBodyLayer);

		EntityRendererRegistry.register(WWEntityTypes.MOOBLOOM, FlowerCowRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MOOBLOOM, CowModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(MOOBLOOM_BABY, () -> CowModel.createBodyLayer().apply(CowModel.BABY_TRANSFORMER));

		EntityRendererRegistry.register(WWEntityTypes.COCONUT, ThrownItemRenderer::new);

		EntityRendererRegistry.register(WWEntityTypes.CHEST_BUBBLER, NoopRenderer::new);
		EntityRendererRegistry.register(WWEntityTypes.SCULK_SPREADER, NoopRenderer::new);
		EntityRendererRegistry.register(WWEntityTypes.FALLING_LEAVES, NoopRenderer::new);

		BlockEntityRenderers.register(BlockEntityType.SCULK_SENSOR, SculkSensorRenderer::new);
		BlockEntityRenderers.register(BlockEntityType.CALIBRATED_SCULK_SENSOR, SculkSensorRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(SCULK_SENSOR, SculkSensorRenderer::getTexturedModelData);

		BlockEntityRenderers.register(WWBlockEntityTypes.HANGING_TENDRIL, HangingTendrilRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(HANGING_TENDRIL, HangingTendrilRenderer::getTexturedModelData);

		BlockEntityRenderers.register(WWBlockEntityTypes.DISPLAY_LANTERN, DisplayLanternRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(DISPLAY_LANTERN, DisplayLanternRenderer::getTexturedModelData);

		BlockEntityRenderers.register(WWBlockEntityTypes.STONE_CHEST, StoneChestRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(STONE_CHEST, StoneChestRenderer::createSingleBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(DOUBLE_STONE_CHEST_LEFT, StoneChestRenderer::createDoubleBodyLeftLayer);
		EntityModelLayerRegistry.registerModelLayer(DOUBLE_STONE_CHEST_RIGHT, StoneChestRenderer::createDoubleBodyRightLayer);

		// BOATS
		EntityRendererRegistry.register(WWEntityTypes.BAOBAB_BOAT, context -> new BoatRenderer(context, BAOBAB_BOAT));
		EntityRendererRegistry.register(WWEntityTypes.BAOBAB_CHEST_BOAT, context -> new BoatRenderer(context, BAOBAB_CHEST_BOAT));
		EntityRendererRegistry.register(WWEntityTypes.CYPRESS_BOAT, context -> new BoatRenderer(context, CYPRESS_BOAT));
		EntityRendererRegistry.register(WWEntityTypes.CYPRESS_CHEST_BOAT, context -> new BoatRenderer(context, CYPRESS_CHEST_BOAT));
		EntityRendererRegistry.register(WWEntityTypes.PALM_BOAT, context -> new BoatRenderer(context, PALM_BOAT));
		EntityRendererRegistry.register(WWEntityTypes.PALM_CHEST_BOAT, context -> new BoatRenderer(context, PALM_CHEST_BOAT));
		EntityRendererRegistry.register(WWEntityTypes.MAPLE_BOAT, context -> new BoatRenderer(context, MAPLE_BOAT));
		EntityRendererRegistry.register(WWEntityTypes.MAPLE_CHEST_BOAT, context -> new BoatRenderer(context, MAPLE_CHEST_BOAT));

		LayerDefinition boat = BoatModel.createBoatModel();
		LayerDefinition chestBoat = BoatModel.createChestBoatModel();
		EntityModelLayerRegistry.registerModelLayer(BAOBAB_BOAT, () -> boat);
		EntityModelLayerRegistry.registerModelLayer(BAOBAB_CHEST_BOAT, () -> chestBoat);
		EntityModelLayerRegistry.registerModelLayer(CYPRESS_BOAT, () -> boat);
		EntityModelLayerRegistry.registerModelLayer(CYPRESS_CHEST_BOAT, () -> chestBoat);
		EntityModelLayerRegistry.registerModelLayer(PALM_BOAT, () -> boat);
		EntityModelLayerRegistry.registerModelLayer(PALM_CHEST_BOAT, () -> chestBoat);
		EntityModelLayerRegistry.registerModelLayer(MAPLE_BOAT, () -> boat);
		EntityModelLayerRegistry.registerModelLayer(MAPLE_CHEST_BOAT, () -> chestBoat);
	}
}
