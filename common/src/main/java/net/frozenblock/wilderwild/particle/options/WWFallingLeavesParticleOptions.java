/*
 * Copyright 2025-2026 FrozenBlock
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
import java.util.Optional;
import net.frozenblock.wilderwild.block.leaves.FallingLeafData;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public final class WWFallingLeavesParticleOptions implements ParticleOptions {
	public static final MapCodec<WWFallingLeavesParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		RegistryFixedCodec.create(Registries.PARTICLE_TYPE).fieldOf("particle_type").forGetter(WWFallingLeavesParticleOptions::getTypeAsHolder),
		RegistryFixedCodec.create(Registries.BLOCK).optionalFieldOf("origin_block").forGetter(WWFallingLeavesParticleOptions::getOriginBlock),
		Vec3.CODEC.fieldOf("velocity").forGetter(WWFallingLeavesParticleOptions::getVelocity),
		Codec.INT.fieldOf("texture_size").forGetter(WWFallingLeavesParticleOptions::getTextureSize),
		Codec.FLOAT.fieldOf("gravity").forGetter(WWFallingLeavesParticleOptions::getGravityScale),
		Codec.BOOL.fieldOf("is_fast_falling").forGetter(WWFallingLeavesParticleOptions::isFastFalling),
		Codec.BOOL.fieldOf("control_velocity_upon_spawn").forGetter(WWFallingLeavesParticleOptions::controlVelUponSpawn),
		Codec.FLOAT.fieldOf("wind_scale").forGetter(WWFallingLeavesParticleOptions::getWindScale),
		FallingLeafData.LeafMovementType.CODEC.fieldOf("leaf_movement_type").forGetter(WWFallingLeavesParticleOptions::leafMovementType)
	).apply(instance, WWFallingLeavesParticleOptions::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, WWFallingLeavesParticleOptions> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.holderRegistry(Registries.PARTICLE_TYPE), WWFallingLeavesParticleOptions::getTypeAsHolder,
		ByteBufCodecs.optional(ByteBufCodecs.holderRegistry(Registries.BLOCK)), WWFallingLeavesParticleOptions::getOriginBlock,
		Vec3.STREAM_CODEC, WWFallingLeavesParticleOptions::getVelocity,
		ByteBufCodecs.INT, WWFallingLeavesParticleOptions::getTextureSize,
		ByteBufCodecs.FLOAT, WWFallingLeavesParticleOptions::getGravityScale,
		ByteBufCodecs.BOOL, WWFallingLeavesParticleOptions::isFastFalling,
		ByteBufCodecs.BOOL, WWFallingLeavesParticleOptions::controlVelUponSpawn,
		ByteBufCodecs.FLOAT, WWFallingLeavesParticleOptions::getWindScale,
		FallingLeafData.LeafMovementType.STREAM_CODEC, WWFallingLeavesParticleOptions::leafMovementType,
		WWFallingLeavesParticleOptions::new
	);

	private final Holder<ParticleType<?>> type;
	private final Optional<Holder<Block>> originBlock;
	private final Vec3 velocity;
	private final int textureSize;
	private final float gravityScale;
	private final float windScale;
	private final FallingLeafData.LeafMovementType leafMovementType;
	private final boolean isFastFalling;
	private final boolean controlVelUponSpawn;

	public static WWFallingLeavesParticleOptions create(
		Holder<ParticleType<?>> type,
		Optional<Holder<Block>> tintOwner,
		int textureSize,
		float gravityScale,
		float windScale,
		FallingLeafData.LeafMovementType leafMovementType
	) {
		return new WWFallingLeavesParticleOptions(
			type, tintOwner, 0D, 0D, 0D, textureSize, gravityScale, false, windScale, leafMovementType
		);
	}

	public static WWFallingLeavesParticleOptions createControlledVelocity(
		Holder<ParticleType<?>> type,
		Optional<Holder<Block>> tintOwner,
		Vec3 velocity,
		int textureSize,
		float gravityScale,
		float windScale,
		FallingLeafData.LeafMovementType leafMovementType
	) {
		return new WWFallingLeavesParticleOptions(
			type, tintOwner, velocity.x(), velocity.y(), velocity.z(), textureSize, gravityScale, false, true, windScale, leafMovementType
		);
	}

	public static WWFallingLeavesParticleOptions createFastFalling(
		Holder<ParticleType<?>> type,
		Optional<Holder<Block>> tintOwner,
		int textureSize
	) {
		return new WWFallingLeavesParticleOptions(
			type, tintOwner, 0D, -0.05D, 0D, textureSize, 25F, true, 0F, FallingLeafData.LeafMovementType.NONE
		);
	}

	private WWFallingLeavesParticleOptions(
		Holder<ParticleType<?>> type,
		Optional<Holder<Block>> originBlock,
		double xSpeed, double ySpeed, double zSpeed,
		int textureSize,
		float gravityScale,
		boolean isFastFalling,
		float windScale,
		FallingLeafData.LeafMovementType leafMovementType
	) {
		this(type, originBlock, new Vec3(xSpeed, ySpeed, zSpeed), textureSize, gravityScale, isFastFalling, isFastFalling, windScale, leafMovementType);
	}

	private WWFallingLeavesParticleOptions(
		Holder<ParticleType<?>> type,
		Optional<Holder<Block>> originBlock,
		double xSpeed, double ySpeed, double zSpeed,
		int textureSize,
		float gravityScale,
		boolean isFastFalling,
		boolean controlVelUponSpawn,
		float windScale,
		FallingLeafData.LeafMovementType leafMovementType
	) {
		this(type, originBlock, new Vec3(xSpeed, ySpeed, zSpeed), textureSize, gravityScale, isFastFalling, controlVelUponSpawn, windScale, leafMovementType);
	}

	private WWFallingLeavesParticleOptions(
		Holder<ParticleType<?>> type,
		Optional<Holder<Block>> originBlock,
		Vec3 velocity,
		int textureSize,
		float gravityScale,
		boolean isFastFalling,
		boolean controlVelUponSpawn,
		float windScale,
		FallingLeafData.LeafMovementType leafMovementType
	) {
		this.type = type;
		this.originBlock = originBlock;
		this.velocity = velocity;
		this.textureSize = textureSize;
		this.gravityScale = gravityScale;
		this.isFastFalling = isFastFalling;
		this.controlVelUponSpawn = controlVelUponSpawn;
		this.windScale = windScale;
		this.leafMovementType = leafMovementType;
	}

	@Override
	public ParticleType<?> getType() {
		return this.type.value();
	}

	public Holder<ParticleType<?>> getTypeAsHolder() {
		return this.type;
	}

	public Optional<Holder<Block>> getOriginBlock() {
		return this.originBlock;
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

	public FallingLeafData.LeafMovementType leafMovementType() {
		return this.leafMovementType;
	}
}
