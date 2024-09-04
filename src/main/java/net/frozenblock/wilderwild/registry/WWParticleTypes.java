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

import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.particle.options.FloatingSculkBubbleParticleOptions;
import net.frozenblock.wilderwild.particle.options.LeafClusterParticleOptions;
import net.frozenblock.wilderwild.particle.options.LeafParticleOptions;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.particle.options.WindParticleOptions;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class WWParticleTypes {
	public static final SimpleParticleType POLLEN = register("pollen");
	public static final ParticleType<SeedParticleOptions> SEED = register(
		"seed", false, particleType -> SeedParticleOptions.CODEC, particleType -> SeedParticleOptions.STREAM_CODEC)
		;
	public static final ParticleType<FloatingSculkBubbleParticleOptions> FLOATING_SCULK_BUBBLE = register(
		"floating_sculk_bubble", false, particleType -> FloatingSculkBubbleParticleOptions.CODEC, particleType -> FloatingSculkBubbleParticleOptions.STREAM_CODEC
	);
	public static final ParticleType<WindParticleOptions> WIND = register(
		"wind", false, particleType -> WindParticleOptions.CODEC, particleType -> WindParticleOptions.STREAM_CODEC
	);
	public static final SimpleParticleType TERMITE = register("termite");
	public static final SimpleParticleType COCONUT_SPLASH = register("coconut_splash");
	public static final SimpleParticleType SCORCHING_FLAME = register("scorching_flame");
	public static final ParticleType<LeafClusterParticleOptions> LEAF_CLUSTER_SPAWNER = register(
		"leaf_cluster", false, particleType -> LeafClusterParticleOptions.CODEC, particleType -> LeafClusterParticleOptions.STREAM_CODEC
	);
	public static final ParticleType<LeafParticleOptions> MAPLE_LEAVES = createLeafParticle(WWConstants.id("maple_leaves"));
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

	private WWParticleTypes() {
		throw new UnsupportedOperationException("RegisterParticles contains only static declarations.");
	}

	public static void registerParticles() {
		WWConstants.logWithModId("Registering Particles for", WWConstants.UNSTABLE_LOGGING);
	}

	public static @NotNull ParticleType<LeafParticleOptions> createLeafParticle(ResourceLocation location) {
		return register(
			location, false, particleType -> LeafParticleOptions.CODEC, particleType -> LeafParticleOptions.STREAM_CODEC
		);
	}

	@NotNull
	private static SimpleParticleType register(@NotNull String name, boolean alwaysShow) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, WWConstants.id(name), FabricParticleTypes.simple(alwaysShow));
	}

	@NotNull
	private static SimpleParticleType register(@NotNull String name) {
		return register(name, false);
	}

	@NotNull
	private static <T extends ParticleOptions> ParticleType<T> register(
		String string,
		boolean alwaysShow,
		Function<ParticleType<T>, MapCodec<T>> function,
		Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> function2
	) {
		return register(WWConstants.id(string), alwaysShow, function, function2);
	}

	@NotNull
	private static <T extends ParticleOptions> ParticleType<T> register(
		ResourceLocation resourceLocation,
		boolean alwaysShow,
		Function<ParticleType<T>, MapCodec<T>> function,
		Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> function2
	) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, resourceLocation, new ParticleType<T>(alwaysShow) {
			@Override
			public @NotNull MapCodec<T> codec() {
				return function.apply(this);
			}

			@NotNull
			@Override
			public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
				return function2.apply(this);
			}
		});
	}
}
