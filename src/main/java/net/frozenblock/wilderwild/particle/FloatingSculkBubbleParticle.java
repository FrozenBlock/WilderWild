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
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.config.MiscConfig;
import net.frozenblock.wilderwild.particle.options.FloatingSculkBubbleParticleOptions;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class FloatingSculkBubbleParticle extends RisingParticle {
	private final SpriteSet spriteProvider;
	private final SoundEvent sound;
	private final int stayInflatedTime;

	private float currentInflation = 0F;
	private float targetInflation = 2F;

	protected FloatingSculkBubbleParticle(@NotNull ClientLevel clientLevel, double x, double y, double z, double size, int maxAge, @NotNull Vec3 velocity, @NotNull SpriteSet spriteProvider) {
		super(clientLevel, x, y, z, 0D, 0D, 0D);
		this.spriteProvider = spriteProvider;
		this.setSpriteFromAge(spriteProvider);
		this.xd = velocity.x();
		this.yd = velocity.y();
		this.zd = velocity.z();
		this.sound = size <= 0 ? RegisterSounds.PARTICLE_FLOATING_SCULK_BUBBLE_POP : RegisterSounds.PARTICLE_FLOATING_SCULK_BUBBLE_BIG_POP;
		if (size >= 1) {
			this.scale((float) (1.4F + size));
		}
		this.lifetime = Math.max(maxAge, 10);
		this.stayInflatedTime = (4 - this.lifetime) * -1;
	}

	@Override
	public int getLightColor(float tint) {
		return 240;
	}

	@Override
	@NotNull
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void setSpriteFromAge(@NotNull SpriteSet spriteProvider) {
		if (!this.removed) {
			int i = this.age < 3 ? this.age : (this.age < this.stayInflatedTime ? 3 : this.age - (this.stayInflatedTime) + 4);
			this.setSprite(spriteProvider.get(i, 7));
		}
	}

	@Override
	public void tick() {
		super.tick();
		Vec3 wind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 1.5D, 7D, 5D).scale(MiscConfig.get().getParticleWindIntensity());
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
			level.playSound(Minecraft.getInstance().player, this.x, this.y, this.z, this.sound, SoundSource.NEUTRAL, 0.4F, level.random.nextFloat() * 0.2F + 0.8F);
			this.setParticleSpeed(0D, 0D, 0D);
		}
		this.setSpriteFromAge(this.spriteProvider);
	}

	@Override
	public float getQuadSize(float partialTick) {
		return this.quadSize * Mth.lerp(partialTick, this.currentInflation, this.targetInflation);
	}

	@Environment(EnvType.CLIENT)
	public static class BubbleFactory implements ParticleProvider<FloatingSculkBubbleParticleOptions> {
		private final SpriteSet spriteProvider;

		public BubbleFactory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		@Override
		public Particle createParticle(@NotNull FloatingSculkBubbleParticleOptions options, @NotNull ClientLevel clientLevel, double x, double y, double z, double xd, double yd, double zd) {
			FloatingSculkBubbleParticle bubble = new FloatingSculkBubbleParticle(clientLevel, x, y, z, options.getSize(), options.getMaxAge(), options.getVelocity(), this.spriteProvider);
			bubble.setAlpha(1F);
			return bubble;
		}
	}

}
