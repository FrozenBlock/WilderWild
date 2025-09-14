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

import java.util.Optional;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class PollenParticle extends SingleQuadParticle {
	public static final double WIND_INTENSITY = 0.2D;
	private float prevScale = 0F;
	private float scale = 0F;
	private float targetScale = 0F;
	private Optional<Supplier<Boolean>> canExist = Optional.empty();

	PollenParticle(
		@NotNull ClientLevel level,
		double x,
		double y,
		double z,
		double xd,
		double yd,
		double zd,
		TextureAtlasSprite sprite
	) {
		super(level, x, y - 0.125D, z, xd, yd, zd, sprite);
		this.setSize(0.01F, 0.02F);
		this.quadSize *= (this.random.nextFloat() * 0.6F + 0.6F) * 0.5F;
		this.lifetime = (int) (16D / (AdvancedMath.random().nextDouble() * 0.8D + 0.2D));
		this.hasPhysics = true;
		this.friction = 1F;
		this.gravity = 0F;
	}

	@Override
	public void tick() {
		if (this.canExist.isEmpty() || this.canExist.get().get()) {
			BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
			boolean rain = this.level.isRainingAt(blockPos);
			if (rain) this.gravity = 0.06F;
			this.xo = this.x;
			this.yo = this.y;
			this.zo = this.z;
			this.yd -= 0.04 * (double) this.gravity;
			this.move(this.xd, this.yd, this.zd);
			if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
				this.xd *= 1.1D;
				this.zd *= 1.1D;
			}
			this.xd *= this.friction;
			this.yd *= this.friction;
			this.zd *= this.friction;
			if (this.onGround) {
				this.xd *= 0.7D;
				this.zd *= 0.7D;
			}
			this.prevScale = this.scale;
			this.scale += (this.targetScale - this.scale) * 0.15F;
			FluidState fluidState = this.level.getFluidState(blockPos);
			if (blockPos.getY() + fluidState.getHeight(this.level, blockPos) >= this.y) {
				this.lifetime = this.age;
			}
			if (this.age++ >= this.lifetime) {
				if (this.prevScale == 0F) {
					this.remove();
				} else {
					this.targetScale = 0F;
					if (this.prevScale <= 0.04F) {
						this.scale = 0F;
					}
				}
			} else {
				this.targetScale = 1F;
				if (this.x == this.xo && this.y == this.yo && this.z == this.zo) this.age += 5;
			}
			boolean onGround = this.onGround;
			if (!rain) {
				double multXZ = (onGround ? 0.00025D : 0.0035D) * WIND_INTENSITY;
				double multY = (onGround ? 0.00025D : 0.00175D) * WIND_INTENSITY;
				Vec3 wind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 1D, 7D, 5D)
					.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
				this.xd += wind.x() * multXZ;
				this.yd += (wind.y() + 0.1D) * multY;
				this.zd += wind.z() * multXZ;
			}
		} else {
			this.remove();
		}
	}

	@Override
	public float getQuadSize(float partialTicks) {
		return this.quadSize * Mth.lerp(partialTicks, this.prevScale, this.scale);
	}

	@Override
	protected @NotNull Layer getLayer() {
		return Layer.OPAQUE;
	}

	@Environment(EnvType.CLIENT)
	public record Provider(@NotNull SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		@NotNull
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			PollenParticle pollenParticle = new PollenParticle(level, x, y, z, 0D, -0.800000011920929D, 0D, this.spriteSet.get(random));
			pollenParticle.lifetime = Mth.randomBetweenInclusive(random, 500, 1000);
			pollenParticle.gravity = 0.01F;
			pollenParticle.setColor(250F / 255F, 171F / 255F, 28F / 255F);
			pollenParticle.canExist = Optional.of(() -> WWBlockConfig.Client.POLLEN_ENABLED);
			return pollenParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public record PaleSporeFactory(@NotNull SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		@NotNull
		public Particle createParticle(
			@NotNull SimpleParticleType simpleParticleType,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			PollenParticle sporeParticle = new PollenParticle(level, x, y, z, 0D, -0.800000011920929D, 0D, this.spriteSet.get(random));
			sporeParticle.lifetime = Mth.randomBetweenInclusive(random, 200, 500);
			sporeParticle.gravity = 0.01F;
			sporeParticle.setColor(112F / 255F, 114F / 255F, 112F / 255F);
			return sporeParticle;
		}
	}
}
