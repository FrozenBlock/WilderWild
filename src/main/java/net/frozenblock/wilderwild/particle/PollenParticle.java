package net.frozenblock.wilderwild.particle;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class PollenParticle extends SpriteBillboardParticle {
    public boolean hasCarryingWind;
    PollenParticle(ClientWorld world, SpriteProvider spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
        this.setBoundingBoxSpacing(0.01F, 0.02F);
        this.setSprite(spriteProvider);
        this.scale *= this.random.nextFloat() * 0.6F + 0.6F;
        this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        this.collidesWithWorld = true;
        this.velocityMultiplier = 1.0F;
        this.gravityStrength = 0.0F;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age>this.maxAge/5 && !this.onGround && this.hasCarryingWind) {
            if (random.nextFloat()>0.98) {
                this.velocityY = this.velocityY + 1;
                this.velocityX*=1.2;
                this.velocityZ*=1.2;
            }
        }
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class PollenFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public PollenFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            PollenParticle pollenParticle = new PollenParticle(clientWorld, this.spriteProvider, d, e, f, 0.0D, -0.800000011920929D, 0.0D) {};
            pollenParticle.maxAge = MathHelper.nextBetween(clientWorld.random, 500, 1000);
            pollenParticle.gravityStrength = 0.01F;
            pollenParticle.setColor(250F/255F, 171F/255F, 28F/255F);
            return pollenParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class DandelionFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public DandelionFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            double windex = Math.cos((clientWorld.getTimeOfDay()*Math.PI)/12000) * 1.1;
            double windZ = -Math.sin((clientWorld.getTimeOfDay()*Math.PI)/12000) * 1.1;
            PollenParticle pollenParticle = new PollenParticle(clientWorld, this.spriteProvider, d, e, f, windex, -0.800000011920929D, windZ);
            pollenParticle.maxAge = MathHelper.nextBetween(clientWorld.random, 500, 1000);
            pollenParticle.gravityStrength = 0.01F;
            pollenParticle.velocityX = (windex + clientWorld.random.nextPredictable(0, 0.8))/17;
            pollenParticle.velocityZ = (windZ + clientWorld.random.nextPredictable(0, 0.8))/17;
            pollenParticle.setColor(250F/255F, 250F/255F, 250F/255F);
            pollenParticle.hasCarryingWind = true;
            return pollenParticle;
        }
    }

    public static class EasyDandelionSeedPacket {
        public static void createParticle(World world, Vec3d pos, int count) {
            if (world.isClient)
                throw new IllegalStateException("Particle attempting spawning on THE CLIENT JESUS CHRIST WHAT THE HECK");
            PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
            byteBuf.writeDouble(pos.x);
            byteBuf.writeDouble(pos.y);
            byteBuf.writeDouble(pos.z);
            byteBuf.writeVarInt(count);
            for (ServerPlayerEntity player : PlayerLookup.around((ServerWorld)world, pos, 32)) {
                ServerPlayNetworking.send(player, WilderWild.DANDELION_SEED_PACKET, byteBuf);
            }
        }
    }
}
