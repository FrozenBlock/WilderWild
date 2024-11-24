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

	public WWFallingLeavesParticle(
		ClientLevel world,
		double x, double y, double z,
		float gravityScale,
		float windBig,
		boolean swirl,
		boolean flowAway,
		float quadSize,
		float downwardVelocity,
		SpriteSet spriteProvider,
		ParticleType<WWFallingLeavesParticleOptions> particleType
	) {
		super(world, x, y, z, spriteProvider, gravityScale, windBig, swirl, flowAway, quadSize, downwardVelocity);
		FallingLeafUtil.LeafParticleData leafParticleData = FallingLeafUtil.getLeafParticleData(particleType);
		int color = DEFAULT_UNTINTED_COLOR;
		if (leafParticleData != null) {
			Block leavesBlock = leafParticleData.leavesBlock();
			BlockColor blockColor = ColorProviderRegistry.BLOCK.get(leavesBlock);
			if (blockColor != null) {
				BlockPos particlePos = BlockPos.containing(x, y, z);
				color = world.getBlockTint(particlePos, BiomeColors.FOLIAGE_COLOR_RESOLVER);
				try {
					color = blockColor.getColor(leavesBlock.defaultBlockState(), world, particlePos, 0);
				} catch (Exception ignored) {}
			}
		}
		this.rCol = ARGB.red(color) / 255F;
		this.bCol = ARGB.blue(color) / 255F;
		this.gCol = ARGB.green(color) / 255F;
	}

	@Environment(EnvType.CLIENT)
	public record Factory(@NotNull SpriteSet spriteProvider) implements ParticleProvider<WWFallingLeavesParticleOptions> {
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
				options.swirl(),
				!options.swirl(),
				options.getQuadSize() * 16F,
				0F,
				this.spriteProvider,
				(ParticleType<WWFallingLeavesParticleOptions>) options.getType()
			);
			//leafParticle.quadSize = options.getQuadSize();
			if (options.isFastFalling()) {
				leafParticle.gravity = 0.04F;
			}

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
