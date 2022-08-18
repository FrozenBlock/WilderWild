package net.frozenblock.wilderwild.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;

import java.util.Locale;

public class AncientHornParticleEffect implements ParticleEffect {
    public static final Codec<AncientHornParticleEffect> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(Codec.INT.fieldOf("delay").forGetter(AncientHornParticleEffect -> AncientHornParticleEffect.delay))
                    .apply(instance, AncientHornParticleEffect::new)
    );
    public static final ParticleEffect.Factory<AncientHornParticleEffect> FACTORY = new ParticleEffect.Factory<>() {
        public AncientHornParticleEffect read(ParticleType<AncientHornParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            int i = stringReader.readInt();
            return new AncientHornParticleEffect(i);
        }

        public AncientHornParticleEffect read(ParticleType<AncientHornParticleEffect> particleType, PacketByteBuf packetByteBuf) {
            return new AncientHornParticleEffect(packetByteBuf.readVarInt());
        }
    };
    private final int delay;

    public AncientHornParticleEffect(int delay) {
        this.delay = delay;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVarInt(this.delay);
    }

    @Override
    public String asString() {
        return String.format(Locale.ROOT, "%s %d", Registry.PARTICLE_TYPE.getId(this.getType()), this.delay);
    }

    @Override
    public ParticleType<AncientHornParticleEffect> getType() {
        return RegisterParticles.ANCIENT_HORN;
    }

    public int getDelay() {
        return this.delay;
    }
}
