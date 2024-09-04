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

package net.frozenblock.wilderwild;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Objects;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.frozenblock.lib.config.frozenlib_config.FrozenLibConfig;
import net.frozenblock.lib.debug.client.api.DebugRendererEvents;
import net.frozenblock.lib.debug.client.impl.DebugRenderManager;
import net.frozenblock.lib.item.api.ItemBlockStateTagUtils;
import net.frozenblock.lib.liquid.render.api.LiquidRenderUtils;
import net.frozenblock.lib.menu.api.Panoramas;
import net.frozenblock.lib.menu.api.SplashTextAPI;
import net.frozenblock.lib.sound.api.FlyBySoundHub;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.debug.client.renderer.OstrichDebugRenderer;
import net.frozenblock.wilderwild.entity.render.blockentity.DisplayLanternBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.blockentity.HangingTendrilBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.blockentity.SculkSensorBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.blockentity.StoneChestBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.easter.WilderEasterEggs;
import net.frozenblock.wilderwild.entity.render.model.AncientHornProjectileModel;
import net.frozenblock.wilderwild.entity.render.model.CrabModel;
import net.frozenblock.wilderwild.entity.render.model.JellyfishModel;
import net.frozenblock.wilderwild.entity.render.model.OstrichInbredModel;
import net.frozenblock.wilderwild.entity.render.model.OstrichModel;
import net.frozenblock.wilderwild.entity.render.model.ScorchedModel;
import net.frozenblock.wilderwild.entity.render.model.TumbleweedModel;
import net.frozenblock.wilderwild.entity.render.renderer.AncientHornProjectileRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.CrabRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.FireflyRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.JellyfishRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.OstrichRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.ScorchedRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.TumbleweedRenderer;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.networking.WilderClientNetworking;
import net.frozenblock.wilderwild.particle.FallingParticle;
import net.frozenblock.wilderwild.particle.FloatingSculkBubbleParticle;
import net.frozenblock.wilderwild.particle.LeafClusterSeedParticle;
import net.frozenblock.wilderwild.particle.LeafParticle;
import net.frozenblock.wilderwild.particle.MesogleaDripParticle;
import net.frozenblock.wilderwild.particle.PollenParticle;
import net.frozenblock.wilderwild.particle.SeedParticle;
import net.frozenblock.wilderwild.particle.TermiteParticle;
import net.frozenblock.wilderwild.particle.WindParticle;
import net.frozenblock.wilderwild.particle.factory.WilderParticleFactories;
import net.frozenblock.wilderwild.registry.WWBlockEntities;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWEntities;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWParticles;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public final class WilderWildClient implements ClientModInitializer {
	public static final ModelLayerLocation ANCIENT_HORN_PROJECTILE_LAYER = new ModelLayerLocation(WilderConstants.id("ancient_horn_projectile"), "main");
	public static final ModelLayerLocation SCULK_SENSOR = new ModelLayerLocation(WilderConstants.id("sculk_sensor"), "main");
	public static final ModelLayerLocation HANGING_TENDRIL = new ModelLayerLocation(WilderConstants.id("hanging_tendril"), "main");
	public static final ModelLayerLocation DISPLAY_LANTERN = new ModelLayerLocation(WilderConstants.id("display_lantern"), "main");
	public static final ModelLayerLocation STONE_CHEST = new ModelLayerLocation(WilderConstants.id("stone_chest"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_LEFT = new ModelLayerLocation(WilderConstants.id("double_stone_chest_left"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_RIGHT = new ModelLayerLocation(WilderConstants.id("double_stone_chest_right"), "main");
	public static final ModelLayerLocation JELLYFISH = new ModelLayerLocation(WilderConstants.id("jellyfish"), "main");
	public static final ModelLayerLocation TUMBLEWEED = new ModelLayerLocation(WilderConstants.id("tumbleweed"), "main");
	public static final ModelLayerLocation CRAB = new ModelLayerLocation(WilderConstants.id("crab"), "main");
	public static final ModelLayerLocation OSTRICH = new ModelLayerLocation(WilderConstants.id("ostrich"), "main");
	public static final ModelLayerLocation OSTRICH_INBRED = new ModelLayerLocation(WilderConstants.id("ostrich"), "inbred");
	public static final ModelLayerLocation OSTRICH_SADDLE = new ModelLayerLocation(WilderConstants.id("ostrich"), "saddle");
	public static final ModelLayerLocation SCORCHED = new ModelLayerLocation(WilderConstants.id("scorched"), "main");

	@Override
	public void onInitializeClient() {
		SplashTextAPI.addSplashLocation(WilderConstants.id("texts/splashes.txt"));
		addPanorama("birch_valley");
		addPanorama("cherry_grove_and_sunflower_plains");;
		WilderEasterEggs.hatchEasterEggs();

		BlockRenderLayerMap renderLayerRegistry = BlockRenderLayerMap.INSTANCE;

		renderLayerRegistry.putBlock(WWBlocks.CARNATION, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.SEEDING_DANDELION, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_CARNATION, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_SEEDING_DANDELION, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_BAOBAB_NUT, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_CYPRESS_SAPLING, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_COCONUT, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_MAPLE_SAPLING, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_BIG_DRIPLEAF, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_SMALL_DRIPLEAF, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_SHORT_GRASS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_TUMBLEWEED_PLANT, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_TUMBLEWEED, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_BUSH, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_PRICKLY_PEAR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.DATURA, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.CATTAIL, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.ALGAE, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.MILKWEED, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.MARIGOLD, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POTTED_MARIGOLD, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.POLLEN, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.ECHO_GLASS, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.HANGING_TENDRIL, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.FLOWERING_LILY_PAD, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BROWN_SHELF_FUNGUS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.RED_SHELF_FUNGUS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BAOBAB_DOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.CYPRESS_DOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PALM_DOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.MAPLE_DOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BAOBAB_TRAPDOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.CYPRESS_TRAPDOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PALM_TRAPDOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.MAPLE_TRAPDOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BAOBAB_NUT, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.CYPRESS_SAPLING, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.COCONUT, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.MAPLE_SAPLING, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.GLORY_OF_THE_SNOW, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.ALBA_GLORY_OF_THE_SNOW, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BLUE_GIANT_GLORY_OF_THE_SNOW, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PINK_GIANT_GLORY_OF_THE_SNOW, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.VIOLET_BEAUTY_GLORY_OF_THE_SNOW, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BUSH, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.PRICKLY_PEAR_CACTUS, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.TERMITE_MOUND, RenderType.solid());
		renderLayerRegistry.putBlock(WWBlocks.DISPLAY_LANTERN, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_ACACIA_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_BAOBAB_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_BIRCH_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_CHERRY_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_CYPRESS_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_MAPLE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_DARK_OAK_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_JUNGLE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_MANGROVE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_OAK_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_SPRUCE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_PALM_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_CRIMSON_STEM, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.HOLLOWED_WARPED_STEM, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_PALM_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.BLUE_PEARLESCENT_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.BLUE_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.LIME_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.PINK_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.RED_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.YELLOW_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.BLUE_PEARLESCENT_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.BLUE_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.LIME_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.PINK_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.RED_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.YELLOW_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(WWBlocks.TUMBLEWEED_PLANT, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.TUMBLEWEED, RenderType.cutout());
		renderLayerRegistry.putBlock(WWBlocks.MAPLE_LEAF_LITTER, RenderType.cutoutMipped());

		ParticleFactoryRegistry particleRegistry = ParticleFactoryRegistry.getInstance();

		particleRegistry.register(WWParticles.LEAF_CLUSTER_SPAWNER, LeafClusterSeedParticle.Factory::new);
		particleRegistry.register(WWParticles.MAPLE_LEAVES, LeafParticle.Factory::new);

		particleRegistry.register(WWParticles.POLLEN, PollenParticle.PollenFactory::new);
		particleRegistry.register(WWParticles.SEED, SeedParticle.Factory::new);
		particleRegistry.register(WWParticles.FLOATING_SCULK_BUBBLE, FloatingSculkBubbleParticle.BubbleFactory::new);
		particleRegistry.register(WWParticles.WIND, WindParticle.Factory::new);
		particleRegistry.register(WWParticles.TERMITE, TermiteParticle.Factory::new);
		particleRegistry.register(WWParticles.COCONUT_SPLASH, FallingParticle.Factory::new);
		particleRegistry.register(WWParticles.SCORCHING_FLAME, WilderParticleFactories.ScorchingEffectFlameFactory::new);
		particleRegistry.register(WWParticles.BLUE_PEARLESCENT_HANGING_MESOGLEA, MesogleaDripParticle.BPMesogleaHangProvider::new);
		particleRegistry.register(WWParticles.BLUE_PEARLESCENT_FALLING_MESOGLEA, MesogleaDripParticle.BPMesogleaFallProvider::new);
		particleRegistry.register(WWParticles.BLUE_PEARLESCENT_LANDING_MESOGLEA, MesogleaDripParticle.BPMesogleaLandProvider::new);
		particleRegistry.register(WWParticles.PURPLE_PEARLESCENT_HANGING_MESOGLEA, MesogleaDripParticle.PPMesogleaHangProvider::new);
		particleRegistry.register(WWParticles.PURPLE_PEARLESCENT_FALLING_MESOGLEA, MesogleaDripParticle.PPMesogleaFallProvider::new);
		particleRegistry.register(WWParticles.PURPLE_PEARLESCENT_LANDING_MESOGLEA, MesogleaDripParticle.PPMesogleaLandProvider::new);
		particleRegistry.register(WWParticles.BLUE_HANGING_MESOGLEA, MesogleaDripParticle.BMesogleaHangProvider::new);
		particleRegistry.register(WWParticles.BLUE_FALLING_MESOGLEA, MesogleaDripParticle.BMesogleaFallProvider::new);
		particleRegistry.register(WWParticles.BLUE_LANDING_MESOGLEA, MesogleaDripParticle.BMesogleaLandProvider::new);
		particleRegistry.register(WWParticles.YELLOW_HANGING_MESOGLEA, MesogleaDripParticle.YMesogleaHangProvider::new);
		particleRegistry.register(WWParticles.YELLOW_FALLING_MESOGLEA, MesogleaDripParticle.YMesogleaFallProvider::new);
		particleRegistry.register(WWParticles.YELLOW_LANDING_MESOGLEA, MesogleaDripParticle.YMesogleaLandProvider::new);
		particleRegistry.register(WWParticles.LIME_HANGING_MESOGLEA, MesogleaDripParticle.LMesogleaHangProvider::new);
		particleRegistry.register(WWParticles.LIME_FALLING_MESOGLEA, MesogleaDripParticle.LMesogleaFallProvider::new);
		particleRegistry.register(WWParticles.LIME_LANDING_MESOGLEA, MesogleaDripParticle.LMesogleaLandProvider::new);
		particleRegistry.register(WWParticles.PINK_HANGING_MESOGLEA, MesogleaDripParticle.PMesogleaHangProvider::new);
		particleRegistry.register(WWParticles.PINK_FALLING_MESOGLEA, MesogleaDripParticle.PMesogleaFallProvider::new);
		particleRegistry.register(WWParticles.PINK_LANDING_MESOGLEA, MesogleaDripParticle.PMesogleaLandProvider::new);
		particleRegistry.register(WWParticles.RED_HANGING_MESOGLEA, MesogleaDripParticle.RMesogleaHangProvider::new);
		particleRegistry.register(WWParticles.RED_FALLING_MESOGLEA, MesogleaDripParticle.RMesogleaFallProvider::new);
		particleRegistry.register(WWParticles.RED_LANDING_MESOGLEA, MesogleaDripParticle.RMesogleaLandProvider::new);

		EntityRendererRegistry.register(WWEntities.FIREFLY, FireflyRenderer::new);

		EntityRendererRegistry.register(WWEntities.ANCIENT_HORN_VIBRATION, AncientHornProjectileRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ANCIENT_HORN_PROJECTILE_LAYER, AncientHornProjectileModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntities.JELLYFISH, JellyfishRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(JELLYFISH, JellyfishModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntities.TUMBLEWEED, TumbleweedRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(TUMBLEWEED, TumbleweedModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntities.CRAB, CrabRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(CRAB, CrabModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntities.OSTRICH, OstrichRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH, OstrichModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH_INBRED, OstrichInbredModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH_SADDLE, OstrichModel::createBodyLayer);

		EntityRendererRegistry.register(WWEntities.SCORCHED, ScorchedRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(SCORCHED, ScorchedModel::createSpiderBodyLayer);

		EntityRendererRegistry.register(WWEntities.COCONUT, ThrownItemRenderer::new);

		BlockEntityRenderers.register(BlockEntityType.SCULK_SENSOR, SculkSensorBlockEntityRenderer::new);
		BlockEntityRenderers.register(BlockEntityType.CALIBRATED_SCULK_SENSOR, SculkSensorBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(SCULK_SENSOR, SculkSensorBlockEntityRenderer::getTexturedModelData);

		BlockEntityRenderers.register(WWBlockEntities.HANGING_TENDRIL, HangingTendrilBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(HANGING_TENDRIL, HangingTendrilBlockEntityRenderer::getTexturedModelData);

		BlockEntityRenderers.register(WWBlockEntities.DISPLAY_LANTERN, DisplayLanternBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(DISPLAY_LANTERN, DisplayLanternBlockEntityRenderer::getTexturedModelData);

		BlockEntityRenderers.register(WWBlockEntities.STONE_CHEST, StoneChestBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(STONE_CHEST, StoneChestBlockEntityRenderer::createSingleBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(DOUBLE_STONE_CHEST_LEFT, StoneChestBlockEntityRenderer::createDoubleBodyLeftLayer);
		EntityModelLayerRegistry.registerModelLayer(DOUBLE_STONE_CHEST_RIGHT, StoneChestBlockEntityRenderer::createDoubleBodyRightLayer);

		WilderClientNetworking.registerPacketReceivers();

		FlyBySoundHub.AUTO_ENTITIES_AND_SOUNDS.put(WWEntities.ANCIENT_HORN_VIBRATION, new FlyBySoundHub.FlyBySound(1.0F, 0.5F, SoundSource.PLAYERS, WWSounds.ENTITY_ANCIENT_HORN_VIBRATION_FLYBY));

		ItemProperties.register(WWItems.ANCIENT_HORN, WilderConstants.vanillaId("tooting"), (itemStack, clientLevel, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
		ItemProperties.register(WWItems.COPPER_HORN, WilderConstants.vanillaId("tooting"), (itemStack, clientLevel, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

		ItemProperties.register(WWItems.SCORCHED_SAND, WilderConstants.vanillaId("cracked"), (itemStack, clientLevel, livingEntity, seed) -> ItemBlockStateTagUtils.getBoolProperty(itemStack, WWBlockStateProperties.CRACKED, false) ? 1F : 0F);
		ItemProperties.register(WWItems.SCORCHED_RED_SAND, WilderConstants.vanillaId("cracked"), (itemStack, clientLevel, livingEntity, seed) -> ItemBlockStateTagUtils.getBoolProperty(itemStack, WWBlockStateProperties.CRACKED, false) ? 1F : 0F);
		ItemProperties.register(WWItems.ECHO_GLASS, WilderConstants.vanillaId("damage"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, WWBlockStateProperties.DAMAGE, 0)) / 4F);
		ItemProperties.register(Items.BEE_NEST, WilderConstants.vanillaId("honey"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, BlockStateProperties.LEVEL_HONEY, 0)) / 5F);
		ItemProperties.register(Items.BEEHIVE, WilderConstants.vanillaId("honey"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, BlockStateProperties.LEVEL_HONEY, 0)) / 5F);
		ItemProperties.register(Items.SCULK_SHRIEKER, WilderConstants.vanillaId("souls_taken"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, WWBlockStateProperties.SOULS_TAKEN, 0)) / 2F);

		ItemProperties.register(WWItems.FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.CYAN_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.GRAY_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.GREEN_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.LIGHT_BLUE_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.LIME_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.MAGENTA_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.ORANGE_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.PINK_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.PURPLE_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.YELLOW_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.WHITE_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.BLACK_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.BLUE_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.RED_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.BROWN_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(WWItems.LIGHT_GRAY_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);

		ColorProviderRegistry.ITEM.register(
			((state, tintIndex) -> 5877296),
			WWBlocks.BAOBAB_LEAVES
		);

		ColorProviderRegistry.ITEM.register(
			((state, tintIndex) -> 5877296),
			WWBlocks.CYPRESS_LEAVES
		);

		ColorProviderRegistry.ITEM.register(
			((state, tintIndex) -> 5877296),
			WWBlocks.PALM_FRONDS
		);

		ColorProviderRegistry.BLOCK.register(
			((state, level, pos, tintIndex) -> level == null || pos == null ? 7455580 : 2129968),
			WWBlocks.FLOWERING_LILY_PAD
		);

		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
			BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), WWBlocks.BAOBAB_LEAVES);

		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
			BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), WWBlocks.CYPRESS_LEAVES);

		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
			BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), WWBlocks.PALM_FRONDS);

		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
			BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), WWBlocks.POTTED_SHORT_GRASS);

		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
			BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), WWBlocks.BUSH);

		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
			BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), WWBlocks.POTTED_BUSH);

		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public ResourceLocation getFabricId() {
				return WilderConstants.id("minecraft_live_sculk_sensor");
			}

			@Override
			public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
				WilderConstants.MC_LIVE_TENDRILS = resourceManager.getResource(WilderConstants.id("textures/entity/sculk_sensor/new_tendril_enabler.png")).isPresent();
			}
		});

		setupMesogleaRendering();

		DebugRendererEvents.DEBUG_RENDERERS_CREATED.register(client -> {
			OstrichDebugRenderer ostrichDebugRenderer = new OstrichDebugRenderer(client);

			ClientTickEvents.START_WORLD_TICK.register(clientLevel -> {
				if (FrozenLibConfig.IS_DEBUG) {
					ostrichDebugRenderer.tick();
				}
			});

			DebugRenderManager.addClearRunnable(ostrichDebugRenderer::clear);

			DebugRenderManager.registerRenderer(WilderConstants.id("ostrich"), ostrichDebugRenderer::render);
		});
	}

	private static void setupMesogleaRendering() { // Credit to embeddedt: https://github.com/embeddedt/embeddium/issues/284#issuecomment-2098864705
		FluidRenderHandler originalHandler = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER);
		// Assert that the original handler exists now, otherwise the crash will happen later inside our handler
		Objects.requireNonNull(originalHandler);
		var customWaterHandler = new FluidRenderHandler() {

			private boolean isSingleTexture(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos) {
				if (view != null && pos != null && BlockConfig.Client.MESOGLEA_LIQUID) {
					BlockState state = view.getBlockState(pos);
					return state.getBlock() instanceof MesogleaBlock && state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED);
				}
				return false;
			}

			@Override
			public TextureAtlasSprite[] getFluidSprites(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos, FluidState state) {
				return originalHandler.getFluidSprites(view, pos, state);
			}

			@Override
			public void renderFluid(BlockPos pos, BlockAndTintGetter world, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState) {
				if (isSingleTexture(world, pos)) {
					TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getBlockModelShaper().getParticleIcon(world.getBlockState(pos));
					LiquidRenderUtils.tesselateWithSingleTexture(
						world,
						pos,
						vertexConsumer,
						blockState,
						fluidState,
						sprite
					);
				} else {
					originalHandler.renderFluid(pos, world, vertexConsumer, blockState, fluidState);
				}
			}

			// Delegate all other methods to the original

			@Override
			public int getFluidColor(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos, FluidState state) {
				return isSingleTexture(view, pos) ? 0xFFFFFFFF : originalHandler.getFluidColor(view, pos, state);
			}
		};

		FluidRenderHandlerRegistry.INSTANCE.register(Fluids.WATER, Fluids.FLOWING_WATER, customWaterHandler);
	}

	private static void addPanorama(String panoramaName) {
		ResourceLocation panoramaLocation = WilderConstants.id("textures/gui/title/" + panoramaName + "/panorama");
		Panoramas.addPanorama(panoramaLocation);
	}

}
