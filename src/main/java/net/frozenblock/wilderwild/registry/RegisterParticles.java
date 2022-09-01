package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.particle.AncientHornParticleEffect;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Function;

public final class RegisterParticles {
    public static final SimpleParticleType POLLEN = FabricParticleTypes.simple();
    public static final SimpleParticleType DANDELION_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType CONTROLLED_DANDELION_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType MILKWEED_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType CONTROLLED_MILKWEED_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType FLOATING_SCULK_BUBBLE = FabricParticleTypes.simple();
    public static final SimpleParticleType TERMITE = FabricParticleTypes.simple();
    public static final ParticleType<AncientHornParticleEffect> ANCIENT_HORN = register("ancient_horn", false, AncientHornParticleEffect.FACTORY, type -> AncientHornParticleEffect.CODEC);

    public static void registerParticles() {
        WilderWild.logWild("Registering Particles for", WilderWild.UNSTABLE_LOGGING);
        Registry.register(Registry.PARTICLE_TYPE, WilderWild.id("pollen"), POLLEN);
        Registry.register(Registry.PARTICLE_TYPE, WilderWild.id("dandelion_seed"), DANDELION_SEED);
        Registry.register(Registry.PARTICLE_TYPE, WilderWild.id("controlled_dandelion_seed"), CONTROLLED_DANDELION_SEED);
        Registry.register(Registry.PARTICLE_TYPE, WilderWild.id("milkweed_seed"), MILKWEED_SEED);
        Registry.register(Registry.PARTICLE_TYPE, WilderWild.id("controlled_milkweed_seed"), CONTROLLED_MILKWEED_SEED);
        Registry.register(Registry.PARTICLE_TYPE, WilderWild.id("floating_sculk_bubble"), FLOATING_SCULK_BUBBLE);
        Registry.register(Registry.PARTICLE_TYPE, WilderWild.id("termite"), TERMITE);
    }

    private static <T extends ParticleOptions> ParticleType<T> register(
            String name, boolean alwaysShow, ParticleOptions.Deserializer<T> factory, Function<ParticleType<T>, Codec<T>> codecGetter
    ) {
        return Registry.register(Registry.PARTICLE_TYPE, WilderWild.id(name), new ParticleType<T>(alwaysShow, factory) {
            @Override
            public Codec<T> codec() {
                return codecGetter.apply(this);
            }
        });
    }
}
