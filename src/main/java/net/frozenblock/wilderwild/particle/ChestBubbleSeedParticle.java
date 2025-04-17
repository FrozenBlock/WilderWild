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
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ChestBubbleSeedParticle extends NoRenderParticle {
	private final BlockPos pos;

	ChestBubbleSeedParticle(ClientLevel world, double d, double e, double f) {
		super(world, d, e, f, 0D, 0D, 0D);
		this.lifetime = 5;
		this.pos = BlockPos.containing(d, e, f);
	}

	@Override
	public void tick() {
		BlockState state = this.level.getBlockState(this.pos);
		if (this.level.getBlockEntity(pos) instanceof ChestBlockEntity && state.getBlock() instanceof ChestBlock) {
			if (state.getFluidState().is(Fluids.WATER) && WWBlockConfig.get().chestBubbling) {
				double additionalX = 0.5D;
				double additionalZ = 0.5D;
				if (state.hasProperty(BlockStateProperties.CHEST_TYPE) && state.getValue(BlockStateProperties.CHEST_TYPE) != ChestType.SINGLE) {
					Direction direction = ChestBlock.getConnectedDirection(state);
					additionalX += (double) direction.getStepX() * 0.125D;
					additionalZ += (double) direction.getStepZ() * 0.125D;
				}

				double particleY = this.pos.getY() + 0.625D;
				for (int i = 0; i < this.random.nextInt(4, 10); i++) {
					double particleX = (this.pos.getX() + additionalX) + this.random.nextGaussian() * 0.21875D;
					double particleZ = (this.pos.getZ() + additionalZ)  + this.random.nextGaussian() * 0.21875D;

					this.level.addParticle(
						ParticleTypes.BUBBLE,
						particleX,
						particleY,
						particleZ,
						this.random.nextGaussian() * 0.2D,
						this.random.nextDouble() * 0.2D,
						this.random.nextGaussian() * 0.2D
					);
				}
			}
		} else {
			this.remove();
			return;
		}

		this.age++;
		if (this.age == this.lifetime) {
			this.remove();
		}
	}

	@Environment(EnvType.CLIENT)
	public record Factory(@NotNull SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
		@Override
		@NotNull
		public Particle createParticle(@NotNull SimpleParticleType options, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new ChestBubbleSeedParticle(level, x, y, z);
		}
	}
}
