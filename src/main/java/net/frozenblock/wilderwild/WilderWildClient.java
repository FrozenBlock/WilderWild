package net.frozenblock.wilderwild;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.frozenblock.api.mathematics.AdvancedMath;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.entity.render.*;
import net.frozenblock.wilderwild.misc.CompetitionCounter;
import net.frozenblock.wilderwild.misc.FlowerLichenParticleRegistry;
import net.frozenblock.wilderwild.misc.PVZGWSound.FlyBySoundHub;
import net.frozenblock.wilderwild.misc.PVZGWSound.MovingSoundLoop;
import net.frozenblock.wilderwild.misc.PVZGWSound.MovingSoundLoopWithRestriction;
import net.frozenblock.wilderwild.misc.PVZGWSound.MovingSoundWithRestriction;
import net.frozenblock.wilderwild.misc.config.MMDistantInteractions;
import net.frozenblock.wilderwild.particle.FloatingSculkBubbleParticle;
import net.frozenblock.wilderwild.particle.PollenParticle;
import net.frozenblock.wilderwild.particle.TermiteParticle;
import net.frozenblock.wilderwild.registry.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.UUID;

public final class WilderWildClient implements ClientModInitializer {
    public static final EntityModelLayer ANCIENT_HORN_PROJECTILE_LAYER = new EntityModelLayer(WilderWild.id("ancient_horn_projectile"), "main");
    public static final EntityModelLayer SCULK_SENSOR = new EntityModelLayer(WilderWild.id("sculk_sensor"), "main");
    public static final EntityModelLayer FIREFLY_LANTERN = new EntityModelLayer(WilderWild.id("firefly_lantern"), "main");
    public static final EntityModelLayer STONE_CHEST = new EntityModelLayer(WilderWild.id("stone_chest"), "main");
    public static final EntityModelLayer DOUBLE_STONE_CHEST_LEFT = new EntityModelLayer(WilderWild.id("double_stone_chest_left"), "main");
    public static final EntityModelLayer DOUBLE_STONE_CHEST_RIGHT = new EntityModelLayer(WilderWild.id("double_stone_chest_right"), "main");

    @Override
    public void onInitializeClient() {
        FlowerLichenParticleRegistry.init();

        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CARNATION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.SEEDING_DANDELION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_CARNATION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_SEEDING_DANDELION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_BAOBAB_NUT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_CYPRESS_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.DATURA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CATTAIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.ALGAE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.MILKWEED, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POLLEN_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.ECHO_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HANGING_TENDRIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.FLOWERING_LILY_PAD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BROWN_SHELF_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.RED_SHELF_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BAOBAB_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CYPRESS_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BAOBAB_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CYPRESS_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BAOBAB_NUT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CYPRESS_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.GLORY_OF_THE_SNOW, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.WHITE_GLORY_OF_THE_SNOW, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BLUE_GLORY_OF_THE_SNOW, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PINK_GLORY_OF_THE_SNOW, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PURPLE_GLORY_OF_THE_SNOW, RenderLayer.getCutout());
        //BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CYPRESS_ROOTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.TERMITE_MOUND, RenderLayer.getSolid());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.FIREFLY_LANTERN, RenderLayer.getCutout());

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
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

        ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
            registry.register(WilderWild.id("entity/stone_chest/stone"));
            registry.register(WilderWild.id("entity/stone_chest/stone_left"));
            registry.register(WilderWild.id("entity/stone_chest/stone_right"));
        });

        ParticleFactoryRegistry.getInstance().register(RegisterParticles.POLLEN, PollenParticle.PollenFactory::new);
        ParticleFactoryRegistry.getInstance().register(RegisterParticles.DANDELION_SEED, PollenParticle.DandelionFactory::new);
        ParticleFactoryRegistry.getInstance().register(RegisterParticles.CONTROLLED_DANDELION_SEED, PollenParticle.ControlledDandelionFactory::new);
        ParticleFactoryRegistry.getInstance().register(RegisterParticles.MILKWEED_SEED, PollenParticle.MilkweedFactory::new);
        ParticleFactoryRegistry.getInstance().register(RegisterParticles.CONTROLLED_MILKWEED_SEED, PollenParticle.ControlledMilkweedFactory::new);
        ParticleFactoryRegistry.getInstance().register(RegisterParticles.FLOATING_SCULK_BUBBLE, FloatingSculkBubbleParticle.BubbleFactory::new);
        ParticleFactoryRegistry.getInstance().register(RegisterParticles.TERMITE, TermiteParticle.Factory::new);

        EntityRendererRegistry.register(RegisterEntities.FIREFLY, FireflyRenderer::new);
        EntityRendererRegistry.register(RegisterEntities.ANCIENT_HORN_PROJECTILE_ENTITY, AncientHornProjectileRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ANCIENT_HORN_PROJECTILE_LAYER, AncientHornProjectileModel::getTexturedModelData);

        BlockEntityRendererRegistry.register(BlockEntityType.SCULK_SENSOR, SculkSensorBlockEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SCULK_SENSOR, SculkSensorBlockEntityRenderer::getTexturedModelData);

        BlockEntityRendererRegistry.register(RegisterBlockEntities.FIREFLY_LANTERN, FireflyLanternBlockEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(FIREFLY_LANTERN, FireflyLanternBlockEntityRenderer::getTexturedModelData);

        BlockEntityRendererRegistry.register(RegisterBlockEntities.STONE_CHEST, StoneChestBlockEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(STONE_CHEST, StoneChestBlockEntityRenderer::getSingleTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DOUBLE_STONE_CHEST_LEFT, StoneChestBlockEntityRenderer::getLeftDoubleTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DOUBLE_STONE_CHEST_RIGHT, StoneChestBlockEntityRenderer::getRightDoubleTexturedModelData);

        receiveAncientHornProjectilePacket();
        receiveEasyEchoerBubblePacket();
        receiveSeedPacket();
        receiveControlledSeedPacket();
        receiveTermitePacket();
        receiveSensorHiccupPacket();

        receiveFireflyCaptureInfoPacket();
        receiveAncientHornKillInfoPacket();

        receiveFlybySoundPacket();
        receiveMovingLoopingSoundPacket();
        receiveMovingRestrictionSoundPacket();
        receiveMovingRestrictionLoopingSoundPacket();

        ModelPredicateProviderRegistry.register(RegisterItems.ANCIENT_HORN, new Identifier("tooting"), (itemStack, clientWorld, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F);
        ModelPredicateProviderRegistry.register(RegisterItems.COPPER_HORN, new Identifier("tooting"), (itemStack, clientWorld, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F);

        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return 7455580;
            }
            return 2129968;
        }), RegisterBlocks.FLOWERING_LILY_PAD);

        ColorProviderRegistry.ITEM.register(((state, tintIndex) -> 5877296), RegisterBlocks.BAOBAB_LEAVES);
        ColorProviderRegistry.ITEM.register(((state, tintIndex) -> 5877296), RegisterBlocks.CYPRESS_LEAVES);

        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            assert world != null;
            return BiomeColors.getFoliageColor(world, pos);
        }), RegisterBlocks.BAOBAB_LEAVES);
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            assert world != null;
            return BiomeColors.getFoliageColor(world, pos);
        }), RegisterBlocks.CYPRESS_LEAVES);

        /*ClientTickEvents.START_WORLD_TICK.register(e -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world != null) {
                FlyBySoundHub.update(client, client.player, false); //CHANGE TO FALSE TO NOT AUTOMATICALLY ADD FLYBY SOUNDS
            }
        });*/
        if (WilderWild.hasModMenu()) {
            MMDistantInteractions.loadConfig();
            WilderWild.RENDER_TENDRILS = MMDistantInteractions.tendrilsEnabled();
        }
    }

    public static void requestBlockEntitySync(BlockPos pos, World world) {
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        if (pos != null && world != null) {
            byteBuf.writeInt(pos.getX());
            byteBuf.writeInt(pos.getY());
            byteBuf.writeInt(pos.getZ());
            byteBuf.writeRegistryKey(world.getRegistryKey());
            ClientPlayNetworking.send(WilderWild.REQUEST_BLOCK_ENTITY_SYNC_PACKET, byteBuf);
        }
    }

    private static void receiveAncientHornProjectilePacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.HORN_PROJECTILE_PACKET_ID, (ctx, handler, byteBuf, responseSender) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = AncientHornProjectile.EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
            float pitch = AncientHornProjectile.EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            float yaw = AncientHornProjectile.EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            WilderWild.log("Receiving Ancient Horn Projectile Packet At " + pos, WilderWild.DEV_LOGGING);
            ctx.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(MinecraftClient.getInstance().world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos.x, pos.y, pos.z);
                e.setPos(pos.x, pos.y, pos.z);
                e.setPitch(pitch);
                e.setYaw(yaw);
                e.setId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
                WilderWild.log("Spawned Ancient Horn Projectile", WilderWild.UNSTABLE_LOGGING);
            });
        });
    }

    private static void receiveEasyEchoerBubblePacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.FLOATING_SCULK_BUBBLE_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            Vec3d pos = new Vec3d(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
            int size = byteBuf.readVarInt();
            int age = byteBuf.readVarInt();
            double yVel = byteBuf.readDouble();
            int count = byteBuf.readVarInt();
            ctx.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("why is your world null");
                for (int i = 0; i < count; i++) {
                    MinecraftClient.getInstance().world.addParticle(RegisterParticles.FLOATING_SCULK_BUBBLE, pos.x, pos.y, pos.z, size, age, yVel);
                }
            });
        });
    }

    private static void receiveSeedPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.SEED_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            Vec3d pos = new Vec3d(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
            int count = byteBuf.readVarInt();
            ParticleEffect particle = byteBuf.readBoolean() ? RegisterParticles.MILKWEED_SEED : RegisterParticles.DANDELION_SEED;
            ctx.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("why is your world null");
                for (int i = 0; i < count; i++) {
                    MinecraftClient.getInstance().world.addParticle(particle, pos.x, pos.y, pos.z, 0, 0, 0);
                }
            });
        });
    }

    private static void receiveControlledSeedPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.CONTROLLED_SEED_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            Vec3d pos = new Vec3d(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
            double velx = byteBuf.readDouble();
            double vely = byteBuf.readDouble();
            double velz = byteBuf.readDouble();
            int count = byteBuf.readVarInt();
            ParticleEffect particle = byteBuf.readBoolean() ? RegisterParticles.CONTROLLED_MILKWEED_SEED : RegisterParticles.CONTROLLED_DANDELION_SEED;
            ctx.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("why is your world null");
                for (int i = 0; i < count; i++) {
                    MinecraftClient.getInstance().world.addParticle(particle, pos.x, pos.y, pos.z, velx, vely, velz);
                }
            });
        });
    }

    private static void receiveTermitePacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.TERMITE_PARTICLE_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            Vec3d pos = new Vec3d(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
            int count = byteBuf.readVarInt();
            ctx.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("why is your world null");
                for (int i = 0; i < count; i++) {
                    MinecraftClient.getInstance().world.addParticle(RegisterParticles.TERMITE, pos.x, pos.y, pos.z, AdvancedMath.randomPosNeg() / 14, AdvancedMath.randomPosNeg() / 14, AdvancedMath.randomPosNeg() / 14);
                }
            });
        });
    }

    private static void receiveSensorHiccupPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.SENSOR_HICCUP_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            Vec3d pos = new Vec3d(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
            ctx.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("why is your world null");
                ClientWorld world = MinecraftClient.getInstance().world;
                int i = 5578058;
                boolean bl2 = world.random.nextBoolean();
                if (bl2) {
                    double d = (double) (i >> 16 & 255) / 255.0D;
                    double e = (double) (i >> 8 & 255) / 255.0D;
                    double f = (double) (i & 255) / 255.0D;
                    world.addParticle(ParticleTypes.ENTITY_EFFECT, pos.x, pos.y, pos.z, d, e, f);
                }
            });
        });
    }

    private static void receiveFireflyCaptureInfoPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.CAPTURE_FIREFLY_NOTIFY_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            boolean creative = byteBuf.readBoolean();
            boolean natural = byteBuf.readBoolean();
            ctx.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
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
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("why is your world null");
                CompetitionCounter.addAncientHornKill(creative, natural);
            });
        });
    }

    private static void receiveMovingLoopingSoundPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.MOVING_LOOPING_SOUND_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            int id = byteBuf.readVarInt();
            SoundEvent sound = byteBuf.readRegistryValue(Registry.SOUND_EVENT);
            SoundCategory category = byteBuf.readEnumConstant(SoundCategory.class);
            float volume = byteBuf.readFloat();
            float pitch = byteBuf.readFloat();
            Identifier predicateId = byteBuf.readIdentifier();
            ctx.execute(() -> {
                ClientWorld world = MinecraftClient.getInstance().world;
                if (world == null)
                    throw new IllegalStateException("why is your world null");
                Entity entity = world.getEntityById(id);
                if (entity == null)
                    throw new IllegalStateException("Unable to play moving looping sound (from wilderwild) whilst entity does not exist!");
                RegisterMovingSoundRestrictions.LoopPredicate<?> predicate = RegisterMovingSoundRestrictions.getPredicate(predicateId);
                if (predicate == null) {
                    predicate = RegisterMovingSoundRestrictions.getPredicate(WilderWild.id("default"));
                }
                MinecraftClient.getInstance().getSoundManager().play(new MovingSoundLoop(entity, sound, category, volume, pitch, predicate));
            });
        });
    }

    private static void receiveMovingRestrictionSoundPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.MOVING_RESTRICTION_SOUND_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            int id = byteBuf.readVarInt();
            SoundEvent sound = byteBuf.readRegistryValue(Registry.SOUND_EVENT);
            SoundCategory category = byteBuf.readEnumConstant(SoundCategory.class);
            float volume = byteBuf.readFloat();
            float pitch = byteBuf.readFloat();
            Identifier predicateId = byteBuf.readIdentifier();
            ctx.execute(() -> {
                ClientWorld world = MinecraftClient.getInstance().world;
                if (world == null)
                    throw new IllegalStateException("why is your world null");
                Entity entity = world.getEntityById(id);
                if (entity == null)
                    throw new IllegalStateException("Unable to play moving sound with restriction (from wilderwild) whilst entity does not exist!");
                RegisterMovingSoundRestrictions.LoopPredicate<?> predicate = RegisterMovingSoundRestrictions.getPredicate(predicateId);
                if (predicate == null) {
                    predicate = RegisterMovingSoundRestrictions.getPredicate(WilderWild.id("default"));
                }
                MinecraftClient.getInstance().getSoundManager().play(new MovingSoundWithRestriction(entity, sound, category, volume, pitch, predicate));
            });
        });
    }

    private static void receiveMovingRestrictionLoopingSoundPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.MOVING_RESTRICTION_LOOPING_SOUND_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            int id = byteBuf.readVarInt();
            SoundEvent sound = byteBuf.readRegistryValue(Registry.SOUND_EVENT);
            SoundCategory category = byteBuf.readEnumConstant(SoundCategory.class);
            float volume = byteBuf.readFloat();
            float pitch = byteBuf.readFloat();
            Identifier predicateId = byteBuf.readIdentifier();
            ctx.execute(() -> {
                ClientWorld world = MinecraftClient.getInstance().world;
                if (world == null)
                    throw new IllegalStateException("why is your world null");
                Entity entity = world.getEntityById(id);
                if (entity == null)
                    throw new IllegalStateException("Unable to play moving looping sound (from wilderwild) whilst entity does not exist!");
                RegisterMovingSoundRestrictions.LoopPredicate<?> predicate = RegisterMovingSoundRestrictions.getPredicate(predicateId);
                if (predicate == null) {
                    predicate = RegisterMovingSoundRestrictions.getPredicate(WilderWild.id("default"));
                }
                MinecraftClient.getInstance().getSoundManager().play(new MovingSoundLoopWithRestriction(entity, sound, category, volume, pitch, predicate));
            });
        });
    }

    private static void receiveFlybySoundPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.FLYBY_SOUND_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            int id = byteBuf.readVarInt();
            SoundEvent sound = byteBuf.readRegistryValue(Registry.SOUND_EVENT);
            SoundCategory category = byteBuf.readEnumConstant(SoundCategory.class);
            float volume = byteBuf.readFloat();
            float pitch = byteBuf.readFloat();
            ctx.execute(() -> {
                ClientWorld world = MinecraftClient.getInstance().world;
                if (world == null)
                    throw new IllegalStateException("why is your world null");
                Entity entity = world.getEntityById(id);
                if (entity == null)
                    throw new IllegalStateException("Unable to add flyby sound to non-existent entity!");
                FlyBySoundHub.addEntity(entity, sound, category, volume, pitch);
                WilderWild.log("ADDED ENTITY TO FLYBYS", true);
            });
        });
    }

    public static void closeInv() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity clientPlayer = client.player;
        if (clientPlayer != null) {
            if (clientPlayer.currentScreenHandler instanceof GenericContainerScreenHandler) {
                clientPlayer.currentScreenHandler.close(clientPlayer);
                clientPlayer.currentScreenHandler = clientPlayer.playerScreenHandler;
            }
        }
    }

}