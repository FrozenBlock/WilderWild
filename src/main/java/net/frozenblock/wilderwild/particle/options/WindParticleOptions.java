/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.particle.options;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.lib.networking.FrozenByteBufCodecs;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WindParticleOptions implements ParticleOptions {
	public static final MapCodec<WindParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		instance.group(
				Codec.INT.fieldOf("lifespan").forGetter(WindParticleOptions::getLifespan),
				Vec3.CODEC.fieldOf("velocity").forGetter(WindParticleOptions::getVelocity)
			)
			.apply(instance, WindParticleOptions::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, WindParticleOptions> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.VAR_INT, WindParticleOptions::getLifespan,
		FrozenByteBufCodecs.VEC3, WindParticleOptions::getVelocity,
		WindParticleOptions::new
	);

	private final int lifespan;
	private final Vec3 velocity;

	public WindParticleOptions(int lifespan, Vec3 velocity) {
		this.lifespan = lifespan;
		this.velocity = velocity;
	}
	public WindParticleOptions(int lifespan, double xVel, double yVel, double zVel) {
		this.lifespan = lifespan;
		this.velocity = new Vec3(xVel, yVel, zVel);
	}

	@NotNull
	@Override
	public ParticleType<?> getType() {
		return WWParticleTypes.WIND;
	}


	public int getLifespan() {
		return this.lifespan;
	}

	public Vec3 getVelocity() {
		return this.velocity;
	}

}
