/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.particle.options.FloatingSculkBubbleParticleOptions;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.particle.options.WindParticleOptions;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.NotNull;

public final class RegisterParticles {
	public static final SimpleParticleType POLLEN = register("pollen");
	public static final ParticleType<SeedParticleOptions> SEED = register("seed", false, SeedParticleOptions.DESERIALIZER, particleType -> SeedParticleOptions.CODEC);
	public static final ParticleType<FloatingSculkBubbleParticleOptions> FLOATING_SCULK_BUBBLE = register("floating_sculk_bubble", false, FloatingSculkBubbleParticleOptions.DESERIALIZER, particleType -> FloatingSculkBubbleParticleOptions.CODEC);
	public static final ParticleType<WindParticleOptions> WIND = register("wind", false, WindParticleOptions.DESERIALIZER, particleType -> WindParticleOptions.CODEC);
	public static final SimpleParticleType TERMITE = register("termite");
	public static final SimpleParticleType COCONUT_SPLASH = register("coconut_splash");
	public static final SimpleParticleType BLUE_PEARLESCENT_HANGING_MESOGLEA = register("blue_pearlescent_hanging_mesoglea_drip");
	public static final SimpleParticleType BLUE_PEARLESCENT_FALLING_MESOGLEA = register("blue_pearlescent_falling_mesoglea_drip");
	public static final SimpleParticleType BLUE_PEARLESCENT_LANDING_MESOGLEA = register("blue_pearlescent_landing_mesoglea_drip");
	public static final SimpleParticleType PURPLE_PEARLESCENT_HANGING_MESOGLEA = register("purple_pearlescent_hanging_mesoglea_drip");
	public static final SimpleParticleType PURPLE_PEARLESCENT_FALLING_MESOGLEA = register("purple_pearlescent_falling_mesoglea_drip");
	public static final SimpleParticleType PURPLE_PEARLESCENT_LANDING_MESOGLEA = register("purple_pearlescent_landing_mesoglea_drip");
	public static final SimpleParticleType PINK_HANGING_MESOGLEA = register("pink_hanging_mesoglea_drip");
	public static final SimpleParticleType PINK_FALLING_MESOGLEA = register("pink_falling_mesoglea_drip");
	public static final SimpleParticleType PINK_LANDING_MESOGLEA = register("pink_landing_mesoglea_drip");
	public static final SimpleParticleType RED_HANGING_MESOGLEA = register("red_hanging_mesoglea_drip");
	public static final SimpleParticleType RED_FALLING_MESOGLEA = register("red_falling_mesoglea_drip");
	public static final SimpleParticleType RED_LANDING_MESOGLEA = register("red_landing_mesoglea_drip");
	public static final SimpleParticleType YELLOW_HANGING_MESOGLEA = register("yellow_hanging_mesoglea_drip");
	public static final SimpleParticleType YELLOW_FALLING_MESOGLEA = register("yellow_falling_mesoglea_drip");
	public static final SimpleParticleType YELLOW_LANDING_MESOGLEA = register("yellow_landing_mesoglea_drip");
	public static final SimpleParticleType LIME_HANGING_MESOGLEA = register("lime_hanging_mesoglea_drip");
	public static final SimpleParticleType LIME_FALLING_MESOGLEA = register("lime_falling_mesoglea_drip");
	public static final SimpleParticleType LIME_LANDING_MESOGLEA = register("lime_landing_mesoglea_drip");
	public static final SimpleParticleType BLUE_HANGING_MESOGLEA = register("blue_hanging_mesoglea_drip");
	public static final SimpleParticleType BLUE_FALLING_MESOGLEA = register("blue_falling_mesoglea_drip");
	public static final SimpleParticleType BLUE_LANDING_MESOGLEA = register("blue_landing_mesoglea_drip");

	private RegisterParticles() {
		throw new UnsupportedOperationException("RegisterParticles contains only static declarations.");
	}

	public static void registerParticles() {
		WilderSharedConstants.logWithModId("Registering Particles for", WilderSharedConstants.UNSTABLE_LOGGING);
	}

	@NotNull
	private static SimpleParticleType register(@NotNull String name, boolean alwaysShow) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id(name), FabricParticleTypes.simple(alwaysShow));
	}

	@NotNull
	private static SimpleParticleType register(@NotNull String name) {
		return register(name, false);
	}

	@NotNull
	private static <T extends ParticleOptions> ParticleType<T> register(@NotNull String name, boolean alwaysShow, @NotNull ParticleOptions.Deserializer<T> factory, Function<ParticleType<T>, Codec<T>> codecGetter) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, WilderSharedConstants.id(name), new ParticleType<T>(alwaysShow, factory) {
			@Override
			public Codec<T> codec() {
				return codecGetter.apply(this);
			}
		});
	}
}
