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
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.frozenblock.lib.item.api.ItemBlockStateTagUtils;
import net.frozenblock.lib.liquid.render.api.LiquidRenderUtils;
import net.frozenblock.lib.menu.api.Panoramas;
import net.frozenblock.lib.menu.api.SplashTextAPI;
import net.frozenblock.lib.sound.api.FlyBySoundHub;
import net.frozenblock.wilderwild.config.BlockConfig;
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
import net.frozenblock.wilderwild.particle.AdditionalParticleFactories;
import net.frozenblock.wilderwild.particle.FallingParticle;
import net.frozenblock.wilderwild.particle.FloatingSculkBubbleParticle;
import net.frozenblock.wilderwild.particle.MesogleaDripParticle;
import net.frozenblock.wilderwild.particle.PollenParticle;
import net.frozenblock.wilderwild.particle.SeedParticle;
import net.frozenblock.wilderwild.particle.TermiteParticle;
import net.frozenblock.wilderwild.particle.WindParticle;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
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
		Panoramas.addPanorama(WilderConstants.id("textures/gui/title/first/panorama"));
		Panoramas.addPanorama(WilderConstants.id("textures/gui/title/second/panorama"));
		Panoramas.addPanorama(WilderConstants.id("textures/gui/title/third/panorama"));
		WilderEasterEggs.hatchEasterEggs();

		BlockRenderLayerMap renderLayerRegistry = BlockRenderLayerMap.INSTANCE;

		renderLayerRegistry.putBlock(RegisterBlocks.CARNATION, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.SEEDING_DANDELION, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_CARNATION, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_SEEDING_DANDELION, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_BAOBAB_NUT, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_CYPRESS_SAPLING, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_COCONUT, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_BIG_DRIPLEAF, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_SMALL_DRIPLEAF, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_SHORT_GRASS, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_TUMBLEWEED_PLANT, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_TUMBLEWEED, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_BUSH, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.POTTED_PRICKLY_PEAR, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.DATURA, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.CATTAIL, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.ALGAE, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.MILKWEED, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.POLLEN, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.ECHO_GLASS, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.HANGING_TENDRIL, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.FLOWERING_LILY_PAD, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.BROWN_SHELF_FUNGUS, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.RED_SHELF_FUNGUS, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.BAOBAB_DOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.CYPRESS_DOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.PALM_DOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.BAOBAB_TRAPDOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.CYPRESS_TRAPDOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.PALM_TRAPDOOR, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.BAOBAB_NUT, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.CYPRESS_SAPLING, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.COCONUT, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.GLORY_OF_THE_SNOW, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.ALBA_GLORY_OF_THE_SNOW, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.BLUE_GIANT_GLORY_OF_THE_SNOW, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.PINK_GIANT_GLORY_OF_THE_SNOW, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.VIOLET_BEAUTY_GLORY_OF_THE_SNOW, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.BUSH, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.PRICKLY_PEAR_CACTUS, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.TERMITE_MOUND, RenderType.solid());
		renderLayerRegistry.putBlock(RegisterBlocks.DISPLAY_LANTERN, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.HOLLOWED_ACACIA_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.HOLLOWED_BAOBAB_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.HOLLOWED_BIRCH_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.HOLLOWED_CHERRY_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.HOLLOWED_CYPRESS_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.HOLLOWED_DARK_OAK_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.HOLLOWED_JUNGLE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.HOLLOWED_MANGROVE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.HOLLOWED_OAK_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.HOLLOWED_SPRUCE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.HOLLOWED_PALM_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.HOLLOWED_CRIMSON_STEM, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.HOLLOWED_WARPED_STEM, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_PALM_LOG, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_WARPED_STEM, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.BLUE_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.LIME_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.PINK_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.RED_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.YELLOW_MESOGLEA, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.BLUE_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.LIME_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.PINK_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.RED_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.YELLOW_NEMATOCYST, RenderType.translucent());
		renderLayerRegistry.putBlock(RegisterBlocks.TUMBLEWEED_PLANT, RenderType.cutout());
		renderLayerRegistry.putBlock(RegisterBlocks.TUMBLEWEED, RenderType.cutout());

		ParticleFactoryRegistry particleRegistry = ParticleFactoryRegistry.getInstance();

		particleRegistry.register(RegisterParticles.POLLEN, PollenParticle.PollenFactory::new);
		particleRegistry.register(RegisterParticles.SEED, SeedParticle.Factory::new);
		particleRegistry.register(RegisterParticles.FLOATING_SCULK_BUBBLE, FloatingSculkBubbleParticle.BubbleFactory::new);
		particleRegistry.register(RegisterParticles.WIND, WindParticle.Factory::new);
		particleRegistry.register(RegisterParticles.TERMITE, TermiteParticle.Factory::new);
		particleRegistry.register(RegisterParticles.COCONUT_SPLASH, FallingParticle.Factory::new);
		particleRegistry.register(RegisterParticles.SCORCHING_FLAME, AdditionalParticleFactories.ScorchingEffectFlameFactory::new);
		particleRegistry.register(RegisterParticles.BLUE_PEARLESCENT_HANGING_MESOGLEA, MesogleaDripParticle.BPMesogleaHangProvider::new);
		particleRegistry.register(RegisterParticles.BLUE_PEARLESCENT_FALLING_MESOGLEA, MesogleaDripParticle.BPMesogleaFallProvider::new);
		particleRegistry.register(RegisterParticles.BLUE_PEARLESCENT_LANDING_MESOGLEA, MesogleaDripParticle.BPMesogleaLandProvider::new);
		particleRegistry.register(RegisterParticles.PURPLE_PEARLESCENT_HANGING_MESOGLEA, MesogleaDripParticle.PPMesogleaHangProvider::new);
		particleRegistry.register(RegisterParticles.PURPLE_PEARLESCENT_FALLING_MESOGLEA, MesogleaDripParticle.PPMesogleaFallProvider::new);
		particleRegistry.register(RegisterParticles.PURPLE_PEARLESCENT_LANDING_MESOGLEA, MesogleaDripParticle.PPMesogleaLandProvider::new);
		particleRegistry.register(RegisterParticles.BLUE_HANGING_MESOGLEA, MesogleaDripParticle.BMesogleaHangProvider::new);
		particleRegistry.register(RegisterParticles.BLUE_FALLING_MESOGLEA, MesogleaDripParticle.BMesogleaFallProvider::new);
		particleRegistry.register(RegisterParticles.BLUE_LANDING_MESOGLEA, MesogleaDripParticle.BMesogleaLandProvider::new);
		particleRegistry.register(RegisterParticles.YELLOW_HANGING_MESOGLEA, MesogleaDripParticle.YMesogleaHangProvider::new);
		particleRegistry.register(RegisterParticles.YELLOW_FALLING_MESOGLEA, MesogleaDripParticle.YMesogleaFallProvider::new);
		particleRegistry.register(RegisterParticles.YELLOW_LANDING_MESOGLEA, MesogleaDripParticle.YMesogleaLandProvider::new);
		particleRegistry.register(RegisterParticles.LIME_HANGING_MESOGLEA, MesogleaDripParticle.LMesogleaHangProvider::new);
		particleRegistry.register(RegisterParticles.LIME_FALLING_MESOGLEA, MesogleaDripParticle.LMesogleaFallProvider::new);
		particleRegistry.register(RegisterParticles.LIME_LANDING_MESOGLEA, MesogleaDripParticle.LMesogleaLandProvider::new);
		particleRegistry.register(RegisterParticles.PINK_HANGING_MESOGLEA, MesogleaDripParticle.PMesogleaHangProvider::new);
		particleRegistry.register(RegisterParticles.PINK_FALLING_MESOGLEA, MesogleaDripParticle.PMesogleaFallProvider::new);
		particleRegistry.register(RegisterParticles.PINK_LANDING_MESOGLEA, MesogleaDripParticle.PMesogleaLandProvider::new);
		particleRegistry.register(RegisterParticles.RED_HANGING_MESOGLEA, MesogleaDripParticle.RMesogleaHangProvider::new);
		particleRegistry.register(RegisterParticles.RED_FALLING_MESOGLEA, MesogleaDripParticle.RMesogleaFallProvider::new);
		particleRegistry.register(RegisterParticles.RED_LANDING_MESOGLEA, MesogleaDripParticle.RMesogleaLandProvider::new);

		EntityRendererRegistry.register(RegisterEntities.FIREFLY, FireflyRenderer::new);

		EntityRendererRegistry.register(RegisterEntities.ANCIENT_HORN_VIBRATION, AncientHornProjectileRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ANCIENT_HORN_PROJECTILE_LAYER, AncientHornProjectileModel::createBodyLayer);

		EntityRendererRegistry.register(RegisterEntities.JELLYFISH, JellyfishRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(JELLYFISH, JellyfishModel::createBodyLayer);

		EntityRendererRegistry.register(RegisterEntities.TUMBLEWEED, TumbleweedRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(TUMBLEWEED, TumbleweedModel::createBodyLayer);

		EntityRendererRegistry.register(RegisterEntities.CRAB, CrabRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(CRAB, CrabModel::createBodyLayer);

		EntityRendererRegistry.register(RegisterEntities.OSTRICH, OstrichRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH, OstrichModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH_INBRED, OstrichInbredModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(OSTRICH_SADDLE, OstrichModel::createBodyLayer);

		EntityRendererRegistry.register(RegisterEntities.SCORCHED, ScorchedRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(SCORCHED, ScorchedModel::createSpiderBodyLayer);

		EntityRendererRegistry.register(RegisterEntities.COCONUT, ThrownItemRenderer::new);

		BlockEntityRenderers.register(BlockEntityType.SCULK_SENSOR, SculkSensorBlockEntityRenderer::new);
		BlockEntityRenderers.register(BlockEntityType.CALIBRATED_SCULK_SENSOR, SculkSensorBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(SCULK_SENSOR, SculkSensorBlockEntityRenderer::getTexturedModelData);

		BlockEntityRenderers.register(RegisterBlockEntities.HANGING_TENDRIL, HangingTendrilBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(HANGING_TENDRIL, HangingTendrilBlockEntityRenderer::getTexturedModelData);

		BlockEntityRenderers.register(RegisterBlockEntities.DISPLAY_LANTERN, DisplayLanternBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(DISPLAY_LANTERN, DisplayLanternBlockEntityRenderer::getTexturedModelData);

		BlockEntityRenderers.register(RegisterBlockEntities.STONE_CHEST, StoneChestBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(STONE_CHEST, StoneChestBlockEntityRenderer::createSingleBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(DOUBLE_STONE_CHEST_LEFT, StoneChestBlockEntityRenderer::createDoubleBodyLeftLayer);
		EntityModelLayerRegistry.registerModelLayer(DOUBLE_STONE_CHEST_RIGHT, StoneChestBlockEntityRenderer::createDoubleBodyRightLayer);

		WilderClientNetworking.registerPacketReceivers();

		FlyBySoundHub.AUTO_ENTITIES_AND_SOUNDS.put(RegisterEntities.ANCIENT_HORN_VIBRATION, new FlyBySoundHub.FlyBySound(1.0F, 0.5F, SoundSource.PLAYERS, RegisterSounds.ENTITY_ANCIENT_HORN_VIBRATION_FLYBY));

		ItemProperties.register(RegisterItems.ANCIENT_HORN, WilderConstants.vanillaId("tooting"), (itemStack, clientLevel, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
		ItemProperties.register(RegisterItems.COPPER_HORN, WilderConstants.vanillaId("tooting"), (itemStack, clientLevel, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

		ItemProperties.register(RegisterItems.SCORCHED_SAND, WilderConstants.vanillaId("cracked"), (itemStack, clientLevel, livingEntity, seed) -> ItemBlockStateTagUtils.getBoolProperty(itemStack, RegisterProperties.CRACKED, false) ? 1F : 0F);
		ItemProperties.register(RegisterItems.SCORCHED_RED_SAND, WilderConstants.vanillaId("cracked"), (itemStack, clientLevel, livingEntity, seed) -> ItemBlockStateTagUtils.getBoolProperty(itemStack, RegisterProperties.CRACKED, false) ? 1F : 0F);
		ItemProperties.register(RegisterItems.ECHO_GLASS, WilderConstants.vanillaId("damage"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, RegisterProperties.DAMAGE, 0)) / 4F);
		ItemProperties.register(Items.BEE_NEST, WilderConstants.vanillaId("honey"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, BlockStateProperties.LEVEL_HONEY, 0)) / 5F);
		ItemProperties.register(Items.BEEHIVE, WilderConstants.vanillaId("honey"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, BlockStateProperties.LEVEL_HONEY, 0)) / 5F);
		ItemProperties.register(Items.SCULK_SHRIEKER, WilderConstants.vanillaId("souls_taken"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, RegisterProperties.SOULS_TAKEN, 0)) / 2F);

		ItemProperties.register(RegisterItems.FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.CYAN_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.GRAY_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.GREEN_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.LIGHT_BLUE_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.LIME_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.MAGENTA_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.ORANGE_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.PINK_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.PURPLE_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.YELLOW_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.WHITE_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.BLACK_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.BLUE_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.RED_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.BROWN_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.LIGHT_GRAY_FIREFLY_BOTTLE, WilderConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);

		ColorProviderRegistry.ITEM.register(
			((state, tintIndex) -> 5877296),
			RegisterBlocks.BAOBAB_LEAVES
		);

		ColorProviderRegistry.ITEM.register(
			((state, tintIndex) -> 5877296),
			RegisterBlocks.CYPRESS_LEAVES
		);

		ColorProviderRegistry.ITEM.register(
			((state, tintIndex) -> 5877296),
			RegisterBlocks.PALM_FRONDS
		);

		ColorProviderRegistry.BLOCK.register(
			((state, level, pos, tintIndex) ->
				level == null || pos == null ? 7455580 : 2129968
			),
			RegisterBlocks.FLOWERING_LILY_PAD
		);

		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
			BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), RegisterBlocks.BAOBAB_LEAVES);

		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
			BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), RegisterBlocks.CYPRESS_LEAVES);

		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
			BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), RegisterBlocks.PALM_FRONDS);

		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
			BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), RegisterBlocks.POTTED_SHORT_GRASS);

		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
			BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), RegisterBlocks.BUSH);

		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
			BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), RegisterBlocks.POTTED_BUSH);

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
	}

	private static void setupMesogleaRendering() { // Credit to embeddedt: https://github.com/embeddedt/embeddium/issues/284#issuecomment-2098864705
		FluidRenderHandler originalHandler = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.WATER);
		// Assert that the original handler exists now, otherwise the crash will happen later inside our handler
		Objects.requireNonNull(originalHandler);
		var customWaterHandler = new FluidRenderHandler() {

			private boolean isSingleTexture(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos) {
				if (view != null && pos != null && BlockConfig.Client.MESOGLEA_LIQUID) {
					BlockState state = view.getBlockState(pos);
					return state.is(WilderBlockTags.MESOGLEA) && state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED);
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

}
