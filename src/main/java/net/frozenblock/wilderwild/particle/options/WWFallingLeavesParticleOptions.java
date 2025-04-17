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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WWFallingLeavesParticleOptions implements ParticleOptions {
	public static final MapCodec<WWFallingLeavesParticleOptions> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		instance.group(
				ResourceLocation.CODEC.fieldOf("particleType").forGetter(WWFallingLeavesParticleOptions::getParticleId),
				Vec3.CODEC.fieldOf("velocity").forGetter(WWFallingLeavesParticleOptions::getVelocity),
				Codec.INT.fieldOf("textureSize").forGetter(WWFallingLeavesParticleOptions::getTextureSize),
				Codec.FLOAT.fieldOf("gravity").forGetter(WWFallingLeavesParticleOptions::getGravityScale),
				Codec.BOOL.fieldOf("isFastFalling").forGetter(WWFallingLeavesParticleOptions::isFastFalling),
				Codec.FLOAT.fieldOf("windScale").forGetter(WWFallingLeavesParticleOptions::getWindScale),
				Codec.BOOL.fieldOf("swirl").forGetter(WWFallingLeavesParticleOptions::swirl)
			)
			.apply(instance, WWFallingLeavesParticleOptions::createCodecParticleOptions)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, WWFallingLeavesParticleOptions> STREAM_CODEC = StreamCodec.composite(
		ResourceLocation.STREAM_CODEC, WWFallingLeavesParticleOptions::getParticleId,
		FrozenByteBufCodecs.VEC3, WWFallingLeavesParticleOptions::getVelocity,
		ByteBufCodecs.INT, WWFallingLeavesParticleOptions::getTextureSize,
		ByteBufCodecs.FLOAT, WWFallingLeavesParticleOptions::getGravityScale,
		ByteBufCodecs.BOOL, WWFallingLeavesParticleOptions::isFastFalling,
		ByteBufCodecs.FLOAT, WWFallingLeavesParticleOptions::getWindScale,
		ByteBufCodecs.BOOL, WWFallingLeavesParticleOptions::swirl,
		WWFallingLeavesParticleOptions::createCodecParticleOptions
	);

	private final ParticleType<WWFallingLeavesParticleOptions> type;
	private final ResourceLocation particleId;
	private final Vec3 velocity;
	private final int textureSize;
	private final float gravityScale;
	private final float windScale;
	private final boolean swirl;
	private final boolean isFastFalling;
	private final boolean controlVelUponSpawn;

	@NotNull
	public static WWFallingLeavesParticleOptions create(ParticleType<WWFallingLeavesParticleOptions> type, int textureSize, float gravityScale, float windScale, boolean swirl) {
		return new WWFallingLeavesParticleOptions(type, 0D, 0D, 0D, textureSize, gravityScale, false, windScale, swirl);
	}

	@NotNull
	public static WWFallingLeavesParticleOptions createFastFalling(ParticleType<WWFallingLeavesParticleOptions> type, int textureSize) {
		return new WWFallingLeavesParticleOptions(type, 0D, -0.05D, 0D, textureSize, 25F, true, 0F, false);
	}

	private WWFallingLeavesParticleOptions(
		ParticleType<WWFallingLeavesParticleOptions> type,
		double xSpeed,
		double ySpeed,
		double zSpeed,
		int textureSize,
		float gravityScale,
		boolean isFastFalling,
		float windScale,
		boolean swirl
	) {
		this(type, new Vec3(xSpeed, ySpeed, zSpeed), textureSize, gravityScale, isFastFalling, isFastFalling, windScale, swirl);
	}


	private @NotNull static WWFallingLeavesParticleOptions createCodecParticleOptions(
		ResourceLocation particleId,
		Vec3 velocity,
		int textureSize,
		float gravityScale,
		boolean isFastFalling,
		float windScale,
		boolean swirl
	) {
		ParticleType<WWFallingLeavesParticleOptions> particleType;
		if (BuiltInRegistries.PARTICLE_TYPE.containsKey(particleId)) {
			particleType = (ParticleType<WWFallingLeavesParticleOptions>) BuiltInRegistries.PARTICLE_TYPE.getValue(particleId);
		} else {
			particleType = WWParticleTypes.OAK_LEAVES;
		}
		return new WWFallingLeavesParticleOptions(particleType, velocity, textureSize, gravityScale, isFastFalling, isFastFalling, windScale, swirl);
	}

	private WWFallingLeavesParticleOptions(
		ParticleType<WWFallingLeavesParticleOptions> type,
		Vec3 velocity,
		int textureSize,
		float gravityScale,
		boolean isFastFalling,
		boolean controlVelUponSpawn,
		float windScale,
		boolean swirl
	) {
		this.type = type;
		this.particleId = BuiltInRegistries.PARTICLE_TYPE.getKey(type);
		this.velocity = velocity;
		this.textureSize = textureSize;
		this.gravityScale = gravityScale;
		this.isFastFalling = isFastFalling;
		this.controlVelUponSpawn = controlVelUponSpawn;
		this.windScale = windScale;
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

	public int getTextureSize() {
		return this.textureSize;
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

	public boolean swirl() {
		return this.swirl;
	}
}
