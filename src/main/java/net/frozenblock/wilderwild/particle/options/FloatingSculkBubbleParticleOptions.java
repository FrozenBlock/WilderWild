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
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class FloatingSculkBubbleParticleOptions implements ParticleOptions {
	public static final Codec<FloatingSculkBubbleParticleOptions> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(
				Codec.DOUBLE.fieldOf("size").forGetter((particleOptions) -> particleOptions.size),
				Codec.INT.fieldOf("maxAge").forGetter((particleOptions) -> particleOptions.maxAge),
				Codec.FLOAT.fieldOf("xSpeed").forGetter((particleOptions) -> particleOptions.velocity.x),
				Codec.FLOAT.fieldOf("ySpeed").forGetter((particleOptions) -> particleOptions.velocity.y),
				Codec.FLOAT.fieldOf("zSpeed").forGetter((particleOptions) -> particleOptions.velocity.z)
			)
			.apply(instance, FloatingSculkBubbleParticleOptions::new)
	);
	public static final ParticleOptions.Deserializer<FloatingSculkBubbleParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
		@NotNull
		@Override
		public FloatingSculkBubbleParticleOptions fromCommand(ParticleType<FloatingSculkBubbleParticleOptions> type, @NotNull StringReader reader) throws CommandSyntaxException {
			double d = reader.readDouble();
			int i = reader.readInt();
			Vector3f vector3f = DustParticleOptionsBase.readVector3f(reader);
			reader.expect(' ');
			return new FloatingSculkBubbleParticleOptions(d, i, vector3f);
		}

		@NotNull
		@Override
		public FloatingSculkBubbleParticleOptions fromNetwork(ParticleType<FloatingSculkBubbleParticleOptions> particleType, FriendlyByteBuf buffer) {
			return new FloatingSculkBubbleParticleOptions(buffer.readDouble(), buffer.readVarInt(), buffer.readVector3f());
		}
	};
	private final double size;
	private final int maxAge;
	private final Vector3f velocity;

	public FloatingSculkBubbleParticleOptions(double size, int maxAge, float xSpeed, float ySpeed, float zSpeed) {
		this(size, maxAge, new Vector3f(xSpeed, ySpeed, zSpeed));
	}

	public FloatingSculkBubbleParticleOptions(double size, int maxAge, Vector3f velocity) {
		this.size = size;
		this.maxAge = maxAge;
		this.velocity = velocity;
	}

	public static float getRandomVelocity(RandomSource random, int size) {
        return size >= 1 ? (random.nextFloat() - 0.5F) / 10.5F : (random.nextFloat() - 0.5F) / 9.5F;
	}

	@Override
	@NotNull
	public ParticleType<?> getType() {
		return RegisterParticles.FLOATING_SCULK_BUBBLE;
	}

	@Override
	public void writeToNetwork(FriendlyByteBuf buffer) {
		buffer.writeDouble(this.getSize());
		buffer.writeVarInt(this.getMaxAge());
		buffer.writeVector3f(this.getVelocity());
	}

	@NotNull
	@Override
	public String writeToString() {
		return String.format(Locale.ROOT, "%s %.2f %d %.2f %.2f %.2f", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.getSize(), this.getMaxAge(), this.velocity.x(), this.velocity.y(), this.velocity.z());
	}

	public double getSize() {
		return this.size;
	}

	public int getMaxAge() {
		return this.maxAge;
	}

	public Vector3f getVelocity() {
		return this.velocity;
	}

}
