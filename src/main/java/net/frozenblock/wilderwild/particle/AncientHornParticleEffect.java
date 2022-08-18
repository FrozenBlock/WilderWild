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
            instance -> instance.group(Codec.INT.fieldOf("delay").forGetter(AncientHornParticleEffect -> AncientHornParticleEffect.delay))
                    .apply(instance, AncientHornParticleEffect::new)
    );
    public static final ParticleOptions.Deserializer<AncientHornParticleEffect> FACTORY = new ParticleOptions.Deserializer<>() {
        public AncientHornParticleEffect fromCommand(ParticleType<AncientHornParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            int i = stringReader.readInt();
            return new AncientHornParticleEffect(i);
        }

        public AncientHornParticleEffect fromNetwork(ParticleType<AncientHornParticleEffect> particleType, FriendlyByteBuf packetByteBuf) {
            return new AncientHornParticleEffect(packetByteBuf.readVarInt());
        }
    };
    private final int delay;

    public AncientHornParticleEffect(int delay) {
        this.delay = delay;
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
}
