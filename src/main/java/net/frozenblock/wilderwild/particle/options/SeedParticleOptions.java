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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;

public record SeedParticleOptions(boolean isMilkweed, boolean controlled, Vec3 velocity) implements ParticleOptions {
	public static final MapCodec<SeedParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(
			Codec.BOOL.fieldOf("isMilkweed").forGetter(SeedParticleOptions::isMilkweed),
			Codec.BOOL.fieldOf("isControlled").forGetter(SeedParticleOptions::controlled),
			Vec3.CODEC.fieldOf("velocity").forGetter(SeedParticleOptions::velocity)
		).apply(instance, SeedParticleOptions::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, SeedParticleOptions> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.BOOL, SeedParticleOptions::isMilkweed,
		ByteBufCodecs.BOOL, SeedParticleOptions::controlled,
		Vec3.STREAM_CODEC, SeedParticleOptions::velocity,
		SeedParticleOptions::new
	);

	@Contract(value = "_, _, _, _ -> new", pure = true)
	public static SeedParticleOptions controlled(boolean isMilkweed, double xSpeed, double ySpeed, double zSpeed) {
		return new SeedParticleOptions(isMilkweed, true, xSpeed, ySpeed, zSpeed);
	}

	@Contract(value = "_ -> new", pure = true)
	public static SeedParticleOptions unControlled(boolean isMilkweed) {
		return new SeedParticleOptions(isMilkweed, false, 0F, 0F, 0F);
	}

	private SeedParticleOptions(boolean isMilkweed, boolean controlled, double xSpeed, double ySpeed, double zSpeed) {
		this(isMilkweed, controlled, new Vec3(xSpeed, ySpeed, zSpeed));
	}

	@Override
	public ParticleType<?> getType() {
		return WWParticleTypes.SEED;
	}
}
