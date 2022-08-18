package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.particle.AncientHornParticleEffect;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;

import java.util.function.Function;

public final class RegisterParticles {
    public static final DefaultParticleType POLLEN = FabricParticleTypes.simple();
    public static final DefaultParticleType DANDELION_SEED = FabricParticleTypes.simple();
    public static final DefaultParticleType CONTROLLED_DANDELION_SEED = FabricParticleTypes.simple();
    public static final DefaultParticleType MILKWEED_SEED = FabricParticleTypes.simple();
    public static final DefaultParticleType CONTROLLED_MILKWEED_SEED = FabricParticleTypes.simple();
    public static final DefaultParticleType FLOATING_SCULK_BUBBLE = FabricParticleTypes.simple();
    public static final DefaultParticleType TERMITE = FabricParticleTypes.simple();
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

    private static <T extends ParticleEffect> ParticleType<T> register(
            String name, boolean alwaysShow, ParticleEffect.Factory<T> factory, Function<ParticleType<T>, Codec<T>> codecGetter
    ) {
        return Registry.register(Registry.PARTICLE_TYPE, WilderWild.id(name), new ParticleType<T>(alwaysShow, factory) {
            @Override
            public Codec<T> getCodec() {
                return codecGetter.apply(this);
            }
        });
    }
}
