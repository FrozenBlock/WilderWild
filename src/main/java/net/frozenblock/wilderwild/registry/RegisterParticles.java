package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterParticles {
    public static final DefaultParticleType POLLEN = FabricParticleTypes.simple();
    public static final DefaultParticleType DANDELION_SEED = FabricParticleTypes.simple();
    public static final DefaultParticleType CONTROLLED_DANDELION_SEED = FabricParticleTypes.simple();
    public static final DefaultParticleType MILKWEED_SEED = FabricParticleTypes.simple();
    public static final DefaultParticleType CONTROLLED_MILKWEED_SEED = FabricParticleTypes.simple();
    public static final DefaultParticleType FLOATING_SCULK_BUBBLE = FabricParticleTypes.simple();
    public static final DefaultParticleType TERMITE = FabricParticleTypes.simple();
    public static final DefaultParticleType EFFECT_POINT = FabricParticleTypes.simple();

    public static void RegisterParticles() {
        WilderWild.logWild("Registering Particles for", true);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(WilderWild.MOD_ID, "pollen"), POLLEN);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(WilderWild.MOD_ID, "dandelion_seed"), DANDELION_SEED);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(WilderWild.MOD_ID, "controlled_dandelion_seed"), CONTROLLED_DANDELION_SEED);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(WilderWild.MOD_ID, "milkweed_seed"), MILKWEED_SEED);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(WilderWild.MOD_ID, "controlled_milkweed_seed"), CONTROLLED_MILKWEED_SEED);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(WilderWild.MOD_ID, "floating_sculk_bubble"), FLOATING_SCULK_BUBBLE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(WilderWild.MOD_ID, "termite"), TERMITE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(WilderWild.MOD_ID, "effect_point"), EFFECT_POINT);
    }
}
