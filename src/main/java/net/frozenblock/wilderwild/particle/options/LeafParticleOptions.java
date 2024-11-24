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

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.lib.networking.FrozenByteBufCodecs;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LeafParticleOptions implements ParticleOptions {
	public static final MapCodec<LeafParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		instance.group(
				ResourceLocation.CODEC.fieldOf("particleType").forGetter(LeafParticleOptions::getParticleId),
				Vec3.CODEC.fieldOf("velocity").forGetter(LeafParticleOptions::getVelocity),
				Codec.FLOAT.fieldOf("quadSize").forGetter(LeafParticleOptions::getQuadSize),
				Codec.FLOAT.fieldOf("gravity").forGetter(LeafParticleOptions::getGravityScale),
				Codec.BOOL.fieldOf("isFastFalling").forGetter(LeafParticleOptions::isFastFalling),
				Codec.FLOAT.fieldOf("windScale").forGetter(LeafParticleOptions::getWindScale),
				Codec.BOOL.fieldOf("flowAway").forGetter(LeafParticleOptions::flowAway),
				Codec.BOOL.fieldOf("swirl").forGetter(LeafParticleOptions::swirl)
			)
			.apply(instance, LeafParticleOptions::createCodecParticleOptions)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, LeafParticleOptions> STREAM_CODEC = StreamCodec.composite(
		ResourceLocation.STREAM_CODEC, LeafParticleOptions::getParticleId,
		FrozenByteBufCodecs.VEC3, LeafParticleOptions::getVelocity,
		ByteBufCodecs.FLOAT, LeafParticleOptions::getQuadSize,
		ByteBufCodecs.FLOAT, LeafParticleOptions::getGravityScale,
		ByteBufCodecs.BOOL, LeafParticleOptions::isFastFalling,
		ByteBufCodecs.FLOAT, LeafParticleOptions::getWindScale,
		ByteBufCodecs.BOOL, LeafParticleOptions::flowAway,
		ByteBufCodecs.BOOL, LeafParticleOptions::swirl,
		LeafParticleOptions::createCodecParticleOptions
	);

	private final ParticleType<LeafParticleOptions> type;
	private final ResourceLocation particleId;
	private final Vec3 velocity;
	private final float quadSize;
	private final float gravityScale;
	private final float windScale;
	private final boolean flowAway;
	private final boolean swirl;
	private final boolean isFastFalling;
	private final boolean controlVelUponSpawn;

	@NotNull
	public static LeafParticleOptions create(ParticleType<LeafParticleOptions> type, float quadSize, float gravityScale, float windScale, boolean flowAway, boolean swirl) {
		return new LeafParticleOptions(type, 0D, 0D, 0D, quadSize, gravityScale, false, windScale, flowAway, swirl);
	}

	@NotNull
	public static LeafParticleOptions createFastFalling(ParticleType<LeafParticleOptions> type, float quadSize) {
		return new LeafParticleOptions(type, 0D, -0.05D, 0D, quadSize, 25F, true, 0F, false, false);
	}

	private LeafParticleOptions(
		ParticleType<LeafParticleOptions> type,
		double xSpeed,
		double ySpeed,
		double zSpeed,
		float quadSize,
		float gravityScale,
		boolean isFastFalling,
		float windScale,
		boolean flowAway,
		boolean swirl
	) {
		this(type, new Vec3(xSpeed, ySpeed, zSpeed), quadSize, gravityScale, isFastFalling, isFastFalling, windScale, flowAway, swirl);
	}


	private @NotNull static LeafParticleOptions createCodecParticleOptions(
		ResourceLocation particleId,
		Vec3 velocity, float quadSize,
		float gravityScale,
		boolean isFastFalling,
		float windScale,
		boolean flowAway,
		boolean swirl
	) {
		ParticleType<LeafParticleOptions> particleType;
		if (BuiltInRegistries.PARTICLE_TYPE.containsKey(particleId)) {
			particleType = (ParticleType<LeafParticleOptions>) BuiltInRegistries.PARTICLE_TYPE.getValue(particleId);
		} else {
			particleType = WWParticleTypes.OAK_LEAVES;
		}
		return new LeafParticleOptions(particleType, velocity, quadSize, gravityScale, isFastFalling, isFastFalling, windScale, flowAway, swirl);
	}

	private LeafParticleOptions(
		ParticleType<LeafParticleOptions> type,
		Vec3 velocity,
		float quadSize,
		float gravityScale,
		boolean isFastFalling,
		boolean controlVelUponSpawn,
		float windScale,
		boolean flowAway,
		boolean swirl
	) {
		this.type = type;
		this.particleId = BuiltInRegistries.PARTICLE_TYPE.getKey(type);
		this.velocity = velocity;
		this.quadSize = quadSize;
		this.gravityScale = gravityScale;
		this.isFastFalling = isFastFalling;
		this.controlVelUponSpawn = controlVelUponSpawn;
		this.windScale = windScale;
		this.flowAway = flowAway;
		this.swirl = swirl;
	}

	@NotNull
	@Override
	public ParticleType<?> getType() {
		return this.type;
	}

	public ResourceLocation getParticleId() {
		return particleId;
	}

	public Vec3 getVelocity() {
		return this.velocity;
	}

	public float getQuadSize() {
		return this.quadSize;
	}

	public float getGravityScale() {
		return this.gravityScale;
	}

	public boolean isFastFalling() {
		return this.isFastFalling;
	}

	public boolean controlVelUponSpawn() {
		return this.controlVelUponSpawn;
	}

	public float getWindScale() {
		return this.windScale;
	}

	public boolean flowAway() {
		return this.flowAway;
	}

	public boolean swirl() {
		return this.swirl;
	}
}
