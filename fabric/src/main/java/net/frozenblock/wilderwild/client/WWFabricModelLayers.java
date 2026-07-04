package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.platform.FrozenLibInitPlatformUtils;
import net.frozenblock.lib.renderer.blockentity.BillboardBlockEntityRenderer;
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
import net.frozenblock.wilderwild.client.renderer.blockentity.DisplayLanternRenderer;
import net.frozenblock.wilderwild.client.renderer.blockentity.HangingTendrilRenderer;
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
import net.frozenblock.wilderwild.registry.WWFabricEntityTypes;
import net.minecraft.client.model.animal.cow.BabyCowModel;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.monster.spider.SpiderModel;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import static net.frozenblock.wilderwild.client.WWModelLayers.*;

@Environment(EnvType.CLIENT)
public final class WWFabricModelLayers {

	public static void init() {
		var modelRegistry = FrozenLibInitPlatformUtils.MODEL_LAYER;

		EntityRenderers.register(WWFabricEntityTypes.BUTTERFLY.get(), ButterflyRenderer::new);
		modelRegistry.registerModelLayer(BUTTERFLY, ButterflyModel::createBodyLayer);

		EntityRenderers.register(WWFabricEntityTypes.TUMBLEWEED.get(), TumbleweedRenderer::new);

		EntityRenderers.register(WWFabricEntityTypes.CRAB.get(), CrabRenderer::new);
		modelRegistry.registerModelLayer(CRAB, AdultCrabModel::createBodyLayer);
		modelRegistry.registerModelLayer(CRAB_MOJANG, AdultCrabModel::createMojangBodyLayer);
		modelRegistry.registerModelLayer(CRAB_BABY, BabyCrabModel::createBodyLayer);

		EntityRenderers.register(WWFabricEntityTypes.OSTRICH.get(), OstrichRenderer::new);
		EntityRenderers.register(WWFabricEntityTypes.ZOMBIE_OSTRICH.get(), UndeadOstrichRenderer::new);
		modelRegistry.registerModelLayer(OSTRICH, OstrichModel::createBodyLayer);
		modelRegistry.registerModelLayer(OSTRICH_BABY, BabyOstrichModel::createBodyLayer);
		modelRegistry.registerModelLayer(OSTRICH_INBRED, OstrichInbredModel::createBodyLayer);
		modelRegistry.registerModelLayer(OSTRICH_BABY_INBRED, OstrichInbredModel::createLegacyBabyBodyLayer);
		modelRegistry.registerModelLayer(OSTRICH_SADDLE, OstrichModel::createBodyLayer);
		modelRegistry.registerModelLayer(OSTRICH_BABY_SADDLE, BabyOstrichModel::createBodyLayer);

		EntityRenderers.register(WWFabricEntityTypes.SCORCHED.get(), ScorchedRenderer::new);
		modelRegistry.registerModelLayer(SCORCHED, SpiderModel::createSpiderBodyLayer);

		EntityRenderers.register(WWFabricEntityTypes.MOOBLOOM.get(), FlowerCowRenderer::new);
		modelRegistry.registerModelLayer(MOOBLOOM, CowModel::createBodyLayer);
		modelRegistry.registerModelLayer(MOOBLOOM_BABY, BabyCowModel::createBodyLayer);

		EntityRenderers.register(WWFabricEntityTypes.PENGUIN.get(), PenguinRenderer::new);
		modelRegistry.registerModelLayer(PENGUIN, AdultPenguinModel::createBodyLayer);
		modelRegistry.registerModelLayer(PENGUIN_BABY, BabyPenguinModel::createBodyLayer);

		BlockEntityRenderers.register(WWBlockEntityTypes.HANGING_TENDRIL.get(), HangingTendrilRenderer::new);
		modelRegistry.registerModelLayer(HANGING_TENDRIL, BillboardBlockEntityRenderer::createModelLayer);

		BlockEntityRenderers.register(WWBlockEntityTypes.DISPLAY_LANTERN.get(), DisplayLanternRenderer::new);
		modelRegistry.registerModelLayer(DISPLAY_LANTERN, DisplayLanternRenderer::getTexturedModelData);

		BlockEntityRenderers.register(WWBlockEntityTypes.STONE_CHEST.get(), StoneChestRenderer::new);
		modelRegistry.registerModelLayer(STONE_CHEST, StoneChestModel::createSingleBodyLayer);
		modelRegistry.registerModelLayer(DOUBLE_STONE_CHEST_LEFT, StoneChestModel::createDoubleBodyLeftLayer);
		modelRegistry.registerModelLayer(DOUBLE_STONE_CHEST_RIGHT, StoneChestModel::createDoubleBodyRightLayer);

		// BOATS
		EntityRenderers.register(WWFabricEntityTypes.BAOBAB_BOAT.get(), context -> new BoatRenderer(context, BAOBAB_BOAT));
		EntityRenderers.register(WWFabricEntityTypes.BAOBAB_CHEST_BOAT.get(), context -> new BoatRenderer(context, BAOBAB_CHEST_BOAT));
		EntityRenderers.register(WWFabricEntityTypes.WILLOW_BOAT.get(), context -> new BoatRenderer(context, WILLOW_BOAT));
		EntityRenderers.register(WWFabricEntityTypes.WILLOW_CHEST_BOAT.get(), context -> new BoatRenderer(context, WILLOW_CHEST_BOAT));
		EntityRenderers.register(WWFabricEntityTypes.CYPRESS_BOAT.get(), context -> new BoatRenderer(context, CYPRESS_BOAT));
		EntityRenderers.register(WWFabricEntityTypes.CYPRESS_CHEST_BOAT.get(), context -> new BoatRenderer(context, CYPRESS_CHEST_BOAT));
		EntityRenderers.register(WWFabricEntityTypes.PALM_BOAT.get(), context -> new BoatRenderer(context, PALM_BOAT));
		EntityRenderers.register(WWFabricEntityTypes.PALM_CHEST_BOAT.get(), context -> new BoatRenderer(context, PALM_CHEST_BOAT));
		EntityRenderers.register(WWFabricEntityTypes.MAPLE_BOAT.get(), context -> new BoatRenderer(context, MAPLE_BOAT));
		EntityRenderers.register(WWFabricEntityTypes.MAPLE_CHEST_BOAT.get(), context -> new BoatRenderer(context, MAPLE_CHEST_BOAT));

		final LayerDefinition boat = BoatModel.createBoatModel();
		final LayerDefinition chestBoat = BoatModel.createChestBoatModel();
		modelRegistry.registerModelLayer(BAOBAB_BOAT, () -> boat);
		modelRegistry.registerModelLayer(BAOBAB_CHEST_BOAT, () -> chestBoat);
		modelRegistry.registerModelLayer(WILLOW_BOAT, () -> boat);
		modelRegistry.registerModelLayer(WILLOW_CHEST_BOAT, () -> chestBoat);
		modelRegistry.registerModelLayer(CYPRESS_BOAT, () -> boat);
		modelRegistry.registerModelLayer(CYPRESS_CHEST_BOAT, () -> chestBoat);
		modelRegistry.registerModelLayer(PALM_BOAT, () -> boat);
		modelRegistry.registerModelLayer(PALM_CHEST_BOAT, () -> chestBoat);
		modelRegistry.registerModelLayer(MAPLE_BOAT, () -> boat);
		modelRegistry.registerModelLayer(MAPLE_CHEST_BOAT, () -> chestBoat);
	}
}
