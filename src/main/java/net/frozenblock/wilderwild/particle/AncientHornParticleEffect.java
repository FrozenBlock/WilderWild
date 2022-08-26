package net.frozenblock.wilderwild.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;

public class AncientHornParticleEffect implements ParticleOptions {
    public static final Codec<AncientHornParticleEffect> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(Codec.INT.fieldOf("delay").forGetter(AncientHornParticleEffect -> AncientHornParticleEffect.delay),
                            Codec.DOUBLE.fieldOf("xd").forGetter(AncientHornParticleEffect -> AncientHornParticleEffect.xd),
                            Codec.DOUBLE.fieldOf("zd").forGetter(AncientHornParticleEffect -> AncientHornParticleEffect.zd))
                    .apply(instance, AncientHornParticleEffect::new)
    );
    public static final ParticleOptions.Deserializer<AncientHornParticleEffect> FACTORY = new ParticleOptions.Deserializer<>() {
        public AncientHornParticleEffect fromCommand(ParticleType<AncientHornParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            int i = stringReader.readInt();
            double x = stringReader.readDouble();
            double z = stringReader.readDouble();
            return new AncientHornParticleEffect(i, x, z);
        }

        public AncientHornParticleEffect fromNetwork(ParticleType<AncientHornParticleEffect> particleType, FriendlyByteBuf packetByteBuf) {
            return new AncientHornParticleEffect(packetByteBuf.readVarInt(), packetByteBuf.readDouble(), packetByteBuf.readDouble());
        }
    };
    private final int delay;
    private final double xd;
    private final double zd;

    public AncientHornParticleEffect(int delay, double xRot, double zRot) {
        this.delay = delay;
        this.xd = xRot;
        this.zd = zRot;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeVarInt(this.delay);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %d", Registry.PARTICLE_TYPE.getKey(this.getType()), this.delay);
    }

    @Override
    public ParticleType<AncientHornParticleEffect> getType() {
        return RegisterParticles.ANCIENT_HORN;
    }

    public int getDelay() {
        return this.delay;
    }

    public double getXd() {
        return this.xd;
    }

    public double getZd() {
        return this.zd;
    }
}
