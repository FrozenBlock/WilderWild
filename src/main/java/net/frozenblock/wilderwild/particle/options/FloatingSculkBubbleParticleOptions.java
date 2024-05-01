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
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class FloatingSculkBubbleParticleOptions implements ParticleOptions {
	public static final Codec<FloatingSculkBubbleParticleOptions> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(
				Codec.DOUBLE.fieldOf("size").forGetter((particleOptions) -> particleOptions.size),
				Codec.INT.fieldOf("maxAge").forGetter((particleOptions) -> particleOptions.maxAge),
				Vec3.CODEC.fieldOf("velocity").forGetter((particleOptions) -> particleOptions.velocity)
			)
			.apply(instance, FloatingSculkBubbleParticleOptions::new)
	);
	public static final ParticleOptions.Deserializer<FloatingSculkBubbleParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
		@NotNull
		@Override
		public FloatingSculkBubbleParticleOptions fromCommand(ParticleType<FloatingSculkBubbleParticleOptions> type, @NotNull StringReader reader) throws CommandSyntaxException {
			double d = reader.readDouble();
			reader.expect(' ');
			int i = reader.readInt();
			Vec3 vec3 = WindParticleOptions.readVec3(reader);
			return new FloatingSculkBubbleParticleOptions(d, i, vec3);
		}

		@NotNull
		@Override
		public FloatingSculkBubbleParticleOptions fromNetwork(ParticleType<FloatingSculkBubbleParticleOptions> particleType, FriendlyByteBuf buffer) {
			return new FloatingSculkBubbleParticleOptions(buffer.readDouble(), buffer.readVarInt(), new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
		}
	};
	private final double size;
	private final int maxAge;
	private final Vec3 velocity;

	public FloatingSculkBubbleParticleOptions(double size, int maxAge, float xSpeed, float ySpeed, float zSpeed) {
		this(size, maxAge, new Vec3(xSpeed, ySpeed, zSpeed));
	}

	public FloatingSculkBubbleParticleOptions(double size, int maxAge, Vec3 velocity) {
		this.size = size;
		this.maxAge = maxAge;
		this.velocity = velocity;
	}

	public static double getRandomVelocity(RandomSource random, int size) {
        return size >= 1 ? (random.nextDouble() - 0.5D) / 10.5D : (random.nextDouble() - 0.5D) / 9.5D;
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

		Vec3 velocity = this.getVelocity();
		buffer.writeDouble(velocity.x);
		buffer.writeDouble(velocity.y);
		buffer.writeDouble(velocity.z);
	}

	@NotNull
	@Override
	public String writeToString() {
		return String.format(Locale.ROOT, "%s %.2f %d", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.getSize(), this.getMaxAge());
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
