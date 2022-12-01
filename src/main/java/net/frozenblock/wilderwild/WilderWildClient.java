package net.frozenblock.wilderwild;

import java.util.UUID;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.lib.menu.api.Panoramas;
import net.frozenblock.lib.menu.api.Splashes;
import net.frozenblock.lib.sound.api.FlyBySoundHub;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.entity.render.AncientHornProjectileModel;
import net.frozenblock.wilderwild.entity.render.AncientHornProjectileRenderer;
import net.frozenblock.wilderwild.entity.render.DisplayLanternBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.FireflyRenderer;
import net.frozenblock.wilderwild.entity.render.JellyfishModel;
import net.frozenblock.wilderwild.entity.render.JellyfishRenderer;
import net.frozenblock.wilderwild.entity.render.SculkSensorBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.StoneChestBlockEntityRenderer;
import net.frozenblock.wilderwild.entity.render.TumbleweedModel;
import net.frozenblock.wilderwild.entity.render.TumbleweedRenderer;
import net.frozenblock.wilderwild.misc.CompetitionCounter;
import net.frozenblock.wilderwild.particle.FloatingSculkBubbleParticle;
import net.frozenblock.wilderwild.particle.MesogleaDripParticle;
import net.frozenblock.wilderwild.particle.PollenParticle;
import net.frozenblock.wilderwild.particle.TermiteParticle;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public final class WilderWildClient implements ClientModInitializer {
	public static final ModelLayerLocation ANCIENT_HORN_PROJECTILE_LAYER = new ModelLayerLocation(WilderWild.id("ancient_horn_projectile"), "main");
	public static final ModelLayerLocation SCULK_SENSOR = new ModelLayerLocation(WilderWild.id("sculk_sensor"), "main");
	public static final ModelLayerLocation DISPLAY_LANTERN = new ModelLayerLocation(WilderWild.id("display_lantern"), "main");
	public static final ModelLayerLocation STONE_CHEST = new ModelLayerLocation(WilderWild.id("stone_chest"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_LEFT = new ModelLayerLocation(WilderWild.id("double_stone_chest_left"), "main");
	public static final ModelLayerLocation DOUBLE_STONE_CHEST_RIGHT = new ModelLayerLocation(WilderWild.id("double_stone_chest_right"), "main");
	public static final ModelLayerLocation JELLYFISH = new ModelLayerLocation(WilderWild.id("jellyfish"), "main");
	public static final ModelLayerLocation TUMBLEWEED = new ModelLayerLocation(WilderWild.id("tumbleweed"), "main");

	@Override
	public void onInitializeClient() {
		Splashes.addSplashLocation(WilderWild.id("texts/splashes.txt"));
		Panoramas.addPanorama(WilderWild.id("textures/gui/title/first/panorama"));
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
        //BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CYPRESS_ROOTS, RenderLayer.getCutout());
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
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.MESOGLEA, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PURPLE_MESOGLEA, RenderType.translucent());
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
		//BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.NEMATOCYST, RenderType.cutout());

		ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((atlasTexture, registry) -> {
			registry.register(WilderWild.id("particle/floating_sculk_bubble_0"));
			registry.register(WilderWild.id("particle/floating_sculk_bubble_1"));
			registry.register(WilderWild.id("particle/floating_sculk_bubble_2"));
			registry.register(WilderWild.id("particle/floating_sculk_bubble_3"));
			registry.register(WilderWild.id("particle/floating_sculk_bubble_4"));
			registry.register(WilderWild.id("particle/floating_sculk_bubble_5"));
			registry.register(WilderWild.id("particle/floating_sculk_bubble_6"));
			registry.register(WilderWild.id("particle/termite_0"));
			registry.register(WilderWild.id("particle/termite_1"));
			registry.register(WilderWild.id("particle/termite_2"));
			registry.register(WilderWild.id("particle/termite_3"));
			registry.register(WilderWild.id("particle/termite_4"));
			registry.register(WilderWild.id("particle/termite_5"));
			registry.register(WilderWild.id("particle/termite_6"));
			registry.register(WilderWild.id("particle/termite_7"));
			registry.register(WilderWild.id("particle/termite_8"));
			registry.register(WilderWild.id("particle/termite_9"));
		});

		ClientSpriteRegistryCallback.event(Sheets.CHEST_SHEET).register((atlasTexture, registry) -> {
			registry.register(WilderWild.id("entity/stone_chest/stone"));
			registry.register(WilderWild.id("entity/stone_chest/stone_left"));
			registry.register(WilderWild.id("entity/stone_chest/stone_right"));
			registry.register(WilderWild.id("entity/stone_chest/ancient"));
			registry.register(WilderWild.id("entity/stone_chest/ancient_left"));
			registry.register(WilderWild.id("entity/stone_chest/ancient_right"));
		});

		ParticleFactoryRegistry.getInstance().register(RegisterParticles.POLLEN, PollenParticle.PollenFactory::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.DANDELION_SEED, PollenParticle.DandelionFactory::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.CONTROLLED_DANDELION_SEED, PollenParticle.ControlledDandelionFactory::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.MILKWEED_SEED, PollenParticle.MilkweedFactory::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.CONTROLLED_MILKWEED_SEED, PollenParticle.ControlledMilkweedFactory::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.FLOATING_SCULK_BUBBLE, FloatingSculkBubbleParticle.BubbleFactory::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.TERMITE, TermiteParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.BLUE_PEARLESCENT_HANGING_MESOGLEA, MesogleaDripParticle.BPMesogleaHangProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.BLUE_PEARLESCENT_FALLING_MESOGLEA, MesogleaDripParticle.BPMesogleaFallProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.BLUE_PEARLESCENT_LANDING_MESOGLEA, MesogleaDripParticle.BPMesogleaLandProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.PURPLE_PEARLESCENT_HANGING_MESOGLEA, MesogleaDripParticle.PPMesogleaHangProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.PURPLE_PEARLESCENT_FALLING_MESOGLEA, MesogleaDripParticle.PPMesogleaFallProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.PURPLE_PEARLESCENT_LANDING_MESOGLEA, MesogleaDripParticle.PPMesogleaLandProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.BLUE_HANGING_MESOGLEA, MesogleaDripParticle.BMesogleaHangProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.BLUE_FALLING_MESOGLEA, MesogleaDripParticle.BMesogleaFallProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.BLUE_LANDING_MESOGLEA, MesogleaDripParticle.BMesogleaLandProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.YELLOW_HANGING_MESOGLEA, MesogleaDripParticle.YMesogleaHangProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.YELLOW_FALLING_MESOGLEA, MesogleaDripParticle.YMesogleaFallProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.YELLOW_LANDING_MESOGLEA, MesogleaDripParticle.YMesogleaLandProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.LIME_HANGING_MESOGLEA, MesogleaDripParticle.LMesogleaHangProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.LIME_FALLING_MESOGLEA, MesogleaDripParticle.LMesogleaFallProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.LIME_LANDING_MESOGLEA, MesogleaDripParticle.LMesogleaLandProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.PINK_HANGING_MESOGLEA, MesogleaDripParticle.PMesogleaHangProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.PINK_FALLING_MESOGLEA, MesogleaDripParticle.PMesogleaFallProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.PINK_LANDING_MESOGLEA, MesogleaDripParticle.PMesogleaLandProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.RED_HANGING_MESOGLEA, MesogleaDripParticle.RMesogleaHangProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.RED_FALLING_MESOGLEA, MesogleaDripParticle.RMesogleaFallProvider::new);
		ParticleFactoryRegistry.getInstance().register(RegisterParticles.RED_LANDING_MESOGLEA, MesogleaDripParticle.RMesogleaLandProvider::new);

        EntityRendererRegistry.register(RegisterEntities.FIREFLY, FireflyRenderer::new);
        EntityRendererRegistry.register(RegisterEntities.ANCIENT_HORN_PROJECTILE_ENTITY, AncientHornProjectileRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ANCIENT_HORN_PROJECTILE_LAYER, AncientHornProjectileModel::createBodyLayer);
        EntityRendererRegistry.register(RegisterEntities.JELLYFISH, JellyfishRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(JELLYFISH, JellyfishModel::createBodyLayer);
		EntityRendererRegistry.register(RegisterEntities.TUMBLEWEED, TumbleweedRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(TUMBLEWEED, TumbleweedModel::createBodyLayer);
		EntityRendererRegistry.register(RegisterEntities.COCONUT, ThrownItemRenderer::new);

		BlockEntityRendererRegistry.register(BlockEntityType.SCULK_SENSOR, SculkSensorBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(SCULK_SENSOR, SculkSensorBlockEntityRenderer::getTexturedModelData);

		BlockEntityRendererRegistry.register(RegisterBlockEntities.DISPLAY_LANTERN, DisplayLanternBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(DISPLAY_LANTERN, DisplayLanternBlockEntityRenderer::getTexturedModelData);

		BlockEntityRendererRegistry.register(RegisterBlockEntities.STONE_CHEST, StoneChestBlockEntityRenderer::new);
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

		receiveFireflyCaptureInfoPacket();
		receiveAncientHornKillInfoPacket();
		FlyBySoundHub.AUTO_ENTITIES_AND_SOUNDS.put(RegisterEntities.ANCIENT_HORN_PROJECTILE_ENTITY, new FlyBySoundHub.FlyBySound(1.0F, 0.5F, SoundSource.PLAYERS, RegisterSounds.ENTITY_ANCIENT_HORN_PROJECTILE_FLYBY));

		ItemProperties.register(RegisterItems.ANCIENT_HORN, WilderWild.vanillaId("tooting"), (itemStack, clientLevel, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
		ItemProperties.register(RegisterItems.COPPER_HORN, WilderWild.vanillaId("tooting"), (itemStack, clientLevel, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) -> {
			if (level == null || pos == null) {
				return 7455580;
			}
			return 2129968;
		}), RegisterBlocks.FLOWERING_LILY_PAD);

        ColorProviderRegistry.ITEM.register(((state, tintIndex) -> 5877296), RegisterBlocks.BAOBAB_LEAVES);
        ColorProviderRegistry.ITEM.register(((state, tintIndex) -> 5877296), RegisterBlocks.CYPRESS_LEAVES);
		ColorProviderRegistry.ITEM.register(((state, tintIndex) -> 5877296), RegisterBlocks.PALM_LEAVES);

        ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) -> {
            assert level != null;
			assert pos != null;
			return BiomeColors.getAverageFoliageColor(level, pos);
        }), RegisterBlocks.BAOBAB_LEAVES);
        ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) -> {
            assert level != null;
			assert pos != null;
			return BiomeColors.getAverageFoliageColor(level, pos);
        }), RegisterBlocks.CYPRESS_LEAVES);
		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) -> {
			assert level != null;
			assert pos != null;
			return BiomeColors.getAverageFoliageColor(level, pos);
		}), RegisterBlocks.PALM_LEAVES);
        ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) -> {
            assert level != null;
			assert pos != null;
			return BiomeColors.getAverageFoliageColor(level, pos);
        }), RegisterBlocks.POTTED_GRASS);
		ColorProviderRegistry.BLOCK.register(((state, level, pos, tintIndex) -> {
			assert level != null;
			assert pos != null;
			return BiomeColors.getAverageGrassColor(level, pos);
		}), RegisterBlocks.BUSH);
    }

	private static void receiveAncientHornProjectilePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.HORN_PROJECTILE_PACKET_ID, (ctx, handler, byteBuf, responseSender) -> {
			EntityType<?> et = Registry.ENTITY_TYPE.byId(byteBuf.readVarInt());
			UUID uuid = byteBuf.readUUID();
			int entityId = byteBuf.readVarInt();
			Vec3 pos = AncientHornProjectile.EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
			float pitch = AncientHornProjectile.EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			float yaw = AncientHornProjectile.EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			WilderWild.log("Receiving Ancient Horn Projectile Packet At " + pos, WilderWild.DEV_LOGGING);
			ctx.execute(() -> {
				if (Minecraft.getInstance().level == null)
					throw new IllegalStateException("Tried to spawn entity in a null world!");
				Entity e = et.create(Minecraft.getInstance().level);
				if (e == null)
					throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getKey(et) + "\"!");
				e.syncPacketPositionCodec(pos.x, pos.y, pos.z);
				e.setPosRaw(pos.x, pos.y, pos.z);
				e.setXRot(pitch);
				e.setYRot(yaw);
				e.setId(entityId);
				e.setUUID(uuid);
				Minecraft.getInstance().level.putNonPlayerEntity(entityId, e);
				WilderWild.log("Spawned Ancient Horn Projectile", WilderWild.UNSTABLE_LOGGING);
			});
		});
	}

	private static void receiveEasyEchoerBubblePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.FLOATING_SCULK_BUBBLE_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			int size = byteBuf.readVarInt();
			int age = byteBuf.readVarInt();
			double yVel = byteBuf.readDouble();
			int count = byteBuf.readVarInt();
			ctx.execute(() -> {
				if (Minecraft.getInstance().level == null)
					throw new IllegalStateException("why is your world null");
				for (int i = 0; i < count; i++) {
					Minecraft.getInstance().level.addParticle(RegisterParticles.FLOATING_SCULK_BUBBLE, pos.x, pos.y, pos.z, size, age, yVel);
				}
			});
		});
	}

	private static void receiveSeedPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.SEED_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			int count = byteBuf.readVarInt();
			ParticleOptions particle = byteBuf.readBoolean() ? RegisterParticles.MILKWEED_SEED : RegisterParticles.DANDELION_SEED;
			ctx.execute(() -> {
				if (Minecraft.getInstance().level == null)
					throw new IllegalStateException("why is your world null");
				for (int i = 0; i < count; i++) {
					Minecraft.getInstance().level.addParticle(particle, pos.x, pos.y, pos.z, 0, 0, 0);
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
			ParticleOptions particle = byteBuf.readBoolean() ? RegisterParticles.CONTROLLED_MILKWEED_SEED : RegisterParticles.CONTROLLED_DANDELION_SEED;
			ctx.execute(() -> {
				if (Minecraft.getInstance().level == null)
					throw new IllegalStateException("why is your world null");
				for (int i = 0; i < count; i++) {
					Minecraft.getInstance().level.addParticle(particle, pos.x, pos.y, pos.z, velx, vely, velz);
				}
			});
		});
	}

	private static void receiveTermitePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.TERMITE_PARTICLE_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			int count = byteBuf.readVarInt();
			ctx.execute(() -> {
				if (Minecraft.getInstance().level == null)
					throw new IllegalStateException("why is your world null");
				for (int i = 0; i < count; i++) {
					Minecraft.getInstance().level.addParticle(RegisterParticles.TERMITE, pos.x, pos.y, pos.z, AdvancedMath.randomPosNeg() / 14, AdvancedMath.randomPosNeg() / 14, AdvancedMath.randomPosNeg() / 14);
				}
			});
		});
	}

	private static void receiveSensorHiccupPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.SENSOR_HICCUP_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
			ctx.execute(() -> {
				if (Minecraft.getInstance().level == null)
					throw new IllegalStateException("why is your world null");
				ClientLevel level = Minecraft.getInstance().level;
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

	private static void receiveFireflyCaptureInfoPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.CAPTURE_FIREFLY_NOTIFY_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			boolean creative = byteBuf.readBoolean();
			boolean natural = byteBuf.readBoolean();
			ctx.execute(() -> {
				if (Minecraft.getInstance().level == null)
					throw new IllegalStateException("why is your world null");
				CompetitionCounter.addFireflyCapture(creative, natural);
			});
		});
	}

	private static void receiveAncientHornKillInfoPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWild.ANCIENT_HORN_KILL_NOTIFY_PACKET, (ctx, handler, byteBuf, responseSender) -> {
			boolean creative = byteBuf.readBoolean();
			boolean natural = byteBuf.readBoolean();
			ctx.execute(() -> {
				if (Minecraft.getInstance().level == null)
					throw new IllegalStateException("why is your world null");
				CompetitionCounter.addAncientHornKill(creative, natural);
			});
		});
	}

    private static void receiveJellyStingPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.JELLY_STING_PACKET, (ctx, handler, byteBuf, responseSender) -> ctx.execute(() -> {
			if (Minecraft.getInstance().level != null) {
				LocalPlayer player = ctx.player;
				if (player != null) {
					Minecraft.getInstance().level.playSound(player, player.getX(), player.getY(), player.getZ(), RegisterSounds.ENTITY_JELLYFISH_STING, SoundSource.NEUTRAL, 1.0F, Minecraft.getInstance().level.random.nextFloat() * 0.2F + 0.9F);
				}
			}
		}));
    }
}
