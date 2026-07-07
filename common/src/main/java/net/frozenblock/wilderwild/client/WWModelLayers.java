package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.platform.FrozenLibInitPlatformUtils;
import net.frozenblock.lib.platform.service.ModelLayerHelper;
import net.frozenblock.lib.renderer.blockentity.BillboardBlockEntityRenderer;
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
import net.frozenblock.wilderwild.client.model.object.chest.StoneChestModel;
import net.frozenblock.wilderwild.client.model.object.sculksensor.SculkSensorModel;
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
import net.frozenblock.wilderwild.client.renderer.entity.PenguinRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.ScorchedRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.TumbleweedRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.UndeadOstrichRenderer;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.minecraft.client.model.animal.cow.BabyCowModel;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.monster.spider.SpiderModel;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.level.block.entity.BlockEntityTypes;

@Environment(EnvType.CLIENT)
public final class WWModelLayers {
	public static final ModelLayerLocation SCULK_SENSOR = new ModelLayerLocation(WWConstants.id("sculk_sensor"), "main");
	public static final ModelLayerLocation HANGING_TENDRIL = new ModelLayerLocation(WWConstants.id("hanging_tendril"), "main");
	public static final ModelLayerLocation DISPLAY_LANTERN = new ModelLayerLocation(WWConstants.id("display_lantern"), "main");
	public static final ModelLayerLocation STONE_CHEST = new ModelLayerLocation(WWConstants.id("stone_chest"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_LEFT = new ModelLayerLocation(WWConstants.id("double_stone_chest_left"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_RIGHT = new ModelLayerLocation(WWConstants.id("double_stone_chest_right"), "main");

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
		final ModelLayerHelper modelLayers = FrozenLibInitPlatformUtils.MODEL_LAYER;

		modelLayers.registerModelLayer(SCULK_SENSOR, SculkSensorModel::createModelLayer);

		modelLayers.registerModelLayer(BUTTERFLY, ButterflyModel::createBodyLayer);

		modelLayers.registerModelLayer(JELLYFISH, JellyfishModel::createBodyLayer);
		modelLayers.registerModelLayer(JELLYFISH_BABY, BabyJellyfishModel::createBodyLayer);

		modelLayers.registerModelLayer(CRAB, AdultCrabModel::createBodyLayer);
		modelLayers.registerModelLayer(CRAB_MOJANG, AdultCrabModel::createMojangBodyLayer);
		modelLayers.registerModelLayer(CRAB_BABY, BabyCrabModel::createBodyLayer);

		modelLayers.registerModelLayer(OSTRICH, OstrichModel::createBodyLayer);
		modelLayers.registerModelLayer(OSTRICH_BABY, BabyOstrichModel::createBodyLayer);
		modelLayers.registerModelLayer(OSTRICH_INBRED, OstrichInbredModel::createBodyLayer);
		modelLayers.registerModelLayer(OSTRICH_BABY_INBRED, OstrichInbredModel::createLegacyBabyBodyLayer);
		modelLayers.registerModelLayer(OSTRICH_SADDLE, OstrichModel::createBodyLayer);
		modelLayers.registerModelLayer(OSTRICH_BABY_SADDLE, BabyOstrichModel::createBodyLayer);

		modelLayers.registerModelLayer(SCORCHED, SpiderModel::createSpiderBodyLayer);

		modelLayers.registerModelLayer(MOOBLOOM, CowModel::createBodyLayer);
		modelLayers.registerModelLayer(MOOBLOOM_BABY, BabyCowModel::createBodyLayer);

		modelLayers.registerModelLayer(PENGUIN, AdultPenguinModel::createBodyLayer);
		modelLayers.registerModelLayer(PENGUIN_BABY, BabyPenguinModel::createBodyLayer);

		modelLayers.registerModelLayer(HANGING_TENDRIL, BillboardBlockEntityRenderer::createModelLayer);

		modelLayers.registerModelLayer(DISPLAY_LANTERN, DisplayLanternRenderer::getTexturedModelData);

		modelLayers.registerModelLayer(STONE_CHEST, StoneChestModel::createSingleBodyLayer);
		modelLayers.registerModelLayer(DOUBLE_STONE_CHEST_LEFT, StoneChestModel::createDoubleBodyLeftLayer);
		modelLayers.registerModelLayer(DOUBLE_STONE_CHEST_RIGHT, StoneChestModel::createDoubleBodyRightLayer);

		final LayerDefinition boat = BoatModel.createBoatModel();
		final LayerDefinition chestBoat = BoatModel.createChestBoatModel();
		modelLayers.registerBoatModelLayers(BAOBAB_BOAT, BAOBAB_CHEST_BOAT, boat, chestBoat);
		modelLayers.registerBoatModelLayers(WILLOW_BOAT, WILLOW_CHEST_BOAT, boat, chestBoat);
		modelLayers.registerBoatModelLayers(CYPRESS_BOAT, CYPRESS_CHEST_BOAT, boat, chestBoat);
		modelLayers.registerBoatModelLayers(PALM_BOAT, PALM_CHEST_BOAT, boat, chestBoat);
		modelLayers.registerBoatModelLayers(MAPLE_BOAT, MAPLE_CHEST_BOAT, boat, chestBoat);
	}

	// This is called at a different point on Fabric vs. Neo
	public static void setupRenderers() {
		EntityRenderers.register(WWEntityTypes.FIREFLY.get(), FireflyRenderer::new);
		EntityRenderers.register(WWEntityTypes.BUTTERFLY.get(), ButterflyRenderer::new);
		EntityRenderers.register(WWEntityTypes.JELLYFISH.get(), JellyfishRenderer::new);
		EntityRenderers.register(WWEntityTypes.TUMBLEWEED.get(), TumbleweedRenderer::new);
		EntityRenderers.register(WWEntityTypes.CRAB.get(), CrabRenderer::new);
		EntityRenderers.register(WWEntityTypes.OSTRICH.get(), OstrichRenderer::new);
		EntityRenderers.register(WWEntityTypes.ZOMBIE_OSTRICH.get(), UndeadOstrichRenderer::new);
		EntityRenderers.register(WWEntityTypes.SCORCHED.get(), ScorchedRenderer::new);
		EntityRenderers.register(WWEntityTypes.MOOBLOOM.get(), FlowerCowRenderer::new);
		EntityRenderers.register(WWEntityTypes.PENGUIN.get(), PenguinRenderer::new);
		EntityRenderers.register(WWEntityTypes.COCONUT.get(), ThrownItemRenderer::new);
		EntityRenderers.register(WWEntityTypes.FALLING_LEAVES.get(), NoopRenderer::new);

		// TODO: check if these need something different on neo
		BlockEntityRenderers.register(BlockEntityTypes.SCULK_SENSOR, SculkSensorRenderer::new);
		BlockEntityRenderers.register(BlockEntityTypes.CALIBRATED_SCULK_SENSOR, SculkSensorRenderer::new);
		BlockEntityRenderers.register(WWBlockEntityTypes.HANGING_TENDRIL.get(), HangingTendrilRenderer::new);
		BlockEntityRenderers.register(WWBlockEntityTypes.DISPLAY_LANTERN.get(), DisplayLanternRenderer::new);
		BlockEntityRenderers.register(WWBlockEntityTypes.STONE_CHEST.get(), StoneChestRenderer::new);

		// BOATS
		EntityRenderers.register(WWEntityTypes.BAOBAB_BOAT.get(), context -> new BoatRenderer(context, BAOBAB_BOAT));
		EntityRenderers.register(WWEntityTypes.BAOBAB_CHEST_BOAT.get(), context -> new BoatRenderer(context, BAOBAB_CHEST_BOAT));
		EntityRenderers.register(WWEntityTypes.WILLOW_BOAT.get(), context -> new BoatRenderer(context, WILLOW_BOAT));
		EntityRenderers.register(WWEntityTypes.WILLOW_CHEST_BOAT.get(), context -> new BoatRenderer(context, WILLOW_CHEST_BOAT));
		EntityRenderers.register(WWEntityTypes.CYPRESS_BOAT.get(), context -> new BoatRenderer(context, CYPRESS_BOAT));
		EntityRenderers.register(WWEntityTypes.CYPRESS_CHEST_BOAT.get(), context -> new BoatRenderer(context, CYPRESS_CHEST_BOAT));
		EntityRenderers.register(WWEntityTypes.PALM_BOAT.get(), context -> new BoatRenderer(context, PALM_BOAT));
		EntityRenderers.register(WWEntityTypes.PALM_CHEST_BOAT.get(), context -> new BoatRenderer(context, PALM_CHEST_BOAT));
		EntityRenderers.register(WWEntityTypes.MAPLE_BOAT.get(), context -> new BoatRenderer(context, MAPLE_BOAT));
		EntityRenderers.register(WWEntityTypes.MAPLE_CHEST_BOAT.get(), context -> new BoatRenderer(context, MAPLE_CHEST_BOAT));
	}
}
