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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class SeedParticleOptions implements ParticleOptions {
	public static final MapCodec<SeedParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		instance.group(
				Codec.BOOL.fieldOf("isMilkweed").forGetter(SeedParticleOptions::isMilkweed),
				Codec.BOOL.fieldOf("isControlled").forGetter(SeedParticleOptions::isControlled),
				Vec3.CODEC.fieldOf("velocity").forGetter(SeedParticleOptions::getVelocity)
			)
			.apply(instance, SeedParticleOptions::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, SeedParticleOptions> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.BOOL, SeedParticleOptions::isMilkweed,
		ByteBufCodecs.BOOL, SeedParticleOptions::isControlled,
		FrozenByteBufCodecs.VEC3, SeedParticleOptions::getVelocity,
		SeedParticleOptions::new
	);

	private final boolean isMilkweed;
	private final boolean controlled;
	private final Vec3 velocity;

	@NotNull
	@Contract(value = "_, _, _, _ -> new", pure = true)
	public static SeedParticleOptions controlled(boolean isMilkweed, double xSpeed, double ySpeed, double zSpeed) {
		return new SeedParticleOptions(isMilkweed, true, xSpeed, ySpeed, zSpeed);
	}

	@NotNull
	@Contract(value = "_ -> new", pure = true)
	public static SeedParticleOptions unControlled(boolean isMilkweed) {
		return new SeedParticleOptions(isMilkweed, false, 0F, 0F, 0F);
	}

	private SeedParticleOptions(boolean isMilkweed, boolean controlled, double xSpeed, double ySpeed, double zSpeed) {
		this(isMilkweed, controlled, new Vec3(xSpeed, ySpeed, zSpeed));
	}

	private SeedParticleOptions(boolean isMilkweed, boolean controlled, Vec3 velocity) {
		this.isMilkweed = isMilkweed;
		this.controlled = controlled;
		this.velocity = velocity;
	}

	@NotNull
	@Override
	public ParticleType<?> getType() {
		return RegisterParticles.SEED;
	}

	public boolean isMilkweed() {
		return this.isMilkweed;
	}

	public boolean isControlled() {
		return this.controlled;
	}

	public Vec3 getVelocity() {
		return this.velocity;
	}

}
