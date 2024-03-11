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
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.frozenblock.lib.networking.FrozenByteBufCodecs;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WindParticleOptions implements ParticleOptions {
	public static final Codec<WindParticleOptions> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(
				Codec.BOOL.fieldOf("flipped").forGetter(WindParticleOptions::isFlipped),
				Codec.INT.fieldOf("lifespan").forGetter(WindParticleOptions::getLifespan),
				Vec3.CODEC.fieldOf("velocity").forGetter(WindParticleOptions::getVelocity)
			)
			.apply(instance, WindParticleOptions::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, WindParticleOptions> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.BOOL, WindParticleOptions::isFlipped,
		ByteBufCodecs.VAR_INT, WindParticleOptions::getLifespan,
		FrozenByteBufCodecs.VEC3, WindParticleOptions::getVelocity,
		WindParticleOptions::new
	);
	public static final Deserializer<WindParticleOptions> DESERIALIZER = new Deserializer<>() {
        @Override
        @NotNull
        public WindParticleOptions fromCommand(ParticleType<WindParticleOptions> type, @NotNull StringReader reader, HolderLookup.Provider provider) throws CommandSyntaxException {
			reader.expect(' ');
            boolean flipped = reader.readBoolean();
			reader.expect(' ');
            int lifespan = reader.readInt();
            Vec3 speed = readVec3(reader);
            return new WindParticleOptions(flipped, lifespan, speed);
        }
    };

	public static Vec3 readVec3(StringReader reader) throws CommandSyntaxException {
		reader.expect(' ');
		double f = reader.readDouble();
		reader.expect(' ');
		double g = reader.readDouble();
		reader.expect(' ');
		double h = reader.readDouble();
		return new Vec3(f, g, h);
	}

	private final boolean flipped;
	private final int lifespan;
	private final Vec3 velocity;

	private WindParticleOptions(boolean flipped, int lifespan, Vec3 velocity) {
		this.flipped = flipped;
		this.lifespan = lifespan;
		this.velocity = velocity;
	}

	@NotNull
	@Override
	public ParticleType<?> getType() {
		return RegisterParticles.WIND;
	}

	@NotNull
	@Override
	public String writeToString(HolderLookup.Provider provider) {
		return String.format(Locale.ROOT, "%s %b %b", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.flipped, this.getLifespan());
	}

	public boolean isFlipped() {
		return this.flipped;
	}

	public int getLifespan() {
		return this.lifespan;
	}

	public Vec3 getVelocity() {
		return this.velocity;
	}

}
