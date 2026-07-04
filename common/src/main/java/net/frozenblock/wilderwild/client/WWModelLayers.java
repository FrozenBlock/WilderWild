package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.platform.FrozenLibInitPlatformUtils;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.model.object.sculksensor.SculkSensorModel;
import net.frozenblock.wilderwild.client.renderer.blockentity.SculkSensorRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.FireflyRenderer;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;
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
		var modelRegistry = FrozenLibInitPlatformUtils.MODEL_LAYER;
		var blockEntityRenderers = FrozenLibInitPlatformUtils.BLOCK_ENTITY_RENDERER;

		blockEntityRenderers.register(BlockEntityTypes.SCULK_SENSOR, SculkSensorRenderer::new);
		blockEntityRenderers.register(BlockEntityTypes.CALIBRATED_SCULK_SENSOR, SculkSensorRenderer::new);
		modelRegistry.registerModelLayer(SCULK_SENSOR, SculkSensorModel::createModelLayer);
	}

	public static void setupInit() {
		EntityRenderers.register(WWEntityTypes.FIREFLY.get(), FireflyRenderer::new);
	}
}
