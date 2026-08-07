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

package net.frozenblock.wilderwild.particle;

import java.util.Optional;
import net.frozenblock.wilderwild.block.leaves.FallingLeafData;
import net.frozenblock.wilderwild.particle.options.WWFallingLeavesParticleOptions;
import net.frozenblock.wilderwild.tag.WWBlockItemTags;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FallingLeavesParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@ClientOnly
public class WWFallingLeavesParticle extends FallingLeavesParticle {
	private static final int DEFAULT_UNTINTED_COLOR = ARGB.color(255, 255, 255);
	public final boolean bounceOnFloor;
	public final boolean isLitter;

	public WWFallingLeavesParticle(
		ClientLevel level,
		double x, double y, double z,
		Optional<Holder<Block>> originBlock,
		float gravityScale,
		float windBig,
		FallingLeafData.LeafMovementType movementType,
		float quadSize,
		float downwardVelocity,
		TextureAtlasSprite sprite
	) {
		super(level, x, y, z, sprite, gravityScale, windBig, movementType.swirl(), movementType.flowAway(), quadSize, downwardVelocity);
		this.bounceOnFloor = movementType.bounceOnFloor();

		boolean isLitter = false;

		int color = DEFAULT_UNTINTED_COLOR;
		applyBlockProperties: {
			if (originBlock.isEmpty()) break applyBlockProperties;

			final BlockState originBlockState = originBlock.get().value().defaultBlockState();
			if (originBlockState.is(WWBlockItemTags.LEAF_LITTERS.block())) isLitter = true;

			final BlockTintSource tintSource = Minecraft.getInstance().getBlockColors().getTintSource(originBlockState, 0);
			if (tintSource == null) break applyBlockProperties;

			final BlockPos particlePos = BlockPos.containing(x, y, z);
			final int newColor = tintSource.colorAsTerrainParticle(originBlockState, level, particlePos);
			if (newColor == -1) break applyBlockProperties;
			color =	newColor;
		}

		this.isLitter = isLitter;
		this.rCol = ARGB.red(color) / 255F;
		this.bCol = ARGB.blue(color) / 255F;
		this.gCol = ARGB.green(color) / 255F;
	}

	public record Provider(SpriteSet spriteSet) implements ParticleProvider<WWFallingLeavesParticleOptions> {
		@Override
		public Particle createParticle(
			WWFallingLeavesParticleOptions options,
			ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			final WWFallingLeavesParticle leafParticle = new WWFallingLeavesParticle(
				level,
				x, y, z,
				options.getOriginBlock(),
				0.25F * options.getGravityScale(),
				options.getWindScale(),
				options.leafMovementType(),
				(options.getTextureSize() / 16F) * 0.5F,
				0F,
				this.spriteSet.get(random)
			);

			leafParticle.quadSize = (options.getTextureSize() / 16F) * 0.5F;
			if (options.isFastFalling()) leafParticle.gravity = 0.04F;

			//particle.rotSpeed *= options.getGravityScale() * 0.5F;
			if (options.controlVelUponSpawn()) {
				Vec3 velocity = options.getVelocity();
				leafParticle.xd = velocity.x;
				leafParticle.yd = velocity.y;
				leafParticle.zd = velocity.z;
			} else {
				leafParticle.xd = xd;
				leafParticle.yd = yd;
				leafParticle.zd = zd;
			}
			return leafParticle;
		}
	}
}
