package net.frozenblock.wilderwild;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.frozenblock.api.mathematics.AdvancedMath;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.entity.render.AncientHornProjectileModel;
import net.frozenblock.wilderwild.entity.render.AncientHornProjectileRenderer;
import net.frozenblock.wilderwild.entity.render.FireflyRenderer;
import net.frozenblock.wilderwild.misc.CompetitionCounter;
import net.frozenblock.wilderwild.misc.FlowerLichenParticleRegistry;
import net.frozenblock.wilderwild.misc.PVZGWSound.FlyBySoundHub;
import net.frozenblock.wilderwild.misc.PVZGWSound.MovingSoundLoop;
import net.frozenblock.wilderwild.particle.FloatingSculkBubbleParticle;
import net.frozenblock.wilderwild.particle.PollenParticle;
import net.frozenblock.wilderwild.particle.TermiteParticle;
import net.frozenblock.wilderwild.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class WilderWildClient implements ClientModInitializer {
    public static final ModelLayerLocation ANCIENT_HORN_PROJECTILE_LAYER = new ModelLayerLocation(WilderWild.id("ancient_horn_projectile"), "main");

    @Override
    public void onInitializeClient() {
        FlowerLichenParticleRegistry.init();

        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CARNATION, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.WHITE_DANDELION, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_CARNATION, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_WHITE_DANDELION, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_BAOBAB_SAPLING, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_CYPRESS_SAPLING, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.DATURA, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CATTAIL, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.FLOATING_MOSS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.MILKWEED, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POLLEN_BLOCK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.ECHO_GLASS, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HANGING_TENDRIL, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.FLOWERED_LILY_PAD, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BROWN_SHELF_FUNGUS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.RED_SHELF_FUNGUS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BAOBAB_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CYPRESS_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BAOBAB_TRAPDOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CYPRESS_TRAPDOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BAOBAB_SAPLING, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CYPRESS_SAPLING, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.GLORY_OF_THE_SNOW, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.WHITE_GLORY_OF_THE_SNOW, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BLUE_GLORY_OF_THE_SNOW, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PINK_GLORY_OF_THE_SNOW, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.PURPLE_GLORY_OF_THE_SNOW, RenderType.cutout());
        //BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CYPRESS_ROOTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.TERMITE_MOUND, RenderType.solid());

        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((((atlasTexture, registry) -> {
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
        })));

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

        ItemProperties.register(RegisterItems.ANCIENT_HORN, new ResourceLocation("tooting"), (itemStack, clientWorld, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return 7455580;
            }
            return 2129968;
        }), RegisterBlocks.FLOWERED_LILY_PAD);

        ColorProviderRegistry.ITEM.register(((state, tintIndex) -> 5877296), RegisterBlocks.BAOBAB_LEAVES);
        ColorProviderRegistry.ITEM.register(((state, tintIndex) -> 5877296), RegisterBlocks.CYPRESS_LEAVES);

        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            assert world != null;
            return BiomeColors.getAverageFoliageColor(world, pos);
        }), RegisterBlocks.BAOBAB_LEAVES);
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            assert world != null;
            return BiomeColors.getAverageFoliageColor(world, pos);
        }), RegisterBlocks.CYPRESS_LEAVES);

        /*ClientTickEvents.START_WORLD_TICK.register(e -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world != null) {
                FlyBySoundHub.update(client, client.player, false); //CHANGE TO FALSE TO NOT AUTOMATICALLY ADD FLYBY SOUNDS
            }
        });*/
    }


    public void receiveAncientHornProjectilePacket() {
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

    public void receiveEasyEchoerBubblePacket() {
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

    public void receiveSeedPacket() {
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

    public void receiveControlledSeedPacket() {
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

    public void receiveTermitePacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.TERMITE_PARTICLE_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
            int count = byteBuf.readVarInt();
            ctx.execute(() -> {
                if (Minecraft.getInstance().level == null)
                    throw new IllegalStateException("why is your world null");
                for (int i = 0; i < count; i++) {
                    Minecraft.getInstance().level.addParticle(RegisterParticles.TERMITE, pos.x, pos.y, pos.z, AdvancedMath.randomPosNeg() / 7, AdvancedMath.randomPosNeg() / 7, AdvancedMath.randomPosNeg() / 7);
                }
            });
        });
    }

    public void receiveSensorHiccupPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.SENSOR_HICCUP_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            Vec3 pos = new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
            ctx.execute(() -> {
                if (Minecraft.getInstance().level == null)
                    throw new IllegalStateException("why is your world null");
                ClientLevel world = Minecraft.getInstance().level;
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

    public void receiveFireflyCaptureInfoPacket() {
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

    public void receiveAncientHornKillInfoPacket() {
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

    public void receiveMovingLoopingSoundPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.MOVING_LOOPING_SOUND_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            int id = byteBuf.readVarInt();
            SoundEvent sound = byteBuf.readById(Registry.SOUND_EVENT);
            SoundSource category = byteBuf.readEnum(SoundSource.class);
            float volume = byteBuf.readFloat();
            float pitch = byteBuf.readFloat();
            ResourceLocation predicateId = byteBuf.readResourceLocation();
            ctx.execute(() -> {
                ClientLevel world = Minecraft.getInstance().level;
                if (world == null)
                    throw new IllegalStateException("why is your world null");
                Entity entity = world.getEntity(id);
                if (entity == null)
                    throw new IllegalStateException("Unable to play moving looping sound (from wilderwild) whilst entity does not exist!");
                RegisterLoopingSoundRestrictions.LoopPredicate<?> predicate = RegisterLoopingSoundRestrictions.getPredicate(predicateId);
                if (predicate == null) {
                    predicate = RegisterLoopingSoundRestrictions.getPredicate(WilderWild.id("default"));
                }
                Minecraft.getInstance().getSoundManager().play(new MovingSoundLoop(entity, sound, category, volume, pitch, predicate));
            });
        });
    }

    public void receiveFlybySoundPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.FLYBY_SOUND_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            int id = byteBuf.readVarInt();
            SoundEvent sound = byteBuf.readById(Registry.SOUND_EVENT);
            SoundSource category = byteBuf.readEnum(SoundSource.class);
            float volume = byteBuf.readFloat();
            float pitch = byteBuf.readFloat();
            ctx.execute(() -> {
                ClientLevel world = Minecraft.getInstance().level;
                if (world == null)
                    throw new IllegalStateException("why is your world null");
                Entity entity = world.getEntity(id);
                if (entity == null)
                    throw new IllegalStateException("Unable to add flyby sound to non-existent entity!");
                FlyBySoundHub.addEntity(entity, sound, category, volume, pitch);
                WilderWild.log("ADDED ENTITY TO FLYBYS", true);
            });
        });
    }

}