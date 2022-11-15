package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public final class RegisterParticles {
	private RegisterParticles() {
		throw new UnsupportedOperationException("RegisterParticles contains only static declarations.");
	}

    public static final SimpleParticleType POLLEN = FabricParticleTypes.simple();
    public static final SimpleParticleType DANDELION_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType CONTROLLED_DANDELION_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType MILKWEED_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType CONTROLLED_MILKWEED_SEED = FabricParticleTypes.simple();
    public static final SimpleParticleType FLOATING_SCULK_BUBBLE = FabricParticleTypes.simple();
    public static final SimpleParticleType TERMITE = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_PEARLESCENT_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_PEARLESCENT_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_PEARLESCENT_LANDING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType PURPLE_PEARLESCENT_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType PURPLE_PEARLESCENT_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType PURPLE_PEARLESCENT_LANDING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType PINK_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType PINK_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType PINK_LANDING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType RED_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType RED_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType RED_LANDING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType YELLOW_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType YELLOW_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType YELLOW_LANDING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType LIME_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType LIME_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType LIME_LANDING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_HANGING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_FALLING_MESOGLEA = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_LANDING_MESOGLEA = FabricParticleTypes.simple();

    public static void registerParticles() {
        WilderWild.logWild("Registering Particles for", WilderSharedConstants.UNSTABLE_LOGGING);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("pollen"), POLLEN);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("dandelion_seed"), DANDELION_SEED);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("controlled_dandelion_seed"), CONTROLLED_DANDELION_SEED);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("milkweed_seed"), MILKWEED_SEED);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("controlled_milkweed_seed"), CONTROLLED_MILKWEED_SEED);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("floating_sculk_bubble"), FLOATING_SCULK_BUBBLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("termite"), TERMITE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("blue_pearlescent_hanging_mesoglea_drip"), BLUE_PEARLESCENT_HANGING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("blue_pearlescent_falling_mesoglea_drip"), BLUE_PEARLESCENT_FALLING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("blue_pearlescent_landing_mesoglea_drip"), BLUE_PEARLESCENT_LANDING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("purple_pearlescent_hanging_mesoglea_drip"), PURPLE_PEARLESCENT_HANGING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("purple_pearlescent_falling_mesoglea_drip"), PURPLE_PEARLESCENT_FALLING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("purple_pearlescent_landing_mesoglea_drip"), PURPLE_PEARLESCENT_LANDING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("pink_hanging_mesoglea_drip"), PINK_HANGING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("pink_falling_mesoglea_drip"), PINK_FALLING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("pink_landing_mesoglea_drip"), PINK_LANDING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("red_hanging_mesoglea_drip"), RED_HANGING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("red_falling_mesoglea_drip"), RED_FALLING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("red_landing_mesoglea_drip"), RED_LANDING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("yellow_hanging_mesoglea_drip"), YELLOW_HANGING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("yellow_falling_mesoglea_drip"), YELLOW_FALLING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("yellow_landing_mesoglea_drip"), YELLOW_LANDING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("lime_hanging_mesoglea_drip"), LIME_HANGING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("lime_falling_mesoglea_drip"), LIME_FALLING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("lime_landing_mesoglea_drip"), LIME_LANDING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("blue_hanging_mesoglea_drip"), BLUE_HANGING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("blue_falling_mesoglea_drip"), BLUE_FALLING_MESOGLEA);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id("blue_landing_mesoglea_drip"), BLUE_LANDING_MESOGLEA);
    }

    private static <T extends ParticleOptions> ParticleType<T> register(String name, boolean alwaysShow, ParticleOptions.Deserializer<T> factory, Function<ParticleType<T>, Codec<T>> codecGetter) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id(name), new ParticleType<T>(alwaysShow, factory) {
            @Override
            public Codec<T> codec() {
                return codecGetter.apply(this);
            }
        });
    }
}
