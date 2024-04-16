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

package net.frozenblock.wilderwild.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class WindParticleOptions implements ParticleOptions {
	public static final Codec<WindParticleOptions> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(
				Codec.INT.fieldOf("lifespan").forGetter(WindParticleOptions::getLifespan),
				Vec3.CODEC.fieldOf("velocity").forGetter(WindParticleOptions::getVelocity)
			)
			.apply(instance, WindParticleOptions::new)
	);
	public static final Deserializer<WindParticleOptions> DESERIALIZER = new Deserializer<>() {
        @Override
        @NotNull
        public WindParticleOptions fromCommand(ParticleType<WindParticleOptions> type, @NotNull StringReader reader) throws CommandSyntaxException {
			reader.expect(' ');
            int lifespan = reader.readInt();
            Vec3 speed = readVec3(reader);
            return new WindParticleOptions(lifespan, speed);
        }

		@Override
		@NotNull
		public WindParticleOptions fromNetwork(ParticleType<WindParticleOptions> particleType, FriendlyByteBuf buffer) {
			return new WindParticleOptions(buffer.readVarInt(), buffer.readVec3());
		}
	};

	@Override
	public void writeToNetwork(FriendlyByteBuf buffer) {
		buffer.writeVarInt(this.getLifespan());
		buffer.writeVec3(this.getVelocity());
	}

	@NotNull
	@Contract("_ -> new")
	public static Vec3 readVec3(@NotNull StringReader reader) throws CommandSyntaxException {
		reader.expect(' ');
		double f = reader.readDouble();
		reader.expect(' ');
		double g = reader.readDouble();
		reader.expect(' ');
		double h = reader.readDouble();
		return new Vec3(f, g, h);
	}

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
		return RegisterParticles.WIND;
	}

	@NotNull
	@Override
	public String writeToString() {
		return String.format(Locale.ROOT, "%s %b %b", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.getLifespan());
	}

	public int getLifespan() {
		return this.lifespan;
	}

	public Vec3 getVelocity() {
		return this.velocity;
	}

}
