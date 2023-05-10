/*
 * Copyright 2022-2023 FrozenBlock
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
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class FloatingSculkBubbleParticleOptions implements ParticleOptions {
	private final double size;
	private final int maxAge;
	private final Vec3 velocity;

	public static final Codec<FloatingSculkBubbleParticleOptions> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(
					Codec.DOUBLE.fieldOf("size").forGetter((particleOptions) -> particleOptions.size),
							Codec.INT.fieldOf("maxAge").forGetter((particleOptions) -> particleOptions.maxAge),
							Vec3.CODEC.fieldOf("velocity").forGetter((particleOptions) -> particleOptions.velocity)
			)
			.apply(instance, FloatingSculkBubbleParticleOptions::new)
	);

	public static final ParticleOptions.Deserializer<FloatingSculkBubbleParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {

		@Override
		public FloatingSculkBubbleParticleOptions fromCommand(@NotNull ParticleType<FloatingSculkBubbleParticleOptions> particleType, @NotNull StringReader stringReader) throws CommandSyntaxException {
			double d = stringReader.readDouble();
			int i = stringReader.readInt();
			Vec3 vec3 = FloatingSculkBubbleParticleOptions.readVec3(stringReader);
			stringReader.expect(' ');
			return new FloatingSculkBubbleParticleOptions(d, i, vec3);
		}

		@Override
		public FloatingSculkBubbleParticleOptions fromNetwork(@NotNull ParticleType<FloatingSculkBubbleParticleOptions> particleType, @NotNull FriendlyByteBuf friendlyByteBuf) {
			return new FloatingSculkBubbleParticleOptions(friendlyByteBuf.readDouble(), friendlyByteBuf.readInt(), FloatingSculkBubbleParticleOptions.readVelocity(friendlyByteBuf));
		}
	};

	public FloatingSculkBubbleParticleOptions(double size, int maxAge, Vec3 velocity) {
		this.size = size;
		this.maxAge = maxAge;
		this.velocity = velocity;
	}

	public static Vec3 readVec3(StringReader stringInput) throws CommandSyntaxException {
		stringInput.expect(' ');
		double f = stringInput.readDouble();
		stringInput.expect(' ');
		double g = stringInput.readDouble();
		stringInput.expect(' ');
		double h = stringInput.readDouble();
		return new Vec3(f, g, h);
	}

	public static Vec3 readVelocity(FriendlyByteBuf buffer) {
		return new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}

	@Override
	public ParticleType<?> getType() {
		return RegisterParticles.FLOATING_SCULK_BUBBLE;
	}

	public void writeToNetwork(FriendlyByteBuf buffer) {
		buffer.writeDouble(this.size);
		buffer.writeInt(this.maxAge);
		buffer.writeDouble(this.velocity.x());
		buffer.writeDouble(this.velocity.y());
		buffer.writeDouble(this.velocity.z());
	}

	public String writeToString() {
		return String.format(Locale.ROOT, "%s %.2f %d %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()), this.getSize(), this.getMaxAge(), this.velocity.x(), this.velocity.y(), this.velocity.z());
	}

	public double getSize() {
		return this.size;
	}

	public int getMaxAge() {
		return this.maxAge;
	}

	public Vec3 getVelocity() {
		return this.velocity;
	}

}
