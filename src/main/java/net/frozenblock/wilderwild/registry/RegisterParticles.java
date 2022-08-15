package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;

public final class RegisterParticles {
    public static final DefaultParticleType POLLEN = FabricParticleTypes.simple();
    public static final DefaultParticleType DANDELION_SEED = FabricParticleTypes.simple();
    public static final DefaultParticleType CONTROLLED_DANDELION_SEED = FabricParticleTypes.simple();
    public static final DefaultParticleType MILKWEED_SEED = FabricParticleTypes.simple();
    public static final DefaultParticleType CONTROLLED_MILKWEED_SEED = FabricParticleTypes.simple();
    public static final DefaultParticleType FLOATING_SCULK_BUBBLE = FabricParticleTypes.simple();
    public static final DefaultParticleType TERMITE = FabricParticleTypes.simple();

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
