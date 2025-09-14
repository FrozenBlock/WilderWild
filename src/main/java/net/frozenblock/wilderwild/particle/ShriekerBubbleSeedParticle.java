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
import net.frozenblock.wilderwild.particle.options.FloatingSculkBubbleParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ShriekerBubbleSeedParticle extends NoRenderParticle {
	private final BlockPos pos;
	private final Vec3 centerPos;

	ShriekerBubbleSeedParticle(ClientLevel world, double d, double e, double f) {
		super(world, d, e, f, 0D, 0D, 0D);
		this.lifetime = 50;
		this.pos = BlockPos.containing(d, e, f);
		this.centerPos = Vec3.atCenterOf(this.pos);
	}

	@Override
	public void tick() {
		BlockState state = this.level.getBlockState(this.pos);
		if (state.is(Blocks.SCULK_SHRIEKER) && state.getFluidState().is(Fluids.WATER) && WWBlockConfig.get().sculk.shriekerGargling) {
			this.level.addParticle(
				new FloatingSculkBubbleParticleOptions(
					this.level.random.nextDouble() > 0.7 ? 1 : 0,
					20 + this.level.random.nextInt(80),
					new Vec3(
						FloatingSculkBubbleParticleOptions.getRandomVelocity(this.level.random, 0),
						0.075F,
						FloatingSculkBubbleParticleOptions.getRandomVelocity(this.level.random, 0)
					)
				),
				this.centerPos.x,
				this.centerPos.y,
				this.centerPos.z,
				0D,
				0D,
				0D
			);
		} else {
			this.remove();
			return;
		}

		this.age++;
		if (this.age == this.lifetime) this.remove();
	}

	@Environment(EnvType.CLIENT)
	public record Provider(@NotNull SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
		@Override
		@NotNull
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			return new ShriekerBubbleSeedParticle(level, x, y, z);
		}
	}
}
