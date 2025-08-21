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
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class FloatingSculkBubbleParticleOptions implements ParticleOptions {
	public static final MapCodec<FloatingSculkBubbleParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(
			Codec.DOUBLE.fieldOf("size").forGetter((particleOptions) -> particleOptions.size),
			Codec.INT.fieldOf("maxAge").forGetter((particleOptions) -> particleOptions.maxAge),
			Vec3.CODEC.fieldOf("velocity").forGetter((particleOptions) -> particleOptions.velocity)
		).apply(instance, FloatingSculkBubbleParticleOptions::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, FloatingSculkBubbleParticleOptions> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.DOUBLE, FloatingSculkBubbleParticleOptions::getSize,
		ByteBufCodecs.VAR_INT, FloatingSculkBubbleParticleOptions::getMaxAge,
		Vec3.STREAM_CODEC, FloatingSculkBubbleParticleOptions::getVelocity,
		FloatingSculkBubbleParticleOptions::new
	);

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
		return WWParticleTypes.FLOATING_SCULK_BUBBLE;
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
