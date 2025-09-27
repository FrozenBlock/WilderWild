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
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.particle.options.FloatingSculkBubbleParticleOptions;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class FloatingSculkBubbleParticle extends RisingParticle {
	private final SpriteSet spriteSet;
	private final int stayInflatedTime;

	private float currentInflation = 0F;
	private float targetInflation = 2F;

	protected FloatingSculkBubbleParticle(
		@NotNull ClientLevel clientLevel,
		double x,
		double y,
		double z,
		double size,
		int maxAge,
		@NotNull Vec3 velocity,
		@NotNull SpriteSet spriteSet
	) {
		super(clientLevel, x, y, z, 0D, 0D, 0D, spriteSet.first());
		this.spriteSet = spriteSet;
		this.setSpriteFromAge(spriteSet);
		this.xd = velocity.x();
		this.yd = velocity.y();
		this.zd = velocity.z();
		if (size >= 1) this.scale((float) (1.4F + size));
		this.lifetime = Math.max(maxAge, 10);
		this.stayInflatedTime = (4 - this.lifetime) * -1;
	}

	@Override
	public int getLightColor(float tint) {
		return 240;
	}

	@Override
	protected @NotNull Layer getLayer() {
		return Layer.TRANSLUCENT;
	}

	@Override
	public void setSpriteFromAge(@NotNull SpriteSet spriteSet) {
		if (!this.removed) {
			int i = this.age < 3 ? this.age : (this.age < this.stayInflatedTime ? 3 : this.age - (this.stayInflatedTime) + 4);
			this.setSprite(spriteSet.get(i, 7));
		}
	}

	@Override
	public void tick() {
		super.tick();
		Vec3 wind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 1.5D, 7D, 5D)
			.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
		this.xd += wind.x * 0.001D;
		this.yd += wind.y * 0.00005D;
		this.zd += wind.z * 0.001D;
		int flateAge = this.age - (this.stayInflatedTime) + 4;
		switch (this.age) {
			case 1 -> {
				this.currentInflation = 0;
				this.targetInflation = 2;
			}
			case 2 -> {
				this.currentInflation = 1F;
				this.targetInflation = 1.4F;
			}
			case 3 -> {
				this.currentInflation = 1F;
				this.targetInflation = 1.3F;
			}
			case 4 -> {
				this.currentInflation = 1.3F;
				this.targetInflation = 0.7F;
			}
			case 5 -> {
				this.currentInflation = 0.7F;
				this.targetInflation = 1.2F;
			}
			case 6 -> {
				this.currentInflation = 1.2F;
				this.targetInflation = 0.9F;
			}
			case 7 -> {
				this.currentInflation = 0.9F;
				this.targetInflation = 1.04F;
			}
			case 8 -> {
				this.currentInflation = 1.02F;
				this.targetInflation = 0.95F;
			}
			case 9 -> {
				this.currentInflation = 0.95F;
				this.targetInflation = 1F;
			}
			default -> {
				switch (flateAge) {
					case 3 -> {
						this.currentInflation = 1F;
						this.targetInflation = 1.3F;
					}
					case 4 -> {
						this.currentInflation = 1;
						this.targetInflation = 1.2F;
					}
					case 5 -> {
						this.currentInflation = 1;
						this.targetInflation = 1.1F;
					}
					case 6 -> {
						this.currentInflation = 1.1F;
						this.targetInflation = 1.4F;
					}
					case 7 -> {
						this.currentInflation = 1.4F;
						this.targetInflation = 1.65F;
					}
					case 8 -> {
						this.currentInflation = 1.65F;
						this.targetInflation = 1.95F;
					}
					default -> {
						this.currentInflation = 1F;
						this.targetInflation = 1F;
					}
				}
			}
		}

		if (this.age == this.stayInflatedTime + 1) {
			this.level.playLocalSound(this.x, this.y, this.z, WWSounds.PARTICLE_FLOATING_SCULK_BUBBLE_POP, SoundSource.NEUTRAL, 0.4F, this.level.random.nextFloat() * 0.2F + 0.8F, false);
			this.setParticleSpeed(0D, 0D, 0D);
		}
		this.setSpriteFromAge(this.spriteSet);
	}

	@Override
	public float getQuadSize(float partialTick) {
		return this.quadSize * Mth.lerp(partialTick, this.currentInflation, this.targetInflation);
	}

	public static class Provider implements ParticleProvider<FloatingSculkBubbleParticleOptions> {
		private final SpriteSet spriteSet;

		public Provider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(
			@NotNull FloatingSculkBubbleParticleOptions options,
			@NotNull ClientLevel level,
			double x, double y, double z,
			double xd, double yd, double zd,
			RandomSource random
		) {
			FloatingSculkBubbleParticle bubble = new FloatingSculkBubbleParticle(level, x, y, z, options.getSize(), options.getMaxAge(), options.getVelocity(), this.spriteSet);
			bubble.setAlpha(1F);
			return bubble;
		}
	}

}
