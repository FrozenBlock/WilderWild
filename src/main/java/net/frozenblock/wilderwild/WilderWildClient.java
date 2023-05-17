/*
 * Copyright 2022-2023 FrozenBlock
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

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.frozenblock.lib.item.api.ItemBlockStateTagUtils;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.lib.menu.api.Panoramas;
import net.frozenblock.lib.menu.api.Splashes;
import net.frozenblock.lib.sound.api.FlyBySoundHub;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.entity.render.blockentity.DisplayLanternBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.blockentity.HangingTendrilBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.blockentity.SculkSensorBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.blockentity.StoneChestBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.easter.EasterEggs;
import net.frozenblock.wilderwild.entity.render.model.AncientHornProjectileModel;
import net.frozenblock.wilderwild.entity.render.model.JellyfishModel;
import net.frozenblock.wilderwild.entity.render.model.TumbleweedModel;
import net.frozenblock.wilderwild.entity.render.renderer.AncientHornProjectileRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.FireflyRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.JellyfishRenderer;
import net.frozenblock.wilderwild.entity.render.renderer.TumbleweedRenderer;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.particle.FallingParticle;
import net.frozenblock.wilderwild.particle.FloatingSculkBubbleParticle;
import net.frozenblock.wilderwild.particle.MesogleaDripParticle;
import net.frozenblock.wilderwild.particle.PollenParticle;
import net.frozenblock.wilderwild.particle.SeedParticle;
import net.frozenblock.wilderwild.particle.TermiteParticle;
import net.frozenblock.wilderwild.particle.options.FloatingSculkBubbleParticleOptions;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public final class WilderWildClient implements ClientModInitializer {
	public static final ModelLayerLocation ANCIENT_HORN_PROJECTILE_LAYER = new ModelLayerLocation(WilderSharedConstants.id("ancient_horn_projectile"), "main");
	public static final ModelLayerLocation SCULK_SENSOR = new ModelLayerLocation(WilderSharedConstants.id("sculk_sensor"), "main");
	public static final ModelLayerLocation HANGING_TENDRIL = new ModelLayerLocation(WilderSharedConstants.id("hanging_tendril"), "main");
	public static final ModelLayerLocation DISPLAY_LANTERN = new ModelLayerLocation(WilderSharedConstants.id("display_lantern"), "main");
	public static final ModelLayerLocation STONE_CHEST = new ModelLayerLocation(WilderSharedConstants.id("stone_chest"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_LEFT = new ModelLayerLocation(WilderSharedConstants.id("double_stone_chest_left"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_RIGHT = new ModelLayerLocation(WilderSharedConstants.id("double_stone_chest_right"), "main");
	public static final ModelLayerLocation JELLYFISH = new ModelLayerLocation(WilderSharedConstants.id("jellyfish"), "main");
	public static final ModelLayerLocation TUMBLEWEED = new ModelLayerLocation(WilderSharedConstants.id("tumbleweed"), "main");

	@Override
	public void onInitializeClient() {
		Splashes.addSplashLocation(WilderSharedConstants.id("texts/splashes.txt"));
		Panoramas.addPanorama(WilderSharedConstants.id("textures/gui/title/first/panorama"));
		Panoramas.addPanorama(WilderSharedConstants.id("textures/gui/title/second/panorama"));
		EasterEggs.registerEaster();

		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CARNATION, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.SEEDING_DANDELION, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_CARNATION, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_SEEDING_DANDELION, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_BAOBAB_NUT, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_CYPRESS_SAPLING, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_COCONUT, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_BIG_DRIPLEAF, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_SMALL_DRIPLEAF, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_GRASS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_TUMBLEWEED_PLANT, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_TUMBLEWEED, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_BUSH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_PRICKLY_PEAR, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.DATURA, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CATTAIL, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.ALGAE, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.MILKWEED, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POLLEN_BLOCK, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.ECHO_GLASS, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HANGING_TENDRIL, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.FLOWERING_LILY_PAD, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BROWN_SHELF_FUNGUS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.RED_SHELF_FUNGUS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BAOBAB_DOOR, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CYPRESS_DOOR, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PALM_DOOR, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BAOBAB_TRAPDOOR, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CYPRESS_TRAPDOOR, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PALM_TRAPDOOR, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BAOBAB_NUT, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CYPRESS_SAPLING, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.COCONUT, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.GLORY_OF_THE_SNOW, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.WHITE_GLORY_OF_THE_SNOW, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BLUE_GLORY_OF_THE_SNOW, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PINK_GLORY_OF_THE_SNOW, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PURPLE_GLORY_OF_THE_SNOW, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BUSH, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PRICKLY_PEAR_CACTUS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.TERMITE_MOUND, RenderType.solid());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.DISPLAY_LANTERN, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_ACACIA_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_BAOBAB_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_BIRCH_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_CYPRESS_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_DARK_OAK_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_JUNGLE_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_MANGROVE_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_OAK_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_SPRUCE_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_PALM_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_CRIMSON_STEM, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_WARPED_STEM, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_PALM_LOG, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.STRIPPED_HOLLOWED_WARPED_STEM, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BLUE_MESOGLEA, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.LIME_MESOGLEA, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PINK_MESOGLEA, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.RED_MESOGLEA, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.YELLOW_MESOGLEA, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BLUE_NEMATOCYST, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.LIME_NEMATOCYST, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PINK_NEMATOCYST, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.RED_NEMATOCYST, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.YELLOW_NEMATOCYST, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.TUMBLEWEED_PLANT, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.TUMBLEWEED, RenderType.cutout());

		ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((atlasTexture, registry) -> {
			registry.register(WilderSharedConstants.id("particle/floating_sculk_bubble_0"));
			registry.register(WilderSharedConstants.id("particle/floating_sculk_bubble_1"));
			registry.register(WilderSharedConstants.id("particle/floating_sculk_bubble_2"));
			registry.register(WilderSharedConstants.id("particle/floating_sculk_bubble_3"));
			registry.register(WilderSharedConstants.id("particle/floating_sculk_bubble_4"));
			registry.register(WilderSharedConstants.id("particle/floating_sculk_bubble_5"));
			registry.register(WilderSharedConstants.id("particle/floating_sculk_bubble_6"));
			registry.register(WilderSharedConstants.id("particle/termite"));
			registry.register(WilderSharedConstants.id("particle/coconut_splash_0"));
			registry.register(WilderSharedConstants.id("particle/coconut_splash_1"));
			registry.register(WilderSharedConstants.id("particle/coconut_splash_2"));
			registry.register(WilderSharedConstants.id("particle/coconut_splash_3"));
		});

		ClientSpriteRegistryCallback.event(Sheets.CHEST_SHEET).register((atlasTexture, registry) -> {
			registry.register(WilderSharedConstants.id("entity/stone_chest/stone"));
			registry.register(WilderSharedConstants.id("entity/stone_chest/stone_left"));
			registry.register(WilderSharedConstants.id("entity/stone_chest/stone_right"));
			registry.register(WilderSharedConstants.id("entity/stone_chest/ancient"));
			registry.register(WilderSharedConstants.id("entity/stone_chest/ancient_left"));
			registry.register(WilderSharedConstants.id("entity/stone_chest/ancient_right"));
		});

		var particleRegistry = ParticleFactoryRegistry.getInstance();

		particleRegistry.register(RegisterParticles.POLLEN, PollenParticle.PollenFactory::new);
		particleRegistry.register(RegisterParticles.SEED, SeedParticle.Factory::new);
		particleRegistry.register(RegisterParticles.FLOATING_SCULK_BUBBLE, FloatingSculkBubbleParticle.BubbleFactory::new);
		particleRegistry.register(RegisterParticles.TERMITE, TermiteParticle.Factory::new);
		particleRegistry.register(RegisterParticles.COCONUT_SPLASH, FallingParticle.Factory::new);
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

		EntityRendererRegistry.register(RegisterEntities.ANCIENT_HORN_PROJECTILE_ENTITY, AncientHornProjectileRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ANCIENT_HORN_PROJECTILE_LAYER, AncientHornProjectileModel::createBodyLayer);

		EntityRendererRegistry.register(RegisterEntities.JELLYFISH, JellyfishRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(JELLYFISH, JellyfishModel::createBodyLayer);

		EntityRendererRegistry.register(RegisterEntities.TUMBLEWEED, TumbleweedRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(TUMBLEWEED, TumbleweedModel::createBodyLayer);

		EntityRendererRegistry.register(RegisterEntities.COCONUT, ThrownItemRenderer::new);

		BlockEntityRenderers.register(BlockEntityType.SCULK_SENSOR, SculkSensorBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(SCULK_SENSOR, SculkSensorBlockEntityRenderer::getTexturedModelData);

		BlockEntityRenderers.register(RegisterBlockEntities.HANGING_TENDRIL, HangingTendrilBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(HANGING_TENDRIL, HangingTendrilBlockEntityRenderer::getTexturedModelData);

		BlockEntityRenderers.register(RegisterBlockEntities.DISPLAY_LANTERN, DisplayLanternBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(DISPLAY_LANTERN, DisplayLanternBlockEntityRenderer::getTexturedModelData);

		BlockEntityRenderers.register(RegisterBlockEntities.STONE_CHEST, StoneChestBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(STONE_CHEST, StoneChestBlockEntityRenderer::createSingleBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(DOUBLE_STONE_CHEST_LEFT, StoneChestBlockEntityRenderer::createDoubleBodyLeftLayer);
		EntityModelLayerRegistry.registerModelLayer(DOUBLE_STONE_CHEST_RIGHT, StoneChestBlockEntityRenderer::createDoubleBodyRightLayer);

		receiveAncientHornProjectilePacket();
		receiveEasyEchoerBubblePacket();
		receiveSeedPacket();
		receiveControlledSeedPacket();
		receiveTermitePacket();
		receiveSensorHiccupPacket();
		receiveJellyStingPacket();

		FlyBySoundHub.AUTO_ENTITIES_AND_SOUNDS.put(RegisterEntities.ANCIENT_HORN_PROJECTILE_ENTITY, new FlyBySoundHub.FlyBySound(1.0F, 0.5F, SoundSource.PLAYERS, RegisterSounds.ENTITY_ANCIENT_HORN_PROJECTILE_FLYBY));

		ItemProperties.register(RegisterItems.ANCIENT_HORN, WilderSharedConstants.vanillaId("tooting"), (itemStack, clientLevel, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
		ItemProperties.register(RegisterItems.COPPER_HORN, WilderSharedConstants.vanillaId("tooting"), (itemStack, clientLevel, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

		ItemProperties.register(RegisterItems.SCORCHED_SAND, WilderSharedConstants.vanillaId("crackedness"), (itemStack, clientLevel, livingEntity, seed) -> ItemBlockStateTagUtils.getBoolProperty(itemStack, RegisterProperties.CRACKEDNESS, false) ? 1F : 0F);
		ItemProperties.register(RegisterItems.SCORCHED_RED_SAND, WilderSharedConstants.vanillaId("crackedness"), (itemStack, clientLevel, livingEntity, seed) -> ItemBlockStateTagUtils.getBoolProperty(itemStack, RegisterProperties.CRACKEDNESS, false) ? 1F : 0F);
		ItemProperties.register(RegisterItems.ECHO_GLASS, WilderSharedConstants.vanillaId("damage"), (itemStack, clientLevel, livingEntity, seed) -> ((float)ItemBlockStateTagUtils.getProperty(itemStack, RegisterProperties.DAMAGE, 0)) / 4F);
		ItemProperties.register(Items.BEE_NEST, WilderSharedConstants.vanillaId("honey"), (itemStack, clientLevel, livingEntity, seed) -> ((float)ItemBlockStateTagUtils.getProperty(itemStack, BlockStateProperties.LEVEL_HONEY, 0)) / 5F);
		ItemProperties.register(Items.BEEHIVE, WilderSharedConstants.vanillaId("honey"), (itemStack, clientLevel, livingEntity, seed) -> ((float)ItemBlockStateTagUtils.getProperty(itemStack, BlockStateProperties.LEVEL_HONEY, 0)) / 5F);
		ItemProperties.register(Items.SCULK_SHRIEKER, WilderSharedConstants.vanillaId("souls_taken"), (itemStack, clientLevel, livingEntity, seed) -> ((float)ItemBlockStateTagUtils.getProperty(itemStack, RegisterProperties.SOULS_TAKEN, 0)) / 2F);

		ItemProperties.register(RegisterItems.FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.CYAN_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.GRAY_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.GREEN_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.LIGHT_BLUE_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.LIME_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.MAGENTA_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.ORANGE_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.PINK_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.PURPLE_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.YELLOW_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.WHITE_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.BLACK_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.BLUE_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.RED_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.BROWN_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);
		ItemProperties.register(RegisterItems.LIGHT_GRAY_FIREFLY_BOTTLE, WilderSharedConstants.vanillaId("nectar"), (itemStack, clientLevel, livingEntity, seed) -> FireflyBottle.isNectar(itemStack) ? 1F : 0F);

		ColorProviderRegistry.BLOCK.register(
				((state, level, pos, tintIndex) ->
						level == null || pos == null ? 7455580 : 2129968
				),
				RegisterBlocks.FLOWERING_LILY_PAD
		);

		ColorProviderRegistry.ITEM.register(((state, tintIndex) -> 5877296), RegisterBlocks.BAOBAB_LEAVES);
		ColorProviderRegistry.ITEM.register(((state, tintIndex) -> 5877296), RegisterBlocks.CYPRESS_LEAVES);
		ColorProviderRegistry.ITEM.register(((state, tintIndex) -> 5877296), RegisterBlocks.PALM_FRONDS);

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
		), RegisterBlocks.POTTED_GRASS);
		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
				BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), RegisterBlocks.BUSH);
		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) ->
				BiomeColors.getAverageFoliageColor(Objects.requireNonNull(level), Objects.requireNonNull(pos))
		), RegisterBlocks.POTTED_BUSH);
	}

	private static void receiveAncientHornProjectilePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.HORN_PROJECTILE_PACKET_ID, (ctx, handler, byteBuf, responseSender) -> {
			EntityType<?> et = Registry.ENTITY_TYPE.byId(byteBuf.readVarInt());
			UUID uuid = byteBuf.readUUID();
			int entityId = byteBuf.readVarInt();
			Vec3 pos = AncientHornProjectile.EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
			float pitch = AncientHornProjectile.EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			float yaw = AncientHornProjectile.EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			ctx.execute(() -> {
				if (ctx.level == null)
					throw new IllegalStateException("Tried to spawn entity in a null world!");
				Entity e = et.create(ctx.level);
				if (e == null)
					throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getKey(et) + "\"!");
				e.syncPacketPositionCodec(pos.x, pos.y, pos.z);
				e.setPosRaw(pos.x, pos.y, pos.z);
				e.setXRot(pitch);
				e.setYRot(yaw);
				e.setId(entityId);
				e.setUUID(uuid);
				ctx.level.putNonPlayerEntity(entityId, e);
				WilderSharedConstants.log("Spawned Ancient Horn Projectile", WilderSharedConstants.UNSTABLE_LOGGING);
			});
		});
	}

	private static void receiveEasyEchoerBubblePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.FLOATING_SCULK_BUBBLE_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			double size = byteBuf.readDouble();
			int age = byteBuf.readInt();
			double yVel = byteBuf.readDouble();
			int count = byteBuf.readVarInt();
			ctx.execute(() -> {
				if (ctx.level == null)
					throw new IllegalStateException("why is your world null");
				var random = AdvancedMath.random();
				for (int i = 0; i < count; i++) {
					double xVel = (random.nextDouble() - 0.5) / 9.5;
					double zVel = (random.nextDouble() - 0.5) / 9.5;
					if (size >= 1) {
						xVel = (random.nextDouble() - 0.5) / 10.5;
						zVel = (random.nextDouble() - 0.5) / 10.5;
					}
					ctx.level.addParticle(new FloatingSculkBubbleParticleOptions(size, age, new Vec3(xVel, yVel, zVel)), pos.x, pos.y, pos.z, 0, 0, 0);
				}
			});
		});
	}

	private static void receiveSeedPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.SEED_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			int count = byteBuf.readVarInt();
			boolean milkweed = byteBuf.readBoolean();
			ctx.execute(() -> {
				if (ctx.level == null)
					throw new IllegalStateException("why is your world null");
				for (int i = 0; i < count; i++) {
					ctx.level.addParticle(new SeedParticleOptions(milkweed, false), pos.x, pos.y, pos.z, 0, 0, 0);
				}
			});
		});
	}

	private static void receiveControlledSeedPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.CONTROLLED_SEED_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			double velx = byteBuf.readDouble();
			double vely = byteBuf.readDouble();
			double velz = byteBuf.readDouble();
			int count = byteBuf.readVarInt();
			boolean milkweed = byteBuf.readBoolean();
			double posRandomizer = byteBuf.readDouble();
			ctx.execute(() -> {
				if (ctx.level == null)
					throw new IllegalStateException("why is your world null");
				for (int i = 0; i < count; i++) {
					ctx.level.addParticle(new SeedParticleOptions(milkweed, true), pos.x, pos.y + ((ctx.level.random.nextBoolean() ? -1 : 1) * (ctx.level.random.nextDouble() * posRandomizer)), pos.z, velx, vely + (ctx.level.random.nextDouble() * 0.07), velz);
				}
			});
		});
	}

	private static void receiveTermitePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.TERMITE_PARTICLE_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			AtomicInteger count = new AtomicInteger(byteBuf.readVarInt());
			ctx.execute(() -> {
				if (ctx.level == null)
					throw new IllegalStateException("why is your world null");

				count.addAndGet(-1);
				ctx.level.addAlwaysVisibleParticle(RegisterParticles.TERMITE, pos.x, pos.y, pos.z, 0, 0, 0);
				for (int i = 0; i < count.get(); i++) {
					ctx.level.addParticle(RegisterParticles.TERMITE, pos.x, pos.y, pos.z, 0, 0, 0);
				}
			});
		});
	}

	private static void receiveSensorHiccupPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.SENSOR_HICCUP_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			ctx.execute(() -> {
				if (ctx.level == null)
					throw new IllegalStateException("why is your world null");
				ClientLevel level = ctx.level;
				int i = 5578058;
				boolean bl2 = level.random.nextBoolean();
				if (bl2) {
					double d = (double) (i >> 16 & 255) / 255.0D;
					double e = (double) (i >> 8 & 255) / 255.0D;
					double f = (double) (i & 255) / 255.0D;
					level.addParticle(ParticleTypes.ENTITY_EFFECT, pos.x, pos.y, pos.z, d, e, f);
				}
			});
		});
	}

	private static void receiveJellyStingPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.JELLY_STING_PACKET, (ctx, handler, byteBuf, responseSender) -> ctx.execute(() -> {
			if (ctx.level != null) {
				LocalPlayer player = ctx.player;
				if (player != null) {
					ctx.level.playSound(player, player.getX(), player.getY(), player.getZ(), RegisterSounds.ENTITY_JELLYFISH_STING, SoundSource.NEUTRAL, 1.0F, ctx.level.random.nextFloat() * 0.2F + 0.9F);
				}
			}
		}));
	}
}
