package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;

public class RegisterParticles {
    public static final SimpleParticleType POLLEN = FabricParticleTypes.simple();
    public static final SimpleParticleType DANDELION_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType CONTROLLED_DANDELION_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType MILKWEED_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType CONTROLLED_MILKWEED_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType FLOATING_SCULK_BUBBLE = FabricParticleTypes.simple();
    public static final SimpleParticleType TERMITE = FabricParticleTypes.simple();

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
}
