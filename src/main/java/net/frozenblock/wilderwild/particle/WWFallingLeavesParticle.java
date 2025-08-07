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

package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.frozenblock.wilderwild.particle.options.WWFallingLeavesParticleOptions;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FallingLeavesParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class WWFallingLeavesParticle extends FallingLeavesParticle {
	private static final int DEFAULT_UNTINTED_COLOR = ARGB.color(255, 255, 255);
	public final boolean bounceOnFloor;

	public WWFallingLeavesParticle(
		ClientLevel world,
		double x, double y, double z,
		float gravityScale,
		float windBig,
		FallingLeafUtil.LeafMovementType leafMovementType,
		float quadSize,
		float downwardVelocity,
		SpriteSet spriteProvider,
		boolean isLitter,
		ParticleType<WWFallingLeavesParticleOptions> particleType
	) {
		super(world, x, y, z, spriteProvider, gravityScale, windBig, leafMovementType.swirl(), leafMovementType.flowAway(), quadSize, downwardVelocity);
		this.bounceOnFloor = leafMovementType.bounceOnFloor();

		FallingLeafUtil.LeafParticleData leafParticleData = isLitter ? FallingLeafUtil.getLitterOrLeafParticleData(particleType) : FallingLeafUtil.getLeafParticleData(particleType);
		int color = DEFAULT_UNTINTED_COLOR;
		if (leafParticleData != null) {
			Block leavesBlock = leafParticleData.leavesBlock();
			BlockColor blockColor = ColorProviderRegistry.BLOCK.get(leavesBlock);
			if (blockColor != null) {
				BlockPos particlePos = BlockPos.containing(x, y, z);
				color = world.getBlockTint(particlePos, isLitter ? BiomeColors.DRY_FOLIAGE_COLOR_RESOLVER : BiomeColors.FOLIAGE_COLOR_RESOLVER);
				try {
					color = blockColor.getColor(leavesBlock.defaultBlockState(), world, particlePos, 0);
				} catch (Exception ignored) {}
			}
		}
		this.rCol = ARGB.red(color) / 255F;
		this.bCol = ARGB.blue(color) / 255F;
		this.gCol = ARGB.green(color) / 255F;
	}

	public record Provider(@NotNull SpriteSet spriteProvider) implements ParticleProvider<WWFallingLeavesParticleOptions> {
		@Override
		@NotNull
		public Particle createParticle(
			@NotNull WWFallingLeavesParticleOptions options, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
		) {
			WWFallingLeavesParticle leafParticle = new WWFallingLeavesParticle(
				level,
				x, y, z,
				0.25F * options.getGravityScale(),
				options.getWindScale(),
				options.leafMovementType(),
				(options.getTextureSize() / 16F) * 0.5F,
				0F,
				this.spriteProvider,
				options.isLitter(),
				(ParticleType<WWFallingLeavesParticleOptions>) options.getType()
			);

			leafParticle.quadSize = (options.getTextureSize() / 16F) * 0.5F;
			if (options.isFastFalling()) leafParticle.gravity = 0.04F;

			//leafParticle.rotSpeed *= options.getGravityScale() * 0.5F;
			if (options.controlVelUponSpawn()) {
				Vec3 velocity = options.getVelocity();
				leafParticle.xd = velocity.x;
				leafParticle.yd = velocity.y;
				leafParticle.zd = velocity.z;
			} else {
				leafParticle.xd = xSpeed;
				leafParticle.yd = ySpeed;
				leafParticle.zd = zSpeed;
			}
			return leafParticle;
		}
	}
}
